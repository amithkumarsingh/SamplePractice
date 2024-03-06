package com.vam.whitecoats.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.ChannelSelectionActivity;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.activities.MCACardUploadActivity;
import com.vam.whitecoats.ui.activities.NetworkSearchActivity;
import com.vam.whitecoats.ui.activities.PreferencesActivity;
import com.vam.whitecoats.ui.activities.ProfileViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class NavigationUtils {

    public NavigationUtils(Context mContext, Map<String, Object> map) {
        RealmManager realmManager=new RealmManager(mContext);
        Realm realm=Realm.getDefaultInstance();
        int docID = realmManager.getDoc_id(realm);
        String deeplinkValue = (String) map.get("deepLink");
        if (deeplinkValue != null && !deeplinkValue.isEmpty()) {
            try {
                boolean needToNavigate=true;
                JSONObject navigationObj = new JSONObject(deeplinkValue);
                AppUtil.logUserActionEvent(docID,"deeplinkNavigation",navigationObj,AppUtil.convertJsonToHashMap(navigationObj),mContext);
                if (navigationObj.has("navigation_url")) {
                    AppUtil.handleSendText(mContext,docID, navigationObj.optString("navigation_url"), RestApiConstants.GET_SHORTURL_DATA, RestUtils.TAG_SHORT_URL);
                } else if (navigationObj.has("navigation_path")) {
                    Intent in = new Intent();
                    int navigation_path = navigationObj.optInt("navigation_path");
                    if (navigation_path == NavigationType.MY_PROFILE.getNavigationValue()) {
                        in.setClass(mContext, ProfileViewActivity.class);
                    } else if (navigation_path == NavigationType.VERIFY_PROFILE.getNavigationValue()) {
                        if(AppUtil.getUserVerifiedStatus() == 1) {
                            in.setClass(mContext, MCACardUploadActivity.class);
                        }else if(AppUtil.getUserVerifiedStatus()==2){
                            needToNavigate=false;
                            Toast.makeText(mContext,mContext.getString(R.string.mca_uploaded_but_not_verified),Toast.LENGTH_SHORT).show();
                        }else if(AppUtil.getUserVerifiedStatus()==3){
                            needToNavigate=false;
                            Toast.makeText(mContext,mContext.getString(R.string.mca_verified),Toast.LENGTH_SHORT).show();
                        }
                    } else if (navigation_path == NavigationType.CHANNEL_TAB.getNavigationValue()) {
                       /* in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.CHANNEL_TAB.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);*/
                    } else if (navigation_path == NavigationType.MY_PREFERENCE.getNavigationValue()) {
                        in.setClass(mContext, PreferencesActivity.class);
                    } else if (navigation_path == NavigationType.SEARCH_LANDING.getNavigationValue()) {
                        in.setClass(mContext, NetworkSearchActivity.class);
                    } else if (navigation_path == NavigationType.SHARE_UPDATE_OR_CASE.getNavigationValue()) {
                        in.putExtra("post_btn_tap",true);
                        in.setClass(mContext, DashboardActivity.class);
                    } else if (navigation_path == NavigationType.COMMUNITY_DOCTORS.getNavigationValue() || navigation_path== NavigationType.INVITE_TO_WHITECOATS.getNavigationValue()) {
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.COMMUNITY_DOCTORS.getNavigationValue());
                        if(navigation_path == NavigationType.INVITE_TO_WHITECOATS.getNavigationValue()){
                            in.putExtra("Is_InviteToWhitecoats",true);
                        }
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.NOTIFICATION_PREFERENCE.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.NOTIFICATION_PREFERENCE.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);

                    }else if(navigation_path == NavigationType.NOTIFICATION_TAB.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.NOTIFICATION_TAB.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);

                    }else if(navigation_path == NavigationType.KNOWLEDGE_FEEDS.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.KNOWLEDGE_FEEDS.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.KNOWLEDGE_DRUG_REFERENCES.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.KNOWLEDGE_DRUG_REFERENCES.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.KNOWLEDGE_MEDICAL_EVENTS.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.KNOWLEDGE_MEDICAL_EVENTS.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.COMMUNITY_SPOTLIGHT.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.COMMUNITY_SPOTLIGHT.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.COMMUNITY_FEEDS.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.COMMUNITY_FEEDS.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.COMMUNITY_ORGANIZATION.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.COMMUNITY_ORGANIZATION.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.PROFESSIONAL_FEEDS.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.PROFESSIONAL_FEEDS.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.PROFESSIONAL_SKILLING.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.PROFESSIONAL_SKILLING.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.PROFESSIONAL_OPPORTUNITIES.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.PROFESSIONAL_OPPORTUNITIES.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.PROFESSIONAL_PARTNERS.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.PROFESSIONAL_PARTNERS.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.TRENDING_CATEGORY.getNavigationValue()){
                        in.setFlags(  Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("Navigation_Tab",NavigationType.TRENDING_CATEGORY.getNavigationValue());
                        in.setClass(mContext, DashboardActivity.class);
                    }else if(navigation_path == NavigationType.NOTIFICATION_ENABLE_POPUP.getNavigationValue()){
                        in.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra(Settings.EXTRA_APP_PACKAGE, mContext.getPackageName());
                    }
                    else{
                        needToNavigate=false;
                    }
                    if(needToNavigate) {
                        mContext.startActivity(in);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
