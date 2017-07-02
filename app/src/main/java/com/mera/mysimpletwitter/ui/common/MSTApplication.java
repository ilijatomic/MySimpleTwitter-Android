package com.mera.mysimpletwitter.ui.common;

import android.app.Application;

import com.twitter.sdk.android.core.Twitter;

/**
 * Created by ilija.tomic on 6/29/2017.
 * Application object initialization and utilities
 */
public class MSTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Twitter.initialize(this);
    }

}
