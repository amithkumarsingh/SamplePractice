package com.vam.whitecoats.async;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.flurry.android.FlurryAgent;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.OTP;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnOtpActionListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static com.vam.whitecoats.utils.RestUtils.TAG_STATUS;
import static com.vam.whitecoats.utils.RestUtils.TAG_SUCCESS;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by pardhasaradhid on 3/21/2017.
 */

public class OtpService {
    public static final String TAG = OtpService.class.getSimpleName();
    ProgressDialogFragement progress;
    Activity mContext;
    OnOtpActionListener otpActionListener;
    AlertDialog.Builder builder;
    String response_OTP;
    Dialog dialog;
    static OtpService otpService;
    EditText mobile_num;
    EditText editText1, editText2, editText3, editText4;

    private int login_doc_id = 0;
    private String emailId;

    private Realm realm;
    private RealmManager realmManager;
    String android_id, firstName_value, lastName_value;
    private String activityName;
    private LinearLayout mainLay;
    private boolean num_edit_visible;

    public static OtpService getInstance() {
        Log.i(TAG, "getInstance() - " + otpService);
        if (otpService == null) {
            otpService = new OtpService();
        }
        return otpService;
    }

    public void requestParameters(String first_Name, String last_Name, final Activity context, boolean num_edit_required) {
        Log.i(TAG, "requestParameters(firstName,lastName)");
        firstName_value = first_Name;
        lastName_value = last_Name;
        num_edit_visible=num_edit_required;


    }

    /**
     * @param requestObject
     * @param context
     * @param num_edit_required
     * @return void
     */
    public void requestOtp(final JSONObject requestObject, final Activity context, boolean num_edit_required) {
        Log.i(TAG, "requestOtp(jsonObject,context)");
        this.mContext = context;
        otpActionListener = (OnOtpActionListener) context;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(context);
        login_doc_id = realmManager.getDoc_id(realm);
        emailId = realmManager.getDoc_EmailId(realm);
        num_edit_visible=num_edit_required;
        android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        activityName = mContext.getClass().getSimpleName();

        if (!activityName.equalsIgnoreCase("LoginActivity")) {

            HashMap<String, Object> data = new HashMap<>();
            data.put(RestUtils.EVENT_DOCID, android_id);
            data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
            data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);

            AppUtil.logUserEventWithHashMap("SignUp_Verification_Impression", 0, data, context);
        }
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
        showProgress();
        try {
            new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.SEND_OTP, requestObject.toString(), "REQUEST_OTP", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    try {
                        hideProgress();
                        JSONObject jsonObject = new JSONObject(successResponse);
                        response_OTP = jsonObject.optString("otp");
                        if (jsonObject.getString(TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            displayErrorPage(successResponse);
                        } else if (jsonObject.getString(TAG_STATUS).equals((TAG_SUCCESS))) {
                            dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.otp_dialog_layout);
                            dialog.setCancelable(false);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                            mainLay = (LinearLayout)dialog.findViewById(R.id.mainLay);
                            mobile_num = (EditText) dialog.findViewById(R.id.mobile_num);
                            mobile_num.setText(requestObject.optString("contact"));
                            editText1 = (EditText) dialog.findViewById(R.id.editText1);
                            editText2 = (EditText) dialog.findViewById(R.id.editText2);
                            editText3 = (EditText) dialog.findViewById(R.id.editText3);
                            editText4 = (EditText) dialog.findViewById(R.id.editText4);
                            final TextView otp_error = (TextView) dialog.findViewById(R.id.otp_error);

                            final EditText[] editText_Values = new EditText[]{editText1, editText2, editText3, editText4};
                            final EditText et_otp = (EditText) dialog.findViewById(R.id.et_otp);
                            et_otp.requestFocus();
                            Button number_edit = (Button) dialog.findViewById(R.id.number_edit);
                            Button resend_otp = (Button) dialog.findViewById(R.id.resend_otp);


                            dialog.show();
                            showKeyboard(et_otp);
                            if(num_edit_visible){
                                number_edit.setVisibility(View.VISIBLE);
                            }else{
                                number_edit.setVisibility(View.GONE);
                            }

//                            //we need to ask user permission to auto read sms
//                            requestsmspermission();
//                            new OTP_Receiver().setEditText(editText1, editText2, editText3, editText4);


                            number_edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "number_edit click");
                                    if (!activityName.equalsIgnoreCase("LoginActivity")) {
                                        HashMap<String, Object> data = new HashMap<>();
                                        data.put(RestUtils.EVENT_DOCID, android_id);
                                        data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                                        data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);

                                        AppUtil.logUserEventWithHashMap("SignUp_EditNum", 0, data, context);
                                    }

                                    dialog.dismiss();
                                    otpActionListener.onOtpAction(OTP.CHANGE_MOB_NO, response_OTP);
                                }
                            });
                            resend_otp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "resend_otp click");
                                    dialog.dismiss();
                                    if (!activityName.equalsIgnoreCase("LoginActivity")) {
                                        HashMap<String, Object> data = new HashMap<>();
                                        data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                                        data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);

                                        AppUtil.logUserEventWithHashMap("SignUp_ResendOTP", 0, data, context);
                                    }
                                    requestOtp(requestObject, mContext, num_edit_required);
                                    otpActionListener.onOtpAction(OTP.RESEND, response_OTP);
                                }
                            });
                            editText1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "editText1 click");
                                    showKeyboard(et_otp);
                                }
                            });
                            editText2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "editText2 click");
                                    showKeyboard(et_otp);
                                }
                            });
                            editText3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "editText3 click");
                                    showKeyboard(et_otp);
                                }
                            });
                            editText4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "editText4 click");
                                    showKeyboard(et_otp);
                                }
                            });

//                            editText4.addTextChangedListener(new TextWatcher() {
//                                @Override
//                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                    Log.d(TAG, "beforeTextChanged() - char :" + s + " start:" + start + " before" + count + " count" + count);
//                                }
//
//                                @Override
//                                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                    Log.d(TAG, "onTextChanged() - char :" + s + " start:" + start + " before" + before + " count" + count);
//                                    if (before == 0) {
//                                        //Data entered
////                                        editText_Values[start].setText("" + s.charAt(start));
//                                    } else {
//                                        //Data deleted
////                                        editText_Values[start].setText("");
////                                        otp_error.setText("");
//                                    }
//                                }
//
//                                @Override
//                                public void afterTextChanged(Editable s) {
//                                    Log.d(TAG, "afterTextChanged() - String - " + s.toString());
//                                    if (s.toString().length() == 1) {
//                                        if (!activityName.equalsIgnoreCase("LoginActivity")) {
//                                            HashMap<String, Object> data = new HashMap<>();
//                                            data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
//                                            data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
//
//                                            AppUtil.logUserEventWithHashMap("SignUp_EnterOTP", 0, data, context);
//                                        }
//
//                                        if (response_OTP.equalsIgnoreCase(editText1.getText().toString().trim() + editText2.getText().toString().trim() + editText3.getText().toString().trim() + editText4.getText().toString().trim())) {
//                                            if (dialog != null) {
//                                                Handler handler = new Handler();
//                                                handler.postDelayed(new Runnable() {
//                                                    public void run() {
//                                                        // yourMethod();
//                                                        dialog.dismiss();
//                                                    }
//                                                }, 1000);   //5 seconds
////                                                dialog.dismiss();
//                                            }
//                                            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                                            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                            imm.hideSoftInputFromWindow(mainLay.getWindowToken(), 0);
//                                            otpActionListener.onOtpAction(OTP.SUCCESS, response_OTP);
//                                        } else {
//                                            otp_error.setText("Entered OTP is wrong");
//                                            otpActionListener.onOtpAction(OTP.FAILURE, response_OTP);
//                                        }
//                                    }
//                                }
//                            });


                            et_otp.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    Log.d(TAG, "beforeTextChanged() - char :" + s + " start:" + start + " before" + count + " count" + count);
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    Log.d(TAG, "onTextChanged() - char :" + s + " start:" + start + " before" + before + " count" + count);
                                    if (before == 0) {
                                        //Data entered
                                        editText_Values[start].setText("" + s.charAt(start));
                                    } else {
                                        //Data deleted
                                        editText_Values[start].setText("");
                                        otp_error.setText("");
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    Log.d(TAG, "afterTextChanged() - String - " + s.toString());
                                    if (s.toString().length() == 4) {

                                        if (!activityName.equalsIgnoreCase("LoginActivity")) {
                                            HashMap<String, Object> data = new HashMap<>();
                                            data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                                            data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);

                                            AppUtil.logUserEventWithHashMap("SignUp_EnterOTP", 0, data, context);
                                        }

                                        if (response_OTP.equalsIgnoreCase(s.toString())) {

                                            if (dialog != null) {
                                                dialog.dismiss();
                                            }
                                            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                            otpActionListener.onOtpAction(OTP.SUCCESS, response_OTP);
                                        } else {
                                            otp_error.setText("Entered OTP is wrong");
                                            otpActionListener.onOtpAction(OTP.FAILURE, response_OTP);
                                        }
                                    }
                                }
                            });
                        } else {
                            hideProgress();
                            displayErrorPage(successResponse);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //fabric logs
                    logOtpServiceFlurryEvent("OtpService_success", emailId);
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    hideProgress();
                    logOtpServiceFlurryEvent("OtpServiceError", emailId);
                    displayErrorPage(errorResponse);
                }
            }).sendSinglePartRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void displayErrorPage(String response) {
        ((BaseActionBarActivity) mContext).displayErrorScreen(response);
    }

    public synchronized void showProgress() {
        Log.i(TAG, "showProgress()");
        try {
            if (!progress.isAdded()) {
                progress.show(mContext.getFragmentManager(), null);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void hideProgress() {
        Log.i(TAG, "hideProgress()");
        if (progress != null && progress.getActivity() != null) {
            progress.dismissAllowingStateLoss();
        }
    }

    public void ShowSimpleDialog(final String title, final String message) {
        Log.i(TAG, "ShowSimpleDialog()");
        try {
            builder = new AlertDialog.Builder(mContext);
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("OK", null);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showKeyboard(EditText mEditText) {
        Log.i(TAG, "showKeyboard()");
        mEditText.setFocusable(true);
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void logOtpServiceFlurryEvent(String eventName, String emailId) {
        String contact_num = "0";
        if (mobile_num != null && mobile_num.getText().toString() != null && !mobile_num.getText().toString().isEmpty()) {
            contact_num = mobile_num.getText().toString();
        }

        // Capture author info & user status
        Map<String, String> articleParams = new HashMap<String, String>();
        //param keys and values have to be of String type
        articleParams.put(RestUtils.TAG_CONTACT, contact_num);
        articleParams.put(RestUtils.TAG_EMAIL, emailId);
        //up to 10 params can be logged with each event
        FlurryAgent.logEvent(eventName, articleParams);

        AppUtil.logSentryEventWithCustomParams(eventName, articleParams);
    }

    private void requestsmspermission() {
        String smspermission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(mContext, smspermission);
        //check if read SMS permission is granted or not
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = smspermission;
            ActivityCompat.requestPermissions(mContext, permission_list, 1);
        }
    }

}
