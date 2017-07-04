package com.mera.mysimpletwitter.ui.application;

import android.app.Application;

import com.twitter.sdk.android.core.Twitter;

import io.realm.Realm;

/**
 * Created by ilija.tomic on 6/29/2017.
 * Application object initialization and utilities
 */
public class MSTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Twitter.initialize(this);
        Realm.init(this);
    }

}