package com.vam.whitecoats.ui.interfaces;

import org.json.JSONObject;

/**
 * Created by venuv on 6/9/2017.
 */

public interface UiUpdateListener {

    void updateUI(int feedId,JSONObject socialInteractionObj);

    void notifyUIWithNewData(JSONObject newUpdate);

    void notifyUIWithDeleteFeed(int feedId,JSONObject deletedFeedObj);
    void onBookmark(boolean isBookmarked,int feedID,boolean isAutoRefresh,JSONObject socialInteractionObj);

    void notifyUIWithFeedSurveyResponse(int feedId,JSONObject surveyResponse);

    void notifyUIWithFeedWebinarResponse(int feedId, JSONObject webinarRegisterResponse);

    void notifyUIWithJobApplyStatus(int feedId,JSONObject jobApplyResponse);
}
