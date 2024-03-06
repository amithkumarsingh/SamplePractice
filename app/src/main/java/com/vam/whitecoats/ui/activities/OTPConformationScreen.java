package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.utils.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class OTPConformationScreen extends BaseActionBarActivity {


    private TextView countDown_timer;
    private CountDownTimerClass countdowntimer;
    private LinearLayout havent_layout;
    private TextView contactDetails_textView;
    private String contactDetails="";
    private Button goto_login;
    private TextView resendOTP_Action;
    private TextView title_text_otp,contactSupport_textView;
    private String contact_type="";
    private Realm realm;
    private RealmManager realmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpconformation_screen);
        initialize();
        countdowntimer = new CountDownTimerClass(30000, 1000);
        countdowntimer.start();
    }

    @Override
    protected void setCurrentActivity() {

    }

    private void initialize() {
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_reg, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(32,0,0,0);
        mTitleTextView.setLayoutParams(lp);
        mTitleTextView.setText("Reset Password");
        next_button.setVisibility(View.GONE);
        contactDetails_textView =(TextView)findViewById(R.id.email_id_sentto);
        title_text_otp=(TextView)findViewById(R.id.title_textotp);
        goto_login=(Button)findViewById(R.id.gotologin_action);
        contactSupport_textView=(TextView)findViewById(R.id.contact_support_action);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        contactSupport_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactsupportIntent= new Intent(OTPConformationScreen.this,ContactSupport.class);
                startActivity(contactsupportIntent);
            }
        });
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            contactDetails = bundle.getString("contact");
            contact_type=bundle.getString("contactType");
        }

        contactDetails_textView.setText(contactDetails);
        if(contact_type!=null && contact_type.equalsIgnoreCase("Phone")){
            title_text_otp.setText(getString(R.string.loginwith_text_mobile));
        }

        countDown_timer=(TextView)findViewById(R.id.countdown_timer);
        havent_layout=(LinearLayout)findViewById(R.id.havent_layout);
        resendOTP_Action=(TextView)findViewById(R.id.resend_otp_action);
        resendOTP_Action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resendIntent=new Intent(OTPConformationScreen.this,ForgotPassword.class);
                startActivity(resendIntent);
                finish();
            }
        });

        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }
    public class CountDownTimerClass extends CountDownTimer {

        public CountDownTimerClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished/1000);
            countDown_timer.setText("You will receive SMS with OTP shortly.");
        }

        @Override
        public void onFinish() {
            countDown_timer.setVisibility(View.GONE);
            havent_layout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {
            JSONObject jsonObject=new JSONObject();
            try {
                if(realmManager.getUserUUID(realm)!=null) {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                }
                AppUtil.logUserUpShotEvent("ResetPasswordDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        super.onBackPressed();
    }
}
