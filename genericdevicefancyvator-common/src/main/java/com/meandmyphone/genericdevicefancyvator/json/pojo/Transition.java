
package com.meandmyphone.genericdevicefancyvator.json.pojo;

public class Transition {


    private TransitionType transitionType;

    private int duration;

    private Ease ease;

    private CycleType cycleType;

    private int cycleCount;

    /**
     * Gets the value of the duration property.
     * 
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     */
    public void setDuration(int value) {
        this.duration = value;
    }

    /**
     * Gets the value of the ease property.
     * 
     * @return
     *     possible object is
     *     {@link Ease }
     *     
     */
    public Ease getEase() {
        if (ease == null) {
            return Ease.LINEAR;
        } else {
            return ease;
        }
    }

    /**
     * Sets the value of the ease property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ease }
     *     
     */
    public void setEase(Ease value) {
        this.ease = value;
    }

    /**
     * Gets the value of the cycleType property.
     * 
     * @return
     *     possible object is
     *     {@link CycleType }
     *     
     */
    public CycleType getCycleType() {
        if (cycleType == null) {
            return CycleType.RESTART;
        } else {
            return cycleType;
        }
    }

    /**
     * Sets the value of the cycleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CycleType }
     *     
     */
    public void setCycleType(CycleType value) {
        this.cycleType = value;
    }

    /**
     * Gets the value of the cycleCount property.
     * 
     */
    public int getCycleCount() {
        return cycleCount;
    }

    /**
     * Sets the value of the cycleCount property.
     * 
     */
    public void setCycleCount(int value) {
        this.cycleCount = value;
    }

    public TransitionType getTransitionType() {
        return transitionType;
    }

    public void setTransitionType(TransitionType transitionType) {
        this.transitionType = transitionType;
    }
}
