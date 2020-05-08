//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.09 at 12:21:06 AM CEST 
//


package org.opentrafficsim.xml.generated;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentrafficsim.xml.bindings.LengthAdapter;
import org.opentrafficsim.xml.bindings.PositiveLengthAdapter;
import org.opentrafficsim.xml.bindings.StripeTypeAdapter;
import org.opentrafficsim.xml.bindings.types.StripeType;


/**
 * <p>Java class for CSESTRIPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CSESTRIPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice minOccurs="0"&gt;
 *           &lt;element name="CENTEROFFSET" type="{http://www.opentrafficsim.org/ots}LENGTHTYPE"/&gt;
 *           &lt;sequence&gt;
 *             &lt;choice&gt;
 *               &lt;element name="CENTEROFFSETSTART" type="{http://www.opentrafficsim.org/ots}LENGTHTYPE"/&gt;
 *             &lt;/choice&gt;
 *             &lt;choice&gt;
 *               &lt;element name="CENTEROFFSETEND" type="{http://www.opentrafficsim.org/ots}LENGTHTYPE"/&gt;
 *             &lt;/choice&gt;
 *           &lt;/sequence&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="DRAWINGWIDTH" type="{http://www.opentrafficsim.org/ots}POSITIVELENGTHTYPE" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="TYPE" use="required" type="{http://www.opentrafficsim.org/ots}STRIPETYPE" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSESTRIPE", propOrder = {
    "centeroffset",
    "centeroffsetstart",
    "centeroffsetend",
    "drawingwidth"
})
@Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
public class CSESTRIPE implements Serializable
{

    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    private final static long serialVersionUID = 10102L;
    @XmlElement(name = "CENTEROFFSET", type = String.class)
    @XmlJavaTypeAdapter(LengthAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected org.djunits.value.vdouble.scalar.Length centeroffset;
    @XmlElement(name = "CENTEROFFSETSTART", type = String.class)
    @XmlJavaTypeAdapter(LengthAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected org.djunits.value.vdouble.scalar.Length centeroffsetstart;
    @XmlElement(name = "CENTEROFFSETEND", type = String.class)
    @XmlJavaTypeAdapter(LengthAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected org.djunits.value.vdouble.scalar.Length centeroffsetend;
    @XmlElement(name = "DRAWINGWIDTH", type = String.class)
    @XmlJavaTypeAdapter(PositiveLengthAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected org.djunits.value.vdouble.scalar.Length drawingwidth;
    @XmlAttribute(name = "ID")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected String id;
    @XmlAttribute(name = "TYPE", required = true)
    @XmlJavaTypeAdapter(StripeTypeAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected StripeType type;

    /**
     * Gets the value of the centeroffset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public org.djunits.value.vdouble.scalar.Length getCENTEROFFSET() {
        return centeroffset;
    }

    /**
     * Sets the value of the centeroffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setCENTEROFFSET(org.djunits.value.vdouble.scalar.Length value) {
        this.centeroffset = value;
    }

    /**
     * Gets the value of the centeroffsetstart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public org.djunits.value.vdouble.scalar.Length getCENTEROFFSETSTART() {
        return centeroffsetstart;
    }

    /**
     * Sets the value of the centeroffsetstart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setCENTEROFFSETSTART(org.djunits.value.vdouble.scalar.Length value) {
        this.centeroffsetstart = value;
    }

    /**
     * Gets the value of the centeroffsetend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public org.djunits.value.vdouble.scalar.Length getCENTEROFFSETEND() {
        return centeroffsetend;
    }

    /**
     * Sets the value of the centeroffsetend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setCENTEROFFSETEND(org.djunits.value.vdouble.scalar.Length value) {
        this.centeroffsetend = value;
    }

    /**
     * Gets the value of the drawingwidth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public org.djunits.value.vdouble.scalar.Length getDRAWINGWIDTH() {
        return drawingwidth;
    }

    /**
     * Sets the value of the drawingwidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setDRAWINGWIDTH(org.djunits.value.vdouble.scalar.Length value) {
        this.drawingwidth = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public StripeType getTYPE() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setTYPE(StripeType value) {
        this.type = value;
    }

}
