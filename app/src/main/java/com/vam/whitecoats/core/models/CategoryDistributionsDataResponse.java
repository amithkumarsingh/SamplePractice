package com.vam.whitecoats.core.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryDistributionsDataResponse implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private CategoryDistributionsData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CategoryDistributionsData getData() {
        return data;
    }

    public void setData(CategoryDistributionsData data) {
        this.data = data;
    }
}
