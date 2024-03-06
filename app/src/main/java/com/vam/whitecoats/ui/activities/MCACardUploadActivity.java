package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AWSKeys;
import com.vam.whitecoats.core.models.AttachmentInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AWSHelperClass;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AwsAndGoogleKeysServiceClass;
import com.vam.whitecoats.utils.CopyFileToAppDirExecutor;
import com.vam.whitecoats.utils.DESedeEncryption;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class MCACardUploadActivity extends BaseActionBarActivity {
    private static final String TAG = MCACardUploadActivity.class.getSimpleName();
    private TextView verify_later, learnmore_text;
    private Button attach_action, submit_action;

    MarshMallowPermission marshMallowPermission;
    String fileName = "";
    private File myDirectory;
    File folder;
    String selectedImagePath = "";
    private ImageView mci_image, delete_img;
    private Bundle bundle;
    private String navigation;
    int doctorId;
    private RealmManager realmManager;
    private Realm realm;
    private Bitmap resizedBitmap;
    private boolean mandatory_verify;
    boolean neverAskAgain_Camera;
    boolean neverAskAgain_Library;
    private int login_doc_id = 0;
    private String emailId;
    private Uri mCapturedImageURI = null;
    AWSKeys awsKeys = null;
    private String firstName_value, lastName_value;
    private RealmBasicInfo basicInfo;

    private TextView pdfFileName;
    private LinearLayout ll_parent_Pdf_view;
    private RelativeLayout overlayLayout;
    private AttachmentInfo attachmentInfoDupli;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchCameraActivityResults, launchGalleryActivityResults, launchPDFActivityResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen_three);
        verify_later = (TextView) findViewById(R.id.verify_laterText);
        attach_action = (Button) findViewById(R.id.attach_button);
        submit_action = (Button) findViewById(R.id.submit_button);
        learnmore_text = (TextView) findViewById(R.id.learnmore_text);
        firstName_value = getIntent().getStringExtra("first_name");
        lastName_value = getIntent().getStringExtra("last_name");
        HashMap<String, Object> data = new HashMap<>();
        data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
        data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
        AppUtil.logUserEventWithHashMap("SignUp_DocInfo3_Impression", login_doc_id, data, MCACardUploadActivity.this);
        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchCameraActivityResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {
                        if (mCapturedImageURI != null) {
                            selectedImagePath = getPath(mCapturedImageURI, MCACardUploadActivity.this);
                        }
                        if (selectedImagePath != null) {
                            resizedBitmap = AppUtil.bitmapCompression(selectedImagePath);
                        }
                        if (resizedBitmap != null) {
                            delete_img.setVisibility(View.VISIBLE);
                            mci_image.setVisibility(View.VISIBLE);
                            ll_parent_Pdf_view.setVisibility(View.GONE);
                            mci_image.setImageBitmap(resizedBitmap);
                            submit_action.setVisibility(View.VISIBLE);
                            attach_action.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(MCACardUploadActivity.this, "Image not supported,please select another image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        launchGalleryActivityResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    Intent data2 = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        Uri selectedImageUri = data2.getData();
                        selectedImagePath = getPath(selectedImageUri, MCACardUploadActivity.this);
                        if (selectedImagePath != null) {
                            resizedBitmap = AppUtil.bitmapCompression(selectedImagePath);
                        }

                        if (resizedBitmap != null) {
                            delete_img.setVisibility(View.VISIBLE);
                            mci_image.setVisibility(View.VISIBLE);
                            ll_parent_Pdf_view.setVisibility(View.GONE);
                            mci_image.setImageBitmap(resizedBitmap);
                            submit_action.setVisibility(View.VISIBLE);
                            attach_action.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(MCACardUploadActivity.this, "Image not supported,please select another image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        launchPDFActivityResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
                    int resultCode = result.getResultCode();
                    Intent data3 = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        if (data3 != null && data3.getData() != null) {
                            new CopyFileToAppDirExecutor(MCACardUploadActivity.this, data3.getData(), ".Whitecoats/MCACardPDFType", attachmentInfo -> {
                                if (attachmentInfo != null) {
                                    attachmentInfo.setAttachmentType("pdf");
                                    attachmentInfoDupli = attachmentInfo;
                                    if (attachmentInfo.getFileAttachmentPath() != null) {
                                        selectedImagePath = String.valueOf(new File(attachmentInfo.getFileAttachmentPath()));
                                        mci_image.setVisibility(View.GONE);
                                        ll_parent_Pdf_view.setVisibility(View.VISIBLE);
                                        if (attachmentInfo.getAttachmentName() != null) {
                                            pdfFileName.setText(attachmentInfo.getAttachmentName());
                                        }
                                        delete_img.setVisibility(View.VISIBLE);
                                        submit_action.setVisibility(View.VISIBLE);
                                        attach_action.setVisibility(View.GONE);
                                    }

                                }
                            }).executeCopyFile();
                        } else {
                            Log.d("MCA_Attachment_PDF", "File uri not found {}");
                        }
                    }
                });
        //End

        learnmore_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                AppUtil.logUserEventWithHashMap("SignUp_LearnMore", login_doc_id, data, MCACardUploadActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(MCACardUploadActivity.this);
                builder.setTitle("Attach a copy of any of the id’s below to verify your profile ");

                builder.setMessage("•\tStudent ID Card" +
                                "\n•\tHospital/Corporate ID Card" +
                                "\n•\tMedical Association ID Card" +
                                "\n•\tVisiting Card" +
                                "\n•\tPrescription" +
                                "\n•\tMCI Issued Registration Certificate" +
                                "\n•\tDegree Certificate")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        });

        mci_image = findViewById(R.id.mci_image);
        delete_img = findViewById(R.id.delete_img);

        /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
        //Start
        pdfFileName = findViewById(R.id.pdfFileName);
        ll_parent_Pdf_view = findViewById(R.id.ll_parent_Pdf_view);
        overlayLayout = findViewById(R.id.overlayLayout);
        //End
        marshMallowPermission = new MarshMallowPermission(this);
        myDirectory = AppUtil.getExternalStoragePathFile(MCACardUploadActivity.this, ".Whitecoats");

        createDirIfNotExists();
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        login_doc_id = realmManager.getDoc_id(realm);
        emailId = realmManager.getDoc_EmailId(realm);
        doctorId = realmManager.getDoc_id(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            navigation = bundle.getString("NAVIGATION");
            mandatory_verify = bundle.getBoolean("mandatory_verify");
        }

        JSONObject requestAwsObj;
        requestAwsObj = getAWSRequest();
//        getAWSCredentials(requestAwsObj);

        new AwsAndGoogleKeysServiceClass(MCACardUploadActivity.this, doctorId, basicInfo.getUserUUID(), new AwsAndGoogleKey() {
            @Override
            public void awsAndGoogleKey(String google_api_key, String aws_key) {
                if (aws_key != null && !aws_key.isEmpty()) {
                    Log.d("Response", aws_key);
                    JSONObject awskeysObj = null;
                    try {
                        awskeysObj = new JSONObject(aws_key);
                        awsKeys = new AWSKeys();
                        awsKeys.setAWS_ACCESS_KEY(awskeysObj.optString(RestUtils.TAG_ACCESS_KEY));
                        awsKeys.setAWS_SECRET_KEY(awskeysObj.optString(RestUtils.TAG_SECRET_KEY));
                        awsKeys.setAWS_BUCKET(awskeysObj.optString(RestUtils.TAG_BUCKET));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        attach_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                AppUtil.logUserEventWithHashMap("SignUp_AttachNow", login_doc_id, data, MCACardUploadActivity.this);
                selectImage();

            }
        });

        if (mandatory_verify) {
            verify_later.setVisibility(View.GONE);
        }
        verify_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AppConstants.IS_USER_VERIFIED_CONSTANT=1;
                HashMap<String, Object> data = new HashMap<>();
                data.put(RestUtils.TAG_USER_FIRST_NAME, firstName_value);
                data.put(RestUtils.TAG_USER_LAST_NAME, lastName_value);
                AppUtil.logUserEventWithHashMap("SignUp_VerifyLater", login_doc_id, data, MCACardUploadActivity.this);
                MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_IS_USER_VERIFIED, 1);
                if ((navigation != null && navigation.equalsIgnoreCase("fromManadatory")) || isTaskRoot()) {
                    Intent in = new Intent(MCACardUploadActivity.this, DashboardActivity.class);
                    AppUtil.logFlurryEventWithDocIdAndEmailEvent("Verify Later", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
                    startActivity(in);
                }
                finish();
            }
        });

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_registrations, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        mTitleTextView.setText("Verify your profile ");
        next_button.setText("3/3");

        delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mci_image.setVisibility(View.VISIBLE);
                ll_parent_Pdf_view.setVisibility(View.GONE);
                mci_image.setImageResource(R.drawable.ic_mci);
                delete_img.setVisibility(View.GONE);
                attach_action.setVisibility(View.VISIBLE);
                submit_action.setVisibility(View.GONE);
            }
        });

        submit_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    showProgress();
                    //AWS upload
                    new AWSHelperClass(MCACardUploadActivity.this, awsKeys, new File(selectedImagePath), login_doc_id, basicInfo.getUserUUID(), new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String url) {
                            if (url != null && !url.isEmpty()) {
                                JSONObject requestObj = new JSONObject();
                                try {
                                    requestObj.put(RestUtils.TAG_DOC_ID, doctorId);
                                    requestObj.put(RestUtils.ATTACH_ORIGINAL_URL, url);
                                    final String reqData = requestObj.toString();
                                    new VolleySinglePartStringRequest(MCACardUploadActivity.this, Request.Method.POST, RestApiConstants.MCA_UPLOAD_REST, reqData, "MCI_CARD_UPLOAD", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            hideProgress();
                                            if (successResponse != null && !successResponse.isEmpty()) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(successResponse);
                                                    if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                                        //AppConstants.IS_USER_VERIFIED_CONSTANT=2;
                                                        MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_IS_USER_VERIFIED, 2);
                                                        AppUtil.logUserVerificationInfoEvent(2);
                                                        Toast.makeText(MCACardUploadActivity.this, "Thanks for submitting, you’ll be notified when your profile is verified.", Toast.LENGTH_SHORT).show();
                                                        /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
                                                        //Start
                                                        if (attachmentInfoDupli != null) {
                                                            if (attachmentInfoDupli.getAttachmentType().equalsIgnoreCase("pdf")) {
                                                                cleanUpPostPDFFiles();
                                                            }
                                                        }
                                                        //End
                                                        Intent in = new Intent(MCACardUploadActivity.this, DashboardActivity.class);
                                                        startActivity(in);
                                                        finish();
                                                    } else if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                                                        displayErrorScreen(successResponse);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                displayErrorScreen(successResponse);
                                            }
                                            AppUtil.logFlurryEventWithDocIdAndEmailEvent("MCACardUpload", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
                                        }

                                        @Override
                                        public void onErrorResponse(String errorResponse) {
                                            AppUtil.logFlurryEventWithDocIdAndEmailEvent("MCACardUploaderror", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
                                            displayErrorScreen(errorResponse);
                                        }

                                    }).sendSinglePartRequest();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                hideProgress();
                                ShowSimpleDialog(getResources().getString(R.string.unabletosave), getResources().getString(R.string.trylater));
                            }
                        }
                    });
                }
            }
        });


        /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
        //Start
        overlayLayout.setOnClickListener(view -> {
            if (attachmentInfoDupli != null) {
                File file = new File(attachmentInfoDupli.getFileAttachmentPath());
                Intent intent = new Intent(this, PdfViewerActivity.class);
                intent.putExtra(RestUtils.TAG_FEED_ID, "");
                intent.putExtra(RestUtils.CHANNEL_ID, "");
                intent.putExtra(RestUtils.ATTACHMENT_TYPE, attachmentInfoDupli.getAttachmentType());
                intent.putExtra(RestUtils.ATTACHMENT_NAME, attachmentInfoDupli.getAttachmentName());
                intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, file.getAbsolutePath());
                intent.putExtra("loadFromLocal", true);
                startActivity(intent);
            }
        });
        //End


        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
    }

    private JSONObject getAWSRequest() {
        Log.i(TAG, "getAWSRequestcredentials()");
        JSONObject requestObj = null;
        try {
            requestObj = new JSONObject();
            requestObj.put(RestUtils.TAG_DOC_ID, doctorId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj;
    }

    private void getAWSCredentials(final JSONObject request) {
        if (isConnectingToInternet()) {
            String url = RestApiConstants.AWS_KEYS;
            new VolleySinglePartStringRequest(MCACardUploadActivity.this, Request.Method.POST, url, request.toString(), "", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    if (successResponse != null) {
                        if (!successResponse.isEmpty()) {
                            try {
                                DESedeEncryption myEncryptor = DESedeEncryption.getInstance();
                                JSONObject responseJsonObject = new JSONObject(successResponse);
                                if (responseJsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                    String decriptString = myEncryptor.decrypt(responseJsonObject.optString("resource"));
                                    Log.d("Response", decriptString);
                                    JSONObject awskeysObj = new JSONObject(decriptString);
                                    awsKeys = new AWSKeys();
                                    awsKeys.setAWS_ACCESS_KEY(awskeysObj.optString(RestUtils.TAG_ACCESS_KEY));
                                    awsKeys.setAWS_SECRET_KEY(awskeysObj.optString(RestUtils.TAG_SECRET_KEY));
                                    awsKeys.setAWS_BUCKET(awskeysObj.optString(RestUtils.TAG_BUCKET));
                                } else if (responseJsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    String errorMsg = "Unable to fetch credentials";
                                    if (responseJsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                        errorMsg = responseJsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    }
                                    AppUtil.logCustomEventsIntoFabric("FetchAWSInfo", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), "get_aws_keys", DateUtils.getCurrentTimeWithTimeZone(), errorMsg);
                                    if (doctorId != 0) {
                                        AppUtil.logUserActionEvent(doctorId, "FetchAWSInfo", responseJsonObject, AppUtil.convertJsonToHashMap(responseJsonObject), MCACardUploadActivity.this);
                                    } else {
                                        AppUtil.logUserWithEventName(0, "AWSUploadFail", responseJsonObject, MCACardUploadActivity.this);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    if (errorResponse != null && !errorResponse.isEmpty()) {
                        AppUtil.logCustomEventsIntoFabric("FetchAWSInfo", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), "get_aws_keys", DateUtils.getCurrentTimeWithTimeZone(), errorResponse);
                    } else {
                        AppUtil.logCustomEventsIntoFabric("FetchAWSInfo", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), "get_aws_keys", DateUtils.getCurrentTimeWithTimeZone(), "Unable to fetch credentials");
                    }
                    JSONObject errorObj = new JSONObject();
                    try {
                        errorObj.put("errorMsg", "Unable to fetch credentials");
                        if (doctorId != 0) {
                            AppUtil.logUserActionEvent(doctorId, "FetchAWSInfo", errorObj, AppUtil.convertJsonToHashMap(errorObj), MCACardUploadActivity.this);
                        } else {
                            AppUtil.logUserWithEventName(0, "AWSUploadFail", errorObj, MCACardUploadActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }).sendSinglePartRequest();
        }
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void createDirIfNotExists() {
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        folder = new File(myDirectory + "/Profile_Pic");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

    /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
    private void cleanUpPostPDFFiles() {
        File file = AppUtil.getExternalStoragePathFile(MCACardUploadActivity.this, ".Whitecoats/MCACardPDFType");
        if (file.exists()) {
            AppUtil.deleteDirectoryTree(file);
        }
    }

    /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanUpPostPDFFiles();
    }

    public void alertDialog_Message() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MCACardUploadActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MCACardUploadActivity.this);
        builder.setCancelable(false);
        builder.setMessage(AppUtil.alert_StoragePermissionDeny_Message());
//        builder.setMessage(getString(R.string.storage_deny_message));
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
            launchCameraActivityResults.launch(takePictureIntent);
        }
    }

    private void chooseFromLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launchGalleryActivityResults.launch(Intent.createChooser(intent, "Select File"));
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library", "Attach Pdf"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MCACardUploadActivity.this);
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
                    /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
                } else if (items[item].equals("Attach Pdf")) {
                    if (!marshMallowPermission.requestPermissionForStorage(PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_FOR_PDF, false)) {
                        if (AppConstants.neverAskAgain_Library) {
                            AppUtil.alertMessage(MCACardUploadActivity.this, getString(R.string.label_files));
                        }
                    } else {
                        pickPdfFile();

                    }
                }

            }
        });
        builder.show();
    }

    /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
    private void pickPdfFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        launchPDFActivityResults.launch(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.CAMERA_PERMISSION_REQUEST_CODE:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                boolean camera = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
                } else {
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    } else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                }
                break;

            /*ENGG-3275 -- Android : Allow PDF as attachement for MCI*/
            //Start
            case PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_FOR_PDF:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createDirIfNotExists();
                    pickPdfFile();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    } else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                }
                break;
            //end
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DocID", realmManager.getUserUUID(realm));
            AppUtil.logUserUpShotEvent("MCAUploadDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (navigation != null && navigation.equalsIgnoreCase("fromManadatory")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to exit");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}

