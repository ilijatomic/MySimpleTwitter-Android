package com.mera.mysimpletwitter.ui.search.model;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ilija.tomic on 7/4/2017.
 * Model for {@link com.mera.mysimpletwitter.ui.search.presenter.SearchPresenter}
 */
public class SearchModel extends TwitterApiClient implements ISearchModel {

    private static final Integer DEFAULT_COUNT_SIZE = 100;

    public SearchModel(TwitterSession session) {
        super(session);
    }

    @Override
    public Observable<List<Tweet>> getTweetsByHashtag(String hashtag) {
        return Observable.create(subscriber -> {
            Callback<Search> callback = new Callback<Search>() {
                @Override
                public void success(Result<Search> result) {
                    subscriber.onNext(result.data.tweets);
                }

                @Override
                public void failure(TwitterException exception) {
                    subscriber.onError(exception);
                }
            };

            getSearchService().tweets(hashtag, null, null, null, null, DEFAULT_COUNT_SIZE, null, null, null, null).enqueue(callback);
        });
    }
}
