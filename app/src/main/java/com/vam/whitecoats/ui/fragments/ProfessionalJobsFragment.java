package com.vam.whitecoats.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.SubCategoriesInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.ContentFullView;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.ui.activities.FilterSearchActivity;
import com.vam.whitecoats.ui.activities.JobFeedCompleteView;
import com.vam.whitecoats.ui.activities.MCACardUploadActivity;
import com.vam.whitecoats.ui.activities.UserSearchResultsActivity;
import com.vam.whitecoats.ui.adapters.DashboardFeedsAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.interfaces.OnFeedItemClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnSocialInteractionListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.EndlessRecyclerOnScrollListener;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.TabsTagId;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

public class ProfessionalJobsFragment  extends Fragment implements UiUpdateListener, OnFeedItemClickListener {

    private static final String TAG = "Feeds";
    private Realm realm;
    private RealmManager realmManager;
    private SparseArray<JSONObject> channelsList = new SparseArray<>();
    private List<JSONObject> feedData;
    private RecyclerView mRecyclerView;
    DashboardFeedsAdapter dashboardFeedsAdapter;
    public static int selectedPosition = -1;
    private LikeActionAsync likeAPICall;
    private CustomLinearLayoutManager mLayoutManager;
    public static String navigationFrom="";
    public static int contentFeeds_pageNum = 0;
    private ArrayList<Integer> catergoryIdsList;
    ArrayList<SubCategoriesInfo> categoriesList = new ArrayList<SubCategoriesInfo>();
    private LinearLayoutManager linearLayoutManager;
    private boolean mIsListExhausted = false;
    private boolean loading = false;
    public static int skilling_filters_page_num = 0;
    private EndlessRecyclerOnScrollListener recyclerScrollListener;
    private TextView no_bookMarks;
    private SwipeRefreshLayout swipeContainer_content_feeds;
    public static boolean skillingFilterClicked=false;
    public static int subCategoryId=0;
    private ViewGroup shimmerContentLayout;
    private ShimmerFrameLayout shimmerContainer;
    private ShimmerFrameLayout shimmerContainer1;
    private TextView button_location;
    private HashMap<String, String> filtersHashMap = new HashMap<>();
    private ActivityResultLauncher<Intent> launchActivityForResults;
    private String location;
    private ArrayList<String> specialitiesList = new ArrayList<>();
    private TextView button_speciality;
    private TextView button_clear;
    private String speciality;
    private ProgressDialog mProgressDialog;
    private boolean filterApplied;
    private int jobFiltersPageNum=0;
    private LinearLayout jobs_filters_list;
    private int lastFeedId;
    private HorizontalScrollView user_filters_list;
    private int unbookmarkedFeedId = -1;
    private boolean unbookmarkedFromFullView, isAutoRefreshList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CallbackCollectionManager.getInstance().registerListener(this);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.professinal_jobs_lay, container, false);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.feeds_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new CustomLinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        feedData = new ArrayList<JSONObject>();
        catergoryIdsList = new ArrayList<>();
        no_bookMarks = (TextView) view.findViewById(R.id.no_bookMarks);
        swipeContainer_content_feeds = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer_content_feeds);
        shimmerContentLayout = (ViewGroup) view.findViewById(R.id.shimmer_content_layout);
        shimmerContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerContainer1 = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container1);
        button_location=(TextView)view.findViewById(R.id.btn_Location);
        button_speciality=(TextView)view.findViewById(R.id.btn_speciality);
        button_clear=(TextView)view.findViewById(R.id.btn_clear);
        jobs_filters_list=(LinearLayout)view.findViewById(R.id.jobs_filters_list);
        user_filters_list=(HorizontalScrollView)view.findViewById(R.id.user_filters_list);
        filtersHashMap = new HashMap<>();

        if (getArguments() != null) {
            navigationFrom = getArguments().getString("NavigationFrom");
        }


        dashboardFeedsAdapter = new DashboardFeedsAdapter(getContext(), feedData, realmManager.getDoc_id(realm), mRecyclerView, channelsList, realmManager.getDocSalutation(realm) + " " + realmManager.getDoc_name(realm),navigationFrom, new OnSocialInteractionListener() {
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
                    bottomSheet.show(requireActivity().getSupportFragmentManager(),
                            "ModalBottomSheetReportSpam");

                }
            }
        }, this);
        dashboardFeedsAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(dashboardFeedsAdapter);

        dashboardFeedsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (AppUtil.isConnectingToInternet(getContext())) {
                    feedData.add(null);
                    contentFeeds_pageNum++;
                    dashboardFeedsAdapter.notifyItemInserted(feedData.size());
                    /*if(skillingFilterClicked){
                        if(subCategoryId!=0)
                            sendLoadmoreDataRequest(contentFeeds_pageNum,subCategoryId);
                    }else {
                        sendLoadmoreDataRequest(contentFeeds_pageNum,0);
                    }*/
                    if(filterApplied){
                        jobFiltersPageNum++;
                        performFiltersRequest(jobFiltersPageNum,lastFeedId,realmManager.getDoc_name(realm),speciality,location,speciality,realmManager.getDoc_id(realm),"jobs","");
                    }else{
                        sendLoadmoreDataRequest(contentFeeds_pageNum,0);
                    }

                } else {
                   /* if (loading_progress_bookMarks.getVisibility() == View.VISIBLE) {
                        loading_progress_bookMarks.setVisibility(View.GONE);
                    }*/
                }
            }
        });
        if(AppUtil.isConnectingToInternet(getContext())) {
            shimmerContentLayout.setVisibility(View.VISIBLE);
            shimmerContainer.startShimmerAnimation();
            shimmerContainer1.startShimmerAnimation();
            contentFeeds_pageNum=0;
            requestForContentFeeds(contentFeeds_pageNum, false, 0);
        }else{
            shimmerContentLayout.setVisibility(View.GONE);
        }

        swipeContainer_content_feeds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppUtil.isConnectingToInternet(getContext())) {
                    if(filterApplied){
                        filterApplied=true;
                        jobFiltersPageNum=0;
                        performFiltersRequest(jobFiltersPageNum,0,realmManager.getDoc_name(realm),speciality,location,"",realmManager.getDoc_id(realm),"jobs","");
                    }else {
                        contentFeeds_pageNum = 0;
                        requestForContentFeeds(contentFeeds_pageNum, true, 0);
                    }

                } else {
                    if (swipeContainer_content_feeds != null) {
                        swipeContainer_content_feeds.setRefreshing(false);
                    }
                }
            }
        });

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtil.isConnectingToInternet(getContext())) {
                   updateCriteriaFilters(filtersHashMap.get("Location"),"fromLocation");
                    Intent filterLocationSearch = new Intent(getContext(), FilterSearchActivity.class);
                    filterLocationSearch.putExtra("filter_map", filtersHashMap);
                    launchActivityForResults.launch(filterLocationSearch);
                }
            }
        });

        button_speciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(getContext())) {
                    /*
                     * Sort the list in alphabetical order
                     */
                    Collections.sort(specialitiesList, new Comparator<String>() {
                        @Override
                        public int compare(String s1, String s2) {
                            return s1.compareToIgnoreCase(s2);
                        }
                    });
                    CharSequence[] specialities = specialitiesList.toArray(new CharSequence[specialitiesList.size()]);
                    SpecialityDialogFragment specialityDialogFragment = SpecialityDialogFragment.newInstance(specialities, "Network_search_specialities");
                    specialityDialogFragment.setUpListener(new SpecialityDialogFragment.NoticeDialogListener() {
                        @Override
                        public void onDialogListItemSelect(String selectedItem) {
                            Log.i(TAG, "onDialogListItemSelect()");
                            if (selectedItem == null)
                                selectedItem = "";
                            speciality = selectedItem;
                            button_speciality.setText(selectedItem);
                            //specialist = selectedItem.toString();

                            button_speciality.setBackgroundResource(R.drawable.filter_border);
                            button_speciality.setTextColor(getResources().getColor(R.color.white));
                            button_speciality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_white, 0);

                            button_clear.setVisibility(View.VISIBLE);
                            filterApplied=true;
                            jobFiltersPageNum=0;
                            if(AppUtil.isConnectingToInternet(getContext())) {
                                mRecyclerView.setVisibility(View.GONE);
                                shimmerContentLayout.setVisibility(View.VISIBLE);
                                shimmerContainer.startShimmerAnimation();
                                shimmerContainer1.startShimmerAnimation();
                                performFiltersRequest(jobFiltersPageNum,0,realmManager.getDoc_name(realm),speciality,location,"",realmManager.getDoc_id(realm),"jobs","");
                            }else{
                                shimmerContentLayout.setVisibility(View.GONE);
                            }



                        }
                    });
                    specialityDialogFragment.show(getChildFragmentManager(), SpecialityDialogFragment.TAG);
                }
            }
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = "";
                speciality="";
                button_location.setText(R.string.location);
                button_speciality.setText(R.string.speciality);
                button_clear.setVisibility(View.GONE);
                //set location text to normal
                button_location.setBackgroundResource(R.drawable.professional_jobs_filters_bg);
                button_location.setTextColor(getResources().getColor(R.color.black));
                button_location.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                //set workplace text normal
                //set speciality text normal
                button_speciality.setBackgroundResource(R.drawable.professional_jobs_filters_bg);
                button_speciality.setTextColor(getResources().getColor(R.color.black));
                button_speciality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                filterApplied=false;
                if(AppUtil.isConnectingToInternet(getContext())) {
                    mRecyclerView.setVisibility(View.GONE);
                    shimmerContentLayout.setVisibility(View.VISIBLE);
                    shimmerContainer.startShimmerAnimation();
                    shimmerContainer1.startShimmerAnimation();
                    contentFeeds_pageNum=0;
                    requestForContentFeeds(contentFeeds_pageNum, false, 0);
                }else{
                    shimmerContentLayout.setVisibility(View.GONE);
                }

                /*if (AppUtil.isConnectingToInternet(UserSearchResultsActivity.this)) {
                    try {
                        performSearchRequest(1, searchText, location, specialist, workPlace, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });



        launchActivityForResults = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            filtersHashMap = (HashMap<String, String>) data.getExtras().getSerializable("criteriaFilter");
                            location = filtersHashMap.get("Location");

                            if (filtersHashMap.get("selectedFilter").equalsIgnoreCase("fromlocation")) {
                                button_location.setText(location);
                                button_location.setBackgroundResource(R.drawable.filter_border);
                                button_location.setTextColor(getResources().getColor(R.color.white));
                                button_location.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_white, 0);
                                button_clear.setVisibility(View.VISIBLE);
                                //performSearchRequest(1, searchText, location, specialist, workPlace, false);
                                filterApplied=true;
                                jobFiltersPageNum=0;
                                if(AppUtil.isConnectingToInternet(getContext())) {
                                    mRecyclerView.setVisibility(View.GONE);
                                    shimmerContentLayout.setVisibility(View.VISIBLE);
                                    shimmerContainer.startShimmerAnimation();
                                    shimmerContainer1.startShimmerAnimation();
                                    performFiltersRequest(jobFiltersPageNum,0,realmManager.getDoc_name(realm),speciality,location,"",realmManager.getDoc_id(realm),"jobs","");
                                }else{
                                    shimmerContentLayout.setVisibility(View.GONE);
                                }
                            }
                            //doSomeOperations();
                        }
                    }
                });

        swipeContainer_content_feeds.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        return view;
    }

    private void performFiltersRequest(int pg_num, int last_feed_id, String doc_name, String speciality, String location, String workPlace, int doc_id, String jobs, String searchText) {

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put(RestUtils.TAG_USER_ID, doc_id);
            requestObj.put(RestUtils.TAG_SEARCH_IN, "jobs");
            requestObj.put(RestUtils.TAG_SEARCH_TEXT, "");
            requestObj.put(RestUtils.TAG_PAGE_NUM, pg_num);
            requestObj.put(RestUtils.LAST_FEED_ID, last_feed_id);
            JSONObject searchCriteriaObj = new JSONObject();
            searchCriteriaObj.put(RestUtils.TAG_USER_NAME, "");
            if(speciality==null){
                searchCriteriaObj.put(RestUtils.TAG_SPECIALIST, "");
            }else{
                searchCriteriaObj.put(RestUtils.TAG_SPECIALIST, speciality);
            }
            searchCriteriaObj.put(RestUtils.TAG_LOCATION, location);
            searchCriteriaObj.put(RestUtils.TAG_WORKPLACE, workPlace);
            requestObj.put(RestUtils.TAG_SEARCH_CRITERIA, searchCriteriaObj);

            new VolleySinglePartStringRequest(getContext(), Request.Method.POST, RestApiConstants.GET_PROFESSIONAL_JOB_FILTERS, requestObj.toString(), TAG, new OnReceiveResponse() {

                @Override
                public void onSuccessResponse(String successResponse) {
                    Log.i(TAG, successResponse);
                    if (swipeContainer_content_feeds != null) {
                        swipeContainer_content_feeds.setRefreshing(false);
                    }
                    shimmerContentLayout.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(successResponse);
                        if(jobFiltersPageNum==0) {
                            feedData.clear();
                        }
                        if (feedData.contains(null)) {
                            feedData.remove(null);
                            dashboardFeedsAdapter.notifyItemRemoved(feedData.size());
                        }
                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                            if (jsonObject.has(RestUtils.TAG_DATA)) {
                               lastFeedId=jsonObject.optJSONObject(RestUtils.TAG_DATA).optInt("last_feed_id");
                                if (jsonObject.optJSONObject(RestUtils.TAG_DATA).has(RestUtils.FEED_DATA) && jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA) != null) {
                                    JSONArray feedsArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA);
                                    int feedsLength = feedsArray.length();
                                    if(feedsLength>0){
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
                                        setUpNoFeedsInfoUI(feedData.size());
                                    }else{
                                        setUpNoFeedsInfoUI(feedData.size());
                                    }


                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.i(TAG, "onErrorResponse() - " + errorResponse);
                    if (swipeContainer_content_feeds != null) {
                        swipeContainer_content_feeds.setRefreshing(false);
                    }
                    shimmerContentLayout.setVisibility(View.GONE);
                    if(jobFiltersPageNum==0) {
                        feedData.clear();
                    }
                    if (errorResponse != null) {
                        try {
                            dashboardFeedsAdapter.setLoaded();
                            if(errorResponse.equalsIgnoreCase("network_error_retry")){
                                setUpNoFeedsInfoUI(feedData.size());
                            }else{
                                JSONObject jsonObject = new JSONObject(errorResponse);
                                setUpNoFeedsInfoUI(feedData.size());
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(getContext()!=null) {
                            Toast.makeText(getContext(), errorResponse, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }).sendSinglePartRequest();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    protected void sendLoadmoreDataRequest(int page_num, int subCategoryId) {
        requestForContentFeeds(page_num, false, subCategoryId);
    }


    public void requestForContentFeeds(int pageNum, boolean pullToRefresh, Integer subCategoryId) {


        new VolleySinglePartStringRequest(getContext(), Request.Method.POST, RestApiConstants.GET_CONTENT_FEEDS, prepareGetCategoryListRequest(pageNum,subCategoryId), navigationFrom, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
               // filterApplied=false;

                if (swipeContainer_content_feeds != null) {
                    swipeContainer_content_feeds.setRefreshing(false);
                }
                shimmerContentLayout.setVisibility(View.GONE);

                JSONObject jsonObject = null;
                if (successResponse != null) {
                    try {
                        if (feedData.contains(null)) {
                            feedData.remove(null);
                            dashboardFeedsAdapter.notifyItemRemoved(feedData.size());
                        }
                        jsonObject = new JSONObject(successResponse);
                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                            if (jsonObject.has(RestUtils.TAG_DATA)) {
                                if (jsonObject.optJSONObject(RestUtils.TAG_DATA).has(RestUtils.FEED_DATA) &&jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA)!=null){
                                    JSONArray feedsArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA);
                                    int feedsLength = feedsArray.length();
                                    if (pullToRefresh || pageNum == 0) {
                                        dashboardFeedsAdapter.setLastPositionToDefalut();
                                        channelsList.clear();
                                        feedData.clear();
                                        dashboardFeedsAdapter.notifyDataSetChanged();

                                    }
                                    if (feedsLength > 0) {
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
                                                if(pageNum==0) {
                                                    getSpecialities();
                                                }
                                            }
                                        }

                                        setUpNoFeedsInfoUI(feedData.size());
                                    } else {
                                        if (pageNum == 0) {
                                            setUpNoFeedsInfoUI(feedData.size());
                                        }

                                    }
                                }else{
                                    setUpNoFeedsInfoUI(feedData.size());
                                }

                            }else {
                                setUpNoFeedsInfoUI(feedData.size());
                            }

                        } else if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                            feedData.clear();
                            setUpNoFeedsInfoUI(feedData.size());
                            if(getContext()!=null) {
                                //Toast.makeText(getContext(), jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                Log.i(TAG, "onErrorResponse() - " + errorResponse);
               // filterApplied=false;
                shimmerContentLayout.setVisibility(View.GONE);
                if (errorResponse != null) {
                    try {
                        dashboardFeedsAdapter.setLoaded();
                        if(errorResponse.equalsIgnoreCase("network_error_retry")){
                            setUpNoFeedsInfoUI(feedData.size());
                        }else{
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            setUpNoFeedsInfoUI(feedData.size());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(getContext()!=null) {
                        // Toast.makeText(getContext(), errorResponse, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).sendSinglePartRequest();

    }

    private void getSpecialities() {
        JSONObject jsonObject = new JSONObject();
        try {
            //showProgress();
            if (AppUtil.isConnectingToInternet(getContext())) {
                jsonObject.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
                jsonObject.put(RestUtils.CHANNEL_ID, 0);
                new VolleySinglePartStringRequest(getContext(), Request.Method.POST, RestApiConstants.DEPARTMENT_AND_SPECIALITY, jsonObject.toString(), TAG, new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        Log.i(TAG, "onSuccessResponse()");
                        /*
                         * Populate UI Adapter for depts & splty
                         */
                        JSONObject response = null;
                        try {
                            response = new JSONObject(successResponse);
                            if (response.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {

                                JSONArray specialitiesArray = response.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.KEY_SPECIALITIES);


                                specialitiesList.clear();
                                if (specialitiesArray.length() > 0) {
                                    for (int i = 0; i < specialitiesArray.length(); i++) {
                                        specialitiesList.add(specialitiesArray.optJSONObject(i).optString("speciality_name"));
                                    }
                                }

                            } else if (response.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                Toast.makeText(getContext(), response.getString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        Log.i(TAG, "onErrorResponse()");
                        // hideProgress();
                    }
                }).sendSinglePartRequest();
            } else {
              //  setupUI(false);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private String prepareGetCategoryListRequest(int page_num,int subCategoryId) {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
            reqObj.put(RestUtils.PG_NUM, page_num);
            int tagId = 0;
            if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromProfessionalFeeds")) {
                tagId = TabsTagId.PROFESSIONAL_FEEDS.geTagId();
            } else if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromProfessionalOpportunities")) {
                tagId = TabsTagId.PROFESSIONAL_OPPORTUNITIES.geTagId();
            } else if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromKnowledgeFeeds")) {
                tagId = TabsTagId.KNOWLEDGE_FEEDS.geTagId();
            } else if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromKnowledgeMedicalEvents")) {
                tagId = TabsTagId.KNOWLEDGE_MEDICAL_EVENTS.geTagId();
            } else if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromCommunitySpotlight")) {
                tagId = TabsTagId.COMMUNITY_SPOTLIGHTS.geTagId();
            } else if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromCommunityFeeds")) {
                tagId = TabsTagId.COMMUNITY_FEEDS.geTagId();
            } else if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromProfessionalSkilling")) {
                tagId = TabsTagId.PROFESSIONAL_SKILLING.geTagId();
            }
            reqObj.put("tag_id", tagId);
            if(skillingFilterClicked){
                reqObj.put("category_id",subCategoryId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }

    public static ProfessionalJobsFragment newInstance(String navigatedFrom) {

        Bundle args = new Bundle();

        ProfessionalJobsFragment fragment = new ProfessionalJobsFragment();
        args.putSerializable("NavigationFrom", navigatedFrom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClickListener(JSONObject feedObj, boolean isNetworkChannel, int channelId, String channelName, View sharedView, int selectedPosition) {
        JSONObject feedInfoObj = feedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
        if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
            Intent in = new Intent(getContext(), ContentFullView.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelId);
            in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelName);
            in.putExtra(RestUtils.TAG_CONTENT_OBJECT, feedObj.toString());
            if (sharedView != null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                ActivityCompat.startActivity(getContext(), in, options.toBundle());
            } else {
                startActivity(in);
            }
        } else if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
            Intent in = new Intent(getContext(), JobFeedCompleteView.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelId);
            in.putExtra(RestUtils.CHANNEL_NAME, channelName);
            in.putExtra(RestUtils.TAG_POSITION, selectedPosition);
            in.putExtra(RestUtils.TAG_FEED_OBJECT, feedObj.toString());
            in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, isNetworkChannel);
            if (sharedView != null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                ActivityCompat.startActivity(getContext(), in, options.toBundle());
            } else {
                startActivity(in);
            }
        } else {
            Intent in = new Intent(getContext(), FeedsSummary.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelId);
            in.putExtra(RestUtils.CHANNEL_NAME, channelName);
            in.putExtra(RestUtils.TAG_POSITION, selectedPosition);
            in.putExtra(RestUtils.TAG_FEED_OBJECT, feedObj.toString());
            in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, isNetworkChannel);
            if (sharedView != null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                ActivityCompat.startActivity(getContext(), in, options.toBundle());
            } else {
                startActivity(in);
            }
        }
    }

    @Override
    public void updateUI(int feedId, JSONObject socialInteractionObj) {
        Log.i(TAG, "updateUI()");
        realmManager.UpdateFeedWithSocialInteraction(feedId, socialInteractionObj);
        int len = feedData.size();
        for (int i = 0; i < len; i++) {
            if (feedData.get(i) != null && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO) != null && feedId == feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
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
        if (newUpdate.has(RestUtils.TAG_FROM_EDIT_POST)) {
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

    @Override
    public void onBookmark(boolean isBookmarked, int feedID, boolean isAutoRefresh, JSONObject socialInteractionObj) {
        Log.i(TAG, "onBookmark(boolean isBookmarked, int feedID)");
        unbookmarkedFeedId = feedID;
        unbookmarkedFromFullView = isBookmarked;
        isAutoRefreshList = isAutoRefresh;
//        try {
        int position = 0;
        int len = feedData.size();
        for (int i = 0; i < len; i++) {
            if (feedData.get(i).optJSONObject("feed_info").optInt(RestUtils.TAG_FEED_ID) == feedID) {
                position = i;
                break;
            }
        }
//            feedData.get(position).optJSONObject("feed_info").put(RestUtils.TAG_IS_BOOKMARKED, isBookmarked);
       /* if (navigation.equalsIgnoreCase("Bookmarks")) {
            if(feedData.size()>0) {
                feedData.remove(position);
            }
        } else {*/
            try {
                if(feedData.size()>0) {
                    feedData.get(position).optJSONObject("feed_info").put(RestUtils.TAG_IS_BOOKMARKED, isBookmarked);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
       // }
        dashboardFeedsAdapter.notifyDataSetChanged();
        setUpNoFeedsInfoUI(feedData.size());
    }

    @Override
    public void notifyUIWithFeedSurveyResponse(int feedId, JSONObject surveyResponse) {
        int selectedPosition = -1;
        JSONObject feedObj = null;
        for (int i = 0; i < feedData.size(); i++) {
            if (feedId == feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                selectedPosition = i;
                feedObj = feedData.get(i);
                break;
            }
        }
        if (selectedPosition != -1 && feedObj != null) {
            feedData.set(selectedPosition, AppUtil.processFeedSurveyResponse(feedObj, surveyResponse));
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
            if (feedData.get(i) != null && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO) != null && feedId == feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
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

    private void setUpNoFeedsInfoUI(int size) {
        if(getContext()!=null) {
            if (size == 0) {
                no_bookMarks.setText(getString(R.string.no_feeds));
                no_bookMarks.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                if(!filterApplied) {
                    user_filters_list.setVisibility(View.GONE);

                }
            } else {
                no_bookMarks.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                if(!filterApplied) {
                    user_filters_list.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void makeLikeServiceCall(JSONObject subItem, final int channel_id, Boolean isLiked, final int mFeedTypeId) {
        JSONObject likeRequest = new JSONObject();
        try {
            likeRequest.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
            likeRequest.put(RestUtils.CHANNEL_ID, channel_id);
            likeRequest.put(RestUtils.FEED_TYPE_ID, mFeedTypeId);
            JSONObject socialInteractionObj = new JSONObject();
            socialInteractionObj.put("type", "Like");
            socialInteractionObj.put(RestUtils.TAG_IS_LIKE, isLiked);
            likeRequest.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!AppUtil.isConnectingToInternet(getContext())) {
            return;
        } else {
            AppConstants.likeActionList.add(channel_id + "_" + mFeedTypeId);
        }
        likeAPICall = new LikeActionAsync(getContext(), RestApiConstants.SOCIAL_INTERACTIONS, new OnTaskCompleted() {
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
                                if (selectedPosition != -1) {
                                    feedData.get(selectedPosition).optJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_SOCIALINTERACTION, likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                }
                                dashboardFeedsAdapter.notifyDataSetChanged();

                            } else {
                                if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage(jsonObject.getString(RestUtils.TAG_ERROR_MESSAGE));
                                        builder.setCancelable(true);
                                        builder.setPositiveButton("Verify Now", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent verifyLikeIntent = new Intent(getContext(), MCACardUploadActivity.class);
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
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        likeAPICall.execute(likeRequest.toString());
        dashboardFeedsAdapter.setLikeAPICallAsync(likeAPICall);
    }



    private String prepareGetCategoryListRequest() {
        JSONObject reqObj = new JSONObject();
        try {
            int categoryId = 0;
            if (catergoryIdsList.size() > 0) {
                categoryId = catergoryIdsList.get(0);
            }
            reqObj.put("category_id", categoryId);
            reqObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
            reqObj.put(RestUtils.PG_NUM, 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (getArguments() != null) {
                navigationFrom = getArguments().getString("NavigationFrom");
            }
            Log.e("Viewpager", "fragment is visible"+navigationFrom);
        }
    }

    public void updateCriteriaFilters(String location, String selected) {
        filtersHashMap.put("Location", location);
        filtersHashMap.put("selectedFilter", selected);
    }







/*
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible){
            if (getArguments() != null) {
                navigationFrom = getArguments().getString("NavigationFrom");
            }
            Log.e("Viewpager", "fragment is visible"+navigationFrom);
        }
    }
*/

    public synchronized void showProgress() {
        //if (progress == null && progress.getActivity() == null) {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
    }

    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        location = "";
        speciality="";
        button_location.setText(R.string.location);
        button_speciality.setText(R.string.speciality);
        button_clear.setVisibility(View.GONE);
        //set location text to normal
        button_location.setBackgroundResource(R.drawable.professional_jobs_filters_bg);
        button_location.setTextColor(getResources().getColor(R.color.black));
        button_location.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
        //set workplace text normal
        //set speciality text normal
        button_speciality.setBackgroundResource(R.drawable.professional_jobs_filters_bg);
        button_speciality.setTextColor(getResources().getColor(R.color.black));
        button_speciality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
        filterApplied=false;
    }

    public void resetFilterStatus() {
        filterApplied=false;
        location = "";
        speciality="";
        button_location.setText(R.string.location);
        button_speciality.setText(R.string.speciality);
        button_clear.setVisibility(View.GONE);
        //set location text to normal
        button_location.setBackgroundResource(R.drawable.professional_jobs_filters_bg);
        button_location.setTextColor(getResources().getColor(R.color.black));
        button_location.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
        //set workplace text normal
        //set speciality text normal
        button_speciality.setBackgroundResource(R.drawable.professional_jobs_filters_bg);
        button_speciality.setTextColor(getResources().getColor(R.color.black));
        button_speciality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);


        if(AppUtil.isConnectingToInternet(getContext())) {
            shimmerContentLayout.setVisibility(View.VISIBLE);
            shimmerContainer.startShimmerAnimation();
            shimmerContainer1.startShimmerAnimation();

            contentFeeds_pageNum=0;
            requestForContentFeeds(contentFeeds_pageNum, false, 0);
        }else{
            shimmerContentLayout.setVisibility(View.GONE);
        }
    }
}
