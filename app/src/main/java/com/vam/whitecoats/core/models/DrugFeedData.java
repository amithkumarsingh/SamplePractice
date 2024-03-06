package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugFeedData {
    @SerializedName("feed_data")
    @Expose
    private List<CategoryFeeds> feedData = null;

    public List<CategoryFeeds> getFeedData() {
        return feedData;
    }

    public void setFeedData(List<CategoryFeeds> feedData) {
        this.feedData = feedData;
    }
}
