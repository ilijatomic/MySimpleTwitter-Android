package com.mera.mysimpletwitter.ui.search.model;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ilija.tomic on 7/4/2017.
 * Signature interface for {@link SearchModel}
 */
public interface ISearchModel {

    /**
     * Perform search tweets by #hashtag on TwitterAPI
     *
     * @param hashtag   hashtag provided by user
     * @return          list of search result
     */
    Observable<List<Tweet>> getTweetsByHashtag(String hashtag);
}
