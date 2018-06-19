package com.meandmyphone.genericdevicefancyvator.transformer;

import android.content.Context;
import android.util.Log;

import com.meandmyphone.genericdevicefancyvator.core.background.Background;
import com.meandmyphone.genericdevicefancyvator.core.background.FillType;
import com.meandmyphone.genericdevicefancyvator.core.background.GradientBackground;
import com.meandmyphone.genericdevicefancyvator.core.background.ImageBackground;
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
import com.meandmyphone.genericdevicefancyvator.core.transitions.SequentialTranstion;
import com.meandmyphone.genericdevicefancyvator.core.transitions.Transition;
import com.meandmyphone.genericdevicefancyvator.core.transitions.TranslateTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Aspect;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.BaseTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.DestroyEffect;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.Ease;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FlipbookTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.Frame;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Measure;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Position;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SpriteRelativePosition;

import java.util.HashMap;
import java.util.Map;

import static com.meandmyphone.genericdevicefancyvator.core.gl.SpriteFactory.Sprite;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.background.BackgroundType.*;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transform.PositionType.SCENE_RELATIVE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transform.PositionType.SPRITE_RELATIVE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transform.RelativityType.*;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.CycleType.YOYO;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.FADE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.FLIPBOOK;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.ROTATE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.SCALE;
import static com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType.SEQUENCE;
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
        for (com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite sprite : xmlScene.getSprites()) {
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
                xmlTransition.getCycleCount(),
                transform(xmlTransition.getEase()),
                transform(xmlTransition.getDestroyEffect()),
                YOYO.equals(xmlTransition.getCycleType()),
                xmlTransition.getFromAlpha(),
                xmlTransition.getToAlpha()
        );
    }

    private TranslateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TranslateTransition xmlTransition) {
        return new TranslateTransition(
                renderer,
                new Point2D(0, 0),
                new Point2D(
                        transformWidth(xmlTransition.getByX()),
                        transformHeight(xmlTransition.getByY())),
                xmlTransition.getDuration(),
                spriteId,
                xmlTransition.getCycleCount(),
                transform(xmlTransition.getEase()),
                transform(xmlTransition.getDestroyEffect()),
                YOYO.equals(xmlTransition.getCycleType())
        );
    }

    private RotateTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.RotateTransition xmlTransition) {
        return new RotateTransition(
                renderer,
                xmlTransition.getDuration(),
                spriteId,
                xmlTransition.getCycleCount(),
                transform(xmlTransition.getEase()),
                transform(xmlTransition.getDestroyEffect()),
                YOYO.equals(xmlTransition.getCycleType()),
                xmlTransition.getFromAngle(),
                xmlTransition.getToAngle()
        );
    }

    private ScaleTransition transform(int spriteId, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.ScaleTransition xmlTransition) {
        return new ScaleTransition(
                renderer,
                xmlTransition.getDuration(),
                spriteId,
                xmlTransition.getCycleCount(),
                transform(xmlTransition.getEase()),
                transform(xmlTransition.getDestroyEffect()),
                YOYO.equals(xmlTransition.getCycleType()),
                xmlTransition.getFromX(),
                xmlTransition.getToX(),
                xmlTransition.getFromY(),
                xmlTransition.getToY()
        );
    }

    private FlipBookAnimation transform(int spriteId, FlipbookTransition xmlTransition) {
        int flipCount = xmlTransition.getSprites().size();
        int[] resources = new int[flipCount];
        float[] top_left_u = new float[flipCount];
        float[] top_left_v = new float[flipCount];
        float[] bot_right_u = new float[flipCount];
        float[] bot_right_v = new float[flipCount];
        int delay = xmlTransition.getDuration() / flipCount;

        for (int i = 0; i < flipCount; i++) {
            Frame xmlSprite = xmlTransition.getSprites().get(i);
            //noinspection SuspiciousMethodCalls
            resources[i] = resourceIdByXmlResourceId.get(xmlSprite.getResource());
            top_left_u[i] = xmlSprite.getTopLeftU();
            top_left_v[i] = xmlSprite.getTopLeftV();
            bot_right_u[i] = xmlSprite.getBotRightU();
            bot_right_v[i] = xmlSprite.getBotRightV();
        }
        return new FlipBookAnimation(
                renderer,
                spriteId,
                xmlTransition.getCycleCount(),
                transform(xmlTransition.getDestroyEffect()),
                YOYO.equals(xmlTransition.getCycleType()),
                resources,
                top_left_u,
                top_left_v,
                bot_right_u,
                bot_right_v,
                delay);
    }

    private SequentialTranstion transform(int spriteID, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.SequentialTransition xmlTransition) {
        Transition [] transitions = new Transition[xmlTransition.getTransitions().size()];
        for (int i = 0; i < transitions.length; i++) {
            com.meandmyphone.genericdevicefancyvator.json.pojo.transition.BaseTransition transition = xmlTransition.getTransitions().get(i);
            transitions[i] = transform(spriteID, transition);
        }
        return new SequentialTranstion(renderer, spriteID, xmlTransition.getCycleCount(), YOYO.equals(xmlTransition.getCycleType()), transitions);
    }

    private Transition transform(int spriteID, com.meandmyphone.genericdevicefancyvator.json.pojo.transition.BaseTransition xmlTransition) {
        if (FADE.equals(xmlTransition.getTransitionType())) {
            return transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FadeTransition) xmlTransition);
        } else if (TRANSLATE.equals(xmlTransition.getTransitionType())) {
            return transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TranslateTransition) xmlTransition);
        } else if (ROTATE.equals(xmlTransition.getTransitionType())) {
            return transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.RotateTransition) xmlTransition);
        } else if (SCALE.equals(xmlTransition.getTransitionType())) {
            return transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.ScaleTransition) xmlTransition);
        } else if (FLIPBOOK.equals(xmlTransition.getTransitionType())) {
            return transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FlipbookTransition) xmlTransition);
        } else {
            throw new IllegalArgumentException("Invalid transition: " + xmlTransition.getTransitionType());
        }
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

        for (BaseTransition transition : xmlSprite.getTransitions()) {
            if (FADE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FadeTransition) transition));
            } else if (TRANSLATE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TranslateTransition) transition));
            } else if (ROTATE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.RotateTransition) transition));
            } else if (SCALE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.ScaleTransition) transition));
            } else if (FLIPBOOK.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FlipbookTransition) transition));
            } else if (SEQUENCE.equals(transition.getTransitionType())) {
                sprite.addTransition(transform(spriteID, (com.meandmyphone.genericdevicefancyvator.json.pojo.transition.SequentialTransition) transition));
            } else {
                throw new IllegalArgumentException("Invalid transition: " + transition);
            }
        }
        return sprite;
    }

    private int transform(Ease xmlEase) {
        switch (xmlEase) {
            case DEFAULT:
                return ITransition.DEFAULT;
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

    @Override
    public Background transform(com.meandmyphone.genericdevicefancyvator.json.pojo.background.Background background) {
        if (GRADIENT.equals(background.getBackgroundType())) {
            com.meandmyphone.genericdevicefancyvator.json.pojo.background.GradientBackground gradientBackground =
                    (com.meandmyphone.genericdevicefancyvator.json.pojo.background.GradientBackground) background;
            return new GradientBackground(
                    context,
                    gradientBackground.getTopLeftColor(),
                    gradientBackground.getBotLeftColor(),
                    gradientBackground.getBotRightColor(),
                    gradientBackground.getBotRightColor()
            );
        } else if (IMAGE.equals(background.getBackgroundType())) {
            com.meandmyphone.genericdevicefancyvator.json.pojo.background.ImageBackground imageBackground =
                    (com.meandmyphone.genericdevicefancyvator.json.pojo.background.ImageBackground) background;
            int resId;
            if (resourceIdByXmlResourceId.containsKey(imageBackground.getResource())) {
                resId = resourceIdByXmlResourceId.get(imageBackground.getResource());
            } else {
                resId = context.getResources().getIdentifier(imageBackground.getResource(), "drawable", context.getPackageName());
            }
            return new ImageBackground(
                    context,
                    resId,
                    transform(imageBackground.getFillType()),
                    imageBackground.getTopLeftU(),
                    imageBackground.getTopLeftV(),
                    imageBackground.getBotRightU(),
                    imageBackground.getBotRightV()
            );
        }
        throw new IllegalArgumentException("Invalid backgroundType: " + background.getBackgroundType());
    }

    private float transformWidth(Measure measure) {
        if (NONE.equals(measure.getRelativity())) {
            Log.w(TAG, "Creating absolute width sprite!");
            return measure.getValue();
        } else if (SCENE.equals(measure.getRelativity())) {
            return measure.getValue() * scene.getSceneWidth();
        } else if (SPRITE.equals(measure.getRelativity())) {
            int relativeToSpriteId = spriteIdByXmlId.get(measure.getRelativeTo());
            Sprite relativeToSprite = scene.getSprite(relativeToSpriteId);
            return relativeToSprite.width * measure.getValue();
        }
        throw new IllegalArgumentException("Invalid measure: " + measure.getRelativity());
    }

    private float transformHeight(Measure measure) {
        if (NONE.equals(measure.getRelativity())) {
            Log.w(TAG, "Creating absolute height sprite!");
            return measure.getValue();
        } else if (SCENE.equals(measure.getRelativity())) {
            return measure.getValue() * scene.getSceneHeight();
        } else if (SPRITE.equals(measure.getRelativity())) {
            int relativeToSpriteId = spriteIdByXmlId.get(measure.getRelativeTo());
            Sprite relativeToSprite = scene.getSprite(relativeToSpriteId);
            return relativeToSprite.height * measure.getValue();
        }
        throw new IllegalArgumentException("Invalid measure: " + measure.getRelativity());
    }

    private Point2D transformPoint(Position position) {
        float distanceX = transformWidth(position.getXDistanceFromTarget());
        float distanceY = transformHeight(position.getYDistanceFromTarget());
        if (SCENE_RELATIVE.equals(position.getPositionType())) {
            SceneRelativePosition pos = (SceneRelativePosition) position;
            Point2D relativePoint = scene.getScenePointOfInterest(Anchor.fromPivot(pos.getRelativePointOfTarget()));
            float x = relativePoint.X + distanceX;
            float y = relativePoint.Y - distanceY;
            return new Point2D(x, y);
        } else if (SPRITE_RELATIVE.equals(position.getPositionType())) {
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

    private FillType transform(com.meandmyphone.genericdevicefancyvator.json.pojo.background.FillType fillType) {
        switch (fillType) {
            case STRETCH: return FillType.STRETCH;
            case REPEAT: return FillType.REPEAT;
        }
        throw new IllegalArgumentException("Invalid fillType: " + fillType);
    }

    private int transform(DestroyEffect destroyEffect) {
        switch (destroyEffect) {
            case DONTDESTROY: return ITransition.DESTROY_EFFECT_DONT_DESTROY;
            case NONE: return ITransition.DESTROY_EFFECT_NONE;
            case FADE: return ITransition.DESTROY_EFFECT_FADE;
        }
        throw new IllegalArgumentException("Invalid destroyEffect: " + destroyEffect);
    }
}
