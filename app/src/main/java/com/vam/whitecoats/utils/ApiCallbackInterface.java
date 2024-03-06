package com.vam.whitecoats.utils;

public interface ApiCallbackInterface {
    void onSuccessResponse(String response);
    void onErrorResponse(String error);
}
