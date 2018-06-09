package com.meandmyphone.genericdevicefancyvator.common;

import android.content.Context;
import android.util.Log;

import com.meandmyphone.genericdevicefancyvator.core.LWPTheme;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.data.misc.Anchor;
import com.meandmyphone.genericdevicefancyvator.core.gl.GLRenderer;
import com.meandmyphone.genericdevicefancyvator.core.gl.Scene;
import com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory;
import com.meandmyphone.genericdevicefancyvator.core.transitions.FadeTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.FlipBookAnimation;
import com.meandmyphone.genericdevicefancyvator.core.transitions.ITransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.RotateTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.ScaleTransition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.Transition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.TranslateTransition;

import com.meandmyphone.genericdevicefancyvator.json.pojo.Ease;
import com.meandmyphone.genericdevicefancyvator.json.pojo.FlipbookTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Measure;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Position;
import com.meandmyphone.genericdevicefancyvator.json.pojo.RelativityType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SpriteRelativePosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory.*;

public class GDFTransformer implements Transformer {

    private final static String TAG = "Transformer";

    private final GLRenderer renderer;
    private final Context context;
    private final com.meandmyphone.genericdevicefancyvator.json.pojo.Scene xmlScene;
    private final Scene scene;
    private final SpriteFactory spriteFactory;
    private Map<String, Integer> spriteIdByXmlId = new HashMap<>();
    private Map<String, Integer> resourceIdByXmlResourceId = new HashMap<>();

    public GDFTransformer(Context context, GLRenderer renderer, com.meandmyphone.genericdevicefancyvator.json.pojo.Scene xmlScene, Scene scene) {
        this.renderer = renderer;
        this.context = context;
        this.xmlScene = xmlScene;
        this.scene = scene;
        this.spriteFactory = new SpriteFactory(scene);
        for (com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite sprite : xmlScene.getSprite()) {
            spriteIdByXmlId.put(sprite.getId(), Sprite.SPRITE_COUNTER++);
            int resourceId = context.getResources().getIdentifier(sprite.getResource(), "drawable", context.getPackageName());
            resourceIdByXmlResourceId.put(sprite.getResource(), resourceId);
        }
    }

    @Override
    public LWPTheme transform(com.meandmyphone.genericdevicefancyvator.json.pojo.Scene xmlScene) {
        return null;
    }

    private FadeTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.FadeTransition xmlTransition) {
        return new FadeTransition(renderer, xmlTransition.getDuration(), spriteId, transform(xmlTransition.getEase()), (float) xmlTransition.getFromAlpha(), (float) xmlTransition.getToAlpha());
    }

    private TranslateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.TranslateTransition xmlTransition) {
        return null;
    }

    private RotateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.RotateTransition xmlTransition) {
        return null;
    }

    private ScaleTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.ScaleTransition xmlTransition) {
        return null;
    }

    private FlipBookAnimation transform(int spriteId, FlipbookTransition xmlTransition) {
        return null;
    }

    public Transition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.Transition xmlTransition) {
        if (xmlTransition instanceof com.meandmyphone.genericdevicefancyvator.json.pojo.TranslateTransition) {
            return transform(spriteId, (com.meandmyphone.genericdevicefancyvator.json.pojo.TranslateTransition) xmlTransition);
        } else if (xmlTransition instanceof com.meandmyphone.genericdevicefancyvator.json.pojo.RotateTransition) {
            return transform(spriteId, (com.meandmyphone.genericdevicefancyvator.json.pojo.RotateTransition) xmlTransition);
        } else if (xmlTransition instanceof com.meandmyphone.genericdevicefancyvator.json.pojo.ScaleTransition) {
            return transform(spriteId, (com.meandmyphone.genericdevicefancyvator.json.pojo.ScaleTransition) xmlTransition);
        } else if (xmlTransition instanceof com.meandmyphone.genericdevicefancyvator.json.pojo.FadeTransition) {
            return transform(spriteId, (com.meandmyphone.genericdevicefancyvator.json.pojo.FadeTransition) xmlTransition);
        } else if (xmlTransition instanceof FlipbookTransition) {
            return transform(spriteId, (FlipbookTransition) xmlTransition);
        }
        throw new IllegalArgumentException("Invalid transition: " + xmlTransition);
    }

    @Override
    public Sprite transform(com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite xmlSprite) {
        int SpriteID = spriteIdByXmlId.get(xmlSprite.getId());
        float realWidth = getRealWidth(xmlSprite.getSpriteTransform().getWidth());
        float realHeight = getRealHeight(xmlSprite.getSpriteTransform().getHeight());
        Point2D spriteTopLeft = getRealPoint(xmlSprite.getSpriteTransform().getPosition());
        List<Transition> transitions = new ArrayList<>();
        for (com.meandmyphone.genericdevicefancyvator.json.pojo.Transition transition : xmlSprite.getTransition()) {
            // TODO
//            if (transition.getFadeTransition() != null) {
//                transitions.add(transform(SpriteID, transition.getFadeTransition()));
//            } else if (transition.getTranslateTransition() != null) {
//                transitions.add(transform(SpriteID, transition.getFadeTransition()));
//            } else if (transition.getRotateTransition() != null) {
//                transitions.add(transform(SpriteID, transition.getFadeTransition()));
//            } else if (transition.getScaleTransition() != null) {
//                transitions.add(transform(SpriteID, transition.getFadeTransition()));
//            } else if (transition.getFlipbookTransition() != null) {
//                transitions.add(transform(SpriteID, transition.getFlipbookTransition()));
//            } else {
//                throw new IllegalArgumentException("Invalid transition: " + transition);
//            }
        }
        return spriteFactory.createSprite(
                resourceIdByXmlResourceId.get(xmlSprite.getResource()),
                spriteTopLeft.X,
                spriteTopLeft.Y,
                realWidth,
                realHeight,
                xmlSprite.getSpriteTopleftU(),
                xmlSprite.getSpriteTopleftV(),
                xmlSprite.getSpriteBotrightU(),
                xmlSprite.getSpriteBotrightV(),
                xmlSprite.getAlpha(),
                xmlSprite.getScaleX(),
                xmlSprite.getScaleY(),
                xmlSprite.getRotation(),
                Anchor.fromPivot(xmlSprite.getPivot())
        );
    }

    @Override
    public int transform(Ease xmlEase) {
        switch (xmlEase) {
            case LINEAR:
                return ITransition.LINEAR;
            case CUBIC_IN:
                return ITransition.CUBICEASEIN;
            case CUBIC_OUT:
                return ITransition.CUBICEASEOUT;
            case CUBIC_INOUT:
                return ITransition.CUBICEASEINOUT;
            case QUAD_IN:
                return ITransition.QUADRATICEASEIN;
            case QUAD_OUT:
                return ITransition.QUADRATICEASEINOUT;
            case QUAD_INOUT:
                return ITransition.QUADRATICEASEINOUT;
            case SINE_IN:
                return ITransition.SINEASEIN;
            case SINE_OUT:
                return ITransition.SINEASEOUT;
            case SINE_INOUT:
                return ITransition.SINEASEINOUT;
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
        float distanceX = getRealWidth(position.getXDistanceFromTarget());
        float distanceY = getRealHeight(position.getYDistanceFromTarget());
        if (position instanceof SceneRelativePosition) {
            SceneRelativePosition pos = (SceneRelativePosition)position;
            Point2D relativePoint = scene.getScenePointOfInterest(Anchor.fromPivot(pos.getRelativePointOfTarget()));
            float x = relativePoint.X + distanceX;
            float y = relativePoint.Y - distanceY;
            return new Point2D(x, y);
        } else if (position instanceof SpriteRelativePosition) {
            SpriteRelativePosition pos = (SpriteRelativePosition) position;
            int relativeToSpirteId = spriteIdByXmlId.get(pos.getRelativeSpriteId());
            Sprite relativeToSprite = scene.getSprite(relativeToSpirteId);
            Point2D relativePoint = relativeToSprite.getPointOfInterest(Anchor.fromPivot(pos.getRelativePointOfTarget()));
            float x = relativePoint.X + distanceX;
            float y = relativePoint.Y - distanceY;
            return new Point2D(x, y);
        }
        throw new IllegalArgumentException("Invalid position: " + position.toString());
    }
}
