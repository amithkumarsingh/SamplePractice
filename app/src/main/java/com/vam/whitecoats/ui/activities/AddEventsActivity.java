package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.EventInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class AddEventsActivity extends BaseActionBarActivity {
    public static final String TAG = AddEventsActivity.class.getSimpleName();
    private EditText et_title, start_date, end_date;
    private DatePicker dpResult;
    AutoCompleteTextView event_location;
    TextView text_title, text_start_date, text_end_date, events_heading;
    private int year;
    private int month;
    private int day;

    static final int START_DATE_DIALOG_ID = 1;
    static final int END_DATE_DIALOG_ID2 = 2;
    int cur = 0;
    Button add_button;
    RelativeLayout rv_delete_layout;
    TextView next_button;
    private ArrayList<String> cities_array = new ArrayList<>();
    ArrayAdapter<String> cities_adpt;
    EventInfo eventInfo;
    static String oper = "add";
    private long mLastClickTime = 0;
    int doctorID;
    private Realm realm;
    private RealmManager realmManager;

    boolean add_another = false;
    Long selectedStartDate, selectedendDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        setCurrentDateOnView();
        addListenerOnButton();
        initialize();
        validationUtils = new ValidationUtils(AddEventsActivity.this, new EditText[]{et_title, start_date, end_date}, new TextView[]{text_title, text_start_date, text_end_date});

        ArrayList<String> searchkeys = new ArrayList<String>();
        searchkeys.add("cities");
        if (isConnectingToInternet()) {
            new AutoSuggestionsAsync(AddEventsActivity.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
        }

        cities_adpt = new ArrayAdapter<String>(AddEventsActivity.this, android.R.layout.simple_list_item_1, cities_array);
        event_location.setAdapter(cities_adpt);
        event_location.setThreshold(1);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (realmManager.getEventsListFromDB().size() >= 25) {
                    Toast.makeText(AddEventsActivity.this, "Max limit reached", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Long time = calendar.getTimeInMillis();
                try {
                    hideKeyboard();
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    } else {
                        mLastClickTime = SystemClock.elapsedRealtime();
                        if (isConnectingToInternet()) {

                            if (selectedStartDate <= selectedendDate && selectedStartDate >= time) {

                                String title = et_title.getText().toString().trim();
                                String location = event_location.getText().toString().trim();
                                String startDate = start_date.getText().toString().trim();
                                String endDate = end_date.getText().toString().trim();
                                if (validationUtils.isValidThreeField(title, startDate, endDate)) {
                                    eventInfo.setEventTitle(title);
                                    eventInfo.setStartDate(selectedStartDate);
                                    eventInfo.setEndDate(selectedendDate);
                                    eventInfo.setLocation(location);


                                    add_another = true;
                                    /** Creating JSONObject **/
                                    JSONObject object = getEventRequestJson(eventInfo);
                                    requestEventService(object);
                                }
                            } else {
                                if (selectedStartDate < selectedendDate) {
                                    Toast.makeText(AddEventsActivity.this, R.string.start_date, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AddEventsActivity.this, R.string.end_date, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                            else {
                                hideProgress();
                            }

                    }
                } catch (Exception e) {
                    hideProgress();
                    e.printStackTrace();
                }
            }
        });
    }

    public void initialize() {
        Log.i(TAG, "initialize()");
        try {
            et_title = _findViewById(R.id.event_title);
            event_location = _findViewById(R.id.event_location);
            add_button = _findViewById(R.id.add_button);
            rv_delete_layout = _findViewById(R.id.delete_layout);
            events_heading = _findViewById(R.id.events_heading);
            /** Error TextViews **/
            text_title = _findViewById(R.id.title_error_text);
            text_start_date = _findViewById(R.id.start_date_error);
            text_end_date = _findViewById(R.id.end_date_error);
            oper = "add";
            eventInfo = (EventInfo) getIntent().getSerializableExtra("eventcalendarinfo");
            if (eventInfo != null) {
                oper = "update";
                et_title.setText(eventInfo.getEventTitle());
                event_location.setText(eventInfo.getLocation());
                start_date.setText(DateUtils.convertLongtoDates(eventInfo.getStartDate()));
                end_date.setText(DateUtils.convertLongtoDates(eventInfo.getEndDate()));
                selectedStartDate = eventInfo.getStartDate();
                selectedendDate = eventInfo.getEndDate();
                rv_delete_layout.setVisibility(View.VISIBLE);
                add_button.setVisibility(View.GONE);

            } else {
                eventInfo = new EventInfo();
            }
            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(this);
            doctorID = realmManager.getDoc_id(realm);

            mInflater = LayoutInflater.from(this);
            mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            next_button = (TextView) mCustomView.findViewById(R.id.next_button);
            mTitleTextView.setText(getString(R.string.add_event_actionbar_title));
            next_button.setText(getString(R.string.actionbar_save));
            if (oper.equals("update"))
                mTitleTextView.setText(getString(R.string.edit_event_actionbar_title));


            final List<EventInfo> realmEventInfo = realmManager.getEventCalendarInfo(realm);

            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (oper.equals("add") &&realmManager.getEventsListFromDB().size() >= 25) {
                        Toast.makeText(AddEventsActivity.this, "Max limit reached", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    Long time = calendar.getTimeInMillis();
                    try {
                        hideKeyboard();
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        } else {
                            mLastClickTime = SystemClock.elapsedRealtime();
                            if (isConnectingToInternet()) {

                                if (selectedStartDate <= selectedendDate && selectedStartDate >= time) {

                                    String title = et_title.getText().toString().trim();
                                    String location = event_location.getText().toString().trim();
                                    String startDate = start_date.getText().toString().trim();
                                    String endDate = end_date.getText().toString().trim();
                                    if (validationUtils.isValidThreeField(title, startDate, endDate)) {
                                        eventInfo.setEventTitle(title);
                                        eventInfo.setStartDate(selectedStartDate);
                                        eventInfo.setEndDate(selectedendDate);
                                        eventInfo.setLocation(location);

                                        add_another = false;
                                        /** Creating JSONObject **/
                                        JSONObject object = getEventRequestJson(eventInfo);
                                        requestEventService(object);
                                    }
                                } else {
                                    if(selectedStartDate < selectedendDate) {
                                        Toast.makeText(AddEventsActivity.this, R.string.start_date, Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(AddEventsActivity.this, R.string.end_date, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                hideProgress();
                            }
                        }
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            rv_delete_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        } else {
                            mLastClickTime = SystemClock.elapsedRealtime();
                            if (isConnectingToInternet()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddEventsActivity.this);
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
                                            JSONObject eventCalendarObj = new JSONObject();
                                            JSONArray eventVCalendarArray = new JSONArray();
                                            eventCalendarObj.put(RestUtils.TAG_EVENT_ID, eventInfo.getEventId());
                                            requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                            eventVCalendarArray.put(eventCalendarObj);
                                            requestObject.put(RestUtils.TAG_EVENTS, eventVCalendarArray);
                                            showProgress();
                                            new VolleySinglePartStringRequest(AddEventsActivity.this, Request.Method.POST, RestApiConstants.DELETE_USER_PROFILE, requestObject.toString(), "EVENT_DETAILS_ACTIVITY_DELETE", new OnReceiveResponse() {
                                                @Override
                                                public void onSuccessResponse(String successResponse) {
                                                    hideProgress();
                                                    JSONObject responseObj = null;
                                                    try {
                                                        responseObj = new JSONObject(successResponse);
                                                        if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                            Toast.makeText(AddEventsActivity.this, getResources().getString(R.string.profile_update), Toast.LENGTH_SHORT).show();
                                                            realmManager.deleteEventCalendar(realm, eventInfo.getEventId());
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

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(mCustomView);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject getEventRequestJson(EventInfo eventInfo) {

        JSONObject object = new JSONObject();
        try {
            JSONObject eventObj = new JSONObject();
            JSONArray eventArray = new JSONArray();
            if (oper.equals(RestUtils.TAG_UPDATE)) {
                eventObj.put(RestUtils.TAG_EVENT_ID, eventInfo.getEventId());
            }

            eventObj.put(RestUtils.TAG_EVENT_NAME, eventInfo.getEventTitle());
            eventObj.put(RestUtils.TAG_EVENT_LOCATION, eventInfo.getLocation());
            eventObj.put(RestUtils.TAG_EVENT_START_DATE, eventInfo.getStartDate());
            eventObj.put(RestUtils.TAG_EVENT_END_DATE, eventInfo.getEndDate());
            eventArray.put(eventObj);
            object.put(RestUtils.TAG_USER_ID, doctorID);
            object.put(RestUtils.TAG_EVENTS, eventArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
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
                        if (data.has(RestUtils.TAG_EVENTS)) {
                            JSONArray events = data.optJSONArray(RestUtils.TAG_EVENTS);
                            JSONObject eventObj = events.optJSONObject(0);
                            eventInfo.setEventId(eventObj.optInt(RestUtils.TAG_EVENT_ID));
                            eventInfo.setEventTitle(eventObj.optString(RestUtils.TAG_EVENT_NAME));
                            eventInfo.setLocation(eventObj.optString(RestUtils.TAG_EVENT_LOCATION));
                            eventInfo.setStartDate(eventObj.optLong(RestUtils.TAG_EVENT_START_DATE));
                            eventInfo.setEndDate(eventObj.optLong(RestUtils.TAG_EVENT_END_DATE));

                            if (oper.equals("update")) {
                                Toast.makeText(AddEventsActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                                realmManager.updateEventInfo(realm, eventInfo);
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();
                            } else {
                                realmManager.insertEventCalendar(realm, eventInfo);
                            }

                            eventInfo = new EventInfo();
                            if (add_another == false) {
                                Toast.makeText(AddEventsActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();
                            } else {
                                add_another = false;
                                final Toast toast = Toast.makeText(AddEventsActivity.this, "Event Saved", Toast.LENGTH_SHORT);
                                toast.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast.cancel();
                                    }
                                }, 1000);
                                et_title.setText("");
                                event_location.setText("");
                                setCurrentDateOnView();

                                rv_delete_layout.setVisibility(View.GONE);
                                add_button.setVisibility(View.VISIBLE);
                            }

                        } else if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            hideProgress();
                            String errorMsg = getResources().getString(R.string.unknown_server_error);
                            if (!jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE).isEmpty()) {
                                errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                            }
                            ShowSimpleDialog("Error", errorMsg);
                        } else {
                            JSONArray citiesjArray = data.getJSONArray("cities");
                            int citieslen = citiesjArray.length();
                            if (citieslen > 0) {
                                for (int i = 0; i < citieslen; i++) {
                                    cities_array.add(citiesjArray.get(i).toString());
                                    cities_adpt.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    hideProgress();
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    public void setCurrentDateOnView() {

        start_date = (EditText) findViewById(R.id.start_date_editText);
        end_date = (EditText) findViewById(R.id.end_date_editText);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        // set current date into textview
        start_date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("/").append(month + 1).append("/")
                .append(year).append(" "));
        selectedStartDate = c.getTimeInMillis();
        selectedendDate = c.getTimeInMillis();
        end_date.setText(start_date.getText().toString());
    }

    public void addListenerOnButton() {

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = START_DATE_DIALOG_ID;
                showDialog(START_DATE_DIALOG_ID);
            }
        });
        //btnChangeDate2 = (Button) findViewById(R.id.btnChangeDate2);

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = END_DATE_DIALOG_ID2;
                showDialog(END_DATE_DIALOG_ID2);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case START_DATE_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = START_DATE_DIALOG_ID;
                if (!start_date.getText().toString().isEmpty()) {
                    String[] dateStrings = start_date.getText().toString().trim().split("/");
                    year=Integer.parseInt(dateStrings[2]);
                    month=Integer.parseInt(dateStrings[1])-1;
                    day=Integer.parseInt(dateStrings[0]);
                }
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
            case END_DATE_DIALOG_ID2:
                cur = END_DATE_DIALOG_ID2;
                System.out.println("onCreateDialog2  : " + id);
                if (!end_date.getText().toString().isEmpty()) {
                    String[] dateStrings = end_date.getText().toString().trim().split("/");
                    year=Integer.parseInt(dateStrings[2]);
                    month=Integer.parseInt(dateStrings[1])-1;
                    day=Integer.parseInt(dateStrings[0]);
                }
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    @Override
    public void onBackPressed() {

        hideKeyboard();

        String title = et_title.getText().toString().trim();
        String location = event_location.getText().toString().trim();

        String startdate = start_date.getText().toString();
        String tilldate = end_date.getText().toString();
        if (oper.equals("add") && (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(location))) {

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
        } else if (oper.equals("update") && (!eventInfo.getEventTitle().equals(title) || !eventInfo.getLocation().equals(location) || eventInfo.getStartDate() != milliseconds(startdate) || eventInfo.getEndDate() != milliseconds(tilldate))) {
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

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if (cur == START_DATE_DIALOG_ID) {
                // set selected date into textview
                start_date.setText(new StringBuilder().append(day)
                        .append("/").append(month + 1).append("/").append(year)
                        .append(" "));
                selectedStartDate = calendar.getTimeInMillis();

            } else {
                end_date.setText(new StringBuilder().append(day)
                        .append("/").append(month + 1).append("/").append(year)
                        .append(" "));
                selectedendDate = calendar.getTimeInMillis();
            }
        }
    };


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

    public void requestEventService(JSONObject request) {

        String URL = RestApiConstants.CREATE_USER_PROFILE;
        if (oper.equals(RestUtils.TAG_UPDATE)) {
            URL = RestApiConstants.UPDATE_USER_PROFILE;
        }
        showProgress();
        new VolleySinglePartStringRequest(AddEventsActivity.this, Request.Method.POST, URL, request.toString(), "EVENT_DETAILS_ACTIVITY_SAVEORUPDATE", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                hideProgress();
                onTaskCompleted(successResponse);
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                hideProgress();
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();
    }

    public long milliseconds(String date) {
        String date_ = date;
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

        return 0;
    }
}
