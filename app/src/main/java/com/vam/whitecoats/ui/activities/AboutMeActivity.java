package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.hbb20.CountryCodePicker;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.OtpService;
import com.vam.whitecoats.constants.OTP;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.interfaces.OnOtpActionListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;

/**
 * Created by swathim on 6/22/2015.
 */
public class AboutMeActivity extends BaseActionBarActivity implements OnOtpActionListener {
    public static final String TAG = AboutMeActivity.class.getSimpleName();
    EditText edt_web_url, edt_blog_url, edt_facebook_url, linkedInEdtTxt, twitterEdtTxt, instaEdtTxt, contact_no_edit, email_id_edt, profile_url_edt;
    private CountryCodePicker country_CodePicker;
    TextView contact_error, email_error, profile_url_prefix;
    private Realm realm;
    private RealmManager realmManager;
    String countryCode, phoneNumber_countryCode, phoneNumber_value;
    private BasicInfo basicInfo;
    private TextView next_button;
    private boolean isCountryCodeChanged = false;

    RealmBasicInfo realmBasicInfo;
    private String blockCharacterSet = " ", trimmedCountryCode;
    private String trimmedPhoneNumber = "";

    private ViewGroup profile_url_layout;

    private CheckBox contactNumberVisibility, emailIDVisibility;
    private Boolean checkUnCheckContactNumber = false, checkUnCheckEmail = false;

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains((source))) {
                return "";
            }
            return null;
        }
    };
    private ValidationUtils validationUtilsObj;
    private String profile_url_prefix_string = "";
    private String personalized_url = "";
    private boolean customBackButton = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        initialize();
        validationUtilsObj = new ValidationUtils(AboutMeActivity.this,
                new EditText[]{contact_no_edit, email_id_edt},
                new TextView[]{contact_error, email_error});
        edt_web_url.setFilters(new InputFilter[]{filter});
        edt_blog_url.setFilters(new InputFilter[]{filter});
        edt_facebook_url.setFilters(new InputFilter[]{filter});
        linkedInEdtTxt.setFilters(new InputFilter[]{filter});
        twitterEdtTxt.setFilters(new InputFilter[]{filter});
        instaEdtTxt.setFilters(new InputFilter[]{filter});
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
        try {

            edt_web_url = _findViewById(R.id.website_edit);
            edt_blog_url = _findViewById(R.id.blog_page_edit);
            edt_facebook_url = _findViewById(R.id.facebookpage_edit);
            linkedInEdtTxt = _findViewById(R.id.linkedIn_edit);
            twitterEdtTxt = _findViewById(R.id.twitter_edit);
            instaEdtTxt = _findViewById(R.id.insta_edit);
            contact_no_edit = _findViewById(R.id.contact_no_edit);
            email_id_edt = _findViewById(R.id.email_text);
            country_CodePicker = _findViewById(R.id.ccp_basicprofile);
            contact_error = _findViewById(R.id.contact_no_text);
            email_error = _findViewById(R.id.email_error);
            profile_url_prefix = _findViewById(R.id.prefix_url_tv);
            profile_url_edt = _findViewById(R.id.personalized_url);
            profile_url_layout = _findViewById(R.id.profile_url_layout);
            contactNumberVisibility = _findViewById(R.id.contactNumberVisibility);
            emailIDVisibility = _findViewById(R.id.emailIDVisibility);
            basicInfo = new BasicInfo();
            String code = "";
            contact_no_edit.setCompoundDrawablesWithIntrinsicBounds(new TextDrawable(code), null, null, null);
            contact_no_edit.setCompoundDrawablePadding(code.length() * 12 + 12);
            mInflater = LayoutInflater.from(this);
            mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            next_button = (TextView) mCustomView.findViewById(R.id.next_button);
            mTitleTextView.setText(getString(R.string.about_contactdet));
            next_button.setText(getString(R.string.actionbar_save));
            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(this);
            realmBasicInfo = realmManager.getRealmBasicInfo(realm);
            if (realmBasicInfo != null) {
                String aboutMe = realmBasicInfo.getAbout_me() == null ? "" : realmBasicInfo.getAbout_me();
                String website = realmBasicInfo.getWebsite();
                String blog = realmBasicInfo.getBlog_page();
                String facebook = realmBasicInfo.getFb_page();
                String linkedInStr = realmBasicInfo.getLinkedInPg();
                String twitterStr = realmBasicInfo.getTwitterPg();
                String instaStr = realmBasicInfo.getInstagramPg();
                String email = realmBasicInfo.getEmail();
                String contactNum = realmBasicInfo.getPhone_num();
                String specificAsk = realmBasicInfo.getSpecificAsk();
                String profileUrl = realmBasicInfo.getDocProfileURL();
                int phoneNumberVisibility = realmBasicInfo.getPhone_num_visibility();
                int emailVisibility = realmBasicInfo.getEmail_visibility();

                if (phoneNumberVisibility == 1) {
                    contactNumberVisibility.setChecked(true);
                    checkUnCheckContactNumber = true;
                } else {
                    contactNumberVisibility.setChecked(false);
                    checkUnCheckContactNumber = false;
                }

                if (emailVisibility == 1) {
                    emailIDVisibility.setChecked(true);
                    checkUnCheckEmail = true;
                } else {
                    emailIDVisibility.setChecked(false);
                    checkUnCheckEmail = false;
                }

                edt_web_url.setText(website);
                edt_blog_url.setText(blog);
                edt_facebook_url.setText(facebook);
                email_id_edt.setText(email);
                contact_no_edit.setText(contactNum);
                linkedInEdtTxt.setText(linkedInStr);
                twitterEdtTxt.setText(twitterStr);
                instaEdtTxt.setText(instaStr);
                basicInfo.setDoc_id(realmBasicInfo.getDoc_id());
                basicInfo.setAbout_me(aboutMe);
                basicInfo.setSpecificAsk(specificAsk);
                basicInfo.setWebsite(website);
                basicInfo.setBlog_page(blog);
                basicInfo.setFb_page(facebook);
                basicInfo.setLinkedInPg(linkedInStr);
                basicInfo.setTwitterPg(twitterStr);
                basicInfo.setInstagramPg(instaStr);
                basicInfo.setEmail(email);
                basicInfo.setPhone_num(contactNum);
                basicInfo.setDocProfilePdfURL(realmBasicInfo.getDocProfilePdfURL());
                if (profileUrl != null && !profileUrl.isEmpty() && profileUrl.contains("/")) {
                    basicInfo.setDocProfileURL(profileUrl);
                    profile_url_layout.setVisibility(View.VISIBLE);
                    int lastIndex = profileUrl.lastIndexOf("/");
                    profile_url_prefix_string = profileUrl.substring(0, lastIndex + 1);
                    personalized_url = profileUrl.substring(lastIndex + 1, profileUrl.length());
                    profile_url_edt.setText(personalized_url);
                    profile_url_prefix.setText(profile_url_prefix_string);
                } else {
                    profile_url_layout.setVisibility(View.GONE);
                }
                if (basicInfo.getPhone_num().length() > 10) {
                    trimmedCountryCode = basicInfo.getPhone_num().substring(0, (basicInfo.getPhone_num().length() - 10));
                    trimmedPhoneNumber = basicInfo.getPhone_num().substring(trimmedCountryCode.length(), basicInfo.getPhone_num().length());
                } else {
                    trimmedPhoneNumber = basicInfo.getPhone_num();
                }
                contact_no_edit.setText(trimmedPhoneNumber);
                if (trimmedCountryCode != null) {
                    country_CodePicker.setCountryForPhoneCode(Integer.parseInt(trimmedCountryCode));
                    String countryNameCode = mySharedPref.getPref(MySharedPref.PREF_USER_COUNTRY_NAME_CODE, "");
                    if (!countryNameCode.isEmpty()) {
                        country_CodePicker.setCountryForNameCode(countryNameCode);
                    }
                }
                country_CodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                    @Override
                    public void onCountrySelected() {
                        if (trimmedCountryCode != null) {
                            if (country_CodePicker.getSelectedCountryCode().equalsIgnoreCase(trimmedCountryCode)) {
                                isCountryCodeChanged = false;
                            } else {
                                isCountryCodeChanged = true;
                            }
                        } else {
                            isCountryCodeChanged = true;
                        }
                    }
                });
            }

            contactNumberVisibility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CompoundButton) view).isChecked()) {
                        checkUnCheckContactNumber = true;
                    } else {
                        checkUnCheckContactNumber = false;

                    }
                }
            });

            emailIDVisibility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CompoundButton) view).isChecked()) {
                        checkUnCheckEmail = true;
                    } else {
                        checkUnCheckEmail = false;

                    }
                }
            });


            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String edt_web_url_str = edt_web_url.getText().toString().trim();
                    String edt_blog_url_str = edt_blog_url.getText().toString().trim();
                    String edt_facebook_url_str = edt_facebook_url.getText().toString().trim();
                    String linkedIn = linkedInEdtTxt.getText().toString().trim();
                    String twitter = twitterEdtTxt.getText().toString().trim();
                    String instagram = instaEdtTxt.getText().toString().trim();
                    String emailEditStr = email_id_edt.getText().toString().trim();
                    String personalizedUrlStr = profile_url_edt.getText().toString().trim();
                    phoneNumber_value = contact_no_edit.getText().toString().trim();
                    countryCode = country_CodePicker.getSelectedCountryCode();
                    phoneNumber_countryCode = countryCode.concat(phoneNumber_value);

                    if (validationUtilsObj.isphoneEmailEntered(phoneNumber_value, emailEditStr)) {
                        basicInfo.setWebsite(edt_web_url_str);
                        basicInfo.setBlog_page(edt_blog_url_str);
                        basicInfo.setFb_page(edt_facebook_url_str);
                        basicInfo.setLinkedInPg(linkedIn);
                        basicInfo.setTwitterPg(twitter);
                        basicInfo.setInstagramPg(instagram);
                        basicInfo.setPhone_num(phoneNumber_countryCode);
                        basicInfo.setEmail(emailEditStr);
                        basicInfo.setDocProfilePdfURL(realmBasicInfo.getDocProfilePdfURL());
                        if (profile_url_prefix_string != null && !profile_url_prefix_string.isEmpty()) {
                            if (!personalizedUrlStr.isEmpty()) {
                                basicInfo.setDocProfileURL(profile_url_prefix_string + personalizedUrlStr);
                            } else {
                                basicInfo.setDocProfileURL(profile_url_prefix_string + personalized_url);
                            }
                        }
                        hideKeyboard();
                        updateAboutMe();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    private void updateAboutMe() {
        if (isConnectingToInternet()) {
            if (!TextUtils.isEmpty(edt_web_url.getText().toString()) ||
                    !TextUtils.isEmpty(edt_blog_url.getText().toString()) ||
                    !TextUtils.isEmpty(edt_facebook_url.getText().toString()) ||
                    !TextUtils.isEmpty(email_id_edt.getText().toString()) ||
                    !TextUtils.isEmpty(contact_no_edit.getText().toString())) {
                if (isCountryCodeChanged || !(trimmedPhoneNumber.equals(contact_no_edit.getText().toString())) || !realmBasicInfo.isMobileVerified()) {
                    try {
                        JSONObject requestObj_OTP = new JSONObject();
                        requestObj_OTP.put("contact", basicInfo.getPhone_num());
                        requestObj_OTP.put("contactType", "PHONE");
                        requestObj_OTP.put("isProfileUpdate", true);
                        requestObj_OTP.put(RestUtils.TAG_DOC_ID, realmBasicInfo.getDoc_id());
                        OtpService.getInstance().requestOtp(requestObj_OTP, AboutMeActivity.this, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    try {
                        /** Create JSON Object **/
                        JSONObject object = new JSONObject();
                        object.put(RestUtils.TAG_USER_ID, basicInfo.getDoc_id());
                        object.put(RestUtils.TAG_CNT_EMAIL, basicInfo.getEmail());
                        List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                        /** ContactNumber Visibility **/
                        if (checkUnCheckContactNumber) {
                            object.put(RestUtils.TAG_CNNTMUNVIS, visibilityList.get(1));
                        } else {
                            object.put(RestUtils.TAG_CNNTMUNVIS, visibilityList.get(0));
                        }
//                        if (basicInfo.getPhone_num_visibility() == 0) {
//                            object.put(RestUtils.TAG_CNNTMUNVIS, visibilityList.get(0));
//                        } else if (basicInfo.getPhone_num_visibility() == 1) {
//                            object.put(RestUtils.TAG_CNNTMUNVIS, visibilityList.get(1));
//                        } else if (basicInfo.getPhone_num_visibility() == 2) {
//                            object.put(RestUtils.TAG_CNNTMUNVIS, visibilityList.get(2));
//                        }
                        /** email Visibility **/
                        if (checkUnCheckEmail) {
                            object.put(RestUtils.TAG_CNNTEMAILVIS, visibilityList.get(1));
                        } else {
                            object.put(RestUtils.TAG_CNNTEMAILVIS, visibilityList.get(0));
                        }

//                        if (basicInfo.getEmail_visibility() == 0) {
//                            object.put(RestUtils.TAG_CNNTEMAILVIS, visibilityList.get(0));
//                        } else if (basicInfo.getEmail_visibility() == 1) {
//                            object.put(RestUtils.TAG_CNNTEMAILVIS, visibilityList.get(1));
//                        } else if (basicInfo.getEmail_visibility() == 2) {
//                            object.put(RestUtils.TAG_CNNTEMAILVIS, visibilityList.get(2));
//                        }
                        object.put(RestUtils.TAG_CNT_NUM, basicInfo.getPhone_num());

                        JSONArray socialInfoArray = new JSONArray();
                        JSONObject socialInfoObj = new JSONObject();
                        socialInfoObj.put(RestUtils.TAG_ABOUT_ME, basicInfo.getAbout_me());
                        socialInfoObj.put(RestUtils.KEY_SPECIFIC_ASK, basicInfo.getSpecificAsk());
                        socialInfoObj.put(RestUtils.TAG_BLOG_PAGE, basicInfo.getBlog_page());
                        socialInfoObj.put(RestUtils.TAG_WEB_PAGE, basicInfo.getWebsite());
                        socialInfoObj.put(RestUtils.TAG_FB_PAGE, basicInfo.getFb_page());
                        socialInfoObj.put(RestUtils.TAG_LINKEDIN_PAGE, basicInfo.getLinkedInPg());
                        socialInfoObj.put(RestUtils.TAG_TWITTER_PAGE, basicInfo.getTwitterPg());
                        socialInfoObj.put(RestUtils.TAG_INSTAGRAM_PAGE, basicInfo.getInstagramPg());
                        socialInfoObj.put(RestUtils.TAG_PROFILE_URL, basicInfo.getDocProfileURL() == null ? "" : basicInfo.getDocProfileURL());
                        socialInfoObj.put(RestUtils.TAG_PROFILE_PDF_URL, basicInfo.getDocProfilePdfURL());
                        socialInfoArray.put(socialInfoObj);
                        object.put(RestUtils.TAG_SOCIAL_INFO, socialInfoArray);
                        showProgress();
                        new VolleySinglePartStringRequest(AboutMeActivity.this, Request.Method.POST, RestApiConstants.UPDATE_USER_PROFILE, object.toString(), "ABOUT_ME", new OnReceiveResponse() {
                            @Override
                            public void onSuccessResponse(String successResponse) {
                                hideProgress();
                                JSONObject responseObj = null;
                                try {
                                    responseObj = new JSONObject(successResponse);
                                    if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                        if (responseObj.has(RestUtils.TAG_DATA)) {
                                            JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                            if (dataObj.has(RestUtils.TAG_VERIFICATION_INFO)) {
                                                JSONObject verificationInfoObject = dataObj.optJSONObject(RestUtils.TAG_VERIFICATION_INFO);
                                                basicInfo.setPhone_verify(verificationInfoObject.optBoolean(RestUtils.TAG_PHONE_VERIFY));
                                                basicInfo.setEmail_verify(verificationInfoObject.optBoolean(RestUtils.TAG_EMAIL_VERIFY));
                                            }
                                            if (dataObj.has(RestUtils.TAG_SOCIAL_INFO)) {
                                                JSONObject socialInfoObject = dataObj.optJSONArray(RestUtils.TAG_SOCIAL_INFO).optJSONObject(0);
                                                basicInfo.setAbout_me(socialInfoObject.optString(RestUtils.TAG_ABOUT_ME));
                                                basicInfo.setBlog_page(socialInfoObject.optString(RestUtils.TAG_BLOG_PAGE));
                                                basicInfo.setWebsite(socialInfoObject.optString(RestUtils.TAG_WEB_PAGE));
                                                basicInfo.setFb_page(socialInfoObject.optString(RestUtils.TAG_FB_PAGE));
                                                basicInfo.setLinkedInPg(socialInfoObject.optString(RestUtils.TAG_LINKEDIN_PAGE));
                                                basicInfo.setTwitterPg(socialInfoObject.optString(RestUtils.TAG_TWITTER_PAGE));
                                                basicInfo.setInstagramPg(socialInfoObject.optString(RestUtils.TAG_INSTAGRAM_PAGE));
                                                basicInfo.setDocProfileURL(socialInfoObject.optString(RestUtils.TAG_PROFILE_URL));
                                                basicInfo.setDocProfilePdfURL(socialInfoObject.optString(RestUtils.TAG_PROFILE_PDF_URL));
                                            }
                                            basicInfo.setEmail(dataObj.optString(RestUtils.TAG_CNT_EMAIL));
                                            basicInfo.setPhone_num(dataObj.optString(RestUtils.TAG_CNT_NUM));

                                            List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                                            /** ContactNumber Visibility **/
                                            if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                                basicInfo.setPhone_num_visibility(0);
                                            } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                                basicInfo.setPhone_num_visibility(1);
                                            } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                                basicInfo.setPhone_num_visibility(2);
                                            }
                                            /** email Visibility **/

                                            if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                                basicInfo.setEmail_visibility(0);

                                            } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                                basicInfo.setEmail_visibility(1);

                                            } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                                basicInfo.setEmail_visibility(2);
                                            }
                                            realmManager.updateAboutmeBasicInfo(realm, basicInfo);
                                            mySharedPref.savePref(MySharedPref.PREF_USER_COUNTRY_NAME_CODE, country_CodePicker.getSelectedCountryNameCode());
                                            Toast.makeText(AboutMeActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                                            Intent in = new Intent();
                                            setResult(Activity.RESULT_OK, in);
                                            finish();
                                        }

                                    } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                        String errorMsg = getResources().getString(R.string.unable_to_connect_server);
                                        if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                            errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                        }
                                        Toast.makeText(AboutMeActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(String errorResponse) {
                                try {
                                    JSONObject errorObj = new JSONObject(errorResponse);
                                    String errorMessage = errorObj.optInt(RestUtils.TAG_ERROR_CODE) == 102 ? errorObj.optString(RestUtils.TAG_ERROR_MESSAGE) : getString(R.string.unable_to_connect_server);
                                    Toast.makeText(AboutMeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }).sendSinglePartRequest();
                        //new SingletonAsync(AboutMeActivity.this, RestApiConstants.EDIT_ABOUT_DETAILS).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), object.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } else {
            ShowSimpleDialog(null, getResources().getString(R.string.sNetworkError));
        }
    }

    @Override
    public void onBackPressed() {

        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("UserContactInfoDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        boolean isModified = false;
        try {
            String aboutMe = basicInfo.getAbout_me() == null ? "" : basicInfo.getAbout_me();
            String blogPage = basicInfo.getBlog_page() == null ? "" : basicInfo.getBlog_page();
            String website = basicInfo.getWebsite() == null ? "" : basicInfo.getWebsite();
            String fbPage = basicInfo.getFb_page() == null ? "" : basicInfo.getFb_page();
            String email = basicInfo.getEmail() == null ? "" : basicInfo.getEmail();
            if (!blogPage.equals(edt_blog_url.getText().toString()))
                isModified = true;
            else if (!website.equals(edt_web_url.getText().toString()))
                isModified = true;
            else if (!trimmedPhoneNumber.equals(contact_no_edit.getText().toString()))
                isModified = true;
            else if (!fbPage.equals(edt_facebook_url.getText().toString()))
                isModified = true;
            else if (!email.equals(email_id_edt.getText().toString()))
                isModified = true;

            if (isModified) {
                ShowAlert();
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(String response) {
        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
                hideProgress();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.we_unable_to_save));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Intent in = new Intent(AboutMeActivity.this, ProfileViewActivity.class);
                        startActivity(in);*/
                        finish();
                    }
                });
                builder.show();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        Toast.makeText(AboutMeActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                        hideProgress();
                        realmManager.updateAboutmeBasicInfo(realm, basicInfo);
                        Intent in = new Intent();
                        setResult(Activity.RESULT_OK, in);
                        finish();
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

    private void ShowAlert() {
        /** Back Button Alert **/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.profile_back_button));
        builder.setPositiveButton(getResources().getString(R.string.actionbar_save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                next_button.performClick();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                /*Intent login_intent = new Intent(AboutMeActivity.this, ProfileViewActivity.class);
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
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("UserContactInfoBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
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
        Log.i(TAG, "onOtpAction() - " + otp);
        if (otp.equals(OTP.SUCCESS)) {
            if (isCountryCodeChanged)
                isCountryCodeChanged = false;
            trimmedPhoneNumber = contact_no_edit.getText().toString().trim();
            if (basicInfo != null) {
                realmManager.updatePhoneVerifyStatus(true);
                updateAboutMe();
            }
        } else if (otp.equals(OTP.CHANGE_MOB_NO)) {
            hideProgress();
        } else if (otp.equals(OTP.RESEND)) {
            hideProgress();
            /*
             * Method recursion call written in OtpService class.
             */
//        }else if(otp.equals(OTP.FAILURE)){
            /*
             *
             */
        }
    }

    public class TextDrawable extends Drawable {

        private final String text;
        private final Paint paint;

        public TextDrawable(String text) {
            this.text = text;
            this.paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(26);
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.LEFT);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawText(text, 0, 8, paint);
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
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
