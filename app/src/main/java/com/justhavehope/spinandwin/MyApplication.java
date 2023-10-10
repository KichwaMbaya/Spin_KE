package com.justhavehope.spinandwin;

import android.app.Application;

import com.anythink.core.api.ATSDK;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ATSDK.init(getApplicationContext(), getString(R.string.TopOnAppID), getString(R.string.TopOnAppKEY));
    }
}
