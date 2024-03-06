package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.constants.WhiteCoatsError;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;


public class ErrorResponseActivity extends BaseActionBarActivity {
    public static final String TAG = ErrorResponseActivity.class.getSimpleName();
    ImageView errorImage;
    TextView errorTitle;
    TextView errorDesc;
    Button errorActionBtn;
    private Realm realm;
    private RealmManager realmManager;
    private int user_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        setContentView(R.layout.activity_error_response);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(ErrorResponseActivity.this);
        user_id = realmManager.getDoc_id(realm);
        errorImage = findViewById(R.id.error_image);
        errorTitle = findViewById(R.id.error_title_txt);
        errorDesc = findViewById(R.id.error_desc_txt);
        errorActionBtn = findViewById(R.id.error_action_btn);
        try {
            //Receive bundle params
            JSONObject errResponse = new JSONObject(getIntent().getExtras().getString(RestUtils.TAG_ERROR_MESSAGE));
            if (errResponse.has(RestUtils.TAG_ERROR_CODE)) {
                if (errResponse.optInt(RestUtils.TAG_ERROR_CODE) == WhiteCoatsError.BAD_GATEWAY.getErrorCode()) {
                    displayErrorPage(WhiteCoatsError.BAD_GATEWAY);
                } else if (errResponse.optInt(RestUtils.TAG_ERROR_CODE) == WhiteCoatsError.INCORRECT_APP_VERSION.getErrorCode()) {
                    displayErrorPage(WhiteCoatsError.INCORRECT_APP_VERSION);

                } else if (errResponse.optInt(RestUtils.TAG_ERROR_CODE) == WhiteCoatsError.SERVER_ERROR.getErrorCode()) {
                    displayErrorPage(WhiteCoatsError.SERVER_ERROR);

                } else if (errResponse.optInt(RestUtils.TAG_ERROR_CODE) == WhiteCoatsError.CONNECTION_TIMEOUT.getErrorCode()) {
                    displayErrorPage(WhiteCoatsError.CONNECTION_TIMEOUT);

                } else if (errResponse.optInt(RestUtils.TAG_ERROR_CODE) == WhiteCoatsError.SERVER_UPGRADE.getErrorCode()) {
                    displayErrorPage(WhiteCoatsError.SERVER_UPGRADE);

                } else {
                    displayErrorPage(WhiteCoatsError.BAD_GATEWAY);
                }
            }
            errorActionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (errResponse.optInt(RestUtils.TAG_ERROR_CODE) == WhiteCoatsError.INCORRECT_APP_VERSION.getErrorCode()) {
                        AppUpgradeAPICall();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.vam.whitecoats"));
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(ErrorResponseActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
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

    private void AppUpgradeAPICall() {
        JSONObject reqObj=new JSONObject();
        try {
            reqObj.put(RestUtils.TAG_USER_ID,user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new VolleySinglePartStringRequest(ErrorResponseActivity.this, Request.Method.POST, RestApiConstants.APP_UPGRADE_API_CALL, reqObj.toString(), "APP_UPGRADE", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.d("APP_UPGRADE_CALL","Upgrade success");

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                Log.d("APP_UPGRADE_CALL","Upgrade failure");
            }
        });
    }

    /**
     * Display error page with specific error symbol, error text and error description.
     * @param error
     */
    private void displayErrorPage(WhiteCoatsError error) {
        errorImage.setImageResource(error.getErrorResourceID());
        errorTitle.setText(error.getErrorMsg());
        errorDesc.setText(error.getErrorDesc());
        if(error.getErrorCode()==WhiteCoatsError.INCORRECT_APP_VERSION.getErrorCode())
            errorActionBtn.setText(getString(R.string.btn_upgrade_now));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
