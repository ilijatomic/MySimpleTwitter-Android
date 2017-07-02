package com.mera.mysimpletwitter.ui.splash.di;

import com.mera.mysimpletwitter.core.di.scope.PerActivity;
import com.mera.mysimpletwitter.ui.splash.view.SplashActivity;

import dagger.Component;

/**
 * Created by ikac on 7/2/17.
 * DI component for splash/login activity
 */
@PerActivity
@Component(modules = SplashModule.class)
public interface SplashComponent {

    void inject(SplashActivity splashActivity);
}
