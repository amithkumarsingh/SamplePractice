package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;

public class PermissionAccessActivity extends AppCompatActivity {

    private TextView your_device_id_text,standard_permission_text,allow_text;
    private LinearLayout ok_action;
    private MarshMallowPermission marshMallowPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_access);

        your_device_id_text=(TextView)findViewById(R.id.your_device_id_text);

        standard_permission_text=(TextView)findViewById(R.id.standard_permission_text);

        allow_text=(TextView)findViewById(R.id.allow_text);

        ok_action=(LinearLayout) findViewById(R.id.ok_lay);

        marshMallowPermission = new MarshMallowPermission(this);

        String text = "WhiteCoats requires permission to access certain device information to support app issues if any.";

        SpannableString spannableString= new SpannableString(text);

        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.parseColor("#00A76D"));

        spannableString.setSpan(foregroundColorSpan,20,67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        your_device_id_text.setText(spannableString);


        String standardText = "This is a standard permission and is called \" make and manage phone calls \"";

        SpannableString spannableStringStandard= new SpannableString(standardText);

        ForegroundColorSpan foregroundColorSpanStandard=new ForegroundColorSpan(Color.parseColor("#00A76D"));

        spannableStringStandard.setSpan(foregroundColorSpanStandard,44,75, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        standard_permission_text.setText(spannableStringStandard);



        String allowText = "Please choose \" ALLOW \" in the next screen to enable this permission. You can change this permission at any time using the setting options on your phone. ";

        SpannableString spannableStringAllowText= new SpannableString(allowText);

        ForegroundColorSpan foregroundColorAllowText=new ForegroundColorSpan(Color.parseColor("#00A76D"));

        spannableStringAllowText.setSpan(foregroundColorAllowText,13,24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        allow_text.setText(spannableStringAllowText);


        ok_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PermissionAccessActivity.this,DashboardActivity.class);
                setResult(10101,intent);
                finish();
            }
        });


    }
    @Override
    public void onBackPressed() {

    }
}
