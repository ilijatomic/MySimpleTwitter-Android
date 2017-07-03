package com.mera.mysimpletwitter.ui.timeline.di;

import com.mera.mysimpletwitter.ui.timeline.model.TimelineModel;
import com.mera.mysimpletwitter.ui.timeline.presenter.TimelinePresenter;
import com.twitter.sdk.android.core.TwitterCore;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ikac on 7/2/17.
 */
@Module
public class TimelineModule {

    @Provides
    TimelinePresenter provideTimelinePresenter(TimelineModel timelineModel, Scheduler scheduler) {
        return new TimelinePresenter(timelineModel, scheduler);
    }

    @Provides
    TimelineModel provideTimelineModel() {
        return new TimelineModel(TwitterCore.getInstance().getSessionManager().getActiveSession());
    }

    @Provides
    Scheduler provideScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
