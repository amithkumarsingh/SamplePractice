package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.DownloadAsync;
import com.vam.whitecoats.async.DownloadImageAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.CustomModel;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.fragments.About_Fragment;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FileHelper;
import com.vam.whitecoats.utils.InviteConnectStatusUpdateEvent;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import io.realm.Realm;

/**
 * Created by swathim on 6/9/2015.
 */
public class InviteRequestActivity extends BaseActionBarActivity {


    private TextView tv_invitee_name, tv_invitee_specialty;
    private ImageView ig_invitee_path;

    private EditText edt_custom_text;
    private TextView tv_count;
    private Realm realm;
    private RealmManager realmManager;
    private int doc_id;
    private static final String TAG_STATUS = "status";
    private static final String TAG_SUCCESS = "success";
    private ContactsInfo contactsInfo;
    private AlertDialog.Builder builder;
    private String TAG = "InviteRequestActivity";
    private RelativeLayout name_image_lay;
    private boolean customBackButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_custom_message);


        _initialize();


        tv_count.setText(edt_custom_text.getText().toString().length() + "/256");


        edt_custom_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    tv_count.setText(s.length() + "/256");
                    if (s.length() == 257) {
                        edt_custom_text.setText(s.toString().substring(0, 256));
                        edt_custom_text.setSelection(s.length() - 1);
                    } else if (s.length() <= 255) {
                        tv_count.setTextColor(Color.BLACK);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("InviteDoctorDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b.getString("search") != null && b.getString("search").equals("search")) {
            finish();
        } else if (b.getString("visit") != null && b.getString("visit").equals("visit")) {
            Intent intent = new Intent();
            setResult(2, intent);
            finish();
        } else if (b.getString("groupinfo") != null && b.getString("groupinfo").equals("groupinfo")) {
            super.onBackPressed();
        } else if (b.getString(RestUtils.NAVIGATATE_FROM) != null && b.getString(RestUtils.NAVIGATATE_FROM).equals(DepartmentMembersActivity.TAG)) {
            finish();
        }/*Android : Fix code review bugs analyzed by SonarQube -- Removed dupilcate if condition*/ else if (b.getString(RestUtils.NAVIGATATE_FROM) != null && b.getString(RestUtils.NAVIGATATE_FROM).equals(About_Fragment.TAG)) {
            finish();
        } else {
            super.onBackPressed();
        }
    }


    private void _initialize() {
        try {
            realmManager = new RealmManager(this);
            realm = Realm.getDefaultInstance();
            edt_custom_text = _findViewById(R.id.custom_text);
            tv_count = _findViewById(R.id.textcount);

            tv_invitee_name = _findViewById(R.id.invitee_name);
            tv_invitee_specialty = _findViewById(R.id.invitee_specialty);
            ig_invitee_path = _findViewById(R.id.invitee_img);
            name_image_lay = _findViewById(R.id.name_image_lay);

            mInflater = LayoutInflater.from(this);
            mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
            next_button.setText(getString(R.string.action_done));
            //back_button.setImageResource(R.drawable.ic_back);
            if (getIntent().getExtras() != null) {
                contactsInfo = (ContactsInfo) getIntent().getSerializableExtra("searchContactsInfo");
                tv_invitee_name.setText(contactsInfo.getUserSalutation() + " " + contactsInfo.getName());
                tv_invitee_specialty.setText(contactsInfo.getSpeciality() + ", " + contactsInfo.getLocation());
                if (contactsInfo.getPic_url() != null && !contactsInfo.getPic_url().isEmpty()) {
                    AppUtil.loadCircularImageUsingLib(InviteRequestActivity.this, contactsInfo.getPic_url().trim(), ig_invitee_path, R.drawable.default_profilepic);
                } else if (contactsInfo.getPic_name() != null && !contactsInfo.getPic_name().equals("") && !contactsInfo.getPic_name().equals("null")) {
                    try {
                        if (isConnectingToInternet()) {
                            String image_response = new DownloadAsync(InviteRequestActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), contactsInfo.getPic_name(), "profile").get();
                            /** Image Downloading**/
                            JSONObject jObject = new JSONObject(image_response);
                            if (jObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) && jObject.has(RestUtils.TAG_DATA)) {
                                JSONObject dataJson = jObject.getJSONObject(RestUtils.TAG_DATA);
                                String small_link = dataJson.getString("small_link");
                                final String fileName = contactsInfo.getPic_name();
                                String imagepath = new DownloadImageAsync(InviteRequestActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), small_link, fileName).get();
                                if (FileHelper.isFilePresent(contactsInfo.getPic_name(), "profile", InviteRequestActivity.this)) {
                                    contactsInfo.setPic_name(contactsInfo.getPic_name());
                                }
                                ig_invitee_path.setImageURI(Uri.fromFile(new File(imagepath)));
                            }
                        }
                        /*Android : Fix code review bugs analyzed by SonarQube -- Added catch block for interrupted Exception*/
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        ie.printStackTrace();
                    } catch (Exception ie) {
                        ie.printStackTrace();
                    }
                }
            } else {
                ig_invitee_path.setImageResource(R.drawable.default_profilepic);
            }
            mTitleTextView.setText("Invite " + contactsInfo.getName());
            doc_id = contactsInfo.getDoc_id();
            upshotEventData(0, 0, 0, "", "", "", "", "", false);

            edt_custom_text.setText("Dear " + contactsInfo.getName() + "," + "\n\nI would like to invite you to join my network on WhiteCoats.\n\n\nRegards," + "\n" + realmManager.getRealmBasicInfo(realm).getUser_salutation() + " " + realmManager.getRealmBasicInfo(realm).getFname());

            name_image_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnectingToInternet()) {
                        Intent intent = new Intent(InviteRequestActivity.this, VisitOtherProfile.class);
                        intent.putExtra(RestUtils.TAG_DOC_ID, doc_id);
                        startActivity(intent);

                    }
                }
            });

            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnectingToInternet()) {
                        if (AppUtil.getUserVerifiedStatus() == 3) {
                            if (!TextUtils.isEmpty(edt_custom_text.getText().toString().trim())) {
                                //  new InviteConnectAsync(InviteRequestActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), "" + doc_id, edt_custom_text.getText().toString().trim());
                                JSONObject object = new JSONObject();
                                try {
                                    object.put("from_doc_id", realmManager.getDoc_id(realm));
                                    object.put("to_doc_id", doc_id);
                                    object.put("invite_text", edt_custom_text.getText().toString().trim());

                                    new VolleySinglePartStringRequest(InviteRequestActivity.this, Request.Method.POST, RestApiConstants.INVITE_FOR_CONNECT, object.toString(), "INVITE_CONNECT_REQUEST", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            onTaskCompleted(successResponse);
                                        }

                                        @Override
                                        public void onErrorResponse(String errorResponse) {
                                            hideProgress();
                                            JSONObject acceptjObject = null;
                                            try {
                                                acceptjObject = new JSONObject(errorResponse);
                                                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                                                String errorMsg = mContext.getResources().getString(R.string.somenthing_went_wrong);
                                                if (acceptjObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                                    errorMsg = acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                }
                                                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }).sendSinglePartRequest();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(InviteRequestActivity.this, getResources().getString(R.string.invite_message), Toast.LENGTH_SHORT).show();
                            }
                        } else if (AppUtil.getUserVerifiedStatus() == 1) {
                            AppUtil.AccessErrorPrompt(InviteRequestActivity.this, getString(R.string.mca_not_uploaded));
                        } else if (AppUtil.getUserVerifiedStatus() == 2) {
                            AppUtil.AccessErrorPrompt(InviteRequestActivity.this, getString(R.string.mca_uploaded_but_not_verified));
                        }
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
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invite_custom_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            customBackButton = true;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("InviteDoctorBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskCompleted(String searchresponse) {
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
                        if (jsonObject.getString(TAG_STATUS).equals(TAG_SUCCESS)) {
                            realmManager.insertMyContacts(realm, contactsInfo, 2);
                            Toast.makeText(InviteRequestActivity.this, "Your invitation has been sent successfully", Toast.LENGTH_LONG).show();
                            hideProgress();
                            Intent in = getIntent();
                            Bundle b = in.getExtras();
                            EventBus.getDefault().post(new InviteConnectStatusUpdateEvent("INVITE_CONNECT_ACTION"));
                            if (b.getString(RestUtils.NAVIGATATE_FROM) != null && b.getString(RestUtils.NAVIGATATE_FROM).equals(CommentsActivity.TAG)) {
                                CustomModel.getInstance().changeState(true, new JSONObject());
                            }
                            Intent intent = new Intent();
                            setResult(1, intent);
                            finish();//finishing activity
                        } else if (jsonObject.getString(TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            hideProgress();
                            if (jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                                AppUtil.AccessErrorPrompt(InviteRequestActivity.this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                            } else if (jsonObject.optInt(RestUtils.TAG_ERROR_CODE) == 502 || jsonObject.optInt(RestUtils.TAG_ERROR_CODE) == 503) {
                                displayErrorScreen(searchresponse);
                            } else {
                                builder = new AlertDialog.Builder(InviteRequestActivity.this);
                                builder.setMessage(jsonObject.getString(RestUtils.TAG_ERROR_MESSAGE));
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("107")) {
                                            EventBus.getDefault().post(new InviteConnectStatusUpdateEvent("INVITE_CONNECT_ACTION"));
                                            finish();
                                        } else if (getIntent().getExtras() != null) {
                                            if (getIntent().getExtras().getString("groupinfo") != null && getIntent().getExtras().getString("groupinfo").equals("groupinfo")) {
                                                onBackPressed();
                                            } else if (getIntent().getExtras().getString("visit") != null && getIntent().getExtras().getString("visit").equals("visit")) {
                                                onBackPressed();
                                            }
                                        }
                                    }
                                });
                                builder.create().show();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
