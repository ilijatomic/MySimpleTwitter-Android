package com.mera.mysimpletwitter.ui.splash.di;

import com.mera.mysimpletwitter.ui.splash.presenter.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ikac on 7/2/17.
 * DI module for {@link SplashComponent}
 */
@Module
public class SplashModule {

    @Provides
    LoginPresenter provideLoginPresenter() {
        return new LoginPresenter();
    }
}
