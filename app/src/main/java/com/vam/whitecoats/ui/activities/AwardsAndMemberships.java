package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.ui.fragments.Awards_fragment;
import com.vam.whitecoats.ui.fragments.Memberships_fragment;

public class AwardsAndMemberships extends BaseActionBarActivity {
    public static final String TAG = AwardsAndMemberships.class.getSimpleName();
    private FragmentTabHost mTabHost;
    private TabWidget tabWidget;
    private TextView next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards_memberships);
        initialize();
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
        mInflater = LayoutInflater.from(AwardsAndMemberships.this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        next_button.setVisibility(View.GONE);
        mTitleTextView.setText(R.string.membership);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabWidget = (TabWidget) findViewById(android.R.id.tabs);
        mTabHost.setup(AwardsAndMemberships.this, getSupportFragmentManager(), R.id.award_membership_tab_content);
        mTabHost.addTab(setIndicator(AwardsAndMemberships.this, mTabHost.newTabSpec("Awards"), "AWARDS"), Awards_fragment.class, null);
        mTabHost.addTab(setIndicator(AwardsAndMemberships.this, mTabHost.newTabSpec("Memberships"), "MEMBERSHIPS"),Memberships_fragment.class , null);


        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);

        int numberOfTabs = mTabHost.getTabWidget().getChildCount();
        for (int t = 0; t < numberOfTabs; t++) {
            mTabHost.getTabWidget().getChildAt(t).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTabHost.getCurrentTabTag().equals("Memberships")) {
                        if (!TextUtils.isEmpty(AppConstants.add_membership)) {
                            ShowDialog(1);
                        } else {
                            mTabHost.setCurrentTab(0);
                        }
                    } else {
                        if (!TextUtils.isEmpty(AppConstants.awards_title) || !TextUtils.isEmpty(AppConstants.presented_at) || !TextUtils.isEmpty(AppConstants.year_awarded)) {
                            ShowDialog(0);
                        } else {
                            mTabHost.setCurrentTab(1);
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void ShowDialog(final int currentTab) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Would you like to save the changes");
        final AlertDialog.Builder builder1 = builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (currentTab == 0) {
                    Awards_fragment.getInstance().checkForServiceCall("add");
                    mTabHost.setCurrentTab(1);
                    Awards_fragment.getInstance().clearUIFields();
                }
                else {
                    Memberships_fragment.getInstance().checkForServiceCall("add");
                    mTabHost.setCurrentTab(0);
                    Memberships_fragment.getInstance().clearUIFields();
                }
            }
        });
        builder.setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (currentTab == 1) {
                    Memberships_fragment.getInstance().clearUIFields();
                    mTabHost.setCurrentTab(0);
                } else {
                    Awards_fragment.getInstance().clearUIFields();
                    mTabHost.setCurrentTab(1);
                }
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public TabHost.TabSpec setIndicator(Context ctx, TabHost.TabSpec spec, String title) {
        // TODO Auto-generated method stub
        View v = LayoutInflater.from(ctx).inflate(R.layout.tab_text_item, null);
        TextView text = (TextView) v.findViewById(R.id.tab_title);
        text.setText(title);
        return spec.setIndicator(v);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (!TextUtils.isEmpty(AppConstants.awards_title) ||
                !TextUtils.isEmpty(AppConstants.presented_at) ||
                        !TextUtils.isEmpty(AppConstants.year_awarded)) {
            ShowAlert();
        }else if(!TextUtils.isEmpty(AppConstants.add_membership)){
            ShowAlert();
        }
        else {
            Intent in = new Intent();
            setResult(Activity.RESULT_OK,in);
            finish();
        }
    }


    public void ShowAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("Would you like to save the changes");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //next_button.performClick();
                if (mTabHost.getCurrentTabTag().equals("Memberships")) {
                    if(Memberships_fragment.getInstance()!=null) {
                        Memberships_fragment.getInstance().checkForServiceCall("save");
                    }
                }else{
                    if(Awards_fragment.getInstance()!=null) {
                        Awards_fragment.getInstance().checkForServiceCall("save");
                    }

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                // journal_list.clear();
                /*Intent login_intent = new Intent(PublicationsActivity.this, ProfileViewActivity.class);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login_intent);*/
                Intent in = new Intent();
                setResult(Activity.RESULT_OK,in);
                finish();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConstants.awards_title = "";
        AppConstants.presented_at = "";
        AppConstants.year_awarded = "";
        AppConstants.add_membership="";
    }


}
