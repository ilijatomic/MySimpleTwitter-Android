package com.mera.mysimpletwitter.ui.search.view;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by ilija.tomic on 7/4/2017.
 * Listener for {@link SearchActivity}
 */
public interface SearchListener {

    /**
     * Display search results from TwitterAPI
     *
     * @param tweets list of tweets from search result
     */
    void showSearchResult(List<Tweet> tweets);

    /**
     * Show error message and try again
     */
    void showErrorMessage();
}
