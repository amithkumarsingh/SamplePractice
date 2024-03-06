package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.Category;

import java.util.List;

public class CategoriesDistributionApiResponse {
    public boolean isSuccess() {
        return isSuccess;
    }

    private  boolean isSuccess;
    private String success;



    public CategoriesDistributionApiResponse(String response,boolean _isSuccess) {
        this.success = response;
        this.isSuccess=_isSuccess;
    }
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
