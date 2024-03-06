package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.doceree.androidadslibrary.ads.DocereeMobileAds;
import com.doceree.androidadslibrary.ads.Hcp;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.BuildConfig;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.AreasOfInterest;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.DashBoardBottomListModel;
import com.vam.whitecoats.core.models.EventInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.core.realm.RealmAcademicInfo;
import com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmEventsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo;
import com.vam.whitecoats.core.realm.RealmProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmProfessionalMembership;
import com.vam.whitecoats.core.realm.RealmPublications;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.AssociationsListAdapter;
import com.vam.whitecoats.ui.adapters.CategoriesAdapter;
import com.vam.whitecoats.ui.adapters.DashBoardListAdapter;
import com.vam.whitecoats.ui.adapters.ViewPagerAdapter;
import com.vam.whitecoats.ui.customviews.CircularImageViewWithBorder;
import com.vam.whitecoats.ui.customviews.CustomRecycleView;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.fragments.AssociationDashboardFragment;
import com.vam.whitecoats.ui.fragments.ChannelsFragment;
import com.vam.whitecoats.ui.fragments.CommunityTabFragment;
import com.vam.whitecoats.ui.fragments.DashboardUpdatesFragment;
import com.vam.whitecoats.ui.fragments.ExploreFragment;
import com.vam.whitecoats.ui.fragments.KnowledgeTabFragment;
import com.vam.whitecoats.ui.fragments.MyNetworkFragment;
import com.vam.whitecoats.ui.fragments.Professional_Fragment;
import com.vam.whitecoats.ui.interfaces.ConnectNotificationPreDataListener;
import com.vam.whitecoats.ui.interfaces.OnCategoryClickListener;
import com.vam.whitecoats.ui.interfaces.OnDataLoadWithList;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.ProfileUpdatedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.ConnectsNotificationInsertionTODB;
import com.vam.whitecoats.utils.CustomViewPager;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.FetchUserConnects;
import com.vam.whitecoats.utils.NavigationType;
import com.vam.whitecoats.utils.ProfileUpdateCollectionManager;
import com.vam.whitecoats.utils.RequestLocationType;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.TabsTagId;
import com.vam.whitecoats.utils.UpShotHelperClass;
import com.vam.whitecoats.utils.UpdateCategoryUnreadCountEvent;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.vam.whitecoats.viewmodel.ExploreViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

import io.realm.Realm;
import io.realm.RealmResults;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class DashboardActivity extends BaseActionBarActivity implements AssociationDashboardFragment.DashboardListener, OnDataLoadWithList, ProfileUpdatedListener {
    public static final String TAG = DashboardActivity.class.getSimpleName();
    MenuItem notificationmenuItem, action_back;
    TextView search_magnifier;
    FrameLayout notificationView;
    private TextView not_count;
    private Realm realm;
    private RealmManager realmManager;
    RealmBasicInfo mysearchinfo;
    TextView updatesTabUnreadCount, channelsTabUnreadCount, chatTabUnreadCount, connectsTabUnreadCount, updatesTabTitle, channelsTabTitle, chatTabTitle, connectsTabTitle;
    Point p;
    static int currenttabid = 0;
    private int connects_count, groups_count, caserooms_count;
    public static final int DELETE_ACTION = 1003;
    Bundle bundleData;
    boolean isChatNotification = false, isDialogNotExists = false;
    private boolean isFromConnectNotification;
    private int tabNumber = 0;
    //private ViewPager dash_viewPager;
    // private TabLayout dash_tabLayout;
    private ViewPagerAdapter adapter;
    private AssociationDashboardFragment associationViewFragment;
    private DashboardUpdatesFragment networkViewFragment;

    private ExploreFragment exploreFragment;
    private ChannelsFragment channelsFragment;
    private MyNetworkFragment network_frag;
    private KnowledgeTabFragment knowledgeTabFragment;
    private CommunityTabFragment communityTabFragment;

    // private FloatingActionButton fab_button_dashboard;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private Dialog dialog;
    private ImageView close_btn;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    View channelsTab;
    private String qbDialogId;
    private MarshMallowPermission marshMallowPermission;
    RecyclerView mRecyclerViewDashboard, mRecyclerViewFeeds, my_recycler_view_media;
    private boolean pendingIntroAnimation;
    private RealmBasicInfo basicInfo;
    private UpShotHelperClass upshotHelper;
    private MySharedPref sharedPrefObj = null;
    private int selectedTab;
    private Menu mMenu;
    private CustomViewPager dash_viewPager;
    private BottomNavigationView bottomNavigationView;
    private View notificationBadgeChannel, notificationBadgeChat;
    private TextView channelTabCount, chatTabCount;
    private ImageView notification_icon;
    private ArrayList<JSONObject> associationList;
    private CustomRecycleView membersListview;
    private View pagesTab;
    boolean defaultChannel;
    private FloatingActionButton fab_button_dashboard;
    private boolean displayUserPreferredChannel;
    private boolean firstRunExperience;
    private int mFeedsNotificationsCount;
    private int networkNotificationsCount;
    private int networkNotificationCount = 0;
    private boolean isNeedUpdateNotiCount = false;
    private boolean setNotificationCount = false;
    private Menu mainMenu;
    private JSONArray chooseCommunityJsonObj = new JSONArray();
    private FloatingActionButton post_fab_button_dashboard;
    public static SparseArray<JSONObject> channelsList = new SparseArray<>();
    public static int networkChannelId;
    private ExtendedFloatingActionButton post_fab_button;
    private CircularImageViewWithBorder profile_image;
    private CardView no_profile_back;
    private TextView no_profile_text;
    private boolean isContextMenuLoaded = false;
    private TextView fab_post_text;
    private String profileText;
    private Boolean isProfileVisible = true;
    private BasicInfo bi;
    private RecyclerView categoryRecyclerView;
    private ViewGroup categoryListLayout;
    private ArrayList<Category> categoryList = new ArrayList();
    private View categoryListDummyView;
    private ExploreViewModel dataViewModel;
    private CategoriesAdapter categoriesAdapter;
    private ProfessionalInfo professionalInfo;
    private ActivityResultLauncher<Intent> launchActivityForResults;
    private AppUpdateManager appUpdateManager;
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout image_lay;
    private TextView nav_doc_name;
    private TextView nav_doc_spec;
    private TextView nav_doc_share_profile;
    private View navHeader;
    private RelativeLayout myPostMenu, myBookMarks, myHomePreferences, mySpecialityPreferences, notificationPreferences, inviteToWhiteCoats, help, change_password_tv, logout_tv;
    private TextView terms_of_use_tv, version_num_tv;

    private LinearLayoutManager linearLayoutManager;
    private Boolean isListExhausted;
    private boolean loading;
    private int page_num = 0;
    private CircularImageViewWithBorder nav_profile_image;
    private CardView nav_no_profile_back;
    private TextView nav_no_profile_text;
    private FrameLayout nav_image_lay;
    private Professional_Fragment professionalFragment;
    private boolean firstrun;
    private boolean is_user_active;
    private int overAllExperience;
    private TextView privacy_policy;
    private LinearLayout name_spec_lay;
    private ArrayList<DashBoardBottomListModel> dashBoardBottomModelList;
    public static ArrayList<JSONObject> communityList;
    /*Issue with wc_user_event data*/
    public static boolean isKnowledgeTabFragInitFirstTime = false;
    public static boolean isCommunityTabFragInitFirstTime = false;
    public static boolean isProfessionalTabFragInitFirstTime = false;
    public boolean isKnowledgeDeepLink = true;
    public boolean isCommunityTabDeepLink = true;
    public boolean isProfessionalDeepLink = true;
    public static int knowledgeCategoryTypeID = 1;
    public static int communityCategoryTypeID = 4;
    public static int professionalCategoryTypeID = 8;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    String[] permissionSDK33HigherNotifi = new String[]{Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.READ_PHONE_STATE};
    private final int PERMISSION_CALLBACK_CONSTANT_NOTIFICATION = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        /*Issue with wc_user_event data*/
        isKnowledgeTabFragInitFirstTime = true;
        isCommunityTabFragInitFirstTime = true;
        isProfessionalTabFragInitFirstTime = true;
        associationViewFragment = AssociationDashboardFragment.newInstance(this);
        communityTabFragment = CommunityTabFragment.newInstance("", "");
        knowledgeTabFragment = KnowledgeTabFragment.newInstance("", "");
        exploreFragment = ExploreFragment.newInstance("", "");
        professionalFragment = Professional_Fragment.newInstance("", "");

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }
        EventBus.getDefault().register(this);

        appBarLayout = (AppBarLayout) findViewById(R.id.dashBoardAppBarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_dashboard);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        sharedPrefObj = new MySharedPref(this);
        boolean isParallelCall = true;
        bundleData = getIntent().getExtras();
        realm = Realm.getDefaultInstance();//getInstance(this);
        realmManager = new RealmManager(this);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        professionalInfo = realmManager.getProfessionalInfoOfShowoncard(realm);
        firstRunExperience = sharedPrefObj.getPref("EXPERIENCE", true);
        if (basicInfo.getOverAllExperience() != 999) {
            sharedPrefObj.savePref("is_user_active", true);
        }
        is_user_active = sharedPrefObj.getPref("is_user_active", false);

        /*Show the runtime notification permission propmt for Android OS level 13 and more*/
        showNotifPermissionPrompt();

        //AppUpdate
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                removeInstallStateUpdateListener();
            } else {
                Toast.makeText(getApplicationContext(), "InstallStateUpdatedListener: state: " + state.installStatus(), Toast.LENGTH_LONG).show();
            }
        };
        profile_image = (CircularImageViewWithBorder) findViewById(R.id.profile_image);
        no_profile_back = findViewById(R.id.no_profile_image);
        no_profile_text = findViewById(R.id.no_profile_text);
        image_lay = findViewById(R.id.image_lay);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        appUpdateManager.registerListener(installStateUpdatedListener);

        navHeader = navigationView.getHeaderView(0);

        nav_doc_name = navHeader.findViewById(R.id.nav_doc_name);
        nav_doc_spec = navHeader.findViewById(R.id.nav_doc_spec);
        nav_doc_share_profile = navHeader.findViewById(R.id.nav_doc_share_profile);
        //nav_spec_edit_lay = navHeader.findViewById(R.id.nav_spec_edit_lay);
        nav_profile_image = navHeader.findViewById(R.id.profile_image_nav);
        nav_no_profile_back = navHeader.findViewById(R.id.no_profile_image_nav);
        nav_no_profile_text = navHeader.findViewById(R.id.no_profile_text_nav);
        nav_image_lay = navHeader.findViewById(R.id.image_lay_nav);

        myPostMenu = navHeader.findViewById(R.id.myPostMenu);
        myBookMarks = navHeader.findViewById(R.id.myBookMarks);
        myHomePreferences = navHeader.findViewById(R.id.myHomePreferences);
        mySpecialityPreferences = navHeader.findViewById(R.id.mySpecialityPreferences);
        notificationPreferences = navHeader.findViewById(R.id.notificationPreferences);
        inviteToWhiteCoats = navHeader.findViewById(R.id.inviteToWhiteCoats);
        help = navHeader.findViewById(R.id.help);
        terms_of_use_tv = navHeader.findViewById(R.id.terms_of_use_tv);
        change_password_tv = navHeader.findViewById(R.id.change_password_tv);
        logout_tv = navHeader.findViewById(R.id.logout_tv);
        version_num_tv = navHeader.findViewById(R.id.version_num_tv);
        privacy_policy = navHeader.findViewById(R.id.privacy_policy);
        name_spec_lay = (LinearLayout) navHeader.findViewById(R.id.name_spec_lay);
        nav_doc_name.setText(basicInfo.getUser_salutation() + " " + basicInfo.getFname() + " " + basicInfo.getLname());
        nav_doc_spec.setText(basicInfo.getSplty());

        loadHamburgerMenuProfileIcon();
        //Display dummy upshot survey
        upshotHelper = UpShotHelperClass.getInstance();
        getExternalSpeciality(basicInfo.getSplty());
        getAdSlotDefinitions();
        checkForUpdate();
        if (sharedPrefObj.getPref("NotificationCountMigration", true)) {
            connects_count = realmManager.getNotificationCount(realm);
            groups_count = realmManager.getGroupNotificationCount(realm);
            caserooms_count = realmManager.getCRNotificationCount(realm);
            int totalNotificationsCount = connects_count + groups_count + caserooms_count;
            sharedPrefObj.savePref("networkNotificationCount", totalNotificationsCount);
            sharedPrefObj.savePref("NotificationCountMigration", false);
        }
        loadProfilePic();
        ProfileUpdateCollectionManager.registerListener(this);
        //check for Connect notification DB

        if (sharedPrefObj.getPref("ConnectNotificationMigration", true)) {
            //clear connect notifications table
            realmManager.clearConnectNotificationsDB();
            sharedPrefObj.savePref("ConnectNotificationMigration", false);
        }
        if (sharedPrefObj.getPref("connects_notification_sync_completed", true)) {
            new ConnectsNotificationInsertionTODB(DashboardActivity.this, realmManager, realm, realmManager.getDoc_id(realm), true, false, realmManager.getNotificationDataFromDB(), new ConnectNotificationPreDataListener() {
                @Override
                public void notifyUIWithPreData(JSONArray data) {
                    if (data.length() > 0) {
                        networkNotificationCount = mySharedPref.getPref("networkNotificationCount", 0);
                        for (int i = 0; i < data.length(); i++) {
                            networkNotificationCount++;
                        }
                        mySharedPref.savePref("networkNotificationCount", networkNotificationCount);
                        setNotificationCount = true;
                        setNotificationCountOnBadge();

                    }
                }
            });

        } else {
            if (realmManager.getNotificationDataFromDB() != null && realmManager.getNotificationDataFromDB().size() > 0) {
                new ConnectsNotificationInsertionTODB(DashboardActivity.this, realmManager, realm, realmManager.getDoc_id(realm), false, true, realmManager.getNotificationDataFromDB(), new ConnectNotificationPreDataListener() {
                    @Override
                    public void notifyUIWithPreData(JSONArray data) {
                        if (data.length() > 0) {
                            networkNotificationCount = mySharedPref.getPref("networkNotificationCount", 0);
                            for (int i = 0; i < data.length(); i++) {
                                networkNotificationCount++;
                            }
                            mySharedPref.savePref("networkNotificationCount", networkNotificationCount);
                            setNotificationCount = true;
                        }

                    }
                });
            }
        }


        //check for notification settings
        checkForNotificationSettings();
        if (bundleData != null) {
            if (bundleData.containsKey(RestUtils.TAG_IS_PARALLEL_CALL)) {
                isParallelCall = bundleData.getBoolean(RestUtils.TAG_IS_PARALLEL_CALL, true);
            }
            if (bundleData.containsKey(RestUtils.TAG_IS_CHAT_NOTIFICATION)) {
                isChatNotification = bundleData.getBoolean(RestUtils.TAG_IS_CHAT_NOTIFICATION, false);
            }
            if (bundleData.containsKey(RestUtils.TAG_IS_CONNECT_NOTIFICATION)) {
                isFromConnectNotification = bundleData.getBoolean(RestUtils.TAG_IS_CONNECT_NOTIFICATION, false);
                tabNumber = bundleData.getInt("TAB_NUMBER");
            }
            if (bundleData.containsKey("non_stored_dialog_msg")) {
                isDialogNotExists = bundleData.getBoolean("non_stored_dialog_msg", false);
            }

            if (bundleData.containsKey("qb_dialog_id")) {
                qbDialogId = bundleData.getString("qb_dialog_id");
            }
        }
        /*
         * Dashboard & QB parallel call goes here
         */
        if (isParallelCall) {
            // establishQBConnection();
            new FetchUserConnects(DashboardActivity.this, basicInfo.getDoc_id(), mySharedPref.getPrefsHelper().getPref("last_doc_id", 0), true, realm, realmManager, new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                }
            });
            if (isFromConnectNotification) {
                hideProgress();
                Intent intent = new Intent(DashboardActivity.this, NotificationsActivity.class);
                intent.putExtra("TAB_NUMBER", tabNumber);
                isNeedUpdateNotiCount = true;

                startActivity(intent);

            }
        }
        editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        AppConstants.COACHMARK_INCREMENTER = sharedPrefObj.getPref(MySharedPref.PREF_COACHMARK_INCREMENTER, 1);
        if (AppConstants.COACHMARK_INCREMENTER == 1) {
            sharedPrefObj.savePref(MySharedPref.PREF_COACHMARK_INCREMENTER, 2);
        } else if (AppConstants.COACHMARK_INCREMENTER == 2) {
            sharedPrefObj.savePref(MySharedPref.PREF_COACHMARK_INCREMENTER, 3);
        } else if (AppConstants.COACHMARK_INCREMENTER == 3) {
            sharedPrefObj.savePref(MySharedPref.PREF_COACHMARK_INCREMENTER, 4);
        }

        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
        launchActivityForResults = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == 10101) {
                            if (!marshMallowPermission.requestPhoneAndMediaPermissions()) {
                            }
                            DashboardActivity.this.getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("firstrun", false)
                                    .commit();
                        } else if (result.getResultCode() == 7777) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (firstrun) {
                                    Intent intent = new Intent(DashboardActivity.this, PermissionAccessActivity.class);
                                    //startActivityForResult(intent, 10101);
                                    launchActivityForResults.launch(intent);
                                } else {
                                    if (AppUtil.checkPhoneStatePermission(DashboardActivity.this)) {
                                        //new PlayServicesHelper(DashboardActivity.this);
                                        marshMallowPermission.requestPermissionForContacts();
                                    }
                                    if (firstRunExperience) {
                                        if (displayUserPreferredChannel) {
                                            if (associationList != null) {
                                                Intent intent = new Intent(DashboardActivity.this, DefaultHomePageActivity.class);
                                                intent.putExtra("AssociationList", associationList.toString());
                                                //startActivityForResult(intent, 50505);
                                                launchActivityForResults.launch(intent);
                                            }
                                        }
                                    }

                                }
                            } else {
                                if (firstRunExperience) {
                                    if (displayUserPreferredChannel) {
                                        if (associationList != null) {
                                            Intent intent = new Intent(DashboardActivity.this, DefaultHomePageActivity.class);
                                            intent.putExtra("AssociationList", associationList.toString());
                                            //startActivityForResult(intent, 50505);
                                            launchActivityForResults.launch(intent);
                                        }
                                    } else {
                                        sharedPrefObj.savePref("EXPERIENCE", false);
                                    }

                                }
                            }
                            sharedPrefObj.savePref("mandatory_completed", true);
                            JSONObject requestData = new JSONObject();
                            //request data
                            try {
                                requestData.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
                                requestData.put("last_feed_time", 0);
                                requestData.put("last_open", 0);
                                requestData.put("last_close", 0);
                                requestData.put("load_next", true);
                                requestData.put("pg_num", 0);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (basicInfo.getUserUUID() == null || basicInfo.getUserUUID().isEmpty()) {
                                associationViewFragment.setUpUserProfile();
                            }
                            associationViewFragment.requestForDashBoardData(requestData, false, false);
                            nav_doc_spec.setText(basicInfo.getSplty());
                        } else if (result.getResultCode() == 50505) {
                            sharedPrefObj.savePref("EXPERIENCE", false);

                            Bundle bundle = result.getData().getExtras();
                            int selectedPosition = 0;
                            if (bundle != null) {
                                selectedPosition = bundle.getInt("ListPosition");
                            }

                            requestForChannelSelection(selectedPosition);
                        }
                        if (associationViewFragment != null) {
                            associationViewFragment.onActivityResult(result.getResultCode(), result.getResultCode(), result.getData());
                        }

                    }
                });
        initialize();
        String eventName = "";
        if (dash_viewPager.getCurrentItem() == 0) {
            eventName = "Dashboard";
        } else if (dash_viewPager.getCurrentItem() == 1) {
            eventName = "Doctors";
        } else if (dash_viewPager.getCurrentItem() == 2) {
            eventName = "Pages";
        } else if (dash_viewPager.getCurrentItem() == 3) {
            eventName = "Explore";
        }
        upshotEventData(0, 0, 0, "", eventName, "", "", " ", false);
        setUpNavigationView();
        image_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "MyProfileTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        version_num_tv.setText("Version " + BuildConfig.VERSION_NAME);
        name_spec_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.logUserEventWithDocIDAndSplty("MyProfileView", basicInfo, DashboardActivity.this);
                Intent intent_profile = new Intent(DashboardActivity.this, ProfileViewActivity.class);
                startActivity(intent_profile);
                drawerLayout.closeDrawers();
            }
        });
        nav_doc_share_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ShareProfileTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String userProfileURL = basicInfo.getDocProfileURL();
                if (userProfileURL.isEmpty()) {
                    return;
                }
                if (!AppUtil.isConnectingToInternet(DashboardActivity.this)) {
                    Toast.makeText(DashboardActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject requestObj = new JSONObject();
                try {
                    requestObj.put(RestUtils.TAG_USER_ID, basicInfo.getDoc_id());
                    requestObj.put(RestUtils.TAG_SHARED_USER_ID, basicInfo.getDoc_id());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String prefixMsg = basicInfo.getUser_salutation() + " " + basicInfo.getFname() + " " + basicInfo.getLname() + " has shared his profile from Whitecoats. Visit profile by clicking";
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, prefixMsg + " " + userProfileURL);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                AppUtil.sendUserActionEventAPICall(basicInfo.getDoc_id(), "ShareOwnProfile", requestObj, DashboardActivity.this);
                drawerLayout.setSelected(false);
            }
        });

        myPostMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "MyPostTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(DashboardActivity.this, MyBookmarksActivity.class);
                    intent.putExtra("Navigation", "MyPosts");
                    intent.putExtra(RestUtils.TAG_OTHER_USER_ID, basicInfo.getDoc_id());
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                }
            }
        });
        myBookMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "MyBookmarksTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(DashboardActivity.this, MyBookmarksActivity.class);
                intent.putExtra(RestUtils.NAVIGATATION, "Bookmarks");
                startActivity(intent);
                drawerLayout.closeDrawers();
            }
        });
        myHomePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (associationList != null) {
                    displayPreferredChannelDialog(false);
                    drawerLayout.closeDrawers();
//                    navigationView.getMenu().getItem(R.id.nav_home_preferences).setChecked(false);

                }
            }
        });
        mySpecialityPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "SpecialityPreferencesTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent1 = new Intent(DashboardActivity.this, PreferencesActivity.class);
                intent1.putExtra(RestUtils.CHANNEL_ID, 0);
                //startActivityForResult(intent, PREFERENCE_ACTION);
                launchActivityForResults.launch(intent1);
                drawerLayout.closeDrawers();
            }
        });
        notificationPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launch new intent instead of loading fragment
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "NotificationPreferencesTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent NotificationSettingsActivity = new Intent(mContext, NotificationSettingsActivity.class);
                startActivity(NotificationSettingsActivity);
                drawerLayout.closeDrawers();
            }
        });
        inviteToWhiteCoats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "InvitetoWhiteCoatsTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent shareIntent1 = new Intent();
                shareIntent1.setAction(Intent.ACTION_SEND);
                shareIntent1.setType("text/plain");
                String link_val = "https://invite.whitecoats.com";
                String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
                shareIntent1.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
                shareIntent1.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.inviate_to_whiteCoats) + Html.fromHtml(body));
                startActivity(Intent.createChooser(shareIntent1, "Invite via"));
                drawerLayout.closeDrawers();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "HelpTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intentsupport = new Intent(getApplicationContext(), ContactSupport.class);
                startActivity(intentsupport);
                drawerLayout.closeDrawers();
            }
        });
        terms_of_use_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "TermsOfUseTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intentTerms = new Intent(getApplicationContext(), TermsOfServiceActivity.class);
                intentTerms.putExtra("NavigationFrom", "terms_of_use");
                startActivity(intentTerms);
            }
        });
        privacy_policy.setOnClickListener(view -> {
            Intent intentTerms = new Intent(getApplicationContext(), TermsOfServiceActivity.class);
            intentTerms.putExtra("NavigationFrom", "privacy_policy");
            startActivity(intentTerms);

        });
        logout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtil.isConnectingToInternet(DashboardActivity.this)) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "LogoutTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showProgress();
                    new VolleySinglePartStringRequest(DashboardActivity.this, Request.Method.POST, RestApiConstants.LOGOUT, "", "LOGOUT", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            hideProgress();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(successResponse);
                                if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                    //QBUsers.signOut();
                                    sharedPrefObj.savePref(MySharedPref.PREF_SESSION_TOKEN, "");
                                    sharedPrefObj.savePref(MySharedPref.STAY_LOGGED_IN, false);
                                    sharedPrefObj.savePref(MySharedPref.PREF_REGISTRATION_FLAG, true);
                                    sharedPrefObj.savePref(MySharedPref.PREF_USER_PASSWORD, null);
                                    Intent intentLogout = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intentLogout);

                                } else {
                                    Toast.makeText(DashboardActivity.this, getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            hideProgress();
                            displayErrorScreen(errorResponse);
                        }
                    }).sendSinglePartRequest();
                }

            }
        });

        deepLinkingNavigation("fromOnCreate");

    }

    private void showNotifPermissionPrompt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(DashboardActivity.this, permissionSDK33HigherNotifi[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DashboardActivity.this, permissionSDK33HigherNotifi, PERMISSION_CALLBACK_CONSTANT_NOTIFICATION);
            }
        }
    }

    private void loadHamburgerMenuProfileIcon() {

        String picUrl = (basicInfo.getPic_url() != null) ? basicInfo.getPic_url().trim() : "";
        if (picUrl != null && !picUrl.isEmpty()) {
            nav_no_profile_back.setVisibility(GONE);
            nav_profile_image.setVisibility(VISIBLE);
            AppUtil.invalidateAndLoadCircularImage(this, basicInfo.getPic_url().trim(), nav_profile_image, R.drawable.default_profilepic);
        } else {
            /*Checking the null condition for first and last name and if null the assign empty string to profileText*/
            if (basicInfo.getFname() != null && basicInfo.getLname() != null) {
                profileText = basicInfo.getFname().toUpperCase().charAt(0) + "" + basicInfo.getLname().toUpperCase().charAt(0);
            } else {
                profileText = "";
            }
            nav_no_profile_text.setText(profileText);
            nav_no_profile_back.setCardBackgroundColor(changeBackground());
            nav_no_profile_back.setVisibility(VISIBLE);
            nav_profile_image.setVisibility(GONE);

        }

    }

    public void checkForUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                startUpdateFlow(appUpdateInfo);
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, FLEXIBLE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.button_backround))
                .show();
    }

    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeInstallStateUpdateListener();
    }

    private void getAdSlotDefinitions() {
        new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.GET_AD_SLOT_DEFINITIONS, getAdSlotsRequest(), "GET_AD_SLOTS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (successResponse != null && !successResponse.isEmpty()) {
                    try {
                        JSONObject responseObj = new JSONObject(successResponse);
                        if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            JSONObject data = responseObj.getJSONObject(RestUtils.TAG_DATA);
                            if (data.has("ads_definitions")) {
                                realmManager.clearAdSlotDefinitionsData();
                                realmManager.addOrUpdateAdslot(data.optJSONArray("ads_definitions"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                if (errorResponse != null) {
                    Log.i("AdSlotsError", errorResponse);
                }
            }
        }).sendSinglePartRequest();
    }

    private String getAdSlotsRequest() {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_USER_ID, basicInfo.getDoc_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj.toString();
    }

    /*API Binding to map Doceree taxonomy with network specialties*/
    private void getExternalSpeciality(String speciality) {
        JSONObject resObj = new JSONObject();
        try {
            resObj.put("specialist", speciality);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = RestApiConstants.getExternalSpeciality;
        new VolleySinglePartStringRequest(this, Request.Method.POST, URL, resObj.toString(), "DOCEREE_SPECIALITY", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                try {
                    JSONObject res = new JSONObject(successResponse);
                    if (res.has("data")) {
                        JSONObject data = res.getJSONObject("data");
                        String externalSpeciality = data.getString("speciality");
                        doHcpLogin(externalSpeciality);
                    } else {
                        doHcpLogin(basicInfo.getSplty());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    doHcpLogin(basicInfo.getSplty());
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                doHcpLogin(basicInfo.getSplty());
            }
        }).sendSinglePartRequest();
    }

    private void doHcpLogin(String externalSpeciality) {
        Hcp hcp = new Hcp.HcpBuilder()
                .setSpecialization(externalSpeciality)
                .setCity(professionalInfo.getLocation())
                .build();
        DocereeMobileAds.getInstance().loginWith(hcp);
    }


    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void initialize() {
        Log.i(TAG, "initialize()");
        try {
            Bundle b = new Bundle();
            b.putString("tag", "WhiteCoats");
            b.putString("tag", "Messages");
            AppConstants.login_doc_id = realmManager.getDoc_id(realm);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dash_viewPager = (CustomViewPager) findViewById(R.id.dash_viewpager);
            fab_button_dashboard = (FloatingActionButton) findViewById(R.id.fab_button_dashboard);
            post_fab_button_dashboard = (FloatingActionButton) findViewById(R.id.post_fab);
            fab_post_text = (TextView) findViewById(R.id.fab_post_text);

            post_fab_button_dashboard.setEnabled(false);
            bottomNavigationView = (BottomNavigationView)
                    findViewById(R.id.navigation);

            bottomNavigationView.setBackground(null);
            bottomNavigationView.getMenu().getItem(2).setEnabled(false);
            categoryRecyclerView = (RecyclerView) findViewById(R.id.category_list_dashboard);
            categoryListLayout = (ViewGroup) findViewById(R.id.category_list_layout);

            categoryListDummyView = (View) findViewById(R.id.category_list_dummy_view);

            dataViewModel = ViewModelProviders.of(this).get(ExploreViewModel.class);
            dash_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.e(TAG, "onPageScrolled - " + position);
                }

                @SuppressLint("RestrictedApi")
                @Override
                public void onPageSelected(int position) {
                    Log.e(TAG, "onPageSelected - " + position);
                    currenttabid = position;
                    String eventName = "";
                    if (position == 0) {
                        eventName = "Dashboard";
                    } else if (position == 1) {
                        eventName = "Doctors";
                    } else if (position == 2) {
                        eventName = "Pages";
                    } else if (position == 3) {
                        eventName = "ExploreTapped";
                    }
                    upshotEventData(0, 0, 0, "", eventName, "", "", " ", false);
                    if (position == 0) {
                        fab_button_dashboard.setVisibility(View.GONE);
                        categoryListLayout.setVisibility(View.VISIBLE);
                        categoryListDummyView.setVisibility(VISIBLE);
                    } else if (position == 1) {
                        categoryListLayout.setVisibility(View.GONE);
                        categoryListDummyView.setVisibility(GONE);
                        // Hide the floating action button
                        fab_button_dashboard.setVisibility(View.GONE);
                    } else if (position == 2) {
                        categoryListDummyView.setVisibility(GONE);
                        categoryListLayout.setVisibility(View.GONE);
                        fab_button_dashboard.setVisibility(GONE);//new edited for knowledge module
                        fab_button_dashboard.setImageResource(R.drawable.fab_ic_search);
                    } else if (position == 3) {
                        selectedTab = 2;
                        categoryListLayout.setVisibility(View.GONE);
                        categoryListDummyView.setVisibility(GONE);
                        fab_button_dashboard.setVisibility(View.GONE);
                        fab_button_dashboard.setVisibility(GONE);
                        fab_button_dashboard.setImageResource(R.drawable.fab_ic_message);
                        JSONObject jsonObject = new JSONObject();
                        if (professionalFragment != null && professionalFragment.getView() != null) {
                            professionalFragment.setFilterStatus();
                        }
                    } else {
                        selectedTab = 1;
                    }
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.e(TAG, "onPageScrollStateChanged - " + state);

                }
            });


            categoriesAdapter = new CategoriesAdapter(DashboardActivity.this, categoryList);
            categoriesAdapter.setCategoryClickListener(new OnCategoryClickListener() {
                @Override
                public void onCategoryItemClick(Category categoryItem) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("CategoryName", categoryItem.getCategoryName());
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DashboardCategoryTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int categoryTagId = categoryItem.getTagId();
                    if (categoryTagId > 0) {
                        if (categoryTagId == 1 || categoryTagId == 2 || categoryTagId == 3) {
                            /*Issue with wc_user_event data*/
                            if (categoryTagId == 1) {
                                isKnowledgeDeepLink = knowledgeCategoryTypeID == 1;
                            } else if (categoryTagId == 2) {
                                isKnowledgeDeepLink = knowledgeCategoryTypeID == 2;
                            } else {
                                isKnowledgeDeepLink = knowledgeCategoryTypeID == 3;
                            }
                            knowledgeTabFragment.tabNavigation(categoryTagId);
                            bottomNavigationView.setSelectedItemId(R.id.action_knowledge);
                        } else if (categoryTagId == 4 || categoryTagId == 5 || categoryTagId == 6 || categoryTagId == 7) {
                            /*Issue with wc_user_event data*/
                            if (categoryTagId == 4) {
                                isCommunityTabDeepLink = communityCategoryTypeID == 4;
                            } else if (categoryTagId == 5) {
                                isCommunityTabDeepLink = communityCategoryTypeID == 5;
                            } else if (categoryTagId == 6) {
                                isCommunityTabDeepLink = communityCategoryTypeID == 6;
                            } else {
                                isCommunityTabDeepLink = communityCategoryTypeID == 7;
                            }
                            communityTabFragment.navigateToNetworkTab(categoryTagId);
                            bottomNavigationView.setSelectedItemId(R.id.action_community);
                        } else if (categoryTagId == 8 || categoryTagId == 9 || categoryTagId == 10 || categoryTagId == 11) {
                            /*Issue with wc_user_event data*/
                            if (categoryTagId == 8) {
                                isProfessionalDeepLink = professionalCategoryTypeID == 8;
                            } else if (categoryTagId == 9) {
                                isProfessionalDeepLink = professionalCategoryTypeID == 9;
                            } else if (categoryTagId == 10) {
                                isProfessionalDeepLink = professionalCategoryTypeID == 10;
                            } else {
                                isProfessionalDeepLink = professionalCategoryTypeID == 11;
                            }
                            professionalFragment.tabNavigation(categoryTagId);
                            bottomNavigationView.setSelectedItemId(R.id.action_explore);
                        }
                    } else {
                        if (AppUtil.isConnectingToInternet(DashboardActivity.this)) {
                            if ((categoryItem.getCategoryType().equalsIgnoreCase("DrugsCategory"))) {
                                Intent intent = new Intent(DashboardActivity.this, DrugClassActivity.class);
                                startActivity(intent);
                                EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnRegularUnreadCountUpdate", 0, categoryItem.getCategoryId()));
                            } else {
                                Intent intent = new Intent(DashboardActivity.this, CategoryLoadingActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("CurrentCategoryDetails", categoryItem);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }

                    }
                }
            });
            linearLayoutManager = new LinearLayoutManager(DashboardActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(linearLayoutManager);
            categoryRecyclerView.setAdapter(categoriesAdapter);

            categoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    categoryRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DashboardCategoryScroll", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int visiblecount = linearLayoutManager.getChildCount();
                            int toatlcount = linearLayoutManager.getItemCount();
                            int pastitem = linearLayoutManager.findFirstVisibleItemPosition();
                            int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                            if (isListExhausted) {
                                return;
                            }
                            if (!loading) {
                                if (lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount - 1) && AppUtil.isConnectingToInternet(DashboardActivity.this)) {
                                    page_num += 1;
                                    requestForCategoriesData(page_num);
                                    loading = true;
                                } else {
                                    loading = false;
                                }
                            }
                        }
                    });

                    super.onScrolled(recyclerView, dx, dy);
                }
            });

            dataViewModel.isListExhausted().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    isListExhausted = aBoolean;
                    if (aBoolean) {
                        // Toast.makeText(DashboardActivity.this,"No more categories",Toast.LENGTH_SHORT).show();
                        categoriesAdapter.setListExhausted();
                    }
                }
            });


            fab_button_dashboard.setOnClickListener((View.OnClickListener) v -> {
                if (dash_viewPager.getCurrentItem() == 0) {

                } else if (dash_viewPager.getCurrentItem() == 1) {
                    // Here goes Channels Tab stuffs
                } else if (dash_viewPager.getCurrentItem() == 2) {
                    Intent inviteIntent = new Intent(DashboardActivity.this, NetworkSearchActivity.class);
                    inviteIntent.putExtra("Feeds_checked", false);
                    startActivity(inviteIntent);
                } else if (dash_viewPager.getCurrentItem() == 3) {
                    dialog = new Dialog(DashboardActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.activity_dialog_overlay);
                    close_btn = (ImageView) dialog.findViewById(R.id.btn_close);
                    LinearLayout newgroup_layout, newmsg_layout;
                    newgroup_layout = (LinearLayout) dialog.findViewById(R.id.newgroup_layout);
                    newmsg_layout = (LinearLayout) dialog.findViewById(R.id.newmsg_layout);
                    newmsg_layout.setOnClickListener(v12 -> {
                        dialog.dismiss();
                        if (AppUtil.getUserVerifiedStatus() == 3) {
                            Intent startConversation = new Intent(DashboardActivity.this, Network_MyConnects.class);
                            startActivity(startConversation);
                        }
                    });
                    newgroup_layout.setOnClickListener(v1 -> {
                        dialog.dismiss();
                       /* if (AppUtil.getUserVerifiedStatus() == 3) {
                            Intent creategroup = new Intent(DashboardActivity.this, CreateGroupActivity.class);
                            startActivity(creategroup);
                        } else*/
                        if (AppUtil.getUserVerifiedStatus() == 1) {
                            AppUtil.AccessErrorPrompt(DashboardActivity.this, getString(R.string.mca_not_uploaded));
                        } else if (AppUtil.getUserVerifiedStatus() == 2) {
                            AppUtil.AccessErrorPrompt(DashboardActivity.this, getString(R.string.mca_uploaded_but_not_verified));
                        }
                    });

                    dialog.show();
                    addTransparency(dialog);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;

                    wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(wlp);
                    close_btn.setOnClickListener(v13 -> {
                        dialog.dismiss();
                    });
                }
            });


            post_fab_button_dashboard.setOnClickListener(view -> {

                postFabButtonClick();
            });

            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                Fragment selectedFragment = null;
                JSONObject jsonObject;
                /*ENGG-3040 -- Add App Version in Upshot Events*/
                String appVersion = String.valueOf(App_Application.getInstance().getAppVersion());

                switch (item.getItemId()) {
                    case R.id.action_home:
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("DocID", realmManager.getUserUUID(realm));
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "HomeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        selectedTab = 1;
                        dash_viewPager.setCurrentItem(0);
                        return true;
                    case R.id.action_knowledge:
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("DocID", realmManager.getUserUUID(realm));
                            /*ENGG-3040 -- Add App Version in Upshot Events*/
                            jsonObject.put(RestUtils.EVENT_APP_VERSION, appVersion);
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "KnowledgeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                            /*Issue with wc_user_event data*/
                            JSONObject jsonObjectKnowledge = new JSONObject();
                            if (isKnowledgeDeepLink) {
                                if (knowledgeCategoryTypeID == 1) {
                                    jsonObjectKnowledge.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectKnowledge.put("TabName", "Feeds");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "KnowledgeTabTapped", jsonObjectKnowledge, AppUtil.convertJsonToHashMap(jsonObjectKnowledge), DashboardActivity.this);
                                } else if (knowledgeCategoryTypeID == 2) {
                                    jsonObjectKnowledge.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectKnowledge.put("TabName", "Drug Reference");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "KnowledgeTabTapped", jsonObjectKnowledge, AppUtil.convertJsonToHashMap(jsonObjectKnowledge), DashboardActivity.this);
                                } else if (knowledgeCategoryTypeID == 3) {
                                    jsonObjectKnowledge.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectKnowledge.put("TabName", "Medical Events");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "KnowledgeTabTapped", jsonObjectKnowledge, AppUtil.convertJsonToHashMap(jsonObjectKnowledge), DashboardActivity.this);
                                }
                            } else {
                                isKnowledgeDeepLink = true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        selectedTab = 2;
                        dash_viewPager.setCurrentItem(1);
                        return true;
                    case R.id.action_community:
                        CommunityTabFragment.tabSelected = "COMMUNITY_TAB";
                        Professional_Fragment.tabSelected = "";
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("DocID", realmManager.getUserUUID(realm));
                            /*ENGG-3040 -- Add App Version in Upshot Events*/
                            jsonObject.put(RestUtils.EVENT_APP_VERSION, appVersion);
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                            /*Issue with wc_user_event data*/
                            JSONObject jsonObjectCommunity = new JSONObject();
                            if (isCommunityTabDeepLink) {
                                if (communityCategoryTypeID == 4) {
                                    jsonObjectCommunity.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectCommunity.put("TabName", "Spotlights");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTypeTapped", jsonObjectCommunity, AppUtil.convertJsonToHashMap(jsonObjectCommunity), DashboardActivity.this);
                                } else if (communityCategoryTypeID == 5) {
                                    jsonObjectCommunity.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectCommunity.put("TabName", "Feeds");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTypeTapped", jsonObjectCommunity, AppUtil.convertJsonToHashMap(jsonObjectCommunity), DashboardActivity.this);

                                } else if (communityCategoryTypeID == 6) {
                                    jsonObjectCommunity.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectCommunity.put("TabName", "Doctors");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTypeTapped", jsonObjectCommunity, AppUtil.convertJsonToHashMap(jsonObjectCommunity), DashboardActivity.this);

                                } else if (communityCategoryTypeID == 7) {
                                    jsonObjectCommunity.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectCommunity.put("TabName", "Organizations");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTypeTapped", jsonObjectCommunity, AppUtil.convertJsonToHashMap(jsonObjectCommunity), DashboardActivity.this);
                                }
                            } else {
                                isCommunityTabDeepLink = true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        selectedTab = 3;
                        dash_viewPager.setCurrentItem(2);
                        return true;
                    case R.id.action_explore:
                        CommunityTabFragment.tabSelected = "";
                        Professional_Fragment.tabSelected = "PROFESSIONAL_TAB";
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("DocID", realmManager.getUserUUID(realm));
                            /*ENGG-3040 -- Add App Version in Upshot Events*/
                            jsonObject.put(RestUtils.EVENT_APP_VERSION, appVersion);
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                            /*Issue with wc_user_event data*/
                            if (isProfessionalDeepLink) {
                                JSONObject jsonObjectProfessional = new JSONObject();
                                if (professionalCategoryTypeID == 8) {
                                    jsonObjectProfessional.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectProfessional.put("TabName", "Feeds");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTypeTapped", jsonObjectProfessional, AppUtil.convertJsonToHashMap(jsonObjectProfessional), DashboardActivity.this);
                                } else if (professionalCategoryTypeID == 9) {
                                    jsonObjectProfessional.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectProfessional.put("TabName", "Skilling");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTypeTapped", jsonObjectProfessional, AppUtil.convertJsonToHashMap(jsonObjectProfessional), this);
                                } else if (professionalCategoryTypeID == 10) {
                                    jsonObjectProfessional.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectProfessional.put("TabName", "Opportunities");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTypeTapped", jsonObjectProfessional, AppUtil.convertJsonToHashMap(jsonObjectProfessional), this);
                                } else if (professionalCategoryTypeID == 11) {
                                    jsonObjectProfessional.put("DocID", realmManager.getUserUUID(realm));
                                    jsonObjectProfessional.put("TabName", "Partners");
                                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTypeTapped", jsonObjectProfessional, AppUtil.convertJsonToHashMap(jsonObjectProfessional), this);

                                }


                            } else {
                                isProfessionalDeepLink = true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        selectedTab = 4;
                        dash_viewPager.setCurrentItem(3);
                        return true;
                }
                return true;
            });

            bottomNavigationView.setOnNavigationItemReselectedListener((BottomNavigationView.OnNavigationItemReselectedListener) menuItem -> {
                try {
                    Fragment currentFragment = adapter.getCurrentFragment(dash_viewPager.getCurrentItem());
                    if (currentFragment != null) {
                        View fragmentView = currentFragment.getView();
                        if (fragmentView != null) {
                            if (dash_viewPager.getCurrentItem() == 0) {
                                mRecyclerViewDashboard = (RecyclerView) fragmentView.findViewById(R.id.channel_recycler_view);
                                mRecyclerViewFeeds = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);
                                membersListview = (CustomRecycleView) fragmentView.findViewById(R.id.deptRecycleview);
                                my_recycler_view_media = (RecyclerView) fragmentView.findViewById(R.id.media_tab_list);
                                if (mRecyclerViewDashboard != null) {
                                    mRecyclerViewDashboard.scrollToPosition(0);
                                }
                                if (mRecyclerViewFeeds != null) {
                                    mRecyclerViewFeeds.scrollToPosition(0);
                                }
                                if (membersListview != null) {
                                    membersListview.scrollToPosition(0);
                                }
                                if (my_recycler_view_media != null) {
                                    my_recycler_view_media.scrollToPosition(0);
                                }


                                Button newUpdatesBtn = (Button) fragmentView.findViewById(R.id.new_updates_btn);//mine one is RecyclerView
                                if (newUpdatesBtn != null && newUpdatesBtn.getVisibility() == VISIBLE) {
                                    newUpdatesBtn.setVisibility(GONE);
                                }
                            } else if (dash_viewPager.getCurrentItem() == 1) {
                                RecyclerView mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.subscriptionCatRecyclerView);//mine one is RecyclerView
                                if (mRecyclerView != null) {
                                    mRecyclerView.scrollToPosition(0);
                                }
                            } else if (dash_viewPager.getCurrentItem() == 2) {
                                RecyclerView mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.connects_recycler_list);//mine one is RecyclerView
                                if (mRecyclerView != null) {
                                    mRecyclerView.scrollToPosition(0);
                                }
                            } else if (dash_viewPager.getCurrentItem() == 3) {
                                RecyclerView mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.categoty_grid);//mine one is RecyclerView
                                if (mRecyclerView != null) {
                                    mRecyclerView.scrollToPosition(0);
                                }
                            }
                            if (appBarLayout != null) {
                                appBarLayout.setExpanded(true, true);
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            });

            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            bottomNavigationView.setItemIconTintList(null);

            dash_viewPager.setOffscreenPageLimit(3);

            search_magnifier = findViewById(R.id.search_magifier);

            dash_viewPager.setCurrentItem(0);

            File deleteFile = AppUtil.getExternalStoragePathFile(this, ".Whitecoats/Post_images");
            AppUtil.deleteFilesOnPermission(this, deleteFile);
            /*
             * Initialize Custom tabs
             */
            View updatesTab = inflater.inflate(R.layout.tab_item, null);
            updatesTabUnreadCount = (TextView) updatesTab.findViewById(R.id.tab_not_count);
            updatesTabTitle = (TextView) updatesTab.findViewById(R.id.tab_title);
            updatesTabTitle.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_dashboard, 0, 0);

            channelsTab = inflater.inflate(R.layout.tab_item, null);
            channelsTabUnreadCount = (TextView) channelsTab.findViewById(R.id.tab_not_count);
            channelsTabTitle = (TextView) channelsTab.findViewById(R.id.tab_title);
            channelsTabTitle.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_channels, 0, 0);

            pagesTab = bottomNavigationView.findViewById(R.id.action_knowledge);

            View chatTab = inflater.inflate(R.layout.tab_item, null);
            chatTabUnreadCount = (TextView) chatTab.findViewById(R.id.tab_not_count);
            chatTabTitle = (TextView) chatTab.findViewById(R.id.tab_title);
            chatTabTitle.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_chat, 0, 0);

            View connectsTab = inflater.inflate(R.layout.tab_item, null);
            connectsTabUnreadCount = (TextView) connectsTab.findViewById(R.id.tab_not_count);
            connectsTabTitle = (TextView) connectsTab.findViewById(R.id.tab_title);
            connectsTabTitle.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_connects, 0, 0);
            /*
             * Set tab name and icon
             */
            updatesTabUnreadCount.setVisibility(GONE);
            updatesTabTitle.setText(getString(R.string.tab_updates));

            channelsTabUnreadCount.setText("");
            channelsTabTitle.setText(getString(R.string.tab_channels));

            chatTabUnreadCount.setText("");
            chatTabTitle.setText(getString(R.string.tab_chats));

            connectsTabUnreadCount.setVisibility(GONE);
            connectsTabTitle.setText(getString(R.string.tab_network));

            setupViewPager(dash_viewPager);

            BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(3);


            notificationBadgeChannel = LayoutInflater.from(this).inflate(R.layout.view_notification_badge, menuView, false);
            notificationBadgeChat = LayoutInflater.from(this).inflate(R.layout.view_notification_badge, menuView, false);

            channelTabCount = (TextView) notificationBadgeChannel.findViewById(R.id.channel_tab_count);


            chatTabCount = (TextView) notificationBadgeChat.findViewById(R.id.channel_tab_count);
            itemView.addView(notificationBadgeChannel);

            search_magnifier.setOnClickListener(view -> {
                AppUtil.logUserEventWithDocIDAndSplty("DashboardSearchInitiation", basicInfo, DashboardActivity.this);
                Intent searchIntent = new Intent(DashboardActivity.this, NetworkSearchActivity.class);
                startActivity(searchIntent);
            });

            updateDialogsBroadcastManagers();
            mysearchinfo = realmManager.getRealmBasicInfo(realm);


            /**
             * Set Tab unread counts if any
             */
            if (realmManager.getUnreadDialogCount(realm) != 0) {
                chatTabUnreadCount.setVisibility(VISIBLE);
                chatTabUnreadCount.setText(realmManager.getUnreadDialogCount(realm) + "");
            } else {
                chatTabUnreadCount.setVisibility(GONE);
            }

            /*
             * Set current tab
             */
            if (bundleData != null) {
                if (bundleData.containsKey("tabid")) {
                    getSupportActionBar().setDisplayUseLogoEnabled(true);
                }
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }

        marshMallowPermission = new

                MarshMallowPermission(this);

        firstrun =

                getSharedPreferences("PREFERENCE", MODE_PRIVATE).

                        getBoolean("firstrun", true);

        if (is_user_active) {
            overAllExperience = basicInfo.getOverAllExperience();
            AppUtil.subscribeDeviceForNotifications(DashboardActivity.this, realmManager.getDoc_id(realm), "FCM", true, sharedPrefObj.getPref(MyFcmListenerService.PROPERTY_REG_ID, ""));
        } else {
            overAllExperience = 999;
        }

        if (overAllExperience == 999) {
            Intent intent = new Intent(DashboardActivity.this, LocationAndExperienceActivity.class);
            //startActivityForResult(intent, 7777);
            launchActivityForResults.launch(intent);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (firstrun) {
                    Intent intent = new Intent(DashboardActivity.this, PermissionAccessActivity.class);
                    //startActivityForResult(intent, 10101);
                    launchActivityForResults.launch(intent);
                } else {
                    if (AppUtil.checkPhoneStatePermission(DashboardActivity.this)) {
                        //new PlayServicesHelper(DashboardActivity.this);
                        marshMallowPermission.requestPermissionForContacts();
                    }
                    if (firstRunExperience) {
                        if (displayUserPreferredChannel) {
                            if (associationList != null) {
                                Intent intent = new Intent(DashboardActivity.this, DefaultHomePageActivity.class);
                                intent.putExtra("AssociationList", associationList.toString());
                                //startActivityForResult(intent, 50505);
                                launchActivityForResults.launch(intent);
                            }
                        }
                    }

                }
            } else {
                if (firstRunExperience) {
                    if (displayUserPreferredChannel) {
                        if (associationList != null) {
                            Intent intent = new Intent(DashboardActivity.this, DefaultHomePageActivity.class);
                            intent.putExtra("AssociationList", associationList.toString());
                            //startActivityForResult(intent, 50505);
                            launchActivityForResults.launch(intent);
                        }
                    }
                }
            }
        }


    }

    private void postFabButtonClick() {

        HashMap<String, Object> data = new HashMap<>();
        data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
        data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
        AppUtil.logUserEventWithHashMap("DashboardFeedCreationInitiation", basicInfo.getDoc_id(), data, DashboardActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DocID", realmManager.getUserUUID(realm));
            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DashboardFeedCreationInitiation", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mySharedPref != null && mySharedPref.getPref(MySharedPref.PREF_IS_USER_VERIFIED, 3) == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
            final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(DashboardActivity.this);
            final View sheetView = DashboardActivity.this.getLayoutInflater().inflate(R.layout.dashboard_bottom_sheet_modal, null);
            RelativeLayout bottomSheetCloseButton = (RelativeLayout) sheetView.findViewById(R.id.bottomSheetCloseButton);
            final ListView dashBoardCommunityListView = (ListView) sheetView.findViewById(R.id.communityList);
            /*
             * If No network channel is there, hide "post to network" layout
             */

            communityList = new ArrayList<JSONObject>();
            dashBoardBottomModelList = new ArrayList<>();


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

            DashBoardBottomListModel temp = new DashBoardBottomListModel();
            temp.setCommunityName("Ask A Question");
            temp.setSubCommunityName("Get answers/tips from your peers from the medical community");
            temp.setIconCommunityName("Question");
            dashBoardBottomModelList.add(temp);

            temp = new DashBoardBottomListModel();
            temp.setCommunityName("Post A Case");
            temp.setSubCommunityName("Share and discuss interesting medical cases");
            temp.setIconCommunityName("Case");
            dashBoardBottomModelList.add(temp);

            temp = new DashBoardBottomListModel();
            temp.setCommunityName("Share News/Announcements");
            temp.setSubCommunityName("Share latest/useful developments in medicine or your work");
            temp.setIconCommunityName("Post");
            dashBoardBottomModelList.add(temp);




            /*
             * Set adapter
             */
            DashBoardListAdapter dashBoardListAdapter = new DashBoardListAdapter(DashboardActivity.this, dashBoardBottomModelList);
            dashBoardCommunityListView.setAdapter(dashBoardListAdapter);
            mBottomSheetDialog.setContentView(sheetView);
            BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) sheetView.getParent());
            //mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mBottomSheetDialog.show();
            dashBoardCommunityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("FeedType", dashBoardBottomModelList.get(position).getIconCommunityName());
                        jsonObject.put("DocSpeciality", realmManager.getDocSpeciality(realm));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DashboardFeedCreationInitiation", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mBottomSheetDialog.dismiss();
                    JSONObject channelObj = channelsList.get(networkChannelId);
                    viewCreatePost(channelObj, position);

                }
            });


            bottomSheetCloseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetDialog.dismiss();
                }
            });

        } else if (AppUtil.getUserVerifiedStatus() == 1) {
            AppUtil.AccessErrorPrompt(DashboardActivity.this, getString(R.string.mca_not_uploaded));
        } else if (AppUtil.getUserVerifiedStatus() == 2) {
            AppUtil.AccessErrorPrompt(DashboardActivity.this, getString(R.string.mca_uploaded_but_not_verified));
        }

    }

    private void viewCreatePost(JSONObject jsonObject, int communityPosition) {
        Intent intent = new Intent(DashboardActivity.this, CreatePostActivity.class);
        intent.putExtra(RestUtils.NAVIGATATION, "Dashboard");
        if (jsonObject != null)
            intent.putExtra(RestUtils.KEY_SELECTED_CHANNEL, jsonObject.toString());
        intent.putExtra("communitySelectedType", communityPosition);
        intent.putExtra("postFrom", "DASHBOARD_PAGE");
        startActivity(intent);
    }

    private void viewCreatePostNew(String postName) {
        Intent intent = new Intent(DashboardActivity.this, CreatePostActivity.class);
        intent.putExtra(RestUtils.NAVIGATATION, "Dashboard");
        if (postName != null)
            intent.putExtra(RestUtils.KEY_SELECTED_CHANNEL, postName);
        startActivity(intent);
    }

    private void setupViewPager(ViewPager dash_viewPager) {
        Log.i(TAG, "setupViewPager()");
        List<Fragment> fragments = new Vector<Fragment>();

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(associationViewFragment, getString(R.string.tab_updates));
        adapter.addFrag(knowledgeTabFragment, "KNOWLEDGE");
        adapter.addFrag(communityTabFragment, "COMMUNITY");
        adapter.addFrag(professionalFragment, "PROFESSIONALS");

        dash_viewPager.setAdapter(adapter);

    }

    private void establishQBConnection() {
        Log.i(TAG, "establishQBConnection()");
        try {
            if (isChatNotification || isFromConnectNotification) {
                //showProgress();
            }
            int conntectCount = realmManager.getConnectsCount(realm);
            if (conntectCount == 0) {
                Log.i(TAG, "Restoring user connects");
                Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , Start Time : " + DateUtils.getCurrentTime());
                JSONObject requestData = new JSONObject();
                try {
                    requestData.put(RestUtils.TAG_DOC_ID, basicInfo.getDoc_id());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new VolleySinglePartStringRequest(DashboardActivity.this, Request.Method.POST, RestApiConstants.RESTORE_USER_CONNECTS, requestData.toString(), "QB_LOGIN", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {

                        Log.i(TAG, "ChatService.login : onTaskCompleted()");
                        Log.d(TAG, "Response string :" + successResponse);
                        Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
                        try {
                            JSONObject responseJsonObj = new JSONObject(successResponse);
                            if (responseJsonObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                JSONArray jsonArray = responseJsonObj.optJSONArray(RestUtils.TAG_DATA);
                                int size = jsonArray.length();
                                for (int count = 0; count < size; count++) {
                                    JSONObject jsonObj = jsonArray.optJSONObject(count);
                                    ContactsInfo contactsInfo = new ContactsInfo();
                                    contactsInfo.setDoc_id(jsonObj.optInt(RestUtils.TAG_DOC_ID));
                                    contactsInfo.setNetworkStatus(jsonObj.optString(RestUtils.TAG_CONNECT_STATUS));
                                    /**
                                     * Get the Details Json Object
                                     */
                                    JSONObject detailsJsonObj = new JSONObject(jsonObj.optString("card_info"));
                                    contactsInfo.setEmail(detailsJsonObj.optString(RestUtils.TAG_CNT_EMAIL));
                                    contactsInfo.setPhno(detailsJsonObj.optString(RestUtils.TAG_CNT_NUM));
                                    contactsInfo.setQb_userid(detailsJsonObj.optInt(RestUtils.TAG_QB_USER_ID));
                                    contactsInfo.setSpeciality(detailsJsonObj.optString(RestUtils.TAG_SPLTY));
                                    contactsInfo.setSubSpeciality(detailsJsonObj.optString(RestUtils.TAG_SUB_SPLTY, ""));
                                    contactsInfo.setName((detailsJsonObj.has(RestUtils.TAG_USER_FULL_NAME)) ? detailsJsonObj.optString(RestUtils.TAG_USER_FULL_NAME) : detailsJsonObj.optString(RestUtils.TAG_FULL_NAME));
                                    contactsInfo.setLocation(detailsJsonObj.optString(RestUtils.TAG_LOCATION));
                                    contactsInfo.setWorkplace(detailsJsonObj.optString(RestUtils.TAG_WORKPLACE));
                                    contactsInfo.setDesignation(detailsJsonObj.optString(RestUtils.TAG_DESIGNATION));
                                    contactsInfo.setDegree(detailsJsonObj.optString(RestUtils.TAG_DEGREE));
                                    contactsInfo.setPic_name(detailsJsonObj.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                    contactsInfo.setPic_url(detailsJsonObj.optString(RestUtils.TAG_PROFILE_PIC_URL));
                                    contactsInfo.setUserSalutation(detailsJsonObj.optString(RestUtils.TAG_USER_SALUTAION));
                                    contactsInfo.setUserTypeId(detailsJsonObj.optInt(RestUtils.TAG_USER_TYPE_ID));
                                    contactsInfo.setPhno_vis(detailsJsonObj.optString(RestUtils.TAG_CNNTMUNVIS));
                                    contactsInfo.setEmail_vis(detailsJsonObj.optString(RestUtils.TAG_CNNTEMAILVIS));
                                    /**
                                     * Check whether doc_id exists in database, if exists then update it  else insert a new record.
                                     */
                                    boolean isDoctorExists = realmManager.isDoctorExists(realm, contactsInfo.getDoc_id());
                                    if (isDoctorExists) {
                                        realmManager.updateMyContacts(realm, contactsInfo);
                                    } else {
                                        realmManager.insertMyContacts(realm, contactsInfo, Integer.parseInt(contactsInfo.getNetworkStatus()));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {

                        //Toast.makeText(context,context.getString(R.string.unable_to_connect_server),Toast.LENGTH_SHORT).show();
                    }
                }).sendSinglePartRequest();

            }

        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10101) {
            if (!marshMallowPermission.requestPhoneAndMediaPermissions()) {
            }
            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstrun", false)
                    .commit();
        } else if (resultCode == 50505) {
            sharedPrefObj.savePref("EXPERIENCE", false);

            Bundle bundle = data.getExtras();
            int selectedPosition = 0;
            if (bundle != null) {
                selectedPosition = bundle.getInt("ListPosition");
            }

            requestForChannelSelection(selectedPosition);
        }
        if (associationViewFragment != null) {
            associationViewFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkForUpdate();
            }
        }

    }

    private void presentShowcaseSequence(FloatingActionButton postAnUpdateLabel, boolean isDisplayPostIcon, boolean notificationViewCount) {

        if (AppConstants.COACHMARK_INCREMENTER > 1) {
            MaterialShowcaseView.resetSingleUse(DashboardActivity.this, "Sequence_Dashboard_ActionBar");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500);
            config.setShapePadding(1);// half second between each showcase view

            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "Sequence_Dashboard_ActionBar");
            //MaterialShowcaseView.resetAll(DashboardActivity.this);
            sequence.setConfig(config);
            String buttonText = "Next";
            if (isDisplayPostIcon == true && editor.getBoolean("channelsTab", false) && editor.getBoolean("notificationView", false)) {
                buttonText = "Got it";
            }

            if (postAnUpdateLabel.getVisibility() == VISIBLE) {
                if (!editor.getBoolean("postLayout", false) || !sharedPrefObj.getPref("PostBtnCoachMarkInUpgrade", false)) {
                    sequence.addSequenceItem(
                            new MaterialShowcaseView.Builder(this)
                                    .setTarget(postAnUpdateLabel)
                                    .setDismissText(buttonText)
                                    .setDismissTextColor(Color.parseColor("#00a76d"))
                                    .setMaskColour(Color.parseColor("#CC231F20"))
                                    .setContentText(R.string.tap_to_coach_mark_dashboard_update_case_query).setListener(new IShowcaseListener() {
                                        @Override
                                        public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                            editor.edit().putBoolean("postLayout", true).commit();
                                            sharedPrefObj.savePref("PostBtnCoachMarkInUpgrade", true);
                                        }

                                        @Override
                                        public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                        }
                                    })
                                    .withCircleShape()
                                    .build()
                    );
                }
            }
            if (notificationView != null && not_count != null && not_count.getVisibility() == VISIBLE) {
                if (!editor.getBoolean("notificationView", false)) {
                    sequence.addSequenceItem(
                            new MaterialShowcaseView.Builder(this)
                                    .setTarget(notificationView)
                                    .setDismissText("Got it")
                                    .setDismissTextColor(Color.parseColor("#00a76d"))
                                    .setMaskColour(Color.parseColor("#CC231F20"))
                                    .setContentText(R.string.tap_to_coach_mark_dashboard_your_invitations_here).setListener(new IShowcaseListener() {
                                        @Override
                                        public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                            editor.edit().putBoolean("notificationView", true).commit();
                                        }

                                        @Override
                                        public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                        }
                                    })
                                    .withCircleShape()
                                    .build()
                    );
                }

            }
            sequence.start();
        } else if (AppConstants.COACHMARK_INCREMENTER > 0) {
            MaterialShowcaseView.resetSingleUse(DashboardActivity.this, "Sequence_Dashboard_ActionBar");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500);
            config.setShapePadding(1);// half second between each showcase view

            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "Sequence_Dashboard_ActionBar");
            //MaterialShowcaseView.resetAll(DashboardActivity.this);
            sequence.setConfig(config);
            if (notificationView != null && not_count != null && not_count.getVisibility() == VISIBLE) {
                if (!editor.getBoolean("notificationView", false)) {
                    sequence.addSequenceItem(
                            new MaterialShowcaseView.Builder(this)
                                    .setTarget(notificationView)
                                    .setDismissText("Got it")
                                    .setDismissTextColor(Color.parseColor("#00a76d"))
                                    .setMaskColour(Color.parseColor("#CC231F20"))
                                    .setContentText(R.string.tap_to_coach_mark_dashboard_your_invitations_here).setListener(new IShowcaseListener() {
                                        @Override
                                        public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                            editor.edit().putBoolean("notificationView", true).commit();
                                        }

                                        @Override
                                        public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                        }
                                    })
                                    .withCircleShape()
                                    .build()
                    );
                }
            }
            sequence.start();
        }
    }


    private void updateDialogsBroadcastManagers() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("update_dialogs_count");
        filter.addAction("update_channels_count");
        BroadcastReceiver updateCountDialogsBroadcastReceiver = new UpdateCountDialogsBroadcastReceiver();
        LocalBroadcastManager.getInstance(DashboardActivity.this).registerReceiver(updateCountDialogsBroadcastReceiver,
                filter);
    }

    @Override
    public void isDashboardLoaded(boolean isDashboardLoaded, final ViewGroup postUpdateLabel, final boolean isDisplayPostIcon) {
        if (isDashboardLoaded) {
            post_fab_button_dashboard.setEnabled(true);
        }
    }

    private void requestForCategoriesData(int page_num) {
        dataViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_USER_CATEGORIES_LIST_V2, "USER_CATEGORY_LIST", AppUtil.getRequestHeaders(DashboardActivity.this), page_num, realmManager.getDoc_id(realm), RequestLocationType.REQUEST_LOCATION_HOME.getRequestLocation());
        dataViewModel.getAllExporeItems().observe(this, categoriesApiResponse -> {
            if (categoriesApiResponse.getError() != null) {
                loading = false;
                return;
            } else {
                loading = false;
                categoryList.clear();
                categoryList.addAll(categoriesApiResponse.getCategories());
                if (categoryList.size() > 0) {
                    categoriesAdapter.notifyDataSetChanged();
                    if (dash_viewPager != null && dash_viewPager.getCurrentItem() == 0) {
                        categoryListLayout.setVisibility(View.VISIBLE);
                        categoryListDummyView.setVisibility(VISIBLE);
                    }
                }
            }

        });
    }

    @Override
    public void dashboardLoadedWithOfflineData() {
        if (realmManager.getFeedDataFromDB() != null && realmManager.getFeedDataFromDB().size() > 0) {
            post_fab_button_dashboard.setEnabled(true);
        }
    }

    @Override
    public void onProfileUpdate(BasicInfo basicInfo) {
        loadProfilePic();
        loadHamburgerMenuProfileIcon();
        nav_doc_spec.setText(basicInfo.getSplty());
    }

    private int changeBackground() {
        int RedColor = 0, GreenColor = 0, BlueColor = 0;
        String actualName = basicInfo.getFname() + " " + basicInfo.getLname();
        String senderId = "" + basicInfo.getDoc_id();
        if (actualName != null && !actualName.isEmpty()) {
            if (actualName.length() >= 5) {
                actualName = actualName.replaceAll("\\s+", "");
                String only_doc_name = actualName.substring(3, (actualName.length() - 1));
                String requiredDocName, requiredSenderId;
                if (only_doc_name.length() >= 3) {
                    requiredDocName = only_doc_name.substring(0, 3).toUpperCase();
                } else {
                    requiredDocName = only_doc_name.toUpperCase() + "OO";
                }
                if (senderId.length() < 3) {
                    senderId = "000" + senderId;
                }
                requiredSenderId = senderId.substring((senderId.length() - 3), senderId.length());
                ArrayList<Integer> numbersList = new ArrayList<>();
                char[] ch = requiredDocName.toCharArray();
                for (char c : ch) {
                    int temp = (int) c;
                    int temp_integer = 64; //for upper case
                    if (temp <= 90 & temp >= 65) {
                        numbersList.add(temp - temp_integer);
                    }

                }
                if (numbersList.size() > 0) {
                    RedColor = Character.getNumericValue(requiredSenderId.charAt(0)) * numbersList.get(0);
                }
                if (numbersList.size() > 1) {
                    GreenColor = Character.getNumericValue(requiredSenderId.charAt(1)) * numbersList.get(1);
                }
                if (numbersList.size() > 2) {
                    BlueColor = Character.getNumericValue(requiredSenderId.charAt(2)) * numbersList.get(2);
                }
                if (RedColor < 50 && GreenColor < 50 && BlueColor < 50) {
                    RedColor = RedColor + 50;
                    GreenColor = GreenColor + 50;
                    BlueColor = BlueColor + 50;
                }
            }
        }
        return Color.rgb(RedColor, GreenColor, BlueColor);
    }

    private void loadProfilePic() {
        Log.i(TAG, "loadProfilePic");
        String picUrl = (basicInfo.getPic_url() != null) ? basicInfo.getPic_url().trim() : "";
        String picPath = (basicInfo.getProfile_pic_path() != null) ? basicInfo.getProfile_pic_path().trim() : "";
        String profilePicPath = (picUrl != null && !picUrl.isEmpty()) ? picUrl : (picPath != null && !picPath.equals("")) ? picPath : null;
//        if (!AppUtil.checkWriteExternalPermission(this)) {
        if (picUrl != null && !picUrl.isEmpty()) {
//                isProfileVisible = true;
//                invalidateOptionsMenu();
            no_profile_back.setVisibility(GONE);
            profile_image.setVisibility(VISIBLE);
            AppUtil.invalidateAndLoadCircularImage(this, basicInfo.getPic_url().trim(), profile_image, R.drawable.default_profilepic);


        } else {
            profileText = basicInfo.getFname().toUpperCase().charAt(0) + "" + basicInfo.getLname().toUpperCase().charAt(0);
            no_profile_text.setText(profileText);
            no_profile_back.setCardBackgroundColor(changeBackground());
            no_profile_back.setVisibility(VISIBLE);
            profile_image.setVisibility(GONE);

        }
    }


    private class UpdateCountDialogsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction().toLowerCase()) {
                case "update_dialogs_count":
                    Realm realm = Realm.getDefaultInstance();
                    RealmManager realmManager = new RealmManager(context);
                    long dcount = realmManager.getUnreadDialogCount(realm);
                    if (dcount != 0) {
                        /*chatTabUnreadCount.setVisibility(VISIBLE);
                        chatTabUnreadCount.setText(dcount + "");*/
                        chatTabCount.setVisibility(VISIBLE);
                        chatTabCount.setText(dcount + "");
                    } else {
                        ///chatTabUnreadCount.setVisibility(GONE);
                        chatTabCount.setVisibility(GONE);
                    }
                    if (!realm.isClosed()) {
                        realm.close();
                    }
                    break;
                case "update_channels_count":
                    if (AppConstants.unreadChannelsList.size() > 0) {
                        channelTabCount.setVisibility(GONE);
                        channelTabCount.setText("" + AppUtil.suffixNumber(AppConstants.unreadChannelsList.size()));
                    } else {
                        channelTabCount.setVisibility(GONE);
                    }
                    // do something
                    break;
                default:
                    break;
            }
        }
    }

    private final BroadcastReceiver channelIdUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshChannelId();
        }
    };


    private void refreshChannelId() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        mainMenu = menu;
        inflater.inflate(R.menu.menu_dashboard, menu);
        notificationmenuItem = menu.findItem(R.id.action_notification);
        notificationView = (FrameLayout) MenuItemCompat.getActionView(notificationmenuItem);
        action_back = menu.findItem(R.id.action_back);
        //search_item = menu.findItem(R.id.search_magifier);
        action_back.setIcon(R.drawable.ic_back);
        action_back.setVisible(false);
        action_back.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                onBackPressed();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                return false;
            }
        });
        final View menu_hotlist = menu.findItem(R.id.action_notification).getActionView();
        //final View profile_hotlist = menu.findItem(R.id.menu_action_profile).getActionView();
//        final View no_profile_hotlist = menu.findItem(R.id.no_menu_action_profile).getActionView();
        not_count = (TextView) menu_hotlist.findViewById(R.id.notification_count_text);
        notification_icon = (ImageView) menu_hotlist.findViewById(R.id.notification_img);

        try {
            setNotificationCountOnBadge();
            if (!isContextMenuLoaded) {
                isContextMenuLoaded = true;
            }
            //  loadProfilePic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        menu_hotlist.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "NotificationTrayTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent notify_intent = new Intent(DashboardActivity.this, NotificationsActivity.class);
            startActivity(notify_intent);
            not_count.setText("");
            not_count.setVisibility(View.GONE);
            mySharedPref.savePref("networkNotificationCount", 0);
        });

        if (isNeedUpdateNotiCount) {
            not_count.setText("");
            not_count.setVisibility(View.GONE);
            mySharedPref.savePref("networkNotificationCount", 0);
        }
        if (setNotificationCount) {
            setNotificationCountOnBadge();
            setNotificationCount = false;
        }
        return true;

    }

    private void setNotificationCountOnBadge() {
        networkNotificationsCount = mySharedPref.getPref("networkNotificationCount", 0);
        if (mFeedsNotificationsCount != 0 || networkNotificationsCount != 0) {
            if (not_count != null) {
                not_count.setText("" + AppUtil.suffixNumber(mFeedsNotificationsCount + networkNotificationsCount));
                not_count.setVisibility(View.VISIBLE);
            }
            if (network_frag != null) {
                network_frag.updatePendingConntectsData(realmManager.getPendingNotification(realm));
            }
        } else {
            if (not_count != null) {
                not_count.setText("");
                not_count.setVisibility(View.GONE);
            }
            notification_icon.setBackgroundResource(R.drawable.ic_notifications);
        }
    }

    private void hideOption(int id) {
        if (toolbar != null && mMenu != null) {
            MenuItem item = mMenu.findItem(id);
            item.setVisible(false);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.my_profile) {
            AppUtil.logUserEventWithDocIDAndSplty("MyProfileView", basicInfo, DashboardActivity.this);
            Intent intent_profile = new Intent(DashboardActivity.this, ProfileViewActivity.class);
            startActivity(intent_profile);
            return true;
        } else if (id == R.id.my_home_preference) {
            if (associationList != null) {
                displayPreferredChannelDialog(false);
            }


        } else if (id == R.id.my_preferences) {
            AppUtil.logUserEventWithDocIDAndSplty("MyPreferenceViewing", basicInfo, DashboardActivity.this);
            Intent intent = new Intent(DashboardActivity.this, PreferencesActivity.class);
            intent.putExtra(RestUtils.CHANNEL_ID, 0);
            //startActivityForResult(intent, PREFERENCE_ACTION);
            launchActivityForResults.launch(intent);
            return true;
        } else if (id == R.id.invite) {
            AppUtil.logUserEventWithDocIDAndSplty("InvitingtoWhiteCoats", basicInfo, DashboardActivity.this);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String link_val = "https://invite.whitecoats.com";
            String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.inviate_to_whiteCoats) + Html.fromHtml(body));
            startActivity(Intent.createChooser(shareIntent, "Invite via"));
            drawerLayout.closeDrawers();
        } else if (id == R.id.bookmarks) {
            AppUtil.logUserEventWithDocIDAndSplty("MyBookmarksListing", basicInfo, DashboardActivity.this);
            Intent intent = new Intent(this, MyBookmarksActivity.class);
            intent.putExtra(RestUtils.NAVIGATATION, "Bookmarks");
            startActivity(intent);

        } else if (id == R.id.action_terms_of_use) {
            Intent intentTerms = new Intent(getApplicationContext(), TermsOfServiceActivity.class);
            intentTerms.putExtra("NavigationFrom", "terms_of_use");
            startActivity(intentTerms);
            return true;
        } else if (id == R.id.action_help) {
            Intent intentsupport = new Intent(getApplicationContext(), ContactSupport.class);
            startActivity(intentsupport);
            return true;
        }
        // pardha modified
        else if (id == R.id.action_logout) {
            if (AppUtil.isConnectingToInternet(DashboardActivity.this)) {
                showProgress();
                new VolleySinglePartStringRequest(DashboardActivity.this, Request.Method.POST, RestApiConstants.LOGOUT, "", "LOGOUT", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        hideProgress();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(successResponse);
                            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                //QBUsers.signOut();
                                sharedPrefObj.savePref(MySharedPref.PREF_SESSION_TOKEN, "");
                                sharedPrefObj.savePref(MySharedPref.STAY_LOGGED_IN, false);
                                sharedPrefObj.savePref(MySharedPref.PREF_REGISTRATION_FLAG, true);
                                sharedPrefObj.savePref(MySharedPref.PREF_USER_PASSWORD, null);
                                Intent intentLogout = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intentLogout);

                            } else {
                                Toast.makeText(DashboardActivity.this, getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        hideProgress();
                        displayErrorScreen(errorResponse);
                    }
                }).sendSinglePartRequest();
            }
            return true;
        } /*else if (id == R.id.my_caserooms) {
            Intent inviteIntent = new Intent(DashboardActivity.this, CaseRoomActivity.class);
            startActivity(inviteIntent);
            return true;
        }*/
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayPreferredChannelDialog(boolean displayPreferredChannelPrompt) {
        int position = 0;
        int savedChannelId = mySharedPref.getPref(MySharedPref.PREF_SAVED_PREF_CHANNEL, -1);
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setCanceledOnTouchOutside(false);
        View sheetView = getLayoutInflater().inflate(R.layout.home_preference_bottom_sheet, null);
        ListView associationsListView = (ListView) sheetView.findViewById(R.id.associationsList);
        TextView skip = (TextView) sheetView.findViewById(R.id.association_selection_skip);
        TextView done = (TextView) sheetView.findViewById(R.id.association_selection_done);


        AssociationsListAdapter associationsListAdapter = new AssociationsListAdapter(this, associationList);
        associationsListView.setAdapter(associationsListAdapter);
        mBottomSheetDialog.setContentView(sheetView);

        associationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                defaultChannel = associationList.get(i).optBoolean("is_user_default_channel");
                view.setSelected(true);
                associationsListAdapter.notifyDataSetChanged();

            }
        });

        for (int i = 0; i < associationList.size(); i++) {
            if (savedChannelId == associationList.get(i).optInt(RestUtils.CHANNEL_ID)) {
                position = i;
            }
        }
        if (position > 0) {
            associationsListView.setItemChecked(position, true);
        }

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                if (displayPreferredChannelPrompt || savedChannelId == -1) {
                    requestForChannelSelection(-1);
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (associationsListView.getCheckedItemPosition() != -1) {
                    mBottomSheetDialog.dismiss();
                    if (displayPreferredChannelPrompt || savedChannelId != associationList.get(associationsListView.getCheckedItemPosition()).optInt(RestUtils.CHANNEL_ID)) {
                        requestForChannelSelection(associationsListView.getCheckedItemPosition());
                    }

                } else {
                    Toast.makeText(DashboardActivity.this, "Please select a channel", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBottomSheetDialog.show();
    }

    private void requestForChannelSelection(int checkedItemPosition) {
        if (!isConnectingToInternet()) {
            return;

        }
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
            if (checkedItemPosition == -1) {
                requestObj.put(RestUtils.CHANNEL_ID, associationList.get(0).optInt(RestUtils.CHANNEL_ID));
            } else {
                requestObj.put(RestUtils.CHANNEL_ID, associationList.get(checkedItemPosition).optInt(RestUtils.CHANNEL_ID));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showProgress();
        new VolleySinglePartStringRequest(DashboardActivity.this, Request.Method.POST, RestApiConstants.PREFERRED_CHANNEL_SELECTION_API, requestObj.toString(), "PREF_CHANNEL_SELECTION", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                hideProgress();
                if (successResponse != null && !successResponse.isEmpty()) {
                    try {
                        JSONObject responseObj = new JSONObject(successResponse);
                        if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                            int position = checkedItemPosition;
                            if (checkedItemPosition == -1) {
                                position = 0;
                            }
                            mySharedPref.savePref(MySharedPref.PREF_SAVED_PREF_CHANNEL, associationList.get(position).optInt(RestUtils.CHANNEL_ID));
                            associationViewFragment.setTabSelection(position, associationList.get(position));
                        } else if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                            if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                Toast.makeText(DashboardActivity.this, responseObj.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DashboardActivity.this, "Something went wrong,Please try again", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                hideProgress();
                Toast.makeText(DashboardActivity.this, "Something went wrong,Please try again", Toast.LENGTH_SHORT).show();
            }
        }).sendSinglePartRequest();


    }

    public Bitmap createCircleBitmap(Bitmap bitmapimg) {
        Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(),
                bitmapimg.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmapimg.getWidth(),
                bitmapimg.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle((float) bitmapimg.getWidth() / 2,
                (float) bitmapimg.getHeight() / 2,
                (float) bitmapimg.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
    }

    @Override
    public void onBackPressed() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DocID", realmManager.getUserUUID(realm));
            String eventName = "";
            if (dash_viewPager.getCurrentItem() == 0) {
                eventName = "DashboardBackTapped";
            } else if (dash_viewPager.getCurrentItem() == 1) {
                eventName = "DoctorsDeviceBackTapped";
            } else if (dash_viewPager.getCurrentItem() == 2) {
                eventName = "PagesDeviceBackTapped";
            } else if (dash_viewPager.getCurrentItem() == 3) {
                eventName = "ExploreDeviceBackTapped";
            }
            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DashboardActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        // }
    }

    private class UpdateNotificationsCount extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {

                setNotificationCountOnBadge();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void deepLinkingNavigation(String calingFrom) {
        if (bundleData != null) {
            try {
                JSONObject reqObject = null;
                boolean stackBoolean;

                if (bundleData.containsKey(RestUtils.KEY_REQUEST_BUNDLE)) {
                    reqObject = new JSONObject(bundleData.getString(RestUtils.KEY_REQUEST_BUNDLE));
                    stackBoolean = reqObject.getBoolean("deeplinkingStack");
                    if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("1")) {
                        appBarLayout.setExpanded(true, true);
                        if (calingFrom.equals("fromOnCreate")) {
                            isKnowledgeDeepLink = true;
                        } else {
                            if (knowledgeCategoryTypeID == 1) {
                                isKnowledgeDeepLink = true;
                            } else {
                                isKnowledgeDeepLink = false;
                            }
                        }
                        bottomNavigationView.setSelectedItemId(R.id.action_knowledge);
                        if (stackBoolean == false) {
                            knowledgeTabFragment.setCategoryId(1);
                        } else {
                            knowledgeTabFragment.tabNavigation(TabsTagId.KNOWLEDGE_FEEDS.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("2")) {
                        appBarLayout.setExpanded(true, true);
                        isKnowledgeDeepLink = false;
                        bottomNavigationView.setSelectedItemId(R.id.action_knowledge);
                        if (stackBoolean == false) {
                            knowledgeTabFragment.setCategoryId(2);
                        } else {
                            knowledgeTabFragment.tabNavigation(TabsTagId.KNOWLEDGE_DRUG_REFERENCE.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("3")) {
                        appBarLayout.setExpanded(true, true);
                        isKnowledgeDeepLink = false;
                        bottomNavigationView.setSelectedItemId(R.id.action_knowledge);
                        if (stackBoolean == false) {
                            knowledgeTabFragment.setCategoryId(3);
                        } else {
                            knowledgeTabFragment.tabNavigation(TabsTagId.KNOWLEDGE_MEDICAL_EVENTS.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("4")) {
                        appBarLayout.setExpanded(true, true);
                        if (calingFrom.equals("fromOnCreate")) {
                            isCommunityTabDeepLink = true;
                        } else {
                            if (communityCategoryTypeID == 4) {
                                isCommunityTabDeepLink = true;
                            } else {
                                isCommunityTabDeepLink = false;
                            }
                        }
                        bottomNavigationView.setSelectedItemId(R.id.action_community);
                        if (stackBoolean == false) {
                            communityTabFragment.setCategoryId(4);
                        } else {
                            communityTabFragment.navigateToNetworkTab(TabsTagId.COMMUNITY_SPOTLIGHTS.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("5")) {
                        appBarLayout.setExpanded(true, true);
                        isCommunityTabDeepLink = false;
                        bottomNavigationView.setSelectedItemId(R.id.action_community);
                        if (stackBoolean == false) {
                            communityTabFragment.setCategoryId(5);
                        } else {
                            communityTabFragment.navigateToNetworkTab(TabsTagId.COMMUNITY_FEEDS.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("6")) {
                        appBarLayout.setExpanded(true, true);
                        isCommunityTabDeepLink = false;
                        bottomNavigationView.setSelectedItemId(R.id.action_community);
                        if (stackBoolean == false) {
                            communityTabFragment.setCategoryId(6);
                        } else {
                            communityTabFragment.navigateToNetworkTab(TabsTagId.COMMUNITY_DOCTORS.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("7")) {
                        appBarLayout.setExpanded(true, true);
                        isCommunityTabDeepLink = false;
                        bottomNavigationView.setSelectedItemId(R.id.action_community);
                        if (stackBoolean == false) {
                            communityTabFragment.setCategoryId(7);
                        } else {
                            communityTabFragment.navigateToNetworkTab(TabsTagId.COMMUNITY_ORGANIZATIONS.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("8")) {
                        appBarLayout.setExpanded(true, true);
                        if (calingFrom.equals("fromOnCreate")) {
                            isProfessionalDeepLink = true;
                        } else {
                            if (professionalCategoryTypeID == 8) {
                                isProfessionalDeepLink = true;
                            } else {
                                isProfessionalDeepLink = false;
                            }
                        }
                        bottomNavigationView.setSelectedItemId(R.id.action_explore);
                        if (stackBoolean == false) {
                            professionalFragment.setCategoryId(8);
                        } else {
                            professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_FEEDS.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("9")) {
                        appBarLayout.setExpanded(true, true);
                        isProfessionalDeepLink = false;
                        bottomNavigationView.setSelectedItemId(R.id.action_explore);
                        if (stackBoolean == false) {
                            professionalFragment.setCategoryId(9);
                        } else {
                            professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_SKILLING.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("10")) {
                        appBarLayout.setExpanded(true, true);
                        isProfessionalDeepLink = false;
                        bottomNavigationView.setSelectedItemId(R.id.action_explore);
                        if (stackBoolean == false) {
                            professionalFragment.setCategoryId(10);
                        } else {
                            professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_OPPORTUNITIES.geTagId());
                        }
                    } else if (reqObject.getString(RestUtils.NOTIFICATION_TAG_ID).equalsIgnoreCase("11")) {
                        appBarLayout.setExpanded(true, true);
                        isProfessionalDeepLink = false;
                        bottomNavigationView.setSelectedItemId(R.id.action_explore);
                        if (stackBoolean == false) {
                            professionalFragment.setCategoryId(11);
                        } else {
                            professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_PARTNERS.geTagId());
                        }
                        //professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_PARTNERS.geTagId());
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("15")) {
                        Intent intent_profile = new Intent(DashboardActivity.this, ProfileViewActivity.class);
                        startActivity(intent_profile);
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("16")) {
                        /*Intent intent = new Intent(DashboardActivity.this, CreatePostActivity.class);
                        startActivity(intent);*/
                        postFabButtonClick();
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("17")) {
                        Intent shareIntent1 = new Intent();
                        shareIntent1.setAction(Intent.ACTION_SEND);
                        shareIntent1.setType("text/plain");
                        String link_val = "https://invite.whitecoats.com";
                        String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
                        shareIntent1.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
                        shareIntent1.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.inviate_to_whiteCoats) + Html.fromHtml(body));
                        startActivity(Intent.createChooser(shareIntent1, "Invite via"));
                        //drawerLayout.closeDrawers();
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("19")) {
                        Intent NotificationSettingsActivity = new Intent(mContext, NotificationSettingsActivity.class);
                        startActivity(NotificationSettingsActivity);
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("18")) {
                        Intent NotificationSettingsActivity = new Intent(mContext, PreferencesActivity.class);
                        startActivity(NotificationSettingsActivity);
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("3")) {
                        Intent NotificationSettingsActivity = new Intent(mContext, NotificationsActivity.class);
                        NotificationSettingsActivity.putExtra("TAB_NUMBER", 1);
                        startActivity(NotificationSettingsActivity);
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("4")) {
                        Intent NotificationSettingsActivity = new Intent(mContext, NotificationsActivity.class);
                        startActivity(NotificationSettingsActivity);
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("22")) {
                        if (AppUtil.getUserVerifiedStatus() == 1) {
                            Intent intent1 = new Intent(getApplicationContext(), MCACardUploadActivity.class);
                            intent1.putExtra("NAVIGATION", "fromLink");
                            startActivity(intent1);
                        } else if (AppUtil.getUserVerifiedStatus() == 2) {
                            Toast.makeText(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified), Toast.LENGTH_SHORT).show();
                        } else if (AppUtil.getUserVerifiedStatus() == 3) {
                            Toast.makeText(mContext, mContext.getString(R.string.mca_verified), Toast.LENGTH_SHORT).show();
                        }
                    } else if (reqObject.getString(RestUtils.TAG_C_MSG_TYPE).equalsIgnoreCase("21")) {
                        appBarLayout.setExpanded(true, true);
                        bottomNavigationView.setSelectedItemId(R.id.action_home);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getIntent().removeExtra(RestUtils.KEY_REQUEST_BUNDLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check network connectivity of app and display appropriate
        // message in Snack bar.
        // Handle any Google Play services errors
        setCurrentActivity();
        checkNetworkConnectivity();
        nav_doc_spec.setText(basicInfo.getSplty());
        bundleData = getIntent().getExtras();
        //deepLinkingNavigation();
        if (AppConstants.login_doc_id == 0) {
            if (realm != null && !realm.isClosed() && realmManager != null) {
                AppConstants.login_doc_id = realmManager.getDoc_id(realm);
            }
        }
        try {
            App_Application.setNumUnreadMessages(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateNotificationsCount updateNotificationscount = new UpdateNotificationsCount();
        LocalBroadcastManager.getInstance(App_Application.getInstance()).registerReceiver(updateNotificationscount, new IntentFilter("NotificationCount"));
        if (bundleData != null) {
            int tabNumb = getIntent().getIntExtra("Navigation_Tab", 0);
            boolean isInviteToWhitecoats = getIntent().getBooleanExtra("Is_InviteToWhitecoats", false);
            boolean postBtnTap = getIntent().getBooleanExtra("post_btn_tap", false);
            if (postBtnTap) {
                postFabButtonClick();
            } else if (tabNumb == NavigationType.CHANNEL_TAB.getNavigationValue()) {
                dash_viewPager.setCurrentItem(1, true);
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
            } else if (tabNumb == NavigationType.COMMUNITY_DOCTORS.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                dash_viewPager.setCurrentItem(2, false);
                communityTabFragment.navigateToNetworkTab(0);
                if (isInviteToWhitecoats && communityTabFragment != null) {
                    communityTabFragment.inviteToWhiteCoatsPopup();
                    getIntent().putExtra("Is_InviteToWhitecoats", false);
                }
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
            } else if (tabNumb == NavigationType.NOTIFICATION_PREFERENCE.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                Intent NotificationSettingsActivity = new Intent(mContext, NotificationSettingsActivity.class);
                startActivity(NotificationSettingsActivity);
            } else if (tabNumb == NavigationType.NOTIFICATION_TAB.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                Intent notify_intent = new Intent(DashboardActivity.this, NotificationsActivity.class);
                startActivity(notify_intent);
                not_count.setText("");
                not_count.setVisibility(View.GONE);
                mySharedPref.savePref("networkNotificationCount", 0);
            } else if (tabNumb == NavigationType.KNOWLEDGE_FEEDS.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isKnowledgeDeepLink = true;
                knowledgeTabFragment.tabNavigation(TabsTagId.KNOWLEDGE_FEEDS.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_knowledge);
            } else if (tabNumb == NavigationType.KNOWLEDGE_DRUG_REFERENCES.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isKnowledgeDeepLink = false;
                knowledgeTabFragment.tabNavigation(TabsTagId.KNOWLEDGE_DRUG_REFERENCE.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_knowledge);
            } else if (tabNumb == NavigationType.KNOWLEDGE_MEDICAL_EVENTS.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isKnowledgeDeepLink = false;
                knowledgeTabFragment.tabNavigation(TabsTagId.KNOWLEDGE_MEDICAL_EVENTS.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_knowledge);
            } else if (tabNumb == NavigationType.COMMUNITY_SPOTLIGHT.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isCommunityTabDeepLink = true;
                communityTabFragment.navigateToNetworkTab(TabsTagId.COMMUNITY_SPOTLIGHTS.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_community);
            } else if (tabNumb == NavigationType.COMMUNITY_FEEDS.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isCommunityTabDeepLink = false;
                communityTabFragment.navigateToNetworkTab(TabsTagId.COMMUNITY_FEEDS.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_community);
            } else if (tabNumb == NavigationType.COMMUNITY_ORGANIZATION.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isCommunityTabDeepLink = false;
                communityTabFragment.navigateToNetworkTab(TabsTagId.COMMUNITY_ORGANIZATIONS.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_community);
            } else if (tabNumb == NavigationType.PROFESSIONAL_FEEDS.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isProfessionalDeepLink = true;
                professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_FEEDS.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_explore);
            } else if (tabNumb == NavigationType.PROFESSIONAL_SKILLING.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isProfessionalDeepLink = false;
                professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_SKILLING.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_explore);
            } else if (tabNumb == NavigationType.PROFESSIONAL_OPPORTUNITIES.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isProfessionalDeepLink = false;
                professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_OPPORTUNITIES.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_explore);
            } else if (tabNumb == NavigationType.PROFESSIONAL_PARTNERS.getNavigationValue()) {
                appBarLayout.setExpanded(true, true);
                isProfessionalDeepLink = false;
                professionalFragment.tabNavigation(TabsTagId.PROFESSIONAL_PARTNERS.geTagId());
                bottomNavigationView.setSelectedItemId(R.id.action_explore);
            }
            getIntent().putExtra("Navigation_Tab", 0);
        }
        if (isContextMenuLoaded) {
            //loadProfilePic();
        } else {
            isContextMenuLoaded = false;
        }
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        bundleData = getIntent().getExtras();
        deepLinkingNavigation("onNewIntent");
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (ProfileUpdateCollectionManager.getRegisterListeners().contains(this)) {
            ProfileUpdateCollectionManager.removeListener(this);
        }
        EventBus.getDefault().unregister(this);
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager locationBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        locationBroadcastManager.unregisterReceiver(channelIdUpdateReceiver);
    }

    void addTransparency(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case PermissionsConstants.REQUEST_ID_MULTIPLE_PERMISSIONS:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }
                boolean phone = shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE);
                if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    if (displayUserPreferredChannel) {
                        if (associationList != null) {
                            Intent intent = new Intent(DashboardActivity.this, DefaultHomePageActivity.class);
                            intent.putExtra("AssociationList", associationList.toString());
                            //startActivityForResult(intent, 50505);
                            launchActivityForResults.launch(intent);
                        }
                    } else {
                        sharedPrefObj.savePref("EXPERIENCE", false);
                    }
                    // }
                } else {
                    if (firstRunExperience) {
                        if (displayUserPreferredChannel) {
                            if (associationList != null) {
                                Intent intent = new Intent(DashboardActivity.this, DefaultHomePageActivity.class);
                                intent.putExtra("AssociationList", associationList.toString());
                                //startActivityForResult(intent, 50505);
                                launchActivityForResults.launch(intent);
                            }
                        } else {
                            sharedPrefObj.savePref("EXPERIENCE", false);
                        }
                        // }
                    }
                }
                break;
            case PERMISSION_CALLBACK_CONSTANT_NOTIFICATION:
                boolean allgranted = false;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        allgranted = true;
                    } else {
                        allgranted = false;
                        break;
                    }
                }
                if (allgranted) {
                    AppUtil.subscribeDeviceForNotifications(DashboardActivity.this, realmManager.getDoc_id(realm), "FCM", true, sharedPrefObj.getPref(MyFcmListenerService.PROPERTY_REG_ID, ""));
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    @Override
    public void onDataLoadWithList(ArrayList<JSONObject> list, boolean display_user_preferred_channel, int feedNotificationsCount, JSONArray communityList, SparseArray<JSONObject> channelsLists, int networkChannel_id, boolean isFromPreData) {
        associationList = list;
        if (associationList != null) {
            if (associationList.size() == 1) {
                myHomePreferences.setVisibility(GONE);
            } else {
                myHomePreferences.setVisibility(VISIBLE);
            }
        }
        displayUserPreferredChannel = display_user_preferred_channel;
        mFeedsNotificationsCount = feedNotificationsCount;
        chooseCommunityJsonObj = communityList;
        channelsList = channelsLists;
        networkChannelId = networkChannel_id;
        setNotificationCountOnBadge();
        if (dash_viewPager != null && dash_viewPager.getCurrentItem() == 0) {
            requestForCategoriesData(page_num);
        }
        if (displayUserPreferredChannel) {
            firstRunExperience = sharedPrefObj.getPref("EXPERIENCE", true);
            if (firstRunExperience) {
                return;
            }
            if (associationList != null) {
                Intent intent = new Intent(DashboardActivity.this, DefaultHomePageActivity.class);
                intent.putExtra("AssociationList", associationList.toString());
                //startActivityForResult(intent, 50505);
                launchActivityForResults.launch(intent);
            }
        }
    }

    private void checkForNotificationSettings() {
        RealmResults<RealmNotificationSettingsInfo> categoryList = realmManager.getNotificationSettingsFromDB();
        if (categoryList.size() == 0) {
            JSONObject reqObj = new JSONObject();
            try {
                reqObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.NOTIFICATION_SETTINGS_CATEGORIES, reqObj.toString(), "NOTIFICATION_SERVICE_CATEGORIES", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        if (successResponse != null && !successResponse.isEmpty()) {
                            Log.i(TAG, "onSuccessResponse - " + successResponse);
                            try {
                                JSONObject responseObj = new JSONObject(successResponse);
                                JSONArray responseArray = new JSONArray();
                                if (responseObj.has(RestUtils.TAG_STATUS) && responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                    if (responseObj.has(RestUtils.TAG_DATA)) {
                                        JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                        responseArray = dataObj.optJSONArray(RestUtils.TAG_NOTIFICAYTION_CATEGORIES);
                                        realmManager.clearNotificationCategoryRealmData();
                                        int len = responseArray.length();
                                        for (int i = 0; i < len; i++) {
                                            JSONObject channelResult = responseArray.optJSONObject(i);
                                            if (channelResult.optInt(RestUtils.TAG_CATEGORY_ID) == 5) {
                                                if (channelResult.optBoolean(RestUtils.TAG_IS_ENABLE)) {
                                                    mySharedPref.savePref("QB_PUSH_SUBSCRIPTION", true);
                                                } else {
                                                    mySharedPref.savePref("QB_PUSH_SUBSCRIPTION", false);
                                                }
                                            }
                                            realmManager.insertNotificationSettngsInfo(realm, channelResult.optInt(RestUtils.TAG_CATEGORY_ID), channelResult.optBoolean(RestUtils.TAG_IS_ENABLE), channelResult.toString());
                                        }
                                    }
                                }
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        Log.i(TAG, "onErrorResponse()");

                    }
                }).sendSinglePartRequest();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateCategoryUnreadCountEvent event) {
        if (event == null) {
            return;
        }
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryId() == event.getCategoryId()) {
                categoryList.get(i).setUnreadCount(event.getCountNeedtoUpdate());
                if (categoriesAdapter != null) {
                    categoriesAdapter.notifyDataSetChanged();
                }
                return;
            }
        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_post:
                        if (isConnectingToInternet()) {
                            Intent intent = new Intent(DashboardActivity.this, MyBookmarksActivity.class);
                            intent.putExtra("Navigation", "MyPosts");
                            intent.putExtra(RestUtils.TAG_OTHER_USER_ID, basicInfo.getDoc_id());
                            startActivity(intent);
                            drawerLayout.closeDrawers();
                        }

                        break;

                    case R.id.nav_bookmarks:
                        AppUtil.logUserEventWithDocIDAndSplty("MyBookmarksListing", basicInfo, DashboardActivity.this);
                        Intent intent = new Intent(DashboardActivity.this, MyBookmarksActivity.class);
                        intent.putExtra(RestUtils.NAVIGATATION, "Bookmarks");
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                    case R.id.nav_home_preferences:
                        if (associationList != null) {
                            displayPreferredChannelDialog(false);
                            drawerLayout.closeDrawers();
                            navigationView.getMenu().getItem(R.id.nav_home_preferences).setChecked(false);

                        }
                        break;
                    case R.id.nav_speciality_preferences:
                        AppUtil.logUserEventWithDocIDAndSplty("MyPreferenceViewing", basicInfo, DashboardActivity.this);
                        Intent intent1 = new Intent(DashboardActivity.this, PreferencesActivity.class);
                        intent1.putExtra(RestUtils.CHANNEL_ID, 0);
                        //startActivityForResult(intent, PREFERENCE_ACTION);
                        launchActivityForResults.launch(intent1);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_notification_preferences:
                        // launch new intent instead of loading fragment
                        Intent NotificationSettingsActivity = new Intent(mContext, NotificationSettingsActivity.class);
                        startActivity(NotificationSettingsActivity);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_invite_whiteCoats:
                        AppUtil.logUserEventWithDocIDAndSplty("InvitingtoWhiteCoats", basicInfo, DashboardActivity.this);
                        Intent shareIntent1 = new Intent();
                        shareIntent1.setAction(Intent.ACTION_SEND);
                        shareIntent1.setType("text/plain");
                        String link_val = "https://invite.whitecoats.com";
                        String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
                        shareIntent1.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
                        shareIntent1.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.inviate_to_whiteCoats) + Html.fromHtml(body));
                        startActivity(Intent.createChooser(shareIntent1, "Invite via"));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_help:
                        Intent intentsupport = new Intent(getApplicationContext(), ContactSupport.class);
                        startActivity(intentsupport);
                        drawerLayout.closeDrawers();
                    default:

                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);


                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);


        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private BasicInfo getBasicInfo() {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setDoc_id(this.basicInfo.getDoc_id());
        basicInfo.setFname(this.basicInfo.getFname());
        basicInfo.setLname(this.basicInfo.getLname());
        basicInfo.setEmail(this.basicInfo.getEmail());
        basicInfo.setEmail_verify(this.basicInfo.isEmailVerified());
        basicInfo.setEmail_visibility(this.basicInfo.getEmail_visibility());
        basicInfo.setPhone_num(this.basicInfo.getPhone_num());
        basicInfo.setPhone_num_visibility(this.basicInfo.getPhone_num_visibility());
        basicInfo.setPhone_verify(this.basicInfo.isMobileVerified());
        basicInfo.setSplty(this.basicInfo.getSplty());
        basicInfo.setSubSpeciality(this.basicInfo.getSubSpeciality());
        basicInfo.setSpecificAsk(this.basicInfo.getSpecificAsk());
        basicInfo.setAbout_me(this.basicInfo.getAbout_me());
        basicInfo.setWebsite(this.basicInfo.getWebsite());
        basicInfo.setBlog_page(this.basicInfo.getBlog_page());
        basicInfo.setFb_page(this.basicInfo.getFb_page());
        basicInfo.setLinkedInPg(this.basicInfo.getLinkedInPg());
        basicInfo.setTwitterPg(this.basicInfo.getTwitterPg());
        basicInfo.setInstagramPg(this.basicInfo.getInstagramPg());
        basicInfo.setPic_url(this.basicInfo.getPic_url());
        basicInfo.setPic_name(this.basicInfo.getPic_name());
        basicInfo.setProfile_pic_path(this.basicInfo.getProfile_pic_path());
        basicInfo.setTot_caserooms(this.basicInfo.getTot_caserooms());
        basicInfo.setTot_contacts(this.basicInfo.getTot_contacts());
        basicInfo.setTot_groups(this.basicInfo.getTot_groups());
        basicInfo.setUserType(this.basicInfo.getUser_type_id());
        basicInfo.setUserSalutation(this.basicInfo.getUser_salutation());
        basicInfo.setFeedCount(this.basicInfo.getFeedCount());
        basicInfo.setLikeCount(this.basicInfo.getLikesCount());
        basicInfo.setShareCount(this.basicInfo.getShareCount());
        basicInfo.setCommentCount(this.basicInfo.getCommentsCount());
        basicInfo.setFollowersCount(this.basicInfo.getFollowersCount());
        basicInfo.setFollowingCount(this.basicInfo.getFollowingCount());
        basicInfo.setDocProfileURL(this.basicInfo.getDocProfileURL());
        basicInfo.setDocProfilePdfURL(this.basicInfo.getDocProfilePdfURL());
        basicInfo.setOverAllExperience(this.basicInfo.getOverAllExperience());

        return basicInfo;
    }


    public void mandatoryPageDialog(Activity activity) {
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_mandatory_page, viewGroup, false);
        AlertDialog optionDialog = new AlertDialog.Builder(this).create();
        optionDialog.setCancelable(false);
        //setting the view of the builder to our custom view that we already inflated
        optionDialog.setView(dialogView);
        if (!isFinishing()) {
            optionDialog.show();
        }

    }

}
