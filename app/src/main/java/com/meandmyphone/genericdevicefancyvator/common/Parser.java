package com.meandmyphone.genericdevicefancyvator.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meandmyphone.genericdevicefancyvator.json.pojo.FadeTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.FlipbookTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Position;
import com.meandmyphone.genericdevicefancyvator.json.pojo.RotateTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.ScaleTransition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Scene;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SceneRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.SpriteRelativePosition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.Transition;
import com.meandmyphone.genericdevicefancyvator.json.pojo.TranslateTransition;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parser {

    public Scene parse(InputStream inputStream) {

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
                .registerSubtype(FlipbookTransition.class, "FLIPBOOK")
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