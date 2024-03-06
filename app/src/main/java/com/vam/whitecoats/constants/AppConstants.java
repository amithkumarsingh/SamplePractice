package com.vam.whitecoats.constants;

import android.graphics.Matrix;

import com.google.common.collect.ListMultimap;

import java.util.ArrayList;

public class AppConstants {
	
	public static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	

	public static final String APP_DATE_FORMAT		= "yyyy-MM-dd";
	public static final String APP_TIME_FORMAT		= "HH:mm";

	//REQ_INDICATORS
	public static final int LOGIN_REQ_INDICATOR					= 1;
	public static final int REGISTRATION_STEP_1_REQ_INDICATOR	= 2;
	public static final int GET_CATEGORIES_REQ_INDICATOR 		= 3;
	public static final int GET_SUB_CATEGORIES_REQ_INDICATOR 	= 4;
	public static final int GET_USER_INFO_BY_USER_ID_INDICATOR 	= 5;
	public static final int CREATE_RESUME_REQ_STEP1_INDICATOR 	= 6;
	public static final int REGISTRATION_STEP_2_REQ_INDICATOR 	= 7;
	public static final int CREATE_RESUME_REQ_STEP2_INDICATOR   = 8;
	public static final int VERFICATION_REQ_INDICATOR   		= 9;
	public static final int COMPANY_LIST_REQ_INTICATOR          =10;
	public static final int EVENT_CREATE_REQ_INDICATOR   		= 11;
	public static final int EVENT_LIST_REQ_INDICATOR   			= 12;
	public static  int IS_USER_VERIFIED_CONSTANT  			= 0;




	//REQ_TAG
	public static final String LOGIN_REQ_TAG = "LOGIN REQ TAG";
	public static final String REGISTRATION_STEP_1_REQ_TAG = "REGISTER REQ STEP 1 TAG";
	public static final String REGISTRATION_STEP_2_REQ_TAG = "REGISTER REQ STEP 2 TAG";
	public static final String CATEGORY_TAG = "CATEGORY TAG";
	public static final String VERFICATION_REQ_TAG		= "verfication request tag";
	public static final String COMPANY_LIST_REQ_TAG     ="COMPANY REQ TAG";
	public static final String EVENT_CREATE_REQ_TAG		= "event create request tag";
	public static final String EVENT_LIST_REQ_TAG		= "event list request tag";
	

	
	public static final String APP_TRUE  = "true";
	public static final String APP_FALSE = "false";
	
	public static final String USER ="User";
	public static final String COMPANY ="Company";
	public static final String UNIVERSITY = "University";
	
	
	public static final String USER_NAME= "username";
	public static final String PASSWORD = "password";
	public static final String REMEMBER_ME_CHECK_BOX_STATUS = "remember me check box status";
	
	//Bundle args
	
	/*bundle.putString("C", regVO.getCreateddate());
	bundle.putString("", regVO.getEmail());
	bundle.putString("", regVO.getStatus());
	bundle.putString("", regVO.getUserID());
	bundle.putString("", regVO.getUserName());
	bundle.putString("", regVO.getUserType());*/
	
	public static final String BUNDLE_REG_STEP_1			= "bundl_REG_STEP_1";
	
	public static final String BUNDLE_USER_NAME			= "bundle_username";
	public static final String BUNDLE_CREATED_DATE		= "bundle_createddate";
	public static final String BUNDLE_EMAIL				= "bundle_email";
	public static final String BUNDLE_STATUS			= "bundle_status";
	public static final String BUNDLE_USER_ID			= "bundle_userid";
	public static final String BUNDLE_USER_TYPE			= "bundle_usertype";
	
	public static final String AVATAR			= "avatar";
	public static final String COVER			= "cover";
	
	public static final String AVATAR_IMAGE_PROFILE_PATH   	= "avatar profile path";
	public static final String COVER_IMAGE_PROFILE_PATH		= "cover profile path";
	
	public static final String ACTIVE = "active";
	public static boolean isAttachmentsUploaded = true;
	public static boolean isMediaServiceRequired = false;
	public static boolean neverAskAgain_Camera = false;
	public static boolean neverAskAgain_Phone = false;
	public static boolean neverAskAgain_Library = false;
	public static boolean neverAskAgain_Location = false;

    public static final int FILE_SELECT_CODE   = 0;
    public static final int CAMERA_PIC_REQUEST = 1;
    public static final int IMAGE_SAVE_RESULT  = 3;

    public static Matrix mMatrix = new Matrix();
    public static float mScaleFactor = .4f;
    public static float mRotationDegrees = 0.f;
    public static float mFocusX = 0.f;
    public static float mFocusY = 0.f;
    public static int mAlpha = 255;
    public static int mImageHeight, mImageWidth;
	/**
	 * Prod/Stage Account Key
	 */
	public static final String PROD_ACCOUNT_KEY = "gzPjA6N1VtsFtx4Mgwfd";
	/**
	 * Dev Account Key
	 */
	public static final String DEV_ACCOUNT_KEY = "KqqGTxy9N1GbEqUqwfS7";
	/**
	 * QA Account Key
	 */
	public static final String QA_ACCOUNT_KEY = "86e273PybpW8uAfgnrV5";

	public static final String UAT_ACCOUNT_KEY = "x7-vGGZncB2vzRnssQpK";
	//QB credentials for DEV Environment
	public static final String DEV_APP_ID = "14";
	public static final String DEV_AUTH_KEY = "TMuPrmK5T4ZgKMM";
	public static final String DEV_AUTH_SECRET = "zXZOe4ayDJxEh9s";

	//QB credentials for QA Environment
	public static final String QA_APP_ID = "26";
	public static final String QA_AUTH_KEY = "zzdQN2qTjray5XS";
	public static final String QA_AUTH_SECRET = "6zvFPpD7haGU2gZ";

	//QB credentials for STAGE Environment
	public static final String STAGE_APP_ID = "5";
	public static final String STAGE_AUTH_KEY = "JereYnVTuzw4-QF";
	public static final String STAGE_AUTH_SECRET = "dDm9QTgny-H7t9G";

	//QB credentials for UAT Environment
	public static final String UAT_APP_ID = "35";
	public static final String UAT_AUTH_KEY = "ZNVkqZ82uu8dk94";
	public static final String UAT_AUTH_SECRET = "PBv8TxWGaGZDEbW";

	//QB credentials for PROD Environment
	public static final String PROD_APP_ID = "4";
	public static final String PROD_AUTH_KEY = "qexXf3mcqhX-LN9";
	public static final String PROD_AUTH_SECRET = "qA-DDWgeAwUBc4A";

	//new qb arch
	public static final String QB_ApiDomain = "https://apivaluemomentum.quickblox.com";
	public static final String QB_ChatDomain = "chatvaluemomentum.quickblox.com";
	public static final String QB_BucketName = "qb-valuemomentum-s3";




	public static final int DEFAULT_PACKET_REPLY_TIMEOUT = 15 * 1000;

	public static String USER_LOGIN = "";
	public static String USER_PASSWORD = "";

	public static ListMultimap<String, String> Pre_ProMemmultimap;

	public static ListMultimap<String,String> Pre_PrintPubmap;
	public static ListMultimap<String,String> Pre_OnlinePubmap;

	public static String str_title,str_authors,str_journals,str_webpage,awards_title,presented_at,year_awarded,add_membership;

	// In GCM, the Sender ID is a project ID that you acquire from the API console
	public static final String PROJECT_NUMBER = "252981997001";
	//for dev 967234336320
	// for production 39051953962";
	public static final String EXTRA_MESSAGE = "message";

	public static final String GCM_NOTIFICATION = "GCM Notification";
	public static final String GCM_DELETED_MESSAGE = "Deleted messages on server: ";
	public static final String GCM_INTENT_SERVICE = "GcmIntentService";
	public static final String GCM_SEND_ERROR = "Send error: ";
	public static final String GCM_RECEIVED = "Received: ";


	public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
	public static final String REGISTRATION_COMPLETE = "registrationComplete";
	public static final String UPDATED_CASE_TITLE="updated_case_title";

	public static ArrayList<String> likeActionList=new ArrayList<>();

	public static ArrayList<Integer> unreadChannelsList=new ArrayList<>();

	public  static Boolean IS_USER_VERIFIED = false;
	public  static int COACHMARK_INCREMENTER = 1;
	public  static int login_doc_id = 0;

	//urbanairship keys
	/*public static final String AIRSHIP_PRODUCTIONAPPKEY="0DGHOJ9VR_6s1MG8BssaJw";
	public static final String AIRSHIP_PRODUCTIONAPPSECRET="NvUoVKPITnGrF3xlVeKobw";
	public static final String AIRSHIP_DEVELOPMENTAPPKEY="4HC2A6qSTvKe2tOX2WRang";
	public static final String AIRSHIP_DEVELOPMENTAPPSECRET="pCdROZqdT-qddauvDF5i1Q";
	public static final String AIRSHIP_QAAPPKEY="9vq2oPTaQhaZyBg2k3QcaA";
	public static final String AIRSHIP_QAAPPSECRET="1HWnDJhFQUOAF6qTo6WJdA";
	public static final String AIRSHIP_STAGEAPPKEY="A5VWudi4QvOHaNUUbqDepA";
	public static final String AIRSHIP_STAGEAPPSECRET="syrIigRLRdC4BFxb6g1NSw";
	public static final String AIRSHIP_UATAPPKEY="LS-Xy3osQM-XdbJECHAJjw";
	public static final String AIRSHIP_UATAPPSECRET="nMlOCFQGQy-KI707Qcat0Q";*/

}
