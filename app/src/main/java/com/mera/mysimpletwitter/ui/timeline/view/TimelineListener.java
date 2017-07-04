package com.mera.mysimpletwitter.ui.timeline.view;

import com.mera.mysimpletwitter.core.db.TweetEntity;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by ilija.tomic on 7/3/2017.
 * Listener for {@link TimelineActivity}
 */
public interface TimelineListener {

    /**
     * Display tweets form Twitter API call
     *
     * @param tweets list of tweets as result
     */
    void showTimeline(List<Tweet> tweets);

    /**
     * Display tweets from DB if user is offline, or some exception acquired
     *
     * @param tweets list of tweets save in DB
     */
    void showTimelineFromDB(List<TweetEntity> tweets);
}
