package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.SpecialitiesInfo;

import java.util.List;

public class CatDistributionApiResponse {
    public List<SpecialitiesInfo> specialitiesInfos;
    private String error;

    public CatDistributionApiResponse(List<SpecialitiesInfo> posts) {
        this.specialitiesInfos = posts;
        this.error = null;
    }

    public CatDistributionApiResponse(String error) {
        this.error = error;
        this.specialitiesInfos = null;
    }

    public List<SpecialitiesInfo> getSpecialitiesInfos() {
        return specialitiesInfos;
    }

    public void setCategories(List<SpecialitiesInfo> categories) {
        this.specialitiesInfos = categories;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
