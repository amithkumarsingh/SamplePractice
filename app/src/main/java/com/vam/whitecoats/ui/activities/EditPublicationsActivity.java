package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

public class EditPublicationsActivity extends BaseActionBarActivity {
    public static final String TAG = EditPublicationsActivity.class.getSimpleName();
    private EditText edt_title, edt_authors, edt_web_link;
    private AutoCompleteTextView auto_journals;
    private TextView tv_remove;
    RealmManager realmManager;
    Realm realm;
    PublicationsInfo publicationsInfo;
    String oper = "update";
    private ArrayList<String> edit_journal_list = new ArrayList<String>();
    Context context;
    private ArrayAdapter<String> journal_adapter;
    private TextView next_button;
    int doctorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_publications);
        context = this;
        _initializeUI();
        /** Journal AutoSuggestions**/
        try {
            ArrayList<String> searchkeys = new ArrayList<String>();
            searchkeys.add(RestUtils.TAG_JOURNALS);
            if (isConnectingToInternet()) {
                new AutoSuggestionsAsync(EditPublicationsActivity.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void _initializeUI() {
        try {
            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(EditPublicationsActivity.this);
            doctorID = realmManager.getDoc_id(realm);
            edt_title = _findViewById(R.id.edit_Pub_title);
            edt_authors = _findViewById(R.id.edit_authors_name);
            edt_web_link = _findViewById(R.id.edit_webpage_link);
            auto_journals = _findViewById(R.id.edit_journal_name);
            tv_remove = _findViewById(R.id.remove_publications);
            mInflater = LayoutInflater.from(EditPublicationsActivity.this);
            mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            next_button = (TextView) mCustomView.findViewById(R.id.next_button);
            mTitleTextView.setText(getString(R.string.str_edt_publications));
            next_button.setText(getString(R.string.actionbar_save));
            //back_button.setImageResource(R.drawable.ic_back);
            publicationsInfo = new PublicationsInfo();
            publicationsInfo = (PublicationsInfo) getIntent().getSerializableExtra("Edit_publicationsInfo");
            if (publicationsInfo != null) {
                if (publicationsInfo.getType().equals(RestUtils.TAG_PRINT)) {
                    auto_journals.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    edt_web_link.setVisibility(View.GONE);
                    edt_title.setText(publicationsInfo.getTitle());
                    edt_authors.setText(publicationsInfo.getAuthors());
                    auto_journals.setText(publicationsInfo.getJournal());
                } else {
                    auto_journals.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    edt_title.setText(publicationsInfo.getTitle());
                    edt_authors.setText(publicationsInfo.getAuthors());
                    auto_journals.setText(publicationsInfo.getJournal());
                    edt_web_link.setText(publicationsInfo.getWeb_page());
                }

            }
            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        if (!TextUtils.isEmpty(edt_title.getText().toString()) || !TextUtils.isEmpty(edt_authors.getText().toString()) || !TextUtils.isEmpty(auto_journals.getText().toString())
                                || !TextUtils.isEmpty(edt_web_link.getText().toString())) {
                            publicationsInfo.setTitle(edt_title.getText().toString());
                            publicationsInfo.setAuthors(edt_authors.getText().toString());
                            publicationsInfo.setJournal(auto_journals.getText().toString());
                            publicationsInfo.setWeb_page(edt_web_link.getText().toString());
                            hideKeyboard();
                            if (isConnectingToInternet()) {
                                JSONObject requestObject = new JSONObject();
                                requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                if (oper.equals(RestUtils.TAG_UPDATE)) {
                                    JSONObject pubObj = new JSONObject();
                                    JSONArray pubArray = new JSONArray();
                                    pubObj.put(RestUtils.TAG_PUB_TYPE, publicationsInfo.getType());
                                    pubObj.put(RestUtils.TAG_TITLE, publicationsInfo.getTitle());
                                    pubObj.put(RestUtils.TAG_JOURNAL, publicationsInfo.getJournal());
                                    if (publicationsInfo.getType().equalsIgnoreCase(RestUtils.TAG_ONLINE)) {
                                        pubObj.put(RestUtils.TAG_AUTHORS, publicationsInfo.getAuthors());
                                        pubObj.put(RestUtils.TAG_WEBPAGE_LINK, publicationsInfo.getWeb_page());
                                        pubObj.put(RestUtils.TAG_ONLINE_PUB_ID, publicationsInfo.getPub_id());
                                        pubArray.put(pubObj);
                                        requestObject.put(RestUtils.TAG_ONLINE_PUB_HISTORY, pubArray);
                                    } else {
                                        pubObj.put(RestUtils.TAG_PRINT_PUB_ID, publicationsInfo.getPub_id());
                                        pubArray.put(pubObj);
                                        requestObject.put(RestUtils.TAG_PRINT_PUB_HISTORY, pubArray);
                                    }
                                }

                                showProgress();
                                new VolleySinglePartStringRequest(EditPublicationsActivity.this, Request.Method.POST, RestApiConstants.UPDATE_USER_PROFILE, requestObject.toString(), "UPDATE_PUBLICATION", new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        hideProgress();
                                        JSONObject responseObj = null;
                                        try {
                                            responseObj = new JSONObject(successResponse);
                                            if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                handlePublicationResponse(responseObj);
                                            } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                                displayErrorScreen(successResponse);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onErrorResponse(String errorResponse) {
                                        hideProgress();
                                        displayErrorScreen(errorResponse);

                                    }
                                }).sendSinglePartRequest();
                            } else {
                                hideProgress();
                            }
                        } else {
                            Toast.makeText(EditPublicationsActivity.this, "please enter details to continue", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
                    }

                }
            });


            tv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        hideKeyboard();
                        if (isConnectingToInternet()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(EditPublicationsActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle(getResources().getString(R.string.profile_delete));
                            builder.setMessage(getResources().getString(R.string.profile_delete_other));
                            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    oper = "delete";
                                    try {
                                        /** Creating JSON **/
                                        JSONObject requestObject = new JSONObject();
                                        if (publicationsInfo.getType().equalsIgnoreCase(RestUtils.TAG_ONLINE)) {
                                            JSONObject pubOnlineObj = new JSONObject();
                                            JSONArray pubOnlineArray = new JSONArray();
                                            pubOnlineObj.put(RestUtils.TAG_ONLINE_PUB_ID, publicationsInfo.getPub_id());
                                            pubOnlineObj.put(RestUtils.TAG_PUB_TYPE, RestUtils.TAG_ONLINE);
                                            pubOnlineArray.put(pubOnlineObj);
                                            requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                            requestObject.put(RestUtils.TAG_ONLINE_PUB_HISTORY, pubOnlineArray);
                                        } else {
                                            JSONObject pubPrintObj = new JSONObject();
                                            JSONArray pubPrintArray = new JSONArray();
                                            pubPrintObj.put(RestUtils.TAG_PRINT_PUB_ID, publicationsInfo.getPub_id());
                                            pubPrintObj.put(RestUtils.TAG_PUB_TYPE, RestUtils.TAG_PRINT);
                                            pubPrintArray.put(pubPrintObj);
                                            requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                            requestObject.put(RestUtils.TAG_PRINT_PUB_HISTORY, pubPrintArray);
                                        }

                                        showProgress();
                                        new VolleySinglePartStringRequest(EditPublicationsActivity.this, Request.Method.POST, RestApiConstants.DELETE_USER_PROFILE, requestObject.toString(), "DELETE_PUBLICATION", new OnReceiveResponse() {
                                            @Override
                                            public void onSuccessResponse(String successResponse) {
                                                hideProgress();
                                                JSONObject responseObj = null;
                                                try {
                                                    responseObj = new JSONObject(successResponse);
                                                    if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                        realmManager.deletePublications(realm, publicationsInfo.getPub_id());
                                                        hideProgress();
                                                        edit_journal_list.clear();
                                                        Toast.makeText(EditPublicationsActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                                                        Intent in = new Intent();
                                                        setResult(Activity.RESULT_OK, in);
                                                        finish();
                                                    } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                                        displayErrorScreen(successResponse);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onErrorResponse(String errorResponse) {
                                                hideProgress();
                                                displayErrorScreen(errorResponse);

                                            }
                                        }).sendSinglePartRequest();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else {
                            hideProgress();
                        }
                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
                    }
                }
            });

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(mCustomView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePublicationResponse(JSONObject jsonObject) {
        try {
            if (jsonObject.has(RestUtils.TAG_DATA)) {
                JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                if (data.has(RestUtils.TAG_PRINT_PUB_HISTORY) || data.has(RestUtils.TAG_ONLINE_PUB_HISTORY)) {
                    JSONArray pubHistoryArray = null;
                    JSONObject pubHistoryObj = null;
                    if (data.has(RestUtils.TAG_PRINT_PUB_HISTORY)) {
                        pubHistoryArray = data.optJSONArray(RestUtils.TAG_PRINT_PUB_HISTORY);
                        pubHistoryObj = pubHistoryArray.optJSONObject(0);
                        publicationsInfo.setPub_id(pubHistoryObj.optInt(RestUtils.TAG_PRINT_PUB_ID));
                    } else {
                        pubHistoryArray = data.optJSONArray(RestUtils.TAG_ONLINE_PUB_HISTORY);
                        pubHistoryObj = pubHistoryArray.optJSONObject(0);
                        publicationsInfo.setPub_id(pubHistoryObj.optInt(RestUtils.TAG_ONLINE_PUB_ID));
                        publicationsInfo.setAuthors(pubHistoryObj.optString(RestUtils.TAG_AUTHORS));
                        publicationsInfo.setWeb_page(pubHistoryObj.optString(RestUtils.TAG_WEBPAGE_LINK));
                    }
                    publicationsInfo.setType(pubHistoryObj.optString(RestUtils.TAG_PUB_TYPE));
                    publicationsInfo.setTitle(pubHistoryObj.optString(RestUtils.TITLE));
                    publicationsInfo.setJournal(pubHistoryObj.optString(RestUtils.TAG_JOURNAL));
                    if (oper.equals("update")) {
                        realmManager.updatePublicationsInfo(realm, publicationsInfo);
                    } else if (oper.equals("delete")) {
                        realmManager.deletePublications(realm, publicationsInfo.getPub_id());
                    }
                    hideProgress();
                    edit_journal_list.clear();
                    Toast.makeText(EditPublicationsActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                    Intent in = new Intent();
                    setResult(Activity.RESULT_OK, in);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (publicationsInfo!=null && (publicationsInfo.getType().equals("print")) &&
                (!publicationsInfo.getTitle().equals(edt_title.getText().toString()) ||
                        !publicationsInfo.getJournal().equals(auto_journals.getText().toString()) ||
                        !publicationsInfo.getAuthors().equals(edt_authors.getText().toString()))) {

            ShowAlert();

        } else if (publicationsInfo!=null && (publicationsInfo.getType().equals("online")) &&
                (!publicationsInfo.getTitle().equals(edt_title.getText().toString()) ||
                        !publicationsInfo.getJournal().equals(auto_journals.getText().toString()) ||
                        !publicationsInfo.getAuthors().equals(edt_authors.getText().toString()) ||
                        !publicationsInfo.getWeb_page().equals(edt_web_link.getText().toString()))) {

            ShowAlert();

        } else {
            /*Intent login_intent = new Intent(EditPublicationsActivity.this, ProfileViewActivity.class);
            login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login_intent);*/
            finish();
        }
    }

    @Override
    public void onTaskCompleted(String response) {

        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
                hideProgress();
                ShowSimpleDialog(getResources().getString(R.string.unabletosave), getResources().getString(R.string.trylater));
                finish();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {
                            JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            if (data.has(RestUtils.TAG_JOURNALS)) {
                                JSONArray journaljArray = data.getJSONArray(RestUtils.TAG_JOURNALS);
                                if (journaljArray.length() > 0) {
                                    for (int i = 0; i < journaljArray.length(); i++) {
                                        edit_journal_list.add(journaljArray.get(i).toString());
                                    }
                                }
                                journal_adapter = new ArrayAdapter<String>(EditPublicationsActivity.this, android.R.layout.simple_list_item_1, edit_journal_list);
                                auto_journals.setThreshold(1);
                                auto_journals.setAdapter(journal_adapter);
                                hideProgress();
                            }
                        } else {
                            if (oper.equals("update")) {
                                realmManager.updatePublicationsInfo(realm, publicationsInfo);
                            } else if (oper.equals("delete")) {
                                realmManager.deletePublications(realm, publicationsInfo.getPub_id());
                            }
                            edit_journal_list.clear();
                            Toast.makeText(EditPublicationsActivity.this, "Profile Updated", Toast.LENGTH_LONG).show();
                            hideProgress();
                            Intent in = new Intent();
                            setResult(Activity.RESULT_OK, in);
                            finish();
                        }
                    } else {
                        hideProgress();
                        ShowSimpleDialog("Error", getResources().getString(R.string.unknown_server_error));
                    }
                    hideProgress();
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

    private void ShowAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("Would you like to save the changes");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                next_button.performClick();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                /*Intent login_intent = new Intent(EditPublicationsActivity.this, ProfileViewActivity.class);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login_intent);*/
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (!realm.isClosed())
            realm.close();
    }
}
