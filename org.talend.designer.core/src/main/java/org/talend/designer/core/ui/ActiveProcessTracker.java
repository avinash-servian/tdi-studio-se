// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.core.ui;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.talend.core.model.process.IProcess;
import org.talend.designer.core.DesignerPlugin;
import org.talend.designer.core.ui.editor.process.Process;
import org.talend.designer.core.ui.views.contexts.Contexts;
import org.talend.designer.core.ui.views.problems.Problems;
import org.talend.designer.core.ui.views.statsandlogs.StatsAndLogs;
import org.talend.designer.core.ui.views.statsandlogs.StatsAndLogsView;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.sqlbuilder.util.UIUtils;

/**
 * Track the active Process being edited. <br/>
 * 
 * $Id$
 * 
 */
public class ActiveProcessTracker implements IPartListener {

    private static IProcess currentProcess;

    public IProcess getJobFromActivatedEditor(IWorkbenchPart part) {
        if (MultiPageTalendEditor.ID.equals(part.getSite().getId())) {
            MultiPageTalendEditor mpte = (MultiPageTalendEditor) part;
            mpte.setName();

            IProcess process = mpte.getTalendEditor().getProcess();
            return process;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
     */
    public void partActivated(final IWorkbenchPart part) {
        IProcess process = getJobFromActivatedEditor(part);
        if (process != null) {
            if (process instanceof Process) {
                Process p = (Process) process;
                if (!p.isReadOnly() && p.isActivate()) {
                    if (p.checkDifferenceWithRepository()) {
                        MultiPageTalendEditor mpte = (MultiPageTalendEditor) part;
                        mpte.getTalendEditor().setDirty(true);
                    }
                }
            }
        }
    }

    // private boolean updateContextSection() {
    // boolean modified = false;
    // IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    // IViewPart view = page.findView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$
    // PropertySheet sheet = (PropertySheet) view;
    // if (view instanceof TabbedPropertySheetPage) {
    // TabbedPropertySheetPage tabbedPropertySheetPage = (TabbedPropertySheetPage) sheet.getCurrentPage();
    // if (tabbedPropertySheetPage.getCurrentTab() == null) {
    // return modified;
    // }
    // ISection[] sections = tabbedPropertySheetPage.getCurrentTab().getSections();
    // for (int i = 0; i < sections.length; i++) {
    // if (sections[i] instanceof ContextProcessSection2) {
    // ContextProcessSection2 currentSection = (ContextProcessSection2) sections[i];
    // modified = currentSection.updateContextView();
    // }
    // }
    // }
    // return modified;
    // }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
     */
    public void partBroughtToTop(IWorkbenchPart part) {
        IProcess process = getJobFromActivatedEditor(part);
        if (process != null) {
            currentProcess = process;
             setContextsView(process);
            setStatsAndLogsView(process);
        }
    }

    /**
     * ftang Comment method "setStatsAndLogsView".
     * @param process
     */
    private void setStatsAndLogsView(IProcess process) {
        StatsAndLogs.setTitle("Job " + process.getProperty().getLabel()); //$NON-NLS-1$
        StatsAndLogs.switchToCurStatsAndLogsView(); 
    }

    /**
     * qzhang Comment method "setProblemsView".
     * 
     * @param process
     */
    private void addJobInProblemView(IProcess process) {
        Problems.addProcess(process);

        IRunProcessService service = DesignerPlugin.getDefault().getRunProcessService();
        service.setActiveProcess(process);

        Problems.setTitle("Job " + process.getProperty().getLabel()); //$NON-NLS-1$
    }

    /**
     * qzhang Comment method "setProblemsView".
     * 
     * @param process
     */
    private void setContextsView(IProcess process) {

        IRunProcessService service = DesignerPlugin.getDefault().getRunProcessService();
        service.setActiveProcess(process);

        Contexts.setTitle("Job " + process.getProperty().getLabel()); //$NON-NLS-1$
        Contexts.switchToCurContextsView();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
     */
    public void partClosed(IWorkbenchPart part) {
        IProcess process = getJobFromActivatedEditor(part);
        if (process != null) {
            Problems.removeProblemsByProcess(process);
            Problems.removeJob(process);
            IRunProcessService service = DesignerPlugin.getDefault().getRunProcessService();
            service.removeProcess(process);

            if (currentProcess == process) {
                Problems.setTitle(""); //$NON-NLS-1$
                Problems.clearAll();
                Contexts.setTitle(""); //$NON-NLS-1$
                Contexts.clearAll();
                StatsAndLogs.setTitle("");
                StatsAndLogs.clearAll();
            }
            UIUtils.closeSqlBuilderDialogs(process.getName());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
     */
    public void partDeactivated(IWorkbenchPart part) {
        IProcess process = getJobFromActivatedEditor(part);
        if (process != null) {
            MultiPageTalendEditor mpte = (MultiPageTalendEditor) part;
            mpte.getTalendEditor().savePaletteState();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
     */
    public void partOpened(IWorkbenchPart part) {
        IProcess process = getJobFromActivatedEditor(part);
        if (process != null) {
            currentProcess = process;
            addJobInProblemView(process);
        }
    }
}
