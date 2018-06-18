package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.transitions.misc.TransitionCallback;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

/**
 * Created by csumpakadabra on 2017.10.18..
 */

public abstract class BaseTransition implements ITransition, TransitionCallback {

    protected final static String TAG = "Transition";
    private static int counter = 0;

    protected long startTime, cycleStartTime;
    protected int cyclesDone, cycleCount;
    protected final int nodeId;
    protected final int transitionId = ++counter;
    protected short direction = 1;
    protected boolean autoreverse;
    protected boolean playing = false;
    protected GLRenderer renderer;

    public BaseTransition(GLRenderer renderer, int nodeId, int cycleCount, boolean autoreverse) {

        this.nodeId = nodeId;
        this.renderer = renderer;
        this.autoreverse = autoreverse;
        this.cycleCount = cycleCount;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        cycleStartTime = startTime;
        playing = true;
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

    protected abstract int getCycleDuration();

    @Override
    public void transitionCycleFinished() {
        Logger.Log(TAG, String.format("Transition: %d, %s | cycle %d finished", transitionId, getClass().getSimpleName(), cyclesDone));

        if (cycleCount != ITransition.CYCLE_INDEFINITE) {
            ++cyclesDone;
        }
        if (autoreverse) {
            changeDirectionAndReset();
        }
        cycleStartTime = System.currentTimeMillis();
    }

    @Override
    public void transitionFinished() {
        Logger.Log(TAG, String.format("Transition: %d, %s finished", transitionId, getClass().getSimpleName()));
        playing = false;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCycleStartTime() {
        return cycleStartTime;
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
