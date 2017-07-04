package com.mera.mysimpletwitter.ui.search.presenter;

import com.mera.mysimpletwitter.ui.search.model.SearchModel;
import com.mera.mysimpletwitter.ui.search.view.SearchListener;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;

/**
 * Created by ilija.tomic on 7/4/2017.
 * Presenter for {@link com.mera.mysimpletwitter.ui.search.view.SearchActivity}
 */
public class SearchPresenter implements ISearchPresenter {

    private final SearchModel mSearchModel;
    private final Scheduler mScheduler;

    private SearchListener mSearchListener;
    private List<Tweet> mSearchTweets = new ArrayList<>();

    public SearchPresenter(SearchModel searchModel, Scheduler scheduler) {
        this.mSearchModel = searchModel;
        this.mScheduler = scheduler;
    }

    @Override
    public void init(SearchListener searchListener) {
        this.mSearchListener = searchListener;
    }

    @Override
    public void searchTwitterWithHashtag(String hashtag) {
        mSearchModel.getTweetsByHashtag(hashtag)
                .observeOn(mScheduler)
                .subscribe(searchTweets -> {
                    this.mSearchTweets = searchTweets;
                    if (mSearchListener != null) {
                        mSearchListener.showSearchResult(mSearchTweets);
                    }
                }, throwable -> {
                    if (mSearchListener != null) {
                        mSearchListener.showErrorMessage();
                    }
                });
    }
}
