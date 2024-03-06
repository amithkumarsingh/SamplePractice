package com.vam.whitecoats.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.ui.activities.TimeLine;
import com.vam.whitecoats.ui.activities.DepartmentMembersActivity;
import com.vam.whitecoats.ui.activities.SearchContactsActivity;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.net.SocketTimeoutException;

import io.realm.Realm;

/**
 * Created by swathim on 6/9/2015.
 */
public class SearchContactsAsync extends AsyncTask<String, String, String> {

    Context mContext;
    String response;
    private OnTaskCompleted listener;
    protected ProgressDialogFragement progress;
    private SearchContactsActivity searchContactsActivity;
    private Realm realm;
    private RealmManager realmManager;
    int communityid;
    int channelid;
    int departmentId;
    private int userid;
    String navigateFrom;

    public SearchContactsAsync(Context context) {
        this.mContext = context;
        searchContactsActivity = (SearchContactsActivity) context;
        listener = (OnTaskCompleted) context;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
        realmManager = new RealmManager(context);
        realm = Realm.getDefaultInstance();
        userid = realmManager.getDoc_id(realm);

    }

    public SearchContactsAsync(Context context, int channelid, int departmentId, String navigateFrom, OnTaskCompleted taskCompletedListener) {
        this.mContext = context;
        searchContactsActivity = (SearchContactsActivity) context;
        listener = (OnTaskCompleted) context;
        this.channelid = channelid;
        this.departmentId = departmentId;
        this.navigateFrom = navigateFrom;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
        realmManager = new RealmManager(context);
        realm = Realm.getDefaultInstance();
        userid = realmManager.getDoc_id(realm);

    }

    public SearchContactsAsync(Context context, int channelid, String navigateFrom, OnTaskCompleted taskCompletedListener) {
        this.mContext = context;
        searchContactsActivity = (SearchContactsActivity) context;
        listener = (OnTaskCompleted) context;
        this.channelid = channelid;
        this.navigateFrom = navigateFrom;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
        realmManager = new RealmManager(context);
        realm = Realm.getDefaultInstance();
        userid = realmManager.getDoc_id(realm);
    }

    @Override
    protected void onPreExecute() {
        searchContactsActivity.showProgress();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            HttpClient client = new HttpClient(mContext,RestApiConstants.SEARCH_CONTACT);
            client.connectForSinglepartWithOutCookie(mContext);
            JSONObject object = new JSONObject();
            if (navigateFrom != null && (navigateFrom.equalsIgnoreCase(DepartmentMembersActivity.TAG) || navigateFrom.equalsIgnoreCase(TimeLine.TAG))) {
                object.put(RestUtils.QUERY, params[0]);
                object.put(RestUtils.PG_NUM, Integer.parseInt(params[1]));
                object.put(RestUtils.TAG_DOC_ID, userid);
                object.put(RestUtils.CHANNEL_ID, channelid);
                if (!navigateFrom.equalsIgnoreCase(TimeLine.TAG))
                    object.put(RestUtils.DEPARTMENT_ID, departmentId);
            } else if (navigateFrom != null && navigateFrom.equalsIgnoreCase(TimeLine.TAG)) {
                object.put(RestUtils.QUERY, params[0]);
                object.put(RestUtils.PG_NUM, Integer.parseInt(params[1]));
                object.put(RestUtils.TAG_DOC_ID, userid);
                object.put(RestUtils.CHANNEL_ID, channelid);
            } else {
                object.put(RestUtils.TAG_DOC_ID, userid);
                object.put(RestUtils.QUERY, params[0]);
                object.put(RestUtils.PG_NUM, Integer.parseInt(params[1]));
            }

            String reqData = object.toString();
            Log.d("SearchContactsAsync", " reqData :" + reqData);
            client.addpara("reqData", reqData);
            response = client.getResponse();
            Log.d("search info", "responce" + response);
        } catch (SocketTimeoutException e) {
            response = "SocketTimeoutException";
            return response;
        } catch (Exception e) {
            response = "Exception";
            return response;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String login_result) {
        try {
            listener.onTaskCompleted(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(!realm.isClosed())
                realm.close();
        }

    }

}
