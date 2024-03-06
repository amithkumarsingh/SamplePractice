package com.vam.whitecoats.core.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by venuv on 12/29/2017.
 */

public class RealmShareFailedFeedInfo extends RealmObject {
    @PrimaryKey
    private int id;
    private String feedData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedData() {
        return feedData;
    }

    public void setFeedData(String feedData) {
        this.feedData = feedData;
    }
}
