//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.04 at 02:18:21 PM CET 
//


package org.opentrafficsim.xml.generated;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LENGTHDISTTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LENGTHDISTTYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.opentrafficsim.org/ots}CONTDISTTYPE"&gt;
 *       &lt;attribute name="LENGTHUNIT" type="{http://www.opentrafficsim.org/ots}LENGTHUNITTYPE" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LENGTHDISTTYPE")
@XmlSeeAlso({
    PARAMETERLENGTHDIST.class
})
@Generated(value = "com.sun.tools.xjc.Driver", date = "2020-02-04T02:18:21+01:00", comments = "JAXB RI v2.3.0")
public class LENGTHDISTTYPE
    extends CONTDISTTYPE
    implements Serializable
{

    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-02-04T02:18:21+01:00", comments = "JAXB RI v2.3.0")
    private final static long serialVersionUID = 10102L;
    @XmlAttribute(name = "LENGTHUNIT")
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-02-04T02:18:21+01:00", comments = "JAXB RI v2.3.0")
    protected String lengthunit;

    /**
     * Gets the value of the lengthunit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-02-04T02:18:21+01:00", comments = "JAXB RI v2.3.0")
    public String getLENGTHUNIT() {
        return lengthunit;
    }

    /**
     * Sets the value of the lengthunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2020-02-04T02:18:21+01:00", comments = "JAXB RI v2.3.0")
    public void setLENGTHUNIT(String value) {
        this.lengthunit = value;
    }

}
