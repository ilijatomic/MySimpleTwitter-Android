package com.mera.mysimpletwitter.ui.timeline.model;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ikac on 7/2/17.
 */

public class TimelineModel extends TwitterApiClient implements ITimelineModel {

    @Override
    public Observable<List<Tweet>> getTimelineTweets() {
        return Observable.create(subscriber -> {

        });
    }
}
