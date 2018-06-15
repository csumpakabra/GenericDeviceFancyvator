package com.meandmyphone.genericdevicefancyvator.json.pojo.transform;

public class Measure {

    private RelativityType relativity;
    private String relativeTo;
    private float value;

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
