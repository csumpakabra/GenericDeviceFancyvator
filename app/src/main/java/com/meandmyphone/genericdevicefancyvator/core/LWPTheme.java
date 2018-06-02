package com.meandmyphone.genericdevicefancyvator.core;

import java.util.Map;
import java.util.Set;

/**
 * Created by csumpakadabra on 2017.10.21..
 */

public interface LWPTheme {
    int [] getResources();
    void fillScene(Scene scene);
    void onSceneTouched(float sceneX, float sceneY);
    Map<String,Integer> getPreferences();
    Set<String> getPreferenceKeys();
    void onOffsetChanged(float offsetDelta);
    void init(int speed, int ghostanim); // TODO !!!
    void specialAnimation();
}
