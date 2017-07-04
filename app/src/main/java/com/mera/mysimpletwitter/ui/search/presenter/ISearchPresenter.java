package com.mera.mysimpletwitter.ui.search.presenter;

import com.mera.mysimpletwitter.ui.search.view.SearchListener;

/**
 * Created by ilija.tomic on 7/4/2017.
 * Signature interface for {@link SearchPresenter}
 */
public interface ISearchPresenter {

    /**
     * Init presenter with activity listener
     * @param searchListener listener from {@link com.mera.mysimpletwitter.ui.search.view.SearchActivity}
     */
    void init(SearchListener searchListener);

    /**
     * Perform search tweets with given #hashtag
     *
     * @param hashtag hashtag to search by
     */
    void searchTwitterWithHashtag(String hashtag);
}
