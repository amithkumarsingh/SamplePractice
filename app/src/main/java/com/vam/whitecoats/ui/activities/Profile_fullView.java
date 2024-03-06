package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.soundcloud.android.crop.Crop;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.ProfileUpdatedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.ProfileUpdateCollectionManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleyMultipartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by pardhasaradhid on 7/29/2017.
 */

public class Profile_fullView extends BaseActionBarActivity {
    private Realm realm;
    private RealmManager realmManager;
    private RealmBasicInfo realmBasicInfo;
    MarshMallowPermission marshMallowPermission;
    File folder;
    private File myDirectory;
    private static Uri picUri;
    String selectedImagePath = "";
    ImageView myImage;
    final int CROP_PIC = 22;
    private static final String TAG_STATUS = "status";
    private static final String TAG_SUCCESS = "success";
    private String TAG_SPECIALITY = "specialities";
    public static final String TAG = ProfileViewActivity.class.getSimpleName();
    private Bitmap myBitmap;
    private BasicInfo basicInfo = new BasicInfo();
    private int docId;
    private String profilePicURL;
    private String profilePicPath;
    private Uri mCapturedImageURI = null;
    private boolean customBackButton = false;
    private ActivityResultLauncher<Intent> launchGalleryResults, launchCameraResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilefullview);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        myImage = (ImageView) findViewById(R.id.profilefullview);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            basicInfo = (BasicInfo) getIntent().getSerializableExtra("basicInfo");
            docId = bundle.getInt("selectedDocId", 0);
            profilePicURL = bundle.getString("profilePicUrl", "");
            profilePicPath = bundle.getString("profile_pic_path", "");
        }
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        final TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        mTitleTextView.setVisibility(View.GONE);
        next_button.setText(R.string.actionbar_edit);

        if (docId != 0) {
            if (docId == realmBasicInfo.getDoc_id()) {
                next_button.setVisibility(View.VISIBLE);
            } else {
                next_button.setVisibility(View.GONE);
            }
        } else {
            next_button.setVisibility(View.GONE);
        }


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        marshMallowPermission = new MarshMallowPermission(this);
        /**
         * Check if directory exists
         */

        createDirIfNotExists();
        ProfileViewImage();
        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchCameraResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 1313
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        if (mCapturedImageURI != null) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                            Date now = new Date();
                            final String fileName = "profile" + "_" + formatter.format(now) + ".jpg";
                            File f = new File(folder, fileName);
                            selectedImagePath = f.getAbsolutePath();
                            Crop.of(mCapturedImageURI, Uri.fromFile(f)).asSquare().start(this);
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        if (mCapturedImageURI != null) {
                            getContentResolver().delete(mCapturedImageURI, null, null);
                        }
                    }
                });
        launchGalleryResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 1
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        picUri = data.getData();
                        performCrop();
                    }
                });
        //End
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
    }

    private void createDirIfNotExists() {
        myDirectory = AppUtil.getExternalStoragePathFile(Profile_fullView.this, ".Whitecoats");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        folder = new File(myDirectory + "/Profile_Pic");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

    public void ProfileViewImage() {
        if (docId != 0) {
            if (docId == realmBasicInfo.getDoc_id()) {
                if (!AppUtil.checkWriteExternalPermission(Profile_fullView.this)) {
                    if (realmBasicInfo.getPic_url() != null && !realmBasicInfo.getPic_url().isEmpty()) {
                        AppUtil.invalidateAndLoadImage(getApplicationContext(), realmBasicInfo.getPic_url().trim(), myImage);
                    }
                } else if (realmBasicInfo.getPic_url() != null && !realmBasicInfo.getPic_url().isEmpty()) {
                    AppUtil.invalidateAndLoadImage(getApplicationContext(), realmBasicInfo.getPic_url().trim(), myImage);
                } else if (profilePicPath != null && !profilePicPath.isEmpty()) {
                    File imgFile = new File(profilePicPath);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        if (myBitmap != null) {
                            myImage.setImageBitmap(myBitmap);
                        }
                    }
                }

            } else {
                if (profilePicURL != null && !profilePicURL.isEmpty()) {
                    AppUtil.loadImageUsingGlide(Profile_fullView.this, profilePicURL.trim(), myImage, R.drawable.default_profilepic);

                } else {
                    myImage.setImageResource(R.drawable.default_profilepic);
                }
            }
        }

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Remove"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_fullView.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (!marshMallowPermission.requestPermissionForCamera(false)) {
                        if (AppConstants.neverAskAgain_Camera) {
                            alertDialog_Message();
                        }
                    } else {
                        cameraClick();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    if (!marshMallowPermission.requestPermissionForStorage(false)) {
                        if (AppConstants.neverAskAgain_Library) {
                            alertDialog_Message1();
                        }
                    } else {
                        chooseFromLibrary();
                    }
                } else if (items[item].equals("Remove")) {
                    picUri = null;
                    selectedImagePath = "";
                    myImage.setImageResource(R.drawable.default_profilepic);
                    updateProfilePic(myBitmap);
                }
            }
        });
        builder.show();
    }

    private void cameraClick() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            String fileName = "profile" + formatter.format(now) + ".jpg";
            //String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            launchCameraResults.launch(takePictureIntent);
        }
    }

    private void chooseFromLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launchGalleryResults.launch(Intent.createChooser(intent, "Select File"));
    }

    public void alertDialog_Message() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_fullView.this);
        builder.setCancelable(false);
        builder.setMessage(AppUtil.alert_CameraPermissionDeny_Message());
//        builder.setMessage(getString(R.string.camera_deny_message));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    public void alertDialog_Message1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_fullView.this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.storage_deny_message));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("UserProfileImageBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getPath(Uri uri, Activity activity) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CROP_PIC) {
                try {
                    picUri = data.getData();
                    selectedImagePath = getPath(picUri, Profile_fullView.this);
                    String s1 = data.getDataString();
                    if (selectedImagePath == null && s1 != null) {
                        selectedImagePath = s1.replaceAll("file://", "");
                    }
                    if(selectedImagePath!=null){
                        File imgFiles = new File(selectedImagePath);
                        if (imgFiles.exists()) {
                            myBitmap = BitmapFactory.decodeFile(imgFiles.getAbsolutePath());
                            myImage.setImageBitmap(myBitmap);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            }
        }
    }

    private void updateProfilePic(final Bitmap myBitmap) {
        JSONObject object = new JSONObject();
        String subSpeciality = realmBasicInfo.getSubSpeciality();
        if (subSpeciality == null)
            subSpeciality = "";
        try {
            object.put(RestUtils.TAG_IS_UPDATE, true);
            object.put(RestUtils.TAG_USER_ID, docId);
            object.put(RestUtils.TAG_USER_FIRST_NAME, realmBasicInfo.getFname());
            object.put(RestUtils.TAG_USER_LAST_NAME, realmBasicInfo.getLname());
            object.put(RestUtils.TAG_USER_SALUTAION, realmBasicInfo.getUser_salutation());
            object.put("experience", realmBasicInfo.getOverAllExperience());
            object.put(RestUtils.TAG_USER_TYPE_ID, 1);
            object.put(RestUtils.TAG_SUB_SPLTY, realmBasicInfo.getSubSpeciality());
            object.put(RestUtils.KEY_SPECIALITIES, new JSONArray().put(realmBasicInfo.getSplty()));
            object.put(RestUtils.KEY_SPECIALISTS, new JSONArray().put(realmBasicInfo.getSplty()));

            showProgress();
            new VolleyMultipartStringRequest(Profile_fullView.this, Request.Method.POST, RestApiConstants.EDIT_PERSONAL_INFO, object.toString(), "BASIC_PROFILE_ACIVITY", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    hideProgress();
                    if (successResponse != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(successResponse);
                            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                if (jsonObject.has(RestUtils.TAG_DATA)) {
                                    JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                                    if (data.has(RestUtils.TAG_PROFILE_PIC_NAME)) {
                                        basicInfo.setPic_name(data.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                    }
                                    basicInfo.setUserSalutation(data.optString(RestUtils.TAG_USER_SALUTAION));
                                    basicInfo.setUserType(data.optInt(RestUtils.TAG_USER_TYPE_ID));
                                    basicInfo.setFname(data.optString(RestUtils.TAG_USER_FIRST_NAME));
                                    basicInfo.setLname(data.optString(RestUtils.TAG_USER_LAST_NAME));
                                    basicInfo.setSubSpeciality(data.optString(RestUtils.TAG_SUB_SPLTY));
                                    basicInfo.setPic_url(data.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    if (data.has(TAG_SPECIALITY)) {
                                        basicInfo.setSplty(data.getJSONArray(TAG_SPECIALITY).optString(0));
                                    }
                                } else {
                                    basicInfo.setPic_name("");
                                }
                                basicInfo.setProfile_pic_path(selectedImagePath);
                                realmManager.updateBasicInfo(realm, basicInfo);
                                Toast.makeText(Profile_fullView.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                BasicProfileActivity.updateProfilePicOnResume = true;
                                BasicProfileActivity.selectedImagePath = selectedImagePath;
                                for (ProfileUpdatedListener listener : ProfileUpdateCollectionManager.getRegisterListeners()) {
                                    listener.onProfileUpdate(basicInfo);
                                }
                                if (myBitmap != null) {
                                    myImage.setImageBitmap(myBitmap);
                                } else {
                                    myImage.setImageResource(R.drawable.default_profilepic);
                                }
                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                    ShowServerErrorSimpleDialog("Error", getResources().getString(R.string.session_timedout));
                                } else {
                                    String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                                    if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                        errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    }
                                    Toast.makeText(Profile_fullView.this, errorMsg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    hideProgress();
                    displayErrorScreen(errorResponse);

                }
            }).sendMultipartRequest(myBitmap, "file", "profilepic.png");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(String s) {
        if (s != null) {
            if (s.equals("SocketTimeoutException") || s.equals("Exception")) {
                hideProgress();
                displayErrorScreen(s);
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString(TAG_STATUS).equals(TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {
                            JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            if (data.has(TAG_SPECIALITY)) {
                                JSONArray spltyjArray = data.getJSONArray(TAG_SPECIALITY);
                                for (int i = 0; i < spltyjArray.length(); i++) {
                                }
                            } else {
                                Toast.makeText(Profile_fullView.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_LONG).show();
                                Intent in = new Intent();
                                setResult(Activity.RESULT_OK, in);
                                finish();
                            }
                        } else {
                            hideProgress();
                            Intent in = new Intent(Profile_fullView.this, ProfileViewActivity.class);
                            startActivity(in);
                        }
                    } else {
                        hideProgress();
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                ShowServerErrorSimpleDialog("Error", getResources().getString(R.string.session_timedout));
                            } else {
                                String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                                if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(Profile_fullView.this, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    hideProgress();
                }
            }
            hideProgress();
        }
    }

    private void handleCrop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri fileUri = Crop.getOutput(data);
            if (AppUtil.loadImageFromPath(fileUri.getPath()) != null) {
                try {
                    Bitmap imgBitmap = AppUtil.rotateImageIfRequired(AppUtil.loadImageFromPath(fileUri.getPath()), fileUri, Profile_fullView.this);
                    //Uri uri = AppUtil.getImageUri(Profile_fullView.this, imgBitmap);
                    //selectedImagePath=getPath(uri, Profile_fullView.this);
                    /*
                     * compress to specified percentage
                     */
                    imgBitmap = AppUtil.sampleResize(imgBitmap, 1536, 1152);
                    FileOutputStream out = new FileOutputStream(selectedImagePath);
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
                    updateProfilePic(imgBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Image not supported,choose another image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(data).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void performCrop() {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            final String fileName = "profile" + "_" + formatter.format(now) + ".jpg";
            File f = new File(folder, fileName);
            selectedImagePath = f.getAbsolutePath();
            Crop.of(picUri, Uri.fromFile(f)).asSquare().start(this);

        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(this, getResources().getString(R.string.device_not_support_crop), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("UserProfileImageDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.CAMERA_PERMISSION_REQUEST_CODE:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                boolean camera = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                    perms.put(Manifest.permission.READ_MEDIA_IMAGES, PackageManager.PERMISSION_GRANTED);
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    boolean media = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                        createDirIfNotExists();
                        cameraClick();
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                        if (!camera && !media) {
                            AppConstants.neverAskAgain_Camera = true;
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !media) {
                        AppConstants.neverAskAgain_Library = true;
                    }
                    if (perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED && !camera) {
                        AppConstants.neverAskAgain_Camera = true;
                    }
                }else {
                    perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    boolean media = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        createDirIfNotExists();
                        cameraClick();
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        if (!camera && !media) {
                            AppConstants.neverAskAgain_Camera = true;
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !media) {
                        AppConstants.neverAskAgain_Library = true;
                    }
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && !camera) {
                        AppConstants.neverAskAgain_Camera = true;
                    }
                }

                break;
            case PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createDirIfNotExists();
                    chooseFromLibrary();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
