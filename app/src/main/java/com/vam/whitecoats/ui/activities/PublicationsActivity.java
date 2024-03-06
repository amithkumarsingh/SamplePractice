package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.fragment.app.FragmentTabHost;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.async.SingletonAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.fragments.OnlinePublicationsFragment;
import com.vam.whitecoats.ui.fragments.PrintPublicationsFragment;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by lokesh on 5/13/2015.
 */
public class PublicationsActivity extends BaseActionBarActivity {
    public static final String TAG = PublicationsActivity.class.getSimpleName();
    private FragmentTabHost mTabHost;
    private TabWidget tabWidget;
    private Realm realm;
    private RealmManager realmManager;
    public static final ArrayList<String> journal_list = new ArrayList<String>();
    int doctorID;
    PublicationsInfo publicationsInfo = null;

    String oper = "add";
    public static long mLastClickTime = 0;
    private static String title, author, journal, webpage;
    private static String Identifier;
    private TextView next_button;
    private AlertDialog.Builder alert_builder;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publications);

        _initialize();
        /** Journal AutoSuggestions**/
        try {
            ArrayList<String> searchkeys = new ArrayList<String>();
            searchkeys.add(RestUtils.TAG_JOURNALS);
            if (isConnectingToInternet()) {
                new AutoSuggestionsAsync(PublicationsActivity.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void _initialize() {
        try {
            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(PublicationsActivity.this);
            doctorID = realmManager.getDoc_id(realm);
            mTabHost = _findViewById(android.R.id.tabhost);
            tabWidget = _findViewById(android.R.id.tabs);

            mTabHost.setup(PublicationsActivity.this, getSupportFragmentManager(), R.id.publicationstabconent);
            mTabHost.addTab(setIndicator(PublicationsActivity.this, mTabHost.newTabSpec("Print"), "PRINT"), PrintPublicationsFragment.class, null);
            mTabHost.addTab(setIndicator(PublicationsActivity.this, mTabHost.newTabSpec("Online"), "ONLINE"), OnlinePublicationsFragment.class, null);

            oper = "add";
            publicationsInfo = new PublicationsInfo();


            mInflater = LayoutInflater.from(PublicationsActivity.this);
            mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            next_button = (TextView) mCustomView.findViewById(R.id.next_button);
            next_button.setText(getString(R.string.actionbar_save));
            //back_button.setImageResource(R.drawable.ic_back);
            mTitleTextView.setText(getString(R.string.publications));


            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideKeyboard();
//                    if (!TextUtils.isEmpty(AppConstants.str_title) || !TextUtils.isEmpty(AppConstants.str_authors) || !TextUtils.isEmpty(AppConstants.str_journals)) {
                    publicationsInfo.setTitle(AppConstants.str_title);
                    publicationsInfo.setType(mTabHost.getCurrentTabTag().toString().toLowerCase());
                    publicationsInfo.setAuthors(AppConstants.str_authors);
                    publicationsInfo.setJournal(AppConstants.str_journals);
                    if (AppConstants.str_webpage != null) {
                        publicationsInfo.setWeb_page(AppConstants.str_webpage);
                    }
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    } else {
                        mLastClickTime = SystemClock.elapsedRealtime();
                        serverCallForAdd(PublicationsActivity.this, oper, publicationsInfo, "activity");
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

            //click on seleccted tab
            int numberOfTabs = mTabHost.getTabWidget().getChildCount();
            for (int t = 0; t < numberOfTabs; t++) {
                mTabHost.getTabWidget().getChildAt(t).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTabHost.getCurrentTabTag().equals("Online")) {
                            if (!TextUtils.isEmpty(AppConstants.str_title) || !TextUtils.isEmpty(AppConstants.str_authors) || !TextUtils.isEmpty(AppConstants.str_journals) || !TextUtils.isEmpty(AppConstants.str_webpage)) {
                                ShowDialog(1);
                            } else {
                                mTabHost.setCurrentTab(0);
                            }
                        } else {
                            if (!TextUtils.isEmpty(AppConstants.str_title) || !TextUtils.isEmpty(AppConstants.str_authors) || !TextUtils.isEmpty(AppConstants.str_journals)) {
                                ShowDialog(0);
                            } else {
                                mTabHost.setCurrentTab(1);
                            }
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public TabHost.TabSpec setIndicator(Context ctx, TabHost.TabSpec spec, String title) {
        // TODO Auto-generated method stub
        View v = LayoutInflater.from(ctx).inflate(R.layout.tab_text_item, null);
        TextView text = (TextView) v.findViewById(R.id.tab_title);
        text.setText(title);
        return spec.setIndicator(v);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (TextUtils.isEmpty(AppConstants.str_webpage) &&
                (!TextUtils.isEmpty(AppConstants.str_title) ||
                        !TextUtils.isEmpty(AppConstants.str_authors) ||
                        !TextUtils.isEmpty(AppConstants.str_journals))) {

            ShowAlert();

        } else if (!TextUtils.isEmpty(AppConstants.str_webpage) &&
                (!TextUtils.isEmpty(AppConstants.str_title) ||
                        !TextUtils.isEmpty(AppConstants.str_authors) ||
                        !TextUtils.isEmpty(AppConstants.str_journals) ||
                        !TextUtils.isEmpty(AppConstants.str_webpage))) {

            ShowAlert();

        } else {
            journal_list.clear();
            Intent in = new Intent();
            setResult(Activity.RESULT_OK,in);
            finish();
        }
    }


    @Override
    public void onTaskCompleted(String response) {
        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
                hideProgress();
                ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
            } else {
                try {
                    hideProgress();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {
                            JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            if (data.has(RestUtils.TAG_JOURNALS)) {
                                JSONArray journaljArray = data.getJSONArray(RestUtils.TAG_JOURNALS);
                                if (journaljArray.length() > 0) {
                                    for (int i = 0; i < journaljArray.length(); i++) {
                                        journal_list.add(journaljArray.get(i).toString());
                                    }
                                }
                            } else if (data.has(RestUtils.TAG_PRINT_PUB_HISTORY) || data.has(RestUtils.TAG_ONLINE_PUB_HISTORY)) {
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
                                realmManager.insertPublicationsInfo(realm, publicationsInfo);
                                if (Identifier.equalsIgnoreCase("fragment")) {
                                    Toast.makeText(PublicationsActivity.this, "Publication Saved", Toast.LENGTH_SHORT).show();
                                }
                                if (Identifier.equalsIgnoreCase("activity")) {
                                    journal_list.clear();
                                    Toast.makeText(PublicationsActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                                    Intent in = new Intent();
                                    setResult(Activity.RESULT_OK, in);
                                    finish();
                                }
                                publicationsInfo = new PublicationsInfo();
                                title = "";
                                author = "";
                                journal = "";
                                webpage = "";
                                AppConstants.str_title = "";
                                AppConstants.str_authors = "";
                                AppConstants.str_journals = "";
                                AppConstants.str_webpage = "";
                                if (Identifier.equalsIgnoreCase("fragment")) {
                                    PrintPublicationsFragment.printpub_title.setText("");
                                    PrintPublicationsFragment.printjournal_name.setText("");
                                    PrintPublicationsFragment.printauthors_name.setText("");
                                    PrintPublicationsFragment.printpub_title.requestFocus();
                                    OnlinePublicationsFragment.edt_title.setText("");
                                    OnlinePublicationsFragment.edt_authors.setText("");
                                    OnlinePublicationsFragment.auto_journals.setText("");
                                    OnlinePublicationsFragment.edt_webpage_link.setText("");
                                    OnlinePublicationsFragment.edt_title.requestFocus();
                                }
                            }
                        }
                    } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            hideProgress();
                            if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), PublicationsActivity.this);
                            }else{
                                displayErrorScreen(response);
                            }
                        }
                } catch (Exception e) {
                    if (response.contains("FileNotFoundException")) {
                        //ShowSimpleDialog("Error", getResources().getString(R.string.unknown_server_error));
                        AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), PublicationsActivity.this);

                    }
                    hideProgress();
                    e.printStackTrace();
                }
            }

        }

    }

    public void ShowDialog(final int currentTab) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Would you like to save the changes");
        final AlertDialog.Builder builder1 = builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                if (isConnectingToInternet()) {
                    if (TextUtils.isEmpty(publicationsInfo.getTitle())) {
                        publicationsInfo.setType(mTabHost.getCurrentTabTag().toString().toLowerCase());
                        publicationsInfo.setTitle(AppConstants.str_title);
                        publicationsInfo.setAuthors(AppConstants.str_authors);
                        publicationsInfo.setJournal(AppConstants.str_journals);
                        if (!TextUtils.isEmpty(AppConstants.str_webpage)) {
                            publicationsInfo.setWeb_page(AppConstants.str_webpage);
                        }
                        serverCallForAdd(PublicationsActivity.this, oper, publicationsInfo, "Tab_Change");
                    }
                    hideProgress();
                    if (currentTab == 0)
                        mTabHost.setCurrentTab(1);
                    else
                        mTabHost.setCurrentTab(0);
                } else {
                    hideProgress();
                }
                dialog.cancel();

            }
        });
        builder.setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (currentTab == 1) {
                    OnlinePublicationsFragment.getInstance().clearUIFields();
                    mTabHost.setCurrentTab(0);
                } else {
                    PrintPublicationsFragment.getInstance().clearUIFields();
                    mTabHost.setCurrentTab(1);
                }
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void serverCallForAdd(Context context, String oper, PublicationsInfo publicationsInfo, String identifier) {
        Realm mRealm = Realm.getDefaultInstance();
        RealmManager mRealmManager = new RealmManager(PublicationsActivity.this);
        try {

            doctorID = mRealmManager.getDoc_id(mRealm);
            Identifier = identifier;
            title = publicationsInfo.getTitle();
            author = publicationsInfo.getAuthors();
            journal = publicationsInfo.getJournal();
            webpage = "";
            if (!TextUtils.isEmpty(publicationsInfo.getWeb_page())) {
                webpage = publicationsInfo.getWeb_page();
            }
            if (AppUtil.isConnectingToInternet(context)) {
                if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(author) || !TextUtils.isEmpty(journal) || !TextUtils.isEmpty(webpage)) {
                    Log.v("Identifier", identifier);
                    /** Creating JSON **/
                    JSONObject requestObject = new JSONObject();
                    requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                    if (oper.equals(RestUtils.TAG_ADD)) {
                        JSONObject pubObj = new JSONObject();
                        JSONArray pubArray = new JSONArray();
                        pubObj.put(RestUtils.TAG_PUB_TYPE, publicationsInfo.getType());
                        pubObj.put(RestUtils.TAG_TITLE, publicationsInfo.getTitle());
                        pubObj.put(RestUtils.TAG_JOURNAL, publicationsInfo.getJournal());
                        pubObj.put(RestUtils.TAG_AUTHORS, publicationsInfo.getAuthors());
                        if (publicationsInfo.getType().equalsIgnoreCase(RestUtils.TAG_ONLINE)) {
                            pubObj.put(RestUtils.TAG_WEBPAGE_LINK, publicationsInfo.getWeb_page());
                            pubArray.put(pubObj);
                            requestObject.put(RestUtils.TAG_ONLINE_PUB_HISTORY, pubArray);
                        } else {
                            pubArray.put(pubObj);
                            requestObject.put(RestUtils.TAG_PRINT_PUB_HISTORY, pubArray);
                        }
                    }
                    new SingletonAsync(context, RestApiConstants.CREATE_USER_PROFILE).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), requestObject.toString());

                } else {
                    Toast.makeText(context, getResources().getString(R.string.publication_emptysave), Toast.LENGTH_LONG).show();
                }
            } else {
                hideProgress();
            }


        } catch (Exception e) {
            hideProgress();
            e.printStackTrace();
        } finally {
            if (!mRealm.isClosed())
                mRealm.close();
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
                journal_list.clear();
                /*Intent login_intent = new Intent(PublicationsActivity.this, ProfileViewActivity.class);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login_intent);*/
                Intent in = new Intent();
                setResult(Activity.RESULT_OK,in);
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
