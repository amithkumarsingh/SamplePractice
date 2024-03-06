package com.vam.whitecoats.ui.interfaces;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public interface OnDataLoadWithList {
    void onDataLoadWithList(ArrayList<JSONObject> list, boolean is_display_user_preferred_channel, int feedNotificationsCount, JSONArray communityList, SparseArray<JSONObject> channelsList, int networkChannelId,boolean isFromPreData);
}
