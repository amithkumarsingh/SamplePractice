package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.Prediction;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmProfessionalInfo;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.PlacesAutoCompleteAdapter;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.ui.interfaces.LocationCaputerListner;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnlocationApiFinishedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AwsAndGoogleKeysServiceClass;
import com.vam.whitecoats.utils.LocationHelperClass;
import com.vam.whitecoats.utils.RequestForCurrentLocPlacesUsingPlaceId;
import com.vam.whitecoats.utils.RequestForPlacesUsingPlaceId;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.vam.whitecoats.viewmodel.SpecialityViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class LocationAndExperienceActivity extends AppCompatActivity implements TextWatcher {

    // private TextView enhanced_text;
    private AutoCompleteTextView city_edit, experience_autocomplete_view;
    private MarshMallowPermission marshMallowPermission;
    private LocationHelperClass locationHelperObj;

    String[] experience = {"< 1 year", "1 year", "2 years", "3 years", "4 years", "5 years", "6 years", "7 years", "8 years", "9 years", "10 years", "11 years", "12 years", "13 years", "14 years",
            "15 years", "16 years", "17 years", "18 years", "19 years", "20 years", "21 years", "22 years", "23 years", "24 years", "25 years", "26 years", "27 years", "28 years", "29 years", "30 years",
            "31 years", "32 years", "33 years", "34 years", "35 years", "36 years", "37 years", "38 years", "39 years", "40 years", "41 years", "42 years", "43 years", "44 years", "45 years", "46 years",
            "47 years", "48 years", "49 years", "50 years", "51 years", "52 years", "53 years", "54 years", "55 years", "56 years", "57 years", "58 years", "59 years", "60 years", "61 years", "62 years",
            "63 years", "64 years", "65 years", "66 years", "67 years", "68 years", "69 years", "70 years"};
    List<String> convertedExpList = Arrays.asList(experience);
    private ArrayAdapter<String> dataAdapter;
    private Realm realm;
    private RealmManager realmManager;
    private int login_doc_id;
    private PlacesClient placesClient;
    private RequestForCurrentLocPlacesUsingPlaceId requestForCurrentLocPlacesUsingPlaceId;
    private RequestForPlacesUsingPlaceId requestForPlacesUsingPlaceId;
    private String api_key;
    private boolean isExactCity;
    private JSONObject cityJson;
    /* private TextView skip_text;
     private Button update_action;*/
    private BasicInfo basicInfoObj;
    private RealmBasicInfo realmBasicInfo;
    private RealmProfessionalInfo realmProfessionalInfo;
    private ProfessionalInfo realmProfessionInfo;
    private TextView cityErrorText, experienceErrorText;
    private ValidationUtils validationUtils;
    private ProgressDialog mProgressDialog;
    private RelativeLayout getStartedButtonLayout;
    private SpecialityViewModel specialityViewModel;
    private AutoCompleteTextView specialityAutoCompleteTextView;
    private ArrayAdapter specialityArrayAdapter;
    private ArrayList specialityArrayList;
    private String itemSelected = "";
    protected MySharedPref mySharedPref = null;
    private int overallExperience;
    private RelativeLayout speciality_lay;
    private TextView speciality_error_text;
    private boolean isCitySelectedFromList;
    private Button clear_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(R.style.AppTheme_Dialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mandatory_page);
        this.setFinishOnTouchOutside(false);
        city_edit = (AutoCompleteTextView) findViewById(R.id.city_edit);
        experience_autocomplete_view = (AutoCompleteTextView) findViewById(R.id.experience_autocomplete_view);
        specialityAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.speciality_edit);
        speciality_error_text = (TextView) findViewById(R.id.speciality_error_text);
        clear_text = (Button) findViewById(R.id.clear_text);
        getStartedButtonLayout = (RelativeLayout) findViewById(R.id.getStartedButtonLayout);
        speciality_lay = (RelativeLayout) findViewById(R.id.speciality_lay);
        cityErrorText = (TextView) findViewById(R.id.city_error_text);
        experienceErrorText = (TextView) findViewById(R.id.experience_error_text);
        if (mySharedPref == null) {
            mySharedPref = new MySharedPref(this);
        }

        mProgressDialog = new ProgressDialog(LocationAndExperienceActivity.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));


        validationUtils = new ValidationUtils(LocationAndExperienceActivity.this,
                new EditText[]{city_edit, experience_autocomplete_view, specialityAutoCompleteTextView}, new TextView[]{cityErrorText, experienceErrorText, speciality_error_text}
        );


        marshMallowPermission = new MarshMallowPermission(this);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        login_doc_id = realmManager.getDoc_id(realm);
        city_edit.addTextChangedListener(this);
        specialityArrayList = new ArrayList<>();

        specialityViewModel = new ViewModelProvider(this).get(SpecialityViewModel.class);
        specialityViewModel.init();


        realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        realmProfessionalInfo = new RealmProfessionalInfo();
        basicInfoObj = new BasicInfo();

        if (realmBasicInfo.getSplty().isEmpty() || realmBasicInfo.getSplty().equalsIgnoreCase("null")) {
            speciality_lay.setVisibility(View.VISIBLE);
        } else {
            speciality_lay.setVisibility(View.GONE);
        }


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

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city_edit.setText("");
            }
        });

        // Display a Toast Message when the user clicks on an item in the AutoCompleteTextView
        specialityAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {
                itemSelected = arg0.getItemAtPosition(arg2).toString();
            }
        });

        realmProfessionInfo = realmManager.getProfessionalInfoOfShowoncard(realm);


        String text = "We have enhanced WhiteCoats to provide feeds that are relevant per your locations and experience";

        SpannableString spannableString = new SpannableString(text);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#00A76D"));
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.parseColor("#00A76D"));
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(Color.parseColor("#00A76D"));

        spannableString.setSpan(foregroundColorSpan, 8, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpan1, 72, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpan2, 86, 96, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        dataAdapter = new ArrayAdapter<String>
                (this, R.layout.textview_item, experience);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experience_autocomplete_view.setThreshold(1);
        experience_autocomplete_view.setAdapter(dataAdapter);
        city_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    isExactCity = false;
                } else {
                    isExactCity = true;
                }
            }
        });


        new AwsAndGoogleKeysServiceClass(LocationAndExperienceActivity.this, login_doc_id, basicInfoObj.getUserUUID(), new AwsAndGoogleKey() {
            @Override
            public void awsAndGoogleKey(String google_api_key, String aws_key) {
                api_key = google_api_key;
                Places.initialize(getApplicationContext(), api_key);
                placesClient = Places.createClient(LocationAndExperienceActivity.this);


                List<Prediction> predictions = new ArrayList<>();
                PlacesAutoCompleteAdapter placesAutoCompleteAdapter = new PlacesAutoCompleteAdapter(getApplicationContext(), predictions, false, api_key, login_doc_id);

                city_edit.setThreshold(1);
                city_edit.setAdapter(placesAutoCompleteAdapter);
                city_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Prediction prediction = predictions.get(position);
                        if (prediction.getId() != null && prediction.getId().equals("-1")) {
                            if (prediction.getDescription().equalsIgnoreCase("Use Current Location")) {
                                city_edit.setText("");
                            }
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("screen", "locationUpdate");
                                jsonObject.put("type", "city");
                                AppUtil.logUserActionEvent(login_doc_id, "CurrentLocationTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), LocationAndExperienceActivity.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            isExactCity = true;
                            isCitySelectedFromList = true;
                            if (!marshMallowPermission.requestPermissionForLocation()) {
                                if (AppConstants.neverAskAgain_Location) {
                                    AppUtil.showLocationServiceDenyAlert(LocationAndExperienceActivity.this);
                                }
                            } else {
                                // getLocationUsingAPI();

                                locationHelperObj = new LocationHelperClass(LocationAndExperienceActivity.this, new LocationCaputerListner() {
                                    @Override
                                    public void onLocationCapture(Location location) {
                                        if (location != null) {
                                            double lat = location.getLatitude();
                                            double longi = location.getLongitude();

                                            requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(LocationAndExperienceActivity.this, lat, longi, api_key, new OnReceiveResponse() {
                                                @Override
                                                public void onSuccessResponse(String successResponse) {

                                                    try {
                                                        JSONObject jsonObject = new JSONObject(successResponse);
                                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                            AppUtil.getAddressDetailsUsingAPI(LocationAndExperienceActivity.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                                @Override
                                                                public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject jsonObject) {
                                                                    if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                                        if (apiStringHashMap.containsKey("city")) {
                                                                            city_edit.setText(apiStringHashMap.get("city"));
                                                                            city_edit.setSelection(city_edit.length());
                                                                            city_edit.dismissDropDown();
                                                                        }
                                                                        cityJson = jsonObject;
                                                                        isCitySelectedFromList = true;
                                                                    } else {
                                                                        cityJson = null;
                                                                        String errorMsg = "Unable to fetch Geocode Details";
                                                                        if (jsonObject.has("error_message")) {
                                                                            errorMsg = jsonObject.optString("error_message");
                                                                        }
                                                                        JSONObject errorObj = new JSONObject();
                                                                        try {
                                                                            errorObj.put("screen", "LocationUpdate");
                                                                            errorObj.put("searchKey", city_edit.getText().toString());
                                                                            errorObj.put("errorMsg", errorMsg);
                                                                            AppUtil.logUserActionEvent(login_doc_id, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                }

                                                                @Override
                                                                public void onLocationApiError(String error) {

                                                                    JSONObject errorObj = new JSONObject();
                                                                    try {
                                                                        errorObj.put("screen", "LocationUpdate");
                                                                        errorObj.put("searchKey", city_edit.getText().toString());
                                                                        errorObj.put("errorMsg", error);
                                                                        AppUtil.logUserActionEvent(login_doc_id, "FetchCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            });

                                                        } else {
                                                            cityJson = null;
                                                            String errorMsg = "Unable to fetch User current location";
                                                            if (jsonObject.has("error_message")) {
                                                                errorMsg = jsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj = new JSONObject();
                                                            try {
                                                                errorObj.put("screen", "LocationUpdate");
                                                                errorObj.put("searchKey", city_edit.getText().toString());
                                                                errorObj.put("errorMsg", errorMsg);
                                                                AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                }

                                                @Override
                                                public void onErrorResponse(String errorResponse) {
                                                    String error = errorResponse;
                                                    JSONObject errorObj = new JSONObject();
                                                    try {
                                                        errorObj.put("screen", "LocationUpdate");
                                                        errorObj.put("searchKey", city_edit.getText().toString());
                                                        errorObj.put("errorMsg", errorResponse);
                                                        AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            });
                                        }
                                    }
                                });

                            }
                            return;
                        } else {
                            isExactCity = false;
                            requestForPlacesUsingPlaceId = new RequestForPlacesUsingPlaceId(LocationAndExperienceActivity.this, prediction.getPlaceId(), api_key, new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(successResponse);
                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                            if (jsonObject.has("result")) {
                                                JSONObject resultObj = jsonObject.optJSONObject("result");
                                                JSONArray addressComponents = resultObj.optJSONArray("address_components");
                                                for (int i = 0; i < addressComponents.length(); i++) {
                                                    JSONObject addressComponent = addressComponents.optJSONObject(i);
                                                    JSONArray types = addressComponent.optJSONArray("types");
                                                    for (int j = 0; j < types.length(); j++) {
                                                        if (types.optString(j).equalsIgnoreCase("locality") || types.optString(j).equalsIgnoreCase("administrative_area_level_3")) {
                                                            city_edit.setText(addressComponent.optString("long_name"));
                                                            city_edit.setSelection(city_edit.length());
                                                            city_edit.dismissDropDown();
                                                        }
                                                    }
                                                }
                                            }
                                            cityJson = jsonObject;
                                            isCitySelectedFromList = true;
                                        } else {
                                            cityJson = null;
                                            String errorMsg = "Unable to fetch User current location";
                                            if (jsonObject.has("error_message")) {
                                                errorMsg = jsonObject.optString("error_message");
                                            }
                                            JSONObject errorObj = new JSONObject();
                                            try {
                                                errorObj.put("screen", "LocationUpdate");
                                                errorObj.put("searchKey", city_edit.getText().toString());
                                                errorObj.put("errorMsg", errorMsg);
                                                AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onErrorResponse(String errorResponse) {
                                    JSONObject errorObj = new JSONObject();
                                    try {
                                        errorObj.put("screen", "LocationUpdate");
                                        errorObj.put("searchKey", city_edit.getText().toString());
                                        errorObj.put("errorMsg", errorResponse);
                                        AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }


                    }
                });
            }
        });

        getStartedButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtil.isConnectingToInternet(LocationAndExperienceActivity.this)) {
                    String cityValue = city_edit.getText().toString();
                    String experienceValue = experience_autocomplete_view.getText().toString();
                    String speciality = specialityAutoCompleteTextView.getText().toString();
                    boolean isMandatory;
                    if (realmBasicInfo.getSplty().isEmpty() || realmBasicInfo.getSplty().equalsIgnoreCase("null")) {
                        isMandatory = true;
                    } else {
                        isMandatory = false;
                    }
                    if (validationUtils.isMandatoryEntered(cityValue, experienceValue, speciality, isMandatory)) {

                        if (isMandatory) {
                            if (!specialityArrayList.contains(specialityAutoCompleteTextView.getText().toString())) {
                                speciality_error_text.setVisibility(View.VISIBLE);
                                speciality_error_text.setText("Please enter valid speciality");
                                return;
                            }
                        }

                        if (!convertedExpList.contains(experienceValue)) {
                            Toast.makeText(LocationAndExperienceActivity.this, "Please select your relevant experience", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!isCitySelectedFromList) {
                            Toast.makeText(LocationAndExperienceActivity.this, "Please select relevant city of residence", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        overallExperience = 999;
                        String experienceSplitedString = null;
                        String overAllExperience = experience_autocomplete_view.getText().toString().trim();
                        String[] splited = overAllExperience.split(" ");
                        if (splited.length > 0) {
                            experienceSplitedString = splited[0];
                        }
                        if (experienceSplitedString != null) {
                            if (experienceSplitedString.contains("<")) {
                                //String strNew = experienceSplitedString.replace("<", "");
                                overallExperience = 0;
                            } else {
                                overallExperience = Integer.parseInt(experienceSplitedString);
                            }

                        }


                        JSONObject object = new JSONObject();
                        String subSpeciality = basicInfoObj.getSubSpeciality();
                        if (subSpeciality == null)
                            subSpeciality = "";
                        try {
                            object.put(RestUtils.TAG_DOC_ID, login_doc_id);
                            object.put("experience", overallExperience);

                            object.put(RestUtils.TAG_LOCATION, city_edit.getText().toString());
                            if (cityJson == null) {
                                object.put("user_google_city_info", new JSONObject());
                            } else {
                                object.put("user_google_city_info", cityJson);
                            }
                            object.put("is_exact_city", isExactCity);
                            String speciality_text;
                            if (realmBasicInfo.getSplty().isEmpty() || realmBasicInfo.getSplty().equalsIgnoreCase("null")) {
                                speciality_text = specialityAutoCompleteTextView.getText().toString();
                            } else {
                                speciality_text = realmBasicInfo.getSplty();
                            }
                            object.put("speciality", speciality_text);
                            showProgress();
                            new VolleySinglePartStringRequest(LocationAndExperienceActivity.this, Request.Method.POST, RestApiConstants.NEW_UPDATE_MANDATORY_DATA, object.toString(), "BASIC_PROFILE_ACIVITY", new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    hideProgress();
                                    cityJson = new JSONObject();
                                    if (successResponse != null) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(successResponse);

                                            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                mySharedPref.getPrefsHelper().savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_REMEMBER_ME, false));
                                                mySharedPref.getPrefsHelper().savePref(mySharedPref.PREF_MANDATORY_PROFILE_CHECK, true);
                                                mySharedPref.savePref("is_user_active", true);
                                                JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                                                JSONArray socialInfoArray = new JSONArray();
                                                socialInfoArray = data.optJSONArray(RestUtils.TAG_SOCIAL_INFO);
                                                BasicInfo basicInfo = new BasicInfo();
                                                if (data.has(RestUtils.TAG_PROFILE_PIC_NAME)) {
                                                    basicInfo.setPic_name(data.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                                }
                                                if (data.has(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL)) {
                                                    basicInfo.setPic_url(data.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                                }
                                                basicInfo.setUserSalutation(realmBasicInfo.getUser_salutation());
                                                basicInfo.setUserType(realmBasicInfo.getUser_type_id());
                                                basicInfo.setProfile_pic_path("");
                                                basicInfo.setFname(realmBasicInfo.getFname());
                                                basicInfo.setLname(realmBasicInfo.getLname());
                                                basicInfo.setAbout_me(realmBasicInfo.getAbout_me());
                                                basicInfo.setSpecificAsk(realmBasicInfo.getSpecificAsk());
                                                basicInfo.setDocProfilePdfURL(realmBasicInfo.getDocProfilePdfURL());
                                                basicInfo.setOverAllExperience(overallExperience);
                                                basicInfo.setSplty(speciality_text);
                                                basicInfo.setSubSpeciality(realmBasicInfo.getSubSpeciality());

                                                realmManager.updateBasicInfo(realm, basicInfo);
                                                ProfessionalInfo professionalInfo = new ProfessionalInfo();
                                                professionalInfo.setProf_id(realmProfessionInfo.getProf_id());
                                                professionalInfo.setWorkplace(realmProfessionInfo.getWorkplace());
                                                professionalInfo.setDesignation(realmProfessionalInfo.getDesignation());
                                                professionalInfo.setLocation(city_edit.getText().toString());
                                                professionalInfo.setStart_date(realmProfessionInfo.getStart_date());
                                                professionalInfo.setEnd_date(realmProfessionInfo.getEnd_date());
                                                professionalInfo.setShowOncard(realmProfessionInfo.isShowOncard());
                                                professionalInfo.setWorking_here(realmProfessionInfo.isWorking_here());
                                                realmManager.updateProfessionInfo(realm, professionalInfo);
                                                AppUtil.subscribeDeviceForNotifications(LocationAndExperienceActivity.this, realmManager.getDoc_id(realm), "FCM", true, mySharedPref.getPref(MyFcmListenerService.PROPERTY_REG_ID, ""));
                                                Intent intent = new Intent();
                                                setResult(7777, intent);
                                                finish();

                                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                                //displayErrorScreen(successResponse);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }

                                @Override
                                public void onErrorResponse(String errorResponse) {
                                    hideProgress();
                                    cityJson = new JSONObject();
                                    Toast.makeText(LocationAndExperienceActivity.this, errorResponse, Toast.LENGTH_SHORT).show();

                                }
                            }).sendSinglePartRequest();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.LOCATION_PERMISSION_REQUEST_CODE:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }
                boolean location = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("screen", "locationUpdate");
                        jsonObject.put("type", "city");
                        AppUtil.logUserActionEvent(login_doc_id, "LocationPermissionAccepted", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), LocationAndExperienceActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // showCurrentLocation();
                    // getLocationUsingAPI();

                    locationHelperObj = new LocationHelperClass(LocationAndExperienceActivity.this, new LocationCaputerListner() {
                        @Override
                        public void onLocationCapture(Location location) {
                            if (location != null) {
                                double lat = location.getLatitude();
                                double longi = location.getLongitude();

                                requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(LocationAndExperienceActivity.this, lat, longi, api_key, new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {

                                        try {
                                            JSONObject responseObject = new JSONObject(successResponse);
                                            if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                AppUtil.getAddressDetailsUsingAPI(LocationAndExperienceActivity.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                    @Override
                                                    public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject jsonObject) {
                                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                            if (apiStringHashMap.containsKey("city")) {
                                                                city_edit.setText(apiStringHashMap.get("city"));
                                                                city_edit.setSelection(city_edit.length());
                                                                city_edit.dismissDropDown();
                                                            }
                                                            cityJson = jsonObject;
                                                            isCitySelectedFromList = true;

                                                        } else {
                                                            cityJson = null;
                                                            String errorMsg = "Unable to fetch Geocode Details";
                                                            if (jsonObject.has("error_message")) {
                                                                errorMsg = jsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj = new JSONObject();
                                                            try {
                                                                errorObj.put("screen", "LocationUpdate");
                                                                errorObj.put("searchKey", city_edit.getText().toString());
                                                                errorObj.put("errorMsg", errorMsg);
                                                                AppUtil.logUserActionEvent(login_doc_id, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }

                                                    }

                                                    @Override
                                                    public void onLocationApiError(String error) {
                                                        JSONObject errorObj = new JSONObject();
                                                        try {
                                                            errorObj.put("screen", "LocationUpdate");
                                                            errorObj.put("searchKey", city_edit.getText().toString());
                                                            errorObj.put("errorMsg", error);
                                                            AppUtil.logUserActionEvent(login_doc_id, "FetchCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                });
                                            } else {
                                                cityJson = null;
                                                String errorMsg = "Unable to fetch User current location";
                                                if (responseObject.has("error_message")) {
                                                    errorMsg = responseObject.optString("error_message");
                                                }
                                                JSONObject errorObj = new JSONObject();
                                                try {
                                                    errorObj.put("screen", "LocationUpdate");
                                                    errorObj.put("searchKey", city_edit.getText().toString());
                                                    errorObj.put("errorMsg", errorMsg);
                                                    AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                    @Override
                                    public void onErrorResponse(String errorResponse) {
                                        String error = errorResponse;
                                        JSONObject errorObj = new JSONObject();
                                        try {
                                            errorObj.put("screen", "LocationUpdate");
                                            errorObj.put("searchKey", city_edit.getText().toString());
                                            errorObj.put("errorMsg", errorResponse);
                                            AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    });

                }
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("screen", "locationUpdate");
                        jsonObject.put("type", "city");
                        AppUtil.logUserActionEvent(login_doc_id, "LocationPermissionRejected", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), LocationAndExperienceActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!location) {
                        AppConstants.neverAskAgain_Location = true;
                    }
                }
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    AppConstants.neverAskAgain_Library = true;
                }
                if (!location) {
                    AppConstants.neverAskAgain_Location = true;
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                //Do something after 100ms
                //getLocation();
                if (locationHelperObj != null) {
                    locationHelperObj.getLocation(new LocationCaputerListner() {
                        @Override
                        public void onLocationCapture(Location location) {
                            Location _location = location;
                            if (_location != null) {
                                double lat = _location.getLatitude();
                                double longi = _location.getLongitude();
                                requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(LocationAndExperienceActivity.this, lat, longi, api_key, new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        //
                                        try {
                                            JSONObject responseObject = new JSONObject(successResponse);
                                            if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                AppUtil.getAddressDetailsUsingAPI(LocationAndExperienceActivity.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                    @Override
                                                    public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject jsonObject) {
                                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                            if (apiStringHashMap.containsKey("city")) {
                                                                city_edit.setText(apiStringHashMap.get("city"));
                                                                city_edit.setSelection(city_edit.length());
                                                                city_edit.dismissDropDown();
                                                            }
                                                            cityJson = jsonObject;
                                                            isExactCity = true;
                                                            isCitySelectedFromList = true;
                                                        } else {
                                                            cityJson = null;
                                                            String errorMsg = "Unable to fetch Geocode Details";
                                                            if (jsonObject.has("error_message")) {
                                                                errorMsg = jsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj = new JSONObject();
                                                            try {
                                                                errorObj.put("screen", "LocationUpdate");
                                                                errorObj.put("searchKey", city_edit.getText().toString());
                                                                errorObj.put("errorMsg", errorMsg);
                                                                AppUtil.logUserActionEvent(login_doc_id, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onLocationApiError(String error) {
                                                        JSONObject errorObj = new JSONObject();
                                                        try {
                                                            errorObj.put("screen", "LocationUpdate");
                                                            errorObj.put("searchKey", city_edit.getText().toString());
                                                            errorObj.put("errorMsg", error);
                                                            AppUtil.logUserActionEvent(login_doc_id, "FetchCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                });

                                            } else {
                                                cityJson = null;
                                                String errorMsg = "Unable to fetch User current location";
                                                if (responseObject.has("error_message")) {
                                                    errorMsg = responseObject.optString("error_message");
                                                }
                                                JSONObject errorObj = new JSONObject();
                                                try {
                                                    errorObj.put("screen", "LocationUpdate");
                                                    errorObj.put("searchKey", city_edit.getText().toString());
                                                    errorObj.put("errorMsg", errorMsg);
                                                    AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onErrorResponse(String errorResponse) {
                                        String error = errorResponse;
                                        JSONObject errorObj = new JSONObject();
                                        try {
                                            errorObj.put("screen", "LocationUpdate");
                                            errorObj.put("searchKey", city_edit.getText().toString());
                                            errorObj.put("errorMsg", errorResponse);
                                            AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), LocationAndExperienceActivity.this);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(LocationAndExperienceActivity.this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        }
    }


    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public synchronized void showProgress() {
        //if (progress == null && progress.getActivity() == null) {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isExactCity) {
            cityJson = new JSONObject();
        }
        isCitySelectedFromList = false;
        if (s.length() > 0) {
            clear_text.setVisibility(View.VISIBLE);
        } else {
            clear_text.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void getSpeciality() {
        specialityViewModel.getSpeciality(LocationAndExperienceActivity.this).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("status") == 200) {
                        JSONArray specialitiesArray = jsonObject.getJSONObject("response").getJSONObject("data").getJSONArray("specialities");

                        specialityArrayList.clear();
                        for (int i = 0; i < specialitiesArray.length(); i++) {
                            String specialityString = specialitiesArray.get(i).toString();
                            specialityArrayList.add(specialityString);
                        }
                        specialityArrayAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(LocationAndExperienceActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
