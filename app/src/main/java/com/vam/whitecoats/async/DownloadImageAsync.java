package com.vam.whitecoats.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vam.whitecoats.parser.HttpClient;

/**
 * Created by swathim on 21-10-2015.
 */
public class DownloadImageAsync extends AsyncTask<String,String,String> {



    private Context mContext;
    private String response;

    public DownloadImageAsync(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient image_client = new HttpClient(mContext,params[0]);
            response = image_client.downloadImage(params[1]);
            Log.v("small_link", "" + params[0]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
