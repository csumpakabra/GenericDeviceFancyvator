package com.meandmyphone.genericdevicefancyvator.core;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by csumpakadabra on 2017.10.15..
 */

public class GLView extends GLSurfaceView {
    private final GLRenderer mRenderer;

    private boolean rendererSet = false;

    public GLView(Context context, OnTouchListener onTouchListener) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new GLRenderer(context);


        setOnTouchListener(onTouchListener);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        rendererSet = true;
    }


}
