//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.07 at 09:01:16 PM CEST 
//


package com.meandmyphone.genericdevicefancyvator.xml.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for on_click complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="on_click">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sprite" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}sprite" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "on_click", propOrder = {
    "sprite"
})
public class OnClick {

    @XmlElement(required = true)
    protected List<Sprite> sprite;

    /**
     * Gets the value of the sprite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sprite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSprite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sprite }
     * 
     * 
     */
    public List<Sprite> getSprite() {
        if (sprite == null) {
            sprite = new ArrayList<Sprite>();
        }
        return this.sprite;
    }

}
