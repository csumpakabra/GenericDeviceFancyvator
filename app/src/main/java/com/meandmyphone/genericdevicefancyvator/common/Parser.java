package com.meandmyphone.genericdevicefancyvator.common;

import android.content.Context;

import com.meandmyphone.genericdevicefancyvator.xml.pojo.Scene;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Parser {

    private final Context context;

    public Parser(Context context) {
        this.context = context;
    }

    Scene parse() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Scene.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        int xmlId = context.getResources().getIdentifier("input", "raw", context.getPackageName());
        InputStream inputStream = context.getResources().openRawResource(xmlId);
        Scene scene = (Scene) unmarshaller.unmarshal(inputStream);
        return scene;
    }
}