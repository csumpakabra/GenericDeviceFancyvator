package com.meandmyphone.genericdevicefancyvator.core.transitions.misc;

/**
 * Created by csumpakadabra on 2017.10.22..
 */

public interface TransitionCallback {
    void transitionStarted();
    void transitionCycleStarted();
    void transitionCycleFinished();
    void transitionFinished();
}
