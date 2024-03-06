package com.vam.whitecoats.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.utils.AppUtil;

import io.realm.Realm;

public class UrbanAirshipChannelCreatedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ON_READY", "AirshipServiceCal_0");
        if (intent.getExtras() != null && intent.getExtras().getString("com.urbanairship.push.EXTRA_CHANNEL_ID") != null) {
            Realm realm = Realm.getDefaultInstance();
            RealmManager realmManager = new RealmManager(context);
            MySharedPref sharedPref = new MySharedPref(context);
            Log.e("ON_READY", "AirshipServiceCal_1");
            sharedPref.savePref(MySharedPref.PREF_URBANAIRSHIP_CHANNEL_ID, intent.getExtras().getString("com.urbanairship.push.EXTRA_CHANNEL_ID"));
            if (realmManager.getDoc_id(realm) != 0) {
                Log.e("ON_READY", "AirshipServiceCal_2 :" + intent.getExtras().getString("com.urbanairship.push.EXTRA_CHANNEL_ID"));
                AppUtil.subscribeDeviceForNotifications(context, realmManager.getDoc_id(realm), "UrbanAirship", true, intent.getExtras().getString("com.urbanairship.push.EXTRA_CHANNEL_ID"));
            }
            context.unregisterReceiver(this);
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }
}
