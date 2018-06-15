package com.meandmyphone.genericdevicefancyvator.common;

import android.content.Context;
import android.util.Log;

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
import com.meandmyphone.genericdevicefancyvator.core.transitions.TranslateTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Aspect;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.CycleType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.Ease;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FlipbookTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.Frame;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Measure;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Position;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.RelativityType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SpriteRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.Transition;

import java.util.HashMap;
import java.util.Map;

import static com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory.Sprite;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.FADE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.FLIPBOOK;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.ROTATE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.SCALE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.TRANSLATE;

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
            int internalId = Sprite.SPRITE_COUNTER++;
            spriteIdByXmlId.put(sprite.getId(), internalId);
            int resourceId = context.getResources().getIdentifier(sprite.getResource(), "drawable", context.getPackageName());
            resourceIdByXmlResourceId.put(sprite.getResource(), resourceId);
        }
    }

    private FadeTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FadeTransition xmlTransition) {
        return new FadeTransition(
                renderer,
                xmlTransition.getDuration(),
                spriteId,
                transform(xmlTransition.getEase()),
                xmlTransition.getFromAlpha(),
                xmlTransition.getToAlpha()
        );
    }

    private TranslateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TranslateTransition xmlTransition) {
        TranslateTransition translateTransition = new TranslateTransition(
                renderer,
                new Point2D(0, 0),
                new Point2D(
                        transformWidth(xmlTransition.getByX()),
                        transformHeight(xmlTransition.getByY())),
                xmlTransition.getDuration(),
                spriteId,
                transform(xmlTransition.getEase())
        );
        if (CycleType.RESTART.equals(xmlTransition.getCycleType())) {
            translateTransition.setAutoreverse(false);
        }
        return translateTransition;
    }

    private RotateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.RotateTransition xmlTransition) {
        return new RotateTransition(
                renderer,
                xmlTransition.getDuration(),
                spriteId,
                transform(xmlTransition.getEase()),
                xmlTransition.getFromAngle(),
                xmlTransition.getToAngle()
        );
    }

    private ScaleTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.ScaleTransition xmlTransition) {
        return new ScaleTransition(
                renderer,
                xmlTransition.getDuration(),
                spriteId,
                transform(xmlTransition.getEase()),
                xmlTransition.getFromX(),
                xmlTransition.getToX(),
                xmlTransition.getFromY(),
                xmlTransition.getToY()
        );
    }

    private FlipBookAnimation transform(int spriteId, FlipbookTransition xmlTransition) {
        int flipCount = xmlTransition.getSprite().size();
        int[] resources = new int[flipCount];
        float[] top_left_u = new float[flipCount];
        float[] top_left_v = new float[flipCount];
        float[] bot_right_u = new float[flipCount];
        float[] bot_right_v = new float[flipCount];
        int delay = xmlTransition.getDuration() / flipCount;

        for (int i = 0; i < flipCount; i++) {
            Frame xmlSprite = xmlTransition.getSprite().get(i);
            //noinspection SuspiciousMethodCalls
            resources[i] = resourceIdByXmlResourceId.get(xmlSprite.getResource());
            top_left_u[i] = xmlSprite.getTopLeftU();
            top_left_v[i] = xmlSprite.getTopLeftV();
            bot_right_u[i] = xmlSprite.getBotRightU();
            bot_right_v[i] = xmlSprite.getBotRightV();
        }
        FlipBookAnimation flipBookAnimation = new FlipBookAnimation(renderer, spriteId, resources, top_left_u, top_left_v, bot_right_u, bot_right_v, delay);
        if (CycleType.RESTART.equals(xmlTransition.getCycleType())) {
            flipBookAnimation.setAutoreverse(false);
        }
        return flipBookAnimation;
    }

    @Override
    public Sprite transform(com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite xmlSprite) {
        int spriteID = spriteIdByXmlId.get(xmlSprite.getId());
        float realWidth;
        float realHeight;

        if (Aspect.NONE.equals(xmlSprite.getAspect())) {
            realWidth = transformWidth(xmlSprite.getSpriteTransform().getWidth());
            realHeight = transformHeight(xmlSprite.getSpriteTransform().getHeight());
        } else {

            float originalWidth = xmlSprite.getSpriteBotrightU() - xmlSprite.getSpriteTopleftU();
            float originalHeight = xmlSprite.getSpriteBotrightV() - xmlSprite.getSpriteTopleftV();
            float aspectRatio = originalWidth / originalHeight;

            if (Aspect.WIDTH.equals(xmlSprite.getAspect())) {
                realWidth = transformWidth(xmlSprite.getSpriteTransform().getWidth());
                realHeight = realWidth / aspectRatio;
            } else if (Aspect.HEIGHT.equals(xmlSprite.getAspect())) {
                realHeight = transformHeight(xmlSprite.getSpriteTransform().getHeight());
                realWidth = realHeight * aspectRatio;
            } else {
                throw new IllegalArgumentException("Invalid aspect: " + xmlSprite.getAspect() + " in: " + xmlSprite.getId());
            }
        }
        Point2D pivotPoint = transformPoint(xmlSprite.getSpriteTransform().getPosition());
        Point2D spriteTopLeft = transformPointToTopLeft(pivotPoint, Anchor.fromPivot(xmlSprite.getPivot()), realWidth, realHeight);

        Sprite sprite = spriteFactory.createSprite(
                spriteIdByXmlId.get(xmlSprite.getId()),
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
                Anchor.fromPivot(xmlSprite.getPivot()),
                xmlSprite.getSortingOrder()
        );

        for (Transition transition : xmlSprite.getTransitions()) {
            if (FADE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FadeTransition) transition));
            } else if (TRANSLATE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TranslateTransition) transition));
            } else if (ROTATE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.RotateTransition) transition));
            } else if (SCALE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.ScaleTransition) transition));
            } else if (FLIPBOOK.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (FlipbookTransition) transition));
            } else {
                throw new IllegalArgumentException("Invalid transition: " + transition);
            }
        }
        return sprite;
    }

    private int transform(Ease xmlEase) {
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

    private float transformWidth(Measure measure) {
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

    private float transformHeight(Measure measure) {
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

    private Point2D transformPoint(Position position) {
        float distanceX = transformWidth(position.getXDistanceFromTarget());
        float distanceY = transformHeight(position.getYDistanceFromTarget());
        if (position instanceof SceneRelativePosition) {
            SceneRelativePosition pos = (SceneRelativePosition) position;
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

    private Point2D transformPointToTopLeft(Point2D pivotPoint, Anchor anchor, float realWidth, float realHeight) {
        switch (anchor) {
            case TOPLEFT:
                return pivotPoint;
            case TOPCENTER:
                return new Point2D(pivotPoint.X - realWidth / 2, pivotPoint.Y);
            case TOPRIGHT:
                return new Point2D(pivotPoint.X - realWidth, pivotPoint.Y);
            case CENTERRIGHT:
                return new Point2D(pivotPoint.X - realWidth, pivotPoint.Y + realHeight / 2);
            case BOTRIGHT:
                return new Point2D(pivotPoint.X - realWidth, pivotPoint.Y + realHeight);
            case BOTCENTER:
                return new Point2D(pivotPoint.X - realWidth / 2, pivotPoint.Y + realHeight);
            case BOTLEFT:
                return new Point2D(pivotPoint.X, pivotPoint.Y + realHeight);
            case CENTERLEFT:
                return new Point2D(pivotPoint.X, pivotPoint.Y + realHeight / 2);
            case CENTER:
                return new Point2D(pivotPoint.X - realWidth / 2, pivotPoint.Y + realHeight);
        }
        throw new IllegalArgumentException("Invalid anchor: " + anchor);
    }
}
