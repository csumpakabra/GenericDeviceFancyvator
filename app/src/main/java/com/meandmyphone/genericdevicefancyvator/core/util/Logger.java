package com.meandmyphone.genericdevicefancyvator.core.util;

import android.util.Log;

/**
 * Created by csumpakadabra on 2017.10.17..
 */

public class Logger {
    public static void Log(String TAG, String message) {
        Log(TAG,message,null);
    }

    public static void Log(String TAG, String message, Object... args) {
        Log.d("GLLWP/".concat(TAG), String.format(message,args));}
}
