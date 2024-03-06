package com.vam.whitecoats.core.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by venuv on 5/31/2017.
 */

public class RealmFeedInfo extends RealmObject {
    @Required
    @PrimaryKey
    private String feedChannelId;
    private int feedId;
    private long createdOrUpdatedTime;
    private String feedsJson;
    private int channelId;
    private int docId;

    public void setFeedsJson(String feedString) {
        feedsJson = feedString;
    }

    public String getFeedsJson() {
        return feedsJson;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public long getCreatedOrUpdatedTime() {
        return createdOrUpdatedTime;
    }

    public void setCreatedOrUpdatedTime(long time) {
        this.createdOrUpdatedTime = time;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getFeedChannelId() {
        return feedChannelId;
    }

    public void setFeedChannelId(String feedChannelId) {
        this.feedChannelId = feedChannelId;
    }
}
