package com.meandmyphone.genericdevicefancyvator.json.pojo.transition;


import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;

import java.util.ArrayList;
import java.util.List;

public class FlipbookTransition
    extends Transition
{

    private List<Frame> frames;

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
     *    getSprites().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sprite }
     * 
     * 
     */
    public List<Frame> getSprites() {
        if (frames == null) {
            frames = new ArrayList<Frame>();
        }
        return this.frames;
    }

}
