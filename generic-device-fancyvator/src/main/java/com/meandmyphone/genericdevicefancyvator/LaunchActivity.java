package com.meandmyphone.genericdevicefancyvator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meandmyphone.genericdevicefancyvator.json.Parser;
import com.meandmyphone.genericdevicefancyvator.json.RuntimeTypeAdapterFactory;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.CycleType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.DestroyEffect;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.Ease;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FadeTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FlipbookTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Measure;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Pivot;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Position;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.PositionType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.RelativityType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.RotateTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.ScaleTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Scene;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Sprite;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SpriteRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SpriteTransform;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.SequentialTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TransitionType;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TranslateTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.Transition;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        TextView text = findViewById(R.id.text);

        Scene scene = new Parser().parse(this.getResources().openRawResource(this.getResources().getIdentifier("input", "raw", this.getPackageName())));
        Sprite sprite = scene.getSprites().get(0);
        TranslateTransition tt = new TranslateTransition();
        Measure by = new Measure();
        by.setRelativity(RelativityType.SCENE);
        by.setValue(1.0f);
        tt.setByX(by);
        tt.setByY(by);
        tt.setCycleCount(10);
        tt.setEase(Ease.CUBIC_INOUT);
        tt.setDuration(1000);
        tt.setCycleType(CycleType.YOYO);
        tt.setTransitionType(TransitionType.TRANSLATE);
        sprite.getTransitions().add(tt);

        Sprite robot = new Sprite();
        SpriteTransform robotTransform = new SpriteTransform();
        Measure robotWidth = new Measure();
        Measure robotHeight = new Measure();
        Measure robotDistanceFromTargetX = new Measure();
        Measure robotDistanceFromTargetY = new Measure();
        SceneRelativePosition robotPosition = new SceneRelativePosition();
        robotDistanceFromTargetX.setRelativity(RelativityType.SCENE);
        robotDistanceFromTargetY.setRelativity(RelativityType.SCENE);
        robotDistanceFromTargetX.setValue(0.0f);
        robotDistanceFromTargetY.setValue(0.0f);
        robotPosition.setPositionType(PositionType.SCENE_RELATIVE);
        robotPosition.setRelativePointOfTarget(Pivot.CENTER);
        robotPosition.setXDistanceFromTarget(robotDistanceFromTargetX);
        robotPosition.setYDistanceFromTarget(robotDistanceFromTargetY);
        robotWidth.setRelativity(RelativityType.SCENE);
        robotWidth.setValue(0.2f);
        robotHeight.setRelativity(RelativityType.SCENE);
        robotHeight.setValue(0.2f);
        robotTransform.setPivot(Pivot.CENTER);
        robotTransform.setWidth(robotWidth);
        robotTransform.setHeight(robotHeight);
        robotTransform.setPosition(robotPosition);
        robot.setAlpha(1.0f);
        robot.setId("robot");
        robot.setPivot(Pivot.CENTER);
        robot.setRotation(0.0f);
        robot.setScaleX(1.0f);
        robot.setScaleY(1.0f);

        robot.setSpriteTopleftU(0);
        robot.setSpriteTopleftV(0);
        robot.setSpriteBotrightU(0.3333f);
        robot.setSpriteBotrightV(0.3333f);

        robot.setResource("roborun");
        robot.setSpriteTransform(robotTransform);
        
        
        
        FlipbookTransition ft = new FlipbookTransition();
        ft.setCycleCount(10);
        ft.setCycleType(CycleType.RESTART);
        ft.setEase(Ease.LINEAR);
        ft.setTransitionType(TransitionType.FLIPBOOK);
        ft.setDuration(3000);
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 3 && j == 3) break;
                Sprite roboAnim = new Sprite();
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
                sprite1Position.setRelativePointOfTarget(Pivot.CENTER);
                sprite1Position.setXDistanceFromTarget(sprite1DistanceFromTargetX);
                sprite1Position.setYDistanceFromTarget(sprite1DistanceFromTargetY);
                sprite1Width.setRelativity(RelativityType.SCENE);
                sprite1Width.setValue(0.2f);
                sprite1Height.setRelativity(RelativityType.SCENE);
                sprite1Height.setValue(0.2f);
                sprite1Transform.setPivot(Pivot.CENTER);
                sprite1Transform.setWidth(sprite1Width);
                sprite1Transform.setHeight(sprite1Height);
                sprite1Transform.setPosition(sprite1Position);
                roboAnim.setAlpha(1.0f);
                roboAnim.setId("robot");
                roboAnim.setPivot(Pivot.CENTER);
                roboAnim.setRotation(0.0f);
                roboAnim.setScaleX(1.0f);
                roboAnim.setScaleY(1.0f);

                float valU2 = j * 1.0f / 3;
                float valV2 = i * 1.0f / 3;

                float valU1 = valU2 - 1.0f / 3;
                float valV1 = valV2 - 1.0f / 3;

                roboAnim.setSpriteTopleftU(valU1);
                roboAnim.setSpriteTopleftV(valV1);
                roboAnim.setSpriteBotrightU(valU2);
                roboAnim.setSpriteBotrightV(valV2);

                roboAnim.setResource("roborun");
                roboAnim.setSpriteTransform(sprite1Transform);
//                ft.getSprites().add(roboAnim);
            }
        }

        robot.getTransitions().add(ft);

        scene.getSprites().add(robot);
        
        Parser parser = new Parser();
        com.meandmyphone.genericdevicefancyvator.json.pojo.Scene xmlScene = parser.parse(this.getResources().openRawResource(this.getResources().getIdentifier("input","raw",this.getPackageName())));
        
        Sprite seq = new Sprite();
        SpriteTransform seqTransform = new SpriteTransform();
        Measure seqWidth = new Measure();
        Measure seqHeight = new Measure();
        Measure seqDistanceFromTargetX = new Measure();
        Measure seqDistanceFromTargetY = new Measure();
        SceneRelativePosition seqPosition = new SceneRelativePosition();
        seqDistanceFromTargetX.setRelativity(RelativityType.SCENE);
        seqDistanceFromTargetY.setRelativity(RelativityType.SCENE);
        seqDistanceFromTargetX.setValue(0.0f);
        seqDistanceFromTargetY.setValue(0.0f);
        seqPosition.setPositionType(PositionType.SCENE_RELATIVE);
        seqPosition.setRelativePointOfTarget(Pivot.CENTER);
        seqPosition.setXDistanceFromTarget(seqDistanceFromTargetX);
        seqPosition.setYDistanceFromTarget(seqDistanceFromTargetY);
        seqWidth.setRelativity(RelativityType.SCENE);
        seqWidth.setValue(0.2f);
        seqHeight.setRelativity(RelativityType.SCENE);
        seqHeight.setValue(0.2f);
        seqTransform.setPivot(Pivot.CENTER);
        seqTransform.setWidth(seqWidth);
        seqTransform.setHeight(seqHeight);
        seqTransform.setPosition(seqPosition);
        seq.setAlpha(1.0f);
        seq.setId("seq");
        seq.setPivot(Pivot.CENTER);
        seq.setRotation(0.0f);
        seq.setScaleX(1.0f);
        seq.setScaleY(1.0f);

        seq.setSpriteTopleftU(0);
        seq.setSpriteTopleftV(0);
        seq.setSpriteBotrightU(0.3333f);
        seq.setSpriteBotrightV(0.3333f);

        seq.setResource("roborun");
        seq.setSpriteTransform(seqTransform);

        SequentialTransition st = new SequentialTransition();
        TranslateTransition tt1 = new TranslateTransition();
        Measure bySome = new Measure();
        bySome.setValue(0.5f);
        bySome.setRelativity(RelativityType.SCENE);
        Measure byZero = new Measure();
        bySome.setValue(0.0f);
        bySome.setRelativity(RelativityType.SCENE);
        
        tt1.setByX(bySome);
        tt1.setByY(byZero);
        
        tt1.setCycleType(CycleType.RESTART);
        tt1.setCycleCount(0);
        tt1.setDestroyEffect(DestroyEffect.DONTDESTROY);
        tt1.setDuration(1000);
        tt1.setEase(Ease.LINEAR);
        tt1.setTransitionType(TransitionType.TRANSLATE);

        TranslateTransition tt2 = new TranslateTransition();

        tt2.setByX(byZero);
        tt2.setByY(bySome);

        tt2.setCycleType(CycleType.RESTART);
        tt2.setCycleCount(0);
        tt2.setDestroyEffect(DestroyEffect.DONTDESTROY);
        tt2.setDuration(1000);
        tt2.setEase(Ease.LINEAR);
        tt2.setTransitionType(TransitionType.TRANSLATE);
        
        st.getTransitions().add(tt1);


        st.setCycleCount(0);
        st.setCycleType(CycleType.YOYO);
        st.setTransitionType(TransitionType.SEQUENCE);

        seq.getTransitions().add(st);

        RuntimeTypeAdapterFactory<Position> positionRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Position.class, "positionType")
                .registerSubtype(SpriteRelativePosition.class, "SPRITE_RELATIVE")
                .registerSubtype(SceneRelativePosition.class, "SCENE_RELATIVE");

        RuntimeTypeAdapterFactory<Transition> transitionRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Transition.class, "transitionType")
                .registerSubtype(FadeTransition.class, "FADE")
                .registerSubtype(TranslateTransition.class, "TRANSLATE")
                .registerSubtype(RotateTransition.class, "ROTATE")
                .registerSubtype(ScaleTransition.class, "SCALE")
                .registerSubtype(FlipbookTransition.class, "FLIPBOOK");

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(positionRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(transitionRuntimeTypeAdapterFactory)
                .create();

        //scene.sprite = new ArrayList<>();
        //scene.getSprites().add(sprite);

        Log.d("sprite", gson.toJson(seq));

    }
}