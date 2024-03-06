package com.vam.whitecoats.ui.interfaces;

import org.json.JSONObject;

/**
 * Created by venuv on 11/18/2016.
 */

public interface OnSocialInteractionListener {

    void onSocialInteraction(JSONObject subItem, int channel_id, Boolean isLiked,int mFeedTypeId);

    void onUIupdateForLike(JSONObject subItem, int channel_id, Boolean isLiked,int mFeedTypeId);

    void onReportSpam(String clickOnSpam, int feedId, int docId);
}
