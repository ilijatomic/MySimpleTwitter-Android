package com.mera.mysimpletwitter.ui.timeline.presenter;

import com.mera.mysimpletwitter.ui.timeline.view.TimelineListener;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by ilija.tomic on 7/3/2017.
 * Signature interface for {@link TimelinePresenter}
 */
public interface ITimelinePresenter {

    /**
     * Init presenter with listener
     *
     * @param timelineListener listener from {@link com.mera.mysimpletwitter.ui.timeline.view.TimelineActivity}
     */
    void init(TimelineListener timelineListener);

    /**
     * Load next set of tweets
     */
    void loadMore();

    /**
     * Save tweets in database
     *
     * @param tweets from TwitterAPI
     */
    void saveTweetsToDB(List<Tweet> tweets);

    /**
     * Load tweets from database
     */
    void loadTweetsFromDB();
}
