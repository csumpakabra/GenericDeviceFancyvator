package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;

/**
 * Created by csumpakadabra on 2017.10.27..
 */

public class FlipBookAnimation extends Transition {

    private int[] resources;
    private float[] top_left_u, top_left_v, bot_right_u, bot_right_v;
    private int delay, currentIndex = 0;
    private long lastFlipTime;

    public FlipBookAnimation(GLRenderer renderer, int nodeId, int cycleCount, int destroyEffect, boolean destroyOnFinished, boolean autoreverse, int[] resources, float[] top_left_u, float[] top_left_v, float[] bot_right_u, float[] bot_right_v, int delay) {
        super(renderer, resources.length * delay, nodeId, cycleCount, ITransition.LINEAR, destroyEffect, destroyOnFinished, autoreverse);
        this.resources = resources;
        this.delay = delay;
        this.top_left_u = top_left_u;
        this.top_left_v = top_left_v;
        this.bot_right_u = bot_right_u;
        this.bot_right_v = bot_right_v;
    }

    @Override
    public void doTransit() {
        if (System.currentTimeMillis() - lastFlipTime >= delay) {
            SpriteFactory.Sprite sprite = renderer.getCurrentScene().getSprite(nodeId);

            sprite.refreshUV(
                    resources[currentIndex],
                    top_left_u[currentIndex],
                    top_left_v[currentIndex],
                    bot_right_u[currentIndex],
                    bot_right_v[currentIndex]
            );

            nextIndex();
            lastFlipTime = System.currentTimeMillis();
        }
    }

    private void nextIndex() {
        currentIndex += direction;
        if (currentIndex > resources.length - 1) {
            currentIndex = autoreverse ? resources.length - 2 : 0;
            transitionCycleFinished();

        }

        if (currentIndex < 0) {
            currentIndex = 1;
            direction = 1;
            transitionCycleFinished();
        }
    }

    @Override
    protected void changeDirectionAndReset() {
        direction = -1;
    }
}
