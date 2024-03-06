package com.vam.whitecoats;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.utils.BKAppStatusUtil;
import com.doceree.androidadslibrary.ads.DocereeMobileAds;
import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryPerformance;
import com.gu.toolargetool.TooLargeTool;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.ENVIRONMENT;
import com.vam.whitecoats.constants.WhitecoatsFlavor;
import com.vam.whitecoats.core.realm.Migration;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.AppDataBindingComponent;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.broadCasts.NetworkChangeReceiver;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.BackgroundListener;
import com.vam.whitecoats.utils.Foreground;
import com.vam.whitecoats.utils.UpShotHelperClass;
import com.vam.whitecoats.utils.UpshotConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.sentry.android.core.SentryAndroid;


/**
 * Created by lokeshl on 5/7/2015.
 */
public class App_Application extends MultiDexApplication implements BKAppStatusUtil.BKAppStatusListener {
    public static final String TAG = App_Application.class.getSimpleName();

    private static App_Application instance;
    public static String REST_DOMAIN = "";
    public static String REST_GATEWAY_DOMAIN = "";
    public static String UPSHOT_APP_ID = "";
    public static String AIRSHIP_APP_ID = "";
    Boolean rememberChecked;
    public Realm realm;
    private static Executor threadPoolExecutor = null;
    private static int nofUnreadMessages;
    private RequestQueue mRequestQueue;
    private RealmManager realmManager;
    int doc_id;
    public static MySharedPref sharedPref;
    private UpShotHelperClass upshotHelper;
    private String flurryApiKey = "";

    @Override
    public void onCreate() {
        Log.i(TAG, getString(R.string._onCreate));
        super.onCreate();
        //Enable urban airship user notification
        // UAirship.shared().getPushManager().setUserNotificationsEnabled(true);

        DataBindingUtil.setDefaultComponent(new AppDataBindingComponent());
        getRequestQueue().getCache().clear();
        AppUtil.deleteDirectoryTree(getApplicationContext().getCacheDir());
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new BackgroundListener(getApplicationContext()));
        initApplication();

        /*
         * Set Default development server & default Flavor
         * Values - 'qa',qakb,'dev','stage','uat','prod'
         *
         * <p>This is only for development mode, in release mode app will generate flavors as specified
         * in gradle.</p>
         */
        ENVIRONMENT defaultEnvironment = null;
        if (BuildConfig.DEBUG) {
            defaultEnvironment = ENVIRONMENT.DEFAULT.setDefaultEnvironment("qa");
            WhitecoatsFlavor.DEFAULT.setDefaultFlavor("qa");
        }

        TooLargeTool.startLogging(this);
        /*r
         * Build Server Environment for each flavors
         */
        if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.QA.getName())) {
            REST_DOMAIN = "http://" + ENVIRONMENT.QA.getHost() + ":" + ENVIRONMENT.QA.getPortNumber() + "/ebiz-web/app/m/";
            REST_GATEWAY_DOMAIN = "http://qa-events.whitecoats.net:5001";
            //REST_GATEWAY_DOMAIN = "http://13.233.203.247:5001";
            UPSHOT_APP_ID = UpshotConstants.qa_app_id;

        } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.QAKB.getName())) {
            REST_DOMAIN = "http://" + ENVIRONMENT.QAKB.getHost() + ":" + ENVIRONMENT.QAKB.getPortNumber() + "/ebiz-web/app/m/";
            REST_GATEWAY_DOMAIN = "http://qa-events.whitecoats.net:5001";
            //REST_GATEWAY_DOMAIN = "http://13.233.203.247:5001";
            UPSHOT_APP_ID = UpshotConstants.qa_app_id;

        } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.DEV.getName())) {
            Log.i(TAG, "Development Environment - " + defaultEnvironment);
            //For release build defaultEnvironment will be null;
            if (defaultEnvironment != null) {
                REST_DOMAIN = defaultEnvironment.getDefaultRestDomain();
                REST_GATEWAY_DOMAIN = defaultEnvironment.getDefaultGateWayRestDomain();
                UPSHOT_APP_ID = defaultEnvironment.getUpshotAppId();

            } else {
                REST_DOMAIN = "http://" + ENVIRONMENT.DEV.getHost() + ":" + ENVIRONMENT.DEV.getPortNumber() + "/ebiz-web/app/m/";
                REST_GATEWAY_DOMAIN = "http://dev-events.whitecoats.net:5001";
                UPSHOT_APP_ID = UpshotConstants.dev_app_id;

            }
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.STAGE.getName())) {
            REST_DOMAIN = "https://" + ENVIRONMENT.STAGE.getHost() + "/ebiz-web/app/m/";
            REST_GATEWAY_DOMAIN = "http://stage-events.whitecoats.com" + ":5001";
            //For UAT
            //REST_GATEWAY_DOMAIN="http://52.66.29.232:5001";
            UPSHOT_APP_ID = UpshotConstants.stage_app_id;

        } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.UAT.getName())) {
            REST_DOMAIN = "https://" + ENVIRONMENT.UAT.getHost() + "/ebiz-web/app/m/";
            //REST_GATEWAY_DOMAIN="http://13.233.203.247:5001";
            //For UAT
            REST_GATEWAY_DOMAIN = "http://uat-events.whitecoats.com:5001";
            UPSHOT_APP_ID = UpshotConstants.uat_app_id;

        } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.PROD.getName())) {
            REST_DOMAIN = "https://" + ENVIRONMENT.PROD.getHost() + "/ebiz-web/app/m/";
            //REST_GATEWAY_DOMAIN="https://userevent.whitecoats.com:5001";
            REST_GATEWAY_DOMAIN = "https://events.whitecoats.com";
            UPSHOT_APP_ID = UpshotConstants.prod_app_id;

        }
        //Initialize doceree sdk
        DocereeMobileAds.initialize(this);
        FlurryAgent.setUserId(String.valueOf(doc_id));
        /*ENGG-3643 -- Updating the flurry SDK and Read Contacts Permission in Network App*/
        //Start
        if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.PROD.getName())) {
            flurryApiKey = "NPGXCY8P9F75H2FVTVVH";
            DocereeMobileAds.setApplicationKey("0b98ac3d-4521-44f7-b393-3da5a229f559");
        } else {
            flurryApiKey = "DTK9SYSYD5HBWDR48ZC6";
            DocereeMobileAds.setApplicationKey("3d7e35f8-99d7-4716-9604-77d410d15d97");

        }
        new FlurryAgent.Builder()
                .withDataSaleOptOut(false) //CCPA - the default value is false
                .withCaptureUncaughtExceptions(true)
                .withIncludeBackgroundSessionsInMetrics(true)
                .withLogLevel(Log.VERBOSE)
                .withPerformanceMetrics(FlurryPerformance.ALL)
                .build(this, flurryApiKey);
        //End
        SentryAndroid.init(this, options -> {
            String dns = "https://84fdf1286302428788746946a7c62f21@o574145.ingest.sentry.io/5830143";
            if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.PROD.getName())) {
                dns = "https://2b59633f7e974445bbd905811f87e986@o574145.ingest.sentry.io/5827713";
            }
            options.setDsn(dns);
            options.setTracesSampleRate(1.0);
        });
    }

    private void initApplication() {
        Log.i(TAG, "initApplication()");
        instance = this;
        sharedPref = new MySharedPref(this);
        upshotHelper = UpShotHelperClass.getInstance();
        upshotHelper.registerAppInUpshot(this, this);
        Foreground.init(this);
        try {
            Realm.init(this);
            RealmConfiguration config0 = new RealmConfiguration.Builder().
                    schemaVersion(19).
                    migration(new Migration()).
                    build();
            Realm.setDefaultConfiguration(config0);
            realm = Realm.getInstance(config0);
            realmManager = new RealmManager(this);
            doc_id = realmManager.getDoc_id(realm);
        } catch (Exception e) {
            Log.e(TAG, "Exception - ");
            e.printStackTrace();
        }
        /*
        instantiaing the executor service thread pool
         */
        int corePoolSize = 5;
        int maximumPoolSize = 10;

        int keepAliveTime = 45;


        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
        Date current_time1 = new Date();
        Log.v("Current Time", "before" + current_time1.getTime());
        App_Application.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
        Log.v("Current Time", "after" + current_time1.getTime());
        AppConstants.likeActionList = new ArrayList<>();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkChangeReceiver(), intentFilter);
    }


    public static Executor getCommonThreadPoolExecutor() {
        return threadPoolExecutor;
    }


    public static synchronized App_Application getInstance() {
        return instance;
    }

    public Boolean getRememberChecked() {
        return rememberChecked;
    }

    public void setRememberChecked(Boolean rememberChecked) {
        this.rememberChecked = rememberChecked;
    }

    public int getNumUnreadMessages() {
        return nofUnreadMessages;
    }

    public static void setNumUnreadMessages(int nofUnreadMessages) {
        App_Application.nofUnreadMessages = nofUnreadMessages;
    }


    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public int getAppVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private static Activity mCurrentActivity = null;

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static void setCurrentActivity(Activity mCurrentActivity1) {
        mCurrentActivity = mCurrentActivity1;
    }


    private String copyBundledRealmFile(InputStream inputStream, String outFileName) {
        File file = new File(this.getFilesDir(), outFileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Section related to Volley lib

    public RequestQueue getRequestQueue() {
        //For custom cache size
        /*if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mRequestQueue.start();
        }
        return mRequestQueue;*/
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setShouldCache(false);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    public void onAppComesForeground(Activity activity) {
        if (upshotHelper != null) {
            Log.d("UPSHOT_INIT", "start init");
            upshotHelper.initUpshot(activity);
        }
    }

    @Override
    public void onAppGoesBackground() {
        BrandKinesis brandKinesis = BrandKinesis.getBKInstance();
        brandKinesis.terminate(getApplicationContext());
    }

    @Override
    public void onAppRemovedFromRecentsList() {
        Log.d("UPSHOT_INIT", "app removed");
    }

    //volley section ends
}
