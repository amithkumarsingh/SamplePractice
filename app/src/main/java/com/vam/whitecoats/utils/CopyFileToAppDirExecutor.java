package com.vam.whitecoats.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.vam.whitecoats.core.models.AttachmentInfo;
import com.vam.whitecoats.ui.interfaces.OnCopyFileSuccessListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.sentry.protocol.App;

public class CopyFileToAppDirExecutor {
    private Uri uri;
    private OnCopyFileSuccessListener callbackListener;
    private String path;
    private Context context;
    private ProgressDialog mProgressDialog;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    private AttachmentInfo copiedAttachementInfo;

    public CopyFileToAppDirExecutor(Context mContext, Uri _uri, String _path, OnCopyFileSuccessListener _callbackListener) {
        this.context = mContext;
        this.path = _path;
        this.callbackListener = _callbackListener;
        this.uri = _uri;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.show();

    }

    public void executeCopyFile() {
        executor.execute(() -> {
            //Background work here
            try {
                copiedAttachementInfo = writeFileContent(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.post(() -> {
                //UI Thread work here
                mProgressDialog.dismiss();
                if (copiedAttachementInfo != null) {
                    Log.d("CREATE_POST", "Cached file path {}" + copiedAttachementInfo.getFileAttachmentPath());
                    callbackListener.onSuccessListener(copiedAttachementInfo);
                } else {
                    Log.d("CREATE_POST", "Writing failed {}");
                }
            });
        });
    }

    private AttachmentInfo writeFileContent(final Uri uri) throws IOException {
        try (InputStream selectedFileInputStream = context.getContentResolver().openInputStream(uri)) {
            if (selectedFileInputStream != null) {
                final File certCacheDir = AppUtil.getExternalStoragePathFile(context, path);
                boolean isCertCacheDirExists = certCacheDir.exists();
                if (!isCertCacheDirExists) {
                    isCertCacheDirExists = certCacheDir.mkdirs();
                }
                if (isCertCacheDirExists) {
                    //String name = uri.getLastPathSegment();
//Here uri is your uri from which you want to get extension
                    //String extension = name.substring(name.lastIndexOf("."));
                    String extension = AppUtil.getFileExtension(context, uri);
                    // String extension= AppUtil.getFileExtension(uri.toString());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
                    Date now = new Date();
                    String fileName = "file_" + formatter.format(now) + "." + extension;
                    String filePath = certCacheDir.getAbsolutePath() + "/" + fileName;

                    try (OutputStream selectedFileOutPutStream = new FileOutputStream(filePath)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = selectedFileInputStream.read(buffer)) > 0) {
                            selectedFileOutPutStream.write(buffer, 0, length);
                        }
                        selectedFileOutPutStream.flush();
                        selectedFileOutPutStream.close();
                        AttachmentInfo obj = new AttachmentInfo();
                        obj.setEditPost(false);
                        obj.setFileAttachmentPath(filePath);
                        obj.setAttachmentName(getFileDisplayName(uri));
                        return obj;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                selectedFileInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private String getFileDisplayName(final Uri uri) {
        String displayName = null;
        try (Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                //Log.i("Display Name {}" + displayName);

            }
        }

        return displayName;
    }
}
