package com.meandmyphone.genericdevicefancyvator.common;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.meandmyphone.genericdevicefancyvator.core.LWPTheme;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor;
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
import com.meandmyphone.genericdevicefancyvator.xml.pojo.Position;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.RelativityType;
import com.meandmyphone.genericdevicefancyvator.xml.pojo.SceneRelativePosition;


import java.util.HashMap;
import java.util.Map;

import static com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory.*;

public class GDFTransformer implements Transformer {

    private final static String TAG = "Transformer";

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
            xmlIdBySpriteId.put(Sprite.SPRITE_COUNTER, sprite.getId());
            spriteIdByXmlId.put(sprite.getId(), Sprite.SPRITE_COUNTER++);
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
    public Sprite transform(com.meandmyphone.genericdevicefancyvator.xml.pojo.Sprite xmlSprite) {
        int resourceId = context.getResources().getIdentifier(xmlSprite.getResource(),
                "drawable", context.getPackageName());
        int SpriteID = spriteIdByXmlId.get(xmlSprite.getId());
        if (xmlSprite.getSpriteTransform().getPosition().getSceneRelativePosition() != null) {
            SceneRelativePosition sceneRelativePosition = xmlSprite.getSpriteTransform().getPosition().getSceneRelativePosition();
            float realWidth = getRealWidth(xmlSprite.getSpriteTransform().getWidth());
            float realHeight = getRealHeight(xmlSprite.getSpriteTransform().getHeight());
            Point2D getSpriteTopLeft = getRealPoint(xmlSprite.getSpriteTransform().getPosition());



//            float realWidth =


        }


        return null;
    }

    @Override
    public Sprite transform(Background xmlImageBackground) {
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

    private float getRealWidth(Measure measure) {
        if (RelativityType.NONE.equals(measure.getRelativity())) {
            Log.w(TAG, "Creating absolute widht sprite!");
            return measure.getValue();
        } else if (RelativityType.SCENE.equals(measure.getRelativity())) {
            return measure.getValue() * scene.getSceneWidth();
        } else if (RelativityType.SPRITE.equals(measure.getRelativity())) {
            int relativeToSpriteId = spriteIdByXmlId.get(measure.getRelativeTo());
            Sprite relativeToSprite = scene.getSprite(relativeToSpriteId);
            return relativeToSprite.width * measure.getValue();
        }
        throw new IllegalArgumentException("Invalid measure: " + measure.getRelativity());
    }

    private float getRealHeight(Measure measure) {
        if (RelativityType.NONE.equals(measure.getRelativity())) {
            Log.w(TAG, "Creating absolute height sprite!");
            return measure.getValue();
        } else if (RelativityType.SCENE.equals(measure.getRelativity())) {
            return measure.getValue() * scene.getSceneHeight();
        } else if (RelativityType.SPRITE.equals(measure.getRelativity())) {
            int relativeToSpriteId = spriteIdByXmlId.get(measure.getRelativeTo());
            Sprite relativeToSprite = scene.getSprite(relativeToSpriteId);
            return relativeToSprite.height * measure.getValue();
        }
        throw new IllegalArgumentException("Invalid measure: " + measure.getRelativity());
    }

    private Point2D getRealPoint(Position position) {
        if (position.getSceneRelativePosition() != null) {
            float distanceX = getRealWidth(position.getSceneRelativePosition().getXDistanceFromPivot());
            float distanceY = getRealHeight(position.getSceneRelativePosition().getYDistanceFromPivot());
            float x = scene.getScenePointOfInterest(Anchor.fromPivot(position.getSceneRelativePosition().getScenePoint())).X + distanceX;
            float y = scene.getScenePointOfInterest(Anchor.fromPivot(position.getSceneRelativePosition().getScenePoint())).Y - distanceY;
            return new Point2D(x, y);
        } else if (position.getSpriteRelativePosition() != null) {
            float distanceX = getRealWidth(position.getSpriteRelativePosition().getXDistanceFromSprite());
            float distanceY = getRealHeight(position.getSpriteRelativePosition().getYDistanceFromSprite());
            int relativeToSpirteId = spriteIdByXmlId.get(position.getSpriteRelativePosition().getRelativeSpriteId());
            Sprite relativeToSprite = scene.getSprite(relativeToSpirteId);
            Point2D relativePoint = relativeToSprite.getPointOfInterest(Anchor.fromPivot(position.getSpriteRelativePosition().getRelativeSpritePoint()));
            float x = relativePoint.X + distanceX;
            float y = relativePoint.Y - distanceY;
            return new Point2D(x, y);
        }
        throw new IllegalArgumentException("Invalid position: " + position.toString());
    }
}
