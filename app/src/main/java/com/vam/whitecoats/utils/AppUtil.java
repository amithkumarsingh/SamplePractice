package com.vam.whitecoats.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpHeaderParser;
import com.brandkinesis.BKProperties;
import com.brandkinesis.BKUserInfo;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.callback.BKUserInfoCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flurry.android.FlurryAgent;
import com.vam.whitecoats.BuildConfig;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.constants.WhitecoatsFlavor;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.core.models.CaseroomNotifyInfo;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.FeedAttachment;
import com.vam.whitecoats.core.models.FeedCreator;
import com.vam.whitecoats.core.models.FeedInfo;
import com.vam.whitecoats.core.models.FeedSurvey;
import com.vam.whitecoats.core.models.GroupNotifyInfo;
import com.vam.whitecoats.core.models.RealmNotificationInfo;
import com.vam.whitecoats.core.models.SurveyOption;
import com.vam.whitecoats.core.models.SurveyQuestion;
import com.vam.whitecoats.core.realm.RealmAdSlotInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmCaseRoomNotifications;
import com.vam.whitecoats.core.realm.RealmGroupNotifications;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmNotifications;
import com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo;
import com.vam.whitecoats.models.AdsDefinition;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.CreatePostActivity;
import com.vam.whitecoats.ui.activities.DrugsActivity;
import com.vam.whitecoats.ui.activities.EmptyActivity;
import com.vam.whitecoats.ui.activities.LoginActivity;
import com.vam.whitecoats.ui.activities.MCACardUploadActivity;
import com.vam.whitecoats.ui.activities.SplashScreenActivity;
import com.vam.whitecoats.ui.activities.WebViewActivity;
import com.vam.whitecoats.ui.activities.YoutubeVideoViewActivity;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnlocationApiFinishedListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.protocol.Message;


public class AppUtil {
    public static final String TAG = AppUtil.class.getSimpleName();
    private static int REQUEST_PHONE_CALL = 1010;
    //public static int drugClassId;
    //public static int drugSubClassId;
    String default_docId;

    /*public static void checkNetworkStatus(Context context) {

        if (context != null) {
            final ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
                GlobalVO.IS_ONLINE = false;
                Log.i(TAG, "checkConnection - no connection found");
            } else {
                GlobalVO.IS_ONLINE = true;
                Log.i(TAG, "network connection found");
            }
        }

    }*/

    public static SpannableStringBuilder alert_PhonePermissionDeny_Message() {
        SpannableStringBuilder longDescription = new SpannableStringBuilder();
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        longDescription.append("Without this permission, we are unable to continue with the registration. " +
                "Please go to the Permissions section inside the application settings and toggle the  ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            longDescription.append("Phone", boldSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            longDescription.append(" permissions switch.");
        } else {
            longDescription.append("Phone permissions switch.");
        }
        return longDescription;
    }


    public static SpannableStringBuilder alert_CameraPermissionDeny_Message() {
        SpannableStringBuilder longDescription = new SpannableStringBuilder();
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        longDescription.append("Without this permission, we are unable to take a photo. Please go to the Permissions " +
                "section inside the application settings and toggle the  ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            longDescription.append("Camera and Storage", boldSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            longDescription.append(" permissions.");
        } else {
            longDescription.append("Camera and Storage permissions.");
        }
        return longDescription;
    }

    public static SpannableStringBuilder alert_StoragePermissionDeny_Message() {
        SpannableStringBuilder longDescription = new SpannableStringBuilder();
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        longDescription.append("Without this permission, we are unable to choose from photo library." +
                " Please go to the Permissions section inside the application settings and toggle the  ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            longDescription.append("Storage", boldSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            longDescription.append(" permissions switch.");
        } else {
            longDescription.append("Storage permissions switch.");
        }
        return longDescription;
    }

    public static SpannableStringBuilder alert_StoragePermissionDeny_Message_ForFiles() {
        SpannableStringBuilder longDescription = new SpannableStringBuilder();
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        longDescription.append("Without this permission, we are unable to choose files from storage." +
                " Please go to the Permissions section inside the application settings and toggle the  ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            longDescription.append("Storage", boldSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            longDescription.append(" permissions switch.");
        } else {
            longDescription.append("Storage permissions switch.");
        }
        return longDescription;
    }

    public static String suffixNumber(Number number) {
        char[] suffix = {' ', 'K', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, (double) base * 3)) + suffix[base] + "+";
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    public static void toggleSocialBarViewCount(JSONObject socialInteractionObj, TextView likes_count, TextView comments_count, TextView viewCount,
                                                RelativeLayout socialbar_likes_count, ViewGroup social_bar_dashboard, ViewGroup like_layout, ViewGroup comment_layout, ViewGroup share_layout, ViewGroup bookmark_lay, TextView shareCount) {
        if (socialInteractionObj != null) {

            if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE_ENABLED)) {
                like_layout.setVisibility(View.VISIBLE);
            } else {
                like_layout.setVisibility(View.GONE);
            }
            if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_COMMENT_ENABLED)) {
                comment_layout.setVisibility(View.VISIBLE);
            } else {
                comment_layout.setVisibility(View.GONE);
            }
            if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_SHARE_ENABLED)) {
                share_layout.setVisibility(View.VISIBLE);
            } else {
                share_layout.setVisibility(View.GONE);
            }
            if (bookmark_lay != null) {
                if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_BOOKMARK_ENABLED)) {
                    bookmark_lay.setVisibility(View.VISIBLE);
                } else {
                    bookmark_lay.setVisibility(View.GONE);
                }
            }

            if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE_ENABLED) || socialInteractionObj.optBoolean(RestUtils.TAG_IS_COMMENT_ENABLED) || socialInteractionObj.optBoolean(RestUtils.TAG_IS_SHARE_ENABLED)) {
                social_bar_dashboard.setVisibility(View.VISIBLE);
            } else {
                social_bar_dashboard.setVisibility(View.GONE);
            }

            if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT, 0) > 0 || socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT, 0) > 0 || socialInteractionObj.optInt(RestUtils.TAG_VIEW_COUNT, 0) > 0 || socialInteractionObj.optInt(RestUtils.TAG_SHARE_COUNT, 0) > 0) {
                socialbar_likes_count.setVisibility(View.VISIBLE);
            } else {
                socialbar_likes_count.setVisibility(View.GONE);
            }

            if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT, 0) > 0) {
                likes_count.setVisibility(View.VISIBLE);
                if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT, 0) == 1) {
                    likes_count.setText(suffixNumber(socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT, 0)) + " like");
                } else {
                    likes_count.setText(suffixNumber(socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT, 0)) + " likes");
                }
            } else {
                likes_count.setVisibility(View.GONE);
            }

            if (socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT, 0) > 0) {
                comments_count.setVisibility(View.VISIBLE);
                if (socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT, 0) == 1) {
                    comments_count.setText(suffixNumber(socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT, 0)) + " comment");
                } else {
                    comments_count.setText(suffixNumber(socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT, 0)) + " comments");
                }
            } else {
                comments_count.setVisibility(View.GONE);
            }

            if (socialInteractionObj.optInt(RestUtils.TAG_VIEW_COUNT, 0) > 0) {
                viewCount.setVisibility(View.VISIBLE);
                if (socialInteractionObj.optInt(RestUtils.TAG_VIEW_COUNT, 0) == 1) {
                    viewCount.setText(suffixNumber(socialInteractionObj.optInt(RestUtils.TAG_VIEW_COUNT, 0)) + " view");
                } else {
                    viewCount.setText(suffixNumber(socialInteractionObj.optInt(RestUtils.TAG_VIEW_COUNT, 0)) + " views");
                }
            } else {
                viewCount.setVisibility(View.GONE);
            }

            if (socialInteractionObj.optInt(RestUtils.TAG_SHARE_COUNT, 0) > 0) {
                shareCount.setVisibility(View.VISIBLE);
                if (socialInteractionObj.optInt(RestUtils.TAG_SHARE_COUNT, 0) == 1) {
                    shareCount.setText(suffixNumber(socialInteractionObj.optInt(RestUtils.TAG_SHARE_COUNT, 0)) + " share");
                } else {
                    shareCount.setText(suffixNumber(socialInteractionObj.optInt(RestUtils.TAG_SHARE_COUNT, 0)) + " shares");
                }
            } else {
                shareCount.setVisibility(View.GONE);
            }
        }
    }


    // close all activities end here
    // global method to check the Internet connection
    public static boolean isConnectingToInternet(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to mobile data
                return true;
            }
        }
        // not connected to the internet
        return false;

    }

    public static boolean validateEmail(String strEmail) {

        Pattern pattern = Pattern.compile(AppConstants.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(strEmail);
        return matcher.matches();

    }


    public static String unescapeJavaString(String st) {

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        .charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt(
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                        + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }


    public static void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                ErrorUtils.logError(e);
            }
        }
    }


    public static int getDeviceWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int getDeviceHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        return height;
    }

    public static String getDeviceID(Context context) {
        String deviceId;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            //final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            /*if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }*/
        }
        return deviceId;
    }

    public static Bitmap sampleResize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }


    /*1 – NOT_VERIFIED
      2 – IN-PROGRESS (MCI Card uploaded but not verified)
      3 - VERIFIED
    */
    public static void AccessErrorPrompt(final Context mContext, String errorMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(errorMsg);
        builder.setCancelable(false);

        /*condition for not yet uploaded*/
        if (AppUtil.getUserVerifiedStatus() == 2) {
            /*condition for uploaded for not verified*/
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        } else {
            builder.setPositiveButton("Verify Now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent verifyLikeIntent = new Intent(mContext, MCACardUploadActivity.class);
                    mContext.startActivity(verifyLikeIntent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create().show();
        }


    }


    public static Bitmap bitmapCompression(String mPath) {
        Bitmap selected_img = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        try {
            ExifInterface exif = new ExifInterface(mPath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap original = BitmapFactory.decodeFile(mPath, bmOptions);
            Bitmap bitmap = CreatePostActivity.rotateBitmap(original, orientation);
            if (bitmap == null) {
                return selected_img;
            }
            selected_img = sampleResize(bitmap, 1536, 1152);
            FileOutputStream out = new FileOutputStream(mPath);
            selected_img.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return selected_img;
    }


    public static String maskEmailId(String mEmailId) {
        String[] full_email_id;
        String emailid_name = "";
        String email_domain = "";
        if (mEmailId.contains("@")) {
            full_email_id = mEmailId.split("@");
            email_domain = full_email_id[1];
            emailid_name = full_email_id[0];
        }
        return emailid_name.replaceAll("\\B\\w\\B", "*") + "@" + email_domain;
    }


    public static void showSessionExpireAlert(final String title, final String message, final Context mContext) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_SESSION_TOKEN, "");
                    MySharedPref.getPrefsHelper().savePref(MySharedPref.STAY_LOGGED_IN, false);
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                    ((Activity) mContext).finish();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadCircularImageUsingLib(Context mContext, String attachment_url, ImageView imageView, int placeHolder) {
        Glide.with(mContext)
                .load(attachment_url)
                .circleCrop()
                .apply(getRequestOptions(mContext, ContextCompat.getDrawable(mContext, placeHolder)))
                .into(imageView);
    }


    public static void loadImageUsingGlide(Context mContext, String attachment_url, ImageView imageView, int placeHolder) {
        Glide.with(mContext)
                .load(attachment_url)
                .apply(getRequestOptions(mContext, ContextCompat.getDrawable(mContext, placeHolder)))
                .into(imageView);
    }

    public static RequestOptions getRequestOptions(Context context, Drawable placeHolder) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(placeHolder)
                .priority(Priority.IMMEDIATE);

        return options;
    }

    public static FeedSurvey getSurveyData(JSONObject feedInfoObj) {
        FeedSurvey feedSurvey = null;
        try {
            List<SurveyQuestion> questionList = new ArrayList<SurveyQuestion>();
            JSONObject feedSurveyObj = feedInfoObj.optJSONObject(RestUtils.KEY_FEED_SURVEY);
            JSONArray questionsArray = feedSurveyObj.optJSONArray(RestUtils.KEY_QUESTIONS);
            int questionsLen = questionsArray.length();
            // Set Survey Questions
            for (int index = 0; index < questionsLen; index++) {
                List<SurveyOption> surveyOptionList = new ArrayList<SurveyOption>();
                List<FeedAttachment> attachmentList = new ArrayList<FeedAttachment>();
                JSONObject questionObj = questionsArray.optJSONObject(index);
                SurveyQuestion surveyQuestion = new SurveyQuestion();
                surveyQuestion.setQuestionId(questionObj.optInt(RestUtils.KEY_QUESTION_ID));
                surveyQuestion.setMandatory(questionObj.optBoolean(RestUtils.KEY_IS_MANDATORY));
                surveyQuestion.setMultiSelect(questionObj.optBoolean(RestUtils.KEY_IS_MULTI_SELECT));
                surveyQuestion.setQuestion(questionObj.optString(RestUtils.KEY_QUESTION).trim());
                surveyQuestion.setDescription(questionObj.optString(RestUtils.DESCRIPTION).trim());
                surveyQuestion.setQuestionJsonObj(questionObj);
                surveyQuestion.setHighPercentage(questionObj.optInt(RestUtils.KEY_HIGH_PERCENTAGE));
                surveyQuestion.setAttempted(questionObj.optBoolean(RestUtils.KEY_IS_ATTEMPTED));
                surveyQuestion.setParticipantCount(questionObj.optInt(RestUtils.KEY_NO_OF_PARTICIPANTS));
                //Set Survey options
                JSONArray optionsArray = questionObj.optJSONArray(RestUtils.KEY_OPTIONS);
                int optionLen = optionsArray.length();
                for (int j = 0; j < optionLen; j++) {
                    JSONObject optionObj = optionsArray.optJSONObject(j);
                    SurveyOption surveyOption = new SurveyOption();
                    surveyOption.setOptionId(optionObj.optInt(RestUtils.KEY_OPTION_ID));
                    surveyOption.setParticipatedPercent(optionObj.optInt(RestUtils.KEY_PARTICIPATED_PCT));
                    surveyOption.setOption(optionObj.optString(RestUtils.KEY_OPTION).trim());
                    surveyOption.setSelected(optionObj.optBoolean(RestUtils.KEY_IS_SELECTED));
                    surveyOptionList.add(surveyOption);
                }
                //Set attachment details
                JSONArray attachmentsArray = questionObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                int attachmentLen = attachmentsArray.length();
                for (int i = 0; i < attachmentLen; i++) {
                    JSONObject attachmentObj = attachmentsArray.optJSONObject(i);
                    FeedAttachment feedAttachment = new FeedAttachment();
                    feedAttachment.setName(attachmentObj.optString(RestUtils.ATTACHMENT_S3_NAME));
                    feedAttachment.setType(attachmentObj.optString(RestUtils.ATTACHMENT_TYPE));
                    feedAttachment.setOriginalUrl(attachmentObj.optString(RestUtils.ATTACH_ORIGINAL_URL));
                    feedAttachment.setSmallUrl(attachmentObj.optString(RestUtils.ATTACH_SMALL_URL));
                    feedAttachment.setSize(attachmentObj.optLong(RestUtils.ATTACH_SIZE));
                    attachmentList.add(feedAttachment);
                }
                surveyQuestion.setOptions(surveyOptionList);
                surveyQuestion.setFeedAttachments(attachmentList);
                questionList.add(surveyQuestion);
            }
            // Set Survey data
            feedSurvey = new FeedSurvey();
            feedSurvey.setIsOpen(feedSurveyObj.optBoolean(RestUtils.KEY_IS_OPEN));
            feedSurvey.setEligible(feedSurveyObj.optBoolean(RestUtils.KEY_IS_ELIGIBLE));
            feedSurvey.setIneligibleMsg(feedSurveyObj.optString(RestUtils.KEY_INELIGIBLE_MSG));
            feedSurvey.setParticipated(feedSurveyObj.optBoolean(RestUtils.KEY_IS_PARTICIPATED));
            feedSurvey.setImmediate(feedSurveyObj.optBoolean(RestUtils.KEY_IMMEDIATE_RESULTS));

            feedSurvey.setCloseTime(feedSurveyObj.optInt(RestUtils.KEY_CLOSE_TIME));
            feedSurvey.setTimeStamp(feedSurveyObj.optInt(RestUtils.TAG_TIMESTAMP));
            feedSurvey.setQuestions(questionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedSurvey;

    }

    public static FeedInfo setUpdatedFeedInfo(JSONObject feedObj, FeedInfo feedInfo) {

        if (feedObj.has(RestUtils.TAG_FEED_ID)) {
            feedInfo.setFeedID(feedObj.optInt(RestUtils.TAG_FEED_ID));
        } else {
            feedInfo.setFeedID(feedObj.optInt(RestUtils.TAG_POST_ID));
        }
        if (feedObj.has(RestUtils.CHANNEL_ID)) {
            feedInfo.setChannelID(feedObj.optInt(RestUtils.CHANNEL_ID));
        }
        if (feedObj.has(RestUtils.TAG_ARTICLE_ID)) {
            feedInfo.setArticleID(feedObj.optString(RestUtils.TAG_ARTICLE_ID));
        }
        if (feedObj.has(RestUtils.TAG_TEMPLATE)) {
            feedInfo.setTemplate(feedObj.optInt(RestUtils.TAG_TEMPLATE));
        }
        if (feedObj.has(RestUtils.TITLE)) {
            feedInfo.setTitle(feedObj.optString(RestUtils.TITLE));
        }
        if (feedObj.has(RestUtils.FEED_TYPE)) {
            feedInfo.setFeedType(feedObj.optString(RestUtils.FEED_TYPE));
        }
        if (feedObj.has(RestUtils.TAG_ARTICLE_TYPE)) {
            feedInfo.setArticleType(feedObj.optString(RestUtils.TAG_ARTICLE_TYPE));
        }
        if (feedObj.has(RestUtils.TAG_IS_EDITED)) {
            feedInfo.setEdited(feedObj.optBoolean(RestUtils.TAG_IS_EDITED));
        }
        if (feedObj.has(RestUtils.FEED_DESC)) {
            feedInfo.setFeedDesc(feedObj.optString(RestUtils.FEED_DESC));
        }
        if (feedObj.has(RestUtils.TAG_LONG_DESC)) {
            feedInfo.setLongDesc(feedObj.optString(RestUtils.TAG_LONG_DESC));
        }
        if (feedObj.has(RestUtils.TAG_SHORT_DESC)) {
            feedInfo.setShortDesc(feedObj.optString(RestUtils.TAG_SHORT_DESC));
        }
        if (feedObj.has(RestUtils.TAG_DISPLAY)) {
            feedInfo.setDisplayTag(feedObj.optString(RestUtils.TAG_DISPLAY));
        }
        if (feedObj.has(RestUtils.TAG_POSTING_DATE)) {
            feedInfo.setPostingDate(feedObj.optString(RestUtils.TAG_POSTING_DATE));
        }
        if (feedObj.has(RestUtils.TAG_POSTING_TIME)) {
            feedInfo.setPostingTime(feedObj.optLong(RestUtils.TAG_POSTING_TIME));
        }
        if (feedObj.has(RestUtils.TAG_CONTENT_URL)) {
            feedInfo.setContentUrl(feedObj.optString(RestUtils.TAG_CONTENT_URL));
        }
        if (feedObj.has(RestUtils.TAG_LARGE_IMAGE)) {
            feedInfo.setLargeImage(feedObj.optString(RestUtils.TAG_LARGE_IMAGE));
        }
        if (feedObj.has(RestUtils.TAG_MEDIUM_IMAGE)) {
            feedInfo.setMediumImage(feedObj.optString(RestUtils.TAG_MEDIUM_IMAGE));
        }
        if (feedObj.has(RestUtils.TAG_SMALL_IMAGE)) {
            feedInfo.setSmallImage(feedObj.optString(RestUtils.TAG_SMALL_IMAGE));
        }
        if (feedObj.has(RestUtils.TAG_MICRO_IMAGE)) {
            feedInfo.setMicroImage(feedObj.optString(RestUtils.TAG_MICRO_IMAGE));
        }
        if (feedObj.has(RestUtils.ATTACHMENT_NAME)) {
            feedInfo.setAttachmentName(feedObj.optString(RestUtils.ATTACHMENT_NAME));
        }
        if (feedObj.has(RestUtils.TAG_COPYRIGHT)) {
            feedInfo.setCopyright(feedObj.optString(RestUtils.TAG_COPYRIGHT));
        }
        if (feedObj.has(RestUtils.TAG_WC_COPYRIGHT)) {
            feedInfo.setWcCopyright(feedObj.optString(RestUtils.TAG_WC_COPYRIGHT));
        }
        if (feedObj.has(RestUtils.TAG_UPDATED_TIME)) {
            feedInfo.setUpdatedTime(feedObj.optLong(RestUtils.TAG_UPDATED_TIME));
        }
        if (feedObj.has(RestUtils.TAG_IS_BOOKMARKED)) {
            feedInfo.setBookmarked(feedObj.optBoolean(RestUtils.TAG_IS_BOOKMARKED));
        }
        if (feedObj.has(RestUtils.TAG_IS_DELETABLE)) {
            feedInfo.setDeletable(feedObj.optBoolean(RestUtils.TAG_IS_DELETABLE));
        }
        if (feedObj.has(RestUtils.TAG_IS_EDITABLE)) {
            feedInfo.setEditable(feedObj.optBoolean(RestUtils.TAG_IS_EDITABLE));
        }
        if (feedObj.has(RestUtils.TAG_TOPICS)) {
            feedInfo.setTopics(feedObj.optJSONArray(RestUtils.TAG_TOPICS));
        }
        if (feedObj.has(RestUtils.TAG_ARTICLE_BODY)) {
            feedInfo.setArticleBody(feedObj.optJSONArray(RestUtils.TAG_ARTICLE_BODY));
        }
        if (feedObj.has(RestUtils.TAG_SPLTY)) {
            feedInfo.setSpeciality(feedObj.optJSONArray(RestUtils.TAG_SPLTY));
        }
        if (feedObj.has(RestUtils.TAG_SOCIALINTERACTION)) {
            feedInfo.setSocialInteraction(feedObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
        }
        feedInfo.setFeedInfoJson(feedObj);
        if (feedObj.has(RestUtils.TAG_SHARE_INFO)) {
            feedInfo.setShareInfo(feedObj.optJSONObject(RestUtils.TAG_SHARE_INFO));
        }
        if (feedObj.has(RestUtils.ATTACHMENT_DETAILS)) {
            feedInfo.setAttachmentJson(feedObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS));
        }
        if (feedObj.has(RestUtils.KEY_FEED_SURVEY)) {
            feedInfo.setSurveyData(getSurveyData(feedObj));
        }
        if (feedObj.has("event_details")) {
            feedInfo.setEventDetails(feedObj.optJSONObject("event_details"));
        }
        if (feedObj.has(RestUtils.TAG_FEED_SUB_TYPE)) {
            feedInfo.setFeedSubType(feedObj.optString(RestUtils.TAG_FEED_SUB_TYPE));
        }

        return feedInfo;

    }

    public static FeedCreator getFeedCreator(JSONObject feedCreatorObj) {
        FeedCreator feedCreator = new FeedCreator();
        if (feedCreatorObj.has(RestUtils.TAG_DOC_ID)) {
            feedCreator.setDocID(feedCreatorObj.optInt(RestUtils.TAG_DOC_ID));
        }
        if (feedCreatorObj.has(RestUtils.TAG_USER_TYPE_ID)) {
            feedCreator.setTypeID(feedCreatorObj.optInt(RestUtils.TAG_USER_TYPE_ID));
        }
        if (feedCreatorObj.has(RestUtils.TAG_USER_SALUTAION)) {
            feedCreator.setSalutation(feedCreatorObj.optString(RestUtils.TAG_USER_SALUTAION));
        }
        if (feedCreatorObj.has(RestUtils.TAG_POSTED_BY)) {
            feedCreator.setPostedBy(feedCreatorObj.optString(RestUtils.TAG_POSTED_BY));
        }
        if (feedCreatorObj.has(RestUtils.TAG_USER_FULL_NAME)) {
            feedCreator.setFullName(feedCreatorObj.optString(RestUtils.TAG_USER_FULL_NAME));
        }
        if (feedCreatorObj.has(RestUtils.TAG_PROFILE_URL)) {
            feedCreator.setProfileUrl(feedCreatorObj.optString(RestUtils.TAG_PROFILE_URL));
        }

        return feedCreator;
    }

    public static String getSurveyClosingTime(long surveyCloseTime, long timestamp) {
        long diff = surveyCloseTime - timestamp;
        int MINUTE = 60 * 1000;
        int HOUR = 60 * MINUTE;
        int DAY = 24 * HOUR;
        StringBuffer text = new StringBuffer("");
        if (diff > DAY) { // if time more than 24 hours
            if (diff > DAY) {
                text.append(diff / DAY).append(" days ");
                diff %= DAY;
            }
            if (diff > HOUR) {
                text.append(diff / HOUR).append(" hrs ");
                diff %= HOUR;
            }
        } else {

            if (diff > HOUR) {
                text.append(diff / HOUR).append(" hrs ");
                diff %= HOUR;
            }
            if (diff > MINUTE) {
                text.append(diff / MINUTE).append(" mins ");
                diff %= MINUTE;
            }

        }
        return text.toString();
    }

    public static void loadMultipleAttachments(Context mContext, JSONArray attachment_details_array, ImageView communityImgvw, ImageView communityImgvw1, ImageView communityImgvw2, TextView remainingCountText, ImageView attachment_icon, ImageView attachment_icon1, ImageView attachment_icon2, ImageView attachment_video_type, ImageView attachment_video_type1, ImageView attachment_video_type2) {
        remainingCountText.setVisibility(View.GONE);
        attachment_video_type.setVisibility(View.GONE);
        attachment_video_type1.setVisibility(View.GONE);
        attachment_video_type2.setVisibility(View.GONE);
        if (attachment_details_array.length() == 2) {
            communityImgvw.setVisibility(View.GONE);
            communityImgvw1.setVisibility(View.VISIBLE);
            communityImgvw2.setVisibility(View.VISIBLE);
            for (int i = 0; i < attachment_details_array.length(); i++) {
                if (i == 0) {
                    loadAttachmentsBasedOnType(mContext, attachment_details_array.optJSONObject(i), communityImgvw1, attachment_icon1, attachment_video_type1);
                } else if (i == 1) {
                    loadAttachmentsBasedOnType(mContext, attachment_details_array.optJSONObject(i), communityImgvw2, attachment_icon2, attachment_video_type2);
                }
            }

        } else if (attachment_details_array.length() == 3) {
            loadMoreImages(mContext, attachment_details_array, communityImgvw, communityImgvw1, communityImgvw2, attachment_icon, attachment_icon1, attachment_icon2, attachment_video_type, attachment_video_type1, attachment_video_type2);
        } else if (attachment_details_array.length() == 4) {
            loadMoreImages(mContext, attachment_details_array, communityImgvw, communityImgvw1, communityImgvw2, attachment_icon, attachment_icon1, attachment_icon2, attachment_video_type, attachment_video_type1, attachment_video_type2);
            remainingCountText.setVisibility(View.VISIBLE);
            remainingCountText.setText("+" + (attachment_details_array.length() - 3) + " more");
        } else {
            loadMoreImages(mContext, attachment_details_array, communityImgvw, communityImgvw1, communityImgvw2, attachment_icon, attachment_icon1, attachment_icon2, attachment_video_type, attachment_video_type1, attachment_video_type2);
            remainingCountText.setVisibility(View.VISIBLE);
            remainingCountText.setText("+" + (attachment_details_array.length() - 3) + " more");
        }

    }

    private static void loadMoreImages(Context mContext, JSONArray attachment_details_array, ImageView communityImgvw, ImageView communityImgvw1, ImageView communityImgvw2, ImageView attachment_icon, ImageView attachment_icon1, ImageView attachment_icon2, ImageView attachment_video_type, ImageView attachment_video_type1, ImageView attachment_video_type2) {
        communityImgvw.setVisibility(View.VISIBLE);
        communityImgvw1.setVisibility(View.VISIBLE);
        communityImgvw2.setVisibility(View.VISIBLE);
        for (int j = 0; j < attachment_details_array.length(); j++) {
            if (j == 0) {
                loadAttachmentsBasedOnType(mContext, attachment_details_array.optJSONObject(j), communityImgvw, attachment_icon, attachment_video_type);
            } else if (j == 1) {
                loadAttachmentsBasedOnType(mContext, attachment_details_array.optJSONObject(j), communityImgvw1, attachment_icon1, attachment_video_type1);
            } else if (j == 2) {
                loadAttachmentsBasedOnType(mContext, attachment_details_array.optJSONObject(j), communityImgvw2, attachment_icon2, attachment_video_type2);
            }
        }
    }

    public static void loadAttachmentsBasedOnType(Context mContext, JSONObject attachment_details_obj, ImageView imageView, ImageView attachment_icon, ImageView attachment_video_type) {
        if (attachment_details_obj == null) {
            return;
        }
        if (attachment_details_obj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_PDF) || attachment_details_obj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
            if (attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL) != null && !attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL).isEmpty()) {
                if (attachment_details_obj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                    attachment_icon.setVisibility(View.VISIBLE);
                    attachment_icon.setImageResource(R.drawable.ic_attachment_type_pdf);
                    loadImageUsingGlide(mContext, attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL).trim(), imageView, R.drawable.default_image_feed);
                } else if (attachment_details_obj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                    attachment_icon.setVisibility(View.VISIBLE);
                    attachment_video_type.setVisibility(View.VISIBLE);
                    attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
                    attachment_video_type.setImageResource(R.drawable.ic_playvideo);
                    loadImageUsingGlide(mContext, attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL).trim(), imageView, R.drawable.default_image_feed);
                }
            }
        } else if (attachment_details_obj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE)) {
            if (attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).contains(RestUtils.TAG_TYPE_GIF)) {
                attachment_icon.setVisibility(View.VISIBLE);
                attachment_icon.setImageResource(R.drawable.ic_attachment_type_gif);
                if (attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL) != null && !attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).isEmpty()) {
                    loadImageUsingGlide(mContext, attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).trim(), imageView, R.drawable.default_image_feed);
                }
            } else {
                attachment_icon.setVisibility(View.GONE);
                attachment_video_type.setVisibility(View.GONE);
                if (attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL) != null && !attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).isEmpty()) {
                    loadImageUsingGlide(mContext, attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).trim(), imageView, R.drawable.default_image_feed);
                }
            }

        } else if (attachment_details_obj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
            imageView.setBackgroundColor(Color.parseColor("#E8E8E8"));
            imageView.setImageResource(R.drawable.ic_attachment_type_audio);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
        } else {
            attachment_video_type.setVisibility(View.GONE);
            attachment_icon.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        }
    }

    public static String saveImage(Bitmap image, Context mContext) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        Date now = new Date();
        String imageFileName = "post_edit" + "_" + formatter.format(now) + ".jpg";
        File storageDir = AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/Post_images/");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            Log.e("saveImage :", "");
        }
        return imageFileName;
    }


    public static Bitmap createCircleBitmap(Bitmap bitmapimg) {
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
                (float) bitmapimg.getHeight() / 2, (float) bitmapimg.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
    }


    public static void loadBounceAnimation(Context mContext, View mView) {
        Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        mView.startAnimation(myAnim);
    }

    public static int getUserVerifiedStatus() {
        return MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_IS_USER_VERIFIED, 1);
    }

    public static boolean getCommunityUserVerifiedStatus() {
        return MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_IS_COMMUNITY_VERIFIED, false);
    }


    public static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage, Context mContext) throws IOException {

        try (InputStream input = mContext.getContentResolver().openInputStream(selectedImage)) {
            ExifInterface ei;
            if (Build.VERSION.SDK_INT > 23)
                ei = new ExifInterface(input);
            else
                ei = new ExifInterface(selectedImage.getPath());

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public static Bitmap loadImageFromPath(String imgPath) {
        BitmapFactory.Options options;
        Bitmap bitmap = null;
        try {
            options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(imgPath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static void redirectToSettings(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        mContext.startActivity(intent);
    }

    public static void logCustomEventsIntoFabric(String eventName, String doc_id, String serviceName, String timeStamp, String errorMsg) {
        Map<String, String> eventParams = new HashMap<>();
        //param keys and values have to be of String type
        eventParams.put(RestUtils.TAG_DOC_ID, doc_id);
        eventParams.put(RestUtils.TAG_SERVICE_NAME, serviceName);
        eventParams.put(RestUtils.TAG_TIMESTAMP, timeStamp);
        eventParams.put(RestUtils.TAG_ERROR_MESSAGE, errorMsg);
        //up to 10 params can be logged with each event
        FlurryAgent.logEvent(eventName, eventParams);

        logSentryEventWithCustomParams(eventName, eventParams);
    }

    //Method to display latest comment
    public static void displayLatestCommentUI(Context mContext, int login_doc_id, JSONObject commentInfoObj, RoundedImageView doc_img, TextView doc_name, TextView commented_text) {
        if (commentInfoObj.optJSONObject(RestUtils.TAG_USER_INFO).has(RestUtils.TAG_PROFILE_URL) && !commentInfoObj.optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_PROFILE_URL).trim().isEmpty() && mContext != null) {
            //asdfsdaf
            /*Picasso.with(mContext)
                    .load(commentInfoObj.optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_PROFILE_URL).trim()).placeholder(R.drawable.default_profilepic)
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(doc_img);*/

            Glide.with(mContext)
                    .load(commentInfoObj.optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_PROFILE_URL).trim())
                    .circleCrop()
                    .apply(getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_profilepic)))
                    .into(doc_img);
        } else {
            doc_img.setImageResource(R.drawable.default_profilepic);
        }
        doc_name.setText((login_doc_id == commentInfoObj.optJSONObject(RestUtils.TAG_USER_INFO).optInt(RestUtils.TAG_DOC_ID)) ? "You" : commentInfoObj.optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_SALUTAION) + " " + commentInfoObj.optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_FIRST_NAME));
        if (!commentInfoObj.optString(RestUtils.TAG_COMMENTED_TEXT).isEmpty()) {
            commented_text.setText(StringEscapeUtils.unescapeJava(commentInfoObj.optString(RestUtils.TAG_COMMENTED_TEXT)));
            commented_text.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, 0, 0);
        } else {
            commented_text.setText(" Image");
            commented_text.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_camera, 0, 0, 0);

        }
    }

    //Method to display latest action
    public static void displayLatestActionUI(JSONObject socialInteractionObj, int login_doc_id, TextView last_action_name_text, ViewGroup last_actoin_viewGroup) {
        if ((socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) && (socialInteractionObj.has(RestUtils.TAG_LIKE_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).length() > 0)) {
            if (!DateUtils.compareDates(socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).optLong(RestUtils.TAG_TIMESTAMP), socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).optLong(RestUtils.TAG_TIMESTAMP))) {
                last_action_name_text.setText((login_doc_id == socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optInt(RestUtils.TAG_DOC_ID)) ? "You commented on" : socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_SALUTAION) + " " + socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_FIRST_NAME) + " commented on");
            } else {
                last_action_name_text.setText((login_doc_id == socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optInt(RestUtils.TAG_DOC_ID)) ? "You liked" : socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_SALUTAION) + " " + socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_FIRST_NAME) + " liked");
            }
        } else if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
            last_action_name_text.setText((login_doc_id == socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optInt(RestUtils.TAG_DOC_ID)) ? "You commented on" : socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_SALUTAION) + " " + socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_FIRST_NAME) + " commented on");
        } else if (socialInteractionObj.has(RestUtils.TAG_LIKE_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).length() > 0) {
            last_action_name_text.setText((login_doc_id == socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optInt(RestUtils.TAG_DOC_ID)) ? "You liked" : socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_SALUTAION) + " " + socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).optJSONObject(RestUtils.TAG_USER_INFO).optString(RestUtils.TAG_USER_FIRST_NAME) + " liked");
        } else {
            last_actoin_viewGroup.setVisibility(View.GONE);
        }
    }

    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    public static int getArticleTypeBGColor(String article_type) {
        int textColor;
        switch (article_type) {
            case "journal":
                textColor = Color.parseColor("#f2cf67");
                break;
            case "conference news":
                textColor = Color.parseColor("#9bcb68");
                break;
            case "monthly briefing":
                textColor = Color.parseColor("#69cdcd");
                break;
            case "conference highlights":
                textColor = Color.parseColor("#67a6d2");
                break;
            case "institutional":
                textColor = Color.parseColor("#5cb0d5");
                break;
            case "capsules":
                textColor = Color.parseColor("#ff9368");
                break;
            case "matters":
                textColor = Color.parseColor("#0bc7c2");
                break;
            case "moments":
                textColor = Color.parseColor("#488ef7");
                break;
            case "bytes":
                textColor = Color.parseColor("#3ac36a");
                break;
            case "update":
                textColor = Color.parseColor("#89C659");
                break;
            case "case":
                textColor = Color.parseColor("#47AFFF");
                break;
            case "health tips":
                textColor = Color.parseColor("#19b867");
                break;
            case "medical news":
                textColor = Color.parseColor("#917fde");
                break;
            case "survey":
                textColor = Color.parseColor("#987EBB");
                break;
            case "facility updates":
                textColor = Color.parseColor("#8d6e63");
                break;
            case "faculty updates":
                textColor = Color.parseColor("#ba68c8");
                break;
            case "initiatives":
                textColor = Color.parseColor("#afb42b");
                break;
            case "opportunities":
                textColor = Color.parseColor("#546e7a");
                break;
            case "collaboration":
                textColor = Color.parseColor("#ffb300");
                break;
            case "expression of interest":
                textColor = Color.parseColor("#ff9a76");
                break;
            default:
                textColor = Color.parseColor("#888686");
                break;
        }
        return textColor;
    }


    public static Spannable linkifyHtml(String html, int linkifyMask) {
        Spanned text = Html.fromHtml(html, null, new MyTagHandler());
        URLSpan[] currentSpans = text.getSpans(0, text.length(), URLSpan.class);

        SpannableString buffer = new SpannableString(text);
        Linkify.addLinks(buffer, linkifyMask);

        for (URLSpan span : currentSpans) {
            int end = text.getSpanEnd(span);
            int start = text.getSpanStart(span);
            buffer.setSpan(span, start, end, 0);
        }
        return buffer;
    }

    public static long getJoinEnableTime(long startTimeInLong, long timeStamp, long joinBufferTime) {
        long timeLeftInLong;
        long diff = startTimeInLong - timeStamp;
        timeLeftInLong = diff - joinBufferTime;
        return timeLeftInLong;
    }


    /**
     * Deletes a directory tree recursively.
     */
    public static void deleteDirectoryTree(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteDirectoryTree(child);
            }
        }
        boolean del = fileOrDirectory.delete();
        Log.i("File Del", "" + del);
    }


    /**
     * Extracts a {@link Cache.Entry} from a {@link NetworkResponse}.
     * Cache-control headers are ignored. SoftTtl == 3 mins, ttl == 24 hours.
     *
     * @param response The network response to parse headers from
     * @return a cache entry for the given response, or null if the response is not cacheable.
     */
    public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        final long cacheHitButRefreshed = (long) 3 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
        final long cacheExpired = (long) 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }

    public static String convertFeedDataToString(long feedId, long docId, long channelId, long timeStamp) {
        JSONObject feedDataJson = new JSONObject();
        try {
            feedDataJson.put("c", channelId);
            feedDataJson.put("d", docId);
            feedDataJson.put("f", feedId);
            feedDataJson.put("t", timeStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return feedDataJson.toString();
    }

    public static JSONObject encryptFeedData(Context mContext, int feedId, int docId, int channelId, long selectedTime, String docName, String feedTitle, String text, String url) {
        JSONObject convertedShareJson = null;
        String encryptedString = "";
        String convertedString = convertFeedDataToString(feedId, docId, channelId, selectedTime);
        DESedeEncryption myEncryptor = DESedeEncryption.getInstance();
        Map<String, String> encryptionEvent = new HashMap<>();

        //param keys and values have to be of String type
        encryptionEvent.put(RestUtils.TAG_DOC_ID, "" + docId);
        encryptionEvent.put(RestUtils.TAG_FEED_ID, "" + feedId);
        encryptionEvent.put(RestUtils.TAG_TIMESTAMP, "" + selectedTime);
        if (myEncryptor != null) {
            String encrypted = myEncryptor.encrypt(convertedString).trim();
            try {
                if (encrypted != null) {
                    String shareURLPrefix = mContext.getResources().getText(R.string.share_url_qa).toString();
                    if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.PROD.getName())) {
                        shareURLPrefix = mContext.getResources().getText(R.string.share_url_prod).toString();
                    } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.STAGE.getName())) {
                        shareURLPrefix = mContext.getResources().getText(R.string.share_url_stage).toString();
                    }
                    byte[] data = encrypted.getBytes("UTF-8");
                    encryptedString = Base64.encodeToString(data, Base64.NO_WRAP | Base64.URL_SAFE);
                    String feedDesText = mContext.getString(R.string.share_des_text, docName, "article", feedTitle);
                    if (text != null && !text.isEmpty()) {
                        feedDesText = docName + " " + text + " " + feedTitle;
                    }
                    if (url != null && !url.isEmpty()) {
                        shareURLPrefix = url;
                    }
                    convertedShareJson = new JSONObject();
                    convertedShareJson.put(RestUtils.TAG_SHARE_TEXT, feedDesText);
                    convertedShareJson.put(RestUtils.TAG_ORIGINAL_URL, shareURLPrefix + encryptedString);
                    byte[] data1 = Base64.decode(encryptedString, Base64.NO_WRAP | Base64.URL_SAFE);
                    String base64Decoded = new String(data1, "UTF-8");
                    String decrypted = myEncryptor.decrypt(base64Decoded);
                    Log.e("DECRYPTED :", decrypted);
                } else {
                    //up to 10 params can be logged with each event
                    FlurryAgent.logEvent("SHARE_ERROR", encryptionEvent);
                    logSentryEventWithCustomParams("SHARE_ERROR", encryptionEvent);


                }
            } catch (Exception e) {
                FlurryAgent.logEvent("SHARE_ERROR", encryptionEvent);
                logSentryEventWithCustomParams("SHARE_ERROR", encryptionEvent);
                e.printStackTrace();
            }
        }
        return convertedShareJson;
    }

    public static void logSentryEventWithCustomParams(String eventName, Map<String, String> eventMap) {
        HashMap<String, Object> mapObj = new HashMap<>(eventMap);
        SentryEvent event = new SentryEvent();
        event.setLevel(SentryLevel.INFO);
        Message message = new Message();
        message.setMessage(eventName);
        event.setMessage(message);
        event.setExtras(mapObj);
        Sentry.captureEvent(event);
    }

    public static void captureSentryMessage(String message) {
        Sentry.captureMessage(message);
    }

    public static void inviteToWhiteCoatsIntent(Context context, String shareDescription, String intentTitle, String inviteURL) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String link_val = "https://whitecoats.com/invite";
        if (inviteURL != null && !inviteURL.isEmpty()) {
            link_val = inviteURL;
        }
        String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareDescription + Html.fromHtml(body));
        context.startActivity(Intent.createChooser(shareIntent, intentTitle));
    }

    public static void requestForShortURL(Context context, final RealmManager realmManager, final String request, final String subRequest, JSONObject convertedShareObj) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();
        new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.SHARE_A_FEED_V2, request, "SHARE_A_FEED", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                dialog.dismiss();
                boolean isURLconverted = false;
                String completeShareText = "";
                if (successResponse != null && !successResponse.isEmpty()) {
                    try {
                        JSONObject responseJson = new JSONObject(successResponse);
                        if (responseJson.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            String shortURL = responseJson.optString(RestUtils.TAG_SHORT_URL);
                            if (shortURL != null && !shortURL.isEmpty()) {
                                isURLconverted = true;
                                completeShareText = convertedShareObj.optString(RestUtils.TAG_SHARE_TEXT) + shortURL;
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (!isURLconverted) {
                    completeShareText = convertedShareObj.optString(RestUtils.TAG_SHARE_TEXT) + convertedShareObj.optString(RestUtils.TAG_ORIGINAL_URL);
                    Realm realm = Realm.getDefaultInstance();
                    realmManager.insertShareFailedFeedIntoDB(realm, subRequest);
                    if (!realm.isClosed()) {
                        realm.close();
                    }
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, completeShareText);
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, "Share via"));
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                dialog.dismiss();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, convertedShareObj.optString(RestUtils.TAG_SHARE_TEXT) + convertedShareObj.optString(RestUtils.TAG_ORIGINAL_URL));
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, "Share via"));
                Realm realm = Realm.getDefaultInstance();
                realmManager.insertShareFailedFeedIntoDB(realm, subRequest);
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        }).sendSinglePartRequest();
    }

    public static void requestForShareAFeed(Context mContext, final RealmManager realmManager, final String request, final String subRequest) {
        new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.SHARE_A_FEED, request, "SHARE_A_FEED", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (successResponse != null) {
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                Realm realm = Realm.getDefaultInstance();
                realmManager.insertShareFailedFeedIntoDB(realm, subRequest);
                if (!realm.isClosed()) {
                    realm.close();
                }
            }
        }).sendSinglePartRequest();
    }

    public static void syncShareFailedFeedsToServer(Context mContext, final RealmManager realmManager, int docId) {
        ArrayList<RealmShareFailedFeedInfo> results = realmManager.getShareFailedFeeds();
        JSONArray feedsJsonArray = new JSONArray();
        if (results != null) {
            for (int i = 0; i < results.size(); i++) {
                try {
                    feedsJsonArray.put(new JSONObject(results.get(i).getFeedData()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (results.size() > 0 && feedsJsonArray.length() > 0) {
                JSONObject requestObj = new JSONObject();
                try {
                    requestObj.put(RestUtils.TAG_USER_ID, docId);
                    requestObj.put(RestUtils.TAG_SHARED_FEEDS, feedsJsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.SHARE_A_FEED, requestObj.toString(), "SHARE_A_FEED", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        if (successResponse != null) {
                            //responseHandler(successResponse);
                            realmManager.clearShareFailedFeedsData();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {

                    }
                }).sendSinglePartRequest();
            }
        }

    }

    /**
     * @param days
     * @return formatted days
     * Note : Considering index of MON = 1,TUE=2.....SUN=7.
     */
    public static String formatAvailableDays(String days) {
        String[] daysOfWeek = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        String[] daysArr = days.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //Sort the array in ascending order
            Arrays.sort(daysArr, new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return Integer.parseInt(s) - Integer.parseInt(t1);
                }
            });
            Log.d(TAG, "Sorted Array - " + daysArr);
            int len = daysArr.length;
            /** Format the array
             *  option 1 - if consecutive numeric then add hyphen(-)
             *  option 2 - if not consecutive numeric then add hyphen(,)
             */
            for (int index = 0; index < len; index++) {
                int currentElement = Integer.parseInt(daysArr[index]);
                if (index != len - 1) {// if not last element of array
                    int nextElement = Integer.parseInt(daysArr[index + 1]);
                    if (index > 0) { //if not first element of array
                        int previousElement = Integer.parseInt(daysArr[index - 1]);
                        // if the current element comes in between next number and previous number then skip it.
                        if ((previousElement + 1) == (nextElement - 1))
                            continue;
                    }
                    if ((currentElement + 1) == nextElement)
                        stringBuilder.append(daysOfWeek[currentElement - 1] + "-");
                    else
                        stringBuilder.append(daysOfWeek[currentElement - 1] + ",");
                } else { // if last element of array then just append it.
                    stringBuilder.append(daysOfWeek[currentElement - 1]);
                }
            }
        } catch (NumberFormatException e) {
            Log.e("NUMBER_FORMAT_ERROR", e.getLocalizedMessage());
        }
        Log.d(TAG, "Final String - " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static void permissionNeverAskPrompt(final Context mContext, SpannableStringBuilder message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                intent.setData(uri);
                mContext.startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean checkWriteExternalPermission(Context mContext) {
        int res;
        String permission="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES;
        }else {
            permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        }
        res = mContext.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean checkPhoneStatePermission(Context mContext) {
        String permission = Manifest.permission.READ_PHONE_STATE;
        int res = mContext.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    public static void openLinkInBrowser(String url, Context mContext) {
        Log.i(TAG, "openLinkInBrowser()");
        try {
            if (url.startsWith("http://", 0) || url.startsWith("https://", 0)) {

            } else {
                url = "http://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Invalid URL", Toast.LENGTH_SHORT).show();
        }
    }

    public static void makePhoneCall(Context mContext, String mPhNum) {
        String phNumber = "+" + mPhNum.trim().replaceAll(" ", "");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phNumber));
        /*if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/
        mContext.startActivity(intent);
    }


    public static void sendEmail(Context mContext, String emailId, String subject, String text) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("text/plain");
        emailIntent.setData(Uri.parse("mailto:" + emailId.trim()));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static JSONObject processFeedSurveyResponse(JSONObject feedData, JSONObject surveyResponse) {
        try {
            JSONObject feedJson = feedData.optJSONObject(RestUtils.TAG_FEED_INFO);
            if (feedJson.has(RestUtils.KEY_FEED_SURVEY)) {
                feedJson.optJSONObject(RestUtils.KEY_FEED_SURVEY).put(RestUtils.KEY_IS_PARTICIPATED, surveyResponse.optBoolean(RestUtils.KEY_IS_PARTICIPATED));
                feedJson.optJSONObject(RestUtils.KEY_FEED_SURVEY).put(RestUtils.KEY_IMMEDIATE_RESULTS, surveyResponse.optBoolean(RestUtils.KEY_IMMEDIATE_RESULTS));
                if (feedJson.optJSONObject(RestUtils.KEY_FEED_SURVEY).has(RestUtils.KEY_QUESTIONS)) {
                    JSONArray storedQtnsArray = feedJson.optJSONObject(RestUtils.KEY_FEED_SURVEY).optJSONArray(RestUtils.KEY_QUESTIONS);
                    JSONArray updatedQtnsArray = surveyResponse.optJSONArray(RestUtils.KEY_QUESTIONS);
                    if (updatedQtnsArray != null && storedQtnsArray != null) {
                        for (int j = 0; j < updatedQtnsArray.length(); j++) {
                            for (int k = 0; k < storedQtnsArray.length(); k++) {
                                if (updatedQtnsArray.optJSONObject(j).optInt(RestUtils.KEY_QUESTION_ID) == storedQtnsArray.optJSONObject(k).optInt(RestUtils.KEY_QUESTION_ID)) {
                                    JSONObject qtnObj = updatedQtnsArray.optJSONObject(j);
                                    storedQtnsArray.optJSONObject(k).put(RestUtils.KEY_HIGH_PERCENTAGE, qtnObj.optInt(RestUtils.KEY_HIGH_PERCENTAGE));
                                    storedQtnsArray.optJSONObject(k).put(RestUtils.KEY_IS_ATTEMPTED, qtnObj.optBoolean(RestUtils.KEY_IS_ATTEMPTED));
                                    storedQtnsArray.optJSONObject(k).put(RestUtils.KEY_NO_OF_PARTICIPANTS, qtnObj.optInt(RestUtils.KEY_NO_OF_PARTICIPANTS));
                                    if (storedQtnsArray.optJSONObject(k).has(RestUtils.KEY_OPTIONS) && storedQtnsArray.optJSONObject(k).optJSONArray(RestUtils.KEY_OPTIONS) != null) {
                                        JSONArray storedOptionsArray = storedQtnsArray.optJSONObject(k).optJSONArray(RestUtils.KEY_OPTIONS);
                                        JSONArray updatedOptionsArray = updatedQtnsArray.optJSONObject(j).optJSONArray(RestUtils.KEY_OPTIONS);
                                        for (int x = 0; x < updatedOptionsArray.length(); x++) {
                                            for (int y = 0; y < storedOptionsArray.length(); y++) {
                                                if (updatedOptionsArray.optJSONObject(x).optInt(RestUtils.KEY_OPTION_ID) == storedOptionsArray.optJSONObject(y).optInt(RestUtils.KEY_OPTION_ID)) {
                                                    storedQtnsArray.optJSONObject(k).optJSONArray(RestUtils.KEY_OPTIONS).optJSONObject(y).put(RestUtils.KEY_IS_SELECTED, updatedOptionsArray.optJSONObject(x).optBoolean(RestUtils.KEY_IS_SELECTED));
                                                    storedQtnsArray.optJSONObject(k).optJSONArray(RestUtils.KEY_OPTIONS).optJSONObject(y).put(RestUtils.KEY_PARTICIPATED_PCT, updatedOptionsArray.optJSONObject(x).optInt(RestUtils.KEY_PARTICIPATED_PCT));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    feedJson.optJSONObject(RestUtils.KEY_FEED_SURVEY).put(RestUtils.KEY_QUESTIONS, storedQtnsArray);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return feedData;
    }

    public static FeedSurvey processSurveyResponseInFullView(FeedSurvey surveyObj, JSONObject surveyResponse) {
        try {
            if (surveyObj != null) {
                surveyObj.setParticipated(surveyResponse.optBoolean(RestUtils.KEY_IS_PARTICIPATED));
                surveyObj.setImmediate(surveyResponse.optBoolean(RestUtils.KEY_IMMEDIATE_RESULTS));
                JSONArray updatedQtnsArray = surveyResponse.optJSONArray(RestUtils.KEY_QUESTIONS);
                if (updatedQtnsArray != null) {
                    for (int j = 0; j < updatedQtnsArray.length(); j++) {
                        for (SurveyQuestion surveyQuestion : surveyObj.getQuestions()) {
                            if (surveyQuestion != null) {
                                if (updatedQtnsArray.optJSONObject(j).optInt(RestUtils.KEY_QUESTION_ID) == surveyQuestion.getQuestionId()) {
                                    JSONObject qtnObj = updatedQtnsArray.optJSONObject(j);
                                    surveyQuestion.setHighPercentage(qtnObj.optInt(RestUtils.KEY_HIGH_PERCENTAGE));
                                    surveyQuestion.setAttempted(qtnObj.optBoolean(RestUtils.KEY_IS_ATTEMPTED));
                                    surveyQuestion.setParticipantCount(qtnObj.optInt(RestUtils.KEY_NO_OF_PARTICIPANTS));
                                    surveyQuestion.setMandatory(qtnObj.optBoolean(RestUtils.KEY_IS_MANDATORY));
                                    surveyQuestion.setMultiSelect(qtnObj.optBoolean(RestUtils.KEY_IS_MULTI_SELECT));
                                    JSONArray updatedOptionsArray = updatedQtnsArray.optJSONObject(j).optJSONArray(RestUtils.KEY_OPTIONS);
                                    for (int x = 0; x < updatedOptionsArray.length(); x++) {
                                        for (SurveyOption surveyOption : surveyQuestion.getOptions()) {
                                            if (updatedOptionsArray.optJSONObject(x).optInt(RestUtils.KEY_OPTION_ID) == surveyOption.getOptionId()) {
                                                surveyOption.setSelected(updatedOptionsArray.optJSONObject(x).optBoolean(RestUtils.KEY_IS_SELECTED));
                                                surveyOption.setParticipatedPercent(updatedOptionsArray.optJSONObject(x).optInt(RestUtils.KEY_PARTICIPATED_PCT));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return surveyObj;
    }

    public static ContactsInfo convertConnectNotificationToPojo(RealmNotifications notification) {
        ContactsInfo notify_contactsInfo = new ContactsInfo();
        notify_contactsInfo.setNotification_id(notification.getNotification_id());
        notify_contactsInfo.setNotification_type(notification.getNotification_type());
        notify_contactsInfo.setName(notification.getDoc_name());
        notify_contactsInfo.setDoc_id(notification.getDoc_id());
        notify_contactsInfo.setQb_userid(notification.getDoc_qb_user_id());
        notify_contactsInfo.setLocation(notification.getDoc_location());
        notify_contactsInfo.setSpeciality(notification.getDoc_speciality());
        notify_contactsInfo.setSubSpeciality(notification.getDoc_sub_speciality());
        notify_contactsInfo.setWorkplace(notification.getDoc_workplace());
        notify_contactsInfo.setPic_name(notification.getDoc_pic());
        notify_contactsInfo.setPic_url(notification.getDoc_pic_url());
        notify_contactsInfo.setEmail(notification.getDoc_email());
        notify_contactsInfo.setPhno(notification.getDoc_phno());
        notify_contactsInfo.setEmail_vis(notification.getDoc_email_vis());
        notify_contactsInfo.setPhno_vis(notification.getDoc_phno_vis());
        notify_contactsInfo.setMessage(notification.getMessage());
        notify_contactsInfo.setTime(notification.getTime());
        notify_contactsInfo.setReadStatus(notification.isReadStatus());
        notify_contactsInfo.setUserSalutation(notification.getUser_salutation());
        notify_contactsInfo.setUserTypeId(notification.getUser_type_id());
        notify_contactsInfo.setPhno_vis(notification.getDoc_phno_vis());
        notify_contactsInfo.setEmail_vis(notification.getDoc_email_vis());
        return notify_contactsInfo;
    }

    public static ContactsInfo convertNetworkNotificationToPojo(RealmNotificationInfo notificationInfo) {

        ContactsInfo notify_contactsInfo = new ContactsInfo();
        try {
            JSONObject obj = new JSONObject(notificationInfo.getNotifyData());
            notify_contactsInfo.setNotification_id(notificationInfo.getNotificationID());
            notify_contactsInfo.setNotification_type(obj.optString(RestUtils.TAG_TYPE));
            notify_contactsInfo.setMessage(obj.optString("notificationMsg"));
            notify_contactsInfo.setTime(obj.optInt(RestUtils.TAG_TIME_RECEIVED));
            if (obj.has(RestUtils.TAG_NOTI_INFO)) {
                JSONObject fromDocjson = obj.optJSONObject(RestUtils.TAG_NOTI_INFO);
                notify_contactsInfo.setDoc_id(fromDocjson.optInt(RestUtils.TAG_USER_ID));
                notify_contactsInfo.setCnt_email(fromDocjson.optString(RestUtils.TAG_CNT_EMAIL));
                notify_contactsInfo.setCnt_num(fromDocjson.optString(RestUtils.TAG_CNT_NUM));
                notify_contactsInfo.setQb_userid(fromDocjson.optInt(RestUtils.TAG_QB_USER_ID));
                notify_contactsInfo.setSpeciality(fromDocjson.optString(RestUtils.TAG_SPLTY));
                notify_contactsInfo.setSubSpeciality(fromDocjson.optString(RestUtils.TAG_SUB_SPECIALITY));
                notify_contactsInfo.setUserTypeId(fromDocjson.optInt(RestUtils.TAG_USER_TYPE_ID));
                notify_contactsInfo.setUserSalutation(fromDocjson.optString(RestUtils.TAG_USER_SALUTAION));
                notify_contactsInfo.setLocation(fromDocjson.optString(RestUtils.TAG_LOCATION));
                notify_contactsInfo.setWorkplace(fromDocjson.optString(RestUtils.TAG_WORKPLACE));
                notify_contactsInfo.setPic_url(fromDocjson.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                notify_contactsInfo.setName(fromDocjson.optString(RestUtils.TAG_USER_SALUTAION) + fromDocjson.optString(RestUtils.TAG_FNAME) + " " + fromDocjson.optString(RestUtils.TAG_LNAME));
                notify_contactsInfo.setPhno_vis(fromDocjson.optString(RestUtils.TAG_CNNTMUNVIS));
                notify_contactsInfo.setEmail_vis(fromDocjson.optString(RestUtils.TAG_CNNTEMAILVIS));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notify_contactsInfo;
    }

    public static ContactsInfo convertNetworkTabNotificationToPojo(JSONObject obj) {

        ContactsInfo notify_contactsInfo = new ContactsInfo();
        try {
            if (obj != null) {
                //obj = new JSONObject(notificationInfo.getNotifyData());
                notify_contactsInfo.setNotification_id(obj.optString(RestUtils.TAG_NOTIFICATION_ID));
                notify_contactsInfo.setNotification_type(obj.optString(RestUtils.TAG_TYPE));
                notify_contactsInfo.setMessage(obj.optString(RestUtils.TAG_MESSAGE));
                notify_contactsInfo.setTime(obj.optInt(RestUtils.TAG_TIME_RECEIVED));
                if (obj.has(RestUtils.TAG_FROM_DOC)) {
                    JSONObject fromDocjson = obj.optJSONObject(RestUtils.TAG_FROM_DOC);
                    notify_contactsInfo.setDoc_id(fromDocjson.optInt(RestUtils.TAG_DOC_ID));
                    notify_contactsInfo.setEmail(fromDocjson.optString(RestUtils.TAG_CNT_EMAIL));
                    notify_contactsInfo.setPhno(fromDocjson.optString(RestUtils.TAG_CNT_NUM));
                    notify_contactsInfo.setQb_userid(fromDocjson.optInt(RestUtils.TAG_QB_USER_ID));
                    notify_contactsInfo.setSpeciality(fromDocjson.optString(RestUtils.TAG_SPLTY));
                    notify_contactsInfo.setSubSpeciality(fromDocjson.optString(RestUtils.TAG_SUB_SPECIALITY));
                    notify_contactsInfo.setFull_name(fromDocjson.optString(RestUtils.TAG_FULL_NAME));
                    notify_contactsInfo.setUserTypeId(fromDocjson.optInt(RestUtils.TAG_USER_TYPE_ID));
                    notify_contactsInfo.setUserSalutation(fromDocjson.optString(RestUtils.TAG_USER_SALUTAION));
                    notify_contactsInfo.setLocation(fromDocjson.optString(RestUtils.TAG_LOCATION));
                    notify_contactsInfo.setWorkplace(fromDocjson.optString(RestUtils.TAG_WORKPLACE));
                    notify_contactsInfo.setPic_name(fromDocjson.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                    notify_contactsInfo.setPic_url(fromDocjson.optString(RestUtils.TAG_PROFILE_PIC_URL));
                    notify_contactsInfo.setName((fromDocjson.has(RestUtils.TAG_USER_FULL_NAME)) ? fromDocjson.optString(RestUtils.TAG_USER_FULL_NAME) : fromDocjson.optString(RestUtils.TAG_FULL_NAME));
                    notify_contactsInfo.setPhno_vis(fromDocjson.optString(RestUtils.TAG_CNNTMUNVIS));
                    notify_contactsInfo.setEmail_vis(fromDocjson.optString(RestUtils.TAG_CNNTEMAILVIS));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notify_contactsInfo;
    }


    public static CaseroomNotifyInfo convertCaseroomNotificationToPojo(RealmCaseRoomNotifications notification) {
        CaseroomNotifyInfo caseroomNotifyInfo = new CaseroomNotifyInfo();
        caseroomNotifyInfo.setCaseroom_notify_id(notification.getCaseroom_notification_id());
        caseroomNotifyInfo.setCaseroom_id(notification.getCaseroom_id());
        caseroomNotifyInfo.setCaseroom_notify_type(notification.getCaseroom_notify_type());
        caseroomNotifyInfo.setCase_heading(notification.getCase_heading());
        caseroomNotifyInfo.setCaseroom_summary_id(notification.getCaseroom_summary_id());
        caseroomNotifyInfo.setCaseroom_group_created_date(notification.getCaseroom_group_created_date());
        caseroomNotifyInfo.setCase_speciality(notification.getCase_speciality());
        caseroomNotifyInfo.setTime_received(notification.getTime_received());
        caseroomNotifyInfo.setDoc_id(notification.getDoc_id());
        caseroomNotifyInfo.setDoc_qb_user_id(notification.getDoc_qb_user_id());
        caseroomNotifyInfo.setDoc_name(notification.getDoc_name());
        caseroomNotifyInfo.setDoc_speciality(notification.getDoc_speciality());
        caseroomNotifyInfo.setDoc_sub_speciality(notification.getSubSpeciality());
        caseroomNotifyInfo.setDoc_location(notification.getDoc_location());
        caseroomNotifyInfo.setDoc_workplace(notification.getDoc_workplace());
        caseroomNotifyInfo.setDoc_cnt_email(notification.getDoc_cnt_email());
        caseroomNotifyInfo.setDoc_cnt_num(notification.getDoc_cnt_num());
        caseroomNotifyInfo.setDoc_email_vis("SHOW ALL");
        caseroomNotifyInfo.setDoc_phno_vis("SHOW ALL");
        caseroomNotifyInfo.setDoc_pic_name(notification.getDoc_pic_name());
        caseroomNotifyInfo.setDoc_pic_url(notification.getDoc_pic_url());
        caseroomNotifyInfo.setUser_salutation(notification.getUser_salutation());
        caseroomNotifyInfo.setUser_type_id(notification.getUser_type_id());
        return caseroomNotifyInfo;
    }

    public static CaseroomNotifyInfo convertCaseRoomNetworkNotificationToPojo(RealmNotificationInfo notificationInfo) {
        CaseroomNotifyInfo caseroomNotifyInfo = new CaseroomNotifyInfo();
        try {
            JSONObject obj = new JSONObject(notificationInfo.getNotifyData());
            caseroomNotifyInfo.setCaseroom_notify_type(obj.optString(RestUtils.TAG_TYPE));
            caseroomNotifyInfo.setCaseroom_notify_id(notificationInfo.getNotificationID());
            caseroomNotifyInfo.setTime_received(obj.optInt(RestUtils.TAG_TIME_RECEIVED));
            if (obj.has("caseroom_invite_info")) {
                JSONObject fromCaseInviteInfo = obj.optJSONObject("caseroom_invite_info");
                caseroomNotifyInfo.setCaseroom_summary_id(fromCaseInviteInfo.optString(RestUtils.CASEROOM_SUMMARY_ID));
                caseroomNotifyInfo.setCaseroom_id(fromCaseInviteInfo.optString(RestUtils.TAG_CASE_ROOM_ID));
                caseroomNotifyInfo.setCase_speciality(fromCaseInviteInfo.optString(RestUtils.TAG_CASE_ROOM_SPLTY));
                caseroomNotifyInfo.setCaseroom_group_created_date(fromCaseInviteInfo.optInt(RestUtils.TAG_CASE_ROOM_GROUP_CREATED_DATE));
            }
            if (obj.has("inviter_info")) {
                JSONObject fromInviterInfo = obj.optJSONObject("inviter_info");
                caseroomNotifyInfo.setDoc_id(fromInviterInfo.optInt(RestUtils.TAG_DOC_ID));
                caseroomNotifyInfo.setDoc_cnt_email(fromInviterInfo.optString(RestUtils.TAG_CNT_EMAIL));
                caseroomNotifyInfo.setDoc_cnt_num(fromInviterInfo.optString(RestUtils.TAG_CNT_NUM));
                caseroomNotifyInfo.setDoc_qb_user_id(fromInviterInfo.optString(RestUtils.TAG_QB_USER_ID));
                caseroomNotifyInfo.setCase_speciality(fromInviterInfo.optString(RestUtils.TAG_SPLTY));
                caseroomNotifyInfo.setDoc_sub_speciality(fromInviterInfo.optString(RestUtils.TAG_SUB_SPECIALITY));
                caseroomNotifyInfo.setDoc_name(fromInviterInfo.optString(RestUtils.TAG_FULL_NAME));
                caseroomNotifyInfo.setUser_type_id(fromInviterInfo.optInt(RestUtils.TAG_USER_TYPE_ID));
                caseroomNotifyInfo.setUser_salutation(fromInviterInfo.optString(RestUtils.TAG_USER_SALUTAION));
                caseroomNotifyInfo.setDoc_location(fromInviterInfo.optString(RestUtils.TAG_LOCATION));
                caseroomNotifyInfo.setDoc_workplace(fromInviterInfo.optString(RestUtils.TAG_WORKPLACE));
                caseroomNotifyInfo.setDoc_pic_name(fromInviterInfo.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                caseroomNotifyInfo.setDoc_pic_url(fromInviterInfo.optString(RestUtils.TAG_PROFILE_PIC_URL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return caseroomNotifyInfo;
    }

    public static GroupNotifyInfo convertNetworkGroupNotificationToPojo(RealmNotificationInfo notificationInfo) {
        GroupNotifyInfo groupNotifyInfo = new GroupNotifyInfo();
        try {
            JSONObject obj = new JSONObject(notificationInfo.getNotifyData());
            groupNotifyInfo.setGroup_notification_type(obj.optString(RestUtils.TAG_TYPE));
            groupNotifyInfo.setGroup_notify_id(notificationInfo.getNotificationID());
            groupNotifyInfo.setGroup_notification_time(obj.optInt(RestUtils.TAG_TIME_RECEIVED));

            if (obj.has("group_info")) {
                JSONObject fromCaseInviteInfo = obj.optJSONObject("group_info");
                groupNotifyInfo.setGroup_id(fromCaseInviteInfo.optString(RestUtils.TAG_GROUP_ID));
                groupNotifyInfo.setGroup_pic_url(fromCaseInviteInfo.optString(RestUtils.TAG_GROUP_PROFILE_IMG_NAME));
                groupNotifyInfo.setGroup_name(fromCaseInviteInfo.optString(RestUtils.TAG_GROUP_TITLE));
                groupNotifyInfo.setGroup_creation_time(fromCaseInviteInfo.optInt(RestUtils.TAG_GROUP_CREATION_TIME));
            }
            if (obj.has("inviter_info")) {
                JSONObject fromInviterInfo = obj.optJSONObject("inviter_info");
                groupNotifyInfo.setGroup_admin_Doc_id(fromInviterInfo.optInt(RestUtils.TAG_DOC_ID));
                groupNotifyInfo.setGroup_admin_email(fromInviterInfo.optString(RestUtils.TAG_CNT_EMAIL));
                groupNotifyInfo.setGroup_admin_phno(fromInviterInfo.optString(RestUtils.TAG_CNT_NUM));
                groupNotifyInfo.setGroup_admin_qb_user_id(fromInviterInfo.optString(RestUtils.TAG_QB_USER_ID));
                groupNotifyInfo.setGroup_admin_specialty(fromInviterInfo.optString(RestUtils.TAG_SPLTY));
                groupNotifyInfo.setGroup_admin_sub_specialty(fromInviterInfo.optString(RestUtils.TAG_SUB_SPECIALITY));
                groupNotifyInfo.setGroup_admin_name(fromInviterInfo.optString(RestUtils.TAG_FULL_NAME));
                groupNotifyInfo.setUser_type_id(fromInviterInfo.optInt(RestUtils.TAG_USER_TYPE_ID));
                groupNotifyInfo.setUser_salutation(fromInviterInfo.optString(RestUtils.TAG_USER_SALUTAION));
                groupNotifyInfo.setGroup_admin_location(fromInviterInfo.optString(RestUtils.TAG_LOCATION));
                groupNotifyInfo.setGroup_admin_workplace(fromInviterInfo.optString(RestUtils.TAG_WORKPLACE));
                groupNotifyInfo.setGroup_admin_pic(fromInviterInfo.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                groupNotifyInfo.setGroup_admin_pic_url(fromInviterInfo.optString(RestUtils.TAG_PROFILE_PIC_URL));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupNotifyInfo;
    }

    public static GroupNotifyInfo convertGroupNotificationToPojo(RealmGroupNotifications realmGroupNotifications) {
        GroupNotifyInfo groupNotifyInfo = new GroupNotifyInfo();
        groupNotifyInfo.setGroup_notify_id(realmGroupNotifications.getGroup_notification_id());
        groupNotifyInfo.setGroup_id(realmGroupNotifications.getGroup_id());
        groupNotifyInfo.setGroup_notification_time(realmGroupNotifications.getGroup_notification_time());
        groupNotifyInfo.setGroup_notification_type(realmGroupNotifications.getGroup_notification_type());
        groupNotifyInfo.setGroup_name(realmGroupNotifications.getGroup_name());
        groupNotifyInfo.setGroup_pic(realmGroupNotifications.getGroup_pic());
        groupNotifyInfo.setGroup_pic_url(realmGroupNotifications.getGroup_pic_url());
        groupNotifyInfo.setGroup_admin_Doc_id(realmGroupNotifications.getGroup_admin_Doc_id());
        groupNotifyInfo.setGroup_admin_name(realmGroupNotifications.getGroup_admin_name());
        groupNotifyInfo.setGroup_admin_specialty(realmGroupNotifications.getGroup_admin_specialty());
        groupNotifyInfo.setGroup_admin_sub_specialty(realmGroupNotifications.getGroup_admin_sub_specialty());
        groupNotifyInfo.setGroup_admin_workplace(realmGroupNotifications.getGroup_admin_workplace());
        groupNotifyInfo.setGroup_admin_location(realmGroupNotifications.getGroup_admin_location());
        groupNotifyInfo.setGroup_admin_phno(realmGroupNotifications.getGroup_admin_phno());
        groupNotifyInfo.setGroup_admin_email(realmGroupNotifications.getGroup_admin_email());
        groupNotifyInfo.setGroup_admin_phno_vis(realmGroupNotifications.getGroup_admin_phno_vis());
        groupNotifyInfo.setGroup_admin_email_vis(realmGroupNotifications.getGroup_admin_email_vis());
        groupNotifyInfo.setGroup_admin_qb_user_id(realmGroupNotifications.getGroup_admin_qb_user_id());
        groupNotifyInfo.setGroup_admin_pic(realmGroupNotifications.getGroup_admin_pic());
        groupNotifyInfo.setGroup_admin_pic_url(realmGroupNotifications.getGroup_admin_pic_url());
        groupNotifyInfo.setUser_salutation(realmGroupNotifications.getUser_salutation());
        groupNotifyInfo.setUser_type_id(realmGroupNotifications.getUser_type_id());
        return groupNotifyInfo;
    }


    public static Object[] checkAndUpdateJSON(Object original, Object replace) {
//        boolean changed = false;
        Object[] returnObjects = new Object[2];
        returnObjects[0] = false;
        try {
            if (replace.getClass() == JSONArray.class) {
//                changed = true;
                returnObjects[0] = true;
                if (((JSONArray) replace).length() == ((JSONArray) original).length()) {
                    for (int i = 0; i < ((JSONArray) replace).length(); i++) {
                        checkAndUpdateJSON(((JSONArray) original).get(i), ((JSONArray) replace).get(i));
                    }
                } else {
                    returnObjects[1] = replace;
                }
            } else if (replace.getClass() == JSONObject.class) {
                // New way
                for (Iterator<String> it = ((JSONObject) replace).keys(); it.hasNext(); ) {
                    String key = it.next();
                    Object[] returnObj = checkAndUpdateJSON(((JSONObject) original).get(key), ((JSONObject) replace).get(key));
                    if (returnObj[0] != null && ((Boolean) returnObj[0]) == false) {
                        ((JSONObject) original).put(key, ((JSONObject) replace).get(key));
                        returnObjects[0] = true;
//                        changed = true;

                    } else if (returnObj.length > 1 && returnObjects[1] != null && returnObjects[1].getClass() == JSONArray.class) {
                        ((JSONObject) original).put(key, returnObjects[1]);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            returnObjects[0] = false;
        }

        return returnObjects;
    }

    public static void subscribeDeviceForNotifications(Context mContext, int doc_id, String providerName, boolean isLogedIn, String regId) {
        String tag = "DEVICE_INFO_API_FCM";
        Log.e("CLASS", mContext.toString());
        if (providerName.equalsIgnoreCase("UrbanAirship")) {
            Log.e("ON_READY", "AirshipService_3 : " + regId);
            tag = "DEVICE_INTO_API_AIRSHIP";
        }
        JSONObject deviceInfoObject = new JSONObject();
        try {
            deviceInfoObject.put(RestUtils.TAG_USER_ID, doc_id);
            deviceInfoObject.put(RestUtils.TAG_REGISTRATION_ID, regId);
            deviceInfoObject.put(RestUtils.TAG_DEVICE_OS, "Android");
            String deviceID = "";
            if (AppUtil.checkPhoneStatePermission(mContext)) {
                deviceID = AppUtil.getDeviceID(mContext);
            }
            deviceInfoObject.put(RestUtils.TAG_DEVICE_ID, deviceID);
            String environmentValue = "prod";
            if (BuildConfig.DEBUG) {
                // do something for a debug build
                environmentValue = "prod";
            }
            deviceInfoObject.put(RestUtils.TAG_ENVIRONMENT, environmentValue);
            deviceInfoObject.put(RestUtils.TAG_MAC_ID, "");
            deviceInfoObject.put(RestUtils.TAG_PROVIDER, providerName);
            deviceInfoObject.put(RestUtils.TAG_IS_ENABLE, NotificationManagerCompat.from(mContext).areNotificationsEnabled());
            deviceInfoObject.put(RestUtils.TAG_IS_USER_LOGGEDIN, isLogedIn);

            new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.DEVICE_INFO, deviceInfoObject.toString(), tag, new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    Log.d("DEVICE_INFO", "success");
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.d("DEVICE_INFO", "failure");
                }
            }).sendSinglePartRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void openFbLink(Context mContext, String fblink) {
        String url = fblink;
        if (url.startsWith("http://", 0) || url.startsWith("https://", 0)) {

        } else {
            fblink = "http://" + url;
            url = "http://" + url;
        }
        if (isFbUrl(url)) {
            url = url.replace("https://www.facebook.com/", "fb://");
            url = url.replace("http://www.facebook.com/", "fb://");
            url = url.replace("https://facebook.com/", "fb://");
            url = url.replace("http://facebook.com/", "fb://");
            url = url.replace("https://facebook.com", "fb://");
            url = url.replace("http://facebook.com", "fb://");
            url = url.replace("wwww.facebook.com", "fb://");
            url = url.replace("facebook.com", "fb://");
        }
        try {
            mContext.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            Intent appIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)); //Trys to make intent with FB's URI
            mContext.startActivity(appIntent);
        } catch (Exception e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fblink));
            mContext.startActivity(browserIntent); //catches and opens a url to the desired page
        }
    }


    public static void logFlurryEventWithDocIdAndEmailEvent(String eventName, String doc_id, String emailId) {
        Map<String, String> articleParams = new HashMap<>();
        //param keys and values have to be of String type
        articleParams.put(RestUtils.TAG_DOC_ID, doc_id);
        articleParams.put(RestUtils.TAG_EMAIL, emailId);
        //up to 10 params can be logged with each event
        FlurryAgent.logEvent(eventName, articleParams);

        //Events in Sentry
        logSentryEventWithCustomParams(eventName, articleParams);


    }

    public static void invalidateAndLoadImage(Context mContext, String mUri, ImageView imgView) {
        if (mUri != null && !mUri.isEmpty()) {
            /*Picasso.with(mContext)
                    .load(mUri).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(imgView);*/
            Glide.with(mContext).load(mUri).apply(new RequestOptions()
                    .placeholder(R.drawable.default_profilepic)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.default_profilepic)
                    .priority(Priority.IMMEDIATE)).into(imgView);

        }
    }

    public static void invalidateAndLoadCircularImage(Context mContext, String imageUrl, ImageView imageView, int placeHolder) {
        if (imageUrl.isEmpty()) {
            return;
        }
        Glide.with(mContext).load(imageUrl).circleCrop().apply(new RequestOptions()
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(placeHolder)
                .priority(Priority.IMMEDIATE)).into(imageView);
    }

    public static void logUserActionEvent(int docId, String eventName, JSONObject eventRequestObj, HashMap<String, Object> upShotEventMap, Context context) {
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.createEvent(eventName, upShotEventMap, false);
        //API call to log user action events
        sendUserActionEventAPICall(docId, eventName, eventRequestObj, context);
    }

    public static void logUserUpShotEvent(String eventName, HashMap<String, Object> upShotEventMap) {
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.createEvent(eventName, upShotEventMap, false);
    }


    public static void logUserEventWithDocIDAndSplty(String eventName, RealmBasicInfo basicInfo, Context context) {
        HashMap<String, Object> data = new HashMap<>();
        if (basicInfo != null && basicInfo.getDoc_id() != null && basicInfo.getUserUUID() != null && basicInfo.getSplty() != null) {
            data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
            data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
            JSONObject eventRequestObj = new JSONObject();
            try {
                eventRequestObj.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
                eventRequestObj.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            logUserActionEvent(basicInfo.getDoc_id(), eventName, eventRequestObj, data, context);
        }
    }

    //Log dashboard impression event

    public static void logDashboardImpressionEvent(String eventName, RealmBasicInfo basicInfo, Context context) {
        String versioncode = String.valueOf(BuildConfig.VERSION_CODE);
        HashMap<String, Object> data = new HashMap<>();
        if (basicInfo != null && basicInfo.getDoc_id() != null && basicInfo.getUserUUID() != null && basicInfo.getSplty() != null) {
            data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
            data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
            data.put(RestUtils.EVENT_APP_VERSION, versioncode);
            JSONObject eventRequestObj = new JSONObject();
            try {
                eventRequestObj.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
                eventRequestObj.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
                eventRequestObj.put(RestUtils.EVENT_APP_VERSION, versioncode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            logUserActionEvent(basicInfo.getDoc_id(), eventName, eventRequestObj, data, context);
        }
    }

    public static void logUserWithEventName(int docId, String eventName, JSONObject eventRequestObj, Context context) {

        int default_docId = 0;
        if (docId == 0) {
            //check for login doc id
            if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.DEV.getName())) {
                default_docId = context.getResources().getInteger(R.integer.default_dev_doc_id);
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.QA.getName())) {
                default_docId = context.getResources().getInteger(R.integer.default_qa_doc_id);
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.QAKB.getName())) {
                default_docId = context.getResources().getInteger(R.integer.default_qakb_doc_id);
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.STAGE.getName())) {
                default_docId = context.getResources().getInteger(R.integer.default_prod_doc_id);
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.UAT.getName())) {
                default_docId = context.getResources().getInteger(R.integer.default_uat_doc_id);
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.PROD.getName())) {
                default_docId = context.getResources().getInteger(R.integer.default_prod_doc_id);
            }
        } else {
            default_docId = docId;
        }
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.createEvent(eventName, new HashMap<>(), false);
        sendUserActionEventAPICall(default_docId, eventName, eventRequestObj, context);
    }

    public static void logUserEventWithParams(String eventName, RealmBasicInfo basicInfo, int feedId, int channelId, String channelName, Context context) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
        data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
        data.put(RestUtils.EVENT_FEED_ID, feedId);
        data.put(RestUtils.EVENT_CHANNEL_ID, channelId);
        data.put(RestUtils.EVENT_CHANNEL_NAME, channelName);
        JSONObject eventRequestObj = new JSONObject();
        try {
            eventRequestObj.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
            eventRequestObj.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
            eventRequestObj.put(RestUtils.EVENT_FEED_ID, feedId);
            eventRequestObj.put(RestUtils.EVENT_CHANNEL_ID, channelId);
            eventRequestObj.put(RestUtils.EVENT_CHANNEL_NAME, channelName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        logUserActionEvent(basicInfo.getDoc_id(), eventName, eventRequestObj, data, context);
    }

    public static void sendUserActionEventAPICall(int docId, String eventName, JSONObject eventRequestObj, Context context) {
        RealmManager realmManager = RealmManager.getInstance(context);
        JSONObject userEventRequest = new JSONObject();
        JSONArray eventsObjArray = new JSONArray();
        JSONObject eventObj = new JSONObject();
        try {
            eventRequestObj.put(RestUtils.TAG_TIMESTAMP, getTimeWithTimeZone());
            eventObj.put(RestUtils.TAG_EVENT_TYPE, eventName);
            eventObj.put(RestUtils.TAG_EVENT_DATA, eventRequestObj);
            eventsObjArray.put(eventObj);
            userEventRequest.put(RestUtils.TAG_USER_ID, docId);
            userEventRequest.put(RestUtils.TAG_USER_EVENTS, eventsObjArray);
            userEventRequest.put(RestUtils.TAG_COUNT, 0);
            new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.LOG_USER_EVENT_API, userEventRequest.toString(), "TAG_NOTIFICATION_ECHO", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    try {
                        JSONObject responseObj = new JSONObject(successResponse);
                        if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {

                        } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            realmManager.insertOrUpdateEventDataDB(eventName, eventRequestObj.toString(), System.currentTimeMillis());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    realmManager.insertOrUpdateEventDataDB(eventName, eventRequestObj.toString(), System.currentTimeMillis());
                }
            }).sendSinglePartRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static boolean isPhoneNumber(String url) {
        return android.util.Patterns.PHONE.matcher(url).matches();
    }

    public static boolean isEmailAddress(String url) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(url).matches();
    }

    public static boolean isWebUrl(String url) {
        return Patterns.WEB_URL.matcher(url.toLowerCase()).matches();
    }

    public static boolean isFbUrl(String url) {
        return (url.contains("https://www.facebook.com/") || url.contains("http://www.facebook.com/") || url.contains("https://facebook.com/") || url.contains("http://facebook.com/") || url.contains("http://facebook.com") || url.contains("https://facebook.com") || url.contains("facebook.com") || url.contains("www.facebook.com"));
    }

    public static void copyFile(File src, File dst) throws IOException {
        try (FileChannel inChannel = new FileInputStream(src).getChannel(); FileChannel outChannel = new FileOutputStream(dst).getChannel()) {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void alertMessage(Context mContext, String permissionType) {
        Log.i(TAG, "alertMessage()");
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        if (permissionType.equalsIgnoreCase(mContext.getString(R.string.label_camera))) {
            builder.setMessage(AppUtil.alert_CameraPermissionDeny_Message());
        } else if (permissionType.equalsIgnoreCase(mContext.getString(R.string.label_storage))) {
            builder.setMessage(AppUtil.alert_StoragePermissionDeny_Message());
        } else if (permissionType.equalsIgnoreCase(mContext.getString(R.string.label_files))) {
            builder.setMessage(AppUtil.alert_StoragePermissionDeny_Message_ForFiles());
        }
        builder.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                intent.setData(uri);
                mContext.startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null && !extension.equals("")) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        } else if (extension != null && url.lastIndexOf(".") != -1) {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(ext);
        }
        return type;
    }

    public static boolean isFileSizeSupported(File file) {
        // Get length of file in bytes
        long fileSizeInBytes = file.length();
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        if (fileSizeInMB > 50) {
            return false;
        }
        return true;
    }


    public static String logScreenEvent(String eventName) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(BrandKinesis.BK_CURRENT_PAGE, eventName);

        String eventID = BrandKinesis.getBKInstance().createEvent(BKProperties.BKPageViewEvent.NATIVE, data, true);
        return eventID;
    }


    public static void logUserEventWithHashMap(String eventName, int docId, HashMap<String, Object> data, Context context) {
        int default_docId = 0;
        if (docId == 0) {
            //check for login doc id
            if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.DEV.getName())) {
                default_docId = 138;
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.QA.getName())) {
                default_docId = 1369;
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.QAKB.getName())) {
                default_docId = 1369;
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.STAGE.getName())) {
                default_docId = 444;
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.UAT.getName())) {
                default_docId = 537;
            } else if (BuildConfig.FLAVOR.equalsIgnoreCase(WhitecoatsFlavor.PROD.getName())) {
                default_docId = 25676;
            }
        } else {
            default_docId = docId;
        }
        JSONObject eventRequestObj = new JSONObject(data);

        logUserActionEvent(default_docId, eventName, eventRequestObj, data, context);
    }

    public static boolean deleteRecursive(File path) {
        Log.i(TAG, "deleteRecursive()");
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            boolean del = false;
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteRecursive(files[i]);
                } else {
                     del = files[i].delete();
                }
            }
            Log.i("File Del", "" + del);
        }
        return (path.delete());
    }

    public static void deleteFilesOnPermission(Context context, File deleteFile) {
       if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
           if (ContextCompat.checkSelfPermission(context,
                   Manifest.permission.READ_MEDIA_IMAGES) ==
                   PackageManager.PERMISSION_GRANTED) {
               if (deleteFile.exists()) {
                   deleteRecursive(deleteFile);
               }
           }
       }else {
           if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
               if (ContextCompat.checkSelfPermission(context,
                       Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                       PackageManager.PERMISSION_GRANTED) {
                   if (deleteFile.exists()) {
                       deleteRecursive(deleteFile);
                   }
               }
           } else {
               if (deleteFile.exists()) {
                   deleteRecursive(deleteFile);
               }
           }
       }

    }


    public static String getMimeTypeFromUri(Context context, Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = cR.getType(uri);

        return type;
    }


    public static String getFileNameFromUri(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    public static String getTimeWithTimeZone() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ", Locale.ENGLISH);
        //Log.e(C.TAG, "formatted string: "+sdf.format(c.getTime()));
        return sdf.format(c.getTime());
    }

    public static String getTimeZone() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ZZZZZ", Locale.ENGLISH);
        return sdf.format(c.getTime());
    }

    public static void handleSendText(Context mContext, int docId, String sharedURL, String restAPI_URL, String requestKey) {
        if (sharedURL != null) {
            JSONObject shareDetailsObj = new JSONObject();
            try {
                shareDetailsObj.put(RestUtils.TAG_USER_ID, docId);
                shareDetailsObj.put(requestKey, sharedURL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, EmptyActivity.class);
            intent.putExtra(RestUtils.TAG_SHARED_FEED_DATA, shareDetailsObj.toString());
            intent.putExtra(RestUtils.TAG_REST_API_URL, restAPI_URL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
            if (mContext instanceof SplashScreenActivity) {
                ((SplashScreenActivity) mContext).finish();
            }
        }
    }

    public static HashMap<String, Object> convertJsonToHashMap(JSONObject jObject) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Iterator<?> keys = jObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = jObject.getString(key);
            map.put(key, value);

        }
        return map;
    }

    public static void getAddressDetailsUsingAPI(Context context, String successResponse, String api_key, OnlocationApiFinishedListener callBack) {
        HashMap<String, String> addressMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(successResponse);
            if (jsonObject != null && jsonObject.has("results")) {
                JSONArray resultObj = jsonObject.optJSONArray("results");
                if (resultObj != null && resultObj.length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    String placeId = resultObj.optJSONObject(0).optString("place_id");
                    JSONArray addressComponents = resultObj.optJSONObject(0).optJSONArray("address_components");
                    for (int i = 0; i < addressComponents.length(); i++) {
                        JSONObject addressComponent = addressComponents.optJSONObject(i);
                        JSONArray types = addressComponent.optJSONArray("types");
                        for (int j = 0; j < types.length(); j++) {
                            if (types.optString(j).equalsIgnoreCase("premise") || types.optString(j).equalsIgnoreCase("sublocality_level_2")) {
                                if (types.optString(j).equalsIgnoreCase("premise")) {
                                    sb.append(addressComponent.optString("long_name"));
                                    sb.append(", ");
                                }
                                if (types.optString(j).equalsIgnoreCase("sublocality_level_2")) {
                                    sb.append(addressComponent.optString("long_name"));
                                }
                            }
                        }
                    }
                    if (sb.length() > 0) {
                        addressMap.put("workplace", sb.toString());
                    }
                    new RequestForPlacesUsingPlaceId(context, placeId, api_key, new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            try {
                                JSONObject jsonObject = new JSONObject(successResponse);
                                if (jsonObject != null && jsonObject.has("result")) {
                                    JSONObject resultObj = jsonObject.optJSONObject("result");
                                    JSONArray addressComponents = resultObj.optJSONArray("address_components");
                                    for (int i = 0; i < addressComponents.length(); i++) {
                                        JSONObject addressComponent = addressComponents.optJSONObject(i);
                                        JSONArray types = addressComponent.optJSONArray("types");
                                        for (int j = 0; j < types.length(); j++) {
                                            if (types.optString(j).equalsIgnoreCase("locality") || types.optString(j).equalsIgnoreCase("administrative_area_level_3")) {
                                                addressMap.put("city", addressComponent.optString("long_name"));
                                                callBack.onlocationApiFinishedListener(addressMap, jsonObject);
                                            }
                                        }
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            String error = errorResponse;
                            callBack.onLocationApiError(error);
                        }
                    });

                } else {
                    //log event
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static String parseDateIntoDays(Long timeAtMiliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatterYear = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatterHrs = new SimpleDateFormat("hh:mm a");
        //timeAtMiliseconds *= 1000L; //Check if this is unnecessary for your use

        if (timeAtMiliseconds == 0) {
            return "";
        }

        //API.log("Day Ago "+dayago);
        String result = "now";
        String dataSot = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong = timeAtMiliseconds;
        calendar.setTimeInMillis(dayagolong);
        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(dataSot);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (elapsedDays == 0) {
                if (elapsedHours == 0) {

                    String timeInHrs = formatterHrs.format(calendar.getTime());
                    return timeInHrs;
                } else {
                    return String.valueOf(elapsedHours) + " h";
                }

            } else {
                if (elapsedDays <= 6) {
                    return String.valueOf(elapsedDays) + " d";
                }
                if (elapsedDays <= 30) {
                    int weeks = (int) elapsedDays / 7;
                    return String.valueOf(weeks) + " w";
                }
                if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1 m";
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2 m";
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3 m";
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4 m";
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5 m";
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6 m";
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7 m";
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8 m";
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9 m";
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10 m";
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11 m";
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12 m";
                }

                if (elapsedDays > 360 && elapsedDays <= 720) {
                    return "1 y";
                }

                if (elapsedDays > 720) {
                    Calendar calendarYear = Calendar.getInstance();
                    calendarYear.setTimeInMillis(dayagolong);
                    return formatterYear.format(calendarYear.getTime()) + "";
                }

            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String parsePersonalizeDateIntoDays(Long timeAtMiliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatterYear = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatterHrs = new SimpleDateFormat("hh:mm a");
        //timeAtMiliseconds *= 1000L; //Check if this is unnecessary for your use

        if (timeAtMiliseconds == 0) {
            return "";
        }

        //API.log("Day Ago "+dayago);
        String result = "now";
        String dataSot = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong = timeAtMiliseconds;
        calendar.setTimeInMillis(dayagolong);
        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(dataSot);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (elapsedDays == 0) {
                if (elapsedHours == 0) {

                    String timeInHrs = formatterHrs.format(calendar.getTime());
                    return timeInHrs;
                } else {
                    return String.valueOf(elapsedHours) + " hour(s)";
                }

            } else {
                if (elapsedDays <= 6) {
                    return String.valueOf(elapsedDays) + " day(s)";
                }
                if (elapsedDays <= 30) {
                    int weeks = (int) elapsedDays / 7;
                    return String.valueOf(weeks) + " week(s)";
                }
                if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1 month(s)";
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2 month(s)";
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3 month(s)";
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4 month(s)";
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5 month(s)";
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6 month(s)";
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7 month(s)";
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8 month(s)";
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9 month(s)";
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10 month(s)";
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11 month(s)";
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12 month(s)";
                }

                if (elapsedDays > 360 && elapsedDays <= 720) {
                    return "1 year(s)";
                }

                if (elapsedDays > 720) {
                    Calendar calendarYear = Calendar.getInstance();
                    calendarYear.setTimeInMillis(dayagolong);
                    return formatterYear.format(calendarYear.getTime()) + "";
                }

            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Map<String, String> getRequestHeaders(Context context) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.put("Connection", "close");
        headers.put("X-DEVICE-ID", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        headers.put("X-TIME-ZONE", AppUtil.getTimeZone());
        headers.put("X-STAY-LOGGEDIN", "yes");
        SharedPreferences httpshp = context.getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
        final String spring_securityToken = httpshp.getString("SSTOKEN", "");
        if (spring_securityToken != null && !spring_securityToken.equals("")) {
            headers.put("X-Auth-Token", spring_securityToken);
        }
        String cookies = MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_COOKIE, null);
        if (cookies != null) {
            headers.put("Cookie", cookies);
        }
        headers.put("X-APP-VERSION", HttpClient.getVerionCode(context));
        headers.put("X-APP-CHANNEL-NAME", "ANDROID");
        MySharedPref mySharedPref = new MySharedPref(context);
        String reg_id = "";
        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            reg_id = mySharedPref.getPrefsHelper().getPref(MyFcmListenerService.PROPERTY_REG_ID, "");
        }
        String AndroidVersion = android.os.Build.VERSION.RELEASE;
        String devicemodel = android.os.Build.MODEL;
        String deviceMaker = Build.MANUFACTURER;
        if (!AndroidVersion.equals("") && !devicemodel.equals("")) {
            headers.put("X-DEVICE-OS-VERSION", AndroidVersion);
            headers.put("X-DEVICE-MODEL", devicemodel);
        }
        headers.put("X-DEVICE-MAKER", deviceMaker);
        headers.put("X_APP_NOTIFICATIONS_ID", reg_id);
        headers.put("Authorization", "Basic YWRtaW46ZDAzM2UyMmFlMzQ4YWViNTY2MGZjMjE0MGFlYzM1ODUwYzRkYTk5Nw==");
        return headers;
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static void showLocationServiceDenyAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("Please enable location services to help us capture your current location.");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public static void processUpToDateLink(Context context, String isUptodatelink, int doc_Id) {
        if (AppUtil.getUserVerifiedStatus() == 3) {
            if (AppUtil.isConnectingToInternet(context)) {
                JSONObject reqUrlJsonObject = new JSONObject();
                try {
                    reqUrlJsonObject.put(RestUtils.TAG_USER_ID, doc_Id);
                    reqUrlJsonObject.put(RestUtils.TAG_EXTERNAL_URL, isUptodatelink);

                    new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.UP_TO_DATE_API, reqUrlJsonObject.toString(), "UPTODATE_URL_CONTENT_FILLVIEW", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            try {
                                JSONObject responseObj = new JSONObject(successResponse);
                                if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                    if (responseObj.has(RestUtils.TAG_DATA)) {
                                        String external_link = responseObj.optJSONObject(RestUtils.TAG_DATA).optString(RestUtils.TAG_EXTERNAL_URL);
                                        Intent intent = new Intent(context, WebViewActivity.class);
                                        intent.putExtra("EXTERNAL_LINK", external_link);
                                        context.startActivity(intent);
                                    } else {
                                        promptDialogWithMessage(context, context.getResources().getString(R.string.somenthing_went_wrong));
                                    }
                                } else if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                                    String errorMsg = context.getResources().getString(R.string.somenthing_went_wrong);
                                    if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                        errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    }
                                    promptDialogWithMessage(context, errorMsg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            if (errorResponse != null && !errorResponse.isEmpty()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(errorResponse);
                                    String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    if (errorMessage != null && !errorMessage.isEmpty()) {
                                        promptDialogWithMessage(context, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                promptDialogWithMessage(context, context.getResources().getString(R.string.somenthing_went_wrong));
                            }
                        }
                    }).sendSinglePartRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else if (AppUtil.getUserVerifiedStatus() == 1) {
            AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_not_uploaded));
        } else if (AppUtil.getUserVerifiedStatus() == 2) {
            AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_uploaded_but_not_verified));
        }
    }

    public static void promptDialogWithMessage(Context mContext, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public static void processWebinarData(Context mContext, int docId, int feedId, String feedTitle, JSONObject eventDetails, TextView date_webinar, TextView webinar_time, Button register_now, Button join_now, ImageView already_registered_icon, TextView already_registered_text, ProgressBar progressBar, TextView webinar_status, LinearLayout webinar_time_date_inner_lay, TextView ended_text, Button view_recordings_btn, ProgressBar simplePB) {
        String[] suffixes =
                {"0th", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th",
                        "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th",
                        "20th", "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th",
                        "30th", "31st"};

        DateFormat Dformatter = new SimpleDateFormat("dd MM yyyy");
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        Dformatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(eventDetails.optLong("from_date"));
        calendar.setTime(calendar.getTime());
        String webinarDate = Dformatter.format(calendar.getTime());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = suffixes[day];

        String[] webinarDateSplit = webinarDate.split(" ");
        String webinar_date = webinarDateSplit[0];
        String webinar_month = webinarDateSplit[1];
        String webinar_year = webinarDateSplit[2];

        int actualMonth = Integer.parseInt(webinar_month) - 1;
        final Calendar c = Calendar.getInstance(Locale.getDefault());
        c.set(Integer.parseInt(webinar_year), actualMonth, Integer.parseInt(webinar_date));
        SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.getDefault());
        String month_name = month_date.format(c.getTime()).toUpperCase();
        date_webinar.setText(Html.fromHtml(dayStr + " " + month_name + " " + webinar_year));

        GradientDrawable titleDrawable = (GradientDrawable) webinar_status.getBackground();

        String startTime = DateUtils.longToMessageDate(eventDetails.optLong("from_date"));
        String endTime = DateUtils.longToMessageDate(eventDetails.optLong("to_date"));
        webinar_time.setText(startTime + " - " + endTime);
        // date_webinar.setText(DateUtils.convertLongtoDates(eventDetails.optLong("from_date")));
        long startTimeInLong = eventDetails.optLong("from_date");
        long endTimeInLong = eventDetails.optLong("to_date");
        long timeStamp = System.currentTimeMillis();
        long joinBtnBufferTime = eventDetails.optLong("enableJoinButton");
        String joinLink = eventDetails.optString("link");
        boolean isYouTubeLiveStreaming = eventDetails.optBoolean("is_live");
        boolean isLoadInWebView = eventDetails.optBoolean("is_web_view");

        boolean isUserRegistered = eventDetails.optBoolean("is_user_registered");
        boolean hasCancelled = eventDetails.optBoolean("has_cancelled");
        String webinarStatus = getWebinarStatus(eventDetails);
        if (webinarStatus.equalsIgnoreCase("LIVE")) {
            webinar_status.setVisibility(View.VISIBLE);
            webinar_status.setText(webinarStatus);
            titleDrawable.setColor(mContext.getResources().getColor(R.color.white));
            webinar_status.setTextColor(mContext.getResources().getColor(R.color.red));
            webinar_time_date_inner_lay.setVisibility(View.GONE);
            ended_text.setVisibility(View.GONE);
            view_recordings_btn.setVisibility(View.GONE);
        } else if (webinarStatus.equalsIgnoreCase("ENDED")) {
            webinar_status.setVisibility(View.VISIBLE);
            webinar_status.setText(webinarStatus);
            titleDrawable.setColor(mContext.getResources().getColor(R.color.white));
            webinar_status.setTextColor(mContext.getResources().getColor(R.color.black));
            webinar_time_date_inner_lay.setVisibility(View.GONE);
            /*if (eventDetails.optBoolean("videos_uploaded")) {
                ended_text.setVisibility(View.GONE);
            } else {
                ended_text.setVisibility(View.VISIBLE);
            }*/
            if (AppUtil.isYoutubeUrl(joinLink) && isYouTubeLiveStreaming) {
                view_recordings_btn.setVisibility(View.VISIBLE);
                ended_text.setVisibility(View.GONE);
            } else if (eventDetails.optBoolean("videos_uploaded")) {
                view_recordings_btn.setVisibility(View.GONE);
                ended_text.setVisibility(View.GONE);
            } else {
                view_recordings_btn.setVisibility(View.GONE);
                ended_text.setVisibility(View.VISIBLE);
            }

        } else if (webinarStatus.equalsIgnoreCase("CANCELLED")) {
            webinar_status.setVisibility(View.VISIBLE);
            webinar_status.setText(webinarStatus);
            titleDrawable.setColor(mContext.getResources().getColor(R.color.red));
            webinar_status.setTextColor(mContext.getResources().getColor(R.color.white));
            webinar_time_date_inner_lay.setVisibility(View.GONE);
            ended_text.setVisibility(View.GONE);
            view_recordings_btn.setVisibility(View.GONE);
        } else {
            webinar_time_date_inner_lay.setVisibility(View.VISIBLE);
            webinar_status.setVisibility(View.GONE);
            ended_text.setVisibility(View.GONE);
            view_recordings_btn.setVisibility(View.GONE);
        }


        if (hasCancelled) {
            register_now.setVisibility(View.GONE);
            join_now.setVisibility(View.GONE);
            already_registered_icon.setVisibility(View.GONE);
            already_registered_text.setVisibility(View.GONE);
        } else {
            if (!isUserRegistered) {
                if (timeStamp >= (startTimeInLong - (joinBtnBufferTime * 60000)) && timeStamp <= endTimeInLong) {
                    join_now.setVisibility(View.VISIBLE);
                    register_now.setVisibility(View.GONE);
                } else {
                    join_now.setVisibility(View.GONE);
                    if (timeStamp > endTimeInLong) {
                        register_now.setVisibility(View.GONE);
                    } else {
                        register_now.setVisibility(View.VISIBLE);
                    }
                }
                already_registered_icon.setVisibility(View.GONE);
                already_registered_text.setVisibility(View.GONE);
            } else {
                register_now.setVisibility(View.GONE);
                join_now.setVisibility(View.GONE);
                already_registered_icon.setVisibility(View.VISIBLE);
                already_registered_text.setVisibility(View.VISIBLE);
                if (timeStamp >= (startTimeInLong - (joinBtnBufferTime * 60000)) && timeStamp <= endTimeInLong) {
                    register_now.setVisibility(View.GONE);
                    join_now.setVisibility(View.VISIBLE);
                    webinar_status.setVisibility(View.VISIBLE);
                    webinar_status.setText("LIVE");
                    titleDrawable.setColor(mContext.getResources().getColor(R.color.white));
                    webinar_status.setTextColor(mContext.getResources().getColor(R.color.red));
                    webinar_time_date_inner_lay.setVisibility(View.GONE);
                    already_registered_icon.setVisibility(View.GONE);
                    already_registered_text.setVisibility(View.GONE);
                } else if (timeStamp > endTimeInLong) {
                    register_now.setVisibility(View.GONE);
                    join_now.setVisibility(View.GONE);
                    already_registered_icon.setVisibility(View.GONE);
                    already_registered_text.setVisibility(View.GONE);

                }
            }

            long joinBtnEnableTime = getJoinEnableTime(startTimeInLong, timeStamp, (joinBtnBufferTime * 60000));
            if (joinBtnEnableTime >= 0L) {
                Handler handler = new Handler();
                //creates handler object on main thread
                final Runnable runTask = () -> {
                    // Execute tasks on main thread
                    /*if(isUserRegistered) {
                        join_now.setVisibility(View.VISIBLE);
                    }else{
                        join_now.setVisibility(View.GONE);
                    }*/

                    join_now.setVisibility(View.VISIBLE);
                    webinar_status.setVisibility(View.VISIBLE);
                    webinar_status.setText("LIVE");
                    titleDrawable.setColor(mContext.getResources().getColor(R.color.white));
                    webinar_status.setTextColor(mContext.getResources().getColor(R.color.red));
                    register_now.setVisibility(View.GONE);
                    webinar_time_date_inner_lay.setVisibility(View.GONE);
                    already_registered_icon.setVisibility(View.GONE);
                    already_registered_text.setVisibility(View.GONE);
                };
                handler.postDelayed(runTask, joinBtnEnableTime);
            }
        }

        register_now.setOnClickListener(view -> {
            boolean userVerify = eventDetails.optBoolean("verify_user");
            if (userVerify) {
                if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                    registerNowService(mContext, feedId, docId, feedTitle, register_now, join_now, already_registered_icon, already_registered_text, eventDetails, progressBar);
                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified));
                }
            } else {
                registerNowService(mContext, feedId, docId, feedTitle, register_now, join_now, already_registered_icon, already_registered_text, eventDetails, progressBar);
            }
        });
        join_now.setOnClickListener(v -> {
            if (joinLink.isEmpty()) {
                Toast.makeText(mContext, "Invalid link to join.", Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject requestObj = new JSONObject();
            try {
                requestObj.put(RestUtils.TAG_FEED_ID, feedId);
                sendUserActionEventAPICall(docId, "WebinarJoinEvent", requestObj, mContext);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Make API call to get latest Webinar URL
            if (isYouTubeLiveStreaming) {
                join_now.setVisibility(View.GONE);
                simplePB.setVisibility(View.VISIBLE);
                JSONObject _requestObj = new JSONObject();
                try {
                    _requestObj.put(RestUtils.TAG_USER_ID, docId);
                    _requestObj.put(RestUtils.TAG_FEED_ID, feedId);

                    new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.GET_LATEST_EVENT_URL, _requestObj.toString(), "GET_EVNET_URL", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            try {
                                join_now.setVisibility(View.VISIBLE);
                                simplePB.setVisibility(View.GONE);
                                JSONObject responseObj = new JSONObject(successResponse);
                                if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                    if (responseObj.has(RestUtils.TAG_DATA)) {
                                        String event_url = responseObj.optJSONObject(RestUtils.TAG_DATA).optString("event_url");
                                        if (!event_url.isEmpty()) {
                                            webinarEventNavigation(mContext, docId, event_url, isLoadInWebView);
                                        } else {
                                            webinarEventNavigation(mContext, docId, joinLink, isLoadInWebView);
                                        }
                                    }

                                } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    webinarEventNavigation(mContext, docId, joinLink, isLoadInWebView);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            join_now.setVisibility(View.VISIBLE);
                            simplePB.setVisibility(View.GONE);
                            webinarEventNavigation(mContext, docId, joinLink, isLoadInWebView);
                        }
                    }).sendSinglePartRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                webinarEventNavigation(mContext, docId, joinLink, isLoadInWebView);
            }
            /*if (AppUtil.isYoutubeUrl(joinLink) && isYouTubeLiveStreaming) {
                Intent in = new Intent(mContext, YoutubeVideoViewActivity.class);
                in.putExtra("video_id", AppUtil.getVideoIdFromYoutubeUrl(joinLink));
                in.putExtra("login_user_id", docId);
                mContext.startActivity(in);
            } else if (isLoadInWebView) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("EXTERNAL_LINK", joinLink);
                mContext.startActivity(intent);
            } else {
                openLinkInBrowser(joinLink, mContext);
            }*/
        });
        view_recordings_btn.setOnClickListener(v -> {
            if (joinLink.isEmpty()) {
                Toast.makeText(mContext, "Unable to view recording,please try after sometime.", Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject requestObj = new JSONObject();
            try {
                requestObj.put(RestUtils.TAG_FEED_ID, feedId);
                sendUserActionEventAPICall(docId, "WebinarViewRecording", requestObj, mContext);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Make API call to get latest Webinar URL
            if (isYouTubeLiveStreaming) {
                view_recordings_btn.setVisibility(View.GONE);
                simplePB.setVisibility(View.VISIBLE);
                JSONObject _requestObj = new JSONObject();
                try {
                    _requestObj.put(RestUtils.TAG_USER_ID, docId);
                    _requestObj.put(RestUtils.TAG_FEED_ID, feedId);

                    new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.GET_LATEST_EVENT_URL, _requestObj.toString(), "GET_EVNET_URL", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            try {
                                view_recordings_btn.setVisibility(View.VISIBLE);
                                simplePB.setVisibility(View.GONE);
                                JSONObject responseObj = new JSONObject(successResponse);
                                if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                    if (responseObj.has(RestUtils.TAG_DATA)) {
                                        String event_url = responseObj.optJSONObject(RestUtils.TAG_DATA).optString("event_url");
                                        if (!event_url.isEmpty()) {
                                            viewRecordingNavigation(mContext, docId, event_url);
                                        } else {
                                            viewRecordingNavigation(mContext, docId, joinLink);
                                        }
                                    }

                                } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    viewRecordingNavigation(mContext, docId, joinLink);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            view_recordings_btn.setVisibility(View.VISIBLE);
                            simplePB.setVisibility(View.GONE);
                            viewRecordingNavigation(mContext, docId, joinLink);
                        }
                    }).sendSinglePartRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                viewRecordingNavigation(mContext, docId, joinLink);
            }
            /*if (AppUtil.isYoutubeUrl(joinLink) && isYouTubeLiveStreaming) {
                Intent in = new Intent(mContext, YoutubeVideoViewActivity.class);
                in.putExtra("video_id", AppUtil.getVideoIdFromYoutubeUrl(joinLink));
                in.putExtra("login_user_id", docId);
                mContext.startActivity(in);
            } else {
                openLinkInBrowser(joinLink, mContext);
            }*/
            //AppUtil.sendUserActionEventAPICall(docId, "WebinarViewRecording", new JSONObject(), mContext);
        });
    }

    private static void viewRecordingNavigation(Context mContext, int docId, String event_url) {
        if (AppUtil.isYoutubeUrl(event_url)) {
            Intent in = new Intent(mContext, YoutubeVideoViewActivity.class);
            in.putExtra("video_id", AppUtil.getVideoIdFromYoutubeUrl(event_url));
            in.putExtra("login_user_id", docId);
            mContext.startActivity(in);
        } else {
            openLinkInBrowser(event_url, mContext);
        }
    }

    private static void webinarEventNavigation(Context mContext, int docId, String event_url, boolean isLoadInWebView) {
        if (AppUtil.isYoutubeUrl(event_url)) {
            Intent in = new Intent(mContext, YoutubeVideoViewActivity.class);
            in.putExtra("video_id", AppUtil.getVideoIdFromYoutubeUrl(event_url));
            in.putExtra("login_user_id", docId);
            mContext.startActivity(in);
        } else if (isLoadInWebView) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("EXTERNAL_LINK", event_url);
            mContext.startActivity(intent);
        } else {
            openLinkInBrowser(event_url, mContext);
        }
    }

    // process Register event data
    public static void processRegisterEventData(Context mContext, int docId, int feedId, String feedTitle, JSONObject eventDetails, TextView date_webinar, TextView webinar_time, Button register_now, Button join_now, ImageView already_registered_icon, TextView already_registered_text, ProgressBar progressBar, TextView webinar_status, LinearLayout webinar_time_date_inner_lay, TextView ended_text, TextView tv_date_time_label, TextView tv_organizer) {
        String[] suffixes =
                {"0th", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th",
                        "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th",
                        "20th", "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th",
                        "30th", "31st"};

        DateFormat Dformatter = new SimpleDateFormat("dd MM yyyy");
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        Dformatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(eventDetails.optLong("from_date"));
        calendar.setTime(calendar.getTime());
        String webinarDate = Dformatter.format(calendar.getTime());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = suffixes[day];

        String[] webinarDateSplit = webinarDate.split(" ");
        String webinar_date = webinarDateSplit[0];
        String webinar_month = webinarDateSplit[1];
        String webinar_year = webinarDateSplit[2];

        int actualMonth = Integer.parseInt(webinar_month) - 1;
        final Calendar c = Calendar.getInstance(Locale.getDefault());
        c.set(Integer.parseInt(webinar_year), actualMonth, Integer.parseInt(webinar_date));
        SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.getDefault());
        String month_name = month_date.format(c.getTime()).toUpperCase();
        date_webinar.setText(Html.fromHtml(dayStr + " " + month_name + " " + webinar_year));

        date_webinar.setVisibility(View.VISIBLE);
        webinar_time.setVisibility(View.VISIBLE);
        tv_organizer.setVisibility(View.VISIBLE);
        tv_date_time_label.setText("Event Date and Time");
        tv_organizer.setText("Organizer Details :" + "\n");
        tv_organizer.append(eventDetails.optString("organization") + "\n");
        tv_organizer.append(eventDetails.optString("org_name") + "\n");
        tv_organizer.append(eventDetails.optString("org_phone") + "\n");
        tv_organizer.append(eventDetails.optString("org_email"));
        GradientDrawable titleDrawable = (GradientDrawable) webinar_status.getBackground();
        //String webinarStatus = getWebinarStatus(eventDetails);

        //long start_time = eventDetails.optLong("from_date");
        long end_time = eventDetails.optLong("to_date");
        String webinarStatus = "";
        long currentTime = System.currentTimeMillis();
        boolean hasCancelled = eventDetails.optBoolean("has_cancelled");
        //long joinBtnBufferTime = eventDetails.optLong("enableJoinButton");
        boolean isUserRegistered = eventDetails.optBoolean("is_user_registered");
        if (hasCancelled) {
            webinarStatus = "CANCELLED";
        } else {
            if (currentTime > end_time) {
                webinarStatus = "ENDED";
            } else {
                webinarStatus = "LIVE";
            }
        }
        if (webinarStatus.equalsIgnoreCase("LIVE")) {
            webinar_status.setVisibility(View.GONE);
            webinar_status.setText(webinarStatus);
            titleDrawable.setColor(mContext.getResources().getColor(R.color.white));
            webinar_status.setTextColor(mContext.getResources().getColor(R.color.red));
            //webinar_time_date_inner_lay.setVisibility(View.GONE);
            ended_text.setVisibility(View.GONE);
        } else if (webinarStatus.equalsIgnoreCase("ENDED")) {
            webinar_status.setVisibility(View.VISIBLE);
            webinar_status.setText(webinarStatus);
            titleDrawable.setColor(mContext.getResources().getColor(R.color.white));
            webinar_status.setTextColor(mContext.getResources().getColor(R.color.black));
            //webinar_time_date_inner_lay.setVisibility(View.GONE);
            ended_text.setVisibility(View.VISIBLE);
            if (isUserRegistered) {
                ended_text.setText("You are successfully registered for this event.");
            } else {
                ended_text.setText("Registrations are closed for this event.");
            }

        } else if (webinarStatus.equalsIgnoreCase("CANCELLED")) {
            webinar_status.setVisibility(View.VISIBLE);
            webinar_status.setText(webinarStatus);
            titleDrawable.setColor(mContext.getResources().getColor(R.color.red));
            webinar_status.setTextColor(mContext.getResources().getColor(R.color.white));
            //webinar_time_date_inner_lay.setVisibility(View.GONE);
            ended_text.setVisibility(View.GONE);
        } else {
            //webinar_time_date_inner_lay.setVisibility(View.GONE);
            webinar_status.setVisibility(View.GONE);
            ended_text.setVisibility(View.GONE);
        }

        String startTime = DateUtils.longToMessageDate(eventDetails.optLong("from_date"));
        String endTime = DateUtils.longToMessageDate(eventDetails.optLong("to_date"));
        webinar_time.setText(startTime + " - " + endTime);
        // date_webinar.setText(DateUtils.convertLongtoDates(eventDetails.optLong("from_date")));
        long startTimeInLong = eventDetails.optLong("from_date");
        long endTimeInLong = eventDetails.optLong("to_date");
        long timeStamp = System.currentTimeMillis();


        //boolean hasCancelled = eventDetails.optBoolean("has_cancelled");
        if (hasCancelled) {
            register_now.setVisibility(View.GONE);
            join_now.setVisibility(View.GONE);
            already_registered_icon.setVisibility(View.GONE);
            already_registered_text.setVisibility(View.GONE);
        } else {
            if (!isUserRegistered) {
                if (timeStamp <= endTimeInLong) {
                    join_now.setVisibility(View.GONE);
                    register_now.setVisibility(View.VISIBLE);
                } else {
                    join_now.setVisibility(View.GONE);
                    register_now.setVisibility(View.GONE);
                }
                already_registered_icon.setVisibility(View.GONE);
                already_registered_text.setVisibility(View.GONE);
            } else {
                register_now.setVisibility(View.GONE);
                join_now.setVisibility(View.GONE);
                already_registered_icon.setVisibility(View.VISIBLE);
                already_registered_text.setVisibility(View.VISIBLE);
            }
        }

        register_now.setOnClickListener(view -> {
            new AlertDialog.Builder(mContext).setMessage("Do you want to proceed with registration? ").setPositiveButton("Confirm Registration", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!isConnectingToInternet(mContext)) {
                        return;
                    }
                    boolean userVerify = eventDetails.optBoolean("verify_user");
                    if (userVerify) {
                        if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                            if (eventDetails.optString("event_type").equalsIgnoreCase("Internal")) {
                                registerNowService(mContext, feedId, docId, feedTitle, register_now, join_now, already_registered_icon, already_registered_text, eventDetails, progressBar);
                            } else {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put(RestUtils.TAG_USER_ID, docId);
                                    jsonObject.put(RestUtils.TAG_FEED_ID, feedId);
                                    AppUtil.logUserActionEvent(docId, "Register_External_Event", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), mContext);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                openLinkInBrowser(eventDetails.optString("external_url"), mContext);
                            }
                        } else if (AppUtil.getUserVerifiedStatus() == 1) {
                            AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_not_uploaded));
                        } else if (AppUtil.getUserVerifiedStatus() == 2) {
                            AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified));
                        }
                    } else {
                        if (eventDetails.optString("event_type").equalsIgnoreCase("Internal")) {
                            registerNowService(mContext, feedId, docId, feedTitle, register_now, join_now, already_registered_icon, already_registered_text, eventDetails, progressBar);
                        } else {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put(RestUtils.TAG_USER_ID, docId);
                                jsonObject.put(RestUtils.TAG_FEED_ID, feedId);
                                AppUtil.logUserActionEvent(docId, "Register_External_Event", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), mContext);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            openLinkInBrowser(eventDetails.optString("external_url"), mContext);
                        }
                    }

                }
            }).setNegativeButton(android.R.string.no, null).show();

        });

    }

    private static void registerNowService(Context mContext, int feedId, int docId, String feedTitle, Button register_now, Button join_now, ImageView already_registered_icon, TextView already_registered_text, JSONObject eventDetails, ProgressBar progressBar) {
        if (AppUtil.isConnectingToInternet(mContext)) {
            register_now.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            JSONObject registerNowJsonObject = new JSONObject();
            try {
                registerNowJsonObject.put(RestUtils.TAG_USER_ID, docId);
                registerNowJsonObject.put(RestUtils.FEEDID, feedId);
                new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.WEBINAR_REGISTER_NOW_SERVICE, registerNowJsonObject.toString(), "WEBINAR_REGISTER", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject responseJsonObject = new JSONObject(successResponse);
                            if (responseJsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                String registrationSuccessMsg = mContext.getString(R.string.webinar_registration_thanks_text, feedTitle);
                                Toast.makeText(mContext, registrationSuccessMsg, Toast.LENGTH_LONG).show();
                                register_now.setVisibility(View.GONE);
                                join_now.setVisibility(View.GONE);
                                already_registered_icon.setVisibility(View.VISIBLE);
                                already_registered_text.setVisibility(View.VISIBLE);
                                eventDetails.put("is_user_registered", true);
                                for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                    listener.notifyUIWithFeedWebinarResponse(feedId, eventDetails);
                                }
                            } else if (responseJsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                String errorMsg = mContext.getResources().getString(R.string.unable_to_connect_server);
                                if (responseJsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = responseJsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                /*5063 - completed
                                  5062-cancelled
                                  5064-Max limit reached*/
                                if (responseJsonObject.optString(RestUtils.TAG_ERROR_CODE).equalsIgnoreCase("5063")) {
                                    //register_now.setVisibility(View.GONE);
                                } else if (responseJsonObject.optString(RestUtils.TAG_ERROR_CODE).equalsIgnoreCase("5062")) {
                                    eventDetails.put("has_cancelled", true);
                                    for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                        listener.notifyUIWithFeedWebinarResponse(feedId, eventDetails);
                                    }
                                } else if (responseJsonObject.optString(RestUtils.TAG_ERROR_CODE).equalsIgnoreCase("5064")) {
                                    register_now.setVisibility(View.VISIBLE);
                                } else {
                                    register_now.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        progressBar.setVisibility(View.GONE);
                        register_now.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, "Something went wrong,please try after sometime.", Toast.LENGTH_SHORT).show();

                    }
                }).sendSinglePartRequest();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static String getWebinarStatus(JSONObject eventDetails) {
        long start_time = eventDetails.optLong("from_date");
        long end_time = eventDetails.optLong("to_date");
        String webinarStatus = "";
        long currentTime = System.currentTimeMillis();
        boolean hasCancelled = eventDetails.optBoolean("has_cancelled");
        long joinBtnBufferTime = eventDetails.optLong("enableJoinButton");
        if (hasCancelled) {
            webinarStatus = "CANCELLED";
        } else {
            if (currentTime >= (start_time - (joinBtnBufferTime * 60000)) && currentTime <= end_time) {
                webinarStatus = "LIVE";
            } else if (currentTime > end_time) {
                webinarStatus = "ENDED";
            }
        }
        return webinarStatus;
    }

    public static void logUserVerificationInfoEvent(int verificationStatus) {
        Bundle userInfo = new Bundle();
        HashMap<String, Object> others = new HashMap<>();
        others.put("isCommunityVerified", getCommunityUserVerifiedStatus());
        others.put("isUserVerified", verificationStatus);
        userInfo.putSerializable(BKUserInfo.BKUserData.OTHERS, others);
        Log.d("BUNDLE_DATA", userInfo.toString());
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

    public static void ifInterestedButtonVisibility(Context mContext, JSONObject eventDetails, Button eoi_lay, int feedId, int docId) {
        if (eventDetails != null) {
            if (eventDetails.optString("type").equalsIgnoreCase(RestUtils.TAG_EOI)) {
                eoi_lay.setVisibility(View.VISIBLE);
            } else {
                eoi_lay.setVisibility(View.GONE);
            }
        } else {
            eoi_lay.setVisibility(View.GONE);
        }
        eoi_lay.setOnClickListener(view -> {

            if (AppUtil.isConnectingToInternet(mContext)) {
                boolean userVerify = eventDetails.optBoolean("verify_user");
                if (userVerify) {
                    if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                        String ifInterestedLink = eventDetails.optString("link");
                        if (ifInterestedLink.isEmpty()) {
                            Toast.makeText(mContext, "Invalid link to join.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        JSONObject requestObj = new JSONObject();
                        try {
                            requestObj.put(RestUtils.TAG_FEED_ID, feedId);
                            AppUtil.sendUserActionEventAPICall(docId, "ifInterestedEvent", requestObj, mContext);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppUtil.openLinkInBrowser(ifInterestedLink, mContext);
                    } else if (AppUtil.getUserVerifiedStatus() == 1) {
                        AccessErrorPrompt(mContext, mContext.getString(R.string.mca_not_uploaded));
                    } else if (AppUtil.getUserVerifiedStatus() == 2) {
                        AccessErrorPrompt(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified));
                    }

                } else {
                    String ifInterestedLink = eventDetails.optString("link");
                    if (ifInterestedLink.isEmpty()) {
                        Toast.makeText(mContext, "Invalid link to join.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    JSONObject requestObj = new JSONObject();
                    try {
                        requestObj.put(RestUtils.TAG_FEED_ID, feedId);
                        AppUtil.sendUserActionEventAPICall(docId, "ifInterestedEvent", requestObj, mContext);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.openLinkInBrowser(ifInterestedLink, mContext);
                }


            }

        });
    }

    public static boolean isFileSizeLessThan5Mb(File file) {
        // Get length of file in bytes
        long fileSizeInBytes = file.length();
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        if (fileSizeInMB > 5) {
            return false;
        }
        return true;
    }

    public static boolean isYoutubeUrl(String youTubeURl) {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern)) {
            success = true;
        } else {
            // Not Valid youtube URL
            success = false;
        }
        return success;
    }

    public static String getVideoIdFromYoutubeUrl(String youtubeUrl) {
       /*
           Possibile Youtube urls.
           http://www.youtube.com/watch?v=WK0YhfKqdaI
           http://www.youtube.com/embed/WK0YhfKqdaI
           http://www.youtube.com/v/WK0YhfKqdaI
           http://www.youtube-nocookie.com/v/WK0YhfKqdaI?version=3&hl=en_US&rel=0
           http://www.youtube.com/watch?v=WK0YhfKqdaI
           http://www.youtube.com/watch?feature=player_embedded&v=WK0YhfKqdaI
           http://www.youtube.com/e/WK0YhfKqdaI
           http://youtu.be/WK0YhfKqdaI
        */
        //String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        //url is youtube url for which you want to extract the id.
        Matcher matcher = compiledPattern.matcher(youtubeUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


    public static boolean isWhitecoatsUrl(String inputUrl) {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((dev.|qa.|stage.)?whitecoats.com|wcts.app)?\\/(.+)?";
        if (!inputUrl.isEmpty() && inputUrl.matches(pattern)) {
            success = true;
        } else {
            // Not Valid youtube URL
            success = false;
        }
        return success;
    }

    //log ad event
    public static void logAdEvent(RealmAdSlotInfo adSlotInfo, String eventName, int docID, Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ad_slot_id", adSlotInfo.getSlot_id());
            jsonObject.put("source_slot_id", adSlotInfo.getSource_slot_id());
            jsonObject.put(RestUtils.TAG_TIMESTAMP, getTimeWithTimeZone());
        } catch (JSONException | IllegalStateException e) {
            e.printStackTrace();
        }
        sendUserActionEventAPICall(docID, eventName, jsonObject, context);
    }

    public static void logAdEvent(AdsDefinition adSlotInfo, String eventName, int docID, Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ad_slot_id", adSlotInfo.getSlotId());
            jsonObject.put("source_slot_id", adSlotInfo.getSourceSlotId());
            jsonObject.put(RestUtils.TAG_TIMESTAMP, getTimeWithTimeZone());
        } catch (JSONException | IllegalStateException e) {
            e.printStackTrace();
        }
        sendUserActionEventAPICall(docID, eventName, jsonObject, context);
    }

    public static File getExternalStoragePathFile(Context mContext, String path) {
        File file = new File(mContext.getExternalFilesDir(null).getAbsolutePath(), path);
        return file;
    }

    public static void createDirIfNotExists(Context context, String subDir) {
        //myDirectory = new File(Environment.getExternalStorageDirectory(), ".Whitecoats");
        File myDirectory = AppUtil.getExternalStoragePathFile(context, ".Whitecoats");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        File folder = new File(myDirectory + subDir);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

  /*  public static String getFileExtension(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        return TextUtils.isEmpty(extension) ? "" : extension;
    }*/

    public static String getFileExtension(Context mContext, Uri uri) {
        String extension;
        ContentResolver contentResolver = mContext.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        return extension;
    }

    public static boolean areNotificationsEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (!manager.areNotificationsEnabled()) {
                return false;
            }
            List<NotificationChannel> channels = manager.getNotificationChannels();
            for (NotificationChannel channel : channels) {
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    return false;
                }
            }
            return true;
        } else {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
    }

}





