package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.customviews.MonthYearPickerDialog;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by swathim on 07-07-2015.
 */
public class ProfessionalMemActivity extends BaseActionBarActivity implements OnTaskCompleted {

    private AutoCompleteTextView etMembership;
    EditText awardsTitle, presentedAtEdtTxt, yearEdtTxt;
    private TextView removeButton;
    LinearLayout awardParentLayout, memberParentLayout;
    Context context;
    ProfessionalMembershipInfo professionalMembershipInfo = null;
    private ArrayList<String> associations_array = new ArrayList<>();
    ArrayAdapter<String> associations_adpt;
    TextView next_button;
    int doctorID;
    String type;
    int id;
    String presentedYear;
    String presentedAt;
    String title;
    ArrayList<String> associationList = new ArrayList<>();
    ArrayAdapter<String> associationAdapter;
    private Realm realm;
    private RealmManager realmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_mem);
        context = this;
        initializeUI();

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
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        etMembership = (AutoCompleteTextView)findViewById(R.id.add_membership);
        removeButton = (TextView)findViewById(R.id.remove_button);
        awardParentLayout = (LinearLayout)findViewById(R.id.awards_parent_layout);
        memberParentLayout = (LinearLayout)findViewById(R.id.membership_parent_layout);
        awardsTitle = (EditText) findViewById(R.id.awards_title);
        presentedAtEdtTxt = (EditText) findViewById(R.id.presented_at);
        yearEdtTxt = (EditText) findViewById(R.id.year_awards);
        professionalMembershipInfo = (ProfessionalMembershipInfo) getIntent().getSerializableExtra("membershipInfo");
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        next_button.setText(getString(R.string.actionbar_save));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        doctorID = realmManager.getDoc_id(realm);

        if (professionalMembershipInfo != null) {
            type = professionalMembershipInfo.getType();
            if (type == null || type.isEmpty())
                type = "membership"; //default value should be membership
            if (type.equalsIgnoreCase("award")) {
                awardParentLayout.setVisibility(View.VISIBLE);
                memberParentLayout.setVisibility(View.GONE);
                id = professionalMembershipInfo.getAward_id();
                presentedYear = professionalMembershipInfo.getAward_year()==0?"":""+professionalMembershipInfo.getAward_year();
                presentedAt = professionalMembershipInfo.getPresented_at();
                title = professionalMembershipInfo.getAward_name();
                awardsTitle.setText(title);
                presentedAtEdtTxt.setText(presentedAt);
                yearEdtTxt.setText(presentedYear);
                //Set for year picker
                yearEdtTxt.setInputType(InputType.TYPE_NULL);
                yearEdtTxt.requestFocus();
                yearEdtTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(ProfessionalMemActivity.this);
                    }
                });
                mTitleTextView.setText("Edit Award");
                removeButton.setText(getString(R.string.str_remove_award));

            } else if (type.equalsIgnoreCase("membership")) {
                ArrayList<String> searchKeys = new ArrayList<String>();
                searchKeys.add("associations");
                if (AppUtil.isConnectingToInternet(this)) {
                    new AutoSuggestionsAsync(this, searchKeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
                }
                associationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, associationList);
                awardParentLayout.setVisibility(View.GONE);
                memberParentLayout.setVisibility(View.VISIBLE);
                id = professionalMembershipInfo.getProf_mem_id();
                title = professionalMembershipInfo.getMembership_name();
                etMembership.setText(title);
                mTitleTextView.setText("Edit Membership");
                removeButton.setText(getString(R.string.str_remove_mem));
            }
        }
        etMembership.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etMembership.setAdapter(associationAdapter);
                etMembership.setThreshold(1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hideKeyboard();
                    if (!TextUtils.isEmpty(title)) {
                        showProgress();
                        JSONObject requestObj = getRequestJson(type);
                        requestEditService(requestObj);
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning_empty_membership_title), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    hideProgress();
                    e.printStackTrace();
                }
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hideKeyboard();
                    if (isConnectingToInternet()) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfessionalMemActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle(getResources().getString(R.string.profile_delete));
                        builder.setMessage(getResources().getString(R.string.profile_delete_other));
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    /** Creating JSON **/
                                    JSONObject requestObject = new JSONObject();
                                    if (type.equalsIgnoreCase("award")) {
                                        JSONObject awardObj = new JSONObject();
                                        JSONArray awardArray = new JSONArray();
                                        awardObj.put(RestUtils.TAG_AWARD_ID, id);
                                        awardObj.put(RestUtils.TAG_TYPE, "award");
                                        awardArray.put(awardObj);
                                        requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                        requestObject.put(RestUtils.TAG_AWARD_HISTORY, awardArray);
                                    } else {
                                        JSONObject memberShipObj = new JSONObject();
                                        JSONArray memberShipArray = new JSONArray();
                                        memberShipObj.put(RestUtils.TAG_MEM_ID, id);
                                        memberShipObj.put(RestUtils.TAG_TYPE, "membership");
                                        memberShipArray.put(memberShipObj);
                                        requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                        requestObject.put(RestUtils.TAG_MEM_HISTORY, memberShipArray);
                                    }
                                    showProgress();
                                    new VolleySinglePartStringRequest(ProfessionalMemActivity.this, Request.Method.POST, RestApiConstants.DELETE_USER_PROFILE, requestObject.toString(), "AWARDS_MEMBERSHIPS_DELETE", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            hideProgress();
                                            realmManager.deleteProfessionalMemName(realm, id,type);
                                            Intent in = new Intent();
                                            setResult(Activity.RESULT_OK, in);
                                            finish();
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
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        hideProgress();
                    }
                } catch (Exception e) {
                    hideProgress();
                    e.printStackTrace();
                }
            }
        });

    }

    private void requestEditService(JSONObject request) {
        if (isConnectingToInternet()) {
            new VolleySinglePartStringRequest(ProfessionalMemActivity.this, Request.Method.POST, RestApiConstants.UPDATE_USER_PROFILE, request.toString(), "AWARDS_MEMBERSHIPS_SAVE", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    hideProgress();
                    onTaskCompleted(successResponse);
                    Intent in = new Intent();
                    setResult(Activity.RESULT_OK, in);
                    finish();
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    hideProgress();
                    displayErrorScreen(errorResponse);
                }
            }).sendSinglePartRequest();

        } else {
            hideProgress();
        }
    }
    private void showDatePickerDialog(FragmentActivity activity) {

        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yearEdtTxt.setText("" + year);
            }
        });
        pd.show(activity.getFragmentManager(), "MonthYearPickerDialog");
    }
    private JSONObject getRequestJson(String type) {
        JSONObject requestObject = new JSONObject();
        try {
            if (type.equalsIgnoreCase("award")) {
                JSONObject awardObj = new JSONObject();
                JSONArray awardArray = new JSONArray();
                awardObj.put(RestUtils.TAG_AWARD_ID, id);
                awardObj.put(RestUtils.TITLE, awardsTitle.getText().toString().trim());
                awardObj.put(RestUtils.TAG_PRESENTED_AT, presentedAtEdtTxt.getText().toString().trim());
                awardObj.put(RestUtils.TAG_YEAR, yearEdtTxt.getText().toString().trim());
                awardObj.put(RestUtils.TAG_TYPE, "award");
                awardArray.put(awardObj);
                requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                requestObject.put(RestUtils.TAG_AWARD_HISTORY, awardArray);
            } else {
                JSONObject memberShipObj = new JSONObject();
                JSONArray memberShipArray = new JSONArray();
                memberShipObj.put(RestUtils.TAG_MEM_ID, id);
                memberShipObj.put(RestUtils.TITLE, etMembership.getText().toString().trim());
                memberShipObj.put(RestUtils.TAG_TYPE, "membership");
                memberShipArray.put(memberShipObj);
                requestObject.put(RestUtils.TAG_USER_ID, doctorID);
                requestObject.put(RestUtils.TAG_MEM_HISTORY, memberShipArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;

    }

    @Override
    public void onTaskCompleted(String response) {
        super.onTaskCompleted(response);
        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
                hideProgress();
                ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {
                            JSONObject data = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                            if (data.has(RestUtils.TAG_MEM_HISTORY)) {
                                JSONArray membershipArray = data.optJSONArray(RestUtils.TAG_MEM_HISTORY);
                                int len = membershipArray.length();
                                for (int i = 0; i < len; i++) {
                                    JSONObject membershipObj = membershipArray.optJSONObject(i);
                                    ProfessionalMembershipInfo professionalMembershipInfo = new ProfessionalMembershipInfo();
                                    professionalMembershipInfo.setProf_mem_id(membershipObj.optInt(RestUtils.TAG_MEM_ID));
                                    professionalMembershipInfo.setMembership_name(membershipObj.optString(RestUtils.TITLE));
                                    professionalMembershipInfo.setType(membershipObj.optString(RestUtils.TAG_TYPE));
                                    realmManager.updateProfessionalMem(realm, professionalMembershipInfo,type);
                                }
                            } else if (data.has(RestUtils.TAG_AWARD_HISTORY)) {
                                JSONArray awardsArray = data.optJSONArray(RestUtils.TAG_AWARD_HISTORY);
                                int len = awardsArray.length();
                                for (int i = 0; i < len; i++) {
                                    JSONObject awardObj = awardsArray.optJSONObject(i);
                                    ProfessionalMembershipInfo professionalMembershipInfo = new ProfessionalMembershipInfo();
                                    professionalMembershipInfo.setAward_id(awardObj.optInt(RestUtils.TAG_AWARD_ID));
                                    professionalMembershipInfo.setAward_name(awardObj.optString(RestUtils.TITLE));
                                    professionalMembershipInfo.setAward_year(awardObj.optLong(RestUtils.TAG_YEAR));
                                    professionalMembershipInfo.setPresented_at(awardObj.optString(RestUtils.TAG_PRESENTED_AT));
                                    professionalMembershipInfo.setType(awardObj.optString(RestUtils.TAG_TYPE));
                                    realmManager.updateProfessionalMem(realm, professionalMembershipInfo,type);
                                }
                            } else {
                                JSONArray associationArray = data.getJSONArray("associations");
                                int len = associationArray.length();
                                for (int i = 0; i < len; i++) {
                                    associationList.add(associationArray.get(i).toString());
                                    associationAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        hideProgress();

                    } else {
                        hideProgress();
                        AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), this);

                    }
                } catch (Exception e) {
                    if (response.contains("FileNotFoundException")) {
                        AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), this);

                    }
                    hideProgress();
                    e.printStackTrace();
                }
            }

        }


    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        String tempTitle = "";
        if (type!=null && type.equalsIgnoreCase("award")) {
            tempTitle = awardsTitle.getText().toString().trim();
        } else {
            tempTitle = etMembership.getText().toString().trim();

        }
        if (title!=null && !title.equalsIgnoreCase(tempTitle)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage("Would you like to save the changes");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    next_button.performClick();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            finish();
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
