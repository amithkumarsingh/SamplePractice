package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.Prediction;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.models.WeekDay;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.PlacesAutoCompleteAdapter;
import com.vam.whitecoats.ui.adapters.WeekDayAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.ui.interfaces.LocationCaputerListner;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnlocationApiFinishedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AwsAndGoogleKeysServiceClass;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.LocationHelperClass;
import com.vam.whitecoats.utils.RequestForCurrentLocPlacesUsingPlaceId;
import com.vam.whitecoats.utils.RequestForPlacesUsingPlaceId;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by lokesh on 5/13/2015.
 */
public class ProfessionalDetActivity extends BaseActionBarActivity implements CheckBox.OnClickListener, TextWatcher {

    EditText designation_edit, fromdate_edit, toDateEdtTxt;
    AutoCompleteTextView location_edit, workplace_edit;
    CheckBox working_check;
    RadioButton opdRadioBtn, otRadioBtn;
    TextView till_date_txt;
    TextView text_workplace, text_location, text_fromdate;
    ToggleButton toggleButton;
    LinearLayout availableParentLayout;
    RelativeLayout rv_delete_layout;
    Button add_button;
    private Realm realm;
    private RealmManager realmManager;
    static String oper = "add";
    String profId = "0";
    int doctorID;
    String department = "";
    long startTime = -19800001;
    long endTime = -19800001;
    Context context;
    private WeekDayAdapter weekDayAdapter;
    // private ArrayList<String> cities_array = new ArrayList<>();
    //ArrayAdapter<String> cities_adpt;


    DatePickerDialog dpd, todpd;
    private long frommillisec, tomillisec;
    private long mLastClickTime = 0;
    Calendar fromcal = Calendar.getInstance();
    Calendar tocal = Calendar.getInstance();
    ProfessionalInfo professionalInfo;
    static boolean edit_toggleButton = false;

    boolean add_another = false;
    List<WeekDay> weekDays;
    TextView next_button;
    private TextInputLayout todate_edit_text_input_lay;
    private EditText startTimeEdt;
    private EditText endTimeEdt;
    RecyclerView weekDaysRecyclerView;
    private RadioGroup opd_ot_rg;
    private LocationHelperClass locationHelperObj;
    private RequestForCurrentLocPlacesUsingPlaceId requestForCurrentLocPlacesUsingPlaceId;
    private String api_key;
    private PlacesClient placesClient;
    private MarshMallowPermission marshMallowPermission;
    private RequestForPlacesUsingPlaceId requestForPlacesUsingPlaceId;
    private boolean isCityEditText = false;
    private boolean isExactWorkplace;
    private boolean isExactCity;
    private JSONObject workPlaceJson, cityJson;
    private boolean isWorkEditText;
    private String cityName;
    private String location;
    private RealmBasicInfo basicInfo;
    private boolean isCitySelectedFromList;
    private Button clear_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_det);
        context = this;
        initialize();
        validationUtils = new ValidationUtils(ProfessionalDetActivity.this, new EditText[]{workplace_edit, location_edit}, new TextView[]{text_workplace, text_location});

        ArrayList<String> searchkeys = new ArrayList<String>();
        searchkeys.add("cities");
        if (isConnectingToInternet()) {
            new AutoSuggestionsAsync(ProfessionalDetActivity.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
        }

        location_edit.setThreshold(1);
        working_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((((CheckBox) v).isChecked())) {
                    hideKeyboard();
                    displayAvailabilityView(true);
                    toDateEdtTxt.setVisibility(View.GONE);
                    todate_edit_text_input_lay.setVisibility(View.GONE);
                    till_date_txt.setVisibility(View.VISIBLE);
                    availableParentLayout.setVisibility(View.VISIBLE);
                } else {
                    displayAvailabilityView(false);
                    toDateEdtTxt.setText("");
                    todate_edit_text_input_lay.setVisibility(View.VISIBLE);
                    toDateEdtTxt.setVisibility(View.VISIBLE);
                    till_date_txt.setVisibility(View.GONE);
                    availableParentLayout.setVisibility(View.GONE);
                    tocal = Calendar.getInstance();
                }
            }
        });


        fromdate_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = fromdate_edit.getText().toString();

                showFromDatePicker(fromdate_edit);
                if (!TextUtils.isEmpty((s))) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(milliseconds(s));
                    dpd.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                } else {
                    Calendar calendar = Calendar.getInstance();
                    dpd.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }
                dpd.show();
                //s="";
            }
        });

        toDateEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    frommillisec = milliseconds(fromdate_edit.getText().toString());
                    String s = toDateEdtTxt.getText().toString();

                    if (frommillisec == 0) {
                    } else {

                        showToDatePicker(toDateEdtTxt);
                        if (!TextUtils.isEmpty((s))) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(milliseconds(s));
                            todpd.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        } else {
                            Calendar calendar = Calendar.getInstance();
                            todpd.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        }
                        todpd.show();
                    }
                    // s="";
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hideKeyboard();
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    } else {
                        mLastClickTime = SystemClock.elapsedRealtime();
                        if (isConnectingToInternet()) {
                            String selectedWeekDays = "";
                            // showProgress();
                            String workplace = workplace_edit.getText().toString().trim();
                            String location = location_edit.getText().toString().trim();
                            String designation = designation_edit.getText().toString().trim();
                            if (validationUtils.isTwoEnteder(workplace, location)) {
                                if(!isCitySelectedFromList){
                                    Toast.makeText(ProfessionalDetActivity.this, "Please select relevant city of residence", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                professionalInfo.setWorkplace(workplace);
                                professionalInfo.setDesignation(designation);
                                professionalInfo.setLocation(location);
                                frommillisec = milliseconds(fromdate_edit.getText().toString());
                                professionalInfo.setStart_date(frommillisec);
                                if (working_check.isChecked() == false) {
                                    professionalInfo.setEnd_date(tomillisec);
                                    professionalInfo.setWorking_here(false);
                                } else {
                                    professionalInfo.setEnd_date(0);
                                    professionalInfo.setWorking_here(true);
                                    selectedWeekDays = weekDayAdapter.updatedAvailableDays();
                                    professionalInfo.setAvailableDays(selectedWeekDays);
                                    professionalInfo.setWorkOptions(department);
                                    professionalInfo.setStartTime(startTime);
                                    professionalInfo.setEndTime(endTime);
                                }
                                if (toggleButton.isChecked() == true) {
                                    professionalInfo.setShowOncard(true);
                                } else {
                                    professionalInfo.setShowOncard(false);
                                }
                                add_another = true;

                                /** Creating JSONObject **/

                                if (!realmManager.isWorkTimingExists(realm, professionalInfo, false)) {
                                    JSONObject object = getProfessionRequestJson(professionalInfo);
                                    String URL = RestApiConstants.CREATE_USER_PROFILE;
                                    if (oper.equals(RestUtils.TAG_UPDATE)) {
                                        URL = RestApiConstants.UPDATE_USER_PROFILE;
                                    }
                                    showProgress();
                                    new VolleySinglePartStringRequest(ProfessionalDetActivity.this, Request.Method.POST, URL, object.toString(), "PROFESSIONAL_DETAILS_ACTIVITY_ADD", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            hideProgress();
                                            workPlaceJson = new JSONObject();
                                            cityJson = new JSONObject();
                                            onTaskCompleted(successResponse);
                                            weekDays.addAll(getWeekDays(null));
                                            weekDayAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onErrorResponse(String errorResponse) {
                                            hideProgress();
                                            workPlaceJson = new JSONObject();
                                            cityJson = new JSONObject();
                                            displayErrorScreen(errorResponse);
                                        }
                                    }).sendSinglePartRequest();
                                } else {
                                    ShowSimpleDialog("", getString(R.string.alert_existing_time));
                                }
                            }
                        } else {
                            hideProgress();
                        }
                    }

                } catch (Exception e) {
                    hideProgress();
                    e.printStackTrace();
                }

            }
        });
        startTimeEdt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setSelectedTimeToEdt(startTimeEdt, true);


            }
        });

        endTimeEdt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setSelectedTimeToEdt(endTimeEdt, false);

            }
        });

    }

    private void displayAvailabilityView(boolean flag) {
        if (flag) {
            availableParentLayout.setVisibility(View.VISIBLE);
            weekDayAdapter.notifyDataSetChanged();
        } else
            availableParentLayout.setVisibility(View.GONE);
    }

    private List<WeekDay> getWeekDays(String availableDays) {
        weekDays.clear();
        List<WeekDay> daysList = new ArrayList<WeekDay>();
        String[] shortNamesOfDays = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        for (int index = 1; index <= 7; index++) {
            WeekDay weekDay = new WeekDay();
            weekDay.setDaySerialNumber(index);
            weekDay.setShortName(shortNamesOfDays[index - 1]);
            if (availableDays != null && availableDays.contains(index + "")) // if day already present in the selection
                weekDay.setChecked(true);
            else
                weekDay.setChecked(false);
            daysList.add(weekDay);
        }
        return daysList;

    }

    private void setSelectedTimeToEdt(EditText timeEdt, boolean isStartTime) {
        Calendar mCurrentTime = Calendar.getInstance();

        if (!timeEdt.getText().toString().isEmpty()) {
            DateFormat sdf = new SimpleDateFormat("hh:mm a");
            Date date = null;
            try {
                date = sdf.parse(timeEdt.getText().toString().trim());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mCurrentTime.setTime(date);
        }
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ProfessionalDetActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                datetime.set(Calendar.MINUTE, selectedMinute);
                datetime.set(Calendar.MILLISECOND, 0);
                datetime.set(Calendar.SECOND, 0);
                datetime.set(Calendar.AM_PM, datetime.get(Calendar.AM_PM));
                datetime.set(Calendar.YEAR, 1970);
                datetime.set(Calendar.DAY_OF_MONTH, 1);
                datetime.set(Calendar.MONTH, 0);
                // store user start & end time.
                if (isStartTime) {
                    startTime = datetime.getTimeInMillis();
                } else {
                    endTime = datetime.getTimeInMillis();

                }
                String formattedTime = DateUtils.parseMillisIntoDateWithDefaultValue(datetime.getTimeInMillis(), "hh:mm a");//format your time
                timeEdt.setText(formattedTime);
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
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

    private void initialize() {

        try {
            workplace_edit = _findViewById(R.id.workplace_edit);
            location_edit = _findViewById(R.id.city_edit);
            designation_edit = _findViewById(R.id.designation_edit);
            fromdate_edit = _findViewById(R.id.fromdate_edit);
            toDateEdtTxt = _findViewById(R.id.todate_edit);
            working_check = _findViewById(R.id.working_check);
            till_date_txt = _findViewById(R.id.tilldate_text);
            toggleButton = _findViewById(R.id.toggleButton1);
            // toggle_layout = _findViewById(R.id.show_on_card_toggle);
            rv_delete_layout = _findViewById(R.id.delete_layout);
            opdRadioBtn = findViewById(R.id.opd_radio_btn);
            otRadioBtn = findViewById(R.id.ot_radio_btn);
            weekDaysRecyclerView = findViewById(R.id.week_days_recycler_view);
            /** Error TextViews **/
            text_workplace = _findViewById(R.id.workplace_error_text);
            text_location = _findViewById(R.id.location_error_text);
            text_fromdate = _findViewById(R.id.fromdate_error);
            todate_edit_text_input_lay = _findViewById(R.id.todate_edit_text_input_lay);
            clear_text=_findViewById(R.id.clear_text);

            add_button = _findViewById(R.id.add_button);
            startTimeEdt = (EditText) findViewById(R.id.startTime_edt);
            endTimeEdt = (EditText) findViewById(R.id.endTime_edt);
            availableParentLayout = findViewById(R.id.available_parent_layout);
            opd_ot_rg = (RadioGroup) findViewById(R.id.opd_ot_rg);
            oper = "add";

            professionalInfo = (ProfessionalInfo) getIntent().getSerializableExtra("professionalInfo");
            /*
             * Create week day adapter
             */
            String availableDays = professionalInfo == null ? "" : professionalInfo.getAvailableDays();
            //Has to be modified for dynamic size of each day
            weekDays = new ArrayList<WeekDay>();
            weekDayAdapter = new WeekDayAdapter(weekDays, getApplicationContext());
            weekDays.addAll(getWeekDays(availableDays));
            weekDaysRecyclerView.setAdapter(weekDayAdapter);
            weekDaysRecyclerView.setHasFixedSize(true);
            CustomLinearLayoutManager mLayoutManager = new CustomLinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mLayoutManager.setScrollEnabled(false);
            weekDaysRecyclerView.setLayoutManager(mLayoutManager);

            workplace_edit.addTextChangedListener(this);
            location_edit.addTextChangedListener(this);


            if (professionalInfo != null) {
                oper = "update";
                profId = professionalInfo.getProf_id() + "";
                workplace_edit.setText(professionalInfo.getWorkplace());
                designation_edit.setText(professionalInfo.getDesignation());
                if (professionalInfo.getStart_date() != 0)
                    fromdate_edit.setText(convertLongtoDate(professionalInfo.getStart_date()));
                location_edit.setText(professionalInfo.getLocation());
                isCitySelectedFromList=true;
                working_check.setChecked(professionalInfo.isWorking_here());
                if (working_check.isChecked()) {
                    displayAvailabilityView(true);
                    String fromTime = DateUtils.parseMillisIntoDateWithDefaultValue(professionalInfo.getStartTime(), "hh:mm a");
                    String toTime = DateUtils.parseMillisIntoDateWithDefaultValue(professionalInfo.getEndTime(), "hh:mm a");
                    startTimeEdt.setText(fromTime);
                    endTimeEdt.setText(toTime);
                    startTime = professionalInfo.getStartTime();
                    endTime = professionalInfo.getEndTime();
                    if (professionalInfo.getWorkOptions() != null && !professionalInfo.getWorkOptions().isEmpty()) {
                        if (professionalInfo.getWorkOptions().equalsIgnoreCase(getString(R.string.label_opd)))
                            opdRadioBtn.setChecked(true);
                        else
                            otRadioBtn.setChecked(true);
                    }

                    toDateEdtTxt.setVisibility(View.GONE);
                    todate_edit_text_input_lay.setVisibility(View.GONE);
                    todate_edit_text_input_lay.setHintEnabled(false);
                    till_date_txt.setVisibility(View.VISIBLE);
                    availableParentLayout.setVisibility(View.VISIBLE);
                } else {
                    displayAvailabilityView(false);
                    if (professionalInfo.getEnd_date() != 0)
                        toDateEdtTxt.setText(convertLongtoDate(professionalInfo.getEnd_date()));
                    toDateEdtTxt.setVisibility(View.VISIBLE);
                    todate_edit_text_input_lay.setVisibility(View.VISIBLE);
                    todate_edit_text_input_lay.setHintEnabled(true);
                    till_date_txt.setVisibility(View.GONE);
                    availableParentLayout.setVisibility(View.GONE);
                    tocal = Calendar.getInstance();
                }
                edit_toggleButton = professionalInfo.isShowOncard();
                toggleButton.setChecked(edit_toggleButton);
                rv_delete_layout.setVisibility(View.VISIBLE);
                add_button.setVisibility(View.GONE);
            } else {
                professionalInfo = new ProfessionalInfo();
                displayAvailabilityView(false);
            }

            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(this);
            doctorID = realmManager.getDoc_id(realm);
            basicInfo=realmManager.getRealmBasicInfo(realm);
            mInflater = LayoutInflater.from(this);
            mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            next_button = (TextView) mCustomView.findViewById(R.id.next_button);

            marshMallowPermission = new MarshMallowPermission(this);
            next_button.setText(getString(R.string.actionbar_save));
            //back_button.setImageResource(R.drawable.ic_back);
            if (oper.equals("update")) {
                mTitleTextView.setText(getString(R.string.edit_workplace_actionbar_title));
            } else {
                mTitleTextView.setText(getString(R.string.add_workplace_actionbar_title));
            }

            workplace_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        isWorkEditText = false;
                    } else {
                        isWorkEditText = true;
                    }
                }
            });

            clear_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    location_edit.setText("");
                }
            });



            location_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        isCityEditText = false;
                    } else {
                        isCityEditText = true;
                    }

                }
            });


            new AwsAndGoogleKeysServiceClass(ProfessionalDetActivity.this, doctorID, basicInfo.getUserUUID(),new AwsAndGoogleKey() {
                @Override
                public void awsAndGoogleKey(String google_api_key, String aws_key) {
                    api_key = google_api_key;
                    Places.initialize(getApplicationContext(), api_key);
                    placesClient = Places.createClient(ProfessionalDetActivity.this);


                    List<Prediction> predictions = new ArrayList<>();
                    PlacesAutoCompleteAdapter placesAutoCompleteAdapter = new PlacesAutoCompleteAdapter(getApplicationContext(), predictions, false, api_key,doctorID);
                    workplace_edit.setThreshold(1);
                    workplace_edit.setAdapter(placesAutoCompleteAdapter);
                    workplace_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Prediction prediction = predictions.get(position);
                            if (prediction.getId()!=null && prediction.getId().equals("-1")) {
                                isExactWorkplace = true;
                                if (prediction.getDescription().equalsIgnoreCase("Use Current Location")) {
                                    workplace_edit.setText("");
                                }
                                if (!marshMallowPermission.requestPermissionForLocation()) {
                                    if (AppConstants.neverAskAgain_Location) {
                                        AppUtil.showLocationServiceDenyAlert(ProfessionalDetActivity.this);
                                    }
                                } else {
                                    // getLocationUsingAPI();

                                    locationHelperObj = new LocationHelperClass(ProfessionalDetActivity.this, new LocationCaputerListner() {
                                        @Override
                                        public void onLocationCapture(Location location) {
                                            if (location != null) {
                                                double lat = location.getLatitude();
                                                double longi = location.getLongitude();

                                                requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(ProfessionalDetActivity.this, lat, longi, api_key, new OnReceiveResponse() {
                                                    @Override
                                                    public void onSuccessResponse(String successResponse) {
                                                        String s1 = successResponse;
                                                        try{
                                                        JSONObject responseJsonObject=new JSONObject(successResponse);
                                                        if(responseJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                            AppUtil.getAddressDetailsUsingAPI(ProfessionalDetActivity.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                                @Override
                                                                public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject currentLocJsonObject) {
                                                                    if(currentLocJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                                        if (!isCityEditText) {
                                                                            if (apiStringHashMap.containsKey("workplace")) {
                                                                                workplace_edit.setText(apiStringHashMap.get("workplace"));
                                                                                workplace_edit.setSelection(workplace_edit.length());
                                                                                workplace_edit.dismissDropDown();
                                                                            }
                                                                        }
                                                                        if (apiStringHashMap.containsKey("city")) {
                                                                            cityName = apiStringHashMap.get("city");
                                                                            location_edit.setText(cityName);
                                                                            isCitySelectedFromList=true;
                                                                            location_edit.setSelection(location_edit.length());
                                                                            location_edit.dismissDropDown();
                                                                        }

                                                                        if (isExactWorkplace) {
                                                                            isExactCity = true;
                                                                        } else {
                                                                            isExactCity = false;
                                                                        }
                                                                        workPlaceJson = currentLocJsonObject;
                                                                        cityJson = currentLocJsonObject;
                                                                    }else{

                                                                        String errorMsg = "Unable to fetch Geocode Details";
                                                                        if (currentLocJsonObject.has("error_message")) {
                                                                            errorMsg = currentLocJsonObject.optString("error_message");
                                                                        }
                                                                        JSONObject errorObj=new JSONObject();
                                                                        try {
                                                                            errorObj.put("screen","UserProfile");
                                                                            errorObj.put("searchKey",workplace_edit.getText().toString());
                                                                            errorObj.put("errorMsg",errorMsg);
                                                                            AppUtil.logUserActionEvent(doctorID, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }


                                                                }

                                                                @Override
                                                                public void onLocationApiError(String error) {
                                                                    JSONObject errorObj=new JSONObject();
                                                                    try {
                                                                        errorObj.put("screen","UserProfile");
                                                                        errorObj.put("searchKey",workplace_edit.getText().toString());
                                                                        errorObj.put("errorMsg",error);
                                                                        AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                        }else{
                                                            workPlaceJson=null;
                                                            cityJson=null;
                                                            String errorMsg = "Unable to fetch User current location";
                                                            if (responseJsonObject.has("error_message")) {
                                                                errorMsg = responseJsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj=new JSONObject();
                                                            try {
                                                                errorObj.put("screen","UserProfile");
                                                                errorObj.put("searchKey",workplace_edit.getText().toString());
                                                                errorObj.put("errorMsg",errorMsg);
                                                                AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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
                                                        JSONObject errorObj=new JSONObject();
                                                        try {
                                                            errorObj.put("screen","UserProfile");
                                                            errorObj.put("searchKey",workplace_edit.getText().toString());
                                                            errorObj.put("errorMsg",errorResponse);
                                                            AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(ProfessionalDetActivity.this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

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

                                requestForPlacesUsingPlaceId = new RequestForPlacesUsingPlaceId(ProfessionalDetActivity.this, prediction.getPlaceId(), api_key, new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        try {
                                            JSONObject placeIdJsonObject = new JSONObject(successResponse);
                                            if(placeIdJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                if (placeIdJsonObject.has("result")) {
                                                    JSONObject resultObj = placeIdJsonObject.optJSONObject("result");
                                                    JSONArray addressComponents = resultObj.optJSONArray("address_components");
                                                    for (int i = 0; i < addressComponents.length(); i++) {
                                                        JSONObject addressComponent = addressComponents.optJSONObject(i);
                                                        JSONArray types = addressComponent.optJSONArray("types");
                                                        for (int j = 0; j < types.length(); j++) {
                                                            if (types.optString(j).equalsIgnoreCase("locality") || types.optString(j).equalsIgnoreCase("administrative_area_level_3")) {
                                                                location_edit.setText(addressComponent.optString("long_name"));
                                                                isCitySelectedFromList=true;
                                                                location_edit.setSelection(location_edit.length());
                                                                location_edit.dismissDropDown();
                                                            }
                                                        }
                                                    }
                                                }
                                                workPlaceJson = placeIdJsonObject;
                                                cityJson = placeIdJsonObject;
                                            }else{
                                                workPlaceJson=null;
                                                cityJson=null;
                                                String errorMsg = "Unable to fetch place details";
                                                if (placeIdJsonObject.has("error_message")) {
                                                    errorMsg = placeIdJsonObject.optString("error_message");
                                                }
                                                JSONObject errorObj=new JSONObject();
                                                try {
                                                    errorObj.put("screen","UserProfile");
                                                    errorObj.put("searchKey",workplace_edit.getText().toString());
                                                    errorObj.put("errorMsg",errorMsg);
                                                    AppUtil.logUserActionEvent(doctorID, "FetchCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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
                                        JSONObject errorObj=new JSONObject();
                                        try {
                                            errorObj.put("screen","UserProfile");
                                            errorObj.put("searchKey",workplace_edit.getText().toString());
                                            errorObj.put("errorMsg",errorResponse);
                                            AppUtil.logUserActionEvent(doctorID, "FetchCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }                                    }
                                });
                            }
                        }
                    });
                    location_edit.setThreshold(1);
                    location_edit.setAdapter(placesAutoCompleteAdapter);
                    location_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Prediction prediction = predictions.get(position);
                            if (prediction.getId()!=null && prediction.getId().equals("-1")) {
                                if (prediction.getDescription().equalsIgnoreCase("Use Current Location")) {
                                    location_edit.setText("");
                                }
                                isExactCity = true;
                                isCitySelectedFromList=true;
                                if (!marshMallowPermission.requestPermissionForLocation()) {
                                    if (AppConstants.neverAskAgain_Location) {
                                        AppUtil.showLocationServiceDenyAlert(ProfessionalDetActivity.this);
                                    }
                                } else {
                                    // getLocationUsingAPI();

                                    locationHelperObj = new LocationHelperClass(ProfessionalDetActivity.this, new LocationCaputerListner() {
                                        @Override
                                        public void onLocationCapture(Location location) {
                                            if (location != null) {
                                                double lat = location.getLatitude();
                                                double longi = location.getLongitude();

                                                requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(ProfessionalDetActivity.this, lat, longi, api_key, new OnReceiveResponse() {
                                                    @Override
                                                    public void onSuccessResponse(String successResponse) {

                                                        try {
                                                            JSONObject responseJsonObject=new JSONObject(successResponse);
                                                            if(responseJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                                AppUtil.getAddressDetailsUsingAPI(ProfessionalDetActivity.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                                    @Override
                                                                    public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject currentLocJsonObject) {
                                                                        if(currentLocJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                                            if (!isCityEditText) {
                                                                                if (apiStringHashMap.containsKey("workplace")) {
                                                                                    workplace_edit.setText(apiStringHashMap.get("workplace"));
                                                                                    workplace_edit.setSelection(workplace_edit.length());
                                                                                    workplace_edit.dismissDropDown();
                                                                                }
                                                                            }
                                                                            if (apiStringHashMap.containsKey("city")) {
                                                                                cityName = apiStringHashMap.get("city");
                                                                                location_edit.setText(apiStringHashMap.get("city"));
                                                                                location_edit.setSelection(location_edit.length());
                                                                                location_edit.dismissDropDown();
                                                                            }
                                                                            cityJson = currentLocJsonObject;
                                                                            isCitySelectedFromList=true;
                                                                        }else{
                                                                            String errorMsg = "Unable to fetch Geocode Details";
                                                                            if (currentLocJsonObject.has("error_message")) {
                                                                                errorMsg = currentLocJsonObject.optString("error_message");
                                                                            }
                                                                            JSONObject errorObj=new JSONObject();
                                                                            try {
                                                                                errorObj.put("screen","UserProfile");
                                                                                errorObj.put("searchKey",workplace_edit.getText().toString());
                                                                                errorObj.put("errorMsg",errorMsg);
                                                                                AppUtil.logUserActionEvent(doctorID, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }


                                                                    }

                                                                    @Override
                                                                    public void onLocationApiError(String error) {
                                                                        JSONObject errorObj=new JSONObject();
                                                                        try {
                                                                            errorObj.put("screen","UserProfile");
                                                                            errorObj.put("searchKey",location_edit.getText().toString());
                                                                            errorObj.put("errorMsg",error);
                                                                            AppUtil.logUserActionEvent(doctorID, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                    }
                                                                });
                                                            }else{
                                                                cityJson=null;
                                                                String errorMsg = "Unable to fetch User current location";
                                                                if (responseJsonObject.has("error_message")) {
                                                                    errorMsg = responseJsonObject.optString("error_message");
                                                                }
                                                                JSONObject errorObj=new JSONObject();
                                                                try {
                                                                    errorObj.put("screen","UserProfile");
                                                                    errorObj.put("searchKey",location_edit.getText().toString());
                                                                    errorObj.put("errorMsg",errorMsg);
                                                                    AppUtil.logUserActionEvent(doctorID, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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
                                                        JSONObject errorObj=new JSONObject();
                                                        try {
                                                            errorObj.put("screen","UserProfile");
                                                            errorObj.put("searchKey",location_edit.getText().toString());
                                                            errorObj.put("errorMsg",errorResponse);
                                                            AppUtil.logUserActionEvent(doctorID, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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
                                //isExactWorkplace=false;
                                isExactCity = false;
                                requestForPlacesUsingPlaceId = new RequestForPlacesUsingPlaceId(ProfessionalDetActivity.this, prediction.getPlaceId(), api_key, new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        String success = successResponse;

                                        try {
                                            JSONObject jsonObject = new JSONObject(successResponse);
                                            if(jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                if (jsonObject != null && jsonObject.has("result")) {
                                                    JSONObject resultObj = jsonObject.optJSONObject("result");
                                                    JSONArray addressComponents = resultObj.optJSONArray("address_components");
                                                    for (int i = 0; i < addressComponents.length(); i++) {
                                                        JSONObject addressComponent = addressComponents.optJSONObject(i);
                                                        JSONArray types = addressComponent.optJSONArray("types");
                                                        for (int j = 0; j < types.length(); j++) {
                                                            if (types.optString(j).equalsIgnoreCase("locality") || types.optString(j).equalsIgnoreCase("administrative_area_level_3")) {
                                                                location_edit.setText(addressComponent.optString("long_name"));
                                                                location_edit.setSelection(location_edit.length());
                                                                location_edit.dismissDropDown();
                                                            }
                                                        }
                                                    }
                                                }
                                                cityJson = jsonObject;
                                                isCitySelectedFromList=true;
                                            }else{
                                                cityJson=null;
                                                String errorMsg = "Unable to fetch place details";
                                                if (jsonObject.has("error_message")) {
                                                    errorMsg = jsonObject.optString("error_message");
                                                }
                                                JSONObject errorObj=new JSONObject();
                                                try {
                                                    errorObj.put("screen","UserProfile");
                                                    errorObj.put("searchKey",location_edit.getText().toString());
                                                    errorObj.put("errorMsg",errorMsg);
                                                    AppUtil.logUserActionEvent(doctorID, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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
                                        JSONObject errorObj=new JSONObject();
                                        try {
                                            errorObj.put("screen","UserProfile");
                                            errorObj.put("searchKey",location_edit.getText().toString());
                                            errorObj.put("errorMsg",errorResponse);
                                            AppUtil.logUserActionEvent(doctorID, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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

            if (oper.equals("update")) {

                //handling working_check
                working_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {

                            if (edit_toggleButton) {
                                working_check.setChecked(true);
                                toggleButton.setChecked(true);
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);
                                builder.setMessage(getResources().getString(R.string.workplace_presently));
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        }
                    }
                });

                //handling show on card toggleButton
                toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {
                            if (edit_toggleButton) {
                                toggleButton.setChecked(true);
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);
                                builder.setMessage(getResources().getString(R.string.professional_togglebutton_alert));
                                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //if user pressed "yes", then he is allowed to exit from application
                                        dialog.cancel();

                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    }
                });

            }
            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        hideKeyboard();
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        } else {
                            mLastClickTime = SystemClock.elapsedRealtime();
                            if (isConnectingToInternet()) {
                                // showProgress();
                                String selectedWeekDays = "";
                                String workplace = workplace_edit.getText().toString().trim();
                                location = location_edit.getText().toString().trim();
                                String designation = designation_edit.getText().toString().trim();
                                if (validationUtils.isTwoEnteder(workplace, location)) {
                                    if(!isCitySelectedFromList){
                                        Toast.makeText(ProfessionalDetActivity.this, "Please select relevant city of residence", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    professionalInfo.setWorkplace(workplace);
                                    professionalInfo.setDesignation(designation);
                                    professionalInfo.setLocation(location);
                                    frommillisec = milliseconds(fromdate_edit.getText().toString());
                                    tomillisec = milliseconds(toDateEdtTxt.getText().toString());
                                    professionalInfo.setStart_date(frommillisec);

                                    if (working_check.isChecked() == false) {
                                        professionalInfo.setEnd_date(tomillisec);
                                        professionalInfo.setWorking_here(false);
                                    } else {
                                        professionalInfo.setEnd_date(0);
                                        professionalInfo.setWorking_here(true);
                                        selectedWeekDays = weekDayAdapter.updatedAvailableDays();
                                        professionalInfo.setAvailableDays(selectedWeekDays);
                                        professionalInfo.setWorkOptions(department);
                                        professionalInfo.setStartTime(startTime);
                                        professionalInfo.setEndTime(endTime);
                                    }
                                    if (toggleButton.isChecked() == true) {
                                        professionalInfo.setShowOncard(true);
                                    } else {
                                        professionalInfo.setShowOncard(false);
                                    }
                                    add_another = false;
                                    /** Creating JSONObject **/
                                    boolean isUpdate = false;
                                    if (oper.equals(RestUtils.TAG_UPDATE)) {
                                        isUpdate = true;
                                    }
                                    // Check if similar records exists in db
                                    if (!realmManager.isWorkTimingExists(realm, professionalInfo, isUpdate)) {
                                        JSONObject object = getProfessionRequestJson(professionalInfo);
                                        String URL = RestApiConstants.CREATE_USER_PROFILE;
                                        if (oper.equals(RestUtils.TAG_UPDATE)) {
                                            URL = RestApiConstants.UPDATE_USER_PROFILE;
                                        }
                                        showProgress();
                                        new VolleySinglePartStringRequest(ProfessionalDetActivity.this, Request.Method.POST, URL, object.toString(), "PROFESSIONAL_DETAILS_ACTIVITY_SAVEORUPDATE", new OnReceiveResponse() {
                                            @Override
                                            public void onSuccessResponse(String successResponse) {
                                                hideProgress();
                                                workPlaceJson = new JSONObject();
                                                cityJson = new JSONObject();
                                                onTaskCompleted(successResponse);
                                            }

                                            @Override
                                            public void onErrorResponse(String errorResponse) {
                                                hideProgress();
                                                workPlaceJson = new JSONObject();
                                                cityJson = new JSONObject();
                                                displayErrorScreen(errorResponse);
                                            }
                                        }).sendSinglePartRequest();
                                    } else {
                                        ShowSimpleDialog("", getString(R.string.alert_existing_time));
                                    }
                                }
                            } else {
                                hideProgress();
                            }
                        }
                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
                    }

                }
            });

            rv_delete_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        } else {
                            mLastClickTime = SystemClock.elapsedRealtime();
                            if (isConnectingToInternet()) {

                                if (edit_toggleButton) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setCancelable(true);
                                    builder.setMessage(getResources().getString(R.string.profile_workplacedeletion));
                                    builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfessionalDetActivity.this);
                                    builder.setCancelable(true);
                                    builder.setTitle(getResources().getString(R.string.profile_delete));
                                    builder.setMessage(getResources().getString(R.string.profile_delete_other));
                                    builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            oper = "delete";
                                            add_another = false;
                                            /**JSONObject Creation**/
                                            try {
                                                JSONObject requestObject = new JSONObject();
                                                JSONObject professionalObj = new JSONObject();
                                                JSONArray professionalArray = new JSONArray();
                                                professionalObj.put(RestUtils.TAG_PROF_ID, professionalInfo.getProf_id());
                                                requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                                professionalArray.put(professionalObj);
                                                requestObject.put(RestUtils.TAG_PROFESSIONAL_HISTORY, professionalArray);
                                                showProgress();
                                                new VolleySinglePartStringRequest(ProfessionalDetActivity.this, Request.Method.POST, RestApiConstants.DELETE_USER_PROFILE, requestObject.toString(), "PROFESSIONAL_DETAILS_ACTIVITY_DELETE", new OnReceiveResponse() {
                                                    @Override
                                                    public void onSuccessResponse(String successResponse) {
                                                        hideProgress();
                                                        JSONObject responseObj = null;
                                                        try {
                                                            responseObj = new JSONObject(successResponse);
                                                            if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                                Toast.makeText(ProfessionalDetActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                                                                realmManager.deleteProfession(realm, professionalInfo.getProf_id());
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
                            } else {
                                hideProgress();
                            }
                        }
                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
                    }
                }
            });

            opdRadioBtn.setOnClickListener(this);
            otRadioBtn.setOnClickListener(this);

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(mCustomView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject getProfessionRequestJson(ProfessionalInfo professionalInfo) {
        JSONObject object = new JSONObject();
        long startTime = professionalInfo.getStartTime();
        long endTime = professionalInfo.getEndTime();
        if (startTime > endTime)
            endTime += 86400000; // add one day milliseconds
        try {
            JSONObject professionalObj = new JSONObject();
            JSONArray professionalArray = new JSONArray();
            if (oper.equals(RestUtils.TAG_UPDATE)) {
                professionalObj.put(RestUtils.TAG_PROF_ID, professionalInfo.getProf_id());
            }
            professionalObj.put(RestUtils.TAG_WORKPLACE, professionalInfo.getWorkplace());
            professionalObj.put(RestUtils.TAG_LOCATION, professionalInfo.getLocation());
            professionalObj.put(RestUtils.TAG_DESIGNATION, professionalInfo.getDesignation());
            professionalObj.put(RestUtils.TAG_FROMDATE, professionalInfo.getStart_date());
            professionalObj.put(RestUtils.TAG_TODATE, professionalInfo.getEnd_date());
            professionalObj.put(RestUtils.TAG_WORKING, professionalInfo.isWorking_here());
            professionalObj.put(RestUtils.TAG_SHOW_ON_CARD, professionalInfo.isShowOncard());
            if (workPlaceJson == null) {
                professionalObj.put("user_google_places_info", new JSONObject());
            } else {
                professionalObj.put("user_google_places_info", workPlaceJson);
            }
            if (cityJson == null) {
                professionalObj.put("user_google_city_info", new JSONObject());
            } else {
                professionalObj.put("user_google_city_info", cityJson);
            }
            professionalObj.put("is_exact_location", isExactWorkplace);
            professionalObj.put("is_exact_city", isExactCity);
            //Week days array
            //Available Days object creation
            JSONObject availableDaysObj = new JSONObject();
            // weekDaysArray can be empty (or) can contain values either.
            JSONArray weekDaysArray = new JSONArray();
            if (professionalInfo.getAvailableDays() != null && !professionalInfo.getAvailableDays().isEmpty()) {
                String[] weekdays = professionalInfo.getAvailableDays().split(",");
                int length = weekdays.length;
                for (int i = 0; i < length; i++) {
                    weekDaysArray.put(Integer.parseInt(weekdays[i]));
                }
            }
            availableDaysObj.put(RestUtils.TAG_WEEK_OF_DAYS, weekDaysArray);
            availableDaysObj.put(RestUtils.TAG_FROM_TIME, startTime);
            availableDaysObj.put(RestUtils.TAG_TO_TIME, endTime);
            availableDaysObj.put(RestUtils.TAG_DEPARTMENT, professionalInfo.getWorkOptions());
            professionalObj.put(RestUtils.TAG_AVAILABLE_DAYS, availableDaysObj);
            professionalArray.put(professionalObj);
            object.put(RestUtils.TAG_USER_ID, doctorID);
            object.put(RestUtils.TAG_PROFESSIONAL_HISTORY, professionalArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    @Override
    public void onBackPressed() {

        hideKeyboard();
        String workplace = workplace_edit.getText().toString().trim();
        String designation = designation_edit.getText().toString().trim();
        String location = location_edit.getText().toString().trim();

        boolean working = working_check.isChecked();
        boolean showon = toggleButton.isChecked();

        String startdate = fromdate_edit.getText().toString();
        String tilldate = toDateEdtTxt.getText().toString();

        if (oper.equals("add") && (!TextUtils.isEmpty(workplace) || !TextUtils.isEmpty(designation) || !TextUtils.isEmpty(location) || !TextUtils.isEmpty(startdate) || !TextUtils.isEmpty(tilldate) || working == true || showon == true)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage("Do you want to save the changes");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    next_button.performClick();
                }
            });
            builder.setNegativeButton("Don't save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent();
                    setResult(Activity.RESULT_OK, in);
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else if (oper.equals("update") && (!professionalInfo.getWorkplace().equals(workplace) || !professionalInfo.getDesignation().equals(designation) || !professionalInfo.getLocation().equals(location) || professionalInfo.getStart_date() != milliseconds(startdate) || professionalInfo.getEnd_date() != milliseconds(tilldate) || professionalInfo.isWorking_here() != working || professionalInfo.isShowOncard() != showon)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage("Do you want to save the changes");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    next_button.performClick();
                }
            });
            builder.setNegativeButton("Don't save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent();
                    setResult(Activity.RESULT_OK, in);
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        } else {
            Intent in = new Intent();
            setResult(Activity.RESULT_OK, in);
            finish();
        }

    }


    private void showFromDatePicker(final EditText editText) {

        dpd = new DatePickerDialog(ProfessionalDetActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fromcal.set(year, monthOfYear, dayOfMonth);
                fromcal.add(Calendar.DATE, 0);
                editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                if (milliseconds(toDateEdtTxt.getText().toString()) < milliseconds(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)) {
                    toDateEdtTxt.setText("");
                }
            }
        }, fromcal.get(Calendar.YEAR), fromcal.get(Calendar.MONTH), fromcal.get(Calendar.DATE));
        dpd.getDatePicker().setMaxDate(new Date().getTime());
        dpd.getDatePicker().setMinDate(-19800000);
        dpd.setCancelable(true);
    }


    private void showToDatePicker(final EditText editText) {

        todpd = new DatePickerDialog(ProfessionalDetActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tocal.set(year, monthOfYear, dayOfMonth);
                tocal.add(Calendar.DATE, 0);

                editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                tomillisec = milliseconds(editText.getText().toString());
            }
        }, tocal.get(Calendar.YEAR), tocal.get(Calendar.MONTH), tocal.get(Calendar.DATE));
        todpd.getDatePicker().setMinDate(frommillisec);
        todpd.getDatePicker().setMaxDate(new Date().getTime());
        todpd.setCancelable(true);
    }

    private String convertLongtoDate(Long millisec) {
        Long millisec_ = millisec;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millisec);
            return formatter.format(calendar.getTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }


    public long milliseconds(String date) {
        if (!date.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date mDate = sdf.parse(date);
                long timeInMilliseconds = mDate.getTime();
                System.out.println("Date in milli :: " + timeInMilliseconds);
                return timeInMilliseconds;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return 0;
    }

    public Date convertStringToDate(String sdate) {
        Date mDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            mDate = sdf.parse(sdate);
        } catch (Exception e) {

        }
        return mDate;
    }

    @Override
    public void onTaskCompleted(String response) {
        hideProgress();
        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
                ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {
                            JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            if (data.has(RestUtils.TAG_PROFESSIONAL_HISTORY)) {

                                JSONArray profHistory = data.optJSONArray(RestUtils.TAG_PROFESSIONAL_HISTORY);
                                JSONObject professionalObj = profHistory.optJSONObject(0);
                                professionalInfo.setProf_id(professionalObj.optInt(RestUtils.TAG_PROF_ID));
                                professionalInfo.setWorkplace(professionalObj.optString(RestUtils.TAG_WORKPLACE));
                                professionalInfo.setDesignation(professionalObj.optString(RestUtils.TAG_DESIGNATION));
                                professionalInfo.setLocation(professionalObj.optString(RestUtils.TAG_LOCATION));
                                professionalInfo.setStart_date(professionalObj.optLong(RestUtils.TAG_FROMDATE));
                                professionalInfo.setEnd_date(professionalObj.optLong(RestUtils.TAG_TODATE));
                                professionalInfo.setShowOncard(professionalObj.optBoolean(RestUtils.TAG_SHOW_ON_CARD));
                                professionalInfo.setWorking_here(professionalObj.optBoolean(RestUtils.TAG_WORKING));
                                if (professionalObj.has(RestUtils.TAG_AVAILABLE_DAYS)) {
                                    JSONObject availableDaysObj = professionalObj.optJSONObject(RestUtils.TAG_AVAILABLE_DAYS);

                                    JSONArray weekDaysArray = availableDaysObj.optJSONArray(RestUtils.TAG_WEEK_OF_DAYS);
                                    String convertedArr = weekDaysArray.toString().substring(1, weekDaysArray.toString().length() - 1);
                                    professionalInfo.setAvailableDays(convertedArr);
                                    professionalInfo.setStartTime(availableDaysObj.optLong(RestUtils.TAG_FROM_TIME));
                                    professionalInfo.setEndTime(availableDaysObj.optLong(RestUtils.TAG_TO_TIME));
                                    professionalInfo.setWorkOptions(availableDaysObj.optString(RestUtils.TAG_DEPARTMENT));
                                }

                                if (oper.equals("update")) {
                                    Toast.makeText(ProfessionalDetActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                                    realmManager.updateProfessionInfo(realm, professionalInfo);
                                    Intent in = new Intent();
                                    setResult(Activity.RESULT_OK, in);
                                    finish();
                                } else {
                                    realmManager.insertProfession(realm, professionalInfo);
                                }
                                professionalInfo = new ProfessionalInfo();
                                if (add_another == false) {
                                    Toast.makeText(ProfessionalDetActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                                    Intent in = new Intent();
                                    setResult(Activity.RESULT_OK, in);
                                    finish();

                                } else {
                                    add_another = false;
                                    final Toast toast = Toast.makeText(ProfessionalDetActivity.this, "Workplace Saved", Toast.LENGTH_SHORT);
                                    toast.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            toast.cancel();
                                        }
                                    }, 1000);
                                    workplace_edit.setText("");
                                    designation_edit.setText("");
                                    fromdate_edit.setText("");
                                    location_edit.setText("");
                                    isCitySelectedFromList=false;
                                    working_check.setChecked(false);
                                    toDateEdtTxt.setText("");
                                    workplace_edit.requestFocus();
                                    toDateEdtTxt.setVisibility(View.VISIBLE);
                                    till_date_txt.setVisibility(View.GONE);
                                    availableParentLayout.setVisibility(View.GONE);
                                    toggleButton.setChecked(false);
                                    startTimeEdt.setText("");
                                    endTimeEdt.setText("");
                                    //opdRadioBtn.setChecked(false);
                                    //otRadioBtn.setChecked(false);
                                    opd_ot_rg.clearCheck();
                                    rv_delete_layout.setVisibility(View.GONE);
                                    add_button.setVisibility(View.VISIBLE);
                                }


                            } else {
                                JSONArray citiesjArray = data.getJSONArray("cities");
                            }
                        }
                        hideProgress();

                    } else {
                        hideProgress();
                        ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));

                    }
                } catch (Exception e) {
                    if (response.contains("FileNotFoundException")) {
                        AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), ProfessionalDetActivity.this);

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


    @Override
    public void onClick(View view) {
        if (view instanceof RadioButton) {
            RadioButton radioBtn = (RadioButton) view;
            department = radioBtn.getText().toString();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ProfessionDetActivity", getString(R.string._onDestroy));
        department = null;
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
                    // showCurrentLocation();
                    // getLocationUsingAPI();

                    locationHelperObj = new LocationHelperClass(ProfessionalDetActivity.this, new LocationCaputerListner() {
                        @Override
                        public void onLocationCapture(Location location) {
                            if (location != null) {
                                double lat = location.getLatitude();
                                double longi = location.getLongitude();

                                requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(ProfessionalDetActivity.this, lat, longi, api_key, new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {

                                        try {
                                            JSONObject responseJsonObject=new JSONObject(successResponse);
                                            if(responseJsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                AppUtil.getAddressDetailsUsingAPI(ProfessionalDetActivity.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                    @Override
                                                    public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject jsonObject) {

                                                        if(jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                            if (!isCityEditText) {
                                                                if (apiStringHashMap.containsKey("workplace")) {
                                                                    workplace_edit.setText(apiStringHashMap.get("workplace"));
                                                                    workplace_edit.setSelection(workplace_edit.length());
                                                                    workplace_edit.dismissDropDown();
                                                                }
                                                            }

                                                            if (apiStringHashMap.containsKey("city")) {
                                                                cityName = apiStringHashMap.get("city");
                                                                location_edit.setText(apiStringHashMap.get("city"));
                                                                location_edit.setSelection(location_edit.length());
                                                                location_edit.dismissDropDown();
                                                            }
                                                            isCitySelectedFromList=true;
                                                            if(isWorkEditText){
                                                                workPlaceJson=jsonObject;
                                                                cityJson=jsonObject;
                                                            }else{
                                                                cityJson=jsonObject;
                                                            }
                                                        }else {
                                                            String errorMsg = "Unable to fetch Geocode Details";
                                                            if (jsonObject.has("error_message")) {
                                                                errorMsg = jsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj=new JSONObject();
                                                            try {
                                                                errorObj.put("screen","UserProfile");
                                                                errorObj.put("searchKey",workplace_edit.getText().toString());
                                                                errorObj.put("errorMsg",errorMsg);
                                                                AppUtil.logUserActionEvent(doctorID, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }


                                                    }

                                                    @Override
                                                    public void onLocationApiError(String error) {
                                                        JSONObject errorObj=new JSONObject();
                                                        try {
                                                            errorObj.put("screen","UserProfile");
                                                            if(isWorkEditText){
                                                                errorObj.put("searchKey", workplace_edit.getText().toString());
                                                            }else {
                                                                errorObj.put("searchKey", location_edit.getText().toString());
                                                            }
                                                            errorObj.put("errorMsg",error);
                                                            if(isWorkEditText){
                                                                AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);

                                                            }else{
                                                                AppUtil.logUserActionEvent(doctorID, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                            }
                                                            AppUtil.logUserActionEvent(doctorID, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                });
                                            }else{
                                                String errorMsg = "Unable to fetch User current location";
                                                if (responseJsonObject.has("error_message")) {
                                                    errorMsg = responseJsonObject.optString("error_message");
                                                }
                                                JSONObject errorObj=new JSONObject();
                                                try {
                                                    errorObj.put("screen","UserProfile");
                                                    if(isWorkEditText){
                                                        errorObj.put("searchKey",workplace_edit.getText().toString());
                                                    }else{
                                                        errorObj.put("searchKey",location_edit.getText().toString());

                                                    }                                                    errorObj.put("errorMsg",errorMsg);
                                                    if(isWorkEditText){
                                                        AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);

                                                    }else {
                                                        AppUtil.logUserActionEvent(doctorID, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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
                                        JSONObject errorObj=new JSONObject();
                                        try {
                                            errorObj.put("screen","UserProfile");
                                            if(isWorkEditText){
                                                errorObj.put("searchKey",workplace_edit.getText().toString());
                                            }else{
                                                errorObj.put("searchKey",location_edit.getText().toString());

                                            }
                                            errorObj.put("errorMsg",errorResponse);
                                            if(isWorkEditText){
                                                AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);

                                            }else {
                                                AppUtil.logUserActionEvent(doctorID, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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
                if (locationHelperObj != null) {
                    locationHelperObj.getLocation(new LocationCaputerListner() {
                        @Override
                        public void onLocationCapture(Location location) {
                            Location _location = location;
                            if (_location != null) {
                                double lat = _location.getLatitude();
                                double longi = _location.getLongitude();
                                requestForCurrentLocPlacesUsingPlaceId = new RequestForCurrentLocPlacesUsingPlaceId(ProfessionalDetActivity.this, lat, longi, api_key, new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        //
                                        try {
                                            JSONObject jsonObject=new JSONObject(successResponse);
                                            if(jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                AppUtil.getAddressDetailsUsingAPI(ProfessionalDetActivity.this, successResponse, api_key, new OnlocationApiFinishedListener() {
                                                    @Override
                                                    public void onlocationApiFinishedListener(HashMap<String, String> apiStringHashMap, JSONObject jsonObject) {
                                                        if(jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase("OK")){
                                                            if (!isCityEditText) {
                                                                if (apiStringHashMap.containsKey("workplace")) {
                                                                    workplace_edit.setText(apiStringHashMap.get("workplace"));
                                                                    workplace_edit.setSelection(workplace_edit.length());
                                                                    workplace_edit.dismissDropDown();
                                                                }
                                                            }
                                                            if (apiStringHashMap.containsKey("city")) {
                                                                cityName = apiStringHashMap.get("city");
                                                                location_edit.setText(apiStringHashMap.get("city"));
                                                                location_edit.setSelection(location_edit.length());
                                                                location_edit.dismissDropDown();
                                                            }
                                                            isCitySelectedFromList=true;
                                                            cityJson = jsonObject;
                                                            workPlaceJson = jsonObject;
                                                            isExactCity = true;
                                                        }else{
                                                            cityJson=null;
                                                            workPlaceJson=null;
                                                            String errorMsg = "Unable to fetch Geocode Details";
                                                            if (jsonObject.has("error_message")) {
                                                                errorMsg = jsonObject.optString("error_message");
                                                            }
                                                            JSONObject errorObj=new JSONObject();
                                                            try {
                                                                errorObj.put("screen","UserProfile");
                                                                if(isCityEditText){
                                                                    errorObj.put("searchKey",workplace_edit.getText().toString());
                                                                }else {
                                                                    errorObj.put("searchKey",location_edit.getText().toString());
                                                                }                                                                errorObj.put("errorMsg",errorMsg);
                                                                AppUtil.logUserActionEvent(doctorID, "FetchGeoLocationFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onLocationApiError(String error) {
                                                        JSONObject errorObj=new JSONObject();
                                                        try {
                                                            errorObj.put("screen","UserProfile");
                                                            if(isCityEditText){
                                                                errorObj.put("searchKey",workplace_edit.getText().toString());
                                                            }else {
                                                                errorObj.put("searchKey",location_edit.getText().toString());
                                                            }
                                                            errorObj.put("errorMsg",error);
                                                            if(isCityEditText){
                                                                AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                            }else{
                                                                AppUtil.logUserActionEvent(doctorID, "FetchCityCityFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                });
                                            }else{
                                                workPlaceJson=null;
                                                cityJson=null;

                                                String errorMsg = "Unable to fetch User current location";
                                                if (jsonObject.has("error_message")) {
                                                    errorMsg = jsonObject.optString("error_message");
                                                }
                                                JSONObject errorObj=new JSONObject();
                                                try {
                                                    errorObj.put("screen","UserProfile");
                                                    if(isCityEditText){
                                                        errorObj.put("searchKey",workplace_edit.getText().toString());
                                                    }else {
                                                        errorObj.put("searchKey",location_edit.getText().toString());
                                                    }                                                    errorObj.put("errorMsg",errorMsg);
                                                    if(isCityEditText){
                                                        AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                                    }else{
                                                        AppUtil.logUserActionEvent(doctorID, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
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
                                        JSONObject errorObj=new JSONObject();
                                        try {
                                            errorObj.put("screen","UserProfile");
                                            if(isCityEditText){
                                                errorObj.put("searchKey",workplace_edit.getText().toString());
                                            }else {
                                                errorObj.put("searchKey",location_edit.getText().toString());
                                            }
                                            errorObj.put("errorMsg",errorResponse);
                                            if(isCityEditText){
                                                AppUtil.logUserActionEvent(doctorID, "FetchWorkLocationPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                            }else{
                                                AppUtil.logUserActionEvent(doctorID, "FetchCityPlaceFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),ProfessionalDetActivity.this);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ProfessionalDetActivity.this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isWorkEditText) {
            workPlaceJson = new JSONObject();
        }
        if (isCityEditText) {
            cityJson = new JSONObject();
            isCitySelectedFromList=false;
            if (s.length() > 0) {
                clear_text.setVisibility(View.VISIBLE);
            }else{
                clear_text.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
