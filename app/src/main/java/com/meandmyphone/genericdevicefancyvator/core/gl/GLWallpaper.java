package com.meandmyphone.genericdevicefancyvator.core.gl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.meandmyphone.genericdevicefancyvator.common.Transformer;
import com.meandmyphone.genericdevicefancyvator.core.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.core.util.Logger;

/**
 * Created by csumpakadabra on 2017.10.15..
 */

public class GLWallpaper extends WallpaperService {


    private boolean rendererSet;

    @Override
    public Engine onCreateEngine() {
        return new GLEngine();
    }

    public class GLEngine extends Engine {
        private static final String TAG = "Engine";

        private Transformer transformer;
        private WallpaperGLSurfaceView glSurfaceView;
        private GLRenderer glRenderer;
        private Point2D touchStart;
        private boolean offsetChangedWorking = false;

        public GLEngine() {
            glRenderer = new GLRenderer(GLWallpaper.this);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            Log.d("asd", "GlEngine created");

            glSurfaceView = new WallpaperGLSurfaceView(GLWallpaper.this);


            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();

            final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
            // Check for emulator. || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86")));

            if (supportsEs2) {
                glSurfaceView.setEGLContextClientVersion(2);
                glSurfaceView.setRenderer(glRenderer);
                rendererSet = true;
            } else {
                Toast.makeText(GLWallpaper.this, "This device does not support OpenGL ES 2.0.", Toast.LENGTH_LONG).show();
                return;
            }
        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (rendererSet) {
                if (visible) {
                    glSurfaceView.onResume();
                } else {
                    glSurfaceView.onPause();
                }
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            glSurfaceView.onWallpaperDestroy();
        }

        @Override
        public void onOffsetsChanged(final float xOffset, final float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
//            if (xOffset != 0.0f && xOffset != 0.5f) {
//                offsetChangedWorking = true;
//                Logger.Log(TAG, "onOffsetChanged is working!");
//            }
//
//            if (offsetChangedWorking) {
//                glSurfaceView.queueEvent(new Runnable() {
//                    @Override
//                    public void run() {
//                        float offsetDelta = 2 * (xOffset - 0.5f);
//                        glRenderer.setOffsetDelta(offsetDelta);
//                    }
//                });
//            }
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchStart = new Point2D(event.getX(), event.getY());
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                long eventTime = event.getEventTime() - event.getDownTime();
                if (!offsetChangedWorking) {
                    Logger.Log(TAG, "changing offset, from toucheventhandler!");
                    Point2D touchCurrent = new Point2D(event.getX(), event.getY());
                    final float distanceX = touchCurrent.X - touchStart.X;
                    final float distanceY = touchCurrent.Y - touchStart.Y;
                    if (eventTime > Constants.SWIPE_TIME_MS && Math.abs(distanceX) > GLRenderer.screenWidth / 6 && Math.abs(distanceY) < GLRenderer.screenHeight / 3) {
                        glSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                float offsetDelta = Math.signum(distanceX) * 0.5f;
                                glRenderer.setOffsetDelta(offsetDelta);
                            }
                        });

                    }
                }
                if (eventTime < Constants.SWIPE_TIME_MS) {
                    glRenderer.propagateTouchEvent(event.getX(), event.getY());
                }

            }

        }

        class WallpaperGLSurfaceView extends GLSurfaceView {
            WallpaperGLSurfaceView(Context context) {
                super(context);
            }

            @Override
            public SurfaceHolder getHolder() {
                return getSurfaceHolder();
            }

            public void onWallpaperDestroy() {
                super.onDetachedFromWindow();
            }
        }

    }


}
