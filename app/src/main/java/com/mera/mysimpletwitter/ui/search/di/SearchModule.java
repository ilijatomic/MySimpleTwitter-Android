package com.mera.mysimpletwitter.ui.search.di;

import com.mera.mysimpletwitter.ui.search.model.SearchModel;
import com.mera.mysimpletwitter.ui.search.presenter.SearchPresenter;
import com.twitter.sdk.android.core.TwitterCore;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ilija.tomic on 7/4/2017.
 * Module for {@link SearchComponent}
 */
@Module
public class SearchModule {

    @Provides
    SearchPresenter provideSearchPresenter(SearchModel searchModel, Scheduler scheduler) {
        return new SearchPresenter(searchModel, scheduler);
    }

    @Provides
    SearchModel providesSearchModel() {
        return new SearchModel(TwitterCore.getInstance().getSessionManager().getActiveSession());
    }

    @Provides
    Scheduler provideScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
