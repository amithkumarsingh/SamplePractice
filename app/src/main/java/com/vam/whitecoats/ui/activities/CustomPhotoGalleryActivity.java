package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ImageProcessingTask;
import com.vam.whitecoats.constants.ConstsCore;
import com.vam.whitecoats.constants.DIRECTORY;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnCompressCompletedListener;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;

public class CustomPhotoGalleryActivity extends BaseActionBarActivity implements OnCompressCompletedListener {
    public static final String TAG = CustomPhotoGalleryActivity.class.getSimpleName();
    private GridView grdImages;
    private ImageAdapter imageAdapter;
    private String[] arrPath;
    private boolean[] thumbnailsselection;
    private int ids[];
    private int count;
    boolean flag = false;
    private Realm realm;
    private RealmManager realmManager;
    String caseRoomId;
    TextView actionBarTitle, actionBarCancel, actionBarDone;
    ImageProcessingTask imageProcessingTask;
    Cursor imagecursor;

    /**
     * Overrides methods
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_gallery);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        grdImages = (GridView) findViewById(R.id.grdImages);

        /*
         * Set action bar
         */
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_customgallery, null);
        actionBarTitle = (TextView) mCustomView.findViewById(R.id.titleTextvw);
        actionBarCancel = (TextView) mCustomView.findViewById(R.id.cancelButtonLeft);
        actionBarDone = (TextView) mCustomView.findViewById(R.id.doneButtonRight);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        /*
         * Get all the gallery images sorted by DATE TAKEN and the images size mustn't 0 bytes.
         */
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN+" desc";
        final String where = MediaStore.Images.Media.SIZE+" > 0 ";
        imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, where, null, orderBy);
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = imagecursor.getCount();
        this.arrPath = new String[this.count];
        ids = new int[count];
        this.thumbnailsselection = new boolean[this.count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            ids[i] = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            arrPath[i] = imagecursor.getString(dataColumnIndex);
        }
        flag = getIntent().getBooleanExtra("isChat", false);
        caseRoomId = getIntent().getStringExtra("caseroomId");
        imageAdapter = new ImageAdapter();
        grdImages.setAdapter(imageAdapter);


        actionBarDone.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                long attachCount = 0;
                if (flag)
                    attachCount = ConstsCore.ATTACHMENT_COUNT_CHAT;
                else
                    attachCount = ConstsCore.ATTACHMENT_COUNT;

                final int len = thumbnailsselection.length;
                int cnt = 0;
                String selectImages = "";
                for (int i = 0; i < len; i++) {
                    if (thumbnailsselection[i]) {
                        cnt++;
                        selectImages = selectImages + arrPath[i] + ",";
                    }
                }
                if (cnt == 0 && attachCount < 5) {
                    Toast.makeText(getApplicationContext(), "Please select at least one image", Toast.LENGTH_LONG).show();
                } else if (cnt == 0 && attachCount >= 5) {
                    finish();
                } else {
                    /*
                     * Store Images to whitecoats gallery
                     */
                    String[] imagesArray = selectImages.split(",");
                    if (flag)
                        imageProcessingTask = new ImageProcessingTask(CustomPhotoGalleryActivity.this, DIRECTORY.CHAT, imagesArray);
                    else
                        imageProcessingTask = new ImageProcessingTask(CustomPhotoGalleryActivity.this, DIRECTORY.CASEROOM, imagesArray);
                    imageProcessingTask.execute();

                }
            }
        });
        actionBarCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*
         * Get the attach count
         */
        if(!flag) {
            ConstsCore.ATTACHMENT_COUNT =realmManager.getCRAttcount(realm, caseRoomId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (flag) {
            ConstsCore.ATTACHMENT_COUNT_CHAT = 0;
        } else {
            ConstsCore.ATTACHMENT_COUNT = 0;
        }

        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();

    }


    private void setBitmap(final ImageView iv, final int id) {

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return MediaStore.Images.Thumbnails.getThumbnail(getApplicationContext().getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                iv.setImageBitmap(result);
            }
        }.execute();
    }


    @Override
    public void onCompressCompleted(String[] filePaths) {
         /*
          * Send the Intent back to caller Activity
          */
        if (!flag)
            ConstsCore.ATTACHMENT_COUNT = realmManager.getCRAttcount(realm, caseRoomId);
        if (flag) {
            int len = ConstsCore.ATTACHMENT_COUNT_CHAT;
             /*
              * Increase the array size
              */
            ConstsCore.selectedImages = Arrays.copyOf(ConstsCore.selectedImages, len);
            /*
             * calculate the copy index
             */
            int dstIndex = 0;
            for (int i = 0; i < ConstsCore.selectedImages.length; i++) {
                if (ConstsCore.selectedImages[i] != null && !ConstsCore.selectedImages[i].equals("")) {
                    dstIndex++;
                }
            }
//                int dstIndex= ConstsCore.ATTACHMENT_COUNT_CHAT-srcSizeCount;
            Log.d("TAG", "dstIndex:" + dstIndex);
            /*
             * Copy old array into new array
             */
            System.arraycopy(filePaths, 0, ConstsCore.selectedImages, dstIndex, filePaths.length);
            Log.d("TAG", "Local Array :" + filePaths);
            Log.d("TAG", "Global Array :" + ConstsCore.selectedImages);
        }

        Intent i = new Intent();
        i.putExtra("selectedImages", filePaths);
        i.putExtra("mimetype", "image");
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @Override
    public void onCompressCompletedWithArrayList(ArrayList<String> filePaths) {

    }


    /**
     * List adapter
     *
     * @author tasol
     */

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.custom_gallery_item, null);
                holder.imgThumb = (ImageView) convertView.findViewById(R.id.imgThumb);
                holder.chkImage = (CheckBox) convertView.findViewById(R.id.chkImage);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.chkImage.setId(position);
            holder.imgThumb.setId(position);
            holder.chkImage.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    int id = cb.getId();
                    selectThumnail(id, cb, holder.imgThumb);
                }
            });
            holder.imgThumb.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    int id = holder.chkImage.getId();
                    selectThumnail(id, holder.chkImage, holder.imgThumb);
                }
            });
            try {
                setBitmap(holder.imgThumb, ids[position]);
            } catch (Throwable e) {
            }
            holder.chkImage.setChecked(thumbnailsselection[position]);
            holder.id = position;
            return convertView;
        }

        private void selectThumnail(int id, CheckBox cb, ImageView vw) {

            if (thumbnailsselection[id]) {
                vw.setColorFilter(null);
                cb.setChecked(false);
                thumbnailsselection[id] = false;
                if (flag) {
                    ConstsCore.ATTACHMENT_COUNT_CHAT -= 1;
                    setTitleText(ConstsCore.ATTACHMENT_COUNT_CHAT);
                } else {
                    ConstsCore.ATTACHMENT_COUNT -= 1;
                    setTitleText(ConstsCore.ATTACHMENT_COUNT);
                }

            } else {
                long size = 0;
                if (flag)
                    size = ConstsCore.ATTACHMENT_COUNT_CHAT;
                else
                    size = ConstsCore.ATTACHMENT_COUNT;
                if (size < 5) {
                    vw.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
                    cb.setChecked(true);
                    thumbnailsselection[id] = true;
                    if (flag) {
                        ConstsCore.ATTACHMENT_COUNT_CHAT += 1;
                        setTitleText(ConstsCore.ATTACHMENT_COUNT_CHAT);
                    } else {
                        ConstsCore.ATTACHMENT_COUNT += 1;
                        setTitleText(ConstsCore.ATTACHMENT_COUNT);
                    }
                } else {
                    cb.setChecked(false);
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.cant_add_more_items), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void setTitleText(long selectCount) {
        if (selectCount <= 0) {
            actionBarDone.setVisibility(View.INVISIBLE);
            actionBarTitle.setText("Select");
        } else {
            actionBarDone.setVisibility(View.VISIBLE);
            actionBarTitle.setText(selectCount + " Selected");
        }
    }


    /**
     * Inner class
     *
     * @author tasol
     */
    class ViewHolder {
        ImageView imgThumb;
        CheckBox chkImage;
        int id;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,getString(R.string._onDestroy));
        if(!realm.isClosed())
            realm.close();
        imagecursor.close();
    }
}
