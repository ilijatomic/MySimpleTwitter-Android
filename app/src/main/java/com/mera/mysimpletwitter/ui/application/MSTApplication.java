package com.mera.mysimpletwitter.ui.application;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Twitter;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ilija.tomic on 6/29/2017.
 * Application object initialization and utilities
 */
public class MSTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Twitter.initialize(this);
        Realm.init(getApplicationContext());
    }

}