package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.CategoriesFilterAdapter;
import com.vam.whitecoats.ui.adapters.RecommendationsRecyclerAdapter;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;


public class FeedSearchActivity extends BaseActionBarActivity {
    public static final String TAG = FeedSearchActivity.class.getSimpleName();
    private TextView tv_noResults;
    private Realm realm;
    private RealmManager realmManager;
    RecyclerView mFeedsRecyclerView, recyclerview_feed_searchtype;
    //private EditText searchTextView;
    private RecommendationsRecyclerAdapter feedsSearchRecyclerAdapter;
    private SwipeRefreshLayout swipeContainer_feedSearch;
    ArrayList<JSONObject> feedList = new ArrayList<>();
    int doc_id;
    private AVLoadingIndicatorView feedsSearchAvi;
    private JSONObject feedSearchRequestObj = null;
    private JSONObject feedSearchfilterRequestObj = null;
    JSONArray filters = new JSONArray();
    String feeds_search;
    private SearchView searchView;
    int lastfeedId = 0;
    private AVLoadingIndicatorView aviBookmarksList;

    private CategoriesFilterAdapter categoriesFilterAdapter;
    private ArrayList<JSONObject> searchTypeResultList = new ArrayList<>();
    private LinearLayout searchtype_lay;
    private boolean customBackButton = false;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchVoiceInputResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_search);
        //setupActionbar();
        initilize();
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        doc_id = realmManager.getDoc_id(realm);
        feeds_search = getIntent().getExtras().getString("searchFeedText");
        requestdata(filters, feeds_search, false);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(FeedSearchActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFeedsRecyclerView.setLayoutManager(mLayoutManager);

        mFeedsRecyclerView.addItemDecoration(HeaderDecoration.with(mFeedsRecyclerView)
                .inflate(R.layout.chat_list_header)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, mContext));

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchVoiceInputResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result1 -> {
                    int resultCode = result1.getResultCode();
                    Intent data = result1.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        if (resultCode == RESULT_OK && null != data) {
                            lastfeedId = 0;
                            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            feeds_search = result.get(0);
                            searchView.setQuery(feeds_search,false);
                            searchView.clearFocus();
                            requestdata(filters, feeds_search, false);
                        }
                    }
                });
        //End

        feedsSearchRecyclerAdapter = new RecommendationsRecyclerAdapter(FeedSearchActivity.this, feedList, doc_id, mFeedsRecyclerView, new OnCustomClickListener() {
            @Override
            public void OnCustomClick(View aView, int position) {
                if (feedsSearchAvi.isShown()) {
                    return;
                }
                if (feedsSearchAvi != null) {
                    feedsSearchAvi.show();
                }
                JSONObject requestJsonObject = new JSONObject();
                try {
                    requestJsonObject.put(RestUtils.TAG_DOC_ID, doc_id);
                    requestJsonObject.put(RestUtils.CHANNEL_ID, feedList.get(position).optString(RestUtils.CHANNEL_ID));
                    requestJsonObject.put(RestUtils.FEED_ID, feedList.get(position).optString(RestUtils.TAG_FEED_ID));
                    new VolleySinglePartStringRequest(FeedSearchActivity.this, com.android.volley.Request.Method.POST, RestApiConstants.FEED_FULL_VIEW_UPDATED, requestJsonObject.toString(), "RECOMMENDATIONS_CONTENT_FULLVIEW", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            try {
                                if (feedsSearchAvi != null) {
                                    feedsSearchAvi.hide();
                                }
                                JSONObject responseJsonObject = new JSONObject(successResponse);
                                JSONObject completeJsonObject = responseJsonObject.getJSONObject(RestUtils.TAG_DATA);
                                String contentProviderName = completeJsonObject.optString(RestUtils.FEED_PROVIDER_NAME);
                                String feedProviderType = completeJsonObject.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
                                int channel_id = completeJsonObject.optInt(RestUtils.CHANNEL_ID);
                                JSONObject feed_obj = completeJsonObject.getJSONObject(RestUtils.TAG_FEED_INFO);
                                Intent in = new Intent();
                                if (feed_obj.optString(RestUtils.TAG_FEED_TYPE).equalsIgnoreCase("article")) {
                                    in.setClass(FeedSearchActivity.this, ContentFullView.class);
                                    in.putExtra(RestUtils.TAG_CONTENT_OBJECT, completeJsonObject.toString());
                                    in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, contentProviderName);
                                } else if (feed_obj.optString(RestUtils.TAG_FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
                                    in.setClass(FeedSearchActivity.this, JobFeedCompleteView.class);
                                    in.putExtra(RestUtils.TAG_FEED_OBJECT, completeJsonObject.toString());
                                    if (feedProviderType.equalsIgnoreCase("Network")) {
                                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, true);
                                    } else {
                                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
                                    }
                                    in.putExtra(RestUtils.CHANNEL_NAME, contentProviderName);
                                } else {
                                    in.setClass(FeedSearchActivity.this, FeedsSummary.class);
                                    in.putExtra(RestUtils.TAG_FEED_OBJECT, completeJsonObject.toString());
                                    if (feedProviderType.equalsIgnoreCase("Network")) {
                                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, true);
                                    } else {
                                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
                                    }
                                    in.putExtra(RestUtils.CHANNEL_NAME, contentProviderName);
                                }
                                in.putExtra(RestUtils.CHANNEL_ID, channel_id);
                                startActivity(in);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            if (feedsSearchAvi != null) {
                                feedsSearchAvi.hide();
                            }
                            Log.i(TAG, "onErrorResponse()");
                            Toast.makeText(FeedSearchActivity.this, getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                        }
                    }).sendSinglePartRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        feedsSearchRecyclerAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (AppUtil.isConnectingToInternet(mContext)) {
                    if (lastfeedId < 0) {
                        return;
                    }
                    feedList.add(null);
                    mFeedsRecyclerView.post(new Runnable() {
                        public void run() {
                            feedsSearchRecyclerAdapter.notifyItemInserted(feedList.size());
                        }
                    });

                    requestdata(filters, feeds_search, true);
                } else {
                    aviBookmarksList.smoothToHide();
                }
            }
        });
        mFeedsRecyclerView.setAdapter(feedsSearchRecyclerAdapter);

        swipeContainer_feedSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppUtil.isConnectingToInternet(mContext)) {
                    lastfeedId = 0;
                    requestdata(filters, feeds_search, false);
                } else {
                    if (swipeContainer_feedSearch != null) {
                        swipeContainer_feedSearch.setRefreshing(false);
                    }
                }
            }
        });
        swipeContainer_feedSearch.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void initilize() {
        tv_noResults = _findViewById(R.id.tv_noResult);
        searchtype_lay = findViewById(R.id.layout_btns);
        recyclerview_feed_searchtype = findViewById(R.id.recyclerview_feedsearch_type);
        recyclerview_feed_searchtype.setHasFixedSize(true);
        recyclerview_feed_searchtype.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mFeedsRecyclerView = _findViewById(R.id.feedsSearchList_list);
        feedsSearchAvi = _findViewById(R.id.aviInRecommendationsList);
        aviBookmarksList = (AVLoadingIndicatorView) findViewById(R.id.aviInBookmarksList);
        swipeContainer_feedSearch = (SwipeRefreshLayout) findViewById(R.id.swipeContainer_feeds);
        categoriesFilterAdapter = new CategoriesFilterAdapter(this, searchTypeResultList, new OnCustomClickListener() {
            @Override
            public void OnCustomClick(View aView, int position) {
                if (searchTypeResultList != null && searchTypeResultList.size() > 0) {
                    JSONObject filterObj = searchTypeResultList.get(position);
                    if (filterObj != null) {
                        lastfeedId = 0;
                        String filterName = filterObj.optString("name");
                        int filterId = filterObj.optInt("id");
                        JSONArray filterJsonArray = new JSONArray();
                        filterJsonArray.put(filterId);
                        requestdata(filterJsonArray, feeds_search, false);
                    }
                }
            }
        });
        recyclerview_feed_searchtype.setAdapter(categoriesFilterAdapter);
        int resId = R.anim.item_animation_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(FeedSearchActivity.this, resId);
        animation.getAnimation().setDuration(500);
        recyclerview_feed_searchtype.setLayoutAnimation(animation);

    }

    /*
    action bar set up
     */
    private void setupActionbar() {
        Log.i(TAG, "setupActionbar()");
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_feedsearch, null);
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_contacts, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem action_back = menu.findItem(R.id.action_back);
        MenuItem voiceSearch = menu.findItem(R.id.search_with_voice);

        searchView = (SearchView) searchItem.getActionView();
        searchView.onActionViewExpanded();
        searchView.setQuery(feeds_search,false);
        searchView.setFocusable(false);
        searchView.clearFocus();
        if (getIntent().getExtras() != null) {
            searchView.setQuery(feeds_search,false);
            if (!isConnectingToInternet())
                hideProgress();
        } else {
            searchView.requestFocus();
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.trim().length() <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FeedSearchActivity.this);
                    builder.setMessage(R.string.empty_search_keyword);
                    builder.setCancelable(true);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                } else if (query.trim().length() < 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FeedSearchActivity.this);
                    builder.setMessage(R.string.min_characters_search_keyword);
                    builder.setCancelable(true);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                } else {
                    if (isConnectingToInternet()) {
                        lastfeedId = 0;
                        feeds_search = searchView.getQuery().toString();
                        requestdata(filters, feeds_search, false);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                searchView.setFocusable(true);
                if (newText.length() > 70) {
                    System.out.println("Text character is more than 70");
                    searchView.setQuery(newText.substring(0, 70), false);
                }
                return false;
            }
        });




        voiceSearch.setOnMenuItemClickListener(menuItem -> {
            startVoiceInput();
            return false;
        });
        action_back.setOnMenuItemClickListener(item -> {
            customBackButton = true;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("FeedSearchBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
            return false;
        });
        return true;
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            launchVoiceInputResults.launch(intent);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
        }
    }

    public void requestdata(JSONArray filterArray, String feeds_search, boolean isFromOnScroll) {
        try {
            filters = filterArray;
            feedSearchRequestObj = new JSONObject();
            feedSearchfilterRequestObj = new JSONObject();
            feedSearchRequestObj.put(RestUtils.TAG_USER_ID, doc_id);
            feedSearchRequestObj.put(RestUtils.TAG_SEARCH_IN, "feeds");
            feedSearchRequestObj.put(RestUtils.LAST_FEED_ID, lastfeedId);
            feedSearchfilterRequestObj.put(RestUtils.TAG_SEARCH_TYPE, filterArray);
            feedSearchRequestObj.put("filter", feedSearchfilterRequestObj);
            feedSearchRequestObj.put(RestUtils.TAG_SEARCH_TEXT, feeds_search);

            feedSearchResponse(feedSearchRequestObj, isFromOnScroll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void feedSearchResponse(final JSONObject requestObj, final boolean isFromOnScroll) {
        Log.i(TAG, "requestfeedSearch() - " + requestObj);
        if (!isFromOnScroll) {
            aviBookmarksList.smoothToShow();
        }
        new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.SEARCH_RESULTS, requestObj.toString(), TAG, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i(TAG, "onSuccessResponse()");

                if (successResponse != null) {
                    if (!isFromOnScroll) {
                        aviBookmarksList.smoothToHide();
                    }
                    if (swipeContainer_feedSearch != null) {
                        swipeContainer_feedSearch.setRefreshing(false);
                    }
                    if (feedList.contains(null)) {
                        feedList.remove(feedList.size() - 1);
                        feedsSearchRecyclerAdapter.notifyItemRemoved(feedList.size());
                    }
                    try {
                        JSONObject responseObj = new JSONObject(successResponse);
                        if (responseObj == null) {
                            return;
                        }
                        String status = responseObj.optString(RestUtils.TAG_STATUS);
                        if (status.equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                            JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                            JSONArray feedarrayresults = dataObj.optJSONArray(RestUtils.FEED_DATA);
                            int len = feedarrayresults.length();
                            if (!isFromOnScroll) {
                                feedList.clear();
                                JSONObject filterObj = dataObj.optJSONObject(RestUtils.TAG_FILTERS);
                                JSONArray searchType = filterObj.optJSONArray(RestUtils.TAG_SEARCH_TYPE);
                                int searchlen = searchType.length();
                                searchTypeResultList.clear();
                                for (int i = 0; i < searchlen; i++) {
                                    JSONObject searchDetails = searchType.optJSONObject(i);
                                    searchTypeResultList.add(searchDetails);
                                }
                                if (searchTypeResultList.size() == 0) {
                                    searchtype_lay.setVisibility(View.GONE);
                                } else {
                                    searchtype_lay.setVisibility(View.VISIBLE);
                                }
                            }
                            for (int i = 0; i < len; i++) {
                                feedList.add(feedarrayresults.optJSONObject(i));
                            }
                            if (len > 0) {
                                lastfeedId = feedList.get(feedList.size() - 1).optInt("feed_id");
                            } else {
                                lastfeedId = -1;
                            }
                            if (isFromOnScroll && len == 0) {
                                Toast.makeText(FeedSearchActivity.this, getResources().getString(R.string.search_result), Toast.LENGTH_SHORT).show();
                            }
                            categoriesFilterAdapter.notifyDataSetChanged();
                            feedsSearchRecyclerAdapter.notifyDataSetChanged();
                            feedsSearchRecyclerAdapter.setLoaded();
                        }
                        if (feedList.size() == 0) {
                            tv_noResults.setVisibility(View.VISIBLE);
                        } else {
                            tv_noResults.setVisibility(View.GONE);
                        }
                        AppUtil.logUserActionEvent(doc_id, "Feed_SearchInitiation", feedSearchRequestObj, AppUtil.convertJsonToHashMap(feedSearchRequestObj), FeedSearchActivity.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                Log.i(TAG, "onErrorResponse()");
                if (feedList.contains(null)) {
                    feedList.remove(feedList.size() - 1);
                    feedsSearchRecyclerAdapter.notifyItemRemoved(feedList.size());
                }
                if (!isFromOnScroll) {
                    aviBookmarksList.smoothToHide();
                }
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();
    }


    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("FeedSearchDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Intent backIn = new Intent();
        backIn.putExtra("SearchFeedString", feeds_search);
        setResult(RESULT_CANCELED, backIn);
        super.onBackPressed();
    }
}
