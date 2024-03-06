package com.vam.whitecoats.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.brandkinesis.BKUserInfo;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.callback.BKUserInfoCallback;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.flurry.android.FlurryAgent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.AreasOfInterest;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.Category;
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
import com.vam.whitecoats.core.realm.UserEvents;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.CreatePostActivity;
import com.vam.whitecoats.ui.activities.LoginActivity;
import com.vam.whitecoats.ui.activities.ProfileViewActivity;
import com.vam.whitecoats.ui.adapters.CommunityListAdapter;
import com.vam.whitecoats.ui.adapters.DashboardFeedsAdapter;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.OnDataLoadWithList;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnRefreshDashboardData;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.ProfileUpdatedListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.Foreground;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.sentry.Sentry;
import io.sentry.protocol.App;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class AssociationDashboardFragment extends Fragment implements OnTaskCompleted, UiUpdateListener, DashboardFeedsAdapter.BookMarkListener, OnRefreshDashboardData, ProfileUpdatedListener {
    private static OnDataLoadWithList onTaskCompletedListener;
    private ViewGroup postUpdateLayout;
    private TextView postUpdateHint;
    private LinearLayout postUpdateLabel_lay;
    private RoundedImageView profilePic;
    private Realm realm;
    private RealmManager realmManager;
    private RealmBasicInfo basicInfo;
    private SparseArray<JSONObject> channelsList = new SparseArray<>();
    private boolean isDisplayPostIcon;
    private int networkChannelId;
    private JSONArray chooseCommunityJsonObj = new JSONArray();
    private ArrayList<JSONObject> associationListJson = new ArrayList<>();
    private MySharedPref mySharedPref;
    //private ViewPager viewPager;
    private TabLayout tabs;
    private DashboardUpdatesFragment updates_frag;
    //private ImageView tabsFilter;
    private Adapter adapter;
    public static final String TAG = AssociationDashboardFragment.class.getSimpleName();
    private JSONObject requestData;
    private int pageIndex = 0;
    private int login_doc_id = 0;
    private ViewGroup shimmerContentLayout;
    private ShimmerFrameLayout shimmerContainer;
    private ShimmerFrameLayout shimmerContainer1;
    private ViewGroup tabsLayout;
    ArrayList<String> tabTitle = new ArrayList<>();
    //private FrameLayout dataContainer;
    private boolean isFragmentInForeground;
    private ViewPager viewPager;
    private View separator_view;
    private DashboardListener dashboardListener;
    private SharedPreferences editor;
    public static final String MyPREFERENCES = "MyPrefs";
    private RecyclerView recyclerView;
    private boolean isRefreshFromAllFeeds;
    private ImageView addPicSymbol;
    private ArrayList<Category> categoryList=new ArrayList();
    private Boolean is_user_active;
    //private RecyclerView categoryRecyclerView;
    //private ViewGroup categoryListLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        CallbackCollectionManager.getInstance().registerListener(this);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        basicInfo = realmManager.getRealmBasicInfo(realm);
        login_doc_id = realmManager.getDoc_id(realm);
        mySharedPref = new MySharedPref(getActivity());
        requestData = new JSONObject();
        //request data
        try {
            requestData.put(RestUtils.TAG_DOC_ID, login_doc_id);
            requestData.put("last_feed_time", 0);
            requestData.put("last_open", 0);
            requestData.put("last_close", 0);
            requestData.put("load_next", true);
            requestData.put("pg_num", pageIndex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        is_user_active=mySharedPref.getPref("is_user_active",false);
        if (basicInfo.getUserUUID() == null || basicInfo.getUserUUID().isEmpty()) {
            if(is_user_active) {
                setUpUserProfile();
            }
        }
        editor = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    public void setUpUserProfile() {
        if (realmManager.getDoc_id(realm) == 0) {
            return;
        }
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (AppUtil.isConnectingToInternet(getActivity())) {
            new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.VIEW_USER_PROFILE, requestObj.toString(), "VIEW_USER_PROFILE", new OnReceiveResponse() {
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
                                    RealmManager realmManager = new RealmManager(getActivity());
                                    BasicInfo basicInfoObj = new BasicInfo();
                                    basicInfoObj.setUserSalutation(dataObj.optString(RestUtils.TAG_USER_SALUTAION));
                                    basicInfoObj.setUserType(dataObj.optInt(RestUtils.TAG_USER_TYPE));
                                    basicInfoObj.setFname(dataObj.optString(RestUtils.TAG_USER_FIRST_NAME));
                                    basicInfoObj.setLname(dataObj.optString(RestUtils.TAG_USER_LAST_NAME));
                                    basicInfoObj.setEmail(dataObj.optString(RestUtils.TAG_CNT_EMAIL));
                                    basicInfoObj.setUserUUID(dataObj.optString(RestUtils.TAG_USER_UUID));
                                    List<String> visibilityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                                    /** ContactNumber Visibility **/
                                    if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfoObj.setPhone_num_visibility(0);
                                    } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfoObj.setPhone_num_visibility(1);
                                    } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTMUNVIS))) {
                                        basicInfoObj.setPhone_num_visibility(2);
                                    }
                                    /** email Visibility **/


                                    if (visibilityList.get(0).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfoObj.setEmail_visibility(0);

                                    } else if (visibilityList.get(1).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfoObj.setEmail_visibility(1);

                                    } else if (visibilityList.get(2).equals(dataObj.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                                        basicInfoObj.setEmail_visibility(2);
                                    }
                                    basicInfoObj.setPic_url(dataObj.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    basicInfoObj.setPhone_num(dataObj.optString(RestUtils.TAG_CNT_NUM));
                                    basicInfoObj.setSubSpeciality(dataObj.optString(RestUtils.TAG_SUB_SPLTY));
                                    basicInfoObj.setOverAllExperience(dataObj.optInt("experience"));
                                    if (dataObj.has(RestUtils.TAG_ACTIVITY_COUNT)) {
                                        JSONObject activityCountObject = dataObj.optJSONObject(RestUtils.TAG_ACTIVITY_COUNT);
                                        basicInfoObj.setTot_caserooms(activityCountObject.optInt(RestUtils.TAG_CASE_ROOM_COUNT));
                                        basicInfoObj.setTot_contacts(activityCountObject.optInt(RestUtils.TAG_CONNECTS_COUNT));
                                        basicInfoObj.setTot_groups(activityCountObject.optInt(RestUtils.TAG_GROUPS_COUNT));
                                        basicInfoObj.setLikeCount(activityCountObject.optInt(RestUtils.TAG_LIKES_COUNT));
                                        basicInfoObj.setShareCount(activityCountObject.optInt(RestUtils.TAG_SHARE_COUNT));
                                        basicInfoObj.setFeedCount(activityCountObject.optInt(RestUtils.TAG_FEED_COUNT));
                                        basicInfoObj.setFollowersCount(activityCountObject.optInt(RestUtils.TAG_FOLLOWERS_COUNT));
                                        basicInfoObj.setFollowingCount(activityCountObject.optInt(RestUtils.TAG_FOLLOWING_COUNT));
                                    }
                                    if (dataObj.has(RestUtils.TAG_VERIFICATION_INFO)) {
                                        JSONObject verificationInfoObject = dataObj.optJSONObject(RestUtils.TAG_VERIFICATION_INFO);
                                        basicInfoObj.setEmail_verify(verificationInfoObject.optBoolean(RestUtils.TAG_EMAIL_VERIFY));
                                        basicInfoObj.setPhone_verify(verificationInfoObject.optBoolean(RestUtils.TAG_PHONE_VERIFY));
                                    }
                                    if (dataObj.has(RestUtils.KEY_SPECIALISTS)) {
                                        StringBuilder specialityString = new StringBuilder();
                                        JSONArray specialistsArray = dataObj.optJSONArray(RestUtils.KEY_SPECIALISTS);
                                        int len = specialistsArray.length();
                                        for (int count = 0; count < len; count++) {
                                            specialityString.append(specialistsArray.optString(count)).append(",");
                                        }
                                        basicInfoObj.setSplty(specialityString.deleteCharAt(specialityString.length() - 1).toString());
                                    }
                                    if (dataObj.has(RestUtils.TAG_SOCIAL_INFO)) {
                                        JSONObject socialInfoObj = dataObj.optJSONArray(RestUtils.TAG_SOCIAL_INFO).optJSONObject(0);
                                        basicInfoObj.setAbout_me(socialInfoObj.optString(RestUtils.TAG_ABOUT_ME));
                                        basicInfoObj.setBlog_page(socialInfoObj.optString(RestUtils.TAG_BLOG_PAGE));
                                        basicInfoObj.setFb_page(socialInfoObj.optString(RestUtils.TAG_FB_PAGE));
                                        basicInfoObj.setWebsite(socialInfoObj.optString(RestUtils.TAG_WEB_PAGE));
                                        basicInfoObj.setLinkedInPg(socialInfoObj.optString(RestUtils.TAG_LINKEDIN_PAGE));
                                        basicInfoObj.setTwitterPg(socialInfoObj.optString(RestUtils.TAG_TWITTER_PAGE));
                                        basicInfoObj.setInstagramPg(socialInfoObj.optString(RestUtils.TAG_INSTAGRAM_PAGE));
                                        basicInfoObj.setSpecificAsk(socialInfoObj.optString(RestUtils.KEY_SPECIFIC_ASK));
                                        basicInfoObj.setDocProfileURL(socialInfoObj.optString(RestUtils.TAG_PROFILE_URL));
                                        basicInfoObj.setDocProfilePdfURL(socialInfoObj.optString(RestUtils.TAG_PROFILE_PDF_URL));
                                    }
                                    if (dataObj.has(RestUtils.TAG_OTHER_INFO)) {
                                        /*JSONObject otherInfoObj = dataObj.optJSONObject(RestUtils.TAG_OTHER_INFO);
                                        basicInfo.setQb_userid(otherInfoObj.optString(RestUtils.TAG_ABOUT_ME));
                                        basicInfo.setQb_login(otherInfoObj.optString(RestUtils.TAG_BLOG_PAGE));
                                        basicInfo.setuse(otherInfoObj.optString(RestUtils.TAG_FB_PAGE));
                                        basicInfo.setWebsite(otherInfoObj.optString(RestUtils.TAG_WEB_PAGE));*/
                                    }
                                    basicInfoObj.setProfile_pic_path(basicInfo.getProfile_pic_path());
                                    realmManager.updateBasicInfo(realm, basicInfoObj);
                                    realmManager.updateAboutmeBasicInfo(realm, basicInfoObj);
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
                                    /*
                                     * call setUpMyProfile(UPDATE_ALL)
                                     */
                                    if (!realm.isClosed()) {
                                        realm.close();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.e(TAG, "onErrorResponse - " + errorResponse);
                }
            }).sendSinglePartRequest();
        }
    }

    public void requestForDashBoardData(JSONObject requestData, final boolean loadPreData, final boolean refreshData) {
        FlurryAgent.logEvent("Dashboard hit :" + AppConstants.login_doc_id);
        AppUtil.captureSentryMessage("Dashboard hit :"+AppConstants.login_doc_id);
        if (requestData.optInt(RestUtils.TAG_DOC_ID, 0) == 0) {
            Toast.makeText(getActivity(), "Session has expired,Please login.", Toast.LENGTH_SHORT).show();
            mySharedPref.savePref(MySharedPref.PREF_SESSION_TOKEN, "");
            mySharedPref.savePref(MySharedPref.STAY_LOGGED_IN, false);
            Intent intentLogout = new Intent(getActivity(), LoginActivity.class);
            startActivity(intentLogout);
            getActivity().finish();
        }
        new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.GET_AGGREGATED_DASHBOARD_DATA, requestData.toString(), "DAY_FIRST_FEED", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (getActivity() != null && isAdded()) {
                    //tabsLayout.setVisibility(View.VISIBLE);
                    if (shimmerContentLayout != null && shimmerContentLayout.getVisibility() == View.VISIBLE) {
                        shimmerContainer.stopShimmerAnimation();
                        shimmerContainer1.stopShimmerAnimation();
                        shimmerContentLayout.setVisibility(View.GONE);
                    }
                    JSONObject jsonObject = null;
                    JSONObject completeJson = null;
                    JSONObject networkChannelObj = null;
                    try {
                        jsonObject = new JSONObject(successResponse);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            if (jsonObject.has(RestUtils.TAG_DATA)) {
                                completeJson = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                if (completeJson.has("listofChannels") && completeJson.optJSONArray("listofChannels") != null) {
                                    isDisplayPostIcon = false;
                                    AppConstants.unreadChannelsList.clear();
                                    chooseCommunityJsonObj = new JSONArray();
                                    realmManager.clearChannelRealmData();
                                    realmManager.insertOrUpdateChannelsListInDB("listofChannels", completeJson.optJSONArray("listofChannels").toString());
                                    /*if (realmManager.getChannelsListFromDB().size() == 0) {
                                        realmManager.insertOrUpdateChannelsListInDB("listofChannels", completeJson.optJSONArray("listofChannels").toString());
                                    } else {
                                        JSONArray storedList = realmManager.getChannelsListFromDB("listofChannels");
                                        JSONArray updatedList = completeJson.optJSONArray("listofChannels");
                                        Object[] returnObj = AppUtil.checkAndUpdateJSON(storedList, updatedList);
                                        if (returnObj.length > 1 && returnObj[1] != null && returnObj[1].getClass() == JSONArray.class) {
                                            storedList = (JSONArray) returnObj[1];
                                        }
                                        realmManager.insertOrUpdateChannelsListInDB("listofChannels", storedList.toString());
                                    }*/
                                    JSONArray updatedChannelsList = realmManager.getChannelsListFromDB("listofChannels");
                                    StringBuffer associationName = new StringBuffer();
                                    for (int x = 0; x < updatedChannelsList.length(); x++) {
                                        JSONObject currentChannelObj = updatedChannelsList.optJSONObject(x);
                                        associationName.append(currentChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                                        if (x != updatedChannelsList.length() - 1) {
                                            associationName.append(",");
                                        }
                                        channelsList.put(currentChannelObj.optInt(RestUtils.CHANNEL_ID), currentChannelObj);
                                        if ((currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Community")) && currentChannelObj.optBoolean("is_preferred_channel")
                                        ) {
                                            associationListJson.add(currentChannelObj);
                                        }
                                        if (currentChannelObj.optBoolean("is_admin")) {
                                            JSONObject jsonObj = new JSONObject();
                                            jsonObj.put(RestUtils.CHANNEL_ID, currentChannelObj.optInt(RestUtils.CHANNEL_ID));
                                            jsonObj.put(RestUtils.FEED_PROVIDER_NAME, currentChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                                            chooseCommunityJsonObj.put(currentChannelObj);
                                            isDisplayPostIcon = true;
                                        }
                                        if (currentChannelObj.has("hasUnread") && currentChannelObj.optBoolean("hasUnread") && currentChannelObj.optBoolean(RestUtils.TAG_IS_SUBSCRIBED)) {
                                            AppConstants.unreadChannelsList.add(currentChannelObj.optInt(RestUtils.CHANNEL_ID));
                                        }
                                        if (currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(getString(R.string.label_network))) {
                                            networkChannelObj = currentChannelObj;
                                            networkChannelId = currentChannelObj.optInt(RestUtils.CHANNEL_ID);
                                            isDisplayPostIcon = true;
                                        }
                                    }

                                    if (associationName.length() > 0) {
                                        Bundle userInfo = new Bundle();
                                        HashMap<String, Object> others = new HashMap<>();
                                        others.put("AssociationNames", associationName.toString());
                                        userInfo.putSerializable(BKUserInfo.BKUserData.OTHERS, others);
                                        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
                                        if (bkInstance != null) {
                                            bkInstance.setUserInfoBundle(userInfo, new BKUserInfoCallback() {
                                                @Override
                                                public void onUserInfoUploaded(boolean uploadStatus) {
                                                    Log.d("UPSHOT_USER_LOG", "result :" + uploadStatus);
                                                }
                                            });
                                        }
                                    }
                                    postUpdateLayout.setVisibility(View.GONE);
                                    isRefreshFromAllFeeds = false;
                                    setupViewPager(viewPager, successResponse, false, false);
                                    if (networkChannelObj != null) {
                                        associationListJson.add(0, networkChannelObj);
                                    }
                                    onTaskCompletedListener.onDataLoadWithList(associationListJson, completeJson.optBoolean("display_user_preferred_channel", false),completeJson.optInt(RestUtils.TAG_FEED_NOTIFICATION_COUNT,0),chooseCommunityJsonObj,channelsList,networkChannelId,loadPreData);
                                    dashboardStatus(true, postUpdateLayout, true);
                                    //requestForCategoriesData();

                                }

                            }


                        } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            if (realmManager.checkFeedExists(-4, 0)) {
                                realmManager.deleteFeedFromDB(-4, 0);
                            }
                            deleteRelatedFeedsInoffline();
                            dashboardListener.dashboardLoadedWithOfflineData();
                        }

                       /* new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                presentShowcaseSequence();
                            }
                        }, 1000);*/



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                if (shimmerContentLayout.getVisibility() == View.VISIBLE) {
                    shimmerContainer.stopShimmerAnimation();
                    shimmerContainer1.stopShimmerAnimation();
                    shimmerContentLayout.setVisibility(View.GONE);
                }


                processChannelsData(realmManager, postUpdateLayout, false);
                if (Foreground.get().isForeground() && isFragmentInForeground) {
                    if (realmManager.getFeedDataFromDB() == null || realmManager.getFeedDataFromDB().size() == 0) {
                        ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
                    }
                }
            }
        }).sendSinglePartRequest();

        AppUtil.syncShareFailedFeedsToServer(getActivity(), realmManager, login_doc_id);
        RealmResults<UserEvents> eventsList = realmManager.getAllEventsDataFromDB();
        if (eventsList.size() > 0) {
            JSONObject failedNotificationsRequest = new JSONObject();
            JSONArray eventObjArray = new JSONArray();
            try {
                for (UserEvents userEvent : eventsList) {
                    JSONObject eventObj = new JSONObject();
                    //JSONObject eventDataObj = new JSONObject();
                    //eventDataObj.put(RestUtils.TAG_NOTIFICATION_ID, userEvent.getEventData());
                    eventObj.put(RestUtils.TAG_EVENT_TYPE, userEvent.getEventName());
                    eventObj.put(RestUtils.TAG_EVENT_DATA, new JSONObject(userEvent.getEventData()));
                    eventObjArray.put(eventObj);
                }
                failedNotificationsRequest.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
                failedNotificationsRequest.put(RestUtils.TAG_USER_EVENTS, eventObjArray);
                failedNotificationsRequest.put(RestUtils.TAG_COUNT, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.LOG_USER_EVENT_API, failedNotificationsRequest.toString(), "TAG_NOTIFICATIONS_SYNC", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    try {
                        JSONObject responseObj = new JSONObject(successResponse);
                        if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            realmManager.deleteAllUserEventsDataFromDB();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                }
            }).sendSinglePartRequest();
        }

    }


    /*private void requestForCategoriesData() {
        GetCategoriesRepository categoriesData = new GetCategoriesRepository(realmManager.getDoc_id(realm), true, 0);
        categoriesData.initRequest(Request.Method.POST,  RestApiConstants.GET_USER_CATEGORIES_LIST, "USER_CATEGORY_LIST",AppUtil.getRequestHeaders(getActivity()));
        categoriesData.getUserCategoryList().observe(this, categoriesApiResponse -> {
            if (categoriesApiResponse.getError() != null) {
                return;
            } else {

                List<Category> categories = categoriesApiResponse.getCategories();
                if (categories.size() > 0) {
                    Category mCategory = new Category();
                    mCategory.setCategoryId(-1);
                    mCategory.setCategoryName("More");
                    mCategory.setCategoryType("");
                    mCategory.setImageUrl("");
                    mCategory.setUnreadCount(0);
                    mCategory.setRank(0);
                    categories.add(categories.size(), mCategory);
                    categoryList.addAll(categories);
                }
            }

            if (categoryList.size() > 0) {
                //realmManager.insertTestFeedDataIntoDB(realm, -3, true);
                categoryListLayout.setVisibility(View.VISIBLE);
                CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity(), categoryList);
                categoryRecyclerView.setHasFixedSize(true);
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                categoryRecyclerView.setAdapter(categoriesAdapter);
            }
            //dashboradAdapter.updateCategoriesDataWithList(categoryList);
            //dashboradAdapter.notifyDataSetChanged();
        });

    }*/
    private void processChannelsData(RealmManager realmManager, ViewGroup postUpdateLayout, boolean isDashboardData) {
        JSONObject networkChannelObj = null;
        try {
            if (realmManager.checkFeedExists(-4, 0)) {
                realmManager.deleteFeedFromDB(-4, 0);
            }
            if (realmManager.checkFeedExists(-6, 0)) {
                realmManager.deleteFeedFromDB(-6, 0);
            }
            realmManager.deleteFeedsByChannelId(-6);
            deleteRelatedFeedsInoffline();
            JSONArray updatedChannelsList = realmManager.getChannelsListFromDB("listofChannels");
            if (updatedChannelsList.length() > 0) {
                channelsList.clear();
                if (realmManager.checkFeedExists(-3, 0)) {
                    realmManager.deleteFeedFromDB(-3, 0);
                }
                for (int x = 0; x < updatedChannelsList.length(); x++) {
                    JSONObject currentChannelObj = updatedChannelsList.optJSONObject(x);
                    channelsList.put(currentChannelObj.optInt(RestUtils.CHANNEL_ID), currentChannelObj);
                    if ((currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Community")) && currentChannelObj.optBoolean("is_preferred_channel")) {
                        associationListJson.add(currentChannelObj);
                    }
                    if (currentChannelObj.optBoolean("is_admin")) {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put(RestUtils.CHANNEL_ID, currentChannelObj.optInt(RestUtils.CHANNEL_ID));
                        jsonObj.put(RestUtils.FEED_PROVIDER_NAME, currentChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                        chooseCommunityJsonObj.put(currentChannelObj);
                        isDisplayPostIcon = true;
                    }
                    if (currentChannelObj.has("hasUnread") && currentChannelObj.optBoolean("hasUnread")) {
                        AppConstants.unreadChannelsList.add(currentChannelObj.optInt(RestUtils.CHANNEL_ID));
                    }
                    if (currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(getString(R.string.label_network))) {
                        networkChannelObj = currentChannelObj;
                        networkChannelId = currentChannelObj.optInt(RestUtils.CHANNEL_ID);
                        isDisplayPostIcon = true;
                    }
                }
                postUpdateLayout.setVisibility(View.GONE);
                setupViewPager(viewPager, "", true, isDashboardData);
                if (networkChannelObj != null) {
                    associationListJson.add(0, networkChannelObj);
                }
                dashboardListener.dashboardLoadedWithOfflineData();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteRelatedFeedsInoffline() {
        realmManager.deleteFeedsByChannelId(-5);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.association_frament_layout, container, false);
        postUpdateLayout = (ViewGroup) view.findViewById(R.id.post_update_dashboard);
        profilePic = (RoundedImageView) view.findViewById(R.id.profile_pic_timeline);
        postUpdateHint = (TextView) view.findViewById(R.id.postUpdateLabel);
        postUpdateLabel_lay = (LinearLayout) view.findViewById(R.id.postUpdateLabel_lay);
        //tabsFilter = (ImageView) view.findViewById(R.id.iv_tabs_filter);
        tabsLayout = (ViewGroup) view.findViewById(R.id.association_filter_layout);
        //dataContainer=(FrameLayout)view.findViewById(R.id.frame_container);
        // Setting ViewPager for each Tabs
        viewPager = (ViewPager) view.findViewById(R.id.frag2_viewpager);
        shimmerContentLayout = (ViewGroup) view.findViewById(R.id.shimmer_content_layout);
        shimmerContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerContainer1 = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container1);
        separator_view = (View) view.findViewById(R.id.separator_view);
        //setupViewPager(viewPager,new JSONArray());
        // Set Tabs inside Toolbar
        tabs = (TabLayout) view.findViewById(R.id.frag2_tabs);
        //tabs.setSmoothScrollingEnabled(true);
        tabs.setupWithViewPager(viewPager);
        addPicSymbol = (ImageView) view.findViewById(R.id.add_pic_symbol);
        //categoryRecyclerView=(RecyclerView)view.findViewById(R.id.category_list_dashboard);
        //categoryListLayout=(ViewGroup)view.findViewById(R.id.category_list_layout);

        postUpdateLabel_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
                data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
                AppUtil.logUserEventWithHashMap("DashboardFeedCreationInitiation", basicInfo.getDoc_id(), data,getActivity());
                if (mySharedPref != null && mySharedPref.getPref(MySharedPref.PREF_IS_USER_VERIFIED, 3) == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                    if (chooseCommunityJsonObj.length() == 0) {
                        JSONObject channelObj = channelsList.get(networkChannelId);
                        viewCreatePost(channelObj);
                    } else {
                        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                        final View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_modal, null);
                        ViewGroup network_selection_layout = (ViewGroup) sheetView.findViewById(R.id.network_selection_layout);
                        final ListView communityListView = (ListView) sheetView.findViewById(R.id.communityList);
                        /*
                         * If No network channel is there, hide "post to network" layout
                         */
                        if (networkChannelId != -1) {
                            network_selection_layout.setVisibility(View.VISIBLE);
                        } else {
                            network_selection_layout.setVisibility(View.GONE);
                        }

                        final ArrayList<JSONObject> communityList = new ArrayList<JSONObject>();
                        int len = chooseCommunityJsonObj.length();
                        for (int i = 0; i < len; i++) {
                            try {
                                communityList.add(chooseCommunityJsonObj.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        /*
                         * Sort the list in alphabetical order
                         */
                        Collections.sort(communityList, new Comparator<JSONObject>() {

                            @Override
                            public int compare(JSONObject lhs, JSONObject rhs) {
                                try {
                                    return (lhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase().compareTo(rhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });
                        /*
                         * Set adapter
                         */
                        CommunityListAdapter communityListAdapter = new CommunityListAdapter(getActivity(), communityList);
                        communityListView.setAdapter(communityListAdapter);
                        mBottomSheetDialog.setContentView(sheetView);
                        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) sheetView.getParent());
                        //mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        mBottomSheetDialog.show();
                        communityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                data.put(RestUtils.EVENT_COMMUNITY_ID, communityList.get(position).optInt(RestUtils.CHANNEL_ID));
                                AppUtil.logUserEventWithHashMap("CommunityShareInitiation", basicInfo.getDoc_id(), data,getActivity());
                                mBottomSheetDialog.dismiss();
                                viewCreatePost(communityList.get(position));
                            }
                        });
                        network_selection_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppUtil.logUserEventWithHashMap("ShareWithEveryOneInitiation", basicInfo.getDoc_id(), data,getActivity());
                                mBottomSheetDialog.dismiss();
                                /*
                                 * Send customized params when sharing to Network channel
                                 */
                                JSONObject channelObj = channelsList.get(networkChannelId);
                                viewCreatePost(channelObj);
                            }
                        });
                    }

                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(getActivity(), getActivity().getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(getActivity(), getActivity().getString(R.string.mca_uploaded_but_not_verified));
                }
            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.logUserEventWithDocIDAndSplty("DashboardOwnProfileIcon", basicInfo,getActivity());
                Intent intent_profile = new Intent(getActivity(), ProfileViewActivity.class);
                startActivity(intent_profile);
            }
        });
        //tabsLayout.setVisibility(View.GONE);
        if (AppUtil.isConnectingToInternet(getActivity())) {
            shimmerContentLayout.setVisibility(View.VISIBLE);
            shimmerContainer.startShimmerAnimation();
            shimmerContainer1.startShimmerAnimation();
            if(is_user_active) {
                requestForDashBoardData(requestData, false, false);
            }
        }else {
            shimmerContentLayout.setVisibility(View.GONE);
            processChannelsData(realmManager, postUpdateLayout, false);
        }

        return view;

    }

    private void viewCreatePost(JSONObject channelObj) {
        Intent intent = new Intent(getActivity(), CreatePostActivity.class);
        intent.putExtra(RestUtils.NAVIGATATION, "Dashboard");
        if (channelObj != null)
            intent.putExtra(RestUtils.KEY_SELECTED_CHANNEL, channelObj.toString());
        startActivity(intent);
    }

    public AssociationDashboardFragment() {
    }

    public static AssociationDashboardFragment newInstance(OnDataLoadWithList taskCompleted) {
        onTaskCompletedListener = taskCompleted;
        AssociationDashboardFragment fragment = new AssociationDashboardFragment();
        return fragment;
    }

    private void setupViewPager(ViewPager viewPager, String successResponse, boolean loadOfflineData, boolean isDashboard) {
        int defaultChannelPosition = -1;
        JSONObject defaultChannelObj = null;
        List<Fragment> tempFragmentList = new ArrayList<>();
        List<String> tempTabTitleList = new ArrayList<>();
        String responseForDashboard = successResponse;

        boolean dashboardReload = false;
        Collections.sort(associationListJson, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                try {
                    return (lhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase().compareTo(rhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        boolean isDefaultChannelFound = false;
        for (int i = 0; i < associationListJson.size(); i++) {
            if (!associationListJson.get(i).optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_NETWORK)) {
                if (associationListJson.get(i).optBoolean("is_user_default_channel")) {
                    defaultChannelPosition = i;
                    defaultChannelObj = associationListJson.get(i);
                    mySharedPref.savePref(MySharedPref.PREF_SAVED_PREF_CHANNEL, associationListJson.get(i).optInt(RestUtils.CHANNEL_ID));
                    isDefaultChannelFound = true;
                } else {
                    AssociationTimelineFragment associationTimelineFragment = AssociationTimelineFragment.newInstance(associationListJson.get(i).toString(), "", false, isDashboard);
                    tempFragmentList.add(associationTimelineFragment);
                    tempTabTitleList.add(associationListJson.get(i).optString(RestUtils.FEED_PROVIDER_NAME));
                }
            }
        }
        if (isDefaultChannelFound == false) {
            mySharedPref.delete(MySharedPref.PREF_SAVED_PREF_CHANNEL);
        }
        if (associationListJson.size() == 0) {
            ViewGroup.LayoutParams params = tabs.getLayoutParams();
            //Change the height in 'Pixels'
            params.height = 0;
            tabs.setLayoutParams(params);
            separator_view.setVisibility(View.GONE);
        } else {
            ViewGroup.LayoutParams params = tabs.getLayoutParams();
            //Change the height in 'Pixels'
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = height;
            tabs.setLayoutParams(params);
            separator_view.setVisibility(View.VISIBLE);
        }
        if (defaultChannelPosition != -1 && defaultChannelObj != null) {
            responseForDashboard = "";
            dashboardReload = true;
            associationListJson.remove(defaultChannelPosition);
            associationListJson.add(0, defaultChannelObj);
            AssociationTimelineFragment associationTimelineFragment = new AssociationTimelineFragment().newInstance(defaultChannelObj.toString(), isDashboard ? "" : successResponse, loadOfflineData, isDashboard);
            tempFragmentList.add(0, associationTimelineFragment);
            tempTabTitleList.add(0, defaultChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
        }
        updates_frag = DashboardUpdatesFragment.newInstance(dashboardReload);
        updates_frag.setDashboardResponse(responseForDashboard);
        updates_frag.setDataRefreshListener(this);
        adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(updates_frag, "All Feeds");
        adapter.addAllFragment(tempFragmentList, tempTabTitleList);
        if(viewPager!=null) {
            viewPager.setAdapter(adapter);
            if (dashboardReload && !isRefreshFromAllFeeds) {
                viewPager.setCurrentItem(1);
            }
        }


    }

    // Add Fragments to Tabs

    @Override
    public void onTaskCompleted(String s) {

    }

    @Override
    public void updateUI(int feedId, JSONObject socialInteractionObj) {

    }

    @Override
    public void notifyUIWithNewData(JSONObject newUpdate) {
        if (!newUpdate.optBoolean("isFromNotification", false) && viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void notifyUIWithDeleteFeed(int feedId, JSONObject deletedFeedObj) {

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

    @Override
    public void isBookmarkClicked(final boolean isBookmarked) {
        if (isBookmarked) {
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presentShowcaseSequence(isBookmarked);
                }
            }, 500);*/
        }
    }

    @Override
    public void onRefresh(ArrayList<JSONObject> arrayList, JSONArray communityList, String response, JSONObject networkChannel, boolean isDashboard,boolean isFromPredata) {
        if (viewPager != null) {
            isRefreshFromAllFeeds = true;
            associationListJson.clear();
            associationListJson.addAll(arrayList);
            chooseCommunityJsonObj = new JSONArray();
            chooseCommunityJsonObj = communityList;
            if(!isFromPredata) {
                setupViewPager(viewPager, response, false, isDashboard);
            }
            if (networkChannel != null) {
                associationListJson.add(0, networkChannel);
            }
            if (onTaskCompletedListener != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject completeJson = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                    onTaskCompletedListener.onDataLoadWithList(associationListJson, completeJson.optBoolean("display_user_preferred_channel", false),completeJson.optInt(RestUtils.TAG_FEED_NOTIFICATION_COUNT,0),chooseCommunityJsonObj, channelsList, networkChannelId,isFromPredata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onProfileUpdate(BasicInfo basicInfo) {
        if (getActivity() != null && isAdded()) {
            loadProfilePic();
        }
    }


    public class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void addAllFragment(List<Fragment> fragmentsList, List<String> titles) {
            mFragmentList.addAll(fragmentsList);
            mFragmentTitleList.addAll(titles);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = mFragmentTitleList.get(position);
            if (title.length() > 30) {
                title = title.substring(0, 30);
                title = title + "...";
            }
            return title;
        }

        public void deletePage(int position) {
            if (canDelete()) {
                mFragmentList.remove(position);
                mFragmentTitleList.remove(position);
                notifyDataSetChanged();
            }
        }

        boolean canDelete() {
            return mFragmentList.size() > 0;
        }

        // This is called when notifyDataSetChanged() is called
        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed
            return PagerAdapter.POSITION_NONE;
        }


    }



    @Override
    public void onResume() {
        super.onResume();
        isFragmentInForeground = true;
        if (basicInfo != null && basicInfo.getFname() != null && !basicInfo.getFname().isEmpty()) {
            Spannable padString = new SpannableString("Share an update or a case...");
            padString.setSpan(new ForegroundColorSpan(Color.parseColor("#00A76D")), 0, padString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            postUpdateHint.setText("Hi " + basicInfo.getUser_salutation() + " " + basicInfo.getFname() + ",");
        }
        loadProfilePic();
        AppUtil.logDashboardImpressionEvent("DashboardImpression", basicInfo,getActivity());
        AppUtil.logScreenEvent("DashBoardTimeSpent");
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentInForeground = false;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        dashboardListener = (DashboardListener) activity;
    }

    public void dashboardStatus(boolean dashboardStatus, ViewGroup postUpdateLabel, boolean isDisplayPostIcon) {
        dashboardListener.isDashboardLoaded(dashboardStatus, postUpdateLabel, isDisplayPostIcon);
    }

    public void setTabSelection(int position, JSONObject channelObj) {
        if (viewPager != null) {
            viewPager.setCurrentItem(position);
        }

    }



    public interface DashboardListener {
        void isDashboardLoaded(boolean isDashboardLoaded, ViewGroup postUpdateLabel, boolean isDisplayPostIcon);
        void dashboardLoadedWithOfflineData();

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (updates_frag != null) {
            updates_frag.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadProfilePic() {
        Log.i(TAG, "loadProfilePic");
        String picUrl = (basicInfo.getPic_url() != null) ? basicInfo.getPic_url().trim() : "";
        String picPath = (basicInfo.getProfile_pic_path() != null) ? basicInfo.getProfile_pic_path().trim() : "";
        String profilePicPath = (picUrl != null && !picUrl.isEmpty()) ? picUrl : (picPath != null && !picPath.equals("")) ? picPath : null;
        /*if(picUrl!=null && !picUrl.isEmpty()){
            Picasso.with(context)
                    .load(picUrl)
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(profilePic);
        }else if(picPath!=null && !picPath.isEmpty()){ // loading from device locally
            File imgFile = new File(picPath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                if (myBitmap != null) {
                    profilePic.setImageBitmap(myBitmap);
                } else {
                    profilePic.setImageResource(R.drawable.default_profilepic);
                }
            } else {
                profilePic.setImageResource(R.drawable.default_profilepic);
            }
        }else{ // default user profile pic loading
            Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profilepic);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), AppUtil.createCircleBitmap(sourceBitmap));
            profilePic.setImageDrawable(drawable);
        }*/
        if (!AppUtil.checkWriteExternalPermission(getActivity())) {
            if (picUrl != null && !picUrl.isEmpty()) {
                AppUtil.invalidateAndLoadCircularImage(getActivity(), basicInfo.getPic_url().trim(), profilePic,R.drawable.default_profilepic);
            }
        } else if (basicInfo.getPic_url() != null && !basicInfo.getPic_url().isEmpty()) {
            AppUtil.invalidateAndLoadCircularImage(getActivity(), basicInfo.getPic_url().trim(), profilePic,R.drawable.default_profilepic);
        } else if (basicInfo != null && basicInfo.getProfile_pic_path() != null && !basicInfo.getProfile_pic_path().equals("")) {
            File imgFile = new File(basicInfo.getProfile_pic_path());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                if (myBitmap != null) {
                    profilePic.setImageBitmap(myBitmap);
                } else {
                    profilePic.setImageResource(R.drawable.default_profilepic);
                }
            } else {
                profilePic.setImageResource(R.drawable.default_profilepic);
            }
        } else {
            Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profilepic);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), AppUtil.createCircleBitmap(sourceBitmap));
            profilePic.setImageDrawable(drawable);
        }
        if (profilePicPath != null && !profilePicPath.isEmpty()) {
            addPicSymbol.setVisibility(View.GONE);
        } else {
            addPicSymbol.setVisibility(View.VISIBLE);
        }
    }


}


