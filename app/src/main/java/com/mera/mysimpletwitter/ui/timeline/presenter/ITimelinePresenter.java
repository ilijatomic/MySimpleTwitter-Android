package com.mera.mysimpletwitter.ui.timeline.presenter;

import com.mera.mysimpletwitter.ui.timeline.view.TimelineListener;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by ilija.tomic on 7/3/2017.
 */
public interface ITimelinePresenter {

    void init(TimelineListener timelineListener);

    void loadMore();

    void saveTweetsToDB(List<Tweet> tweets);

    void loadTweetsFromDB();
}
