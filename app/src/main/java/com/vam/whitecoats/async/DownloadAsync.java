package com.vam.whitecoats.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.parser.HttpClient;

import org.json.JSONObject;

import java.net.SocketTimeoutException;

import io.realm.Realm;

/**
 * Created by swathim on 23-10-2015.
 */
public class DownloadAsync extends AsyncTask<String,String,String>{

    private Context mContext;
    private String response,imagepath;
    private String doc_id;
    RealmManager realmManager;
    Realm realm;

    public DownloadAsync(Context mContext) {
        this.mContext = mContext;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);

    }


    @Override
    protected String doInBackground(String... params) {
        try{
            HttpClient client=new HttpClient(mContext,RestApiConstants.DOWNLOAD_IMAGE_LINK);
            client.connectForSinglepart(mContext);
            JSONObject jsonObject = new JSONObject();
            if(params[1].equals("group")){
                jsonObject.put("category","GROUP_PROFILE");
            }else if(params[1].equals("profile")) {
                jsonObject.put("category", "DOC_PROFILE");
            }
            jsonObject.put("file_name", params[0]);
//            doc_id = params[1];
            String reqData = jsonObject.toString();
            Log.d("", "reqData" + reqData);
            client.addpara("reqData", reqData);
            response = client.getResponse();
            Log.d("DownloadImage", "Links" + response);
        }catch(SocketTimeoutException e){
            response="SocketTimeoutException";
            e.printStackTrace();
            return response;
        }catch (Exception e){
            response="Exception";
            e.printStackTrace();
            return response;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(!realm.isClosed())
            realm.close();
    }
}
