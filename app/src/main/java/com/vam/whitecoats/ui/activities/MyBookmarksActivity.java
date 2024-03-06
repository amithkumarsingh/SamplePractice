package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.CategoriesFilterAdapter;
import com.vam.whitecoats.ui.adapters.DashboardFeedsAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnFeedItemClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnSocialInteractionListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class MyBookmarksActivity extends BaseActionBarActivity implements UiUpdateListener, OnFeedItemClickListener {
    public static final String TAG = MyBookmarksActivity.class.getSimpleName();
    Realm realm;
    RealmManager realmManager;
    protected int doctorID;
    private RecyclerView mRecyclerView, category_filter_list;
    private CustomLinearLayoutManager mLayoutManager;
    DashboardFeedsAdapter dashboardFeedsAdapter;
    private SparseArray<JSONObject> channelsList = new SparseArray<>();
    private List<JSONObject> feedData;
    int lastFeedID = 0;
    private TextView no_bookMarks;
    private ProgressBar loading_progress_bookMarks;
    private SwipeRefreshLayout swipeContainer_bookMarks;
    private TextView mTitleTextView;
    private TextView next_button;
    public static int selectedPosition = -1;
    private int unbookmarkedFeedId = -1;
    private boolean unbookmarkedFromFullView, isAutoRefreshList;
    private String doctorName = "";
    private AVLoadingIndicatorView aviBookmarksList;
    private String navigation = "Bookmarks";
    String requestUrl = RestApiConstants.BOOKMARK_LIST;
    String categoryUrl = RestApiConstants.CATEGORY_TIMELINE;
    private int otherDocId = 0;
    private int category_id;
    private JSONArray categoriesFilterJsonArray = new JSONArray();
    private ArrayList<JSONObject> categoriesFilterList = new ArrayList<>();
    private CategoriesFilterAdapter categoriesFilterAdapter;
    private String category_name;
    private String type = "";
    private int id = 0;
    private int pageIndex = 0;
    private boolean isCategoryFiltersLoaded=false;
    private boolean customBackButton=false;
    private String otherDocUUID="";
    private LikeActionAsync likeAPICall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        setContentView(R.layout.activity_my_bookmarks);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        next_button = (TextView) mCustomView.findViewById(R.id.next_button);

        aviBookmarksList = (AVLoadingIndicatorView) findViewById(R.id.aviInBookmarksList);
        mRecyclerView = (RecyclerView) findViewById(R.id.bookmarks_recycler_view);
        category_filter_list = (RecyclerView) findViewById(R.id.category_filter_list);
        no_bookMarks = (TextView) findViewById(R.id.no_bookMarks);
        loading_progress_bookMarks = (ProgressBar) findViewById(R.id.loading_progress_bookMarks);
        swipeContainer_bookMarks = (SwipeRefreshLayout) findViewById(R.id.swipeContainer_bookMarks);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new CustomLinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        doctorID = realmManager.getDoc_id(realm);
        doctorName = realmManager.getDoc_name(realm);

        CallbackCollectionManager.getInstance().registerListener(this);
        feedData = new ArrayList<JSONObject>();
        aviBookmarksList.smoothToShow();
        next_button.setVisibility(View.GONE);
        mTitleTextView.setText(getScreenName());
        dashboardFeedsAdapter = new DashboardFeedsAdapter(this, feedData, doctorID, mRecyclerView, channelsList, realmManager.getDocSalutation(realm) + " " + doctorName,"",new OnSocialInteractionListener() {
            @Override
            public void onSocialInteraction(JSONObject subItem, int channel_id, Boolean isLiked, int mFeedTypeId) {
                makeLikeServiceCall(subItem, channel_id, isLiked, mFeedTypeId);
            }

            @Override
            public void onUIupdateForLike(JSONObject subItem, int channel_id, Boolean isLiked, int mFeedTypeId) {

            }

            @Override
            public void onReportSpam(String clickOnSpam, int feedId, int docId) {
                if (clickOnSpam.equalsIgnoreCase("SPAM_CLICK")) {
                    BottomSheetDialogReportSpam bottomSheet = new BottomSheetDialogReportSpam();
                    Bundle bundle = new Bundle();
                    bundle.putInt("feedId", feedId);
                    bundle.putInt("docId", docId);
                    bottomSheet.setArguments(bundle);
                    bottomSheet.show(getSupportFragmentManager(),
                            "ModalBottomSheetReportSpam");

                }
            }
        }, this);
        dashboardFeedsAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(dashboardFeedsAdapter);

        categoriesFilterAdapter = new CategoriesFilterAdapter(this, categoriesFilterList, new OnCustomClickListener() {
            @Override
            public void OnCustomClick(View aView, int position) {
                type = categoriesFilterList.get(position).optString(RestUtils.TAG_TYPE);
                id = categoriesFilterList.get(position).optInt(RestUtils.TAG_ID);
                pageIndex = 0;
                categoryRequestData(pageIndex, type, id,false,true);
            }
        });

        category_filter_list.setHasFixedSize(true);
        category_filter_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        category_filter_list.setAdapter(categoriesFilterAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            setNavigation(bundle.getString(RestUtils.NAVIGATATION, ""));
            category_id = bundle.getInt(RestUtils.TAG_CATEGORY_ID);
            category_name = bundle.getString("categoryName");
            if (navigation != null && !navigation.isEmpty()) {
                if (navigation.equalsIgnoreCase("MyPosts")) {
                    mTitleTextView.setText("My Posts");
                    otherDocId = bundle.getInt(RestUtils.TAG_OTHER_USER_ID);
                    category_filter_list.setVisibility(View.GONE);
                } else if (navigation.equalsIgnoreCase("OtherUserPosts")) {
                    otherDocId = bundle.getInt(RestUtils.TAG_OTHER_USER_ID);
                    mTitleTextView.setText(bundle.getString("otherDocName") + " Posts");
                    category_filter_list.setVisibility(View.GONE);
                } else if (navigation.equalsIgnoreCase("fromCategoriesList")) {
                    mTitleTextView.setText(category_name);
                    category_filter_list.setVisibility(View.VISIBLE);

                }
            }
            otherDocUUID=bundle.getString("otherDocUUID");
        }
        // Request the service
        /*Android : Fix code review bugs analyzed by SonarQube -- Added the null check condition*/
        if (navigation != null) {
            if (navigation.equalsIgnoreCase("fromCategoriesList")) {
                pageIndex=0;
                categoryRequestData(pageIndex, type, id,false,false);
            } else {
                if (isConnectingToInternet()) {
                    requestBookmarksList(getRequestObj(navigation), false, requestUrl, false);
                } else {
                    aviBookmarksList.smoothToHide();
                }
            }
        }
        // Set load more listener
        dashboardFeedsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (AppUtil.isConnectingToInternet(MyBookmarksActivity.this)) {
                        if (navigation.equalsIgnoreCase("fromCategoriesList")) {
                            pageIndex++;
                            feedData.add(null);
                            dashboardFeedsAdapter.notifyItemInserted(feedData.size());
                            categoryRequestData(pageIndex, type, id,true,false);
                        } else {
                            // #lastFeedID will be -1 in case it was the last set of results
                            // So, no further request should be made
                            if (lastFeedID != -1) {
                                feedData.add(null);
                                dashboardFeedsAdapter.notifyItemInserted(feedData.size());
                                sendLoadmoreDataRequest();
                            }
                        }

                }else {
                    if (loading_progress_bookMarks.getVisibility() == View.VISIBLE) {
                        loading_progress_bookMarks.setVisibility(View.GONE);
                    }
                }
            }
        });
            if(otherDocUUID!=null){
                upshotEventData(0, 0,0, otherDocUUID, navigation, "", "", "",false);
            }else {
                upshotEventData(0, 0, otherDocId, "", navigation, "", "", category_name,false);
            }
        swipeContainer_bookMarks.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnectingToInternet()) {
                        if (navigation.equalsIgnoreCase("fromCategoriesList")) {
                            pageIndex=0;
                            categoryRequestData(pageIndex, type, id,false,true);
                        } else {
                            lastFeedID = 0;
                            sendRefreshDataRequest();
                        }

                } else {
                    if (swipeContainer_bookMarks != null) {
                        swipeContainer_bookMarks.setRefreshing(false);
                    }
                }
            }
        });
        swipeContainer_bookMarks.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);


    }

    protected void sendLoadmoreDataRequest() {
        requestBookmarksList(getRequestObj(navigation), false, requestUrl, true);
    }

    protected void sendRefreshDataRequest() {
        requestBookmarksList(getRequestObj(navigation), true, requestUrl, false);
    }

    protected void setNavigation(String string) {
        this.navigation=string;
    }

    protected String getScreenName() {
        return "My Bookmarks";
    }

    private JSONObject getRequestObj(String navigation) {
        JSONObject requestObj = new JSONObject();
        try {
            if (navigation.equalsIgnoreCase("Bookmarks")) {
                requestObj.put(RestUtils.TAG_DOC_ID, doctorID);
            } else {
                requestObj.put(RestUtils.TAG_USER_ID, doctorID);
                requestObj.put(RestUtils.TAG_OTHER_USER_ID, otherDocId);
                requestUrl = RestApiConstants.VIEW_USER_POSTS_API;
            }
            requestObj.put(RestUtils.LAST_FEED_ID, lastFeedID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj;
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void categoryRequestData(int pageIndex, String type, int id,boolean isFromLoadMore,boolean isPullToFresh) {
        try {
            JSONObject categoryRequestObject = new JSONObject();
            JSONObject categoryFilterListObject = new JSONObject();
            categoryRequestObject.put(RestUtils.TAG_USER_ID, doctorID);
            categoryRequestObject.put(RestUtils.TAG_CATEGORY_ID, category_id);
            categoryRequestObject.put(RestUtils.TAG_PAGE_NUM, pageIndex);
            categoryFilterListObject.put(RestUtils.TAG_TYPE, type);
            categoryFilterListObject.put(RestUtils.TAG_ID, id);
            categoryRequestObject.put(RestUtils.TAG_FILTERS_LIST, new JSONArray().put(categoryFilterListObject));
            if (isConnectingToInternet()) {
                requestBookmarksList(categoryRequestObject, isPullToFresh, categoryUrl, isFromLoadMore);
            } else {
                if(feedData.contains(null)){
                    feedData.remove(null);
                    dashboardFeedsAdapter.notifyItemRemoved(feedData.size());
                }
                aviBookmarksList.smoothToHide();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
        setCurrentActivity();
        checkNetworkConnectivity();
        if (unbookmarkedFeedId != -1 && !unbookmarkedFromFullView && isAutoRefreshList) {
            int len = feedData.size();
            for (int i = 0; i < len; i++) {
                if (feedData.get(i).optJSONObject("feed_info").optInt(RestUtils.TAG_FEED_ID) == unbookmarkedFeedId) {
                    if (navigation.equalsIgnoreCase("Bookmarks")) {
                        feedData.remove(i);
                    }
                    break;
                }
            }
            if (feedData.size() == 0) {
                no_bookMarks.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                no_bookMarks.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
            dashboardFeedsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
    }

    @Override
    public void updateUI(int feedId, JSONObject socialInteractionObj) {
        Log.i(TAG, "updateUI()");
        realmManager.UpdateFeedWithSocialInteraction(feedId, socialInteractionObj);
        int len = feedData.size();
        for (int i = 0; i < len; i++) {
            if (feedData.get(i)!=null && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO)!=null && feedId == feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                try {
                    feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        dashboardFeedsAdapter.notifyDataSetChanged();

    }

    @Override
    public void notifyUIWithNewData(JSONObject newUpdate) {
        Log.i(TAG, "notifyUIWithNewData()");
        if(newUpdate.has(RestUtils.TAG_FROM_EDIT_POST)) {
            int len = feedData.size();
            for (int i = 0; i < len; i++) {
                if (feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO) != null && newUpdate.optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID) == feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                    feedData.set(i, newUpdate);
                    break;
                }
            }
            dashboardFeedsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyUIWithDeleteFeed(int feedId, JSONObject deletedFeedObj) {
        Log.i(TAG, "notifyUIWithDeleteFeed()");
        int size = feedData.size();
        for (int i = 0; i < size; i++) {
            if (feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID) == feedId) {
                feedData.remove(i);
                break;
            }
        }
        dashboardFeedsAdapter.notifyDataSetChanged();
        setUpNoFeedsInfoUI(feedData.size());
    }

    protected void requestBookmarksList(JSONObject request, final boolean isPullToRefresh, final String URL, final boolean isFromLoadMore) {
        Log.i(TAG, "requestBookmarksList() - " + request.toString());
        new VolleySinglePartStringRequest(mContext, Request.Method.POST, URL, request.toString(), "BOOKMARKS_LIST", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i(TAG, "onSuccessResponse()");
                aviBookmarksList.smoothToHide();
                if (swipeContainer_bookMarks != null) {
                    swipeContainer_bookMarks.setRefreshing(false);
                }
                try {
                    // For the first service call we don't need to check
                    if (feedData.contains(null)) {
                        feedData.remove(null);
                        dashboardFeedsAdapter.notifyItemRemoved(feedData.size());
                    }
                    JSONObject jsonObject = null;
                    if (successResponse != null) {
                        jsonObject = new JSONObject(successResponse);
                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                            if (jsonObject.has(RestUtils.TAG_DATA)) {
                                lastFeedID = jsonObject.optJSONObject(RestUtils.TAG_DATA).optInt(RestUtils.LAST_FEED_ID);
                                JSONArray feedsArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA);
                                int feedsLength = feedsArray.length();
                                if (isPullToRefresh || (URL.equalsIgnoreCase(categoryUrl) && !isFromLoadMore)) {
                                    dashboardFeedsAdapter.setLastPositionToDefalut();
                                    categoriesFilterList.clear();
                                    channelsList.clear();
                                    feedData.clear();
                                    dashboardFeedsAdapter.notifyDataSetChanged();

                                }
                                categoriesFilterJsonArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.TAG_FILTERS_LIST);
                                if (categoriesFilterJsonArray != null) {
                                    for (int i = 0; i < categoriesFilterJsonArray.length(); i++) {
                                        if (categoriesFilterJsonArray.optJSONObject(i) != null) {
                                            categoriesFilterList.add(categoriesFilterJsonArray.optJSONObject(i));
                                        }
                                    }
                                    categoriesFilterAdapter.notifyDataSetChanged();
                                    if (!isCategoryFiltersLoaded) {
                                        int resId = R.anim.item_animation_from_right;
                                        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(MyBookmarksActivity.this, resId);
                                        animation.getAnimation().setDuration(500);
                                        category_filter_list.setLayoutAnimation(animation);
                                        isCategoryFiltersLoaded = true;
                                    }
                                }
                                // " listofChannels "comes only once for the first request, so check whether the node exist.
                                if (jsonObject.optJSONObject(RestUtils.TAG_DATA).has("listofChannels")) {
                                    JSONArray channelsArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray("listofChannels");
                                    int channelsLength = channelsArray.length();
                                    for (int i = 0; i < channelsLength; i++) {
                                        JSONObject currentChannelObj = channelsArray.optJSONObject(i);
                                        channelsList.put(currentChannelObj.optInt(RestUtils.CHANNEL_ID), currentChannelObj);
                                    }
                                }
                                for (int j = 0; j < feedsLength; j++) {
                                    if (feedsArray.optJSONObject(j) != null) {
                                        feedData.add(feedsArray.optJSONObject(j));
                                    }
                                }

                                if (mRecyclerView != null) {
                                    dashboardFeedsAdapter.notifyDataSetChanged();
                                    if (feedsLength > 0) {
                                        dashboardFeedsAdapter.setLoaded();
                                    }
                                }
                                if (isPullToRefresh || (URL.equalsIgnoreCase(categoryUrl) && !isFromLoadMore)) {
                                    if (feedData.size() > 0) {
                                        /*Android : Fix code review bugs analyzed by SonarQube Added the null check*/
                                        if (mRecyclerView != null) {
                                            mRecyclerView.scrollToPosition(0);
                                        }
                                    }
                                }
                                setUpNoFeedsInfoUI(feedData.size());
                            }
                        }else if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                            Toast.makeText(mContext, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {

                aviBookmarksList.smoothToHide();
                if (swipeContainer_bookMarks != null) {
                    swipeContainer_bookMarks.setRefreshing(false);
                }
                Log.i(TAG, "onErrorResponse() - " + errorResponse);
                hideProgress();
                if (errorResponse != null) {
                    try {
                        dashboardFeedsAdapter.setLoaded();
                        JSONObject jsonObject = new JSONObject(errorResponse);
                        setUpNoFeedsInfoUI(feedData.size());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    displayErrorScreen(errorResponse);
                }

            }
        }).sendSinglePartRequest();


    }

    private void setUpNoFeedsInfoUI(int size) {
        if (size == 0) {
            if (navigation.equalsIgnoreCase("Bookmarks")) {
                no_bookMarks.setText(getString(R.string.label_no_bookmarks));
            } else if(navigation.equalsIgnoreCase("fromCategoriesList")){
                no_bookMarks.setText(getString(R.string.no_feeds));
            }else {
                if (doctorID != otherDocId) {
                    no_bookMarks.setText(getString(R.string.no_feeds_info));
                } else {
                    no_bookMarks.setText(getString(R.string.no_feeds_created));
                }
            }
            no_bookMarks.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            no_bookMarks.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    public void makeLikeServiceCall(JSONObject subItem, final int channel_id, Boolean isLiked, final int mFeedTypeId) {
        JSONObject likeRequest = new JSONObject();
        try {
            likeRequest.put(RestUtils.TAG_DOC_ID, doctorID);
            likeRequest.put(RestUtils.CHANNEL_ID, channel_id);
            likeRequest.put(RestUtils.FEED_TYPE_ID, mFeedTypeId);
            JSONObject socialInteractionObj = new JSONObject();
            socialInteractionObj.put("type", "Like");
            socialInteractionObj.put(RestUtils.TAG_IS_LIKE, isLiked);
            likeRequest.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!AppUtil.isConnectingToInternet(this)) {
            return;
        } else {
            AppConstants.likeActionList.add(channel_id + "_" + mFeedTypeId);
        }
        likeAPICall=new LikeActionAsync(this, RestApiConstants.SOCIAL_INTERACTIONS, new OnTaskCompleted() {
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
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(sResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*Android : Fix code review bugs analyzed by SonarQube -- Added the null check condition*/
                        if (jsonObject != null) {
                            if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                JSONObject likeResponseObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                realmManager.UpdateFeedWithSocialInteraction(mFeedTypeId, likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                if (selectedPosition != -1) {
                                    try {
                                        feedData.get(selectedPosition).optJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_SOCIALINTERACTION, likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                dashboardFeedsAdapter.notifyDataSetChanged();

                            } else {
                                try {
                                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                        if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("603")) {

                                            AlertDialog.Builder builder = new AlertDialog.Builder(MyBookmarksActivity.this);
                                            builder.setMessage(jsonObject.getString(RestUtils.TAG_ERROR_MESSAGE));
                                            builder.setCancelable(true);
                                            builder.setPositiveButton("Verify Now", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent verifyLikeIntent = new Intent(MyBookmarksActivity.this, MCACardUploadActivity.class);
                                                    startActivity(verifyLikeIntent);
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
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
        likeAPICall.execute(likeRequest.toString());
        dashboardFeedsAdapter.setLikeAPICallAsync(likeAPICall);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            customBackButton=true;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                String eventName="";
                if(navigation.equalsIgnoreCase("MyPosts")){
                    eventName = "MyPostsBackTapped";
                }else if(navigation.equalsIgnoreCase("OtherUserPosts")){
                    eventName="OtherUserPostsBackTapped";
                    jsonObject.put("OtherDocId",otherDocUUID);
                }else if(navigation.equalsIgnoreCase("fromCategoriesList")){
                    eventName="TrendingCategoryBackTapped";
                    jsonObject.put("CategoryName",category_name);
                }else{
                    eventName="BookmarksBackTapped";
                }
                if(!mContext.getClass().getSimpleName().equalsIgnoreCase("SuggestedListActivity")) {
                    AppUtil.logUserUpShotEvent(eventName, AppUtil.convertJsonToHashMap(jsonObject));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBookmark(boolean isBookmarked, int feedID, boolean isAutoRefresh, JSONObject socialInteractionObj) {
        Log.i(TAG, "onBookmark(boolean isBookmarked, int feedID)");
        unbookmarkedFeedId = feedID;
        unbookmarkedFromFullView = isBookmarked;
        isAutoRefreshList = isAutoRefresh;
            int position = 0;
            int len = feedData.size();
            for (int i = 0; i < len; i++) {
                if (feedData.get(i).optJSONObject("feed_info").optInt(RestUtils.TAG_FEED_ID) == feedID) {
                    position = i;
                    break;
                }
            }
            if (navigation.equalsIgnoreCase("Bookmarks")) {
                    if(feedData.size()>0) {
                        feedData.remove(position);
                    }
            } else {
                try {
                    if(feedData.size()>0) {
                        feedData.get(position).optJSONObject("feed_info").put(RestUtils.TAG_IS_BOOKMARKED, isBookmarked);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            dashboardFeedsAdapter.notifyDataSetChanged();
            setUpNoFeedsInfoUI(feedData.size());

    }

    @Override
    public void notifyUIWithFeedSurveyResponse(int feedId, JSONObject surveyResponse) {
        int selectedPosition = -1;
        JSONObject feedObj=null;
        for (int i = 0; i < feedData.size(); i++) {
            if (feedId == feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                selectedPosition = i;
                feedObj = feedData.get(i);
                break;
            }
        }
        if(selectedPosition!=-1 && feedObj!=null){
            feedData.set(selectedPosition,AppUtil.processFeedSurveyResponse(feedObj,surveyResponse));
            if (dashboardFeedsAdapter != null) {
                dashboardFeedsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void notifyUIWithFeedWebinarResponse(int feedId, JSONObject webinarRegisterResponse) {
        Log.i(TAG, "updateWithWebinar()");
        realmManager.UpdateFeedWithWebinarRegisterResponse(feedId, webinarRegisterResponse);
        int len = feedData.size();
        for (int i = 0; i < len; i++) {
            if (feedData.get(i)!=null && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO)!=null && feedId == feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                try {
                    feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).put("event_details", webinarRegisterResponse);
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        dashboardFeedsAdapter.notifyDataSetChanged();

    }

    @Override
    public void notifyUIWithJobApplyStatus(int feedId, JSONObject jobApplyResponse) {

    }

    @Override
    public void onItemClickListener(JSONObject feedObj,boolean isNetworkChannel,int channelId, String channelName, View sharedView, int selectedPosition) {
        JSONObject feedInfoObj = feedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
      /*Android : Fix code review bugs analyzed by SonarQube -- Added the null check condition*/
        if (feedInfoObj != null) {
            if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                Intent in = new Intent(MyBookmarksActivity.this, ContentFullView.class);
                in.putExtra(RestUtils.CHANNEL_ID, channelId);
                in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelName);
                in.putExtra(RestUtils.TAG_CONTENT_OBJECT, feedObj.toString());
                if (sharedView != null) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MyBookmarksActivity.this,
                                    sharedView,
                                    ViewCompat.getTransitionName(sharedView));
                    ActivityCompat.startActivity(MyBookmarksActivity.this, in, options.toBundle());
                } else {
                    startActivity(in);
                }
            } else if(feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)){
                Intent in = new Intent(MyBookmarksActivity.this, JobFeedCompleteView.class);
                in.putExtra(RestUtils.CHANNEL_ID, channelId);
                in.putExtra(RestUtils.CHANNEL_NAME, channelName);
                in.putExtra(RestUtils.TAG_POSITION, selectedPosition);
                in.putExtra(RestUtils.TAG_FEED_OBJECT, feedObj.toString());
                in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL,isNetworkChannel);
                if (sharedView != null) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MyBookmarksActivity.this,
                                    sharedView,
                                    ViewCompat.getTransitionName(sharedView));
                    ActivityCompat.startActivity(MyBookmarksActivity.this, in, options.toBundle());
                } else {
                    startActivity(in);
                }
            }
            else {
                Intent in = new Intent(MyBookmarksActivity.this, FeedsSummary.class);
                in.putExtra(RestUtils.CHANNEL_ID, channelId);
                in.putExtra(RestUtils.CHANNEL_NAME, channelName);
                in.putExtra(RestUtils.TAG_POSITION, selectedPosition);
                in.putExtra(RestUtils.TAG_FEED_OBJECT, feedObj.toString());
                in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL,isNetworkChannel);
                if (sharedView != null) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MyBookmarksActivity.this,
                                    sharedView,
                                    ViewCompat.getTransitionName(sharedView));
                    ActivityCompat.startActivity(MyBookmarksActivity.this, in, options.toBundle());
                } else {
                    startActivity(in);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            String eventName="";
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                if(navigation.equalsIgnoreCase("MyPosts")){
                    eventName = "MyPostsDeviceBackTapped";
                }else if(navigation.equalsIgnoreCase("OtherUserPosts")){
                    eventName="OtherUserPostsDeviceBackTapped";
                    jsonObject.put("OtherDocID",otherDocUUID);
                }else if(navigation.equalsIgnoreCase("fromCategoriesList")){
                    eventName="TrendingCategoryDeviceBackTapped";
                    jsonObject.put("CategoryName",category_name);
                }else{
                    eventName="BookmarksDeviceBackTapped";
                }
                if(!mContext.getClass().getSimpleName().equalsIgnoreCase("SuggestedListActivity")) {
                    AppUtil.logUserUpShotEvent(eventName, AppUtil.convertJsonToHashMap(jsonObject));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        finish();
    }
}
