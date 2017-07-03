package com.mera.mysimpletwitter.ui.timeline.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mera.mysimpletwitter.R;
import com.mera.mysimpletwitter.core.db.TweetEntity;
import com.mera.mysimpletwitter.ui.application.BaseActivity;
import com.mera.mysimpletwitter.ui.timeline.di.DaggerTimelineComponent;
import com.mera.mysimpletwitter.ui.timeline.presenter.TimelinePresenter;
import com.mera.mysimpletwitter.ui.timeline.view.adapter.EndlessScrollListener;
import com.mera.mysimpletwitter.ui.timeline.view.adapter.TimelineAdapter;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ikac on 7/2/17.
 */

public class TimelineActivity extends BaseActivity implements TimelineListener {

    @Inject
    TimelinePresenter mTimelinePresenter;

    @BindView(R.id.timeline_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.timeline_tweets)
    RecyclerView mTimelineTweets;

    private TimelineAdapter mTimelineAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ButterKnife.bind(this);

        DaggerTimelineComponent
                .builder()
                .build()
                .inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.timeline_toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTimelineTweets.setLayoutManager(lm);

        mTimelineAdapter = new TimelineAdapter();
        mTimelineTweets.setAdapter(mTimelineAdapter);
        mTimelineTweets.addOnScrollListener(mEndlessListener);

        mSwipeRefreshLayout.setOnRefreshListener(() -> mTimelinePresenter.loadMore());

        mTimelinePresenter.init(this);
    }

    @Override
    public void showTimeline(List<Tweet> tweets) {
        mSwipeRefreshLayout.setRefreshing(false);

        List<Object> tweet = new ArrayList<>(tweets);
        mTimelineAdapter.setItems(tweet);

        mTimelinePresenter.saveTweetsToDB(tweets);
    }

    @Override
    public void showTimelineFromDB(List<TweetEntity> tweets) {
        mSwipeRefreshLayout.setRefreshing(false);

        List<Object> tweet = new ArrayList<>(tweets);
        mTimelineAdapter.setItems(tweet);
    }

    private EndlessScrollListener mEndlessListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore() {
            mTimelinePresenter.loadMore();
        }
    };
}
