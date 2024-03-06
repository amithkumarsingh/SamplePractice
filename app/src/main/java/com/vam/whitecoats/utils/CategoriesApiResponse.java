package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.NotificationInfo;

import java.util.List;

public class CategoriesApiResponse {

    public List<Category> categories;
    private String error;

    public CategoriesApiResponse(List<Category> posts) {
        this.categories = posts;
        this.error = null;
    }

    public CategoriesApiResponse(String error) {
        this.error = error;
        this.categories = null;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
