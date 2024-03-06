package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ConnectNotificationsAsync;
import com.vam.whitecoats.async.DownloadAsync;
import com.vam.whitecoats.async.DownloadImageAsync;
import com.vam.whitecoats.async.VisitProfileAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.AcademicAdapter;
import com.vam.whitecoats.ui.adapters.MembershipAdapter;
import com.vam.whitecoats.ui.adapters.ProfessionalAdapter;
import com.vam.whitecoats.ui.adapters.PublicationsAdapter;
import com.vam.whitecoats.ui.customviews.NonScrollListView;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FileHelper;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowCard;
import com.vam.whitecoats.utils.ValidationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;


/**
 * Created by swathim on 31-07-2015.
 */
public class VisitProfileActivity extends BaseActionBarActivity {

    private ImageView ig_visitor_pic, ig_share;
    private Button btn_share, btn_connect, ig_invite, ig_message;
    private TextView tx_name, tx_degree, tx_speciality, tx_subSpeciality, tx_workplace, tx_print_pub_heading, tx_online_pub_heading;
    private TextView tx_connects_count, tx_caserooms_count, tx_groups_count;
    private TextView tx_coomon_connects_count, tx_common_caserooms_count, tx_common_groups_count;
    private TextView tv_website, tv_facebook, tv_blog, mTitleTextView;
    private TextView tv_about_me, tv_aboutMe_heading;

    private View include_lOut, seperator, about_me_seperator0, about_me_seperator1, about_me_seperator2;
    private LinearLayout lOut_accept, lOut_reject;
    private TextView tv_accept, tv_reject, tv_awaiting;

    private NestedScrollView visit_profile_scroll;
    private LinearLayout linear_lv;
    /**
     * About visitor layout
     **/
    TextView tx_visitor_name;
    private long mLastClickTime = 0;


    private RelativeLayout rv_aboutMe, rv_professional, rv_academic, rv_membership;
    private LinearLayout lv_publications;

    private NonScrollListView professional_lv;
    private NonScrollListView academic_lv;
    private NonScrollListView printpublications_lv, onlinepublications_lv, professionalmem_lv;


    private int connect_status;


    ArrayList<AcademicInfo> academic_history = new ArrayList<AcademicInfo>();
    AcademicInfo academicInfo;

    ArrayList<ProfessionalInfo> professional_history = new ArrayList<ProfessionalInfo>();
    ProfessionalInfo professionalInfo;

    ArrayList<PublicationsInfo> print_publications_history = new ArrayList<PublicationsInfo>();
    ArrayList<PublicationsInfo> online_publications_history = new ArrayList<PublicationsInfo>();


    ArrayList<ProfessionalMembershipInfo> membership_history = new ArrayList<ProfessionalMembershipInfo>();
    ProfessionalMembershipInfo professionalMembershipInfo;

    private Menu menu;

    private int otherdocId;

    private boolean isphChangedStat = false;
    private boolean ismailChangeStat = false;

    private String str_phno, str_email;

    //private ScrollView visit_scrollView;
    private ProgressBar centered_progressbar;

    ValidationUtils validationUtils;

    /**
     * Setting DocId from realm
     **/
    private int userid;
    private Realm realm;
    private RealmManager realmManager;
    ContactsInfo contactsInfo = new ContactsInfo();
    private String dl_imagepath, profile_pic_name;


    private PublicationsAdapter publicationsAdapter01 = null;
    private PublicationsAdapter publicationsAdapter = null;
    private JSONObject jpersonalInfoObj = null;
    private PublicationsAdapter opublicationsAdapter01;
    private PublicationsAdapter opublicationsAdapter;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_profile);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_reg, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);

        // next_button.getLayoutParams().width = 0;
        next_button.setVisibility(View.GONE);


        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);

        realmManager = new RealmManager(VisitProfileActivity.this);
        realm = Realm.getDefaultInstance();


        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                contactsInfo = (ContactsInfo) getIntent().getSerializableExtra("searchinfo");
                if (contactsInfo == null) {
                    otherdocId = getIntent().getIntExtra(RestUtils.TAG_DOC_ID, 0);
                } else {
                    otherdocId = contactsInfo.getDoc_id();
                }

            }
            _initializeUI();

            validationUtils = new ValidationUtils(VisitProfileActivity.this);
            userid = realmManager.getDoc_id(realm);
            if (isConnectingToInternet()) {
                new VisitProfileAsync(VisitProfileActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), "" + userid, "" + otherdocId);
            } else {
                hideProgress();
            }

            tv_website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = tv_website.getText().toString();
                    try {
                        if (!url.contains("http://")) {
                            url = "http://" + url;
                        }
                        boolean valid = validationUtils.isUrlValid(url);
                        if (valid) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
            tv_blog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = tv_blog.getText().toString();
                    try {
                        if (!url.contains("http://")) {
                            url = "http://" + url;
                        }
                        boolean valid = validationUtils.isUrlValid(url);
                        if (valid) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
            tv_facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = tv_facebook.getText().toString();
                    try {
                        if (!url.contains("http://")) {
                            url = "http://" + url;
                        }
                        boolean valid = validationUtils.isUrlValid(url);
                        if (valid) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        ig_visitor_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jpersonalInfoObj != null && !jpersonalInfoObj.optString(RestUtils.TAG_PROFILE_PIC_URL).isEmpty()) {
                    Intent intent = new Intent(VisitProfileActivity.this, Profile_fullView.class);
                    intent.putExtra("selectedDocId", otherdocId);
                    intent.putExtra("profilePicUrl", jpersonalInfoObj.optString(RestUtils.TAG_PROFILE_PIC_URL));
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            setCurrentActivity();
            checkNetworkConnectivity();
            //visit_scrollView.smoothScrollTo(0, visit_scrollView.getTop());
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
            /*Android : Fix code review bugs analyzed by SonarQube -- passes View object instead of null value*/
            if (behavior != null) {
                behavior.onNestedFling(coordinatorLayout, appBarLayout, new View(this), 0, 10000, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            //visit_scrollView.smoothScrollTo(0, visit_scrollView.getTop());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    private void _initializeUI() {


        tx_name = _findViewById(R.id.visit_prof_name);
        //tx_degree = _findViewById(R.id.visit_prof_degree);
        tx_speciality = _findViewById(R.id.visit_prof_speciality);
        tx_subSpeciality = _findViewById(R.id.visit_prof_sub_speciality);
        tx_workplace = _findViewById(R.id.visit_prof_workplace);
        ig_visitor_pic = _findViewById(R.id.visit_prof_pic_img);
        tx_coomon_connects_count = _findViewById(R.id.visit_prof_connects);
       /* tx_common_caserooms_count = _findViewById(R.id.visit_prof_caserooms);
        tx_common_groups_count = _findViewById(R.id.visit_prof_groups);*/
        professional_lv = _findViewById(R.id.visit_others_professional_list);
        tv_website = _findViewById(R.id.v_website_text);
        tv_facebook = _findViewById(R.id.v_facebook_text);
        tv_blog = _findViewById(R.id.v_blog_text);
        tv_about_me = _findViewById(R.id.visitor_about_expandableTextView);
        academic_lv = _findViewById(R.id.visit_others_academic_list);
        printpublications_lv = _findViewById(R.id.visit_others_print_list);
        onlinepublications_lv = _findViewById(R.id.visit_others_online_list);
        professionalmem_lv = _findViewById(R.id.visit_others_membership_list);
        tx_print_pub_heading = _findViewById(R.id.print_publications_heading);
        tx_online_pub_heading = _findViewById(R.id.online_publications_heading);

        tv_aboutMe_heading = _findViewById(R.id.about_user);
        rv_aboutMe = _findViewById(R.id.about_me_layout);
        rv_professional = _findViewById(R.id.professional_layout);
        rv_academic = _findViewById(R.id.academics_layout);
        lv_publications = _findViewById(R.id.publications_layout);
        rv_membership = _findViewById(R.id.membership_layout);
        visit_profile_scroll = _findViewById(R.id.nestedScroll);
        linear_lv = _findViewById(R.id.main_linear_layout);

        ig_invite = _findViewById(R.id.others_invite_btn);
        ig_message = _findViewById(R.id.others_message_btn);
//         ig_share                  = _findViewById(R.id.others_share_btn);


        include_lOut = _findViewById(R.id.invite_layout);
        tv_accept = _findViewById(R.id.accept_txt);
        tv_reject = _findViewById(R.id.reject_txt);
        tv_awaiting = _findViewById(R.id.txt_awaiting);
        seperator = _findViewById(R.id.vertical_seperator);
        lOut_accept = _findViewById(R.id.layout_accept);
        lOut_reject = _findViewById(R.id.layout_reject);

        //about_me_seperator0       = _findViewById(R.id.about_me_border0);
        about_me_seperator1 = _findViewById(R.id.about_me_border1);
        about_me_seperator2 = _findViewById(R.id.about_me_border2);

        /*visit_scrollView = _findViewById(R.id.visit_profile_scrollview);*/

        centered_progressbar = _findViewById(R.id.centered_progressbar);


        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        int deviceHeight = AppUtil.getDeviceHeight(this);
        int deviceWidth = AppUtil.getDeviceWidth(this);
        int oneFourthOfScreen = (int) (deviceHeight * 0.4);
        int oneSixthOfScreen = (int) (deviceHeight * 0.6);
        int scrollHeight = (deviceHeight - oneFourthOfScreen);
        int appBarHeight = (deviceHeight - scrollHeight);
        appBarLayout.getLayoutParams().height = appBarHeight;

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }


        lOut_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isConnectingToInternet()) {
                        if (AppUtil.getUserVerifiedStatus() == 3) {

                            JSONObject object = new JSONObject();
                            object.put("from_doc_id", userid);
                            object.put("to_doc_id", otherdocId);
                            object.put("resp_status", connect_status);
                            String reqData = object.toString();

                            new ConnectNotificationsAsync(VisitProfileActivity.this, RestApiConstants.CONNECT_INVITE, contactsInfo, new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String response) {
                                    if (response != null && !response.isEmpty()) {
                                        try {
                                            JSONObject responseObj = new JSONObject(response);
                                            if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                                include_lOut.setVisibility(View.GONE);
                                                lOut_accept.setVisibility(View.GONE);
                                                lOut_reject.setVisibility(View.GONE);
                                                seperator.setVisibility(View.GONE);
                                                tv_awaiting.setVisibility(View.GONE);
                                                ig_invite.setVisibility(View.GONE);
                                                ig_message.setVisibility(View.GONE);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), reqData.toString(), "connects_notification");
                        } else if (AppUtil.getUserVerifiedStatus() == 1) {
                            AppUtil.AccessErrorPrompt(VisitProfileActivity.this, getString(R.string.mca_not_uploaded));
                        } else if (AppUtil.getUserVerifiedStatus() == 2) {
                            AppUtil.AccessErrorPrompt(VisitProfileActivity.this, getString(R.string.mca_uploaded_but_not_verified));
                        }
                    } else {
                        hideProgress();
                    }
                } catch (Exception e) {
                    hideProgress();
                    e.printStackTrace();
                }
            }
        });


        tv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   new ConnectNotificationsAsync(VisitProfileActivity.this,"reject").execute(userid,otherdocId);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Dr Srikant Reddy");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
        ig_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime();
                    new ShowCard(VisitProfileActivity.this, contactsInfo).goToChatWindow("VisitProfileActivity");
                }
            }
        });

        ig_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InviteRequestActivity.class);
                intent.putExtra("searchContactsInfo", contactsInfo);
                intent.putExtra("visit", "visit");
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public void onTaskCompleted(String response) {
        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception") || response.equals("FileNotFoundException")) {
                hideProgress();
                displayErrorScreen(response);
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {

                            JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            JSONObject jaboutObj = data.getJSONObject(RestUtils.TAG_ABOUT);
                            jpersonalInfoObj = data.getJSONObject(RestUtils.TAG_PERSONAL_INFO);
                            JSONArray jacademicjArray = data.getJSONArray(RestUtils.TAG_ACADEMIC_HISTORY);
                            JSONArray jProfessionalArray = data.getJSONArray(RestUtils.TAG_PROFESSIONAL_HISTORY);
                            JSONArray jonlinepubArray = data.getJSONArray(RestUtils.TAG_ONLINE_PUB_HISTORY);
                            JSONArray jprintpubArray = data.getJSONArray(RestUtils.TAG_PRINT_PUB_HISTORY);
                            JSONArray jpromemArray = data.getJSONArray(RestUtils.TAG_MEM_HISTORY);
                            connect_status = data.getInt(RestUtils.TAG_CONNECT_STATUS);
                            boolean isConnected = data.getBoolean(RestUtils.TAG_IS_CONNECTED);
                            String qb_user_id = data.getString(RestUtils.TAG_QB_USER_ID);
                            /** invited **/
                            if (data.getInt(RestUtils.TAG_CONNECT_STATUS) == 2) {
                                ig_invite.setVisibility(View.INVISIBLE);
                                include_lOut.setVisibility(View.VISIBLE);

                                /**awaiting **/
                            } else if (data.getInt(RestUtils.TAG_CONNECT_STATUS) == 1) {
                                ig_invite.setVisibility(View.INVISIBLE);
                                include_lOut.setVisibility(View.VISIBLE);
                                lOut_accept.setVisibility(View.GONE);
                                lOut_reject.setVisibility(View.GONE);
                                seperator.setVisibility(View.GONE);
                                tv_awaiting.setVisibility(View.VISIBLE);
                                include_lOut.setBackgroundColor(Color.parseColor("#8A231f20"));

                                /**connect**/
                            } else if (data.getInt(RestUtils.TAG_CONNECT_STATUS) == 3) {

                                include_lOut.setVisibility(View.GONE);
                                lOut_accept.setVisibility(View.GONE);
                                lOut_reject.setVisibility(View.GONE);
                                seperator.setVisibility(View.GONE);
                                tv_awaiting.setVisibility(View.GONE);
                                ig_invite.setVisibility(View.GONE);
                                ig_message.setVisibility(View.GONE);
//                                ig_share.setVisibility(View.VISIBLE);
                            }


                            /**Personal JSON**/
                            if ((jpersonalInfoObj.has(RestUtils.TAG_FULL_NAME) || jpersonalInfoObj.has(RestUtils.TAG_CNT_NUM) || jpersonalInfoObj.has(RestUtils.TAG_CNNTMUNVIS) ||
                                    jpersonalInfoObj.has(RestUtils.TAG_CNT_EMAIL) || jpersonalInfoObj.has(RestUtils.TAG_CNNTEMAILVIS) || jpersonalInfoObj.has(RestUtils.TAG_CASE_ROOM_COUNT) ||
                                    jpersonalInfoObj.has(RestUtils.TAG_CONNECTS_COUNT) || jpersonalInfoObj.has(RestUtils.TAG_GROUPS_COUNT) || jpersonalInfoObj.has(RestUtils.TAG_SPLTY) ||
                                    jpersonalInfoObj.has(RestUtils.TAG_PROFILE_PIC_NAME))) {

                                tx_name.setText("Dr. " + jpersonalInfoObj.getString(RestUtils.TAG_FULL_NAME));
                                tx_speciality.setText(jpersonalInfoObj.getString(RestUtils.TAG_SPLTY));
                                if (jpersonalInfoObj.optString(RestUtils.TAG_SUB_SPLTY) != null && !jpersonalInfoObj.optString(RestUtils.TAG_SUB_SPLTY).isEmpty()) {
                                    tx_subSpeciality.setText(jpersonalInfoObj.optString(RestUtils.TAG_SUB_SPLTY));
                                    tx_subSpeciality.setVisibility(View.VISIBLE);
                                } else {
                                    tx_subSpeciality.setVisibility(View.GONE);
                                }

                                if (jpersonalInfoObj.has(RestUtils.TAG_PROFILE_PIC_URL) && !jpersonalInfoObj.optString(RestUtils.TAG_PROFILE_PIC_URL).isEmpty()) {
                                    AppUtil.loadImageUsingGlide(VisitProfileActivity.this, jpersonalInfoObj.optString(RestUtils.TAG_PROFILE_PIC_URL).trim(), ig_visitor_pic, R.drawable.default_profilepic);
                                } else if (!jpersonalInfoObj.getString(RestUtils.TAG_PROFILE_PIC_NAME).equals("")) {
                                    try {
                                        profile_pic_name = jpersonalInfoObj.getString(RestUtils.TAG_PROFILE_PIC_NAME);
                                        String image_response = new DownloadAsync(VisitProfileActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), jpersonalInfoObj.getString(RestUtils.TAG_PROFILE_PIC_NAME), "profile").get();
                                        JSONObject jObject = new JSONObject(image_response);
                                        if (jObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) && jObject.has(RestUtils.TAG_DATA)) {
                                            JSONObject dataJson = jObject.getJSONObject(RestUtils.TAG_DATA);
                                            String original_link = dataJson.getString(RestUtils.TAG_ORIGINAL_LINK);
                                            new LoadImageFromUrl().executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), original_link, jpersonalInfoObj.getString(RestUtils.TAG_PROFILE_PIC_NAME));

                                        }
                                    } catch (InterruptedException ie) {
                                        Thread.currentThread().interrupt();
                                        ie.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                tx_coomon_connects_count.setText(jpersonalInfoObj.getString(RestUtils.TAG_CONNECTS_COUNT));
                                mTitleTextView.setText("Dr. " + jpersonalInfoObj.getString(RestUtils.TAG_FULL_NAME));
                                str_phno = jpersonalInfoObj.getString(RestUtils.TAG_CNT_NUM);
                                str_email = jpersonalInfoObj.getString(RestUtils.TAG_CNT_EMAIL);


                                String[] vis_array = getResources().getStringArray(R.array.number_visibility_arrays_for_srever);
                                List<String> stringList = new ArrayList<>(Arrays.asList(vis_array));
                                /** ContactNumber Visibility **/
                                if (stringList.get(0).equals(jpersonalInfoObj.getString(RestUtils.TAG_CNNTMUNVIS))) {
                                    isphChangedStat = true;
                                    onPrepareOptionsMenu(menu);
                                }
                                if (stringList.get(1).equals(jpersonalInfoObj.getString(RestUtils.TAG_CNNTMUNVIS))) {
                                    onPrepareOptionsMenu(menu);
                                }
                                if (stringList.get(2).equals(jpersonalInfoObj.getString(RestUtils.TAG_CNNTMUNVIS))) {
                                    onPrepareOptionsMenu(menu);
                                }

                                /** email Visibility **/
                                if (stringList.get(0).equals(jpersonalInfoObj.getString(RestUtils.TAG_CNNTEMAILVIS))) {
                                    ismailChangeStat = true;
                                    onPrepareOptionsMenu(menu);
                                } else if (stringList.get(1).equals(jpersonalInfoObj.getString(RestUtils.TAG_CNNTEMAILVIS))) {
                                    onPrepareOptionsMenu(menu);
                                } else if (stringList.get(2).equals(jpersonalInfoObj.getString(RestUtils.TAG_CNNTEMAILVIS))) {
                                    onPrepareOptionsMenu(menu);
                                }

                            }
                            /**Academic JSON**/
                            if (jacademicjArray.length() > 0) {
                                int length = jacademicjArray.length();
                                for (int a = 0; a < length; a++) {
                                    JSONObject ac = jacademicjArray.getJSONObject(a);
                                    academicInfo = new AcademicInfo();
                                    academicInfo.setCollege(ac.getString(RestUtils.TAG_COLLEGE));
                                    academicInfo.setDegree(ac.getString(RestUtils.TAG_DEGREE));
                                    academicInfo.setCurrently_pursuing(ac.getBoolean(RestUtils.TAG_CURRENTLY));
                                    academicInfo.setPassing_year(ac.getInt(RestUtils.TAG_PASSING_YEAR));
                                    academicInfo.setUniversity(ac.getString(RestUtils.TAG_UNIVERSITY));
                                    academic_history.add(academicInfo);
                                }
                            }
                            /**AboutMe JSON**/
                            if (jaboutObj.has(RestUtils.TAG_ABOUT_ME) || jaboutObj.has(RestUtils.TAG_WEB_PAGE) || jaboutObj.has(RestUtils.TAG_BLOG_PAGE) || jaboutObj.has(RestUtils.TAG_FB_PAGE)) {
                                rv_aboutMe.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(jaboutObj.getString(RestUtils.TAG_ABOUT_ME))) {
                                    tv_aboutMe_heading.setText("ABOUT " + "Dr. " + " " + jpersonalInfoObj.getString(RestUtils.TAG_FULL_NAME));
                                    tv_about_me.setText(jaboutObj.getString(RestUtils.TAG_ABOUT_ME));
                                } else {
                                    tv_aboutMe_heading.setVisibility(View.GONE);
                                    tv_about_me.setVisibility(View.GONE);
//                                       about_me_seperator0.setVisibility(View.GONE);
                                    rv_aboutMe.setVisibility(View.GONE);
                                }
                                if (!TextUtils.isEmpty(jaboutObj.getString(RestUtils.TAG_BLOG_PAGE))) {
                                    tv_blog.setText(jaboutObj.getString(RestUtils.TAG_BLOG_PAGE));
                                } else {
                                    tv_blog.setVisibility(View.GONE);
//                                       about_me_seperator0.setVisibility(View.GONE);
                                }
                                if (!TextUtils.isEmpty(jaboutObj.getString(RestUtils.TAG_WEB_PAGE))) {
                                    tv_website.setText(jaboutObj.getString(RestUtils.TAG_WEB_PAGE));
                                } else {
                                    tv_website.setVisibility(View.GONE);
                                    about_me_seperator1.setVisibility(View.GONE);
                                }
                                if (!TextUtils.isEmpty(jaboutObj.getString(RestUtils.TAG_FB_PAGE))) {
                                    tv_facebook.setText(jaboutObj.getString(RestUtils.TAG_FB_PAGE));
                                } else {
                                    tv_facebook.setVisibility(View.GONE);
                                    about_me_seperator2.setVisibility(View.GONE);
                                }
                            } else
                                rv_aboutMe.setVisibility(View.GONE);

                            /**Professional JSON**/
                            if (jProfessionalArray.length() > 0) {
                                int plen = jProfessionalArray.length();
                                for (int pr = 0; pr < plen; pr++) {
                                    JSONObject pc = jProfessionalArray.getJSONObject(pr);
                                    professionalInfo = new ProfessionalInfo();
                                    professionalInfo.setDesignation(pc.getString(RestUtils.TAG_DESIGNATION));
                                    professionalInfo.setStart_date(pc.getLong(RestUtils.TAG_FROMDATE));
                                    professionalInfo.setEnd_date(pc.getLong(RestUtils.TAG_TODATE));
                                    professionalInfo.setWorking_here(pc.getBoolean(RestUtils.TAG_WORKING));
                                    professionalInfo.setWorkplace(pc.getString(RestUtils.TAG_WORKPLACE));
                                    professionalInfo.setShowOncard(pc.getBoolean(RestUtils.TAG_SHOW_ON_CARD));
                                    professionalInfo.setLocation(pc.getString(RestUtils.TAG_LOCATION));
                                    professional_history.add(professionalInfo);
                                }
                                /**Setting professional showoncard workplace to personal details workplace **/
                                for (int w = 0; w < professional_history.size(); w++) {
                                    if (professional_history.get(w).isWorking_here() && professional_history.get(w).isShowOncard()) {
                                        tx_workplace.setText(professional_history.get(w).getWorkplace());
                                    }
                                }
                            }
                            /**OnlinePublications JSON**/
                            if (jonlinepubArray.length() > 0) {
                                int onlinelgth = jonlinepubArray.length();
                                for (int o = 0; o < onlinelgth; o++) {
                                    JSONObject onlinejObj = jonlinepubArray.getJSONObject(o);
                                    PublicationsInfo publicationsInfo = new PublicationsInfo();
                                    publicationsInfo.setTitle(onlinejObj.getString(RestUtils.TAG_TITLE));
                                    publicationsInfo.setAuthors(onlinejObj.getString(RestUtils.TAG_AUTHORS));
                                    publicationsInfo.setJournal(onlinejObj.getString(RestUtils.TAG_JOURNAL));
                                    publicationsInfo.setWeb_page(onlinejObj.getString(RestUtils.TAG_WEBPAGE_LINK));
                                    online_publications_history.add(publicationsInfo);
                                }
                            }
                            /**PrintPublications JSON**/
                            if (jprintpubArray.length() > 0) {
                                int printlgth = jprintpubArray.length();
                                for (int p = 0; p < printlgth; p++) {
                                    JSONObject printjObj = jprintpubArray.getJSONObject(p);
                                    PublicationsInfo publicationsInfo = new PublicationsInfo();
                                    publicationsInfo.setTitle(printjObj.getString(RestUtils.TAG_TITLE));
                                    publicationsInfo.setAuthors(printjObj.getString(RestUtils.TAG_AUTHORS));
                                    publicationsInfo.setJournal(printjObj.getString(RestUtils.TAG_JOURNAL));
                                    print_publications_history.add(publicationsInfo);
                                }
                            }
                            /**ProfessionalMembership JSON**/
                            if (jpromemArray.length() > 0) {
                                int prolgth = jpromemArray.length();
                                for (int m = 0; m < prolgth; m++) {
                                    JSONObject memjObj = jpromemArray.getJSONObject(m);
                                    professionalMembershipInfo = new ProfessionalMembershipInfo();
                                    professionalMembershipInfo.setMembership_name(memjObj.getString(RestUtils.TAG_PROF_MEM_NAME));
                                    membership_history.add(professionalMembershipInfo);
                                }
                            }


                            // Academic list
                            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                            final View pro_footer = inflater.inflate(R.layout.professional_list_footer, null);
                            final TextView pro_viewall_text = (TextView) pro_footer.findViewById(R.id.viewall_text);
                            academic_lv.addFooterView(pro_footer);
                            academic_lv.setAdapter(null);
                            final int acad_length = academic_history.size();
                            if (acad_length != 0) {
                                int i = 0;
                                if (acad_length == 1) {
                                    i = 1;
                                } else {
                                    i = 2;
                                }

                                final AcademicAdapter academicadapter01 = new AcademicAdapter(VisitProfileActivity.this, academic_history.subList(0, i));
                                final AcademicAdapter academicAdapter = new AcademicAdapter(VisitProfileActivity.this, academic_history);
                                if (acad_length <= 2) {
                                    academic_lv.removeFooterView(pro_footer);
                                }
                                academic_lv.setAdapter(academicadapter01);

                                pro_footer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (pro_viewall_text.getText().toString().equals("View all")) {
                                            academic_lv.setAdapter(academicAdapter);
                                            academicAdapter.notifyDataSetChanged();
                                            pro_viewall_text.setText("Show less");
                                        } else if (pro_viewall_text.getText().toString().equals("Show less")) {
                                            academic_lv.setAdapter(academicadapter01);
                                            academicadapter01.notifyDataSetChanged();
                                            pro_viewall_text.setText("View all");
                                        }
                                    }
                                });
                            } else {
                                linear_lv.removeView(rv_academic);
                            }

                            // Professional list
                            LayoutInflater workplace_inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                            final View workplace_footer = workplace_inflater.inflate(R.layout.professional_list_footer, null);
                            final TextView workplace_viewall_text = (TextView) workplace_footer.findViewById(R.id.viewall_text);
                            professional_lv.addFooterView(workplace_footer);
                            professional_lv.setAdapter(null);
                            final int professional_length = professional_history.size();
                            if (professional_length != 0) {
                                int i = 0;
                                if (professional_length == 1) {
                                    i = 1;
                                } else {
                                    i = 2;
                                }
                                final ProfessionalAdapter professionaladapter01 = new ProfessionalAdapter(VisitProfileActivity.this, professional_history.subList(0, i), false);
                                final ProfessionalAdapter professionalAdapter = new ProfessionalAdapter(VisitProfileActivity.this, professional_history, false);
                                if (professional_length <= 2) {
                                    professional_lv.removeFooterView(workplace_footer);
                                }
                                professional_lv.setAdapter(professionaladapter01);

                                workplace_footer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (workplace_viewall_text.getText().toString().equals("View all")) {
                                            professional_lv.setAdapter(professionalAdapter);
                                            professionalAdapter.notifyDataSetChanged();
                                            workplace_viewall_text.setText("Show less");
                                        } else if (workplace_viewall_text.getText().toString().equals("Show less")) {
                                            professional_lv.setAdapter(professionaladapter01);
                                            professionaladapter01.notifyDataSetChanged();
                                            workplace_viewall_text.setText("View all");
                                        }
                                    }
                                });
                            } else {
                                linear_lv.removeView(rv_professional);
                            }

                            // PrintPublications list
                            LayoutInflater publications_inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                            final View publications_footer = publications_inflater.inflate(R.layout.professional_list_footer, null);
                            final TextView publications_viewall_text = (TextView) publications_footer.findViewById(R.id.viewall_text);
//                            printpublications_lv.addFooterView(publications_footer);
                            onlinepublications_lv.addFooterView(publications_footer);
                            printpublications_lv.addFooterView(publications_footer);
                            printpublications_lv.setAdapter(null);
                            onlinepublications_lv.setAdapter(null);

                            /** Print List **/
                            final int print_publications_length = print_publications_history.size();
                            if (print_publications_length != 0) {
                                int i = 0;
                                if (print_publications_length == 1) {
                                    i = 1;
                                } else {
                                    i = 2;
                                }
                                publicationsAdapter01 = new PublicationsAdapter(VisitProfileActivity.this, print_publications_history.subList(0, i));
                                publicationsAdapter = new PublicationsAdapter(VisitProfileActivity.this, print_publications_history);
                                if (print_publications_length <= 2) {
                                    printpublications_lv.removeFooterView(publications_footer);
                                }
                                printpublications_lv.setAdapter(publicationsAdapter01);
                            } else {
                                tx_print_pub_heading.setVisibility(View.GONE);
                                lv_publications.removeView(printpublications_lv);
                            }


                            /** Online List **/
                            final int online_publications_length = online_publications_history.size();
                            if (online_publications_length != 0) {
                                int i = 0;
                                if (online_publications_length == 1) {
                                    i = 1;
                                } else {
                                    i = 2;
                                }
                                opublicationsAdapter01 = new PublicationsAdapter(VisitProfileActivity.this, online_publications_history.subList(0, i));
                                opublicationsAdapter = new PublicationsAdapter(VisitProfileActivity.this, online_publications_history);
                                if (online_publications_length <= 2) {
                                    onlinepublications_lv.removeFooterView(publications_footer);
                                }
                                onlinepublications_lv.setAdapter(opublicationsAdapter01);


                            } else {
                                tx_online_pub_heading.setVisibility(View.GONE);
                                lv_publications.removeView(onlinepublications_lv);
                            }
                            publications_footer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (publications_viewall_text.getText().toString().equals("View all")) {
                                        printpublications_lv.setAdapter(publicationsAdapter);
                                        if (publicationsAdapter != null) {
                                            publicationsAdapter.notifyDataSetChanged();
                                        }
                                        onlinepublications_lv.setAdapter(opublicationsAdapter);
                                        if (opublicationsAdapter != null) {
                                            opublicationsAdapter.notifyDataSetChanged();
                                        }
                                        publications_viewall_text.setText("Show less");
                                    } else if (publications_viewall_text.getText().toString().equals("Show less")) {
                                        printpublications_lv.setAdapter(publicationsAdapter01);
                                        if (publicationsAdapter01 != null) {
                                            publicationsAdapter01.notifyDataSetChanged();
                                        }
                                        onlinepublications_lv.setAdapter(opublicationsAdapter01);
                                        if (opublicationsAdapter01 != null) {
                                            opublicationsAdapter01.notifyDataSetChanged();
                                        }
                                        publications_viewall_text.setText("View all");
                                    }
                                }
                            });
                            if (print_publications_length > 2 && online_publications_length > 2) {
                                printpublications_lv.removeFooterView(publications_footer);
                            }
                            if (print_publications_length == 0 && online_publications_length == 0) {
                                linear_lv.removeView(lv_publications);
                            }

                            // Membership list
                            LayoutInflater membership_inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                            final View membership_footer = membership_inflater.inflate(R.layout.professional_list_footer, null);
                            final TextView membership_viewall_text = (TextView) membership_footer.findViewById(R.id.viewall_text);
                            professionalmem_lv.addFooterView(membership_footer);
                            professionalmem_lv.setAdapter(null);
                            final int mem_length = membership_history.size();
                            if (mem_length != 0) {
                                int i = 0;
                                if (mem_length == 1) {
                                    i = 1;
                                } else {
                                    i = 2;
                                }

                                final MembershipAdapter membershipAdapter01 = new MembershipAdapter(VisitProfileActivity.this, membership_history.subList(0, i));
                                final MembershipAdapter membershipAdapter = new MembershipAdapter(VisitProfileActivity.this, membership_history);
                                if (mem_length <= 2) {
                                    professionalmem_lv.removeFooterView(membership_footer);
                                }
                                professionalmem_lv.setAdapter(membershipAdapter01);

                                membership_footer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (membership_viewall_text.getText().toString().equals("View all")) {
                                            professionalmem_lv.setAdapter(membershipAdapter);
                                            membershipAdapter.notifyDataSetChanged();
                                            membership_viewall_text.setText("Show less");
                                        } else if (membership_viewall_text.getText().toString().equals("Show less")) {
                                            professionalmem_lv.setAdapter(membershipAdapter01);
                                            membershipAdapter01.notifyDataSetChanged();
                                            membership_viewall_text.setText("View all");
                                        }
                                    }
                                });
                            } else {
                                linear_lv.removeView(rv_membership);
                            }
                            hideProgress();
                            //visit_scrollView.smoothScrollTo(0, visit_scrollView.getTop());
                            /**
                             * If Contacts info not available build manually
                             */

                            if (contactsInfo == null) {
                                contactsInfo = new ContactsInfo();
                                contactsInfo.setDoc_id(otherdocId);
                                contactsInfo.setQb_userid(data.getInt(RestUtils.TAG_QB_USER_ID));
                                contactsInfo.setNetworkStatus(connect_status + "");
                                contactsInfo.setName(jpersonalInfoObj.optString(RestUtils.TAG_FULL_NAME));
                                contactsInfo.setPhno(jpersonalInfoObj.optString(RestUtils.TAG_CNT_NUM));
                                contactsInfo.setPhno_vis(jpersonalInfoObj.optString(RestUtils.TAG_CNNTMUNVIS));
                                contactsInfo.setEmail(jpersonalInfoObj.optString(RestUtils.TAG_CNT_EMAIL));
                                contactsInfo.setEmail_vis(jpersonalInfoObj.optString(RestUtils.TAG_CNNTEMAILVIS));
                                contactsInfo.setSpeciality(jpersonalInfoObj.optString(RestUtils.TAG_SPLTY));
                                contactsInfo.setSubSpeciality(jpersonalInfoObj.optString(RestUtils.TAG_SUB_SPLTY));
                                contactsInfo.setPic_name(jpersonalInfoObj.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                contactsInfo.setPic_url(jpersonalInfoObj.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
                                if (academicInfo != null) {
                                    contactsInfo.setDegree(academicInfo.getDegree());
                                }
                                if (professionalInfo != null) {
                                    contactsInfo.setDesignation(professionalInfo.getDesignation());
                                    contactsInfo.setLocation(professionalInfo.getLocation());
                                    contactsInfo.setWorkplace(professionalInfo.getWorkplace());
                                }

                            } else {
                                contactsInfo.setSpeciality(jpersonalInfoObj.optString(RestUtils.TAG_SPLTY));
                            }
                        } else {
                            hideProgress();

                            /** Success response for Accept **/
                            include_lOut.setVisibility(View.GONE);
                            lOut_accept.setVisibility(View.GONE);
                            lOut_reject.setVisibility(View.GONE);
                            seperator.setVisibility(View.GONE);
                            tv_awaiting.setVisibility(View.GONE);
                            ig_invite.setVisibility(View.GONE);
                            ig_message.setVisibility(View.GONE);
//                            ig_share.setVisibility(View.VISIBLE);
                            connect_status = 3;
                            try {
                                if (contactsInfo.getPic_url() != null && !contactsInfo.getPic_url().isEmpty()) {
                                    AppUtil.loadImageUsingGlide(VisitProfileActivity.this, contactsInfo.getPic_url().trim(), ig_visitor_pic, R.drawable.default_profilepic);
                                } else if (!TextUtils.isEmpty(profile_pic_name)) {
                                    String image_response = new DownloadAsync(VisitProfileActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), profile_pic_name, "profile").get();
                                    /** Image Downloading**/
                                    JSONObject jObject = new JSONObject(image_response);
                                    if (jObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) && jObject.has(RestUtils.TAG_DATA)) {
                                        JSONObject dataJson = jObject.getJSONObject(RestUtils.TAG_DATA);
                                        String small_link = dataJson.getString(RestUtils.TAG_SMALL_LINK);
                                        final String fileName = profile_pic_name + ".jpg";
                                        dl_imagepath = new DownloadImageAsync(VisitProfileActivity.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), small_link, fileName).get();
                                        if (FileHelper.isFilePresent(profile_pic_name + ".jpg", "profile", VisitProfileActivity.this)) {
                                            contactsInfo.setPic_name(profile_pic_name);
                                        }
                                    }
                                } else {
                                    ig_visitor_pic.setImageResource(R.drawable.default_profilepic);
                                }
                                int qb_userid = contactsInfo.getQb_userid();
                                realmManager.insertMyContacts(realm, contactsInfo, 3);
                                realmManager.deleteNotification(realm, "" + otherdocId, RestUtils.TAG_DOC_ID);
                                /** firing Broadcast to update notification count **/
                                Intent intent = new Intent("NotificationCount");
                                LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                                ie.printStackTrace();
                            } catch (Exception e) {
                                hideProgress();
                                e.printStackTrace();
                            }
                        }
                    } else {
                        hideProgress();
                        displayErrorScreen(response);
                        finish();
                    }
                } catch (Exception e) {
                    displayErrorScreen(response);
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                    hideProgress();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_visit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ac_help) {
            Intent intentsupport = new Intent(getApplicationContext(), ContactSupport.class);
            startActivity(intentsupport);
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return super.onPrepareOptionsMenu(menu);
    }

    public class LoadImageFromUrl extends AsyncTask<String, String, Bitmap> {

        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            centered_progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                InputStream in = url.openConnection().getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            centered_progressbar.setVisibility(View.GONE);
            if (bitmap != null) {
                ig_visitor_pic.setImageBitmap(bitmap);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        try {
            if (requestCode == 1 && resultCode == 1) {
                /** awaiting **/
                ig_invite.setVisibility(View.INVISIBLE);
                include_lOut.setVisibility(View.VISIBLE);
                lOut_accept.setVisibility(View.GONE);
                lOut_reject.setVisibility(View.GONE);
                seperator.setVisibility(View.GONE);
                tv_awaiting.setVisibility(View.VISIBLE);
                include_lOut.setBackgroundColor(Color.parseColor("#8A231f20"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
