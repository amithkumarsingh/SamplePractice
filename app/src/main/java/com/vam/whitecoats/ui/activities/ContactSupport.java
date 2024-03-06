package com.vam.whitecoats.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ContactSupport extends BaseActionBarActivity {

    private EditText full_name, mobile_number;
    private Spinner preferred_time;
    private Button call_me_action;
    private TextView fullName_error_textView, phone_num_error_textview;
    private String fullName_value, mobile_number_value, spinner_value;
    private TextView support_email, support_phNum;
    private SharedPreferences httpshp;

    private Realm realm;
    private RealmManager realmManager;
    private RealmBasicInfo realmBasicInfo;
    private boolean customBackButton=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        realmBasicInfo = realmManager.getRealmBasicInfo(realm);

        initialize();

        validationUtils = new ValidationUtils(ContactSupport.this,
                new EditText[]{full_name, mobile_number},
                new TextView[]{fullName_error_textView, phone_num_error_textview});
    }

    @Override
    protected void setCurrentActivity() {

    }

    private void initialize() {
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_reg, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        mTitleTextView.setText("Contact Support");
        next_button.setVisibility(View.GONE);
        full_name = (EditText) findViewById(R.id.full_name_edittext);
        mobile_number = (EditText) findViewById(R.id.mobile_num_edittext);
        preferred_time = (Spinner) findViewById(R.id.preferred_time);
        call_me_action = (Button) findViewById(R.id.call_me_action);
        fullName_error_textView = (TextView) findViewById(R.id.full_name_error);
        phone_num_error_textview = (TextView) findViewById(R.id.phone_num_error);
        support_email = (TextView) findViewById(R.id.contact_support_mail);
        support_phNum = (TextView) findViewById(R.id.contact_support_phNum);

        RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        full_name.setText(realmBasicInfo.getUser_salutation()+" " + realmBasicInfo.getFname() + " " + realmBasicInfo.getLname());
        mobile_number.setText(realmBasicInfo.getPhone_num());
        httpshp = getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
        final String spring_securityToken = httpshp.getString("SSTOKEN", "");
        support_phNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.makePhoneCall(ContactSupport.this,support_phNum.getText().toString());
            }
        });
        support_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.sendEmail(ContactSupport.this,support_email.getText().toString(),"Request for support.","Hi WhiteCoats Team,");
            }
        });

        call_me_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    fullName_value = full_name.getText().toString();
                    mobile_number_value = mobile_number.getText().toString();
                    spinner_value = preferred_time.getSelectedItem().toString();
                    if (validationUtils.isContactSupport(fullName_value, mobile_number_value)) {
                        JSONObject requestContactSupportJsonObject = new JSONObject();
                        try {
                            requestContactSupportJsonObject.put(RestUtils.TAG_FULL_NAME, fullName_value);
                            requestContactSupportJsonObject.put(RestUtils.TAG_CNT_NUM, mobile_number_value);
                            requestContactSupportJsonObject.put("preferred_time", spinner_value);
                            final String reqData = requestContactSupportJsonObject.toString();
                            if (AppUtil.isConnectingToInternet(ContactSupport.this)) {
                                showProgress();
                                new VolleySinglePartStringRequest(ContactSupport.this, Request.Method.POST, RestApiConstants.CONTACT_SUPPORT, reqData, "CONTACT_SUPPORT", new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        hideProgress();
                                        JSONObject responseContactSupportJsonObject = null;
                                        try {
                                            responseContactSupportJsonObject = new JSONObject(successResponse);
                                            if (responseContactSupportJsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ContactSupport.this);
                                                builder.setMessage("Thanks for your interest in WhiteCoats, we have received your request and will get back shortly")
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                //Intent contactSupportIntent= new Intent(ContactSupport.this,LoginActivity.class);
                                                                // startActivity(contactSupportIntent);
                                                                setResult(RESULT_OK);
                                                                finish();
                                                            }
                                                        });
                                                AlertDialog alert = builder.create();
                                                alert.show();
                                            } else if (responseContactSupportJsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
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
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("8 AM to 10 AM");
        categories.add("10 AM to 12 AM");
        categories.add("12 PM to 2 PM");
        categories.add("2 PM to 4 PM");
        categories.add("4 PM to 8 PM");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        preferred_time.setAdapter(dataAdapter);



        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton=true;
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("HelpBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
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
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("HelpDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        finish();
    }
}
