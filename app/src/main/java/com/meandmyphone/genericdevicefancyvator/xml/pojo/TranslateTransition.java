//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.07 at 09:01:16 PM CEST 
//


package com.meandmyphone.genericdevicefancyvator.xml.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for translate_transition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="translate_transition">
 *   &lt;complexContent>
 *     &lt;extension base="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}transition">
 *       &lt;sequence>
 *         &lt;element name="from_x" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="to_x" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="from_y" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="to_y" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "translate_transition", propOrder = {
    "fromX",
    "toX",
    "fromY",
    "toY"
})
public class TranslateTransition
    extends Transition
{

    @XmlElement(name = "from_x")
    protected float fromX;
    @XmlElement(name = "to_x")
    protected float toX;
    @XmlElement(name = "from_y")
    protected float fromY;
    @XmlElement(name = "to_y")
    protected float toY;

    /**
     * Gets the value of the fromX property.
     * 
     */
    public float getFromX() {
        return fromX;
    }

    /**
     * Sets the value of the fromX property.
     * 
     */
    public void setFromX(float value) {
        this.fromX = value;
    }

    /**
     * Gets the value of the toX property.
     * 
     */
    public float getToX() {
        return toX;
    }

    /**
     * Sets the value of the toX property.
     * 
     */
    public void setToX(float value) {
        this.toX = value;
    }

    /**
     * Gets the value of the fromY property.
     * 
     */
    public float getFromY() {
        return fromY;
    }

    /**
     * Sets the value of the fromY property.
     * 
     */
    public void setFromY(float value) {
        this.fromY = value;
    }

    /**
     * Gets the value of the toY property.
     * 
     */
    public float getToY() {
        return toY;
    }

    /**
     * Sets the value of the toY property.
     * 
     */
    public void setToY(float value) {
        this.toY = value;
    }

}
