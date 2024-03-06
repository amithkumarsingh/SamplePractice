package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.fragments.SpecialityDialogFragment;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;

/**
 * Created by pardhasaradhid on 1/2/2018.
 */

public class NetworkSearchActivity extends BaseActionBarActivity implements SpecialityDialogFragment.NoticeDialogListener {
    public static final String TAG = NetworkSearchActivity.class.getSimpleName();
    private TextView  noNetwresults;
    private Realm realm;
    private RealmManager realmManager;
    String feeds_search;
    private ProgressDialog mProgressDialog;
    private ArrayList<ContactsInfo> searchResultList;
    protected ValidationUtils validationUtils;
    int docID;
    int pageNum = 1;

    LinearLayout layoutSearchFields, layoutSearchList;
    private boolean isResultsPage = false;
    public static int selectedPosition = -1;
    private JSONObject docSearchRequestObj = null;
    private Button feed_search_selection_button, doctor_search_selection_button;
    private boolean feeds_checked;

    public static final String MyPREFERENCES = "MyPrefs";
    //private EditText searchTextView;

    private String searchText;
    private boolean customBackButton = false;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchUserSearchResults,launchFeedSearchResults,
            launchVoiceInputResults,launchGlobalSearchResults;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.networktab_search);
        //setupActionbar();
        initialize();
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        docID = realmManager.getDoc_id(realm);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));
        searchResultList = new ArrayList<>();

        Log.d("String Value", "" + feeds_checked);


        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchGlobalSearchResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
            //requestcode 101
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_CANCELED && data!=null) {
                        if (data.hasExtra("globalsearchString")) {
                            if (data.getExtras().getString("globalsearchString") != null) {
                                searchView.setQuery(data.getExtras().getString("globalsearchString"),false);
                                /*searchTextView.setText(data.getExtras().getString("globalsearchString"));
                                searchTextView.setSelection(searchTextView.getText().toString().length());*/
                            }
                        }
                    }
                });
        launchUserSearchResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
           // request code 102
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK && data!=null) {
                            if (data.hasExtra("usersearchString")) {
                                if (data.getExtras().getString("usersearchString") != null) {
                                    searchView.setQuery(data.getExtras().getString("usersearchString"),false);
                                    //searchTextView.setText(data.getExtras().getString("usersearchString"));
                                }
                        }
                    }
                });
        launchFeedSearchResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
            //request code 103
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_CANCELED && data!=null) {
                        if (data.hasExtra("SearchFeedString")) {
                            if (data.getExtras().getString("SearchFeedString") != null) {
                                searchView.setQuery(data.getExtras().getString("SearchFeedString"),false);
                                //searchTextView.setText(data.getExtras().getString("SearchFeedString"));
                            }
                        }
                    }
                });
        launchVoiceInputResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result1 -> {
            //request code 100
                    int resultCode = result1.getResultCode();
                    Intent data = result1.getData();
                        if (resultCode == RESULT_OK && data!=null) {
                            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            //searchTextView.setText(result.get(0));
                            searchView.setQuery(result.get(0),false);
                            performGlobalSearchClick();
                        }
                });
        //End

    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_contacts, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem action_back = menu.findItem(R.id.action_back);
        MenuItem voiceSearch=menu.findItem(R.id.search_with_voice);

        searchView = (SearchView) searchItem.getActionView();
        searchView.onActionViewExpanded();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    if (query.trim().length() <= 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NetworkSearchActivity.this);
                        builder.setMessage(R.string.empty_search_keyword);
                        builder.setCancelable(true);
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                    } else if (query.trim().length() < 3) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NetworkSearchActivity.this);
                        builder.setMessage(R.string.min_characters_search_keyword);
                        builder.setCancelable(true);
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                    } else {
                        if (isConnectingToInternet()) {
                            performGlobalSearchClick();
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

    private void setSearchViewVisible(boolean visible) {

        if (searchView.isIconified() == visible) {
            searchView.setIconified(!visible);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        }
    }

    public void performGlobalSearchClick() {
        if (AppUtil.isConnectingToInternet(NetworkSearchActivity.this)) {
            try {
                searchText = searchView.getQuery().toString();
                JSONObject jsonObjectEvent = new JSONObject();
                if (!searchText.isEmpty()) {
                    Intent globalSearch = new Intent(NetworkSearchActivity.this, GlobalSearchActivity.class);
                    globalSearch.putExtra("globalsearchText", searchText);
                    launchGlobalSearchResults.launch(globalSearch);
                    jsonObjectEvent.put("search_string", searchText);
                    //AppUtil.logUserWithEventName(docID,"DashboardSearchInitiation",new JSONObject(),NetworkSearchActivity.this);
                    AppUtil.logUserActionEvent(docID, "Doctor_Feed_SearchInitiation", jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent), NetworkSearchActivity.this);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void requestNetworkSearch(final JSONObject requestObj, final boolean isFromOnScroll) {
        Log.i(TAG, "requestNetworkSearch() - " + requestObj);
        showProgress();
        new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.SEARCH_NETWORK, requestObj.toString(), TAG, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i(TAG, "onSuccessResponse()");
                if (successResponse != null) {
                    try {
                        hideProgress();
                        if (!isFromOnScroll) {
                            searchResultList.clear();
                        } else if (searchResultList.size() > 0) {
                            searchResultList.remove(searchResultList.size() - 1);
                        }
                        JSONObject responseObj = new JSONObject(successResponse);
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

                                searchResultList.add(contactsInfo);
                            }
                            // If there are no search results
                            if (searchResultList.size() == 0) {
                                noNetwresults.setVisibility(View.VISIBLE);
                                //next_button.setVisibility(View.GONE);
                                layoutSearchFields.setVisibility(View.GONE);
                                layoutSearchList.setVisibility(View.GONE);
                                //radio_group_search.setVisibility(View.GONE);

                            } else {
                                noNetwresults.setVisibility(View.GONE);
                                displaySearchResults(true);
                            }
                            // set Navigation params to Network tab when back button clicked
                            if (layoutSearchFields.getVisibility() == View.VISIBLE) {
                                isResultsPage = false;
                            } else {
                                isResultsPage = true;

                            }
                            if (isFromOnScroll && len == 0) {
                                Toast.makeText(NetworkSearchActivity.this, getResources().getString(R.string.search_result), Toast.LENGTH_LONG).show();
                            }
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, 1000);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onErrorResponse(String errorResponse) {
                hideProgress();
                Log.i(TAG, "onErrorResponse()");
                // set Navigation params to Network tab when back button clicked
                if (layoutSearchFields.getVisibility() == View.VISIBLE) {
                    isResultsPage = false;
                } else {
                    isResultsPage = true;
                }
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();
    }


    private void initialize() {

        feed_search_selection_button = (Button) findViewById(R.id.feed_search_selection_button);
        doctor_search_selection_button = (Button) findViewById(R.id.doctor_search_selection_button);
        JSONObject jsonSearchObject = new JSONObject();
        feed_search_selection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet()) {
                    if (searchView.getQuery().toString().trim().length() <= 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NetworkSearchActivity.this);
                        builder.setMessage(R.string.empty_search_keyword);
                        builder.setCancelable(true);
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                    } else if (searchView.getQuery().toString().trim().length() < 3) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NetworkSearchActivity.this);
                        builder.setMessage(R.string.min_characters_search_keyword);
                        builder.setCancelable(true);
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                    } else {
                        try {

                            feeds_search = searchView.getQuery().toString();
                            searchText = searchView.getQuery().toString();
                            Intent feedSearch = new Intent(NetworkSearchActivity.this, FeedSearchActivity.class);
                            feedSearch.putExtra("searchFeedText", feeds_search);
                            jsonSearchObject.put("search_string", feeds_search);
                            AppUtil.logUserActionEvent(docID, "Feed_SearchInitiation", jsonSearchObject, AppUtil.convertJsonToHashMap(jsonSearchObject), NetworkSearchActivity.this);
                            launchFeedSearchResults.launch(feedSearch);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        doctor_search_selection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isConnectingToInternet()) {

                        if (searchView.getQuery().toString().trim().length() <= 0) {
                            searchText = searchView.getQuery().toString();
                            Intent userSearch = new Intent(NetworkSearchActivity.this, UserSearchResultsActivity.class);
                            userSearch.putExtra("globalsearchText", searchText);
                            jsonSearchObject.put("search_string", searchText);
                            AppUtil.logUserActionEvent(docID, "Doctor_SearchInitiation", jsonSearchObject, AppUtil.convertJsonToHashMap(jsonSearchObject), NetworkSearchActivity.this);
                            launchUserSearchResults.launch(userSearch);
                        } else if (searchView.getQuery().toString().trim().length() < 3) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NetworkSearchActivity.this);
                            builder.setMessage(R.string.min_characters_search_keyword);
                            builder.setCancelable(true);
                            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create().show();
                        } else {
                            searchText = searchView.getQuery().toString();
                            Intent userSearch = new Intent(NetworkSearchActivity.this, UserSearchResultsActivity.class);
                            userSearch.putExtra("globalsearchText", searchText);
                            jsonSearchObject.put("search_string", searchText);
                            AppUtil.logUserActionEvent(docID, "Doctor_SearchInitiation", jsonSearchObject, AppUtil.convertJsonToHashMap(jsonSearchObject), NetworkSearchActivity.this);
                            launchUserSearchResults.launch(userSearch);
                        }
                    } else {
                        ShowSimpleDialog(null, getResources().getString(R.string.sNetworkError));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            feeds_checked = bundle.getBoolean("Feeds_checked");
        }

        /*
        auto text for city and speciality
         */
        ArrayList<String> searchkeys = new ArrayList<String>();
        searchkeys.add("cities");
        searchkeys.add("specialities");
        if (AppUtil.isConnectingToInternet(mContext)) {
            new AutoSuggestionsAsync(NetworkSearchActivity.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
        }
    }

    private void displaySoftKeyboard(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //search_by_speciality.setText(""); //clear edittext
            }
        };
    }

    private TextWatcher textWatcher() {
        return new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
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

                        JSONArray citiesjArray = data.getJSONArray("cities");
                        JSONArray specialitiesjArray = data.getJSONArray("specialities");

                        int speclen = specialitiesjArray.length();
                        if (speclen > 0) {
                            for (int i = 0; i < speclen; i++) {
                            }
                        }
                        int citieslen = citiesjArray.length();
                        if (citieslen > 0) {
                            for (int i = 0; i < citieslen; i++) {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("DoctorFeedSearchBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogListItemSelect(String selectedItem) {
        Log.i(TAG, "onDialogListItemSelect()");

    }

    private void displaySearchResults(boolean flag) {
        Log.i(TAG, "displaySearchResults()");
        if (flag) {
            layoutSearchFields.setVisibility(View.GONE);
            layoutSearchList.setVisibility(View.VISIBLE);
        } else {
            searchResultList.clear();
            noNetwresults.setVisibility(View.GONE);
            layoutSearchFields.setVisibility(View.VISIBLE);
            layoutSearchList.setVisibility(View.GONE);
        }
        //refresh adapter data
        //networkSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("DoctorFeedSearchDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (isResultsPage) {
            isResultsPage = false;
            displaySearchResults(false);
        } else {
            finish();
        }
    }

    /**
     * This method simply displays the progress dialog if it
     * is currently not showing on UI.
     */
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (requestCode == 1) {
                if (docSearchRequestObj != null) {
                    pageNum = 1;
                    requestNetworkSearch(docSearchRequestObj, false);
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == 1) {
                if (docSearchRequestObj != null) {
                    pageNum = 1;
                    requestNetworkSearch(docSearchRequestObj, false);
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
        setCurrentActivity();
        checkNetworkConnectivity();
    }
}
