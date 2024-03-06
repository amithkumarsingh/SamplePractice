package com.vam.whitecoats.tools;





import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPref {


    public static final String PREF_REMEMBER_ME = "remember_me";
    public static final String PREF_MIGRATION_FIRST_RUN = "migration_first_run";
    public static final String PREF_USER_NAME = "user_name";
    public static final String STAY_LOGGED_IN = "stay_logged_in";
    public static final String PREF_USER_PASSWORD = "password";
    public static final String PREF_USER_EMAIL = "useremail";
    public static final String PREF_MANDATORY_PROFILE_CHECK = "mandatory_profile_check";
    public static final String PREF_SESSION_TOKEN = "session_token";
    public static final String PREF_CHANGEPASSWORD_FLAG = "changePassword";
    public static final String PREF_INTROSLIDES = "introslides";
    public static final String PREF_REGISTRATION_FLAG = "onRegistrationComplete";
    public static final String EMAIL_ID_OTP = "EMAIL_ID_OTP";
    public static final String PREF_COOKIE = "stored_cookie";
    public static final String PREF_COACHMARK_INCREMENTER = "coachmark_incrementer";
    public static final String PREF_IS_USER_VERIFIED = "is_user_verified";
    public static final String PREF_IS_COMMUNITY_VERIFIED = "isCommunityVerified";
    public static final String REQUEST_PG_INDEX = "page_index";

    public static final String PREF_SYNC_CONTACTS_UPDATED = "sync_contacts_updated";

    public static final String PREF_URBANAIRSHIP_CHANNEL_ID = "airship_channel_id";
    public static final String PREF_USER_COUNTRY_NAME_CODE = "country_name_code";
    public static final String PREF_SAVED_PREF_CHANNEL = "saved_pref_channel";

    public static final String PREF_LAST_LOGIN_TIME = "last_login_time";
    public static final String PREF_ON_BOARD_DATE = "on_board_date";

    private final SharedPreferences sharedPreferences;
    private final Editor editor;

    private static MySharedPref instance;

    public static MySharedPref getPrefsHelper() {
        return instance;
    }

    public MySharedPref(Context context) {
        instance = this;
        String prefsFile = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void delete(String key) {
        if (sharedPreferences!=null && sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }

    public void savePref(String key, Object value) {
        if(editor!=null){ // Check for null in case editor not initialized
            delete(key);
            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Enum) {
                editor.putString(key, value.toString());
            } else if (value != null) {
                throw new RuntimeException("Attempting to save non-primitive preference");
            }

            editor.commit();
        }

    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        T returnValue = null;
        if(sharedPreferences!=null)
            returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean isPrefExists(String key) {
        return sharedPreferences.contains(key);
    }


}
