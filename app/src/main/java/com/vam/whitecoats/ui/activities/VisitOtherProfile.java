package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.DownloadAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.AreasOfInterest;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.EventInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.AcademicAdapter;
import com.vam.whitecoats.ui.adapters.AvailabilityAdapter;
import com.vam.whitecoats.ui.adapters.EventAdapter;
import com.vam.whitecoats.ui.adapters.MembershipAdapter;
import com.vam.whitecoats.ui.adapters.ProfessionalAdapter;
import com.vam.whitecoats.ui.adapters.PublicationsAdapter;
import com.vam.whitecoats.ui.customviews.NonScrollListView;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.fragments.DashboardUpdatesFragment;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.ControllableAppBarLayout;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowCard;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import io.realm.Realm;


public class VisitOtherProfile extends BaseActionBarActivity {


    private ImageView ig_visitor_pic;
    private Button btn_share, btn_connect, ig_invite, ig_message;
    private TextView tx_name, tx_degree, tx_speciality, tx_subSpeciality, tx_workplace, tx_print_pub_heading, tx_online_pub_heading, connect_to_text, eventsDefaultHeading;
    private TextView tx_connects_count, tx_caserooms_count, tx_groups_count;
    private TextView tx_common_caserooms_count, tx_common_groups_count;
    private TextView tv_website, tv_facebook, tv_blog, mTitleTextView;
    private TextView tv_about_me, tv_aboutMe_heading, follwersTextView, followingTextView;

    private View about_me_seperator0, about_me_seperator1, about_me_seperator2;
    private TextView tv_accept, tv_reject, tv_awaiting;

    private NestedScrollView visit_profile_scroll;
    // private LinearLayout linear_lv;
    /**
     * About visitor layout
     **/
    TextView tx_visitor_name;
    private long mLastClickTime = 0;


    private RelativeLayout rv_aboutMe, rv_professional, availableParentLayout, rv_academic, rv_membership, rv_areaInterest, rv_eventcalendar;
    private LinearLayout lv_publications;

    private NonScrollListView professional_lv;
    private NonScrollListView academic_lv;
    private NonScrollListView printpublications_lv, onlinepublications_lv, awards_lv, memberships_lv;


    private int connect_status;


    ArrayList<AcademicInfo> academic_history = new ArrayList<AcademicInfo>();
    AcademicInfo academicInfo;

    ArrayList<ProfessionalInfo> professional_history = new ArrayList<ProfessionalInfo>();
    ArrayList<ProfessionalInfo> availableList = new ArrayList<ProfessionalInfo>();
    ProfessionalInfo professionalInfo;
    ProfessionalInfo availableInfo;

    ArrayList<PublicationsInfo> print_publications_history = new ArrayList<PublicationsInfo>();
    ArrayList<PublicationsInfo> online_publications_history = new ArrayList<PublicationsInfo>();
    private Menu menu;
    private int otherdocId;
    //private ScrollView visit_scrollView;
    private ProgressBar centered_progressbar;

    /**
     * Setting DocId from realm
     **/
    private int userid;
    private Realm realm;
    private RealmManager realmManager;
    ContactsInfo contactsInfo = new ContactsInfo();
    private String profile_pic_name;


    private PublicationsAdapter publicationsAdapter01 = null;
    private PublicationsAdapter publicationsAdapter = null;
    private EventAdapter eventAdapter = null;
    private PublicationsAdapter opublicationsAdapter01;
    private PublicationsAdapter opublicationsAdapter;
    private ControllableAppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private TextView user_email_text_visit, user_contact_text_visit;
    private LinearLayout accept_reject_lay;
    private TextView visit_connect_count, visit_post_count;
    private int otherDocId = 0;
    private String otherDocName = "";
    private String profilePicUrl = "";
    private String userNameWithSalutation = "";
    private int scrollRange = -1;
    private boolean isScroll;
    public static final String TAG = VisitOtherProfile.class.getSimpleName();
    private RealmBasicInfo realmBasicInfo;
    private ImageView aboutMeEditIcon, professionalAddIcon, qualificationAddIcon, publicationAddIcon, awardsMembershipAddIcon, about_profile_icon, eventAddIcon;
    private TextView phoneVerifyLabel, emailVerifyLabel, add_about_label, academics_heading, add_publications_text, add_awards_membership_text, experienceHeadigTxt;
    private View websiteSeparator, blogSeparator;
    private TextView viewAllProfTxtVw, viewAllQualTxtVw, viewAllPubTxtVw, viewAllMembership, viewAllEvents, viewAllAvailability;
    private ProfessionalAdapter professionalAdapter;
    private AvailabilityAdapter availablityAdapter;
    private NonScrollListView professional_listView, publications_listView, academic_listView, memberships_listView, events_listView, availabilityListView;
    private AcademicAdapter academicAdapter;
    private ArrayList<PublicationsInfo> publication_history = new ArrayList<PublicationsInfo>();
    private ArrayList<ProfessionalMembershipInfo> professionalMembershipInfoList = new ArrayList<>();
    private ArrayList<EventInfo> eventInfoArrayList = new ArrayList<>();
    private MembershipAdapter membershipAdapter;
    private ContactsInfo updatedContactInfo;
    private TextView user_post_count_text;
    private TextView user_followersCntTxt;
    private TextView user_followingCntTxt;
    private TextView user_connect_count_text;
    private Button user_follow_btn;
    private boolean isUserFollow;
    private LinearLayout user_post_count_text_lay, user_followersCntTxt_lay, user_followingCntTxt_lay, user_connect_count_text_lay;
    private TextView linkedInTxtVw, twitterTxtVw, instaTxtVw, userProfileURL;
    private TagContainerLayout mTagContainerLayout;
    List<String> areaInfoList = new ArrayList<>();
    private RelativeLayout interest_add_img_lay;
    private TextView area_of_interest_label, visit_usp_text, visit_specific_ask_text;
    private ViewGroup visit_specific_ask_layout;
    private RelativeLayout available_add_img_lay, eventAddButtonLayout;
    private TextView available_at_label;
    private String otherUserProfileURL;
    private TextView visit_over_all_experience;
    private RelativeLayout experience_lay;
    private boolean customBackButton = false;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launcherInviteResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_other_profile);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_reg, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        final TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);

        next_button.setVisibility(View.GONE);

        initialize();
        realmManager = new RealmManager(VisitOtherProfile.this);

        realm = Realm.getDefaultInstance();
        userid = realmManager.getDoc_id(realm);
        realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launcherInviteResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // result code ==1
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    try {
                        if (resultCode == 1) {
                            /** awaiting **/
                            ig_invite.setVisibility(View.GONE);
                            tv_awaiting.setVisibility(View.VISIBLE);
                            connect_to_text.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        //End
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
            if (isConnectingToInternet()) {

                JSONObject visitRequestJsonObject = new JSONObject();
                visitRequestJsonObject.put(RestUtils.TAG_USER_ID, userid);
                visitRequestJsonObject.put(RestUtils.TAG_OTHER_USER_ID, otherdocId);
                showProgress();
                new VolleySinglePartStringRequest(VisitOtherProfile.this, Request.Method.POST, RestApiConstants.VIEW_USER_PROFILE, visitRequestJsonObject.toString(), "VISIT_OTHER_PROFILE", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        hideProgress();

                        if (successResponse != null && !successResponse.isEmpty()) {
                            Log.i(TAG, "onSuccessResponse - " + successResponse);
                            try {
                                JSONObject responseObj = new JSONObject(successResponse);

                                if (responseObj.has(RestUtils.TAG_STATUS) && responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                    if (responseObj.has(RestUtils.TAG_DATA)) {
                                        /*
                                         * DATA node elements
                                         */
                                        JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                        otherDocId = dataObj.optInt(RestUtils.TAG_USER_ID, 0);
                                        otherDocName = dataObj.optString(RestUtils.TAG_USER_SALUTAION) + " " + dataObj.optString(RestUtils.TAG_USER_FIRST_NAME);
                                        updatedContactInfo = new ContactsInfo();
                                        updatedContactInfo.setDoc_id(otherdocId);
                                        updatedContactInfo.setNetworkStatus(connect_status + "");
                                        updatedContactInfo.setFollow_status(dataObj.optString(RestUtils.TAG_FOLLOW_STATUS));
                                        updatedContactInfo.setName(dataObj.optString(RestUtils.TAG_USER_FULL_NAME));
                                        updatedContactInfo.setPhno(dataObj.optString(RestUtils.TAG_CNT_NUM));
                                        updatedContactInfo.setEmail(dataObj.optString(RestUtils.TAG_CNT_EMAIL));
                                        List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                                        /** ContactNumber Visibility **/
                                        updatedContactInfo.setPhno_vis(dataObj.optString(RestUtils.TAG_CNNTMUNVIS));
                                        /** email Visibility **/
                                        updatedContactInfo.setEmail_vis(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS));
                                        if (dataObj.has(RestUtils.KEY_SPECIALISTS)) {
                                            StringBuilder specialityString = new StringBuilder();
                                            JSONArray specialitiesArray = dataObj.optJSONArray(RestUtils.KEY_SPECIALISTS);
                                            int len = specialitiesArray.length();
                                            for (int count = 0; count < len; count++) {
                                                specialityString.append(specialitiesArray.optString(count)).append(",");
                                            }
                                            updatedContactInfo.setSpeciality(specialityString.deleteCharAt(specialityString.length() - 1).toString());
                                        }
                                        updatedContactInfo.setSubSpeciality(dataObj.optString(RestUtils.TAG_SUB_SPLTY));
                                        updatedContactInfo.setPic_name(dataObj.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                        updatedContactInfo.setPic_url(dataObj.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
                                        updatedContactInfo.setUserSalutation(dataObj.optString(RestUtils.TAG_USER_SALUTAION));
                                        updatedContactInfo.setUserTypeId(dataObj.optInt(RestUtils.TAG_USER_TYPE_ID));
                                        updatedContactInfo.setLocation(dataObj.optString(RestUtils.TAG_LOCATION));
                                        updatedContactInfo.setUUID(dataObj.optString(RestUtils.TAG_USER_UUID));

                                        if (dataObj.has(RestUtils.TAG_OTHER_INFO)) {
                                            JSONObject otherInfoObj = dataObj.optJSONObject(RestUtils.TAG_OTHER_INFO);
                                            if (otherInfoObj != null) {
                                                updatedContactInfo.setQb_userid(otherInfoObj.optInt(RestUtils.TAG_QB_USER_ID));
                                            }
                                        }
                                        realmManager.updateMyConnectData(updatedContactInfo.getDoc_id(), updatedContactInfo);
                                        upshotEventData(0, 0, 0, updatedContactInfo.getUUID(), "", "", "", " ", false);
                                        visitBasicInfo(dataObj);
                                        aboutMe(dataObj);
                                        professionalData(dataObj);
                                        updateAvailableInfo(dataObj);
                                        academicsData(dataObj);
                                        publicationData(dataObj);
                                        awardsAndMembershipsData(dataObj);
                                        activityCountData(dataObj);
                                        areaOFInterest(dataObj);
                                        eventCalendarData(dataObj);
                                    }
                                } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    displayErrorScreen(successResponse);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(VisitOtherProfile.this, "Unable to load data,please try again.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        hideProgress();
                        displayErrorScreen(errorResponse);
                    }
                }).sendSinglePartRequest();
            } else {
                hideProgress();
            }


            tv_website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = tv_website.getText().toString();
                    AppUtil.openLinkInBrowser(url, VisitOtherProfile.this);
                }
            });
            tv_blog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = tv_blog.getText().toString();
                    AppUtil.openLinkInBrowser(url, VisitOtherProfile.this);
                }
            });
            tv_facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtil.openFbLink(VisitOtherProfile.this, tv_facebook.getText().toString().toLowerCase());
                }
            });
            linkedInTxtVw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = linkedInTxtVw.getText().toString();
                    AppUtil.openLinkInBrowser(url, VisitOtherProfile.this);

                }
            });
            twitterTxtVw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = twitterTxtVw.getText().toString();
                    AppUtil.openLinkInBrowser(url, VisitOtherProfile.this);

                }
            });
            instaTxtVw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = instaTxtVw.getText().toString();
                    AppUtil.openLinkInBrowser(url, VisitOtherProfile.this);

                }
            });
            userProfileURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VisitOtherProfile.this);
                    String[] animals = {"Copy", "Share via..."};
                    builder.setItems(animals, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("Copied text", userProfileURL.getText().toString());
                                    clipboard.setPrimaryClip(clip);
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    shareOtherUserProfile();
                                    break;

                            }
                        }
                    });

// create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        ig_visitor_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
                    Intent intent = new Intent(VisitOtherProfile.this, Profile_fullView.class);
                    intent.putExtra("selectedDocId", otherdocId);
                    intent.putExtra("profilePicUrl", profilePicUrl);
                    startActivity(intent);
                }
            }
        });


        user_post_count_text_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    Intent intent = new Intent(VisitOtherProfile.this, MyBookmarksActivity.class);
                    intent.putExtra(RestUtils.NAVIGATATION, "OtherUserPosts");
                    intent.putExtra(RestUtils.TAG_OTHER_USER_ID, otherDocId);
                    intent.putExtra("otherDocName", otherDocName + "'s");
                    intent.putExtra("otherDocUUID", updatedContactInfo.getUUID());
                    startActivity(intent);

                }
            }
        });


        user_connect_count_text_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    Intent viewconnects = new Intent(VisitOtherProfile.this, ViewContactsActivity.class);
                    viewconnects.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    viewconnects.putExtra("profileid", otherdocId);
                    viewconnects.putExtra("doc_name", otherDocName + "'s");
                    viewconnects.putExtra("otherDocUUID", updatedContactInfo.getUUID());
                    startActivity(viewconnects);
                }
            }
        });

        user_followersCntTxt_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    Intent viewconnects = new Intent(VisitOtherProfile.this, ViewContactsActivity.class);
                    viewconnects.putExtra("profileid", otherdocId);
                    viewconnects.putExtra("doc_name", otherDocName + "'s");
                    viewconnects.putExtra(RestUtils.NAVIGATATE_FROM, "Followers");
                    viewconnects.putExtra("otherDocUUID", updatedContactInfo.getUUID());
                    startActivity(viewconnects);
                }
            }
        });

        user_followingCntTxt_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    Intent viewconnects = new Intent(VisitOtherProfile.this, ViewContactsActivity.class);
                    viewconnects.putExtra("profileid", otherdocId);
                    viewconnects.putExtra("doc_name", otherDocName);
                    viewconnects.putExtra("isOtherProfileFollowing", true);
                    viewconnects.putExtra(RestUtils.NAVIGATATE_FROM, "Following");
                    viewconnects.putExtra("otherDocUUID", updatedContactInfo.getUUID());
                    startActivity(viewconnects);
                }
            }
        });

        user_follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isConnectingToInternet(VisitOtherProfile.this)) {
                    Toast.makeText(VisitOtherProfile.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgress();
                JSONObject requestObj = new JSONObject();
                try {
                    requestObj.put(RestUtils.TAG_USER_ID, userid);
                    requestObj.put(RestUtils.TAG_OTHER_USER_ID, otherdocId);
                    requestObj.put(RestUtils.TAG_IS_FOLLOW, !isUserFollow);
                    new VolleySinglePartStringRequest(VisitOtherProfile.this, Request.Method.POST, RestApiConstants.USER_FOLLOW_REST_API, requestObj.toString(), "USER_FOLLOW", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            hideProgress();
                            if (successResponse != null && !successResponse.isEmpty()) {
                                JSONObject responseObj = null;
                                try {
                                    responseObj = new JSONObject(successResponse);
                                    if (responseObj != null && responseObj.has(RestUtils.TAG_STATUS)) {
                                        if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                            updateUIWithFollowResponse(true);
                                        } else if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                                            if (responseObj.optInt(RestUtils.TAG_ERROR_CODE) == 1039) {
                                                updateUIWithFollowResponse(false);
                                            }
                                            Toast.makeText(VisitOtherProfile.this, responseObj.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            hideProgress();
                        }
                    }).sendSinglePartRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateUIWithFollowResponse(boolean onSuccess) {
        DashboardUpdatesFragment.dashboardRefreshOnSubscription = true;
        if (isUserFollow) {
            isUserFollow = false;
            user_follow_btn.setText("Follow");
            user_follow_btn.setBackgroundResource(R.drawable.accept_button);
        } else {
            isUserFollow = true;
            user_follow_btn.setText("Unfollow");
            user_follow_btn.setBackgroundResource(R.drawable.reject_button);
        }
        if (onSuccess) {
            realmManager.updateFollowingCount(isUserFollow);
        }
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }


    private void visitBasicInfo(JSONObject data) {
        try {
            tx_name.setText(data.optString(RestUtils.TAG_USER_SALUTAION) + " " + data.optString(RestUtils.TAG_USER_FULL_NAME));
            if (data.getJSONArray(RestUtils.KEY_SPECIALISTS).length() > 0) {
                tx_speciality.setText(data.getJSONArray(RestUtils.KEY_SPECIALISTS).optString(0));
            }
            tx_subSpeciality.setText(data.optString(RestUtils.TAG_SUB_SPLTY));
            if (data.getJSONArray(RestUtils.TAG_PROFESSIONAL_HISTORY).length() > 0) {
                tx_workplace.setText(data.getJSONArray(RestUtils.TAG_PROFESSIONAL_HISTORY).getJSONObject(0).optString(RestUtils.TAG_WORKPLACE));
            }
            JSONArray socialInfoArray = new JSONArray();
            socialInfoArray = data.optJSONArray(RestUtils.TAG_SOCIAL_INFO);
            if (!socialInfoArray.getJSONObject(0).optString(RestUtils.TAG_ABOUT_ME).isEmpty()) {
                visit_usp_text.setVisibility(View.VISIBLE);
                visit_usp_text.setText("\"" + socialInfoArray.getJSONObject(0).optString(RestUtils.TAG_ABOUT_ME) + "\"");
            } else {
                visit_usp_text.setVisibility(View.GONE);
            }
            if (!socialInfoArray.getJSONObject(0).optString(RestUtils.KEY_SPECIFIC_ASK).isEmpty()) {
                visit_specific_ask_layout.setVisibility(View.VISIBLE);
                visit_specific_ask_text.setText(socialInfoArray.getJSONObject(0).optString(RestUtils.KEY_SPECIFIC_ASK));
            } else {
                visit_specific_ask_layout.setVisibility(View.GONE);
            }
            int experience = data.optInt("experience");
            if (experience != 999) {
                if (experience == 1) {
                    experience_lay.setVisibility(View.VISIBLE);
                    visit_over_all_experience.setText(experience + " Year");
                } else if (experience == 0) {
                    visit_over_all_experience.setText("0-1 Year");
                } else {
                    visit_over_all_experience.setText(experience + " Years");
                }
            } else {
                experience_lay.setVisibility(View.GONE);
            }


            if (data.has(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL) && !data.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL).isEmpty()) {
                profilePicUrl = data.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL).trim();
                AppUtil.loadImageUsingGlide(VisitOtherProfile.this, profilePicUrl, ig_visitor_pic, R.drawable.default_profilepic);
            } else if (!data.optString(RestUtils.TAG_PROFILE_PIC_NAME).equals("")) {
                try {
                    profile_pic_name = data.optString(RestUtils.TAG_PROFILE_PIC_NAME);
                    String image_response = new DownloadAsync(VisitOtherProfile.this).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), data.optString(RestUtils.TAG_PROFILE_PIC_NAME), "profile").get();
                    JSONObject jObject = new JSONObject(image_response);
                    if (jObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) && jObject.has(RestUtils.TAG_DATA)) {
                        JSONObject dataJson = jObject.optJSONObject(RestUtils.TAG_DATA);
                        String original_link = dataJson.optString(RestUtils.TAG_ORIGINAL_LINK);
                        new LoadImageFromUrl().executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), original_link, data.optString(RestUtils.TAG_PROFILE_PIC_NAME));

                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    ie.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ig_visitor_pic.setImageResource(R.drawable.default_profilepic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void aboutMe(JSONObject data) {
        try {
            userNameWithSalutation = data.optString(RestUtils.TAG_USER_SALUTAION) + " " + data.optString(RestUtils.TAG_USER_FULL_NAME);
            tv_aboutMe_heading.setText("About" + " " + userNameWithSalutation);
            JSONObject jaboutObj = new JSONObject();
            JSONArray dataJson = data.optJSONArray(RestUtils.TAG_SOCIAL_INFO);

            if (dataJson != null && dataJson.length() > 0) {
                jaboutObj = data.optJSONArray(RestUtils.TAG_SOCIAL_INFO).getJSONObject(0);
            }
            if (data.has(RestUtils.TAG_FOLLOW_STATUS)) {
                user_follow_btn.setVisibility(View.VISIBLE);
                if (data.optInt(RestUtils.TAG_FOLLOW_STATUS) == 1) {
                    user_follow_btn.setText("Unfollow");
                    user_follow_btn.setBackgroundResource(R.drawable.reject_button);
                    isUserFollow = true;
                } else {
                    user_follow_btn.setText("Follow");
                    user_follow_btn.setBackgroundResource(R.drawable.accept_button);
                    isUserFollow = false;
                }
            } else {
                user_follow_btn.setVisibility(View.GONE);
            }

            /** invited **/
            user_email_text_visit.setVisibility(View.GONE);
            user_contact_text_visit.setVisibility(View.GONE);
            userProfileURL.setVisibility(View.GONE);
            otherUserProfileURL = jaboutObj.optString(RestUtils.TAG_PROFILE_URL);
            if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_WEB_PAGE))) {
                tv_website.setText(jaboutObj.optString(RestUtils.TAG_WEB_PAGE));
                tv_website.setVisibility(View.VISIBLE);
            } else {
                tv_website.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_BLOG_PAGE))) {
                tv_blog.setText(jaboutObj.optString(RestUtils.TAG_BLOG_PAGE));
                tv_blog.setVisibility(View.VISIBLE);
            } else {
                tv_blog.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_FB_PAGE))) {
                tv_facebook.setText(jaboutObj.optString(RestUtils.TAG_FB_PAGE));
                tv_facebook.setVisibility(View.VISIBLE);
            } else {
                tv_facebook.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_LINKEDIN_PAGE))) {
                linkedInTxtVw.setVisibility(View.GONE);
            } else {
                linkedInTxtVw.setVisibility(View.VISIBLE);
                linkedInTxtVw.setText(jaboutObj.optString(RestUtils.TAG_LINKEDIN_PAGE));
            }
            if (TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_TWITTER_PAGE))) {
                twitterTxtVw.setVisibility(View.GONE);
            } else {
                twitterTxtVw.setVisibility(View.VISIBLE);
                twitterTxtVw.setText(jaboutObj.optString(RestUtils.TAG_TWITTER_PAGE));
            }
            if (TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_INSTAGRAM_PAGE))) {
                instaTxtVw.setVisibility(View.GONE);
            } else {
                instaTxtVw.setVisibility(View.VISIBLE);
                instaTxtVw.setText(jaboutObj.optString(RestUtils.TAG_INSTAGRAM_PAGE));
            }
            if (data.getInt(RestUtils.TAG_CONNECT_STATUS) == 2) {
                ig_invite.setVisibility(View.GONE);
                accept_reject_lay.setVisibility(View.VISIBLE);
                ig_message.setVisibility(View.GONE);
                tv_awaiting.setVisibility(View.GONE);
                connect_to_text.setVisibility(View.GONE);
                /**awaiting **/
            } else if (data.getInt(RestUtils.TAG_CONNECT_STATUS) == 1) {
                ig_invite.setVisibility(View.GONE);
                tv_awaiting.setVisibility(View.VISIBLE);
                connect_to_text.setVisibility(View.GONE);
                ig_message.setVisibility(View.GONE);
                accept_reject_lay.setVisibility(View.GONE);
                /**connect**/
            } else if (data.getInt(RestUtils.TAG_CONNECT_STATUS) == 3) {
                if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_PROFILE_URL))) {
                    userProfileURL.setText(jaboutObj.optString(RestUtils.TAG_PROFILE_URL));
                    userProfileURL.setVisibility(View.VISIBLE);
                } else {
                    userProfileURL.setVisibility(View.GONE);
                }
                tv_awaiting.setVisibility(View.GONE);
                ig_invite.setVisibility(View.GONE);
                ig_message.setVisibility(View.GONE);
                accept_reject_lay.setVisibility(View.GONE);
                connect_to_text.setVisibility(View.GONE);
                user_email_text_visit.setVisibility(View.VISIBLE);
                user_contact_text_visit.setVisibility(View.VISIBLE);
                connect_to_text.setVisibility(View.GONE);

                if (data.optString("cnt_email_vis") != null && data.optString("cnt_email_vis").equalsIgnoreCase("SHOW_ALL")) {
                    user_email_text_visit.setText(data.optString(RestUtils.TAG_CNT_EMAIL));

                } else {
                    user_email_text_visit.setText("Not shared");
                }

                if (data.optString("cnt_num_vis") != null && data.optString("cnt_num_vis").equalsIgnoreCase("SHOW_ALL")) {
                    user_contact_text_visit.setText(data.optString(RestUtils.TAG_CNT_NUM));

                } else {
                    user_contact_text_visit.setText("Not Shared");
                }

            } else if (data.getInt(RestUtils.TAG_CONNECT_STATUS) == 0) {
                user_email_text_visit.setVisibility(View.GONE);
                user_contact_text_visit.setVisibility(View.GONE);
                ig_message.setVisibility(View.GONE);
                ig_invite.setVisibility(View.VISIBLE);
                connect_to_text.setVisibility(View.VISIBLE);
                tv_awaiting.setVisibility(View.GONE);
                accept_reject_lay.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_WEB_PAGE))) {
                    tv_website.setText(jaboutObj.optString(RestUtils.TAG_WEB_PAGE));
                    tv_website.setVisibility(View.VISIBLE);
                } else {
                    tv_website.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_BLOG_PAGE))) {
                    tv_blog.setText(jaboutObj.optString(RestUtils.TAG_BLOG_PAGE));
                    tv_blog.setVisibility(View.VISIBLE);
                } else {
                    tv_blog.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_FB_PAGE))) {
                    tv_facebook.setText(jaboutObj.optString(RestUtils.TAG_FB_PAGE));
                    tv_facebook.setVisibility(View.VISIBLE);
                } else {
                    tv_facebook.setVisibility(View.GONE);
                }
                if (TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_LINKEDIN_PAGE))) {
                    linkedInTxtVw.setVisibility(View.GONE);
                } else {
                    linkedInTxtVw.setVisibility(View.VISIBLE);
                    linkedInTxtVw.setText(jaboutObj.optString(RestUtils.TAG_LINKEDIN_PAGE));
                }
                if (TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_TWITTER_PAGE))) {
                    twitterTxtVw.setVisibility(View.GONE);
                } else {
                    twitterTxtVw.setVisibility(View.VISIBLE);
                    twitterTxtVw.setText(jaboutObj.optString(RestUtils.TAG_TWITTER_PAGE));
                }
                if (TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_INSTAGRAM_PAGE))) {
                    instaTxtVw.setVisibility(View.GONE);
                } else {
                    instaTxtVw.setVisibility(View.VISIBLE);
                    instaTxtVw.setText(jaboutObj.optString(RestUtils.TAG_INSTAGRAM_PAGE));
                }
                if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_WEB_PAGE)) && (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_BLOG_PAGE)) || !TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_FB_PAGE)))) {
                    websiteSeparator.setVisibility(View.VISIBLE);
                } else {
                    websiteSeparator.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_BLOG_PAGE)) && !TextUtils.isEmpty(jaboutObj.optString(RestUtils.TAG_FB_PAGE))) {
                    blogSeparator.setVisibility(View.VISIBLE);
                } else {
                    blogSeparator.setVisibility(View.GONE);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void areaOFInterest(JSONObject dataObj) {
        Log.i(TAG, "updateAreaInfo()");

        JSONArray jAreaInterestArray = new JSONArray();

        final ArrayList<AreasOfInterest> completeList = new ArrayList<>();
        jAreaInterestArray = dataObj.optJSONArray(RestUtils.TAG_AREAS_OF_INTEREST);

        if (jAreaInterestArray != null && jAreaInterestArray.length() > 0) {
            rv_areaInterest.setVisibility(View.VISIBLE);
            int interestLen = jAreaInterestArray.length();
            areaInfoList.clear();

            for (int area = 0; area < interestLen; area++) {

                JSONObject pc = null;

                try {
                    pc = jAreaInterestArray.getJSONObject(area);
                    if (!pc.optString(RestUtils.TAG_AREA_OF_INTEREST).isEmpty()) {
                        areaInfoList.add(pc.optString(RestUtils.TAG_AREA_OF_INTEREST));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            Collections.sort(areaInfoList, new Comparator<String>() {
                @Override
                public int compare(String text1, String text2) {
                    return text1.compareToIgnoreCase(text2);
                }
            });
            mTagContainerLayout.setTags(areaInfoList);
        } else {
            rv_areaInterest.setVisibility(View.GONE);
        }

    }

    private void professionalData(JSONObject data) {
        Log.i(TAG, "updateProfessionalInfo()");

        JSONArray jProfessionalArray = new JSONArray();
        final ArrayList<ProfessionalInfo> completeList = new ArrayList<>();
        jProfessionalArray = data.optJSONArray(RestUtils.TAG_PROFESSIONAL_HISTORY);
        if (jProfessionalArray != null && jProfessionalArray.length() > 0) {
            rv_professional.setVisibility(View.VISIBLE);
            int plen = jProfessionalArray.length();
            for (int pr = 0; pr < plen; pr++) {
                JSONObject pc = null;
                try {
                    pc = jProfessionalArray.getJSONObject(pr);
                    if (pc.optBoolean(RestUtils.TAG_WORKING) == false) {
                        professionalInfo = new ProfessionalInfo();
                        professionalInfo.setDesignation(pc.optString(RestUtils.TAG_DESIGNATION));
                        professionalInfo.setStart_date(pc.optLong(RestUtils.TAG_FROMDATE));
                        professionalInfo.setStartTime(pc.optLong(RestUtils.TAG_FROM_TIME));
                        professionalInfo.setEnd_date(pc.optLong(RestUtils.TAG_TODATE));
                        professionalInfo.setEndTime(pc.optLong(RestUtils.TAG_TO_TIME));
                        professionalInfo.setAvailableDays(pc.optString(RestUtils.TAG_AVAILABLE_DAYS));
                        professionalInfo.setWorking_here(pc.optBoolean(RestUtils.TAG_WORKING));
                        professionalInfo.setWorkOptions(pc.optString(RestUtils.TAG_DEPARTMENT));
                        professionalInfo.setWorkplace(pc.optString(RestUtils.TAG_WORKPLACE));
                        professionalInfo.setShowOncard(pc.optBoolean(RestUtils.TAG_SHOW_ON_CARD));
                        professionalInfo.setLocation(pc.optString(RestUtils.TAG_LOCATION));
                        professional_history.add(professionalInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            completeList.clear();
            completeList.addAll(professional_history);
            final int size = completeList.size();
            if (size <= 2) {
                viewAllProfTxtVw.setVisibility(View.GONE);
            } else {
                viewAllProfTxtVw.setVisibility(View.VISIBLE);
                viewAllProfTxtVw.setText("View all");
            }
            if (size == 0)
                rv_professional.setVisibility(View.GONE);

            viewAllProfTxtVw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewAllProfTxtVw.getText().toString().equalsIgnoreCase("View all")) {
                        viewAllProfTxtVw.setText("Show less");
                        completeList.clear();
                        completeList.addAll(professional_history);
                        professionalAdapter.notifyDataSetChanged();
                    } else if (viewAllProfTxtVw.getText().toString().equalsIgnoreCase("Show less")) {
                        viewAllProfTxtVw.setText("View all");
                        completeList.clear();
                        completeList.addAll(professional_history);
                        ArrayList<ProfessionalInfo> tempList = (ArrayList<ProfessionalInfo>) getTempList(size, 2, professional_history);
                        completeList.clear();
                        completeList.addAll(tempList);
                        tempList.clear();
                        professionalAdapter.notifyDataSetChanged();
                    }
                }
            });
            ArrayList<ProfessionalInfo> tempList = (ArrayList<ProfessionalInfo>) getTempList(size, 2, professional_history);
            completeList.clear();
            completeList.addAll(tempList);
            tempList.clear();
            professionalAdapter = new ProfessionalAdapter(VisitOtherProfile.this, completeList, true);
            professional_listView.setAdapter(professionalAdapter);
        } else {
            rv_professional.setVisibility(View.GONE);
        }
    }

    private void updateAvailableInfo(JSONObject dataObj) {
        Log.i(TAG, "updateAvailableInfo()");
        JSONArray jProfessionalArray = new JSONArray();
        final ArrayList<ProfessionalInfo> completeList = new ArrayList<>();
        jProfessionalArray = dataObj.optJSONArray(RestUtils.TAG_PROFESSIONAL_HISTORY);
        if (jProfessionalArray != null && jProfessionalArray.length() > 0) {
            availableParentLayout.setVisibility(View.VISIBLE);
            int plen = jProfessionalArray.length();
            for (int pr = 0; pr < plen; pr++) {
                JSONObject pc = null;
                try {
                    pc = jProfessionalArray.getJSONObject(pr);
                    if (pc.optBoolean(RestUtils.TAG_WORKING) == true) {
                        availableInfo = new ProfessionalInfo();
                        availableInfo.setDesignation(pc.optString(RestUtils.TAG_DESIGNATION));
                        availableInfo.setStart_date(pc.optLong(RestUtils.TAG_FROMDATE));
                        availableInfo.setEnd_date(pc.optLong(RestUtils.TAG_TODATE));
                        JSONObject availableDaysObj = pc.optJSONObject(RestUtils.TAG_AVAILABLE_DAYS);
                        availableInfo.setStartTime(availableDaysObj.optLong(RestUtils.TAG_FROM_TIME));
                        availableInfo.setEndTime(availableDaysObj.optLong(RestUtils.TAG_TO_TIME));
                        availableInfo.setWorkOptions(availableDaysObj.optString(RestUtils.TAG_DEPARTMENT));

                        JSONArray weekDaysArray = availableDaysObj.optJSONArray(RestUtils.TAG_WEEK_OF_DAYS);
                        String convertedArr = weekDaysArray.toString().substring(1, weekDaysArray.toString().length() - 1);
                        availableInfo.setAvailableDays(convertedArr);
                        availableInfo.setWorking_here(pc.optBoolean(RestUtils.TAG_WORKING));
                        availableInfo.setWorkplace(pc.optString(RestUtils.TAG_WORKPLACE));
                        availableInfo.setShowOncard(pc.optBoolean(RestUtils.TAG_SHOW_ON_CARD));
                        availableInfo.setLocation(pc.optString(RestUtils.TAG_LOCATION));
                        availableList.add(availableInfo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            completeList.clear();
            completeList.addAll(availableList);
            final int size = completeList.size();
            if (size <= 2) {
                viewAllAvailability.setVisibility(View.GONE);
            } else {
                viewAllAvailability.setVisibility(View.VISIBLE);
                viewAllAvailability.setText("View all");
            }
            viewAllAvailability.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewAllAvailability.getText().toString().equalsIgnoreCase("View all")) {
                        viewAllAvailability.setText("Show less");
                        completeList.clear();
                        completeList.addAll(availableList);
                        availablityAdapter.notifyDataSetChanged();
                    } else if (viewAllAvailability.getText().toString().equalsIgnoreCase("Show less")) {
                        viewAllAvailability.setText("View all");
                        completeList.clear();
                        completeList.addAll(availableList);
                        ArrayList<ProfessionalInfo> tempList = (ArrayList<ProfessionalInfo>) getTempList(size, 2, availableList);
                        completeList.clear();
                        completeList.addAll(tempList);
                        tempList.clear();
                        availablityAdapter.notifyDataSetChanged();
                    }
                }
            });
            ArrayList<ProfessionalInfo> tempList = (ArrayList<ProfessionalInfo>) getTempList(size, 2, availableList);
            completeList.clear();
            completeList.addAll(tempList);
            tempList.clear();
            availablityAdapter = new AvailabilityAdapter(VisitOtherProfile.this, completeList);
            availabilityListView.setAdapter(availablityAdapter);
        } else {
            availableParentLayout.setVisibility(View.GONE);
        }

    }

    private <T> List<T> getTempList(int originalSize, int requiredSize, List<T> originalList) {
        Log.i(TAG, "getTempListOfSizeTwo()");
        ArrayList<T> tempList = new ArrayList<T>();
        for (int i = 0; i < originalSize; i++) {
            if (i == requiredSize)
                break;
            tempList.add(originalList.get(i));
        }
        return tempList;

    }

    private void academicsData(JSONObject data) {

        Log.i(TAG, "updateQualificationInfo()");

        JSONArray jacademicjArray = new JSONArray();
        final ArrayList<AcademicInfo> academicInfoscompleteList = new ArrayList<>();

        jacademicjArray = data.optJSONArray(RestUtils.TAG_ACADEMIC_HISTORY);
        if (jacademicjArray != null && jacademicjArray.length() > 0) {
            rv_academic.setVisibility(View.VISIBLE);
            int length = jacademicjArray.length();
            for (int a = 0; a < length; a++) {
                JSONObject ac = null;
                try {
                    ac = jacademicjArray.getJSONObject(a);
                    academicInfo = new AcademicInfo();
                    academicInfo.setCollege(ac.optString(RestUtils.TAG_COLLEGE));
                    academicInfo.setDegree(ac.optString(RestUtils.TAG_DEGREE));
                    academicInfo.setCurrently_pursuing(ac.optBoolean(RestUtils.TAG_CURRENTLY));
                    academicInfo.setPassing_year(ac.getInt(RestUtils.TAG_PASSING_YEAR));
                    academicInfo.setUniversity(ac.optString(RestUtils.TAG_UNIVERSITY));
                    academic_history.add(academicInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            academicInfoscompleteList.clear();
            academicInfoscompleteList.addAll(academic_history);
            final int size = academicInfoscompleteList.size();
            if (size <= 2) {
                viewAllQualTxtVw.setVisibility(View.GONE);
            } else {
                viewAllQualTxtVw.setVisibility(View.VISIBLE);
                viewAllQualTxtVw.setText("View all");
            }
            viewAllQualTxtVw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewAllQualTxtVw.getText().toString().equalsIgnoreCase("View all")) {
                        viewAllQualTxtVw.setText("Show less");
                        academicInfoscompleteList.clear();
                        academicInfoscompleteList.addAll(academic_history);
                        academicAdapter.notifyDataSetChanged();
                    } else if (viewAllQualTxtVw.getText().toString().equalsIgnoreCase("Show less")) {
                        viewAllQualTxtVw.setText("View all");
                        academicInfoscompleteList.clear();
                        academicInfoscompleteList.addAll(academic_history);
                        ArrayList<AcademicInfo> tempList = (ArrayList<AcademicInfo>) getTempList(size, 2, academic_history);
                        academicInfoscompleteList.clear();
                        academicInfoscompleteList.addAll(tempList);
                        tempList.clear();
                        academicAdapter.notifyDataSetChanged();
                    }
                }
            });
            ArrayList<AcademicInfo> tempList = (ArrayList<AcademicInfo>) getTempList(size, 2, academic_history);
            /*
             * Clear the list and add the temp list
             */
            academicInfoscompleteList.clear();
            academicInfoscompleteList.addAll(tempList);
            tempList.clear();
            academicAdapter = new AcademicAdapter(VisitOtherProfile.this, academicInfoscompleteList);
            academic_listView.setAdapter(academicAdapter);
        } else {
            rv_academic.setVisibility(View.GONE);
        }
    }


    private void publicationData(JSONObject dataObj) {
        final ArrayList<PublicationsInfo> completeListPublicationsInfo = new ArrayList<>();
        final ArrayList<PublicationsInfo> lessDataList = new ArrayList<PublicationsInfo>();
        JSONArray onlinePubArray = dataObj.optJSONArray(RestUtils.TAG_ONLINE_PUB_HISTORY);
        JSONArray printPubArray = dataObj.optJSONArray(RestUtils.TAG_PRINT_PUB_HISTORY);
        int onlinePubLen = 0;
        int printPubLen = 0;
        if (onlinePubArray != null && onlinePubArray.length() > 0 || printPubArray != null && printPubArray.length() > 0) {
            lv_publications.setVisibility(View.VISIBLE);
            if (printPubArray != null && printPubArray.length() > 0) {
                printPubLen = printPubArray.length();
                for (int p = 0; p < printPubLen; p++) {
                    try {
                        JSONObject printObj = printPubArray.getJSONObject(p);
                        PublicationsInfo publicationsInfo = new PublicationsInfo();
                        if (p == 0)
                            publicationsInfo.setFirstItem(true);
                        publicationsInfo.setTitle(printObj.optString(RestUtils.TAG_TITLE));
                        publicationsInfo.setAuthors(printObj.optString(RestUtils.TAG_AUTHORS));
                        publicationsInfo.setJournal(printObj.optString(RestUtils.TAG_JOURNAL));
                        publicationsInfo.setType(printObj.optString(RestUtils.TAG_PUB_TYPE));
                        publication_history.add(publicationsInfo);
                        if (p < 2)
                            lessDataList.add(publicationsInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            if (onlinePubArray != null && onlinePubArray.length() > 0) {
                onlinePubLen = onlinePubArray.length();
                for (int o = 0; o < onlinePubLen; o++) {
                    try {
                        JSONObject onlineObj = onlinePubArray.getJSONObject(o);
                        PublicationsInfo publicationsInfo = new PublicationsInfo();
                        if (o == 0)
                            publicationsInfo.setFirstItem(true);
                        publicationsInfo.setTitle(onlineObj.optString(RestUtils.TAG_TITLE));
                        publicationsInfo.setAuthors(onlineObj.optString(RestUtils.TAG_AUTHORS));
                        publicationsInfo.setJournal(onlineObj.optString(RestUtils.TAG_JOURNAL));
                        publicationsInfo.setWeb_page(onlineObj.optString(RestUtils.TAG_WEBPAGE_LINK));
                        publicationsInfo.setType(onlineObj.optString(RestUtils.TAG_PUB_TYPE));
                        publication_history.add(publicationsInfo);
                        if (o < 2)
                            lessDataList.add(publicationsInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            Log.i(TAG, "updatePublicationInfo()");
            completeListPublicationsInfo.clear();
            completeListPublicationsInfo.addAll(publication_history);
            if (onlinePubLen > 2 || printPubLen > 2) {
                viewAllPubTxtVw.setVisibility(View.VISIBLE);
                viewAllPubTxtVw.setText("View all");
            } else {
                viewAllPubTxtVw.setVisibility(View.GONE);
            }
            viewAllPubTxtVw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewAllPubTxtVw.getText().toString().equalsIgnoreCase("View all")) {
                        viewAllPubTxtVw.setText("Show less");
                        completeListPublicationsInfo.clear();
                        completeListPublicationsInfo.addAll(publication_history);
                        publicationsAdapter.notifyDataSetChanged();
                    } else if (viewAllPubTxtVw.getText().toString().equalsIgnoreCase("Show less")) {
                        viewAllPubTxtVw.setText("View all");
                        completeListPublicationsInfo.clear();
                        completeListPublicationsInfo.addAll(lessDataList);
                        publicationsAdapter.notifyDataSetChanged();
                    }
                }
            });
            /*
             * Clear the list and add the temp list
             */
            completeListPublicationsInfo.clear();
            completeListPublicationsInfo.addAll(lessDataList);
            publicationsAdapter = new PublicationsAdapter(VisitOtherProfile.this, completeListPublicationsInfo);
            publications_listView.setAdapter(publicationsAdapter);
        } else {
            lv_publications.setVisibility(View.GONE);
        }
    }

    private void awardsAndMembershipsData(JSONObject dataObj) {
        final ArrayList<ProfessionalMembershipInfo> completeListProfessionalMembershipInfo = new ArrayList<>();
        final ArrayList<ProfessionalMembershipInfo> lessDataList = new ArrayList<ProfessionalMembershipInfo>();

        JSONArray awards_history = dataObj.optJSONArray(RestUtils.TAG_AWARD_HISTORY);
        JSONArray membershipHistory = dataObj.optJSONArray(RestUtils.TAG_MEM_HISTORY);
        int awardsLen = 0;
        int membershipsLen = 0;
        if (awards_history != null && awards_history.length() > 0 || membershipHistory != null && membershipHistory.length() > 0) {
            rv_membership.setVisibility(View.VISIBLE);
            if (awards_history != null && awards_history.length() > 0) {
                awardsLen = awards_history.length();
                for (int m = 0; m < awardsLen; m++) {
                    JSONObject memjObj = null;
                    try {
                        memjObj = awards_history.getJSONObject(m);
                        ProfessionalMembershipInfo professionalMembershipInfo = new ProfessionalMembershipInfo();
                        professionalMembershipInfo.setAward_name(memjObj.optString(RestUtils.TAG_TITLE));
                        professionalMembershipInfo.setPresented_at(memjObj.optString(RestUtils.TAG_PRESENTED_AT));
                        professionalMembershipInfo.setAward_year(memjObj.optLong(RestUtils.TAG_YEAR));
                        professionalMembershipInfo.setType(memjObj.optString(RestUtils.TAG_TYPE));
                        professionalMembershipInfoList.add(professionalMembershipInfo);
                        if (m < 2)
                            lessDataList.add(professionalMembershipInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (membershipHistory != null && membershipHistory.length() > 0) {
                membershipsLen = membershipHistory.length();
                for (int m = 0; m < membershipsLen; m++) {
                    JSONObject memjObj = null;
                    try {
                        memjObj = membershipHistory.getJSONObject(m);
                        ProfessionalMembershipInfo professionalMembershipInfo = new ProfessionalMembershipInfo();
                        professionalMembershipInfo.setMembership_name(memjObj.optString(RestUtils.TAG_TITLE));
                        professionalMembershipInfo.setType(memjObj.optString(RestUtils.TAG_TYPE));
                        professionalMembershipInfoList.add(professionalMembershipInfo);
                        if (m < 2)
                            lessDataList.add(professionalMembershipInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            Log.i(TAG, "updateAwardMembershipInfo()");
            completeListProfessionalMembershipInfo.clear();
            completeListProfessionalMembershipInfo.addAll(professionalMembershipInfoList);
            if (awardsLen > 2 || membershipsLen > 2) {
                viewAllMembership.setVisibility(View.VISIBLE);
                viewAllMembership.setText("View all");
            } else {
                viewAllMembership.setVisibility(View.GONE);
            }
            viewAllMembership.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewAllMembership.getText().toString().equalsIgnoreCase("View all")) {
                        viewAllMembership.setText("Show less");
                        completeListProfessionalMembershipInfo.clear();
                        completeListProfessionalMembershipInfo.addAll(professionalMembershipInfoList);
                        membershipAdapter.notifyDataSetChanged();
                    } else if (viewAllMembership.getText().toString().equalsIgnoreCase("Show less")) {
                        viewAllMembership.setText("View all");
                        completeListProfessionalMembershipInfo.clear();
                        completeListProfessionalMembershipInfo.addAll(lessDataList);
                        membershipAdapter.notifyDataSetChanged();
                    }
                }
            });
            /*
             * Clear the list and add the temp list
             */
            completeListProfessionalMembershipInfo.clear();
            completeListProfessionalMembershipInfo.addAll(lessDataList);
            membershipAdapter = new MembershipAdapter(VisitOtherProfile.this, completeListProfessionalMembershipInfo);
            memberships_listView.setAdapter(membershipAdapter);
        } else {
            rv_membership.setVisibility(View.GONE);
        }
    }

    private void eventCalendarData(JSONObject dataObj) {
        final ArrayList<EventInfo> completeeventDataList = new ArrayList<EventInfo>();
        final ArrayList<EventInfo> lessDataList = new ArrayList<EventInfo>();
        JSONArray eventsHistory = dataObj.optJSONArray(RestUtils.TAG_EVENTS);

        int eventsLength = 0;
        rv_eventcalendar.setVisibility(View.VISIBLE);
        if (eventsHistory != null && eventsHistory.length() > 0) {
            eventsDefaultHeading.setVisibility(View.GONE);
            eventsLength = eventsHistory.length();
            for (int e = 0; e < eventsLength; e++) {
                JSONObject evejObj = null;
                try {
                    evejObj = eventsHistory.getJSONObject(e);
                    EventInfo eventInfo = new EventInfo();
                    eventInfo.setEventTitle(evejObj.optString(RestUtils.TAG_EVENT_NAME));
                    eventInfo.setLocation(evejObj.optString(RestUtils.TAG_EVENT_LOCATION));
                    eventInfo.setStartDate(evejObj.optLong(RestUtils.TAG_EVENT_START_DATE));
                    eventInfo.setEndDate(evejObj.optLong(RestUtils.TAG_EVENT_END_DATE));
                    eventInfoArrayList.add(eventInfo);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


            completeeventDataList.clear();
            completeeventDataList.addAll(eventInfoArrayList);
            final int size = completeeventDataList.size();
            if (size <= 2) {
                viewAllEvents.setVisibility(View.GONE);
            } else {
                viewAllEvents.setVisibility(View.VISIBLE);
                viewAllEvents.setText("View all");
            }
            viewAllEvents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewAllEvents.getText().toString().equalsIgnoreCase("View all")) {
                        viewAllEvents.setText("Show less");
                        completeeventDataList.clear();
                        completeeventDataList.addAll(eventInfoArrayList);
                        eventAdapter.notifyDataSetChanged();
                    } else if (viewAllEvents.getText().toString().equalsIgnoreCase("Show less")) {
                        viewAllEvents.setText("View all");
                        completeeventDataList.clear();
                        completeeventDataList.addAll(eventInfoArrayList);
                        ArrayList<EventInfo> tempList = (ArrayList<EventInfo>) getTempList(size, 2, eventInfoArrayList);
                        completeeventDataList.clear();
                        completeeventDataList.addAll(tempList);
                        tempList.clear();
                        eventAdapter.notifyDataSetChanged();
                    }
                }
            });

            ArrayList<EventInfo> tempList = (ArrayList<EventInfo>) getTempList(size, 2, eventInfoArrayList);
            /*
             * Clear the list and add the temp list
             */
            completeeventDataList.clear();
            completeeventDataList.addAll(tempList);
            tempList.clear();
            eventAdapter = new EventAdapter(VisitOtherProfile.this, completeeventDataList);
            events_listView.setAdapter(eventAdapter);
        } else {
            rv_eventcalendar.setVisibility(View.GONE);
        }

    }

    private void activityCountData(JSONObject data) {
        JSONObject activity_count = data.optJSONObject(RestUtils.TAG_ACTIVITY_COUNT);

        user_post_count_text.setText(AppUtil.suffixNumber(activity_count.optInt(RestUtils.TAG_FEED_COUNT)));
        user_followersCntTxt.setText(AppUtil.suffixNumber(activity_count.optInt(RestUtils.TAG_FOLLOWERS_COUNT)));
        user_followingCntTxt.setText(AppUtil.suffixNumber(activity_count.optInt(RestUtils.TAG_FOLLOWING_COUNT)));
        user_connect_count_text.setText(AppUtil.suffixNumber(activity_count.optInt(RestUtils.TAG_CONNECTS_COUNT)));
    }

    private void onlineAndPublicationsetup() {

        // PrintPublications list
        LayoutInflater publications_inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View publications_footer = publications_inflater.inflate(R.layout.professional_list_footer, null);
        final TextView publications_viewall_text = (TextView) publications_footer.findViewById(R.id.viewall_text);
//                            printpublications_lv.addFooterView(publications_footer);

        /** Print List **/
        final int print_publications_length = print_publications_history.size();
        if (print_publications_length != 0) {
            int i = 0;
            if (print_publications_length == 1) {
                i = 1;
            } else {
                i = 2;
            }
            printpublications_lv.addFooterView(publications_footer);
            printpublications_lv.setAdapter(null);
            publicationsAdapter01 = new PublicationsAdapter(VisitOtherProfile.this, print_publications_history.subList(0, i));
            publicationsAdapter = new PublicationsAdapter(VisitOtherProfile.this, print_publications_history);
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
            onlinepublications_lv.addFooterView(publications_footer);
            onlinepublications_lv.setAdapter(null);
            opublicationsAdapter01 = new PublicationsAdapter(VisitOtherProfile.this, online_publications_history.subList(0, i));
            opublicationsAdapter = new PublicationsAdapter(VisitOtherProfile.this, online_publications_history);
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
            // linear_lv.removeView(lv_publications);
            lv_publications.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
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
        if (isTaskRoot()) {
            if (!customBackButton) {
                customBackButton = false;
                logOnBackPressEvent();
            }
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
            startActivity(intent);
        }
        if (!customBackButton) {
            customBackButton = false;
            logOnBackPressEvent();
        }
        finish();
    }

    private void logOnBackPressEvent() {
        if (updatedContactInfo != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("OtherDocID", updatedContactInfo.getUUID());
                AppUtil.logUserUpShotEvent("OtherUserProfileDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initialize() {


        tx_name = _findViewById(R.id.visit_prof_name);
        //tx_degree = _findViewById(R.id.visit_prof_degree);
        tx_speciality = _findViewById(R.id.visit_prof_speciality);
        tx_subSpeciality = _findViewById(R.id.visit_prof_sub_speciality);
        tx_workplace = _findViewById(R.id.visit_prof_workplace);
        ig_visitor_pic = _findViewById(R.id.visit_prof_pic_img);
       /* tx_common_caserooms_count = _findViewById(R.id.visit_prof_caserooms);
        tx_common_groups_count = _findViewById(R.id.visit_prof_groups);*/
        professional_lv = _findViewById(R.id.visit_others_professional_list);
        tv_website = _findViewById(R.id.website_text);
        tv_facebook = _findViewById(R.id.facebook_text);
        tv_blog = _findViewById(R.id.blog_text);
        linkedInTxtVw = _findViewById(R.id.linkedIn_text);
        twitterTxtVw = _findViewById(R.id.twitter_text);
        instaTxtVw = _findViewById(R.id.insta_text);
        userProfileURL = _findViewById(R.id.doc_profile_url);
//        tv_about_me = _findViewById(R.id.about_text);
        academic_lv = _findViewById(R.id.visit_others_academic_list);
        printpublications_lv = _findViewById(R.id.visit_others_print_list);
        onlinepublications_lv = _findViewById(R.id.visit_others_online_list);
        awards_lv = _findViewById(R.id.visit_others_membership_list);
        memberships_lv = _findViewById(R.id.visit_others_membership_list);
        tx_print_pub_heading = _findViewById(R.id.print_publications_heading);
        tx_online_pub_heading = _findViewById(R.id.online_publications_heading);
        eventsDefaultHeading = (TextView) findViewById(R.id.events_heading);

        tv_aboutMe_heading = _findViewById(R.id.about_me_label);
        rv_aboutMe = _findViewById(R.id.about_me_layout);
        rv_professional = _findViewById(R.id.professional_layout);
        availableParentLayout = _findViewById(R.id.available_layout);
        rv_areaInterest = _findViewById(R.id.area_layout);
        rv_academic = _findViewById(R.id.academics_layout);
        lv_publications = _findViewById(R.id.publications_layout);
        rv_membership = _findViewById(R.id.membership_layout);
        rv_eventcalendar = _findViewById(R.id.event_layout);
        visit_profile_scroll = _findViewById(R.id.contentScrollView);
        //linear_lv = _findViewById(R.id.main_linear_layout);
        //ribbonCountLayout = (LinearLayout) findViewById(R.id.ribbonCountLayout);
        user_email_text_visit = _findViewById(R.id.user_email_text);
        user_contact_text_visit = _findViewById(R.id.user_contact_text);

        ig_invite = _findViewById(R.id.others_invite_btn);
        ig_message = _findViewById(R.id.others_message_btn);
//         ig_share                  = _findViewById(R.id.others_share_btn);

        user_follow_btn = (Button) findViewById(R.id.user_follow_radio_button);

        experience_lay = (RelativeLayout) findViewById(R.id.experience_lay);

        user_post_count_text = (TextView) findViewById(R.id.user_post_count_text);
        user_followersCntTxt = (TextView) findViewById(R.id.user_followers_count_text);
        user_followingCntTxt = (TextView) findViewById(R.id.user_following_count_text);
        user_connect_count_text = (TextView) findViewById(R.id.user_connect_count_text);

        user_post_count_text_lay = (LinearLayout) findViewById(R.id.user_post_count_text_lay);
        user_followersCntTxt_lay = (LinearLayout) findViewById(R.id.user_followers_count_text_lay);
        user_followingCntTxt_lay = (LinearLayout) findViewById(R.id.user_following_count_text_lay);
        user_connect_count_text_lay = (LinearLayout) findViewById(R.id.user_connect_count_text_lay);

        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
        interest_add_img_lay = (RelativeLayout) findViewById(R.id.interest_add_img_lay);
        area_of_interest_label = (TextView) findViewById(R.id.area_of_interest_label);


        tv_accept = _findViewById(R.id.accept_txt);
        tv_reject = _findViewById(R.id.reject_txt);
        tv_awaiting = _findViewById(R.id.txt_awaiting);
        connect_to_text = _findViewById(R.id.connect_to_text);
        accept_reject_lay = _findViewById(R.id.accept_reject_lay);
        visit_usp_text = _findViewById(R.id.visit_usp_text);

        visit_specific_ask_layout = _findViewById(R.id.visit_specific_ask_layout);
        visit_specific_ask_text = _findViewById(R.id.visit_specific_ask_text);

        centered_progressbar = _findViewById(R.id.centered_progressbar);

        visit_connect_count = _findViewById(R.id.visit_connect_count);
        visit_post_count = _findViewById(R.id.visit_post_count);


        appBarLayout = (ControllableAppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        aboutMeEditIcon = (ImageView) findViewById(R.id.aboutMeEditIcon);
        professionalAddIcon = (ImageView) findViewById(R.id.professional_add_img);
        qualificationAddIcon = (ImageView) findViewById(R.id.qualification_add_img);
        publicationAddIcon = (ImageView) findViewById(R.id.publication_add_img);
        awardsMembershipAddIcon = (ImageView) findViewById(R.id.awards_membership_add_img);
        eventAddIcon = findViewById(R.id.event_add_img);

        phoneVerifyLabel = (TextView) findViewById(R.id.verify_phone);
        emailVerifyLabel = (TextView) findViewById(R.id.verify_email_label);
//        about_profile_icon = (ImageView) findViewById(R.id.about_profile_icon);
//        add_about_label = (TextView) findViewById(R.id.add_about_label);
        academics_heading = (TextView) findViewById(R.id.academics_heading);
        add_publications_text = (TextView) findViewById(R.id.add_publications_heading);
        experienceHeadigTxt = findViewById(R.id.experience_label);
        add_awards_membership_text = (TextView) findViewById(R.id.add_awards_membership_heading);

        visit_over_all_experience = (TextView) findViewById(R.id.visit_over_all_experience);

        websiteSeparator = _findViewById(R.id.websiteSeparator);
        blogSeparator = _findViewById(R.id.blogSeparator);

        available_add_img_lay = _findViewById(R.id.available_add_img_lay);
        eventAddButtonLayout = _findViewById(R.id.event_add_img_lay);
        available_at_label = _findViewById(R.id.available_at_label);

        viewAllProfTxtVw = (TextView) findViewById(R.id.view_all_professional);
        viewAllAvailability = (TextView) findViewById(R.id.view_all_availability);
        viewAllQualTxtVw = (TextView) findViewById(R.id.view_all_qualifications);
        viewAllPubTxtVw = (TextView) findViewById(R.id.view_all_publications);
        viewAllMembership = (TextView) findViewById(R.id.view_all_awards_memberships);
        viewAllEvents = (TextView) findViewById(R.id.view_all_events);
        professional_listView = _findViewById(R.id.professional_list);
        availabilityListView = _findViewById(R.id.available_list);
        publications_listView = _findViewById(R.id.publications_print_list);
        academic_listView = _findViewById(R.id.qualification_list);
        memberships_listView = _findViewById(R.id.memberships_list);
        events_listView = _findViewById(R.id.events_list);
        aboutMeEditIcon.setVisibility(View.GONE);
        professionalAddIcon.setVisibility(View.GONE);
        qualificationAddIcon.setVisibility(View.GONE);
        publicationAddIcon.setVisibility(View.GONE);
        eventAddIcon.setVisibility(View.GONE);
        awardsMembershipAddIcon.setVisibility(View.GONE);
        //about_profile_icon.setVisibility(View.GONE);
        emailVerifyLabel.setVisibility(View.GONE);
        phoneVerifyLabel.setVisibility(View.GONE);
//        add_about_label.setVisibility(View.GONE);
        academics_heading.setVisibility(View.GONE);
        add_publications_text.setVisibility(View.GONE);
        experienceHeadigTxt.setVisibility(View.GONE);
        add_awards_membership_text.setVisibility(View.GONE);
        interest_add_img_lay.setVisibility(View.GONE);
        area_of_interest_label.setVisibility(View.GONE);
        available_add_img_lay.setVisibility(View.GONE);
        eventAddButtonLayout.setVisibility(View.GONE);

        available_at_label.setVisibility(View.GONE);

        user_email_text_visit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
        user_contact_text_visit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrRejectRequest("accept");
            }
        });


        tv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrRejectRequest("reject");
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;

            int previousVerticalOffset;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    previousVerticalOffset = verticalOffset;
                }
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    collapsingToolbar.setTitle(userNameWithSalutation);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                }
                if (previousVerticalOffset == verticalOffset) {

                } else if (previousVerticalOffset > verticalOffset) {
                    //AppUtil.slideDown(ribbonCountLayout);
                    previousVerticalOffset = verticalOffset;
                } else {
                    //AppUtil.slideUp(ribbonCountLayout);
                    previousVerticalOffset = verticalOffset;
                }
            }
        });
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heightPixels = (int) (getResources().getDisplayMetrics().heightPixels / 1.8);
                ig_visitor_pic.getLayoutParams().height = heightPixels;
                float heightDp = heightPixels;
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                lp.height = (int) heightDp;
            }
        });
        appBarLayout.setOnStateChangeListener(new ControllableAppBarLayout.OnStateChangeListener() {

            @Override
            public void onStateChange(ControllableAppBarLayout.State toolbarChange) {
                switch (toolbarChange) {

                    case COLLAPSED:
                        isScroll = false;
                        break;
                    case EXPANDED:
                        isScroll = true;
                        int heightPixels = (int) (getResources().getDisplayMetrics().heightPixels / 3.5);

                        ig_visitor_pic.getLayoutParams().height = heightPixels;
                        /*float heightDp = getResources().getDisplayMetrics().heightPixels / 2;
                        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                        lp.height = (int) heightDp;*/
                        scrollRange = -1;
                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();

                        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, heightPixels
                                , new int[]{0, 0}, 0);


                        appBarLayout.setOnStateChangeListener(null);
                        break;
                    case IDLE: // Just fired once between switching states
                        isScroll = false;
                        break;

                }
            }
        });


        visit_profile_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    //AppUtil.slideDown(ribbonCountLayout);
                }
                if (scrollY < oldScrollY) {
                    //AppUtil.slideUp(ribbonCountLayout);
                }

                if (scrollY == 0) {
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                }
            }
        });
        ig_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                } else {
                    if (updatedContactInfo != null) {
                        mLastClickTime = SystemClock.elapsedRealtime();
                        new ShowCard(VisitOtherProfile.this, updatedContactInfo).goToChatWindow("VisitOtherProfile");
                    }
                }
            }
        });

        ig_invite.setOnClickListener(v -> {
            if (updatedContactInfo != null) {
                Intent intent = new Intent(mContext, InviteRequestActivity.class);
                intent.putExtra("searchContactsInfo", updatedContactInfo);
                intent.putExtra("visit", "visit");
                launcherInviteResults.launch(intent);
            }
        });
        connect_to_text.setOnClickListener(v -> {
            if (updatedContactInfo != null) {
                Intent intent = new Intent(mContext, InviteRequestActivity.class);
                intent.putExtra("searchContactsInfo", updatedContactInfo);
                intent.putExtra("visit", "visit");
                launcherInviteResults.launch(intent);
            }
        });

        user_contact_text_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_contact_text_visit.getText().toString().isEmpty() || user_contact_text_visit.getText().toString().equalsIgnoreCase("Not shared")) {
                    return;
                }
                AppUtil.makePhoneCall(VisitOtherProfile.this, user_contact_text_visit.getText().toString());
            }
        });
        user_email_text_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_email_text_visit.getText().toString().isEmpty() || user_email_text_visit.getText().toString().equalsIgnoreCase("Not shared")) {
                    return;
                }
                AppUtil.sendEmail(VisitOtherProfile.this, user_email_text_visit.getText().toString(), "WhiteCoats", "");
            }
        });

    }

    private void acceptOrRejectRequest(String respStatus) {
        try {
            if (isConnectingToInternet()) {
                if (AppUtil.getUserVerifiedStatus() == 3) {
                    showProgress();
                    JSONObject object = new JSONObject();
                    object.put("from_doc_id", userid);
                    object.put("to_doc_id", otherdocId);
                    object.put("resp_status", respStatus);
                    String reqData = object.toString();
                    new VolleySinglePartStringRequest(VisitOtherProfile.this, Request.Method.POST, RestApiConstants.CONNECT_INVITE, reqData, "VISIT_OTHER_PROFILE_CONNECT_REQUEST", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            hideProgress();
                            if (successResponse != null && !successResponse.isEmpty()) {
                                try {
                                    JSONObject responseObj = new JSONObject(successResponse);
                                    if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                        if (responseObj.has("resp_status") && responseObj.optString("resp_status").equalsIgnoreCase("accept")) {
                                            tv_awaiting.setVisibility(View.GONE);
                                            ig_invite.setVisibility(View.GONE);
                                            ig_message.setVisibility(View.VISIBLE);
                                            accept_reject_lay.setVisibility(View.GONE);
                                            contactsInfo.setNetworkStatus("3");
                                            realmManager.insertMyContacts(realm, updatedContactInfo);
                                            realmManager.updateConnectsNotificationReadStatus(contactsInfo.getNotification_id());
                                        } else {
                                            tv_awaiting.setVisibility(View.GONE);
                                            ig_invite.setVisibility(View.VISIBLE);
                                            ig_message.setVisibility(View.GONE);
                                            accept_reject_lay.setVisibility(View.GONE);
                                        }
                                        //realmManager.deleteNetworkNotification(realm, contactsInfo.getNotification_id());
                                        realmManager.deleteNotification(realm, "" + otherdocId, RestUtils.TAG_DOC_ID);
                                        Intent intent = new Intent("NotificationCount");
                                        LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);
                                    } else if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR) && (responseObj.optInt(RestUtils.TAG_ERROR_CODE) == 108 || responseObj.optInt(RestUtils.TAG_ERROR_CODE) == 114 || responseObj.optInt(RestUtils.TAG_ERROR_CODE) == 1042)) {
                                        String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                                        if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                            errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                        }

                                        Toast.makeText(VisitOtherProfile.this, errorMsg, Toast.LENGTH_SHORT).show();
                                        if (responseObj.optInt(RestUtils.TAG_ERROR_CODE) == 1042) {
                                            finish();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            hideProgress();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(errorResponse);
                                Toast.makeText(VisitOtherProfile.this, "Error", Toast.LENGTH_SHORT).show();
                                String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                                if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(VisitOtherProfile.this, errorMsg, Toast.LENGTH_SHORT).show();
                                if (jsonObject.has(RestUtils.TAG_ERROR_CODE) && jsonObject.optInt(RestUtils.TAG_ERROR_CODE) == 1042) {
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }).sendSinglePartRequest();
                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(VisitOtherProfile.this, getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(VisitOtherProfile.this, getString(R.string.mca_uploaded_but_not_verified));
                }
            } else {
                hideProgress();
            }
        } catch (Exception e) {
            hideProgress();
            e.printStackTrace();
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
            if (updatedContactInfo != null) {
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    jsonObject.put("OtherDocID", updatedContactInfo.getUUID());
                    AppUtil.logUserUpShotEvent("OtherUserProfileBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            onBackPressed();
            return true;
        } else if (id == R.id.action_share) {
            shareOtherUserProfile();
            return true;
        } else if (id == R.id.ac_report_user) {
            BottomSheetDialogReportSpam bottomSheet = new BottomSheetDialogReportSpam();
            Bundle bundle = new Bundle();
            bundle.putInt("feedId", 0);
            bundle.putInt("docId", userid);
            bundle.putInt("otherDocId", otherDocId);
            bundle.putString("callingFrom", "ReportOtherProfileUser");
            bottomSheet.setArguments(bundle);
            bottomSheet.show(getSupportFragmentManager(),
                    "ModalBottomSheetReportSpam");
            return true;
        } else if (id == R.id.ac_remove_user) {
            removePopup();
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
                ig_invite.setVisibility(View.GONE);
                tv_awaiting.setVisibility(View.VISIBLE);
                connect_to_text.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareOtherUserProfile() {
        if (otherUserProfileURL == null || otherUserProfileURL.isEmpty()) {
            return;
        }
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_USER_ID, realmBasicInfo.getDoc_id());
            requestObj.put(RestUtils.TAG_SHARED_USER_ID, otherDocId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String prefixMsg = realmBasicInfo.getUser_salutation() + " " + realmBasicInfo.getFname() + " " + realmBasicInfo.getLname() + " has shared " + tx_name.getText().toString() + "'s profile. Visit";
        String suffixMsg = "to know more about " + tx_name.getText().toString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, prefixMsg + " " + otherUserProfileURL + " " + suffixMsg);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
        AppUtil.sendUserActionEventAPICall(realmBasicInfo.getDoc_id(), "ShareOtherUserProfile", requestObj, VisitOtherProfile.this);
    }

    public void removePopup() {
        JSONObject resObje = null;
        try {
            resObje = new JSONObject();
            resObje.put("user_id", userid);
            resObje.put("otherUserId", otherDocId);
            resObje.put("is_follow", false);
            resObje.put("is_remove", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Dialog dialog = new Dialog(VisitOtherProfile.this);
        dialog.setContentView(R.layout.report_spam_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        RelativeLayout bottomSheetCloseButton = dialog.findViewById(R.id.bottomSheetCloseButton);
        TextView headingText = dialog.findViewById(R.id.heading_text);
        TextView textBody = dialog.findViewById(R.id.text_body);
        LinearLayout llButton = dialog.findViewById(R.id.ll_button);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnRemoveUser = dialog.findViewById(R.id.btn_Remove_User);
        llButton.setVisibility(View.VISIBLE);
        headingText.setVisibility(View.GONE);
        textBody.setText("Are you sure you want to remove " + otherDocName + "?");
        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        JSONObject finalResObje = resObje;
        btnRemoveUser.setOnClickListener(view -> {
            new VolleySinglePartStringRequest(VisitOtherProfile.this, Request.Method.POST, RestApiConstants.USER_FOLLOW_REST_API, finalResObje.toString(), "UserReportFeed", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    try {
                        if (successResponse != null && !successResponse.isEmpty()) {
                            JSONObject responseObj = new JSONObject(successResponse);
                            if (responseObj.has(RestUtils.TAG_STATUS)) {
                                if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                    DashboardUpdatesFragment.dashboardRefreshOnSubscription = true;
                                    Toast.makeText(VisitOtherProfile.this, "User has been removed successfully", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {

                }
            }).sendSinglePartRequest();

        });

        bottomSheetCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
