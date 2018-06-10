package com.meandmyphone.genericdevicefancyvator.xml;

import com.meandmyphone.genericdevicefancyvator.json.SpriteSorter;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Measure;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Pivot;
import com.meandmyphone.genericdevicefancyvator.json.pojo.PositionType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.RelativityType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SpriteRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SpriteTransform;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SpriteSorterTest {


    List<Sprite> spriteList;
    
    @Before
    public void setUp() {
        spriteList = new ArrayList<>();
        
        Sprite sprite1 = new Sprite();
        SpriteTransform sprite1Transform = new SpriteTransform();
        Measure sprite1Width = new Measure();
        Measure sprite1Height = new Measure();
        Measure sprite1DistanceFromTargetX = new Measure();
        Measure sprite1DistanceFromTargetY = new Measure();
        SceneRelativePosition sprite1Position = new SceneRelativePosition();
        sprite1DistanceFromTargetX.setRelativity(RelativityType.SCENE);
        sprite1DistanceFromTargetY.setRelativity(RelativityType.SCENE);
        sprite1DistanceFromTargetX.setValue(0.0f);
        sprite1DistanceFromTargetY.setValue(0.0f);
        sprite1Position.setPositionType(PositionType.SCENE_RELATIVE);
        sprite1Position.setRelativePointOfTarget(Pivot.TOP_LEFT);
        sprite1Position.setxDistanceFromTarget(sprite1DistanceFromTargetX);
        sprite1Position.setyDistanceFromTarget(sprite1DistanceFromTargetY);
        sprite1Width.setRelativity(RelativityType.SCENE);
        sprite1Width.setValue(0.5f);
        sprite1Height.setRelativity(RelativityType.SCENE);
        sprite1Height.setValue(0.5f);
        sprite1Transform.setPivot(Pivot.TOP_LEFT);
        sprite1Transform.setWidth(sprite1Width);
        sprite1Transform.setHeight(sprite1Height);
        sprite1Transform.setPosition(sprite1Position);
        sprite1.setAlpha(0.0f);
        sprite1.setId("sprite1");
        sprite1.setPivot(Pivot.TOP_LEFT);
        sprite1.setRotation(0.0f);
        sprite1.setScaleX(1.0f);
        sprite1.setScaleY(1.0f);
        sprite1.setSpriteTopleftU(0.0f);
        sprite1.setSpriteTopleftU(1.0f);
        sprite1.setSpriteBotrightU(1.0f);
        sprite1.setSpriteBotrightV(1.0f);
        sprite1.setResource("resource1");
        sprite1.setSpriteTransform(sprite1Transform);


        Sprite sprite2 = new Sprite();
        SpriteTransform sprite2Transform = new SpriteTransform();
        Measure sprite2Width = new Measure();
        Measure sprite2Height = new Measure();
        Measure sprite2DistanceFromTargetX = new Measure();
        Measure sprite2DistanceFromTargetY = new Measure();
        SpriteRelativePosition sprite2Position = new SpriteRelativePosition();
        sprite2DistanceFromTargetX.setRelativity(RelativityType.SPRITE);
        sprite2DistanceFromTargetY.setRelativity(RelativityType.SPRITE);
        sprite2DistanceFromTargetX.setValue(0.0f);
        sprite2DistanceFromTargetY.setValue(0.0f);
        sprite2DistanceFromTargetX.setRelativeTo("sprite1");
        sprite2DistanceFromTargetY.setRelativeTo("sprite1");
        sprite2Position.setPositionType(PositionType.SPRITE_RELATIVE);
        sprite2Position.setRelativePointOfTarget(Pivot.BOT_RIGHT);
        sprite2Position.setRelativeSpriteId("sprite1");
        sprite2Position.setxDistanceFromTarget(sprite2DistanceFromTargetX);
        sprite2Position.setyDistanceFromTarget(sprite2DistanceFromTargetY);
        sprite2Width.setRelativity(RelativityType.SPRITE);
        sprite2Width.setValue(0.5f);
        sprite2Height.setRelativity(RelativityType.SPRITE);
        sprite2Width.setRelativeTo("sprite1");
        sprite2Height.setRelativeTo("sprite1");
        sprite2Height.setValue(0.5f);
        sprite2Transform.setPivot(Pivot.TOP_LEFT);
        sprite2Transform.setWidth(sprite2Width);
        sprite2Transform.setHeight(sprite2Height);
        sprite2Transform.setPosition(sprite2Position);
        sprite2.setAlpha(0.0f);
        sprite2.setId("sprite2");
        sprite2.setPivot(Pivot.TOP_LEFT);
        sprite2.setRotation(0.0f);
        sprite2.setScaleX(1.0f);
        sprite2.setScaleY(1.0f);
        sprite2.setSpriteTopleftU(0.0f);
        sprite2.setSpriteTopleftU(1.0f);
        sprite2.setSpriteBotrightU(1.0f);
        sprite2.setSpriteBotrightV(1.0f);
        sprite2.setResource("resource1");
        sprite2.setSpriteTransform(sprite2Transform);


        Sprite sprite3 = new Sprite();
        SpriteTransform sprite3Transform = new SpriteTransform();
        Measure sprite3Width = new Measure();
        Measure sprite3Height = new Measure();
        Measure sprite3DistanceFromTargetX = new Measure();
        Measure sprite3DistanceFromTargetY = new Measure();
        SpriteRelativePosition sprite3Position = new SpriteRelativePosition();
        sprite3DistanceFromTargetX.setRelativity(RelativityType.SPRITE);
        sprite3DistanceFromTargetY.setRelativity(RelativityType.SPRITE);
        sprite3DistanceFromTargetX.setValue(0.0f);
        sprite3DistanceFromTargetY.setValue(0.0f);
        sprite3DistanceFromTargetX.setRelativeTo("sprite1");
        sprite3DistanceFromTargetY.setRelativeTo("sprite1");
        sprite3Position.setPositionType(PositionType.SPRITE_RELATIVE);
        sprite3Position.setRelativePointOfTarget(Pivot.BOT_RIGHT);
        sprite3Position.setRelativeSpriteId("sprite1");
        sprite3Position.setxDistanceFromTarget(sprite3DistanceFromTargetX);
        sprite3Position.setyDistanceFromTarget(sprite3DistanceFromTargetY);
        sprite3Width.setRelativity(RelativityType.SPRITE);
        sprite3Width.setValue(0.5f);
        sprite3Height.setRelativity(RelativityType.SPRITE);
        sprite3Height.setValue(0.5f);
        sprite3Width.setRelativeTo("sprite2");
        sprite3Height.setRelativeTo("sprite2");
        sprite3Transform.setPivot(Pivot.TOP_LEFT);
        sprite3Transform.setWidth(sprite3Width);
        sprite3Transform.setHeight(sprite3Height);
        sprite3Transform.setPosition(sprite3Position);
        sprite3.setAlpha(0.0f);
        sprite3.setId("sprite3");
        sprite3.setPivot(Pivot.TOP_LEFT);
        sprite3.setRotation(0.0f);
        sprite3.setScaleX(1.0f);
        sprite3.setScaleY(1.0f);
        sprite3.setSpriteTopleftU(0.0f);
        sprite3.setSpriteTopleftU(1.0f);
        sprite3.setSpriteBotrightU(1.0f);
        sprite3.setSpriteBotrightV(1.0f);
        sprite3.setResource("resource1");
        sprite3.setSpriteTransform(sprite3Transform);

        Sprite sprite4 = new Sprite();
        SpriteTransform sprite4Transform = new SpriteTransform();
        Measure sprite4Width = new Measure();
        Measure sprite4Height = new Measure();
        Measure sprite4DistanceFromTargetX = new Measure();
        Measure sprite4DistanceFromTargetY = new Measure();
        SpriteRelativePosition sprite4Position = new SpriteRelativePosition();
        sprite4DistanceFromTargetX.setRelativity(RelativityType.SPRITE);
        sprite4DistanceFromTargetY.setRelativity(RelativityType.SPRITE);
        sprite4DistanceFromTargetX.setValue(0.0f);
        sprite4DistanceFromTargetY.setValue(0.0f);
        sprite4DistanceFromTargetX.setRelativeTo("sprite3");
        sprite4DistanceFromTargetY.setRelativeTo("sprite2");
        sprite4Position.setPositionType(PositionType.SPRITE_RELATIVE);
        sprite4Position.setRelativePointOfTarget(Pivot.BOT_RIGHT);
        sprite4Position.setRelativeSpriteId("sprite1");
        sprite4Position.setxDistanceFromTarget(sprite4DistanceFromTargetX);
        sprite4Position.setyDistanceFromTarget(sprite4DistanceFromTargetY);
        sprite4Width.setRelativity(RelativityType.SPRITE);
        sprite4Width.setValue(0.5f);
        sprite4Height.setRelativity(RelativityType.SPRITE);
        sprite4Height.setValue(0.5f);
        sprite4Width.setRelativeTo("sprite2");
        sprite4Height.setRelativeTo("sprite2");
        sprite4Transform.setPivot(Pivot.TOP_LEFT);
        sprite4Transform.setWidth(sprite4Width);
        sprite4Transform.setHeight(sprite4Height);
        sprite4Transform.setPosition(sprite4Position);
        sprite4.setAlpha(0.0f);
        sprite4.setId("sprite4");
        sprite4.setPivot(Pivot.TOP_LEFT);
        sprite4.setRotation(0.0f);
        sprite4.setScaleX(1.0f);
        sprite4.setScaleY(1.0f);
        sprite4.setSpriteTopleftU(0.0f);
        sprite4.setSpriteTopleftU(1.0f);
        sprite4.setSpriteBotrightU(1.0f);
        sprite4.setSpriteBotrightV(1.0f);
        sprite4.setResource("resource1");
        sprite4.setSpriteTransform(sprite4Transform);

        Sprite sprite5 = new Sprite();
        SpriteTransform sprite5Transform = new SpriteTransform();
        Measure sprite5Width = new Measure();
        Measure sprite5Height = new Measure();
        Measure sprite5DistanceFromTargetX = new Measure();
        Measure sprite5DistanceFromTargetY = new Measure();
        SpriteRelativePosition sprite5Position = new SpriteRelativePosition();
        sprite5DistanceFromTargetX.setRelativity(RelativityType.SPRITE);
        sprite5DistanceFromTargetY.setRelativity(RelativityType.SPRITE);
        sprite5DistanceFromTargetX.setValue(0.0f);
        sprite5DistanceFromTargetY.setValue(0.0f);
        sprite5DistanceFromTargetX.setRelativeTo("sprite4");
        sprite5DistanceFromTargetY.setRelativeTo("sprite4");
        sprite5Position.setPositionType(PositionType.SPRITE_RELATIVE);
        sprite5Position.setRelativePointOfTarget(Pivot.BOT_RIGHT);
        sprite5Position.setRelativeSpriteId("sprite4");
        sprite5Position.setxDistanceFromTarget(sprite5DistanceFromTargetX);
        sprite5Position.setyDistanceFromTarget(sprite5DistanceFromTargetY);
        sprite5Width.setRelativity(RelativityType.SPRITE);
        sprite5Width.setValue(0.5f);
        sprite5Height.setRelativity(RelativityType.SPRITE);
        sprite5Height.setValue(0.5f);
        sprite5Width.setRelativeTo("sprite4");
        sprite5Height.setRelativeTo("sprite4");
        sprite5Transform.setPivot(Pivot.TOP_LEFT);
        sprite5Transform.setWidth(sprite5Width);
        sprite5Transform.setHeight(sprite5Height);
        sprite5Transform.setPosition(sprite5Position);
        sprite5.setAlpha(0.0f);
        sprite5.setId("sprite5");
        sprite5.setPivot(Pivot.TOP_LEFT);
        sprite5.setRotation(0.0f);
        sprite5.setScaleX(1.0f);
        sprite5.setScaleY(1.0f);
        sprite5.setSpriteTopleftU(0.0f);
        sprite5.setSpriteTopleftU(1.0f);
        sprite5.setSpriteBotrightU(1.0f);
        sprite5.setSpriteBotrightV(1.0f);
        sprite5.setResource("resource1");
        sprite5.setSpriteTransform(sprite5Transform);

        spriteList.add(sprite5);
        spriteList.add(sprite2);
        spriteList.add(sprite3);
        spriteList.add(sprite4);
        spriteList.add(sprite1);
        
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
    }
}