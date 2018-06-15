
package com.meandmyphone.genericdevicefancyvator.json.pojo.transition;

import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Measure;

public class TranslateTransition
    extends Transition
{

    private Measure byX;
    private Measure byY;

    /**
     * Gets the value of the byX property.
     * 
     * @return
     *     possible object is
     *     {@link Measure }
     *     
     */
    public Measure getByX() {
        return byX;
    }

    /**
     * Sets the value of the byX property.
     * 
     * @param value
     *     allowed object is
     *     {@link Measure }
     *     
     */
    public void setByX(Measure value) {
        this.byX = value;
    }

    /**
     * Gets the value of the byY property.
     * 
     * @return
     *     possible object is
     *     {@link Measure }
     *     
     */
    public Measure getByY() {
        return byY;
    }

    /**
     * Sets the value of the byY property.
     * 
     * @param value
     *     allowed object is
     *     {@link Measure }
     *     
     */
    public void setByY(Measure value) {
        this.byY = value;
    }

}
