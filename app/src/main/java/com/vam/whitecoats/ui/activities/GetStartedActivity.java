package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.RequiresApi;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.interfaces.OnHomePressedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HomeWatcher;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class GetStartedActivity extends Activity {

    private Button register_button;
    TextView login_textview;
    private HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
        register_button = (Button) findViewById(R.id.register_button);
        login_textview = (TextView) findViewById(R.id.login_textview);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("X-DEVICE-ID", android_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppUtil.logUserWithEventName(0, " SignUpLoginImpressions", jsonObject,GetStartedActivity.this);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(GetStartedActivity.this, RegistrationDetailsActivity.class);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "SignupLoginRegisterNow", jsonObject,GetStartedActivity.this);
                startActivity(registerIntent);
                finish();
            }
        });
        login_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(GetStartedActivity.this, LoginActivity.class);
                loginIntent.putExtra(RestUtils.NAVIGATATION, "fromGetStarted");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.logUserWithEventName(0, "SignupLogin_LoginHere", jsonObject,GetStartedActivity.this);
                startActivity(loginIntent);
                finish();
            }
        });

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                JSONObject jsonObject=new JSONObject();
                try {
                    AppUtil.logUserUpShotEvent("GetStartedHomeTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onHomeLongPressed() {

            }
        });
        mHomeWatcher.startWatch();
    }

    @Override
    public void onBackPressed() {
        JSONObject jsonObject=new JSONObject();
        try {
            AppUtil.logUserUpShotEvent("GetStartedDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHomeWatcher!=null) {
            mHomeWatcher.stopWatch();
        }
    }
}
