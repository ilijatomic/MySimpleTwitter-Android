package com.mera.mysimpletwitter.ui.splash.presenter;

/**
 * Created by ikac on 7/1/17.
 * Presenter for login screen/functions
 */

public interface ILoginPresenter {

    /**
     * Checking if user is logged in (active session exists)
     *
     * @return success result
     */
    boolean checkLoginSession();
}
