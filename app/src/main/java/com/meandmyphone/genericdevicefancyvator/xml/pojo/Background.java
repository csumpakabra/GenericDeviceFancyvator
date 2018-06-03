//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.03 at 08:33:33 PM CEST 
//


package com.meandmyphone.genericdevicefancyvator.xml.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for background complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="background">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="background_type" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}background_type"/>
 *         &lt;element name="background_source">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="image_background" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}image_background"/>
 *                   &lt;element name="diffuse_background" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}diffuse_background"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "background", propOrder = {
    "backgroundType",
    "backgroundSource"
})
public class Background {

    @XmlElement(name = "background_type", required = true)
    @XmlSchemaType(name = "string")
    protected BackgroundType backgroundType;
    @XmlElement(name = "background_source", required = true)
    protected Background.BackgroundSource backgroundSource;

    /**
     * Gets the value of the backgroundType property.
     * 
     * @return
     *     possible object is
     *     {@link BackgroundType }
     *     
     */
    public BackgroundType getBackgroundType() {
        return backgroundType;
    }

    /**
     * Sets the value of the backgroundType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BackgroundType }
     *     
     */
    public void setBackgroundType(BackgroundType value) {
        this.backgroundType = value;
    }

    /**
     * Gets the value of the backgroundSource property.
     * 
     * @return
     *     possible object is
     *     {@link Background.BackgroundSource }
     *     
     */
    public Background.BackgroundSource getBackgroundSource() {
        return backgroundSource;
    }

    /**
     * Sets the value of the backgroundSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link Background.BackgroundSource }
     *     
     */
    public void setBackgroundSource(Background.BackgroundSource value) {
        this.backgroundSource = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element name="image_background" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}image_background"/>
     *         &lt;element name="diffuse_background" type="{https://github.com/csumpakabra/GenericDeviceFancyvator/blob/master/app/src/main/schemas/GDFScene.xsd}diffuse_background"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "imageBackground",
        "diffuseBackground"
    })
    public static class BackgroundSource {

        @XmlElement(name = "image_background")
        protected ImageBackground imageBackground;
        @XmlElement(name = "diffuse_background")
        protected DiffuseBackground diffuseBackground;

        /**
         * Gets the value of the imageBackground property.
         * 
         * @return
         *     possible object is
         *     {@link ImageBackground }
         *     
         */
        public ImageBackground getImageBackground() {
            return imageBackground;
        }

        /**
         * Sets the value of the imageBackground property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImageBackground }
         *     
         */
        public void setImageBackground(ImageBackground value) {
            this.imageBackground = value;
        }

        /**
         * Gets the value of the diffuseBackground property.
         * 
         * @return
         *     possible object is
         *     {@link DiffuseBackground }
         *     
         */
        public DiffuseBackground getDiffuseBackground() {
            return diffuseBackground;
        }

        /**
         * Sets the value of the diffuseBackground property.
         * 
         * @param value
         *     allowed object is
         *     {@link DiffuseBackground }
         *     
         */
        public void setDiffuseBackground(DiffuseBackground value) {
            this.diffuseBackground = value;
        }

    }

}
