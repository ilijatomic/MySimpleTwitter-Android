package com.mera.mysimpletwitter.ui.splash.presenter;

import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by ikac on 7/1/17.
 */

public class LoginPresenter implements ILoginPresenter {

    public LoginPresenter() {
    }

    @Override
    public boolean checkLoginSession() {
        return TwitterCore.getInstance().getSessionManager().getActiveSession() != null;
    }

}
