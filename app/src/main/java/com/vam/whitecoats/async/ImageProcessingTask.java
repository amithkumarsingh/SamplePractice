package com.vam.whitecoats.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.vam.whitecoats.constants.DIRECTORY;
import com.vam.whitecoats.ui.activities.CommentsActivity;
import com.vam.whitecoats.ui.activities.CommentsPreviewActivity;
import com.vam.whitecoats.ui.activities.CreatePostActivity;
import com.vam.whitecoats.ui.activities.CustomPhotoGalleryActivity;
import com.vam.whitecoats.ui.interfaces.OnCompressCompletedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by satyasarathim on 28-01-2016.
 */
public class ImageProcessingTask extends AsyncTask<String, Integer, Integer> {
    private final static String TAG = ImageProcessingTask.class.getSimpleName();
    private ArrayList<String> imagesList = new ArrayList<>();
    protected ProgressDialog progressDialog;
    String[] imagesArray;
    CustomPhotoGalleryActivity customPhotoGalleryActivity;
    CreatePostActivity postScreen;
    CommentsActivity commentScreen;
    CommentsPreviewActivity previewScreen;
    Context context;
    DIRECTORY dirType;
    private final OnCompressCompletedListener listener;


    public ImageProcessingTask(Context context, DIRECTORY dirType, String[] imagesArray) {
        this.context = context;
        this.dirType = dirType;
        this.imagesArray = imagesArray;
        listener = (OnCompressCompletedListener) context;
    }

    public ImageProcessingTask(Context context, DIRECTORY dirType, ArrayList<String> imagesArray) {
        this.context = context;
        this.dirType = dirType;
        this.imagesList = imagesArray;
        listener = (OnCompressCompletedListener) context;
    }

    @Override
    protected void onPreExecute() {
        if (context instanceof CustomPhotoGalleryActivity) {
            customPhotoGalleryActivity = (CustomPhotoGalleryActivity) context;
            progressDialog = new ProgressDialog(customPhotoGalleryActivity);
        } /*else if (context instanceof CreateCaseroomActivity) {
            createCaseroomActivity = (CreateCaseroomActivity) context;
            progressDialog = new ProgressDialog(createCaseroomActivity);
        }*/ /*else if (context instanceof CaseRoomAttachmentsActivity) {
            caseRoomAttachmentsActivity = (CaseRoomAttachmentsActivity) context;
            progressDialog = new ProgressDialog(caseRoomAttachmentsActivity);
        }*/  else if (context instanceof CreatePostActivity) {
            postScreen = (CreatePostActivity) context;
            progressDialog = new ProgressDialog(postScreen);
        } else if (context instanceof CommentsActivity) {
            commentScreen = (CommentsActivity) context;
            progressDialog = new ProgressDialog(commentScreen);
        } else if (context instanceof CommentsPreviewActivity) {
            previewScreen = (CommentsPreviewActivity) context;
            progressDialog = new ProgressDialog(previewScreen);
        }
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    protected Integer doInBackground(String... paths) {
        Log.i(TAG, "doInBackground ..");
        FileOutputStream out = null;
        try {
            int length = 0;
            if (context instanceof CreatePostActivity || context instanceof CommentsActivity
                    || context instanceof CommentsPreviewActivity) {
                length = imagesList.size();
            } else {
                length = imagesArray.length;
            }

            for (int i = 0; i < length; i++) {
                String filePath = "";
                if (context instanceof CreatePostActivity || context instanceof CommentsActivity
                        || context instanceof CommentsPreviewActivity) {
                    filePath = getRealPathFromURI(imagesList.get(i));
                } else {
                    filePath = getRealPathFromURI(imagesArray[i]);
                }

                Bitmap selected_img = null;
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(filePath);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);
                    Bitmap original = BitmapFactory.decodeFile(filePath, bmOptions);
                    Bitmap bitmap = CreatePostActivity.rotateBitmap(original, orientation);

                    selected_img = AppUtil.sampleResize(bitmap, 1536, 1152);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*
                 *  write the compressed bitmap at the destination directory.
                 */

                try {
                    String subname = filePath.substring(filePath.lastIndexOf('/') + 1);
                    String filename = getFilename(subname);
                    /*
                     * update the array with only image names
                     */
                    if (context instanceof CreatePostActivity || context instanceof CommentsActivity
                            || context instanceof CommentsPreviewActivity) {
                        imagesList.set(i, filename.substring(filename.lastIndexOf('/') + 1));
                    } else {
                        imagesArray[i] = filename.substring(filename.lastIndexOf('/') + 1);
                    }
                    out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
                    if (selected_img != null) {
                        selected_img.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    assert out != null;
                    out.close();
                }

            }
        } catch (OutOfMemoryError | Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        Log.d(TAG, "onPostExecute()");
        progressDialog.dismiss();
        if (context instanceof CreatePostActivity || context instanceof CommentsActivity
                || context instanceof CommentsPreviewActivity) {
            listener.onCompressCompletedWithArrayList(imagesList);
        } else {
            listener.onCompressCompleted(imagesArray);
        }
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public String getFilename(String name) {
        Log.i("TAG", "getFilename() : " + name);
        String path = null;
        String fileName = null;
        boolean flag = false;
        if (dirType.equals(DIRECTORY.CHAT))
            path = ".Whitecoats/Chat_images";
        else if (dirType.equals(DIRECTORY.CASEROOM))
            path = ".Whitecoats/CaseRoom_Pic";
        else if (dirType.equals(DIRECTORY.POST))
            path = ".Whitecoats/Post_images";
        else if (dirType.equals(DIRECTORY.COMMENT))
            path = ".Whitecoats/Comment_images";

        File file = AppUtil.getExternalStoragePathFile(context, path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String directory = file.getAbsolutePath();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        Date now = new Date();
        if (dirType.equals(DIRECTORY.CHAT)) {
            fileName = "chat_attach_" + formatter.format(now) + ".jpg";
            flag = FileHelper.isFilePresent(name, "Chat_images", context);
        } else if (dirType.equals(DIRECTORY.CASEROOM)) {
            fileName = "case_attach_" + formatter.format(now) + ".jpg";
            flag = FileHelper.isFilePresent(name, "CaseRoom_Pic", context);
        } else if (dirType.equals(DIRECTORY.POST)) {
            fileName = "post_attach_" + formatter.format(now) + ".jpg";
            flag = FileHelper.isFilePresent(name, "Post_images", context);
        } else if (dirType.equals(DIRECTORY.COMMENT)) {
            fileName = "comment_attach_" + formatter.format(now) + ".jpg";
            flag = FileHelper.isFilePresent(name, "Comment_images", context);
        }

        if (flag) {
            return directory + "/" + name;
        } else {
            return directory + "/" + fileName;
        }
    }

}