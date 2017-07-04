package com.mera.mysimpletwitter.core.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ilija.tomic on 7/3/2017.
 * DB entity that map and save {@link com.twitter.sdk.android.core.models.Tweet} in database
 */
public class TweetEntity extends RealmObject {

    @PrimaryKey
    private String id;
    private String userName;
    private String userScreenName;
    private String userProfileImgUrl;
    private String createdAt;
    private String text;
    private String mediaUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public String getUserProfileImgUrl() {
        return userProfileImgUrl;
    }

    public void setUserProfileImgUrl(String userProfileImgUrl) {
        this.userProfileImgUrl = userProfileImgUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

}
