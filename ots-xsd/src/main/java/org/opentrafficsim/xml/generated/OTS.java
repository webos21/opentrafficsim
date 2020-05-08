//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.09 at 12:21:06 AM CEST 
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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element ref="{http://www.opentrafficsim.org/ots}DEFINITIONS"/&gt;
 *         &lt;element ref="{http://www.opentrafficsim.org/ots}NETWORK"/&gt;
 *         &lt;element ref="{http://www.opentrafficsim.org/ots}NETWORKDEMAND" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.opentrafficsim.org/ots}CONTROL" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.opentrafficsim.org/ots}MODEL" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.opentrafficsim.org/ots}SCENARIO" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://www.opentrafficsim.org/ots}RUN"/&gt;
 *         &lt;element ref="{http://www.opentrafficsim.org/ots}ANIMATION" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}base"/&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}space default="preserve""/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "definitions",
    "network",
    "networkdemand",
    "control",
    "model",
    "scenario",
    "run",
    "animation"
})
@XmlRootElement(name = "OTS")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
public class OTS
    implements Serializable
{

    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    private final static long serialVersionUID = 10102L;
    @XmlElement(name = "DEFINITIONS", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected DEFINITIONS definitions;
    @XmlElement(name = "NETWORK", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected NETWORK network;
    @XmlElement(name = "NETWORKDEMAND")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected List<NETWORKDEMAND> networkdemand;
    @XmlElement(name = "CONTROL")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected List<CONTROL> control;
    @XmlElement(name = "MODEL")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected List<MODELTYPE> model;
    @XmlElement(name = "SCENARIO", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected List<SCENARIO> scenario;
    @XmlElement(name = "RUN", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected RUN run;
    @XmlElement(name = "ANIMATION")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected ANIMATION animation;
    @XmlAttribute(name = "base", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlSchemaType(name = "anyURI")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected String base;
    @XmlAttribute(name = "space", namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    protected String space;

    /**
     * Gets the value of the definitions property.
     * 
     * @return
     *     possible object is
     *     {@link DEFINITIONS }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public DEFINITIONS getDEFINITIONS() {
        return definitions;
    }

    /**
     * Sets the value of the definitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link DEFINITIONS }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setDEFINITIONS(DEFINITIONS value) {
        this.definitions = value;
    }

    /**
     * Gets the value of the network property.
     * 
     * @return
     *     possible object is
     *     {@link NETWORK }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public NETWORK getNETWORK() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link NETWORK }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setNETWORK(NETWORK value) {
        this.network = value;
    }

    /**
     * Gets the value of the networkdemand property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the networkdemand property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNETWORKDEMAND().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NETWORKDEMAND }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public List<NETWORKDEMAND> getNETWORKDEMAND() {
        if (networkdemand == null) {
            networkdemand = new ArrayList<NETWORKDEMAND>();
        }
        return this.networkdemand;
    }

    /**
     * Gets the value of the control property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the control property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCONTROL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CONTROL }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public List<CONTROL> getCONTROL() {
        if (control == null) {
            control = new ArrayList<CONTROL>();
        }
        return this.control;
    }

    /**
     * Gets the value of the model property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the model property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMODEL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MODELTYPE }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public List<MODELTYPE> getMODEL() {
        if (model == null) {
            model = new ArrayList<MODELTYPE>();
        }
        return this.model;
    }

    /**
     * Gets the value of the scenario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scenario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSCENARIO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SCENARIO }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public List<SCENARIO> getSCENARIO() {
        if (scenario == null) {
            scenario = new ArrayList<SCENARIO>();
        }
        return this.scenario;
    }

    /**
     * Gets the value of the run property.
     * 
     * @return
     *     possible object is
     *     {@link RUN }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public RUN getRUN() {
        return run;
    }

    /**
     * Sets the value of the run property.
     * 
     * @param value
     *     allowed object is
     *     {@link RUN }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setRUN(RUN value) {
        this.run = value;
    }

    /**
     * Gets the value of the animation property.
     * 
     * @return
     *     possible object is
     *     {@link ANIMATION }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public ANIMATION getANIMATION() {
        return animation;
    }

    /**
     * Sets the value of the animation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ANIMATION }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setANIMATION(ANIMATION value) {
        this.animation = value;
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
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
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setBase(String value) {
        this.base = value;
    }

    /**
     * Gets the value of the space property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public String getSpace() {
        if (space == null) {
            return "preserve";
        } else {
            return space;
        }
    }

    /**
     * Sets the value of the space property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-05-09T12:21:06+02:00", comments = "JAXB RI v2.3.0")
    public void setSpace(String value) {
        this.space = value;
    }

}
