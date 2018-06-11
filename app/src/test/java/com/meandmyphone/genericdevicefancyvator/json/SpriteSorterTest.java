package com.meandmyphone.genericdevicefancyvator.json;

import com.meandmyphone.genericdevicefancyvator.common.Parser;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Scene;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SpriteSorterTest {

    private List<Sprite> spriteList;
    
    @Before
    public void setUp() {

        Parser parser = new Parser();

        Scene scene = parser.parse(getClass().getResourceAsStream("test_scene1.json"));

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