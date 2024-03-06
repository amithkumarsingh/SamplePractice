package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnHomePressedListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HomeWatcher;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class SplashScreenActivity extends Activity implements OnTaskCompleted {
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private Realm realm;
    private RealmManager realmManager;
    MySharedPref mySharedPref;
    private AlertDialog.Builder builder;
    private boolean isRememberMe;
    private boolean introslides;
    private String security_token;
    private RealmBasicInfo realmBasicInfo;
    public static Bundle bundle;
    private boolean isLikeOrCommentNotification = false;
    private boolean isPostOrArticleNotification = false;
    private boolean myProfileNotification = false;
    private boolean inviteToWhiteCoatsNotification = false;
    private boolean isUserRejectNotification = false;
    private boolean specPrefNotification = false;
    private boolean NotificationPrefTab = false;
    private boolean knowledgeFeedNotification = false;
    private boolean KnowledgeDrugRefNotification = false;
    private boolean KnowledgeMedicalEventsNotification = false;
    private boolean CommunitySpotlightNotification = false;
    private boolean CommunityFeedNotification = false;
    private boolean CommunityDoctorsNotification = false;
    private boolean CommunityOrganizationNotification = false;
    private boolean ProfessionalFeedsNotification = false;
    private boolean ProfessionalSkillingNotification = false;
    private boolean ProfessionalOppurtunitiesNotification = false;
    private boolean ProfessionalPartnersNotification = false;
    private int docID;
    String jsonResponse;
    int channelId;
    int feedTypeId;
    String tagId = null;
    String type = null;
    String c_msg_type = null;
    public ProgressDialogFragement progress;
    private String docEmailId;
    private MarshMallowPermission marshMallowPermission;
    private boolean isNavigateToNextForPermissions;
    private RealmBasicInfo basicInfo;
    private HomeWatcher mHomeWatcher;
    private boolean stackValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        App_Application.setCurrentActivity(this);
        mySharedPref = new MySharedPref(this);
        docID = realmManager.getDoc_id(realm);
        docEmailId = realmManager.getDoc_EmailId(realm);
        marshMallowPermission = new MarshMallowPermission(this);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        mHomeWatcher = new HomeWatcher(this);


        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                JSONObject jsonObject = new JSONObject();
                try {
                    AppUtil.logUserUpShotEvent("SplashScreenHomeTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onHomeLongPressed() {

            }
        });
        mHomeWatcher.startWatch();
        /*Intent intent = new Intent(this, AppKillIdentifyService.class);
        startService(intent);*/
        //startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        /*
         * when app is in background user taps on home app icon, app will be resumed .
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("X-DEVICE-ID", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (basicInfo.getDoc_id() != null) {
            AppUtil.logUserWithEventName(basicInfo.getDoc_id(), "SplashScreenImpressions", jsonObject, SplashScreenActivity.this);
        } else {
            AppUtil.logUserWithEventName(0, "SplashScreenImpressions", jsonObject, SplashScreenActivity.this);
        }
        if (!isTaskRoot() && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }
        File oldFolder = AppUtil.getExternalStoragePathFile(SplashScreenActivity.this, "Whitecoats");
        if (oldFolder.exists()) {
            File newFolder = AppUtil.getExternalStoragePathFile(SplashScreenActivity.this, ".Whitecoats");
            boolean success = oldFolder.renameTo(newFolder);
            Log.d(TAG, "Rename Folder : " + success);
        }

        /**
         * Check the Bundle params sent from GCM [ NB : If the app being started by tapping on Notifications, then there is
         * a bundle param we would be getting].
         */
        bundle = getIntent().getExtras();
        Log.d(TAG, "Bundle : " + bundle);
        isRememberMe = mySharedPref.getPref(MySharedPref.STAY_LOGGED_IN, false);
        security_token = mySharedPref.getPref(MySharedPref.PREF_SESSION_TOKEN, "");
        introslides = mySharedPref.getPref(MySharedPref.PREF_INTROSLIDES, false);

        realmBasicInfo = realmManager.getRealmBasicInfo(realm);

        if (isRememberMe && security_token != null && security_token.length() != 0) {
            nextNavigation();
        } else {
            Thread background = new Thread() {
                public void run() {
                    try {
                        /*Android : Fix code review bugs analyzed by SonarQube -- casted the time filed to Log data type*/
                        long splTime = (long) (3 * 1000);
                        sleep(splTime);
                        if (introslides) {
                            Intent i;
                            if (docID != 0) {
                                i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                            } else {
                                i = new Intent(SplashScreenActivity.this, GetStartedActivity.class);
                            }
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                        } else {
                            Intent i = new Intent(SplashScreenActivity.this, IntroSlidesActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        finish();

                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        ie.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            background.start();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        //String lastLoginDate = formatter.format(now);
        Long lastDate = now.getTime();
        Log.d("timexyz", "date time" + lastDate);

        String dateLogin = AppUtil.parsePersonalizeDateIntoDays(lastDate);
        Log.d("noOfDays", "days" + dateLogin);

        mySharedPref.savePref(MySharedPref.PREF_LAST_LOGIN_TIME, dateLogin);

    }

    private void nextDeepLinkingNavigation() {
        isNavigateToNextForPermissions = false;

    }

    private void nextNavigation() {
        isNavigateToNextForPermissions = false;
//        if (AppUtil.isConnectingToInternet(this)) {
        /**
         * If the app started upon tapping on Push Notifications, then start appropriate activity.
         */
        String sharedURL = null;
        Intent intent1 = getIntent();
        String action = intent1.getAction();
        //String type = intent1.getType();
        Uri sharedUri = intent1.getData();
        String keyText = null;
        if (sharedUri != null) {
            String[] splitResult = sharedUri.toString().split("/");
            //sharedURL = sharedText.getQueryParameter(RestUtils.TAG_SID);
            sharedURL = splitResult[splitResult.length - 1];
            /*if(splitResult.length>1) {
                keyText = splitResult[splitResult.length - 2];
            }*/
            for (int i = 0; i < splitResult.length; i++) {
                if (splitResult[i].equalsIgnoreCase("feeds") || splitResult[i].equalsIgnoreCase("usr_verify")) {
                    keyText = splitResult[i];
                }
            }
        }
        if (Intent.ACTION_VIEW.equals(action)) {
            //if ("text/plain".equals(type)) {
            if (sharedURL != null && !sharedURL.isEmpty()) {
                if (keyText != null) {
                    if (keyText.equalsIgnoreCase("feeds")) {
                        AppUtil.handleSendText(SplashScreenActivity.this, docID, sharedURL, RestApiConstants.VIEW_SHARED_FEED, RestUtils.TAG_ENCRYPTED_KEY);
                    } else if (keyText.equalsIgnoreCase("usr_verify")) {
                        Intent intent = new Intent(SplashScreenActivity.this, MCACardUploadActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("NAVIGATION", "fromLink");
                        startActivity(intent);
                        finish();
                    }
                } else if (sharedUri != null) {
                    AppUtil.handleSendText(SplashScreenActivity.this, docID, sharedUri.toString(), RestApiConstants.GET_SHORTURL_DATA, RestUtils.TAG_SHORT_URL);
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                    intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        } else {
            Intent intent = null;
            try {
                AppUtil.logFlurryEventWithDocIdAndEmailEvent("STAY_LOGIN", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), docEmailId);
                FlurryAgent.setUserId(String.valueOf(docID));
                if (bundle != null) {
                    // To make sure app has been launched by tapping on Push Notification , so we need to navigate proper screen
                    if (bundle.containsKey(RestUtils.CHANNEL_ID)) {
                        channelId = bundle.getInt(RestUtils.CHANNEL_ID);
                    }
                    if (bundle.containsKey(RestUtils.FEED_TYPE_ID)) {
                        feedTypeId = bundle.getInt(RestUtils.FEED_TYPE_ID);
                    }
                    if (bundle.containsKey(RestUtils.TAG_TYPE)) {
                        type = bundle.getString(RestUtils.TAG_TYPE);
                    }
                    if (bundle.containsKey(RestUtils.TAG_C_MSG_TYPE)) {
                        c_msg_type = bundle.getString(RestUtils.TAG_C_MSG_TYPE);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_IS_LIKE_OR_COMMENT)) {
                        isLikeOrCommentNotification = bundle.getBoolean(MyFcmListenerService.KEY_IS_LIKE_OR_COMMENT);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_IS_POST_OR_ARTICLE)) {
                        isPostOrArticleNotification = bundle.getBoolean(MyFcmListenerService.KEY_IS_POST_OR_ARTICLE);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_IS_USER_REJECT)) {
                        isUserRejectNotification = bundle.getBoolean(MyFcmListenerService.KEY_IS_USER_REJECT);
                    }
                    //deeplinking
                    if (bundle.containsKey(RestUtils.NOTIFICATION_TAG_ID)) {
                        tagId = bundle.getString(RestUtils.NOTIFICATION_TAG_ID);
                    }
                    /*if (bundle.containsKey(MyFcmListenerService.KEY_KNOWLEDGE_FEED_PAGE)) {
                        knowledgeFeedNotification = bundle.getBoolean(MyFcmListenerService.KEY_KNOWLEDGE_FEED_PAGE);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_KNOWLEDGE_DRUG_REF_NOTIFICATION)) {
                        KnowledgeDrugRefNotification = bundle.getBoolean(MyFcmListenerService.KEY_KNOWLEDGE_DRUG_REF_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_KNOWLEDGE_MEDICAL_EVENTS_NOTIFICATION)) {
                        KnowledgeMedicalEventsNotification = bundle.getBoolean(MyFcmListenerService.KEY_KNOWLEDGE_MEDICAL_EVENTS_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_COMMUNITY_SPOTLIGHT_NOTIFICATION)) {
                        CommunitySpotlightNotification = bundle.getBoolean(MyFcmListenerService.KEY_COMMUNITY_SPOTLIGHT_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_COMMUNITY_FEED_NOTIFICATION)) {
                        CommunityFeedNotification = bundle.getBoolean(MyFcmListenerService.KEY_COMMUNITY_FEED_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_COMMUNITY_DOCTORS_PAGE_NOTIFICATION)) {
                        CommunityDoctorsNotification = bundle.getBoolean(MyFcmListenerService.KEY_COMMUNITY_DOCTORS_PAGE_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_COMMUNITY_ORGANIZATION_NOTIFICATION)) {
                        CommunityOrganizationNotification = bundle.getBoolean(MyFcmListenerService.KEY_COMMUNITY_ORGANIZATION_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_PROFESSIONAL_FEEDS_NOTIFICATION)) {
                        ProfessionalFeedsNotification = bundle.getBoolean(MyFcmListenerService.KEY_PROFESSIONAL_FEEDS_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_PROFESSIONAL_SKILLING_NOTIFICATION)) {
                        ProfessionalSkillingNotification = bundle.getBoolean(MyFcmListenerService.KEY_PROFESSIONAL_SKILLING_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_PROFESSIONAL_OPPORTUNITIES_NOTIFICATION)) {
                        ProfessionalOppurtunitiesNotification = bundle.getBoolean(MyFcmListenerService.KEY_PROFESSIONAL_OPPORTUNITIES_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_PROFESSIONAL_PARTNERS_NOTIFICATION)) {
                        ProfessionalPartnersNotification = bundle.getBoolean(MyFcmListenerService.KEY_PROFESSIONAL_PARTNERS_NOTIFICATION);
                    }
                    if (bundle.containsKey(MyFcmListenerService.KEY_NOTIFICATION_TAB)) {
                        NotificationPrefTab = bundle.getBoolean(MyFcmListenerService.KEY_NOTIFICATION_TAB);
                    }*/

                    if (bundle.containsKey("non_stored_dialog_msg")) {

                    }
                    // To make sure app has been launched by tapping on App icon , so we don't navigate to any other screen
                    if (bundle.containsKey(RestUtils.TAG_IS_FROM_NOTIFICATION)) {
                        if (docID > 0) {
                            JSONObject notificationTappedObj = new JSONObject();
                            notificationTappedObj.put(RestUtils.TAG_DOC_ID, docID);
                            notificationTappedObj.put(RestUtils.EVENT_DOC_SPECIALITY, realmBasicInfo.getSplty());
                            notificationTappedObj.put(RestUtils.TAG_NOTIFICATION_ID, bundle.getString(RestUtils.TAG_NOTIFICATION_ID, ""));
                            AppUtil.sendUserActionEventAPICall(docID, "Notification Tapped", notificationTappedObj, SplashScreenActivity.this);
                        }
                        bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, false);
                    } else {
                        bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, true);

                    }

                    if (isLikeOrCommentNotification || isPostOrArticleNotification) {
                        navigateToNextActivity();
                    } else if (bundle.containsKey(RestUtils.NOTIFICATION_TAG_ID)) {
                        navigateToNotifyDeepLinking();
                    } else if (isUserRejectNotification) {
                        Intent rejectIntent = new Intent(SplashScreenActivity.this, MCACardUploadActivity.class);
                        startActivity(rejectIntent);
                        finish();
                    } else {
                        String msg = bundle.getString("message", "");
                        if (msg.contains("has invited you to connect on WhiteCoats") || msg.contains("has accepted invited request")) {
                            intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                            intent.putExtra(RestUtils.TAG_IS_CONNECT_NOTIFICATION, true);
                            intent.putExtra("TAB_NUMBER", 1);
                            intent.putExtras(bundle);
                        } else if (msg.contains("Group on WhiteCoats")) {
                            intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                            intent.putExtra(RestUtils.TAG_IS_CONNECT_NOTIFICATION, true);
                            intent.putExtra("TAB_NUMBER", 1);
                            intent.putExtras(bundle);
                        } else if (msg.contains("CaseRoom on WhiteCoats")) {
                            intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                            intent.putExtra(RestUtils.TAG_IS_CONNECT_NOTIFICATION, true);
                            intent.putExtra("TAB_NUMBER", 1);
                            intent.putExtras(bundle);
                        } else {
                            intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                            intent.putExtras(bundle);
                        }
                    }
                } else {
                    intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                    intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                }
                if (intent != null) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                startActivity(intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onTaskCompleted(String s) {
        Log.i(TAG, "onTaskCompleted()");
    }

    //Notification-Deep-linking
    private void navigateToNotifyDeepLinking() {
        Log.i(TAG, "navigateToNextActivity()");
        JSONObject jsonObject = new JSONObject();
        try {
            /*ActivityManager manager = (ActivityManager) getApplication().getSystemService(ACTIVITY_SERVICE);
            int sizeStack = manager.getRunningTasks(2).size();
            for (int i = 0; i < sizeStack; i++) {
                ComponentName cn = manager.getRunningTasks(2).get(i).topActivity;
                Log.d("task name", cn.getClassName());
            }
            Log.e(TAG, "task - " + manager);*/
            stackValue = bundle.getBoolean("stackBoolValue");
            jsonObject.put(RestUtils.NOTIFICATION_TAG_ID, tagId);
            jsonObject.put(RestUtils.TAG_C_MSG_TYPE, c_msg_type);
            jsonObject.put("deeplinkingStack", stackValue);
            Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
            intent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, jsonObject.toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* if (c_msg_type.equalsIgnoreCase("20")) {
            if (bundle.containsKey(RestUtils.NOTIFICATION_TAG_ID)){
                //feedTypeId = bundle.getInt(RestUtils.FEED_TYPE_ID);
                tagId = bundle.getInt(RestUtils.NOTIFICATION_TAG_ID);
                Log.i(TAG, "tag_id"+tagId);
            }
        }*/
    }

    private void navigateToNextActivity() {
        Log.i(TAG, "navigateToNextActivity()");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(RestUtils.TAG_DOC_ID, docID);
            jsonObject.put(RestUtils.CHANNEL_ID, channelId);
            jsonObject.put(RestUtils.TAG_TYPE, type);
            jsonObject.put(RestUtils.FEED_ID, feedTypeId);
            Intent intent = new Intent(SplashScreenActivity.this, EmptyActivity.class);
            intent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, jsonObject.toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ShowSimpleDialog(final String title, final String message) {
        Log.i(TAG, "ShowSimpleDialog()");
        try {
            builder = new AlertDialog.Builder(this);
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("Try again", null);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (!realm.isClosed())
            realm.close();
        jsonResponse = null;
        type = null;
        c_msg_type = null;
        if (mHomeWatcher != null) {
            mHomeWatcher.stopWatch();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (marshMallowPermission.checkPermissionsAccepted() && isNavigateToNextForPermissions) {
            nextNavigation();
            nextDeepLinkingNavigation();
        }
    }

    public synchronized void hideProgress() {
        if (progress != null && progress.getActivity() != null) {
            progress.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        JSONObject jsonObject = new JSONObject();
        try {
            AppUtil.logUserUpShotEvent("SplashScreenDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }
}