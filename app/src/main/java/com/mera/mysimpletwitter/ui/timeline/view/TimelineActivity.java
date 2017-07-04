package com.mera.mysimpletwitter.ui.timeline.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mera.mysimpletwitter.R;
import com.mera.mysimpletwitter.core.db.TweetEntity;
import com.mera.mysimpletwitter.ui.application.BaseActivity;
import com.mera.mysimpletwitter.ui.search.view.SearchActivity;
import com.mera.mysimpletwitter.ui.timeline.di.DaggerTimelineComponent;
import com.mera.mysimpletwitter.ui.timeline.presenter.TimelinePresenter;
import com.mera.mysimpletwitter.ui.timeline.view.adapter.EndlessScrollListener;
import com.mera.mysimpletwitter.ui.timeline.view.adapter.TimelineAdapter;
import com.mera.mysimpletwitter.ui.timeline.view.fragment.TweetImageFragment;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ikac on 7/2/17.
 * Activity for displaying and handling actions on timeline screen
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
        mTimelineAdapter.getViewClickSubject().subscribe(this::showTweetImage);
        mTimelineTweets.setAdapter(mTimelineAdapter);
        mTimelineTweets.addOnScrollListener(mEndlessListener);

        mSwipeRefreshLayout.setOnRefreshListener(mTimelinePresenter::loadMore);
        mSwipeRefreshLayout.setRefreshing(true);

        mTimelinePresenter.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(this, SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        return true;
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

    /**
     * This is where dialog fragment is created and image from tweet is displayed
     *
     * @param imageUrl image url to load
     */
    void showTweetImage(String imageUrl) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(TweetImageFragment.class.getSimpleName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dialogFragment = TweetImageFragment.newInstance(imageUrl);
        dialogFragment.show(ft, TweetImageFragment.class.getSimpleName());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private EndlessScrollListener mEndlessListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore() {
            mTimelinePresenter.loadMore();
        }
    };
}
