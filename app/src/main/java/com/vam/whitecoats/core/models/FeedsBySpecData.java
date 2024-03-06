package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedsBySpecData {
    @SerializedName("feed_data")
    @Expose
    private List<FeedsBySpecInfo> feedData = null;

    public List<FeedsBySpecInfo> getFeedData() {
        return feedData;
    }

    public void setFeedData(List<FeedsBySpecInfo> feedData) {
        this.feedData = feedData;
    }
}
