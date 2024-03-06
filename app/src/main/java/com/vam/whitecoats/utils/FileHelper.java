package com.vam.whitecoats.utils;

import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;

public class FileHelper {

    private static File filesFolder;
    private static File attfilesFolder;

    private static final String folderName = "/.Whitecoats";
    private static final String attfolderName = "/Attachments";
    private static final String fileType = ".jpg";
    /*public FileHelper() {
        initFilesFolder();
    }
    private void initFilesFolder() {
        filesFolder = new File(Environment.getExternalStorageDirectory() + folderName);
        if (!filesFolder.exists()) {
            filesFolder.mkdirs();
        }
    }*/

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null && !extension.equals("")) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        else if(extension != null && url.lastIndexOf(".") != -1) {
            String ext = url.substring(url.lastIndexOf(".")+1);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(ext);
        }
        return type;
    }



    public static boolean isFilePresent(String fileName, String folderName, Context mContext) {
        String path="";
        if(folderName.equals("profile") && !fileName.equals("")){
             path = mContext.getExternalFilesDir(null).getAbsolutePath()+"/.Whitecoats/Doc_Profiles/"+fileName;
        }else if(folderName.equals("group") && !fileName.equals("")){
            path = mContext.getExternalFilesDir(null).getAbsolutePath()+"/.Whitecoats/Group_Pic/"+fileName;
        }else if(folderName.equals("CaseRoom_Pic") && !fileName.equals("")){
            path = mContext.getExternalFilesDir(null).getAbsolutePath()+"/.Whitecoats/CaseRoom_Pic/"+fileName;
        } else if(folderName.equals("Chat_images") && !fileName.equals("")){
            path = mContext.getExternalFilesDir(null).getAbsolutePath()+"/.Whitecoats/Chat_images/"+fileName;
        }else if(folderName.equals("Profile_Pic") && !fileName.equals("")){
            path = mContext.getExternalFilesDir(null).getAbsolutePath()+"/.Whitecoats/Profile_Pic/"+fileName;
        }else if(folderName.equals("Post_images") && !fileName.equals("")){
            path = mContext.getExternalFilesDir(null).getAbsolutePath()+"/.Whitecoats/Post_images/"+fileName;
        }
        File file = new File(path);
        boolean f = file.exists();

        return f;
    }
    public static File getLatestFilefromDir(String dirPath){
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        int length=files.length;
        File lastModifiedFile = files[0];
        for (int i = 1; i < length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }
}