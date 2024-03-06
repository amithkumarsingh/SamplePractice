package com.vam.whitecoats.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.vam.whitecoats.R;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swathim on 09-10-2015.
 */
public class CreateGroupOrAddAnotherOrUpdateAsync extends AsyncTask<String, String, String> {

    private Context mContext;
    private String response;
    private OnTaskCompleted listener;
    private ProgressDialogFragement progress;
    private ArrayList<Integer> opponentIdsarray;
    private List<String> spltyarray;
    private List<String> groupIdsArray;
    private String url, tag;
    private HttpClient client;


    public CreateGroupOrAddAnotherOrUpdateAsync(Context mContext, ArrayList<Integer> ids, String url, String tag) {
        this.mContext = mContext;
        this.opponentIdsarray = ids;
        listener = (OnTaskCompleted) mContext;
        this.url = url;
        this.tag = tag;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);

    }

    public CreateGroupOrAddAnotherOrUpdateAsync(Context mContext, ArrayList<Integer> ids, List<String> splty,List<String> groupIdsArray, String url, String tag) {
        this.mContext = mContext;
        this.opponentIdsarray = ids;
        this.spltyarray = splty;
        this.groupIdsArray = groupIdsArray;
        listener = (OnTaskCompleted) mContext;
        this.url = url;
        this.tag = tag;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);

    }

    public CreateGroupOrAddAnotherOrUpdateAsync(Context mContext, String url, String tag) {
        this.mContext = mContext;
        listener = (OnTaskCompleted) mContext;
        this.url = url;
        this.tag = tag;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);

    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Bitmap myBitmap = null;
            ByteArrayOutputStream baos = null;
            client = new HttpClient(mContext,url);
            if (tag.equals("create") || tag.equals("update")) {
                client.connectForMultipartCookie(mContext);
            } else if (tag.equals("add_another_mem") || tag.equals("case_add_another_member") || tag.equals("Cadd_another_member")) {
                client.connectForSinglepart(mContext);
            }
            JSONObject object = new JSONObject();
            if (tag.equals("create") || tag.equals("update") || tag.equals("add_another_mem")) {
                object.put(RestUtils.TAG_GROUP_TITLE, params[0]);
            } else if (tag.equals("case_add_another_member")) {
                object.put(RestUtils.TAG_CASE_ROOM_TITLE, params[0]);
            } else if (tag.equals("Cadd_another_member")) {
                object.put("caseroom_title", params[0]);
            }
            if (tag.equals("create") || tag.equals("add_another_mem") || tag.equals("case_add_another_member") || tag.equals("Cadd_another_member")) {
                object.put(RestUtils.TAG_INVITER_DOC_ID, Integer.parseInt(params[1]));
                object.put(RestUtils.TAG_INVITER_QB_ID, Integer.parseInt(params[2]));
                JSONArray jarray = new JSONArray();
                for (int i = 0; i < opponentIdsarray.size(); i++) {
                    jarray.put(opponentIdsarray.get(i));
                }
                object.put(RestUtils.TAG_ADD_INVITEES_LIST, jarray);
            } else if (tag.equals("update")) {
                object.put(RestUtils.TAG_GROUP_ID, params[1]);
                object.put(RestUtils.TAG_GROUP_PIC_OPER, params[2]);
            }
            if (tag.equals("create") || tag.equals("update")) {
                String reqData = object.toString();
                client.addFormPart(RestUtils.TAG_REQ_DATA, reqData);
                 /*   if(tag.equals("update")){
                        client.addpara(RestUtils.TAG_REQ_DATA,reqData);
                    }else {
                        client.addFormPart(RestUtils.TAG_REQ_DATA, reqData);
                    }*/
                Log.d("group_reqData", reqData);
                if (!TextUtils.isEmpty(params[3])) {
                    myBitmap = BitmapFactory.decodeFile(params[3].toString());
                    baos = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
                    client.addFilePart(RestUtils.TAG_FILE, "group_pic.png", baos.toByteArray());
                }
                client.finishMultipart();
            } else if (tag.equals("add_another_mem")) {
                object.put(RestUtils.TAG_GROUP_ID, params[3]);
                object.put(RestUtils.TAG_GROUP_CREATION_TIME, Long.valueOf(params[4]));
                String reqData = object.toString();
                client.addpara(RestUtils.TAG_REQ_DATA, reqData);
                Log.d("group_reqData", reqData);
            } else if (tag.equals("case_add_another_member")) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < spltyarray.size(); i++) {
                    jsonArray.put(spltyarray.get(i));
                }
                JSONArray group_ids_array = new JSONArray();
                for (int i = 0; i < groupIdsArray.size(); i++) {
                    group_ids_array.put(groupIdsArray.get(i));
                }
                object.put(RestUtils.TAG_CASE_ROOM_SPLTY, jsonArray);
                object.put(RestUtils.TAG_CASE_SUMMARY_ID, params[3]);
                object.put(RestUtils.TAG_GROUP_ID_LIST, group_ids_array);

                String reqData = object.toString();
                client.addpara(RestUtils.TAG_REQ_DATA, reqData);
                Log.d("group_reqData", reqData);
            } else if (tag.equals("Cadd_another_member")) {
                object.put("caseroom_id", params[3]);
                object.put("caseroom_group_created_date", params[4]);
                object.put("caseroom_summary_id", params[5]);
                JSONArray group_ids_array = new JSONArray();
                for (int i = 0; i < groupIdsArray.size(); i++) {
                    group_ids_array.put(groupIdsArray.get(i));
                }
                object.put(RestUtils.TAG_GROUP_ID_LIST, group_ids_array);
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < spltyarray.size(); i++) {
                    jsonArray.put(spltyarray.get(i));
                }
                object.put(RestUtils.TAG_CASE_ROOM_SPLTY, jsonArray);
                String reqData = object.toString();
                client.addpara(RestUtils.TAG_REQ_DATA, reqData);
                Log.d("group_reqData", reqData);
            }
            response = client.getResponse();
            Log.d("group_creation", "responce" + response);
        } catch (SocketTimeoutException e) {
            response = "SocketTimeoutException";
            e.printStackTrace();
            return response;
        } catch (Exception e) {
            response = "Exception";
            e.printStackTrace();
            return response;
        }
        return response;
    }


    @Override
    protected void onPostExecute(String s) {
        /*if(client!=null &&(client.getStatusCode()== HttpStatus.SC_BAD_GATEWAY || client.getStatusCode()==HttpStatus.SC_SERVICE_UNAVAILABLE)){
            JSONObject errorObj=new JSONObject();
            try {
                errorObj.put(RestUtils.TAG_STATUS,RestUtils.TAG_ERROR);
                errorObj.put(RestUtils.TAG_ERROR_CODE,client.getStatusCode());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listener.onTaskCompleted(errorObj.toString());
        }else {
            listener.onTaskCompleted(response);
        }*/
    }
}
