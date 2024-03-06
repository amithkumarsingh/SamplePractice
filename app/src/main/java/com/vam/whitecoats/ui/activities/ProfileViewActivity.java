package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;

import com.android.volley.Request;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.soundcloud.android.crop.Crop;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.OtpService;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.OTP;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.AreasOfInterest;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.EventInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.core.realm.RealmAcademicInfo;
import com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmEventsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmProfessionalMembership;
import com.vam.whitecoats.core.realm.RealmPublications;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.AcademicAdapter;
import com.vam.whitecoats.ui.adapters.AvailabilityAdapter;
import com.vam.whitecoats.ui.adapters.EventAdapter;
import com.vam.whitecoats.ui.adapters.MembershipAdapter;
import com.vam.whitecoats.ui.adapters.ProfessionalAdapter;
import com.vam.whitecoats.ui.adapters.PublicationsAdapter;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.customviews.NonScrollListView;
import com.vam.whitecoats.ui.interfaces.OnOtpActionListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.ProfileUpdatedListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.ControllableAppBarLayout;
import com.vam.whitecoats.utils.ProfileUpdateCollectionManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleyMultipartStringRequest;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import io.realm.Realm;
import io.realm.RealmResults;


public class ProfileViewActivity extends BaseActionBarActivity implements OnOtpActionListener, ProfileUpdatedListener, UiUpdateListener {
    public static final String TAG = ProfileViewActivity.class.getSimpleName();
    private static final int UPDATE_PERSONAL_INFO = 101;
    private static final int UPDATE_ABOUT_ME_INFO = 102;
    private static final int UPDATE_PROFESSIONAL_INFO = 103;
    private static final int UPDATE_QUALIFICATION_INFO = 104;
    private static final int UPDATE_PUBLICATION_INFO = 105;
    private static final int UPDATE_AWARDS_MEMBERSHIP_INFO = 106;
    private static final int UPDATE_ALL = 107;
    private static final int UPDATE_EVENT_INFO = 108;
    private static final int UPDATE_AREA_INTEREST_INFO = 109;
    TextView userNameTxtVw, userPhoneNumTxtVw, userEmailTxtVw, specialityTxtVw, subSpecialityTxtVw, uspTxtVw, specificAskTxtVw, emailVerifyLabel, viewAllMembership;
    TextView qualificationDefaultHeading, publicationDefaultHeading, awardsDefaultHeading, eventsDefaultHeading, linkedInTxtVw, twitterTxtVw, instaTxtVw;
    private ImageView userProfilePicImageVw;
    TextView websiteTxtVw, blogTxtVw, facebookTxtVw, phoneVerifyLabel, viewAllProfTxtVw, viewAllEventsTxtVw, viewAllAvailabilityTxt, viewAllQualTxtVw, viewAllPubTxtVw, add_photo_text, post_count_text, connect_count_text, followersCntTxt, followingCntTxt;
    View websiteSeparator, blogSeparator, fbSeparator, linkedInSeparator, twitterSeparator, profileUrlSeparator;
    private RealmBasicInfo realmBasicInfo;
    //    LinearLayout myBookmarksButton,myPreferencesButton;
    private static boolean onBackPressed = false;

    NestedScrollView nestedScrollView;
    ControllableAppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbar;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;

    NonScrollListView professional_listView, academic_listView, publications_listView, memberships_listView, events_listView, availabilityListView;
    ArrayList<ProfessionalInfo> professionalInfoList;
    ArrayList<ProfessionalInfo> availableInfoList;
    ArrayList<AcademicInfo> qualificationInfoList;
    ArrayList<PublicationsInfo> publicationInfoList;
    ArrayList<ProfessionalMembershipInfo> membershipInfoList;
    ArrayList<EventInfo> eventCalInfoList;
    ProfessionalAdapter professionalAdapter;
    AcademicAdapter academicAdapter;
    PublicationsAdapter publicationsAdapter;
    MembershipAdapter membershipAdapter;
    EventAdapter eventAdapter;
    AvailabilityAdapter availabilityAdapter;

    private Realm realm;
    private RealmManager realmManager;

    //    BasicInfo basicInfo;
    ValidationUtils validationUtils;
    private ImageView noImage_over_lay;
    boolean isScroll = false;


    MarshMallowPermission marshMallowPermission;
    private Uri mCapturedImageURI = null;
    private static final int CAMERA_PIC_REQUEST = 1313;
    private static final int SELECT_FILE = 1;
    private static Uri picUri;
    String selectedImagePath = "", toolBarTitle = " ";
    File folder;
    final int CROP_PIC = 22;
    File myDirectory;
    private Bitmap myBitmap;
    private LinearLayout verifyProfileSection;
    private Menu mMenu;
    private TextView connect_to_text, verify_now_text, helpTextView, availableAtLabelTxt, experienceHelpTxt, userProfileURL;
    private RelativeLayout personalInfoEditIcon_lay, aboutMeEditIcon_lay, professional_add_img_lay, qualification_add_img_lay, publication_add_img_lay, awards_membership_add_img_lay,
            event_add_img_lay, availableAddIcon;
    private TagContainerLayout mTagContainerLayout;

    List<String> areaInfoList = new ArrayList<>();

    private RelativeLayout interest_add_img_lay;
    private LinearLayout post_count_text_lay, connect_count_text_lay, followers_count_text_lay, following_count_text_lay;
    private long lastDownloadId;
    private DownloadManager downloadManager;
    private TextView over_all_experience;
    private RelativeLayout experience_lay;
    private boolean customBackButton = false;
    private ActivityResultLauncher<Intent> launch_UPDATE_PERSONAL_INFO, launch_UPDATE_ABOUT_ME_INFO,
            launch_UPDATE_PROFESSIONAL_INFO, launch_UPDATE_QUALIFICATION_INFO, launch_UPDATE_PUBLICATION_INFO,
            launch_UPDATE_AWARDS_MEMBERSHIP_INFO, launch_UPDATE_EVENT_INFO, launch_UPDATE_AREA_INTEREST_INFO,
            launchGalleryResults, launchCameraResults;
    private CardView deleteAccountView;
    private TextView deleteAccountTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        validationUtils = new ValidationUtils(this);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScroll);
        appBarLayout = (ControllableAppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        personalInfoEditIcon_lay = (RelativeLayout) findViewById(R.id.personalInfoEditIcon_lay);
        aboutMeEditIcon_lay = (RelativeLayout) findViewById(R.id.aboutMeEditIcon_lay);
        professional_add_img_lay = (RelativeLayout) findViewById(R.id.professional_add_img_lay);
        event_add_img_lay = (RelativeLayout) findViewById(R.id.event_add_img_lay);
        qualification_add_img_lay = (RelativeLayout) findViewById(R.id.qualification_add_img_lay);
        publication_add_img_lay = (RelativeLayout) findViewById(R.id.publication_add_img_lay);
        awards_membership_add_img_lay = (RelativeLayout) findViewById(R.id.awards_membership_add_img_lay);
        over_all_experience = (TextView) findViewById(R.id.over_all_experience);
        experience_lay = (RelativeLayout) findViewById(R.id.experience_lay);
        availableAddIcon = findViewById(R.id.available_add_img_lay);
        userProfilePicImageVw = _findViewById(R.id.userProfilePicture);
        userNameTxtVw = _findViewById(R.id.name_text);
        specialityTxtVw = _findViewById(R.id.speciality_text);
        subSpecialityTxtVw = _findViewById(R.id.subSpecialityText);
        uspTxtVw = _findViewById(R.id.usp_text);
        helpTextView = _findViewById(R.id.area_of_interest_label);
        availableAtLabelTxt = _findViewById(R.id.available_at_label);
        experienceHelpTxt = _findViewById(R.id.experience_label);
        specificAskTxtVw = _findViewById(R.id.specific_ask_text);
        websiteSeparator = _findViewById(R.id.websiteSeparator);
        blogSeparator = _findViewById(R.id.blogSeparator);
        fbSeparator = _findViewById(R.id.fbSeparator);
        linkedInSeparator = _findViewById(R.id.linkedInSeparator);
        twitterSeparator = _findViewById(R.id.twitterSeparator);
        profileUrlSeparator = _findViewById(R.id.profileUrlSeparator);
        websiteTxtVw = _findViewById(R.id.website_text);
        blogTxtVw = _findViewById(R.id.blog_text);
        facebookTxtVw = _findViewById(R.id.facebook_text);
        linkedInTxtVw = _findViewById(R.id.linkedIn_text);
        twitterTxtVw = _findViewById(R.id.twitter_text);
        instaTxtVw = _findViewById(R.id.insta_text);
        userProfileURL = _findViewById(R.id.doc_profile_url);
        userEmailTxtVw = _findViewById(R.id.user_email_text);
        emailVerifyLabel = (TextView) findViewById(R.id.verify_email_label);
        userPhoneNumTxtVw = _findViewById(R.id.user_contact_text);
        phoneVerifyLabel = (TextView) findViewById(R.id.verify_phone);
        viewAllProfTxtVw = (TextView) findViewById(R.id.view_all_professional);
        viewAllAvailabilityTxt = (TextView) findViewById(R.id.view_all_availability);
        viewAllQualTxtVw = (TextView) findViewById(R.id.view_all_qualifications);
        viewAllPubTxtVw = (TextView) findViewById(R.id.view_all_publications);
        viewAllMembership = (TextView) findViewById(R.id.view_all_awards_memberships);
        viewAllEventsTxtVw = _findViewById(R.id.view_all_events);
        qualificationDefaultHeading = (TextView) findViewById(R.id.academics_heading);
        publicationDefaultHeading = (TextView) findViewById(R.id.add_publications_heading);
        awardsDefaultHeading = (TextView) findViewById(R.id.add_awards_membership_heading);
        eventsDefaultHeading = (TextView) findViewById(R.id.events_heading);
        professional_listView = _findViewById(R.id.professional_list);
        publications_listView = _findViewById(R.id.publications_print_list);
        availabilityListView = _findViewById(R.id.available_list);
        academic_listView = _findViewById(R.id.qualification_list);
        memberships_listView = _findViewById(R.id.memberships_list);
        events_listView = _findViewById(R.id.events_list);
        noImage_over_lay = (ImageView) findViewById(R.id.noImage_over_lay);
        add_photo_text = (TextView) findViewById(R.id.add_photo_text);
        post_count_text = (TextView) findViewById(R.id.post_count_text);
        followersCntTxt = (TextView) findViewById(R.id.followers_count_text);
        followingCntTxt = (TextView) findViewById(R.id.following_count_text);
        connect_count_text = (TextView) findViewById(R.id.connect_count_text);
        verifyProfileSection = (LinearLayout) findViewById(R.id.me_section_verify);
        verify_now_text = (TextView) findViewById(R.id.verify_now_text);
        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
        interest_add_img_lay = (RelativeLayout) findViewById(R.id.interest_add_img_lay);

        post_count_text_lay = (LinearLayout) findViewById(R.id.post_count_text_lay);
        followers_count_text_lay = (LinearLayout) findViewById(R.id.followers_count_text_lay);
        following_count_text_lay = (LinearLayout) findViewById(R.id.following_count_text_lay);
        connect_count_text_lay = (LinearLayout) findViewById(R.id.connect_count_text_lay);
        deleteAccountView = findViewById(R.id.delete_account_cv);
        deleteAccountTv = _findViewById(R.id.delete_account_tv);

        connect_to_text = _findViewById(R.id.connect_to_text);
        mySharedPref = new MySharedPref(this);

        CallbackCollectionManager.getInstance().registerListener(this);
        ProfileUpdateCollectionManager.registerListener(this);
        marshMallowPermission = new MarshMallowPermission(this);

        connect_to_text.setVisibility(View.GONE);


        final SpannableStringBuilder sb = new SpannableStringBuilder("Verify Now");

// Span to set text color to some RGB value
        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0, 167, 109));

// Span to make text bold
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

// Set the text color for first 4 characters
        sb.setSpan(fcs, 0, sb.toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

// make them also bold
        sb.setSpan(bss, 0, sb.toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        verify_now_text.append(sb);


        professionalInfoList = new ArrayList<ProfessionalInfo>();
        availableInfoList = new ArrayList<ProfessionalInfo>();
        qualificationInfoList = new ArrayList<AcademicInfo>();
        publicationInfoList = new ArrayList<PublicationsInfo>();
        membershipInfoList = new ArrayList<ProfessionalMembershipInfo>();
        eventCalInfoList = new ArrayList<EventInfo>();
        /*
         * Get device height and width
         * Divide 2/3 ratio
         */
        int deviceHeight = AppUtil.getDeviceHeight(this);
        int deviceWidth = AppUtil.getDeviceWidth(this);
        int oneFourthOfScreen = (int) (deviceHeight * 0.4);
        int oneSixthOfScreen = (int) (deviceHeight * 0.6);
        int scrollHeight = (deviceHeight - oneFourthOfScreen);
        int appBarHeight = (deviceHeight - scrollHeight);
        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launch_UPDATE_PERSONAL_INFO = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 101
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        setUpMyProfile(UPDATE_PERSONAL_INFO, resultCode, data);
                    }
                });
        launch_UPDATE_ABOUT_ME_INFO = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 102
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        setUpMyProfile(UPDATE_ABOUT_ME_INFO, resultCode, data);
                    }
                });
        launch_UPDATE_PROFESSIONAL_INFO = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 103
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        setUpMyProfile(UPDATE_PROFESSIONAL_INFO, resultCode, data);
                    }
                });
        launch_UPDATE_QUALIFICATION_INFO = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 104
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        setUpMyProfile(UPDATE_QUALIFICATION_INFO, resultCode, data);
                    }
                });
        launch_UPDATE_PUBLICATION_INFO = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 105
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        setUpMyProfile(UPDATE_PUBLICATION_INFO, resultCode, data);
                    }
                });
        launch_UPDATE_AWARDS_MEMBERSHIP_INFO = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 106
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        setUpMyProfile(UPDATE_AWARDS_MEMBERSHIP_INFO, resultCode, data);
                    }
                });
        launch_UPDATE_EVENT_INFO = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 108
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        setUpMyProfile(UPDATE_EVENT_INFO, resultCode, data);
                    }
                });
        launch_UPDATE_AREA_INTEREST_INFO = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 109
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        setUpMyProfile(UPDATE_AREA_INTEREST_INFO, resultCode, data);
                    }
                });
        launchGalleryResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 1
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        picUri = data.getData();
                        performCrop();

                    }
                });
        launchCameraResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 1313
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        if (mCapturedImageURI != null) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                            Date now = new Date();
                            final String fileName = "profile" + "_" + formatter.format(now) + ".jpg";
                            File f = new File(folder, fileName);
                            selectedImagePath = f.getAbsolutePath();
                            Crop.of(mCapturedImageURI, Uri.fromFile(f)).asSquare().start(this);
                        }

                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        if (mCapturedImageURI != null) {
                            getContentResolver().delete(mCapturedImageURI, null, null);
                        }
                    }
                });

        //End

        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heightPixels = (int) (getResources().getDisplayMetrics().heightPixels / 1.8);
                userProfilePicImageVw.getLayoutParams().height = heightPixels;
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

                        userProfilePicImageVw.getLayoutParams().height = heightPixels;
                        /*float heightDp = getResources().getDisplayMetrics().heightPixels / 2;
                        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                        lp.height = (int) heightDp;*/
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

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        loadBackdrop(realmBasicInfo);
        if (AppUtil.getUserVerifiedStatus() == 1) {
            verifyProfileSection.setVisibility(View.VISIBLE);
        } else {
            verifyProfileSection.setVisibility(View.GONE);
        }

        String deleteText = "Your profile and all the saved data will be permanently deleted. Read More";
        SpannableString spannableString = new SpannableString(deleteText);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse("https://www.whitecoats.com/privacypolicy"));
                startActivity(in);
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.app_green));
                textPaint.setUnderlineText(true);
            }
        };

        int startIndex = deleteText.indexOf("Read More");
        int endIndex = startIndex + "Read More".length();
        spannableString.setSpan(clickableSpan1, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        deleteAccountTv.setText(spannableString);
        deleteAccountTv.setMovementMethod(LinkMovementMethod.getInstance());


        deleteAccountView.setOnClickListener(view -> {

            Intent in = new Intent(this, AccountDeleteActivity.class);
            startActivity(in);
        });

        userPhoneNumTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPhoneNumTxtVw.getText().toString().isEmpty() || userPhoneNumTxtVw.getText().toString().equalsIgnoreCase("Not shared")) {
                    return;
                }
                AppUtil.makePhoneCall(ProfileViewActivity.this, userPhoneNumTxtVw.getText().toString());
            }
        });
        userEmailTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmailTxtVw.getText().toString().isEmpty() || userEmailTxtVw.getText().toString().equalsIgnoreCase("Not shared")) {
                    return;
                }
                AppUtil.sendEmail(ProfileViewActivity.this, userEmailTxtVw.getText().toString(), "WhiteCoats", "");
            }
        });

        verifyProfileSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileViewActivity.this, MCACardUploadActivity.class);
                startActivity(intent);
            }

        });

        phoneVerifyLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BasicInfo basicInfo = getBasicInfo();
                    JSONObject requestObj_OTP = new JSONObject();
                    requestObj_OTP.put("contact", basicInfo.getPhone_num());
                    requestObj_OTP.put("contactType", "PHONE");
                    requestObj_OTP.put("isProfileUpdate", true);
                    requestObj_OTP.put(RestUtils.TAG_DOC_ID, basicInfo.getDoc_id());
                    OtpService.getInstance().requestOtp(requestObj_OTP, ProfileViewActivity.this, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        emailVerifyLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet()) {

                    try {
                        BasicInfo basicInfo = getBasicInfo();
                        /** Create JSON Object **/
                        JSONObject object = new JSONObject();
                        object.put(RestUtils.TAG_USER_ID, basicInfo.getDoc_id());
                        object.put(RestUtils.TAG_CNT_EMAIL, basicInfo.getEmail());
                        object.put(RestUtils.TAG_CNT_NUM, basicInfo.getPhone_num());
                        List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                        /** ContactNumber Visibility **/
                        object.put(RestUtils.TAG_CNNTMUNVIS, basicInfo.getPhone_num_visibility());
                        /** email Visibility **/
                        object.put(RestUtils.TAG_CNNTEMAILVIS, basicInfo.getEmail_visibility());
                        JSONObject socialInfoObj = new JSONObject();
                        socialInfoObj.put(RestUtils.TAG_ABOUT_ME, basicInfo.getAbout_me() == null ? "" : basicInfo.getAbout_me());
                        socialInfoObj.put(RestUtils.TAG_BLOG_PAGE, basicInfo.getBlog_page() == null ? "" : basicInfo.getBlog_page());
                        socialInfoObj.put(RestUtils.TAG_WEB_PAGE, basicInfo.getWebsite() == null ? "" : basicInfo.getWebsite());
                        socialInfoObj.put(RestUtils.TAG_FB_PAGE, basicInfo.getFb_page() == null ? "" : basicInfo.getFb_page());
                        socialInfoObj.put(RestUtils.TAG_LINKEDIN_PAGE, basicInfo.getLinkedInPg() == null ? "" : basicInfo.getLinkedInPg());
                        socialInfoObj.put(RestUtils.TAG_TWITTER_PAGE, basicInfo.getTwitterPg() == null ? "" : basicInfo.getTwitterPg());
                        socialInfoObj.put(RestUtils.TAG_INSTAGRAM_PAGE, basicInfo.getInstagramPg() == null ? "" : basicInfo.getInstagramPg());
                        socialInfoObj.put(RestUtils.TAG_PROFILE_URL, basicInfo.getDocProfileURL() == null ? "" : basicInfo.getDocProfileURL());
                        object.put(RestUtils.TAG_SOCIAL_INFO, new JSONArray().put(socialInfoObj));
                        showProgress();
                        new VolleySinglePartStringRequest(ProfileViewActivity.this, Request.Method.POST, RestApiConstants.VERIFY_USER_EMAIL, object.toString(), "ABOUT_ME", new OnReceiveResponse() {
                            @Override
                            public void onSuccessResponse(String successResponse) {
                                hideProgress();
                                JSONObject responseObj = null;
                                try {
                                    responseObj = new JSONObject(successResponse);
                                    if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                        if (responseObj.has(RestUtils.TAG_DATA)) {
                                            JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                            BasicInfo userBasicInfo = new BasicInfo();
                                            userBasicInfo.setEmail(dataObj.optString(RestUtils.TAG_CNT_EMAIL));
                                            userBasicInfo.setPhone_num(dataObj.optString(RestUtils.TAG_CNT_NUM));
                                            List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                                            /** ContactNumber Visibility **/
                                            if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                                userBasicInfo.setPhone_num_visibility(0);
                                            } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                                userBasicInfo.setPhone_num_visibility(1);
                                            } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                                userBasicInfo.setPhone_num_visibility(2);
                                            }
                                            /** email Visibility **/

                                            if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                                userBasicInfo.setEmail_visibility(0);

                                            } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                                userBasicInfo.setEmail_visibility(1);

                                            } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                                userBasicInfo.setEmail_visibility(2);
                                            }
                                            if (dataObj.has(RestUtils.TAG_SOCIAL_INFO)) {
                                                JSONArray socialInfoArray = dataObj.optJSONArray(RestUtils.TAG_SOCIAL_INFO);
                                                if (socialInfoArray != null && socialInfoArray.length() > 0) {
                                                    JSONObject socialInfoObject = socialInfoArray.optJSONObject(0);
                                                    if (socialInfoObject != null) {
                                                        userBasicInfo.setAbout_me(socialInfoObject.optString(RestUtils.TAG_ABOUT_ME));
                                                        userBasicInfo.setBlog_page(socialInfoObject.optString(RestUtils.TAG_BLOG_PAGE));
                                                        userBasicInfo.setWebsite(socialInfoObject.optString(RestUtils.TAG_WEB_PAGE));
                                                        userBasicInfo.setFb_page(socialInfoObject.optString(RestUtils.TAG_FB_PAGE));
                                                        userBasicInfo.setLinkedInPg(socialInfoObject.optString(RestUtils.TAG_LINKEDIN_PAGE));
                                                        userBasicInfo.setTwitterPg(socialInfoObject.optString(RestUtils.TAG_TWITTER_PAGE));
                                                        userBasicInfo.setInstagramPg(socialInfoObject.optString(RestUtils.TAG_INSTAGRAM_PAGE));
                                                        userBasicInfo.setDocProfileURL(socialInfoObject.optString(RestUtils.TAG_PROFILE_URL));
                                                        userBasicInfo.setDocProfilePdfURL(socialInfoObject.optString(RestUtils.TAG_PROFILE_PDF_URL));
                                                    }
                                                }
                                            }
                                            if (dataObj.has(RestUtils.TAG_VERIFICATION_INFO)) {
                                                JSONObject verificationInfoObject = dataObj.optJSONObject(RestUtils.TAG_VERIFICATION_INFO);
                                                userBasicInfo.setPhone_verify(verificationInfoObject.optBoolean(RestUtils.TAG_PHONE_VERIFY));
                                                userBasicInfo.setEmail_verify(verificationInfoObject.optBoolean(RestUtils.TAG_EMAIL_VERIFY));
                                            }
                                            realmManager.updateAboutmeBasicInfo(realm, userBasicInfo);
                                            if (userBasicInfo.getEmail_verify() == false) {
                                                AlertDialog alertDialog = new AlertDialog.Builder(ProfileViewActivity.this).create();
                                                alertDialog.setTitle("Alert");
                                                alertDialog.setMessage("Verification link has been sent to your " + dataObj.optString(RestUtils.TAG_CNT_EMAIL));
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                alertDialog.show();
                                            }
                                        }
                                    } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                        String errorMsg = getResources().getString(R.string.unable_to_connect_server);
                                        if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                            errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                        }
                                        Toast.makeText(ProfileViewActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(String errorResponse) {
                                hideProgress();
                                Toast.makeText(ProfileViewActivity.this, getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();

                            }
                        }).sendSinglePartRequest();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        noImage_over_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        userProfilePicImageVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUrl = realmBasicInfo.getPic_url();
                String imagePath = realmBasicInfo.getProfile_pic_path();
                String profilePicPath = (imageUrl != null && !imageUrl.isEmpty()) ? imageUrl : (imagePath != null && !imagePath.equals("")) ? imagePath : null;
                if (profilePicPath != null && !profilePicPath.isEmpty()) {
                    Intent intent = new Intent(ProfileViewActivity.this, Profile_fullView.class);
                    intent.putExtra("profile_pic_path", realmBasicInfo.getProfile_pic_path());
                    intent.putExtra("basicInfo", getBasicInfo());
                    intent.putExtra("selectedDocId", realmBasicInfo.getDoc_id());
                    startActivity(intent);
                } else {
                    // Display image picker dialog
                }
            }
        });


        websiteTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = websiteTxtVw.getText().toString();
                AppUtil.openLinkInBrowser(url, ProfileViewActivity.this);
            }
        });
        blogTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = blogTxtVw.getText().toString();
                AppUtil.openLinkInBrowser(url, ProfileViewActivity.this);
            }
        });
        facebookTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.openFbLink(ProfileViewActivity.this, facebookTxtVw.getText().toString().toLowerCase());
            }
        });
        linkedInTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linkedInTxtVw.getText().toString();
                AppUtil.openLinkInBrowser(url, ProfileViewActivity.this);

            }
        });
        twitterTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = twitterTxtVw.getText().toString();
                AppUtil.openLinkInBrowser(url, ProfileViewActivity.this);

            }
        });
        instaTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = instaTxtVw.getText().toString();
                AppUtil.openLinkInBrowser(url, ProfileViewActivity.this);

            }
        });
        userProfileURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileViewActivity.this);
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
                                shareOwnProfile();
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

        personalInfoEditIcon_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ProfileViewActivity.this, BasicProfileActivity.class);
                in.putExtra("basicInfo", getBasicInfo());
                launch_UPDATE_PERSONAL_INFO.launch(in);
            }
        });

        aboutMeEditIcon_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ProfileViewActivity.this, AboutMeActivity.class);
                in.putExtra("basicInfo", getBasicInfo());
                launch_UPDATE_ABOUT_ME_INFO.launch(in);
            }
        });
        professional_add_img_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileViewActivity.this, ProfessionalDetActivity.class);
                launch_UPDATE_PROFESSIONAL_INFO.launch(intent);
            }
        });
        event_add_img_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileViewActivity.this, AddEventsActivity.class);
                launch_UPDATE_EVENT_INFO.launch(intent);
            }
        });
        qualification_add_img_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileViewActivity.this, AcademicActivity.class);
                launch_UPDATE_QUALIFICATION_INFO.launch(intent);
            }
        });
        publication_add_img_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileViewActivity.this, PublicationsActivity.class);
                launch_UPDATE_PUBLICATION_INFO.launch(intent);
            }
        });
        awards_membership_add_img_lay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileViewActivity.this, AwardsAndMemberships.class);
                launch_UPDATE_AWARDS_MEMBERSHIP_INFO.launch(intent);
            }
        });
        availableAddIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileViewActivity.this, ProfessionalDetActivity.class);
                launch_UPDATE_PROFESSIONAL_INFO.launch(intent);
            }
        });
        interest_add_img_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileViewActivity.this, AreaOfInterest.class);
                launch_UPDATE_AREA_INTEREST_INFO.launch(intent);
            }
        });
        post_count_text_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet()) {
                    Intent intent = new Intent(ProfileViewActivity.this, MyBookmarksActivity.class);
                    intent.putExtra("Navigation", "MyPosts");
                    intent.putExtra(RestUtils.TAG_OTHER_USER_ID, realmBasicInfo.getDoc_id());
                    startActivity(intent);
                }
            }
        });
        connect_count_text_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inviteIntent = new Intent(ProfileViewActivity.this, Network_MyConnects.class);
                startActivity(inviteIntent);
            }
        });

        followers_count_text_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewconnects = new Intent(ProfileViewActivity.this, ViewContactsActivity.class);
                viewconnects.putExtra(RestUtils.NAVIGATATE_FROM, "Followers");
                viewconnects.putExtra("doc_name", "");
                startActivity(viewconnects);
            }
        });
        following_count_text_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewconnects = new Intent(ProfileViewActivity.this, ViewContactsActivity.class);
                viewconnects.putExtra(RestUtils.NAVIGATATE_FROM, "Following");
                viewconnects.putExtra("doc_name", "");
                startActivity(viewconnects);
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    collapsingToolbar.setTitle(realmBasicInfo.getUser_salutation() + " " + realmBasicInfo.getFname() + " " + realmBasicInfo.getLname());
                    isShow = true;
                    showOption(R.id.action_verify);
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    hideOption(R.id.action_verify);
//                    setAnimation(userProfilePicImageVw,0);
                }

            }
        });

        createDirIfNotExists();
        setUpMyProfile(UPDATE_ALL, -1, null);
        /*
         * User Profile view service call
         */
        final JSONObject requestObj = new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_USER_ID, realmBasicInfo.getDoc_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (AppUtil.isConnectingToInternet(ProfileViewActivity.this)) {
            //showProgress();
            new VolleySinglePartStringRequest(ProfileViewActivity.this, Request.Method.POST, RestApiConstants.VIEW_USER_PROFILE, requestObj.toString(), "VIEW_USER_PROFILE", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
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
                                    Realm realm = Realm.getDefaultInstance();
                                    RealmManager realmManager = new RealmManager(ProfileViewActivity.this);
                                    BasicInfo basicInfo = new BasicInfo();
                                    basicInfo.setUserSalutation(dataObj.optString(RestUtils.TAG_USER_SALUTAION));
                                    basicInfo.setUserType(dataObj.optInt(RestUtils.TAG_USER_TYPE));
                                    basicInfo.setFname(dataObj.optString(RestUtils.TAG_USER_FIRST_NAME));
                                    basicInfo.setLname(dataObj.optString(RestUtils.TAG_USER_LAST_NAME));
                                    basicInfo.setEmail(dataObj.optString(RestUtils.TAG_CNT_EMAIL));
                                    basicInfo.setUserUUID(dataObj.optString(RestUtils.TAG_USER_UUID));
                                    basicInfo.setConnect_status(dataObj.getInt(RestUtils.TAG_CONNECT_STATUS));
                                    List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                                    /** ContactNumber Visibility **/
                                    if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfo.setPhone_num_visibility(0);
                                    } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfo.setPhone_num_visibility(1);
                                    } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfo.setPhone_num_visibility(2);
                                    }
                                    /** email Visibility **/

                                    if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfo.setEmail_visibility(0);

                                    } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfo.setEmail_visibility(1);

                                    } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfo.setEmail_visibility(2);
                                    }
                                    basicInfo.setPic_url(dataObj.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    basicInfo.setPhone_num(dataObj.optString(RestUtils.TAG_CNT_NUM));
                                    basicInfo.setSubSpeciality(dataObj.optString(RestUtils.TAG_SUB_SPLTY));
                                    basicInfo.setOverAllExperience(dataObj.optInt("experience"));
                                    if (dataObj.has(RestUtils.TAG_ACTIVITY_COUNT)) {
                                        JSONObject activityCountObject = dataObj.optJSONObject(RestUtils.TAG_ACTIVITY_COUNT);
                                        basicInfo.setTot_caserooms(activityCountObject.optInt(RestUtils.TAG_CASE_ROOM_COUNT));
                                        basicInfo.setTot_contacts(activityCountObject.optInt(RestUtils.TAG_CONNECTS_COUNT));
                                        basicInfo.setTot_groups(activityCountObject.optInt(RestUtils.TAG_GROUPS_COUNT));
                                        basicInfo.setLikeCount(activityCountObject.optInt(RestUtils.TAG_LIKES_COUNT));
                                        basicInfo.setShareCount(activityCountObject.optInt(RestUtils.TAG_SHARE_COUNT));
                                        basicInfo.setFeedCount(activityCountObject.optInt(RestUtils.TAG_FEED_COUNT));
                                        basicInfo.setFollowersCount(activityCountObject.optInt(RestUtils.TAG_FOLLOWERS_COUNT));
                                        basicInfo.setFollowingCount(activityCountObject.optInt(RestUtils.TAG_FOLLOWING_COUNT));
                                    }
                                    if (dataObj.has(RestUtils.TAG_VERIFICATION_INFO)) {
                                        JSONObject verificationInfoObject = dataObj.optJSONObject(RestUtils.TAG_VERIFICATION_INFO);
                                        basicInfo.setEmail_verify(verificationInfoObject.optBoolean(RestUtils.TAG_EMAIL_VERIFY));
                                        basicInfo.setPhone_verify(verificationInfoObject.optBoolean(RestUtils.TAG_PHONE_VERIFY));
                                    }
                                    if (dataObj.has(RestUtils.KEY_SPECIALISTS)) {
                                        StringBuilder specialityString = new StringBuilder();
                                        JSONArray specialistsArray = dataObj.optJSONArray(RestUtils.KEY_SPECIALISTS);
                                        int len = specialistsArray.length();
                                        for (int count = 0; count < len; count++) {
                                            specialityString.append(specialistsArray.optString(count)).append(",");
                                        }
                                        basicInfo.setSplty(specialityString.deleteCharAt(specialityString.length() - 1).toString());
                                    }
                                    if (dataObj.has(RestUtils.TAG_SOCIAL_INFO)) {
                                        JSONObject socialInfoObj = dataObj.optJSONArray(RestUtils.TAG_SOCIAL_INFO).optJSONObject(0);
                                        basicInfo.setAbout_me(socialInfoObj.optString(RestUtils.TAG_ABOUT_ME));
                                        basicInfo.setBlog_page(socialInfoObj.optString(RestUtils.TAG_BLOG_PAGE));
                                        basicInfo.setFb_page(socialInfoObj.optString(RestUtils.TAG_FB_PAGE));
                                        basicInfo.setWebsite(socialInfoObj.optString(RestUtils.TAG_WEB_PAGE));
                                        basicInfo.setLinkedInPg(socialInfoObj.optString(RestUtils.TAG_LINKEDIN_PAGE));
                                        basicInfo.setTwitterPg(socialInfoObj.optString(RestUtils.TAG_TWITTER_PAGE));
                                        basicInfo.setInstagramPg(socialInfoObj.optString(RestUtils.TAG_INSTAGRAM_PAGE));
                                        basicInfo.setSpecificAsk(socialInfoObj.optString(RestUtils.KEY_SPECIFIC_ASK));
                                        basicInfo.setDocProfileURL(socialInfoObj.optString(RestUtils.TAG_PROFILE_URL));
                                        basicInfo.setDocProfilePdfURL(socialInfoObj.optString(RestUtils.TAG_PROFILE_PDF_URL));
                                    }
                                    basicInfo.setProfile_pic_path(realmBasicInfo.getProfile_pic_path());
                                    realmManager.updateBasicInfo(realm, basicInfo);
                                    realmManager.updateAboutmeBasicInfo(realm, basicInfo);
                                    //clear old db
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<RealmAcademicInfo> rows = realm.where(RealmAcademicInfo.class).findAll();
                                            rows.deleteAllFromRealm();
                                        }
                                    });
                                    if (dataObj.has(RestUtils.TAG_ACADEMIC_HISTORY)) {
                                        JSONArray academicArray = dataObj.optJSONArray(RestUtils.TAG_ACADEMIC_HISTORY);
                                        int len = academicArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject academicInfoObj = academicArray.optJSONObject(i);
                                            AcademicInfo academicInfo = new AcademicInfo();
                                            academicInfo.setAcad_id(academicInfoObj.optInt(RestUtils.TAG_ACADEMIC_ID));
                                            academicInfo.setDegree(academicInfoObj.optString(RestUtils.TAG_DEGREE));
                                            academicInfo.setCollege(academicInfoObj.optString(RestUtils.TAG_COLLEGE));
                                            academicInfo.setUniversity(academicInfoObj.optString(RestUtils.TAG_UNIVERSITY));
                                            academicInfo.setPassing_year(academicInfoObj.optInt(RestUtils.TAG_PASSING_YEAR));
                                            academicInfo.setCurrently_pursuing(academicInfoObj.optBoolean(RestUtils.TAG_CURRENTLY));
                                            realmManager.insertAcademic(realm, academicInfo);
                                        }

                                    }
                                    //clear old db
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<RealmProfessionalInfo> rows = realm.where(RealmProfessionalInfo.class).findAll();
                                            rows.deleteAllFromRealm();
                                        }
                                    });
                                    if (dataObj.has(RestUtils.TAG_PROFESSIONAL_HISTORY)) {
                                        JSONArray professionalArray = dataObj.optJSONArray(RestUtils.TAG_PROFESSIONAL_HISTORY);
                                        int len = professionalArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject profInfoObj = professionalArray.optJSONObject(i);
                                            ProfessionalInfo professionalInfo = new ProfessionalInfo();
                                            professionalInfo.setProf_id(profInfoObj.optInt(RestUtils.TAG_PROF_ID));
                                            professionalInfo.setWorkplace(profInfoObj.optString(RestUtils.TAG_WORKPLACE));
                                            professionalInfo.setDesignation(profInfoObj.optString(RestUtils.TAG_DESIGNATION));
                                            professionalInfo.setLocation(profInfoObj.optString(RestUtils.TAG_LOCATION));
                                            professionalInfo.setStart_date(profInfoObj.optLong(RestUtils.TAG_FROMDATE));
                                            professionalInfo.setEnd_date(profInfoObj.optLong(RestUtils.TAG_TODATE));
                                            professionalInfo.setShowOncard(profInfoObj.optBoolean(RestUtils.TAG_SHOW_ON_CARD));
                                            professionalInfo.setWorking_here(profInfoObj.optBoolean(RestUtils.TAG_WORKING));
                                            if (profInfoObj.has(RestUtils.TAG_AVAILABLE_DAYS)) {
                                                JSONObject availableDaysObj = profInfoObj.optJSONObject(RestUtils.TAG_AVAILABLE_DAYS);
                                                JSONArray weekDaysArray = availableDaysObj.optJSONArray(RestUtils.TAG_WEEK_OF_DAYS);
                                                String convertedArr = weekDaysArray.toString().substring(1, weekDaysArray.toString().length() - 1);
                                                professionalInfo.setAvailableDays(convertedArr);
                                                professionalInfo.setStartTime(availableDaysObj.optLong(RestUtils.TAG_FROM_TIME));
                                                professionalInfo.setEndTime(availableDaysObj.optLong(RestUtils.TAG_TO_TIME));
                                                professionalInfo.setWorkOptions(availableDaysObj.optString(RestUtils.TAG_DEPARTMENT));
                                            }
                                            realmManager.insertProfession(realm, professionalInfo);
                                        }

                                    }
                                    //clear old db
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<RealmAreasOfInterestInfo> rows = realm.where(RealmAreasOfInterestInfo.class).findAll();
                                            rows.deleteAllFromRealm();
                                        }
                                    });
                                    if (dataObj.has(RestUtils.TAG_AREAS_OF_INTEREST)) {
                                        JSONArray areaOfInterestArray = dataObj.optJSONArray(RestUtils.TAG_AREAS_OF_INTEREST);
                                        int len = areaOfInterestArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject areaInfoObj = areaOfInterestArray.optJSONObject(i);
                                            AreasOfInterest areasOfInterest = new AreasOfInterest();
                                            areasOfInterest.setInterestId(areaInfoObj.optInt(RestUtils.TAG_INTEREST_ID));
                                            areasOfInterest.setInterestName(areaInfoObj.optString(RestUtils.TAG_AREA_OF_INTEREST));
                                            realmManager.insertAreaOfInterest(realm, areasOfInterest);
                                        }
                                    }

                                    //clear old db
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<RealmPublications> rows = realm.where(RealmPublications.class).equalTo("type", "online").findAll();
                                            rows.deleteAllFromRealm();
                                        }
                                    });
                                    if (dataObj.has(RestUtils.TAG_ONLINE_PUB_HISTORY)) {
                                        JSONArray onlinePubArray = dataObj.optJSONArray(RestUtils.TAG_ONLINE_PUB_HISTORY);
                                        int len = onlinePubArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject onlinePubObj = onlinePubArray.optJSONObject(i);
                                            PublicationsInfo publicationInfo = new PublicationsInfo();
                                            publicationInfo.setPub_id(onlinePubObj.optInt(RestUtils.TAG_ONLINE_PUB_ID));
                                            publicationInfo.setType(onlinePubObj.optString(RestUtils.TAG_PUB_TYPE));
                                            publicationInfo.setTitle(onlinePubObj.optString(RestUtils.TITLE));
                                            publicationInfo.setJournal(onlinePubObj.optString(RestUtils.TAG_JOURNAL));
                                            publicationInfo.setWeb_page(onlinePubObj.optString(RestUtils.TAG_WEBPAGE_LINK));
                                            publicationInfo.setAuthors(onlinePubObj.optString(RestUtils.TAG_AUTHORS));
                                            realmManager.insertPublicationsInfo(realm, publicationInfo);
                                        }

                                    }

                                    //clear old db
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<RealmPublications> rows = realm.where(RealmPublications.class).equalTo("type", "print").findAll();
                                            rows.deleteAllFromRealm();
                                        }
                                    });
                                    if (dataObj.has(RestUtils.TAG_PRINT_PUB_HISTORY)) {
                                        JSONArray printPubArray = dataObj.optJSONArray(RestUtils.TAG_PRINT_PUB_HISTORY);
                                        int len = printPubArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject printPubObj = printPubArray.optJSONObject(i);
                                            PublicationsInfo publicationInfo = new PublicationsInfo();
                                            publicationInfo.setPub_id(printPubObj.optInt(RestUtils.TAG_PRINT_PUB_ID));
                                            publicationInfo.setType(printPubObj.optString(RestUtils.TAG_PUB_TYPE));
                                            publicationInfo.setTitle(printPubObj.optString(RestUtils.TITLE));
                                            publicationInfo.setJournal(printPubObj.optString(RestUtils.TAG_JOURNAL));
                                            publicationInfo.setAuthors(printPubObj.optString(RestUtils.TAG_AUTHORS));
                                            realmManager.insertPublicationsInfo(realm, publicationInfo);
                                        }
                                    }

                                    //clear old db
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<RealmProfessionalMembership> rows = realm.where(RealmProfessionalMembership.class).equalTo("type", "award").findAll();
                                            rows.deleteAllFromRealm();
                                        }
                                    });
                                    if (dataObj.has(RestUtils.TAG_AWARD_HISTORY)) {
                                        JSONArray awardArray = dataObj.optJSONArray(RestUtils.TAG_AWARD_HISTORY);
                                        int len = awardArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject awardObj = awardArray.optJSONObject(i);
                                            ProfessionalMembershipInfo memberInfo = new ProfessionalMembershipInfo();
                                            memberInfo.setAward_id(awardObj.optInt(RestUtils.TAG_AWARD_ID));
                                            memberInfo.setPresented_at(awardObj.optString(RestUtils.TAG_PRESENTED_AT));
                                            memberInfo.setAward_name(awardObj.optString(RestUtils.TITLE));
                                            memberInfo.setAward_year(awardObj.optLong(RestUtils.TAG_YEAR));
                                            memberInfo.setType(awardObj.optString(RestUtils.TAG_TYPE));
                                            realmManager.insertProfessionalMemData(realm, memberInfo);
                                        }

                                    }

                                    //clear old db
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<RealmProfessionalMembership> rows = realm.where(RealmProfessionalMembership.class).equalTo("type", "membership").findAll();
                                            rows.deleteAllFromRealm();
                                        }
                                    });
                                    if (dataObj.has(RestUtils.TAG_MEM_HISTORY)) {
                                        JSONArray memArray = dataObj.optJSONArray(RestUtils.TAG_MEM_HISTORY);
                                        int len = memArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject memberObj = memArray.optJSONObject(i);
                                            ProfessionalMembershipInfo memberInfo = new ProfessionalMembershipInfo();
                                            memberInfo.setProf_mem_id(memberObj.optInt(RestUtils.TAG_MEM_ID));
                                            memberInfo.setMembership_name(memberObj.optString(RestUtils.TITLE));
                                            memberInfo.setType(memberObj.optString(RestUtils.TAG_TYPE));
                                            realmManager.insertProfessionalMemData(realm, memberInfo);
                                        }
                                    }

                                    //clear old db
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<RealmEventsInfo> rows = realm.where(RealmEventsInfo.class).findAll();
                                            rows.deleteAllFromRealm();
                                        }
                                    });
                                    if (dataObj.has(RestUtils.TAG_EVENTS)) {
                                        JSONArray eventCalendarArray = dataObj.optJSONArray(RestUtils.TAG_EVENTS);
                                        int len = eventCalendarArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject eventInfoObj = eventCalendarArray.optJSONObject(i);
                                            EventInfo eventInfo = new EventInfo();
                                            eventInfo.setEventId(eventInfoObj.optInt(RestUtils.TAG_EVENT_ID));
                                            eventInfo.setEventTitle(eventInfoObj.optString(RestUtils.TAG_EVENT_NAME));
                                            eventInfo.setLocation(eventInfoObj.optString(RestUtils.TAG_EVENT_LOCATION));
                                            eventInfo.setStartDate(eventInfoObj.optLong(RestUtils.TAG_EVENT_START_DATE));
                                            eventInfo.setEndDate(eventInfoObj.optLong(RestUtils.TAG_EVENT_END_DATE));
                                            realmManager.insertEventCalendar(realm, eventInfo);
                                        }

                                    }


                                    if (dataObj.has(RestUtils.TAG_VERIFICATION_INFO)) {
                                        JSONObject verificationObj = dataObj.optJSONObject(RestUtils.TAG_VERIFICATION_INFO);
                                        if (verificationObj.has(RestUtils.TAG_IS_USER_VERIFIED)) {
                                            mySharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, verificationObj.optInt(RestUtils.TAG_IS_USER_VERIFIED, 3));
                                        }
                                        if (verificationObj.has("isCommunityVerified")) {
                                            mySharedPref.savePref(MySharedPref.PREF_IS_COMMUNITY_VERIFIED, verificationObj.optBoolean("isCommunityVerified", true));
                                        }
                                        if (verificationObj.has("is_user_accessible")) {
                                            AppConstants.IS_USER_VERIFIED = verificationObj.optBoolean("is_user_accessible", false);
                                        }
                                    }

                                    /*
                                     * call setUpMyProfile(UPDATE_ALL)
                                     */
                                    setUpMyProfile(UPDATE_ALL, -1, null);
                                    for (ProfileUpdatedListener listener : ProfileUpdateCollectionManager.getRegisterListeners()) {
                                        listener.onProfileUpdate(basicInfo);
                                    }
                                    if (!realm.isClosed()) {
                                        realm.close();
                                    }
                                }
                                hideProgress();
                            }
                        } catch (JSONException e) {
                            hideProgress();
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.e(TAG, "onErrorResponse - " + errorResponse);
                    hideProgress();
                    if (errorResponse != null && !errorResponse.isEmpty()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(errorResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ProfileViewActivity.this, "Unable to connect server,please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }).sendSinglePartRequest();
        } else {
            setUpMyProfile(UPDATE_ALL, -1, null);
        }

    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileViewActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (!marshMallowPermission.requestPermissionForCamera(false)) {
                        if (AppConstants.neverAskAgain_Camera) {
                            alertDialog_Message();
                        }
                    } else {
                        cameraClick();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    if (!marshMallowPermission.requestPermissionForStorage(false)) {
                        if (AppConstants.neverAskAgain_Library) {
                            alertDialog_Message1();
                        }
                    } else {
                        chooseFromLibrary();
                    }
                }
            }
        });
        builder.show();
    }

    private void chooseFromLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launchGalleryResults.launch(Intent.createChooser(intent, "Select File"));
    }

    private void alertDialog_Message1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileViewActivity.this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.storage_deny_message));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void cameraClick() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            String fileName = "profile" + formatter.format(now) + ".jpg";
            //String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            launchCameraResults.launch(takePictureIntent);
        }
    }

    private void alertDialog_Message() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileViewActivity.this);
        builder.setCancelable(false);
        builder.setMessage(AppUtil.alert_CameraPermissionDeny_Message());
//        builder.setMessage(getString(R.string.camera_deny_message));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CROP_PIC) {
                try {
                    picUri = data.getData();
                    selectedImagePath = getPath(picUri, ProfileViewActivity.this);
                    String s1 = data.getDataString();
                    if (selectedImagePath == null && s1 != null) {
                        selectedImagePath = s1.replaceAll("file://", "");
                    }
                    if (selectedImagePath != null) {
                        File imgFiles = new File(selectedImagePath);
                        if (imgFiles.exists()) {
                            myBitmap = BitmapFactory.decodeFile(imgFiles.getAbsolutePath());
                            userProfilePicImageVw.setImageBitmap(myBitmap);
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            } else {
                setUpMyProfile(requestCode, resultCode, data);
            }
        }
    }

    private void handleCrop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri fileUri = Crop.getOutput(data);
            if (AppUtil.loadImageFromPath(fileUri.getPath()) != null) {
                try {
                    Bitmap imgBitmap = AppUtil.rotateImageIfRequired(AppUtil.loadImageFromPath(fileUri.getPath()), fileUri, ProfileViewActivity.this);
                    //Uri uri = AppUtil.getImageUri(Profile_fullView.this, imgBitmap);
                    //selectedImagePath=getPath(uri, Profile_fullView.this);
                    /*
                     * compress to specified percentage
                     */
                    imgBitmap = AppUtil.sampleResize(imgBitmap, 1536, 1152);
                    FileOutputStream out = new FileOutputStream(selectedImagePath);
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
                    //userProfilePicImageVw.setImageBitmap(imgBitmap);
                    updateProfilePic(imgBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Image not supported,choose another image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(data).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProfilePic(final Bitmap myBitmap) {
        JSONObject object = new JSONObject();
        String subSpeciality = realmBasicInfo.getSubSpeciality();
        if (subSpeciality == null)
            subSpeciality = "";
        try {
            object.put(RestUtils.TAG_IS_UPDATE, true);
            object.put(RestUtils.TAG_USER_ID, realmBasicInfo.getDoc_id());
            object.put(RestUtils.TAG_USER_FIRST_NAME, realmBasicInfo.getFname());
            object.put(RestUtils.TAG_USER_LAST_NAME, realmBasicInfo.getLname());
            object.put(RestUtils.TAG_USER_SALUTAION, realmBasicInfo.getUser_salutation());
            object.put(RestUtils.TAG_USER_TYPE_ID, 1);
            object.put(RestUtils.TAG_SUB_SPLTY, realmBasicInfo.getSubSpeciality());
            object.put(RestUtils.KEY_SPECIALITIES, new JSONArray().put(realmBasicInfo.getSplty()));
            object.put(RestUtils.KEY_SPECIALISTS, new JSONArray().put(subSpeciality));
            object.put("experience", realmBasicInfo.getOverAllExperience());
            showProgress();
            new VolleyMultipartStringRequest(ProfileViewActivity.this, Request.Method.POST, RestApiConstants.EDIT_PERSONAL_INFO, object.toString(), "BASIC_PROFILE_ACIVITY", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    hideProgress();
                    if (successResponse != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(successResponse);
                            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                BasicInfo basicInfo = new BasicInfo();
                                if (jsonObject.has(RestUtils.TAG_DATA)) {
                                    JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                                    if (data.has(RestUtils.TAG_PROFILE_PIC_NAME)) {
                                        basicInfo.setPic_name(data.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                    }
                                    basicInfo.setUserSalutation(data.optString(RestUtils.TAG_USER_SALUTAION));
                                    basicInfo.setUserType(data.optInt(RestUtils.TAG_USER_TYPE_ID));
                                    basicInfo.setFname(data.optString(RestUtils.TAG_USER_FIRST_NAME));
                                    basicInfo.setLname(data.optString(RestUtils.TAG_USER_LAST_NAME));
                                    basicInfo.setSubSpeciality(data.optString(RestUtils.TAG_SUB_SPLTY));
                                    basicInfo.setPic_url(data.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    if (data.has(RestUtils.KEY_SPECIALITIES)) {
                                        basicInfo.setSplty(data.getJSONArray(RestUtils.KEY_SPECIALITIES).optString(0));
                                    }
                                } else {
                                    basicInfo.setPic_name("");
                                }
                                basicInfo.setProfile_pic_path(selectedImagePath);
                                realmManager.updateBasicInfo(realm, basicInfo);
                                Toast.makeText(ProfileViewActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                //BasicProfileActivity.updateProfilePicOnResume=true;
                                //BasicProfileActivity.selectedImagePath=selectedImagePath;
                                if (realmBasicInfo.getPic_url() != null && !realmBasicInfo.getPic_url().isEmpty()) {
                                    AppUtil.invalidateAndLoadImage(getApplicationContext(), realmBasicInfo.getPic_url().trim(), userProfilePicImageVw);
                                    noImage_over_lay.setVisibility(View.GONE);
                                    add_photo_text.setVisibility(View.GONE);
                                } else if (myBitmap != null) {
                                    userProfilePicImageVw.setImageBitmap(myBitmap);
                                    noImage_over_lay.setVisibility(View.GONE);
                                    add_photo_text.setVisibility(View.GONE);
                                } else {
                                    userProfilePicImageVw.setImageResource(R.drawable.default_profilepic);
                                    add_photo_text.setVisibility(View.VISIBLE);
                                    noImage_over_lay.setVisibility(View.VISIBLE);
                                }
                                for (ProfileUpdatedListener listener : ProfileUpdateCollectionManager.getRegisterListeners()) {
                                    listener.onProfileUpdate(basicInfo);
                                }
                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                add_photo_text.setVisibility(View.VISIBLE);
                                noImage_over_lay.setVisibility(View.VISIBLE);
                                if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                    ShowServerErrorSimpleDialog("Error", getResources().getString(R.string.session_timedout));
                                } else {
                                    displayErrorScreen(successResponse);
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
                    userProfilePicImageVw.setImageResource(R.drawable.default_profilepic);
                    add_photo_text.setVisibility(View.VISIBLE);
                    noImage_over_lay.setVisibility(View.VISIBLE);
                    displayErrorScreen(errorResponse);

                }
            }).sendMultipartRequest(myBitmap, "file", "profilepic.png");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void performCrop() {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            final String fileName = "profile" + "_" + formatter.format(now) + ".jpg";
            File f = new File(folder, fileName);
            selectedImagePath = f.getAbsolutePath();
            Crop.of(picUri, Uri.fromFile(f)).asSquare().start(this);

        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(this, getResources().getString(R.string.device_not_support_crop), Toast.LENGTH_SHORT).show();
        }
    }

    private void createDirIfNotExists() {
        myDirectory = AppUtil.getExternalStoragePathFile(ProfileViewActivity.this, ".Whitecoats");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        folder = new File(myDirectory + "/Profile_Pic");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

    private void setUpMyProfile(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_PERSONAL_INFO) {
            updatePersonalInfo();
        } else if (requestCode == UPDATE_ABOUT_ME_INFO) {
            updateAboutInfo();
        } else if (requestCode == UPDATE_PROFESSIONAL_INFO) {
            updateProfessionalInfo();
            updateAvailableInfo();
        } else if (requestCode == UPDATE_QUALIFICATION_INFO) {
            updateQualificationInfo();
        } else if (requestCode == UPDATE_PUBLICATION_INFO) {
            updatePublicationInfo();
        } else if (requestCode == UPDATE_AWARDS_MEMBERSHIP_INFO) {
            updateAwardMembershipInfo();
        } else if (requestCode == UPDATE_AREA_INTEREST_INFO) {
            updateAreaInfo();
        } else if (requestCode == UPDATE_EVENT_INFO) {
            updateEventsInfo();
        } else if (requestCode == UPDATE_ALL) {
            updatePersonalInfo();
            updateAboutInfo();
            updateProfessionalInfo();
            updateQualificationInfo();
            updateAreaInfo();
            updatePublicationInfo();
            updateAwardMembershipInfo();
            updateEventsInfo();
            updateAvailableInfo();

        } else if (requestCode == CAMERA_PIC_REQUEST) {
            //performCrop();
            if (mCapturedImageURI != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                Date now = new Date();
                final String fileName = "profile" + "_" + formatter.format(now) + ".jpg";
                File f = new File(folder, fileName);
                selectedImagePath = f.getAbsolutePath();
                //selectedImagePath = getPath(mCapturedImageURI, MandatoryProfileInfo.this);
                Crop.of(mCapturedImageURI, Uri.fromFile(f)).asSquare().start(this);
                //performCrop();
            }

        } else if (requestCode == CROP_PIC) {
            try {
                picUri = data.getData();
                selectedImagePath = getPath(picUri, ProfileViewActivity.this);
                String s1 = data.getDataString();
                if (selectedImagePath == null && s1 != null) {
                    selectedImagePath = s1.replaceAll("file://", "");
                }
                if (selectedImagePath != null) {
                    File imgFiles = new File(selectedImagePath);
                    if (imgFiles.exists()) {
                        myBitmap = BitmapFactory.decodeFile(imgFiles.getAbsolutePath());
                        userProfilePicImageVw.setImageBitmap(myBitmap);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (requestCode == SELECT_FILE) {
            picUri = data.getData();
            performCrop();
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }

    }


    private void updatePersonalInfo() {
        Log.i(TAG, "updatePersonalInfo()");
        RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        loadBackdrop(realmBasicInfo); //load user profile picture
        toolBarTitle = realmBasicInfo.getUser_salutation() + " " + realmBasicInfo.getFname() + " " + realmBasicInfo.getLname();
        userNameTxtVw.setText(toolBarTitle);
        specialityTxtVw.setText(realmBasicInfo.getSplty());
        if (realmBasicInfo.getSubSpeciality() != null && !realmBasicInfo.getSubSpeciality().isEmpty()) {
            subSpecialityTxtVw.setVisibility(View.VISIBLE);
            subSpecialityTxtVw.setText(realmBasicInfo.getSubSpeciality());
        } else {
            subSpecialityTxtVw.setVisibility(View.GONE);
        }
        if (realmBasicInfo.getAbout_me() != null && !realmBasicInfo.getAbout_me().isEmpty()) {
            uspTxtVw.setText("\"" + realmBasicInfo.getAbout_me() + "\"");
        } else {
            uspTxtVw.setText("\"Introduce yourself! We all would like to know you better\"");
        }
        specificAskTxtVw.setText(realmBasicInfo.getSpecificAsk());

        if (realmBasicInfo.getOverAllExperience() != 999) {
            experience_lay.setVisibility(View.VISIBLE);
            if (realmBasicInfo.getOverAllExperience() == 1) {
                over_all_experience.setText(realmBasicInfo.getOverAllExperience() + " Year");
            } else if (realmBasicInfo.getOverAllExperience() == 0) {
                over_all_experience.setText("0-1 Year");
            } else {
                over_all_experience.setText(realmBasicInfo.getOverAllExperience() + " Years");
            }


        } else {
            experience_lay.setVisibility(View.GONE);
        }

        updateCount();

    }

    private void updateAboutInfo() {
        Log.i(TAG, "updateAboutInfo()");
        RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        String website = realmBasicInfo.getWebsite();
        String blogPage = realmBasicInfo.getBlog_page();
        String fbPage = realmBasicInfo.getFb_page();
        String linkedInPage = realmBasicInfo.getLinkedInPg();
        String twitterPage = realmBasicInfo.getTwitterPg();
        String instaPage = realmBasicInfo.getInstagramPg();
        String profileURL = realmBasicInfo.getDocProfileURL();


        if (realmBasicInfo.isEmailVerified()) {
            userEmailTxtVw.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, R.drawable.ic_verified, 0);
            emailVerifyLabel.setVisibility(View.GONE);
        } else {
            userEmailTxtVw.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
            emailVerifyLabel.setVisibility(View.VISIBLE);
        }
        userEmailTxtVw.setText(realmBasicInfo.getEmail());
        if (realmBasicInfo.isMobileVerified()) {
            userPhoneNumTxtVw.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, R.drawable.ic_verified, 0);
            phoneVerifyLabel.setVisibility(View.GONE);
        } else {
            userPhoneNumTxtVw.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);
            phoneVerifyLabel.setVisibility(View.VISIBLE);
        }
        if (!realmBasicInfo.getPhone_num().isEmpty()) {
            userPhoneNumTxtVw.setText(realmBasicInfo.getPhone_num());
        } else {
            userPhoneNumTxtVw.setText(R.string.add_contact_no);
        }
        if (!stringIsEmpty(website) && (!stringIsEmpty(blogPage) || !stringIsEmpty(fbPage))) {
            websiteSeparator.setVisibility(View.VISIBLE);
        } else {
            websiteSeparator.setVisibility(View.GONE);
        }
        if (!stringIsEmpty(blogPage) && !stringIsEmpty(fbPage)) {
            blogSeparator.setVisibility(View.VISIBLE);
        } else {
            blogSeparator.setVisibility(View.GONE);
        }
        if (!stringIsEmpty(fbPage) && !stringIsEmpty(linkedInPage)) {
            fbSeparator.setVisibility(View.VISIBLE);
        } else {
            fbSeparator.setVisibility(View.GONE);
        }
        if (!stringIsEmpty(linkedInPage) && !stringIsEmpty(twitterPage)) {
            linkedInSeparator.setVisibility(View.VISIBLE);
        } else {
            linkedInSeparator.setVisibility(View.GONE);
        }
        if (!stringIsEmpty(profileURL) && !stringIsEmpty(website)) {
            profileUrlSeparator.setVisibility(View.VISIBLE);
        } else {
            profileUrlSeparator.setVisibility(View.GONE);
        }

        if (!stringIsEmpty(twitterPage) && !stringIsEmpty(instaPage)) {
            twitterSeparator.setVisibility(View.VISIBLE);
        } else {
            twitterSeparator.setVisibility(View.GONE);
        }

        if (stringIsEmpty(profileURL)) {
            userProfileURL.setVisibility(View.GONE);
        } else {
            userProfileURL.setVisibility(View.VISIBLE);
            userProfileURL.setText(profileURL);
        }

        if (stringIsEmpty(website)) {
            websiteTxtVw.setVisibility(View.GONE);
        } else {
            websiteTxtVw.setVisibility(View.VISIBLE);
            websiteTxtVw.setText(website);
        }
        if (stringIsEmpty(blogPage)) {
            blogTxtVw.setVisibility(View.GONE);
        } else {
            blogTxtVw.setVisibility(View.VISIBLE);
            blogTxtVw.setText(blogPage);
        }
        if (stringIsEmpty(fbPage)) {
            facebookTxtVw.setVisibility(View.GONE);
        } else {
            facebookTxtVw.setVisibility(View.VISIBLE);
            facebookTxtVw.setText(fbPage);
        }
        if (stringIsEmpty(linkedInPage)) {
            linkedInTxtVw.setVisibility(View.GONE);
        } else {
            linkedInTxtVw.setVisibility(View.VISIBLE);
            linkedInTxtVw.setText(linkedInPage);
        }
        if (stringIsEmpty(twitterPage)) {
            twitterTxtVw.setVisibility(View.GONE);
        } else {
            twitterTxtVw.setVisibility(View.VISIBLE);
            twitterTxtVw.setText(twitterPage);
        }
        if (stringIsEmpty(instaPage)) {
            instaTxtVw.setVisibility(View.GONE);
        } else {
            instaTxtVw.setVisibility(View.VISIBLE);
            instaTxtVw.setText(instaPage);
        }
    }

    private void updateProfessionalInfo() {
        Log.i(TAG, "updateProfessionalInfo()");
        professionalInfoList.clear();
        professionalInfoList.addAll(realmManager.getProfessionalInfo(realm));
        final int size = professionalInfoList.size();
        if (size == 0)
            experienceHelpTxt.setVisibility(View.VISIBLE);
        else
            experienceHelpTxt.setVisibility(View.GONE);

        if (size <= 2) {
            viewAllProfTxtVw.setVisibility(View.GONE);
        } else {
            viewAllProfTxtVw.setVisibility(View.VISIBLE);
            viewAllProfTxtVw.setText("View all");
        }
        viewAllProfTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewAllProfTxtVw.getText().toString().equalsIgnoreCase("View all")) {
                    viewAllProfTxtVw.setText("Show less");
                    professionalInfoList.clear();
                    professionalInfoList.addAll(realmManager.getProfessionalInfo(realm));
                    professionalAdapter.notifyDataSetChanged();
                } else if (viewAllProfTxtVw.getText().toString().equalsIgnoreCase("Show less")) {
                    viewAllProfTxtVw.setText("View all");
                    professionalInfoList.clear();
                    professionalInfoList.addAll(realmManager.getProfessionalInfo(realm));
                    ArrayList<ProfessionalInfo> tempList = (ArrayList<ProfessionalInfo>) getTempList(size, 2, professionalInfoList);
                    professionalInfoList.clear();
                    professionalInfoList.addAll(tempList);
                    tempList.clear();
                    professionalAdapter.notifyDataSetChanged();
                }
            }
        });
        ArrayList<ProfessionalInfo> tempList = (ArrayList<ProfessionalInfo>) getTempList(size, 2, professionalInfoList);
        /*
         * Clear the list and add the temp list
         */
        professionalInfoList.clear();
        professionalInfoList.addAll(tempList);
        tempList.clear();
        professionalAdapter = new ProfessionalAdapter(ProfileViewActivity.this, professionalInfoList, true);
        professional_listView.setAdapter(professionalAdapter);
        professional_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(getApplicationContext(), ProfessionalDetActivity.class);
                in.putExtra("professionalInfo", professionalInfoList.get(i));
                launch_UPDATE_PROFESSIONAL_INFO.launch(in);
            }
        });
    }

    private void updateAvailableInfo() {
        Log.i(TAG, "updateAvailableInfo()");
        availableInfoList.clear();
        availableInfoList.addAll(realmManager.getProfessionalInfoOfWorkingHere(realm));
        final int size = availableInfoList.size();
        if (size == 0)
            availableAtLabelTxt.setVisibility(View.VISIBLE);
        else {
            availableAtLabelTxt.setVisibility(View.GONE);
        }
        if (size <= 2) {
            viewAllAvailabilityTxt.setVisibility(View.GONE);
        } else {
            viewAllAvailabilityTxt.setVisibility(View.VISIBLE);
            viewAllAvailabilityTxt.setText("View all");
        }
        viewAllAvailabilityTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewAllAvailabilityTxt.getText().toString().equalsIgnoreCase("View all")) {
                    viewAllAvailabilityTxt.setText("Show less");
                    availableInfoList.clear();
                    availableInfoList.addAll(realmManager.getProfessionalInfoOfWorkingHere(realm));
                    availabilityAdapter.notifyDataSetChanged();
                } else if (viewAllAvailabilityTxt.getText().toString().equalsIgnoreCase("Show less")) {
                    viewAllAvailabilityTxt.setText("View all");
                    availableInfoList.clear();
                    availableInfoList.addAll(realmManager.getProfessionalInfoOfWorkingHere(realm));
                    ArrayList<ProfessionalInfo> tempList = (ArrayList<ProfessionalInfo>) getTempList(size, 2, availableInfoList);
                    availableInfoList.clear();
                    availableInfoList.addAll(tempList);
                    tempList.clear();
                    availabilityAdapter.notifyDataSetChanged();
                }
            }
        });
        ArrayList<ProfessionalInfo> tempList = (ArrayList<ProfessionalInfo>) getTempList(size, 2, availableInfoList);
        /*
         * Clear the list and add the temp list
         */
        availableInfoList.clear();
        availableInfoList.addAll(tempList);
        tempList.clear();
        availabilityAdapter = new AvailabilityAdapter(ProfileViewActivity.this, availableInfoList);
        availabilityListView.setAdapter(availabilityAdapter);
        availabilityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(getApplicationContext(), ProfessionalDetActivity.class);
                in.putExtra("professionalInfo", availableInfoList.get(i));
                launch_UPDATE_PROFESSIONAL_INFO.launch(in);
            }
        });

    }

    private void updateEventsInfo() {
        Log.i(TAG, "updateEventsInfo()");
        eventCalInfoList.clear();
        eventCalInfoList.addAll(realmManager.getEventInfo(realm));
        final int size = eventCalInfoList.size();
        if (size == 0)
            eventsDefaultHeading.setVisibility(View.VISIBLE);
        else {
            eventsDefaultHeading.setVisibility(View.GONE);
        }
        if (size <= 2) {
            viewAllEventsTxtVw.setVisibility(View.GONE);
        } else {
            viewAllEventsTxtVw.setVisibility(View.VISIBLE);
            viewAllEventsTxtVw.setText("View all");
        }
        viewAllEventsTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewAllEventsTxtVw.getText().toString().equalsIgnoreCase("View all")) {
                    viewAllEventsTxtVw.setText("Show less");
                    eventCalInfoList.clear();
                    eventCalInfoList.addAll(realmManager.getEventInfo(realm));
                    eventAdapter.notifyDataSetChanged();
                } else if (viewAllEventsTxtVw.getText().toString().equalsIgnoreCase("Show less")) {
                    viewAllEventsTxtVw.setText("View all");
                    eventCalInfoList.clear();
                    eventCalInfoList.addAll(realmManager.getEventInfo(realm));
                    ArrayList<EventInfo> tempList = (ArrayList<EventInfo>) getTempList(size, 2, eventCalInfoList);
                    eventCalInfoList.clear();
                    eventCalInfoList.addAll(tempList);
                    tempList.clear();
                    eventAdapter.notifyDataSetChanged();
                }
            }
        });
        ArrayList<EventInfo> tempList = (ArrayList<EventInfo>) getTempList(size, 2, eventCalInfoList);
        /*
         * Clear the list and add the temp list
         */
        eventCalInfoList.clear();
        eventCalInfoList.addAll(tempList);
        tempList.clear();
        eventAdapter = new EventAdapter(ProfileViewActivity.this, eventCalInfoList);
        events_listView.setAdapter(eventAdapter);
        events_listView.setAdapter(eventAdapter);
        events_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AddEventsActivity.class);
                intent.putExtra("eventcalendarinfo", eventCalInfoList.get(i));
                launch_UPDATE_EVENT_INFO.launch(intent);
            }
        });

    }

    private void updateAreaInfo() {
        RealmResults<RealmAreasOfInterestInfo> areasList = realmManager.getAreasOfInterestList();
        areaInfoList.clear();
        for (RealmAreasOfInterestInfo interest : areasList) {
            areaInfoList.add(interest.getInterestName());
        }
        Collections.sort(areaInfoList, new Comparator<String>() {
            @Override
            public int compare(String text1, String text2) {
                return text1.compareToIgnoreCase(text2);
            }
        });
        if (areaInfoList.size() >= 20) {
            interest_add_img_lay.setVisibility(View.GONE);
        } else {
            interest_add_img_lay.setVisibility(View.VISIBLE);
        }
        mTagContainerLayout.setTags(areaInfoList);
        if (areaInfoList.size() >= 1) {
            helpTextView.setVisibility(View.GONE);
        } else {
            helpTextView.setVisibility(View.VISIBLE);

        }
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

            @Override
            public void onTagClick(int position, String text) {

                RealmResults<RealmAreasOfInterestInfo> interestIdResults = realmManager.getAreaOfInterestID(text);
                RealmAreasOfInterestInfo interestID = interestIdResults.get(0);
                Intent intent = new Intent(ProfileViewActivity.this, AreaOfInterest.class);
                intent.putExtra(RestUtils.TAG_INTEREST_ID, interestID.getInterestId());
                intent.putExtra("interest_name", interestID.getInterestName());
                launch_UPDATE_AREA_INTEREST_INFO.launch(intent);

            }

            @Override
            public void onTagLongClick(final int position, String text) {
                // ...
            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                // ...
            }


        });
    }


    private void updateQualificationInfo() {
        Log.i(TAG, "updateQualificationInfo()");
        qualificationInfoList.clear();
        qualificationInfoList.addAll(realmManager.getAcademicinfo(realm));
        final int size = qualificationInfoList.size();
        if (size == 0)
            qualificationDefaultHeading.setVisibility(View.VISIBLE);
        else
            qualificationDefaultHeading.setVisibility(View.GONE);
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
                    qualificationInfoList.clear();
                    qualificationInfoList.addAll(realmManager.getAcademicinfo(realm));
                    academicAdapter.notifyDataSetChanged();
                } else if (viewAllQualTxtVw.getText().toString().equalsIgnoreCase("Show less")) {
                    viewAllQualTxtVw.setText("View all");
                    qualificationInfoList.clear();
                    qualificationInfoList.addAll(realmManager.getAcademicinfo(realm));
                    ArrayList<AcademicInfo> tempList = (ArrayList<AcademicInfo>) getTempList(size, 2, qualificationInfoList);
                    qualificationInfoList.clear();
                    qualificationInfoList.addAll(tempList);
                    tempList.clear();
                    academicAdapter.notifyDataSetChanged();
                }
            }
        });
        ArrayList<AcademicInfo> tempList = (ArrayList<AcademicInfo>) getTempList(size, 2, qualificationInfoList);
        /*
         * Clear the list and add the temp list
         */
        qualificationInfoList.clear();
        qualificationInfoList.addAll(tempList);
        tempList.clear();
        academicAdapter = new AcademicAdapter(ProfileViewActivity.this, qualificationInfoList);
        academic_listView.setAdapter(academicAdapter);
        academic_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(ProfileViewActivity.this, AcademicActivity.class);
                in.putExtra("academicinfo", qualificationInfoList.get(i));
                launch_UPDATE_QUALIFICATION_INFO.launch(in);
            }
        });

    }

    private void updatePublicationInfo() {
        Log.i(TAG, "updatePublicationInfo()");
        publicationInfoList.clear();
        boolean showView = realmManager.getPublications(realm, false, publicationInfoList);
//        publicationInfoList.addAll();
        final int size = publicationInfoList.size();
        if (size == 0)
            publicationDefaultHeading.setVisibility(View.VISIBLE);
        else
            publicationDefaultHeading.setVisibility(View.GONE);
        if (showView == false) {
            viewAllPubTxtVw.setVisibility(View.GONE);
        } else {
            viewAllPubTxtVw.setVisibility(View.VISIBLE);
            viewAllPubTxtVw.setText("View all");
        }
        viewAllPubTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewAllPubTxtVw.getText().toString().equalsIgnoreCase("View all")) {
                    viewAllPubTxtVw.setText("Show less");
                    publicationInfoList.clear();
                    realmManager.getPublications(realm, false, publicationInfoList);
//                    publicationInfoList.addAll(); // all data
                    publicationsAdapter.notifyDataSetChanged();
                } else if (viewAllPubTxtVw.getText().toString().equalsIgnoreCase("Show less")) {
                    viewAllPubTxtVw.setText("View all");
                    publicationInfoList.clear();
                    realmManager.getPublications(realm, true, publicationInfoList);
//                    publicationInfoList.addAll(); // temp data
                    publicationsAdapter.notifyDataSetChanged();
                }
            }
        });
        /*
         * Clear the list and add the temp list
         */
        publicationInfoList.clear();
        realmManager.getPublications(realm, true, publicationInfoList);
//        publicationInfoList.addAll(); // temp data
        publicationsAdapter = new PublicationsAdapter(ProfileViewActivity.this, publicationInfoList);
        publications_listView.setAdapter(publicationsAdapter);
        publications_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(ProfileViewActivity.this, EditPublicationsActivity.class);
                in.putExtra("Edit_publicationsInfo", publicationInfoList.get(i));
                launch_UPDATE_PUBLICATION_INFO.launch(in);
            }
        });


    }

    private void updateAwardMembershipInfo() {
        Log.i(TAG, "updateAwardMembershipInfo()");
        membershipInfoList.clear();
        boolean showView = realmManager.getProfessionalMem(realm, false, membershipInfoList);
//        membershipInfoList.addAll();
        final int size = membershipInfoList.size();
        if (size == 0)
            awardsDefaultHeading.setVisibility(View.VISIBLE);
        else
            awardsDefaultHeading.setVisibility(View.GONE);
        if (showView == false) {
            viewAllMembership.setVisibility(View.GONE);
        } else {
            viewAllMembership.setVisibility(View.VISIBLE);
            viewAllMembership.setText("View all");
        }
        viewAllMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewAllMembership.getText().toString().equalsIgnoreCase("View all")) {
                    viewAllMembership.setText("Show less");
                    membershipInfoList.clear();
                    realmManager.getProfessionalMem(realm, false, membershipInfoList);
//                    membershipInfoList.addAll();
                    membershipAdapter.notifyDataSetChanged();
                } else if (viewAllMembership.getText().toString().equalsIgnoreCase("Show less")) {
                    viewAllMembership.setText("View all");
                    membershipInfoList.clear();
                    realmManager.getProfessionalMem(realm, true, membershipInfoList);
//                    membershipInfoList.addAll();
                    membershipAdapter.notifyDataSetChanged();
                }
            }
        });
        /*
         * Clear the list and add the temp list
         */
        membershipInfoList.clear();
        realmManager.getProfessionalMem(realm, true, membershipInfoList);
//        membershipInfoList.addAll();
        membershipAdapter = new MembershipAdapter(ProfileViewActivity.this, membershipInfoList);
        memberships_listView.setAdapter(membershipAdapter);
        membershipAdapter.notifyDataSetChanged();
        memberships_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(ProfileViewActivity.this, ProfessionalMemActivity.class);
                in.putExtra("membershipInfo", membershipInfoList.get(position));
                launch_UPDATE_AWARDS_MEMBERSHIP_INFO.launch(in);
            }
        });
    }

    private void updateCount() {
        BasicInfo basicInfo = getBasicInfo();
        post_count_text.setText(AppUtil.suffixNumber(basicInfo.getFeedCount()));
        followersCntTxt.setText(AppUtil.suffixNumber(basicInfo.getFollowersCount()));
        followingCntTxt.setText(AppUtil.suffixNumber(basicInfo.getFollowingCount()));

        /*Added the null check condition to avoid NullPointer Exception crash*/
        if (realm != null) {
            if (realmManager.getMyContactsDB(realm) != null) {
                if (!AppUtil.suffixNumber(realmManager.getMyContactsDB(realm).size()).isEmpty()) {
                    connect_count_text.setText(AppUtil.suffixNumber(realmManager.getMyContactsDB(realm).size()));
                }
            }
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


    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
        if (realmManager != null) {
            updateCount();
        }
    }

    private void loadBackdrop(RealmBasicInfo realmBasicInfo) {
        String image_url = realmBasicInfo.getPic_url();
        String imagePath = realmBasicInfo.getProfile_pic_path();
        if (!AppUtil.checkWriteExternalPermission(ProfileViewActivity.this)) {
            if (image_url != null && !image_url.isEmpty()) {
                userProfilePicImageVw.setVisibility(View.VISIBLE);
                noImage_over_lay.setVisibility(View.GONE);
                add_photo_text.setVisibility(View.GONE);
                AppUtil.invalidateAndLoadImage(getApplicationContext(), image_url.trim(), userProfilePicImageVw);

            } else {
                userProfilePicImageVw.setImageResource(R.drawable.default_profilepic);
            }
        } else if (image_url != null && !image_url.isEmpty()) {
            userProfilePicImageVw.setVisibility(View.VISIBLE);
            noImage_over_lay.setVisibility(View.GONE);
            add_photo_text.setVisibility(View.GONE);
            AppUtil.invalidateAndLoadImage(getApplicationContext(), image_url.trim(), userProfilePicImageVw);
            //Picasso.with(ProfileViewActivity.this).invalidate(image_url.trim());
        } else if (imagePath != null && !imagePath.equals("")) {
            File imgFile = new File(realmBasicInfo.getProfile_pic_path());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                if (myBitmap != null) {
                    userProfilePicImageVw.setVisibility(View.VISIBLE);
                    noImage_over_lay.setVisibility(View.GONE);
                    add_photo_text.setVisibility(View.GONE);
                    userProfilePicImageVw.setImageBitmap(myBitmap);
                } else {
                    userProfilePicImageVw.setImageResource(R.drawable.default_profilepic);
                    noImage_over_lay.setVisibility(View.VISIBLE);
                    add_photo_text.setVisibility(View.VISIBLE);
                }
            }
        } else {
            userProfilePicImageVw.setImageResource(R.drawable.default_profilepic);
            noImage_over_lay.setVisibility(View.VISIBLE);
            add_photo_text.setVisibility(View.VISIBLE);
        }
    }

    private BasicInfo getBasicInfo() {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setDoc_id(realmBasicInfo.getDoc_id());
        basicInfo.setFname(realmBasicInfo.getFname());
        basicInfo.setLname(realmBasicInfo.getLname());
        basicInfo.setEmail(realmBasicInfo.getEmail());
        basicInfo.setEmail_verify(realmBasicInfo.isEmailVerified());
        basicInfo.setEmail_visibility(realmBasicInfo.getEmail_visibility());
        basicInfo.setPhone_num(realmBasicInfo.getPhone_num());
        basicInfo.setPhone_num_visibility(realmBasicInfo.getPhone_num_visibility());
        basicInfo.setPhone_verify(realmBasicInfo.isMobileVerified());
        basicInfo.setSplty(realmBasicInfo.getSplty());
        basicInfo.setSubSpeciality(realmBasicInfo.getSubSpeciality());
        basicInfo.setSpecificAsk(realmBasicInfo.getSpecificAsk());
        basicInfo.setAbout_me(realmBasicInfo.getAbout_me());
        basicInfo.setWebsite(realmBasicInfo.getWebsite());
        basicInfo.setBlog_page(realmBasicInfo.getBlog_page());
        basicInfo.setFb_page(realmBasicInfo.getFb_page());
        basicInfo.setLinkedInPg(realmBasicInfo.getLinkedInPg());
        basicInfo.setTwitterPg(realmBasicInfo.getTwitterPg());
        basicInfo.setInstagramPg(realmBasicInfo.getInstagramPg());
        basicInfo.setPic_url(realmBasicInfo.getPic_url());
        basicInfo.setPic_name(realmBasicInfo.getPic_name());
        basicInfo.setProfile_pic_path(realmBasicInfo.getProfile_pic_path());
        basicInfo.setTot_caserooms(realmBasicInfo.getTot_caserooms());
        basicInfo.setTot_contacts(realmBasicInfo.getTot_contacts());
        basicInfo.setTot_groups(realmBasicInfo.getTot_groups());
        basicInfo.setUserType(realmBasicInfo.getUser_type_id());
        basicInfo.setUserSalutation(realmBasicInfo.getUser_salutation());
        basicInfo.setFeedCount(realmBasicInfo.getFeedCount());
        basicInfo.setLikeCount(realmBasicInfo.getLikesCount());
        basicInfo.setShareCount(realmBasicInfo.getShareCount());
        basicInfo.setCommentCount(realmBasicInfo.getCommentsCount());
        basicInfo.setFollowersCount(realmBasicInfo.getFollowersCount());
        basicInfo.setFollowingCount(realmBasicInfo.getFollowingCount());
        basicInfo.setDocProfileURL(realmBasicInfo.getDocProfileURL());
        basicInfo.setDocProfilePdfURL(realmBasicInfo.getDocProfilePdfURL());
        basicInfo.setOverAllExperience(realmBasicInfo.getOverAllExperience());
        basicInfo.setPhone_num_visibility(realmBasicInfo.getPhone_num_visibility());
        basicInfo.setEmail_visibility(realmBasicInfo.getEmail_visibility());

        return basicInfo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_me, menu);
        hideOption(R.id.action_verify);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            if (AppUtil.isConnectingToInternet(this)) {
                AppUtil.subscribeDeviceForNotifications(ProfileViewActivity.this, realmManager.getDoc_id(realm), "FCM", false, mySharedPref.getPrefsHelper().getPref(MyFcmListenerService.PROPERTY_REG_ID, ""));
                showProgress();
                new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.LOGOUT, "", "ME_ACTIVITY_LOGOUT", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(final String successResponse) {
                        /**
                         * Unsubscribe the user from push notification subscription
                         */
                        signOutUserAction(successResponse);
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        hideProgress();
                        displayErrorScreen(errorResponse);
                    }
                }).sendSinglePartRequest();
            }
            return true;

        } else if (id == R.id.action_terms_of_use) {
            Intent intentTerms = new Intent(getApplicationContext(), TermsOfServiceActivity.class);
            intentTerms.putExtra("NavigationFrom", "terms_of_use");
            startActivity(intentTerms);
            return true;

        } else if (id == R.id.action_help) {
            Intent intentsupport = new Intent(getApplicationContext(), ContactSupport.class);
            startActivity(intentsupport);
            return true;

        } else if (id == android.R.id.home) {
            customBackButton = true;
            BasicInfo basicInfo = getBasicInfo();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("UserProfileBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
        } else if (id == R.id.action_verify) {
            Intent intent = new Intent(ProfileViewActivity.this, MCACardUploadActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_share) {
            shareOwnProfile();
        } else if (id == R.id.action_download) {
            if (AppUtil.isConnectingToInternet(ProfileViewActivity.this)) {
                if (!isWriteStoragePermissionGranted()) {
                    if (AppConstants.neverAskAgain_Library) {
                        //alertDialog_Message1();
                        AppUtil.permissionNeverAskPrompt(ProfileViewActivity.this, AppUtil.alert_StoragePermissionDeny_Message());
                    }
                } else {
                    downloadProfilePdf();
                }
            } else {
                Toast.makeText(ProfileViewActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }


        }
        return true;
    }

    private void shareOwnProfile() {
        if (userProfileURL.getText().toString().trim().isEmpty()) {
            return;
        }
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_USER_ID, realmBasicInfo.getDoc_id());
            requestObj.put(RestUtils.TAG_SHARED_USER_ID, realmBasicInfo.getDoc_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String prefixMsg = realmBasicInfo.getUser_salutation() + " " + realmBasicInfo.getFname() + " " + realmBasicInfo.getLname() + " has shared his profile from Whitecoats. Visit profile by clicking";
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, prefixMsg + " " + userProfileURL.getText().toString());
        startActivity(Intent.createChooser(shareIntent, "Share via"));
        AppUtil.sendUserActionEventAPICall(realmBasicInfo.getDoc_id(), "ShareOwnProfile", requestObj, ProfileViewActivity.this);
    }

    private void signOutUserAction(String response) {
        Log.i(TAG, "signOutFromQB()");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                Log.i(TAG, "QBUsers.signOut successful : onSuccess()");
                hideProgress();
                /*
                 * Clear preferences data
                 */
                mySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_SESSION_TOKEN, "");
                mySharedPref.getPrefsHelper().savePref(MySharedPref.STAY_LOGGED_IN, false);
                /*
                 * Destroy QB Chat service
                 * Go to Login
                 */
                //QBLogin.chatService.destroy();
                //DocereeMobileAds.getInstance().clearUserData();
                Intent intentLogout = new Intent(getApplicationContext(), LoginActivity.class);
                //intentLogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLogout);
                finish();

            } else {
                hideProgress();
                displayErrorScreen(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean stringIsEmpty(String data) {
        if (data == null) {
            return true;
        }
        return data.trim().length() == 0;
    }

    @Override
    public void onOtpAction(OTP otp, String response_OTP) {
        Log.i(TAG, "onOtpAction() - " + otp);
        if (otp.equals(OTP.SUCCESS)) {
//            entered_OTP_Correct(requestObj);
            updateAboutMeData();
        } else if (otp.equals(OTP.CHANGE_MOB_NO)) {
            hideProgress();
        } else if (otp.equals(OTP.RESEND)) {
            hideProgress();
            /*
             * Method recursion call written in OtpService class.
             */
//        }else if(otp.equals(OTP.FAILURE)){
            /*
             *
             */
        }
    }

    @Override
    public void onBackPressed() {
        onBackPressed = true;
        if (isTaskRoot()) {
            if (!customBackButton) {
                customBackButton = false;
                BasicInfo basicInfo = getBasicInfo();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("UserProfileDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
            startActivity(intent);
        }
        if (!customBackButton) {
            customBackButton = false;
            BasicInfo basicInfo = getBasicInfo();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("UserProfileDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (CallbackCollectionManager.getInstance().getRegisterListeners().contains(this)) {
            CallbackCollectionManager.getInstance().removeListener(this);
        }
        if (ProfileUpdateCollectionManager.getRegisterListeners().contains(this)) {
            ProfileUpdateCollectionManager.removeListener(this);
        }
        unregisterReceiver(onComplete);
        toolBarTitle = null;
        if (!realm.isClosed())
            realm.close();
    }

    public String getPath(Uri uri, Activity activity) {
        try {
            String[] projection = {MediaStore.MediaColumns.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void hideOption(int id) {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(id);
            item.setVisible(false);
        }
    }

    private void showOption(int id) {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(id);
            if (AppUtil.getUserVerifiedStatus() == 1) {
                item.setVisible(true);
            }
        }
    }

    // slide the view from below itself to the current position


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.CAMERA_PERMISSION_REQUEST_CODE:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                boolean camera = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    perms.put(Manifest.permission.READ_MEDIA_IMAGES, PackageManager.PERMISSION_GRANTED);
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    boolean media = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                        createDirIfNotExists();
                        cameraClick();
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                        if (!camera && !media) {
                            AppConstants.neverAskAgain_Camera = true;
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !media) {
                        AppConstants.neverAskAgain_Library = true;
                    }
                    if (perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED && !camera) {
                        AppConstants.neverAskAgain_Camera = true;
                    }
                } else {
                    perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    boolean media = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        createDirIfNotExists();
                        cameraClick();
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        if (!camera && !media) {
                            AppConstants.neverAskAgain_Camera = true;
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !media) {
                        AppConstants.neverAskAgain_Library = true;
                    }
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && !camera) {
                        AppConstants.neverAskAgain_Camera = true;
                    }
                }
                break;
            case PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createDirIfNotExists();
                    chooseFromLibrary();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    } else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                }
                break;
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //createDirIfNotExists();
                    //chooseFromLibrary();
                    downloadProfilePdf();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    } else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void downloadProfilePdf() {
        if (realmBasicInfo.getDocProfilePdfURL() != null && !realmBasicInfo.getDocProfilePdfURL().isEmpty()) {
            //Toast.makeText(ProfileViewActivity.this,"Started downloading",Toast.LENGTH_SHORT).show();
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri profilePdfUri = Uri.parse(realmBasicInfo.getDocProfilePdfURL());
            DownloadManager.Request request = new DownloadManager.Request(profilePdfUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setTitle(profilePdfUri.getLastPathSegment());
            request.setDescription("Downloading...");
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, profilePdfUri.getLastPathSegment());
            lastDownloadId = downloadManager.enqueue(request);
        }
    }

    @Override
    public void onProfileUpdate(BasicInfo basicInfo) {
        RealmBasicInfo realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        loadBackdrop(realmBasicInfo);
    }

    @Override
    public void updateUI(int feedId, JSONObject socialInteractionObj) {

    }

    @Override
    public void notifyUIWithNewData(JSONObject newUpdate) {

    }

    @Override
    public void notifyUIWithDeleteFeed(int feedId, JSONObject deletedFeedObj) {
        if (realmManager != null && realmBasicInfo != null) {
            if (realmBasicInfo.getFeedCount() > 0) {
                realmManager.updateUserFeedCount(realmBasicInfo.getFeedCount() - 1);
                updateCount();
            }

        }

    }

    @Override
    public void onBookmark(boolean isBookmarked, int feedID, boolean isAutoRefresh, JSONObject socialInteractionObj) {

    }

    @Override
    public void notifyUIWithFeedSurveyResponse(int feedId, JSONObject surveyResponse) {

    }

    @Override
    public void notifyUIWithFeedWebinarResponse(int feedId, JSONObject webinarRegisterResponse) {

    }

    @Override
    public void notifyUIWithJobApplyStatus(int feedId, JSONObject jobApplyResponse) {

    }


    private void updateAboutMeData() {
        if (isConnectingToInternet()) {
            try {
                BasicInfo basicInfo = getBasicInfo();
                /** Create JSON Object **/
                JSONObject object = new JSONObject();
                object.put(RestUtils.TAG_USER_ID, basicInfo.getDoc_id());
                object.put(RestUtils.TAG_CNT_EMAIL, basicInfo.getEmail());
                List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                /** ContactNumber Visibility **/
                object.put(RestUtils.TAG_CNNTMUNVIS, basicInfo.getPhone_num_visibility());
                /** email Visibility **/
                object.put(RestUtils.TAG_CNNTEMAILVIS, basicInfo.getEmail_visibility());
                object.put(RestUtils.TAG_CNT_NUM, basicInfo.getPhone_num());

                JSONArray socialInfoArray = new JSONArray();
                JSONObject socialInfoObj = new JSONObject();
                socialInfoObj.put(RestUtils.TAG_ABOUT_ME, basicInfo.getAbout_me());
                socialInfoObj.put(RestUtils.KEY_SPECIFIC_ASK, basicInfo.getSpecificAsk());
                socialInfoObj.put(RestUtils.TAG_BLOG_PAGE, basicInfo.getBlog_page());
                socialInfoObj.put(RestUtils.TAG_WEB_PAGE, basicInfo.getWebsite());
                socialInfoObj.put(RestUtils.TAG_FB_PAGE, basicInfo.getFb_page());
                socialInfoObj.put(RestUtils.TAG_LINKEDIN_PAGE, basicInfo.getLinkedInPg());
                socialInfoObj.put(RestUtils.TAG_TWITTER_PAGE, basicInfo.getTwitterPg());
                socialInfoObj.put(RestUtils.TAG_INSTAGRAM_PAGE, basicInfo.getInstagramPg());
                socialInfoObj.put(RestUtils.TAG_PROFILE_URL, basicInfo.getDocProfileURL());
                socialInfoObj.put(RestUtils.TAG_PROFILE_PDF_URL, basicInfo.getDocProfilePdfURL());
                socialInfoArray.put(socialInfoObj);
                object.put(RestUtils.TAG_SOCIAL_INFO, socialInfoArray);
                showProgress();
                new VolleySinglePartStringRequest(ProfileViewActivity.this, Request.Method.POST, RestApiConstants.UPDATE_USER_PROFILE, object.toString(), "ABOUT_ME", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        hideProgress();
                        JSONObject responseObj = null;
                        try {
                            responseObj = new JSONObject(successResponse);
                            if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                if (responseObj.has(RestUtils.TAG_DATA)) {
                                    JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                    if (dataObj.has(RestUtils.TAG_VERIFICATION_INFO)) {
                                        JSONObject verificationInfoObject = dataObj.optJSONObject(RestUtils.TAG_VERIFICATION_INFO);
                                        basicInfo.setPhone_verify(verificationInfoObject.optBoolean(RestUtils.TAG_PHONE_VERIFY));
                                        basicInfo.setEmail_verify(verificationInfoObject.optBoolean(RestUtils.TAG_EMAIL_VERIFY));
                                    }
                                    if (dataObj.has(RestUtils.TAG_SOCIAL_INFO)) {
                                        JSONObject socialInfoObject = dataObj.optJSONArray(RestUtils.TAG_SOCIAL_INFO).optJSONObject(0);
                                        basicInfo.setAbout_me(socialInfoObject.optString(RestUtils.TAG_ABOUT_ME));
                                        basicInfo.setBlog_page(socialInfoObject.optString(RestUtils.TAG_BLOG_PAGE));
                                        basicInfo.setWebsite(socialInfoObject.optString(RestUtils.TAG_WEB_PAGE));
                                        basicInfo.setFb_page(socialInfoObject.optString(RestUtils.TAG_FB_PAGE));
                                        basicInfo.setLinkedInPg(socialInfoObject.optString(RestUtils.TAG_LINKEDIN_PAGE));
                                        basicInfo.setTwitterPg(socialInfoObject.optString(RestUtils.TAG_TWITTER_PAGE));
                                        basicInfo.setInstagramPg(socialInfoObject.optString(RestUtils.TAG_INSTAGRAM_PAGE));
                                        basicInfo.setDocProfileURL(socialInfoObject.optString(RestUtils.TAG_PROFILE_URL));
                                        basicInfo.setDocProfilePdfURL(socialInfoObject.optString(RestUtils.TAG_PROFILE_PDF_URL));
                                    }
                                    basicInfo.setEmail(dataObj.optString(RestUtils.TAG_CNT_EMAIL));
                                    basicInfo.setPhone_num(dataObj.optString(RestUtils.TAG_CNT_NUM));

                                    List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                                    /** ContactNumber Visibility **/
                                    if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfo.setPhone_num_visibility(0);
                                    } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfo.setPhone_num_visibility(1);
                                    } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfo.setPhone_num_visibility(2);
                                    }
                                    /** email Visibility **/

                                    if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfo.setEmail_visibility(0);

                                    } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfo.setEmail_visibility(1);

                                    } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfo.setEmail_visibility(2);
                                    }
                                    if (basicInfo.getPhone_verify()) {
                                        userPhoneNumTxtVw.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, R.drawable.ic_verified, 0);
                                        phoneVerifyLabel.setVisibility(View.GONE);
                                    } else {
                                        userPhoneNumTxtVw.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);
                                        phoneVerifyLabel.setVisibility(View.VISIBLE);
                                    }
                                    realmManager.updateAboutmeBasicInfo(realm, basicInfo);
                                }

                            } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                String errorMsg = getResources().getString(R.string.unable_to_connect_server);
                                if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(ProfileViewActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        try {
                            JSONObject errorObj = new JSONObject(errorResponse);
                            String errorMessage = errorObj.optInt(RestUtils.TAG_ERROR_CODE) == 102 ? errorObj.optString(RestUtils.TAG_ERROR_MESSAGE) : getString(R.string.unable_to_connect_server);
                            Toast.makeText(ProfileViewActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            hideProgress();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).sendSinglePartRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(ProfileViewActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission is granted2");
                    return true;
                } else {

                    Log.v(TAG, "Permission is revoked2");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 101);
                    return false;
                }
            } else {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission is granted2");
                    return true;
                } else {

                    Log.v(TAG, "Permission is revoked2");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                    return false;
                }
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            //findViewById(R.id.start).setEnabled(true);
            if (downloadManager == null || lastDownloadId == 0) {
                return;
            }
            Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(lastDownloadId));

            if (c == null) {
                Toast.makeText(ctxt, "Download not found!", Toast.LENGTH_LONG).show();
            } else {
                c.moveToFirst();

                Log.d(getClass().getName(), "COLUMN_ID: " +
                        c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
                Log.d(getClass().getName(), "COLUMN_BYTES_DOWNLOADED_SO_FAR: " +
                        c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
                Log.d(getClass().getName(), "COLUMN_LAST_MODIFIED_TIMESTAMP: " +
                        c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
                Log.d(getClass().getName(), "COLUMN_LOCAL_URI: " +
                        c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
                Log.d(getClass().getName(), "COLUMN_STATUS: " +
                        c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)));
                Log.d(getClass().getName(), "COLUMN_REASON: " +
                        c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)));

                Toast.makeText(ProfileViewActivity.this, statusMessage(c), Toast.LENGTH_LONG).show();
            }
        }
    };

    private String statusMessage(Cursor c) {
        String msg = "???";

        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = "Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download completed!";
                break;
            case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                msg = "Insufficient space";
                break;
            default:
                msg = "Download is nowhere in sight";
                break;
        }

        return (msg);
    }
}
