package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.DrugClass;

import java.util.ArrayList;
import java.util.List;

public class DrugFeedsApiResponse {
    public ArrayList<CategoryFeeds> drugClasses;
    private String error;

    public DrugFeedsApiResponse(ArrayList<CategoryFeeds> posts) {
        this.drugClasses = posts;
        this.error = null;
    }

    public DrugFeedsApiResponse(String error) {
        this.error = error;
        this.drugClasses = null;
    }

    public ArrayList<CategoryFeeds> getFeeds() {
        return drugClasses;
    }

    public void setFeeds(ArrayList<CategoryFeeds> drugClassList) {
        this.drugClasses = drugClassList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
