package com.vam.whitecoats.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.util.Linkify;

import com.vam.whitecoats.core.models.NotificationInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.utils.AppUtil;

import io.realm.Realm;

public class NotificationItemViewModel extends ViewModel {


    private NotificationInfo notificationInfo;
    String username;
    String speciality;
    String boardingDate;
    String loc;
    String lastloginDate;

    public NotificationItemViewModel(NotificationInfo mNotificationInfo, String name, String spec, String onBoardDate, String location, String loginDate) {
        this.notificationInfo = mNotificationInfo;
        username = name;
        speciality = spec;
        boardingDate = onBoardDate;
        loc = location;
        lastloginDate = loginDate;
    }

    //@Bindable
    public Spannable getTitle() {
        String message = notificationInfo.getTitle();
        if (notificationInfo.getIs_personalized()) {
            message = message.replace("@name", username);
            message = message.replace("@speciality", speciality);
            message = message.replace("@location", loc);
            message = message.replace("@onboardDate", boardingDate);
            message = message.replace("@lastLoginDate", lastloginDate);
        }

        return !TextUtils.isEmpty(message) ? AppUtil.linkifyHtml(message, Linkify.WEB_URLS) : new SpannableString("");
        //return !TextUtils.isEmpty(notificationInfo.getTitle()) ? AppUtil.linkifyHtml(notificationInfo.getTitle(), Linkify.WEB_URLS ): new SpannableString("");
    }

    public NotificationInfo getNotificationInfo() {
        return notificationInfo;
    }

    public String getTimeDifference() {
        return notificationInfo.getTimeStamp() != null ? AppUtil.parseDateIntoDays(notificationInfo.getTimeStamp()) : "";
    }
}
