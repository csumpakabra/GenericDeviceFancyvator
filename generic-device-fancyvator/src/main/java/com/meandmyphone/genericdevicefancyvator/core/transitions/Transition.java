package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.TransitionCallback;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public abstract class Transition implements ITransition, TransitionCallback {

    private final static String TAG = "Transition";
    private static int counter = 0;

    protected long startTime, cycleStartTime;
    protected int cycleDuration, cyclesDone, cycleCount;
    protected final int nodeId;
    protected final int transitionId = ++counter;
    protected short direction = 1;
    protected boolean autoreverse = true;
    protected boolean playing = false;
    protected int easeType;
    private int destroyEffect;
    private boolean destroySpriteWhenFinished;
    protected GLRenderer renderer;

    public Transition(GLRenderer renderer, int cycleDuration, int nodeId, int cycleCount, int easeType, int destroyEffect, boolean destroySpriteWhenFinished, boolean autoreverse) {
        this.cycleDuration = (int) calculateSeed(cycleDuration);
        this.nodeId = nodeId;
        this.easeType = easeType;
        this.renderer = renderer;
        this.destroyEffect = destroyEffect;
        this.destroySpriteWhenFinished = destroySpriteWhenFinished;
        this.autoreverse = autoreverse;
        this.cycleCount = cycleCount;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        cycleStartTime = startTime;
        playing = true;
        transitionStarted();
    }

    // EH :S
    private long calculateSeed(long speedOnNormal) {
        // TODO calculatespeed!!!!!!
        return calculateSpeed(speedOnNormal, 1);
    }

    private long calculateSpeed(long speedOnNormal, int currentSpeed) {
        switch (currentSpeed) {
            default:
                return speedOnNormal;
        }
    }

    @Override
    final public void transit() {
        if (!playing) return;
        if (cyclesDone >= cycleCount) {
            transitionFinished();
            return;
        }
        doTransit();
    }

    protected abstract void doTransit();

    protected abstract void changeDirectionAndReset();

    @Override
    public void transitionStarted() {
        Logger.Log(TAG, String.format("Transition: %d, %s started", transitionId, getClass().getSimpleName()));
    }

    @Override
    public void transitionCycleFinished() {
        if (cycleCount != ITransition.CYCLE_INDEFINITE) {
            ++cyclesDone;
        }
        if (autoreverse) {
            changeDirectionAndReset();
        }
        cycleStartTime = System.currentTimeMillis();
    }

    @Override
    public final void transitionFinished() {
        Logger.Log(TAG, String.format("Transition: %d, %s finished", transitionId, getClass().getSimpleName()));
        playing = false;

        if (destroySpriteWhenFinished) {
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
                            true,
                            false,
                            renderer.getCurrentScene().getSprite(nodeId).getAlpha(),
                            0.0f);
                    renderer.getCurrentScene().getSprite(nodeId).addTransition(fadeOut);
                    fadeOut.start();
                    break;
            }
        }
    }

    public int getEaseType() {
        return easeType;
    }

    public void setEaseType(int easeType) {
        this.easeType = easeType;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCycleStartTime() {
        return cycleStartTime;
    }

    public int getCycleDuration() {
        return cycleDuration;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getTransitionId() {
        return transitionId;
    }

    public short getDirection() {
        return direction;
    }

    public void setAutoreverse(boolean autoreverse) {
        this.autoreverse = autoreverse;
    }

    public boolean isAutoreverse() {
        return autoreverse;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }

}
