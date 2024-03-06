package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

/**
 * Created by lokesh on 5/13/2015.
 */
public class AcademicActivity extends BaseActionBarActivity {

    EditText passing_year_edit;
    AutoCompleteTextView degree_edit, college_edit, university_edit;
    TextView year_text_error, degree_text_error, cyear_text_error;
    RelativeLayout delete_layout;
    Button add_another_btn;
    CheckBox currently_checkbox;
    TextView next_button;
    private long mLastClickTime = 0;

    private static final String TAG_STATUS = "status";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DEGREE = "degrees";
    private static final String TAG_UNIVERSITY = "universities";
    private Realm realm;
    private RealmManager realmManager;
    String oper = "add";
    int acadId = 0;
    AcademicInfo academicInfo = null;
    static int add_another = 0;
    boolean add_btn_visibility = false;

    private ArrayList<String> degree_array = new ArrayList<String>();
    private ArrayList<String> college_array = new ArrayList<String>();
    private ArrayList<String> university_array = new ArrayList<String>();
    private List<String> total_college_array = new ArrayList<String>();

    private HashMap<String, ArrayList<String>> university_college = new HashMap<String, ArrayList<String>>();
    ArrayAdapter<String> adapter, college_adapter, university_adapter;
    int doctorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic);
        initialize();
        validationUtils = new ValidationUtils(AcademicActivity.this, new EditText[]{degree_edit, passing_year_edit}, new TextView[]{degree_text_error, year_text_error});

        try {
            ArrayList<String> searchkeys = new ArrayList<String>();
            searchkeys.add(TAG_DEGREE);
            searchkeys.add(TAG_UNIVERSITY);
            if (isConnectingToInternet()) {
                new AutoSuggestionsAsync(AcademicActivity.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
            }

            adapter = new ArrayAdapter<String>(AcademicActivity.this, android.R.layout.simple_list_item_1, degree_array);
            college_adapter = new ArrayAdapter<String>(AcademicActivity.this, android.R.layout.simple_list_item_1, college_array);
            university_adapter = new ArrayAdapter<String>(AcademicActivity.this, android.R.layout.simple_list_item_1, university_array);

            college_edit.setAdapter(college_adapter);
            degree_edit.setAdapter(adapter);
            university_edit.setAdapter(university_adapter);

            degree_edit.setThreshold(1);
            college_edit.setThreshold(1);
            university_edit.setThreshold(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        university_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String item = parent.getItemAtPosition(position).toString();
                    if (university_college.containsKey(item)) {
                        college_array.clear();
                        for (int i = 0; i < university_college.get(item).size(); i++) {
                            college_array.add(university_college.get(item).get(i));
                        }
                        college_adapter = new ArrayAdapter<String>(AcademicActivity.this, android.R.layout.simple_list_item_1, college_array);
                        college_edit.setAdapter(college_adapter);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        university_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (total_college_array.contains(college_edit.getText().toString())) {
                    college_array.clear();
                    college_edit.setText("");
                    college_adapter = new ArrayAdapter<String>(AcademicActivity.this, android.R.layout.simple_list_item_1, college_array);
                    college_edit.setAdapter(college_adapter);
                }
                if (!university_college.containsKey(s.toString())) {
                    college_array.clear();
                    college_adapter = new ArrayAdapter<String>(AcademicActivity.this, android.R.layout.simple_list_item_1, college_array);
                    college_edit.setAdapter(college_adapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /**Set cursor **/
        degree_edit.setSelection(0);
        university_edit.setSelection(0);
        college_edit.setSelection(0);


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

            degree_edit = _findViewById(R.id.degree_edit);
            college_edit = _findViewById(R.id.college_autoedit);
            university_edit = _findViewById(R.id.university_autoedit);
            passing_year_edit = _findViewById(R.id.passing_year_edit);
            delete_layout = _findViewById(R.id.delete_layout);
            year_text_error = _findViewById(R.id.year_error_text);
            degree_text_error = _findViewById(R.id.degree_error_text);

            add_another_btn = _findViewById(R.id.add_another_btn);
            currently_checkbox = _findViewById(R.id.currently_pursuing_checkbox);


            currently_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((((CheckBox) v).isChecked())) {
                        passing_year_edit.setVisibility(View.GONE);
                        passing_year_edit.setText("");
                        validationUtils.clearError();

                    } else {
                        passing_year_edit.setText("");
                        passing_year_edit.setVisibility(View.VISIBLE);

                    }
                }
            });

            oper = "add";

            academicInfo = new AcademicInfo();
            academicInfo = (AcademicInfo) getIntent().getSerializableExtra("academicinfo");

            if (academicInfo != null) {
                oper = "update";
                acadId = academicInfo.getAcad_id();
                degree_edit.setText(academicInfo.getDegree());
                university_edit.setText(academicInfo.getUniversity());
                college_edit.setText(academicInfo.getCollege());
                passing_year_edit.setText(academicInfo.getPassing_year() + "");
                delete_layout.setVisibility(View.VISIBLE);
                currently_checkbox.setChecked(academicInfo.isCurrently_pursuing());
                if (currently_checkbox.isChecked()) {
                    passing_year_edit.setText("");
                    passing_year_edit.setVisibility(View.GONE);
                }
                add_another_btn.setVisibility(View.GONE);
            } else {
                academicInfo = new AcademicInfo();
            }

            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(this);
            doctorID = realmManager.getDoc_id(realm);
            mInflater = LayoutInflater.from(this);
            mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            next_button = (TextView) mCustomView.findViewById(R.id.next_button);
            mTitleTextView.setText(getString(R.string.str_Add_qualification));
            next_button.setText(getString(R.string.actionbar_save));
            if (oper.equals("update"))
                mTitleTextView.setText(getString(R.string.str_Edit_qualification));


            add_another_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        hideKeyboard();
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        } else {
                            mLastClickTime = SystemClock.elapsedRealtime();
                            if (isConnectingToInternet()) {
                                String passingyear = "";
                                String degree = degree_edit.getText().toString().trim();
                                int passing_year = 0;
                                if (!TextUtils.isEmpty(passing_year_edit.getText().toString()))
                                    passing_year = Integer.parseInt(passing_year_edit.getText().toString().trim());
                                passingyear = passing_year_edit.getText().toString().trim();
                                String university = university_edit.getText().toString().trim();
                                String college = college_edit.getText().toString().trim();
                                boolean currently_check = currently_checkbox.isChecked();
                                academicInfo.setAcad_id(acadId);
                                academicInfo.setDegree(degree);
                                academicInfo.setUniversity(university);
                                academicInfo.setCollege(college);
                                academicInfo.setCurrently_pursuing(currently_check);
                                academicInfo.setPassing_year(passing_year);
                                if (currently_check == true) {
                                    passing_year_edit.setText("");
                                    passingyear = passing_year_edit.getText().toString().trim();
                                }
                                if (validationUtils.isAcademicDetEnteder(degree, passingyear, currently_check)) {
                                    JSONObject requestObj = getAcademicJsonRequest(academicInfo);
                                    String URL = RestApiConstants.CREATE_USER_PROFILE;
                                    if (oper.equals(RestUtils.TAG_UPDATE)) {
                                        URL = RestApiConstants.UPDATE_USER_PROFILE;
                                    }
                                    add_btn_visibility = true;

                                    showProgress();
                                    new VolleySinglePartStringRequest(AcademicActivity.this, Request.Method.POST, URL, requestObj.toString(), "ACADEMIC_DETAILS", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            hideProgress();
                                            JSONObject responseObj = null;
                                            try {
                                                responseObj = new JSONObject(successResponse);
                                                if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                    handleAcademicDetailsResponse(responseObj);
                                                } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
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
                            }
                        }
                        delete_layout.setVisibility(View.GONE);
                        add_another_btn.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
                    }

                }
            });
            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        hideKeyboard();
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        } else {
                            mLastClickTime = SystemClock.elapsedRealtime();
                            if (isConnectingToInternet()) {
                                String degree = degree_edit.getText().toString().trim();
                                int passing_year = 0;
                                if (!TextUtils.isEmpty(passing_year_edit.getText().toString()))
                                    passing_year = Integer.parseInt(passing_year_edit.getText().toString().trim());
                                String university = university_edit.getText().toString().trim();
                                String college = college_edit.getText().toString().trim();
                                boolean currently_check = currently_checkbox.isChecked();
                                if (validationUtils.isAcademicDetEnteder(degree, passing_year_edit.getText().toString(), currently_check)) {
                                    academicInfo.setAcad_id(acadId);
                                    academicInfo.setDegree(degree);
                                    academicInfo.setUniversity(university);
                                    academicInfo.setCollege(college);
                                    academicInfo.setCurrently_pursuing(currently_check);
                                    academicInfo.setPassing_year(passing_year);
                                    if (currently_checkbox.isChecked()) {
                                        academicInfo.setPassing_year(0);
                                    }
                                    add_btn_visibility = false;

                                    /** Creating JSONObject **/
                                    JSONObject requestObj = getAcademicJsonRequest(academicInfo);
                                    String URL = RestApiConstants.CREATE_USER_PROFILE;
                                    if (oper.equals(RestUtils.TAG_UPDATE)) {
                                        URL = RestApiConstants.UPDATE_USER_PROFILE;
                                    }
                                    showProgress();
                                    new VolleySinglePartStringRequest(AcademicActivity.this, Request.Method.POST, URL, requestObj.toString(), "ACADEMIC_DETAILS", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            hideProgress();
                                            JSONObject responseObj = null;
                                            try {
                                                responseObj = new JSONObject(successResponse);
                                                if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                    handleAcademicDetailsResponse(responseObj);
                                                } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
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
                            }
                        }
                    } catch (Exception e){
                        hideProgress();
                        e.printStackTrace();
                    }

                }

            });


            delete_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        hideKeyboard();
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        } else {
                            mLastClickTime = SystemClock.elapsedRealtime();
                            if (isConnectingToInternet()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AcademicActivity.this);
                                builder.setCancelable(true);
                                builder.setTitle(getString(R.string.profile_delete));
                                builder.setMessage(getString(R.string.profile_delete_other));
                                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        oper = "delete";
                                        /**JSONObject Creation**/
                                        try {
                                            JSONObject requestObject = new JSONObject();
                                            JSONObject academicObj = new JSONObject();
                                            JSONArray academicArray = new JSONArray();
                                            academicObj.put(RestUtils.TAG_ACADEMIC_ID, academicInfo.getAcad_id());
                                            academicArray.put(academicObj);
                                            requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                            requestObject.put(RestUtils.TAG_ACADEMIC_HISTORY, academicArray);
                                            showProgress();
                                            new VolleySinglePartStringRequest(AcademicActivity.this, Request.Method.POST, RestApiConstants.DELETE_USER_PROFILE, requestObject.toString(), "ACADEMIC_DETAILS", new OnReceiveResponse() {
                                                @Override
                                                public void onSuccessResponse(String successResponse) {
                                                    hideProgress();
                                                    JSONObject responseObj = null;
                                                    try {
                                                        responseObj = new JSONObject(successResponse);
                                                        if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {

                                                            Toast.makeText(AcademicActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                                            realmManager.deleteAcademicInfo(realm, academicInfo.getAcad_id());
                                                            add_btn_visibility = false;
                                                            Intent in = new Intent();
                                                            setResult(Activity.RESULT_OK, in);
                                                            finish();


                                                        } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
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
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();


                            }
                        }
                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
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


        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }

    private JSONObject getAcademicJsonRequest(AcademicInfo academicInfo) {
        JSONObject requestObject = new JSONObject();
        try {
            JSONObject academicObj = new JSONObject();
            JSONArray academicArray = new JSONArray();
            if (oper.equals(RestUtils.TAG_UPDATE)) {
                academicObj.put(RestUtils.TAG_ACADEMIC_ID, academicInfo.getAcad_id());
            }
            academicObj.put(RestUtils.TAG_DEGREE, academicInfo.getDegree());
            academicObj.put(RestUtils.TAG_COLLEGE, academicInfo.getCollege());
            academicObj.put(RestUtils.TAG_UNIVERSITY, academicInfo.getUniversity());
            academicObj.put(RestUtils.TAG_CURRENTLY, academicInfo.isCurrently_pursuing());
            academicObj.put(RestUtils.TAG_PASSING_YEAR, academicInfo.getPassing_year());
            academicArray.put(academicObj);
            requestObject.put(RestUtils.TAG_USER_ID, doctorID);
            requestObject.put(RestUtils.TAG_ACADEMIC_HISTORY, academicArray);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return requestObject;
    }

    private void handleAcademicDetailsResponse(JSONObject jsonObject) {
        if (jsonObject.has(RestUtils.TAG_DATA)) {
            JSONObject data = jsonObject.optJSONObject(RestUtils.TAG_DATA);
            if (data.has(RestUtils.TAG_ACADEMIC_HISTORY)) {
                JSONArray acadHistory = data.optJSONArray(RestUtils.TAG_ACADEMIC_HISTORY);
                JSONObject academicObj = acadHistory.optJSONObject(0);
                academicInfo.setAcad_id(academicObj.optInt(RestUtils.TAG_ACADEMIC_ID));
                academicInfo.setDegree(academicObj.optString(RestUtils.TAG_DEGREE));
                academicInfo.setCollege(academicObj.optString(RestUtils.TAG_COLLEGE));
                academicInfo.setUniversity(academicObj.optString(RestUtils.TAG_UNIVERSITY));
                academicInfo.setPassing_year(academicObj.optInt(RestUtils.TAG_PASSING_YEAR));
                academicInfo.setCurrently_pursuing(academicObj.optBoolean(RestUtils.TAG_CURRENTLY));

                if (oper.equals("update")) {
                    Toast.makeText(AcademicActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                    realmManager.updateAcademicInfo(realm, academicInfo);
                    Intent in = new Intent();
                    setResult(Activity.RESULT_OK, in);
                    finish();

                } else {
                    realmManager.insertAcademic(realm, academicInfo);
                }
                academicInfo = new AcademicInfo();
                hideProgress();
                if (add_btn_visibility == false) {
                    hideProgress();
                    Toast.makeText(AcademicActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                    Intent in = new Intent();
                    setResult(Activity.RESULT_OK, in);
                    finish();

                } else {
                    add_btn_visibility = false;
                    final Toast toast = Toast.makeText(AcademicActivity.this, getResources().getString(R.string.qualification_saved), Toast.LENGTH_SHORT);
                    toast.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 1000);
                    degree_edit.setText("");
                    college_edit.setText("");
                    university_edit.setText("");
                    passing_year_edit.setText("");
                    passing_year_edit.setVisibility(View.VISIBLE);
                    currently_checkbox.setChecked(false);
                    degree_edit.requestFocus();
                }

            } else if (data.has(RestUtils.TAG_DEGREES)) {
                JSONArray degreejArray = data.optJSONArray(RestUtils.TAG_DEGREES);
                JSONObject universityjObject = data.optJSONObject("universities");
                for (int u = 0; u < universityjObject.length(); u++) {
                    university_array.add(universityjObject.names().opt(u).toString());
                    ArrayList<String> arrayList = new ArrayList<String>(universityjObject.optJSONArray(universityjObject.names().opt(u).toString()).length());
                    for (int i = 0; i < universityjObject.optJSONArray(universityjObject.names().opt(u).toString()).length(); i++) {
                        arrayList.add(universityjObject.optJSONArray(universityjObject.names().opt(u).toString()).optString(i));
                    }
                    university_college.put(universityjObject.names().opt(u).toString(), arrayList);
                }
                if (degreejArray.length() > 0) {
                    for (int i = 0; i < degreejArray.length(); i++) {
                        degree_array.add(degreejArray.opt(i).toString());
                    }
                }

                for (String uni : university_array) {
                    for (int i = 0; i < university_college.get(uni).size(); i++) {
                        total_college_array.add(university_college.get(uni).get(i));
                    }
                }
                hideProgress();
                if (oper.equals("update")) {
                    if (university_college.containsKey(university_edit.getText().toString())) {
                        for (int i = 0; i < university_college.get(university_edit.getText().toString()).size(); i++) {
                            college_array.add(university_college.get(university_edit.getText().toString()).get(i));
                        }
                        college_adapter.notifyDataSetChanged();
                    }
                }

            }
        }
    }


    @Override
    public void onBackPressed() {
        hideKeyboard();
        String degree = degree_edit.getText().toString().trim();
        int passing_year = 0;
        if (!TextUtils.isEmpty(passing_year_edit.getText().toString()))
            passing_year = Integer.parseInt(passing_year_edit.getText().toString().trim());
        if (currently_checkbox.isChecked()) {
            passing_year = 0;
        }
        String university = university_edit.getText().toString().trim();
        String college = college_edit.getText().toString().trim();

        if ((!TextUtils.isEmpty(degree) || !TextUtils.isEmpty(university) || !TextUtils.isEmpty(college) || currently_checkbox.isChecked() || passing_year != 0) && oper.equals("add")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
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
                    /*Intent login_intent = new Intent(AcademicActivity.this, ProfileViewActivity.class);
                    login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(login_intent);*/
                    Intent in = new Intent();
                    setResult(Activity.RESULT_OK, in);
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();


        } else if (oper.equals("update") && (!academicInfo.getDegree().equals(degree) || !academicInfo.getUniversity().equals(university) || !academicInfo.getCollege().equals(college) ||
                academicInfo.getPassing_year() != passing_year || academicInfo.isCurrently_pursuing() != currently_checkbox.isChecked())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
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
                    /*Intent login_intent = new Intent(AcademicActivity.this, ProfileViewActivity.class);
                    login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(login_intent);*/
                    Intent in = new Intent();
                    setResult(Activity.RESULT_OK, in);
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            /*Intent login_intent = new Intent(AcademicActivity.this, ProfileViewActivity.class);
            login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login_intent);*/
            Intent in = new Intent();
            setResult(Activity.RESULT_OK, in);
            finish();
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
                        /*Intent in = new Intent(AcademicActivity.this, ProfileViewActivity.class);
                        startActivity(in);*/
                        finish();
                    }
                });
                builder.show();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(TAG_STATUS).equals(TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {
                            JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            if (data.has("acad_id")) {
                                academicInfo.setAcad_id(data.getInt("acad_id"));
                                realmManager.insertAcademic(realm, academicInfo);
                                academicInfo = new AcademicInfo();
                                hideProgress();
                                if (add_btn_visibility == false) {
                                    Toast.makeText(AcademicActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                    hideProgress();
                                    Intent in = new Intent();
                                    setResult(Activity.RESULT_OK, in);
                                    finish();
                                } else {
                                    add_btn_visibility = false;
                                    final Toast toast = Toast.makeText(AcademicActivity.this, getResources().getString(R.string.qualification_saved), Toast.LENGTH_SHORT);
                                    toast.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            toast.cancel();
                                        }
                                    }, 1000);
                                    degree_edit.setText("");
                                    college_edit.setText("");
                                    university_edit.setText("");
                                    passing_year_edit.setText("");
                                    passing_year_edit.setVisibility(View.VISIBLE);
                                    currently_checkbox.setChecked(false);
                                    degree_edit.requestFocus();
                                }

                            } else if (data.has(RestUtils.TAG_DEGREES)) {
                                JSONArray degreejArray = data.getJSONArray(RestUtils.TAG_DEGREES);
                                JSONObject universityjObject = data.getJSONObject("universities");
                                for (int u = 0; u < universityjObject.length(); u++) {
                                    university_array.add(universityjObject.names().get(u).toString());
                                    ArrayList<String> arrayList = new ArrayList<String>(universityjObject.getJSONArray(universityjObject.names().get(u).toString()).length());
                                    for (int i = 0; i < universityjObject.getJSONArray(universityjObject.names().get(u).toString()).length(); i++) {
                                        arrayList.add(universityjObject.getJSONArray(universityjObject.names().get(u).toString()).getString(i));
                                    }
                                    university_college.put(universityjObject.names().get(u).toString(), arrayList);
                                }
                                if (degreejArray.length() > 0) {
                                    for (int i = 0; i < degreejArray.length(); i++) {
                                        degree_array.add(degreejArray.get(i).toString());
                                    }
                                }

                                for (String uni : university_array) {
                                    for (int i = 0; i < university_college.get(uni).size(); i++) {
                                        total_college_array.add(university_college.get(uni).get(i));
                                    }
                                }
                                hideProgress();
                                if (oper.equals("update")) {
                                    if (university_college.containsKey(university_edit.getText().toString())) {
                                        for (int i = 0; i < university_college.get(university_edit.getText().toString()).size(); i++) {
                                            college_array.add(university_college.get(university_edit.getText().toString()).get(i));
                                        }
                                        college_adapter.notifyDataSetChanged();
                                    }
                                }

                            }
                        } else {
                            if (oper.equals("update")) {
                                Toast.makeText(AcademicActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                realmManager.updateAcademicInfo(realm, academicInfo);
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();
                            } else if (oper.equals("delete")) {
                                Toast.makeText(AcademicActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                realmManager.deleteAcademicInfo(realm, academicInfo.getAcad_id());
                                add_btn_visibility = false;
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();
                            }
                        }

                    } else {
                        hideProgress();
                        //ShowSimpleDialog("Error",  getResources().getString(R.string.unknown_server_error));
                        AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), AcademicActivity.this);

                    }

                } catch (Exception e) {
                    if (response.contains("FileNotFoundException")) {
                        //ShowSimpleDialog("Error", getResources().getString(R.string.unknown_server_error));
                        AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), AcademicActivity.this);

                    }
                    hideProgress();
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
