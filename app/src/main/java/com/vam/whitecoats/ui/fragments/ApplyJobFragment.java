package com.vam.whitecoats.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AWSKeys;
import com.vam.whitecoats.core.models.AttachmentInfo;
import com.vam.whitecoats.core.models.JobLocationSpecializationInfo;
import com.vam.whitecoats.core.models.Prediction;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.JobApplicationSpecialityLocationListAdapter;
import com.vam.whitecoats.ui.adapters.PlacesAutoCompleteAdapter;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.ui.interfaces.JobSpecializationLocationInterface;
import com.vam.whitecoats.ui.interfaces.LocationCaputerListner;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.OnlocationApiFinishedListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AWSHelperClass;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AwsAndGoogleKeysServiceClass;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.CopyFileToAppDirExecutor;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplyJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyJobFragment extends Fragment implements TextWatcher {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int OPEN_DIRECTORY_REQUEST_CODE = 101;

    // TODO: Rename and change types of parameters
    private String feedDataString;
    private int userId;
    private JSONObject completeFeedObj;
    private EditText userEmail, userMobileNumber;
    private TextView attachCVtext, attachCovering;
    private Realm realm;
    private RealmManager realmManager;
    private String emailId;
    private String phNum;
    private JSONObject feedInfoObj;
    private View latestCommentLayout;
    private RoundedImageView commented_user_img;
    private TextView commented_user_name;
    private TextView comment_text;
    ArrayList<AttachmentInfo> attachmentsList = new ArrayList<>();
    private String attachmentType = "";
    private AttachmentInfo cvAttachmentObj;
    private AttachmentInfo coveringAttachmentObj;
    private Button cvAttachmentCloseBtn, coveringAttachmentCloseBtn;
    private LinearLayout ll_cvDeleteButton,ll_coveringDeleteButton;
    private Button applyJobButton;
    private String trimmedCountryCode;
    private String trimmedPhoneNumber;
    private CountryCodePicker countryCodePicker;
    private boolean isCountryCodeChanged;
    private MySharedPref mySharedPref;
    private TableRow cvTableRow, coveringTableRow;
    private AWSKeys awsKeys;
    private ProgressDialog mProgressDialog;
    private String userUUID;
    private String uploadedCVUrl;
    private String uploadedCoveringLetterUrl;
    private int uploadStatusCount;
    private TextView alreadySubmittedTv;
    private int applicationType;
    private TableLayout jobAttachmentsTable;
    private int feedId;
    private boolean isApplied;
    private long applicationDeadline;
    private TextInputLayout til_specialization, til_city, experience_job_view_lay;
    private AutoCompleteTextView speciality_job_edit, city_job_edit, experience_autocomplete_view;
    private TextView speciality_error_text, city_error_text, experience_error_text,preferred_speciality_error_text,preferred_location_error_text;
    protected ValidationUtils validationUtils, locationFieldValidation;
    private boolean isCityEditText;
    private String api_key;
    private boolean isExactCity;
    private MarshMallowPermission marshMallowPermission;
    private LocationHelperClass locationHelperObj;
    private RequestForCurrentLocPlacesUsingPlaceId requestForCurrentLocPlacesUsingPlaceId;
    private JSONObject cityJson;
    private RequestForPlacesUsingPlaceId requestForPlacesUsingPlaceId;
    private ArrayAdapter specialityArrayAdapter;
    private ArrayList specialityArrayList;
    private SpecialityViewModel specialityViewModel;
    String[] experience = {"0-1 year", "1 year", "2 years", "3 years", "4 years", "5 years", "6 years", "7 years", "8 years", "9 years", "10 years", "11 years", "12 years", "13 years", "14 years",
            "15 years", "16 years", "17 years", "18 years", "19 years", "20 years", "21 years", "22 years", "23 years", "24 years", "25 years", "26 years", "27 years", "28 years", "29 years", "30 years",
            "31 years", "32 years", "33 years", "34 years", "35 years", "36 years", "37 years", "38 years", "39 years", "40 years", "41 years", "42 years", "43 years", "44 years", "45 years", "46 years",
            "47 years", "48 years", "49 years", "50 years", "51 years", "52 years", "53 years", "54 years", "55 years", "56 years", "57 years", "58 years", "59 years", "60 years", "61 years", "62 years",
            "63 years", "64 years", "65 years", "66 years", "67 years", "68 years", "69 years", "70 years"};
    private ArrayAdapter<String> dataAdapterexpience;
    List<String> convertedExpList = Arrays.asList(experience);
    int overallExperienceInInt = 999;


    private String itemSelected = "";
    private boolean isDefaultLocationSelected = true;
    private EditText preferred_speciality_et,preferred_location_et;
    private ArrayList<JobLocationSpecializationInfo> specializationsArray=new ArrayList<>();
    private ArrayList<JobLocationSpecializationInfo> locationsArray=new ArrayList<>();
    private JobApplicationSpecialityLocationListAdapter jobApplicationSpecialityLocationListAdapter;
    private ArrayList<JobLocationSpecializationInfo> selectedSpecialization=new ArrayList<>();
    private ArrayList<JobLocationSpecializationInfo> selectedLocations=new ArrayList<>();
    private boolean locationsAndSpecializationsMandatory;
    private ConstraintLayout.LayoutParams buttonLayoutParams;
    private static int collapsedMargin; //Button margin in collapsed state
    private static int buttonHeight;
    private static int expandedHeight; //Height of bottom sheet in expanded state


    public ApplyJobFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyJobFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyJobFragment newInstance(String param1, int param2) {
        ApplyJobFragment fragment = new ApplyJobFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feedDataString = getArguments().getString(ARG_PARAM1);
            userId = getArguments().getInt(ARG_PARAM2);
        }
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        emailId = realmManager.getDoc_EmailId(realm);
        phNum = realmManager.getUserPhoneNumber(realm);
        userUUID = realmManager.getUserUUID(realm);

        if (mySharedPref == null) {
            mySharedPref = new MySharedPref(getActivity());
        }

        new AwsAndGoogleKeysServiceClass(getActivity(), userId, userUUID, new AwsAndGoogleKey() {
            @Override
            public void awsAndGoogleKey(String google_api_key, String aws_key) {
                if (aws_key != null && !aws_key.isEmpty()) {

                    Log.d("Response", aws_key);
                    JSONObject awskeysObj = null;
                    try {
                        awskeysObj = new JSONObject(aws_key);
                        awsKeys = new AWSKeys();
                        awsKeys.setAWS_ACCESS_KEY(awskeysObj.optString(RestUtils.TAG_ACCESS_KEY));
                        awsKeys.setAWS_SECRET_KEY(awskeysObj.optString(RestUtils.TAG_SECRET_KEY));
                        awsKeys.setAWS_BUCKET(awskeysObj.optString(RestUtils.TAG_BUCKET));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apply_job, container, false);

        marshMallowPermission = new MarshMallowPermission(getActivity());
        userEmail = view.findViewById(R.id.et_email);
        userMobileNumber = view.findViewById(R.id.et_mobile);
        attachCVtext = view.findViewById(R.id.tv_attach_cv);
        attachCovering = view.findViewById(R.id.tv_attach_convering);
        cvAttachmentCloseBtn = view.findViewById(R.id.cvDeleteButton);
        coveringAttachmentCloseBtn = view.findViewById(R.id.coveringDeleteButton);
        ll_cvDeleteButton = view.findViewById(R.id.ll_cvDeleteButton);
        ll_coveringDeleteButton = view.findViewById(R.id.ll_coveringDeleteButton);
        applyJobButton = view.findViewById(R.id.button_apply_job);
        countryCodePicker = (CountryCodePicker) view.findViewById(R.id.ccp_jobfullview);
        cvTableRow = view.findViewById(R.id.cv_row);
        coveringTableRow = view.findViewById(R.id.covering_letter_row);
        alreadySubmittedTv = view.findViewById(R.id.tv_submitted_text);
        jobAttachmentsTable = (TableLayout) view.findViewById(R.id.attachmentsTable);

        latestCommentLayout = view.findViewById(R.id.job_fullview_comment_layout);

        til_specialization = view.findViewById(R.id.til_specialization);
        speciality_job_edit = view.findViewById(R.id.speciality_job_edit);
        til_city = view.findViewById(R.id.til_city);
        city_job_edit = view.findViewById(R.id.city_job_edit);
        experience_job_view_lay = view.findViewById(R.id.experience_job_view_lay);
        experience_autocomplete_view = view.findViewById(R.id.experience_autocomplete_view);
        speciality_error_text = view.findViewById(R.id.speciality_error_text);
        experience_error_text = view.findViewById(R.id.experience_error_text);
        preferred_speciality_error_text=view.findViewById(R.id.preferred_speciality_error_text);
        preferred_location_error_text=view.findViewById(R.id.preferred_location_error_text);
        city_error_text = view.findViewById(R.id.city_error_text);
        specialityViewModel = new ViewModelProvider(this).get(SpecialityViewModel.class);
        preferred_speciality_et=view.findViewById(R.id.preferred_speciality_et);
        preferred_location_et=view.findViewById(R.id.preferred_location_et);
        specialityViewModel.init();
        specialityArrayList = new ArrayList<>();



        speciality_job_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                Log.d("beforeTextChanged", String.valueOf(s));
                itemSelected = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                Log.d("onTextChanged", String.valueOf(s));
                itemSelected = "";
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        city_job_edit.addTextChangedListener(this);

        validationUtils = new ValidationUtils(getActivity(),
                new EditText[]{speciality_job_edit,city_job_edit, experience_autocomplete_view,preferred_speciality_et,preferred_location_et},
                new TextView[]{speciality_error_text,city_error_text, experience_error_text,preferred_speciality_error_text,preferred_location_error_text});

        locationFieldValidation = new ValidationUtils(getActivity(),
                new EditText[]{city_job_edit},
                new TextView[]{city_error_text});


        loadSpecialization_City_Data();

        commented_user_img = (RoundedImageView) latestCommentLayout.findViewById(R.id.commented_doc_pic);
        commented_user_name = (TextView) latestCommentLayout.findViewById(R.id.commented_doc_name);
        comment_text = (TextView) latestCommentLayout.findViewById(R.id.latest_comment);
        comment_text.setMaxLines(3);

        if (phNum.length() > 10) {
            trimmedCountryCode = phNum.substring(0, (phNum.length() - 10));
            trimmedPhoneNumber = phNum.substring(trimmedCountryCode.length(), phNum.length());
        } else {
            trimmedPhoneNumber = phNum;
        }
        userEmail.setText(emailId);
        userMobileNumber.setText(trimmedPhoneNumber);

        final RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        if (realmBasicInfo != null) {
            if (realmBasicInfo.getSplty() != null && !realmBasicInfo.getSplty().equalsIgnoreCase("")) {
                speciality_job_edit.setText(realmBasicInfo.getSplty(), false);
                itemSelected = realmBasicInfo.getSplty();
            }

            if (realmBasicInfo.getOverAllExperience() != 999) {
                if (realmBasicInfo.getOverAllExperience() == 1) {
                    experience_autocomplete_view.setText(realmBasicInfo.getOverAllExperience() + " year");
                } else if (realmBasicInfo.getOverAllExperience() == 0) {
                    experience_autocomplete_view.setText("0-1 year");
                } else {
                    experience_autocomplete_view.setText(realmBasicInfo.getOverAllExperience() + " years");
                }
            }
        }

        ProfessionalInfo professionalInfo = realmManager.getProfessionalInfoOfShowoncard(realm);
        if (professionalInfo != null) {
            if (professionalInfo.getLocation() != null && !professionalInfo.getLocation().equalsIgnoreCase("")) {
                city_job_edit.setText(professionalInfo.getLocation());
                isDefaultLocationSelected = true;
            } else {
                isDefaultLocationSelected = false;
            }
        } else {
            isDefaultLocationSelected = false;
        }

        if (trimmedCountryCode != null) {
            countryCodePicker.setCountryForPhoneCode(Integer.parseInt(trimmedCountryCode));
            String countryNameCode = mySharedPref.getPref(MySharedPref.PREF_USER_COUNTRY_NAME_CODE, "");
            if (!countryNameCode.isEmpty()) {
                countryCodePicker.setCountryForNameCode(countryNameCode);
            }
        }
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                if (trimmedCountryCode != null) {
                    if (countryCodePicker.getSelectedCountryCode().equalsIgnoreCase(trimmedCountryCode)) {
                        isCountryCodeChanged = false;
                    } else {
                        isCountryCodeChanged = true;
                    }
                } else {
                    isCountryCodeChanged = true;
                }
            }
        });
        //if (getArguments() != null) {
        try {
            completeFeedObj = new JSONObject(feedDataString);
            feedInfoObj = completeFeedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
            if (feedInfoObj != null) {
                feedId = feedInfoObj.optInt(RestUtils.TAG_FEED_ID);
                applicationType = feedInfoObj.optInt("application_type");
                isApplied = feedInfoObj.optBoolean("is_applied");
                applicationDeadline = feedInfoObj.optLong("application_deadline");
                //editTextUIChanges(isApplied);
                if (isApplied) {
                    applyJobButton.setVisibility(View.GONE);
                    alreadySubmittedTv.setVisibility(View.VISIBLE);
                    alreadySubmittedTv.setText("Application submitted");
                } else {
                    if (System.currentTimeMillis() <= applicationDeadline) {
                        if (applicationType == 3) {
                            applyJobButton.setText("Fill Application");
                        } else {
                            applyJobButton.setText("Submit Application");
                        }
                        applyJobButton.setVisibility(View.VISIBLE);
                        alreadySubmittedTv.setVisibility(View.GONE);
                    } else {
                        applyJobButton.setVisibility(View.GONE);
                        alreadySubmittedTv.setVisibility(View.VISIBLE);
                        alreadySubmittedTv.setText("The application deadline has passed");
                    }

                }
                if (feedInfoObj.optBoolean("cv_required")) {
                    cvTableRow.setVisibility(View.VISIBLE);
                } else {
                    cvTableRow.setVisibility(View.GONE);
                }
                if (feedInfoObj.optBoolean("cover_letter_required")) {
                    coveringTableRow.setVisibility(View.VISIBLE);
                } else {
                    coveringTableRow.setVisibility(View.GONE);
                }

                if (applicationType == 3 || isApplied) {
                    jobDetailsTableVisibility(false);
                    //jobAttachmentsTable.setVisibility(View.GONE);
                } else {
                    //jobAttachmentsTable.setVisibility(View.VISIBLE);
                    if (System.currentTimeMillis() <= applicationDeadline) {
                        jobDetailsTableVisibility(true);
                    } else {
                        jobDetailsTableVisibility(false);
                    }
                }


                JSONObject socialInteractionObj = feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                    latestCommentLayout.setVisibility(View.VISIBLE);
                    AppUtil.displayLatestCommentUI(getActivity(), userId, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), commented_user_img, commented_user_name, comment_text);
                } else {
                    latestCommentLayout.setVisibility(View.GONE);
                }
            }

            if ((feedInfoObj.has("specializations") && feedInfoObj.optJSONArray("specializations") != null && feedInfoObj.optJSONArray("specializations").length()>0) || (feedInfoObj.has("locations") && feedInfoObj.optJSONArray("locations") != null && feedInfoObj.optJSONArray("locations").length()>0)) {
                specializationsArray.clear();
                locationsArray.clear();
                preferred_location_et.setVisibility(View.VISIBLE);
                preferred_speciality_et.setVisibility(View.VISIBLE);
                locationsAndSpecializationsMandatory=true;
                    for (int i = 0; i < feedInfoObj.optJSONArray("specializations").length(); i++) {
                        if (feedInfoObj.optJSONArray("specializations").optJSONObject(i) != null) {
                            JobLocationSpecializationInfo jobLocationSpecializationInfo=new JobLocationSpecializationInfo();
                            jobLocationSpecializationInfo.setName(feedInfoObj.optJSONArray("specializations").optJSONObject(i).optString("specialization"));
                            jobLocationSpecializationInfo.setId(feedInfoObj.optJSONArray("specializations").optJSONObject(i).optInt("specialization_id"));
                            jobLocationSpecializationInfo.setType("specializations");
                            jobLocationSpecializationInfo.setSelection(false);
                            specializationsArray.add(jobLocationSpecializationInfo);
                        }
                    }
                    for (int i = 0; i < feedInfoObj.optJSONArray("locations").length(); i++) {
                        if (feedInfoObj.optJSONArray("locations").optJSONObject(i) != null) {
                            JobLocationSpecializationInfo jobLocationSpecializationInfo=new JobLocationSpecializationInfo();
                            jobLocationSpecializationInfo.setName(feedInfoObj.optJSONArray("locations").optJSONObject(i).optString("location"));
                            jobLocationSpecializationInfo.setId(feedInfoObj.optJSONArray("locations").optJSONObject(i).optInt("location_id"));
                            jobLocationSpecializationInfo.setType("locations");
                            jobLocationSpecializationInfo.setSelection(false);
                            locationsArray.add(jobLocationSpecializationInfo);
                        }
                    }
                    if(specializationsArray.size()==1){
                        preferred_speciality_et.setText(specializationsArray.get(0).getName());
                    }else{
                        preferred_speciality_et.setText("Select");
                    }
                    if(locationsArray.size()==1){
                        preferred_location_et.setText(locationsArray.get(0).getName());

                    }else{
                        preferred_location_et.setText("Select");

                    }

            }else{
                preferred_location_et.setVisibility(View.GONE);
                preferred_speciality_et.setVisibility(View.GONE);
                locationsAndSpecializationsMandatory=false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        preferred_speciality_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isApplied||specializationsArray.size()==1) {
                    return;
                }
                if (AppUtil.isConnectingToInternet(getContext())) {
                    /*
                     * Sort the list in alphabetical order
                     */
                    Collections.sort(specializationsArray, new Comparator() {
                        @Override
                        public int compare(Object obj1, Object obj2) {
                            JobLocationSpecializationInfo jobLocationSpecializationInfo1 = (JobLocationSpecializationInfo)obj1;
                            JobLocationSpecializationInfo jobLocationSpecializationInfo2 = (JobLocationSpecializationInfo)obj2;
                            return jobLocationSpecializationInfo1.getName().compareToIgnoreCase(jobLocationSpecializationInfo2.getName());
                        }
                    });

                    displayBottomSheet(specializationsArray,"specializations");


                }

            }
        });
        preferred_location_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isApplied||locationsArray.size()==1) {
                    return;
                }
                if (AppUtil.isConnectingToInternet(getContext())) {
                    /*
                     * Sort the list in alphabetical order
                     */
                    Collections.sort(locationsArray, new Comparator() {
                        @Override
                        public int compare(Object obj1, Object obj2) {
                            JobLocationSpecializationInfo jobLocationSpecializationInfo1 = (JobLocationSpecializationInfo)obj1;
                            JobLocationSpecializationInfo jobLocationSpecializationInfo2 = (JobLocationSpecializationInfo)obj2;
                            return jobLocationSpecializationInfo1.getName().compareToIgnoreCase(jobLocationSpecializationInfo2.getName());
                        }
                    });
                    displayBottomSheet(locationsArray,"locations");
                }

            }
        });
        attachCVtext.setOnClickListener(v -> {
            if (isApplied) {
                return;
            }
            if (cvAttachmentObj == null) {
                attachmentType = "cv";
                browseDocuments();
            } else if (checkIfFileExists(cvAttachmentObj.getFileAttachmentPath())) {
                try {
                    File file = getJobAttachmentFile(cvAttachmentObj.getFileAttachmentPath());
                    Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
                    String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    myIntent.setDataAndType(FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", file), mimetype);
                    myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    getActivity().startActivity(myIntent);
                } catch (Exception e) {
                    // TODO: handle exception
                    String data = e.getMessage();
                }
            }
        });

        attachCovering.setOnClickListener(v -> {
            if (isApplied) {
                return;
            }
            if (coveringAttachmentObj == null) {
                attachmentType = "covering";
                browseDocuments();
            } else if (checkIfFileExists(coveringAttachmentObj.getFileAttachmentPath())) {
                try {
                    File file = getJobAttachmentFile(coveringAttachmentObj.getFileAttachmentPath());
                    Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
                    String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    myIntent.setDataAndType(FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", file), mimetype);
                    myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    getActivity().startActivity(myIntent);
                } catch (Exception e) {
                    // TODO: handle exception
                    String data = e.getMessage();
                }
            }
        });
        cvAttachmentCloseBtn.setOnClickListener(v -> {
            if (isApplied) {
                return;
            }
            cleanUpJobFeedAttachments(cvAttachmentObj);
            if (attachmentsList.contains(cvAttachmentObj)) {
                attachmentsList.remove(cvAttachmentObj);
            }
            cvAttachmentObj = null;
            attachCVtext.setText("Attach file");
            ll_cvDeleteButton.setVisibility(View.GONE);
        });

        coveringAttachmentCloseBtn.setOnClickListener(v -> {
            if (isApplied) {
                return;
            }
            cleanUpJobFeedAttachments(coveringAttachmentObj);
            if (attachmentsList.contains(coveringAttachmentObj)) {
                attachmentsList.remove(coveringAttachmentObj);
            }
            coveringAttachmentObj = null;
            attachCovering.setText("Attach file");
            ll_coveringDeleteButton.setVisibility(View.GONE);
        });

        applyJobButton.setOnClickListener(v -> {
            if (feedInfoObj == null) {
                return;
            }
            if (applicationType == 3) {
                submitJobApplicationAPIcall(null, getSubmitApplicationRequest());
                AppUtil.openLinkInBrowser(feedInfoObj.optString("application_link"), getActivity());
                return;
            }
            if (AppUtil.validateEmail(userEmail.getText().toString())) {
                if (userMobileNumber.getText().toString().length() >= 10) {
                    String overAllExperience = experience_autocomplete_view.getText().toString().trim();
                    String location = city_job_edit.getText().toString().trim();
                    String preferred_speciality = preferred_speciality_et.getText().toString();
                    String preferred_location = preferred_location_et.getText().toString();
                    if(preferred_speciality.equalsIgnoreCase("Select")){
                        preferred_speciality="";
                    }
                    if(preferred_location.equalsIgnoreCase("Select")){
                        preferred_location="";
                    }

                    if (validationUtils.isValidMandatoryFieldForSpecialtyAndExperience(itemSelected, overAllExperience, location,preferred_speciality,preferred_location,locationsAndSpecializationsMandatory)) {
                        if (!convertedExpList.contains(overAllExperience)) {
                            Toast.makeText(getActivity(), R.string.select_valid_experience, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!isDefaultLocationSelected) {
                            if (cityJson != null && cityJson.has("result")) {
                                Log.i("location", String.valueOf(cityJson));
                            } else {
                                locationFieldValidation.isValidMandatoryFieldForLocation();
                                return;
                            }
                        }

                        if (feedInfoObj.optBoolean("cv_required")) {
                            if (cvAttachmentObj != null) {
                                if (feedInfoObj.optBoolean("cover_letter_required")) {
                                    if (coveringAttachmentObj != null) {
                                        submitJobApplicationAction();
                                    } else {
                                        Toast.makeText(getActivity(), "Please attach covering letter", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    submitJobApplicationAction();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Please attach your CV", Toast.LENGTH_SHORT).show();
                            }

                        } else if (feedInfoObj.optBoolean("cover_letter_required")) {
                            if (coveringAttachmentObj != null) {
                                submitJobApplicationAction();
                            } else {
                                Toast.makeText(getActivity(), "Please attach covering letter", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            submitJobApplicationAction();
                        }


                    }

                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.prompt_for_valid_phoneNumber), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.valid_email), Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }

    private void displayBottomSheet(ArrayList<JobLocationSpecializationInfo> locationSpecialityList, String from) {


        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        final View sheetView = getActivity().getLayoutInflater().inflate(R.layout.job_application_bottom_sheet, null);
        final ListView specialityList = (ListView) sheetView.findViewById(R.id.job_speciality_location_list);
        TextView done_btn = (TextView) sheetView.findViewById(R.id.done_btn);
        TextView Preferred_location_speciality_text = (TextView) sheetView.findViewById(R.id.Preferred_location_speciality_text);
        if(from.equalsIgnoreCase("specializations")) {
            Preferred_location_speciality_text.setText("Preferred Speciality");
        }else{
            Preferred_location_speciality_text.setText("Preferred Job Location");
        }



        mBottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                FrameLayout bottomSheet = mBottomSheetDialog.findViewById(R.id.design_bottom_sheet);
                if(bottomSheet == null)
                    return;

                //Retrieve button parameters
                buttonLayoutParams = (ConstraintLayout.LayoutParams) done_btn.getLayoutParams();

                //Retrieve bottom sheet parameters
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
                ViewGroup.LayoutParams bottomSheetLayoutParams = bottomSheet.getLayoutParams();
                bottomSheetLayoutParams.height = getBottomSheetDialogDefaultHeight();

                expandedHeight = bottomSheetLayoutParams.height;
                int peekHeight = (int) (expandedHeight/1.3); //Peek height to 70% of expanded height (Change based on your view)

                //Setup bottom sheet
                bottomSheet.setLayoutParams(bottomSheetLayoutParams);
                BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(false);
                BottomSheetBehavior.from(bottomSheet).setPeekHeight(peekHeight);
                BottomSheetBehavior.from(bottomSheet).setHideable(true);

                //Calculate button margin from top
                buttonHeight = done_btn.getHeight() + 40; //How tall is the button + experimental distance from bottom (Change based on your view)
                collapsedMargin = peekHeight - buttonHeight; //Button margin in bottom sheet collapsed state
                buttonLayoutParams.topMargin = collapsedMargin;
                done_btn.setLayoutParams(buttonLayoutParams);

                //OPTIONAL - Setting up recyclerview margins
                ConstraintLayout.LayoutParams recyclerLayoutParams = (ConstraintLayout.LayoutParams) specialityList.getLayoutParams();
                float k = (buttonHeight - 60) / (float) buttonHeight; //60 is amount that you want to be hidden behind button
                recyclerLayoutParams.bottomMargin = (int) (k*buttonHeight); //Recyclerview bottom margin (from button)
                specialityList.setLayoutParams(recyclerLayoutParams);
            }
        });

        jobApplicationSpecialityLocationListAdapter = new JobApplicationSpecialityLocationListAdapter(getActivity(),locationSpecialityList, "ApplyJob", new JobSpecializationLocationInterface() {
            @Override
            public void OnCustomClick(int position, boolean b, JobLocationSpecializationInfo jobLocationSpecializationInfo) {
                if(from.equalsIgnoreCase("specializations")) {
                    if (b) {
                        specializationsArray.get(position).setSelection(true);
                    } else {
                        specializationsArray.get(position).setSelection(false);
                    }
                }else{
                    if(b){
                        locationsArray.get(position).setSelection(true);
                    }else{
                        locationsArray.get(position).setSelection(false);
                    }
                }
                jobApplicationSpecialityLocationListAdapter.notifyDataSetChanged();
            }
        });
        specialityList.setAdapter(jobApplicationSpecialityLocationListAdapter);
        mBottomSheetDialog.setContentView(sheetView);
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) sheetView.getParent());
        //mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                if(slideOffset > 0) //Sliding happens from 0 (Collapsed) to 1 (Expanded) - if so, calculate margins
                    buttonLayoutParams.topMargin = (int) (((expandedHeight - buttonHeight) - collapsedMargin) * slideOffset + collapsedMargin);
                else //If not sliding above expanded, set initial margin
                    buttonLayoutParams.topMargin = collapsedMargin;
                done_btn.setLayoutParams(buttonLayoutParams); //Set layout params to button (margin from top)

            }
        });
        mBottomSheetDialog.show();
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(from.equalsIgnoreCase("specializations")) {
                    selectedSpecialization.clear();
                    for (int i = 0; i < specializationsArray.size(); i++) {
                        if (specializationsArray.get(i).getSelection()) {
                            selectedSpecialization.add(specializationsArray.get(i));
                        }
                    }
                    if (selectedSpecialization.size() != 0) {
                        if (selectedSpecialization.size() > 1) {
                            if (selectedSpecialization.size() >= 3) {
                                int count = selectedSpecialization.size() - 1;
                                preferred_speciality_et.setText(selectedSpecialization.get(0).getName() + "+" + count + " More");
                            } else {
                                preferred_speciality_et.setText(selectedSpecialization.get(0).getName() + ", " + selectedSpecialization.get(1).getName());
                            }
                        } else {
                            preferred_speciality_et.setText(selectedSpecialization.get(0).getName());
                        }
                    } else {
                        preferred_speciality_et.setText("select");
                    }
                }else{
                    selectedLocations.clear();
                    for(int i=0; i<locationsArray.size(); i++){
                        if(locationsArray.get(i).getSelection()){
                            selectedLocations.add(locationsArray.get(i));
                        }
                    }
                    if(selectedLocations.size()!=0) {
                        if (selectedLocations.size() > 1) {
                            if (selectedLocations.size() >= 3) {
                                int count = selectedLocations.size() - 1;
                                preferred_location_et.setText(selectedLocations.get(0).getName() +"+"+count+ " More");
                            } else {
                                preferred_location_et.setText(selectedLocations.get(0).getName() + ", " + selectedLocations.get(1).getName());
                            }
                        } else {
                            preferred_location_et.setText(selectedLocations.get(0).getName());
                        }
                    }else{
                        preferred_location_et.setText("Select");
                    }
                }
                mBottomSheetDialog.dismiss();
            }
        });
    }

    private void jobDetailsTableVisibility(boolean isVisible) {
        if (isVisible) {
            jobAttachmentsTable.setVisibility(View.VISIBLE);
        } else {
            jobAttachmentsTable.setVisibility(View.GONE);
        }
    }

    private void editTextUIChanges(boolean isApplied) {
        if (isApplied) {
            userEmail.setKeyListener(null);
            userMobileNumber.setKeyListener(null);
            speciality_job_edit.setKeyListener(null);
            city_job_edit.setKeyListener(null);
            experience_autocomplete_view.setKeyListener(null);
        }
    }

    private void submitJobApplicationAction() {
        if (!AppUtil.isConnectingToInternet(getActivity())) {
            return;
        }
        int attachmentsListSize = attachmentsList.size();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.show();
        if (attachmentsListSize > 0) {
            uploadStatusCount = 0;
            for (int i = 0; i < attachmentsListSize; i++) {
                AttachmentInfo attachmentInfoObj = attachmentsList.get(i);
                if (attachmentInfoObj == null) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), "Unable to upload attachment,please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                File attFile = new File(attachmentInfoObj.getFileAttachmentPath());
                new AWSHelperClass(getActivity(), awsKeys, attFile, userId, userUUID, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String url) {
                        if (url != null && !url.isEmpty()) {
                            //String extension = finalAttFile.getAbsolutePath().substring(finalAttFile.getAbsolutePath().lastIndexOf(".") + 1);
                            uploadStatusCount++;
                            if (attachmentInfoObj.getAttachmentType().equalsIgnoreCase("cv")) {
                                uploadedCVUrl = url;
                            } else if (attachmentInfoObj.getAttachmentType().equalsIgnoreCase("covering")) {
                                uploadedCoveringLetterUrl = url;
                            }
                            if (attachmentsListSize == uploadStatusCount) {
                                submitJobApplicationAPIcall(mProgressDialog, getSubmitApplicationRequest());
                            }
                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getActivity(), "Unable to upload attachment,Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        } else {
            submitJobApplicationAPIcall(mProgressDialog, getSubmitApplicationRequest());

        }
    }

    private void submitJobApplicationAPIcall(ProgressDialog mProgressDialog, JSONObject
            submitApplicationRequest) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DocID", realmManager.getUserUUID(realm));
            jsonObject.put("DocSpeciality", realmManager.getDocSpeciality(realm));
            jsonObject.put("FeedId",feedId);
            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "SubmitApplication", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.SUBMIT_JOB_APPLICATION_API_V2, submitApplicationRequest.toString(), "APPPLY_JOB", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                JSONObject responseObj = null;
                try {
                    responseObj = new JSONObject(successResponse);
                    if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if (applicationType != 3) {
                            Toast.makeText(getActivity(), "Application submitted successfully", Toast.LENGTH_SHORT).show();
                            applyJobButton.setVisibility(View.GONE);
                            alreadySubmittedTv.setVisibility(View.VISIBLE);
                            isApplied = true;
                            editTextUIChanges(isApplied);
                            //jobDetailsTableVisibility(false);
                            for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                listener.notifyUIWithJobApplyStatus(feedId, responseObj);
                            }
                        }

                    } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        String errorMsg = getResources().getString(R.string.unable_to_connect_server);
                        if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                            errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                        }
                        if (applicationType != 3) {
                            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                if (applicationType != 3) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                }
            }
        }).sendSinglePartRequest();
    }

    private void convertStringToNum() {
        String experienceSplitedString = null;

        String overAllExperienceWithYears = experience_autocomplete_view.getText().toString().trim();
        if (!convertedExpList.contains(overAllExperienceWithYears)) {
            Toast.makeText(getActivity(), R.string.select_valid_experience, Toast.LENGTH_SHORT).show();
            return;
        }
        String[] splited = overAllExperienceWithYears.split(" ");
        if (splited.length > 0) {
            experienceSplitedString = splited[0];
        }
        if (experienceSplitedString != null) {
            if (experienceSplitedString.contains("-")) {
                overallExperienceInInt = 0;
            } else {
                overallExperienceInInt = Integer.parseInt(experienceSplitedString);
            }
        }
    }

    private JSONObject getSubmitApplicationRequest() {
        JSONObject requestObj = new JSONObject();
        try {
            String overAllExperience = experience_autocomplete_view.getText().toString().trim();
            String location = city_job_edit.getText().toString().trim();
            String experienceSplitedString = null;

            String overAllExperienceWithYears = experience_autocomplete_view.getText().toString().trim();
            String[] splited = overAllExperienceWithYears.split(" ");
            if (splited.length > 0) {
                experienceSplitedString = splited[0];
            }
            if (experienceSplitedString != null && !experienceSplitedString.equalsIgnoreCase("")) {
                if (experienceSplitedString.contains("-")) {
                    overallExperienceInInt = 0;
                } else {
                    overallExperienceInInt = Integer.parseInt(experienceSplitedString);
                }
            }
            requestObj.put(RestUtils.TAG_USER_ID, userId);
            requestObj.put("user_email", userEmail.getText().toString());
            String selectedCountryCode = countryCodePicker.getSelectedCountryCode();
            String completePhNumber = selectedCountryCode.concat(userMobileNumber.getText().toString());
            requestObj.put("user_phone", completePhNumber);
            requestObj.put("job_id", feedInfoObj.optInt("job_id"));
            if (!TextUtils.isEmpty(speciality_job_edit.getText())) {
                requestObj.put("specialization", speciality_job_edit.getText().toString());
            } else {
                requestObj.put("specialization", "");
            }
            if (isDefaultLocationSelected) {
                if (!TextUtils.isEmpty(city_job_edit.getText())) {
                    requestObj.put(RestUtils.TAG_LOCATION, city_job_edit.getText().toString());
                } else {
                    requestObj.put(RestUtils.TAG_LOCATION, "");
                }
            } else {
                if (cityJson == null) {
                    requestObj.put("user_google_city_info", new JSONObject());
                } else {
                    requestObj.put("user_google_city_info", cityJson);
                }
                requestObj.put("is_exact_city", isExactCity);
            }
            requestObj.put("experience", overallExperienceInInt);
            if ((feedInfoObj.has("specializations") && feedInfoObj.optJSONArray("specializations") != null && feedInfoObj.optJSONArray("specializations").length()>0) || (feedInfoObj.has("locations") && feedInfoObj.optJSONArray("locations") != null && feedInfoObj.optJSONArray("locations").length()>0)) {
                requestObj.put("locations", getLocationsId());
                requestObj.put("specializations", getSpecializationsId());
            }else{
                JSONArray locationsId = new JSONArray();
                JSONArray specializationsId = new JSONArray();
                requestObj.put("locations",locationsId);
                requestObj.put("specializations", specializationsId);
            }

            int status = 2;
            if (applicationType == 3) {
                status = 1;
            }
            requestObj.put("status", status);//
            requestObj.put("application_type", applicationType);
            if (!TextUtils.isEmpty(uploadedCVUrl)) {
                requestObj.put("applicant_cv", uploadedCVUrl);
            }
            if (!TextUtils.isEmpty(uploadedCoveringLetterUrl)) {
                requestObj.put("applicant_cover_letter", uploadedCoveringLetterUrl);
            }
            if (applicationType == 3) {
                requestObj.put("clicked_on", System.currentTimeMillis());
            } else {
                requestObj.put("applied_on", System.currentTimeMillis());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj;
    }

    private JSONArray getLocationsId() {

        JSONArray locationsId = new JSONArray();
        for(int i=0; i<locationsArray.size(); i++){
            if(locationsArray.get(i).getSelection()){
                locationsId.put(locationsArray.get(i).getId());
            }
        }
        return locationsId;
    }
    private JSONArray getSpecializationsId() {

        JSONArray specializationsId = new JSONArray();
        for(int i=0; i<specializationsArray.size(); i++){
            if(specializationsArray.get(i).getSelection()){
                specializationsId.put(specializationsArray.get(i).getId());
            }
        }
        return specializationsId;
    }

    private void browseDocuments() {
        String[] mimeTypes =
                {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == OPEN_DIRECTORY_REQUEST_CODE) {
                if (data != null && data.getData() != null) {
                    new CopyFileToAppDirExecutor(getActivity(), data.getData(), ".Whitecoats/JobFeedAttachments", attachmentInfo -> {
                        if (attachmentInfo != null) {
                            if (attachmentType.equalsIgnoreCase("cv")) {
                                cvAttachmentObj = attachmentInfo;
                                cvAttachmentObj.setAttachmentType("cv");
                                attachCVtext.setText(attachmentInfo.getAttachmentName());
                                ll_cvDeleteButton.setVisibility(View.VISIBLE);
                                attachmentsList.add(cvAttachmentObj);
                            } else if (attachmentType.equalsIgnoreCase("covering")) {
                                coveringAttachmentObj = attachmentInfo;
                                coveringAttachmentObj.setAttachmentType("covering");
                                attachCovering.setText(attachmentInfo.getAttachmentName());
                                ll_coveringDeleteButton.setVisibility(View.VISIBLE);
                                attachmentsList.add(coveringAttachmentObj);
                            }
                        }
                    }).executeCopyFile();
                } else {
                    Log.d("JOB_ATTACHMENT", "File uri not found {}");
                }
            }
        }
    }

    private void cleanUpJobFeedAttachments(AttachmentInfo attachmentInfo) {
        if (attachmentInfo != null) {
            File file = AppUtil.getExternalStoragePathFile(getActivity(), ".Whitecoats/JobFeedAttachments/" + attachmentInfo.getFileAttachmentPath());
            if (file.exists()) {
              boolean del= file.delete();
              Log.i("File Del",""+del);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (attachmentsList != null) {
            for (int i = 0; i < attachmentsList.size(); i++) {
                cleanUpJobFeedAttachments(attachmentsList.get(i));
            }
        }
    }


    private boolean checkIfFileExists(String path) {
        if (getJobAttachmentFile(path).exists()) {
            return true;
        }
        return false;
    }

    private File getJobAttachmentFile(String path) {
        File file = new File(path);
        return file;
    }

    public void setLatestSocialInteractionData(JSONObject socialInteractionObj) {
        if (socialInteractionObj != null) {
            if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                latestCommentLayout.setVisibility(View.VISIBLE);
                AppUtil.displayLatestCommentUI(getActivity(), userId, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), commented_user_img, commented_user_name, comment_text);
            } else {
                latestCommentLayout.setVisibility(View.GONE);
            }
        }
    }

    private void loadSpecialization_City_Data() {

        dataAdapterexpience = new ArrayAdapter<String>
                (getActivity(), R.layout.textview_item, experience);
        dataAdapterexpience.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experience_autocomplete_view.setThreshold(1);
        experience_autocomplete_view.setAdapter(dataAdapterexpience);

        getSpeciality();

        specialityArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, specialityArrayList);
        speciality_job_edit.setAdapter(specialityArrayAdapter);
        speciality_job_edit.setThreshold(2);


        speciality_job_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {
                itemSelected = arg0.getItemAtPosition(arg2).toString();

            }
        });


        city_job_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    isCityEditText = false;
                } else {
                    isCityEditText = true;
                }

            }
        });

        new AwsAndGoogleKeysServiceClass(getActivity(), userId, userUUID, new AwsAndGoogleKey() {

            @Override
            public void awsAndGoogleKey(String google_api_key, String aws_key) {
                api_key = google_api_key;
                Places.initialize(getActivity().getApplicationContext(), api_key);

                List<Prediction> predictions = new ArrayList<>();
                PlacesAutoCompleteAdapter placesAutoCompleteAdapter = new PlacesAutoCompleteAdapter(getActivity().getApplicationContext(),
                        predictions, false, api_key, userId);


                city_job_edit.setThreshold(1);
                city_job_edit.setAdapter(placesAutoCompleteAdapter);
                city_job_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Prediction prediction = predictions.get(position);
                        if (prediction.getId()!=null && prediction.getId().equals("-1")) {
                            if (prediction.getDescription().equalsIgnoreCase("Use Current Location")) {
                                city_job_edit.setText("");
                            }
                            isExactCity = true;
                            if (!requestPermissionForLocationFromFragment(getActivity())) {
                                if (AppConstants.neverAskAgain_Location) {
                                    AppUtil.showLocationServiceDenyAlert(getActivity());
                                }
                            } else {
                                locationHelperObj = new LocationHelperClass(getActivity(), new LocationCaputerListner() {
                                    @Override
                                    public void onLocationCapture(Location location) {
                                        if (location != null) {
                                            double lat = location.getLatitude();
                                            double longi = location.getLongitude();
                                            requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(getActivity(), lat, longi, api_key, new OnReceiveResponse() {
                                                @Override
                                                public void onSuccessResponse(String successResponse) {
                                                    try {
                                                        JSONObject responseJsonObject = new JSONObject(successResponse);
                                                        if (responseJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                            AppUtil.getAddressDetailsUsingAPI(getActivity(), successResponse, api_key, new OnlocationApiFinishedListener() {
                                                                @Override
                                                                public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject currentLocJsonObject) {
                                                                    if (currentLocJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {

                                                                        if (apiStringHashMap.containsKey("city")) {
                                                                            city_job_edit.setText(apiStringHashMap.get("city"));
                                                                            city_job_edit.setSelection(city_job_edit.length());
                                                                            city_job_edit.dismissDropDown();
                                                                        }
                                                                        cityJson = currentLocJsonObject;
                                                                    } else {
                                                                        String errorMsg = "Unable to fetch Geocode Details";
                                                                        if (currentLocJsonObject.has("error_message")) {
                                                                            errorMsg = currentLocJsonObject.optString("error_message");
                                                                        }
                                                                        JSONObject errorObj = new JSONObject();
                                                                        try {
                                                                            errorObj.put("screen", "UserProfile");
                                                                            errorObj.put("searchKey", city_job_edit.getText().toString());
                                                                            errorObj.put("errorMsg", errorMsg);
                                                                            AppUtil.logUserActionEvent(userId, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onLocationApiError(String error) {
                                                                    JSONObject errorObj = new JSONObject();
                                                                    try {
                                                                        errorObj.put("screen", "UserProfile");
                                                                        errorObj.put("searchKey", city_job_edit.getText().toString());
                                                                        errorObj.put("errorMsg", error);
                                                                        AppUtil.logUserActionEvent(userId, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                        } else {
                                                            cityJson = null;
                                                            String errorMsg = "Unable to fetch User current location";
                                                            if (responseJsonObject.has("error_message")) {
                                                                errorMsg = responseJsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj = new JSONObject();
                                                            try {
                                                                errorObj.put("screen", "UserProfile");
                                                                errorObj.put("searchKey", city_job_edit.getText().toString());
                                                                errorObj.put("errorMsg", errorMsg);
                                                                AppUtil.logUserActionEvent(userId, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
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
                                                        errorObj.put("screen", "UserProfile");
                                                        errorObj.put("searchKey", city_job_edit.getText().toString());
                                                        errorObj.put("errorMsg", errorResponse);
                                                        AppUtil.logUserActionEvent(userId, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
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
                            requestForPlacesUsingPlaceId = new RequestForPlacesUsingPlaceId(getActivity(), prediction.getPlaceId(), api_key, new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {

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
                                                            city_job_edit.setText(addressComponent.optString("long_name"));
                                                            city_job_edit.setSelection(city_job_edit.length());
                                                            city_job_edit.dismissDropDown();
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
                                                errorObj.put("screen", "UserProfile");
                                                errorObj.put("searchKey", city_job_edit.getText().toString());
                                                errorObj.put("errorMsg", errorMsg);
                                                AppUtil.logUserActionEvent(userId, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
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
                                        errorObj.put("screen", "UserProfile");
                                        errorObj.put("searchKey", city_job_edit.getText().toString());
                                        errorObj.put("errorMsg", errorResponse);
                                        AppUtil.logUserActionEvent(userId, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
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
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("beforeTextChanged", String.valueOf(charSequence));
        if (isCityEditText) {
            isDefaultLocationSelected = false;
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("onTextChanged", String.valueOf(charSequence));
        if (isCityEditText) {
            cityJson = new JSONObject();
            isDefaultLocationSelected = false;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        Log.d("afterTextChanged", String.valueOf(editable));
        if (isCityEditText) {
            isDefaultLocationSelected = false;
        }
    }

    public void getSpeciality() {
        specialityViewModel.getSpeciality(getActivity()).observe(getActivity(), new Observer<String>() {
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
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    locationHelperObj = new LocationHelperClass(getActivity(), new LocationCaputerListner() {
                        @Override
                        public void onLocationCapture(Location location) {
                            if (location != null) {
                                double lat = location.getLatitude();
                                double longi = location.getLongitude();
                                requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(getActivity(), lat, longi, api_key, new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        try {
                                            JSONObject responseJsonObject = new JSONObject(successResponse);
                                            if (responseJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                AppUtil.getAddressDetailsUsingAPI(getActivity(), successResponse, api_key, new OnlocationApiFinishedListener() {
                                                    @Override
                                                    public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject jsonObject) {
                                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")) {
                                                            if (apiStringHashMap.containsKey("city")) {
                                                                city_job_edit.setText(apiStringHashMap.get("city"));
                                                                city_job_edit.setSelection(city_job_edit.length());
                                                                city_job_edit.dismissDropDown();
                                                            }
                                                            isExactCity = true;
                                                            cityJson = jsonObject;
                                                        } else {
                                                            String errorMsg = "Unable to fetch Geocode Details";
                                                            if (jsonObject.has("error_message")) {
                                                                errorMsg = jsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj = new JSONObject();
                                                            try {
                                                                errorObj.put("screen", "UserProfile");
                                                                errorObj.put("searchKey", city_job_edit.getText().toString());
                                                                errorObj.put("errorMsg", errorMsg);
                                                                AppUtil.logUserActionEvent(userId, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onLocationApiError(String error) {
                                                        JSONObject errorObj = new JSONObject();
                                                        try {
                                                            errorObj.put("screen", "UserProfile");

                                                            errorObj.put("searchKey", city_job_edit.getText().toString());
                                                            errorObj.put("errorMsg", error);
                                                            AppUtil.logUserActionEvent(userId, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                            } else {
                                                String errorMsg = "Unable to fetch User current location";
                                                if (responseJsonObject.has("error_message")) {
                                                    errorMsg = responseJsonObject.optString("error_message");
                                                }
                                                JSONObject errorObj = new JSONObject();
                                                try {
                                                    errorObj.put("screen", "UserProfile");
                                                    errorObj.put("searchKey", city_job_edit.getText().toString());

                                                    errorObj.put("errorMsg", errorMsg);
                                                    AppUtil.logUserActionEvent(userId, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
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
                                            errorObj.put("screen", "UserProfile");
                                            errorObj.put("searchKey", city_job_edit.getText().toString());
                                            errorObj.put("errorMsg", errorResponse);
                                            AppUtil.logUserActionEvent(userId, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj), getActivity());
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

    public boolean requestPermissionForLocationFromFragment(Context context) {
        int LocationPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (LocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PermissionsConstants.LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    //Calculates height for 90% of fullscreen
    private int getBottomSheetDialogDefaultHeight() {
        return getWindowHeight() * 90 / 100;
    }

    //Calculates window height for fullscreen use
    private int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) requireContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}