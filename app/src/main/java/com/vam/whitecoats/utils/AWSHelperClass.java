package com.vam.whitecoats.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.vam.whitecoats.core.models.AWSKeys;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import io.sentry.protocol.App;


public class AWSHelperClass {


    private String UUID;
    private Context mContext;
    private OnTaskCompleted listener;
    private File mFile;
    AmazonS3Client s3Client = null;
    AWSKeys awsKeys;
    JSONObject errorObj;

    public AWSHelperClass(Context context, AWSKeys keys, File file,int docId,String _UUID,OnTaskCompleted onTaskCompleted) {
        mContext = context;
        listener = onTaskCompleted;
        mFile = file;
        this.UUID=_UUID;
        errorObj=new JSONObject();
        if (keys != null) {
            this.awsKeys = keys;
            if (s3Client == null) {
                ClientConfiguration clientConfig = new ClientConfiguration();
                clientConfig.setProtocol(Protocol.HTTP);
                clientConfig.setMaxErrorRetry(3);
                clientConfig.setSocketTimeout(60000);
                clientConfig.setConnectionTimeout(6000);
                BasicAWSCredentials credentials = new BasicAWSCredentials(awsKeys.getAWS_ACCESS_KEY(), awsKeys.getAWS_SECRET_KEY());
                s3Client = new AmazonS3Client(credentials, Region.getRegion(Regions.AP_SOUTHEAST_1), clientConfig);
            }
            if (s3Client != null && mFile.exists()) {
                ObjectMetadata metadata=new ObjectMetadata();
                String mimetype = AppUtil.getMimeType(Uri.fromFile(mFile).toString());
                metadata.setContentType(mimetype);
                TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(mContext).build();
                TransferObserver observer = transferUtility.upload(awsKeys.getAWS_BUCKET(), mFile.getName(), mFile,metadata, CannedAccessControlList.PublicRead);
                observer.setTransferListener(new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        Log.e("onStateChanged", id + state.name());
                        if (state == TransferState.COMPLETED) {
                            String url = s3Client.getUrl(awsKeys.getAWS_BUCKET(), mFile.getName()).toString();
                            listener.onTaskCompleted(url);
                        }
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        AppUtil.logCustomEventsIntoFabric("AWSUploadFail", UUID==null?"":UUID, "aws_upload", DateUtils.getCurrentTimeWithTimeZone(), ex.getLocalizedMessage());
                        try {
                            errorObj.put("errorMsg",ex.getLocalizedMessage());
                            if(docId!=0) {
                                AppUtil.logUserActionEvent(docId, "AWSUploadFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),context);
                            }else{
                                AppUtil.logUserWithEventName(0,"AWSUploadFail",errorObj,context);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.onTaskCompleted("");
                    }
                });
            } else {
                AppUtil.logCustomEventsIntoFabric("AWSUploadFail", UUID==null?"":UUID, "aws_upload", DateUtils.getCurrentTimeWithTimeZone(), "File not exists");
                try {
                    errorObj.put("errorMsg","File not exists");
                    if(docId!=0) {
                        AppUtil.logUserActionEvent(docId, "AWSUploadFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),context);
                    }
                    else{
                        AppUtil.logUserWithEventName(0,"AWSUploadFail",errorObj,context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onTaskCompleted("");
            }

        } else {
            AppUtil.logCustomEventsIntoFabric("AWSUploadFail", UUID==null?"":UUID, "aws_upload", DateUtils.getCurrentTimeWithTimeZone(), "Empty credentials");
            try {
                errorObj.put("errorMsg","Empty credentials");
                if(docId!=0) {
                    AppUtil.logUserActionEvent(docId, "AWSUploadFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),context);
                }
                else{
                    AppUtil.logUserWithEventName(0,"AWSUploadFail",errorObj,context);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listener.onTaskCompleted("");
        }
    }
}
