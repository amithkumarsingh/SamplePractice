package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.CategoryFeeds;

import java.util.List;

public class FeedsApiResponse {

    public List<CategoryFeeds> feeds;
    private String error;

    public FeedsApiResponse(List<CategoryFeeds> posts) {
        this.feeds = posts;
        this.error = null;
    }

    public FeedsApiResponse(String error) {
        this.error = error;
        this.feeds = null;
    }

    public List<CategoryFeeds> getSubCategoryFeeds() {
        return feeds;
    }

    public void setCategories(List<CategoryFeeds> categories) {
        this.feeds = categories;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
