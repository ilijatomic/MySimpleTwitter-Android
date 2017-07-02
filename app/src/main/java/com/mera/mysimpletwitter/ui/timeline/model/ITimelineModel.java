package com.mera.mysimpletwitter.ui.timeline.model;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ikac on 7/2/17.
 */

public interface ITimelineModel {

    Observable<List<Tweet>> getTimelineTweets();
}
