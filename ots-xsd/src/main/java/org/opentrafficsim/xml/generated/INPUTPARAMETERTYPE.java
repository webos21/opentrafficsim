//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.08.12 at 12:28:09 AM CEST 
//


package org.opentrafficsim.xml.generated;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentrafficsim.xml.bindings.ClassNameAdapter;


/**
 * <p>Java class for INPUTPARAMETERTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="INPUTPARAMETERTYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="TYPE" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;pattern value="(\ {0})CLASS|STRING|DOUBLE|INTEGER|(\ {0})LENGTH|POSITION|DURATION|TIME|SPEED|ACCELERATION|FREQUENCY|LINEARDENSITY|(\ {0})FRACTION"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="VALUE" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="CLASS" type="{http://www.opentrafficsim.org/ots}CLASSNAMETYPE" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INPUTPARAMETERTYPE")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
public class INPUTPARAMETERTYPE
    implements Serializable
{

    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    private final static long serialVersionUID = 10102L;
    @XmlAttribute(name = "TYPE", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    protected String type;
    @XmlAttribute(name = "VALUE", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    protected String value;
    @XmlAttribute(name = "ID", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    protected String id;
    @XmlAttribute(name = "CLASS")
    @XmlJavaTypeAdapter(ClassNameAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    protected Class _class;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    public String getTYPE() {
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
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    public void setTYPE(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    public String getVALUE() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    public void setVALUE(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
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
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the class property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    public Class getCLASS() {
        return _class;
    }

    /**
     * Sets the value of the class property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-08-12T12:28:09+02:00", comments = "JAXB RI v2.3.0")
    public void setCLASS(Class value) {
        this._class = value;
    }

}
