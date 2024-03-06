package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.FeedsBySpecInfo;

import java.util.List;

public class FeedsBySpecApiResponse {
    public List<FeedsBySpecInfo> feeds;
    private String error;

    public FeedsBySpecApiResponse(List<FeedsBySpecInfo> posts) {
        this.feeds = posts;
        this.error = null;
    }

    public FeedsBySpecApiResponse(String error) {
        this.error = error;
        this.feeds = null;
    }

    public List<FeedsBySpecInfo> getFeedsBySpec() {
        return feeds;
    }

    public void setCategories(List<FeedsBySpecInfo> categories) {
        this.feeds = categories;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
