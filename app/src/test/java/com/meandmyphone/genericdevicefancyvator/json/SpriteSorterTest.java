package com.meandmyphone.genericdevicefancyvator.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Position;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Scene;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SpriteRelativePosition;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class SpriteSorterTest {

    private List<Sprite> spriteList;
    
    @Before
    public void setUp() {
        RuntimeTypeAdapterFactory<Position> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Position.class, "positionType")
                .registerSubtype(SpriteRelativePosition.class, "SPRITE_RELATIVE")
                .registerSubtype(SceneRelativePosition.class, "SCENE_RELATIVE");

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("test_scene1.json")));
        Scene scene = gson.fromJson(reader, Scene.class);

        spriteList = scene.getSprite();
    }
    
    @Test
    public void sortByDependents() {
        SpriteSorter spriteSorter = new SpriteSorter(spriteList);
        List<Sprite> sorted = spriteSorter.sortByDependents();

        Assert.assertEquals("sprite1", sorted.get(0).id);
        Assert.assertEquals("sprite2", sorted.get(1).id);
        Assert.assertEquals("sprite3", sorted.get(2).id);
        Assert.assertEquals("sprite4", sorted.get(3).id);
        Assert.assertEquals("sprite5", sorted.get(4).id);
        Assert.assertEquals("sprite6", sorted.get(5).id);
    }
}