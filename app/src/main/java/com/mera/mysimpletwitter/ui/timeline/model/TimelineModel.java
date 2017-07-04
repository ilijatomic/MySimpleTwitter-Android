package com.mera.mysimpletwitter.ui.timeline.model;

import com.mera.mysimpletwitter.core.db.TweetEntity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * Created by ikac on 7/2/17.
 */

public class TimelineModel extends TwitterApiClient implements ITimelineModel {

    /**
     * Passing active session to {@link TwitterApiClient}
     *
     * @param session active user session
     */
    public TimelineModel(TwitterSession session) {
        super(session);
    }

    @Override
    public Observable<List<Tweet>> getTimelineTweets(Integer count) {
        return Observable.create(subscriber -> {
            Callback<List<Tweet>> callback = new Callback<List<Tweet>>() {
                @Override
                public void success(Result<List<Tweet>> result) {
                    subscriber.onNext(result.data);
                }

                @Override
                public void failure(TwitterException exception) {
                    subscriber.onError(exception);
                }
            };

            getStatusesService().homeTimeline(count, null, null, null, null, null, null).enqueue(callback);
        });
    }

    @Override
    public Observable<Boolean> saveTimelineTweets(List<Tweet> tweets) {
        return Observable.create(subscriber -> {
            Realm realm = Realm.getDefaultInstance();

            for (Tweet tweet : tweets) {
                realm.beginTransaction();
                final TweetEntity tweetEntity = new TweetEntity();
                tweetEntity.setId(tweet.idStr);
                tweetEntity.setUserName(tweet.user.name);
                tweetEntity.setUserScreenName(tweet.user.screenName);
                tweetEntity.setUserProfileImgUrl(tweet.user.profileImageUrl);
                tweetEntity.setCreatedAt(tweet.createdAt);
                tweetEntity.setText(tweet.text);
                if (tweet.entities.media != null) {
                    tweetEntity.setMediaUrl(tweet.entities.media.get(0).mediaUrl);
                }
                realm.copyToRealmOrUpdate(tweetEntity);
                realm.commitTransaction();
            }

            realm.close();

            subscriber.onNext(true);
        });
    }

    @Override
    public Observable<List<TweetEntity>> loadTimelineTweets() {
        return Observable.create(subscriber -> {
            Realm realm = Realm.getDefaultInstance();
            List<TweetEntity> tweetEntities = realm.where(TweetEntity.class).findAll();
            subscriber.onNext(tweetEntities);
        });
    }


}
