package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.droidninja.imageeditengine.ImageEditor;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ImageProcessingTask;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.ConstsCore;
import com.vam.whitecoats.constants.DIRECTORY;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AWSKeys;
import com.vam.whitecoats.core.models.AttachmentInfo;
import com.vam.whitecoats.core.models.FeedInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.CommunityListAdapter;
import com.vam.whitecoats.ui.adapters.CustomAdapter;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.customviews.NonScrollableGridView;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.ui.interfaces.NavigateScreenListener;
import com.vam.whitecoats.ui.interfaces.OnCompressCompletedListener;
import com.vam.whitecoats.ui.interfaces.OnCopyCompletedListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AWSHelperClass;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AwsAndGoogleKeysServiceClass;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.DESedeEncryption;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.EmojiFilter;
import com.vam.whitecoats.utils.FileCopyingTask;
import com.vam.whitecoats.utils.FileDetails;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ValidationUtils;
import com.vam.whitecoats.utils.VolleyMultipartStringRequest;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import net.alhazmy13.mediapicker.Video.VideoPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import in.myinnos.awesomeimagepicker.activities.ImageSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import io.realm.Realm;

public class CreatePostActivity extends BaseActionBarActivity implements OnCompressCompletedListener,
        NavigateScreenListener {
    private static final String TAG = CreatePostActivity.class.getSimpleName();
    private static final int TITLE_TXT_LIMIT = 500;
    private RadioGroup radioGroup;
    private RadioButton radioBtnUpdate, radioBtnCase, radioBtnAskQuetion;
    TextView postToInputLayout, appliesToInputLayout;
    private RelativeLayout postRelativeLayout;
    private EditText postToEdtTxt, titleEdtTxt, descEdtTxt, specialityEdtTxt;
    private TextView titleCharLimit, errorTxtVw;
    private TextView mTitleTextView, postBtn, helpTxtView, exampleTextView;
    private NonScrollableGridView gridView;
    private Bundle bundle;
    private boolean isAllDeptSelected = true;
    private boolean isAllSpltySelected = true;
    private Realm realm;
    private RealmManager realmManager;
    private String postTitle;
    int docID = 0;
    int userID = 0;
    int channelId = 0;
    int feedId = 0;
    int selectedItemsCount = 0;
    List<HashMap<String, String>> departmentList;
    List<HashMap<String, String>> specialityList;
    private File folder, myDirectory;
    String selectedImagePath = "";
    MarshMallowPermission marshMallowPermission;
    private static final int SELECT_FILE = 1;
    RealmBasicInfo basicInfo;
    private Bitmap resizedBitmap;
    String userSpeciality;
    boolean isSpecialitySaved = false;
    boolean isDepartmentSaved = false;
    private LinearLayout title_update_layout;
    String[] tempStringArray = new String[1];
    ArrayList<AttachmentInfo> completeList = new ArrayList<>();
    private int currentUpload = 0;
    CustomAdapter customAdapter;
    String toolBarTitle;
    JSONArray attachmentDetailsArray = new JSONArray();
    public static boolean isDeletedFromImageFullView = false;
    public static int positionForImageFullView = -1;
    JSONObject channelJsonObj = null;
    private LinearLayout attachIcon;
    private int retryCount = 0;
    private int uploadErrorMsgCount = 0;
    private Uri mCapturedImageURI = null;
    private RelativeLayout radioGroup_lay;
    boolean isEdittextFlag = false;
    private JSONArray relatedSpecialtiesArray;
    private String descriptionStr = "";
    private ActionMode.Callback actionCallBack;
    FeedInfo feedInfo;
    private String navigation = "";
    String attachment_type;
    long attachment_size;
    String video_link = "";
    boolean isAnyAttachmentsAdded = false;
    public static int editedImagePosition = -1;
    AWSKeys awsKeys = null;
    private CallbackCollectionManager callbackManager;
    private int docId = 0;
    private boolean customBackButton = false;
    private int communitySelected;
    private RelativeLayout communityFormRelativeLayout, communityFormLayout;
    private RelativeLayout shareWithLayout;
    private TextView shareWithHeading;
    private RelativeLayout shareWithMainLayout;
    private LinearLayout descriptionLayout;
    private RelativeLayout postLayout;
    private TextView sharewith_option_text;
    private String sharedText;
    private TextInputLayout title_floating_label;
    private String shareWithText = "Everyone";
    private ArrayList<JSONObject> listOfChannels = new ArrayList<>();
    private TextView titleHintText, descriptionHintText;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchPDFFileResults, launchAudioResults, launchCameraResults, launchGalleryResults, launchShowPopupResults,
            launcherImageViewerResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        setContentView(R.layout.activity_createpost);
        callbackManager = CallbackCollectionManager.getInstance();
        marshMallowPermission = new MarshMallowPermission(this);
        /*
         * Get realm database object
         */
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        docId = realmManager.getDoc_id(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        /*
         * Create directory for images
         */

        createDirIfNotExists("/Post_images");
        /*
         * Inflate and initialize the UI elements
         */
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_post, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        attachIcon = (LinearLayout) findViewById(R.id.post_attachment);
        postBtn = (TextView) mCustomView.findViewById(R.id.next_button);
        postBtn.setText(getResources().getString(R.string.post_screenactionbarnext));
        postToInputLayout = (TextView) findViewById(R.id.postToInputLayout);
        postRelativeLayout = (RelativeLayout) findViewById(R.id.postRelativeLayout);
        appliesToInputLayout = (TextView) findViewById(R.id.appliesToInputLayout);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        helpTxtView = (TextView) findViewById(R.id.helpTxtVw);
        exampleTextView = (TextView) findViewById(R.id.exampleTextView);
        postToEdtTxt = (EditText) findViewById(R.id.postToEdtTxt);
        titleEdtTxt = (EditText) findViewById(R.id.titleEdtTxt_update);
        descEdtTxt = (EditText) findViewById(R.id.descEdtTxt);
        specialityEdtTxt = (EditText) findViewById(R.id.specialityEdtTxt);
        titleCharLimit = (TextView) findViewById(R.id.titleCharLimit);
        departmentList = new ArrayList<HashMap<String, String>>();
        specialityList = new ArrayList<HashMap<String, String>>();
        title_update_layout = (LinearLayout) findViewById(R.id.title_updates_layout);
        gridView = (NonScrollableGridView) findViewById(R.id.post_gridview);
        errorTxtVw = (TextView) findViewById(R.id.title_error_update);
        radioGroup_lay = (RelativeLayout) findViewById(R.id.radioGroup_lay);
        shareWithMainLayout = (RelativeLayout) findViewById(R.id.shareWithMainLayout);

        communityFormRelativeLayout = (RelativeLayout) findViewById(R.id.communityFormRelativeLayout);
        communityFormLayout = (RelativeLayout) findViewById(R.id.communityFormLayout);
        shareWithLayout = (RelativeLayout) findViewById(R.id.shareWithLayout);
        postLayout = (RelativeLayout) findViewById(R.id.postLayout);
        shareWithHeading = (TextView) findViewById(R.id.shareWithHeading);
        descriptionLayout = (LinearLayout) findViewById(R.id.descriptionLayout);
        sharewith_option_text = (TextView) findViewById(R.id.sharewith_option_text);
        descriptionHintText = (TextView) findViewById(R.id.descriptionHintText);
        titleHintText = (TextView) findViewById(R.id.titleHintText);

        /*
         * Define all clicks and listeners here
         */




        /*Refactoring the deprecated startActivityForResults*/
        //Start

        launcherImageViewerResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 2020
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        App_Application.setCurrentActivity(CreatePostActivity.this);
                        String editedImagePath = data.getStringExtra(ImageEditor.EXTRA_EDITED_PATH);
                        if (editedImagePosition != -1) {
                            AttachmentInfo attachmentObj = completeList.get(editedImagePosition);
                            attachmentObj.setEditPost(false);
                        }
                        customAdapter.notifyDataSetChanged();

                    } else if (resultCode == RESULT_CANCELED) {
                        editedImagePosition = -1;

                    }
                });


        launchShowPopupResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 1000
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        String dialogOption = data.getStringExtra(RestUtils.DIALOG_OPTION);
                        ArrayList<HashMap<String, String>> itemsList = (ArrayList<HashMap<String, String>>) data.getSerializableExtra(RestUtils.KEY_ITEMS_LIST);
                        String itemNames = null;
                        if (dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS)) {
                            isDepartmentSaved = true;
                            departmentList.clear();
                            departmentList.addAll(itemsList);
//                 Update the department and speciality data in UI
                            itemNames = getSelectedItemNames(departmentList, dialogOption);

                        } else if (dialogOption.equalsIgnoreCase(RestUtils.KEY_SPECIALITIES)) {
                            // Keep a flag to track whether speciality selection is changed or not.
                            isSpecialitySaved = true;
                            specialityList.clear();
                            specialityList.addAll(itemsList);
//                 Get the department and speciality data in UI
                            itemNames = getSelectedItemNames(specialityList, dialogOption);
                        }
                        //Update UI
                        if (dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS)) {
                            postToEdtTxt.setText(itemNames);
                        } else if (dialogOption.equalsIgnoreCase(RestUtils.KEY_SPECIALITIES)) {
                            specialityEdtTxt.setText(itemNames);
                        }
                    }
                });
        launchAudioResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 202
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        ArrayList<FileDetails> filesList = data.getParcelableArrayListExtra("SELECTED_FILES_LIST");
                        int size = filesList.size();
                        ConstsCore.POST_ATTACHMENT_COUNT = ConstsCore.POST_ATTACHMENT_COUNT + size;

                        new FileCopyingTask(CreatePostActivity.this, "audio", filesList, new OnCopyCompletedListener() {
                            @Override
                            public void onCopyCompleted(ArrayList<FileDetails> paths, String fileType) {
                                completeList.remove(null);
                                for (int i = 0; i < paths.size(); i++) {
                                    File file = AppUtil.getExternalStoragePathFile(CreatePostActivity.this, ".Whitecoats/Post_images/" + paths.get(i).getFilePath());
                                    if (AppUtil.isFileSizeSupported(file)) {
                                        AttachmentInfo obj = new AttachmentInfo();
                                        obj.setEditPost(false);
                                        obj.setFileAttachmentPath(paths.get(i).getFilePath());
                                        obj.setAttachmentName(paths.get(i).getFileName());
                                        completeList.add(obj);
                                    } else {
                                        if (file.exists()) {
                                            boolean del = file.delete();
                                            Log.i("deleteFile", "Is file deleted" + del);
                                        }
                                        Toast.makeText(CreatePostActivity.this, getString(R.string.size_exceed_msg), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (completeList.size() <= 4) {
                                    completeList.add(null);
                                }
                                customAdapter.notifyDataSetChanged();
                            }
                        }).execute();
                    }
                });
        launchPDFFileResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 101
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        if (data != null && data.getData() != null) {
                            new CopyFileToAppDirTask().execute(data.getData());
                        } else {
                            Log.d("CREATE_POST", "File uri not found {}");
                        }
                    }


                });

        launchCameraResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //request code 1313
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if (resultCode == Activity.RESULT_OK) {
                            if (mCapturedImageURI != null) {
                                selectedImagePath = getPath(mCapturedImageURI, CreatePostActivity.this);
                                ConstsCore.POST_ATTACHMENT_COUNT = ConstsCore.POST_ATTACHMENT_COUNT + 1;
                                ArrayList<String> imagePathList = new ArrayList<>();
                                imagePathList.add(selectedImagePath);
                                new ImageProcessingTask(CreatePostActivity.this, DIRECTORY.POST, imagePathList).execute();
                            }
                        } else if (resultCode == Activity.RESULT_CANCELED) {
                            if (mCapturedImageURI != null) {
                                getContentResolver().delete(mCapturedImageURI, null, null);
                            }
                        }
                    }
                });

        launchGalleryResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //request code 2000
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if (resultCode == Activity.RESULT_OK) {
                            //showProgress();
                            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);
                            int size = images.size();
                            ConstsCore.POST_ATTACHMENT_COUNT = ConstsCore.POST_ATTACHMENT_COUNT + size;
                            ArrayList<String> image_paths = new ArrayList<>();
                            String[] convertedStringArray = new String[size];
                            for (int i = 0; i < size; i++) {
                                convertedStringArray[i] = images.get(i).path;
                                image_paths.add(images.get(i).path);
                            }
                            new ImageProcessingTask(CreatePostActivity.this, DIRECTORY.POST, image_paths).execute();
                        }
                    }
                });
        //End


        titleEdtTxt.setFilters(EmojiFilter.getFilter());
        descEdtTxt.setFilters(EmojiFilter.getFilter());
        int maxLength = 500;
        titleEdtTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});


        new AwsAndGoogleKeysServiceClass(CreatePostActivity.this, docId, basicInfo.getUserUUID(), new AwsAndGoogleKey() {
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

        bundle = getIntent().getExtras();
        navigation = bundle.getString(RestUtils.NAVIGATATION);
        communitySelected = bundle.getInt("communitySelectedType", -1);
        String channelObj = bundle.getString(RestUtils.KEY_SELECTED_CHANNEL);
        if (channelObj != null) {
            try {
                channelJsonObj = new JSONObject(channelObj);
                channelId = channelJsonObj.optInt(RestUtils.CHANNEL_ID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        customAdapter = new CustomAdapter(CreatePostActivity.this, completeList, channelId, feedId);
        gridView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
        basicInfo = realmManager.getRealmBasicInfo(realm);
        JSONArray updated_channels_list = realmManager.getChannelsListFromDB("listofChannels");
        if (updated_channels_list != null) {
            for (int x = 0; x < updated_channels_list.length(); x++) {
                JSONObject currentChannelObj = updated_channels_list.optJSONObject(x);
                if (currentChannelObj.optBoolean(RestUtils.TAG_IS_ADMIN)) {
                    listOfChannels.add(currentChannelObj);
                }
            }
        }


        shareWithLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((listOfChannels != null && listOfChannels.size() == 0)) {
                    return;
                }

                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(CreatePostActivity.this);
                final View sheetView = CreatePostActivity.this.getLayoutInflater().inflate(R.layout.bottom_sheet_modal, null);
                ViewGroup network_selection_layout = (ViewGroup) sheetView.findViewById(R.id.network_selection_layout);
                TextView share_with_everyOne_textView = (TextView) sheetView.findViewById(R.id.share_with_everyOne_textView);
                final ListView communityListView = (ListView) sheetView.findViewById(R.id.communityList);
                share_with_everyOne_textView.setText("Share With WhiteCoatsNetwork");
                /*
                 * If No network channel is there, hide "post to network" layout
                 */
                /*
                 * Sort the list in alphabetical order
                 */


                Collections.sort(listOfChannels, new Comparator<JSONObject>() {

                    @Override
                    public int compare(JSONObject lhs, JSONObject rhs) {
                        try {
                            return (lhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase().compareTo(rhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }
                });
                /*
                 * Set adapter
                 */
                CommunityListAdapter communityListAdapter = new CommunityListAdapter(CreatePostActivity.this, listOfChannels);
                communityListView.setAdapter(communityListAdapter);
                mBottomSheetDialog.setContentView(sheetView);
                BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) sheetView.getParent());
                mBottomSheetDialog.show();
                communityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mBottomSheetDialog.dismiss();
                        shareWithHeading.setVisibility(View.VISIBLE);
                        try {
                            channelJsonObj = new JSONObject(listOfChannels.get(position).toString());
                            channelId = channelJsonObj.optInt(RestUtils.CHANNEL_ID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        customAdapter = new CustomAdapter(CreatePostActivity.this, completeList, channelId, feedId);
                        gridView.setAdapter(customAdapter);
                        customAdapter.notifyDataSetChanged();

                        getDepartmentsAndSpecialitiesForNewPost(channelId);
                        shareWithText = channelJsonObj.optString("feed_provider_name");
                        sharewith_option_text.setText(channelJsonObj.optString("feed_provider_name"));

                    }
                });
                network_selection_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();

                        try {
                            channelJsonObj = new JSONObject(DashboardActivity.channelsList.get(DashboardActivity.networkChannelId).toString());
                            channelId = channelJsonObj.optInt(RestUtils.CHANNEL_ID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        customAdapter = new CustomAdapter(CreatePostActivity.this, completeList, channelId, feedId);
                        gridView.setAdapter(customAdapter);
                        customAdapter.notifyDataSetChanged();
                        getDepartmentsAndSpecialitiesForNewPost(channelId);
                        shareWithText = "WhiteCoats Network";
                        sharewith_option_text.setText("WhiteCoats Network");
                        /*
                         * Send customized params when sharing to Network channel
                         */
                    }
                });


            }
        });

        titleEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                titleCharLimit.setText((TITLE_TXT_LIMIT - s.length()) + "");
                if (s.length() == TITLE_TXT_LIMIT) {
                    titleCharLimit.setTextColor(Color.RED);
                } else {
                    titleCharLimit.setTextColor(Color.GRAY);
                }

                if (s.length() > 0) {
                    titleHintText.setVisibility(View.VISIBLE);
                } else {
                    titleHintText.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        descEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    descriptionHintText.setVisibility(View.VISIBLE);
                } else {
                    descriptionHintText.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        descEdtTxt.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() == R.id.descEdtTxt) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        titleEdtTxt.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() == R.id.titleEdtTxt_update) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        /*
        StyleFont for post description
         */

        actionCallBack = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu_stylefont, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

                menu.removeItem(android.R.id.shareText);

                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                CharacterStyle cs;
                int itemId = menuItem.getItemId();
                String source = descEdtTxt.getText().toString();
                int selectionStart = descEdtTxt.getSelectionStart();
                int selectionEnd = descEdtTxt.getSelectionEnd();
                switch (itemId) {
                    case R.id.action_italic:

                        String substring = source.substring(selectionStart, selectionEnd);
                        SpannableStringBuilder sb = new SpannableStringBuilder(substring);
                        sb.setSpan(new StyleSpan(Typeface.ITALIC), 0, substring.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        descEdtTxt.getText().replace(selectionStart, selectionEnd, sb);
                        actionMode.finish();
                        return true;
                    case R.id.action_bold:

                        String substring1 = source.substring(selectionStart, selectionEnd);
                        SpannableStringBuilder sb1 = new SpannableStringBuilder(substring1);
                        sb1.setSpan(new StyleSpan(Typeface.BOLD), 0, substring1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        descEdtTxt.getText().replace(selectionStart, selectionEnd, sb1);
                        actionMode.finish();
                        return true;

                    case R.id.action_Strikethrough:
                        String substring2 = source.substring(selectionStart, selectionEnd);
                        SpannableStringBuilder sb2 = new SpannableStringBuilder(substring2);
                        sb2.setSpan(new StrikethroughSpan(), 0, substring2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        descEdtTxt.getText().replace(selectionStart, selectionEnd, sb2);
                        actionMode.finish();
                        return true;

                    case R.id.action_UnderLine:
                        String substringUline = source.substring(selectionStart, selectionEnd);
                        SpannableStringBuilder sbUline = new SpannableStringBuilder(substringUline);
                        sbUline.setSpan(new UnderlineSpan(), 0, substringUline.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        descEdtTxt.getText().replace(selectionStart, selectionEnd, sbUline);
                        actionMode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        };
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            descEdtTxt.setCustomSelectionActionModeCallback(actionCallBack);
        }
        postToEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet() & departmentList.size() > 0)
                    showDialog(departmentList, RestUtils.KEY_DEPARTMENTS);
                else
                    setupUI(false);

            }
        });
        specialityEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEdittextFlag = true;
                if (isConnectingToInternet() & specialityList.size() > 0)
                    showDialog(specialityList, RestUtils.KEY_SPECIALITIES);
                else
                    setupUI(false);
            }
        });

        /*
         * Get the bundle data from previous activity
         *
         */
        if (bundle != null) {
            completeList.add(null);
            String shareTypeValue = bundle.getString("SharedType", "");
            if (shareTypeValue.equalsIgnoreCase("text")) {//for text sharing
                sharedText = bundle.getString("SharedText");
                handleSendText(sharedText);
            } else if (shareTypeValue.equalsIgnoreCase("singleFile")) {//for single image
                Uri fileUri = Uri.parse(bundle.getString("FileUri"));
                String fileType = bundle.getString("fileType");
                handleSendImage(fileUri, fileType);
            } else if (shareTypeValue.equalsIgnoreCase("multipleFiles")) {//for multiple images
                ArrayList<Uri> uriList = (ArrayList<Uri>) getIntent().getSerializableExtra("MultipleFileUri");
                String fileType = bundle.getString("fileType");
                handleSendMultipleImages(uriList, fileType);
            }
            if (navigation != null && navigation.equalsIgnoreCase("Dashboard")) {
                radioGroup_lay.setVisibility(View.GONE);
                shareWithMainLayout.setVisibility(View.VISIBLE);
            } else if (navigation.equalsIgnoreCase("Channels") || navigation.equalsIgnoreCase("EditPost")) {
                radioGroup_lay.setVisibility(View.VISIBLE);
                shareWithMainLayout.setVisibility(View.GONE);
            } else if (navigation.equalsIgnoreCase("Feeds")) {
                radioGroup_lay.setVisibility(View.VISIBLE);
                shareWithMainLayout.setVisibility(View.GONE);
            } else if (navigation != null && navigation.equalsIgnoreCase("ChannelSelectionActivity")) {
                radioGroup_lay.setVisibility(View.VISIBLE);
                shareWithMainLayout.setVisibility(View.VISIBLE);
            }


            if (channelObj == null) {
                JSONArray updatedChannelsList = realmManager.getChannelsListFromDB("listofChannels");
                for (int x = 0; x < updatedChannelsList.length(); x++) {
                    JSONObject currentChannelObj = updatedChannelsList.optJSONObject(x);
                    if (currentChannelObj != null && currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Network")) {
                        channelObj = currentChannelObj.toString();
                    }
                }
            }


            //added by dileep
            radioBtnAskQuetion = new RadioButton(getApplicationContext());
            radioBtnAskQuetion.setText(R.string.label_ask_question);
            radioBtnAskQuetion.setTextSize(14);
            radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.black));
            radioBtnAskQuetion.setId(0 + 1);
            RadioGroup.LayoutParams params2 = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(0, 70, 0, 0);
            radioBtnAskQuetion.setLayoutParams(params2);


            radioBtnCase = new RadioButton(getApplicationContext());
            radioBtnCase.setText(R.string.label_case);
            radioBtnCase.setTextSize(14);
            radioBtnCase.setTextColor(getResources().getColor(R.color.app_green));
            radioBtnCase.setId(1 + 100);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            radioBtnCase.setLayoutParams(params);

            radioBtnUpdate = new RadioButton(getApplicationContext());
            radioBtnUpdate.setText("Share News/Announcements");
            radioBtnUpdate.setTextSize(14);
            radioBtnUpdate.setTextColor(getResources().getColor(R.color.black));
            radioBtnUpdate.setId(0);
            RadioGroup.LayoutParams params1 = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params1.setMargins(0, 10, 0, 0);
            radioBtnUpdate.setLayoutParams(params1);


            if (radioGroup != null) {
                radioGroup.addView(radioBtnAskQuetion);
                radioGroup.addView(radioBtnCase);
                radioGroup.addView(radioBtnUpdate);
            }
            if (channelObj != null) {
                try {
                    if (navigation != null && !navigation.equalsIgnoreCase("Dashboard")) {
                        channelJsonObj = new JSONObject(channelObj);
                        if (channelJsonObj != null) {
                            toolBarTitle = channelJsonObj.optString(RestUtils.FEED_PROVIDER_NAME);
                        }
                    }
                    if (communitySelected == 0) {
                        mTitleTextView.setText("Ask A Question");
                    } else if (communitySelected == 1) {
                        mTitleTextView.setText("Post A Case");
                    } else if (communitySelected == 2) {
                        mTitleTextView.setText("Share News/Announcements");
                    } else {
                        mTitleTextView.setText(toolBarTitle);
                        sharewith_option_text.setText(toolBarTitle);
                    }
                    if (navigation != null && !navigation.isEmpty() && !navigation.equalsIgnoreCase("EditPost")) {

                        if (communitySelected == 1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                            } else {
                                radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                                radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                                radioBtnCase.setHighlightColor(getResources().getColor(R.color.app_green));
                            }
                            helpTxtView.setText(getString(R.string.label_case_help_text));
                            exampleTextView.setText(getString(R.string.post_a_case_example));
                            radioGroup.check(1 + 100);
                            titleEdtTxt.setHint("Title");
                            titleHintText.setHint("Title");
//                            title_floating_label.setHint("Title");
                            descriptionLayout.setVisibility(View.VISIBLE);
                        } else if (communitySelected == 2) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                                radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            } else {
                                radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.app_green));
                                radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                                radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                            }
                            helpTxtView.setText(getString(R.string.label_update_help_text));
                            exampleTextView.setText(getString(R.string.news_example));

                            radioGroup.check(0);
                            titleEdtTxt.setHint("Title");
                            titleHintText.setHint("Title");
//                            title_floating_label.setHint("Title");
                            descriptionLayout.setVisibility(View.VISIBLE);
                        } else if (communitySelected == 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                                radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            } else {
                                radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                                radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.app_green));
                                radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                            }
                            helpTxtView.setText(getString(R.string.label_ask_question_help_text));
                            exampleTextView.setText(getString(R.string.ask_question_example));

                            radioGroup.check(0 + 1);

                            titleEdtTxt.setHint("Type a Question");
                            titleHintText.setHint("Type a Question");
                            descriptionLayout.setVisibility(View.GONE);


                        } else {
                            if (navigation != null && !navigation.isEmpty() && navigation.equalsIgnoreCase("ChannelSelectionActivity") || navigation.equalsIgnoreCase("Channels") || navigation.equalsIgnoreCase("Feeds")) {
                                if (channelObj != null) {
                                    String feedProviderType = channelJsonObj.optString("feed_provider_type");
                                    if (feedProviderType.equalsIgnoreCase("Network")) {
                                        radioBtnCase.setChecked(true);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                            radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                            radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                                        } else {
                                            radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                                            radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                                            radioBtnCase.setHighlightColor(getResources().getColor(R.color.app_green));
                                        }
                                        helpTxtView.setText(getString(R.string.label_case_help_text));
                                        exampleTextView.setText(getString(R.string.post_a_case_example));

                                        titleEdtTxt.setHint("Title");
                                        titleHintText.setHint("Title");
                                        descriptionLayout.setVisibility(View.VISIBLE);
                                        radioBtnUpdate.setTextColor(getResources().getColor(R.color.black));
                                        radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.black));
                                        radioBtnCase.setTextColor(getResources().getColor(R.color.app_green));
                                    } else if (feedProviderType.equalsIgnoreCase("Community")) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                                            radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                            radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                        } else {
                                            radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.app_green));
                                            radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                                            radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                                        }
                                        helpTxtView.setText(getString(R.string.label_update_help_text));
                                        exampleTextView.setText(getString(R.string.news_example));
                                        radioBtnUpdate.setChecked(true);
                                        titleEdtTxt.setHint("Title");
                                        titleHintText.setHint("Title");
                                        descriptionLayout.setVisibility(View.VISIBLE);
                                        radioBtnUpdate.setTextColor(getResources().getColor(R.color.app_green));
                                        radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.black));
                                        radioBtnCase.setTextColor(getResources().getColor(R.color.black));
                                    } else {
                                        helpTxtView.setText(getString(R.string.label_ask_question_help_text));
                                        exampleTextView.setText(getString(R.string.ask_question_example));

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                                            radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                            radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                                        } else {
                                            radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.app_green));
                                            radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                                            radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                                        }
                                        radioBtnAskQuetion.setChecked(true);
                                        radioBtnUpdate.setTextColor(getResources().getColor(R.color.black));
                                        radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.app_green));
                                        radioBtnCase.setTextColor(getResources().getColor(R.color.black));
                                        titleEdtTxt.setHint("Type a Question");
                                        titleHintText.setHint("Type a Question");
                                        if (navigation != null && !navigation.isEmpty() && !navigation.equalsIgnoreCase("ChannelSelectionActivity")) {
                                            descriptionLayout.setVisibility(View.GONE);
                                        }

                                    }
                                }
                            }


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                if (communitySelected == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                        radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                        radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                    } else {
                        radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                        radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                        radioBtnCase.setHighlightColor(getResources().getColor(R.color.app_green));
                    }
                    helpTxtView.setText(getString(R.string.label_case_help_text));
                    exampleTextView.setText(getString(R.string.post_a_case_example));


                    radioGroup.check(1 + 100);
                    titleEdtTxt.setHint("Title");
                    titleHintText.setHint("Title");
                    descriptionLayout.setVisibility(View.VISIBLE);
                } else if (communitySelected == 2) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                        radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                        radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                    } else {
                        radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.app_green));
                        radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                        radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                    }
                    helpTxtView.setText(getString(R.string.label_update_help_text));
                    exampleTextView.setText(getString(R.string.news_example));
                    radioGroup.check(0);
                    titleEdtTxt.setHint("Title");
                    titleHintText.setHint("Title");
                    descriptionLayout.setVisibility(View.VISIBLE);
                } else if (communitySelected == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                        radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                        radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                    } else {
                        radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                        radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.app_green));
                        radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                    }
                    helpTxtView.setText(getString(R.string.label_ask_question_help_text));
                    exampleTextView.setText(getString(R.string.ask_question_example));

                    radioGroup.check(0 + 1);

                    titleEdtTxt.setHint("Type a Question");
                    titleHintText.setHint("Type a Question");
                    descriptionLayout.setVisibility(View.GONE);


                }
            }
            if (navigation != null && !navigation.isEmpty() && navigation.equalsIgnoreCase("EditPost")) {
                String jsonArray = bundle.getString("attachmentsArray");
                feedInfo = bundle.getParcelable("feedinfo_object");
                titleHintText.setVisibility(View.VISIBLE);
                titleEdtTxt.setText(feedInfo.getTitle());
                if (feedInfo.getFeedType() != null && feedInfo.getFeedType().equalsIgnoreCase("post")) {
                    titleHintText.setHint("Title");
                    titleEdtTxt.setHint("Title");
                } else if (feedInfo.getFeedType() != null && feedInfo.getFeedType().equalsIgnoreCase("case")) {
                    titleHintText.setHint("Title");
                    titleEdtTxt.setHint("Title");
                } else {
                    titleHintText.setHint("Type a Question");
                    titleEdtTxt.setHint("Type a Question");
                }
                descriptionHintText.setVisibility(View.VISIBLE);
                descEdtTxt.setText(Html.fromHtml(feedInfo.getFeedDesc()));
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioGroup.getChildAt(i).setEnabled(false);
                }
                if (feedInfo.getFeedType() != null && feedInfo.getFeedType().equalsIgnoreCase("case")) {
                    descriptionLayout.setVisibility(View.VISIBLE);
                    radioBtnCase.setChecked(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                        radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                        radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                    } else {
                        radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                        radioBtnCase.setHighlightColor(getResources().getColor(R.color.app_green));
                        radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                    }
                    radioBtnUpdate.setTextColor(getResources().getColor(R.color.black));
                    radioBtnCase.setTextColor(getResources().getColor(R.color.app_green));
                    radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.black));
                    errorTxtVw.setText(getString(R.string.post_title_case));
                    helpTxtView.setText(getString(R.string.label_case_help_text));
                    exampleTextView.setText(getString(R.string.post_a_case_example));
                } else if (feedInfo.getFeedType() != null && feedInfo.getFeedType().equalsIgnoreCase("post")) {
                    if (feedInfo.getDisplayTag().equalsIgnoreCase("Update")) {
                        descriptionLayout.setVisibility(View.VISIBLE);
                        radioBtnUpdate.setChecked(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                            radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                        } else {
                            radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.app_green));
                            radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                            radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                        }
                        radioBtnCase.setTextColor(getResources().getColor(R.color.black));
                        radioBtnUpdate.setTextColor(getResources().getColor(R.color.app_green));
                        radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.black));
                        errorTxtVw.setText(getString(R.string.post_title_update));
                        helpTxtView.setText(getString(R.string.label_update_help_text));
                        exampleTextView.setText(getString(R.string.news_example));
                    } else {
                        descriptionLayout.setVisibility(View.GONE);
                        radioBtnAskQuetion.setChecked(true);
                        titleHintText.setHint("Type a Question");
                        titleEdtTxt.setHint("Type a Question");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                        } else {
                            radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                            radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                            radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.app_green));
                        }
                        radioBtnCase.setTextColor(getResources().getColor(R.color.black));
                        radioBtnUpdate.setTextColor(getResources().getColor(R.color.black));
                        radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.app_green));
                        errorTxtVw.setText("Please provide a title for this Question");
                        helpTxtView.setText(getString(R.string.label_ask_question_help_text));
                        exampleTextView.setText(getString(R.string.ask_question_example));
                    }
                }
                try {
                    specialityEdtTxt.setEnabled(false);
                    postToEdtTxt.setEnabled(false);
                    JSONObject jsonObject = new JSONObject();
                    userID = basicInfo.getDoc_id();
                    channelId = feedInfo.getChannelID();
                    feedId = feedInfo.getFeedID();
                    upshotEventData(feedId, channelId, 0, "", "", "", "", " ", false);
                    if (AppUtil.isConnectingToInternet(CreatePostActivity.this)) {
                        jsonObject.put(RestUtils.TAG_USER_ID, userID);
                        jsonObject.put(RestUtils.CHANNEL_ID, channelId);
                        jsonObject.put(RestUtils.FEEDID, feedId);
                        new VolleySinglePartStringRequest(CreatePostActivity.this, Request.Method.POST, RestApiConstants.GET_SPECIALITIES_DEPARTMENTS, jsonObject.toString(), TAG, new OnReceiveResponse() {

                            @Override
                            public void onSuccessResponse(String successResponse) {
                                Log.i(TAG, "onSuccessResponse(EDIT)");
                                JSONObject response = null;
                                try {
                                    response = new JSONObject(successResponse);
                                    if (response.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                        if (response.has(RestUtils.TAG_DATA)) {
                                            JSONArray departmentsEditArray = response.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.KEY_DEPARTMENTS);
                                            JSONArray specialitiesEditArray = response.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.KEY_SPECIALITIES);

                                            int deptArrayLength = departmentsEditArray.length();
                                            int spltyArrayLength = specialitiesEditArray.length();
                                            for (int i = 0; i < deptArrayLength; i++) {
                                                JSONObject deptObject = departmentsEditArray.optJSONObject(i);
                                                HashMap<String, String> departmentMap = new HashMap<String, String>();
                                                departmentMap.put(RestUtils.DEPARTMENT_ID, deptObject.optString(RestUtils.DEPARTMENT_ID));
                                                departmentMap.put(RestUtils.DEPARTMENT_NAME, deptObject.optString(RestUtils.DEPARTMENT_NAME));
                                                departmentMap.put(RestUtils.KEY_ISSELECTED, "true");
                                                departmentList.add(departmentMap);
                                            }
                                            for (int j = 0; j < spltyArrayLength; j++) {
                                                JSONObject spltyObject = specialitiesEditArray.optJSONObject(j);
                                                HashMap<String, String> specialityMap = new HashMap<String, String>();
                                                specialityMap.put(RestUtils.SPECIALITY_ID, spltyObject.optString(RestUtils.SPECIALITY_ID));
                                                specialityMap.put(RestUtils.SPECIALITY_NAME, spltyObject.optString(RestUtils.SPECIALITY_NAME));

                                                specialityList.add(specialityMap);
                                            }
                                            if (deptArrayLength > 0 || spltyArrayLength > 0) {
                                                setupUI(true);
                                                if (radioGroup.getCheckedRadioButtonId() == 0 || radioGroup.getCheckedRadioButtonId() == 0 + 1) {
                                                    if (!isSpecialitySaved) {
                                                        specialityEdtTxt.setText(getString(R.string.label_default_specialty));

                                                        specialityEdtTxt.setText(getSelectedItemNames(specialityList, RestUtils.EDIT_POST_SPECIALITY_NAME).isEmpty() ? "All Specialities" : getSelectedItemNames(specialityList, RestUtils.EDIT_POST_SPECIALITY_NAME));
                                                    }
                                                } else {
                                                    if (!isSpecialitySaved) {
                                                        sortAlphabetically(specialityList, RestUtils.KEY_SPECIALITIES);
                                                        specialityEdtTxt.setText(getSelectedItemNames(specialityList, RestUtils.EDIT_POST_SPECIALITY_NAME).isEmpty() ? "All Specialities" : getSelectedItemNames(specialityList, RestUtils.EDIT_POST_SPECIALITY_NAME));
                                                    }
                                                }
                                                postToEdtTxt.setText(getSelectedItemNames(departmentList, RestUtils.KEY_DEPARTMENTS_IN_EDIT).isEmpty() ? "All Departments" : getSelectedItemNames(departmentList, RestUtils.KEY_DEPARTMENTS_IN_EDIT));
                                            } else {
                                                setupUI(false);
                                            }
                                        }
                                    } else if (response.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                        hideProgress();
                                        setupUI(false);
                                        Toast.makeText(CreatePostActivity.this, response.getString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(String errorResponse) {
                                Log.i(TAG, "onErrorResponse(EDIT)");
                                // hideProgress();
                            }
                        }).sendSinglePartRequest();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray jsonArr = new JSONArray(jsonArray);
                    if (jsonArr.length() >= 0) {
                        if (completeList.contains(null)) {
                            completeList.remove(null);
                        }
                    }
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        String originalUrl = jsonObject.optString(RestUtils.ATTACH_ORIGINAL_URL);
                        video_link = jsonObject.optString(RestUtils.ATTACH_ORIGINAL_URL);
                        //video/pdf samll_url
                        String smallUrl = jsonObject.optString(RestUtils.ATTACH_SMALL_URL);
                        attachment_type = jsonObject.optString(RestUtils.ATTACHMENT_TYPE);
                        attachment_size = jsonObject.optLong(RestUtils.ATTACH_SIZE);
                        String imageUrl = originalUrl;
                        if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO) || attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF) || attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                            imageUrl = smallUrl;
                            AttachmentInfo obj = new AttachmentInfo();
                            obj.setEditPost(true);
                            obj.setAttachmentUrl(originalUrl);
                            obj.setAttachmentSmallUrl(imageUrl);
                            obj.setAttachmentType(attachment_type);
                            obj.setAttachmentSize(attachment_size);
                            completeList.add(obj);
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                Glide.with(mContext)
                                        .asBitmap()
                                        .load(imageUrl).listener(new RequestListener<Bitmap>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                                return false;
                                            }
                                        }).into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            }
                                        });
                            }
                        } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE)) {
                            final AttachmentInfo obj = new AttachmentInfo();
                            obj.setAttachmentUrl(imageUrl);
                            obj.setEditPost(true);
                            obj.setAttachmentType(attachment_type);
                            obj.setAttachmentSmallUrl(smallUrl);
                            completeList.add(obj);
                            obj.setAttachmentSize(attachment_size);
                            Glide.with(mContext)
                                    .asBitmap()
                                    .load(imageUrl).listener(new RequestListener<Bitmap>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                            return false;
                                        }
                                    })
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            obj.setFileAttachmentPath(AppUtil.saveImage(resource, CreatePostActivity.this));

                                        }
                                    });
                        }
                    }
                    if (completeList.size() <= 4) {
                        completeList.add(null);
                    }
                    customAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                getDepartmentsAndSpecialitiesForNewPost(channelId);
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                setupUI(true);
                switch (checkedId) {
                    case 0:
                        radioBtnUpdate.setTextColor(getResources().getColor(R.color.app_green));
                        radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.black));
                        radioBtnCase.setTextColor(getResources().getColor(R.color.black));
                        helpTxtView.setText(getString(R.string.label_update_help_text));
                        exampleTextView.setText(getString(R.string.news_example));

                        errorTxtVw.setText(getString(R.string.post_title_update));
                        if (!isSpecialitySaved) {
                            specialityEdtTxt.setText(getString(R.string.label_default_specialty));
                            updateSpecialityList(userSpeciality, false);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                            radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                        } else {
                            radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.app_green));
                            radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                            radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                        }
                        titleEdtTxt.setHint("Title");
                        titleHintText.setHint("Title");
                        titleEdtTxt.setText("");
                        descriptionLayout.setVisibility(View.VISIBLE);
                        descEdtTxt.setText(sharedText);
                        break;
                    case 1 + 100:
                        radioBtnUpdate.setTextColor(getResources().getColor(R.color.black));
                        radioBtnCase.setTextColor(getResources().getColor(R.color.app_green));
                        radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.black));
                        helpTxtView.setText(getString(R.string.label_case_help_text));
                        exampleTextView.setText(getString(R.string.post_a_case_example));
                        errorTxtVw.setText(getString(R.string.post_title_case));
                        if (!isSpecialitySaved) {
                            updateSpecialityList(userSpeciality, true);
                            sortAlphabetically(specialityList, RestUtils.KEY_SPECIALITIES);
                            specialityEdtTxt.setText(getSelectedItemNames(specialityList, RestUtils.SPECIALITY_NAME));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                        } else {
                            radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                            radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.black_radio));
                            radioBtnCase.setHighlightColor(getResources().getColor(R.color.app_green));
                        }
                        titleEdtTxt.setHint("Title");
                        titleHintText.setHint("Title");
                        titleEdtTxt.setText("");
                        descriptionLayout.setVisibility(View.VISIBLE);
                        descEdtTxt.setText(sharedText);
                        break;

                    case 0 + 1:
                        radioBtnCase.setTextColor(getResources().getColor(R.color.black));
                        radioBtnUpdate.setTextColor(getResources().getColor(R.color.black));
                        radioBtnAskQuetion.setTextColor(getResources().getColor(R.color.app_green));
                        helpTxtView.setText(getString(R.string.label_ask_question_help_text));
                        exampleTextView.setText(getString(R.string.ask_question_example));
                        errorTxtVw.setText(getString(R.string.post_title_case));
                        if (!isSpecialitySaved) {
                            specialityEdtTxt.setText(getString(R.string.label_default_specialty));
                            updateSpecialityList(userSpeciality, false);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            radioBtnUpdate.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                            radioBtnAskQuetion.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.app_green)));
                            radioBtnCase.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(CreatePostActivity.this, R.color.black_radio)));
                        } else {
                            radioBtnUpdate.setHighlightColor(getResources().getColor(R.color.black_radio));
                            radioBtnAskQuetion.setHighlightColor(getResources().getColor(R.color.app_green));
                            radioBtnCase.setHighlightColor(getResources().getColor(R.color.black_radio));
                        }
                        titleEdtTxt.setHint("Type a Question");
                        titleHintText.setHint("Type a Question");
                        titleEdtTxt.setText(sharedText);
                        if (sharedText != null && !sharedText.isEmpty()) {
                            titleEdtTxt.requestFocus();
                            descEdtTxt.setText("");
                        }
                        descriptionLayout.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
            }
        });

        // Set Action bar & UI
        setActionBar();

        setupUI(false);
        /*
         * Get User Info
         */
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryCount = 0;
                uploadErrorMsgCount = 0;
                validationUtils = new ValidationUtils(CreatePostActivity.this,
                        new EditText[]{titleEdtTxt},
                        new TextView[]{errorTxtVw});
                postTitle = titleEdtTxt.getText().toString();
                if (validationUtils.isOneEntered(postTitle)) {
                    if (isConnectingToInternet()) {
                        createPostWithAttachments(completeList);
                    }
                }
            }
        });

        postLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                retryCount = 0;
                uploadErrorMsgCount = 0;
                validationUtils = new ValidationUtils(CreatePostActivity.this,
                        new EditText[]{titleEdtTxt},
                        new TextView[]{errorTxtVw});
                postTitle = titleEdtTxt.getText().toString();
                if (validationUtils.isOneEntered(postTitle)) {
                    if (isConnectingToInternet()) {
                        createPostWithAttachments(completeList);
                    }
                }

            }
        });

    }

    private void getDepartmentsAndSpecialitiesForNewPost(int channelId) {
        docID = basicInfo.getDoc_id();
        final String specialist = basicInfo.getSplty();
        userSpeciality = getString(R.string.label_default_specialty);
        /*
         * Call DEPARTMENT & SPECIALITY Service
         */
        JSONObject jsonObject = new JSONObject();
        try {
            //showProgress();
            if (AppUtil.isConnectingToInternet(CreatePostActivity.this)) {
                jsonObject.put(RestUtils.TAG_DOC_ID, docID);
                jsonObject.put(RestUtils.CHANNEL_ID, channelId);
                new VolleySinglePartStringRequest(CreatePostActivity.this, Request.Method.POST, RestApiConstants.DEPARTMENT_AND_SPECIALITY, jsonObject.toString(), TAG, new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        Log.i(TAG, "onSuccessResponse()");
                        /*
                         * Populate UI Adapter for depts & splty
                         */
                        JSONObject response = null;
                        try {
                            response = new JSONObject(successResponse);
                            if (response.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                if (response.has(RestUtils.TAG_DATA)) {
                                    JSONArray departmentsArray = response.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.KEY_DEPARTMENTS);
                                    JSONArray specialitiesArray = response.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.KEY_SPECIALITIES);
                                    // get related_specialties array
                                    relatedSpecialtiesArray = response.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.KEY_RELATED_SPECIALITIES);

                                    int deptArrayLen = departmentsArray.length();
                                    int spltyArrayLen = specialitiesArray.length();
                                    //int relatedSpecialtiesArrayLen=relatedSpecialtiesArray.length();
                                    departmentList.clear();
                                    specialityList.clear();

                                    for (int index = 0; index < deptArrayLen; index++) {
                                        JSONObject deptObject = departmentsArray.optJSONObject(index);
                                        HashMap<String, String> departmentMap = new HashMap<String, String>();
                                        departmentMap.put(RestUtils.DEPARTMENT_ID, deptObject.optString(RestUtils.DEPARTMENT_ID));
                                        departmentMap.put(RestUtils.DEPARTMENT_NAME, deptObject.optString(RestUtils.DEPARTMENT_NAME));
                                        departmentMap.put(RestUtils.KEY_ISSELECTED, Boolean.toString(true));
                                        departmentList.add(departmentMap);
                                    }
                                    for (int index = 0; index < spltyArrayLen; index++) {
                                        JSONObject spltyObject = specialitiesArray.optJSONObject(index);
                                        HashMap<String, String> specialityMap = new HashMap<String, String>();
                                        specialityMap.put(RestUtils.SPECIALITY_ID, spltyObject.optString(RestUtils.SPECIALITY_ID));
                                        specialityMap.put(RestUtils.SPECIALITY_NAME, spltyObject.optString(RestUtils.SPECIALITY_NAME));
                                        specialityList.add(specialityMap);
                                    }
                                    if (deptArrayLen > 0 || spltyArrayLen > 0) {
                                        setupUI(true);
                                        if (radioGroup.getCheckedRadioButtonId() == 0 || radioGroup.getCheckedRadioButtonId() == 0 + 1) {
                                            if (!isSpecialitySaved) {
                                                specialityEdtTxt.setText(getString(R.string.label_default_specialty));
                                                updateSpecialityList(userSpeciality, false);
                                            }
                                        } else {
                                            if (!isSpecialitySaved) {
                                                updateSpecialityList(userSpeciality, true);
                                                sortAlphabetically(specialityList, RestUtils.KEY_SPECIALITIES);
                                                specialityEdtTxt.setText(getSelectedItemNames(specialityList, RestUtils.SPECIALITY_NAME));
                                            }
                                        }
                                    } else {
                                        setupUI(false);
                                    }
                                }
                            } else if (response.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                hideProgress();
                                setupUI(false);
                                Toast.makeText(CreatePostActivity.this, response.getString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        Log.i(TAG, "onErrorResponse()");
                    }
                }).sendSinglePartRequest();
            } else {
                setupUI(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        upshotEventData(0, this.channelId, 0, "", "", "", "", " ", false);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void createPostWithAttachments(ArrayList<AttachmentInfo> completeList) {
        if (completeList.contains(null)) {
            completeList.remove(null);
        }
        final int listSize = completeList.size();
        showProgress();
        if (listSize > 0) {
            currentUpload = 1;
            mProgressDialog.setMessage("Uploading " + currentUpload + " of " + listSize);
            final SparseArray<JSONObject> attachmentDetailsMap = new SparseArray<>();
            for (int i = 0; i < listSize; i++) {
                final int selectedPosition = i;
                AttachmentInfo element = completeList.get(i);
                if (element != null) {
                    final JSONObject attachmentObj = new JSONObject();
                    if (element.isEditPost()) {
                        try {
                            if (element.getAttachmentType().equalsIgnoreCase("image")) {
                                attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, element.getAttachmentUrl());
                                attachmentObj.put(RestUtils.ATTACH_SMALL_URL, element.getAttachmentSmallUrl());
                                attachmentObj.put(RestUtils.ATTACH_SIZE, element.getAttachmentSize());
                                attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_IMAGE);
                                attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "jpeg");
                            } else if (element.getAttachmentType().equalsIgnoreCase("audio")) {
                                attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, element.getAttachmentUrl());
                                attachmentObj.put(RestUtils.ATTACH_SMALL_URL, element.getAttachmentSmallUrl());
                                attachmentObj.put(RestUtils.ATTACH_SIZE, element.getAttachmentSize());
                                attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_AUDIO);
                                attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "mp3");
                            } else if (element.getAttachmentType().equalsIgnoreCase("video")) {
                                attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, element.getAttachmentUrl());
                                attachmentObj.put(RestUtils.ATTACH_SMALL_URL, element.getAttachmentSmallUrl());
                                attachmentObj.put(RestUtils.ATTACH_SIZE, element.getAttachmentSize());
                                attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_VIDEO);
                                attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "mp4");
                            } else if (element.getAttachmentType().equalsIgnoreCase("pdf")) {
                                attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, element.getAttachmentUrl());
                                attachmentObj.put(RestUtils.ATTACH_SMALL_URL, element.getAttachmentSmallUrl());
                                attachmentObj.put(RestUtils.ATTACH_SIZE, element.getAttachmentSize());
                                attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_PDF);
                                attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "pdf");
                            }
                            attachmentDetailsMap.put(selectedPosition, attachmentObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        isAnyAttachmentsAdded = true;
                        if (element == null) {
                            mProgressDialog.dismiss();
                            Toast.makeText(mContext, "Unable to upload due to corrupted file", Toast.LENGTH_SHORT).show();
                        } else if (element.getFileAttachmentPath() == null) {
                            mProgressDialog.dismiss();
                            Toast.makeText(mContext, "Unable to upload file", Toast.LENGTH_SHORT).show();
                        }
                        File attFile = new File(element.getFileAttachmentPath());
                        if (!attFile.exists()) {
                            attFile = new File(getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/Post_images/" + element.getFileAttachmentPath());
                        }
                        File finalAttFile = attFile;
                        new AWSHelperClass(CreatePostActivity.this, awsKeys, finalAttFile, docId, basicInfo.getUserUUID(), new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String url) {
                                if (url != null && !url.isEmpty()) {
                                    String extension = finalAttFile.getAbsolutePath().substring(finalAttFile.getAbsolutePath().lastIndexOf(".") + 1);
                                    try {
                                        if (extension.equalsIgnoreCase("jpg")) {
                                            attachmentObj.put(RestUtils.ATTACHMENT_S3_NAME, "");
                                            attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_IMAGE);
                                            attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "jpg");
                                            attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, url);
                                        } else if (extension.equalsIgnoreCase("png")) {
                                            attachmentObj.put(RestUtils.ATTACHMENT_S3_NAME, "");
                                            attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_IMAGE);
                                            attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "png");
                                            attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, url);
                                        } else if (extension.equalsIgnoreCase("mp4")) {
                                            attachmentObj.put(RestUtils.ATTACHMENT_S3_NAME, "");
                                            attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_VIDEO);
                                            attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "mp4");
                                            attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, url);
                                        } else if (extension.equalsIgnoreCase("pdf")) {
                                            attachmentObj.put(RestUtils.ATTACHMENT_S3_NAME, "");
                                            attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_PDF);
                                            attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "pdf");
                                            attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, url);
                                        } else if (extension.equalsIgnoreCase("mp3")) {
                                            attachmentObj.put(RestUtils.ATTACHMENT_S3_NAME, "");
                                            attachmentObj.put(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_AUDIO);
                                            attachmentObj.put(RestUtils.ATTACHMENT_EXTN, "mp3");
                                            attachmentObj.put(RestUtils.ATTACH_ORIGINAL_URL, url);
                                        }
                                        attachmentDetailsMap.put(selectedPosition, attachmentObj);
                                        try {
                                            if (attachmentDetailsMap.size() == listSize) {
                                                hideProgress();
                                                for (int k = 0; k < attachmentDetailsMap.size(); k++) {
                                                    attachmentDetailsArray.put(attachmentDetailsMap.get(k));
                                                }
                                                // Prepare Create Post Request Object
                                                JSONObject requestObj;

                                                if (navigation != null && navigation.equalsIgnoreCase("EditPost")) {
                                                    requestObj = getPostEditRequestParms();
                                                } else {
                                                    requestObj = getPostRequestParams();
                                                }
                                                createPost(requestObj);
                                            } else {
                                                currentUpload++;
                                                mProgressDialog.setMessage("Uploading " + currentUpload + " of " + listSize);
                                            }
                                        } catch (Exception e) {
                                            hideProgress();
                                            Log.d(TAG, "Exception " + e);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    mProgressDialog.dismiss();
                                    ShowSimpleDialog(getResources().getString(R.string.unabletosave), getResources().getString(R.string.trylater));
                                }
                            }
                        });

                    }
                    try {
                        if (!isAnyAttachmentsAdded) {
                            if (attachmentDetailsMap.size() == listSize) {
                                hideProgress();
                                for (int k = 0; k < attachmentDetailsMap.size(); k++) {
                                    attachmentDetailsArray.put(attachmentDetailsMap.get(k));
                                }
                                // Prepare Create Post Request Object
                                JSONObject requestObj;
                                if (navigation != null && navigation.equalsIgnoreCase("EditPost")) {
                                    requestObj = getPostEditRequestParms();
                                } else {
                                    requestObj = getPostRequestParams();
                                }


                                createPost(requestObj);
                            } else {
                                currentUpload++;
                                mProgressDialog.setMessage("Uploading " + currentUpload + " of " + listSize);
                            }
                        }
                    } catch (Exception e) {
                        hideProgress();
                        Log.d(TAG, "Exception " + e);
                    }
                }
            }
        } else {
            hideProgress();
            /*
             * Prepare Create Post Request Object without attachments(empty json array)
             */
            JSONObject requestObj;
            if (navigation != null && navigation.equalsIgnoreCase("EditPost")) {
                requestObj = getPostEditRequestParms();
            } else {
                requestObj = getPostRequestParams();
            }
            createPost(requestObj);
        }
    }


    private void createDirIfNotExists(String subDir) {
        myDirectory = AppUtil.getExternalStoragePathFile(this, ".Whitecoats");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        folder = new File(myDirectory + subDir);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
        setCurrentActivity();
        checkNetworkConnectivity();
        if (isDeletedFromImageFullView && positionForImageFullView != -1) {
            isDeletedFromImageFullView = false;
            completeList.remove(positionForImageFullView);
            if (!completeList.contains(null)) {
                completeList.add(null);
            }
            customAdapter.notifyDataSetChanged();
        }
    }

    private JSONObject getPostRequestParams() {
        Log.i(TAG, "getPostRequestParams()");
        JSONObject requestObj = null;
        try {
            String feedType = "";
            String feedProviderType = "";
            descEdtTxt.clearComposingText();
            if (channelJsonObj != null) {
                feedProviderType = channelJsonObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
            } else {
                feedProviderType = "Network";
            }
            if (radioBtnUpdate.isChecked() || radioBtnAskQuetion.isChecked()) {
                feedType = "post";
            } else {
                feedType = "case";
            }
            String appendedText = appendAnchorTags(descEdtTxt.getText().toString());
            if (appendedText == null) {
                appendedText = "";
            }
            requestObj = new JSONObject();
            requestObj.put(RestUtils.ATTACHMENT_DETAILS, attachmentDetailsArray);
            requestObj.put(RestUtils.CHANNEL_IDS, new JSONArray().put(channelId));
            requestObj.put(RestUtils.DEPARTMENT_IDS, getDepartmentIds());
            requestObj.put(RestUtils.SPECIALITY_IDS, getSpecialityIds());
            requestObj.put(RestUtils.TAG_DOC_ID, docID);
            requestObj.put(RestUtils.FEED_DESC, appendedText);
            requestObj.put(RestUtils.TAG_FEED_PROVIDER_TYPE, feedProviderType);
            requestObj.put(RestUtils.TAG_FEED_TYPE, feedType);
            requestObj.put(RestUtils.TAG_TITLE, postTitle);
            if (radioBtnCase.isChecked()) {
                requestObj.put(RestUtils.TAG_FEED_SUB_TYPE, "CASE");
            } else if (radioBtnUpdate.isChecked()) {
                requestObj.put(RestUtils.TAG_FEED_SUB_TYPE, "UPDATE");
            } else {
                requestObj.put(RestUtils.TAG_FEED_SUB_TYPE, "QUESTION");
            }
            if (retryCount > 0) {
                requestObj.put(RestUtils.TAG_IS_RETRY, true);
            } else {
                requestObj.put(RestUtils.TAG_IS_RETRY, false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj;
    }

    private JSONObject getPostEditRequestParms() {
        Log.i(TAG, "getPostEditRequestParams()");
        JSONObject requestObj = null;
        try {
            String feedType = "";
            String feedProviderType = "";
            userID = basicInfo.getDoc_id();
            //docID = basicInfo.getDoc_id();
            channelId = feedInfo.getChannelID();
            feedId = feedInfo.getFeedID();
            if (channelJsonObj != null) {
                feedProviderType = channelJsonObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
            } else {
                feedProviderType = "Network";
            }
            if (radioBtnUpdate.isChecked())
                feedType = "post";
            else
                feedType = "case";
            String appendedText = appendAnchorTags(descEdtTxt.getText().toString());
            if (appendedText == null) {
                appendedText = "";
            }
            requestObj = new JSONObject();
            requestObj.put(RestUtils.TAG_USER_ID, userID);
            requestObj.put(RestUtils.CHANNEL_ID, channelId);
            requestObj.put(RestUtils.FEEDID, feedId);
            requestObj.put(RestUtils.TAG_TITLE, postTitle);
            requestObj.put(RestUtils.FEED_DESC, appendedText);
            requestObj.put(RestUtils.ATTACHMENT_DETAILS, attachmentDetailsArray);
            if (retryCount > 0) {
                requestObj.put(RestUtils.TAG_IS_RETRY, true);
            } else {
                requestObj.put(RestUtils.TAG_IS_RETRY, false);
            }
            if (!radioBtnCase.isChecked()) {
                if (radioBtnUpdate.isChecked()) {
                    requestObj.put(RestUtils.TAG_FEED_SUB_TYPE, "Update");
                } else {
                    requestObj.put(RestUtils.TAG_FEED_SUB_TYPE, "Question");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestObj;
    }


    private JSONObject getAWSRequest() {
        Log.i(TAG, "getAWSRequestcredentials()");
        JSONObject requestObj = null;
        try {
            requestObj = new JSONObject();
            requestObj.put(RestUtils.TAG_DOC_ID, basicInfo.getDoc_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestObj;
    }

    private JSONArray getDepartmentIds() {
        Log.i(TAG, "getDepartmentIds()");
        JSONArray departmentIds = new JSONArray();
        for (HashMap<String, String> department : departmentList) {
            if (Boolean.parseBoolean(department.get(RestUtils.KEY_ISSELECTED))) {
                departmentIds.put(department.get(RestUtils.DEPARTMENT_ID));
            }
        }
        return departmentIds;

    }

    private JSONArray getSpecialityIds() {
        Log.i(TAG, "getSpecialityIds()");
        JSONArray specialityIds = new JSONArray();
        for (HashMap<String, String> speciality : specialityList) {
            if (Boolean.parseBoolean(speciality.get(RestUtils.KEY_ISSELECTED))) {
                specialityIds.put(speciality.get(RestUtils.SPECIALITY_ID));
            }
        }
        return specialityIds;

    }

    private void showDialog(List<HashMap<String, String>> itemsList, String option) {
        Log.i(TAG, "showDialog()");
        Intent intent = new Intent(CreatePostActivity.this, SpecialityDialogActivity.class);
        intent.putExtra(RestUtils.DIALOG_OPTION, option);
        intent.putExtra(RestUtils.KEY_ITEMS_LIST, (Serializable) getSortedItemList(itemsList, option));
        intent.putExtra(RestUtils.KEY_SELECTED_ITEM_COUNT, selectedItemsCount);
        if (option.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS))
            intent.putExtra(RestUtils.KEY_IS_ALL_SELECTED, isAllDeptSelected);
        else
            intent.putExtra(RestUtils.KEY_IS_ALL_SELECTED, isAllSpltySelected);
        launchShowPopupResults.launch(intent);
    }

    private List<HashMap<String, String>> getSortedItemList(List<HashMap<String, String>> itemList, String option) {
        Log.i(TAG, "getSortedItemList()");
        /*
         * Prepare two list of maps having selected & unselected items
         */
        List<HashMap<String, String>> selectedItemList = new ArrayList<>();
        List<HashMap<String, String>> nonSelectedItemList = new ArrayList<>();
        for (HashMap<String, String> item : itemList) {
            if (Boolean.parseBoolean(item.get(RestUtils.KEY_ISSELECTED))) {
                selectedItemList.add(item);
            } else {
                nonSelectedItemList.add(item);
            }
        }

        /*
         * Sort both of them
         */
        itemList.clear();
        if (selectedItemList.size() > 0) {
            selectedItemsCount = selectedItemList.size();
            selectedItemList = sortAlphabetically(selectedItemList, option);
            itemList.addAll(selectedItemList);
        } else {
            selectedItemsCount = 0;
        }
        if (nonSelectedItemList.size() > 0) {
            nonSelectedItemList = sortAlphabetically(nonSelectedItemList, option);
            itemList.addAll(nonSelectedItemList);
        }
        return itemList;

    }

    private List<HashMap<String, String>> sortAlphabetically(List<HashMap<String, String>> unsortedList, final String option) {
        Log.i(TAG, "sortAlphabetically()");
        Collections.sort(unsortedList, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> map1, HashMap<String, String> map2) {
                if (option.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS))
                    return (map1.get(RestUtils.DEPARTMENT_NAME).toLowerCase().compareTo(map2.get(RestUtils.DEPARTMENT_NAME).toLowerCase()));
                else
                    return (map1.get(RestUtils.SPECIALITY_NAME).toLowerCase().compareTo(map2.get(RestUtils.SPECIALITY_NAME).toLowerCase()));
            }
        });
        return unsortedList;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, getString(R.string._onActivityResult));
        if (resultCode == RESULT_OK) {
            App_Application.setCurrentActivity(CreatePostActivity.this);
            if (requestCode == ImageEditor.RC_IMAGE_EDITOR || requestCode == 2020) {
                String editedImagePath = data.getStringExtra(ImageEditor.EXTRA_EDITED_PATH);
                if (editedImagePosition != -1) {
                    AttachmentInfo attachmentObj = completeList.get(editedImagePosition);
                    attachmentObj.setEditPost(false);
                }
                customAdapter.notifyDataSetChanged();

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri, CreatePostActivity.this);
                resizedBitmap = AppUtil.bitmapCompression(selectedImagePath);
                if (resizedBitmap != null) {
                    attachIcon.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(CreatePostActivity.this, "Image not supported,please select another image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE) {
                List<String> mPaths = data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
                //Your Code
                if (mPaths.size() > 0) {
                    compressVideoFile(mPaths.get(0));
                }
            }

        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == ImageEditor.RC_IMAGE_EDITOR || requestCode == 2020) {
                editedImagePosition = -1;

            }
        }
    }

    private void compressVideoFile(String sourcePath) {
        File sourceFile = new File(sourcePath);
        if (sourcePath == null || !sourceFile.exists()) {
            Toast.makeText(CreatePostActivity.this, "Unable to attach, please try again.", Toast.LENGTH_SHORT).show();
            return;
        }
        ConstsCore.POST_ATTACHMENT_COUNT = ConstsCore.POST_ATTACHMENT_COUNT + 1;

        if (AppUtil.isFileSizeLessThan5Mb(sourceFile)) {
            String sourceFileName = sourceFile.getName();
            completeList.remove(null);
            AttachmentInfo obj = new AttachmentInfo();
            obj.setFileAttachmentPath(sourceFileName);
            obj.setEditPost(false);
            completeList.add(obj);
            if (completeList.size() <= 4) {
                completeList.add(null);
            }
            //gridView.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        Date now = new Date();
        String fileName = "video_" + formatter.format(now) + ".mp4";
        File directory = AppUtil.getExternalStoragePathFile(CreatePostActivity.this, ".Whitecoats/Post_images");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File destinationFile = new File(directory + "/" + fileName);
        VideoCompressor.start(sourcePath, destinationFile.getPath(), new CompressionListener() {
            @Override
            public void onStart() {
                showProgress();
            }

            @Override
            public void onSuccess() {
                hideProgress();
                File tempVideoFile = new File(sourcePath);
                if (tempVideoFile.exists()) {
                    boolean del = tempVideoFile.delete();
                    Log.i("deleteFile", "Is file deleted" + del);
                }
                if (AppUtil.isFileSizeSupported(destinationFile)) {
                    completeList.remove(null);
                    AttachmentInfo obj = new AttachmentInfo();
                    obj.setFileAttachmentPath(fileName);
                    obj.setEditPost(false);
                    completeList.add(obj);
                    if (completeList.size() <= 4) {
                        completeList.add(null);
                    }
                    //gridView.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();
                } else {
                    if (destinationFile.exists()) {
                        boolean del = destinationFile.delete();
                        Log.i("deleteFile", "Is file deleted" + del);
                    }
                    Toast.makeText(CreatePostActivity.this, getString(R.string.size_exceed_msg), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String s) {
                hideProgress();
                Toast.makeText(CreatePostActivity.this, "Unable to compress,please try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(float v) {

            }

            @Override
            public void onCancelled() {

            }
        }, VideoQuality.MEDIUM, false, false);
    }

    public void selectImage() {
        Log.i(TAG, "selectImage()");
        final CharSequence[] items = {getString(R.string.label_take_photo), getString(R.string.label_choose_library), "Attach Video", "Attach Pdf", "Attach Audio"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePostActivity.this);
        builder.setTitle(getString(R.string.label_add_attachment));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    if (!marshMallowPermission.requestPermissionForCamera(true)) {
                        if (AppConstants.neverAskAgain_Camera) {
                            AppUtil.alertMessage(CreatePostActivity.this, getString(R.string.label_camera));
                        }
                    } else {
                        cameraClick();
                    }
                } else if (item == 1) {
                    if (!marshMallowPermission.requestPermissionForStorage(true)) {
                        if (AppConstants.neverAskAgain_Library) {
                            AppUtil.alertMessage(CreatePostActivity.this, getString(R.string.label_storage));
                        }
                    } else {
                        chooseFromLibrary();
                    }
                } else if (item == 2) {
                    chooseFromVideoLibrary();
                } else if (item == 3) {
                    if (!marshMallowPermission.requestPermissionForStorage(PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_FOR_PDF, true)) {
                        if (AppConstants.neverAskAgain_Library) {
                            AppUtil.alertMessage(CreatePostActivity.this, getString(R.string.label_files));
                        }
                    } else {
                        pickPdfFile();

                    }
                } else if (item == 4) {
                    if (!marshMallowPermission.requestPermissionForStorage(PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_FOR_AUDIO, true)) {
                        if (AppConstants.neverAskAgain_Library) {
                            AppUtil.alertMessage(CreatePostActivity.this, getString(R.string.label_files));
                        }
                    } else {
                        Intent in = new Intent(CreatePostActivity.this, ListAllFilesActivity.class);
                        in.putExtra(RestUtils.TAG_FILE_TYPE, RestUtils.TAG_TYPE_AUDIO);
                        in.putExtra(RestUtils.TAG_LIMIT, (5 - (completeList.size() - 1)));
                        launchAudioResults.launch(in);
                    }
                }
            }
        });
        builder.show();
    }

    private void pickPdfFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        launchPDFFileResults.launch(intent);
    }

    private void cameraClick() {
        Log.i(TAG, "cameraClick()");
        File f = null;
        if (ConstsCore.POST_ATTACHMENT_COUNT < 5) {
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
                launchCameraResults.launch(takePictureIntent);
            }

        } else {
            Toast.makeText(
                            getApplicationContext(),
                            String.format(getString(R.string.attachment_limit_exceeded), 5),
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void chooseFromLibrary() {
        Log.i(TAG, "chooseFromLibrary()");
        Intent intent = new Intent(CreatePostActivity.this, ImageSelectActivity.class);
        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 5);
        intent.putExtra(ConstantsCustomGallery.EXTRA_LIMIT_COUNT, completeList.size() - 1);
        launchGalleryResults.launch(intent);
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

    private void setActionBar() {
        Log.i(TAG, "setActionBar()");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    /**
     * To set UI of post screen and setting all values to UI elements.
     * Accepts boolean value - If "true" - Dropdown box will be enabled for both Department & Speciality.
     * If "false"- Dropdown box will be disabled for both Department & Speciality.
     * <p>
     * Note - Always pass true value, only if there is a bad network connection pass "false".
     * <p>
     * </p>
     *
     * @param hasData
     */
    private void setupUI(boolean hasData) {
        Log.i(TAG, "setupUI()");
        /**
         * If  posting WhiteCoats Network, disable "Post To" option otherwise enable it.
         */
        if (channelJsonObj != null) {
            if (channelJsonObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_COMMUNITY)) {
                postToInputLayout.setVisibility(View.VISIBLE);
                postToEdtTxt.setVisibility(View.VISIBLE);
                postRelativeLayout.setVisibility(View.VISIBLE);
            } else {
                postToInputLayout.setVisibility(View.GONE);
                postToEdtTxt.setVisibility(View.GONE);
                postRelativeLayout.setVisibility(View.GONE);

            }
        } else {
            postToInputLayout.setVisibility(View.GONE);
            postToEdtTxt.setVisibility(View.GONE);
            postRelativeLayout.setVisibility(View.GONE);
        }
        /*
         * In case of any error response  or bad network connection we are disabling these two
         * fields and showing the default values.
         */
        if (hasData) {
            postToInputLayout.setClickable(true);
            postToEdtTxt.setClickable(true);
            appliesToInputLayout.setClickable(true);
            specialityEdtTxt.setClickable(true);
            postRelativeLayout.setClickable(true);
        } else {
            postToInputLayout.setClickable(false);
            postToEdtTxt.setClickable(false);
            appliesToInputLayout.setClickable(false);
            specialityEdtTxt.setClickable(false);
            postRelativeLayout.setClickable(false);
        }
    }

    /**
     * After selecting the items from department & speciality popup we would be displaying the
     * item names by grouping it.
     * Call the method once you done selecting the items from department & speciality.
     *
     * @param itemList
     * @param dialogOption
     * @return groupedItemNames
     */
    private String getSelectedItemNames(List<HashMap<String, String>> itemList, String dialogOption) {
        Log.i(TAG, "getSelectedItemNames()");
        ArrayList<String> selectedNames = new ArrayList<String>();
        int count = 0;
        int totalItemLength = itemList.size();
        String groupedItemName = "";
        for (HashMap<String, String> item : itemList) {
            if (Boolean.parseBoolean(item.get(RestUtils.KEY_ISSELECTED)) || dialogOption.equalsIgnoreCase(RestUtils.EDIT_POST_SPECIALITY_NAME)) {
                count++;
                if (dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS) || dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS_IN_EDIT))
                    selectedNames.add(item.get(RestUtils.DEPARTMENT_NAME));
                else
                    selectedNames.add(item.get(RestUtils.SPECIALITY_NAME));
            }
        }
        /*
         * If Total selected count is equals to list size, then display "All Members" (or) "All Specialities".
         * If one item selected, display that name.
         * If multiple items selected, group names and display.
         *
         */
        if (count == totalItemLength && !dialogOption.equalsIgnoreCase(RestUtils.EDIT_POST_SPECIALITY_NAME) && !dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS_IN_EDIT)) {
            if (dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS)) {
                isAllDeptSelected = true;
                groupedItemName = getString(R.string.label_default_department);
            } else {
                isAllSpltySelected = true;
                groupedItemName = getString(R.string.label_default_specialty);
            }
        } else if (count == 1) {
            if (dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS)) {
                isAllDeptSelected = false;
            } else {
                isAllSpltySelected = false;
            }
            if (selectedNames.size() > 0) {
                groupedItemName = selectedNames.get(0);
            }
        } else {
            if (dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS) || dialogOption.equalsIgnoreCase(RestUtils.KEY_DEPARTMENTS_IN_EDIT)) {
                if (selectedNames.size() > 0) {
                    groupedItemName = selectedNames.get(0) + " +" + (count - 1) + " more";
                    isAllDeptSelected = false;
                } else {
                    isAllDeptSelected = true;
                }
            } else {
                //asdfsadf
                if (selectedNames.size() > 0) {
                    String suffixSpeciality = selectedNames.get(0);
                    if (selectedNames.contains(userSpeciality)) {
                        suffixSpeciality = userSpeciality;
                    }
                    groupedItemName = suffixSpeciality + " +" + (count - 1) + " more";
                    isAllSpltySelected = false;
                } else {

                    isAllSpltySelected = true;
                }
            }
        }
        return groupedItemName;
    }

    private void updateSpecialityList(String userSpeciality, boolean isSelected) {
        Log.i(TAG, "updateSpecialityList()");
        int len = specialityList.size();
        for (int index = 0; index < len; index++) {
            if (isSelected) {
                isAllSpltySelected = false;
                if (specialityList.get(index).containsValue(userSpeciality)) {
                    specialityList.get(index).put(RestUtils.KEY_ISSELECTED, Boolean.toString(isSelected));
                } else {
                    specialityList.get(index).put(RestUtils.KEY_ISSELECTED, Boolean.toString(!isSelected));
                    if (relatedSpecialtiesArray != null) {
                        for (int z = 0; z < relatedSpecialtiesArray.length(); z++) {
                            if (Integer.parseInt(specialityList.get(index).get(RestUtils.SPECIALITY_ID)) == relatedSpecialtiesArray.optInt(z)) {
                                specialityList.get(index).put(RestUtils.KEY_ISSELECTED, Boolean.toString(isSelected));
                            }
                        }
                    }
                }
            } else {
                isAllSpltySelected = true;
                specialityList.get(index).put(RestUtils.KEY_ISSELECTED, Boolean.toString(true));
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Log.i(TAG, "rotateBitmap()");
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bmRotated != bitmap)
                bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            customBackButton = true;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                if (navigation != null && navigation.equalsIgnoreCase("editPost")) {
                    jsonObject.put("FeedId", feedId);
                } else {
                    jsonObject.put("FeedId", 0);
                }
                jsonObject.put("ChannelID", channelId);
                AppUtil.logUserUpShotEvent("CreatePostBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String editTemp_title = titleEdtTxt.getText().toString();
        String edittemp_desc = descEdtTxt.getText().toString();
        if (!customBackButton) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                if (navigation != null && navigation.equalsIgnoreCase("editPost")) {
                    jsonObject.put("FeedId", feedId);
                } else {
                    jsonObject.put("FeedId", 0);
                }
                jsonObject.put("ChannelID", channelId);
                AppUtil.logUserUpShotEvent("CreatePostDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (navigation != null && navigation.equalsIgnoreCase("EditPost")) {
            if (editTemp_title.equals(feedInfo.getTitle()) && edittemp_desc.equals(feedInfo.getFeedDesc())) {
                finish();
            } else {
                AlertDialog();
            }
        } else {
            AlertDialog();
        }
    }

    public void AlertDialog() {
        String title = titleEdtTxt.getText().toString();
        String description = descEdtTxt.getText().toString();
        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(description) || isDepartmentSaved || isSpecialitySaved || ConstsCore.POST_ATTACHMENT_COUNT > 0) {

            builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage(getString(R.string.alert_post_ll_be_discarded));
            builder.setPositiveButton(getResources().getString(R.string.discard), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        ConstsCore.POST_ATTACHMENT_COUNT = 0;
        departmentList = null;
        specialityList = null;
        navigation = null;
        selectedImagePath = null;
        postTitle = null;
        tempStringArray = null;
        if (completeList != null)
            cleanUpPostImages(completeList);
        /*
         * Close Realm
         */
        if (!realm.isClosed())
            realm.close();
    }

    @Override
    public void onCompressCompleted(String[] filePaths) {
        Log.i(TAG, "onCompressCompleted(String[] filePaths)");
        /*
         * Store file paths in temp array
         * 1. Increase old array size
         * 2. store new elements
         */
        int tempArrayLength = tempStringArray.length;
        int length = filePaths.length;
        int newLength = ConstsCore.POST_ATTACHMENT_COUNT;
//        Log.d(TAG,"Temp Array size before - "+tempStringArray.length);
        Log.d(TAG, "tempArrayLength - " + tempArrayLength + " Length - " + length + " New Length - " + newLength);
        /*
         * calculate the copy index
         */
        tempStringArray = Arrays.copyOf(tempStringArray, newLength);
        int dstIndex = 0;
        for (int i = 0; i < tempArrayLength; i++) {
            if (tempStringArray[i] != null && !tempStringArray[i].equals("")) {
                dstIndex++;
            }
        }
        Log.d(TAG, "dstIndex:" + dstIndex);

        System.arraycopy(filePaths, 0, tempStringArray, dstIndex, length);
//        Log.d(TAG,"Temp Array size after - "+tempStringArray.length);
        /*
         * merge temp array with new array
         */

        ArrayList<String> imagesList = new ArrayList<>(Arrays.asList(tempStringArray));
        ArrayList<AttachmentInfo> tempImagesList = new ArrayList<>();
        for (int i = 0; i < imagesList.size(); i++) {
            AttachmentInfo obj = new AttachmentInfo();
            obj.setEditPost(false);
            obj.setFileAttachmentPath(imagesList.get(i));
            tempImagesList.add(obj);
        }
        CustomAdapter customAdapter = new CustomAdapter(CreatePostActivity.this, tempImagesList, channelId, feedId);
        gridView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCompressCompletedWithArrayList(ArrayList<String> filePaths) {
        completeList.remove(null);
        for (int i = 0; i < filePaths.size(); i++) {
            AttachmentInfo obj = new AttachmentInfo();
            obj.setEditPost(false);
            obj.setFileAttachmentPath(filePaths.get(i));
            completeList.add(obj);
        }
        if (completeList.size() <= 4) {
            completeList.add(null);
        }
        gridView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    private void getAWSCredentials(final JSONObject request) {
        if (isConnectingToInternet()) {
            String url = RestApiConstants.AWS_KEYS;
            new VolleySinglePartStringRequest(CreatePostActivity.this, Request.Method.POST, url, request.toString(), "", new OnReceiveResponse() {
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
                                    AppUtil.logUserActionEvent(docId, "FetchAWSInfo", responseJsonObject, AppUtil.convertJsonToHashMap(responseJsonObject), CreatePostActivity.this);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                        AppUtil.logUserActionEvent(docId, "FetchAWSInfo", errorObj, AppUtil.convertJsonToHashMap(errorObj), CreatePostActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).sendSinglePartRequest();
        }
    }

    private void createPost(final JSONObject request) {
        if (isConnectingToInternet()) {
            if (retryCount == 0) {
                showProgress();
            }
            String url = RestApiConstants.Community_Make_Post;
            if (navigation != null && navigation.equalsIgnoreCase("EditPost")) {
                url = RestApiConstants.EDIT_POST;
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("FeedType", request.get(RestUtils.TAG_FEED_SUB_TYPE).toString());
                jsonObject.put("DocSpeciality", realmManager.getDocSpeciality(realm));
                jsonObject.put("ShareWith", shareWithText);
                jsonObject.put("AppliesTo", request.get(RestUtils.SPECIALITY_IDS));
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "PostTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), CreatePostActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new VolleyMultipartStringRequest(CreatePostActivity.this, Request.Method.POST, url, request.toString(), "MAKE_POST", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    hideProgress();
                    if (successResponse != null) {
                        if (successResponse.isEmpty()) {
                            //Toast.makeText(CreatePostActivity.this, getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject responseJsonObject = new JSONObject(successResponse);
                                if (responseJsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                    JSONObject postObj = responseJsonObject.optJSONObject(RestUtils.TAG_DATA);
                                    hideProgress();
                                    Toast.makeText(getApplicationContext(), "Post successfully completed", Toast.LENGTH_SHORT).show();
                                    if (navigation != null && navigation.equalsIgnoreCase("EditPost")) {
                                        postObj.put(RestUtils.TAG_FROM_EDIT_POST, true);
                                    } else {
                                        postObj.put(RestUtils.TAG_TITLE, postTitle);
                                        postObj.put("feed_desc", request.optString(RestUtils.FEED_DESC));
                                        postObj.put(RestUtils.TAG_DOC_ID, docID);
                                        if (postObj.has(RestUtils.TAG_POST_ID)) {
                                            postObj.put(RestUtils.TAG_FEED_ID, postObj.optInt(RestUtils.TAG_POST_ID));
                                        }
                                        postObj.put(RestUtils.TAG_FROM_CREATE_POST, true);
                                        JSONObject socialInteraction = new JSONObject();
                                        socialInteraction.put(RestUtils.TAG_IS_LIKE_ENABLED, true);
                                        socialInteraction.put(RestUtils.TAG_IS_COMMENT_ENABLED, true);
                                        socialInteraction.put(RestUtils.TAG_IS_SHARE_ENABLED, true);
                                        socialInteraction.put(RestUtils.TAG_IS_LIKE, false);
                                        socialInteraction.put(RestUtils.TAG_LIKES_COUNT, 0);
                                        socialInteraction.put(RestUtils.TAG_COMMENTS_COUNT, 0);
                                        socialInteraction.put("shareCount", 0);
                                        postObj.put(RestUtils.TAG_SOCIALINTERACTION, socialInteraction);
                                    }
                                    for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                        listener.notifyUIWithNewData(postObj);
                                    }
                                    JSONObject completeJsonObject = responseJsonObject.getJSONObject(RestUtils.TAG_DATA);
                                    if (navigation != null && navigation.equalsIgnoreCase("Dashboard") || navigation != null && navigation.equalsIgnoreCase("Channels") || navigation != null && navigation.equalsIgnoreCase("Feeds")) {
                                        Intent in = new Intent();

                                        in.setClass(CreatePostActivity.this, FeedsSummary.class);
                                        in.putExtra(RestUtils.TAG_FEED_OBJECT, completeJsonObject.toString());
                                        if (completeJsonObject.optString("feed_provider_type").equalsIgnoreCase("Network")) {
                                            in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, true);
                                        } else {
                                            in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
                                        }
                                        in.putExtra(RestUtils.CHANNEL_NAME, (completeJsonObject.optString("feed_provider_name")));
                                        in.putExtra(RestUtils.CHANNEL_ID, completeJsonObject.optInt("channel_id"));
                                        startActivity(in);
                                        finish();

                                    } else {
                                        Intent intent = new Intent();
                                        intent.putExtra("POST_OBJ", postObj.toString());
                                        /*
                                         * Navigate back to appropriate class from where the Intent been fired
                                         */
                                        if (navigation != null && navigation.equalsIgnoreCase("EditPost")) {
                                            setResult(RESULT_OK, intent);
                                        }


                                        finish();
                                    }

                                } else if (responseJsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    hideProgress();
                                    attachmentDetailsArray = new JSONArray();
                                    displayErrorScreen(successResponse);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    if (errorResponse != null && !errorResponse.isEmpty()) {
                        if (errorResponse.equalsIgnoreCase(RestUtils.TAG_NETWORK_ERROR_RETRY)) {
                            if (retryCount < 2) {
                                retryCount++;
                                JSONObject requestObj = getPostRequestParams();
                                createPost(requestObj);
                            } else {
                                attachmentDetailsArray = new JSONArray();
                                hideProgress();
                            }
                        } else {
                            attachmentDetailsArray = new JSONArray();
                            hideProgress();
                            displayErrorScreen(errorResponse);
                        }
                    } else {
                        attachmentDetailsArray = new JSONArray();
                        hideProgress();
                        displayErrorScreen(errorResponse);
                    }
                }
            }).sendMultipartRequest(resizedBitmap, "file", "");
        } else {
            hideProgress();
        }
    }

    public void cleanUpPostImages(ArrayList<AttachmentInfo> attachmentsList) {
        Log.i(TAG, "cleanUpPostImages()");
        int size = attachmentsList.size();
        for (int i = 0; i < size; i++) {
            if (attachmentsList.get(i) != null) {
                Log.i(TAG, "Position " + i + 1 + " - " + attachmentsList.get(i).getFileAttachmentPath());

                File file = AppUtil.getExternalStoragePathFile(CreatePostActivity.this, ".Whitecoats/Post_images/" + attachmentsList.get(i).getFileAttachmentPath());
                boolean isDeleted = file.delete();
                Log.i(TAG, "Position " + i + 1 + " : isDeleted - " + isDeleted);
            }
        }
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
                    perms.put(Manifest.permission.READ_MEDIA_AUDIO, PackageManager.PERMISSION_GRANTED);
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    boolean mediaImages = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES);
                    boolean mediaAudio = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_AUDIO);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                        createDirIfNotExists("/Post_images");
                        cameraClick();
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_DENIED) {
                        if (!camera && !mediaImages && !mediaAudio) {
                            AppConstants.neverAskAgain_Camera = true;
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !mediaImages && !mediaAudio) {
                        AppConstants.neverAskAgain_Library = true;
                    }
                    if (perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED && !camera) {
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
                        createDirIfNotExists("/Post_images");
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
                    createDirIfNotExists("/Post_images");
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
            case PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_FOR_PDF:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
            case PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_FOR_AUDIO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent in = new Intent(CreatePostActivity.this, ListAllFilesActivity.class);
                    in.putExtra(RestUtils.TAG_FILE_TYPE, RestUtils.TAG_TYPE_AUDIO);
                    in.putExtra(RestUtils.TAG_LIMIT, (5 - (completeList.size() - 1)));
                    launchAudioResults.launch(in);
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_AUDIO)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    } else {
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

    private void chooseFromVideoLibrary() {
        new VideoPicker.Builder(CreatePostActivity.this)
                .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
                .directory(getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/Post_images")
                .extension(VideoPicker.Extension.MP4)
                .enableDebuggingMode(true)
                .build();
    }


    public String getImagePathFromInputStreamUri(Uri uri, String fileName) {
        InputStream inputStream = null;
        String filePath = null;
        if (uri.getAuthority() != null) {
            try {
                inputStream = getContentResolver().openInputStream(uri); // context needed
                File photoFile = createTemporalFileFrom(inputStream, fileName);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                // log
                e.printStackTrace();
            } catch (IOException e) {
                // log
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    private File createTemporalFileFrom(InputStream inputStream, String fileName) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile(fileName);

            try (OutputStream outputStream = new FileOutputStream(targetFile)) {
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private File createTemporalFile(String filename) {
        return new File(getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/Post_images/", filename); // context needed
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            titleEdtTxt.setText(sharedText);
        }
    }


    void handleSendText(String sharedText) {
        if (sharedText != null) {
            // Update UI to reflect text being shared
            descEdtTxt.setText(sharedText);
        }
    }

    void handleSendImage(Uri fileUri, String fileType) {
        if (fileUri != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
            Date now = new Date();
            String timeFormat = formatter.format(now);
            // Update UI to reflect image being shared
            String fileName = "ShareFile" + timeFormat + ".jpg";
            if (fileType != null && fileType.isEmpty()) {
                fileName = "ShareFile" + timeFormat + ".pdf";
            } else if (fileType != null) {
                if (fileType.equalsIgnoreCase("video")) {
                    fileName = "ShareFile" + timeFormat + ".mp4";
                } else if (fileType.equalsIgnoreCase("audio")) {
                    fileName = "ShareFile" + timeFormat + ".mp3";
                } else if (fileType.equalsIgnoreCase("pdf")) {
                    fileName = "ShareFile" + timeFormat + ".pdf";
                }
            }
            selectedImagePath = getImagePathFromInputStreamUri(fileUri, fileName);
            if (fileType != null) {
                if (fileType.equalsIgnoreCase("video")) {
                    compressVideoFile(selectedImagePath);
                } else {
                    if (fileType.equalsIgnoreCase("image")) {
                        AppUtil.bitmapCompression(selectedImagePath);
                    }
                    ConstsCore.POST_ATTACHMENT_COUNT = ConstsCore.POST_ATTACHMENT_COUNT + 1;
                    completeList.remove(null);
                    AttachmentInfo obj = new AttachmentInfo();
                    obj.setFileAttachmentPath(selectedImagePath);
                    obj.setEditPost(false);
                    obj.setAttachmentName(fileUri.getLastPathSegment());
                    completeList.add(obj);
                    if (completeList.size() <= 4) {
                        completeList.add(null);
                    }
                    customAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    void handleSendMultipleImages(ArrayList<Uri> fileUris, String fileType) {
        ArrayList<FileDetails> filePaths = new ArrayList<>();
        if (fileUris != null) {
            // Update UI to reflect multiple images being shared
            int size = fileUris.size();
            if (fileUris.size() > 5) {
                size = 5;
                Toast.makeText(CreatePostActivity.this, "You can attach maximum 5 files", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < size; i++) {
                String fileExtension = ".jpg";
                String fileTypeFromURI = AppUtil.getMimeType(fileUris.get(i).toString());
                String fileName = fileUris.get(i).getLastPathSegment();
                if (fileUris.get(i).toString().startsWith("content:")) {
                    fileTypeFromURI = AppUtil.getMimeTypeFromUri(CreatePostActivity.this, fileUris.get(i));
                    fileName = AppUtil.getFileNameFromUri(CreatePostActivity.this, fileUris.get(i));
                }
                if (fileTypeFromURI == null && fileUris.get(i).toString().endsWith(".PDF")) {
                    fileExtension = ".pdf";
                }
                if (fileTypeFromURI != null) {
                    if (fileTypeFromURI.contains("video")) {
                        fileExtension = ".mp4";
                    } else if (fileTypeFromURI.contains("audio")) {
                        fileExtension = ".mp3";
                    } else if (fileTypeFromURI.contains("image")) {
                        fileExtension = ".jpg";
                    } else if (fileTypeFromURI.contains("application/pdf")) {
                        fileExtension = ".pdf";
                    }
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
                Date now = new Date();
                String timeFormat = formatter.format(now);
                String storedPath = getImagePathFromInputStreamUri(fileUris.get(i), "ShareFile" + timeFormat + fileExtension);
                if (storedPath != null) {
                    if (fileExtension.equalsIgnoreCase(".mp4")) {
                        compressVideoFile(storedPath);
                    } else {
                        if (fileExtension.equalsIgnoreCase(".jpg")) {
                            if (storedPath != null) {
                                AppUtil.bitmapCompression(storedPath);
                            }
                        }
                        File file = new File(storedPath);
                        if (AppUtil.isFileSizeSupported(file)) {
                            FileDetails fileObj = new FileDetails();
                            fileObj.setFilePath("ShareFile" + timeFormat + fileExtension);
                            fileObj.setFileName(fileName);
                            filePaths.add(fileObj);
                        } else {
                            if (file.exists()) {
                                boolean del = file.delete();
                                Log.i("deleteFile", "Is file deleted" + del);
                            }
                            Toast.makeText(CreatePostActivity.this, getString(R.string.size_exceed_msg), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            completeList.remove(null);
            for (int i = 0; i < filePaths.size(); i++) {
                AttachmentInfo obj = new AttachmentInfo();
                obj.setEditPost(false);
                obj.setFileAttachmentPath(filePaths.get(i).getFilePath());
                obj.setAttachmentName(filePaths.get(i).getFileName());
                completeList.add(obj);
            }
            if (completeList.size() <= 4) {
                completeList.add(null);
            }
            gridView.setAdapter(customAdapter);
            customAdapter.notifyDataSetChanged();
        }
    }

    public static String appendAnchorTags(String text) {
        Matcher m = Patterns.WEB_URL.matcher(text);
        StringBuffer builder = new StringBuffer();
        while (m.find()) {
            String url = m.group();
            if (!URLUtil.isValidUrl(url)) {
                url = "http://" + url;
            }
            m.appendReplacement(builder, "<a href=\"" + url + "\">" + m.group() + "</a>");
        }
        m.appendTail(builder);
        return builder.toString();
    }

    @SuppressLint("StaticFieldLeak")
    private class CopyFileToAppDirTask extends AsyncTask<Uri, Void, AttachmentInfo> {
        private ProgressDialog mProgressDialog;

        private CopyFileToAppDirTask() {
            mProgressDialog = new ProgressDialog(CreatePostActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Please Wait..");
            mProgressDialog.show();
        }

        protected AttachmentInfo doInBackground(Uri... uris) {
            try {
                return writeFileContent(uris[0]);
            } catch (IOException e) {
                //Log.d("Failed to copy file {}" + e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(AttachmentInfo attachmentInfo) {
            mProgressDialog.dismiss();
            if (attachmentInfo != null) {
                Log.d("CREATE_POST", "Cached file path {}" + attachmentInfo.getFileAttachmentPath());
                ConstsCore.POST_ATTACHMENT_COUNT = ConstsCore.POST_ATTACHMENT_COUNT + 1;
                completeList.remove(null);
                completeList.add(attachmentInfo);
                if (completeList.size() <= 4) {
                    completeList.add(null);
                }
                customAdapter.notifyDataSetChanged();
            } else {
                Log.d("CREATE_POST", "Writing failed {}");
            }

        }

    }

    private AttachmentInfo writeFileContent(final Uri uri) throws IOException {
        try (InputStream selectedFileInputStream =
                     getContentResolver().openInputStream(uri)) {
            if (selectedFileInputStream != null) {
                final File certCacheDir = AppUtil.getExternalStoragePathFile(CreatePostActivity.this, ".Whitecoats/Post_images");
                boolean isCertCacheDirExists = certCacheDir.exists();
                if (!isCertCacheDirExists) {
                    isCertCacheDirExists = certCacheDir.mkdirs();
                }
                if (isCertCacheDirExists) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
                    Date now = new Date();
                    String fileName = "file_" + formatter.format(now) + ".pdf";
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

    // Returns file display name.
    @Nullable
    private String getFileDisplayName(final Uri uri) {
        String displayName = null;
        try (Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

            }
        }

        return displayName;
    }

    /*Refactoring the deprecated startActivityForResults*/
    @Override
    public void onScreenNavigate(Intent intent) {
        launcherImageViewerResults.launch(intent);
    }
}




