package com.meandmyphone.genericdevicefancyvator.transitions;

import com.meandmyphone.genericdevicefancyvator.core.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.transitions.misc.ITransition;
import com.meandmyphone.genericdevicefancyvator.transitions.misc.Transition;

/**
 * Created by csumpakadabra on 2017.10.27..
 */

public class FlipBookAnimation extends Transition{

    private int [] resources;
    private int delay, currentIndex = 0;
    private long lastFlipTime;

    public FlipBookAnimation(GLRenderer renderer, int nodeId, int[] resources, int delay) {
        super(renderer, resources.length * delay, nodeId, ITransition.LINEAR);
        this.resources = resources;
        this.delay = delay;
    }

    @Override
    public void transit() {
        if (System.currentTimeMillis() - lastFlipTime >= delay) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);

            sprite.setResourceId(resources[currentIndex]);
            nextIndex();
            lastFlipTime = System.currentTimeMillis();
        }
    }

    private void nextIndex() {
        currentIndex += direction;
        if (currentIndex > resources.length - 1) {
            currentIndex = autoreverse ? resources.length - 2 : 0;
            if (autoreverse) direction = -1;
        }

        if (currentIndex<0) {
            currentIndex = 1;
            direction=1;
        }
    }
}
