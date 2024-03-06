package com.vam.whitecoats.utils;

/**
 * Created by swathim on 16-09-2015.
 */
public class RestUtils {


    /**
     * Class Names
     **/
    public static final String LOGIN_ACTIVITY = "com.vam.whitecoats.ui.activities.LoginActivity";
    public static final String MYGCM_LISTENER_SERVICE = "com.vam.whitecoats.core.gcm.MyFcmListenerService";
    public static final String CHANGE_PASSWORD_ACTIVITY = "com.vam.whitecoats.ui.activities.ChangePasswordActivity";
    public static final String ABOUTME_ACTIVITY = "com.vam.whitecoats.ui.activities.AboutMeActivity";
    public static final String ACADEMIC_ACTIVITY = "com.vam.whitecoats.ui.activities.AcademicActivity";
    public static final String BASICPROFILE_ACTIVITY = "com.vam.whitecoats.ui.activities.BasicProfileActivity";
    public static final String PROFESSIONALDETAILS_ACTIVITY = "com.vam.whitecoats.ui.activities.ProfessionalDetActivity";
    public static final String PUBLICATIONS_ACTIVITY = "com.vam.whitecoats.ui.activities.PublicationsActivity";
    public static final String PROFESSIONAL_MEM_ACTIVITY = "com.vam.whitecoats.ui.activities.ProfessionalMemActivity";
    public static final String POST_SCREEN_ACTIVITY = "com.vam.whitecoats.ui.activities.CreatePostActivity";


    /**
     * Login
     **/
    public static final String TAG_USERNAME = "app_uname";
    public static final String TAG_PASSWORD = "app_pass";
    public static final String TAG_PASS_CHANGED = "password_change_required";
    public static final String TAG_SSTOKEN = "security_token";
    public static final String NAVIGATATE_FROM = "navigate_from";
    public static final String NAVIGATATION = "Navigation";
    public static final String DIALOG_OPTION = "dialogOption";


    public static final String TAG_REQ_DATA = "reqData";
    public static final String TAG_STATUS = "status";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_DATA = "data";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_ERROR = "error";
    public static final String TAG_ERROR_CODE = "errorCode";
    public static final String TAG_ERROR_MESSAGE = "errorMsg";


    /**
     * ChangePassword
     **/
    public static final String TAG_UPDATED_PASSWORD = "updated_password";
    public static final String TAG_OLD_PASSWORD = "old_password";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String TAG_QB_USER_ID = "qb_user_id";
    public static final String TAG_QB_USER_LOGIN = "qb_user_login";
    public static final String TAG_QB_USER_PSWD = "qb_user_pwd";
    public static final String TAG_USER_DIALOG_ID = "user_dialog_id";

    public static final String TAG_OPERATION = "oper";
    public static final String TAG_UPDATE = "update";
    public static final String TAG_CASE = "case";
    public static final String TAG_ARTICLE = "article";
    public static final String TAG_ADD = "add";
    public static final String TAG_DELETE = "delete";

    /**
     * AboutMe
     **/
    public static final String TAG_ABOUT_ME = "about_me";
    public static final String TAG_WEB_PAGE = "website_pg";
    public static final String TAG_BLOG_PAGE = "blog_pg";
    public static final String TAG_FB_PAGE = "fb_pg";
    public static final String TAG_LINKEDIN_PAGE = "linkedin_pg";
    public static final String TAG_TWITTER_PAGE = "twitter_pg";
    public static final String TAG_INSTAGRAM_PAGE = "instagram_pg";


    /**
     * AcademicInfo
     **/
    public static final String TAG_ACADEMIC_ID = "acad_id";
    public static final String TAG_DEGREE = "degree";
    public static final String TAG_DEGREES = "degrees";
    public static final String TAG_UNIVERSITY = "university";
    public static final String TAG_COLLEGE = "college";
    public static final String TAG_PASSING_YEAR = "passing_year";
    public static final String TAG_CURRENTLY = "currently_pursuing";


    /**
     * BasicProfile
     **/
    public static final String TAG_IS_DOCTOR_VALID = "doctor_valid";
    public static final String TAG_IS_USER_ACTIVE = "is_user_active";
    public static final String TAG_IS_USER_VERIFIED = "is_user_verified";
    public static final String TAG_IS_UPDATE = "isUpdate";
    public static final String TAG_IS_PARALLEL_CALL = "is_parallel_call";
    public static final String IS_HOME_LAUNCH = "is_home_launch";
    public static final String TAG_IS_CHAT_NOTIFICATION = "is_chat_notification";
    public static final String TAG_IS_FROM_NOTIFICATION = "is_from_notification";
    public static final String TAG_IS_PERSONALIZED_NOTIFICATION = "is_personalized";

    public static final String TAG_IS_CONNECT_NOTIFICATION = "is_connect_notification";
    public static final String TAG_PERSONAL = "personal";
    public static final String TAG_NAME = "name";
    public static final String TAG_FNAME = "fname";
    public static final String TAG_LNAME = "lname";
    public static final String TAG_SPLTY = "speciality";
    public static final String TAG_SPECIALIST = "specialist";
    public static final String TAG_USER_NAME = "userName";
    public static final String TAG_SUB_SPLTY = "subSpeciality";
    public static final String TAG_SUB_SPECIALITY = "sub_speciality";
    public static final String TAG_CNNTMUNVIS = "cnt_num_vis";
    public static final String TAG_CNNTEMAILVIS = "cnt_email_vis";
    public static final String TAG_USER_TYPE = "user_type";
    public static final String TAG_ID = "id";
    public static final String NOTIFICATION_TAG_ID = "tag_id";


    /**
     * Registration
     **/
    public static final String TAG_PASSWORD_REGISTRATION = "password";
    public static final String TAG_CONTACT_DETAILS_REGISTRATION = "contact_details";
    public static final String TAG_STAY_LOGGED_IN = "stay_logged_in";
    public static final String TAG_CONTACT = "contact";


    /**
     * ProfessionalInfo
     **/
    public static final String TAG_PROF_ID = "prof_id";
    public static final String TAG_WORKPLACE = "workplace";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_WEBSITE = "website";
    public static final String TAG_FACEBOOK_PAGE = "facebook_page";
    public static final String TAG_DESIGNATION = "designation";
    public static final String TAG_FROMDATE = "from_date";
    public static final String TAG_TODATE = "to_date";
    public static final String TAG_WORKING = "working_here";
    public static final String TAG_SHOW_ON_CARD = "show_on_card";
    public static final String TAG_AVAILABLE_DAYS = "available_days";
    public static final String TAG_WEEK_OF_DAYS = "week_of_days";
    public static final String TAG_FROM_TIME = "from_time";
    public static final String TAG_TO_TIME = "to_time";
    public static final String TAG_DEPARTMENT = "department";


    /**
     * Publications
     **/
    public static final String TAG_PRINT = "print";
    public static final String TAG_ONLINE = "online";
    public static final String TAG_PRINT_PUB_ID = "print_pub_id";
    public static final String TAG_ONLINE_PUB_ID = "online_pub_id";
    public static final String USER_PUBLICATION_TYPE = "userPublicationType";
    public static final String TAG_PUB_TYPE = "pub_type";
    public static final String TAG_TITLE = "title";
    public static final String TAG_DISPLAY = "display_tag";
    public static final String TAG_AUTHORS = "author_names";
    public static final String TAG_JOURNAL = "journal";
    public static final String TAG_WEBPAGE_LINK = "webpg_link";

    /**
     * AutoSuggession Tags
     **/
    public static final String TAG_JOURNALS = "journals";


    /**
     * ProfessionalMem
     **/
    public static final String TAG_PROF_MEM_NAME = "membership_name";
    public static final String TAG_MEM_ID = "prof_mem_id";
    public static final String TAG_AWARD_ID = "award_id";


    /**
     * View Others Profile
     **/
    public static final String TAG_ABOUT = "about";
    public static final String TAG_INFO = "info";
    public static final String TAG_PERSONAL_INFO = "personal_info";
    public static final String TAG_ACADEMIC_HISTORY = "acad_history";
    public static final String TAG_PROFESSIONAL_HISTORY = "prof_history";
    public static final String TAG_ONLINE_PUB_HISTORY = "online_pub_history";
    public static final String TAG_PRINT_PUB_HISTORY = "print_pub_history";
    public static final String TAG_MEM_HISTORY = "membership_history";
    public static final String TAG_AWARD_HISTORY = "award_history";
    public static final String TAG_PRESENTED_AT = "presented_at";
    public static final String TAG_YEAR = "year";
    public static final String TAG_OTHER_INFO = "other_info";
    public static final String TAG_CONNECT_STATUS = "connect_status";
    public static final String TAG_NETWORK_STATUS = "network_status";
    public static final String TAG_IS_CONNECTED = "is_connected";
    public static final String TAG_CASE_ROOM_COUNT = "caserooms_cnt";
    public static final String TAG_CONNECTS_COUNT = "connects_cnt";
    public static final String TAG_GROUPS_COUNT = "groups_cnt";
    public static final String TAG_FEED_COUNT = "feedCount";
    public static final String TAG_LIKES_COUNT = "likesCount";
    public static final String TAG_SHARE_COUNT = "shareCount";
    public static final String TAG_COMMENTS_COUNT = "commentsCount";
    public static final String TAG_ACTIVITY_COUNT = "activity_count";
    public static final String TAG_VERIFICATION_INFO = "verificationInfo";
    /**
     * Group Connect Notifications
     **/
    public static final String TAG_MEMBER_ADDED_DATE = "member_added_date";
    public static final String TAG_MEM_INFO = "members_info";
    public static final String TAG_INVITE_RESPONSE = "invite_response";
    public static final String TAG_IS_ADMIN = "is_admin";
    public static final String TAG_ADMIN_LIST = "admin_list";
    public static final String TAG_CONTACT_DETAILS = "contact_details";
    public static final String TAG_CONTACT_SEARCH_RESULTS = "contact_search_results";
    public static final String TAG_CONTACT_NUMBER = "contact_number";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_CARD_INFO = "card_info";
    public static final String TAG_DOC_ID = "doc_id";
    public static final String TAG_PAGE_NUM = "pg_num";
    public static final String TAG_PROFILE_PIC_NAME = "profile_pic_name";
    public static final String TAG_FULL_NAME = "full_name";
    public static final String TAG_CNT_EMAIL = "cnt_email";
    public static final String TAG_CNT_NUM = "cnt_num";
    public static final String TAG_EMAIL_VERIFY = "cnt_email_verified";
    public static final String TAG_PHONE_VERIFY = "cnt_num_verified";
    public static final String TAG_PROFILE_PIC_URL = "profile_pic_small_url";
    public static final String TAG_PROFILE_PIC_ORIGINAL_URL = "profile_pic_original_url";
    public static final String TAG_LOG_SMALL_URL = "logo_small_url";
    public static final String TAG_TAG_ID = "tag_id";


    /**
     * Notification Constants
     **/
    public static final String TAG_NOTIFICATION_IDS_LIST = "ntfyIdsList";
    public static final String TAG_NOTIFICATION_DATA = "notifications_data";
    public static final String TAG_NOTIFICATION_IDS = "notification_ids";
    public static final String TAG_NOTIFICATION_ID = "notification_id";
    public static final String TAG_TYPE = "type";
    public static final String TAG_FROM_DOC = "from_doc";
    public static final String TAG_FROMDOC = "fromDoc";
    public static final String TAG_NOTIFICATION_MESSAGE = "notification_message";
    public static final String TAG_TIME_RECEIVED = "time_recieved";
    public static final String TAG_ACCEPTED_FRD_REQUEST = "Connect request accepted";
    public static final String TAG_TO_DOC = "to_doc";
    public static final String TAG_INVITE_TEXT = "invite_text";
    public static final String TAG_INVITER_INFO = "inviter_info";
    public static final String TAG_GROUP_INFO = "group_info";
    public static final String TAG_GROUPINFO = "fromGroupInfo";
    public static final String TAG_XMPP_ROOM_JID = "xmpp_jid";
    public static final String TAG_GROUP_TITLE = "group_title";
    public static final String TAG_GROUP_PROFILE_IMG_NAME = "group_profile_img_name";
    public static final String TAG_GROUP_ID = "group_id";
    public static final String TAG_GROUP_CREATION_TIME = "group_created_date";
    public static final String TAG_GROUP_PIC_OPER = "group_pic_oper";
    public static final String TAG_CASE_ROOM_INVITE_INFO = "caseroom_invite_info";
    public static final String TAG_CASE_ROOM_INVITER_INFO = "fromInviterInfo";
    public static final String TAG_CASEROOMINVITE_INFO = "fromCaseInviteInfo";
    public static final String TAG_GROUPINVITER_INFO = "fromGroupInviterInfo";
    public static final String TAG_CASE_ROOM_GROUP_CREATED_DATE = "caseroom_group_created_date";
    public static final String DEPT_OR_DESIG = "dept_or_desig";
    public static final String COMMUNITY_NAME = "community_name";
    public static final String COMMUNITY_DESIGNATION = "community_designation";

    public static final String TAG_CONTENT_POST = "CHANNEL_CONTENT_ARTICLE";
    public static final String TAG_USER_VERIFIED = "USER_VERIFIED";
    public static final String TAG_TYPE_LIKE = "LIKE";
    public static final String TAG_TYPE_COMMENT = "COMMENT";
    public static final String TAG_TYPE_IMAGE = "image";
    public static final String TAG_TYPE_APPLICATION = "application";
    public static final String TAG_TYPE_VIDEO = "video";
    public static final String TAG_TYPE_PDF = "pdf";
    public static final String TAG_TYPE_GIF = ".gif";
    public static final String TAG_TYPE_AUDIO = "audio";
    public static final String IS_COMMUNITY_SET = "is_community_set";

    public static final String TAG_C_MSG_TYPE = "c_msg_type";


    /**
     * Image Download Link Constants
     **/
    public static final String TAG_SMALL_LINK = "small_link";
    public static final String TAG_ORIGINAL_LINK = "original_link";
    public static final String TAG_IMAGE_PATH = "image_path";


    /**
     * Create Group Constants and Add Another Member
     **/

    public static final String TAG_INVITER_DOC_ID = "inviter_doc_id";
    public static final String TAG_INVITER_QB_ID = "inviter_qb_id";
    public static final String TAG_ADD_INVITEES_LIST = "add_invitee_doc_id_list";
    public static final String TAG_FILE = "file";

    //PUSHNOTIFICAtion
    public static final String API_KEY = "API_KEY";
    public static final String REG_ID = "REG_ID";

    //create caseroom
    public static final String CASEROOM_SUMMARY_ID = "caseroom_summary_id";
    public static final String QUERY = "query";
    public static final String TITLE = "title";
    public static final String PATIENT_DETAILS_PRESENT = "patient_details_present";
    public static final String PATIENT_DETAILS = "patient_details";
    public static final String ATTACHMENTS = "attachments";
    public static final String TAG_CASEROOMSPECIALITIES = "crspecialities";

    public static final String AGE = "age";
    public static final String GENDER = "gender";
    public static final String OPER = "oper";
    public static final String SYMPTOMS = "symptoms";
    public static final String VITALS_ANTROPOMETRY = "vitals_antropometry";
    public static final String PATIENT_HISTORY = "patient_history";
    public static final String GENERAL_EXAMINATION = "general_examination";
    public static final String SYSTEMIC_EXAMINATION = "systemic_examination";

    //attachments
    public static final String ATTACHMENT_NAME = "attachment_name";
    public static final String ATTACHMENT_TYPE = "attachment_type";
    public static final String ATTACHMENT_EXTN = "attachment_extension";
    public static final String ATTACHMENT_S3_NAME = "attachment_s3_name";
    public static final String ATTACHMENT_DETAILS = "attachment_details";
    public static final String ATTACH_ORIGINAL_URL = "attachment_original_url";
    public static final String ATTACH_SMALL_URL = "attachment_small_url";
    public static final String ATTACH_SIZE = "attachment_size";
    public static final String CONTENT = "content";
    public static final String TAG_COMMUNITY = "Community";
    public static final String CONTENT_ID = "content_id";
    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel_name";
    public static final String CHANNEL_IDS = "channel_ids";
    public static final String CHANNEL_TYPE = "channel_type";
    public static final String LOGO_URL = "logo_url";
    public static final String LOGO_URL_SMALL = "logo_url_small";
    public static final String DESCRIPTION = "description";
    public static final String COMMUNITY_ID = "community_id";
    public static final String CHANNEL_TYPE_ARTICLE = "article";
    public static final String CHANNEL_TYPE_SURVEY = "survey";
    public static final String TAG_ARTICLE_TYPE = "article_type";
    public static final String DEPARTMENT_ID = "department_id";
    public static final String DEPARTMENT_IDS = "department_ids";
    public static final String SPECIALITY_ID = "speciality_id";
    public static final String SPECIALITY_IDS = "speciality_ids";
    public static final String SPECIALITY_NAME = "speciality_name";
    public static final String EDIT_POST_SPECIALITY_NAME = "edit_speciality_name";
    public static final String SPECIALIST = "specialist";
    public static final String PREFERENCES_LIST = "preferences_list";
    public static final String IS_PREFERENCE_SET = "is_preference_set";
    public static final String DOC_SPECIALITY_LIST = "doc_speciality_list";
    public static final String CHANNEL_PREF_LIST = "channel_preferences_list";
    public static final String DEPT_ID = "dept_id";
    public static final String PG_NUM = "pg_num";
    public static final String DRUG_CLASS_ID = "drugClassId";
    public static final String DRUG_SUB_CLASS_ID = "drugSubClassId";
    public static final String DRUG_ID = "drugId";
    public static final String BRAND_ID = "brandId";

    public static final String DEPARTMENT_NAME = "department_name";
    public static final String LAST_MEMBER_ID = "last_member_id";
    public static final String FEED_DESC = "feed_desc";
    public static final String FEED_DATA = "feed_data";
    public static final String FEED_PROVIDER_NAME = "feed_provider_name";
    public static final String POST_TO = "post_to";
    public static final String LAST_FEED_ID = "last_feed_id";
    public static final String FEED_TYPE_ID = "feedTypeId";
    public static final String FEED_TYPE = "feed_type";
    public static final String FEED_ID = "feedId";
    public static final String FEEDID = "feed_id";
    public static final String TAG_FEED_OBJECT = "feed_obj";
    public static final String KEY_FEED_SURVEY = "feed_survey";
    public static final String KEY_IS_OPEN = "is_open";
    public static final String KEY_IS_ELIGIBLE = "is_eligible";
    public static final String KEY_INELIGIBLE_MSG = "ineligible_msg";
    public static final String KEY_IS_PARTICIPATED = "is_participated";
    public static final String KEY_IMMEDIATE_RESULTS = "immediate_results";
    public static final String KEY_HIGH_PERCENTAGE = "high_pct";
    public static final String KEY_QUESTIONS = "questions";
    public static final String KEY_CLOSE_TIME = "close_time";
    public static final String KEY_NO_OF_PARTICIPANTS = "no_of_participants";
    public static final String KEY_QUESTION_ID = "question_id";
    public static final String KEY_IS_MANDATORY = "is_mandatory";
    public static final String KEY_IS_MULTI_SELECT = "is_multi_select";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_OPTIONS = "options";
    public static final String KEY_OPTION_ID = "option_id";
    public static final String KEY_OPTION = "option";
    public static final String KEY_IS_SELECTED = "is_selected";
    public static final String KEY_PARTICIPATED_PCT = "participated_pct";
    public static final String KEY_IS_ATTEMPTED = "is_attempted";

    public static final String TAG_CHANNELID = "channelId";
    public static final String TAG_SHORT_DESCRIPTION = "shortDescription";
    public static final String TAG_SMALLIMAGE_URL = "smallImageUrl";
    public static final String TAG_ATTACHMENT_TYPE = "attachmentType";


    /*
     * Constant KEYS
     */
    public static final String KEY_FEED_LIST_BUNDLE = "feeds_list_bundle";
    public static final String KEY_IS_SCROLL_TO_POS = "isScrollToPosition";
    public static final String KEY_REQUEST_BUNDLE = "requestBundle";
    public static final String KEY_SELECTED_CHANNEL = "selectedChannel";
    public static final String KEY_ITEMS_LIST = "itemsList";
    public static final String KEY_DEPARTMENTS = "departments";
    public static final String KEY_SPECIALITIES = "specialities";
    public static final String KEY_RELATED_SPECIALITIES = "related_specialties";
    public static final String KEY_SPECIALISTS = "specialists";
    public static final String KEY_ISSELECTED = "isSelected";
    public static final String KEY_IS_ALL_SELECTED = "isAllSelected";
    public static final String KEY_SELECTED_ITEM_COUNT = "selectedItemCount";
    public static final String KEY_SPECIFIC_ASK = "specific_ask";


    //general_examination changed
    public static final String PALLOR = "Pallor";
    public static final String ICTERUS = "Icterus";
    public static final String LYMPHADENOPATHY = "Lymphadenopathy";
    public static final String CYANOSIS = "Cyanosis";
    public static final String CLUBBING = "Clubbing";
    public static final String EDEMA = "Edema";
    public static final String ADDITIONAL_DETAILS = "Others";

    //systemtic_examination changed
    public static final String HEENT = "HEENT";
    public static final String RESPIRATORY = "Respiratory";
    public static final String CVS = "CVS";
    public static final String GIT = "Abdominal & Genital";
    public static final String CNS = "CNS";
    public static final String MUSCULO_SKELETAL = "Musculo-Skeletal";
    public static final String Others = "Others";

    //vitals_antropometry changed
    public static final String HR_PRB = "hr";
    public static final String BP = "bp";
    public static final String BP1 = "bpd";
    public static final String RESP_RATE = "resprate";
    public static final String TEMPERATURE = "temp";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String SP02 = "sp";
    public static final String BMI = "bmi";


    //patient_history changed
    public static final String PAST_HISTORY = "Past History";
    public static final String BIRTH_HISTORY = "Birth History";
    public static final String DEVELOPMENT_HISTORY = "Developmental History";
    public static final String DIETETIC_HISTORY = "Dietetic History";
    public static final String IMMUNIZATION_HISTORY = "Immunization History";
    public static final String FAMILY_HISTORY = "Family History";
    public static final String PERSONAL_HISTORY = "Personal and social History";
    public static final String OBSTETRIC_HISTORY = "Obstetric and Menstrual History";


    //symptoms changed
    public static final String SYMPTOM = "symptom";
    public static final String DURATION = "duration";
    public static final String DETAILS = "desc";

    /**
     * CaseRoom Invite Response Tags
     **/
    public static final String TAG_CASE_ROOM_ID = "caseroom_id";
    public static final String TAG_CASE_ROOM_CREATED_DATE = "caseroom_group_created_date";
    public static final String TAG_CASE_ROOM_DIALOG_ID = "caseroom_dialog_id";
    public static final String TAG_CASE_ROOM_TITLE = "caseroom_title";
    public static final String TAG_CASE_SUMMARY_ID = "caseroom_summary_id";
    public static final String TAG_CASE_ROOM_SPLTY = "caseroom_specialities";
    public static final String TAG_CASE_ROOM = "caseroom";
    public static final String TAG_GROUP_ID_LIST = "group_id_list";
    public static final String TAG_CASE_ROOM_INFO = "caseroom_info";

    /**
     * Dialog categories
     */
    public static final String TAG_PRIVATE_CHAT_DIALOG = "private";
    public static final String TAG_GROUP_CHAT_DIALOG = "groupchat";
    public static final String TAG_CASEROOM_CHAT_DIALOG = "caseroom";
    /**
     * Channels Tab Constants
     */
    public static final String TAG_CHANNELS_SECTIONS = "channels_sections";
    public static final String TAG_CHANNELS = "channels";
    public static final String TAG_CHANNEL_LOGO = "channel_logo";
    public static final String TAG_FEED_PROVIDER_TYPE = "feed_provider_type";
    public static final String TAG_FEED_PROVIDER_SUBTYPE = "feed_provider_subtype";
    public static final String TAG_MEMBERS_COUNT = "members_count";
    public static final String TAG_UNREAD_FEEDS = "unread_feeds";
    public static final String TAG_LATEST_FEED_TIME = "latest_feed_time";
    public static final String TAG_FEED_INFO = "feed_info";
    public static final String TAG_IS_SUBSCRIBED = "is_subscribed";
    public static final String TAG_IS_MANDATORY = "is_mandatory";
    public static final String TAG_SUBSCRIBED = "subscribed";


    public static final String TAG_CONTENT_PROVIDER = "content_provider";
    public static final String TAG_IS_POST_DELETED = "isPostDeleted";
    public static final String TAG_CONTENT_OBJECT = "content_obj";
    public static final String TAG_POSITION = "position";
    public static final String TAG_POST_CREATOR = "post_creator";
    public static final String TAG_POSTED_BY = "posted_by";
    public static final String TAG_POSTING_TIME = "posting_time";
    public static final String TAG_IS_EDITED = "is_edited";
    public static final String TAG_UPDATED_TIME = "updated_time";
    public static final String TAG_POSTING_DATE = "posting_date";
    public static final String TAG_TOPICS = "topics";
    public static final String TAG_ARTICLE_BODY = "article_body";
    public static final String TAG_SOCIALINTERACTION = "socialInteraction";
    public static final String TAG_SOCIAL_INFO = "socialInfo";
    public static final String TAG_SOCIAL_INTERACTION_ID = "social_interaction_id";
    public static final String TAG_IS_LIKE = "isLike";
    public static final String TAG_POST_ID = "post_id";
    public static final String TAG_POST_NAME = "post_name";
    public static final String TAG_ENABLE_KEYBOARD = "keyboard_enable";
    public static final String TAG_FEED_TYPE_ID = "feed_type_Id";
    public static final String TAG_FEED_TYPE = "feed_type";
    public static final String TAG_TOTAL_COMMENTS = "total_comments";
    public static final String TAG_SHORT_DESC = "short_desc";
    public static final String TAG_LONG_DESC = "long_desc";
    public static final String TAG_ARTICLE_ID = "article_id";
    public static final String TAG_MEDIUM_IMAGE = "medium_image";
    public static final String TAG_SMALL_IMAGE = "small_image";
    public static final String TAG_MICRO_IMAGE = "micro_image";
    public static final String TAG_COPYRIGHT = "copyright";
    public static final String TAG_WC_COPYRIGHT = "wc_copyright";
    public static final String TAG_POST_DATA = "post_data";
    public static final String TAG_FEED_ID = "feed_id";
    public static final String TAG_FROM_CREATE_POST = "is_from_create_post";
    public static final String TAG_FROM_EDIT_POST = "is_from_edit_post";
    public static final String TAG_TEMPLATE = "template";
    public static final String TAG_PROFILE_URL = "profile_url";
    public static final String TAG_CONTENT_URL = "content_url";
    public static final String TAG_LARGE_IMAGE = "large_image";
    public static final String TAG_NETWORK = "network";
    public static final String TAG_SERVICE_NAME = "service_name";
    public static final String TAG_TIMESTAMP = "timestamp";
    public static final String TAG_SHARE_INFO = "share_info";
    public static final String TAG_SERVER_TEXT = "text";
    public static final String TAG_SERVER_URL = "url";


    public static final String TAG_COMMENT_INFO = "comment_info";
    public static final String TAG_LIKE_INFO = "like_info";
    public static final String TAG_USER_INFO = "user_info";
    public static final String TAG_COMMENTED_TEXT = "comment_text";

    public static final String TAG_IS_DELETABLE = "is_deletable";
    public static final String TAG_IS_EDITABLE = "is_editable";

    public static final String TAG_IS_LIKE_ENABLED = "isLikeEnabled";
    public static final String TAG_IS_COMMENT_ENABLED = "isCommentEnabled";
    public static final String TAG_IS_SHARE_ENABLED = "isShareEnabled";
    public static final String TAG_IS_BOOKMARK_ENABLED = "isBookmarkEnabled";

    public static final String TAG_FROM_LIKES_COUNT = "fromLikesCount";

    public static final String TAG_LOAD_USING_PICASSO = "loadUsingPicasso";
    public static final String TAG_FILE_PATH = "filepath";
    public static final String TAG_IS_OPTIONS_ENABLE = "isOptionsEnable";
    public static final String TAG_IMAGE_POSITION = "imagePosition";

    public static final String TAG_LAST_LIKE_ID = "lastLikeId";
    public static final String TAG_LAST_COMMENT_ID = "lastCommentId";
    public static final String TAG_COMMENTS = "comments";
    public static final String TAG_VIEW_COUNT = "viewCount";

    public static final String TAG_ARTICLE_DESC = "article_desc";
    public static final String TAG_LINKS = "links";
    public static final String TAG_CALLTOACTION = "call_to_action";
    public static final String TAG_LINK_NAME = "link_name";
    public static final String TAG_LINK_URL = "link_url";
    public static final String TAG_BUTTON_LABEL = "button_label";
    public static final String TAG_ELEMENTS = "elements";
    public static final String TAG_ACTION_TYPE = "action_type";
    public static final String TAG_ELEMENT_TYPE = "element_type";
    public static final String TAG_ELEMENT_LINK = "element_link";
    public static final String TAG_ELEMENT_VALUE = "element_value";
    public static final String TAG_KEY = "key";

    public static final String TAG_SMALL_FILE_DATA = "small_file_data";

    public static final String TAG_POST_BTN_ENABLE = "PostBtnEnable";

    public static final String TAG_SECTION_NAME = "section_name";
    public static final String TAG_MEDIA_TYPES = "mediaTypes";
    public static final String TAG_SECTION_COUNT = "section_count";
    public static final String TAG_COUNT = "count";
    public static final String TAG_VIDEOS = "Videos";
    public static final String TAG_IMAGES = "Images";
    public static final String TAG_DOCUMENTS = "Documents";

    public static final String TAG_FEED_DATA_OBJ = "feed_data_obj";

    public static final String TAG_NETWORK_ERROR_RETRY = "network_error_retry";
    public static final String TAG_IS_RETRY = "isRetry";
    public static final String TAG_IS_BOOKMARK = "isBookmark";
    public static final String TAG_IS_BOOKMARKED = "isBookmarked";

    public static final String TAG_USER_ID = "user_id";
    public static final String TAG_REGISTRATION_ID = "registration_id";
    public static final String TAG_DEVICE_OS = "device_os";
    public static final String TAG_DEVICE_ID = "device_id";
    public static final String TAG_ENVIRONMENT = "environment";
    public static final String TAG_MAC_ID = "mac_id";
    public static final String TAG_PROVIDER = "provider";
    public static final String TAG_IS_ENABLE = "is_enabled";
    public static final String TAG_IS_USER_LOGGEDIN = "is_user_loggedin";
    public static final String TAG_SHARED_FEEDS = "shared_feeds";
    public static final String TAG_ENCRYPTED_KEY = "encrypted_key";
    public static final String TAG_SHARED_FEED_DATA = "SHARED_FEED_DATA";
    public static final String TAG_SID = "sid";

    public static final String TAG_RECOMMENDATIONS = "recommendations";

    public static final String TAG_FEED_SUB_TYPE = "feed_sub_type";
    public static final String TAG_SMALL_IMAGE_URL = "small_image_url";

    public static final String TAG_SEARCH_IN = "searchIn";

    public static final String TAG_SEARCH_TYPE = "searchType";

    public static final String TAG_FILTER = "filter";

    public static final String TAG_SEARCH_TEXT = "searchText";

    public static final String TAG_SEARCH_CRITERIA = "searchCriteria";

    public static final String TAG_USER_SALUTAION = "user_salutation";
    public static final String TAG_USER_TYPE_ID = "user_type_id";
    public static final String TAG_USER_FULL_NAME = "user_full_name";
    public static final String TAG_USER_FIRST_NAME = "user_first_name";
    public static final String TAG_USER_LAST_NAME = "user_last_name";

    public static final String TAG_OTHER_USER_ID = "otherUserId";

    public static final String TAG_CONTACTS_RESULTS = "contacts_results";

    public static final String TAG_OTHER_DOC_ID = "other_doc_id";

    public static final String TAG_TIME_KEY = "time";

    /*
    for global search
     */
    public static final String TAG_FILTERS = "filters";


    public static final String TAG_CATEGORY_ID = "category_id";
    public static final String TAG_FILTERS_LIST = "filters_list";

    public static final String TAG_USER_CATEGORIES = "user_categories";
    public static final String TAG_DISPLAY_NAME = "display_name";

    public static final String TAG_USER_REJECTED = "USER_REJECTED";

    public static final String TAG_SHORT_URL = "short_url";
    public static final String TAG_REST_API_URL = "rest_api_url";
    public static final String TAG_NAVIGATION_TYPE = "navigation_type";
    public static final String TAG_ORIGINAL_URL = "originalURL";
    public static final String TAG_SHARE_URL = "share_url";
    public static final String TAG_SHARE_TEXT = "shareText";

    public static final String TAG_QB_USER_DETAILS = "user_qb_details";

    public static final String TAG_AREAS_OF_INTEREST="areas_of_interest";
    public static final String TAG_AREA_OF_INTEREST="area_of_interest";
    public static final String TAG_INTEREST_ID="interest_id";

    public static final String TAG_FOLLOWERS_COUNT="followers_count";
    public static final String TAG_FOLLOWING_COUNT="following_count";

    public static final String TAG_FOLLOW_STATUS="follow_status";
    public static final String TAG_IS_FOLLOW="is_follow";
    public static final String TAG_SHARED_USER_ID="shared_user_id";

    /*
     */
    public static final String TAG_EVENT_ID = "event_id";
    public static final String TAG_EVENT_NAME = "event_name";
    public static final String TAG_EVENT_LOCATION = "event_location";
    public static final String TAG_EVENT_START_DATE = "event_start_date";
    public static final String TAG_EVENT_END_DATE = "event_end_date";
    public static final String TAG_EVENTS = "events";

    public static final String TAG_EVENT_TYPE = "event_type";
    public static final String TAG_EVENT_DATA = "event_data";

    public static final String TAG_USER_EVENTS = "user_events";

    public static final String EVENT_DOCID = "DocID";
    public static final String EVENT_DOC_SPECIALITY = "DocSpeciality";
    public static final String EVENT_COMMUNITY_ID = "CommunityID";
    public static final String EVENT_TRENDING_CATEGORY_NAME = "TrendingCategoryName";
    public static final String EVENT_APP_VERSION = "AppVersion";

    public static final String EVENT_FEED_ID = "FeedID";
    public static final String EVENT_FEED_TYPE = "FeedType";
    public static final String EVENT_CHANNEL_ID = "ChannelID";
    public static final String EVENT_PREFERENCE = "Preference";

    public static final String TAG_IS_NETWORK_CHANNEL = "isNetworkChannel";
    public static final String TAG_PROFILE_PDF_URL = "pdf_url";

    public static final String TAG_LIMIT="limit";
    public static final String TAG_FILE_TYPE="fileType";

    public static final String EVENT_CHANNEL_NAME ="ChannelName";

    public static final String TAG_USER_UUID ="user_uuid";

    public static final String KEY_DEPARTMENTS_IN_EDIT = "departmentsInEdit";

    //aws keys
    public static final String TAG_ACCESS_KEY ="access_key";
    public static final String TAG_SECRET_KEY ="secret_key";
    public static final String TAG_BUCKET ="bucket";

    //NOtification_settings
    public static final String TAG_NOTIFICAYTION_CATEGORIES ="notification_categories";
    public static final String TAG_CATEGORY_NAME = "category_name";
    public static final String TAG_CATEGORY_DESC = "category_desc";

    //to"from_doc_id
    public static final String TAG_FROM_DOC_ID = "from_doc_id";
    public static final String TAG_TO_DOC_ID = "to_doc_id";

    public static final String TAG_FEED_NOTIFICATION_COUNT = "feedNotificationsCount";

    public static final String TAG_EXTERNAL_URL ="external_url";

    public static final String TAG_EVENT_FEED_TYPE ="EVENT";

    public static final String TAG_WEBINAR ="Webinar";
    public static final String TAG_EVENT_DETAILS ="event_details";

    public static final String TAG_EOI ="EOI";

    public static final String TAG_NOTI_INFO ="noti_info";
    public static final String TAG_JOB_POSTING_TYPE="Job Postings";

    public static final String TAG_IS_REMOVE="is_remove";
}
