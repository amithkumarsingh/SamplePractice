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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
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
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.TabsTagId;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ContentFeedsFragment extends Fragment implements UiUpdateListener, OnFeedItemClickListener {

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
    public static ArrayList<SubCategoriesInfo> categoriesListCheckSelected = new ArrayList<SubCategoriesInfo>();

    private RecyclerView skilling_filters_list;
    private TextView skillClearBtn;
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
    private int tagId;
    public static JSONArray subCategoryIdList;
    private View grayedOverlay;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CallbackCollectionManager.getInstance().registerListener(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_feeds_layout, container, false);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.feeds_list);
        skilling_filters_list = (RecyclerView) view.findViewById(R.id.skilling_filters_list);
        skillClearBtn = (TextView) view.findViewById(R.id.skillClearBtn);
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
        if (getArguments() != null) {
            navigationFrom = getArguments().getString("NavigationFrom");
        }
        grayedOverlay= (View) view.findViewById(R.id.grayOverlayLayout);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        skilling_filters_list.setLayoutManager(linearLayoutManager);
        skilling_filters_list.setAdapter(skillingAdapter);

        if (navigationFrom != null && navigationFrom.equalsIgnoreCase("fromProfessionalSkilling")) {
            skilling_filters_list.setVisibility(View.VISIBLE);
            skillingAdapter = new SkillingFiltersAdapter(getContext(), skilling_filters_list, new SubCategoriesItemClickListener() {
                @Override
                public void onItemClicked(int position) {

                }

                @Override
                public void onItemClickedData(SubCategoriesInfo dataModel, boolean checkboxclicked) {
                    if (checkboxclicked) {
                        skillingFilterClicked = true;
                        subCategoryId = dataModel.getSubCategoryId();

                        if (SkillingFiltersAdapter.checkUncheckCount > 0) {
                            skillClearBtn.setVisibility(View.VISIBLE);
                        }
//                        if (SkillingFiltersAdapter.subCategoryList.size() > 0) {
//                            skillClearBtn.setVisibility(View.VISIBLE);
//                        }
                    } else {
                        skillingFilterClicked = false;
                        subCategoryId = 0;
                        if (SkillingFiltersAdapter.checkUncheckCount == 0) {
                            skillClearBtn.setVisibility(View.GONE);
                        }
                    }
                    shimmerContentLayout.setVisibility(View.VISIBLE);
                    shimmerContainer.startShimmerAnimation();
                    shimmerContainer1.startShimmerAnimation();
                    contentFeeds_pageNum = 0;
                    grayedOverlay.setVisibility(View.VISIBLE);
                    grayedOverlay.setClickable(false);
                    /*getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/

                    requestForContentFeeds(contentFeeds_pageNum, false, subCategoryId);

                }
            });

            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            skilling_filters_list.setLayoutManager(linearLayoutManager);
            skilling_filters_list.setAdapter(skillingAdapter);
            skilling_filters_page_num = 0;
            //requestForSkillingFiltersData(skilling_filters_page_num);
        } else {
            skilling_filters_list.setVisibility(View.GONE);
        }

        skillClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < categoriesList.size(); i++) {
                    SubCategoriesInfo subCategoriesInfo = new SubCategoriesInfo();
                    subCategoriesInfo.setSubCategoryId(categoriesList.get(i).getSubCategoryId());
                    subCategoriesInfo.setCategoryType(categoriesList.get(i).getCategoryType());
                    subCategoriesInfo.setImageUrl(categoriesList.get(i).getImageUrl());
                    subCategoriesInfo.setRank(categoriesList.get(i).getRank());
                    subCategoriesInfo.setSubCategoryName(categoriesList.get(i).getSubCategoryName());
                    subCategoriesInfo.setUnreadCount(categoriesList.get(i).getUnreadCount());
                    subCategoriesInfo.setSelected(false);
                    categoriesList.set(i, subCategoriesInfo);
                }

                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                skilling_filters_list.setLayoutManager(linearLayoutManager);
                skilling_filters_list.setAdapter(skillingAdapter);
                skilling_filters_page_num = 0;
                skillClearBtn.setVisibility(View.GONE);
//                SkillingFiltersAdapter.subCategoryList.clear();
                SkillingFiltersAdapter.checkUncheckCount = 0;
                grayedOverlay.setVisibility(View.VISIBLE);
                grayedOverlay.setClickable(false);
                /*getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
                requestForContentFeeds(contentFeeds_pageNum, false, 0);

            }
        });

        recyclerScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (mIsListExhausted || !AppUtil.isConnectingToInternet(getActivity())) {
                    return;
                }
                int toatlcount = linearLayoutManager.getItemCount();
                int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                if (!loading) {
                    if (lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount - 1)) {
                        skillingAdapter.addDummyItemToList();
                        skilling_filters_page_num += 1;
                        // requestForSkillingFiltersData(skilling_filters_page_num);
                    } else {
                        loading = false;
                    }
                }
            }
        };
        skilling_filters_list.addOnScrollListener(recyclerScrollListener);


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
            grayedOverlay.setVisibility(View.VISIBLE);
            grayedOverlay.setClickable(false);
            /*getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
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
                    grayedOverlay.setVisibility(View.VISIBLE);
                    grayedOverlay.setClickable(false);
                    /*getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
                    requestForContentFeeds(contentFeeds_pageNum, true, 0);
                    skilling_filters_page_num = 0;
                    //requestForSkillingFiltersData(skilling_filters_page_num);

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


        return view;
    }


    protected void sendLoadmoreDataRequest(int page_num, int subCategoryId) {
        requestForContentFeeds(page_num, false, subCategoryId);
    }


    public void requestForContentFeeds(int pageNum, boolean pullToRefresh, Integer subCategoryId) {

        if (getContext() == null) {
            return;
        }
        new VolleySinglePartStringRequest(getContext(), Request.Method.POST, RestApiConstants.GET_CONTENT_FEEDS, prepareContentFeedListRequest(pageNum, subCategoryId), navigationFrom, new OnReceiveResponse() {
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

                                if (pageNum == 0 && !skillingFilterClicked) {
                                    if (jsonObject.optJSONObject(RestUtils.TAG_DATA).has("category_ids")) {
                                        JSONArray catergoryIdsArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray("category_ids");
                                        int channelsLength = catergoryIdsArray.length();
                                        Log.e("Contentskilling_resp", successResponse);
                                        for (int i = 0; i < channelsLength; i++) {
                                            int currentChannelObj = catergoryIdsArray.optInt(i);
                                            catergoryIdsList.add(currentChannelObj);
                                        }
                                        if (getContext() == null) {
                                            return;
                                        }
                                        new VolleySinglePartStringRequest(getContext(), Request.Method.POST, RestApiConstants.GET_USER_CATEGORIES_DISTRIBUTION, prepareSubCategoryListRequest(), "GETCONTENTCATERGORYLIST", new OnReceiveResponse() {
                                            @Override
                                            public void onSuccessResponse(String successResponse) {
                                                JSONObject jsonObject = null;
                                                if (successResponse != null) {
                                                    try {
                                                        jsonObject = new JSONObject(successResponse);
                                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                                            if (jsonObject.has(RestUtils.TAG_DATA)) {
                                                                ArrayList<SubCategoriesInfo> feedCategoriesList = new ArrayList<>();
                                                                if (jsonObject.optJSONObject(RestUtils.TAG_DATA).has("sub_categories")) {
                                                                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                                                    SubCategoriesDataResponse categoryDataResponse = gson.fromJson(successResponse, SubCategoriesDataResponse.class);
                                                                    List<SubCategoriesInfo> responseList = categoryDataResponse.getData().getSubCategories();
                                                                    if (responseList.size() != 0) {
                                                                        mIsListExhausted = false;
                                                                    } else {
                                                                        mIsListExhausted = true;
                                                                    }
                                                                    feedCategoriesList = new ArrayList<>();
                                                                    if (responseList != null) {
                                                                        feedCategoriesList.addAll(responseList);
                                                                    }
                                                                    if (feedCategoriesList != null && feedCategoriesList.size() > 0) {
                                                                /* if (page_num == 0) {
                                                                     categoriesList.clear();
                                                                 }*/

                                                                        if (categoriesList.size() > 0) {
                                                                            categoriesListCheckSelected.clear();

                                                                            for (int j = 0; j < categoriesList.size(); j++) {
                                                                                SubCategoriesInfo subCategoriesInfo = new SubCategoriesInfo();
                                                                                subCategoriesInfo.setSubCategoryId(categoriesList.get(j).getSubCategoryId());
                                                                                subCategoriesInfo.setCategoryType(categoriesList.get(j).getCategoryType());
                                                                                subCategoriesInfo.setImageUrl(categoriesList.get(j).getImageUrl());
                                                                                subCategoriesInfo.setRank(categoriesList.get(j).getRank());
                                                                                subCategoriesInfo.setSubCategoryName(categoriesList.get(j).getSubCategoryName());
                                                                                subCategoriesInfo.setUnreadCount(categoriesList.get(j).getUnreadCount());
                                                                                if (categoriesList.get(j).getSelected()) {
                                                                                    subCategoriesInfo.setSelected(true);
                                                                                } else {
                                                                                    subCategoriesInfo.setSelected(false);
                                                                                }

                                                                                categoriesListCheckSelected.add(subCategoriesInfo);

                                                                            }
                                                                        }
                                                                        categoriesList.clear();
                                                                        Log.e("ContentSubcat-response", successResponse);
                                                                        for (int i = 0; i < responseList.size(); i++) {
                                                                            SubCategoriesInfo subCategoriesInfo = new SubCategoriesInfo();
                                                                            subCategoriesInfo.setSubCategoryId(responseList.get(i).getSubCategoryId());
                                                                            subCategoriesInfo.setCategoryType(responseList.get(i).getCategoryType());
                                                                            subCategoriesInfo.setImageUrl(responseList.get(i).getImageUrl());
                                                                            subCategoriesInfo.setRank(responseList.get(i).getRank());
                                                                            subCategoriesInfo.setSubCategoryName(responseList.get(i).getSubCategoryName());
                                                                            subCategoriesInfo.setUnreadCount(responseList.get(i).getUnreadCount());
                                                                            if (categoriesListCheckSelected.size() > 0) {
                                                                                if (categoriesListCheckSelected.get(i).getSelected()) {
                                                                                    subCategoriesInfo.setSelected(true);
                                                                                } else {
                                                                                    subCategoriesInfo.setSelected(false);
                                                                                }
                                                                            } else {
                                                                                subCategoriesInfo.setSelected(false);
                                                                            }
                                                                            categoriesList.add(subCategoriesInfo);
                                                                        }
                                                                        skillingAdapter.setDataList(categoriesList);
                                                                        //EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnSubcategoriesLoad",categoryUnreadCount,categoryId));

                                                                    } else {
                                                                        if (getContext() != null) {
                                                                            // Toast.makeText(getContext(), "No more data available", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
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
                                                // Toast.makeText(getContext(), errorResponse, Toast.LENGTH_SHORT).show();

                                            }
                                        }).sendSinglePartRequest();
                                    }
                                }


                                if (jsonObject.optJSONObject(RestUtils.TAG_DATA).has(RestUtils.FEED_DATA) && jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA) != null) {
                                    JSONArray feedsArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA);
                                    int feedsLength = feedsArray.length();
                                    Log.e("ContentFeed-response", successResponse);
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
                                //Toast.makeText(getContext(), jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                grayedOverlay.setVisibility(View.GONE);
                grayedOverlay.setClickable(true);
                /*if(getActivity()!=null && getActivity().getWindow()!=null) {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }*/

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
                        //Toast.makeText(getContext(), errorResponse, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).sendSinglePartRequest();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private String prepareContentFeedListRequest(int page_num, int subCategoryId) {
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
//            if (skillingFilterClicked) {
//                reqObj.put("category_id", subCategoryId);
//            }
            if (SkillingFiltersAdapter.dataList != null) {
                subCategoryIdList = new JSONArray();
                for (int i = 0; i < SkillingFiltersAdapter.dataList.size(); i++) {
                    if (SkillingFiltersAdapter.dataList.get(i).getSelected()) {
                        subCategoryIdList.put(SkillingFiltersAdapter.dataList.get(i).getSubCategoryId());
                    }
                }
                reqObj.put("sub_category_ids", subCategoryIdList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("ContentFeed-Request", reqObj.toString());
        return reqObj.toString();
    }

    public static ContentFeedsFragment newInstance(String navigatedFrom) {

        Bundle args = new Bundle();

        ContentFeedsFragment fragment = new ContentFeedsFragment();
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
        try {
            int position = 0;
            int len = feedData.size();
            for (int i = 0; i < len; i++) {
                if (feedData.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID) == feedID) {
                    position = i;
                    break;
                }
            }
            feedData.get(position).getJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_IS_BOOKMARKED, isBookmarked);
            feedData.get(position).getJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
            dashboardFeedsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                                    try {
                                        feedData.get(selectedPosition).optJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_SOCIALINTERACTION, likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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

    private String prepareSkillingCategoryIdRequestData(int page_num) {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
            reqObj.put(RestUtils.PG_NUM, page_num);
            reqObj.put("tag_id", TabsTagId.PROFESSIONAL_SKILLING.geTagId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("ContentSkillingRequest", reqObj.toString());
        return reqObj.toString();
    }

    private String prepareSubCategoryListRequest() {
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
        Log.e("ContentSubcat-request", reqObj.toString());
        return reqObj.toString();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("Viewpager", "fragment is visible" + navigationFrom);
        }
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

    protected void setTag1(int tag) {
        this.tagId = tag;
        Log.e("tag_check    ", "" + tag);
    }
}