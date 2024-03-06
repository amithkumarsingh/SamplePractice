package com.vam.whitecoats.constants;

import static com.vam.whitecoats.App_Application.REST_DOMAIN;
import static com.vam.whitecoats.App_Application.REST_GATEWAY_DOMAIN;

public class RestApiConstants {
    //qa  server
    public static final String QA_HOST = "qa-services.whitecoats.net";

    //kanban QA server
    public static final String QA_KB_HOST = "qakb-services.whitecoats.net";

    //production server
    public static final String PROD_HOST = "services.whitecoats.com";

    //staging server
    public static final String STAGE_HOST = "stage-services.whitecoats.com";

    //DEV server
    public static final String DEV_HOST = "13.127.224.168";

    //staging uat server
    public static final String UAT_STAGE_HOST = "uat-services.whitecoats.com";

    //qa kanban server
    public static final String QA_KANBAN_HOST = "qakb-services.whitecoats.net";

    public static final String REGISTRATION = REST_DOMAIN + "register";
    public static final String RESENDEMAIL = REST_DOMAIN + "resendemail";
    public static final String EMAIL_VALIDATION = REST_DOMAIN + "chkactivate";
    public static final String AUTH = REST_DOMAIN + "auth";
    public static final String GET_CONTACTS = REST_DOMAIN + "getcnts";
    public static final String NEXT_LINK = "https://www.google.com/m8/feeds/contacts/default/full";
    public static final String NO_RECORDS = "NO_MORE_RECORDS";
    public static final String INVITE_CONTACTS = REST_DOMAIN + "inviteCnts";
    public static final String LOGIN = REST_DOMAIN + "login";
    public static final String RESTORE_LOGIN = REST_DOMAIN + "v2/restore/loginProfile";
    public static final String LOGOUT = REST_DOMAIN + "logout";
    public static final String CHANGE_PASSWORD = REST_DOMAIN + "changePsswd";
    public static final String EDITPERSONALDETAILS = REST_DOMAIN + "editPersonalDet";
    public static final String ACADDET = REST_DOMAIN + "acaddet";
    public static final String UPDATEPROFILEDETAILS = REST_DOMAIN + "updateProfDetails";
    public static final String EDIT_ABOUT_DETAILS = REST_DOMAIN + "editAboutMeDet";
    public static final String PROFESSIONAL_MEMBERSHIP = REST_DOMAIN + "profMemDet";
    public static final String PUBLICATIONS = REST_DOMAIN + "pubprintDet";
    public static final String SEARCH_CONTACT = REST_DOMAIN + "searchContacts";
    public static final String SEARCH_NETWORK = REST_DOMAIN + "v1/search";
    public static final String ACADEMIC_SUGGESTIONS = REST_DOMAIN + "autoSuggestions";
    public static final String UPDATE_MANDATORY_DATA = REST_DOMAIN + "updateMandatoryData";
    public static final String ACK_MANDATORY = REST_DOMAIN + "mandatoryAck";
    public static final String VISIT_OTHER_PROFILE = REST_DOMAIN + "visitOther";
    public static final String INVITE_FOR_CONNECT = REST_DOMAIN + "inviteForConnect";
    public static final String CONNECT_INVITE = REST_DOMAIN + "connectInviteResponse";
    public static String RESTORE_DOC_PROFILE = REST_DOMAIN + "restoreDocProfile";
    public static final String CHANGE_PASSWORD_ACK = REST_DOMAIN + "changePsswdAck";
    public static final String GET_VISITED_CONTACTS = REST_DOMAIN + "getVisitedContacts";
    public static final String CREATE_GROUP = REST_DOMAIN + "group";
    public static final String GROUP_INVITE_RESPONSE = REST_DOMAIN + "group/invite/response";
    public static final String DOWNLOAD_IMAGE_LINK = REST_DOMAIN + "downloadLinks";
    public static final String GROUP_MEM = REST_DOMAIN + "group/member";
    public static final String CASEROOM_MEM = REST_DOMAIN + "caseroom/member";
    public static final String GROUP_UPDATE = REST_DOMAIN + "group/update";
    public static final String PUSH_GCM_NOTIFICATION = REST_DOMAIN + "pushGCMNotification";
    public static final String CREATE_CASEROOM = REST_DOMAIN + "caseRoom";
    public static final String CASEROOM_INVITE = REST_DOMAIN + "caseroom/invite";
    public static final String CASEROOM_INVITE_RESPONSE = REST_DOMAIN + "caseroom/invite/response";
    public static final String GET_CASEROOM_SUMMARY = REST_DOMAIN + "getCaseRoomSummary";
    //community url's
    //for  local centos
    //public static final String Community_Local_Dayfirstfeed = REST_DOMAIN + "v2/channel-content/dayFirstFeedInfo";
    //localbox actual
    public static final String Community_Local_Dayfirstfeed = REST_DOMAIN + "v2/feeds/dayFirstFeedInfo";
    //communityfeedDashboard
    public static final String Channel_Dashboard = REST_DOMAIN + "v2/communityDashboard";
    //community Department Info
    public static final String Channel_Dept_info = REST_DOMAIN + "v2/community/deptInfo";
    //community getmember info
    public static final String Community_getMemberInfo = REST_DOMAIN + "v2/community/memberInfo";
    //community about View
    public static final String Community_About_View = REST_DOMAIN + "v2/community/about";
    //content about View
    public static final String Content_About = REST_DOMAIN + "v2/channel/about";
    //content/community about View
    public static final String Channel_About = REST_DOMAIN + "v2/channel/about";
    //community about Edit
    public static final String Community_About_Edit = REST_DOMAIN + "v2/community/editAbout";
    //Community Timeline
    public static final String Community_Timeline = REST_DOMAIN + "v2/community/timeline";
    //Content Timeline
    public static final String Content_Timeline = REST_DOMAIN + "v2/content/timeline";
    //Community MakePost
    //public static final String Community_Make_Post = REST_DOMAIN + "v4/community/makePost ";
    public static final String Community_Make_Post = REST_DOMAIN + "v5/community/makePost ";
    //Community DeletePost
    public static final String Community_Delete_Post = REST_DOMAIN + "v2/community/deletePost";
    //Community ViewPost
    public static final String Community_View_Post = REST_DOMAIN + "v2/community/viewPost";
    /*
     * Member Directory
     */
    public static final String MEMBER_DIRECTORY = REST_DOMAIN + "v2/community/deptInfo";
    public static final String MEMBER_INFO = REST_DOMAIN + "v2/community/membersInfo";
    /**
     * Restore User connections
     */
    public static final String RESTORE_USER_CONNECTS = REST_DOMAIN + "connectsInfo";
    public static final String GET_CHANNEL_PREFERENCES = REST_DOMAIN + "v2/channel/getChannelPreferences";
    public static final String SET_CHANNEL_PREFERENCES = REST_DOMAIN + "v2/channel/setChannelPreferences";
    public static final String GET_COMMENTS = REST_DOMAIN + "v1/feedSocialInteractions/getComments";
    public static final String GET_Likes = REST_DOMAIN + "v1/feedSocialInteractions/getLikes";
    public static final String SOCIAL_INTERACTIONS = REST_DOMAIN + "v1/feedSocialInteractions";

//    public static final String REGISTRATION_PROCESS = REST_DOMAIN + "v2/register";
public static final String REGISTRATION_PROCESS = REST_DOMAIN + "v3/signup";
    //public static final String MCA_UPLOAD_REST = REST_DOMAIN + "upload";
    public static final String MCA_UPLOAD_REST = REST_DOMAIN + "v2/upload";
    public static final String RESET_PASSWORD = REST_DOMAIN + "changePassword";
    public static final String UPDATE_PASSWORD = REST_DOMAIN + "updatePassword";

    public static final String CONTACT_SUPPORT = REST_DOMAIN + "contactSupport";
    public static final String FORGOT_PASSWORD_REST = REST_DOMAIN + "forgotPassword";
    public static final String SEND_OTP = REST_DOMAIN + "sendOTP";
    public static final String VIEW_FEED_REST = REST_DOMAIN + "feeds/updateViewCount";
    public static final String FEED_FULL_VIEW = REST_DOMAIN + "feeds/viewFeed";

    public static final String MEDIA_COMPLETE_SERVICE = REST_DOMAIN + "v2/channel/getMediaSections";
    public static final String MEDIA_SECTION_SERVICE = REST_DOMAIN + "v2/channel/getSectionData";
    public static final String ABOUT_ME_SERVICE = REST_DOMAIN + "aboutMe";
    public static final String EDIT_PERSONAL_INFO = REST_DOMAIN + "v1/userProfile/edit";
    public static final String VIEW_USER_PROFILE = REST_DOMAIN + "v1/userProfile/view";
    public static final String CREATE_USER_PROFILE = REST_DOMAIN + "v1/userProfile/create";
    public static final String UPDATE_USER_PROFILE = REST_DOMAIN + "v1/userProfile/update";
    public static final String DELETE_USER_PROFILE = REST_DOMAIN + "v1/userProfile/delete";
    public static final String VERIFY_USER_EMAIL = REST_DOMAIN + "v1/userProfile/email/verify";
    public static final String UPDATE_SURVEY = REST_DOMAIN + "v1/feed/survey/update";
    public static final String GET_INVITE_CONNECTS_SERVICE = REST_DOMAIN + "v1/getNonConnectedUsers";
    public static final String GET_DASHBOARD_DATA = REST_DOMAIN + "v3/getDashBoardData";
    public static final String GET_AGGREGATED_DASHBOARD_DATA = REST_DOMAIN + "v4/getDashBoardData";
    public static final String SUBSCRIBED_CHANNELS = REST_DOMAIN + "v2/user/channel/list";
    public static final String CHANNEL_TIMELINE = REST_DOMAIN + "v3/channelTimeline";
    public static final String FEED_FULL_VIEW_UPDATED = REST_DOMAIN + "v3/fullViewFeed";
    public static final String DEPARTMENT_AND_SPECIALITY = REST_DOMAIN + "v2/getDepartmentsAndSpecialties";
    public static final String BOOKMARK = REST_DOMAIN + "v1/feed/bookmark";
    public static final String BOOKMARK_LIST = REST_DOMAIN + "v1/feed/bookmark/list";

    public static final String SHARE_A_FEED = REST_DOMAIN + "v1/share/feed";
    public static final String VIEW_SHARED_FEED = REST_DOMAIN + "v1/share/feed/fullView";

    public static final String RECOMMENDATIONS_SERVICE = REST_DOMAIN + "v2/recommend/feeds";

    public static final String VIEW_USER_POSTS_API = REST_DOMAIN + "v1/feeds/user";

    public static final String CATEGORY_TIMELINE = REST_DOMAIN + "v2/getCategoryData";

    public static final String GLOBAL_SEARCH_RESULTS = REST_DOMAIN + "v1/globalSearch";

    public static final String SEARCH_RESULTS = REST_DOMAIN + "v2/search";

    public static final String SHARE_A_FEED_V2 = REST_DOMAIN + "v2/share/feed";

    public static final String GET_SHORTURL_DATA = REST_DOMAIN + "v1/shorturl/getinfo";

    public static final String GET_QB_USER_DETAILS = REST_DOMAIN + "v1/userQBDetails";

    public static final String APP_UPGRADE_API_CALL = REST_DOMAIN + "v1/user/upgradeinfo/save";

    public static final String DEVICE_INFO = REST_DOMAIN + "v1/user/devicenotification";

    public static final String SUBSCRIPTION_SERVICE = REST_DOMAIN + "v1/user/channel/subscription";


    public static final String GET_FOLLOW_LIST = REST_DOMAIN + "v1/user/follow/list";

    public static final String USER_FOLLOW_REST_API = REST_DOMAIN + "v1/user/follow";

    public static final String SHARE_A_PROFILE_REST_API = REST_DOMAIN + "v1/userprofile/share";


    public static final String LOG_USER_EVENT_API = REST_GATEWAY_DOMAIN + "/wc-gateway/api/user/v1/events";

    public static final String DELETE_COMMENT = REST_DOMAIN + "v1/feeds/comment/delete";

    public static final String UPDATE_COMMENT = REST_DOMAIN + "v1/feeds/comment/update";
    //onEdit specialities
    public static final String GET_SPECIALITIES_DEPARTMENTS = REST_DOMAIN + "v1/feeds/get";
    //MODIFIED EDIT FEED
    public static final String EDIT_POST = REST_DOMAIN + "v1/feeds/update";
    //aws keys
    public static final String AWS_KEYS = REST_DOMAIN + "v1/aws/resources/get";

    public static final String PREFERRED_CHANNEL_SELECTION_API = REST_DOMAIN + "v1/user/preferredchannel/create";

    public static final String API_KEY = REST_DOMAIN + "v2/external-resources/get";

    public static final String GET_NOTIFICATIONS_LIST_API = REST_DOMAIN + "v1/user/notifications/list";

    //notification_settingS
    public static final String NOTIFICATION_SETTINGS_UPDATE = REST_DOMAIN + "v1/user/notification-categories/update";

    //notification_settingS_categories
    public static final String NOTIFICATION_SETTINGS_CATEGORIES = REST_DOMAIN + "v1/user/notification-categories/list";

    public static final String UP_TO_DATE_API = REST_DOMAIN + "v1/external/getUrl";

    public static final String GET_SUGGESTED_FEEDS_API = REST_DOMAIN + "v1/feed/getSuggestedFeeds";

    public static final String GET_SUGGESTED_FEEDS_LIST_API = REST_DOMAIN + "v1/feed/getSuggestedFeedList";

    public static final String GET_RELATED_FEEDS_API = REST_DOMAIN + "v1/feed/getRelatedFeeds";

    public static final String WEBINAR_REGISTER_NOW_SERVICE = REST_DOMAIN + "v1/user/webinar/register";

    public static final String FETCH_USER_CONNECTS_API=REST_DOMAIN+"getConnectsInfo";

    public static final String FETCH_USER_CONNECTS_NOTIFICATIONS_API=REST_DOMAIN+"getConnectNotifications";

    public static final String GET_USER_CATEGORIES_LIST=REST_DOMAIN+"v1/feed/categories/list";

    public static final String GET_DRUG_CLASS_LIST = REST_DOMAIN+"/v1/getDrugClasses";

    public static final String GET_DRUG_SUB_CLASS_LIST = REST_DOMAIN+"/v1/getDrugSubClasses";

    public static final String GET_DRUG_LIST = REST_DOMAIN+ "/v1/getDrugs";

    public static final String GET_DRUG_INFO = REST_DOMAIN+ "/v1/getDrugInfo";

    public static final String GET_DRUG_FEEDS = REST_DOMAIN+ "v1/getFeedsByDrugName";

    public static final String GET_DRUG_SEARCH = REST_DOMAIN+ "v1/drug/search";

    public static final String GET_USER_CATEGORIES_DISTRIBUTION=REST_DOMAIN+"v1/feed/categories/get";

    public static final String GET_USER_CATEGORIES_SPECIALITY_DISTRIBUTION=REST_DOMAIN+"v1/category-distribution/getspecialities";

    public static final String GET_USER_SUB_CATEGORY_FEEDS=REST_DOMAIN+"v1/sub-categories/get";
    public static final String GET_USER_FEEDS_BY_SPECIALITY=REST_DOMAIN+"v1/getFeedsBySpeciality";
    public static final String GET_USER_CATEGORY_SEARCH=REST_DOMAIN+"v2/search/feeds";

    //public static final String GET_AD_SLOT_DEFINITIONS=REST_DOMAIN+"v1/notifications/mock/list";

    public static final String GET_AD_SLOT_DEFINITIONS=REST_DOMAIN+"v1/ads/slot/list";

    public static final String SUBMIT_JOB_APPLICATION_API=REST_DOMAIN+"v1/job/apply";
    public static final String SUBMIT_JOB_APPLICATION_API_V2=REST_DOMAIN+"v2/job/apply";
    public static final String GET_USER_CATEGORIES_LIST_V2=REST_DOMAIN+"v2/feed/categories/list";
    public static final String GET_CONTENT_FEEDS=REST_DOMAIN+"v1/app/tag/content";

    public static final String SUBSCRIBED_CHANNELS_CONTENT = REST_DOMAIN + "v1/app/tag/content";
    public static final String GET_SPECIALITY_LIST = REST_DOMAIN + "v1/getSpecialties";
    public static final String NEW_UPDATE_MANDATORY_DATA = REST_DOMAIN + "v2/updateMandatoryData";

    public static final String GET_PROFESSIONAL_JOB_FILTERS = REST_DOMAIN + "v1/job/search";

    public static final String GET_LATEST_EVENT_URL = REST_DOMAIN + "v1/feed/event/getUrl";
    public static final String GET_FEED_AD_SLOTS_API = REST_DOMAIN + "v1/ads/slot";

    public static final String DELETE_USER_ACCOUNT_API=REST_DOMAIN+"v1/user/deactivate";
    public static final String getExternalSpeciality = REST_DOMAIN + "v1/user/external/speciality";

    public static final String saveUserReportFeed = REST_DOMAIN + "reportFeed";

}
