package com.vam.whitecoats.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.hbb20.CountryCodePicker;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.OtpService;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.OTP;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.interfaces.OnOtpActionListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.vam.whitecoats.viewmodel.SpecialityViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

import static com.vam.whitecoats.utils.RestUtils.TAG_DATA;
import static com.vam.whitecoats.utils.RestUtils.TAG_STATUS;
import static com.vam.whitecoats.utils.RestUtils.TAG_SUCCESS;

public class RegistrationDetailsActivity extends BaseActionBarActivity implements OnOtpActionListener, AdapterView.OnItemSelectedListener {

    EditText firstName_editText, lastName_editText, email_id_editText, phoneNum_editText, createpassword_editText;
    EditText editText1, editText2, editText3, editText4, mobile_num, et_otp;
    TextView firstName_error, lastName_error, emailId_error, phoneNum_error, createpassword_error, termsandpolicy_error;
    Button submit_button, number_edit, resend_otp;
    private String firstName_value, lastName_value, emailId_value = "", phoneNumber_value, createPassword_value, phoneNumber_countryCode;
    private CheckBox checkBox;
    private Realm realm;
    private RealmManager realmManager;
    private String fname, lname;
    private SharedPreferences tokenshp;
    private SharedPreferences.Editor tokenshe;
    private Button password_transformation_btn;
    private TextView tv_terms, tv_policies, otp_error;
    private CountryCodePicker country_CodePicker;
    private String countryCode, response_OTP;
    private Dialog dialog;
    private JSONObject requestObj;
    private EditText[] editText_Values;
    MarshMallowPermission marshMallowPermission;
    private int login_doc_id = 0;
    private String emailId;
    private Spinner salutationSpinner;
    List<String> salutationsList = new ArrayList<String>();
    private String selectedSalutation = "Dr. ";
    String android_id;
    private boolean customBackButton = false;
    private RealmBasicInfo basicInfo;
    private AutoCompleteTextView specialityAutoCompleteTextView;
    private SpecialityViewModel specialityViewModel;
    private ArrayList specialityArrayList;
    private ArrayAdapter specialityArrayAdapter;
    private String itemSelected = "";
    private TextView speciality_error_text;
    private String specialityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_screen_one);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        login_doc_id = realmManager.getDoc_id(realm);
        emailId = realmManager.getDoc_EmailId(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        tokenshp = getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
        mySharedPref = new MySharedPref(this);
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        specialityViewModel = new ViewModelProvider(this).get(SpecialityViewModel.class);
        specialityViewModel.init();


        specialityArrayList = new ArrayList<>();

        initializeUI();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("X-DEVICE-ID", android_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtil.logUserWithEventName(0, "SignUpDocinfo1Impressions", jsonObject, RegistrationDetailsActivity.this);

//        validationUtils = new ValidationUtils(RegistrationDetailsActivity.this,
//                new EditText[]{firstName_editText, lastName_editText, email_id_editText, phoneNum_editText, createpassword_editText, null},
//                new TextView[]{firstName_error, lastName_error, emailId_error, phoneNum_error, createpassword_error, termsandpolicy_error});
        validationUtils = new ValidationUtils(RegistrationDetailsActivity.this,
                new EditText[]{firstName_editText, lastName_editText, email_id_editText, phoneNum_editText, createpassword_editText, null},
                new TextView[]{firstName_error, lastName_error, emailId_error, phoneNum_error, speciality_error_text, termsandpolicy_error});
        AppUtil.logFlurryEventWithDocIdAndEmailEvent("RegistrationDetails", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);

        firstName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "SignUpFirstName", jsonObject, RegistrationDetailsActivity.this);
            }
        });
        lastName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "SignUpLastName", jsonObject, RegistrationDetailsActivity.this);
            }
        });

        phoneNum_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "SignUpMobileNum", jsonObject, RegistrationDetailsActivity.this);
            }
        });
        email_id_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "SignUpEmailID", jsonObject, RegistrationDetailsActivity.this);
            }
        });
        createpassword_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "SignUpPassword", jsonObject, RegistrationDetailsActivity.this);
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (checkBox.isChecked()) {
                    try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.logUserWithEventName(0, "SignUpAgreeTnC", jsonObject, RegistrationDetailsActivity.this);
                } else if (!checkBox.isChecked()) {
                    try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.logUserWithEventName(0, "SignUpUncheckAgreeTnC", jsonObject, RegistrationDetailsActivity.this);
                }
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

    private void initializeUI() {
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_login, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        ImageView back_button = (ImageView) mCustomView.findViewById(R.id.back_button);
        //TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
//        mTitleTextView.setText(getString(R.string.str_register));
        mTitleTextView.setText("Sign Up");
        /*next_button.setVisibility(View.GONE);
        next_button.setText("1/3");*/
        marshMallowPermission = new MarshMallowPermission(this);
        firstName_editText = (EditText) findViewById(R.id.firstname_id);
        lastName_editText = (EditText) findViewById(R.id.lastname_id);
        email_id_editText = (EditText) findViewById(R.id.email_id);
        phoneNum_editText = (EditText) findViewById(R.id.phone_number_reg);
        country_CodePicker = (CountryCodePicker) findViewById(R.id.ccp);

        salutationSpinner = (Spinner) findViewById(R.id.saluationSpinner);
        salutationSpinner.setOnItemSelectedListener(this);

        createpassword_editText = (EditText) findViewById(R.id.create_password);
        checkBox = (CheckBox) findViewById(R.id.check);
        firstName_error = (TextView) findViewById(R.id.firstname_error_text);
        lastName_error = (TextView) findViewById(R.id.lastname_error_text);
        emailId_error = (TextView) findViewById(R.id.emailId_error_text);
        phoneNum_error = (TextView) findViewById(R.id.phoneNumber_error_text);
        createpassword_error = (TextView) findViewById(R.id.createpsswrd_error_text);
        termsandpolicy_error = (TextView) findViewById(R.id.check_error_text);
        submit_button = (Button) findViewById(R.id.reg_submit);
        password_transformation_btn = (Button) findViewById(R.id.pswd_transformation_btn);
        tv_terms = _findViewById(R.id.terms_text);
        tv_policies = _findViewById(R.id.policies_text);
        specialityAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.specialityAutoCompleteTextView);
        speciality_error_text = (TextView) findViewById(R.id.speciality_error_text);
        password_transformation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password_transformation_btn.getText().toString().equalsIgnoreCase("Show")) {
                    createpassword_editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password_transformation_btn.setText("Hide");
                } else {
                    createpassword_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password_transformation_btn.setText("Show");
                }
                createpassword_editText.setSelection(createpassword_editText.getText().length());
            }
        });
        createpassword_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    password_transformation_btn.setVisibility(View.VISIBLE);
                } else {
                    password_transformation_btn.setVisibility(View.GONE);
                    createpassword_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password_transformation_btn.setText("Show");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!marshMallowPermission.requestPermissionForContacts()) {
                } else {*/
                specialityName = itemSelected;
                firstName_value = firstName_editText.getText().toString();
                lastName_value = lastName_editText.getText().toString();
                emailId_value = email_id_editText.getText().toString();
                phoneNumber_value = phoneNum_editText.getText().toString();
//                createPassword_value = createpassword_editText.getText().toString();
                countryCode = country_CodePicker.getSelectedCountryCode();
                phoneNumber_countryCode = countryCode.concat(phoneNumber_value);
//                if (validationUtils.isregistrationScreenone(firstName_value, lastName_value, emailId_value, phoneNumber_value, createPassword_value, checkBox.isChecked())) {
                if (validationUtils.isregistrationScreenone(firstName_value, lastName_value, emailId_value, phoneNumber_value,specialityName, checkBox.isChecked())) {


                    requestObj = new JSONObject();
                    JSONObject requestObj_OTP = new JSONObject();
                    JSONObject contactDetailsObj = new JSONObject();
                    try {
                        requestObj_OTP.put("contact", phoneNumber_countryCode);
                        requestObj_OTP.put("contactType", "PHONE");
                        requestObj_OTP.put("isRegister", true);
                        requestObj_OTP.put(RestUtils.TAG_EMAIL, emailId_value);
                       /* requestObj_OTP.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                        requestObj_OTP.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);*/

                        requestObj.put(RestUtils.TAG_FNAME, firstName_value);
                        requestObj.put(RestUtils.TAG_LNAME, lastName_value);
                        requestObj.put(RestUtils.TAG_EMAIL, emailId_value);
                        requestObj.put("speciality", specialityName);

//                        requestObj.put(RestUtils.TAG_PASSWORD_REGISTRATION, createPassword_value);
                        requestObj.put(RestUtils.TAG_USER_SALUTAION, selectedSalutation);

                        contactDetailsObj.put(RestUtils.TAG_CONTACT, phoneNumber_countryCode);
                        contactDetailsObj.put(RestUtils.TAG_TYPE, "PHONE");

                        JSONArray contactDetailsArray = new JSONArray();
                        contactDetailsArray.put(contactDetailsObj);

                        requestObj.put(RestUtils.TAG_CONTACT_DETAILS_REGISTRATION, contactDetailsArray);
                        requestObj.put(RestUtils.TAG_STAY_LOGGED_IN, true);
                        if (AppUtil.isConnectingToInternet(RegistrationDetailsActivity.this)) {
                            OtpService.getInstance().requestParameters(firstName_value, lastName_value, RegistrationDetailsActivity.this, true);
                            OtpService.getInstance().requestOtp(requestObj_OTP, RegistrationDetailsActivity.this, true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               /* JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                    jsonObject.put("SignUpSubmit", firstName_value + lastName_value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.EVENT_DOCID, android_id);
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);

                AppUtil.logUserEventWithHashMap("SignUpSubmitButton", 0, data, RegistrationDetailsActivity.this);
//                }
            }
        });
        tv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showinWebview(tv_terms.getText().toString());
            }
        });

        tv_policies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showinWebview(tv_policies.getText().toString());
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    AppUtil.logUserUpShotEvent("RegisterBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
            }
        });

        salutationsList.add("Dr.");
        salutationsList.add("Mr.");
        salutationsList.add("Mrs.");
        salutationsList.add("Ms.");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.textview_item, salutationsList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        salutationSpinner.setAdapter(dataAdapter);

        actionBar.setDisplayHomeAsUpEnabled(false);
       // actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);

        getSpeciality();

        specialityArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, specialityArrayList);
        specialityAutoCompleteTextView.setAdapter(specialityArrayAdapter);
        specialityAutoCompleteTextView.setThreshold(2);
        specialityAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforeTextChanged", String.valueOf(s));
                itemSelected = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", String.valueOf(s));
                itemSelected = "";

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", String.valueOf(s));
            }
        });

        // Display a Toast Message when the user clicks on an item in the AutoCompleteTextView
        specialityAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {
                itemSelected = arg0.getItemAtPosition(arg2).toString();
//                Toast.makeText(getApplicationContext(),
//                        itemSelected,
//                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void register(JSONObject requestObj) {
        try {
            if (AppUtil.isConnectingToInternet(RegistrationDetailsActivity.this)) {
                showProgress();
                new VolleySinglePartStringRequest(RegistrationDetailsActivity.this, Request.Method.POST, RestApiConstants.REGISTRATION_PROCESS, requestObj.toString(), "REGISTRATION_PROCESS", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String login_response) {
                        try {
                            hideProgress();
                            JSONObject jsonObject = new JSONObject(login_response);
                            if (jsonObject.getString(TAG_STATUS).equals(TAG_SUCCESS)) {
                                mySharedPref.getPrefsHelper().savePref(mySharedPref.PREF_REMEMBER_ME, true);
                                mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
                                mySharedPref.getPrefsHelper().savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_REMEMBER_ME, false));
                                JSONObject data = jsonObject.getJSONObject(TAG_DATA);
                                try {
                                    boolean is_user_active = data.optBoolean("is_user_active", false);
                                    AppConstants.IS_USER_VERIFIED = data.optBoolean("is_user_verified", false);
                                    if (data.has(RestUtils.TAG_FNAME)) {
                                        fname = data.getString(RestUtils.TAG_FNAME);
                                        lname = data.getString(RestUtils.TAG_LNAME);
                                    } else {
                                        fname = "";
                                        lname = "";
                                    }
                                    mySharedPref.savePref("is_user_active",is_user_active);
                                    String security_token = data.getString("security_token");
                                    tokenshe = tokenshp.edit();
                                    tokenshe.putString("SSTOKEN", data.getString(RestUtils.TAG_SSTOKEN));
                                    MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_SESSION_TOKEN, security_token);
                                    MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_USER_PASSWORD, createpassword_editText.getText().toString());
                                    tokenshe.commit();

                                    BasicInfo basicInfo = new BasicInfo();
                                    basicInfo.setFname(fname);
                                    basicInfo.setLname(lname);
                                    basicInfo.setEmail(emailId_value);
                                    basicInfo.setPhone_num(phoneNumber_countryCode);
                                    basicInfo.setDoc_id(Integer.parseInt(data.getString(RestUtils.TAG_DOC_ID)));
                                    basicInfo.setPhone_verify(data.optBoolean(RestUtils.TAG_PHONE_VERIFY));
                                    basicInfo.setEmail_verify(data.optBoolean(RestUtils.TAG_EMAIL_VERIFY));
                                    basicInfo.setUserSalutation(data.optString(RestUtils.TAG_USER_SALUTAION));
                                    basicInfo.setUserType(data.optInt(RestUtils.TAG_USER_TYPE_ID));
                                    basicInfo.setDocProfileURL(data.optString(RestUtils.TAG_PROFILE_URL));
                                    basicInfo.setDocProfilePdfURL(data.optString(RestUtils.TAG_PROFILE_PDF_URL));
                                    basicInfo.setUserUUID(data.optString(RestUtils.TAG_USER_UUID));
                                    basicInfo.setOverAllExperience(999);
                                    basicInfo.setSplty(specialityName);
                                    basicInfo.setEmail_visibility(0);
                                    basicInfo.setPhone_num_visibility(0);
                                    realmManager.insertBasicInfo(realm, basicInfo);

                                    /*Map<String, String> articleParams = new HashMap<String, String>();
                                    //param keys and values have to be of String type
                                    articleParams.put("Doc_ID", data.optString(RestUtils.TAG_DOC_ID));
                                    articleParams.put("Email_ID", emailId_value);
                                    //up to 10 params can be logged with each event
                                    FlurryAgent.logEvent("REGISTRATION", articleParams);*/
                                    AppUtil.logFlurryEventWithDocIdAndEmailEvent("REGISTRATION", data.optString(RestUtils.TAG_USER_UUID), emailId_value);
                                    /*Answers.getInstance().logSignUp(new SignUpEvent()
                                            .putMethod("REGISTRATION")
                                            .putSuccess(true)
                                            .putCustomAttribute("Doc_ID", data.getString(RestUtils.TAG_DOC_ID))
                                            .putCustomAttribute("Email_ID", emailId_value));*/

                                    //** updating DocId **//*
                                    //realmManager.updateUserid(realm, Integer.parseInt(data.getString(RestUtils.TAG_DOC_ID)), fname, lname, emailId_value, phoneNumber_countryCode);

                                    String qb_user_id = data.getString(RestUtils.TAG_QB_USER_ID);
                                    String qb_user_login = data.getString(RestUtils.TAG_QB_USER_LOGIN);
                                    //***saving in realm database ***//
                                    //String qb_password = MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_PASSWORD, data.optString("qb_user_pwd"));
                                    String qb_password = data.optString("qb_user_pwd");
                                    String qb_hidden_dialog_id = data.getString(RestUtils.TAG_USER_DIALOG_ID);

                                    realmManager.updateQBBasicLogin(realm, qb_user_id, qb_user_login, qb_password, qb_hidden_dialog_id);

                                    //RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
                                    //qbLogin = new QBLogin(RegistrationDetailsActivity.this, realmBasicInfo.getQb_login(), realmBasicInfo.getPsswd(), realmBasicInfo.getSplty(), "Login",is_user_active);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                mySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_REMEMBER_ME, true);
                                mySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_REGISTRATION_FLAG, true);
                                AppConstants.USER_LOGIN = emailId_value;
                               /* Intent in = new Intent(RegistrationDetailsActivity.this, MandatoryProfileInfo.class);
                                in.putExtra("first_name", firstName_value);
                                in.putExtra("last_name", lastName_value);
                                startActivity(in);
                                finish();*/
                                Intent i = new Intent(RegistrationDetailsActivity.this, DashboardActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();

                            } else if (jsonObject.getString(TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                hideProgress();
                                displayErrorScreen(login_response);
                            }
                        } catch (Exception e) {
                            hideProgress();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                AppUtil.logUserUpShotEvent("RegisterDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(RegistrationDetailsActivity.this, GetStartedActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    AppUtil.logUserUpShotEvent("RegisterBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showinWebview(String str) {

        final Dialog dialogPolicies = new Dialog(RegistrationDetailsActivity.this);
        dialogPolicies.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPolicies.setContentView(R.layout.registration_terms_policy_dialog);


        WebView webViewPolicies = (WebView) dialogPolicies.findViewById(R.id.policyWebView);
        webViewPolicies.getSettings().setJavaScriptEnabled(true);
        if (str.equals("Terms of Use")) {
            webViewPolicies.loadUrl("file:///android_asset/TermsofService.html");
        } else if (str.equals("Privacy Policy")) {
            webViewPolicies.loadUrl("file:///android_asset/PrivacyPolicy.html");
        }
        dialogPolicies.show();
    }

    @Override
    public void onOtpAction(OTP otp, String response_OTP) {
        Log.i("RegistrationDetailsAc", "onOtpAction() - " + otp);
        if (otp.equals(OTP.SUCCESS)) {
            register(requestObj);
        } else if (otp.equals(OTP.CHANGE_MOB_NO)) {
            hideProgress();
            phoneNum_editText.requestFocus();
        } else if (otp.equals(OTP.RESEND)) {
            hideProgress();
            /*
             * Method recursion call written in OtpService class.
             */
        } else if (otp.equals(OTP.FAILURE)) {
            hideProgress();
            AppUtil.logFlurryEventWithDocIdAndEmailEvent("RegistrationOTPFailure", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
            /*
             *
             */
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.PHONE_PERMISSION_REQUEST_CODE:
                submit_button.performClick();
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
// On selecting a spinner item
        selectedSalutation = adapterView.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void getSpeciality() {
        specialityViewModel.getSpeciality(RegistrationDetailsActivity.this).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("status") == 200) {
                        JSONArray specialitiesArray = jsonObject.getJSONObject("response").getJSONObject("data").getJSONArray("specialities");
//                        if (patientDetails.length() == 0) {
//                            apptCalendarNoRecord.setVisibility(View.VISIBLE);
//                            bookApptValue = 2;
//                            apptCalendarNoRecordText.setText("No appointment found for the patient '" + patientName.toString() + "'");
//                            apptCalendarRecycler.setVisibility(View.GONE);
//
//                        } else {
//
                        specialityArrayList.clear();
                        for (int i = 0; i < specialitiesArray.length(); i++) {
//                                JSONObject patientDetail = patientDetails.getJSONObject(i);
//                                JSONArray assignCategory = patientDetail.getJSONArray("assignedCategories");
//                                PatientPListModel patientPListModel = new PatientPListModel();
//                                patientPListModel.setPatientName(patientDetail.getString("fullname"));
//                                patientPListModel.setPatientId(patientDetail.getInt("id"));
//                                patientPListModel.setEmailid(patientDetail.getString("email"));
//                                patientPListModel.setPhNo(patientDetail.optString("phone"));
//                                patientPListModel.setPatientAge(patientDetail.getString("age"));
//                                patientPListModel.setPatientGender(patientDetail.getInt("gender"));
//                                patientPListModel.setAssignCategory(assignCategory);
                            String specialityString = specialitiesArray.get(i).toString();
                            specialityArrayList.add(specialityString);
                        }
                        specialityArrayAdapter.notifyDataSetChanged();
//                        }

                    }else{
                        Toast.makeText(RegistrationDetailsActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
