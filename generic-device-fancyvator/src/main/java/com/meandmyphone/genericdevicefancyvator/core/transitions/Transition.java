package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

import java.util.Locale;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public abstract class Transition extends BaseTransition {

    protected short direction = 1;
    protected int easeType;
    private int cycleDuration;
    private int destroyEffect;

    public Transition(GLRenderer renderer, int cycleDuration, int nodeId, int cycleCount, int easeType, int destroyEffect, boolean autoreverse) {
        super(renderer, nodeId, cycleCount, autoreverse);
        this.cycleDuration = cycleDuration;
        this.easeType = easeType;
        this.destroyEffect = destroyEffect;
    }

    protected abstract void doTransit();

    protected abstract void changeDirectionAndReset();

    @Override
    public final void transitionFinished() {
        Logger.Log(TAG, String.format(Locale.ENGLISH, "Transition: %d, %s finished", transitionId, getClass().getSimpleName()));
        super.transitionFinished();

        if (ITransition.DESTROY_EFFECT_DONT_DESTROY != destroyEffect) {
            switch (this.destroyEffect) {
                case ITransition.DESTROY_EFFECT_NONE:
                    renderer.getCurrentScene().destroySprite(nodeId);
                    break;
                case ITransition.DESTROY_EFFECT_FADE:
                    FadeTransition fadeOut = new FadeTransition(
                            renderer,
                            500,
                            nodeId,
                            1,
                            ITransition.LINEAR,
                            ITransition.DESTROY_EFFECT_NONE,
                            false,
                            renderer.getCurrentScene().getSprite(nodeId).getAlpha(),
                            0.0f);
                    renderer.getCurrentScene().getSprite(nodeId).addTransition(fadeOut);
                    fadeOut.start();
                    break;
            }
        }
    }

    public int getCycleDuration() {
        return cycleDuration;
    }

    public int getEaseType() {
        return easeType;
    }

    public void setEaseType(int easeType) {
        this.easeType = easeType;
    }
}
