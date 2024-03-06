package com.vam.whitecoats.ui.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.WhiteCoatsError;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.ExceptionHandler;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnHomePressedListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HomeWatcher;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

import io.realm.Realm;

@SuppressLint("NewApi")


public abstract class BaseActionBarActivity extends AppCompatActivity implements OnTaskCompleted {
    private int feed_id;
    private int channel_id;
    private int other_doc_id;
    private String navigated;
    private String department_name;
    private String attchmentType;
    private String other_doc_uuid;
    private String categoryName;
    protected ValidationUtils validationUtils;
    protected ExceptionHandler mExceptionHandler = null;
    protected Intent mIntent;
    protected MySharedPref mySharedPref = null;
    static Snackbar snackbar;
    protected LayoutInflater mInflater;
    protected View mCustomView;

    // finish all activities when app closes
    public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "com.android.smartnet.checkincheckout.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
    protected ProgressDialogFragement progress;
    protected App_Application app;
    protected ActionBar actionBar;
    //create global application context
    protected Context mContext;
    protected AlertDialog.Builder builder;

    protected boolean mKeyboardStatus;
    protected ProgressDialog mProgressDialog;
    private HomeWatcher mHomeWatcher;
    private Realm realm;
    private RealmManager realmManager;
    private int doctorId;
    private boolean isFromDiscovery;


    public BaseActionBarActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        app = App_Application.getInstance();
        actionBar = getSupportActionBar();
        mContext = this; // Get the application scope
        App_Application.setCurrentActivity(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));
        if (mIntent == null)
            mIntent = new Intent();

        if (mySharedPref == null)
            mySharedPref = new MySharedPref(this);

        if (mExceptionHandler == null)
            mExceptionHandler = new ExceptionHandler(this);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        doctorId = realmManager.getDoc_id(realm);
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                String activityName = mContext.getClass().getSimpleName();
                JSONObject jsonObject = new JSONObject();
                try {
                    if (doctorId != 0) {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    }
                    if (isFromDiscovery && navigated != null) {
                        String event_name = "";
                        if (navigated.equalsIgnoreCase("RecentFeeds")) {
                            event_name = "RecentFeedsHomeTapped";
                        } else if (navigated.equalsIgnoreCase("SubCategoryFeeds")) {
                            event_name = "SubCategoryFeedsHomeTapped";
                        } else if (navigated.equalsIgnoreCase("CategoryDistributionSpecialityFeeds")) {
                            event_name = "CategoryDistributionSpecialityFeedsHomeTapped";
                        } else if (navigated.equalsIgnoreCase("CategoryDistributionRecent")) {
                            event_name = "CategoryDistributionRecentFeedsHomeTapped";
                        } else if (navigated.equalsIgnoreCase("CategoryDistributionSpeciality")) {
                            event_name = "CategoryDistributionSpecialityHomeTapped";
                        } else if (navigated.equalsIgnoreCase("SubCategory")) {
                            event_name = "SubCategoryHomeTapped";
                        } else if (navigated.equalsIgnoreCase("DrugClass")) {
                            event_name = "DrugClassHomeTapped";
                        } else if (navigated.equalsIgnoreCase("DrugSubClass")) {
                            event_name = "DrugSubClassHomeTapped";
                        } else if (navigated.equalsIgnoreCase("DrugName")) {
                            event_name = "DrugNameHomeTapped";
                        }
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), event_name, jsonObject, AppUtil.convertJsonToHashMap(jsonObject), BaseActionBarActivity.this);
                    }/*Removed the @param activityName null check condition to remove the warning message*/ else {
                        if (activityName.equalsIgnoreCase("FeedsSummary") || activityName.equalsIgnoreCase("ContentFullView") || activityName.equalsIgnoreCase("CreatePostActivity")) {
                            if (activityName.equalsIgnoreCase("CreatePostActivity")) {
                                activityName = "CreatePost";
                            } else {
                                activityName = "FeedFullView";
                            }
                            jsonObject.put("FeedID", feed_id);
                            jsonObject.put("ChannelID", channel_id);
                        } else if (activityName.equalsIgnoreCase("VisitOtherProfile")) {
                            jsonObject.put("OtherDocId", other_doc_uuid);
                        } else if (activityName.equalsIgnoreCase("Profile_fullView")) {
                            activityName = "UserProfileImage";
                        } else if (activityName.equalsIgnoreCase("BasicProfileActivity")) {
                            activityName = "UserBasicInfo";
                        } else if (activityName.equalsIgnoreCase("NetworkSearchActivity")) {
                            activityName = "DoctorFeedSearch";
                        } else if (activityName.equalsIgnoreCase("GlobalSearchActivity")) {
                            activityName = "Search";
                        } else if (activityName.equalsIgnoreCase("ProfileViewActivity")) {
                            activityName = "UserProfile";
                        } else if (activityName.equalsIgnoreCase("AboutMeActivity")) {
                            activityName = "UserContactInfo";
                        } else if (activityName.equalsIgnoreCase("Network_MyConnects")) {
                            activityName = "Connects";
                        } else if (activityName.equalsIgnoreCase("ViewContactsActivity") || activityName.equalsIgnoreCase("MyBookMarksActivity")) {
                            /*Added the null check condition to avoid the crash whenever memebr click on the Home button*/
                            if (navigated != null) {
                                activityName = navigated;
                                if (navigated.equalsIgnoreCase("OtherUserfollowers") || navigated.equalsIgnoreCase("OtherUserFollowing") || navigated.contains("OtherUserPosts") || navigated.contains("OtherUserConnects")) {
                                    jsonObject.put("OtherDocId", other_doc_uuid);
                                } else if (navigated.equalsIgnoreCase("fromCategoriesList")) {
                                    activityName = "TrendingCategory";
                                    jsonObject.put("CategoryName", categoryName);
                                }
                            }
                        } else if (activityName.equalsIgnoreCase("InviteRequestActivity")) {
                            activityName = "InviteDoctor";
                        } else if (activityName.equalsIgnoreCase("CommentsActivity")) {
                            activityName = "FeedComments";
                            if (navigated != null && navigated.equalsIgnoreCase("fromLikesCount")) {
                                activityName = "FeedLikes";
                            }
                            jsonObject.put("FeedID", String.valueOf(feed_id));
                            jsonObject.put("ChannelID", String.valueOf(channel_id));
                        } else if (activityName.equalsIgnoreCase("DepartmentMembersActivity")) {
                            activityName = "DepartmentMembers";
                            jsonObject.put("ChannelID", String.valueOf(channel_id));
                            jsonObject.put("DepartmentName", department_name);
                        } else if (activityName.equalsIgnoreCase("SearchContactsActivity")) {
                            if (navigated != null && navigated.equalsIgnoreCase("MembersSearch")) {
                                activityName = "MembersSearch";
                                jsonObject.put("ChannelID", String.valueOf(channel_id));
                            } else if (navigated != null && navigated.equalsIgnoreCase("DepartmentMembersSearch")) {
                                activityName = "DepartmentMembersSearch";
                                jsonObject.put("ChannelID", String.valueOf(channel_id));
                                jsonObject.put(RestUtils.DEPARTMENT_NAME, department_name);
                            }

                        } else if (activityName.equalsIgnoreCase("MediaFilesActivity")) {
                            activityName = "MediaTimeline";
                            jsonObject.put("ChannelID", channel_id);
                        } else if (activityName.equalsIgnoreCase("ImageViewer") || activityName.equalsIgnoreCase("PdfViewerActivity")) {
                            /*Added the null check condition to avoid the crash whenever memebr click on the Home button*/
                            if (navigated != null && navigated.equalsIgnoreCase("createPost")) {
                                activityName = "EditAttachment";
                            } else {
                                activityName = "MediaPreview";
                                jsonObject.put("FeedID", feed_id);
                                jsonObject.put("AttachmentType", attchmentType);
                            }
                            jsonObject.put("ChannelID", channel_id);
                        } else if (activityName.equalsIgnoreCase("TimeLine")) {
                            activityName = navigated;
                            jsonObject.put("ChannelID", channel_id);
                        } else if (activityName.equalsIgnoreCase("DashboardActivity")) {
                            activityName = navigated;
                        } else if (activityName.equalsIgnoreCase("UserSearchResultsActivity")) {
                            activityName = "DoctorFeedSearch";
                        } else if (activityName.equalsIgnoreCase("MandatoryProfileInfo")) {
                            activityName = "ProfileInfo";
                        } else if (activityName.equalsIgnoreCase("CommentsPreviewActivity")) {
                            activityName = "FeedCommentsImage";
                            jsonObject.put("FeedID", String.valueOf(feed_id));
                            jsonObject.put("ChannelID", String.valueOf(channel_id));
                        } else if (activityName.equalsIgnoreCase("OTPConformationScreen")) {
                            activityName = "ResetPassword";
                        } else if (activityName.equalsIgnoreCase("PreferencesActivity")) {
                            activityName = "SpecialityPreference";
                        } else if (activityName.equalsIgnoreCase("CaseRoomActivity")) {
                            activityName = "Caserooms";
                        } else if (activityName.equalsIgnoreCase("CreateCaseroomActivity")) {
                            activityName = "NewCaserooms";
                        }
                        AppUtil.logUserUpShotEvent(activityName + "HomeTapped", AppUtil.convertJsonToHashMap(jsonObject));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onHomeLongPressed() {

            }
        });
        // mHomeWatcher.startWatch();
    }

    protected abstract void setCurrentActivity();

    protected void checkNetworkConnectivity() {
        if (isConnectingToInternet()) {
            hideSnackBar();
        } else {
            displaySnackBar(getString(R.string.no_internet_connection));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App_Application.setCurrentActivity(this);
        if (mIntent == null)
            mIntent = new Intent();  // create intent object, we will use this object in the child class to navigate screens
        mHomeWatcher.startWatch();
    }

    @Override
    protected void onPause() {
        if (mIntent != null)
            mIntent = null;

        if (mExceptionHandler != null)
            mExceptionHandler = null;
        mHomeWatcher.stopWatch();
        super.onPause();
    }

    /*ENGG-3376 -- Window leak issue*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*Dismissing the progress view to avoid the window leak issue.*/
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public synchronized void showProgress() {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void ShowSimpleDialog(final String title, final String message) {
        try {
            builder = new AlertDialog.Builder(this);
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("OK", null);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ShowServerErrorSimpleDialog(final String title, final String message) {
        try {
            builder = new AlertDialog.Builder(this);
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MySharedPref.getPrefsHelper().savePref(MySharedPref.STAY_LOGGED_IN, false);
                    Intent i = new Intent(BaseActionBarActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void closeAllActivities() {
        try {
            sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class BaseActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION)) {
                finish();
            }
        }
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @SuppressWarnings("unchecked")
    protected <T> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

    protected void catchException(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        String stackTrace = null;
        PrintWriter mPrintWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(mPrintWriter);

        // get the stackTrace as String...
        stackTrace = stringWriter.toString();

        mExceptionHandler.uncaughtException(stackTrace);

        if (stringWriter != null)
            stringWriter = null;

        if (mPrintWriter != null)
            mPrintWriter = null;
    }

    @Override
    public void onTaskCompleted(String s) {


    }

    protected void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            /*ENGG-3376 -- changed the view of getting the window token as its laking the window*/
            //  inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
            mKeyboardStatus = false;
        } catch (Exception e) {
            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }

    }

    protected void showKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
            mKeyboardStatus = true;
        } catch (Exception e) {
            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }

    public void displayErrorScreen(String errResponse) {
        try {
            if (errResponse != null && !errResponse.isEmpty()) {
                JSONObject errorObj = new JSONObject(errResponse);
                int errorCode = errorObj.optInt(RestUtils.TAG_ERROR_CODE);
                //If error code matches with user defined errors then navigate to error screen. otherwise
                // display a Toast message.
                if (errorCode == WhiteCoatsError.BAD_GATEWAY.getErrorCode() || errorCode == WhiteCoatsError.INCORRECT_APP_VERSION.getErrorCode()
                        || errorCode == WhiteCoatsError.SERVER_UPGRADE.getErrorCode() || errorCode == WhiteCoatsError.SERVER_ERROR.getErrorCode()
                        || errorCode == WhiteCoatsError.CONNECTION_TIMEOUT.getErrorCode() || errorCode == WhiteCoatsError.SERVICE_UNAVAILABLE.getErrorCode()) {
                    Intent intent = new Intent(this, ErrorResponseActivity.class);
                    intent.putExtra(RestUtils.TAG_ERROR_MESSAGE, errResponse);
                    startActivity(intent);
                    finish();

                } else if(errorCode==1042){
                    String errorMsg = "Something went wrong,please try again.";
                    if (errorObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                        errorMsg = errorObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                    }
                    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    String errorMsg = "Unable to connect server.";
                    if (errorObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                        errorMsg = errorObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                    }
                    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Unable to connect server.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Unable to connect server.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public static void displaySnackBar(String message) {

        if (App_Application.getCurrentActivity() != null) {
            snackbar = Snackbar.make(App_Application.getCurrentActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE);
// Get the Snackbar's layout view
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
// Hide the text
            TextView textView = (TextView) layout.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setVisibility(View.INVISIBLE);

// Inflate our custom view

            LayoutInflater mInflater = LayoutInflater.from(App_Application.getCurrentActivity());
            View snackView = mInflater.inflate(R.layout.my_snackbar, null);
// Configure the view

            TextView textViewTop = (TextView) snackView.findViewById(R.id.snack_bar_text);
            textViewTop.setText(message);
            textViewTop.setTextColor(Color.WHITE);
            layout.setPadding(0, 0, 0, 0);
// Add the view to the Snackbar's layout
            layout.addView(snackView, 0);

            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(App_Application.getCurrentActivity(), R.color.red));
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sbView.getLayoutParams();
            if (App_Application.getCurrentActivity() instanceof DashboardActivity) {
                Toolbar toolbar = (Toolbar) (App_Application.getCurrentActivity()).findViewById(R.id.toolbar_dashboard);
                params.setMargins(0, toolbar.getHeight(), 0, 0);
            } else {
                params.setMargins(0, 0, 0, 0);
            }
            params.gravity = Gravity.TOP;
            sbView.setLayoutParams(params);

// Show the Snackbar
            snackbar.show();
        }


        /*if (App_Application.getCurrentActivity() != null) {
            snackbar = Snackbar
                    .make(App_Application.getCurrentActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(App_Application.getCurrentActivity(), R.color.red));
            snackbar.show();
        }*/

    }

    public static void hideSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
//            snackbar.getView().setBackgroundColor(ContextCompat.getColor(App_Application.getCurrentActivity(), R.color.app_green));
            snackbar.dismiss();
        }

    }

    protected boolean isKeyboardActive() {
        return mKeyboardStatus;
    }

    public void upshotEventData(int feedId, int channelId, int otherDocId, String OtherDocUUID, String navigatedFrom, String departmentName, String attachment_type, String category_name, boolean fromDiscovery) {
        feed_id = feedId;
        channel_id = channelId;
        other_doc_id = otherDocId;
        navigated = navigatedFrom;
        department_name = departmentName;
        attchmentType = attachment_type;
        other_doc_uuid = OtherDocUUID;
        categoryName = category_name;
        isFromDiscovery = fromDiscovery;
    }

}