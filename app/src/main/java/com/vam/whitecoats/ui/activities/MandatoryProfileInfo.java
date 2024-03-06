package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.soundcloud.android.crop.Crop;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.Prediction;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.PlacesAutoCompleteAdapter;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.fragments.SpecialityDialogFragment;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.ui.interfaces.LocationCaputerListner;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnlocationApiFinishedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AwsAndGoogleKeysServiceClass;
import com.vam.whitecoats.utils.FetchUserConnects;
import com.vam.whitecoats.utils.LocationHelperClass;
import com.vam.whitecoats.utils.RequestForCurrentLocPlacesUsingPlaceId;
import com.vam.whitecoats.utils.RequestForPlacesUsingPlaceId;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleyMultipartStringRequest;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;

public class MandatoryProfileInfo extends BaseActionBarActivity implements SpecialityDialogFragment.NoticeDialogListener, LocationListener, TextWatcher {
    public static final String TAG = MandatoryProfileInfo.class.getSimpleName();
    EditText subSpecialityEditTxt, specialityTxtVw;
    AutoCompleteTextView workplace_edit;
    AutoCompleteTextView city_edit;
    TextView speciality_error_text, workplace_error_text, city_error_text, experience_error_text;
    ImageView profile_pic_img, prof_pic_selection;
    private Realm realm;
    private RealmManager realmManager;
    private BasicInfo basicInfo;
    private ProfessionalInfo professionalInfo;
    private File myDirectory;
    String selectedImagePath = "";
    private static final int SELECT_FILE = 1;
    private static final int CAMERA_PIC_REQUEST = 1313;

    final int CROP_PIC = 22;
    File folder;
    String speciality = "", workplace = "", city = "";
    String subSpeciality;
    private static Uri picUri;

    private ArrayList<String> cities_array = new ArrayList<>();
    private ArrayList<String> specialities_array = new ArrayList<>();
    ArrayAdapter<String> cities_adpt;

    MarshMallowPermission marshMallowPermission;
    private Button submit_action;
    private Bitmap myBitmap = null;

    private int login_doc_id = 0;
    private String emailId;
    private Uri mCapturedImageURI = null;
    private String firstName_value, lastName_value;
    private Geocoder mGeocoder;
    private LocationManager locationManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    String[] experience = {"0-1 year", "1 year", "2 years", "3 years", "4 years", "5 years", "6 years", "7 years", "8 years", "9 years", "10 years", "11 years", "12 years", "13 years", "14 years",
            "15 years", "16 years", "17 years", "18 years", "19 years", "20 years", "21 years", "22 years", "23 years", "24 years", "25 years", "26 years", "27 years", "28 years", "29 years", "30 years",
            "31 years", "32 years", "33 years", "34 years", "35 years", "36 years", "37 years", "38 years", "39 years", "40 years", "41 years", "42 years", "43 years", "44 years", "45 years", "46 years",
            "47 years", "48 years", "49 years", "50 years", "51 years", "52 years", "53 years", "54 years", "55 years", "56 years", "57 years", "58 years", "59 years", "60 years", "61 years", "62 years",
            "63 years", "64 years", "65 years", "66 years", "67 years", "68 years", "69 years", "70 years"};
    List<String> convertedExpList = Arrays.asList(experience);
    private AutoCompleteTextView experienceAutoCompleteView;

    private ArrayAdapter<String> dataAdapter;
    private LocationHelperClass locationHelperObj;
    private RequestForPlacesUsingPlaceId requestForPlacesUsingPlaceId;
    private RequestForCurrentLocPlacesUsingPlaceId requestForCurrentLocPlacesUsingPlaceId;
    private boolean isCityEditText = false;
    private String api_key;
    private PlacesClient placesClient;
    private String overAllExperience;
    private boolean isExactWorkplace, isExactCity;
    private JSONObject workPlaceJson;
    private JSONObject cityJson;
    private boolean isWorkEditText;
    private boolean customBackButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandatory_profile_info);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_registrations, null);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        createDirIfNotExists();
        initialize();
        mTitleTextView.setText("Profile Info");
        firstName_value = getIntent().getStringExtra("first_name");
        lastName_value = getIntent().getStringExtra("last_name");
        HashMap<String, Object> data = new HashMap<>();
        data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
        data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
        AppUtil.logUserEventWithHashMap("SignUp_DocInfo2_Impression", login_doc_id, data, MandatoryProfileInfo.this);
        validationUtils = new ValidationUtils(MandatoryProfileInfo.this,
                new EditText[]{workplace_edit, city_edit, specialityTxtVw},
                new TextView[]{speciality_error_text, workplace_error_text, city_error_text, experience_error_text});
        next_button.setText("2/3");

        mGeocoder = new Geocoder(this, Locale.getDefault());


        submit_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int overallExperience = 999;
                    String experienceSplitedString = null;
                    subSpeciality = subSpecialityEditTxt.getText().toString().trim();
                    workplace = workplace_edit.getText().toString().trim();
                    city = city_edit.getText().toString().trim();
                    overAllExperience = experienceAutoCompleteView.getText().toString().trim();
                    if (validationUtils.isValidMandatoryField(speciality, workplace, city, overAllExperience, false)) {
                        String[] splited = overAllExperience.split(" ");
                        if (splited.length > 0) {
                            experienceSplitedString = splited[0];
                        }
                        if (experienceSplitedString != null) {
                            if (experienceSplitedString.contains("-")) {
                                // String strNew = experienceSplitedString.replace("<", "");
                                overallExperience = 0;
                            } else {
                                overallExperience = Integer.parseInt(experienceSplitedString);
                            }
                        }
                        hideKeyboard();
                        HashMap<String, Object> data = new HashMap<>();
                        data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                        data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                        AppUtil.logUserEventWithHashMap("SignUp_Submit", login_doc_id, data, MandatoryProfileInfo.this);

                        if (!convertedExpList.contains(overAllExperience)) {
                            Toast.makeText(MandatoryProfileInfo.this, R.string.select_valid_experience, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (isConnectingToInternet()) {
                            basicInfo.setSplty(speciality);
                            basicInfo.setSubSpeciality(subSpeciality);
                            basicInfo.setProfile_pic_path(selectedImagePath);
                            basicInfo.setOverAllExperience(overallExperience);
                            basicInfo.setDoc_id(realmManager.getDoc_id(realm));
                            professionalInfo.setShowOncard(true);
                            professionalInfo.setLocation(city);
                            professionalInfo.setDesignation("");
                            professionalInfo.setEnd_date(0);
                            professionalInfo.setStartTime(0);
                            professionalInfo.setEndTime(0);
                            professionalInfo.setWorkOptions("");
                            professionalInfo.setAvailableDays("");
                            professionalInfo.setProf_id(1);
                            professionalInfo.setWorkplace(workplace);
                            professionalInfo.setWorking_here(true);
                            professionalInfo.setStart_date(0);

                            JSONObject object = new JSONObject();
                            object.put(RestUtils.TAG_DOC_ID, basicInfo.getDoc_id());
                            object.put(RestUtils.TAG_LOCATION, professionalInfo.getLocation());
                            object.put(RestUtils.TAG_SPLTY, basicInfo.getSplty());
                            object.put(RestUtils.TAG_SUB_SPLTY, basicInfo.getSubSpeciality());
                            object.put(RestUtils.TAG_WORKPLACE, professionalInfo.getWorkplace());
                            object.put("experience", basicInfo.getOverAllExperience());
                            if (workPlaceJson == null) {
                                object.put("user_google_places_info", new JSONObject());
                            } else {
                                object.put("user_google_places_info", workPlaceJson);
                            }
                            if (cityJson == null) {
                                object.put("user_google_city_info", new JSONObject());
                            } else {
                                object.put("user_google_city_info", cityJson);
                            }
                            object.put("is_exact_location", isExactWorkplace);
                            object.put("is_exact_city", isExactCity);
                            if (!basicInfo.getProfile_pic_path().trim().equals("")) {
                                File imgFile = new File(basicInfo.getProfile_pic_path());
                                if (imgFile.exists()) {
                                    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                }
                            }
                            showProgress();
                            new VolleyMultipartStringRequest(MandatoryProfileInfo.this, Request.Method.POST, RestApiConstants.UPDATE_MANDATORY_DATA, object.toString(), "MANDATORY_DATA", new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    //hideProgress();
                                    workPlaceJson = null;
                                    cityJson = null;
                                    onTaskCompleted(successResponse);
                                    AppUtil.logFlurryEventWithDocIdAndEmailEvent("MANDATORYPROFILE_SUCCESS", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
                                }

                                @Override
                                public void onErrorResponse(String errorResponse) {
                                    hideProgress();
                                    workPlaceJson = null;
                                    cityJson = null;
                                    AppUtil.logFlurryEventWithDocIdAndEmailEvent("MANDATORYPROFILE_ERROR", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
                                    displayErrorScreen(errorResponse);
                                }
                            }).sendMultipartRequest(myBitmap, "file", "profilepic.png");

                        }
                    }
                } catch (Exception e) {
                    hideProgress();
                }
            }
        });
        specialityTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                AppUtil.logUserEventWithHashMap("SignUp_Speciality", login_doc_id, data, MandatoryProfileInfo.this);

                CharSequence[] specialities = specialities_array.toArray(new CharSequence[specialities_array.size()]);
                SpecialityDialogFragment specialityDialogFragment = SpecialityDialogFragment.newInstance(specialities, "mandatoryspecialities");
                specialityDialogFragment.show(getSupportFragmentManager(), SpecialityDialogFragment.TAG);
            }
        });

        prof_pic_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                AppUtil.logUserEventWithHashMap("SignUp_ProfilePic", login_doc_id, data, MandatoryProfileInfo.this);
                selectImage();
            }
        });

        subSpecialityEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                AppUtil.logUserEventWithHashMap("SignUp_SubSpeciality", login_doc_id, data, MandatoryProfileInfo.this);
            }
        });

        workplace_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    isWorkEditText = false;
                } else {
                    isWorkEditText = true;
                }
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                AppUtil.logUserEventWithHashMap("SignUp_Worklocation", login_doc_id, data, MandatoryProfileInfo.this);
            }
        });
        city_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                AppUtil.logUserEventWithHashMap("SignUp_City", login_doc_id, data, MandatoryProfileInfo.this);
                if (!b) {
                    isCityEditText = false;
                } else {
                    isCityEditText = true;
                }
            }
        });


        new AwsAndGoogleKeysServiceClass(MandatoryProfileInfo.this, login_doc_id, basicInfo.getUserUUID(), new AwsAndGoogleKey() {
            @Override
            public void awsAndGoogleKey(String google_api_key, String aws_key) {
                api_key = google_api_key;
                Places.initialize(getApplicationContext(), api_key);
                placesClient = Places.createClient(MandatoryProfileInfo.this);

                List<Prediction> predictions = new ArrayList<>();
                PlacesAutoCompleteAdapter placesAutoCompleteAdapter = new PlacesAutoCompleteAdapter(getApplicationContext(), predictions, false, api_key, login_doc_id);
                workplace_edit.setThreshold(1);
                workplace_edit.setAdapter(placesAutoCompleteAdapter);
                workplace_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Prediction prediction = predictions.get(position);
                        if (prediction.getId() != null && prediction.getId().equals("-1")) {
                            if (prediction.getDescription().equalsIgnoreCase("Use Current Location")) {
                                workplace_edit.setText("");
                            }

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("screen", "signUpUser");
                                jsonObject.put("type", "workplace");
                                AppUtil.logUserActionEvent(login_doc_id, "CurrentLocationTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), MandatoryProfileInfo.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            isExactWorkplace = true;
                            if (!marshMallowPermission.requestPermissionForLocation()) {
                                if (AppConstants.neverAskAgain_Location) {
                                    AppUtil.showLocationServiceDenyAlert(MandatoryProfileInfo.this);
                                }
                            } else {
                                // getLocationUsingAPI();

                                locationHelperObj = new LocationHelperClass(MandatoryProfileInfo.this, new LocationCaputerListner() {
                                    @Override
                                    public void onLocationCapture(Location location) {
                                        if (location != null) {
                                            double lat = location.getLatitude();
                                            double longi = location.getLongitude();

                                            requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(MandatoryProfileInfo.this, lat, longi, api_key, new OnReceiveResponse() {
                                                @Override
                                                public void onSuccessResponse(String successResponse) {

                                                    JSONObject responseObj = null;
                                                    try {
                                                        responseObj = new JSONObject(successResponse);
                                                        if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                            AppUtil.getAddressDetailsUsingAPI(MandatoryProfileInfo.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                                @Override
                                                                public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject currentLocJsonObject) {

                                                                    if (currentLocJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                                        if (!isCityEditText) {
                                                                            if (apiStringHashMap.containsKey("workplace")) {
                                                                                workplace_edit.setText(apiStringHashMap.get("workplace"));
                                                                                workplace_edit.setSelection(workplace_edit.length());
                                                                                workplace_edit.dismissDropDown();
                                                                            }
                                                                        }
                                                                        if (apiStringHashMap.containsKey("city")) {
                                                                            city_edit.setText(apiStringHashMap.get("city"));
                                                                            city_edit.setSelection(city_edit.length());
                                                                            city_edit.dismissDropDown();
                                                                        }

                                                                        if (isExactWorkplace) {
                                                                            isExactCity = true;
                                                                        } else {
                                                                            isExactCity = false;
                                                                        }
                                                                        workPlaceJson = currentLocJsonObject;
                                                                        cityJson = currentLocJsonObject;
                                                                    } else {
                                                                        workPlaceJson = null;
                                                                        cityJson = null;
                                                                        String errorMsg = "Unable to fetch Geocode Details";
                                                                        if (currentLocJsonObject.has("error_message")) {
                                                                            errorMsg = currentLocJsonObject.optString("error_message");
                                                                        }
                                                                        JSONObject errorObj = new JSONObject();
                                                                        try {
                                                                            errorObj.put("screen", "SignUpUser");
                                                                            errorObj.put("searchKey", workplace_edit.getText().toString());
                                                                            errorObj.put("errorMsg", errorMsg);
                                                                            AppUtil.logUserActionEvent(login_doc_id, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                    }

                                                                }

                                                                @Override
                                                                public void onLocationApiError(String error) {
                                                                    JSONObject errorObj = new JSONObject();
                                                                    try {
                                                                        errorObj.put("screen", "SignUpUser");
                                                                        errorObj.put("searchKey", workplace_edit.getText().toString());
                                                                        errorObj.put("errorMsg", error);
                                                                        AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            workPlaceJson = null;
                                                            cityJson = null;
                                                            String errorMsg = "Unable to fetch User current location";
                                                            if (responseObj.has("error_message")) {
                                                                errorMsg = responseObj.optString("error_message");
                                                            }
                                                            JSONObject errorObj = new JSONObject();
                                                            try {
                                                                errorObj.put("screen", "SignUpUser");
                                                                errorObj.put("searchKey", workplace_edit.getText().toString());
                                                                errorObj.put("errorMsg", errorMsg);
                                                                AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
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
                                                        errorObj.put("screen", "SignUpUser");
                                                        errorObj.put("searchKey", workplace_edit.getText().toString());
                                                        errorObj.put("errorMsg", errorResponse);
                                                        AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
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
                            isExactWorkplace = false;
                            isExactCity = false;
                            if (prediction.getTerms() != null) {
                                if (prediction.getTerms().size() > 1) {
                                    workplace_edit.setText(prediction.getTerms().get(0).getValue() + "," + prediction.getTerms().get(1).getValue());
                                    workplace_edit.setSelection(workplace_edit.length());
                                    workplace_edit.dismissDropDown();
                                } else if (prediction.getTerms().size() > 0) {
                                    workplace_edit.setText(prediction.getTerms().get(0).getValue());
                                    workplace_edit.setSelection(workplace_edit.length());
                                    workplace_edit.dismissDropDown();
                                }
                            }

                            requestForPlacesUsingPlaceId = new RequestForPlacesUsingPlaceId(MandatoryProfileInfo.this, prediction.getPlaceId(), api_key, new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    String success = successResponse;

                                    try {
                                        JSONObject placeIdJsonObject = new JSONObject(successResponse);
                                        if (placeIdJsonObject.getString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                            if (placeIdJsonObject != null && placeIdJsonObject.has("result")) {
                                                JSONObject resultObj = placeIdJsonObject.optJSONObject("result");
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
                                            workPlaceJson = placeIdJsonObject;
                                            cityJson = placeIdJsonObject;
                                        } else {
                                            workPlaceJson = null;
                                            cityJson = null;
                                            String errorMsg = "Unable to fetch place details";
                                            if (placeIdJsonObject.has("error_message")) {
                                                errorMsg = placeIdJsonObject.optString("error_message");
                                            }
                                            JSONObject errorObj = new JSONObject();
                                            try {
                                                errorObj.put("screen", "SignUpUser");
                                                errorObj.put("searchKey", workplace_edit.getText().toString());
                                                errorObj.put("errorMsg", errorMsg);
                                                AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
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
                                        errorObj.put("screen", "SignUpUser");
                                        errorObj.put("searchKey", workplace_edit.getText().toString());
                                        errorObj.put("errorMsg", errorResponse);
                                        AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                    }
                });

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
                                jsonObject.put("screen", "signUpUser");
                                jsonObject.put("type", "city");
                                AppUtil.logUserActionEvent(login_doc_id, "CurrentLocationTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), MandatoryProfileInfo.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            isExactCity = true;
                            if (!marshMallowPermission.requestPermissionForLocation()) {
                                if (AppConstants.neverAskAgain_Location) {
                                    AppUtil.showLocationServiceDenyAlert(MandatoryProfileInfo.this);
                                }
                            } else {
                                // getLocationUsingAPI();

                                locationHelperObj = new LocationHelperClass(MandatoryProfileInfo.this, new LocationCaputerListner() {
                                    @Override
                                    public void onLocationCapture(Location location) {
                                        if (location != null) {
                                            double lat = location.getLatitude();
                                            double longi = location.getLongitude();

                                            requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(MandatoryProfileInfo.this, lat, longi, api_key, new OnReceiveResponse() {
                                                @Override
                                                public void onSuccessResponse(String successResponse) {

                                                    JSONObject responseObject = null;
                                                    try {
                                                        responseObject = new JSONObject(successResponse);
                                                        if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                            AppUtil.getAddressDetailsUsingAPI(MandatoryProfileInfo.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                                @Override
                                                                public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject currentLocJsonObject) {
                                                                    if (currentLocJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                                        if (!isCityEditText) {
                                                                            if (apiStringHashMap.containsKey("workplace")) {
                                                                                workplace_edit.setText(apiStringHashMap.get("workplace"));
                                                                                workplace_edit.setSelection(workplace_edit.length());
                                                                                workplace_edit.dismissDropDown();
                                                                            }
                                                                        }
                                                                        if (apiStringHashMap.containsKey("city")) {
                                                                            city_edit.setText(apiStringHashMap.get("city"));
                                                                            city_edit.setSelection(city_edit.length());
                                                                            city_edit.dismissDropDown();
                                                                        }
                                                                        cityJson = currentLocJsonObject;
                                                                    } else {
                                                                        cityJson = null;
                                                                        String errorMsg = "Unable to fetch Geocode Details";
                                                                        if (currentLocJsonObject.has("error_message")) {
                                                                            errorMsg = currentLocJsonObject.optString("error_message");
                                                                        }
                                                                        JSONObject errorObj = new JSONObject();
                                                                        try {
                                                                            errorObj.put("screen", "SignUpUser");
                                                                            errorObj.put("searchKey", workplace_edit.getText().toString());
                                                                            errorObj.put("errorMsg", errorMsg);
                                                                            AppUtil.logUserActionEvent(login_doc_id, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                }

                                                                @Override
                                                                public void onLocationApiError(String error) {
                                                                    JSONObject errorObj = new JSONObject();
                                                                    try {
                                                                        errorObj.put("screen", "SignUpUser");
                                                                        errorObj.put("searchKey", workplace_edit.getText().toString());
                                                                        errorObj.put("errorMsg", error);
                                                                        AppUtil.logUserActionEvent(login_doc_id, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
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
                                                                errorObj.put("screen", "SignUpUser");
                                                                errorObj.put("searchKey", workplace_edit.getText().toString());
                                                                errorObj.put("errorMsg", errorMsg);
                                                                AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
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
                                                        errorObj.put("screen", "SignUpUser");
                                                        errorObj.put("searchKey", workplace_edit.getText().toString());
                                                        errorObj.put("errorMsg", errorResponse);
                                                        AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
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

                            requestForPlacesUsingPlaceId = new RequestForPlacesUsingPlaceId(MandatoryProfileInfo.this, prediction.getPlaceId(), api_key, new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    String success = successResponse;
                                    try {
                                        JSONObject jsonObject = new JSONObject(successResponse);
                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                            if (jsonObject != null && jsonObject.has("result")) {
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
                                        } else {
                                            cityJson = null;
                                            String errorMsg = "Unable to fetch place details";
                                            if (jsonObject.has("error_message")) {
                                                errorMsg = jsonObject.optString("error_message");
                                            }
                                            JSONObject errorObj = new JSONObject();
                                            try {
                                                errorObj.put("screen", "SignUpUser");
                                                errorObj.put("searchKey", workplace_edit.getText().toString());
                                                errorObj.put("errorMsg", errorMsg);
                                                AppUtil.logUserActionEvent(login_doc_id, "FetchCitCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
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
                                        errorObj.put("screen", "SignUpUser");
                                        errorObj.put("searchKey", workplace_edit.getText().toString());
                                        errorObj.put("errorMsg", errorResponse);
                                        AppUtil.logUserActionEvent(login_doc_id, "FetchCitCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
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


        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
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

    private void createDirIfNotExists() {
        myDirectory = AppUtil.getExternalStoragePathFile(MandatoryProfileInfo.this, ".Whitecoats");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        folder = new File(myDirectory + "/Profile_Pic");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

    public void alertDialog_Message() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MandatoryProfileInfo.this);
        builder.setCancelable(false);
        builder.setMessage(AppUtil.alert_CameraPermissionDeny_Message());
//        builder.setMessage(getString(R.string.camera_deny_message));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void alertDialog_Message1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MandatoryProfileInfo.this);
        builder.setCancelable(false);
        builder.setMessage(AppUtil.alert_StoragePermissionDeny_Message());
//        builder.setMessage(getString(R.string.storage_deny_message));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void initialize() {
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        login_doc_id = realmManager.getDoc_id(realm);
        emailId = realmManager.getDoc_EmailId(realm);

        speciality_error_text = _findViewById(R.id.speciality_error_text);
        workplace_error_text = _findViewById(R.id.workplace_error_text);
        city_error_text = _findViewById(R.id.city_error_text);
        workplace_edit = _findViewById(R.id.cities_autocomplete_view);
        specialityTxtVw = _findViewById(R.id.specialityTxtVw);
        subSpecialityEditTxt = _findViewById(R.id.subSpecialityEditTxt);
        city_edit = _findViewById(R.id.city_edit);
        profile_pic_img = _findViewById(R.id.prof_pic_img);
        prof_pic_selection = _findViewById(R.id.prof_pic_selection);
        submit_action = _findViewById(R.id.profile_submit_action);
        experienceAutoCompleteView = (AutoCompleteTextView) findViewById(R.id.experience_autocomplete_view);
        experience_error_text = (TextView) findViewById(R.id.experience_error_text);
        basicInfo = new BasicInfo();
        professionalInfo = new ProfessionalInfo();
        ArrayList<String> searchkeys = new ArrayList<String>();
        searchkeys.add("cities");
        searchkeys.add("specialities");
        new AutoSuggestionsAsync(MandatoryProfileInfo.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
        cities_adpt = new ArrayAdapter<String>(MandatoryProfileInfo.this, android.R.layout.simple_list_item_1, cities_array);
        city_edit.setAdapter(cities_adpt);
        city_edit.setThreshold(1);


        workplace_edit.addTextChangedListener(this);
        city_edit.addTextChangedListener(this);

        dataAdapter = new ArrayAdapter<String>
                (this, R.layout.textview_item, experience);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experienceAutoCompleteView.setThreshold(1);
        experienceAutoCompleteView.setAdapter(dataAdapter);

        marshMallowPermission = new MarshMallowPermission(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        long interval = 20 * 1000L;
        locationRequest.setInterval(interval);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
            }

            ;
        };

    }


    private void cameraClick() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            String fileName = "profile" + formatter.format(now) + ".jpg";
            //String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
        }
    }

    private void chooseFromLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createDirIfNotExists();
                    cameraClick();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        AppConstants.neverAskAgain_Camera = true;
                    }
                }
                break;
            case PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createDirIfNotExists();
                    chooseFromLibrary();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    } else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                }
                break;


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
                    // showCurrentLocation();
                    // getLocationUsingAPI();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("screen", "SignUpUser");
                        if (isWorkEditText) {
                            jsonObject.put("type", "workPlace");
                        } else if (isCityEditText) {
                            jsonObject.put("type", "city");
                        }
                        AppUtil.logUserActionEvent(login_doc_id, "LocationPermissionAccepted", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), MandatoryProfileInfo.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    locationHelperObj = new LocationHelperClass(MandatoryProfileInfo.this, new LocationCaputerListner() {
                        @Override
                        public void onLocationCapture(Location location) {
                            if (location != null) {
                                double lat = location.getLatitude();
                                double longi = location.getLongitude();

                                requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(MandatoryProfileInfo.this, lat, longi, api_key, new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        try {
                                            JSONObject responseObject = new JSONObject(successResponse);
                                            if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                AppUtil.getAddressDetailsUsingAPI(MandatoryProfileInfo.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                    @Override
                                                    public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject jsonObject) {
                                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                            if (!isCityEditText) {
                                                                if (apiStringHashMap.containsKey("workplace")) {
                                                                    workplace_edit.setText(apiStringHashMap.get("workplace"));
                                                                    workplace_edit.setSelection(workplace_edit.length());
                                                                    workplace_edit.dismissDropDown();
                                                                }
                                                            }
                                                            if (apiStringHashMap.containsKey("city")) {
                                                                city_edit.setText(apiStringHashMap.get("city"));
                                                                city_edit.setSelection(city_edit.length());
                                                                city_edit.dismissDropDown();
                                                            }
                                                            if (isWorkEditText) {
                                                                workPlaceJson = jsonObject;
                                                                cityJson = jsonObject;
                                                            } else {
                                                                cityJson = jsonObject;
                                                            }
                                                        } else {
                                                            if (isWorkEditText) {
                                                                workPlaceJson = null;
                                                                cityJson = null;
                                                            } else {
                                                                cityJson = null;
                                                            }
                                                            String errorMsg = "Unable to fetch Geocode Details";
                                                            if (jsonObject.has("error_message")) {
                                                                errorMsg = jsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj = new JSONObject();
                                                            try {
                                                                errorObj.put("screen", "SignUpUser");
                                                                errorObj.put("searchKey", workplace_edit.getText().toString());
                                                                errorObj.put("errorMsg", errorMsg);
                                                                AppUtil.logUserActionEvent(login_doc_id, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onLocationApiError(String error) {
                                                        JSONObject errorObj = new JSONObject();
                                                        try {
                                                            errorObj.put("screen", "SignUpUser");
                                                            if (!isCityEditText) {
                                                                errorObj.put("searchKey", workplace_edit.getText().toString());
                                                            } else {
                                                                errorObj.put("searchKey", city_edit.getText().toString());
                                                            }
                                                            errorObj.put("errorMsg", error);
                                                            if (!isCityEditText) {
                                                                AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                                            } else {
                                                                AppUtil.logUserActionEvent(login_doc_id, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);

                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                            } else {
                                                String errorMsg = "Unable to fetch User current location";
                                                if (responseObject.has("error_message")) {
                                                    errorMsg = responseObject.optString("error_message");
                                                }
                                                JSONObject errorObj = new JSONObject();
                                                try {
                                                    errorObj.put("screen", "SignUpUser");
                                                    if (!isCityEditText) {
                                                        errorObj.put("searchKey", workplace_edit.getText().toString());
                                                    } else {
                                                        errorObj.put("searchKey", city_edit.getText().toString());
                                                    }
                                                    errorObj.put("errorMsg", errorMsg);
                                                    if (!isCityEditText) {
                                                        AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                                    } else {
                                                        AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);

                                                    }
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
                                            errorObj.put("screen", "SignUpUser");
                                            if (!isCityEditText) {
                                                errorObj.put("searchKey", workplace_edit.getText().toString());
                                            } else {
                                                errorObj.put("searchKey", city_edit.getText().toString());
                                            }
                                            errorObj.put("errorMsg", errorResponse);
                                            if (!isCityEditText) {
                                                AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                            } else {
                                                AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);

                                            }
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
                        jsonObject.put("screen", "SignUpUser");
                        if (isWorkEditText) {
                            jsonObject.put("type", "workPlace");
                        } else if (isCityEditText) {
                            jsonObject.put("type", "city");
                        }
                        AppUtil.logUserActionEvent(login_doc_id, "LocationPermissionRejected", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), MandatoryProfileInfo.this);
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

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Remove"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MandatoryProfileInfo.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    if (!marshMallowPermission.requestPermissionForCamera(false)) {
                        if (AppConstants.neverAskAgain_Camera) {
                            alertDialog_Message();
                        }
                    } else {
                        cameraClick();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    if (!marshMallowPermission.requestPermissionForStorage(false)) {
                        if (AppConstants.neverAskAgain_Library) {
                            alertDialog_Message1();
                        }
                    } else {
                        chooseFromLibrary();
                    }
                } else if (items[item].equals("Remove")) {
                    picUri = null;
                    selectedImagePath = "";
                    profile_pic_img.setImageResource(R.drawable.default_profilepic);
                }

            }
        });
        builder.show();
    }

    public String getPath(Uri uri, Activity activity) {
        try {
            String[] projection = {MediaStore.MediaColumns.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_PIC_REQUEST) {
                if (mCapturedImageURI != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    Date now = new Date();
                    final String fileName = "profile" + "_" + formatter.format(now) + ".jpg";
                    File f = new File(folder, fileName);
                    selectedImagePath = f.getAbsolutePath();
                    //selectedImagePath = getPath(mCapturedImageURI, MandatoryProfileInfo.this);
                    Crop.of(mCapturedImageURI, Uri.fromFile(f)).asSquare().start(this);
                    //performCrop();
                }

            } else if (requestCode == CROP_PIC) {
                try {
                    Bundle extras = data.getExtras();
                    Bitmap thePic = extras.getParcelable("data");
                    profile_pic_img.setImageBitmap(thePic);
                } catch (Exception e) {
                    try {
                        picUri = data.getData();
                        selectedImagePath = getPath(picUri, MandatoryProfileInfo.this);
                        String s1 = data.getDataString();
                        if (selectedImagePath == null && s1 != null) {
                            selectedImagePath = s1.replaceAll("file://", "");
                        }
                        if (selectedImagePath != null) {
                            File imgFiles = new File(selectedImagePath);
                            if (imgFiles.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFiles.getAbsolutePath());
                                profile_pic_img.setImageBitmap(myBitmap);
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE) {
                picUri = data.getData();
                //selectedImagePath = getPath(picUri, MandatoryProfileInfo.this);
                performCrop();

            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            } else if (requestCode == 1000) {
                if (locationHelperObj != null) {
                    locationHelperObj.getLocation(new LocationCaputerListner() {
                        @Override
                        public void onLocationCapture(Location location) {
                            processLocation(location);
                        }
                    });

                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == CAMERA_PIC_REQUEST) {
                if (mCapturedImageURI != null) {
                    getContentResolver().delete(mCapturedImageURI, null, null);
                }
            }
        }
    }

    private void processLocation(Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double longi = location.getLongitude();


            requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(MandatoryProfileInfo.this, lat, longi, api_key, new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    //
                    JSONObject responseJsonObject = null;
                    try {
                        responseJsonObject = new JSONObject(successResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (responseJsonObject != null) {
                        if (responseJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                            AppUtil.getAddressDetailsUsingAPI(MandatoryProfileInfo.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                @Override
                                public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject jsonObject) {
                                    if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                        if (!isCityEditText) {
                                            if (apiStringHashMap.containsKey("workplace")) {
                                                workplace_edit.setText(apiStringHashMap.get("workplace"));
                                                workplace_edit.setSelection(workplace_edit.length());
                                                workplace_edit.dismissDropDown();
                                            }
                                        }
                                        if (apiStringHashMap.containsKey("city")) {
                                            city_edit.setText(apiStringHashMap.get("city"));
                                            city_edit.setSelection(city_edit.length());
                                            city_edit.dismissDropDown();
                                        }
                                        cityJson = jsonObject;
                                        workPlaceJson = jsonObject;
                                        isExactCity = true;
                                    } else {
                                        cityJson = null;
                                        workPlaceJson = null;
                                        String errorMsg = "Unable to fetch Geocode Details";
                                        if (jsonObject.has("error_message")) {
                                            errorMsg = jsonObject.optString("error_message");
                                        }
                                        JSONObject errorObj = new JSONObject();
                                        try {
                                            errorObj.put("screen", "SignUpUser");
                                            errorObj.put("searchKey", workplace_edit.getText().toString());
                                            errorObj.put("errorMsg", errorMsg);
                                            AppUtil.logUserActionEvent(login_doc_id, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }

                                @Override
                                public void onLocationApiError(String error) {
                                    JSONObject errorObj = new JSONObject();
                                    try {
                                        errorObj.put("screen", "SignUpUser");
                                        if (!isCityEditText) {
                                            errorObj.put("searchKey", workplace_edit.getText().toString());
                                        } else {
                                            errorObj.put("searchKey", city_edit.getText().toString());
                                        }
                                        errorObj.put("errorMsg", error);
                                        if (!isCityEditText) {
                                            AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                        } else {
                                            AppUtil.logUserActionEvent(login_doc_id, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        } else {
                            workPlaceJson = null;
                            cityJson = null;
                            String errorMsg = "Unable to fetch User current location";
                            if (responseJsonObject.has("error_message")) {
                                errorMsg = responseJsonObject.optString("error_message");
                            }
                            JSONObject errorObj = new JSONObject();
                            try {
                                errorObj.put("screen", "SignUpUser");
                                if (!isCityEditText) {
                                    errorObj.put("searchKey", workplace_edit.getText().toString());
                                } else {
                                    errorObj.put("searchKey", city_edit.getText().toString());
                                }
                                errorObj.put("errorMsg", errorMsg);
                                if (!isCityEditText) {
                                    AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                                } else {
                                    AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    String error = errorResponse;
                    JSONObject errorObj = new JSONObject();
                    try {
                        errorObj.put("screen", "SignUpUser");
                        if (!isCityEditText) {
                            errorObj.put("searchKey", workplace_edit.getText().toString());
                        } else {
                            errorObj.put("searchKey", city_edit.getText().toString());
                        }
                        errorObj.put("errorMsg", errorResponse);
                        if (!isCityEditText) {
                            AppUtil.logUserActionEvent(login_doc_id, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);
                        } else {
                            AppUtil.logUserActionEvent(login_doc_id, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), MandatoryProfileInfo.this);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void performCrop() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            final String fileName = "profile" + "_" + formatter.format(now) + ".jpg";
            File f = new File(folder, fileName);
            selectedImagePath = f.getAbsolutePath();
            Crop.of(picUri, Uri.fromFile(f)).asSquare().start(this);
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(this, getResources().getString(R.string.device_not_support_crop), Toast.LENGTH_SHORT).show();
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
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                        if (data.has("prof_id")) {
                            if (data.has(RestUtils.TAG_PROFILE_PIC_NAME)) {
                                basicInfo.setPic_name(data.getString(RestUtils.TAG_PROFILE_PIC_NAME));
                            } else {
                                basicInfo.setPic_name("");
                            }
                            if (data.has("is_user_verified")) {
                                mySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_IS_USER_VERIFIED, data.optInt("is_user_verified", 3));
                                //AppConstants.IS_USER_VERIFIED_CONSTANT = data.optInt("is_user_verified");
                            }
                            if (data.has(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL)) {
                                basicInfo.setPic_url(data.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                            }
                            professionalInfo.setProf_id(Integer.parseInt(data.optString("prof_id")));
                            realmManager.updateSpltyBasicInfo(realm, basicInfo);
                            if (realmManager.isProfessionalInfoExists(realm, professionalInfo.getProf_id())) {
                                realmManager.updateProfessionInfo(realm, professionalInfo);
                            } else {
                                realmManager.insertProfession(realm, professionalInfo);
                            }
                            /*if (UAirship.shared().getPushManager().getChannelId() != null && basicInfo != null && basicInfo.getDoc_id() != 0) {
                                AppUtil.subscribeDeviceForNofications(MandatoryProfileInfo.this, basicInfo.getDoc_id(), "UrbanAirship", true, UAirship.shared().getPushManager().getChannelId());
                            }*/
                            //sendMandAck(login_doc_id);
                            fetchConnects(login_doc_id);
                        } else {
                            JSONArray citiesjArray = data.getJSONArray("cities");
                            JSONArray specialitiesjArray = data.getJSONArray("specialities");

                            if (specialitiesjArray.length() > 0) {
                                for (int i = 0; i < specialitiesjArray.length(); i++) {
                                    specialities_array.add(specialitiesjArray.get(i).toString());
                                }
                            }
                            if (citiesjArray.length() > 0) {
                                for (int i = 0; i < citiesjArray.length(); i++) {
                                    cities_array.add(citiesjArray.get(i).toString());
                                    cities_adpt.notifyDataSetChanged();
                                }
                            }

                            Log.d("auto response", "response" + response);
                        }

                    } else if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        hideProgress();
                        ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
                    } else {
                        hideProgress();
                        ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
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

    private void fetchConnects(final int doctorId) {
        new FetchUserConnects(MandatoryProfileInfo.this, doctorId, mySharedPref.getPrefsHelper().getPref("last_doc_id", 0), true, realm, realmManager, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                //hideProgress();
                sendMandAck(doctorId);
                AppUtil.logFlurryEventWithDocIdAndEmailEvent("MANDATORYPROFILEINFO", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                hideProgress();
                AppUtil.logFlurryEventWithDocIdAndEmailEvent("MANDATORYPROFERROR", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
                displayErrorScreen(errorResponse);
            }
        });
    }

    private void sendMandAck(int mDoctorId) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put(RestUtils.TAG_DOC_ID, mDoctorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //showProgress();
        new VolleySinglePartStringRequest(MandatoryProfileInfo.this, Request.Method.POST, RestApiConstants.ACK_MANDATORY, requestData.toString(), "MANDATORY_PROFILE_ACK", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                //hideProgress();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(successResponse);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        mySharedPref.getPrefsHelper().savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_REMEMBER_ME, false));
                        mySharedPref.getPrefsHelper().savePref(mySharedPref.PREF_MANDATORY_PROFILE_CHECK, true);
                        AppConstants.IS_USER_VERIFIED = jsonObject.optBoolean("is_user_verified", false);
                                            /*Intent in = new Intent(MandatoryProfileInfo.this, DashboardActivity.class);
                                            startActivity(in);
                                            finish();*/

                        hideProgress();
                        Intent in = new Intent(MandatoryProfileInfo.this, MCACardUploadActivity.class);
                        in.putExtra("NAVIGATION", "fromManadatory");
                        in.putExtra("first_name", firstName_value);
                        in.putExtra("last_name", lastName_value);
                        startActivity(in);
                        finish();
                    } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        hideProgress();
                        displayErrorScreen(successResponse);
                    } else {
                        hideProgress();
                        displayErrorScreen(successResponse);
                    }

                } catch (JSONException e) {
                    hideProgress();
                    e.printStackTrace();
                }
                AppUtil.logFlurryEventWithDocIdAndEmailEvent("MANDATORYPROFILEINFO", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                hideProgress();
                AppUtil.logFlurryEventWithDocIdAndEmailEvent("MANDATORYPROFERROR", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();

    }

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("ProfileInfoDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("ProfileInfoBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
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
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (!realm.isClosed())
            realm.close();

    }

    @Override
    public void onDialogListItemSelect(String selectedItem) {
        Log.i(TAG, "onDialogListItemSelect()");
        if (selectedItem == null)
            selectedItem = "";
        speciality = selectedItem;
        specialityTxtVw.setText(selectedItem);
    }

    private void handleCrop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri fileUri = Crop.getOutput(data);
            if (AppUtil.loadImageFromPath(fileUri.getPath()) != null) {
                try {
                    Bitmap rotatedImg = AppUtil.rotateImageIfRequired(AppUtil.loadImageFromPath(fileUri.getPath()), fileUri, MandatoryProfileInfo.this);
                    //Uri roatatedUri = AppUtil.getImageUri(MandatoryProfileInfo.this, rotatedImg);
                    //selectedImagePath=getPath(roatatedUri, MandatoryProfileInfo.this);
                    rotatedImg = AppUtil.sampleResize(rotatedImg, 1536, 1152);
                    FileOutputStream out = new FileOutputStream(selectedImagePath);
                    rotatedImg.compress(Bitmap.CompressFormat.JPEG, 70, out);
                    profile_pic_img.setImageBitmap(rotatedImg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Image not supported,choose another image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(data).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        if (isWorkEditText) {
            workPlaceJson = null;
        }
        if (isCityEditText) {
            cityJson = null;
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
