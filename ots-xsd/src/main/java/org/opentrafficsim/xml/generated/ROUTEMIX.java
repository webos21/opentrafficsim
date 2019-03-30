//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.30 at 04:17:01 PM CET 
//


package org.opentrafficsim.xml.generated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ROUTE" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="WEIGHT" use="required" type="{http://www.w3.org/2001/XMLSchema}double" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="RANDOMSTREAM" type="{http://www.w3.org/2001/XMLSchema}string" default="default" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}base"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "route"
})
@XmlRootElement(name = "ROUTEMIX")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
public class ROUTEMIX
    implements Serializable
{

    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    private final static long serialVersionUID = 10102L;
    @XmlElement(name = "ROUTE", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    protected List<ROUTEMIX.ROUTE> route;
    @XmlAttribute(name = "ID", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    protected String id;
    @XmlAttribute(name = "RANDOMSTREAM")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    protected String randomstream;
    @XmlAttribute(name = "base", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlSchemaType(name = "anyURI")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    protected String base;

    /**
     * Gets the value of the route property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the route property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getROUTE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ROUTEMIX.ROUTE }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    public List<ROUTEMIX.ROUTE> getROUTE() {
        if (route == null) {
            route = new ArrayList<ROUTEMIX.ROUTE>();
        }
        return this.route;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
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
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the randomstream property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    public String getRANDOMSTREAM() {
        if (randomstream == null) {
            return "default";
        } else {
            return randomstream;
        }
    }

    /**
     * Sets the value of the randomstream property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    public void setRANDOMSTREAM(String value) {
        this.randomstream = value;
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    public String getBase() {
        return base;
    }

    /**
     * Sets the value of the base property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    public void setBase(String value) {
        this.base = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="WEIGHT" use="required" type="{http://www.w3.org/2001/XMLSchema}double" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
    public static class ROUTE
        implements Serializable
    {

        @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
        private final static long serialVersionUID = 10102L;
        @XmlAttribute(name = "ID", required = true)
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
        protected String id;
        @XmlAttribute(name = "WEIGHT", required = true)
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
        protected double weight;

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
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
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the weight property.
         * 
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
        public double getWEIGHT() {
            return weight;
        }

        /**
         * Sets the value of the weight property.
         * 
         */
        @Generated(value = "com.sun.tools.xjc.Driver", date = "2019-03-30T04:17:01+01:00", comments = "JAXB RI v2.3.0")
        public void setWEIGHT(double value) {
            this.weight = value;
        }

    }

}
