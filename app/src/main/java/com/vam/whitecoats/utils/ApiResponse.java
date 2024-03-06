package com.vam.whitecoats.utils;

import java.util.List;

public class ApiResponse<T> {
    public List<T> objList;
    private String error;

    public ApiResponse(List<T> posts) {
        this.objList = posts;
        this.error = null;
    }

    public ApiResponse(String error) {
        this.error = error;
        this.objList = null;
    }

    public List<T> getObjList() {
        return objList;
    }

    public void setObjList(List<T> objList) {
        this.objList = objList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
