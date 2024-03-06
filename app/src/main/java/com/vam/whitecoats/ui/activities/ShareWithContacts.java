package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.MyContactsAdapter;

import java.util.ArrayList;

import io.realm.Realm;

public class ShareWithContacts extends BaseActionBarActivity {

    private TextView tv_invite;
    private ContactsInfo contactsInfo;
    private static ArrayList<ContactsInfo> contactsarray;
    private Realm realm;
    private RealmManager realmManager;
    private MyContactsAdapter adapter;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_with_contacts);

        _initializeUI();


    }

    @Override
    protected void setCurrentActivity() {

    }


    private void _initializeUI(){
        try{

            mInflater                       = LayoutInflater.from(this);
            mCustomView                     = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView         = (TextView) mCustomView.findViewById(R.id.title_edit);
            TextView next_button            = (TextView)mCustomView.findViewById(R.id.next_button);
            mTitleTextView.setText("Add to Connects");
            next_button.setVisibility(View.GONE);
            //back_button.setImageResource(R.drawable.ic_back);


            realmManager = new RealmManager(this);
            realm        = Realm.getDefaultInstance();


            tv_invite           = _findViewById(R.id.invite_connects);
            lv                  = _findViewById(R.id.whitecoats_connects);

            contactsarray   =  new ArrayList<ContactsInfo>();


            tv_invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi, connect with me on WhiteCoats, an exclusive messaging app for medical professionals. " +
                            "Connect with top medical professionals, share and discuss clinical cases with a single tap, you can download from <link>");
                    startActivity(Intent.createChooser(shareIntent, "Invite via"));
                }
            });


            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(mCustomView);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }    }
}
