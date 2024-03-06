package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.NetworkSearchAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.fragments.SpecialityDialogFragment;
import com.vam.whitecoats.ui.interfaces.NavigateScreenListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.InviteConnectStatusUpdateEvent;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class UserSearchResultsActivity extends BaseActionBarActivity implements SpecialityDialogFragment.NoticeDialogListener,
        NavigateScreenListener {

    public static final String TAG = UserSearchResultsActivity.class.getSimpleName();
    private Realm realm;
    private RealmManager realmManager;
    int docID;
    View view;
    private CustomLinearLayoutManager mLayoutManager;
    private SearchView searchView;
    //private EditText searchTextView;
    TextView noNetwresults;
    Button button_clear, button_location, button_speciality, button_workplace;
    private ArrayList<String> specialitiesList = new ArrayList<>();
    String speciality = "";
    LinearLayout layoutSearchList;
    private SwipeRefreshLayout swipeContainer_userSearch;
    RecyclerView mSearchRecyclerView;
    String searchText = "", specialist = "", location = "", workPlace = "";
    private NetworkSearchAdapter usersSearchAdapter;
    private List<ContactsInfo> userssearchResultList = new ArrayList<>();
    int pageNum = 1;
    private SharedPreferences editor;
    public static final String MyPREFERENCES = "MyPrefs";
    Bundle bundle;
    private String navigation;
    public static int selectedPosition = -1;
    private HashMap<String, String> filtersHashMap = new HashMap<>();
    // private InvitedStatusReceiver invitedStatusReceiver;
    private HorizontalScrollView userFiltersList;
    private boolean customBackButton = false;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchUserSearchLocationResults, launchVoiceInputResults, launcherInviteOrVisitProfileResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_search_results);
        searchText = getIntent().getExtras().getString("globalsearchText");
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        docID = realmManager.getDoc_id(realm);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            navigation = bundle.getString(RestUtils.NAVIGATATION);
        }
        initilize();
        filtersHashMap = new HashMap<>();
        updateCriteriaFilters("", "", "", "");
        EventBus.getDefault().register(this);


        performSearchRequest(1, searchText, location, specialist, workPlace, false);
        swipeContainer_userSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JSONObject requestData = new JSONObject();
                if (AppUtil.isConnectingToInternet(mContext)) {
                    try {
                        performSearchRequest(1, searchText, location, specialist, workPlace, false);
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
        usersSearchAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (AppUtil.isConnectingToInternet(mContext)) {
                    try {
                        //userssearchResultList.add(null);
                        pageNum++;
                        performSearchRequest(pageNum, searchText, location, specialist, workPlace, true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchUserSearchLocationResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                });
        launchVoiceInputResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result1 -> {
                    //request code 100
                    int resultCode = result1.getResultCode();
                    Intent data = result1.getData();
                    if (resultCode == RESULT_OK && null != data) {
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        searchText = result.get(0);
                        searchView.setQuery(searchText,false);
                        searchView.clearFocus();
                        performSearchRequest(1, searchText, location, specialist, workPlace, false);
                    }
                });
        //End


        swipeContainer_userSearch.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
    }

    //set default HashMap Values to custom method
    public void updateCriteriaFilters(String location, String speciality, String workplace, String selected) {
        filtersHashMap.put("Location", location);
        filtersHashMap.put("Speciality", speciality);
        filtersHashMap.put("Workplace", workplace);
        filtersHashMap.put("selectedFilter", selected);
    }

    private void initilize() {
        button_clear = findViewById(R.id.btn_clear);
        button_location = findViewById(R.id.btn_Location);
        button_speciality = findViewById(R.id.btn_speciality);
        button_workplace = findViewById(R.id.btn_workplace);

        layoutSearchList = _findViewById(R.id.layout_search_list);
        swipeContainer_userSearch = findViewById(R.id.swipeContainer_userSearch);
        mSearchRecyclerView = _findViewById(R.id.searchRecyclerView);
        noNetwresults = _findViewById(R.id.noNetworkResults_txt);
        userFiltersList = (HorizontalScrollView) findViewById(R.id.user_filters_list);
        int resId = R.anim.item_animation_from_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(UserSearchResultsActivity.this, resId);
        animation.getAnimation().setDuration(500);
        userFiltersList.setLayoutAnimation(animation);
        /* auto text for speciality
         */
        ArrayList<String> searchkeys = new ArrayList<String>();
        searchkeys.add("specialities");

        if (AppUtil.isConnectingToInternet(mContext)) {
            new AutoSuggestionsAsync(UserSearchResultsActivity.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
        }
        //clear filter values onClick
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specialist = "";
                location = "";
                workPlace = "";
                button_location.setText(R.string.location);
                button_speciality.setText(R.string.speciality);
                button_workplace.setText(R.string.workPlace);
                button_clear.setVisibility(View.GONE);
                //set location text to normal
                button_location.setBackgroundResource(R.drawable.border_gray_text);
                button_location.setTextColor(getResources().getColor(R.color.black));
                button_location.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                //set workplace text normal
                button_workplace.setBackgroundResource(R.drawable.border_gray_text);
                button_workplace.setTextColor(getResources().getColor(R.color.black));
                button_workplace.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                //set speciality text normal
                button_speciality.setBackgroundResource(R.drawable.border_gray_text);
                button_speciality.setTextColor(getResources().getColor(R.color.black));
                button_speciality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);

                if (AppUtil.isConnectingToInternet(UserSearchResultsActivity.this)) {
                    try {
                        performSearchRequest(1, searchText, location, specialist, workPlace, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtil.isConnectingToInternet(UserSearchResultsActivity.this)) {
                    updateCriteriaFilters(filtersHashMap.get("Location"), filtersHashMap.get("Speciality"), filtersHashMap.get("Wrokplace"), "fromLocation");
                    Intent filterLocationSearch = new Intent(UserSearchResultsActivity.this, FilterSearchActivity.class);
                    filterLocationSearch.putExtra("filter_map", filtersHashMap);
                    launchUserSearchLocationResults.launch(filterLocationSearch);
                }
            }
        });
        button_speciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(UserSearchResultsActivity.this)) {
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
                            specialist = selectedItem.toString();

                            button_speciality.setBackgroundResource(R.drawable.filter_border);
                            button_speciality.setTextColor(getResources().getColor(R.color.white));
                            button_speciality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_white, 0);

                            button_clear.setVisibility(View.VISIBLE);
                            performSearchRequest(1, searchView.getQuery().toString().trim(), location, specialist, workPlace, false);
                        }
                    });
                    specialityDialogFragment.show(getSupportFragmentManager(), SpecialityDialogFragment.TAG);
                }
            }
        });
        button_workplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtil.isConnectingToInternet(UserSearchResultsActivity.this)) {

                    updateCriteriaFilters(filtersHashMap.get("Location"), filtersHashMap.get("Speciality"), filtersHashMap.get("Wrokplace"), "fromworkplace");
                    Intent filterWorkplaceSearch = new Intent(UserSearchResultsActivity.this, FilterSearchActivity.class);
                    //filterWorkplaceSearch.putExtra(RestUtils.NAVIGATATION, "fromworkplace");
                    filterWorkplaceSearch.putExtra("filter_map", filtersHashMap);
                    launchUserSearchLocationResults.launch(filterWorkplaceSearch);
                }
            }
        });

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launcherInviteOrVisitProfileResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code == 1
                    int resultCode = result.getResultCode();
                    if (resultCode == 1) {
                        if (selectedPosition != -1) {
                            userssearchResultList.get(selectedPosition).setNetworkStatus("" + 1);
                            usersSearchAdapter.notifyDataSetChanged();
                        }
                    }
                });
        //End


        mSearchRecyclerView.setHasFixedSize(true);
        mLayoutManager = new CustomLinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchRecyclerView.setLayoutManager(mLayoutManager);
        usersSearchAdapter = new NetworkSearchAdapter(UserSearchResultsActivity.this, userssearchResultList, mSearchRecyclerView);
        mSearchRecyclerView.setAdapter(usersSearchAdapter);

        mSearchRecyclerView.addItemDecoration(HeaderDecoration.with(mSearchRecyclerView)
                .inflate(R.layout.chat_list_header)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, mContext));
    }

    public void performSearchRequest(int pageNum, String searchText, String location, String specialist, String workPlace, boolean isFromOnScroll) {
        if (AppUtil.isConnectingToInternet(UserSearchResultsActivity.this)) {
            try {
                JSONObject requestObj = new JSONObject();
                requestObj.put(RestUtils.TAG_USER_ID, docID);
                requestObj.put(RestUtils.TAG_SEARCH_IN, "users");
                requestObj.put(RestUtils.TAG_SEARCH_TEXT, searchText);
                requestObj.put(RestUtils.TAG_PAGE_NUM, pageNum);
                JSONObject searchCriteriaObj = new JSONObject();
                searchCriteriaObj.put(RestUtils.TAG_USER_NAME, "");
                searchCriteriaObj.put(RestUtils.TAG_SPECIALIST, specialist);
                searchCriteriaObj.put(RestUtils.TAG_LOCATION, location);
                searchCriteriaObj.put(RestUtils.TAG_WORKPLACE, workPlace);
                requestObj.put(RestUtils.TAG_SEARCH_CRITERIA, searchCriteriaObj);
                requestObj.put(RestUtils.TAG_FILTER, new JSONObject().put(RestUtils.TAG_SEARCH_TYPE, new JSONArray()));

                if (searchText.trim().length() == 0) {
                    searchUserResponse(requestObj, isFromOnScroll);
                } else if (searchText.trim().length() < 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserSearchResultsActivity.this);
                    builder.setMessage("Please enter minimum 3 characters for search");
                    builder.setCancelable(true);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                } else {
                    searchUserResponse(requestObj, isFromOnScroll);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void searchUserResponse(final JSONObject requestObj, final boolean isFromOnScroll) {
        Log.i(TAG, "requestNetworkSearch() - " + requestObj);
        showProgress();
        new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.SEARCH_RESULTS, requestObj.toString(), TAG, new OnReceiveResponse() {

            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i(TAG, "onSuccessResponse()");
                if (swipeContainer_userSearch != null) {
                    swipeContainer_userSearch.setRefreshing(false);
                }

                if (successResponse != null) {
                    try {
                        hideProgress();
                        if (!isFromOnScroll) {
                            userssearchResultList.clear();
                        } else if (userssearchResultList.size() > 0 && userssearchResultList.contains(null)) {
                            userssearchResultList.remove(null);
                            usersSearchAdapter.notifyItemRemoved(userssearchResultList.size());
                        }
                        JSONObject responseObj = new JSONObject(successResponse);
                        if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            String status = responseObj.optString(RestUtils.TAG_STATUS);
                            if (status.equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                JSONArray searchResults = dataObj.optJSONArray(RestUtils.TAG_CONTACT_SEARCH_RESULTS);
                                int len = searchResults.length();

                                for (int i = 0; i < len; i++) {
                                    JSONObject searchResult = searchResults.optJSONObject(i);
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

                                    userssearchResultList.add(contactsInfo);
                                }
                                // If there are no search results
                                if (userssearchResultList.size() == 0) {
                                    noNetwresults.setVisibility(View.VISIBLE);
                                    layoutSearchList.setVisibility(View.GONE);
                                } else {
                                    noNetwresults.setVisibility(View.GONE);
                                    layoutSearchList.setVisibility(View.VISIBLE);
                                }
                                // set Navigation params to Network tab when back button clicked
                           /* if (layoutSearchFields.getVisibility() == View.VISIBLE) {
                                isResultsPage = false;
                            } else {
                                isResultsPage = true;

                            }*/
                                if (isFromOnScroll && len == 0) {
                                    Toast.makeText(UserSearchResultsActivity.this, getResources().getString(R.string.search_result), Toast.LENGTH_SHORT).show();
                                }
                                usersSearchAdapter.notifyDataSetChanged();


                                if (len > 0) {
                                    usersSearchAdapter.setLoaded();
                                }


                                if (isFromOnScroll && len == 0) {
                                    Toast.makeText(UserSearchResultsActivity.this, getResources().getString(R.string.search_result), Toast.LENGTH_SHORT).show();
                                }
                            }

                            //coachmark
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    presentShowcaseSequence();
                                }
                            }, 1000);
                        } else if (requestObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            hideProgress();
                            displayErrorScreen(successResponse);

                        }
                        AppUtil.logUserActionEvent(docID, "Doctor_SearchInitiation", requestObj, AppUtil.convertJsonToHashMap(requestObj), UserSearchResultsActivity.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                hideProgress();
                usersSearchAdapter.setLoaded();
                Log.i(TAG, "onErrorResponse()");
                if (swipeContainer_userSearch != null) {
                    swipeContainer_userSearch.setRefreshing(false);
                }
                if (AppUtil.isConnectingToInternet(UserSearchResultsActivity.this)) {
                    //Toast.makeText(UserSearchResultsActivity.this, getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserSearchResultsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                try {
                    if (errorResponse != null) {
                        JSONObject errorJson = new JSONObject(errorResponse);
                        displayErrorScreen(errorResponse);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).sendSinglePartRequest();
    }

    public void presentShowcaseSequence() {
        if (AppConstants.COACHMARK_INCREMENTER > 0) {
            MaterialShowcaseView.resetSingleUse(UserSearchResultsActivity.this, "Sequence_Search_Connects");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500); // half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(UserSearchResultsActivity.this, "Sequence_Search_Connects");
            sequence.setConfig(config);
            if (!editor.getBoolean("search_connects", false)) {
                if (mSearchRecyclerView.getChildAt(0) != null) {
                    if (mSearchRecyclerView.getChildAt(0).findViewById(R.id.imageurl) != null) {
                        sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(UserSearchResultsActivity.this)
                                        .setTarget(mSearchRecyclerView.getChildAt(0).findViewById(R.id.imageurl))
                                        .setDismissText("Got it")
                                        .setDismissTextColor(Color.parseColor("#00a76d"))
                                        .setMaskColour(Color.parseColor("#CC231F20"))
                                        .setContentText(R.string.tap_to_coach_mark_search).setListener(new IShowcaseListener() {
                                    @Override
                                    public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                        editor.edit().putBoolean("search_connects", true).commit();
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
        if (getIntent().getExtras() != null) {
            if (!isConnectingToInternet()) {
                hideProgress();
            }
        } else {
            searchView.requestFocus();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!isConnectingToInternet()){
                    return false;
                }
                try {
                    if (query.trim().length() < 3) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserSearchResultsActivity.this);
                        builder.setMessage("Please enter minimum 3 characters for search");
                        builder.setCancelable(true);
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                    }else{
                        searchView.clearFocus();
                        searchText = searchView.getQuery().toString();
                        performSearchRequest(1, searchText, location, specialist, workPlace, false);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView.setFocusable(true);
                if (newText.length() > 70) {
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
                    AppUtil.logUserUpShotEvent("DoctorSearchBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("DoctorSearchDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent();
        intent.putExtra("usersearchString", searchText);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onTaskCompleted(String response) {
        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
                hideProgress();
                ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);

                        JSONArray specialitiesjArray = data.getJSONArray("specialities");

                        if (specialitiesjArray.length() > 0) {
                            for (int i = 0; i < specialitiesjArray.length(); i++) {
                                specialitiesList.add(specialitiesjArray.get(i).toString());
                            }
                        }
                    } else if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        hideProgress();
                        String errorMsg = getResources().getString(R.string.unknown_server_error);
                        if (!jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE).isEmpty()) {
                            errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                        }
                        ShowSimpleDialog("Error", errorMsg);
                    } else {
                        hideProgress();
                        ShowSimpleDialog("Error", getResources().getString(R.string.unknown_server_error));
                    }
                } catch (Exception e) {
                    if (response.contains("FileNotFoundException")) {
                        ShowSimpleDialog("Error", getResources().getString(R.string.unknown_server_error));
                    }
                    hideProgress();
                    e.printStackTrace();
                }
            }
        }
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
        if (resultCode == RESULT_OK) {
            /*Refactoring the deprecated startActivityForResults*/
            if (data != null && data.hasExtra("criteriaFilter")) {
                filtersHashMap = (HashMap<String, String>) data.getExtras().getSerializable("criteriaFilter");
                location = filtersHashMap.get("Location");
                workPlace = filtersHashMap.get("Workplace");

                if (filtersHashMap != null && filtersHashMap.get("selectedFilter") != null) {
                    if (filtersHashMap.get("selectedFilter").equalsIgnoreCase("fromlocation")) {
                        button_location.setText(location);
                        button_location.setBackgroundResource(R.drawable.filter_border);
                        button_location.setTextColor(getResources().getColor(R.color.white));
                        button_location.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_white, 0);
                        performSearchRequest(1, searchText, location, specialist, workPlace, false);
                    } else if (filtersHashMap.get("selectedFilter").equalsIgnoreCase("fromworkplace")) {
                        button_workplace.setText(workPlace);
                        button_workplace.setBackgroundResource(R.drawable.filter_border);
                        button_workplace.setTextColor(getResources().getColor(R.color.white));
                        button_workplace.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_white, 0);
                        performSearchRequest(1, searchText, location, specialist, workPlace, false);
                    }
                }

                button_clear.setVisibility(View.VISIBLE);
            }

        } else if (resultCode == 1) {
            if (selectedPosition != -1) {
                userssearchResultList.get(selectedPosition).setNetworkStatus("" + 1);
                usersSearchAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onDialogListItemSelect(String selectedItem) {
        Log.i(TAG, "onDialogListItemSelect()");
        if (selectedItem == null)
            selectedItem = "";
        speciality = selectedItem;
        button_speciality.setText(selectedItem);
        specialist = selectedItem.toString();

        button_speciality.setBackgroundResource(R.drawable.filter_border);
        button_speciality.setTextColor(getResources().getColor(R.color.white));
        button_speciality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_white, 0);

        button_clear.setVisibility(View.VISIBLE);
        performSearchRequest(1, searchView.getQuery().toString().trim(), location, specialist, workPlace, false);
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
            performSearchRequest(1, searchText, location, specialist, workPlace, false);
        }

    }

    /*Refactoring the deprecated startActivityForResults*/
    @Override
    public void onScreenNavigate(Intent intent) {
        launcherInviteOrVisitProfileResults.launch(intent);
    }
}
