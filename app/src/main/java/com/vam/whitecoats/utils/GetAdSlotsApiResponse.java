package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.models.AdsDefinition;

import java.util.ArrayList;

public class GetAdSlotsApiResponse {

    public ArrayList<AdsDefinition> adsDefinitions;
    private String error;

    public GetAdSlotsApiResponse(ArrayList<AdsDefinition> adsDefinitions) {
        this.adsDefinitions = adsDefinitions;
        this.error = null;
    }

    public GetAdSlotsApiResponse(String error) {
        this.error = error;
        this.adsDefinitions = null;
    }

    public ArrayList<AdsDefinition> getAdDefinitions() {
        return adsDefinitions;
    }

    public void setFeeds(ArrayList<AdsDefinition> adsDefinitions) {
        this.adsDefinitions = adsDefinitions;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
