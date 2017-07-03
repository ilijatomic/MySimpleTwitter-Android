package com.mera.mysimpletwitter.ui.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mera.mysimpletwitter.R;
import com.mera.mysimpletwitter.ui.application.BaseActivity;
import com.mera.mysimpletwitter.ui.splash.di.DaggerSplashComponent;
import com.mera.mysimpletwitter.ui.splash.presenter.LoginPresenter;
import com.mera.mysimpletwitter.ui.timeline.view.TimelineActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ilija.tomic on 6/29/2017.
 * Class for displaying and handling actions in splash screen
 */
public class SplashActivity extends BaseActivity {

    private static final long TWO_SECONDS = 2 * 1000;

    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.splash_login_button)
    TwitterLoginButton mLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        DaggerSplashComponent
                .builder()
                .build()
                .inject(this);

        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                showTimeLiveScreen();
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                checkLoginSession();
                showTimeLiveScreen();
            }
        }, TWO_SECONDS);
    }

    /**
     *
     */
    private void checkLoginSession() {
        if (mLoginPresenter.checkLoginSession()) {
            showTimeLiveScreen();
        } else {
            mLoginButton.setVisibility(View.VISIBLE);
            Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);
            mLoginButton.startAnimation(fadeInAnimation);
        }
    }

    /**
     *
     */
    private void showTimeLiveScreen() {
        startActivity(new Intent(this, TimelineActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
