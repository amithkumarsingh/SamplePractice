package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.view.MenuItemCompat;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.SearchContactsAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.LoadMoreListView;
import com.vam.whitecoats.ui.adapters.ContactsAdapter;
import com.vam.whitecoats.ui.interfaces.NavigateScreenListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import io.realm.Realm;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by swathim on 24-09-2015.
 */
public class SearchContactsActivity extends BaseActionBarActivity implements NavigateScreenListener {
    public static final String TAG = SearchContactsActivity.class.getSimpleName();
    private LoadMoreListView loadMoreListView;
    private int i = 1;
    private String search_keyword;
    private ContactsInfo contactsInfo;
    public static ArrayList<ContactsInfo> searchinfo;
    private TextView tv_noresults;

    private ContactsAdapter adapter;
    private SearchView searchView;

    private EditText searchTextView;

    private boolean enable_loadmore = false;

    String navigateFrom;
    int communityId;
    int channelid;
    int departmentId;
    Parcelable state;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    private Realm realm;
    private RealmManager realmManager;
    private String department_name;
    private boolean customBackButton = false;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launcherInviteResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_contacts);
        loadMoreListView = _findViewById(R.id.searchlist);
        tv_noresults = _findViewById(R.id.noResults_txt);
        /**
         * Get the bundle data from DepartmentMembersActivity for search operation.
         */
        Bundle bundle = getIntent().getExtras();
        navigateFrom = bundle.getString(RestUtils.NAVIGATATE_FROM);
        channelid = bundle.getInt(RestUtils.CHANNEL_ID, 0);
        departmentId = bundle.getInt(RestUtils.DEPARTMENT_ID, 0);
        department_name = bundle.getString(RestUtils.DEPARTMENT_NAME);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);

//        presentShowcaseSequence();

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launcherInviteResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // result code ==1
                    try {
                        searchinfo.clear();
                        loadMoreListView.setAdapter(null);
                        i = 1;
                        if (isConnectingToInternet()) {
                            if (navigateFrom != null && navigateFrom.equalsIgnoreCase(DepartmentMembersActivity.TAG)) {
                                new SearchContactsAsync(SearchContactsActivity.this, channelid, departmentId, navigateFrom, new OnTaskCompleted() {
                                    @Override
                                    public void onTaskCompleted(String s) {
                                        Log.d(TAG, "onTaskCompleted() inside onActivityResult()");
                                    }
                                }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                            } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase(TimeLine.TAG) && !TextUtils.isEmpty(searchTextView.getText().toString().trim())) {
                                new SearchContactsAsync(SearchContactsActivity.this, channelid, navigateFrom, new OnTaskCompleted() {
                                    @Override
                                    public void onTaskCompleted(String s) {
                                        Log.d(TAG, "onTaskCompleted() inside onQueryTextSubmit()");
                                    }
                                }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                            } else if (!TextUtils.isEmpty(searchTextView.getText().toString().trim()) && i == 1) {
                                new SearchContactsAsync(SearchContactsActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                            }
                        } else {
                            hideProgress();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        searchinfo = new ArrayList<ContactsInfo>();
        if (navigateFrom != null && navigateFrom.equalsIgnoreCase("TimeLine")) {
            upshotEventData(0, channelid, 0, "", "MembersSearch", "", "", "", false);
        } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase("DepartmentMembersActivity")) {
            upshotEventData(0, channelid, 0, "", "DepartmentMembersSearch", department_name, "", "", false);
        }

        loadMoreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!searchTextView.getText().toString().trim().equals("")) {
                    if (searchTextView.getText().toString().trim().equals(search_keyword) && (i == 1 || i > 1) && enable_loadmore == true) {
                        i = i + 1;
                        if (navigateFrom != null && navigateFrom.equalsIgnoreCase(DepartmentMembersActivity.TAG)) {
                            new SearchContactsAsync(SearchContactsActivity.this, channelid, departmentId, navigateFrom, new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String s) {
                                    Log.d(TAG, "onTaskCompleted() inside onLoadMore()");
                                }
                            }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                        } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase(TimeLine.TAG)) {
                            new SearchContactsAsync(SearchContactsActivity.this, channelid, navigateFrom, new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String s) {
                                    Log.d(TAG, "onTaskCompleted() inside onCreateOptionsMenu()");
                                }
                            }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                        } else {
                            new SearchContactsAsync(SearchContactsActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                        }
                    } else {
                        Toast.makeText(SearchContactsActivity.this, getResources().getString(R.string.search_result), Toast.LENGTH_LONG).show();
                    }
                } else {
                    searchinfo.clear();
                }
            }
        });
      /*  loadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (searchinfo.get(position).getNetworkStatus().equals("3")) {
                    new ShowCard(SearchContactsActivity.this, searchinfo.get(position)).goToChatWindow();
                }else if(searchinfo.get(position).getNetworkStatus().equals("0")){
                    Intent intent = new Intent(mContext, InviteRequestActivity.class);
                    intent.putExtra("search_query",searchTextView.getText().toString());
                    intent.putExtra("searchContactsInfo", searchinfo.get(position));
                    intent.putExtra("search", "search");
                    startActivityForResult(intent, 1);
                }

            }
        });*/
        editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void presentShowcaseSequence() {
        if (AppConstants.COACHMARK_INCREMENTER > 1) {
            MaterialShowcaseView.resetSingleUse(SearchContactsActivity.this, "Sequence_Search");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500); // half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "Sequence_Search");
            sequence.setConfig(config);
            if (!editor.getBoolean("Search_Contacts", false)) {
                if (loadMoreListView.getChildAt(0) != null) {
                    if (loadMoreListView.getChildAt(0).findViewById(R.id.imageurl) != null) {
                        sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(this)
                                        .setTarget(loadMoreListView.getChildAt(0).findViewById(R.id.imageurl))
                                        .setDismissText("Got it")
                                        .setDismissTextColor(Color.parseColor("#00a76d"))
                                        .setMaskColour(Color.parseColor("#CC231F20"))
                                        .setContentText(R.string.tap_to_coach_mark_search).setListener(new IShowcaseListener() {
                                    @Override
                                    public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                        editor.edit().putBoolean("Search_Contacts", true).commit();
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
            }
            sequence.start();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_contacts, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem action_back = menu.findItem(R.id.action_back);


        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.onActionViewExpanded();
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.edittext_bg);
        int searchTextViewId = getResources().getIdentifier("android:id/search_src_text", null, null);
        searchTextView = (EditText) searchView.findViewById(searchTextViewId);
        searchTextView.setHintTextColor(getResources().getColor(R.color.search_text_black));
        searchTextView.setTextColor(getResources().getColor(R.color.search_text_black));
        searchTextView.setHint("Search");
        if (getIntent().getExtras() != null) {
            searchTextView.setText(getIntent().getExtras().getString("search_query"));
            if (isConnectingToInternet()) {
                searchinfo.clear();
                search_keyword = searchTextView.getText().toString().trim();
                loadMoreListView.setAdapter(null);
                i = 1;
                if (navigateFrom != null && navigateFrom.equalsIgnoreCase(DepartmentMembersActivity.TAG) && !TextUtils.isEmpty(searchTextView.getText().toString().trim())) {
                    new SearchContactsAsync(SearchContactsActivity.this, channelid, departmentId, navigateFrom, new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String s) {
                            Log.d(TAG, "onTaskCompleted() inside onCreateOptionsMenu()");
                        }
                    }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase(TimeLine.TAG) && !TextUtils.isEmpty(searchTextView.getText().toString().trim())) {
                    new SearchContactsAsync(SearchContactsActivity.this, channelid, navigateFrom, new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String s) {
                            Log.d(TAG, "onTaskCompleted() inside onCreateOptionsMenu()");
                        }
                    }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                } else if (!TextUtils.isEmpty(searchTextView.getText().toString().trim()) && i == 1) {
                    new SearchContactsAsync(SearchContactsActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                    searchView.clearFocus();
                }
            } else {
                hideProgress();
            }
        } else {
            searchView.requestFocus();
        }
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(searchTextView, R.drawable.white_cursor);
            Field searchField = SearchView.class.getDeclaredField("mSearchButton");
            searchField.setAccessible(true);
            ImageView searchBtn = (ImageView) searchField.get(searchView);
            searchBtn.setImageResource(R.drawable.ic_search);
            searchField = SearchView.class.getDeclaredField("mCloseButton");
            searchField.setAccessible(true);
            final ImageView closeBtn = (ImageView) searchField.get(searchView);
            closeBtn.setImageResource(R.drawable.ic_close_white);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchTextView.setText("");
                    loadMoreListView.setAdapter(null);
                    enable_loadmore = true;
                    i = 1;
                    if (tv_noresults.getVisibility() == View.VISIBLE) {
                        tv_noresults.setVisibility(View.GONE);
                    }
                }
            });

            searchTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        closeBtn.setEnabled(false);
                        closeBtn.setImageResource(R.drawable.transparent);
                    } else {
                        closeBtn.setEnabled(true);
                        closeBtn.setImageResource(R.drawable.ic_close_white);
                    }
                    loadMoreListView.showLoadProgress();
                    search_keyword = s.toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } catch (NoSuchFieldException e) {
            Log.e("TAG", e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e("TAG", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                try {
                    searchinfo.clear();
                    loadMoreListView.setAdapter(null);
                    i = 1;
                    if (isConnectingToInternet()) {
                        if (navigateFrom != null && navigateFrom.equalsIgnoreCase(DepartmentMembersActivity.TAG) && !TextUtils.isEmpty(searchTextView.getText().toString().trim())) {
                            new SearchContactsAsync(SearchContactsActivity.this, channelid, departmentId, navigateFrom, new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String s) {
                                    Log.d(TAG, "onTaskCompleted() inside onQueryTextSubmit()");

                                }
                            }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                        } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase(TimeLine.TAG) && !TextUtils.isEmpty(searchTextView.getText().toString().trim())) {
                            new SearchContactsAsync(SearchContactsActivity.this, channelid, navigateFrom, new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String s) {
                                    Log.d(TAG, "onTaskCompleted() inside onQueryTextSubmit()");
                                }
                            }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                        } else if (!TextUtils.isEmpty(searchTextView.getText().toString().trim()) && i == 1) {
                            new SearchContactsAsync(SearchContactsActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                        }
                    } else {
                        hideProgress();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView.setFocusable(true);
                if (TextUtils.isEmpty(newText)) {
                    loadMoreListView.setAdapter(null);
                }
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
                    jsonObject.put("ChannelID", channelid);
                    String eventname = "";
                    if (navigateFrom.equalsIgnoreCase("TimeLine")) {
                        eventname = "MembersSearchBackTapped";
                    } else if (navigateFrom.equalsIgnoreCase("DepartmentMembersActivity")) {
                        eventname = "DepartmentMembersSearchBackTapped";
                        jsonObject.put("DepartmentName", department_name);
                    }
                    AppUtil.logUserUpShotEvent(eventname, AppUtil.convertJsonToHashMap(jsonObject));
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
    public void onTaskCompleted(String searchresponse) {
        Log.d(TAG, "onTaskCompleted()");
        try {
            hideProgress();
            if (searchresponse != null) {
                if (searchresponse.equals("SocketTimeoutException") || searchresponse.equals("Exception")) {
                    Log.d("Search Exception", searchresponse);
                    hideProgress();
                    ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(searchresponse);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            if (jsonObject.has(RestUtils.TAG_DATA)) {
                                JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                                JSONArray jsonArray = data.getJSONArray("contact_search_results");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        String jsonStringer = jsonArray.getString(i);
                                        JSONObject object = new JSONObject(jsonStringer);
                                        int doc_id = object.getInt(RestUtils.TAG_DOC_ID);
                                        String full_name = object.getString(RestUtils.TAG_USER_FULL_NAME);
                                        String specialty = object.getString(RestUtils.TAG_SPLTY);
                                        String sub_specialty = object.getString(RestUtils.TAG_SUB_SPLTY);
                                        String degree = object.getString(RestUtils.TAG_DEGREES);
                                        String networkStatus = object.getString(RestUtils.TAG_NETWORK_STATUS);
                                        String workplace = object.getString(RestUtils.TAG_WORKPLACE);
                                        String location = object.getString(RestUtils.TAG_LOCATION);
                                        String pf_img_data = object.getString(RestUtils.TAG_SMALL_FILE_DATA);
                                        String pf_img_name = object.getString(RestUtils.TAG_PROFILE_PIC_NAME);
                                        String pf_img_url = object.optString(RestUtils.TAG_PROFILE_PIC_URL, "");
                                        String e_mail = object.getString(RestUtils.TAG_CNT_EMAIL);
                                        String phno = object.getString(RestUtils.TAG_CNT_NUM);
                                        String designation = object.optString(RestUtils.TAG_DESIGNATION);
                                        String community_designation = object.optString(RestUtils.COMMUNITY_DESIGNATION);
                                        String pic_url = object.optString(RestUtils.TAG_PROFILE_PIC_URL);

                                        contactsInfo = new ContactsInfo();
                                        contactsInfo.setDoc_id(doc_id);
                                        contactsInfo.setName(full_name);
                                        contactsInfo.setSpeciality(specialty);
                                        contactsInfo.setSubSpeciality(sub_specialty);
                                        contactsInfo.setWorkplace(workplace);
                                        contactsInfo.setLocation(location);
                                        contactsInfo.setPic_data(pf_img_data);
                                        contactsInfo.setPic_name(pf_img_name);
                                        contactsInfo.setPic_name(pf_img_url);
                                        contactsInfo.setNetworkStatus(networkStatus);
                                        contactsInfo.setEmail(e_mail);
                                        contactsInfo.setPhno(phno);
                                        contactsInfo.setDegree(degree);
                                        contactsInfo.setPhno_vis("");
                                        contactsInfo.setEmail_vis("");
                                        contactsInfo.setDesignation(designation);
                                        contactsInfo.setCommunity_designation(community_designation);
                                        contactsInfo.setQb_userid(Integer.parseInt(object.getString("qb_user_id")));
                                        contactsInfo.setPic_url(pic_url);
                                        contactsInfo.setUserSalutation(object.optString(RestUtils.TAG_USER_SALUTAION));
                                        contactsInfo.setUserTypeId(object.optInt(RestUtils.TAG_USER_TYPE_ID));
                                        contactsInfo.setPhno_vis(object.optString(RestUtils.TAG_CNNTMUNVIS));
                                        contactsInfo.setEmail_vis(object.optString(RestUtils.TAG_CNNTEMAILVIS));
                                        searchinfo.add(contactsInfo);
                                    }
                                    if (searchinfo != null) {
                                        loadMoreListView.setVisibility(View.VISIBLE);
                                        tv_noresults.setVisibility(View.GONE);
                                        adapter = new ContactsAdapter(SearchContactsActivity.this, searchinfo, searchView.getQuery().toString(),
                                                navigateFrom);
                                        state = loadMoreListView.onSaveInstanceState();
                                        loadMoreListView.setAdapter(adapter);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                presentShowcaseSequence();
                                            }
                                        }, 1000);
//                                        presentShowcaseSequence();
                                        if (state != null) {
                                            loadMoreListView.onRestoreInstanceState(state);
                                        }
                                        loadMoreListView.onLoadMoreComplete();
                                        enable_loadmore = true;
                                        hideProgress();
                                    }
                                } else {
                                    if (jsonArray.length() == 0 && i == 1) {
                                        tv_noresults.setVisibility(View.VISIBLE);
                                        loadMoreListView.setVisibility(View.GONE);
                                        loadMoreListView.onLoadMoreComplete();
                                        hideProgress();
                                    } else if (jsonArray.length() == 0) {
                                        enable_loadmore = false;
                                        loadMoreListView.onLoadMoreComplete();
                                        loadMoreListView.hideLoadProgress();
                                        Toast.makeText(SearchContactsActivity.this, getResources().getString(R.string.search_result), Toast.LENGTH_LONG).show();
                                        hideProgress();
                                    }
                                }
                            }
                        } else {
                            hideProgress();
                            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                    ShowServerErrorSimpleDialog("Error", getResources().getString(R.string.session_timedout));
                                } else
                                    ShowServerErrorSimpleDialog("Error", getResources().getString(R.string.unknown_server_error));
                            }
                        }
                    } catch (Exception e) {
                        if (searchresponse.contains("FileNotFoundException")) {
                            ShowSimpleDialog("Error", getResources().getString(R.string.unknown_server_error));
                        }
                        hideProgress();
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            hideProgress();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("ChannelID", channelid);
                String eventname = "";
                if (navigateFrom != null && navigateFrom.equalsIgnoreCase("TimeLine")) {
                    eventname = "MembersSearchBackTapped";
                } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase("DepartmentMembersActivity")) {
                    eventname = "DepartmentMembersSearchBackTapped";
                    jsonObject.put("DepartmentName", department_name);
                }
                AppUtil.logUserUpShotEvent("DepartmentMembersDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (navigateFrom != null && navigateFrom.equalsIgnoreCase(DepartmentMembersActivity.TAG)) {
            setResult(DepartmentMembersActivity.REFRESH_MEMBERS_ACTION);
            finish();
        } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase(TimeLine.TAG)) {
            finish();
        } else {
            //Intent settings_intent = new Intent(SearchContactsActivity.this, DashboardActivity.class);
            //startActivity(settings_intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigateFrom = "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        try {
            if (requestCode == 1) {
                searchinfo.clear();
                loadMoreListView.setAdapter(null);
                i = 1;
                if (isConnectingToInternet()) {
                    if (navigateFrom != null && navigateFrom.equalsIgnoreCase(DepartmentMembersActivity.TAG)) {
                        new SearchContactsAsync(SearchContactsActivity.this, channelid, departmentId, navigateFrom, new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String s) {
                                Log.d(TAG, "onTaskCompleted() inside onActivityResult()");
                            }
                        }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                    } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase(TimeLine.TAG) && !TextUtils.isEmpty(searchTextView.getText().toString().trim())) {
                        new SearchContactsAsync(SearchContactsActivity.this, channelid, navigateFrom, new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String s) {
                                Log.d(TAG, "onTaskCompleted() inside onQueryTextSubmit()");
                            }
                        }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                    } else if (!TextUtils.isEmpty(searchTextView.getText().toString().trim()) && i == 1) {
                        new SearchContactsAsync(SearchContactsActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), searchTextView.getText().toString().trim(), "" + i);
                    }
                } else {
                    hideProgress();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Refactoring the deprecated startActivityForResults*/
    @Override
    public void onScreenNavigate(Intent intent) {
        launcherInviteResults.launch(intent);
    }
}
