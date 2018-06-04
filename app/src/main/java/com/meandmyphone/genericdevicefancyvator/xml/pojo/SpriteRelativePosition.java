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
 * <p>Java class for sprite_relative_position complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sprite_relative_position">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relative_sprite_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="relative_sprite_point" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}pivot"/>
 *         &lt;element name="distance_from_sprite" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}measure"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sprite_relative_position", propOrder = {
    "relativeSpriteId",
    "relativeSpritePoint",
    "distanceFromSprite"
})
public class SpriteRelativePosition {

    @XmlElement(name = "relative_sprite_id", required = true)
    protected String relativeSpriteId;
    @XmlElement(name = "relative_sprite_point", required = true)
    @XmlSchemaType(name = "string")
    protected Pivot relativeSpritePoint;
    @XmlElement(name = "distance_from_sprite", required = true)
    protected Measure distanceFromSprite;

    /**
     * Gets the value of the relativeSpriteId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelativeSpriteId() {
        return relativeSpriteId;
    }

    /**
     * Sets the value of the relativeSpriteId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelativeSpriteId(String value) {
        this.relativeSpriteId = value;
    }

    /**
     * Gets the value of the relativeSpritePoint property.
     * 
     * @return
     *     possible object is
     *     {@link Pivot }
     *     
     */
    public Pivot getRelativeSpritePoint() {
        return relativeSpritePoint;
    }

    /**
     * Sets the value of the relativeSpritePoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pivot }
     *     
     */
    public void setRelativeSpritePoint(Pivot value) {
        this.relativeSpritePoint = value;
    }

    /**
     * Gets the value of the distanceFromSprite property.
     * 
     * @return
     *     possible object is
     *     {@link Measure }
     *     
     */
    public Measure getDistanceFromSprite() {
        return distanceFromSprite;
    }

    /**
     * Sets the value of the distanceFromSprite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Measure }
     *     
     */
    public void setDistanceFromSprite(Measure value) {
        this.distanceFromSprite = value;
    }

}
