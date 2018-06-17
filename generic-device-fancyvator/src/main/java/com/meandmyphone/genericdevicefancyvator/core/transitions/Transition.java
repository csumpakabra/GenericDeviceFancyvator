package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.Scene;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.TransitionCallback;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public abstract class Transition implements ITransition, TransitionCallback {

    private static int counter = 0;

    protected long startTime, cycleStartTime;
    protected int cycleDuration, cyclesDone, cycleCount = Integer.MAX_VALUE;
    protected final int nodeId;
    protected final int transitionId = ++counter;
    protected short direction = 1;
    protected boolean autoreverse = true;
    protected boolean playing = false;
    protected int easeType;
    private int destroyEffect;
    private boolean destroySpriteWhenFinished = false;
    protected GLRenderer renderer;

    public Transition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType, int destroyEffect, boolean destroySpriteWhenFinished, boolean autoreverse) {
        this.cycleDuration = (int)calculateSeed(cycleDuration);
        this.nodeId = nodeId;
        this.easeType = easeType;
        this.renderer = renderer;
        this.destroyEffect = destroyEffect;
        this.destroySpriteWhenFinished = destroySpriteWhenFinished;
        this.autoreverse = autoreverse;
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
            default: return speedOnNormal;
        }
    }

    public void transit() {
        if (!playing) return;
        if (cyclesDone>=cycleCount) {
            transitionFinished();
            return;
        }
        transitionCycleStarted();

    }

    @Override
    public void transitionStarted() {

    }

    @Override
    public void transitionCycleStarted() {

    }

    @Override
    public void transitionCycleFinished() {
        if (cycleCount != ITransition.CYCLE_INDEFINITE) {
            ++cyclesDone;
        }
        cycleStartTime = System.currentTimeMillis();
    }

    @Override
    public final void transitionFinished() {
        if (destroySpriteWhenFinished) {
            switch (this.destroyEffect) {
                case ITransition.DESTORY_EFFECT_NONE:
                    renderer.getCurrentScene().destroySprite(nodeId);
                    break;
                case ITransition.DESTROY_EFFECT_FADE:
                    FadeTransition fadeOut = new FadeTransition(
                            renderer,
                            500,
                            nodeId,
                            ITransition.LINEAR,
                            ITransition.DESTORY_EFFECT_NONE,
                            true,
                            false,
                            renderer.getCurrentScene().getSprite(nodeId).getAlpha(),
                            0.0f);

                    fadeOut.start();
                    break;
            }
        }
    }

    public void destroySpriteWithEffect(GLRenderer renderer, final int id, int effect) {

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
