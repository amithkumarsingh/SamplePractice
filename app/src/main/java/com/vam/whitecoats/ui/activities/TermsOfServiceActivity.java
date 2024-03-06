package com.vam.whitecoats.ui.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class TermsOfServiceActivity extends BaseActionBarActivity {

    WebView webViewTerms;
    private Realm realm;
    private RealmManager realmManager;
    private boolean customBackButton=false;
    private Bundle bundle;
    private String navigation="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsofservice);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        TextView mTitleTextView       = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button         = (TextView)mCustomView.findViewById(R.id.next_button);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            navigation = bundle.getString("NavigationFrom");
        }

        mTitleTextView.setVisibility(View.GONE);
        next_button.setVisibility(View.GONE);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        webViewTerms=(WebView)findViewById(R.id.terms_service);

        webViewTerms.getSettings().setJavaScriptEnabled(true);
        if(navigation.equalsIgnoreCase("terms_of_use")){
            webViewTerms.loadUrl("file:///android_asset/TermsofService.html");
        }else{
            webViewTerms.loadUrl("file:///android_asset/PrivacyPolicy.html");
        }




        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);



    }

    @Override
    protected void setCurrentActivity() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                customBackButton=true;
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("TermsofServiceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("TermsofServiceDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }
}
