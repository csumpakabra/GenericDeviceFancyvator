package com.meandmyphone.genericdevicefancyvator.common;

import android.content.Context;
import android.util.SparseArray;

import com.meandmyphone.genericdevicefancyvator.core.LWPTheme;
import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.Scene;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.core.primitives.FullScreenRectangle;
import com.meandmyphone.genericdevicefancyvator.core.transitions.FadeTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.FlipBookAnimation;
import com.meandmyphone.genericdevicefancyvator.core.transitions.RotateTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.ScaleTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.TranslateTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.ITransition;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Background;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.DiffuseBackground;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Ease;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.FlipbookTransition;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Measure;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Pivot;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Sprite;

import java.util.HashMap;
import java.util.Map;

public class GDFTransformer implements Transformer {

    private final GLRenderer renderer;
    private final Context context;
    private final com.meandmyphone.genericdevicefancyvator.xml.pojo.Scene xmlScene;
    private final Scene scene;
    private final SpriteFactory spriteFactory;
    private SparseArray<String> xmlIdBySpriteId = new SparseArray<>();
    private Map<String, Integer> spriteIdByXmlId = new HashMap<>();

    public GDFTransformer(Context context, GLRenderer renderer, com.meandmyphone.genericdevicefancyvator.xml.pojo.Scene xmlScene, Scene scene) {
        this.renderer = renderer;
        this.context = context;
        this.xmlScene = xmlScene;
        this.scene = scene;
        this.spriteFactory = new SpriteFactory(scene);
        init();
    }

    private void init() {
        for (com.meandmyphone.genericdevicefancyvator.xml.pojo.Sprite sprite : xmlScene.getSprite()) {
            xmlIdBySpriteId.put(SpriteFactory.Sprite.SPRITE_COUNTER, sprite.getId());
            spriteIdByXmlId.put(sprite.getId(), SpriteFactory.Sprite.SPRITE_COUNTER++);
        }
    }

    @Override
    public LWPTheme transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.Scene xmlScene) {
        return null;
    }

    @Override
    public FadeTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.FadeTransition xmlTransition) {
        return new FadeTransition(renderer, xmlTransition.getDuration(), spriteId, transform(xmlTransition.getEase()), (float) xmlTransition.getFromAlpha(), (float) xmlTransition.getToAlpha());
    }

    @Override
    public TranslateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.TranslateTransition xmlTransition) {
        return null;
    }

    @Override
    public RotateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.RotateTransition xmlTransition) {
        return null;
    }

    @Override
    public ScaleTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.xml.pojo.ScaleTransition xmlTransition) {
        return null;
    }

    @Override
    public FlipBookAnimation transform(int spriteId, FlipbookTransition xmlTransition) {
        return null;
    }

    @Override
    public SpriteFactory.Sprite transform(Sprite xmlSprite) {
        int resourceId = context.getResources().getIdentifier(xmlSprite.getResource(),
                "drawable", context.getPackageName());
        int SpriteID = spriteIdByXmlId.get(xmlSprite.getId());
        if (xmlSprite.getSpriteTransform().getPosition().getSceneRelativePosition() != null) {
            SceneRelativePosition sceneRelativePosition = xmlSprite.getSpriteTransform().getPosition().getSceneRelativePosition();
            Pivot pivot = sceneRelativePosition.getScenePoint();
            Measure distance = sceneRelativePosition.getDistanceFromPivot();
        }


        return null;
    }

    @Override
    public SpriteFactory.Sprite transform(Background xmlImageBackground) {
        int resourceId = context.getResources().getIdentifier(
                xmlImageBackground.getBackgroundSource().getImageBackground().getResource(),
                "drawable", context.getPackageName());
        return null;
    }

    @Override
    public FullScreenRectangle transform(DiffuseBackground xmlDiffuseBackground) {
        return null;
    }

    @Override
    public int transform(Ease xmlEase) {
        switch (xmlEase) {
            case LINEAR: return ITransition.LINEAR;
            case CUBIC_IN: return ITransition.CUBICEASEIN;
            case CUBIC_OUT: return ITransition.CUBICEASEOUT;
            case CUBIC_INOUT: return ITransition.CUBICEASEINOUT;
            case QUAD_IN: return ITransition.QUADRATICEASEIN;
            case QUAD_OUT: return ITransition.QUADRATICEASEINOUT;
            case QUAD_INOUT: return ITransition.QUADRATICEASEINOUT;
            case SINE_IN: return ITransition.SINEASEIN;
            case SINE_OUT: return ITransition.SINEASEOUT;
            case SINE_INOUT: return ITransition.SINEASEINOUT;
        }
        throw new IllegalArgumentException("Unable to transform xmlEase: " + xmlEase);
    }
}
