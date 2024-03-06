package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.CategoriesFilterAdapter;
import com.vam.whitecoats.ui.adapters.NetworkSearchAdapter;
import com.vam.whitecoats.ui.adapters.RecommendationsRecyclerAdapter;
import com.vam.whitecoats.ui.interfaces.NavigateScreenListener;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.InviteConnectStatusUpdateEvent;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class GlobalSearchActivity extends BaseActionBarActivity implements NavigateScreenListener {
    public static final String TAG = GlobalSearchActivity.class.getSimpleName();
    private Realm realm;
    private RealmManager realmManager;
    int doc_id;
    private SearchView searchView;
    //private EditText searchTextView;
    private SwipeRefreshLayout swipeContainer_userSearch;
    private TextView noResultsTextView;
    Button btn_user_viewAll, btn_feed_viewAll;
    String searchText;
    RecyclerView recyclerview_users, recyclerview_feeds, recyclerview_searchtype;
    private JSONObject globalSearchRequestObj = null;
    private AVLoadingIndicatorView aviBookmarksList;
    private ArrayList<JSONObject> searchTypeResultList = new ArrayList<>();
    private List<ContactsInfo> contactsearchResultList = new ArrayList<>();
    private ArrayList<JSONObject> feedsearchResultList = new ArrayList<>();
    private CategoriesFilterAdapter categoriesFilterAdapter;
    private NetworkSearchAdapter contactsSearchAdapter;
    private RecommendationsRecyclerAdapter feedsSearchAdapter;
    private LinearLayout searchtype_lay, users_layout, feeds_layout;
    public static int selectedPosition = -1;
    //private InvitedStatusReceiver invitedStatusReceiver;
    private boolean customBackButton = false;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchUserSearchResults, launchFeedSearchResults, launchVoiceInputResults,
            launcherInviteOrVisitProfileResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.globalsearchactivity);
        searchText = getIntent().getExtras().getString("globalsearchText");
        initilize();
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        doc_id = realmManager.getDoc_id(realm);
        globalSearchRequest(searchText, true);

        /*IntentFilter filter = new IntentFilter();
        filter.addAction("INVITE_CONNECT_ACTION");*/
        EventBus.getDefault().register(this);

      /*  invitedStatusReceiver = new InvitedStatusReceiver();
        registerReceiver(invitedStatusReceiver, filter);*/

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchFeedSearchResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == RESULT_CANCELED) {
                        if (globalSearchRequestObj != null) {
                            if (data != null && data.getExtras()!=null) {
                                searchText = data.getExtras().getString("SearchFeedString");
                            }
                            globalSearchRequest(searchText, true);
                        }
                        if (data != null && data.getExtras() != null) {
                            /*Checking null condition to avoid the NullPointer crash in App*/
                            if (data.hasExtra("SearchFeedString")) {
                                if (data.getExtras().getString("SearchFeedString") != null) {
                                    searchView.setQuery(data.getExtras().getString("SearchFeedString"),false);
                                }
                            }
                        }
                    }
                });
        launchUserSearchResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == RESULT_OK) {
                        if (globalSearchRequestObj != null) {
                            if (data != null && data.getExtras()!=null) {
                                searchText = data.getExtras().getString("usersearchString");
                            }
                            globalSearchRequest(searchText, true);
                        }
                        if (data != null && data.getExtras() != null) {
                            /*Checking null condition to avoid the NullPointer crash in App*/
                            if (data.hasExtra("usersearchString")) {
                                if (data.getExtras().getString("usersearchString") != null) {
                                    searchView.setQuery(data.getExtras().getString("usersearchString"),false);

                                }
                            }
                        }
                    }
                });
        launchVoiceInputResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result1 -> {
                    int resultCode = result1.getResultCode();
                    Intent data = result1.getData();
                    if (resultCode == RESULT_OK && null != data) {
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        /*Checking null condition to avoid the NullPointer crash in App*/
                        if (result != null) {
                            if (result.get(0) != null) {
                                searchText = result.get(0);
                                searchView.setQuery(searchText,false);
                                searchView.clearFocus();
                                globalSearchRequest(searchText, true);
                            }
                        }
                    }
                });
        //End

        swipeContainer_userSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JSONObject requestData = new JSONObject();
                if (AppUtil.isConnectingToInternet(mContext)) {
                    try {
                        globalSearchRequest(searchText, true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (swipeContainer_userSearch != null) {
                        swipeContainer_userSearch.setRefreshing(false);
                    }
                }
            }
        });
        recyclerview_users.addItemDecoration(HeaderDecoration.with(recyclerview_users)
                .inflate(R.layout.chat_list_header)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, mContext));
        recyclerview_feeds.addItemDecoration(HeaderDecoration.with(recyclerview_users)
                .inflate(R.layout.chat_list_header)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, mContext));

        swipeContainer_userSearch.setColorSchemeResources(android.R.color.holo_blue_bright,
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleVoiceSearch(intent);
    }

    private static final String ACTION_VOICE_SEARCH = "com.google.android.gms.actions.SEARCH_ACTION";

    private void handleVoiceSearch(Intent intent) {
        if (ACTION_VOICE_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setSearchViewVisible(true);
            searchView.setQuery(query, true);
        }
    }

    private void initilize() {

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launcherInviteOrVisitProfileResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code == 1
                    int resultCode = result.getResultCode();
                    if (resultCode == 1) {
                        if (selectedPosition != -1) {
                            contactsearchResultList.get(selectedPosition).setNetworkStatus("" + 1);
                            contactsSearchAdapter.notifyDataSetChanged();
                        }
                    }
                });
//End

        btn_user_viewAll = findViewById(R.id.user_view_all);
        aviBookmarksList = (AVLoadingIndicatorView) findViewById(R.id.aviInBookmarksList);
        btn_feed_viewAll = _findViewById(R.id.feeds_all);
        searchtype_lay = findViewById(R.id.layout_btns);
        users_layout = findViewById(R.id.layout_users);
        feeds_layout = findViewById(R.id.layout_feeds);
        noResultsTextView = findViewById(R.id.noGlobalResults_txt);
        swipeContainer_userSearch = findViewById(R.id.swipeContainer_global);
        recyclerview_users = findViewById(R.id.recyclerview_users);
        recyclerview_feeds = findViewById(R.id.recyclerview_feeds);
        recyclerview_searchtype = findViewById(R.id.recyclerview_search_type);
        recyclerview_searchtype.setHasFixedSize(true);
        recyclerview_searchtype.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        categoriesFilterAdapter = new CategoriesFilterAdapter(this, searchTypeResultList, new OnCustomClickListener() {
            @Override
            public void OnCustomClick(View aView, int position) {
                searchText = searchView.getQuery().toString();
                String filterName = searchTypeResultList.get(position).optString("name");
                int filterId = searchTypeResultList.get(position).optInt("id");

                if (filterId == 0) {
                    Intent userSearch = new Intent(GlobalSearchActivity.this, UserSearchResultsActivity.class);
                    userSearch.putExtra("globalsearchText", searchText);
                    launchUserSearchResults.launch(userSearch);
                } else {
                    if (filterId == 1) {
                        Intent feedSearch = new Intent(GlobalSearchActivity.this, FeedSearchActivity.class);
                        feedSearch.putExtra("searchFeedText", searchText);
                        launchFeedSearchResults.launch(feedSearch);
                    }
                }
            }
        });
        recyclerview_searchtype.setAdapter(categoriesFilterAdapter);

        recyclerview_users.setHasFixedSize(true);
        recyclerview_users.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        contactsSearchAdapter = new NetworkSearchAdapter(GlobalSearchActivity.this, contactsearchResultList, recyclerview_users);
        recyclerview_users.setAdapter(contactsSearchAdapter);

        recyclerview_feeds.setHasFixedSize(true);
        recyclerview_feeds.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        feedsSearchAdapter = new RecommendationsRecyclerAdapter(this, feedsearchResultList, doc_id, recyclerview_feeds, new OnCustomClickListener() {
            @Override
            public void OnCustomClick(View aView, int position) {
                JSONObject requestJsonObject = new JSONObject();
                try {
                    requestJsonObject.put(RestUtils.TAG_DOC_ID, doc_id);
                    requestJsonObject.put(RestUtils.CHANNEL_ID, feedsearchResultList.get(position).optString(RestUtils.CHANNEL_ID));
                    requestJsonObject.put(RestUtils.FEED_ID, feedsearchResultList.get(position).optString(RestUtils.TAG_FEED_ID));
                    new VolleySinglePartStringRequest(GlobalSearchActivity.this, com.android.volley.Request.Method.POST, RestApiConstants.FEED_FULL_VIEW_UPDATED, requestJsonObject.toString(), "RECOMMENDATIONS_CONTENT_FULLVIEW", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            try {

                                JSONObject responseJsonObject = new JSONObject(successResponse);
                                JSONObject completeJsonObject = responseJsonObject.getJSONObject(RestUtils.TAG_DATA);
                                String contentProviderName = completeJsonObject.optString(RestUtils.FEED_PROVIDER_NAME);
                                String feedProviderType = completeJsonObject.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
                                int channel_id = completeJsonObject.optInt(RestUtils.CHANNEL_ID);
                                JSONObject feed_obj = completeJsonObject.getJSONObject(RestUtils.TAG_FEED_INFO);
                                Intent in = new Intent();
                                if (feed_obj.optString(RestUtils.TAG_FEED_TYPE).equalsIgnoreCase("article")) {
                                    in.setClass(GlobalSearchActivity.this, ContentFullView.class);
                                    in.putExtra(RestUtils.TAG_CONTENT_OBJECT, completeJsonObject.toString());
                                    in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, contentProviderName);
                                } else if (feed_obj.optString(RestUtils.TAG_FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
                                    in.setClass(GlobalSearchActivity.this, JobFeedCompleteView.class);
                                    in.putExtra(RestUtils.TAG_FEED_OBJECT, completeJsonObject.toString());
                                    if (feedProviderType.equalsIgnoreCase("Network")) {
                                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, true);
                                    } else {
                                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
                                    }
                                    in.putExtra(RestUtils.CHANNEL_NAME, contentProviderName);
                                } else {
                                    in.setClass(GlobalSearchActivity.this, FeedsSummary.class);
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
                            Log.i(TAG, "onErrorResponse()");
                            displayErrorScreen(errorResponse);
                        }
                    }).sendSinglePartRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerview_feeds.setAdapter(feedsSearchAdapter);

        btn_feed_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = searchView.getQuery().toString();
                Intent feedSearch = new Intent(GlobalSearchActivity.this, FeedSearchActivity.class);
                feedSearch.putExtra("searchFeedText", searchText);
                launchFeedSearchResults.launch(feedSearch);
            }
        });
        btn_user_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = searchView.getQuery().toString();
                Intent userSearch = new Intent(GlobalSearchActivity.this, UserSearchResultsActivity.class);
                userSearch.putExtra("globalsearchText", searchText);
                launchUserSearchResults.launch(userSearch);
            }
        });

    }

    public void globalSearchRequest(String searchText, boolean isFromOnScroll) {
        if (AppUtil.isConnectingToInternet(GlobalSearchActivity.this)) {
            try {
                globalSearchRequestObj = new JSONObject();
                globalSearchRequestObj.put(RestUtils.TAG_USER_ID, doc_id);
                globalSearchRequestObj.put(RestUtils.TAG_SEARCH_TEXT, searchText);
                globalSearchResponse(globalSearchRequestObj, isFromOnScroll);
                AppUtil.logUserActionEvent(doc_id, "Doctor_Feed_SearchInitiation", globalSearchRequestObj, AppUtil.convertJsonToHashMap(globalSearchRequestObj), GlobalSearchActivity.this);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void globalSearchResponse(final JSONObject requestObj, final boolean isFromOnScroll) {
        Log.i(TAG, "requestfeedSearch() - " + requestObj);
        aviBookmarksList.smoothToShow();
        if (!isFromOnScroll) {

        }
        new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.GLOBAL_SEARCH_RESULTS, requestObj.toString(), TAG, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i(TAG, "onSuccessResponse()");
                if (swipeContainer_userSearch != null) {
                    swipeContainer_userSearch.setRefreshing(false);
                }
                aviBookmarksList.smoothToHide();
                if (successResponse != null) {
                    try {
                        JSONObject responseObj = new JSONObject(successResponse);
                        if (responseObj == null) {
                            return;
                        }

                        String status = responseObj.optString(RestUtils.TAG_STATUS);
                        if (status.equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                            JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                            JSONObject filterObj = dataObj.optJSONObject(RestUtils.TAG_FILTERS);
                            JSONArray searchType = filterObj.optJSONArray(RestUtils.TAG_SEARCH_TYPE);
                            int len = searchType.length();
                            searchTypeResultList.clear();
                            contactsearchResultList.clear();
                            feedsearchResultList.clear();
                            for (int i = 0; i < len; i++) {
                                JSONObject searchDetails = searchType.optJSONObject(i);
                                searchTypeResultList.add(searchDetails);
                            }

                            JSONArray searchResults = dataObj.optJSONArray(RestUtils.TAG_CONTACT_SEARCH_RESULTS);
                            int userlen = searchResults.length();
                            for (int j = 0; j < userlen; j++) {
                                JSONObject searchResult = searchResults.optJSONObject(j);
                                ContactsInfo contactsInfo = new ContactsInfo();
                                contactsInfo.setDoc_id(searchResult.optInt(RestUtils.TAG_DOC_ID));
                                contactsInfo.setFull_name(searchResult.optString(RestUtils.TAG_USER_FULL_NAME));
                                contactsInfo.setName(searchResult.optString(RestUtils.TAG_USER_FULL_NAME));
                                contactsInfo.setDegree(searchResult.optString(RestUtils.TAG_DEGREES));
                                contactsInfo.setWorkplace(searchResult.optString(RestUtils.TAG_WORKPLACE));
                                contactsInfo.setQb_userid(searchResult.optInt(RestUtils.TAG_QB_USER_ID));
                                contactsInfo.setSpeciality(searchResult.optString(RestUtils.TAG_SPECIALIST));
                                contactsInfo.setSpecialist(searchResult.optString(RestUtils.TAG_SPECIALIST));
                                contactsInfo.setSubSpeciality(searchResult.optString(RestUtils.TAG_SUB_SPLTY));
                                contactsInfo.setLocation(searchResult.optString(RestUtils.TAG_LOCATION));
                                contactsInfo.setCnt_email(searchResult.optString(RestUtils.TAG_CNT_EMAIL));
                                contactsInfo.setCnt_num(searchResult.optString(RestUtils.TAG_CNT_NUM));
                                contactsInfo.setProfile_pic_name(searchResult.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                contactsInfo.setNetworkStatus(searchResult.optString(RestUtils.TAG_NETWORK_STATUS));
                                contactsInfo.setDesignation(searchResult.optString(RestUtils.TAG_DESIGNATION));
                                contactsInfo.setCommunity_designation(searchResult.optString(RestUtils.COMMUNITY_DESIGNATION));
                                contactsInfo.setProfile_pic_original_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                contactsInfo.setPic_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                contactsInfo.setProfile_pic_small_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_URL));
                                contactsInfo.setPic_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_URL));
                                contactsInfo.setUserSalutation(searchResult.optString(RestUtils.TAG_USER_SALUTAION));
                                contactsInfo.setUserTypeId(searchResult.optInt(RestUtils.TAG_USER_TYPE_ID));
                                contactsInfo.setPhno_vis(searchResult.optString(RestUtils.TAG_CNNTMUNVIS));
                                contactsInfo.setEmail_vis(searchResult.optString(RestUtils.TAG_CNNTEMAILVIS));
                                contactsInfo.setPhno_vis(searchResult.optString(RestUtils.TAG_CNNTMUNVIS));
                                contactsInfo.setEmail_vis(searchResult.optString(RestUtils.TAG_CNNTEMAILVIS));

                                contactsearchResultList.add(contactsInfo);
                            }
                            if (contactsearchResultList.size() == 0) {
                                users_layout.setVisibility(View.GONE);
                                btn_user_viewAll.setVisibility(View.GONE);

                            } else {
                                //aviBookmarksList.smoothToHide();
                                users_layout.setVisibility(View.VISIBLE);
                                btn_user_viewAll.setVisibility(View.VISIBLE);
                            }

                            JSONArray feedsearchResults = dataObj.optJSONArray(RestUtils.FEED_DATA);
                            int feedlen = feedsearchResults.length();
                            for (int k = 0; k < feedlen; k++) {
                                feedsearchResultList.add(feedsearchResults.optJSONObject(k));
                            }

                            if (feedsearchResultList.size() == 0) {
                                feeds_layout.setVisibility(View.GONE);
                                btn_feed_viewAll.setVisibility(View.GONE);

                            } else {
                                feeds_layout.setVisibility(View.VISIBLE);
                                btn_feed_viewAll.setVisibility(View.VISIBLE);
                            }
                            if (contactsearchResultList.size() == 0 && feedsearchResultList.size() == 0) {
                                searchtype_lay.setVisibility(View.GONE);
                                noResultsTextView.setVisibility(View.VISIBLE);
                            } else {
                                searchtype_lay.setVisibility(View.VISIBLE);
                                noResultsTextView.setVisibility(View.GONE);
                            }
                            categoriesFilterAdapter.notifyDataSetChanged();
                            contactsSearchAdapter.notifyDataSetChanged();
                            feedsSearchAdapter.notifyDataSetChanged();
                        }
                        if (feedsearchResultList.size() == 0) {
                            feeds_layout.setVisibility(View.GONE);
                        } else {
                            feeds_layout.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                Log.i(TAG, "onErrorResponse()");
                if (swipeContainer_userSearch != null) {
                    swipeContainer_userSearch.setRefreshing(false);
                }
                aviBookmarksList.smoothToHide();
                if (!isFromOnScroll) {
                    //aviBookmarksList.smoothToHide();
                }
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_contacts, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem action_back = menu.findItem(R.id.action_back);
        MenuItem voiceSearch=menu.findItem(R.id.search_with_voice);

        searchView = (SearchView) searchItem.getActionView();
        searchView.onActionViewExpanded();
        searchView.setQuery(searchText,false);
        searchView.setFocusable(false);
        searchView.clearFocus();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GlobalSearchActivity.this);
                    builder.setMessage(R.string.empty_search_keyword);
                    builder.setCancelable(true);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                } else if (query.length() < 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GlobalSearchActivity.this);
                    builder.setMessage(R.string.min_characters_search_keyword);
                    builder.setCancelable(true);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                } else {
                    if (isConnectingToInternet()) {
                        //aviBookmarksList.smoothToShow();
                        searchView.clearFocus();
                        //searchText = v.getText().toString();
                        globalSearchRequest(query, false);
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

        voiceSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startVoiceInput();
                return false;
            }
        });
        action_back.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("SearchBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return false;
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (selectedPosition != -1) {
                contactsearchResultList.get(selectedPosition).setNetworkStatus("" + 1);
                contactsSearchAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("SearchDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Intent backIn = new Intent();
        backIn.putExtra("globalsearchString", searchText);
        setResult(RESULT_CANCELED, backIn);
        super.onBackPressed();
    }

    private void setSearchViewVisible(boolean visible) {

        if (searchView.isIconified() == visible) {
            searchView.setIconified(!visible);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        }
    }

    @Override
    protected void onDestroy() {
        /*unregisterReceiver(invitedStatusReceiver);*/
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InviteConnectStatusUpdateEvent event) {
        if (event == null) {
            return;
        }

        String intentAction = event.getMessage();
        if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
            globalSearchRequest(searchText, false);
        }
    }

    /*Refactoring the deprecated startActivityForResults*/
    @Override
    public void onScreenNavigate(Intent intent) {
        launcherInviteOrVisitProfileResults.launch(intent);
    }
}
