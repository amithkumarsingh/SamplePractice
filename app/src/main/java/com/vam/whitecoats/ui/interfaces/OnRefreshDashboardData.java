package com.vam.whitecoats.ui.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public interface OnRefreshDashboardData {

    void onRefresh(ArrayList<JSONObject> arrayList, JSONArray communityList, String response, JSONObject networkChannel, boolean isDashboard,boolean isPredata);
}
