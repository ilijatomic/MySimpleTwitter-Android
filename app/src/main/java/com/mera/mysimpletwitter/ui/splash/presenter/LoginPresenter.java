package com.mera.mysimpletwitter.ui.splash.presenter;

import com.twitter.sdk.android.core.TwitterCore;

/**
 * Created by ikac on 7/1/17.
 */

public class LoginPresenter implements ILoginPresenter {

    @Override
    public boolean checkLoginSession() {
        return TwitterCore.getInstance().getSessionManager().getActiveSession() != null;
    }

}
