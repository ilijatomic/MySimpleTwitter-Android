package com.mera.mysimpletwitter.ui.search.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mera.mysimpletwitter.R;
import com.mera.mysimpletwitter.ui.application.BaseActivity;
import com.mera.mysimpletwitter.ui.search.di.DaggerSearchComponent;
import com.mera.mysimpletwitter.ui.search.presenter.SearchPresenter;
import com.mera.mysimpletwitter.ui.timeline.view.adapter.TimelineAdapter;
import com.mera.mysimpletwitter.ui.timeline.view.fragment.TweetImageFragment;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ilija.tomic on 7/4/2017.
 * Activity for displaying and handling action on search result screen
 */
public class SearchActivity extends BaseActivity implements SearchListener {

    public static final String HASHTAG_PREFIX = "#";
    @Inject
    SearchPresenter mSearchPresenter;

    @BindView(R.id.search_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.search_tweets)
    RecyclerView mSearchTweets;

    @BindView(R.id.search_error)
    TextView mSearchError;

    @BindView(R.id.search_try_again)
    TextView mSearchTryAgain;

    private String mHashtagSearch;

    private TimelineAdapter mTimelineAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        DaggerSearchComponent
                .builder()
                .build()
                .inject(this);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mHashtagSearch = intent.getStringExtra(SearchManager.QUERY);
            if (!mHashtagSearch.startsWith(HASHTAG_PREFIX)) {
                mHashtagSearch = HASHTAG_PREFIX + mHashtagSearch;
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(mHashtagSearch);
            bar.setDisplayHomeAsUpEnabled(true);
        }

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSearchTweets.setLayoutManager(lm);

        mTimelineAdapter = new TimelineAdapter();
        mTimelineAdapter.getViewClickSubject().subscribe(this::showTweetImage);
        mSearchTweets.setAdapter(mTimelineAdapter);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);

        mSearchPresenter.init(this);
        mSearchPresenter.searchTwitterWithHashtag(mHashtagSearch);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSearchResult(List<Tweet> tweets) {
        mSwipeRefreshLayout.setRefreshing(false);

        if (tweets != null && !tweets.isEmpty()) {
            List<Object> tweet = new ArrayList<>(tweets);
            mTimelineAdapter.setItems(tweet);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void showErrorMessage() {
        mSwipeRefreshLayout.setRefreshing(false);

        mSearchError.setVisibility(View.VISIBLE);
        mSearchTryAgain.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.search_try_again)
    public void onTryAgainClick() {
        mSearchError.setVisibility(View.GONE);
        mSearchTryAgain.setVisibility(View.GONE);

        mSwipeRefreshLayout.setRefreshing(true);
        mSearchPresenter.searchTwitterWithHashtag(mHashtagSearch);
    }

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

}
