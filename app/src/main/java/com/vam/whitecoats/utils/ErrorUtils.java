package com.vam.whitecoats.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.vam.whitecoats.constants.ConstsCore;

public class ErrorUtils {
    //Server upgrade
    public static final String ERR_SERVER_UPGRADE_MSG="Server Upgradation";
    public static final String ERR_SERVER_UPGRADE_DESC="We are working on system upgradation. Please try again shortly";
    //Server Error
    public static final String ERR_SERVER_MSG_="Server Error";
    public static final String ERR_SERVER_DESC="Can't load the content, server is not responding. Please try again shortly";
    //Connection Error
    public static final String ERR_CONNECTION_MSG_="Connection Error";
    public static final String ERR_CONNECTION_DESC="The server is taking longer than expected. Please try again shortly";
    //Incorrect app version Error
    public static final String ERR_INCORRECT_VERSION_MSG_="Time to Update";
    public static final String ERR_INCORRECT_VERSION_DESC="You seem to be using an older version of WhiteCoats. For more features and a better user experience, please upgrade the app.";

    public static void showError(Context context, Exception e) {
        String errorMsg = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : ConstsCore.EMPTY_STRING;
        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

    public static void showError(Context context, String error) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }

    public static Toast getErrorToast(Context context, String error) {
        return Toast.makeText(context, error, Toast.LENGTH_LONG);
    }

    public static void logError(String tag, Exception e) {
        String errorMsg = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : ConstsCore.EMPTY_STRING;
        Log.e(tag, errorMsg, e);
    }

    public static void logError(String tag, String msg) {
        String errorMsg = !TextUtils.isEmpty(msg) ? msg : ConstsCore.EMPTY_STRING;
        Log.e(tag, errorMsg);
    }

    public static void logError(Exception e) {
        e.printStackTrace();
    }
}