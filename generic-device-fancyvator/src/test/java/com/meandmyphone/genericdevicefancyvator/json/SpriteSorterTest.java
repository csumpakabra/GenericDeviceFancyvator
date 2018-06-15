package com.meandmyphone.genericdevicefancyvator.json;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SpriteSorterTest {

    private List<com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite> spriteList;
    
    @Before
    public void setUp() {

        Parser parser = new Parser();

        com.meandmyphone.genericdevicefancyvator.json.pojo.Scene scene = parser.parse(getClass().getResourceAsStream("test_scene1.json"));

        spriteList = scene.getSprite();
    }
    
    @Test
    public void sortByDependents() {
        SpriteSorter spriteSorter = new SpriteSorter(spriteList);
        List<com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite> sorted = spriteSorter.sortByDependents();

        Assert.assertEquals("sprite1", sorted.get(0).getId());
        Assert.assertEquals("sprite2", sorted.get(1).getId());
        Assert.assertEquals("sprite3", sorted.get(2).getId());
        Assert.assertEquals("sprite4", sorted.get(3).getId());
        Assert.assertEquals("sprite5", sorted.get(4).getId());
        Assert.assertEquals("sprite6", sorted.get(5).getId());
    }
}