package com.meandmyphone.genericdevicefancyvator.json;

import android.content.Context;

import com.meandmyphone.genericdevicefancyvator.json.pojo.Scene;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;

import java.util.HashMap;
import java.util.Map;

public class ResourceExtractor {

    private final Scene xmlScene;
    private final Context context;

    public ResourceExtractor(Scene xmlScene, Context context) {
        this.xmlScene = xmlScene;
        this.context = context;
    }

    public Integer[] extractResources() {
        Map<String, Integer> resources = new HashMap<>();
        for (Sprite sprite : xmlScene.getSprite()) {
            if (!resources.containsKey(sprite.resource)) {
                int resourceId = context.getResources().getIdentifier(sprite.getResource(), "drawable", context.getPackageName());
                resources.put(sprite.resource, resourceId);
            }
        }
        return resources.values().toArray(new Integer[resources.size()]);
    }

}
