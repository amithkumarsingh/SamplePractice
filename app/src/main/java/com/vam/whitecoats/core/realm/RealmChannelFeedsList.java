package com.vam.whitecoats.core.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmChannelFeedsList extends RealmObject {

    @PrimaryKey
    private int feed_Id;
    private RealmList<RealmChannelFeedInfo> feedsList;

    public RealmList<RealmChannelFeedInfo> getFeedsList() {
        return feedsList;
    }

    public void setFeedsList(RealmList<RealmChannelFeedInfo> feedsList) {
        this.feedsList = feedsList;
    }

    public int getFeed_Id() {
        return feed_Id;
    }

    public void setFeed_Id(int feed_Id) {
        this.feed_Id = feed_Id;
    }
}
