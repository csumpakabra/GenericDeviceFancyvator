
package com.meandmyphone.genericdevicefancyvator.json.pojo;


import java.util.ArrayList;
import java.util.List;

public class Scene {

    private List<Sprite> sprite;

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
    public List<com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite> getSprite() {
        if (sprite == null) {
            sprite = new ArrayList<>();
        }
        return this.sprite;
    }

    public void setSprite(List<Sprite> sprite) {
        this.sprite = sprite;
    }
}
