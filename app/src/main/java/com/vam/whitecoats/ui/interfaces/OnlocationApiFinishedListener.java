package com.vam.whitecoats.ui.interfaces;

import org.json.JSONObject;

import java.util.HashMap;

public interface OnlocationApiFinishedListener {
    void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap,  JSONObject jsonObject);
    void onLocationApiError(String error);
}
