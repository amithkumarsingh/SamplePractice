package com.vam.whitecoats.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.droidninja.imageeditengine.views.imagezoom.ImageViewTouchBase;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.SubCategoriesDataResponse;
import com.vam.whitecoats.core.models.SubCategoriesInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.ContentFullView;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.ui.activities.JobFeedCompleteView;
import com.vam.whitecoats.ui.activities.MCACardUploadActivity;
import com.vam.whitecoats.ui.adapters.DashboardFeedsAdapter;
import com.vam.whitecoats.ui.adapters.SkillingFiltersAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.interfaces.CategoryListLoadmore;
import com.vam.whitecoats.ui.interfaces.OnFeedItemClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnSocialInteractionListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.SubCategoriesItemClickListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.EndlessRecyclerOnScrollListener;
import com.vam.whitecoats.utils.RequestLocationType;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.TabsTagId;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.vam.whitecoats.viewmodel.ExploreViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;

public class KnowledgeContentFeedsFragment extends Fragment implements UiUpdateListener, OnFeedItemClickListener, CategoryListLoadmore {

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
    public static String navigationFrom = "";
    public static int contentFeeds_pageNum = 0;
    private ArrayList<Integer> catergoryIdsList;
    ArrayList<SubCategoriesInfo> categoriesList = new ArrayList<SubCategoriesInfo>();
    private RecyclerView skilling_filters_list;
    public static SkillingFiltersAdapter skillingAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean mIsListExhausted = false;
    private boolean loading = false;
    public static int skilling_filters_page_num = 0;
    private EndlessRecyclerOnScrollListener recyclerScrollListener;
    private TextView no_bookMarks;
    private SwipeRefreshLayout swipeContainer_content_feeds;
    public static boolean skillingFilterClicked = false;
    public static int subCategoryId = 0;
    private ViewGroup shimmerContentLayout;
    private ShimmerFrameLayout shimmerContainer;
    private ShimmerFrameLayout shimmerContainer1;
    private ExploreViewModel dataViewModel;
    private ArrayList<Category> categoryList = new ArrayList();
    private int page_num = 0;
    private int categoriesListPageNum;
    private boolean categoriesListMoreLoad;
    private Boolean isListExhausted;
    private boolean isFragmentVisible;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CallbackCollectionManager.getInstance().registerListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_feeds_layout, container, false);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.feeds_list);
        skilling_filters_list = (RecyclerView) view.findViewById(R.id.skilling_filters_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new CustomLinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        feedData = new ArrayList<JSONObject>();
        catergoryIdsList = new ArrayList<>();
        dataViewModel = ViewModelProviders.of(this).get(ExploreViewModel.class);
        no_bookMarks = (TextView) view.findViewById(R.id.no_bookMarks);
        swipeContainer_content_feeds = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer_content_feeds);
        shimmerContentLayout = (ViewGroup) view.findViewById(R.id.shimmer_content_layout);
        shimmerContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerContainer1 = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container1);

        if (getArguments() != null) {
            navigationFrom = getArguments().getString("NavigationFrom");
        }
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        skilling_filters_list.setLayoutManager(linearLayoutManager);
        skilling_filters_list.setAdapter(skillingAdapter);

        if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromProfessionalSkilling")) {
            skilling_filters_list.setVisibility(View.VISIBLE);
        } else {
            skilling_filters_list.setVisibility(View.GONE);
        }


        dashboardFeedsAdapter = new DashboardFeedsAdapter(getContext(), feedData, realmManager.getDoc_id(realm), mRecyclerView, channelsList, realmManager.getDocSalutation(realm) + " " + realmManager.getDoc_name(realm), navigationFrom, new OnSocialInteractionListener() {
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
        dashboardFeedsAdapter.setCategoryListLoadMoreListener(this);
        mRecyclerView.setAdapter(dashboardFeedsAdapter);

        dashboardFeedsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (AppUtil.isConnectingToInternet(getContext())) {
                    feedData.add(null);
                    contentFeeds_pageNum++;
                    dashboardFeedsAdapter.notifyItemInserted(feedData.size());
                    if (skillingFilterClicked) {
                        if (subCategoryId != 0)
                            sendLoadmoreDataRequest(contentFeeds_pageNum, subCategoryId);
                    } else {
                        sendLoadmoreDataRequest(contentFeeds_pageNum, 0);
                    }

                } else {
                   /* if (loading_progress_bookMarks.getVisibility() == View.VISIBLE) {
                        loading_progress_bookMarks.setVisibility(View.GONE);
                    }*/
                }
            }
        });
        if (AppUtil.isConnectingToInternet(getContext())) {
            shimmerContentLayout.setVisibility(View.VISIBLE);
            shimmerContainer.startShimmerAnimation();
            shimmerContainer1.startShimmerAnimation();
            contentFeeds_pageNum = 0;
            categoriesListPageNum = 0;
            requestForContentFeeds(contentFeeds_pageNum, false, 0);
        } else {
            shimmerContentLayout.setVisibility(View.GONE);
        }
        swipeContainer_content_feeds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppUtil.isConnectingToInternet(getContext())) {
                    skillingAdapter.selectedPosition = -1;
                    contentFeeds_pageNum = 0;
                    categoriesListPageNum = 0;
                    requestForContentFeeds(contentFeeds_pageNum, true, 0);

                } else {
                    if (swipeContainer_content_feeds != null) {
                        swipeContainer_content_feeds.setRefreshing(false);
                    }
                }
            }
        });
        swipeContainer_content_feeds.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        dataViewModel.isListExhausted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                isListExhausted = aBoolean;
                if (aBoolean) {
                    dashboardFeedsAdapter.setCategoryListExhaustedStatus(isListExhausted);
                }
            }
        });


        return view;
    }

    protected void sendLoadmoreDataRequest(int page_num, int subCategoryId) {
        requestForContentFeeds(page_num, false, subCategoryId);
    }

    public void requestForContentFeeds(int pageNum, boolean pullToRefresh, Integer subCategoryId) {

        new VolleySinglePartStringRequest(getContext(), Request.Method.POST, RestApiConstants.GET_CONTENT_FEEDS, prepareGetContentListRequest(pageNum, subCategoryId), navigationFrom, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
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
                                if (jsonObject.optJSONObject(RestUtils.TAG_DATA).has(RestUtils.FEED_DATA) && jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA) != null) {
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
                                            }
                                        }
                                        if (pageNum == 0) {
                                            requestForCategoriesData();
                                        }

                                        setUpNoFeedsInfoUI(feedData.size());
                                    } else {
                                        if (pageNum == 0) {
                                            setUpNoFeedsInfoUI(feedData.size());
                                        }
                                    }
                                } else {
                                    setUpNoFeedsInfoUI(feedData.size());
                                }

                            } else {
                                setUpNoFeedsInfoUI(feedData.size());
                            }

                        } else if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                            feedData.clear();
                            setUpNoFeedsInfoUI(feedData.size());
                            if (getContext() != null) {
                                // Toast.makeText(getContext(), jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
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
                shimmerContentLayout.setVisibility(View.GONE);
                if (errorResponse != null) {
                    try {
                        dashboardFeedsAdapter.setLoaded();
                        if (errorResponse.equalsIgnoreCase("network_error_retry")) {
                            setUpNoFeedsInfoUI(feedData.size());
                        } else {
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            setUpNoFeedsInfoUI(feedData.size());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (getContext() != null) {
                        // Toast.makeText(getContext(), errorResponse, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).sendSinglePartRequest();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* if (AppUtil.isConnectingToInternet(getContext())) {
            shimmerContentLayout.setVisibility(View.VISIBLE);
            shimmerContainer.startShimmerAnimation();
            shimmerContainer1.startShimmerAnimation();
            contentFeeds_pageNum = 0;
            categoriesListPageNum = 0;
            requestForContentFeeds(contentFeeds_pageNum, false, 0);
        } else {
            shimmerContentLayout.setVisibility(View.GONE);
        }*/
    }

    private String prepareGetContentListRequest(int page_num, int subCategoryId) {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
            reqObj.put(RestUtils.PG_NUM, page_num);
            reqObj.put("tag_id", TabsTagId.KNOWLEDGE_FEEDS.geTagId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }

    public static KnowledgeContentFeedsFragment newInstance(String navigatedFrom) {

        Bundle args = new Bundle();

        KnowledgeContentFeedsFragment fragment = new KnowledgeContentFeedsFragment();
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
                if (feedData.get(i) != null && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO) != null && newUpdate.optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID) == feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
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
            if (feedData.get(i) != null && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO) != null && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID) == feedId) {
                feedData.remove(i);
                break;
            }
        }
        dashboardFeedsAdapter.notifyDataSetChanged();
        setUpNoFeedsInfoUI(feedData.size());

    }

    @Override
    public void onBookmark(boolean isBookmarked, int feedID, boolean isAutoRefresh, JSONObject socialInteractionObj) {
        Log.i(TAG, "onBookmark(boolean isBookmarked,int feedID)");
        try {
            int position=0;
            int len=feedData.size();
            for(int i=0;i<len;i++){
                if(!feedData.get(i).has("SKIP_POSITION") && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)==feedID){
                    position=i;
                    break;
                }
            }
            feedData.get(position).getJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_IS_BOOKMARKED,isBookmarked);
            feedData.get(position).getJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_SOCIALINTERACTION,socialInteractionObj);
            dashboardFeedsAdapter.notifyDataSetChanged();
//            DashboardUpdatesFragment.getInstance().onBookmark(isBookmarked,feedID);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void notifyUIWithFeedSurveyResponse(int feedId, JSONObject surveyResponse) {
        int selectedPosition = -1;
        JSONObject feedObj = null;
        for (int i = 0; i < feedData.size(); i++) {
            try {
                if (feedData.get(i) != null && feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO) != null && feedId == Objects.requireNonNull(feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO)).optInt(RestUtils.TAG_FEED_ID)) {
                    selectedPosition = i;
                    feedObj = feedData.get(i);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        if (getContext() != null) {
            if (size == 0) {
                no_bookMarks.setText(getString(R.string.no_feeds));
                no_bookMarks.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                no_bookMarks.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
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


   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getArguments() != null) {
                navigationFrom = getArguments().getString("NavigationFrom");
            }
            Log.e("Viewpager", "fragment is visible" + navigationFrom);
            isFragmentVisible = isVisibleToUser;
        }
    }*/

   /* @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible){
            if (getArguments() != null) {
                navigationFrom = getArguments().getString("NavigationFrom");
            }
            Log.e("Viewpager", "fragment is visible"+navigationFrom);
        }
    }*/

    private void requestForCategoriesData() {
        dataViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_USER_CATEGORIES_LIST_V2, "USER_CATEGORY_LIST_KNOWLEDGE", AppUtil.getRequestHeaders(getActivity()), categoriesListPageNum, realmManager.getDoc_id(realm), RequestLocationType.REQUEST_LOCATION_Knowledge.getRequestLocation());
        dataViewModel.getAllExporeItems().observe(getViewLifecycleOwner(), categoriesApiResponse -> {
            if (categoriesApiResponse.getError() != null) {
                loading = false;
                return;
            } else {
                loading = false;
                categoryList.clear();
                categoryList.addAll(categoriesApiResponse.getCategories());
                if (categoryList.size() > 0) {
                    dashboardFeedsAdapter.setCategoriesListData(categoryList);
                    dashboardFeedsAdapter.setCategoriesListLoadingStatus(loading);
                    if (categoriesListPageNum == 0) {
                        dashboardFeedsAdapter.setCategoryListExhaustedStatus(false);
                        if (feedData.get(0).has("CATEGORY_ITEM_TYPE")) {
                            feedData.remove(0);
                        }
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("CATEGORY_ITEM_TYPE", "categoryList");
                            jsonObject.put("SKIP_POSITION", "0");
                            feedData.add(0, jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dashboardFeedsAdapter.notifyDataSetChanged();
                    }

                }
            }

        });

    }

    @Override
    public void onLoadMore() {
        this.categoriesListPageNum++;
        requestForCategoriesData();
    }


}
