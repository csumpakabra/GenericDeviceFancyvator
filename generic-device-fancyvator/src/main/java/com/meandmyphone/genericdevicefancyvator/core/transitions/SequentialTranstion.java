package com.meandmyphone.genericdevicefancyvator.core.transitions;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;

public class SequentialTranstion extends BaseTransition {

    private BaseTransition[] transitions;
    private int currentIndex = 0;
    private int totalDuration, currentDuration;

    public SequentialTranstion(GLRenderer renderer, int nodeId, int cycleCount, boolean autoreverse, BaseTransition[] transitions) {
        super(renderer, nodeId, cycleCount, autoreverse);
        this.transitions = transitions;
        for (BaseTransition transition : transitions) {
            totalDuration += transition.getCycleDuration() * transition.getCycleCount();
        }
        currentDuration = transitions[0].getCycleDuration() * transitions[0].getCycleCount();
    }

    @Override
    protected void doTransit() {
        if (System.currentTimeMillis() - cycleStartTime >= currentDuration) {
            nextIndex();
            currentDuration = transitions[currentIndex].getCycleDuration() * transitions[currentIndex].getCycleCount();
        }
        transitions[currentIndex].doTransit();
    }

    private void nextIndex() {
        currentIndex += direction;
        if (currentIndex > transitions.length - 1) {
            currentIndex = autoreverse ? transitions.length - 2 : 0;
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
        direction *= -1;
    }

    @Override
    protected int getCycleDuration() {
        return totalDuration;
    }
}
