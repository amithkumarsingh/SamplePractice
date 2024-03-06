package com.vam.whitecoats.ui.interfaces;

import android.view.View;

import org.json.JSONObject;

/**
 * Created by venuv on 3/21/2018.
 */

public interface OnFeedItemClickListener {
    void onItemClickListener(JSONObject feedObj, boolean isNetworkChannel, int channelId, String channelName, View sharedView, int selectedPosition);
}
