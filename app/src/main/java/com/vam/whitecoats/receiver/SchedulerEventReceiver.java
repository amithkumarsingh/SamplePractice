package com.vam.whitecoats.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;

import io.realm.Realm;

/**
 * Created by swathim on 2/20/2015.
 */
public class SchedulerEventReceiver extends BroadcastReceiver {

    private static final String APP_TAG = "com.vam.whitecoats";
    MySharedPref mySharedPref;

    private Realm realm;
    private RealmManager realmManager;
    @Override
    public void onReceive(final Context ctx,final Intent intent) {

        Log.d(APP_TAG, "SchedulerEventReceiver.onReceive() called");
        try {
            mySharedPref=new MySharedPref(ctx);
            realm = Realm.getDefaultInstance();
            realmManager=new RealmManager(ctx);
            Log.d("mySharedPref scheduler",realmManager.getemailstatusBasicInfo(realm).get(0) +" "+ realmManager.getemailstatusBasicInfo(realm).get(1));
            if(!realmManager.getemailstatusBasicInfo(realm).get(0).equals("") && realmManager.getemailstatusBasicInfo(realm).get(1).equals("false")) {
                Intent eventService = new Intent(ctx, SchedulerEventService.class);
                ctx.startService(eventService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(!realm.isClosed())
                realm.close();
        }

    }
}
