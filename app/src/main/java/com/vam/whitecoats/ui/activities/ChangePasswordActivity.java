package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.ConstsCore;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.realm.Realm;

/**
 * Base class for Change password as well as Forgot Password.
 * <p>
 * You can put a bundle parameter of RestUtils.NAVIGATATE_FROM to determine which class it navigated from.
 */
public class ChangePasswordActivity extends BaseActionBarActivity {
    private EditText editTxtOldPassword;
    private EditText editTxtNewPassword;
    private LinearLayout labelLayout;
    private Button actionButton;
    private static String OLD_PASSWORD = "";
    private static String NEW_PASSWORD = "";
    String navigateFrom;
    int doctorId;

    private TextView oldPasswordErrorTxt, newPasswordErrorTxt;
    private Bundle bundle;
    private RealmManager realmManager;
    private Realm realm;
    private Button oldPassword_transformation,newPassword_transformation;
    private boolean customBackButton=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bundle=getIntent().getExtras();
        if(bundle!=null) {
            navigateFrom = bundle.getString(RestUtils.NAVIGATATE_FROM, null);
        }
        initialize();
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        doctorId = realmManager.getDoc_id(realm);
        if (!realm.isClosed())
            realm.close();
        mySharedPref = new MySharedPref(this);
        mySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_REGISTRATION_FLAG, true);

        /**
         * Get the Navigation parameter from Bundle.
         */


        if (navigateFrom != null) {
            if (navigateFrom.equalsIgnoreCase(ProfileViewActivity.TAG)) {
                validationUtils = new ValidationUtils(ChangePasswordActivity.this,
                        new EditText[]{editTxtOldPassword, editTxtNewPassword},
                        new TextView[]{oldPasswordErrorTxt, newPasswordErrorTxt});
                labelLayout.setVisibility(View.INVISIBLE);
                editTxtOldPassword.setVisibility(View.VISIBLE);
                editTxtNewPassword.setHint(getString(R.string.label_new_password));
                actionButton.setText(getString(R.string.save_new_password));
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                validationUtils = new ValidationUtils(ChangePasswordActivity.this,
                        new EditText[]{editTxtNewPassword},
                        new TextView[]{newPasswordErrorTxt});
                labelLayout.setVisibility(View.VISIBLE);
                editTxtOldPassword.setVisibility(View.GONE);
                editTxtNewPassword.setHint(getString(R.string.newpassword));
                actionButton.setText(getString(R.string.btn_submit));
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }


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
        editTxtOldPassword = _findViewById(R.id.oldPasswordEditTxt);
        editTxtOldPassword.setLongClickable(false);
        editTxtNewPassword = _findViewById(R.id.newPasswordEditTxt);
        editTxtNewPassword.setLongClickable(false);
        oldPasswordErrorTxt = _findViewById(R.id.oldPasswordErrorTxt);
        newPasswordErrorTxt = _findViewById(R.id.newPasswordErrorTxt);
        labelLayout = _findViewById(R.id.labelLayout);
        actionButton = _findViewById(R.id.submitButton);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_reg, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        next_button.setVisibility(View.GONE);
        oldPassword_transformation=_findViewById(R.id.oldPassword_transformation_btn);
        newPassword_transformation=_findViewById(R.id.newPassword_transformation_btn);
        if (navigateFrom != null) {
            if (navigateFrom.equalsIgnoreCase(ProfileViewActivity.TAG)) {
                mTitleTextView.setText(getString(R.string.changepassword));
            } else {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(32,0,0,0);
                mTitleTextView.setLayoutParams(lp);
                mTitleTextView.setText(getString(R.string.create_newpassword));
            }
        }

        oldPassword_transformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPassword_transformation.getText().toString().equalsIgnoreCase("Show")) {
                    editTxtOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    oldPassword_transformation.setText("Hide");
                } else {
                    editTxtOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    oldPassword_transformation.setText("Show");
                }
                editTxtOldPassword.setSelection(editTxtOldPassword.getText().length());
            }
        });

        newPassword_transformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newPassword_transformation.getText().toString().equalsIgnoreCase("Show")) {
                    editTxtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newPassword_transformation.setText("Hide");
                } else {
                    editTxtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPassword_transformation.setText("Show");
                }
                editTxtNewPassword.setSelection(editTxtNewPassword.getText().length());
            }
        });
        editTxtOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    oldPassword_transformation.setVisibility(View.VISIBLE);
                } else {
                    oldPassword_transformation.setVisibility(View.GONE);
                    editTxtOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    oldPassword_transformation.setText("Show");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTxtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    newPassword_transformation.setVisibility(View.VISIBLE);
                } else {
                    newPassword_transformation.setVisibility(View.GONE);
                    editTxtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPassword_transformation.setText("Show");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }
    public void onChangePasswordButtonClick(View view) {
        OLD_PASSWORD = editTxtOldPassword.getText().toString().trim();
        NEW_PASSWORD = editTxtNewPassword.getText().toString().trim();
        String oldpassword = getIntent().getExtras().getString("oldpassword"); //"password";
        String userid = bundle.getString("email_id"); // "username";
        if (navigateFrom != null) {
            if (navigateFrom.equalsIgnoreCase(ProfileViewActivity.TAG)) {
                if (validationUtils.isValidPasswordChange(OLD_PASSWORD, userid, NEW_PASSWORD, mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_PASSWORD).toString())) {
                    hideKeyboard();
                    if (isConnectingToInternet()) {
                        try {
                            JSONObject object = new JSONObject();
                            String URL = RestApiConstants.RESET_PASSWORD;
                            object.put(RestUtils.TAG_DOC_ID, doctorId);
                            object.put(RestUtils.OLD_PASSWORD, OLD_PASSWORD);
                            object.put(RestUtils.NEW_PASSWORD, NEW_PASSWORD);
                            /** Creating JSONObject **/
                            showProgress();
                            new VolleySinglePartStringRequest(ChangePasswordActivity.this, Request.Method.POST,URL,object.toString(),"CHANGE_PASSWORD", new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    hideProgress();
                                    JSONObject responseObj = null;
                                    try {
                                        responseObj = new JSONObject(successResponse);
                                        if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                            Toast.makeText(ChangePasswordActivity.this,getResources().getString(R.string.password_updated_successfully), Toast.LENGTH_SHORT).show();
                                            MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_USER_PASSWORD, editTxtNewPassword.getText().toString());
                                            finish();
                                        }
                                        else if(responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)){
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

                }
            } else {
                if (validationUtils.isValidSetPassword(NEW_PASSWORD)) {
                    hideKeyboard();
                    if (isConnectingToInternet()) {
                        JSONObject requestObject = new JSONObject();
                        String URL = RestApiConstants.UPDATE_PASSWORD;
                        try {
                            requestObject.put(RestUtils.TAG_DOC_ID, doctorId);
                            requestObject.put(RestUtils.TAG_PASSWORD_REGISTRATION, NEW_PASSWORD);
                            showProgress();
                            new VolleySinglePartStringRequest(ChangePasswordActivity.this, Request.Method.POST,URL,requestObject.toString(),"UPDATE_PASSWORD", new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    //hideProgress();
                                    JSONObject responseObj = null;
                                    try {
                                        responseObj = new JSONObject(successResponse);
                                        if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                            Toast.makeText(ChangePasswordActivity.this,getResources().getString(R.string.password_updated_successfully), Toast.LENGTH_SHORT).show();
                                            MySharedPref.getPrefsHelper().delete(MySharedPref.EMAIL_ID_OTP);
                                            MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_USER_PASSWORD, editTxtNewPassword.getText().toString());
                                            HashMap<String, String> qbCredentials = realmManager.getLogin(realm);
                                            //String qb_user_login = qbCredentials.get("qb_user_login");
                                            //String qb_password = qbCredentials.get("qb_user_password");
                                            Intent in = new Intent(ChangePasswordActivity.this, DashboardActivity.class);
                                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(in);
                                            finish();
                                            /*new QBLogin(ChangePasswordActivity.this,"FromChangePassword", new OnTaskCompleted() {
                                                @Override
                                                public void onTaskCompleted(String qbResponse) {
                                                    hideProgress();
                                                    if(qbResponse.equalsIgnoreCase(ConstsCore.QB_LOGIN_SUCCESS)){

                                                    }
                                                    else if(qbResponse.equalsIgnoreCase(ConstsCore.QB_LOGIN_ERROR)){
                                                        Toast.makeText(ChangePasswordActivity.this,getResources().getString(R.string.unable_to_create_session),Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        Toast.makeText(ChangePasswordActivity.this,getResources().getString(R.string.unable_to_connect_server),Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });*/
                                        }
                                        else if(responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)){
                                            hideProgress();
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
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("ChangePasswordDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(navigateFrom!=null&&navigateFrom.equalsIgnoreCase(ProfileViewActivity.TAG)) {
            finish();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Would you like to exit");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigateFrom = null;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            customBackButton=true;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("ChangePasswordBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
        }
        return true;
    }
}

