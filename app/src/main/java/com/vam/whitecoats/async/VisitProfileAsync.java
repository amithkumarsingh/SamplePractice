package com.vam.whitecoats.async;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.ui.activities.VisitProfileActivity;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;

import org.json.JSONObject;

import java.net.SocketTimeoutException;

/**
 * Created by swathim on 31-07-2015.
 */
public class VisitProfileAsync extends AsyncTask<String,String,String> {

    Context mContext;
    String response;
    private OnTaskCompleted listener;
    protected ProgressDialogFragement progress;
    private VisitProfileActivity visitProfileActivity;
    protected AlertDialog.Builder builder;



    private static final String TAG_DOCID         = "doc_id";
    private static final String TAG_OTHER_DOCID   = "other_doc_id";

    public VisitProfileAsync(Context mContext) {
        this.mContext = mContext;
        if(mContext instanceof VisitProfileActivity) {
            visitProfileActivity = (VisitProfileActivity) mContext;
        }
        listener =(OnTaskCompleted)mContext;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
    }

    @Override
    protected void onPreExecute() {
        if(mContext instanceof VisitProfileActivity) {
            visitProfileActivity.showProgress();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            HttpClient client = new HttpClient(mContext,RestApiConstants.VISIT_OTHER_PROFILE);
            client.connectForSinglepart(mContext);
            JSONObject object = new JSONObject();
            object.put(TAG_DOCID, params[0]);
            object.put(TAG_OTHER_DOCID,params[1]);
            String reqData=object.toString();
            Log.d("", "reqData" + reqData);
            client.addpara("reqData", reqData);
            response=client.getResponse();
            Log.d("VisitProfile", "response" + response);
        }catch(SocketTimeoutException e){
            response="SocketTimeoutException";
            return response;
        }catch (Exception e){
            response="Exception";
            return response;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("visit others profile", "response" + s);
        listener.onTaskCompleted(response);
        /*if (response != null) {
            if (response.equals("SocketTimeoutException") || response.toLowerCase().contains("exception")) {
                Log.d("Login Exception", response);
                // changePasswordActivity.hideProgress();
                hideProgress();
                String error = mContext.getResources().getString(R.string.timeoutException);
                showSimpleDialog("Error", error);
            }
        }*/
    }

    public synchronized void hideProgress() {
        if (progress != null && progress.getActivity() != null) {
            progress.dismissAllowingStateLoss();
        }
    }
}
