package com.vam.whitecoats.ui.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.interfaces.User;
import com.vam.whitecoats.utils.RestUtils;

/**
 * Generic version of the Box class.
 * @param <T> the type of the value
 */
public class InactiveMembersCard <T> extends BaseActionBarActivity {
/*
 * Here T stands for "Type"
 */
    TextView doctorName, designation_lable,department,community_name,speciality,workplace,location,card_email_id,card_contact_id;
    T t;
    TextView mTitleTextView;
    LinearLayout back_button;
    String doc_name = "",doc_actionbar_name ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inactive_members_card);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_memberinfo, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_dept);

        doctorName =(TextView)findViewById(R.id.doctor_name);
        designation_lable =(TextView)findViewById(R.id.designation);
        department =(TextView)findViewById(R.id.department);
        community_name =(TextView)findViewById(R.id.community_name);
        speciality =(TextView)findViewById(R.id.speciality);
        workplace =(TextView)findViewById(R.id.workplace);
        location =(TextView)findViewById(R.id.location);
        card_email_id =(TextView)findViewById(R.id.card_email_id);
        card_contact_id =(TextView)findViewById(R.id.card_contact_id);
        Bundle bundle=getIntent().getExtras();
        String deptOrDesig=bundle.getString(RestUtils.DEPT_OR_DESIG, "");
        String designation=bundle.getString(RestUtils.TAG_DESIGNATION);
        String communityName=bundle.getString(RestUtils.COMMUNITY_NAME,"");
        t= (T) bundle.getSerializable(getString(R.string.key_inactive_member));
        User user= (User) t;
        if(user.getUserSalutation()!=null){
            doc_name=user.getUserSalutation();
        }
        doctorName.setText(doc_name+" "+user.getFullName());
        designation_lable.setText(designation);
        department.setText(deptOrDesig);
        speciality.setText(user.getSpeciality());
        workplace.setText(user.getWorkplace());
        location.setText(user.getLocation());
        if(user.getContactEmail().equalsIgnoreCase("null") || user.getContactEmail().equalsIgnoreCase("")) {
            card_email_id.setText("Not Shared");
        }else{
            card_email_id.setText(user.getContactEmail());
        }
        community_name.setText(communityName);
        if(user.getContactNumber()!=null && !user.getContactNumber().isEmpty()){
            card_contact_id.setVisibility(View.VISIBLE);
            if(user.getContactNumber().equalsIgnoreCase("null")) {
                card_contact_id.setText("Not Shared");
            }else{
                card_contact_id.setText(user.getContactNumber());
            }

        }else{
            card_contact_id.setVisibility(View.GONE);
        }
        /**
         *
         */
        //setupActionbar("WhiteCoats");
        if(user.getUserSalutation()!=null){
            doc_actionbar_name=user.getUserSalutation();
        }
        mTitleTextView.setText(doc_actionbar_name+" "+user.getFullName());

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
        switch(item.getItemId()){
            case android.R.id.home:
                 onBackPressed();
                 return  true;
            default:
            return super.onOptionsItemSelected(item);

        }

    }
}
