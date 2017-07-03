package com.mera.mysimpletwitter.ui.timeline.presenter;

import android.util.Log;

import com.mera.mysimpletwitter.core.db.TweetEntity;
import com.mera.mysimpletwitter.ui.timeline.model.TimelineModel;
import com.mera.mysimpletwitter.ui.timeline.view.TimelineListener;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;

/**
 * Created by ikac on 7/2/17.
 */

public class TimelinePresenter implements ITimelinePresenter {

    private static final Integer DEFAULT_COUNT_SIZE = 50;

    private final TimelineModel mTimelineModel;
    private final Scheduler mScheduler;

    private TimelineListener mTimelineListener;
    private List<Tweet> mTimelineTweets = new ArrayList<>();
    private List<TweetEntity> mTimelineTweetsDB = new ArrayList<>();

    public TimelinePresenter(TimelineModel timelineModel, Scheduler scheduler) {
        this.mTimelineModel = timelineModel;
        this.mScheduler = scheduler;
    }

    @Override
    public void init(TimelineListener timelineListener) {
        this.mTimelineListener = timelineListener;
        loadMore();
    }

    @Override
    public void loadMore() {
        Integer count = mTimelineTweets.size() + DEFAULT_COUNT_SIZE;
        mTimelineModel.getTimelineTweets(count)
                .observeOn(mScheduler)
                .subscribe(timelineTweets -> {
                        this.mTimelineTweets = timelineTweets;
                        if (mTimelineListener != null) {
                            mTimelineListener.showTimeline(mTimelineTweets);
                        }
                }, throwable -> loadTweetsFromDB());
    }

    @Override
    public void saveTweetsToDB(List<Tweet> tweets) {
        mTimelineModel.saveTimelineTweets(tweets);
    }

    @Override
    public void loadTweetsFromDB() {
        mTimelineModel.loadTimelineTweets()
                .observeOn(mScheduler)
                .subscribe(timelineTweetsDB -> {
                    this.mTimelineTweetsDB = timelineTweetsDB;
                    if (mTimelineListener != null) {
                        mTimelineListener.showTimelineFromDB(mTimelineTweetsDB);
                    }
                });
    }
}
