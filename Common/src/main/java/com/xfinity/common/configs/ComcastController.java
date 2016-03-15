package com.xfinity.common.configs;

import android.app.Application;

public class ComcastController extends Application {

    private static ComcastController mApp;

    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static ComcastController getApp() {
        return mApp;
    }

}
