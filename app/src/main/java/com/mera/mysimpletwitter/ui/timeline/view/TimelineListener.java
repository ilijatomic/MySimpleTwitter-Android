package com.mera.mysimpletwitter.ui.timeline.view;

import com.mera.mysimpletwitter.core.db.TweetEntity;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by ilija.tomic on 7/3/2017.
 */
public interface TimelineListener {

    void showTimeline(List<Tweet> tweets);

    void showTimelineFromDB(List<TweetEntity> tweets);
}
