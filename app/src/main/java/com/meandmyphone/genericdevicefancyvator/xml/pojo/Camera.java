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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for camera complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="camera">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="recenter_after_swipe" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "camera", propOrder = {
    "recenterAfterSwipe"
})
public class Camera {

    @XmlElement(name = "recenter_after_swipe")
    protected boolean recenterAfterSwipe;

    /**
     * Gets the value of the recenterAfterSwipe property.
     * 
     */
    public boolean isRecenterAfterSwipe() {
        return recenterAfterSwipe;
    }

    /**
     * Sets the value of the recenterAfterSwipe property.
     * 
     */
    public void setRecenterAfterSwipe(boolean value) {
        this.recenterAfterSwipe = value;
    }

}
