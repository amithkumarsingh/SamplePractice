package com.vam.whitecoats.core.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by venuv on 5/31/2017.
 */

public class RealmFeedsList extends RealmObject {
    @PrimaryKey
    private int feed_Id;
    private RealmList<RealmFeedInfo> feedsList;

    public RealmList<RealmFeedInfo> getFeedsList() {
        return feedsList;
    }

    public void setFeedsList(RealmList<RealmFeedInfo> feedsList) {
        this.feedsList = feedsList;
    }

    public void clearFeedsList(){
        if(feedsList!=null){
            feedsList.clear();
        }
    }

    public int getFeed_Id() {
        return feed_Id;
    }

    public void setFeed_Id(int feed_Id) {
        this.feed_Id = feed_Id;
    }
}
