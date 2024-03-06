package com.vam.whitecoats.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vam.whitecoats.ui.fragments.AboutJobRoleFragment;
import com.vam.whitecoats.ui.fragments.ApplyJobFragment;
import com.vam.whitecoats.ui.fragments.JobOrganizationFragment;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class JobInfoTabAdapter extends FragmentStateAdapter {
    private JSONObject feedInfoObj;
    private int userId;
    private String feedDataString;
    private AboutJobRoleFragment aboutJobRoleFragment;
    private JobOrganizationFragment aboutOrganizationFragement;
    private ApplyJobFragment applyJobFragment;
    private JSONObject completeFeedObj;
    private Boolean display_status;
    private int itemCount;

    public JobInfoTabAdapter(@NonNull FragmentActivity fragmentActivity, String feedData,
                             int _userId, Boolean display_status, int itemCount) {
        super(fragmentActivity);
        this.feedDataString = feedData;
        this.userId = _userId;
        this.display_status = display_status;
        this.itemCount=itemCount;
        try {
            completeFeedObj = new JSONObject(feedData);
            feedInfoObj = completeFeedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                aboutJobRoleFragment = AboutJobRoleFragment.newInstance(feedDataString, userId);
                return aboutJobRoleFragment;
            case 1:
                /*Checking the display_status flag to show/hide the organization tab in the screen*/
                if (display_status || (!feedInfoObj.isNull("about_us") && !feedInfoObj.optString("about_us").isEmpty())) {
                    aboutOrganizationFragement = JobOrganizationFragment.newInstance(feedDataString, userId);
                    return aboutOrganizationFragement;
                } else {
                    applyJobFragment = ApplyJobFragment.newInstance(feedDataString, userId);
                    return applyJobFragment;
                }
            default:
                applyJobFragment = ApplyJobFragment.newInstance(feedDataString, userId);
                return applyJobFragment;
        }
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public void updateLatestSocialInteractionData(JSONObject socialInteractionObj) {
        try {
            completeFeedObj.optJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
            feedDataString = completeFeedObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (aboutJobRoleFragment != null) {
            aboutJobRoleFragment.setLatestSocialInteractionData(socialInteractionObj);
        }
        if (aboutOrganizationFragement != null) {
            aboutOrganizationFragement.setLatestSocialInteractionData(socialInteractionObj);
        }
        if (applyJobFragment != null) {
            applyJobFragment.setLatestSocialInteractionData(socialInteractionObj);
        }

    }

}
