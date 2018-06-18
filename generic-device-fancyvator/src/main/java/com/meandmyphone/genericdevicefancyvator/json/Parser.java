package com.meandmyphone.genericdevicefancyvator.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meandmyphone.genericdevicefancyvator.json.pojo.background.Background;
import com.meandmyphone.genericdevicefancyvator.json.pojo.background.GradientBackground;
import com.meandmyphone.genericdevicefancyvator.json.pojo.background.ImageBackground;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.BaseTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FadeTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.FlipbookTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.Position;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.RotateTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.ScaleTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Scene;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transform.SpriteRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.SequentialTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.transition.TranslateTransition;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parser {

    public com.meandmyphone.genericdevicefancyvator.json.pojo.Scene parse(InputStream inputStream) {

        RuntimeTypeAdapterFactory<Position> positionRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Position.class, "positionType")
                .registerSubtype(SpriteRelativePosition.class, "SPRITE_RELATIVE")
                .registerSubtype(SceneRelativePosition.class, "SCENE_RELATIVE");

        RuntimeTypeAdapterFactory<BaseTransition> transitionRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(BaseTransition.class, "transitionType")
                .registerSubtype(FadeTransition.class, "FADE")
                .registerSubtype(TranslateTransition.class, "TRANSLATE")
                .registerSubtype(RotateTransition.class, "ROTATE")
                .registerSubtype(ScaleTransition.class, "SCALE")
                .registerSubtype(FlipbookTransition.class, "FLIPBOOK")
                .registerSubtype(SequentialTransition.class, "SEQUENCE")
                ;

        RuntimeTypeAdapterFactory<Background> backgroudnRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Background.class, "backgroundType")
                .registerSubtype(ImageBackground.class, "GRADIENT")
                .registerSubtype(GradientBackground.class, "IMAGE")
                ;

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(positionRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(transitionRuntimeTypeAdapterFactory)
                .create();


        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));

        return gson.fromJson(reader, Scene.class);
    }
}