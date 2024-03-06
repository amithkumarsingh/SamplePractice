package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AreasOfInterest;
import com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.vam.whitecoats.utils.RestUtils.TAG_STATUS;
import static com.vam.whitecoats.utils.RestUtils.TAG_SUCCESS;

public class AreaOfInterest extends BaseActionBarActivity {

    private TextView next_button;
    private EditText areaOfInterest_editText;
    private Button add_more_btn;
    private TextView remove_button,areaOfInterest_editText_error;
    private RealmManager realmManager;
    private Realm realm;
    private int doctorID;
    private String area_of_interset_text;

    AreasOfInterest areasOfInterest = null;
    boolean add_button_status = false;
    private Bundle bundle;
    private int interestId;
    private String interest_name_editText="";
    String oper = "add";
    private RealmResults<RealmAreasOfInterestInfo> areasOfInterestSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_of_interest);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        doctorID = realmManager.getDoc_id(realm);

        areasOfInterest = new AreasOfInterest();




        initViews();
    }

    @Override
    protected void setCurrentActivity() {

    }

    private void initViews() {


        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        next_button.setText(getString(R.string.actionbar_save));


        areaOfInterest_editText = (EditText) findViewById(R.id.areaOfInterest_editText);
        add_more_btn = (Button) findViewById(R.id.add_more_btn);
        remove_button = (TextView) findViewById(R.id.remove_button);
        areaOfInterest_editText_error = (TextView) findViewById(R.id.areaOfInterest_editText_error);
        add_more_btn.setVisibility(View.VISIBLE);

        areasOfInterestSize=realmManager.getAreasOfInterestList();


        validationUtils = new ValidationUtils(AreaOfInterest.this, new EditText[]{areaOfInterest_editText}, new TextView[]{areaOfInterest_editText_error});


        oper = "add";
        bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RestUtils.TAG_INTEREST_ID)) {
                interestId = bundle.getInt(RestUtils.TAG_INTEREST_ID);
                interest_name_editText = bundle.getString("interest_name", "");
                oper = "update";
                areaOfInterest_editText.setText(interest_name_editText);
                areaOfInterest_editText.setSelection(areaOfInterest_editText.getText().length());
                remove_button.setVisibility(View.VISIBLE);
                add_more_btn.setVisibility(View.GONE);
        }
        if (oper.equals("update")) {
            mTitleTextView.setText("Edit Area of Interest");
        }else{
            mTitleTextView.setText("Add Area of Interest");
        }



        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnectingToInternet()) {
                    add_button_status = false;
                    area_of_interset_text = areaOfInterest_editText.getText().toString();
                    if(oper.equals("add") && areasOfInterestSize.size()>=20) {
                        Toast.makeText(AreaOfInterest.this,"Reached max limit", Toast.LENGTH_SHORT).show();
                        return;
                    }
                        if (validationUtils.isAreaEnteder(area_of_interset_text)) {
                            JSONObject areaRequestObject = new JSONObject();
                            JSONObject areaArray = new JSONObject();
                            try {
                                areaRequestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                if (oper.equals(RestUtils.TAG_UPDATE)) {
                                    areaArray.put(RestUtils.TAG_INTEREST_ID, interestId);
                                }
                                areaArray.put(RestUtils.TAG_AREA_OF_INTEREST, area_of_interset_text);
                                areaRequestObject.put(RestUtils.TAG_AREAS_OF_INTEREST, new JSONArray().put(areaArray));

                                String URL = RestApiConstants.CREATE_USER_PROFILE;
                                if (oper.equals(RestUtils.TAG_UPDATE)) {
                                    URL = RestApiConstants.UPDATE_USER_PROFILE;
                                }
                                showProgress();
                                new VolleySinglePartStringRequest(AreaOfInterest.this, Request.Method.POST, URL, areaRequestObject.toString(), "AREA_OF_INTEREST_CREATE", new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        hideProgress();
                                        responseHandling(successResponse);
                                    }
                                    @Override
                                    public void onErrorResponse(String errorResponse) {
                                        hideProgress();
                                        displayErrorScreen(errorResponse);
                                    }
                                }).sendSinglePartRequest();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                } else {
                    Toast.makeText(AreaOfInterest.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

            }
        });

        add_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet()) {
                    add_button_status = true;

                    area_of_interset_text = areaOfInterest_editText.getText().toString();

                    if(areasOfInterestSize.size()<20) {

                        if (validationUtils.isAreaEnteder(area_of_interset_text)) {
                            JSONObject areaRequestObject = new JSONObject();
                            JSONObject areaArray = new JSONObject();
                            try {
                                areaRequestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                areaArray.put(RestUtils.TAG_AREA_OF_INTEREST, area_of_interset_text);
                                areaRequestObject.put(RestUtils.TAG_AREAS_OF_INTEREST, new JSONArray().put(areaArray));

                                showProgress();

                                new VolleySinglePartStringRequest(AreaOfInterest.this, Request.Method.POST, RestApiConstants.CREATE_USER_PROFILE, areaRequestObject.toString(), "AREA_OF_INTEREST_CREATE", new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        hideProgress();
                                        responseHandling(successResponse);


                                    }

                                    @Override
                                    public void onErrorResponse(String errorResponse) {
                                        hideProgress();
                                        displayErrorScreen(errorResponse);
                                    }
                                }).sendSinglePartRequest();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        Toast.makeText(AreaOfInterest.this,"Reached max limit", Toast.LENGTH_SHORT).show();
                    }



                } else {
                    Toast.makeText(AreaOfInterest.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

            }
        });


        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AreaOfInterest.this);
                    builder.setCancelable(true);
                    builder.setTitle(getString(R.string.profile_delete));
                    builder.setMessage(getString(R.string.profile_delete_other));
                    builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            oper = "delete";
                            JSONObject areaRequestObject = new JSONObject();
                            JSONObject areaArray = new JSONObject();
                            try {
                                areaRequestObject.put(RestUtils.TAG_USER_ID, doctorID);
                                if (oper.equals(RestUtils.TAG_DELETE)) {
                                    areaArray.put(RestUtils.TAG_INTEREST_ID, interestId);
                                }
                                areaRequestObject.put(RestUtils.TAG_AREAS_OF_INTEREST, new JSONArray().put(areaArray));
                                new VolleySinglePartStringRequest(AreaOfInterest.this, Request.Method.POST, RestApiConstants.DELETE_USER_PROFILE, areaRequestObject.toString(), "AREA_OF_INTEREST_DELETE", new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {

                                        JSONObject responseObj = null;
                                        try {
                                            responseObj = new JSONObject(successResponse);
                                            if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {

                                                Toast.makeText(AreaOfInterest.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                                realmManager.deleteAreaOfInterestInfo(realm, interestId);
                                                add_button_status = false;
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


                            } catch (JSONException e) {
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
        });


        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    private void responseHandling(String successResponse) {
        if (successResponse != null) {
            try {
                JSONObject jsonObject = new JSONObject(successResponse);
                if (jsonObject.getString(TAG_STATUS).equals(TAG_SUCCESS)) {
                    if (jsonObject.has(RestUtils.TAG_DATA)) {
                        JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                        if (data.has(RestUtils.TAG_AREAS_OF_INTEREST)) {
                            JSONArray areasOfInterestData = data.optJSONArray(RestUtils.TAG_AREAS_OF_INTEREST);
                            JSONObject areasOfInterestObj = areasOfInterestData.optJSONObject(0);
                            areasOfInterest.setInterestId(areasOfInterestObj.optInt(RestUtils.TAG_INTEREST_ID));
                            areasOfInterest.setInterestName(areasOfInterestObj.optString(RestUtils.TAG_AREA_OF_INTEREST));


                            if (oper.equals("update")) {
                                Toast.makeText(AreaOfInterest.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                realmManager.updateAreaOfInterestInfo(realm, areasOfInterest);
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();

                            } else {
                                realmManager.insertAreaOfInterest(realm, areasOfInterest);
                            }
                            if (add_button_status == false) {

                                Toast.makeText(AreaOfInterest.this, getResources().getString(R.string.profile_update), Toast.LENGTH_LONG).show();
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();
                            } else {
                                add_button_status = false;
                                Toast.makeText(AreaOfInterest.this, "Interest saved", Toast.LENGTH_SHORT).show();
                                areaOfInterest_editText.setText("");

                            }

                        }
                    }

                }else if(jsonObject.getString(TAG_STATUS).equals(RestUtils.TAG_ERROR)){
                    if(jsonObject.optInt(RestUtils.TAG_ERROR_CODE)==106){
                        Toast.makeText(AreaOfInterest.this,getString(R.string.str_area_interest_exist),Toast.LENGTH_SHORT).show();
                    }else{
                        displayErrorScreen(successResponse);
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        String interest_name=areaOfInterest_editText.getText().toString().trim();
            if (!TextUtils.isEmpty(interest_name) && oper.equals("add")) {
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

            } else if (interest_name_editText!=null && !interest_name_editText.equalsIgnoreCase(interest_name)&& oper.equals("update") && !TextUtils.isEmpty(interest_name)) {
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
                       Intent in = new Intent();
                        setResult(Activity.RESULT_OK, in);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }else{
                Intent in = new Intent();
                setResult(Activity.RESULT_OK, in);
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
