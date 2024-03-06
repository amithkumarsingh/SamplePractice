package com.vam.whitecoats.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.vam.whitecoats.ui.activities.ListAllFilesActivity;
import com.vam.whitecoats.ui.interfaces.OnCopyCompletedListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileCopyingTask extends AsyncTask<String, Integer, Integer> {
    private Context context;
    String fileType;
    ArrayList<FileDetails> selectedFilesList=new ArrayList<>();
    OnCopyCompletedListener callBackListener;
    private ProgressDialog progressDialog;
    String extension=".pdf";
    private ArrayList<FileDetails> copiedPath=new ArrayList<>();

    public FileCopyingTask(Context mContext, String _fileType, ArrayList<FileDetails> filesList, OnCopyCompletedListener listener){
       context=mContext;
       fileType=_fileType;
       selectedFilesList=filesList;
       callBackListener=listener;
    }
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }
    @Override
    protected Integer doInBackground(String... paths) {
        for(int i=0;i<selectedFilesList.size();i++) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
            Date now = new Date();
            if (fileType.equalsIgnoreCase("audio")) {
                extension = ".mp3";
            }
            String fileName = "file_" + formatter.format(now) + extension;
            File directory = AppUtil.getExternalStoragePathFile(context,".Whitecoats/Post_images");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File destinationFile = new File(directory + "/" + fileName);
            FileDetails obj=new FileDetails();
            obj.setFileName(selectedFilesList.get(i).getFileName());
            obj.setFilePath(fileName);
            copiedPath.add(obj);
            try {
                AppUtil.copyFile(new File(selectedFilesList.get(i).getFilePath()),destinationFile);
            } catch (Exception e) {
                copiedPath.remove(obj);
                e.printStackTrace();
            }
        }
        return Integer.valueOf(0);
    }

    @Override
    protected void onPostExecute(Integer result) {
        progressDialog.dismiss();
        callBackListener.onCopyCompleted(copiedPath,fileType);
    }
}
