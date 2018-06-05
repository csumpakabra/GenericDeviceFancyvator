//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.04 at 08:10:56 PM CEST 
//


package com.meandmyphone.genericdevicefancyvator.xml.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for measure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="measure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relativity" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}relativity_type"/>
 *         &lt;element name="relative_to" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "measure", propOrder = {
    "relativity",
    "relativeTo",
    "value"
})
public class Measure {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RelativityType relativity;
    @XmlElement(name = "relative_to")
    protected String relativeTo;
    protected float value;

    /**
     * Gets the value of the relativity property.
     * 
     * @return
     *     possible object is
     *     {@link RelativityType }
     *     
     */
    public RelativityType getRelativity() {
        return relativity;
    }

    /**
     * Sets the value of the relativity property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelativityType }
     *     
     */
    public void setRelativity(RelativityType value) {
        this.relativity = value;
    }

    /**
     * Gets the value of the relativeTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelativeTo() {
        return relativeTo;
    }

    /**
     * Sets the value of the relativeTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelativeTo(String value) {
        this.relativeTo = value;
    }

    /**
     * Gets the value of the value property.
     * 
     */
    public float getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(float value) {
        this.value = value;
    }

}