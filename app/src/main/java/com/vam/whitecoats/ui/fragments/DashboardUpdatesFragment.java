package com.vam.whitecoats.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.brandkinesis.BKUserInfo;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.brandkinesis.callback.BKUserInfoCallback;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.flurry.android.FlurryAgent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vam.whitecoats.BuildConfig;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.constants.SlotLocationType;
import com.vam.whitecoats.constants.WhitecoatsFlavor;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.DashBoardSpamReportListModel;
import com.vam.whitecoats.core.models.HorizontalListDataObj;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmAdSlotInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmFeedInfo;
import com.vam.whitecoats.core.realm.RealmFeedsList;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.UserEvents;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.ContentFullView;
import com.vam.whitecoats.ui.activities.CreatePostActivity;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.ui.activities.JobFeedCompleteView;
import com.vam.whitecoats.ui.activities.MCACardUploadActivity;
import com.vam.whitecoats.ui.activities.ProfileViewActivity;
import com.vam.whitecoats.ui.adapters.CommunityListAdapter;
import com.vam.whitecoats.ui.adapters.DashboardFeedsAdapter;
import com.vam.whitecoats.ui.adapters.HorizontalRecyclerAdapter;
import com.vam.whitecoats.ui.adapters.MyRecycleAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.HorizontalItemListener;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnFeedItemClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnRefreshDashboardData;
import com.vam.whitecoats.ui.interfaces.OnScrollTopListener;
import com.vam.whitecoats.ui.interfaces.OnSocialInteractionListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.ProfileUpdatedListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.Foreground;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.sentry.Sentry;
import io.sentry.protocol.User;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static com.vam.whitecoats.ui.activities.DashboardActivity.DELETE_ACTION;
import static com.vam.whitecoats.ui.activities.TimeLine.ARG_REVEAL_START_LOCATION;

public class DashboardUpdatesFragment extends Fragment implements OnCustomClickListener, UiUpdateListener, OnFeedItemClickListener, DashboardFeedsAdapter.BookMarkListener, DashboardFeedsAdapter.OnFeedProfileClickListener, ProfileUpdatedListener, HorizontalItemListener {

    public static final String TAG = DashboardUpdatesFragment.class.getSimpleName();
    private static JSONArray tempArray;
    private static ArrayList<JSONObject> tempFeedsList;
    private OnRefreshDashboardData refreshDataListener;
    private int selectedTabPosition = 0;
    private RecyclerView mRecyclerView;
    private ImageButton makepost;
    private ImageView addPicSymbol;
    private static MyRecycleAdapter mAdapter;
    private static Context context;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeContainer;
    private CustomLinearLayoutManager mLayoutManager;
    private static ArrayList<JSONObject> channelsInfoList;
    RealmList<RealmFeedInfo> feedDataList;
    // private DashboardListener dashboardListener;
    private boolean dashboardStatus;

    private Realm realm;
    private RealmManager realmManager;
    private JSONObject requestData;

    public ProgressDialogFragement progress;
    public AlertDialog.Builder builder;
    private static DashboardUpdatesFragment instance;
    //private NewContentReceiver newContentReceiver;
    private boolean dashboard_call_needed = false;
    private MySharedPref mySharedPref;
    private long startTime, stopTime;
    private float totaDiff;
    private boolean isDisplayPostIcon = false;
    private JSONArray chooseCommunityJsonObj = new JSONArray();
    private int login_doc_id = 0;
    protected Context mContext;
    private ProgressDialog mProgressDialog;
    private OnTaskCompleted callBackListner;
    private RealmBasicInfo basicInfo;
    private RoundedImageView profilePic;
    private ViewGroup postUpdateLayout;
    private SparseArray<JSONObject> channelsList = new SparseArray<>();
    private DashboardFeedsAdapter dashboradAdapter;
    private Button newUpdatesBtn;
    private ShimmerFrameLayout shimmerContainer;
    private boolean dashBoardCallNeeded = false;
    private ViewGroup shimmerContentLayout;
    private ShimmerFrameLayout shimmerContainer1;
    private int pageIndex = 0;
    private TextView postUpdateHint, feeds_errmsg;
    LinearLayout dashboardParentLayout, postUpdateLabel_lay;
    int networkChannelId = -1;
    private boolean isFragmentInForeground = true;
    private String docName = "";
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    private JSONArray trendingCategoriesJsonArray = new JSONArray();
    private ArrayList<JSONObject> trendingCategoriesList = new ArrayList<>();
    public static boolean dashboardRefreshOnSubscription = false;
    // private String eventId;
    //private BrandKinesis bkInstance;
    private String dashboardTag = "Dashboard tag";
    private long lastOpenTime, lastCloseTime;
    private String successResponse;
    private boolean reloadData;
    private ArrayList<JSONObject> associationListJson = new ArrayList<>();
    private ArrayList<Integer> feedIdList = new ArrayList<>();
    private JSONObject dataJsonObj;
    private int horizontalItemPageIndex = 0;
    private int lastFeedId = 0;
    private LikeActionAsync likeAPICall;
    private ArrayList<Category> categoryList = new ArrayList();
    RealmAdSlotInfo midPostSlotInfo = null;
    private RealmResults<RealmAdSlotInfo> adSlotResults;

    private int feedCount = 0;
    private int occurance_count = 0;
    private ArrayList<DashBoardSpamReportListModel> dashBoardSpamReportListModel;


    public static DashboardUpdatesFragment newInstance(boolean refreshData) {
        Log.i(TAG, "DashboardUpdatesFragment newInstance");
        DashboardUpdatesFragment fragment = new DashboardUpdatesFragment();
        Bundle args = new Bundle();
        //args.putString("response", response);
        args.putBoolean("refreshData", refreshData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        // Get the activity who started this Fragment
        context = getActivity();

        Log.e("FRAG_FLOW", "oncreate");
        instance = this;
        setRetainInstance(true);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        dashBoardCallNeeded = true;
        isFragmentInForeground = true;
        CallbackCollectionManager.getInstance().registerListener(this);
        //ProfileUpdateCollectionManager.registerListener(this);
        login_doc_id = realmManager.getDoc_id(realm);
        docName = realmManager.getDoc_name(realm);
        mySharedPref = new MySharedPref(getActivity());
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));
        mySharedPref.savePref(MySharedPref.STAY_LOGGED_IN, true);
        mySharedPref.savePref(MySharedPref.PREF_REGISTRATION_FLAG, true);
        //lastOpenTime=mySharedPref.getPref("APP_OPEN_TIME",Long.parseLong("0"));
        //lastCloseTime=mySharedPref.getPref("APP_CLOSE_TIME",Long.parseLong("0"));
        mySharedPref.savePref("APP_OPEN_TIME", System.currentTimeMillis());
        //mySharedPref.getPrefsHelper().savePref(MySharedPref.REQUEST_PG_INDEX, 0);
        //AppConstants.IS_USER_VERIFIED_CONSTANT=mySharedPref.getPref(MySharedPref.PREF_IS_USER_VERIFIED,0);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        //bkInstance = BrandKinesis.getBKInstance();
        //adSlotResults = realmManager.getAdSlotInfoByLocation("Mid-post slot");


        Bundle userInfo = new Bundle();
        if (basicInfo.getUserUUID() != null && !basicInfo.getUserUUID().isEmpty()) {
            userInfo.putString(BKUserInfo.BKExternalIds.APPUID, basicInfo.getUserUUID());
        }
        userInfo.putString(BKUserInfo.BKExternalIds.GCM, mySharedPref.getPref(MyFcmListenerService.PROPERTY_REG_ID, ""));
        userInfo.putString(BKUserInfo.BKUserData.USER_NAME, basicInfo.getUser_salutation() + " " + basicInfo.getFname() + " " + basicInfo.getLname());
        ProfessionalInfo professionalInfo = realmManager.getProfessionalInfoOfShowoncard(realm);
        HashMap<String, Object> others = new HashMap<>();
        if (professionalInfo != null) {
            others.put("DocLocation", professionalInfo.getLocation());
        }

        others.put("DocSpeciality", basicInfo.getSplty());
        others.put("DocExperience", basicInfo.getOverAllExperience());
        others.put("isCommunityVerified", AppUtil.getCommunityUserVerifiedStatus());
        others.put("isUserVerified", mySharedPref.getPref(MySharedPref.PREF_IS_USER_VERIFIED));
        others.put("isNotificationsEnabled", AppUtil.areNotificationsEnabled(getActivity()));
        userInfo.putSerializable(BKUserInfo.BKUserData.OTHERS, others);
        Log.d("BUNDLE_DATA", userInfo.toString());
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        if (bkInstance != null) {
            bkInstance.setUserInfoBundle(userInfo, new BKUserInfoCallback() {
                @Override
                public void onUserInfoUploaded(boolean uploadStatus) {
                    Log.d("UPSHOT_USER_LOG", "result :" + uploadStatus);
                }
            });
            bkInstance.getActivity(getActivity(), BKActivityTypes.ACTIVITY_ANY, dashboardTag);
        }

        //user register in sentry
        User sentryUserInfo = new User();
        if (basicInfo.getUserUUID() != null && !basicInfo.getUserUUID().isEmpty()) {
            sentryUserInfo.setId(basicInfo.getUserUUID());
        }
        HashMap<String, String> otherUserInfo = new HashMap<>();
        otherUserInfo.put("DocSpeciality", basicInfo.getSplty());
        sentryUserInfo.setOthers(otherUserInfo);
        Sentry.setUser(sentryUserInfo);
        if (realmManager.getFeedDataFromDB() == null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmFeedsList listObj = realm.createObject(RealmFeedsList.class, 0);
//                    listObj.setFeed_Id(0);
                    listObj.setFeedsList(new RealmList<RealmFeedInfo>());
                }
            });
        }

        //clearRealmData(realm);
        requestData = new JSONObject();
        //request data
        try {
            requestData.put(RestUtils.TAG_DOC_ID, login_doc_id);
            requestData.put("last_feed_time", 0);
            requestData.put("last_open", 0);
            requestData.put("last_close", 0);
            requestData.put("load_next", true);
            requestData.put("pg_num", pageIndex);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void clearRealmData(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmFeedsList> rows = realm.where(RealmFeedsList.class).findAll();
                if (rows.size() > 0) {
                    rows.get(0).clearFeedsList();
                }
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmFeedInfo> rows = realm.where(RealmFeedInfo.class).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }

    public static DashboardUpdatesFragment getInstance() {
        return instance;
    }

    public void makeLikeServiceCall(JSONObject subItem, final int channel_id, Boolean isLiked, final int mFeedTypeId) {
        JSONObject likeRequest = new JSONObject();
        try {
            likeRequest.put(RestUtils.TAG_DOC_ID, login_doc_id);
            likeRequest.put(RestUtils.CHANNEL_ID, channel_id);
            likeRequest.put(RestUtils.FEED_TYPE_ID, mFeedTypeId);
            JSONObject socialInteractionObj = new JSONObject();
            socialInteractionObj.put("type", "Like");
            socialInteractionObj.put(RestUtils.TAG_IS_LIKE, isLiked);
            likeRequest.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!AppUtil.isConnectingToInternet(getActivity())) {
            return;
        } else {
            AppConstants.likeActionList.add(channel_id + "_" + mFeedTypeId);
        }
        likeAPICall = new LikeActionAsync(getActivity(), RestApiConstants.SOCIAL_INTERACTIONS, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String sResponse) {
                if (AppConstants.likeActionList.contains(channel_id + "_" + mFeedTypeId)) {
                    AppConstants.likeActionList.remove(channel_id + "_" + mFeedTypeId);
                }
                if (sResponse != null) {
                    if (sResponse.equals("SocketTimeoutException") || sResponse.equals("Exception")) {
                        Log.i(TAG, "onTaskCompleted(String response) " + sResponse);
                        Log.e("Error like response", getResources().getString(R.string.timeoutException));
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(sResponse);
                            if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                JSONObject likeResponseObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                realmManager.UpdateFeedWithSocialInteraction(mFeedTypeId, likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                dashboradAdapter.notifyDataSetChanged();
                            } else {
                                if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setMessage(jsonObject.getString(RestUtils.TAG_ERROR_MESSAGE));
                                        builder.setCancelable(true);
                                        builder.setPositiveButton("Verify Now", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent verifyLikeIntent = new Intent(getActivity(), MCACardUploadActivity.class);
                                                startActivity(verifyLikeIntent);
                                            }
                                        });

                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }).create().show();
                                    } else {
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
        likeAPICall.execute(likeRequest.toString());
        dashboradAdapter.setLikeAPICallAsync(likeAPICall);
    }

    @Override
    public void updateUI(int feedId, JSONObject socialInteractionObj) {
        realmManager.UpdateFeedWithSocialInteraction(feedId, socialInteractionObj);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DocID", realmManager.getUserUUID(realm));
            jsonObject.put("FeedID", feedId);
            AppUtil.logUserUpShotEvent("DashboardFeedFullView", AppUtil.convertJsonToHashMap(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (dashboradAdapter != null) {
            dashboradAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyUIWithNewData(JSONObject newUpdate) {
        // If same feed exists, don't insert into DB

        if (getActivity() != null && isAdded() && AppUtil.isConnectingToInternet(getActivity())) {
            //from edit post
            if (newUpdate.has(RestUtils.TAG_FROM_EDIT_POST) && newUpdate.has(RestUtils.TAG_FEED_INFO)) {

                realmManager.updateFeedsDBWithLatestData(newUpdate.optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID), newUpdate);
                dashboradAdapter.notifyDataSetChanged();
                // To display loader, insert dummy data to DB

            } else {
                if (realmManager.checkFeedExists(newUpdate.optInt(RestUtils.TAG_FEED_ID), newUpdate.optInt(RestUtils.CHANNEL_ID))) {
                    return;
                }
                if (newUpdate.has(RestUtils.TAG_FROM_CREATE_POST)) {
                    if (mRecyclerView != null) {
                        mRecyclerView.scrollToPosition(0);
                    }
                    // To display loader, insert dummy data to DB
                    realmManager.insertTestFeedDataIntoDB(realm, -2, true);
                    dashboradAdapter.notifyItemInserted(realmManager.getFeedDataFromDB().size());
                }
                try {
                    long lastFeedTime = 0;
                    if (realmManager.checkFeedExists(-6, 0)) {
                        lastFeedTime = realmManager.getFeedDataFromDB() != null && realmManager.getFeedDataFromDB().size() > 2 ? realmManager.getFeedDataFromDB().get(2).getCreatedOrUpdatedTime() : 0;
                    } else {
                        lastFeedTime = realmManager.getFeedDataFromDB() != null && realmManager.getFeedDataFromDB().size() > 1 ? realmManager.getFeedDataFromDB().get(1).getCreatedOrUpdatedTime() : 0;
                    }
                    requestData.put("last_feed_time", lastFeedTime);
                    requestData.put("load_next", false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (realmManager.getFeedDataFromDB() != null && realmManager.getFeedDataFromDB().size() > 0) {
                    requestForDashBoardData(requestData, true, false);
                }
            }
        }
    }


    @Override
    public void notifyUIWithDeleteFeed(int feedId, JSONObject deletedFeedObj) {
        if (deletedFeedObj.has(RestUtils.TAG_POST_ID)) {
            realmManager.deleteFeedFromDB(deletedFeedObj.optInt(RestUtils.TAG_POST_ID), deletedFeedObj.optInt(RestUtils.CHANNEL_ID));
            dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
            if (dashboradAdapter.getHorizontalObjList() != null && dashboradAdapter.getHorizontalObjList().size() > 0) {
                //boolean refreshSuggestedFeedRow=false;
                for (int i = 0; i < dashboradAdapter.getHorizontalObjList().size(); i++) {
                    int key = dashboradAdapter.getHorizontalObjList().keyAt(i);
                    HorizontalListDataObj horizontalListDataObj = dashboradAdapter.getHorizontalObjList().get(key);
                    if (feedId == horizontalListDataObj.getParentFeedId() && horizontalListDataObj.getHorizontalListType().equalsIgnoreCase("Related")) {
                        realmManager.deleteFeedFromDB(feedId, -5);
                        dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                    } else {
                        for (int j = 0; j < horizontalListDataObj.getChildFeedIds().size(); j++) {
                            if (horizontalListDataObj.getChildFeedIds().get(j) == deletedFeedObj.optInt(RestUtils.TAG_POST_ID)) {
                                //refreshSuggestedFeedRow = true;
                                horizontalListDataObj.getChildFeedIds().remove(j);
                                if (horizontalListDataObj.getHorizontalListType().equalsIgnoreCase("suggested")) {
                                    if (horizontalListDataObj.getChildFeedIds().size() == 0) {
                                        realmManager.deleteFeedFromDB(-4, 0);
                                        dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                                    } else {
                                        int position = realmManager.getFeedDBPosition(realmManager.getRealmFeedInfoObj(-4, 0));
                                        dashboradAdapter.notifyItemChanged(position);
                                    }
                                } else if (horizontalListDataObj.getHorizontalListType().equalsIgnoreCase("Related")) {
                                    if (horizontalListDataObj.getChildFeedIds().size() == 0) {
                                        realmManager.deleteFeedFromDB(horizontalListDataObj.getParentFeedId(), -5);
                                        dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                                    } else {
                                        dashboradAdapter.notifyDataSetChanged();
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void requestForDashBoardData(JSONObject requestData, final boolean loadPreData, final boolean refreshData) {
        startTime = System.currentTimeMillis();
        FlurryAgent.logEvent("Dashboard hit :" + AppConstants.login_doc_id);
        AppUtil.captureSentryMessage("Dashboard hit :" + AppConstants.login_doc_id);
        if (requestData.optInt(RestUtils.TAG_DOC_ID, 0) == 0) {
            Toast.makeText(getActivity(), "Unable to load data,Please restart the app", Toast.LENGTH_SHORT).show();
            return;
        }
        new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.GET_DASHBOARD_DATA, requestData.toString(), "DAY_FIRST_FEED", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (getActivity() != null && isAdded()) {
                    processDashboardResponse(successResponse, loadPreData, refreshData, true);
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                processDashboardErrorResponse(errorResponse, loadPreData);

            }
        }).sendSinglePartRequest();

        if (!loadPreData) {
            AppUtil.syncShareFailedFeedsToServer(getActivity(), realmManager, login_doc_id);
            RealmResults<UserEvents> eventsList = realmManager.getAllEventsDataFromDB();
            if (eventsList.size() > 0) {
                JSONObject failedNotificationsRequest = new JSONObject();
                JSONArray eventObjArray = new JSONArray();
                try {
                    for (UserEvents userEvent : eventsList) {
                        JSONObject eventObj = new JSONObject();
                        eventObj.put(RestUtils.TAG_EVENT_TYPE, userEvent.getEventName());
                        eventObj.put(RestUtils.TAG_EVENT_DATA, new JSONObject(userEvent.getEventData()));
                        eventObjArray.put(eventObj);
                    }
                    failedNotificationsRequest.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
                    failedNotificationsRequest.put(RestUtils.TAG_USER_EVENTS, eventObjArray);
                    failedNotificationsRequest.put(RestUtils.TAG_COUNT, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.LOG_USER_EVENT_API, failedNotificationsRequest.toString(), "TAG_NOTIFICATIONS_SYNC", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        try {
                            JSONObject responseObj = new JSONObject(successResponse);
                            if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                realmManager.deleteAllUserEventsDataFromDB();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                    }
                }).sendSinglePartRequest();
            }
        }

    }

    public void processDashboardErrorResponse(String errorResponse, boolean loadPreData) {
        if (getActivity() != null && isAdded()) {
            if (loadPreData) {
                if (realmManager.checkFeedExists(-2, 0)) {
                    realmManager.removeFeedDataByFeedId(realm, -2);
                    dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                }
            } else {
                if (shimmerContentLayout.getVisibility() == View.VISIBLE) {
                    shimmerContainer.stopShimmerAnimation();
                    shimmerContainer1.stopShimmerAnimation();
                    shimmerContentLayout.setVisibility(View.GONE);
                }
                mRecyclerView.setVisibility(View.VISIBLE);
                processChannelsData(realmManager, postUpdateLayout, feeds_errmsg);

                //hideProgress();
            }
            if (swipeContainer != null) {
                swipeContainer.setRefreshing(false);
            }
            if (!loadPreData && Foreground.get().isForeground() && isFragmentInForeground) {
                if (realmManager.getFeedDataFromDB().size() == 0) {
                    displayErrorPage(errorResponse);
                }
            }

        }
    }

    public void processDashboardResponse(String successResponse, boolean loadPreData, boolean refreshData, boolean isDashboard) {
        if (shimmerContentLayout != null && shimmerContentLayout.getVisibility() == View.VISIBLE) {
            shimmerContainer.stopShimmerAnimation();
            shimmerContainer1.stopShimmerAnimation();
            shimmerContentLayout.setVisibility(View.GONE);
        }
        mRecyclerView.setVisibility(View.VISIBLE);
        stopTime = System.currentTimeMillis();
        totaDiff = (float) (stopTime - startTime) / 1000;
        if (BuildConfig.FLAVOR.equals(WhitecoatsFlavor.QA.getName())) {
            FlurryAgent.logEvent("QA: <DashBoard Service> : " + totaDiff);
            AppUtil.captureSentryMessage("QA: <DashBoard Service> : " + totaDiff);
        } else if (BuildConfig.FLAVOR.equals(WhitecoatsFlavor.STAGE.getName())) {
            FlurryAgent.logEvent("Staging: <DashBoard Service> :  " + totaDiff);
            AppUtil.captureSentryMessage("Staging: <DashBoard Service> : " + totaDiff);
        } else if (BuildConfig.FLAVOR.equals(WhitecoatsFlavor.PROD.getName())) {
            FlurryAgent.logEvent("Prod: <DashBoard Service> :  " + totaDiff);
            AppUtil.captureSentryMessage("Prod: <DashBoard Service> : " + totaDiff);
        } else {
            FlurryAgent.logEvent("Local: <DashBoard Service> :  " + totaDiff);
            AppUtil.captureSentryMessage("Local: <DashBoard Service> : " + totaDiff);
        }
        if (swipeContainer != null) {
            swipeContainer.setRefreshing(false);
        }
        JSONObject jsonObject = null;
        JSONObject completeJson = null;
        JSONObject networkChannelObj = null;
        try {
            jsonObject = new JSONObject(successResponse);
            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                if (jsonObject.has(RestUtils.TAG_DATA)) {
                    associationListJson = new ArrayList<>();
                    completeJson = jsonObject.optJSONObject(RestUtils.TAG_DATA);

                    Long dateMille = completeJson.optLong("onboard_date");

                    String onboardDate = AppUtil.parsePersonalizeDateIntoDays(dateMille);
                    Log.d("noOfDays", "days" + onboardDate);
                    mySharedPref.savePref(MySharedPref.PREF_ON_BOARD_DATE, onboardDate);

                    if (newUpdatesBtn != null && newUpdatesBtn.getVisibility() == View.VISIBLE) {
                        newUpdatesBtn.setVisibility(View.GONE);
                    }
                    if (mySharedPref != null && mySharedPref.getPrefsHelper() != null) {
                        mySharedPref.getPrefsHelper().savePref(MySharedPref.REQUEST_PG_INDEX, pageIndex);
                        if (completeJson.has("is_user_verified")) {
                            if (mySharedPref.getPrefsHelper().isPrefExists(MySharedPref.PREF_IS_USER_VERIFIED)) {
                                int userVerifyStatus = completeJson.optInt("is_user_verified");
                                if ((int) mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_IS_USER_VERIFIED) != userVerifyStatus) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setCancelable(false);
                                    if (userVerifyStatus == 3) {
                                        builder.setMessage("Congrats! Your verification is complete and you now have full access to WhiteCoats");
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create().show();
                                    } else if (userVerifyStatus == 1) {
                                        builder.setMessage("Upload a Valid ID like MCI certificate/ Medical Degree/ association ID/ Corporate ID to verify your profile");
                                        builder.setPositiveButton("Verify Now", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent verifyLikeIntent = new Intent(getActivity(), MCACardUploadActivity.class);
                                                getActivity().startActivity(verifyLikeIntent);
                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }).create().show();
                                    }
                                }
                            }
                            mySharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, completeJson.optInt("is_user_verified", 3));
                        }
                        if (completeJson.has("isCommunityVerified")) {
                            mySharedPref.savePref(MySharedPref.PREF_IS_COMMUNITY_VERIFIED, completeJson.optBoolean("isCommunityVerified", true));
                        }
                    }
                    if (completeJson.has("is_user_accessible")) {
                        AppConstants.IS_USER_VERIFIED = completeJson.optBoolean("is_user_accessible", false);
                    }
                    if (completeJson.has("listofChannels") && completeJson.optJSONArray("listofChannels") != null) {
                        isDisplayPostIcon = false;
                        AppConstants.unreadChannelsList.clear();
                        chooseCommunityJsonObj = new JSONArray();
                        realmManager.insertOrUpdateChannelsListInDB("listofChannels", completeJson.optJSONArray("listofChannels").toString());

                        JSONArray updatedChannelsList = realmManager.getChannelsListFromDB("listofChannels");
                        StringBuffer associationName = new StringBuffer();
                        for (int x = 0; x < updatedChannelsList.length(); x++) {
                            JSONObject currentChannelObj = updatedChannelsList.optJSONObject(x);
                            associationName.append(currentChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                            if (x != updatedChannelsList.length() - 1) {
                                associationName.append(",");
                            }

                            channelsList.put(currentChannelObj.optInt(RestUtils.CHANNEL_ID), currentChannelObj);
                            if ((currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Community")) && currentChannelObj.optBoolean("is_preferred_channel")) {
                                associationListJson.add(currentChannelObj);
                            }
                            if (currentChannelObj.optBoolean("is_admin")) {
                                JSONObject jsonObj = new JSONObject();
                                jsonObj.put(RestUtils.CHANNEL_ID, currentChannelObj.optInt(RestUtils.CHANNEL_ID));
                                jsonObj.put(RestUtils.FEED_PROVIDER_NAME, currentChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                                chooseCommunityJsonObj.put(currentChannelObj);
                                isDisplayPostIcon = true;
                            }
                            if (currentChannelObj.has("hasUnread") && currentChannelObj.optBoolean("hasUnread") && currentChannelObj.optBoolean(RestUtils.TAG_IS_SUBSCRIBED)) {
                                AppConstants.unreadChannelsList.add(currentChannelObj.optInt(RestUtils.CHANNEL_ID));
                            }
                            if (currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(getString(R.string.label_network))) {
                                networkChannelObj = currentChannelObj;
                                networkChannelId = currentChannelObj.optInt(RestUtils.CHANNEL_ID);
                                isDisplayPostIcon = true;
                            }
                        }

                        if (refreshData) {
                            feedCount = 0;
                        }
                        if ((loadPreData || refreshData) && refreshDataListener != null) {
                            refreshDataListener.onRefresh(associationListJson, chooseCommunityJsonObj, successResponse, networkChannelObj, isDashboard, loadPreData);
                            if (refreshData) {
                                return;
                            }
                        }

                        if (refreshData && dashboradAdapter != null) {
                            dashboradAdapter.clearHorizontalListDataObjects();
                        }

                        if (!loadPreData) {
                            AppUtil.logUserVerificationInfoEvent(mySharedPref.getPref(MySharedPref.PREF_IS_USER_VERIFIED, 1));
                        }
                        if (associationName.length() > 0) {
                            Bundle userInfo = new Bundle();
                            HashMap<String, Object> others = new HashMap<>();
                            others.put("AssociationNames", associationName.toString());
                            userInfo.putSerializable(BKUserInfo.BKUserData.OTHERS, others);
                            BrandKinesis bkInstance = BrandKinesis.getBKInstance();
                            if (bkInstance != null) {
                                bkInstance.setUserInfoBundle(userInfo, new BKUserInfoCallback() {
                                    @Override
                                    public void onUserInfoUploaded(boolean uploadStatus) {
                                        Log.d("UPSHOT_USER_LOG", "result :" + uploadStatus);
                                    }
                                });
                            }
                        }
                    }
                    JSONArray feedsArray = completeJson.optJSONArray("feed_data");
                    if (loadPreData) {
                        if (realmManager.checkFeedExists(-2, 0)) {
                            realmManager.removeFeedDataByFeedId(realm, -2);
                            dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                        }
                        if (completeJson.has("feed_data") && completeJson.optJSONArray("feed_data") != null && completeJson.optJSONArray("feed_data").length() > 0) {
                            if (newUpdatesBtn != null && newUpdatesBtn.getVisibility() == View.GONE) {
                                if (mLayoutManager != null) {
                                    int visibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                                    if (visibleItemPosition == 0) {
                                        newUpdatesBtn.setVisibility(View.GONE);
                                    } else {
                                        newUpdatesBtn.bringToFront();
                                        newUpdatesBtn.invalidate();
                                        newUpdatesBtn.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    newUpdatesBtn.bringToFront();
                                    newUpdatesBtn.invalidate();
                                    newUpdatesBtn.setVisibility(View.VISIBLE);
                                }
                            }

                            for (int j = feedsArray.length() - 1; j >= 0; j--) {
                                JSONObject feedObj = feedsArray.optJSONObject(j);
                                int feedId = feedObj.optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID);
                                int channelId = feedObj.optInt(RestUtils.CHANNEL_ID);
                                realmManager.insertFeedDataIntoDB(feedId, feedObj, true);
                                if (feedObj.has("feed_next_placement")) {
                                    processHorizontalListData(feedObj.optString("feed_next_placement"), false, feedId, channelId);
                                }
                            }


                        }

                    } else {
                        if (completeJson.has("feed_data") && completeJson.optJSONArray("feed_data") != null && completeJson.optJSONArray("feed_data").length() > 0) {
                            dashboradAdapter.setLastPositionToDefalut();
                            clearRealmData(realm);
                            dashboradAdapter.notifyDataSetChanged();

                            for (int i = 0; i < feedsArray.length(); i++) {
                                JSONObject feedObj = feedsArray.optJSONObject(i);
                                int feedId = feedObj.optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID);
                                int channelId = feedObj.optInt(RestUtils.CHANNEL_ID);

                                realmManager.insertFeedDataIntoDB(feedId, feedObj, false);
                                if (feedObj.has("feed_next_placement")) {
                                    processHorizontalListData(feedObj.optString("feed_next_placement"), false, feedId, channelId);
                                }
                                feedCount++;
                                if (midPostSlotInfo != null && midPostSlotInfo.getOccurance() != 0 && feedCount == occurance_count && (midPostSlotInfo.getMax_limit() == 0 || realmManager.getActualFeedCount() <= midPostSlotInfo.getMax_limit())) {
                                    int position = realmManager.getFeedDBPosition(realmManager.getRealmFeedInfoObj(feedId, channelId));
                                    realmManager.insertHorizontalListDataIntoDB(feedId, position + 1, -6);
                                    occurance_count = occurance_count + midPostSlotInfo.getOccurance();
                                }
                            }


                            if (isDashboard || refreshData) {
                            }

                        }
                    }

                    if (mRecyclerView != null) {
                        loadDocereeAdView();
                        dashboradAdapter.notifyDataSetChanged();
                        dashboardStatus = true;
                    }
                }
                postUpdateLayout.setVisibility(View.GONE);
                if (realmManager.getFeedDataFromDB().size() == 0) {
                    feeds_errmsg.setVisibility(View.VISIBLE);
                    dashboardParentLayout.setVisibility(View.GONE);
                } else {
                    feeds_errmsg.setVisibility(View.GONE);
                    dashboardParentLayout.setVisibility(View.VISIBLE);
                }

                if (getActivity() != null) {
                    Intent intent = new Intent("update_channels_count");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
                if (completeJson.has("is_user_accessible") && !completeJson.optBoolean("is_user_accessible") && AppUtil.getUserVerifiedStatus() == 1) {
                    if (getActivity() != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(getResources().getString(R.string.please_verify_whitecoats));
                        builder.setCancelable(false);
                        builder.setPositiveButton("Verify Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent verifyIntent = new Intent(getActivity(), MCACardUploadActivity.class);
                                verifyIntent.putExtra("mandatory_verify", true);
                                getActivity().startActivity(verifyIntent);
                            }
                        }).create().show();
                    }
                    return;
                }

            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                if (loadPreData) {
                    if (realmManager.checkFeedExists(-2, 0)) {
                        realmManager.removeFeedDataByFeedId(realm, -2);
                        dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                    }
                }
                displayErrorPage(successResponse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayErrorPage(String errorResponse) {
        ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
    }

    private void processChannelsData(RealmManager realmManager, ViewGroup postUpdateLayout, TextView feeds_errmsg) {
        try {
            JSONArray updatedChannelsList = realmManager.getChannelsListFromDB("listofChannels");
            if (updatedChannelsList.length() > 0) {
                channelsList.clear();
                if (realmManager.checkFeedExists(-3, 0)) {
                    realmManager.deleteFeedFromDB(-3, 0);
                }
                for (int x = 0; x < updatedChannelsList.length(); x++) {
                    JSONObject currentChannelObj = updatedChannelsList.optJSONObject(x);
                    channelsList.put(currentChannelObj.optInt(RestUtils.CHANNEL_ID), currentChannelObj);
                    if (currentChannelObj.optBoolean("is_admin")) {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put(RestUtils.CHANNEL_ID, currentChannelObj.optInt("channel_id"));
                        jsonObj.put("feed_provider_name", currentChannelObj.optString("feed_provider_name"));
                        chooseCommunityJsonObj.put(currentChannelObj);
                        isDisplayPostIcon = true;
                    }
                    if (currentChannelObj.has("hasUnread") && currentChannelObj.optBoolean("hasUnread")) {
                        AppConstants.unreadChannelsList.add(currentChannelObj.optInt(RestUtils.CHANNEL_ID));
                    }
                    if (currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(getString(R.string.label_network))) {
                        networkChannelId = currentChannelObj.optInt(RestUtils.CHANNEL_ID);
                        isDisplayPostIcon = true;
                    }
                }
                postUpdateLayout.setVisibility(View.GONE);
            } else {
                feeds_errmsg.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //endregion

    //load doceree ad view

    private void loadDocereeAdView() {
        RealmResults<RealmAdSlotInfo> adSlotResults = realmManager.getAdSlotInfoByLocation(SlotLocationType.DASHBOARD_TOP_SLOT.getType());
        if (adSlotResults.size() > 0) {
            dashboradAdapter.setTopSlotAdInfo(realm.copyFromRealm(adSlotResults.get(0)));
            realmManager.insertTestFeedDataIntoDB(realm, -6, true);
        }

    }

    //region onCreateView
    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, getString(R.string._onCreateView));
        Log.e("FRAG_FLOW", "oncreateview");
        View view = inflater.inflate(R.layout.recyclelayout, container, false);
        if (getArguments() != null) {
            //successResponse = getArguments().getString("response");
            reloadData = getArguments().getBoolean("refreshData");
        }
        makepost = (ImageButton) view.findViewById(R.id.dashboardmakepost);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.channel_recycler_view);
        profilePic = (RoundedImageView) view.findViewById(R.id.profile_pic_timeline);
        newUpdatesBtn = (Button) view.findViewById(R.id.new_updates_btn);
        postUpdateLayout = (ViewGroup) view.findViewById(R.id.post_update_dashboard);
        shimmerContentLayout = (ViewGroup) view.findViewById(R.id.shimmer_content_layout);
        shimmerContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerContainer1 = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container1);
        postUpdateHint = (TextView) view.findViewById(R.id.postUpdateLabel);
        postUpdateLabel_lay = (LinearLayout) view.findViewById(R.id.postUpdateLabel_lay);
        dashboardParentLayout = (LinearLayout) view.findViewById(R.id.dashboardParentLayout);
        feeds_errmsg = (TextView) view.findViewById(R.id.feeds_errormsg);
        addPicSymbol = (ImageView) view.findViewById(R.id.add_pic_symbol);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new CustomLinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemViewCacheSize(30);
        feedDataList = realmManager.getFeedDataFromDB();
        dashboradAdapter = new DashboardFeedsAdapter(getActivity(), feedDataList, login_doc_id, mRecyclerView, channelsList /*trendingCategoriesList*/, realmManager.getDocSalutation(realm) + " " + docName, new OnSocialInteractionListener() {
            @Override
            public void onSocialInteraction(JSONObject subItem, int channel_id, Boolean isLiked, int mFeedTypeId) {
                makeLikeServiceCall(subItem, channel_id, isLiked, mFeedTypeId);
            }

            @Override
            public void onUIupdateForLike(JSONObject subItem, int channel_id, Boolean isLiked, int mFeedTypeId) {

            }

            @Override
            public void onReportSpam(String spamClick, int feedId, int docId) {
                if (spamClick.equalsIgnoreCase("SPAM_CLICK")) {
                    BottomSheetDialogReportSpam bottomSheet = new BottomSheetDialogReportSpam();
                    Bundle bundle = new Bundle();
                    bundle.putInt("feedId", feedId);
                    bundle.putInt("docId", docId);
                    bottomSheet.setArguments(bundle);
                    bottomSheet.show(requireActivity().getSupportFragmentManager(),
                            "ModalBottomSheetReportSpam");

                }
            }

        }, this, this, selectedTabPosition);
        dashboradAdapter.setHasStableIds(true);
        dashboradAdapter.setOnFeedItemClickListener(this);
        mRecyclerView.setAdapter(dashboradAdapter);

        adSlotResults = realmManager.getAdSlotInfoByLocation(SlotLocationType.MID_POST_SLOT.getType());
        if (adSlotResults.size() > 0) {
            midPostSlotInfo = realm.copyFromRealm(adSlotResults.get(0));
            dashboradAdapter.setMidPostAdInfo(midPostSlotInfo);
            occurance_count = midPostSlotInfo.getOccurance();
        }
        if (dashboradAdapter != null) {
            dashboradAdapter.notifyDataSetChanged();
            dashboradAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (AppUtil.isConnectingToInternet(getActivity())) {
                        pageIndex = mySharedPref.getPrefsHelper().getPref(MySharedPref.REQUEST_PG_INDEX, 0);
                        pageIndex++;
                        mySharedPref.getPrefsHelper().savePref(MySharedPref.REQUEST_PG_INDEX, pageIndex);
                        realmManager.insertTestFeedDataIntoDB(realm, -1, false);
                        mRecyclerView.post(new Runnable() {
                            public void run() {
                                dashboradAdapter.notifyItemInserted(realmManager.getFeedDataFromDB().size());
                            }
                        });
                        try {
                            long lastFeedTime = 0;
                            if (realmManager.getFeedDataFromDB() != null) {
                                for (int i = realmManager.getFeedDataFromDB().size() - 1; i >= 0; i--) {
                                    if (realmManager.getFeedDataFromDB().get(i).getFeedId() > 0 && realmManager.getFeedDataFromDB().get(i).getChannelId() > 0) {
                                        lastFeedTime = realmManager.getFeedDataFromDB().get(i).getCreatedOrUpdatedTime();
                                        break;
                                    }
                                }

                            }
                            requestData.put("last_feed_time", lastFeedTime);
                            requestData.put("load_next", true);
                            requestData.put("pg_num", mySharedPref.getPrefsHelper().getPref(MySharedPref.REQUEST_PG_INDEX));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HashMap<String, Object> map = new HashMap<>();
                        map.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
                        map.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
                        map.put("pg_num", mySharedPref.getPrefsHelper().getPref(MySharedPref.REQUEST_PG_INDEX));
                        AppUtil.logUserEventWithHashMap("DashboardScroll", basicInfo.getDoc_id(), map, getActivity());
                        new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.GET_DASHBOARD_DATA, requestData.toString(), "FEEDS_LOAD_MORE", new OnReceiveResponse() {
                            @Override
                            public void onSuccessResponse(String successResponse) {
                                if (getActivity() != null && isAdded()) {
                                    realmManager.removeFeedDataByFeedId(realm, -1);
                                    dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                                    JSONObject jsonObject = null;
                                    JSONObject completeJson = null;
                                    try {
                                        jsonObject = new JSONObject(successResponse);
                                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                            if (jsonObject.has(RestUtils.TAG_DATA)) {
                                                completeJson = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                                if (completeJson.has("feed_data") && completeJson.optJSONArray("feed_data") != null) {
                                                    if (completeJson.optJSONArray("feed_data").length() > 0) {
                                                        int totalItemIndex = realmManager.getFeedDataFromDB().size() - 1;
                                                        JSONArray feedsArray = completeJson.optJSONArray("feed_data");
                                                        for (int i = 0; i < feedsArray.length(); i++) {
                                                            JSONObject feedObj = feedsArray.optJSONObject(i);
                                                            int feedId = feedObj.optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID);
                                                            int channelId = feedObj.optInt(RestUtils.CHANNEL_ID);
                                                            realmManager.insertFeedDataIntoDB(feedId, feedObj, false);
                                                            if (feedObj.has("feed_next_placement")) {
                                                                processHorizontalListData(feedObj.optString("feed_next_placement"), false, feedId, channelId);
                                                            }

                                                            feedCount++;
                                                            if (midPostSlotInfo != null && midPostSlotInfo.getOccurance() != 0 && feedCount == occurance_count && (midPostSlotInfo.getMax_limit() == 0 || realmManager.getActualFeedCount() <= midPostSlotInfo.getMax_limit())) {
                                                                int position = realmManager.getFeedDBPosition(realmManager.getRealmFeedInfoObj(feedId, channelId));
                                                                realmManager.insertHorizontalListDataIntoDB(feedId, position + 1, -6);
                                                                occurance_count = occurance_count + midPostSlotInfo.getOccurance();
                                                            }

                                                        }
                                                        if (mRecyclerView != null) {
                                                            dashboradAdapter.notifyItemRangeInserted(totalItemIndex, realmManager.getFeedDataFromDB().size());
                                                        }
                                                    } else {
                                                        if (pageIndex > 0) {
                                                            pageIndex--;
                                                            mySharedPref.getPrefsHelper().savePref(MySharedPref.REQUEST_PG_INDEX, pageIndex);
                                                        }
                                                        Toast.makeText(getActivity(), "No more feeds", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                if (mRecyclerView != null) {
                                                    dashboradAdapter.setLoaded();
                                                    dashboardStatus = true;
                                                }
                                            }
                                        } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                            displayErrorPage(successResponse);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onErrorResponse(String errorResponse) {
                                if (getActivity() != null && isAdded()) {

                                    dashboradAdapter.setLoaded();
                                    if (pageIndex > 0) {
                                        pageIndex--;
                                        mySharedPref.getPrefsHelper().savePref(MySharedPref.REQUEST_PG_INDEX, pageIndex);
                                    }
                                    realmManager.removeFeedDataByFeedId(realm, -1);
                                    dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                                    Toast.makeText(getActivity(), "Unable to connect server.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).sendSinglePartRequest();
                    }
                }
            });
            dashboradAdapter.setOnScrollTopListener(new OnScrollTopListener() {
                @Override
                public void OnScrollTop() {
                    if (newUpdatesBtn != null && newUpdatesBtn.getVisibility() == View.VISIBLE) {
                        newUpdatesBtn.setVisibility(View.GONE);
                    }
                }
            });

            //handle load more horizontal list data

            dashboradAdapter.setOnLoadMoreHorizontalItemsListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (lastFeedId != -1) {
                        horizontalItemPageIndex++;
                        int position = realmManager.getFeedDBPosition(realmManager.getRealmFeedInfoObj(-4, 0));
                        //processHorizontalListData("Suggested", position, false, true);
                    }
                }
            });
        }

        newUpdatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUpdatesBtn.setVisibility(View.GONE);
                mRecyclerView.scrollToPosition(0);
            }
        });
        postUpdateLabel_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
                data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
                AppUtil.logUserEventWithHashMap("DashboardFeedCreationInitiation", basicInfo.getDoc_id(), data, getActivity());
                if (mySharedPref != null && mySharedPref.getPref(MySharedPref.PREF_IS_USER_VERIFIED, 3) == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                    if (chooseCommunityJsonObj.length() == 0) {
                        JSONObject channelObj = channelsList.get(networkChannelId);
                        viewCreatePost(channelObj);
                    } else {
                        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                        final View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_modal, null);
                        ViewGroup network_selection_layout = (ViewGroup) sheetView.findViewById(R.id.network_selection_layout);
                        final ListView communityListView = (ListView) sheetView.findViewById(R.id.communityList);
                        /*
                         * If No network channel is there, hide "post to network" layout
                         */
                        if (networkChannelId != -1) {
                            network_selection_layout.setVisibility(View.VISIBLE);
                        } else {
                            network_selection_layout.setVisibility(View.GONE);
                        }

                        final ArrayList<JSONObject> communityList = new ArrayList<JSONObject>();
                        int len = chooseCommunityJsonObj.length();
                        for (int i = 0; i < len; i++) {
                            try {
                                communityList.add(chooseCommunityJsonObj.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        /*
                         * Sort the list in alphabetical order
                         */
                        Collections.sort(communityList, new Comparator<JSONObject>() {

                            @Override
                            public int compare(JSONObject lhs, JSONObject rhs) {
                                try {
                                    return (lhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase().compareTo(rhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });
                        /*
                         * Set adapter
                         */
                        CommunityListAdapter communityListAdapter = new CommunityListAdapter(getActivity(), communityList);
                        communityListView.setAdapter(communityListAdapter);
                        mBottomSheetDialog.setContentView(sheetView);
                        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) sheetView.getParent());
                        //mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        mBottomSheetDialog.show();
                        communityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                data.put(RestUtils.EVENT_COMMUNITY_ID, communityList.get(position).optInt(RestUtils.CHANNEL_ID));
                                AppUtil.logUserEventWithHashMap("CommunityShareInitiation", basicInfo.getDoc_id(), data, getActivity());
                                mBottomSheetDialog.dismiss();
                                viewCreatePost(communityList.get(position));
                            }
                        });
                        network_selection_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppUtil.logUserEventWithHashMap("ShareWithEveryOneInitiation", basicInfo.getDoc_id(), data, getActivity());
                                mBottomSheetDialog.dismiss();
                                /*
                                 * Send customized params when sharing to Network channel
                                 */
                                JSONObject channelObj = channelsList.get(networkChannelId);
                                viewCreatePost(channelObj);
                            }
                        });
                    }

                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(getActivity(), getActivity().getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(getActivity(), getActivity().getString(R.string.mca_uploaded_but_not_verified));
                }
            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.logUserEventWithDocIDAndSplty("DashboardOwnProfileIcon", basicInfo, getActivity());
                Intent intent_profile = new Intent(getActivity(), ProfileViewActivity.class);
                startActivity(intent_profile);
            }
        });
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppUtil.isConnectingToInternet(getActivity())) {
                    try {
                        AppConstants.unreadChannelsList.clear();
                        pageIndex = 0;
                        requestData.put("last_feed_time", 0);
                        requestData.put("load_next", true);
                        requestData.put("pg_num", pageIndex);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //requestForCategoriesData();
                    requestForAdSlots();
                    requestForDashBoardData(requestData, false, true);
                } else {
                    if (realmManager.getFeedDataFromDB().size() == 0) {
                        feeds_errmsg.setVisibility(View.VISIBLE);
                    } else {
                        feeds_errmsg.setVisibility(View.GONE);
                    }
                    if (swipeContainer != null) {
                        swipeContainer.setRefreshing(false);
                    }
                }
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        postUpdateLayout.setVisibility(View.GONE);
        if (dashBoardCallNeeded && reloadData) {
            dashBoardCallNeeded = false;
            if (AppUtil.isConnectingToInternet(getActivity())) {
                shimmerContentLayout.setVisibility(View.VISIBLE);
                shimmerContainer.startShimmerAnimation();
                shimmerContainer1.startShimmerAnimation();
                requestForDashBoardData(requestData, false, false);
                //requestForCategoriesData();
            } else {
                shimmerContentLayout.setVisibility(View.GONE);
                if (realmManager.getFeedDataFromDB().size() == 0) {
                    feeds_errmsg.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    processChannelsData(realmManager, postUpdateLayout, feeds_errmsg);
                }
            }
        } else if (successResponse != null && !successResponse.isEmpty()) {
            processDashboardResponse(successResponse, false, false, true);
        } else {
            processChannelsData(realmManager, postUpdateLayout, feeds_errmsg);
            mRecyclerView.setVisibility(View.VISIBLE);
            dashboradAdapter.notifyDataSetChanged();
        }/*else{
            processDashboardResponse(successResponse, false,false,true);
        }*/
        return view;
    }

    private void requestForAdSlots() {
        new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.GET_AD_SLOT_DEFINITIONS, getAdSlotsRequest(), "GET_AD_SLOTS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (successResponse != null && !successResponse.isEmpty()) {
                    try {
                        JSONObject responseObj = new JSONObject(successResponse);
                        if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            JSONObject data = responseObj.getJSONObject(RestUtils.TAG_DATA);
                            if (data != null && data.has("ads_definitions")) {
                                realmManager.clearAdSlotDefinitionsData();
                                realmManager.addOrUpdateAdslot(data.optJSONArray("ads_definitions"));

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onErrorResponse(String errorResponse) {

            }
        }).sendSinglePartRequest();
    }

    private String getAdSlotsRequest() {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_USER_ID, login_doc_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj.toString();
    }


    //coach mark for first bookmark
    private void presentShowcaseSequence(boolean isBookmarkClicked) {
        if (getActivity() == null) {
            return;
        }
        if (AppConstants.COACHMARK_INCREMENTER > 2) {
            MaterialShowcaseView.resetSingleUse(getActivity(), "Sequence_Share");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500); // half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), "Sequence_Share");
            sequence.setConfig(config);
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_dashboard);
            //Toolbar toolbar = (Toolbar) act.getSupportActionBar().getCustomView();
            if (toolbar != null) {
                View menuLayout = toolbar.getChildAt(1);
                if (menuLayout instanceof ViewGroup) {
                    // index here depends on actual toolbar layout
                    View menu3dots = ((ViewGroup) menuLayout).getChildAt(1);
                    //View view = getActivity().findViewById(R.id.bookmarks); //get the reference
                    if (!editor.getBoolean("contextMenu", false)) {
                        sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(getActivity()) //instantiate the material showcase view
                                        .setTarget(menu3dots) // set target to view
                                        .setDismissText("GOT IT") // set the dismiss text
                                        .setDismissTextColor(Color.parseColor("#00a76d"))
                                        .setMaskColour(Color.parseColor("#CC231F20"))
                                        .setContentText(R.string.tap_to_coach_mark_context_menu).setListener(new IShowcaseListener() {
                                            @Override
                                            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                                editor.edit().putBoolean("contextMenu", true).commit();
                                            }

                                            @Override
                                            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                            }
                                        })
                                        .setMaskColour(Color.parseColor("#CC231F20"))
                                        .withCircleShape()
                                        .build()
                        );
                    }
                    sequence.start();
                }
            }
        }
    }

    //coach mark share
    private void presentShowcaseSequence() {
        if (getActivity() == null) {
            return;
        }
        if (AppConstants.COACHMARK_INCREMENTER > 3) {
            MaterialShowcaseView.resetSingleUse(getActivity(), "Sequence_Share");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500); // half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), "Sequence_Share");
            sequence.setConfig(config);
            if (!editor.getBoolean("dashboard_share", false)) {
                if (mRecyclerView.getChildAt(0) != null) {
                    if (mRecyclerView.getChildAt(0).findViewById(R.id.share_layout_dashboard) != null) {
                        sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(getActivity())
                                        .setTarget(mRecyclerView.getChildAt(0).findViewById(R.id.share_layout_dashboard))
                                        .setDismissText("Got it")
                                        .setDismissTextColor(Color.parseColor("#00a76d"))
                                        .setMaskColour(Color.parseColor("#CC231F20"))
                                        .setContentText(R.string.tap_to_coach_mark_share).setListener(new IShowcaseListener() {
                                            @Override
                                            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                                editor.edit().putBoolean("dashboard_share", true).commit();
                                            }

                                            @Override
                                            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                            }
                                        })
                                        .withCircleShape()
                                        .build()
                        );
                    }
                }
                sequence.start();
            }
        }
        MaterialShowcaseView.resetSingleUse(getActivity(), "Sequence_Share");
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), "Sequence_Share");
        sequence.setConfig(config);
        if (AppConstants.COACHMARK_INCREMENTER > 2) {
            if (!editor.getBoolean("dashboard_bookmark", false)) {
                if (mRecyclerView.getChildAt(0) != null) {
                    if (mRecyclerView.getChildAt(0).findViewById(R.id.bookmark_dashboard) != null) {
                        sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(getActivity())
                                        .setTarget(mRecyclerView.getChildAt(0).findViewById(R.id.bookmark_dashboard))
                                        .setDismissText("Got it")
                                        .setDismissTextColor(Color.parseColor("#00a76d"))
                                        .setMaskColour(Color.parseColor("#CC231F20"))
                                        .setContentText(R.string.tap_to_coach_mark_bookmark).setListener(new IShowcaseListener() {
                                            @Override
                                            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                                editor.edit().putBoolean("dashboard_bookmark", true).commit();
                                            }

                                            @Override
                                            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                            }
                                        })
                                        .withCircleShape()
                                        .build()
                        );
                    }
                }
                sequence.start();
            }
        }
    }


    private void viewCreatePost(JSONObject channelObj) {
        Log.i(TAG, "viewCreatePost()");
        Intent intent = new Intent(getActivity(), CreatePostActivity.class);
        intent.putExtra(RestUtils.NAVIGATATION, "Dashboard");
        if (channelObj != null)
            intent.putExtra(RestUtils.KEY_SELECTED_CHANNEL, channelObj.toString());
        startActivity(intent);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }


    @Override
    public void onBookmark(boolean isBookmarked, int feedID, boolean isAutoRefresh, JSONObject socialInteractionObj) {
        Log.i(TAG, "onBookmark(boolean isBookmarked,int feedID)");
        try {
            int position = 0;
            boolean feedExistsInDashboard = false;
            int len = feedDataList.size();
            if (len == 0) {
                return;
            }
            for (int i = 0; i < len; i++) {
                if (feedDataList.get(i).getFeedId() == feedID) {
                    position = i;
                    feedExistsInDashboard = true;
                    break;
                }
            }
            if (feedExistsInDashboard) {
                String feedJson = feedDataList.get(position).getFeedsJson();
                JSONObject feedInfoObj = new JSONObject(feedJson).optJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_IS_BOOKMARKED, isBookmarked);
                JSONObject finalFeedJson = new JSONObject(feedJson).put("feed_info", feedInfoObj);
                realm.beginTransaction();
                feedDataList.get(position).setFeedsJson(finalFeedJson.toString());
                realm.commitTransaction();
                realmManager.UpdateFeedWithSocialInteraction(feedID, socialInteractionObj);
                dashboradAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    @Override
    public void notifyUIWithFeedSurveyResponse(int feedId, JSONObject surveyResponse) {
        realmManager.UpdateFeedWithSurveyResponse(feedId, surveyResponse);
        if (dashboradAdapter != null) {
            dashboradAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyUIWithFeedWebinarResponse(int feedId, JSONObject webinarRegisterResponse) {
        realmManager.UpdateFeedWithWebinarRegisterResponse(feedId, webinarRegisterResponse);
        if (dashboradAdapter != null) {
            dashboradAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void notifyUIWithJobApplyStatus(int feedId, JSONObject jobApplyResponse) {
        realmManager.UpdateFeedWithJobApplyStatus(feedId, true);
        if (dashboradAdapter != null) {
            dashboradAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClickListener(JSONObject feedJson, boolean isNetworkChannel, int channelID, String channelName, View sharedView, int position) {
        JSONObject feedInfoObj = feedJson.optJSONObject(RestUtils.TAG_FEED_INFO);
        if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
            Intent in = new Intent(getActivity(), ContentFullView.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelID);
            in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelName);
            in.putExtra(RestUtils.TAG_CONTENT_OBJECT, feedJson.toString());
            if (sharedView != null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                ActivityCompat.startActivity(getActivity(), in, options.toBundle());
            } else {
                getActivity().startActivity(in);
            }
        } else if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
            Intent in = new Intent(getActivity(), JobFeedCompleteView.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelID);
            in.putExtra(RestUtils.CHANNEL_NAME, channelName);
            in.putExtra(RestUtils.TAG_POSITION, position);
            in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, isNetworkChannel);
            in.putExtra(RestUtils.TAG_FEED_OBJECT, feedJson.toString());
            if (sharedView != null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                ActivityCompat.startActivity(getActivity(), in, options.toBundle());
            } else {
                getActivity().startActivity(in);
            }
        } else {
            Intent in = new Intent(getActivity(), FeedsSummary.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelID);
            in.putExtra(RestUtils.CHANNEL_NAME, channelName);
            in.putExtra(RestUtils.TAG_POSITION, position);
            in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, isNetworkChannel);
            in.putExtra(RestUtils.TAG_FEED_OBJECT, feedJson.toString());
            if (sharedView != null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                ActivityCompat.startActivity(getActivity(), in, options.toBundle());
            } else {
                getActivity().startActivity(in);
            }
        }
    }

    @Override
    public void isBookmarkClicked(final boolean isBookmarked) {
        if (isBookmarked) {

        }
    }

    @Override
    public void onProfileClick(View v, Intent in) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        in.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        getActivity().startActivity(in);
    }


    @Override
    public void onProfileUpdate(BasicInfo basicInfo) {
        if (getActivity() != null && isAdded()) {
            loadProfilePic();
        }
    }

    public void setDataRefreshListener(OnRefreshDashboardData dashboardDataRefresh) {
        refreshDataListener = dashboardDataRefresh;
    }

    public void setDashboardResponse(String response) {
        successResponse = response;
    }


    //endregion


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editor = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DELETE_ACTION) {
            if (data != null) {
                try {
                    JSONObject post_obj = new JSONObject(data.getExtras().getString("POST_OBJ"));
                    if (post_obj.has("post_id")) {
                        realmManager.deleteFeedFromDB(post_obj.optInt("post_id"), post_obj.optInt(RestUtils.CHANNEL_ID));
                        dashboradAdapter.notifyItemRemoved(realmManager.getFeedDataFromDB().size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Feeds_Fragment.PREFERENCE_ACTION) {
            JSONObject requestData = new JSONObject();
            try {
                pageIndex = 0;
                requestData.put(RestUtils.TAG_DOC_ID, login_doc_id);
                requestData.put("last_feed_time", 0);
                requestData.put("last_open", 0);
                requestData.put("last_close", 0);
                requestData.put("load_next", true);
                requestData.put("pg_num", pageIndex);
                if (AppUtil.isConnectingToInternet(context)) {
                    requestForDashBoardData(requestData, false, false);
                } else {
                    if (realmManager.getFeedDataFromDB().size() == 0) {
                        feeds_errmsg.setVisibility(View.VISIBLE);
                    } else {
                        feeds_errmsg.setVisibility(View.GONE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Feeds_Fragment.POST_ACTION) {
            if (mRecyclerView != null) {
                mRecyclerView.scrollToPosition(0);
            }
            realmManager.insertTestFeedDataIntoDB(realm, -2, true);
            dashboradAdapter.notifyItemInserted(realmManager.getFeedDataFromDB().size());
            try {
                long lastFeedTime;
                if (realmManager.checkFeedExists(-6, 0)) {
                    lastFeedTime = realmManager.getFeedDataFromDB() != null && realmManager.getFeedDataFromDB().size() >= 3 ? realmManager.getFeedDataFromDB().get(2).getCreatedOrUpdatedTime() : 0;
                } else {
                    lastFeedTime = realmManager.getFeedDataFromDB() != null && realmManager.getFeedDataFromDB().size() >= 2 ? realmManager.getFeedDataFromDB().get(1).getCreatedOrUpdatedTime() : 0;
                }
                requestData.put("last_feed_time", lastFeedTime);
                requestData.put("load_next", false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            requestForDashBoardData(requestData, true, false);
        }
    }

    //endregion
    //region updateDashboardWithDeletedFeed
    public static void updateDashboardWithDeletedFeed(JSONObject feedObj) {
        for (int i = 0; i < channelsInfoList.size(); i++) {
            try {
                JSONObject currentObj = channelsInfoList.get(i);
                int channel_id = currentObj.optInt(RestUtils.CHANNEL_ID);
                int deletedFeed_channel_id = feedObj.optInt(RestUtils.CHANNEL_ID);
                if (channel_id == deletedFeed_channel_id && !currentObj.getString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Content")) {
                    boolean isFeedCountDecrement = false;
                    tempFeedsList = new ArrayList<>();
                    for (int j = 0; j < channelsInfoList.get(i).getJSONArray("feed_data").length(); j++) {
                        if (feedObj.optInt("post_id") == channelsInfoList.get(i).getJSONArray("feed_data").getJSONObject(j).optInt("post_id")) {
                            isFeedCountDecrement = true;
                        } else {
                            tempFeedsList.add(channelsInfoList.get(i).getJSONArray("feed_data").getJSONObject(j));
                        }
                    }
                    if (isFeedCountDecrement) {
                        channelsInfoList.get(i).put("today_count", (channelsInfoList.get(i).optInt("today_count") - 1));
                    }
                    tempArray = new JSONArray(tempFeedsList);
                    channelsInfoList.get(i).put("feed_data", tempArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateDashboardWithNewContent(JSONObject feedObj) {
        for (int i = 0; i < channelsInfoList.size(); i++) {
            try {
                JSONObject currentObj = channelsInfoList.get(i);
                int channel_id = currentObj.optInt(RestUtils.CHANNEL_ID);
                int updatedFeed_channel_id = feedObj.optInt(RestUtils.CHANNEL_ID);
                if (channel_id == updatedFeed_channel_id) {
                    JSONArray updated_articles_array = feedObj.optJSONArray("articles");
                    int updated_today_count = feedObj.optInt("today_count");
                    channelsInfoList.get(i).remove("feed_data");
                    channelsInfoList.get(i).put("feed_data", updated_articles_array);
                    channelsInfoList.get(i).remove("today_count");
                    channelsInfoList.get(i).put("today_count", updated_today_count);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void OnCustomClick(View aView, int position) {
        Toast.makeText(getActivity(), "postion :" + position, Toast.LENGTH_SHORT).show();
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
            builder = new AlertDialog.Builder(getActivity());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (CallbackCollectionManager.getInstance().getRegisterListeners().contains(this)) {
            CallbackCollectionManager.getInstance().removeListener(this);
        }

        if (!realm.isClosed())
            realm.close();
        instance = null;
        Log.e("dashborad", "fragmentdestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentInForeground = false;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            if (dashboardRefreshOnSubscription) {
                requestForDashBoardData(requestData, false, false);
                dashboardRefreshOnSubscription = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
        isFragmentInForeground = true;
        if (dashboard_call_needed) {
            requestForDashBoardData(requestData, false, false);
            dashboard_call_needed = false;
        }
        if (basicInfo != null && basicInfo.getFname() != null && !basicInfo.getFname().isEmpty()) {
            Spannable padString = new SpannableString("Share an update or a case...");
            padString.setSpan(new ForegroundColorSpan(Color.parseColor("#00A76D")), 0, padString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            postUpdateHint.setText("Hi " + basicInfo.getUser_salutation() + " " + basicInfo.getFname() + ",");
        }
        if (dashboardRefreshOnSubscription) {
            requestForDashBoardData(requestData, false, false);
            dashboardRefreshOnSubscription = false;
        }
        loadProfilePic();
        AppUtil.logDashboardImpressionEvent("DashboardImpression", basicInfo, getActivity());
        AppUtil.logScreenEvent("DashBoardTimeSpent");
    }


    private void loadProfilePic() {
        Log.i(TAG, "loadProfilePic");
        String picUrl = (basicInfo.getPic_url() != null) ? basicInfo.getPic_url().trim() : "";
        String picPath = (basicInfo.getProfile_pic_path() != null) ? basicInfo.getProfile_pic_path().trim() : "";
        String profilePicPath = (picUrl != null && !picUrl.isEmpty()) ? picUrl : (picPath != null && !picPath.equals("")) ? picPath : null;
        if (!AppUtil.checkWriteExternalPermission(getActivity())) {
            if (picUrl != null && !picUrl.isEmpty()) {
                AppUtil.invalidateAndLoadCircularImage(context, basicInfo.getPic_url().trim(), profilePic, R.drawable.default_profilepic);
            }
        } else if (basicInfo.getPic_url() != null && !basicInfo.getPic_url().isEmpty()) {
            AppUtil.invalidateAndLoadCircularImage(context, basicInfo.getPic_url().trim(), profilePic, R.drawable.default_profilepic);
        } else if (basicInfo != null && basicInfo.getProfile_pic_path() != null && !basicInfo.getProfile_pic_path().equals("")) {
            File imgFile = new File(basicInfo.getProfile_pic_path());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                if (myBitmap != null) {
                    profilePic.setImageBitmap(myBitmap);
                } else {
                    profilePic.setImageResource(R.drawable.default_profilepic);
                }
            } else {
                profilePic.setImageResource(R.drawable.default_profilepic);
            }
        } else {
            Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profilepic);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), AppUtil.createCircleBitmap(sourceBitmap));
            profilePic.setImageDrawable(drawable);
        }
        if (profilePicPath != null && !profilePicPath.isEmpty()) {
            addPicSymbol.setVisibility(View.GONE);
        } else {
            addPicSymbol.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("dashborad", "viewdestroy");
    }


    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.item_animation_from_right);
        controller.getAnimation().setDuration(600);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void OnHorizontalItemFound(int position, String horizontalItemType) {


    }

    public void processHorizontalListData(String horizontalItemType, boolean isFromItemDelete, int feedId, int channelId) {
        if (getActivity() == null || !AppUtil.isConnectingToInternet(getActivity())) {
            dashboradAdapter.setIsLoadingHorizontalItems(false);
            return;
        }
        JSONObject requestObject = new JSONObject();
        String url = RestApiConstants.GET_SUGGESTED_FEEDS_LIST_API;
        try {
            requestObject.put(RestUtils.TAG_USER_ID, login_doc_id);
            if (horizontalItemType.equalsIgnoreCase("Suggested")) {
                HorizontalRecyclerAdapter.horizontalItemSelectedPosition = -1;
                requestObject.put(RestUtils.TAG_PAGE_NUM, horizontalItemPageIndex);
            } else if (horizontalItemType.equalsIgnoreCase("Related")) {
                requestObject.put(RestUtils.FEEDID, feedId);
                url = RestApiConstants.GET_RELATED_FEEDS_API;
            } else {
                return;
            }

            new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, url, requestObject.toString(), "GET_DASHBOARD_SUGGESTED_FEEDS", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    if (successResponse != null) {
                        if (horizontalItemType.equalsIgnoreCase("Suggested")) {
                            dashboradAdapter.setIsLoadingHorizontalItems(false);
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(successResponse);

                            if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                if (jsonObject.has(RestUtils.TAG_DATA)) {
                                    dataJsonObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                    JSONArray feedJsonArray = dataJsonObj.optJSONArray(RestUtils.FEED_DATA);
                                    if (feedJsonArray.length() == 0) {
                                        return;
                                    }
                                    lastFeedId = dataJsonObj.optInt(RestUtils.LAST_FEED_ID);
                                    //if (!isFromLoadMore) {
                                    boolean isSeeMOre = false;
                                    //if(horizontalItemType.equalsIgnoreCase("Suggested")) {
                                    if (feedJsonArray.length() > 3) {
                                        isSeeMOre = true;
                                    }
                                    int parentFeedPosition = realmManager.getFeedDBPosition(realmManager.getRealmFeedInfoObj(feedId, channelId)) + 1;
                                    if (!isFromItemDelete) {
                                        if (horizontalItemType.equalsIgnoreCase("suggested")) {
                                            realmManager.insertHorizontalListDataIntoDB(-4, parentFeedPosition, 0);
                                        } else if (horizontalItemType.equalsIgnoreCase("related")) {
                                            realmManager.insertHorizontalListDataIntoDB(feedId, parentFeedPosition, -5);
                                        }
                                    }
                                    feedIdList = new ArrayList<>();

                                    for (int i = 0; i < feedJsonArray.length(); i++) {
                                        JSONObject completedJson = feedJsonArray.optJSONObject(i);
                                        JSONObject feedJson = completedJson.optJSONObject(RestUtils.TAG_FEED_INFO);
                                        feedIdList.add(feedJson.optInt(RestUtils.TAG_FEED_ID));
                                        realmManager.insertSuggestedFeedInDB(feedJson.optInt(RestUtils.TAG_FEED_ID), completedJson);
                                    }
                                    HorizontalListDataObj horizontalListDataObj = new HorizontalListDataObj();
                                    horizontalListDataObj.setParentFeedId(feedId);
                                    horizontalListDataObj.setParentChannelId(channelId);
                                    horizontalListDataObj.setChildFeedIds(feedIdList);
                                    horizontalListDataObj.setPagedIndex(0);

                                    if (horizontalItemType.equalsIgnoreCase("Suggested")) {
                                        horizontalListDataObj.setHorizontalListType("suggested");
                                        horizontalListDataObj.setLastFeedId(lastFeedId);
                                        horizontalListDataObj.setMoreVisible(isSeeMOre);
                                        horizontalListDataObj.setHorizontalListTitle("Suggested Feeds");
                                    } else if (horizontalItemType.equalsIgnoreCase("Related")) {
                                        horizontalListDataObj.setHorizontalListType("Related");
                                        horizontalListDataObj.setLastFeedId(-1);
                                        horizontalListDataObj.setMoreVisible(false);
                                        horizontalListDataObj.setHorizontalListTitle("Related Feeds");
                                    }
                                    dashboradAdapter.addHorizontalListDataObj(feedId, horizontalListDataObj);
                                    if (isFromItemDelete) {
                                        dashboradAdapter.notifyItemChanged(parentFeedPosition);
                                    } else {
                                        dashboradAdapter.notifyItemInserted(parentFeedPosition);
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    if (errorResponse != null && !errorResponse.isEmpty()) {
                        try {
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                Toast.makeText(context, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
