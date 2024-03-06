package com.vam.whitecoats.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by venuv on 6/22/2016.
 */
public class ImageDownloaderTask extends AsyncTask<String, Void, String> {
    private final WeakReference<ImageView> imageViewReference;
    private final Context mContext;
    private final OnTaskCompleted callBackListener;
    private String response;
    private String tag, name;
    private File myDirectory;

    public ImageDownloaderTask(ImageView imageView, Context context, OnTaskCompleted onTaskCompleted) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        mContext = context;
        callBackListener = onTaskCompleted;
    }


    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient client = new HttpClient(mContext, RestApiConstants.DOWNLOAD_IMAGE_LINK);
            client.connectForSinglepart(mContext);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("file_name", params[0]);
            if (params[1].equals("feed_pic")) {
                jsonObject.put("category", "COMMUNITY_POST_ATTACHMENT");
            } else if (params[1].equals("profile")) {
                jsonObject.put("category", "DOC_PROFILE");
            } else if (params[1].equals("about_pic")) {
                jsonObject.put("category", "COMMUNITY_LOGO");
            }
            tag = params[1];
            name = params[0];
            String reqData = jsonObject.toString();
            Log.d("", "reqData" + reqData);
            client.addpara("reqData", reqData);
            response = client.getResponse();
            Log.d("DownloadImage", "Links" + response);
            return downloadBitmap(response, params[0], tag);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {

        try {
            if (callBackListener != null) {
                callBackListener.onTaskCompleted(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private String downloadBitmap(String response, String pic_name, String tag) throws IOException {
        HttpURLConnection urlConnection = null;
        String original_link = "";
        File file = null;
        FileOutputStream fos = null;
        try {
            JSONObject connectjObject = new JSONObject(response);
            if (connectjObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) && connectjObject.has(RestUtils.TAG_DATA)) {
                JSONObject imagelinksjObject = connectjObject.getJSONObject(RestUtils.TAG_DATA);
                original_link = imagelinksjObject.getString(RestUtils.TAG_ORIGINAL_LINK);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            int size = 1024;
            byte[] buf;
            URL download_url = new URL(original_link);
            if (tag.equals("profile")) {
                myDirectory = AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/Doc_Profiles");
            } else if (tag.equalsIgnoreCase("feed_pic")) {
                myDirectory = AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/feed_pic");
            } else {
                myDirectory = AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/About_Community_Pic");
            }
            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }
            file = new File(myDirectory + "/" + pic_name);
            urlConnection = (HttpURLConnection) download_url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            /** Read bytes to the Buffer until there is nothing more to read(-1). **/
            buf = new byte[size];

            while ((len = bis.read(buf, 0, size)) != -1) {
                baos.write(buf, 0, len);
            }
            buf = baos.toByteArray();

            fos = new FileOutputStream(file);
            fos.write(buf);
            fos.close();
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Log.w("ImageDownloader", "Error downloading image from " + response);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (fos != null) {
                fos.close();
            }
        }
        JSONObject responseJson = new JSONObject();
        try {
            responseJson.put(RestUtils.TAG_ORIGINAL_LINK, original_link);
            responseJson.put(RestUtils.TAG_IMAGE_PATH, file == null ? "" : file.getAbsolutePath());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseJson.toString();
    }
}
