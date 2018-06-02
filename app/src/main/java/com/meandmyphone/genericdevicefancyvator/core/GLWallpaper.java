package com.meandmyphone.genericdevicefancyvator.core;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.meandmyphone.genericdevicefancyvator.data.Point2D;
import com.meandmyphone.genericdevicefancyvator.util.Logger;

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

        private WallpaperGLSurfaceView glSurfaceView;
        private GLRenderer glRenderer;
        private Point2D touchStart;
        private boolean offsetChangedWorking = false;
        final SharedPreferences.OnSharedPreferenceChangeListener listener;

        public GLEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(GLWallpaper.this);
            glRenderer = new GLRenderer(GLWallpaper.this);
            listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    glRenderer.setFPS(sharedPreferences.getInt("fpspref",30));
                    glRenderer.setTouchEnabled(sharedPreferences.getBoolean("touchpref",true));
                    glRenderer.setGhostAnimationValue(Integer.parseInt(sharedPreferences.getString("ghostanim","2")));
                    glRenderer.setLWPSpeed(sharedPreferences.getInt("speedpref",3));
                    // TODO listen other prefs
                }
            };
            prefs.registerOnSharedPreferenceChangeListener(listener);
            glRenderer.setFPS(prefs.getInt("fpspref",30));
            glRenderer.setTouchEnabled(prefs.getBoolean("toucEnabled",true));
            Logger.Log(TAG,"New GLEngine created!\nFPS=%d\nTouchEnabled=%s",glRenderer.getFPS(), String.valueOf(glRenderer.isTouchEnabled()));
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

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