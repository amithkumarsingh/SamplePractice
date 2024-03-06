package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.hbb20.CountryCodePicker;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

import static com.vam.whitecoats.utils.RestUtils.TAG_STATUS;
import static com.vam.whitecoats.utils.RestUtils.TAG_SUCCESS;

public class ForgotPassword extends BaseActionBarActivity {

    TextView contactSupport_textView;
    private Realm realm;
    private RealmManager realmManager;
    private int doctorId=-1;
    private RealmBasicInfo realmBasicInfo;
    private RadioGroup radio_group;
    private RadioButton radio_number;
    private LinearLayout email_otp_layout,phnum_otp_layout;
    private EditText email_id_editText,phnum_editText;
    private TextView email_id_hint,phnum_hint, email_id_error,phone_num_error;
    private TextView country_code_text;
    private CountryCodePicker country_CodePicker;
    private String emailid="";
    private Button send_otpAction;
    private String phNumber="", countryCode="";
    private String email_id_value,phnum_value;
    private boolean isEmailSelected=false;
    String android_id;
    private boolean customBackButton=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialize();
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

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

    private void initialize() {
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_reg, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        mTitleTextView.setText("Reset Password");
        next_button.setVisibility(View.GONE);
        radio_group=(RadioGroup)findViewById(R.id.radio_group);
        radio_number=(RadioButton) findViewById(R.id.radio_number);
        email_otp_layout=(LinearLayout)findViewById(R.id.email_otp_layout);
        phnum_otp_layout=(LinearLayout)findViewById(R.id.phone_otp_layout);
        email_id_editText=(EditText)findViewById(R.id.entered_emailId);
        phnum_editText=(EditText)findViewById(R.id.entered_phoneNumber);
        email_id_hint=(TextView)findViewById(R.id.email_hint);
        phnum_hint=(TextView)findViewById(R.id.phnum_hint);
        country_code_text=(TextView)findViewById(R.id.country_code_text);
        country_CodePicker = (CountryCodePicker) findViewById(R.id.ccp);
        send_otpAction=(Button)findViewById(R.id.send_otpAction);
        email_id_error=(TextView)findViewById(R.id.email_id_error_msg);
        phone_num_error=(TextView)findViewById(R.id.phonenum_error_msg);
        mySharedPref = new MySharedPref(this);



        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);

        doctorId = realmManager.getDoc_id(realm);
        realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        radio_group.check(R.id.radio_number);

        if(realmBasicInfo.getPhone_num()!=null && realmBasicInfo.getPhone_num().isEmpty()){
            radio_number.setVisibility(View.GONE);
            phnum_otp_layout.setVisibility(View.GONE);
        }else{
            radio_number.setVisibility(View.VISIBLE);
        }

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_emial:

                        phnum_otp_layout.setVisibility(View.GONE);
                        email_otp_layout.setVisibility(View.VISIBLE);
                        isEmailSelected=true;
                        break;
                    case R.id.radio_number:

                        email_otp_layout.setVisibility(View.GONE);
                        phnum_otp_layout.setVisibility(View.VISIBLE);
                        isEmailSelected=false;
                        break;
                    default:
                        break;
                }
            }
        });

        if(doctorId!=0 && realmBasicInfo!=null){
            email_id_hint.setText(getString(R.string.send_otp));
            email_id_hint.setGravity(Gravity.CENTER_HORIZONTAL);
            phnum_hint.setText(getString(R.string.send_otp));
            phnum_hint.setGravity(Gravity.CENTER_HORIZONTAL);
            emailid=realmBasicInfo.getEmail();
            phNumber=realmBasicInfo.getPhone_num();
            email_id_editText.setText(AppUtil.maskEmailId(emailid));
            email_id_editText.setGravity(Gravity.CENTER_HORIZONTAL);
            phnum_editText.setText(realmBasicInfo.getPhone_num().replaceAll("\\d(?=\\d{4})","*"));
            phnum_editText.setGravity(Gravity.CENTER_HORIZONTAL);
            email_id_editText.setEnabled(false);
            phnum_editText.setEnabled(false);
            country_CodePicker.setVisibility(View.GONE);
        }else{
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(10);
            phnum_editText.setFilters(fArray);
            //emailid=email_id_editText.getText().toString();
            //phNumber=phnum_editText.getText().toString();
        }

        realmBasicInfo.getEmail();
        contactSupport_textView=_findViewById(R.id.contact_support_action);

        contactSupport_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "Login_ResetPass_ContactSupport", jsonObject,ForgotPassword.this);
                Intent contactsupportIntent= new Intent(ForgotPassword.this,ContactSupport.class);
                startActivityForResult(contactsupportIntent,1);
//                finish();
            }
        });
        radio_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "Login_ResetPass_GetSMS", jsonObject,ForgotPassword.this);
            }
        });

        send_otpAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "Login_ResetPass_Send", jsonObject,ForgotPassword.this);
                JSONObject requestObj=new JSONObject();
                if(isEmailSelected){
                    try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.logUserWithEventName(0, "Login_ResetPass_GetEmail", jsonObject,ForgotPassword.this);
                    if(doctorId==0){
                        emailid=email_id_editText.getText().toString();
                    }
                    validationUtils = new ValidationUtils(ForgotPassword.this,
                            new EditText[]{email_id_editText},
                            new TextView[]{email_id_error});
                    if(validationUtils.isEmailForgotPassword(emailid)) {
                        try {
                            requestObj.put("contact",emailid);
                            requestObj.put("contactType","EMAIL");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(AppUtil.isConnectingToInternet(ForgotPassword.this)) {
                            requestForForgotPassword(requestObj);
                        }else{
                            Toast.makeText(ForgotPassword.this,getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.logUserWithEventName(0, "Login_ResetPass_GetSMS", jsonObject,ForgotPassword.this);
                    if(doctorId==0){
                        country_CodePicker.setVisibility(View.VISIBLE);
                        countryCode = country_CodePicker.getSelectedCountryCode();
                        phNumber=countryCode.concat(phnum_editText.getText().toString());
                    }
                    validationUtils = new ValidationUtils(ForgotPassword.this,
                            new EditText[]{phnum_editText},
                            new TextView[]{phone_num_error});
                    if(validationUtils.isPhoneForgotPassword(phnum_editText.getText().toString())) {
                        try {
                            requestObj.put("contact",phNumber);
                            requestObj.put("contactType","PHONE");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(AppUtil.isConnectingToInternet(ForgotPassword.this)) {
                            requestForForgotPassword(requestObj);
                        }else{
                            Toast.makeText(ForgotPassword.this,getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
                        }
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            finish();
        }
    }

    private void requestForForgotPassword(final JSONObject requestObj) {
        showProgress();
        new VolleySinglePartStringRequest(ForgotPassword.this, Request.Method.POST, RestApiConstants.FORGOT_PASSWORD_REST, requestObj.toString(), "FORGOTPASSWORD", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                hideProgress();
                try {
                    String contact="";
                    String contactType="";
                    JSONObject jsonObject = new JSONObject(successResponse);
                    if (jsonObject.optString(TAG_STATUS).equals(TAG_SUCCESS)) {
                        String contactEmail=jsonObject.optString(RestUtils.TAG_CNT_EMAIL);
                        if(jsonObject.has("contact")){
                            contactEmail=jsonObject.optString("contact");
                        }
                        mySharedPref.getPrefsHelper().savePref(MySharedPref.EMAIL_ID_OTP,contactEmail);
                        Intent otpIntent = new Intent(ForgotPassword.this, OTPConformationScreen.class);
                        if(requestObj.optString("contactType").equalsIgnoreCase("email")){
                            contact=AppUtil.maskEmailId(emailid);
                            contactType="Email";
                        }
                        else{
                            contact=phNumber.replaceAll("\\d(?=\\d{4})","*");
                            contactType="Phone";
                        }
                        otpIntent.putExtra("contact",contact);
                        otpIntent.putExtra("contactType",contactType);
                        startActivity(otpIntent);
                        finish();
                    }
                    else if(jsonObject.optString(TAG_STATUS).equals(RestUtils.TAG_ERROR)){
                        if(jsonObject.optInt(RestUtils.TAG_ERROR_CODE)==1015){
                            String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                            if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                            }
                            Toast.makeText(ForgotPassword.this, errorMsg, Toast.LENGTH_SHORT).show();
                            mySharedPref.getPrefsHelper().savePref(MySharedPref.EMAIL_ID_OTP,emailid);
                            Intent otpIntent = new Intent(ForgotPassword.this, OTPConformationScreen.class);
                            if(requestObj.optString("contactType").equalsIgnoreCase("email")){
                                contact=AppUtil.maskEmailId(emailid);
                                contactType="Email";
                            }
                            else{
                                contact=phNumber.replaceAll("\\d(?=\\d{4})","*");
                                contactType="Phone";
                            }
                            otpIntent.putExtra("contact",contact);
                            otpIntent.putExtra("contactType",contactType);
                            startActivity(otpIntent);
                            finish();
                        }else {
                            displayErrorScreen(successResponse);
                        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            customBackButton=true;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("ForgotPasswordBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("ForgotPasswordDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("X-DEVICE-ID", android_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtil.logUserWithEventName(0, "Login_ResetPass_BackButton", jsonObject,ForgotPassword.this);

        finish();
    }
}
