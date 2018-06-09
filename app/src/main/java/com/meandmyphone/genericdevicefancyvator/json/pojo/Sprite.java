package com.meandmyphone.genericdevicefancyvator.json.pojo;


import java.util.ArrayList;
import java.util.List;

public class Sprite {

    public String id;
    public String resource;
    public float spriteTopleftU;
    public float spriteTopleftV;
    public float spriteBotrightU;
    public float spriteBotrightV;
    public SpriteTransform spriteTransform;
    public float alpha;
    public float scaleX;
    public float scaleY;
    public float rotation;
    public Pivot pivot;
    public List<Transition> transition;

    public Sprite() {
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the resource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResource() {
        return resource;
    }

    /**
     * Sets the value of the resource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResource(String value) {
        this.resource = value;
    }

    /**
     * Gets the value of the spriteTopleftU property.
     * 
     */
    public float getSpriteTopleftU() {
        return spriteTopleftU;
    }

    /**
     * Sets the value of the spriteTopleftU property.
     * 
     */
    public void setSpriteTopleftU(float value) {
        this.spriteTopleftU = value;
    }

    /**
     * Gets the value of the spriteTopleftV property.
     * 
     */
    public float getSpriteTopleftV() {
        return spriteTopleftV;
    }

    /**
     * Sets the value of the spriteTopleftV property.
     * 
     */
    public void setSpriteTopleftV(float value) {
        this.spriteTopleftV = value;
    }

    /**
     * Gets the value of the spriteBotrightU property.
     * 
     */
    public float getSpriteBotrightU() {
        return spriteBotrightU;
    }

    /**
     * Sets the value of the spriteBotrightU property.
     * 
     */
    public void setSpriteBotrightU(float value) {
        this.spriteBotrightU = value;
    }

    /**
     * Gets the value of the spriteBotrightV property.
     * 
     */
    public float getSpriteBotrightV() {
        return spriteBotrightV;
    }

    /**
     * Sets the value of the spriteBotrightV property.
     * 
     */
    public void setSpriteBotrightV(float value) {
        this.spriteBotrightV = value;
    }

    /**
     * Gets the value of the spriteTransform property.
     * 
     * @return
     *     possible object is
     *     {@link SpriteTransform }
     *     
     */
    public SpriteTransform getSpriteTransform() {
        return spriteTransform;
    }

    /**
     * Sets the value of the spriteTransform property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpriteTransform }
     *     
     */
    public void setSpriteTransform(SpriteTransform value) {
        this.spriteTransform = value;
    }

    /**
     * Gets the value of the alpha property.
     * 
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Sets the value of the alpha property.
     * 
     */
    public void setAlpha(float value) {
        this.alpha = value;
    }

    /**
     * Gets the value of the scaleX property.
     * 
     */
    public float getScaleX() {
        return scaleX;
    }

    /**
     * Sets the value of the scaleX property.
     * 
     */
    public void setScaleX(float value) {
        this.scaleX = value;
    }

    /**
     * Gets the value of the scaleY property.
     * 
     */
    public float getScaleY() {
        return scaleY;
    }

    /**
     * Sets the value of the scaleY property.
     * 
     */
    public void setScaleY(float value) {
        this.scaleY = value;
    }

    /**
     * Gets the value of the rotation property.
     * 
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the value of the rotation property.
     * 
     */
    public void setRotation(float value) {
        this.rotation = value;
    }

    /**
     * Gets the value of the pivot property.
     * 
     * @return
     *     possible object is
     *     {@link Pivot }
     *     
     */
    public Pivot getPivot() {
        return pivot;
    }

    /**
     * Sets the value of the pivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pivot }
     *     
     */
    public void setPivot(Pivot value) {
        this.pivot = value;
    }

    /**
     * Gets the value of the transition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Transition }
     * 
     * 
     */
    public List<Transition> getTransition() {
        if (transition == null) {
            transition = new ArrayList<Transition>();
        }
        return this.transition;
    }

}