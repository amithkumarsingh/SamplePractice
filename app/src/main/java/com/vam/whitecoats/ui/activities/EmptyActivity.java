package com.vam.whitecoats.ui.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.fragments.ChannelsFragment;
import com.vam.whitecoats.ui.fragments.ContentChannelsFragment;
import com.vam.whitecoats.ui.fragments.DashboardUpdatesFragment;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class EmptyActivity extends BaseActionBarActivity {
    private static final String TAG = EmptyActivity.class.getSimpleName();
    private TextView delete_errormsg, delete_dashboard;
    private ProgressBar progressBar;
    JSONObject feedData = null;
    private RealmManager realmManager;
    private Realm realm;
    private int doctorId;
    public boolean isRunning;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        setContentView(R.layout.activity_empty);
        bundle = getIntent().getExtras();
        Log.d(TAG, "Bundle - " + bundle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        delete_dashboard = _findViewById(R.id.deleteerrortitle);

        delete_errormsg = _findViewById(R.id.deletebtngotodashboard);
        progressBar = _findViewById(R.id.simpleProgressBar);

        if (bundle != null) {
            try {
                JSONObject reqObject = null;
                if (bundle.containsKey(RestUtils.KEY_REQUEST_BUNDLE)) {
                    reqObject = new JSONObject(bundle.getString(RestUtils.KEY_REQUEST_BUNDLE));
                    requestFeedFullView(reqObject, RestApiConstants.FEED_FULL_VIEW_UPDATED);
                } else if (bundle.containsKey(RestUtils.TAG_SHARED_FEED_DATA)) {
                    reqObject = new JSONObject(bundle.getString(RestUtils.TAG_SHARED_FEED_DATA));
                    String restAPI_URL = RestApiConstants.VIEW_SHARED_FEED;
                    if (bundle.containsKey(RestUtils.TAG_REST_API_URL)) {
                        restAPI_URL = bundle.getString(RestUtils.TAG_REST_API_URL);
                    }
                    requestFeedFullView(reqObject, restAPI_URL);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        delete_errormsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboardintent = new Intent(EmptyActivity.this, DashboardActivity.class);
                dashboardintent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                startActivity(dashboardintent);
            }
        });

        doctorId = realmManager.getDoc_id(realm);

    }

    @Override
    protected void setCurrentActivity() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        App_Application.getInstance().cancelPendingRequests("EMPTYACTIVITY_TAG");
        if (isTaskRoot()) {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
            startActivity(intent);
        }
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
    }

    private void requestFeedFullView(final JSONObject request,String restURL) {
        Log.i(TAG, "requestFeedFullView()");
        showProgressBar();
        new VolleySinglePartStringRequest(EmptyActivity.this, Request.Method.POST, restURL, request.toString(), "EMPTYACTIVITY_TAG", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                hideProgressBar();
                String navigationFlowText = "FullView";
                try {
                    feedData = new JSONObject(successResponse);
                    if ((feedData.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS))) {
                        Intent intent = null;
                        JSONObject completeFeedObj = feedData.optJSONObject(RestUtils.TAG_DATA);
                        if (completeFeedObj.has(RestUtils.TAG_NAVIGATION_TYPE)) {
                            navigationFlowText = completeFeedObj.optString(RestUtils.TAG_NAVIGATION_TYPE);
                        }
                        if(navigationFlowText.equalsIgnoreCase("deep_link")){
                            String tagId=completeFeedObj.optString(RestUtils.TAG_TAG_ID);
                            String c_msg_type=completeFeedObj.optString(RestUtils.TAG_C_MSG_TYPE);
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put(RestUtils.NOTIFICATION_TAG_ID, tagId);
                                jsonObject.put(RestUtils.TAG_C_MSG_TYPE, c_msg_type);
                                jsonObject.put("deeplinkingStack",true);
                                Intent deeplinkIntent = new Intent(EmptyActivity.this, DashboardActivity.class);
                                deeplinkIntent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, jsonObject.toString());
                                deeplinkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(deeplinkIntent);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else if (navigationFlowText.equalsIgnoreCase("FullView")) {
                            JSONObject feedObject = completeFeedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
                            String channelType = feedObject.optString(RestUtils.FEED_TYPE);
                            String channelName = completeFeedObj.optString(RestUtils.FEED_PROVIDER_NAME);
                            String feedProviderType = completeFeedObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
                            int channelId = completeFeedObj.optInt(RestUtils.CHANNEL_ID);
                            if (channelType.equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                                intent = new Intent(EmptyActivity.this, ContentFullView.class);
                                intent.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelName);
                                intent.putExtra(RestUtils.CHANNEL_ID, channelId);
                                intent.putExtra(RestUtils.TAG_CONTENT_OBJECT, completeFeedObj.toString());
                                intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                            } else if(channelType.equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)){
                                intent = new Intent(EmptyActivity.this, JobFeedCompleteView.class);
                                intent.putExtra(RestUtils.CHANNEL_ID, channelId);
                                if (feedProviderType.equalsIgnoreCase("Network")) {
                                    intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, true);
                                } else {
                                    intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
                                }
                                intent.putExtra(RestUtils.CHANNEL_NAME, channelName);
                                intent.putExtra(RestUtils.TAG_FEED_OBJECT, completeFeedObj.toString());
                                intent.putExtra(RestUtils.TAG_POSITION, 0);
                                intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                            }
                            else {
                                intent = new Intent(EmptyActivity.this, FeedsSummary.class);
                                intent.putExtra(RestUtils.CHANNEL_ID, channelId);
                                if (feedProviderType.equalsIgnoreCase("Network")) {
                                    intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, true);
                                } else {
                                    intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
                                }
                                intent.putExtra(RestUtils.CHANNEL_NAME, channelName);
                                intent.putExtra(RestUtils.TAG_FEED_OBJECT, completeFeedObj.toString());
                                intent.putExtra(RestUtils.TAG_POSITION, 0);
                                intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                            }
                            intent.putExtra(RestUtils.TAG_TYPE, request.optString(RestUtils.TAG_TYPE));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            feedObject.put(RestUtils.FEED_TYPE_ID, request.optInt(RestUtils.FEED_ID));
                            startActivity(intent);
                            finish();
                        } else if (navigationFlowText.equalsIgnoreCase("ChangeMCICard")) {
                            Intent intent1 = new Intent(EmptyActivity.this, MCACardUploadActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent1.putExtra("NAVIGATION", "fromLink");
                            startActivity(intent1);
                            finish();
                        } else if (navigationFlowText.equalsIgnoreCase("UserProfile")) {
                            if (completeFeedObj.has(RestUtils.TAG_OTHER_USER_ID)) {
                                Intent intent1 = new Intent();
                                if (realmManager.getDoc_id(realm) == completeFeedObj.optInt(RestUtils.TAG_OTHER_USER_ID)) {
                                    intent1.setClass(EmptyActivity.this, ProfileViewActivity.class);
                                } else {
                                    intent1.setClass(EmptyActivity.this, VisitOtherProfile.class);
                                }
                                intent1.putExtra(RestUtils.TAG_DOC_ID, completeFeedObj.optInt(RestUtils.TAG_OTHER_USER_ID));
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                                finish();
                            } else {
                                Intent intent2 = new Intent(EmptyActivity.this, DashboardActivity.class);
                                intent2.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent2);
                                finish();
                            }
                        } else {
                            Intent intent2 = new Intent(EmptyActivity.this, DashboardActivity.class);
                            intent2.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent2);
                            finish();
                        }
                    } else if (feedData.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        /**
                         * Display alert
                         */
                        if(feedData.optInt(RestUtils.TAG_ERROR_CODE)==4044){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(EmptyActivity.this);
                            builder1.setMessage(feedData.optString(RestUtils.TAG_ERROR_MESSAGE));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Follow",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (isConnectingToInternet()) {

                                                //boolean isSubscribedValue = !channel.getIsSubscribed();
                                                dialog.dismiss();
                                                JSONObject subscribeJsonRequest = new JSONObject();
                                                try {
                                                    subscribeJsonRequest.put(RestUtils.TAG_USER_ID, doctorId);
                                                    subscribeJsonRequest.put(RestUtils.CHANNEL_ID, feedData.optInt(RestUtils.CHANNEL_ID));
                                                    subscribeJsonRequest.put(RestUtils.TAG_IS_SUBSCRIBED, true);
                                                    showProgressBar();
                                                    new VolleySinglePartStringRequest(EmptyActivity.this, Request.Method.POST, RestApiConstants.SUBSCRIPTION_SERVICE, subscribeJsonRequest.toString(), "SUBSCRIPTION_SERVICE", new OnReceiveResponse() {
                                                        @Override
                                                        public void onSuccessResponse(String successResponse) {
                                                            hideProgressBar();
                                                            DashboardUpdatesFragment.dashboardRefreshOnSubscription = true;
                                                            ContentChannelsFragment.refreshChannelsOnSubscription = true;
                                                            if (bundle != null) {
                                                                try {
                                                                    JSONObject reqObject = null;
                                                                    if (bundle.containsKey(RestUtils.KEY_REQUEST_BUNDLE)) {
                                                                        reqObject = new JSONObject(bundle.getString(RestUtils.KEY_REQUEST_BUNDLE));
                                                                        requestFeedFullView(reqObject, RestApiConstants.FEED_FULL_VIEW_UPDATED);
                                                                    } else if (bundle.containsKey(RestUtils.TAG_SHARED_FEED_DATA)) {
                                                                        reqObject = new JSONObject(bundle.getString(RestUtils.TAG_SHARED_FEED_DATA));
                                                                        String restAPI_URL = RestApiConstants.VIEW_SHARED_FEED;
                                                                        if (bundle.containsKey(RestUtils.TAG_REST_API_URL)) {
                                                                            restAPI_URL = bundle.getString(RestUtils.TAG_REST_API_URL);
                                                                        }
                                                                        requestFeedFullView(reqObject, restAPI_URL);
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onErrorResponse(String errorResponse) {
                                                            hideProgressBar();

                                                            if (errorResponse != null) {
                                                                try {
                                                                    if (!errorResponse.isEmpty()) {
                                                                        JSONObject jsonObject = new JSONObject(errorResponse);
                                                                        String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                                        if (errorMessage != null && !errorMessage.isEmpty()) {
                                                                            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }
                                                    }).sendSinglePartRequest();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            } else {
                                                Toast.makeText(mContext, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            builder1.setNegativeButton(
                                    "Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                            Intent intent = new Intent(EmptyActivity.this, DashboardActivity.class);
                                            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            alert11.setCanceledOnTouchOutside(false);
                        }
                        else if(feedData.optInt(RestUtils.TAG_ERROR_CODE)==5055) {
                            delete_errormsg.setVisibility(View.VISIBLE);
                            delete_dashboard.setVisibility(View.VISIBLE);
                            delete_dashboard.setText(R.string.delete_message);

                        }
                        else if(feedData.optInt(RestUtils.TAG_ERROR_CODE)==5058) {
                            delete_errormsg.setVisibility(View.VISIBLE);
                            delete_dashboard.setVisibility(View.VISIBLE);
                            delete_dashboard.setText(feedData.optString(RestUtils.TAG_ERROR_MESSAGE));
                        }
                        else{
                            String errorMsg="Something went wrong,please try after sometime.";
                            if(feedData.has(RestUtils.TAG_ERROR_MESSAGE)){
                                errorMsg=feedData.optString(RestUtils.TAG_ERROR_MESSAGE);
                            }
                            Toast.makeText(mContext,errorMsg,Toast.LENGTH_SHORT).show();
                            if (isTaskRoot()) {
                                Intent intent = new Intent(EmptyActivity.this, DashboardActivity.class);
                                intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }

                } catch (JSONException e) {
                    hideProgressBar();
                    finish();
                    e.printStackTrace();
                }
            }
            @Override
            public void onErrorResponse(String errorResponse) {
                hideProgressBar();
                Intent intent = new Intent(EmptyActivity.this, DashboardActivity.class);
                intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).sendSinglePartRequest();


    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);

    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);

    }
}
