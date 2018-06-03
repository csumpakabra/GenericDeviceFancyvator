package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.TransitionCallback;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public abstract class Transition implements ITransition {

    private static int counter = 0;

    protected long startTime, cycleStartTime;
    protected int cycleDuration, cyclesDone, cycleCount = Integer.MAX_VALUE;
    protected final int nodeId;
    protected final int transitionId = ++counter;
    protected short direction = 1;
    protected boolean autoreverse = true;
    protected boolean playing = false;
    protected int easeType;
    private TransitionCallback onTransitionFinished = null;
    protected GLRenderer renderer;

    public Transition(GLRenderer renderer, int cycleDuration, int nodeId, int easeType) {
        this.cycleDuration = (int)calculateSeed(cycleDuration);
        this.nodeId = nodeId;
        this.easeType = easeType;
        this.renderer = renderer;
        renderer.getCurrentScene().getSprite(nodeId).transitions.put(transitionId, this);
    }

    public void start() {

        startTime = System.currentTimeMillis();
        cycleStartTime = startTime;
        playing = true;
    }

    // EH :S
    private long calculateSeed(long speedOnNormal) {
        // TODO calculatespeed!!!!!!
        return calculateSpeed(speedOnNormal, 1);
    }

    private long calculateSpeed(long speedOnNormal, int currentSpeed) {
        switch (currentSpeed) {
//            case Halloween.SPEED_SLOW: return speedOnNormal/2;
//            case Halloween.SPEED_SLOWER: return 3*speedOnNormal/4;
//            case Halloween.SPEED_NORMAL: return speedOnNormal;
//            case Halloween.SPEED_FASTER: return 5*speedOnNormal/4;
//            case Halloween.SPEED_FASTEST: return 3*speedOnNormal/2;
            default: return speedOnNormal;
        }
    }

    protected final void transitionFinished() {
        ++cyclesDone;
        cycleStartTime = System.currentTimeMillis();
        if (onTransitionFinished != null) onTransitionFinished.handleEvent();
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

    public void setOnTransitionFinished(TransitionCallback onTransitionFinished) {
        this.onTransitionFinished = onTransitionFinished;
    }
}
