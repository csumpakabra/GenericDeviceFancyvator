
package com.meandmyphone.genericdevicefancyvator.json.pojo.transition;

public class Transition extends BaseTransition {


    private int duration;

    private Ease ease;

    private DestroyEffect destroyEffect;

    private boolean destroySpriteOnFinished;

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

    public DestroyEffect getDestroyEffect() {
        if (destroyEffect == null) {
            return DestroyEffect.NONE;
        }
        return destroyEffect;
    }

    public void setDestroyEffect(DestroyEffect destroyEffect) {
        this.destroyEffect = destroyEffect;
    }

    public boolean isDestroySpriteOnFinished() {
        return destroySpriteOnFinished;
    }

    public void setDestroySpriteOnFinished(boolean destroySpriteOnFinished) {
        this.destroySpriteOnFinished = destroySpriteOnFinished;
    }
}
