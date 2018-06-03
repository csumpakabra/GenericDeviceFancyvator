package com.meandmyphone.genericdevicefancyvator.core;

import android.content.Context;

import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.Scene;

import java.util.Map;
import java.util.Set;

public class ThemeBuilder {

    private final GLRenderer renderer;
    private final Context context;

    public ThemeBuilder(Context context, GLRenderer renderer) {
        this.renderer = renderer;
        this.context = context;
    }

    static ThemeBuilder newBuilder(Context context, GLRenderer renderer) {
        return new ThemeBuilder(context, renderer);
    }

    public LWPTheme build() {
        return new LWPTheme() {
            @Override
            public int[] getResources() {
                return new int[0];
            }

            @Override
            public void fillScene(Scene scene) {

            }

            @Override
            public void onSceneTouched(float sceneX, float sceneY) {

            }

            @Override
            public Map<String, Integer> getPreferences() {
                return null;
            }

            @Override
            public Set<String> getPreferenceKeys() {
                return null;
            }

            @Override
            public void onOffsetChanged(float offsetDelta) {

            }

            @Override
            public void init(int speed, int ghostanim) {

            }

            @Override
            public void specialAnimation() {

            }
        };
    }
}
