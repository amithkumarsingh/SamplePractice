package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.soundcloud.android.crop.Crop;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.fragments.SpecialityDialogFragment;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.ProfileUpdatedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.ProfileUpdateCollectionManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleyMultipartStringRequest;
import com.vam.whitecoats.viewmodel.SpecialityViewModel;

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

/**
 * Created by lokesh on 5/13/2015.
 */
public class BasicProfileActivity extends BaseActionBarActivity implements SpecialityDialogFragment.NoticeDialogListener, AdapterView.OnItemSelectedListener {
    public static final String TAG = BasicProfileActivity.class.getSimpleName();
    EditText firstname_edit, last_name_edit, subSpecialityEditText, specialityTextview, uspEdtTxtVw, specificAskEdtTxtVw;
    TextView firstname_error_text, last_name_error_text, speciality_error_text, contact_no_error_text, email_text, add_photo_text, experience_error_text;
    ImageView profile_pic_img, camera_img, imageView_overlay;
    TextView next_button;
    MarshMallowPermission marshMallowPermission;
    AutoCompleteTextView specialityAutoCompleteTextView;
    private SpecialityViewModel specialityViewModel;
    private ArrayList specialityArrayList;
    private ArrayAdapter specialityArrayAdapter;
    private String itemSelected = "";

    private String TAG_SPECIALITY = "specialities";

    private static final String TAG_STATUS = "status";
    private static final String TAG_SUCCESS = "success";
    private Realm realm;
    private RealmManager realmManager;
    private BasicInfo basicInfo = null;
    private File myDirectory;
    public static String selectedImagePath = "";
    private static final int CAMERA_PIC_REQUEST = 1313;
    final int CROP_PIC = 22;
    String fNameStr;
    String lNameStr;
    String specialtyStr;
    String subSpecialityStr;
    File folder;
    private RealmBasicInfo realmBasicInfo;
    private static Uri picUri;
    private ArrayList<String> speciality_array = new ArrayList<String>();
    private String image_url;
    private BasicInfo basicInfoObj = null;
    private int docId;
    private Bitmap myBitmap;
    public static boolean updateProfilePicOnResume = false;
    private Uri mCapturedImageURI = null;
    private String picUrl = "";

    private Spinner salutationSpinner;
    List<String> salutationsList = new ArrayList<String>();
    private String selectedSalutation = "Dr. ";
    private AutoCompleteTextView experience_autocomplete_view;

    String[] experience = {"0-1 year", "1 year", "2 years", "3 years", "4 years", "5 years", "6 years", "7 years", "8 years", "9 years", "10 years", "11 years", "12 years", "13 years", "14 years",
            "15 years", "16 years", "17 years", "18 years", "19 years", "20 years", "21 years", "22 years", "23 years", "24 years", "25 years", "26 years", "27 years", "28 years", "29 years", "30 years",
            "31 years", "32 years", "33 years", "34 years", "35 years", "36 years", "37 years", "38 years", "39 years", "40 years", "41 years", "42 years", "43 years", "44 years", "45 years", "46 years",
            "47 years", "48 years", "49 years", "50 years", "51 years", "52 years", "53 years", "54 years", "55 years", "56 years", "57 years", "58 years", "59 years", "60 years", "61 years", "62 years",
            "63 years", "64 years", "65 years", "66 years", "67 years", "68 years", "69 years", "70 years"};
    List<String> convertedExpList = Arrays.asList(experience);
    private ArrayAdapter<String> dataAdapterexpience;
    private boolean customBackButton = false;

    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchCameraActivityResults;
    private ActivityResultLauncher<Intent> launchGalleryActivityResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_profile);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        App_Application.setCurrentActivity(this);

        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        mTitleTextView.setText(getString(R.string.str_personalinfo_heading));
        next_button.setText(getString(R.string.actionbar_save));
        createDirIfNotExists();

        specialityViewModel = new ViewModelProvider(this).get(SpecialityViewModel.class);
        specialityViewModel.init();
        specialityArrayList = new ArrayList<>();


        initialize();
        validationUtils = new ValidationUtils(BasicProfileActivity.this,
                new EditText[]{firstname_edit, last_name_edit, specialityTextview, experience_autocomplete_view},
                new TextView[]{firstname_error_text, last_name_error_text, speciality_error_text, experience_error_text});
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    int overallExperience = 999;
                    String experienceSplitedString = null;
                    fNameStr = firstname_edit.getText().toString().trim();
                    lNameStr = last_name_edit.getText().toString().trim();
                    specialtyStr = itemSelected;
                    subSpecialityStr = subSpecialityEditText.getText().toString().trim();
                    String uspStr = uspEdtTxtVw.getText().toString().trim();
                    String specificAskStr = specificAskEdtTxtVw.getText().toString().trim();

                    String overAllExperience = experience_autocomplete_view.getText().toString().trim();
                    if (!convertedExpList.contains(overAllExperience)) {
                        Toast.makeText(BasicProfileActivity.this, R.string.select_valid_experience, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String[] splited = overAllExperience.split(" ");
                    if (splited.length > 0) {
                        experienceSplitedString = splited[0];
                    }
                    if (experienceSplitedString != null) {
                        if (experienceSplitedString.contains("-")) {
                            overallExperience = 0;
                        } else {
                            overallExperience = Integer.parseInt(experienceSplitedString);
                        }
                    }
                    boolean isMandatory;
                    if (specialityAutoCompleteTextView.getText().toString().isEmpty() || itemSelected.isEmpty()) {
                        isMandatory = true;
                    } else {
                        isMandatory = false;
                    }

                    if (validationUtils.isValidMandatoryField(fNameStr, lNameStr, specialtyStr, overAllExperience, isMandatory)) {
                        if (isConnectingToInternet()) {
                            basicInfoObj = new BasicInfo();
                            basicInfoObj.setFname(fNameStr);
                            basicInfoObj.setLname(lNameStr);
                            basicInfoObj.setSplty(specialtyStr);
                            basicInfoObj.setSubSpeciality(subSpecialityStr);
                            basicInfoObj.setProfile_pic_path(selectedImagePath);
                            basicInfoObj.setPic_url(picUrl);
                            basicInfoObj.setUserSalutation(selectedSalutation);
                            basicInfoObj.setAbout_me(uspStr);
                            basicInfoObj.setSpecificAsk(specificAskStr);
                            basicInfo.setOverAllExperience(overallExperience);
                            hideKeyboard();
                            updateProfileAction(false);

                        }
                    }

                } catch (Exception e) {
                    hideProgress();
                }
            }
        });

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchCameraActivityResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        if (resultCode == Activity.RESULT_OK) {
                            if (mCapturedImageURI != null) {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH);
                                Date now = new Date();
                                final String fileName = "profile" + "_" + formatter.format(now) + ".jpg";
                                File f = new File(folder, fileName);
                                selectedImagePath = f.getAbsolutePath();
                                Crop.of(mCapturedImageURI, Uri.fromFile(f)).asSquare().start(BasicProfileActivity.this);
                            }

                        } else if (resultCode == Activity.RESULT_CANCELED) {
                            if (mCapturedImageURI != null) {
                                getContentResolver().delete(mCapturedImageURI, null, null);
                            }
                        }
                    }
                });

        launchGalleryActivityResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if (resultCode == Activity.RESULT_OK) {
                            picUri = data.getData();
                            performCrop();
                        }
                    }
                });

//End
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void createDirIfNotExists() {
        myDirectory = AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        folder = new File(myDirectory + "/Profile_Pic");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

    private void updateProfileAction(boolean isFromOTP) {
        JSONObject object = new JSONObject();
        String subSpeciality = basicInfoObj.getSubSpeciality();
        if (subSpeciality == null)
            subSpeciality = "";
        try {
            object.put(RestUtils.TAG_IS_UPDATE, true);
            object.put(RestUtils.TAG_USER_ID, docId);
            object.put(RestUtils.TAG_USER_FIRST_NAME, basicInfoObj.getFname());
            object.put(RestUtils.TAG_USER_LAST_NAME, basicInfoObj.getLname());
            object.put(RestUtils.TAG_USER_SALUTAION, basicInfoObj.getUserSalutation());
            object.put(RestUtils.TAG_USER_TYPE_ID, 1);
            object.put(RestUtils.TAG_SUB_SPLTY, basicInfoObj.getSubSpeciality());
            object.put(RestUtils.KEY_SPECIALITIES, new JSONArray().put(basicInfoObj.getSplty()));
            object.put(RestUtils.KEY_SPECIALISTS, new JSONArray().put(subSpeciality));
            object.put("experience", basicInfo.getOverAllExperience());
            JSONArray socialInfoArray = new JSONArray();
            JSONObject socialInfoObj = new JSONObject();
            socialInfoObj.put(RestUtils.TAG_ABOUT_ME, basicInfoObj.getAbout_me());
            socialInfoObj.put(RestUtils.KEY_SPECIFIC_ASK, basicInfoObj.getSpecificAsk());
            socialInfoObj.put(RestUtils.TAG_BLOG_PAGE, basicInfo.getBlog_page());
            socialInfoObj.put(RestUtils.TAG_WEB_PAGE, basicInfo.getWebsite());
            socialInfoObj.put(RestUtils.TAG_FB_PAGE, basicInfo.getFb_page());
            socialInfoObj.put(RestUtils.TAG_LINKEDIN_PAGE, basicInfo.getLinkedInPg());
            socialInfoObj.put(RestUtils.TAG_TWITTER_PAGE, basicInfo.getTwitterPg());
            socialInfoObj.put(RestUtils.TAG_INSTAGRAM_PAGE, basicInfo.getInstagramPg());
            socialInfoObj.put(RestUtils.TAG_PROFILE_PDF_URL, basicInfo.getDocProfilePdfURL());
            socialInfoArray.put(socialInfoObj);
            object.put(RestUtils.TAG_SOCIAL_INFO, socialInfoArray);

            if (basicInfoObj.getProfile_pic_path() != null && !basicInfoObj.getProfile_pic_path().trim().equals("")) {
                File imgFile = new File(basicInfoObj.getProfile_pic_path());
                if (imgFile.exists()) {
                    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                }
            }
            //if(!isFromOTP) {
            showProgress();
            //}
            new VolleyMultipartStringRequest(BasicProfileActivity.this, Request.Method.POST, RestApiConstants.EDIT_PERSONAL_INFO, object.toString(), "BASIC_PROFILE_ACIVITY", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    hideProgress();
                    if (successResponse != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(successResponse);
                            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                if (jsonObject.has(RestUtils.TAG_DATA)) {
                                    JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                                    JSONArray socialInfoArray = new JSONArray();
                                    socialInfoArray = data.optJSONArray(RestUtils.TAG_SOCIAL_INFO);
                                    if (data.has(RestUtils.TAG_PROFILE_PIC_NAME)) {
                                        basicInfoObj.setPic_name(data.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                    }
                                    if (data.has(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL)) {
                                        basicInfoObj.setPic_url(data.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    }
                                    basicInfoObj.setUserSalutation(data.optString(RestUtils.TAG_USER_SALUTAION));
                                    basicInfoObj.setUserType(data.optInt(RestUtils.TAG_USER_TYPE_ID));
                                    basicInfoObj.setProfile_pic_path(selectedImagePath);
                                    basicInfoObj.setFname(data.optString(RestUtils.TAG_USER_FIRST_NAME));
                                    basicInfoObj.setLname(data.optString(RestUtils.TAG_USER_LAST_NAME));
                                    basicInfoObj.setSubSpeciality(data.optString(RestUtils.TAG_SUB_SPLTY));
                                    basicInfoObj.setAbout_me(socialInfoArray.getJSONObject(0).optString(RestUtils.TAG_ABOUT_ME));
                                    basicInfoObj.setSpecificAsk(socialInfoArray.getJSONObject(0).optString(RestUtils.KEY_SPECIFIC_ASK));
                                    basicInfoObj.setDocProfilePdfURL(socialInfoArray.getJSONObject(0).optString(RestUtils.TAG_PROFILE_PDF_URL));
                                    basicInfoObj.setFollowingCount(basicInfo.getFollowingCount());
                                    basicInfoObj.setFollowersCount(basicInfo.getFollowersCount());
                                    basicInfoObj.setFeedCount(basicInfo.getFeedCount());
                                    basicInfoObj.setOverAllExperience(data.optInt("experience"));
                                    if (data.has(TAG_SPECIALITY)) {
                                        basicInfoObj.setSplty(data.getJSONArray(TAG_SPECIALITY).optString(0));
                                    }
                                } else {
                                    basicInfoObj.setPic_name("");
                                }
                                realmManager.updateBasicInfo(realm, basicInfoObj);
                                for (ProfileUpdatedListener listener : ProfileUpdateCollectionManager.getRegisterListeners()) {
                                    listener.onProfileUpdate(basicInfo);
                                }
                                Toast.makeText(BasicProfileActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();
                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                displayErrorScreen(successResponse);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    hideProgress();
                    displayErrorScreen(errorResponse);

                }
            }).sendMultipartRequest(myBitmap, "file", "profilepic.png");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        try {
            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(this);
            realmBasicInfo = realmManager.getRealmBasicInfo(realm);
            docId = realmManager.getDoc_id(realm);
            if (realmBasicInfo != null) {
                picUrl = realmBasicInfo.getPic_url();
            }

            marshMallowPermission = new MarshMallowPermission(this);
            /**
             * Check if directory exists
             */

            firstname_error_text = _findViewById(R.id.firstname_error_text);
            last_name_error_text = _findViewById(R.id.last_name_error_text);
            speciality_error_text = _findViewById(R.id.speciality_error_text);
            contact_no_error_text = _findViewById(R.id.contact_no_text);
            experience_error_text = _findViewById(R.id.experience_error_text);
            firstname_edit = _findViewById(R.id.firstname_edit);
            last_name_edit = _findViewById(R.id.last_name_edit);
            specialityAutoCompleteTextView = _findViewById(R.id.specialityAutoCompleteTextView);
            subSpecialityEditText = _findViewById(R.id.subSpecialityEditText);
            uspEdtTxtVw = _findViewById(R.id.uspEditText);
            specificAskEdtTxtVw = _findViewById(R.id.specificAskEditText);
            profile_pic_img = _findViewById(R.id.profile_pic_img);
            camera_img = _findViewById(R.id.camera_img);
            add_photo_text = _findViewById(R.id.add_photo_text);

            imageView_overlay = _findViewById(R.id.green_over_lay);
            experience_autocomplete_view = _findViewById(R.id.experience_autocomplete_view);

            salutationSpinner = (Spinner) findViewById(R.id.salutation_spinner_profile_edit);
            salutationSpinner.setOnItemSelectedListener(this);
            basicInfo = (BasicInfo) getIntent().getSerializableExtra("basicInfo");


            salutationsList.add("Dr.");
            salutationsList.add("Mr.");
            salutationsList.add("Mrs.");
            salutationsList.add("Ms.");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.textview_item, salutationsList);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            dataAdapterexpience = new ArrayAdapter<String>
                    (this, R.layout.textview_item, experience);
            dataAdapterexpience.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            experience_autocomplete_view.setThreshold(1);
            experience_autocomplete_view.setAdapter(dataAdapterexpience);
            getSpeciality();

            specialityArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, specialityArrayList);
            specialityAutoCompleteTextView.setAdapter(specialityArrayAdapter);
            specialityAutoCompleteTextView.setThreshold(2);

            // attaching data adapter to spinner
            salutationSpinner.setAdapter(dataAdapter);
            if (realmBasicInfo.getUser_salutation() != null) {
                int position = dataAdapter.getPosition(realmBasicInfo.getUser_salutation());
                salutationSpinner.setSelection(position);
            }

            if (basicInfo != null) {
                firstname_edit.setText(basicInfo.getFname());
                last_name_edit.setText(basicInfo.getLname());
                specialityAutoCompleteTextView.setText(basicInfo.getSplty(), false);
                itemSelected = basicInfo.getSplty();
                subSpecialityEditText.setText(basicInfo.getSubSpeciality());
                uspEdtTxtVw.setText(basicInfo.getAbout_me());
                specificAskEdtTxtVw.setText(basicInfo.getSpecificAsk());

                int experience = basicInfo.getOverAllExperience();
                if (experience != 999) {
                    if (experience == 1) {
                        experience_autocomplete_view.setText(basicInfo.getOverAllExperience() + " year");
                    } else if (experience == 0) {
                        experience_autocomplete_view.setText("0-1 year");
                    } else {
                        experience_autocomplete_view.setText(basicInfo.getOverAllExperience() + " years");
                    }
                } else {
                    experience_autocomplete_view.setText("");
                }

                selectedImagePath = basicInfo.getProfile_pic_path();
                if (!AppUtil.checkWriteExternalPermission(BasicProfileActivity.this)) {
                    if (realmBasicInfo.getPic_url() != null && !realmBasicInfo.getPic_url().isEmpty()) {
                        AppUtil.invalidateAndLoadCircularImage(BasicProfileActivity.this, realmBasicInfo.getPic_url().trim(), profile_pic_img, R.drawable.default_profilepic);
                        add_photo_text.setVisibility(View.GONE);
                        imageView_overlay.setVisibility(View.GONE);
                    }
                } else if (realmBasicInfo.getPic_url() != null && !realmBasicInfo.getPic_url().isEmpty()) {
                    AppUtil.invalidateAndLoadCircularImage(BasicProfileActivity.this, realmBasicInfo.getPic_url().trim(), profile_pic_img, R.drawable.default_profilepic);
                    add_photo_text.setVisibility(View.GONE);
                    imageView_overlay.setVisibility(View.GONE);
                } else if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                    File imgFile = new File(selectedImagePath);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        if (myBitmap != null) {
                            profile_pic_img.setImageBitmap(myBitmap);
                            add_photo_text.setVisibility(View.GONE);
                            imageView_overlay.setVisibility(View.GONE);
                        }
                    }
                }
            }

            profile_pic_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //updateProfilePicOnResume=true;
                    Intent intent = new Intent(BasicProfileActivity.this, Profile_fullView.class);
                    String pic_path = "";
                    if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                        pic_path = selectedImagePath;
                    } else {
                        pic_path = realmBasicInfo.getProfile_pic_path();
                    }
                    intent.putExtra("profile_pic_path", pic_path);
                    intent.putExtra("basicInfo", basicInfo);
                    intent.putExtra("selectedDocId", realmBasicInfo.getDoc_id());
                    startActivity(intent);
                }
            });

            camera_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });


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
                }
            });


        } catch (Exception e) {
            catchException(e);
        }
    }

    public void alertDialog_Message() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BasicProfileActivity.this);
        builder.setCancelable(false);
        builder.setMessage(AppUtil.alert_CameraPermissionDeny_Message());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(BasicProfileActivity.this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.storage_deny_message));
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

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library", "Remove"};

        AlertDialog.Builder builder = new AlertDialog.Builder(BasicProfileActivity.this);
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
                    picUrl = "";
                    profile_pic_img.setImageResource(R.drawable.default_profilepic);
                    add_photo_text.setVisibility(View.VISIBLE);
                    imageView_overlay.setVisibility(View.VISIBLE);
                }
            }
        });
        builder.show();
    }

    private void cameraClick() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            String fileName = "profile" + formatter.format(now) + ".jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            launchCameraActivityResults.launch(takePictureIntent);
        }
    }

    private void chooseFromLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launchGalleryActivityResults.launch(Intent.createChooser(intent, "Select File"));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.CAMERA_PERMISSION_REQUEST_CODE:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                boolean camera = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    perms.put(Manifest.permission.READ_MEDIA_IMAGES, PackageManager.PERMISSION_GRANTED);
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    boolean mediaImages = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES);

                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                        createDirIfNotExists();
                        cameraClick();
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                        if (!camera && !mediaImages) {
                            AppConstants.neverAskAgain_Camera = true;
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !mediaImages) {
                        AppConstants.neverAskAgain_Library = true;
                    }
                    if (perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED && !camera) {
                        AppConstants.neverAskAgain_Camera = true;
                    }
                } else {
                    perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    boolean media = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        createDirIfNotExists();
                        cameraClick();
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        if (!camera && !media) {
                            AppConstants.neverAskAgain_Camera = true;
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !media) {
                        AppConstants.neverAskAgain_Library = true;
                    }
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && !camera) {
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
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
        if (updateProfilePicOnResume) {
            setUpProfileUpdate();
        }
    }

    private void setUpProfileUpdate() {
        final RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        if (realmBasicInfo != null) {
            String imgpath = realmBasicInfo.getProfile_pic_path();
            image_url = realmBasicInfo.getPic_url();
            basicInfo.setProfile_pic_path(imgpath);
            if (!AppUtil.checkWriteExternalPermission(BasicProfileActivity.this)) {
                if (image_url != null && !image_url.isEmpty()) {
                    AppUtil.loadCircularImageUsingLib(BasicProfileActivity.this, image_url.trim(), profile_pic_img, R.drawable.default_profilepic);
                    add_photo_text.setVisibility(View.GONE);
                    imageView_overlay.setVisibility(View.GONE);
                }
            } else if (imgpath != null && !imgpath.equals("")) {
                File imgFile = new File(imgpath);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    if (myBitmap != null) {
                        profile_pic_img.setImageBitmap(myBitmap);
                        add_photo_text.setVisibility(View.GONE);
                        imageView_overlay.setVisibility(View.GONE);
                    } else {
                        profile_pic_img.setImageResource(R.drawable.default_profilepic);
                        add_photo_text.setVisibility(View.VISIBLE);
                        imageView_overlay.setVisibility(View.VISIBLE);
                    }
                }
            } else if (image_url != null && !image_url.isEmpty()) {
                AppUtil.loadCircularImageUsingLib(BasicProfileActivity.this, image_url.trim(), profile_pic_img, R.drawable.default_profilepic);
                add_photo_text.setVisibility(View.GONE);
                imageView_overlay.setVisibility(View.GONE);
            } else {
                profile_pic_img.setImageResource(R.drawable.default_profilepic);
                add_photo_text.setVisibility(View.VISIBLE);
                imageView_overlay.setVisibility(View.VISIBLE);
            }

        }
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
            if (requestCode == CROP_PIC) {
                try {
                    picUri = data.getData();
                    selectedImagePath = getPath(picUri, BasicProfileActivity.this);
                    String s1 = data.getDataString();
                    if (selectedImagePath == null && s1 != null) {
                        selectedImagePath = s1.replaceAll("file://", "");
                    }
                    if (selectedImagePath != null) {
                        File imgFiles = new File(selectedImagePath);
                        if (imgFiles.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFiles.getAbsolutePath());
                            profile_pic_img.setImageBitmap(myBitmap);
                            add_photo_text.setVisibility(View.GONE);
                            imageView_overlay.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == CAMERA_PIC_REQUEST) {
                if (mCapturedImageURI != null) {
                    getContentResolver().delete(mCapturedImageURI, null, null);
                }
            }
        }
    }

    private void handleCrop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri fileUri = Crop.getOutput(data);
            if (AppUtil.loadImageFromPath(fileUri.getPath()) != null) {
                try {
                    Bitmap imgBitmap = AppUtil.rotateImageIfRequired(AppUtil.loadImageFromPath(fileUri.getPath()), fileUri, BasicProfileActivity.this);
                    //Uri uri = AppUtil.getImageUri(BasicProfileActivity.this, imgBitmap);
                    //selectedImagePath=getPath(uri, BasicProfileActivity.this);
                    /*
                     * compress to specified percentage
                     */
                    imgBitmap = AppUtil.sampleResize(imgBitmap, 1536, 1152);
                    FileOutputStream out = new FileOutputStream(selectedImagePath);
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
                    profile_pic_img.setImageBitmap(imgBitmap);
                    add_photo_text.setVisibility(View.GONE);
                    imageView_overlay.setVisibility(View.GONE);
                    updateProfilePicOnResume = false;
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
    public void onBackPressed() {
        hideKeyboard();
        selectedImagePath = selectedImagePath == null ? "" : selectedImagePath;
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("UserBasicInfoDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (basicInfo != null && (!basicInfo.getFname().equals(firstname_edit.getText().toString())
                || !basicInfo.getLname().equals(last_name_edit.getText().toString())
//                || !basicInfo.getSplty().equals(specialityTextview.getText().toString())
                || !basicInfo.getSplty().equals(itemSelected)
                || !basicInfo.getProfile_pic_path().equals(selectedImagePath))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage(R.string.profile_back_button);
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
                    NavUtils.navigateUpFromSameTask(BasicProfileActivity.this);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            finish();
        }
    }

    @Override
    public void onTaskCompleted(String s) {
        if (s != null) {
            if (s.equals("SocketTimeoutException") || s.equals("Exception")) {
                hideProgress();
                ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString(TAG_STATUS).equals(TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {
                            JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            if (data.has(TAG_SPECIALITY)) {
                                JSONArray spltyjArray = data.getJSONArray(TAG_SPECIALITY);
                                for (int i = 0; i < spltyjArray.length(); i++) {
                                    speciality_array.add(spltyjArray.get(i).toString());
                                }
                            } else {
                                Toast.makeText(BasicProfileActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();
                            }
                        } else {
                            hideProgress();
                            Intent in = new Intent();
                            setResult(Activity.RESULT_OK, in);
                            finish();
                        }
                    } else {
                        hideProgress();
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                ShowServerErrorSimpleDialog("Error", getResources().getString(R.string.session_timedout));
                            } else {
                                String errorMsg = getResources().getString(R.string.unknown_server_error);
                                if (!jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE).isEmpty()) {
                                    errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                ShowSimpleDialog("Error", errorMsg);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    hideProgress();
                }
            }
            hideProgress();
        }
    }

    @Override
    public void onDialogListItemSelect(String selectedItem) {
        Log.i(TAG, "onDialogListItemSelect()");
        if (selectedItem == null)
            selectedItem = "";
        subSpecialityStr = selectedItem;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("UserBasicInfoBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        selectedSalutation = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getSpeciality() {
        specialityViewModel.getSpeciality(BasicProfileActivity.this).observe(this, new Observer<String>() {
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
                        Toast.makeText(BasicProfileActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
