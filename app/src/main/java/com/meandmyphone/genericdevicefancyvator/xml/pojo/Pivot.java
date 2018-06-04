//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.04 at 08:10:56 PM CEST 
//


package com.meandmyphone.genericdevicefancyvator.xml.pojo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pivot.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="pivot">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="top_left"/>
 *     &lt;enumeration value="top_center"/>
 *     &lt;enumeration value="top_right"/>
 *     &lt;enumeration value="center_right"/>
 *     &lt;enumeration value="bot_right"/>
 *     &lt;enumeration value="bot_center"/>
 *     &lt;enumeration value="bot_left"/>
 *     &lt;enumeration value="center_left"/>
 *     &lt;enumeration value="center"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "pivot")
@XmlEnum
public enum Pivot {

    @XmlEnumValue("top_left")
    TOP_LEFT("top_left"),
    @XmlEnumValue("top_center")
    TOP_CENTER("top_center"),
    @XmlEnumValue("top_right")
    TOP_RIGHT("top_right"),
    @XmlEnumValue("center_right")
    CENTER_RIGHT("center_right"),
    @XmlEnumValue("bot_right")
    BOT_RIGHT("bot_right"),
    @XmlEnumValue("bot_center")
    BOT_CENTER("bot_center"),
    @XmlEnumValue("bot_left")
    BOT_LEFT("bot_left"),
    @XmlEnumValue("center_left")
    CENTER_LEFT("center_left"),
    @XmlEnumValue("center")
    CENTER("center");
    private final String value;

    Pivot(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Pivot fromValue(String v) {
        for (Pivot c: Pivot.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
