package com.vam.whitecoats.viewmodel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.vam.whitecoats.databinding.UserContentRes;

import org.json.JSONObject;

public class UserReviewViewModel extends ViewModel {
    private final UserContentRes userContentRes = new UserContentRes();

    public LiveData<String> getUserContentReview(Activity activity, JSONObject resObj) {
        return userContentRes.getUserReviewPost(activity,resObj);
    }
}
