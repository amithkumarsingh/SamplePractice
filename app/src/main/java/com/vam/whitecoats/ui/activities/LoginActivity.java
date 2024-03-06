package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.flurry.android.FlurryAgent;
import com.hbb20.CountryCodePicker;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.OtpService;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.LOGINTYPE;
import com.vam.whitecoats.constants.OTP;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.interfaces.OnOtpActionListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;


public class LoginActivity extends BaseActionBarActivity implements OnTaskCompleted, OnOtpActionListener {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private static String USER_LOGIN = "";
    private static String USER_PASSWORD = "";
    private static final String TAG_STATUS = "status";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    public static final String PREF_SESSION_TOKEN = "security_token";
    private static final String TAG_PASS_CHANGED = "password_change_required";
    private static final String TAG_USERNAME = "app_uname";
    private static final String TAG_PASSWORD = "app_pass";
    private static final String TAG_LOGGED_IN = "stay_logged_in";
    private static final String TAG_IS_OTP = "isOTP";
    private static final String TAG_OLD_PSWD = "oldPassword";

    private TextView tx_userId, newUser_tv;
    public static EditText etUsername = null;
    private EditText etPassword = null;
    private Button password_transformation_btn;
    public static CheckBox rememberCheckbox;
    private TextView txt_forgotpw, password_error_text, username_error_text;
    private String fname, lname, email, restoreDocId;
    private Realm realm;
    private RealmManager realmManager;
    String emailstring = "";
    private Button register_now_btn;
    private SharedPreferences tokenshp;
    private SharedPreferences.Editor tokenshe;
    private boolean stayLoggedInFlag = false;
    MarshMallowPermission marshMallowPermission;
    LOGINTYPE loginType;
    private Bundle bundle;
    private String navigation;
    private String emailid_in_otp = "";
    String android_id;
    private boolean customBackButton = false;
    private TextView countrycode;
    private String countryCode;
    private String phoneNumber_countryCode;
    private EditText phone_number_login;
    private String phoneNumber_value;
    private CountryCodePicker country_CodePicker;
    private LinearLayout countrycode_lay;
    private TextView phoneNumber_error_text;
    private LinearLayout countrycode_mobilenum_lay;
    private RealmBasicInfo mRealmBasicInfo;
    private ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        setContentView(R.layout.activity_login);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            navigation = bundle.getString(RestUtils.NAVIGATATION);
        }
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("X-DEVICE-ID", android_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtil.logUserWithEventName(0, "Login_Impression", jsonObject, LoginActivity.this);

        try {
            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(this);
            /**
             * Initialize UI Elements
             */
            initialize();
            mRealmBasicInfo = realmManager.getRealmBasicInfo(realm);

            tokenshp = getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
            mySharedPref = new MySharedPref(this);
            boolean isRememberMe = mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_REMEMBER_ME, true);

            rememberCheckbox.setChecked(isRememberMe);
            if (isRememberMe) {
                etUsername.setText(mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_EMAIL, ""));//realmManager.getemailstatusBasicInfo(realm).get(0)
                if (mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_PASSWORD) != null) {
                    etPassword.setText(mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_PASSWORD).toString());
                } else {
                    etPassword.setText("");
                }
            }
            List<String> emailStatusList = realmManager.getemailstatusBasicInfo(realm);
            if (emailStatusList != null && emailStatusList.size() > 0) {
                emailstring = emailStatusList.get(0);
            }
            if (!TextUtils.isEmpty(emailstring)) {
                tx_userId.setVisibility(View.VISIBLE);
                etUsername.setVisibility(View.GONE);
                countrycode_lay.setVisibility(View.GONE);
                countrycode.setVisibility(View.VISIBLE);
                countrycode_mobilenum_lay.setVisibility(View.GONE);
                tx_userId.setText(emailstring);
                String phoneNumber = realmManager.getUserPhoneNumber(realm);
                countrycode.setText("+" + mRealmBasicInfo.getPhone_num());
                register_now_btn.setVisibility(View.GONE);
                newUser_tv.setVisibility(View.GONE);

            }
            validationUtils = new ValidationUtils(LoginActivity.this,
                    new EditText[]{phone_number_login}, new TextView[]{phoneNumber_error_text}
            );
            txt_forgotpw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.logUserWithEventName(0, "Login_ForgotPassword", jsonObject, LoginActivity.this);
                    Intent forgotpasswordIntent = new Intent(LoginActivity.this, ForgotPassword.class);
                    startActivity(forgotpasswordIntent);
                }
            });

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean previouslyStarted = prefs.getBoolean("FIRST_START", false);
            Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , Start Time : " + DateUtils.getCurrentTime());
            if (!previouslyStarted) {
                SharedPreferences.Editor edit = prefs.edit();
                edit.putBoolean("FIRST_START", Boolean.TRUE);
                edit.commit();
                File deleteFile = new File(getExternalFilesDir(null).getAbsolutePath() + "/" + ".Whitecoats");
                AppUtil.deleteFilesOnPermission(this, deleteFile);
            }
            Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "Login_EmailID", jsonObject, LoginActivity.this);
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "Login_Password", jsonObject, LoginActivity.this);
            }
        });

    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }


    // Initialize elements
    private void initialize() {
        Log.i(TAG, "initialize()");
        try {
            mInflater = LayoutInflater.from(this);
            mCustomView = mInflater.inflate(R.layout.actionbar_login, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            back_button = (ImageView) mCustomView.findViewById(R.id.back_button);
            //  TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
            mTitleTextView.setText("Sign In");
            // next_button.setVisibility(View.GONE);


            marshMallowPermission = new MarshMallowPermission(this);
            etUsername = _findViewById(R.id.username_edit);
            countrycode_lay = _findViewById(R.id.countrycode_lay);
            countrycode_mobilenum_lay = _findViewById(R.id.countrycode_mobilenum_lay);
            etPassword = _findViewById(R.id.password_edit);
            tx_userId = _findViewById(R.id.user_id_text);
            rememberCheckbox = _findViewById(R.id.stay_singedIn);
            register_now_btn = _findViewById(R.id.register_now_but);
            newUser_tv = _findViewById(R.id.newUser_tv);
            etPassword.setLongClickable(false);
            password_error_text = _findViewById(R.id.password_error_text);
            username_error_text = _findViewById(R.id.username_error_text);
            txt_forgotpw = _findViewById(R.id.forgot_password_txt);
            countrycode = _findViewById(R.id.countrycode);
            phone_number_login = _findViewById(R.id.phone_number_login);
            password_transformation_btn = (Button) findViewById(R.id.pswd_transformation_btn);
            country_CodePicker = (CountryCodePicker) findViewById(R.id.ccp);
            phoneNumber_error_text = (TextView) findViewById(R.id.phoneNumber_error_text);
            actionBar.setDisplayHomeAsUpEnabled(false);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(mCustomView);
            password_transformation_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.logUserWithEventName(0, "password_transformation_btn", jsonObject, LoginActivity.this);

                    if (password_transformation_btn.getText().toString().equalsIgnoreCase("Show")) {
                        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        password_transformation_btn.setText("Hide");
                    } else {
                        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        password_transformation_btn.setText("Show");
                    }
                    etPassword.setSelection(etPassword.getText().length());
                }
            });
            etPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0) {
                        password_transformation_btn.setVisibility(View.VISIBLE);
                    } else {
                        password_transformation_btn.setVisibility(View.GONE);
                        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        password_transformation_btn.setText("Show");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            if (navigation != null && navigation.equalsIgnoreCase("fromGetStarted")) {
                actionBar.show();
            } else {
                actionBar.hide();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("LoginBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
            }
        });
    }

    // registration button click
    public void onRegisterButtonClick(View v) {
    }

    // Login button click
    public void onLoginButtonClick(View view) {
        if (AppUtil.isConnectingToInternet(this)) {
            phoneNumber_value = phone_number_login.getText().toString();
            countryCode = country_CodePicker.getSelectedCountryCode();
            String phone_num;
            if (countrycode.getVisibility() == View.VISIBLE) {
                phoneNumber_countryCode = mRealmBasicInfo.getPhone_num();
                phone_num = mRealmBasicInfo.getPhone_num();
            } else {
                phoneNumber_countryCode = countryCode.concat(phoneNumber_value);
                phone_num = phoneNumber_value;

            }
            if (validationUtils.isLoginScreen(phone_num)) {
                JSONObject requestObj_OTP = new JSONObject();
                try {
                    requestObj_OTP.put("contact", phoneNumber_countryCode);
                    requestObj_OTP.put("contactType", "PHONE");

                    int profileCount = realmManager.getBasicInfoCount(realm);
                    Log.i(TAG, "profileCount - " + profileCount);
                    boolean num_edit_required;
                    if (profileCount > 0) {
                        num_edit_required = false;
                    } else {
                        num_edit_required = true;
                    }

                    if (AppUtil.isConnectingToInternet(LoginActivity.this)) {
                        OtpService.getInstance().requestParameters("", "", LoginActivity.this, num_edit_required);
                        OtpService.getInstance().requestOtp(requestObj_OTP, LoginActivity.this, num_edit_required);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loginUser(String otp) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("X-DEVICE-ID", android_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtil.logUserWithEventName(0, "Login_LoginButton", jsonObject, LoginActivity.this);
        Log.i(TAG, "onLoginButtonClick() : " + DateUtils.getCurrentTime());
        String URL = null;
        USER_PASSWORD = etPassword.getText().toString().trim();
        //     if (validationUtils.isValidUserData(USER_LOGIN, USER_PASSWORD)) {
        hideKeyboard();
        if (isConnectingToInternet()) {
            hideSnackBar();
            /**
             * Get Basic Profile Info fo checking whether any data exists for user. If user data
             * exists then call Login otherwise call Login with Restore Service.
             */
            int profileCount = realmManager.getBasicInfoCount(realm);
            Log.i(TAG, "profileCount - " + profileCount);
            if (profileCount > 0) {
                URL = RestApiConstants.LOGIN;
                loginType = LOGINTYPE.LOGIN;
            } else {
                URL = RestApiConstants.RESTORE_LOGIN;
                loginType = LOGINTYPE.RESTORE_LOGIN;
            }
            Log.i(TAG, "loginType - " + loginType);
            RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
            String loginPswd = realmBasicInfo.getPsswd();
            if (loginPswd == null) {


            }
            mySharedPref.getPrefsHelper().savePref(mySharedPref.PREF_REMEMBER_ME, rememberCheckbox.isChecked());
            emailid_in_otp = mySharedPref.getPrefsHelper().getPref(MySharedPref.EMAIL_ID_OTP, "");
            if (rememberCheckbox.isChecked()) {
                mySharedPref.getPrefsHelper().savePref(mySharedPref.PREF_USER_NAME, USER_LOGIN);
                stayLoggedInFlag = true;
            } else {
                stayLoggedInFlag = false;
            }
            try {
                JSONObject loginRequestObj = new JSONObject();
                loginRequestObj.put(TAG_USERNAME, phoneNumber_countryCode);
                loginRequestObj.put(TAG_PASSWORD, otp);
                loginRequestObj.put(TAG_LOGGED_IN, true);
                // if (emailid_in_otp.length() > 0) {
                loginRequestObj.put(TAG_IS_OTP, true);
                // }
                // loginRequestObj.put(TAG_OLD_PSWD, MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_PASSWORD, ""));
                showProgress();
                new VolleySinglePartStringRequest(LoginActivity.this, Request.Method.POST, URL, loginRequestObj.toString(), "LOGIN_REQUEST", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        processLoginResponse(successResponse);
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        hideProgress();
                        displayErrorScreen(errorResponse);
                        //Toast.makeText(LoginActivity.this,"Unable to login,please try again.",Toast.LENGTH_SHORT).show();
                    }
                }).sendSinglePartRequest();
                //new LoginAsync(this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), URL, USER_LOGIN, USER_PASSWORD, "" + stayLoggedInFlag, emailid_in_otp,MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_PASSWORD, ""));
            } catch (Exception e) {
                FlurryAgent.logEvent("Login service failed");
                AppUtil.captureSentryMessage("Login service failed");
                e.printStackTrace();
            }
        } else {
        }
        // }

    }

    /*public void onRestoreButtonClick(View v) {
        Log.i(TAG, "onRestoreButtonClick()");
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.resend_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Enter restore doc id");
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setView(dialoglayout);
        final EditText input = (EditText) dialoglayout.findViewById(R.id.resendemail_id);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        final TextView text = (TextView) dialoglayout.findViewById(R.id.error_text);
        text.setText("");
        Button declineButton = (Button) dialoglayout.findViewById(R.id.cancel_button);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
        Button okButton = (Button) dialoglayout.findViewById(R.id.ok_button);
        okButton.setText("Get");
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreDocId = input.getText().toString().trim();
                if (!restoreDocId.equals("")) {
                    if (isConnectingToInternet()) {
                        new RestoreProfileAsync(LoginActivity.this).execute(restoreDocId);
                        alertDialog.dismiss();
                    }

                } else {
                    text.setText("Please enter a doc id");

                }
            }
        });
        input.addTextChangedListener(new TextWatcher() {
                                         @Override
                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                         }

                                         @Override
                                         public void onTextChanged(CharSequence s, int start, int before, int count) {
                                             text.setText("");
                                         }

                                         @Override
                                         public void afterTextChanged(Editable s) {
                                         }
                                     }

        );

        alertDialog.show();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
        setCurrentActivity();
        checkNetworkConnectivity();
        List<String> basicInfos = realmManager.getemailstatusBasicInfo(realm);
        Log.d(TAG, "Basic Infos : " + basicInfos);
        emailid_in_otp = mySharedPref.getPrefsHelper().getPref(MySharedPref.EMAIL_ID_OTP, "");
        if (realmManager.getDoc_id(realm) == 0) {
            if (emailid_in_otp.length() > 0) {
                etUsername.setText(emailid_in_otp);
            }
        }

        if (etPassword != null) {
            if (emailid_in_otp.length() > 0) {
                etPassword.setHint("Password/OTP");
                etPassword.setText("");
            } else {
                etPassword.setHint("Password");
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
                AppUtil.logUserUpShotEvent("LoginDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (navigation != null && navigation.equalsIgnoreCase("fromGetStarted")) {
            Intent backIntent = new Intent(LoginActivity.this, GetStartedActivity.class);
            startActivity(backIntent);
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Would you like to exit");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void processLoginResponse(String login_response) {
        Log.i(TAG, "processLoginResponse() : " + DateUtils.getCurrentTime());
        if (login_response != null) {
            if (login_response.equals("SocketTimeoutException") || login_response.equals("Exception")) {
                Log.d("Login Exception", login_response);
                hideProgress();
                displayErrorScreen(login_response);
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(login_response);
                    if (jsonObject.getString(TAG_STATUS).equals(TAG_SUCCESS)) {
                        JSONObject data = jsonObject.getJSONObject(TAG_DATA);
                        /**
                         * Check type of URL.
                         */
                        if (loginType == LOGINTYPE.LOGIN) {
                            login(data);
                        } else if (loginType == LOGINTYPE.RESTORE_LOGIN) {
                            restoreDocId = data.optString(RestUtils.TAG_DOC_ID);
                            restore(data);
                            login(data);
                        } else {
                            restore(data);
                            emailstring = realmManager.getemailstatusBasicInfo(realm).get(0);
                            if (!TextUtils.isEmpty(emailstring)) {
                                tx_userId.setVisibility(View.VISIBLE);
                                etUsername.setVisibility(View.GONE);
                                countrycode_lay.setVisibility(View.GONE);
                                countrycode.setVisibility(View.VISIBLE);
                                countrycode_mobilenum_lay.setVisibility(View.GONE);
                                tx_userId.setText(emailstring);
                                String phoneNumber = realmManager.getUserPhoneNumber(realm);
                                countrycode.setText("+" + mRealmBasicInfo.getPhone_num());
                            }
                        }
                        AppUtil.logFlurryEventWithDocIdAndEmailEvent("LOGIN_ACTION", data.optString(RestUtils.TAG_USER_UUID), etUsername.getText().toString());
                        FlurryAgent.setUserId(data.optString(RestUtils.TAG_DOC_ID));
                    } else if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        hideProgress();
                        displayErrorScreen(login_response);
                    }


                } catch (Exception e) {
                    hideProgress();
                    //displayErrorScreen(login_response);
                    Toast.makeText(LoginActivity.this, "Unable to login,please try again.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void onTaskCompleted(String login_response) {
        Log.i(TAG, "onTaskCompleted() : " + DateUtils.getCurrentTime());
        if (login_response != null) {
            if (login_response.equals("SocketTimeoutException") || login_response.equals("Exception")) {
                Log.d("Login Exception", login_response);
                hideProgress();
                displayErrorScreen(login_response);
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(login_response);
                    if (jsonObject.getString(TAG_STATUS).equals(TAG_SUCCESS)) {
                        JSONObject data = jsonObject.getJSONObject(TAG_DATA);
                        /**
                         * Check type of URL.
                         */
                        if (loginType == LOGINTYPE.LOGIN) {
                            login(data);
                        } else if (loginType == LOGINTYPE.RESTORE_LOGIN) {
                            restoreDocId = data.optString(RestUtils.TAG_DOC_ID);
                            restore(data);
                            login(data);
                        } else {
                            restore(data);
                            emailstring = realmManager.getemailstatusBasicInfo(realm).get(0);
                            if (!TextUtils.isEmpty(emailstring)) {
                                tx_userId.setVisibility(View.VISIBLE);
                                etUsername.setVisibility(View.GONE);
                                countrycode_lay.setVisibility(View.GONE);
                                countrycode.setVisibility(View.VISIBLE);
                                countrycode_mobilenum_lay.setVisibility(View.GONE);
                                tx_userId.setText(emailstring);
                                String phoneNumber = realmManager.getUserPhoneNumber(realm);
                                countrycode.setText("+" + mRealmBasicInfo.getPhone_num());
                            }
                        }


                    } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        hideProgress();
                        displayErrorScreen(login_response);
                    }
                } catch (Exception e) {
                    hideProgress();
                    displayErrorScreen(login_response);
                    e.printStackTrace();
                }
            }

        }
    }

    public void login(JSONObject data) {
        Log.i(TAG, "login() : " + DateUtils.getCurrentTime());
        try {
            /**
             * Global parameters should be define here below
             */
            boolean is_user_active = data.optBoolean(RestUtils.TAG_IS_USER_ACTIVE, false);
            boolean doc_valid = data.optBoolean(RestUtils.TAG_IS_DOCTOR_VALID, false);
            AppConstants.IS_USER_VERIFIED = data.optBoolean(RestUtils.TAG_IS_USER_VERIFIED, false);
            boolean password_changed_required = data.optBoolean(TAG_PASS_CHANGED, false);
            String qbUserId = null;
            String qbUserLogin = null;
            String qbPassword = null;
            String hiddenDialogId = null;
            if (data.has(RestUtils.TAG_IS_DOCTOR_VALID)) {
                /**
                 * If Doctor is valid then create Session Token and save in preferences else
                 * Show Dialog
                 */
                if (doc_valid) {
                    /**
                     * Based on Login type fetch Json data and store into database
                     */
                    if (loginType == LOGINTYPE.LOGIN) {
                        if (data.has(RestUtils.TAG_FNAME)) {
                            fname = data.optString(RestUtils.TAG_FNAME);
                            lname = data.optString(RestUtils.TAG_LNAME);
                        } else {
                            fname = "";
                            lname = "";
                        }
                        email = etUsername.getText().toString();
                        JSONObject jpersonalInfoObj = data.optJSONObject(RestUtils.TAG_PERSONAL_INFO);
                        realmManager.updateUserid(realm, Integer.parseInt(data.optString(RestUtils.TAG_DOC_ID)), fname, lname, email, phoneNumber_countryCode, data.optString(RestUtils.TAG_USER_UUID), jpersonalInfoObj);
                        qbUserId = data.optString(RestUtils.TAG_QB_USER_ID);
                        qbUserLogin = data.optString(RestUtils.TAG_QB_USER_LOGIN);
                        if (data.has(RestUtils.TAG_QB_USER_PSWD) && !data.isNull(RestUtils.TAG_QB_USER_PSWD)) {
                            qbPassword = data.optString(RestUtils.TAG_QB_USER_PSWD);
                        } else {
                            qbPassword = USER_PASSWORD;
                        }
                        hiddenDialogId = data.optString(RestUtils.TAG_USER_DIALOG_ID);
                    } else {
                        if (data.has(RestUtils.TAG_OTHER_INFO)) {
                            JSONObject otherInfo = data.getJSONObject(RestUtils.TAG_OTHER_INFO);
                            qbUserId = otherInfo.optString(RestUtils.TAG_QB_USER_ID);
                            qbUserLogin = otherInfo.optString(RestUtils.TAG_QB_USER_LOGIN);
                            if (otherInfo.has(RestUtils.TAG_QB_USER_PSWD))
                                qbPassword = otherInfo.optString(RestUtils.TAG_QB_USER_PSWD);
                            else
                                qbPassword = USER_PASSWORD;
                            hiddenDialogId = otherInfo.optString(RestUtils.TAG_USER_DIALOG_ID);
                        }
                    }
                    String security_token = data.optString(PREF_SESSION_TOKEN);
                    tokenshe = tokenshp.edit();
                    tokenshe.putString("SSTOKEN", data.optString(RestUtils.TAG_SSTOKEN));
                    MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_SESSION_TOKEN, security_token);
                    MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_USER_PASSWORD, USER_PASSWORD);
                    mySharedPref.savePref("is_user_active", is_user_active);
                    tokenshe.commit();
                    /** updating DocId **/
                    /*
                     * Update QB Login info into database
                     */
                    MySharedPref.getPrefsHelper().delete(MySharedPref.EMAIL_ID_OTP);
                    //realmManager.updateQBBasicLogin(realm, qbUserId, qbUserLogin, qbPassword, hiddenDialogId);
                    /**
                     * Start process to QB Login
                     */
                    //RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
                    // if (is_user_active) {
                    hideProgress();
                    mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
                    Intent i = new Intent(this, DashboardActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                } else {
                    hideProgress();
                    ShowSimpleDialog(null, "The email id or password you entered is incorrect.Please try again");
                }

            } else {
                List<String> emailStatusList = realmManager.getemailstatusBasicInfo(realm);
                if (emailStatusList != null && emailStatusList.size() > 0) {
                    emailstring = emailStatusList.get(0);
                }
                if (!TextUtils.isEmpty(emailstring)) {
                    tx_userId.setVisibility(View.VISIBLE);
                    etUsername.setVisibility(View.GONE);
                    countrycode_lay.setVisibility(View.GONE);
                    countrycode.setVisibility(View.VISIBLE);
                    countrycode_mobilenum_lay.setVisibility(View.GONE);
                    tx_userId.setText(emailstring);
                    String phoneNumber = realmManager.getUserPhoneNumber(realm);
                    countrycode.setText("+" + mRealmBasicInfo.getPhone_num());
                }


                // Toast.makeText(getApplicationContext(), "Restore", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            FlurryAgent.logEvent("Login response, Json Exception");
            AppUtil.captureSentryMessage("Login response, Json Exception");
            e.printStackTrace();
        } catch (Exception e) {
            FlurryAgent.logEvent("Login response, Exception");
            AppUtil.captureSentryMessage("Login response, Exception");
            e.printStackTrace();
        }
    }

    public void restore(JSONObject data) {
        Log.i(TAG, "restore()");
        try {
            if (data.has("doctor_valid") && !data.optBoolean("doctor_valid")) {
                return;
            }
            BasicInfo basicInfo = new BasicInfo();
            if (data.has(RestUtils.TAG_PERSONAL_INFO)) {
                JSONObject jpersonalInfoObj = data.getJSONObject(RestUtils.TAG_PERSONAL_INFO);
                /**
                 * Personal Info Insertion
                 **/
                int docId = 0;
                if (!restoreDocId.equals("")) {
                    docId = Integer.parseInt(restoreDocId);
                }
                basicInfo.setDoc_id(docId);
                basicInfo.setUserUUID(data.optString(RestUtils.TAG_USER_UUID));
                basicInfo.setFname(jpersonalInfoObj.optString(RestUtils.TAG_FNAME));
                basicInfo.setLname(jpersonalInfoObj.optString(RestUtils.TAG_LNAME));
                basicInfo.setEmail(jpersonalInfoObj.optString(RestUtils.TAG_CNT_EMAIL));
                basicInfo.setEmail_verify(jpersonalInfoObj.optBoolean(RestUtils.TAG_EMAIL_VERIFY));
                basicInfo.setOverAllExperience(jpersonalInfoObj.optInt("experience"));
                basicInfo.setPhone_verify(jpersonalInfoObj.optBoolean(RestUtils.TAG_PHONE_VERIFY));
                basicInfo.setSubSpeciality(jpersonalInfoObj.isNull(RestUtils.TAG_SUB_SPLTY) ? "" : jpersonalInfoObj.optString(RestUtils.TAG_SUB_SPLTY));
                basicInfo.setReg_card_path("");
                basicInfo.setEmailvalidation("true");
                basicInfo.setPic_url(jpersonalInfoObj.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                if (AppUtil.checkWriteExternalPermission(LoginActivity.this) && jpersonalInfoObj.has("original_file_data") && !jpersonalInfoObj.optString("original_file_data").equals(""))
                    basicInfo.setProfile_pic_path(saveToInternalSorage(jpersonalInfoObj.optString("original_file_data")));
                else
                    basicInfo.setProfile_pic_path("");

                if (jpersonalInfoObj.has(RestUtils.TAG_SPLTY) && !jpersonalInfoObj.optString(RestUtils.TAG_SPLTY).equals(""))
                    basicInfo.setSplty(jpersonalInfoObj.optString(RestUtils.TAG_SPLTY));
                else
                    basicInfo.setSplty("");

                if (jpersonalInfoObj.has(RestUtils.TAG_CNT_NUM) && !jpersonalInfoObj.optString(RestUtils.TAG_CNT_NUM).equals(""))
                    basicInfo.setPhone_num(jpersonalInfoObj.optString(RestUtils.TAG_CNT_NUM));
                else
                    basicInfo.setPhone_num("");

                String[] vis_array = mContext.getResources().getStringArray(R.array.number_visibility_arrays_for_srever);
                List<String> stringList = new ArrayList<>(Arrays.asList(vis_array));
                if (jpersonalInfoObj.has(RestUtils.TAG_CNNTMUNVIS)) {
                    /** ContactNumber Visibility **/
                    if (stringList.get(0).equals(jpersonalInfoObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                        basicInfo.setPhone_num_visibility(0);

                    } else if (stringList.get(1).equals(jpersonalInfoObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                        basicInfo.setPhone_num_visibility(1);
                    } else if (stringList.get(2).equals(jpersonalInfoObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                        basicInfo.setPhone_num_visibility(2);
                    }
                }

                /** email Visibility **/
                if (jpersonalInfoObj.has(RestUtils.TAG_CNNTEMAILVIS)) {
                    if (stringList.get(0).equals(jpersonalInfoObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                        basicInfo.setEmail_visibility(0);

                    } else if (stringList.get(1).equals(jpersonalInfoObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                        basicInfo.setEmail_visibility(1);

                    } else if (stringList.get(2).equals(jpersonalInfoObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                        basicInfo.setEmail_visibility(2);
                    }
                }

                basicInfo.setTot_contacts(Integer.parseInt(jpersonalInfoObj.optString(RestUtils.TAG_CONNECTS_COUNT)));
                basicInfo.setTot_groups(Integer.parseInt(jpersonalInfoObj.optString(RestUtils.TAG_GROUPS_COUNT)));
                basicInfo.setTot_caserooms(Integer.parseInt(jpersonalInfoObj.optString(RestUtils.TAG_CASE_ROOM_COUNT)));
            }
            /**
             *  About me insertion
             */

            if (data.has(RestUtils.TAG_ABOUT)) {
                JSONObject jaboutObj = data.getJSONObject(RestUtils.TAG_ABOUT);
                if (jaboutObj.has(RestUtils.TAG_INFO) && !jaboutObj.optString(RestUtils.TAG_INFO).equals(""))
                    basicInfo.setAbout_me(jaboutObj.optString(RestUtils.TAG_INFO));
                else
                    basicInfo.setAbout_me("");

                if (jaboutObj.has(RestUtils.TAG_WEB_PAGE) && !jaboutObj.optString(RestUtils.TAG_WEB_PAGE).equals(""))
                    basicInfo.setWebsite(jaboutObj.optString(RestUtils.TAG_WEB_PAGE));
                else
                    basicInfo.setWebsite("");

                if (jaboutObj.has(RestUtils.TAG_BLOG_PAGE) && !jaboutObj.optString(RestUtils.TAG_BLOG_PAGE).equals(""))
                    basicInfo.setBlog_page(jaboutObj.optString(RestUtils.TAG_BLOG_PAGE));
                else
                    basicInfo.setBlog_page("");

                if (jaboutObj.has(RestUtils.TAG_FB_PAGE) && !jaboutObj.optString(RestUtils.TAG_FB_PAGE).equals(""))
                    basicInfo.setFb_page(jaboutObj.optString(RestUtils.TAG_FB_PAGE));
                else
                    basicInfo.setFb_page("");

                if (jaboutObj.has(RestUtils.TAG_PROFILE_URL)) {
                    basicInfo.setDocProfileURL(jaboutObj.optString(RestUtils.TAG_PROFILE_URL));
                } else {
                    basicInfo.setDocProfileURL("");
                }

                if (jaboutObj.has(RestUtils.TAG_PROFILE_PDF_URL)) {
                    basicInfo.setDocProfilePdfURL(jaboutObj.optString(RestUtils.TAG_PROFILE_PDF_URL));
                } else {
                    basicInfo.setDocProfilePdfURL("");
                }
            }

            /*
             * Other info insertion
             */
            if (data.has(RestUtils.TAG_OTHER_INFO)) {
                JSONObject jotherinfo = data.getJSONObject(RestUtils.TAG_OTHER_INFO);
                basicInfo.setQb_login(jotherinfo.optString(RestUtils.TAG_QB_USER_LOGIN));
                basicInfo.setQb_userid(Integer.parseInt(jotherinfo.optString(RestUtils.TAG_QB_USER_ID)));
                basicInfo.setQb_hidden_dialog_id(jotherinfo.optString(RestUtils.TAG_USER_DIALOG_ID));
                if (jotherinfo.has("qb_user_pwd"))
                    basicInfo.setPsswd(jotherinfo.optString("qb_user_pwd"));
                else
                    basicInfo.setPsswd(USER_PASSWORD);

            }

            basicInfo.setRememberMe(false);
            basicInfo.setPhone_num(phoneNumber_countryCode);
            realmManager.insertRestoreBasicInfo(realm, basicInfo);
            //}

            /**
             * Academic info insertion
             **/
            if (data.has(RestUtils.TAG_ACADEMIC_HISTORY)) {
                JSONArray jacademicjArray = data.getJSONArray(RestUtils.TAG_ACADEMIC_HISTORY);
                if (jacademicjArray.length() > 0) {
                    int length = jacademicjArray.length();
                    for (int a = 0; a < length; a++) {
                        JSONObject jacadmic = jacademicjArray.getJSONObject(a);
                        AcademicInfo academicInfo = new AcademicInfo();
                        academicInfo.setAcad_id(Integer.parseInt(jacadmic.optString(RestUtils.TAG_ACADEMIC_ID)));
                        academicInfo.setDegree(jacadmic.optString(RestUtils.TAG_DEGREE));
                        if (jacadmic.has(RestUtils.TAG_COLLEGE) && !jacadmic.optString(RestUtils.TAG_COLLEGE).equals(""))
                            academicInfo.setCollege(jacadmic.optString(RestUtils.TAG_COLLEGE));
                        else
                            academicInfo.setCollege("");
                        if (jacadmic.has(RestUtils.TAG_CURRENTLY) && !jacadmic.optString(RestUtils.TAG_CURRENTLY).equals(""))
                            academicInfo.setCurrently_pursuing(jacadmic.getBoolean(RestUtils.TAG_CURRENTLY));
                        else
                            academicInfo.setCurrently_pursuing(false);
                        if (jacadmic.has(RestUtils.TAG_PASSING_YEAR) && !jacadmic.optString(RestUtils.TAG_PASSING_YEAR).equals(""))
                            academicInfo.setPassing_year(jacadmic.getInt(RestUtils.TAG_PASSING_YEAR));
                        else
                            academicInfo.setPassing_year(0);

                        if (jacadmic.has(RestUtils.TAG_UNIVERSITY) && !jacadmic.optString(RestUtils.TAG_UNIVERSITY).equals(""))
                            academicInfo.setUniversity(jacadmic.optString(RestUtils.TAG_UNIVERSITY));
                        else
                            academicInfo.setUniversity("");
                        realmManager.insertAcademic(realm, academicInfo);
                    }
                }
            }

            /**
             * Professional info insertion
             **/
            if (data.has(RestUtils.TAG_PROFESSIONAL_HISTORY)) {
                JSONArray jProfessionalArray = data.getJSONArray(RestUtils.TAG_PROFESSIONAL_HISTORY);
                if (jProfessionalArray.length() > 0) {
                    int plen = jProfessionalArray.length();
                    for (int pr = 0; pr < plen; pr++) {
                        JSONObject jprofessional = jProfessionalArray.getJSONObject(pr);
                        ProfessionalInfo professionalInfo = new ProfessionalInfo();
                        professionalInfo.setProf_id(Integer.parseInt(jprofessional.optString(RestUtils.TAG_PROF_ID)));
                        if (jprofessional.has(RestUtils.TAG_DESIGNATION) && !jprofessional.optString(RestUtils.TAG_DESIGNATION).equals(""))
                            professionalInfo.setDesignation(jprofessional.optString(RestUtils.TAG_DESIGNATION));
                        else
                            professionalInfo.setDesignation("");
                        if (jprofessional.has("from_date") && !jprofessional.optString("from_date").equals(""))
                            professionalInfo.setStart_date(jprofessional.getLong("from_date"));
                        else
                            professionalInfo.setStart_date(0);
                        if (jprofessional.has("to_date") && !jprofessional.optString("to_date").equals(""))
                            professionalInfo.setEnd_date(jprofessional.getLong("to_date"));
                        else
                            professionalInfo.setEnd_date(0);
                        if (jprofessional.has("working_here") && !jprofessional.optString("working_here").equals(""))
                            professionalInfo.setWorking_here(jprofessional.getBoolean("working_here"));
                        else
                            professionalInfo.setWorking_here(false);
                        if (jprofessional.has(RestUtils.TAG_WORKPLACE) && !jprofessional.optString(RestUtils.TAG_WORKPLACE).equals(""))
                            professionalInfo.setWorkplace(jprofessional.optString(RestUtils.TAG_WORKPLACE));
                        else
                            professionalInfo.setWorkplace("");
                        if (jprofessional.has("show_on_card") && !jprofessional.optString("show_on_card").equals(""))
                            professionalInfo.setShowOncard(jprofessional.getBoolean("show_on_card"));
                        else
                            professionalInfo.setShowOncard(false);
                        if (jprofessional.has(RestUtils.TAG_LOCATION) && !jprofessional.optString(RestUtils.TAG_LOCATION).equals(""))
                            professionalInfo.setLocation(jprofessional.optString(RestUtils.TAG_LOCATION));
                        else
                            professionalInfo.setLocation("");
                        realmManager.insertProfession(realm, professionalInfo);
                    }

                }
            }
            /**
             * OnlinePublications info insertion
             **/
            if (data.has(RestUtils.TAG_ONLINE_PUB_HISTORY)) {
                JSONArray jonlinepubArray = data.getJSONArray(RestUtils.TAG_ONLINE_PUB_HISTORY);

                if (jonlinepubArray.length() > 0) {
                    int onlinelgth = jonlinepubArray.length();
                    for (int o = 0; o < onlinelgth; o++) {
                        JSONObject onlinejObj = jonlinepubArray.getJSONObject(o);
                        PublicationsInfo publicationsInfo = new PublicationsInfo();
                        publicationsInfo.setPub_id(Integer.parseInt(onlinejObj.optString("online_pub_id")));
                        if (onlinejObj.has(RestUtils.TAG_TITLE) && !onlinejObj.optString(RestUtils.TAG_TITLE).equals(""))
                            publicationsInfo.setTitle(onlinejObj.optString(RestUtils.TAG_TITLE));
                        else
                            publicationsInfo.setTitle("");
                        if (onlinejObj.has("author_names") && !onlinejObj.optString("author_names").equals(""))
                            publicationsInfo.setAuthors(onlinejObj.optString("author_names"));
                        else
                            publicationsInfo.setAuthors("");
                        if (onlinejObj.has("journal") && !onlinejObj.optString("journal").equals(""))
                            publicationsInfo.setJournal(onlinejObj.optString("journal"));
                        else
                            publicationsInfo.setJournal("");
                        if (onlinejObj.has("webpage_link") && !onlinejObj.optString("webpage_link").equals(""))
                            publicationsInfo.setWeb_page(onlinejObj.optString("webpage_link"));
                        else
                            publicationsInfo.setWeb_page("");
                        publicationsInfo.setType("online");
                        realmManager.insertPublicationsInfo(realm, publicationsInfo);
                    }
                }
            }
            /**
             * PrintPublications info insertion
             **/
            if (data.has(RestUtils.TAG_PRINT_PUB_HISTORY)) {
                JSONArray jprintpubArray = data.getJSONArray(RestUtils.TAG_PRINT_PUB_HISTORY);
                if (jprintpubArray.length() > 0) {
                    int printlgth = jprintpubArray.length();
                    for (int p = 0; p < printlgth; p++) {
                        JSONObject printjObj = jprintpubArray.getJSONObject(p);
                        PublicationsInfo publicationsInfo = new PublicationsInfo();
                        publicationsInfo.setPub_id(Integer.parseInt(printjObj.optString("print_pub_id")));
                        if (printjObj.has(RestUtils.TAG_TITLE) && !printjObj.optString(RestUtils.TAG_TITLE).equals(""))
                            publicationsInfo.setTitle(printjObj.optString(RestUtils.TAG_TITLE));
                        else
                            publicationsInfo.setTitle("");
                        if (printjObj.has("author_names") && !printjObj.optString("author_names").equals(""))
                            publicationsInfo.setAuthors(printjObj.optString("author_names"));
                        else
                            publicationsInfo.setAuthors("");
                        if (printjObj.has("journal") && !printjObj.optString("journal").equals(""))
                            publicationsInfo.setJournal(printjObj.optString("journal"));
                        else
                            publicationsInfo.setJournal("");
                        publicationsInfo.setType("print");
                        realmManager.insertPublicationsInfo(realm, publicationsInfo);
                    }
                }
            }
            /**
             * ProfessionalMembership info insertion
             **/
            if (data.has(RestUtils.TAG_MEM_HISTORY)) {
                JSONArray jpromemArray = data.getJSONArray(RestUtils.TAG_MEM_HISTORY);
                if (jpromemArray.length() > 0) {
                    int prolgth = jpromemArray.length();
                    for (int m = 0; m < prolgth; m++) {
                        JSONObject jmemjObj = jpromemArray.getJSONObject(m);
                        ProfessionalMembershipInfo professionalMembershipInfo = new ProfessionalMembershipInfo();
                        professionalMembershipInfo.setProf_mem_id(Integer.parseInt(jmemjObj.optString("prof_mem_id")));
                        if (jmemjObj.has("membership_name") && !jmemjObj.optString("membership_name").equals(""))
                            professionalMembershipInfo.setMembership_name(jmemjObj.optString("membership_name"));
                        else
                            professionalMembershipInfo.setMembership_name("");
                        realmManager.insertProfessionalMemData(realm, professionalMembershipInfo);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String saveToInternalSorage(String bitmapImage) {
        Log.i(TAG, "saveToInternalSorage()");
        byte[] encodeByte = Base64.decode(bitmapImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        File myDirectory = AppUtil.getExternalStoragePathFile(LoginActivity.this, ".Whitecoats");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        File folder = new File(myDirectory + "/Profile_Pic");
        if (!folder.exists()) {
            folder.mkdir();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        Date now = new Date();
        final String fileName = "profile" + formatter.format(now) + ".jpg";
        File myPath = new File(folder, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.REQUEST_ID_MULTIPLE_PERMISSIONS:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    perms.put(Manifest.permission.READ_MEDIA_IMAGES, PackageManager.PERMISSION_GRANTED);
                } else {
                    perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                }
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                        File deletefile = new File(getExternalFilesDir(null).getAbsolutePath() + "/" + ".Whitecoats");
                        if (deletefile.exists()) {
                            AppUtil.deleteRecursive(deletefile);
                        }
                    }
                } else {
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        File deletefile = new File(getExternalFilesDir(null).getAbsolutePath() + "/" + ".Whitecoats");
                        if (deletefile.exists()) {
                            AppUtil.deleteRecursive(deletefile);
                        }
                    }
                }
                break;
            case PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File deletefile = new File(getExternalFilesDir(null).getAbsolutePath() + "/" + ".Whitecoats");
                    if (deletefile.exists()) {
                        AppUtil.deleteRecursive(deletefile);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (!realm.isClosed())
            realm.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("LoginBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
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
    public void onOtpAction(OTP otp, String response_OTP) {
        if (otp.equals(OTP.SUCCESS)) {
            loginUser(response_OTP);
        } else if (otp.equals(OTP.CHANGE_MOB_NO)) {
            hideProgress();
        } else if (otp.equals(OTP.RESEND)) {
            hideProgress();
            /*
             * Method recursion call written in OtpService class.
             */
        } else if (otp.equals(OTP.FAILURE)) {
            hideProgress();
        }


    }
}
