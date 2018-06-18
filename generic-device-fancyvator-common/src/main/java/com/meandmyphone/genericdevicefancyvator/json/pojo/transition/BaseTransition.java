
package com.meandmyphone.genericdevicefancyvator.json.pojo.transition;

public class BaseTransition {


    private TransitionType transitionType;

    private CycleType cycleType;

    private int cycleCount;

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
