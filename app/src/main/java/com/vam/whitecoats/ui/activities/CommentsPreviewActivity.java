package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ImageProcessingTask;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.ConstsCore;
import com.vam.whitecoats.constants.DIRECTORY;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.interfaces.OnCompressCompletedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.myinnos.awesomeimagepicker.activities.ImageSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import io.realm.Realm;

public class CommentsPreviewActivity extends BaseActionBarActivity implements  OnCompressCompletedListener {
    public ImageView preview_Image;
    public ImageButton preview_sendbtn, preview_camera,imagebtn_close;
    EditText preview_edit;
    TextView mTitleTextView;
    String imagePath;
    Context context;
    private String commentText;
    String imageView;
    private boolean keyboard_enable;
    private String postTitle;
    public static final String TAG = CommentsPreviewActivity.class.getSimpleName();
    MarshMallowPermission marshMallowPermission;
    private Uri mCapturedImageURI = null;
    String selectedImagePath = "";
    private static final int SELECT_FILE = 1;
    private Bitmap resizedBitmap;
    private int feed_id=0;
    private int channel_id=0;
    private boolean customBackButton;
    private Realm realm;
    private RealmManager realmManager;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchCameraActivityResults;
    private ActivityResultLauncher<Intent> launchLibraryActivityResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewcomments);

        marshMallowPermission = new MarshMallowPermission(this);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_preview, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            keyboard_enable = bundle.getBoolean(RestUtils.TAG_ENABLE_KEYBOARD);
        }
        //Intent i = getIntent();
        imagePath = getIntent().getExtras().getString(ConstantsCustomGallery.INTENT_PREVIEW_IMAGE);
        postTitle = getIntent().getExtras().getString("posttitle");
        commentText = getIntent().getExtras().getString("commentText");
        feed_id=getIntent().getExtras().getInt("feed_id");
        channel_id=getIntent().getExtras().getInt("channel_id");
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);

        upshotEventData(feed_id,channel_id,0,"","","","","",false);


        mTitleTextView.setText(postTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        preview_Image = findViewById(R.id.preview_image);
        preview_camera = findViewById(R.id.preview_cameraBtn);
        preview_sendbtn = findViewById(R.id.preview_sendBtn);
        preview_edit = findViewById(R.id.preview_EditText);

            preview_edit.setText(commentText);
            //Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/.Whitecoats/Comment_images/" + imagePath));
            Uri uri = Uri.fromFile(AppUtil.getExternalStoragePathFile(this,".Whitecoats/Comment_images/" + imagePath));
            preview_Image.setImageURI(uri);

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchCameraActivityResults=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    if (resultCode == RESULT_OK) {
                        App_Application.setCurrentActivity(CommentsPreviewActivity.this);
                            if (mCapturedImageURI != null) {
                                selectedImagePath = getPath(mCapturedImageURI, CommentsPreviewActivity.this);
                                ArrayList<String> imagePathList = new ArrayList<>();
                                imagePathList.add(selectedImagePath);
                                new ImageProcessingTask(CommentsPreviewActivity.this, DIRECTORY.COMMENT, imagePathList).execute();

                        }
                    }
                });
        launchLibraryActivityResults=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == RESULT_OK) {
                        App_Application.setCurrentActivity(CommentsPreviewActivity.this);
                            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);
                            int size = images.size();
                            ArrayList<String> image_paths = new ArrayList<>();
                            String[] convertedStringArray = new String[size];
                            for (int i = 0; i < size; i++) {
                                convertedStringArray[i] = images.get(i).path;
                                image_paths.add(images.get(i).path);
                            }
                            new ImageProcessingTask(CommentsPreviewActivity.this, DIRECTORY.COMMENT, image_paths).execute();
                    }
                });
        //End

        preview_sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagePath==null){
                    return;
                }
                commentText = StringEscapeUtils.escapeJava(preview_edit.getText().toString());
                Intent intent = new Intent(CommentsPreviewActivity.this, CommentsActivity.class);
                intent.putExtra("imagepath",imagePath);
                intent.putExtra("commentText",commentText);
                setResult(18,intent);
                finish();

            }
        });



        if (imagePath.length() > 0) {
            preview_sendbtn.setImageResource(R.drawable.ic_send_active);
        } else {
            preview_sendbtn.setImageResource(R.drawable.ic_send_disabled);
        }
        if (keyboard_enable) {
            preview_edit.setFocusable(true);
        } else {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
        }

        preview_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() >= 0) {
                    preview_sendbtn.setImageResource(R.drawable.ic_send_active);
                } else {
                    preview_sendbtn.setImageResource(R.drawable.ic_send_disabled);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        preview_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //camera click
                selectImage();
            }
        });

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

    public void selectImage() {
        Log.i(TAG, "selectImage()");
        final CharSequence[] items = {getString(R.string.label_take_photo), getString(R.string.label_choose_library)};
        AlertDialog.Builder builder = new AlertDialog.Builder(CommentsPreviewActivity.this);
        builder.setTitle(getString(R.string.label_add_attachment));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    if (!marshMallowPermission.requestPermissionForCamera(false)) {
                        if (AppConstants.neverAskAgain_Camera) {
                            alertMessage(getString(R.string.label_camera));
                        }
                    } else {
                        cameraClick();
                    }
                } else if (item == 1) {
                    if (!marshMallowPermission.requestPermissionForStorage(false)) {
                        if (AppConstants.neverAskAgain_Library) {
                            alertMessage(getString(R.string.label_storage));
                        }
                    } else {
                        chooseFromLibrary();
                    }
                }
            }
        });
        builder.show();
    }
    private void cameraClick() {
        Log.i(TAG, "cameraClick()");
        File f = null;
        if (ConstsCore.COMMENT_ATTACHMENT_COUNT < 1) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                Date now = new Date();
                String fileName = "post_attach" + "_" + formatter.format(now) + ".jpg";
                //String fileName = "temp.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                mCapturedImageURI = getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                takePictureIntent
                        .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                launchCameraActivityResults.launch(takePictureIntent);
            }
            /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
            Date now = new Date();
            final String fileName = "post_attach" + "_" + formatter.format(now) + ".jpg";
            f = new File(folder, fileName);
            picUri = Uri.fromFile(f);
            selectedImagePath = f.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            startActivityForResult(intent, CAMERA_PIC_REQUEST);*/
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    String.format(getString(R.string.attachment_limit_exceeded), 1),
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }
    }

    private void chooseFromLibrary() {
        Log.i(TAG, "chooseFromLibrary()");
        Intent intent = new Intent(CommentsPreviewActivity.this, ImageSelectActivity.class);
        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1);
        //intent.putExtra(ConstantsCustomGallery.EXTRA_LIMIT_COUNT, ConstsCore.COMMENT_ATTACHMENT_COUNT);
        launchLibraryActivityResults.launch(intent);
    }
    public void alertMessage(String permissionType) {
        Log.i(TAG, "alertMessage()");
        AlertDialog.Builder builder = new AlertDialog.Builder(CommentsPreviewActivity.this);
        builder.setCancelable(false);
        if (permissionType.equalsIgnoreCase(getString(R.string.label_camera))) {
            builder.setMessage(AppUtil.alert_CameraPermissionDeny_Message());
        } else if (permissionType.equalsIgnoreCase(getString(R.string.label_storage))) {
            builder.setMessage(AppUtil.alert_StoragePermissionDeny_Message());
        }
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        String commentText = preview_edit.getText().toString();
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                jsonObject.put("FeedID",feed_id);
                jsonObject.put("ChannelID", channel_id);
                AppUtil.logUserUpShotEvent("FeedCommentsImageDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(commentText)) {

            builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage("Your comment will be discarded.");
            builder.setPositiveButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            customBackButton=true;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                jsonObject.put("FeedID",String.valueOf(feed_id));
                jsonObject.put("ChannelID", String.valueOf(channel_id));
                AppUtil.logUserUpShotEvent("FeedCommentsImageBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, getString(R.string._onActivityResult));
        if (resultCode == RESULT_OK) {
            App_Application.setCurrentActivity(CommentsPreviewActivity.this);
            if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri, CommentsPreviewActivity.this);
                resizedBitmap = AppUtil.bitmapCompression(selectedImagePath);
            }
        }

    }

    public String getPath(Uri uri, Activity activity) {
        Log.i(TAG, "getPath()");
        try {
            String[] projection = {MediaStore.MediaColumns.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCompressCompleted(String[] filePaths) {

    }

    @Override
    public void onCompressCompletedWithArrayList(ArrayList<String> filePaths) {
        Log.i(TAG, "onCompressCompletedWithArrayList(ArrayList<String> filePaths)");
        //Intent intent = new Intent(CommentsPreviewActivity.this, CommentsActivity.class);
        //intent.putExtra(ConstantsCustomGallery.INTENT_PREVIEW_IMAGE, filePaths.get(0));
        //startActivityForResult(intent, 15);
        if(filePaths!=null && filePaths.size()>0) {
            imagePath = filePaths.get(0);
            //Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/.Whitecoats/Comment_images/" + filePaths.get(0)));
            Uri uri = Uri.fromFile(AppUtil.getExternalStoragePathFile(this,".Whitecoats/Comment_images/" + filePaths.get(0)));
            preview_Image.setImageURI(uri);
        }
    }
}
