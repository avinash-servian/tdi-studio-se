
package org.talend.mdm.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for executeRoutingOrderV2AsynchronouslyResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="executeRoutingOrderV2AsynchronouslyResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://www.talend.com/mdm}WSRoutingOrderV2PK" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeRoutingOrderV2AsynchronouslyResponse", propOrder = {
    "_return"
})
public class ExecuteRoutingOrderV2AsynchronouslyResponse {

    @XmlElement(name = "return")
    protected WSRoutingOrderV2PK _return;

    /**
     * Default no-arg constructor
     * 
     */
    public ExecuteRoutingOrderV2AsynchronouslyResponse() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public ExecuteRoutingOrderV2AsynchronouslyResponse(final WSRoutingOrderV2PK _return) {
        this._return = _return;
    }

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link WSRoutingOrderV2PK }
     *     
     */
    public WSRoutingOrderV2PK getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSRoutingOrderV2PK }
     *     
     */
    public void setReturn(WSRoutingOrderV2PK value) {
        this._return = value;
    }

}
