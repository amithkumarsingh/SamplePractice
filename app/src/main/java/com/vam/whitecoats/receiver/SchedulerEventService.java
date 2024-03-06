package com.vam.whitecoats.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * Created by swathim on 2/20/2015.
 */
public class SchedulerEventService extends Service {

    MySharedPref sharedPref;
    JSONObject object;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        Realm realm = Realm.getDefaultInstance();
        RealmManager realmManager = new RealmManager(SchedulerEventService.this);
        sharedPref = new MySharedPref(SchedulerEventService.this);
        try {
            if (!realmManager.getemailstatusBasicInfo(realm).get(0).equals("") && realmManager.getemailstatusBasicInfo(realm).get(1).equals("false")) {
                MakeServerCall serverCall = new MakeServerCall();
                serverCall.execute(realmManager.getemailstatusBasicInfo(realm).get(0));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }
        Log.d("Info", "event received");
        return super.onStartCommand(intent, flags, startId);
    }

    public class MakeServerCall extends AsyncTask<String, String, String> {
        String response = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient client = new HttpClient(getApplicationContext(),RestApiConstants.EMAIL_VALIDATION);
                client.connectForMultipart(SchedulerEventService.this);
                String email = params[0];//sharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_EMAIL);
                Log.d("Info", "async task after service call" + email);
                object = new JSONObject();
                object.put("email", email);
                String reqData = object.toString();
                client.addFormPart("reqData", reqData);
                client.finishMultipart();
                response = client.getResponse();
                Log.d("SchedulerEventService", "responce" + response);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Realm realm = Realm.getDefaultInstance();
            RealmManager realmManager = new RealmManager(SchedulerEventService.this);
            try {
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {

                        JSONObject successjObject = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                        if (successjObject.getString("activated").equalsIgnoreCase("true")) {
                            realmManager.updateBasicLoginEmailstatus(realm, "true");
                            try {
                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                Intent i = new Intent(SchedulerEventService.this, SchedulerEventReceiver.class);
                                PendingIntent intentExecuted = PendingIntent.getBroadcast(SchedulerEventService.this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
                                alarmManager.cancel(intentExecuted);
                                /*Intent congintent = new Intent(getBaseContext(), CongratsActivity.class);
                                congintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                congintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplication().startActivity(congintent);*/
                            } catch (Exception e) {
                                Log.d("makeServerCall", "e" + e);

                                e.printStackTrace();
                            }

                        } else if (successjObject.getString("activated").equalsIgnoreCase("false")) {
                            realmManager.updateBasicLoginEmailstatus(realm, "false");

                            //sharedPref.getPrefsHelper().savePref(MySharedPref.PREF_ACTVATION_FLAG,"false");
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (!realm.isClosed())
                    realm.close();
            }

        }
    }


}
