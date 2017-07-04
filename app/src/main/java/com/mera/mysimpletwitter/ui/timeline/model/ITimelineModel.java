package com.mera.mysimpletwitter.ui.timeline.model;

import com.mera.mysimpletwitter.core.db.TweetEntity;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ikac on 7/2/17.
 * Signature interface for {@link TimelineModel}
 */

public interface ITimelineModel {

    /**
     * Get tweets from TwitterAPI
     *
     * @param count number of tweets to get as query param
     * @return      list of tweets from TwitterAPI
     */
    Observable<List<Tweet>> getTimelineTweets(Integer count);

    /**
     * Save tweets to database
     *
     * @param tweets    list of tweets to save
     * @return          success result
     */
    Observable<Boolean> saveTimelineTweets(List<Tweet> tweets);

    /**
     * Load tweets from database
     *
     * @return list of tweets from database
     */
    Observable<List<TweetEntity>> loadTimelineTweets();
}
