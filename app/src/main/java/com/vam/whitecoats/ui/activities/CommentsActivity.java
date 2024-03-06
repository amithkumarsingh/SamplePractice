package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ImageProcessingTask;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.ConstsCore;
import com.vam.whitecoats.constants.DIRECTORY;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AWSKeys;
import com.vam.whitecoats.core.models.CustomModel;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.CommentsAdapter;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.fragments.DashboardUpdatesFragment;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.ui.interfaces.CommentsEditInterface;
import com.vam.whitecoats.ui.interfaces.OnCompressCompletedListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AWSHelperClass;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AwsAndGoogleKeysServiceClass;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.myinnos.awesomeimagepicker.activities.ImageSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import io.realm.Realm;

public class CommentsActivity extends BaseActionBarActivity implements CustomModel.OnUiUpdateListener, OnCompressCompletedListener {
    public static final String TAG = CommentsActivity.class.getSimpleName();
    private TextView totalSocialInteractionCount, mTitleTextView, pullToLoad;
    private EditText commentEditText;
    private TextView update_text, cancle_text;
    private ImageButton send_Button;
    private boolean keyboard_enable;

    CommentsAdapter commentsAdapter;
    private RecyclerView mRecyclerView;

    JSONObject requestJsonObject;
    private JSONArray commentJsonArray = new JSONArray();
    private JSONArray likeJsonArray = new JSONArray();
    private ArrayList<JSONObject> commentsList = new ArrayList<>();
    private String commentText;
    private LinearLayout befirst_lay, update_cancel_lay;
    int doctorId = 0;
    private Realm realm;
    private RealmManager realmManager;
    private int feed_type_id;
    private int feed_id;
    private int totalInteractions_count;
    private int channelID;
    private int lastSocialInteractionId = -1;
    private int lastLikeId = -1;
    private String postName;
    private RoundedImageView doctorImage;
    private RealmBasicInfo realmBasicInfo;
    private String navigation;
    private ViewGroup inputCommentLayout;
    private ProgressBar loading_progress;
    private static int totalCommentsCount = 0;
    private int commentsCount = 0;
    ImageView commentImageView;
    MarshMallowPermission marshMallowPermission;
    private Uri mCapturedImageURI = null;
    String selectedImagePath = "";
    private static final int SELECT_FILE = 1;
    private Bitmap resizedBitmap;
    String[] tempStringArray = new String[1];
    private int currentUpload = 0;
    private File folder, myDirectory;
    private RelativeLayout edit_delete_overlay;
    private int socialInteractionId;
    private String commentedText = "";
    private ImageView edit_imageView;
    private ImageView delete_btn;
    private JSONArray comment_edit_attachment = new JSONArray();
    private File updatedImageFile = null;
    private boolean isAttachmentContains;
    private File attFile;
    AWSKeys awsKeys = null;
    private CallbackCollectionManager callbackManager;
    private boolean customBackButton = false;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchCameraActivityResults;
    private ActivityResultLauncher<Intent> launchLibraryActivityResults;
    private ActivityResultLauncher<Intent> launchImagesPreviewActivityResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        marshMallowPermission = new MarshMallowPermission(this);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        callbackManager = CallbackCollectionManager.getInstance();
        createDirIfNotExists();
        totalSocialInteractionCount = (TextView) findViewById(R.id.comment_total);
        commentEditText = (EditText) findViewById(R.id.comment_EditText);
        inputCommentLayout = (ViewGroup) findViewById(R.id.comment_input_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.comments_list);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_comment, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        delete_btn = (ImageView) mCustomView.findViewById(R.id.delete_btn);
        send_Button = (ImageButton) findViewById(R.id.comment_sendBtn);
        befirst_lay = (LinearLayout) findViewById(R.id.befirst_lay);
        update_cancel_lay = (LinearLayout) findViewById(R.id.update_cancel_lay);
        update_text = (TextView) findViewById(R.id.update);
        cancle_text = (TextView) findViewById(R.id.cancle);
        doctorImage = (RoundedImageView) findViewById(R.id.doc_pic);
        pullToLoad = (TextView) findViewById(R.id.pullToLoad);
        loading_progress = (ProgressBar) findViewById(R.id.loading_progress);
        commentImageView = findViewById(R.id.comment_cameraBtn);
        edit_delete_overlay = (RelativeLayout) findViewById(R.id.edit_delete_overlay);
        edit_imageView = (ImageView) findViewById(R.id.edit_imageView);
        realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        JSONObject requestAwsObj;
        requestAwsObj = getAWSRequest();

        new AwsAndGoogleKeysServiceClass(CommentsActivity.this, doctorId, realmBasicInfo.getUserUUID(), new AwsAndGoogleKey() {
            @Override
            public void awsAndGoogleKey(String google_api_key, String aws_key) {


                if (aws_key != null && !aws_key.isEmpty()) {

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            postName = bundle.getString(RestUtils.TAG_POST_NAME);
            keyboard_enable = bundle.getBoolean(RestUtils.TAG_ENABLE_KEYBOARD);
            feed_type_id = bundle.getInt(RestUtils.TAG_FEED_TYPE_ID);
            totalInteractions_count = bundle.getInt(RestUtils.TAG_TOTAL_COMMENTS);
            channelID = bundle.getInt(RestUtils.CHANNEL_ID);
            navigation = bundle.getString(RestUtils.NAVIGATATION);
        }

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchCameraActivityResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {
                        App_Application.setCurrentActivity(CommentsActivity.this);
                        if (mCapturedImageURI != null) {
                            selectedImagePath = getPath(mCapturedImageURI, CommentsActivity.this);
                            ArrayList<String> imagePathList = new ArrayList<>();
                            imagePathList.add(selectedImagePath);
                            new ImageProcessingTask(CommentsActivity.this, DIRECTORY.COMMENT, imagePathList).execute();
                        }
                    }
                });
        launchLibraryActivityResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == RESULT_OK) {
                        App_Application.setCurrentActivity(CommentsActivity.this);
                        ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);
                        int size = images.size();
                        ArrayList<String> image_paths = new ArrayList<>();
                        String[] convertedStringArray = new String[size];
                        for (int i = 0; i < size; i++) {
                            convertedStringArray[i] = images.get(i).path;
                            image_paths.add(images.get(i).path);
                        }
                        new ImageProcessingTask(CommentsActivity.this, DIRECTORY.COMMENT, image_paths).execute();
                    }

                });
        launchImagesPreviewActivityResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                });

        //End
        if (!AppUtil.checkWriteExternalPermission(CommentsActivity.this)) {
            if (realmBasicInfo.getPic_url() != null && !realmBasicInfo.getPic_url().isEmpty()) {
                AppUtil.loadCircularImageUsingLib(CommentsActivity.this, realmBasicInfo.getPic_url().trim(), doctorImage, R.drawable.default_communitypic);
            }
        } else if (realmBasicInfo != null && realmBasicInfo.getProfile_pic_path() != null && !realmBasicInfo.getProfile_pic_path().equals("")) {
            File imgFile = new File(realmBasicInfo.getProfile_pic_path());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                if (myBitmap != null) {
                    doctorImage.setImageBitmap(myBitmap);
                }
            }
        }
        loading_progress.setVisibility(View.VISIBLE);
        doctorId = realmManager.getDoc_id(realm);
        requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
            requestJsonObject.put(RestUtils.CHANNEL_ID, channelID);
            requestJsonObject.put(RestUtils.FEED_TYPE_ID, feed_type_id);
            if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) {
                requestJsonObject.put(RestUtils.TAG_LAST_LIKE_ID, 0);
            } else {
                requestJsonObject.put(RestUtils.TAG_LAST_COMMENT_ID, 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) {
            inputCommentLayout.setVisibility(View.GONE);
        } else {
            inputCommentLayout.setVisibility(View.VISIBLE);
        }
        upshotEventData(feed_type_id, channelID, 0, "", navigation, "", "", " ", false);
        mTitleTextView.setText(postName);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(CommentsActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) {
            if (totalInteractions_count == 1) {
                totalSocialInteractionCount.setText(totalInteractions_count + " like");
            } else {
                if (totalInteractions_count == 0) {
                    totalSocialInteractionCount.setText("likes");
                } else {
                    totalSocialInteractionCount.setText(totalInteractions_count + " likes");
                }
            }
        }
        commentsAdapter = new CommentsAdapter(CommentsActivity.this, commentsList, mRecyclerView, doctorId, navigation, channelID, feed_id, new CommentsEditInterface() {
            @Override
            public void commentsEditInterface(String commented_text, int social_interaction_id, JSONArray attachmentsArray, int itemId) {
                switch (itemId) {
                    case R.id.edit_comment:
                        comment_edit_attachment = attachmentsArray;
                        if (comment_edit_attachment.length() > 0) {
                            isAttachmentContains = true;
                            String edit_attachment_url = comment_edit_attachment.optJSONObject(0).optString(RestUtils.ATTACH_ORIGINAL_URL);
                            if (!edit_attachment_url.isEmpty()) {
                                mRecyclerView.setVisibility(View.GONE);
                                edit_imageView.setVisibility(View.VISIBLE);
                                delete_btn.setVisibility(View.VISIBLE);
                                totalSocialInteractionCount.setVisibility(View.GONE);
                                AppUtil.loadImageUsingGlide(CommentsActivity.this, edit_attachment_url, edit_imageView, R.drawable.default_profilepic);
                            }
                        } else {
                            delete_btn.setVisibility(View.GONE);
                            edit_imageView.setVisibility(View.GONE);
                            isAttachmentContains = false;
                            if (commentsCount > 0) {
                                totalSocialInteractionCount.setVisibility(View.VISIBLE);
                            } else {
                                totalSocialInteractionCount.setVisibility(View.GONE);
                            }
                        }
                        commentEditText.setBackground(getDrawable(R.drawable.commented_edit_text));
                        commentEditText.setPadding(8, 8, 8, 8);
                        send_Button.setVisibility(View.GONE);
                        edit_delete_overlay.setVisibility(View.VISIBLE);
                        edit_delete_overlay.bringToFront();
                        update_cancel_lay.setVisibility(View.VISIBLE);
                        commentedText = commented_text;
                        commentEditText.setText(commentedText);
                        socialInteractionId = social_interaction_id;
                        commentEditText.setSelection(commentEditText.getText().length());
                        break;
                    case R.id.delete_comment:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CommentsActivity.this);
                        alertDialogBuilder.setMessage("Are you sure you want to delete this comment?");
                        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (feed_id == 0) {
                                    return;
                                }
                                JSONObject deleteJsonObject = new JSONObject();
                                try {
                                    deleteJsonObject.put(RestUtils.TAG_USER_ID, doctorId);
                                    deleteJsonObject.put(RestUtils.CHANNEL_ID, channelID);
                                    deleteJsonObject.put(RestUtils.TAG_FEED_ID, feed_id);
                                    deleteJsonObject.put(RestUtils.TAG_SOCIAL_INTERACTION_ID, social_interaction_id);
                                    showProgress();
                                    new VolleySinglePartStringRequest(CommentsActivity.this, Request.Method.POST, RestApiConstants.DELETE_COMMENT, deleteJsonObject.toString(), "DELETE_COMMENT", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            hideProgress();
                                            JSONObject responseObj = null;
                                            try {
                                                responseObj = new JSONObject(successResponse);
                                                if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                    commentsList.remove(commentsAdapter.getSelectedPostion());
                                                    commentsAdapter.notifyDataSetChanged();
                                                    if (responseObj.has(RestUtils.TAG_DATA) && responseObj.optJSONObject(RestUtils.TAG_DATA).has(RestUtils.TAG_SOCIALINTERACTION)) {
                                                        for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                                            listener.updateUI(feed_id, responseObj.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                                        }
                                                        CustomModel.getInstance().changeState(true, responseObj.optJSONObject(RestUtils.TAG_DATA));
                                                    }
                                                    if (commentsList != null && commentsList.size() > 0) {
                                                        befirst_lay.setVisibility(View.GONE);
                                                        mRecyclerView.setVisibility(View.VISIBLE);
                                                    } else {
                                                        befirst_lay.setVisibility(View.VISIBLE);
                                                        mRecyclerView.setVisibility(View.GONE);
                                                    }
                                                    int totalCommentsCount = responseObj.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_COMMENTS_COUNT);
                                                    int commentsCount = totalCommentsCount - commentsList.size();
                                                    if (commentsCount > 0) {
                                                        totalSocialInteractionCount.setVisibility(View.VISIBLE);
                                                        if (totalCommentsCount == 1) {
                                                            totalSocialInteractionCount.setText("Pull down to view " + commentsCount + " comment");
                                                        } else {
                                                            totalSocialInteractionCount.setText("Pull down to view " + commentsCount + " more comments");
                                                        }
                                                    } else {
                                                        totalSocialInteractionCount.setVisibility(View.GONE);
                                                    }
                                                    if (commentsList.size() == 1) {
                                                        loadPreviousComments();
                                                    }
                                                } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                                    String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                                                    if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                                        errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                    }
                                                    Toast.makeText(CommentsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onErrorResponse(String errorResponse) {
                                            hideProgress();
                                            JSONObject jsonObject = null;
                                            try {
                                                jsonObject = new JSONObject(errorResponse);
                                                String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                if (errorMessage != null && !errorMessage.isEmpty()) {
                                                    Toast.makeText(CommentsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).sendSinglePartRequest();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void removeReportInterface(String action, String otherDocName) {
                if (action.equalsIgnoreCase("REMOVE_INTERFACE")) {
                    Dialog dialog = new Dialog(CommentsActivity.this);
                    dialog.setContentView(R.layout.report_spam_dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    RelativeLayout bottomSheetCloseButton = dialog.findViewById(R.id.bottomSheetCloseButton);
                    TextView headingText = dialog.findViewById(R.id.heading_text);
                    TextView textBody = dialog.findViewById(R.id.text_body);
                    LinearLayout llButton = dialog.findViewById(R.id.ll_button);
                    Button btnCancel = dialog.findViewById(R.id.btn_cancel);
                    Button btnRemoveUser = dialog.findViewById(R.id.btn_Remove_User);
                    btnRemoveUser.setVisibility(View.GONE);
                    llButton.setVisibility(View.GONE);
                    headingText.setVisibility(View.GONE);
                    llButton.setVisibility(View.GONE);
                    textBody.setText("You have removed " + otherDocName + " from you list you can't do any further actions.");
                    btnCancel.setOnClickListener(view -> dialog.dismiss());

                    bottomSheetCloseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }

            }
        });

        mRecyclerView.setAdapter(commentsAdapter);
        commentsAdapter.notifyDataSetChanged();

        commentsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) {
                    loadMoreLikes();
                } else {
                    loadPreviousComments();
                }
            }
        });
        if (keyboard_enable) {
            commentEditText.setFocusable(true);

        } else {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
        }

        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() > 0) {
                    send_Button.setImageResource(R.drawable.ic_send_active);
                } else {
                    send_Button.setImageResource(R.drawable.ic_send_disabled);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        send_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                commentText = StringEscapeUtils.escapeJava(commentEditText.getText().toString());
                if (commentEditText.getText().toString().trim().length() == 0) {
                    return;
                }
                if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                    requestdata(commentText, new JSONArray());
                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(CommentsActivity.this, getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(CommentsActivity.this, getString(R.string.mca_uploaded_but_not_verified));
                }
            }
        });
        cancle_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtil.isConnectingToInternet(CommentsActivity.this)) {
                    if (updatedImageFile != null && updatedImageFile.exists()) {
                        boolean del = updatedImageFile.delete();
                        Log.i("deleteFile", "Is file deleted" + del);
                        updatedImageFile = null;
                    }
                    isAttachmentContains = false;
                    commentedText = "";
                    delete_btn.setVisibility(View.GONE);
                    commentEditText.setText("");
                    commentEditText.setBackgroundResource(0);
                    commentEditText.setPadding(0, 0, 0, 0);
                    send_Button.setVisibility(View.VISIBLE);
                    edit_delete_overlay.setVisibility(View.GONE);
                    update_cancel_lay.setVisibility(View.GONE);
                    edit_imageView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (commentsCount > 0) {
                        totalSocialInteractionCount.setVisibility(View.VISIBLE);
                    } else {
                        totalSocialInteractionCount.setVisibility(View.GONE);
                    }
                }
            }
        });
        update_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtil.isConnectingToInternet(CommentsActivity.this)) {
                    if (updatedImageFile != null && updatedImageFile.exists()) {
                        JSONArray editedAttachmentArray = new JSONArray();
                        JSONObject attachmentObj = new JSONObject();
                        currentUpload = 1;
                        showProgress();
                        File attFile = new File(updatedImageFile.getAbsolutePath());

                        //aws upload
                        new AWSHelperClass(CommentsActivity.this, awsKeys, attFile, doctorId, realmBasicInfo.getUserUUID(), new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String url) {
                                if (url != null && !url.isEmpty()) {
                                    String extension = attFile.getAbsolutePath().substring(attFile.getAbsolutePath().lastIndexOf(".") + 1);
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
                                        }
                                        editedAttachmentArray.put(attachmentObj);
                                        requestForUpdateComment(editedAttachmentArray);
                                        if (updatedImageFile != null && updatedImageFile.exists()) {
                                            boolean del = updatedImageFile.delete();
                                            Log.i("deleteFile", "Is file deleted" + del);
                                            updatedImageFile = null;
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ShowSimpleDialog(getResources().getString(R.string.unabletosave), getResources().getString(R.string.trylater));
                                    finish();
                                }
                            }
                        });

                    } else {
                        requestForUpdateComment(comment_edit_attachment);
                    }
                }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtil.isConnectingToInternet(CommentsActivity.this)) {
                    if (updatedImageFile != null && updatedImageFile.exists()) {
                        boolean del = updatedImageFile.delete();
                        Log.i("deleteFile", "Is file deleted" + del);
                        updatedImageFile = null;
                    }
                    delete_btn.setVisibility(View.GONE);
                    if (commentsCount > 0) {
                        totalSocialInteractionCount.setVisibility(View.VISIBLE);
                    } else {
                        totalSocialInteractionCount.setVisibility(View.GONE);
                    }
                    mRecyclerView.setVisibility(View.VISIBLE);
                    edit_imageView.setVisibility(View.GONE);
                    edit_delete_overlay.setVisibility(View.VISIBLE);
                    edit_delete_overlay.bringToFront();
                    comment_edit_attachment = new JSONArray();

                }
            }
        });


        if (AppUtil.isConnectingToInternet(this)) {
            loadComments(false, false);
        } else {
            if (totalInteractions_count > 0) {
                pullToLoad.setVisibility(View.GONE);
            } else {
                befirst_lay.setVisibility(View.VISIBLE);
            }
            if (loading_progress.getVisibility() == View.VISIBLE) {
                loading_progress.setVisibility(View.GONE);
            } else {
                loading_progress.setVisibility(View.VISIBLE);
            }
        }

        mRecyclerView.addItemDecoration(HeaderDecoration.with(mRecyclerView)
                .inflate(R.layout.empty_layout)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, getApplicationContext()));

        commentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void loadMoreLikes() {
        if (lastLikeId != -1) {
            commentsList.add(null);
            commentsAdapter.notifyDataSetChanged();
        }
        try {
            requestJsonObject.put(RestUtils.TAG_LAST_LIKE_ID, lastLikeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (AppUtil.isConnectingToInternet(CommentsActivity.this)) {
            if (lastLikeId != -1) {
                loadComments(true, false);
            }
        }
    }

    private void loadPreviousComments() {
        if (lastSocialInteractionId != -1) {
            commentsList.add(0, null);
            commentsAdapter.notifyDataSetChanged();
        }
        try {
            requestJsonObject.put(RestUtils.TAG_LAST_COMMENT_ID, lastSocialInteractionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (AppUtil.isConnectingToInternet(CommentsActivity.this)) {
            if (lastSocialInteractionId != -1) {
                loadComments(true, false);
            }
        }
    }

    private JSONObject getAWSRequest() {
        Log.i(TAG, "getAWSRequestcredentials()");
        JSONObject requestObj = null;
        try {
            requestObj = new JSONObject();
            requestObj.put(RestUtils.TAG_DOC_ID, realmBasicInfo.getDoc_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj;
    }

    private void requestForUpdateComment(JSONArray attachmentArray) {
        String editedcommentText = commentEditText.getText().toString();
        if ((attachmentArray != null && attachmentArray.length() > 0) || !editedcommentText.isEmpty()) {
            JSONObject editCommentJsonObject = new JSONObject();
            try {
                editCommentJsonObject.put(RestUtils.TAG_USER_ID, doctorId);
                editCommentJsonObject.put(RestUtils.CHANNEL_ID, channelID);
                editCommentJsonObject.put(RestUtils.TAG_FEED_ID, feed_id);
                editCommentJsonObject.put(RestUtils.TAG_SOCIAL_INTERACTION_ID, socialInteractionId);
                editCommentJsonObject.put("comment", editedcommentText);
                editCommentJsonObject.put(RestUtils.ATTACHMENT_DETAILS, attachmentArray);
                showProgress();
                new VolleySinglePartStringRequest(CommentsActivity.this, Request.Method.POST, RestApiConstants.UPDATE_COMMENT, editCommentJsonObject.toString(), "EDIT_COMMENT", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        hideProgress();
                        isAttachmentContains = false;
                        commentedText = "";
                        edit_delete_overlay.setVisibility(View.GONE);
                        edit_imageView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        commentEditText.setText("");
                        update_cancel_lay.setVisibility(View.GONE);
                        send_Button.setVisibility(View.VISIBLE);
                        commentEditText.setBackgroundResource(0);
                        delete_btn.setVisibility(View.GONE);
                        if (updatedImageFile != null && updatedImageFile.exists()) {
                            boolean del = updatedImageFile.delete();
                            Log.i("deleteFile", "Is file deleted" + del);
                        }
                        try {
                            JSONObject commentedObj = new JSONObject(successResponse);
                            if (commentedObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                if (commentsAdapter.getSelectedPostion() != -1) {
                                    if (commentedObj != null && commentedObj.has(RestUtils.TAG_DATA) && commentedObj.optJSONObject(RestUtils.TAG_DATA) != null) {
                                        commentsList.set(commentsAdapter.getSelectedPostion(), commentedObj.optJSONObject(RestUtils.TAG_DATA));
                                        commentsAdapter.notifyDataSetChanged();
                                        for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                            listener.updateUI(feed_id, commentedObj.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                        }
                                        CustomModel.getInstance().changeState(true, commentedObj.optJSONObject(RestUtils.TAG_DATA));
                                    }
                                }

                                int totalCommentsCount = commentedObj.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_COMMENTS_COUNT);
                                int commentsCount = totalCommentsCount - commentsList.size();
                                if (commentsCount > 0) {
                                    totalSocialInteractionCount.setVisibility(View.VISIBLE);
                                    if (totalCommentsCount == 1) {
                                        totalSocialInteractionCount.setText("Pull down to view " + commentsCount + " comment");
                                    } else {
                                        totalSocialInteractionCount.setText("Pull down to view " + commentsCount + " more comments");
                                    }
                                } else {
                                    totalSocialInteractionCount.setVisibility(View.GONE);
                                }
                            } else if (commentedObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                                if (commentedObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = commentedObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(CommentsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        hideProgress();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(errorResponse);
                            String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                Toast.makeText(CommentsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).sendSinglePartRequest();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(CommentsActivity.this, "Please enter comment", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
        if (DashboardUpdatesFragment.dashboardRefreshOnSubscription) {
            loadComments(false, true);
        }
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void scrollListViewToPosition(final int position) {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mRecyclerView.scrollToPosition(position);
            }
        });
    }

    private void loadComments(final boolean isFromOnScroll, final boolean isLoadFromPullToRefresh) {
        String restUrl = RestApiConstants.GET_COMMENTS;
        String TAG = "GET_COMMENTS";
        if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) {
            restUrl = RestApiConstants.GET_Likes;
            TAG = "GET_LIKES";
        }
        new VolleySinglePartStringRequest(CommentsActivity.this, Request.Method.POST, restUrl, requestJsonObject.toString(), TAG, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                loading_progress.setVisibility(View.GONE);
                if (isFromOnScroll) {
                    if (commentsList.contains(null)) {
                        commentsList.remove(null);
                        commentsAdapter.notifyDataSetChanged();
                    }
                }

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(successResponse);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if (isLoadFromPullToRefresh) {
                            commentsList.clear();
                        }
                        //pardha added for likes view
                        if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) {
                            lastLikeId = jsonObject.optJSONObject(RestUtils.TAG_DATA).optInt(RestUtils.TAG_LAST_LIKE_ID);
                            likeJsonArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray("likes");
                            for (int i = 0; i < likeJsonArray.length(); i++) {
                                commentsList.add(likeJsonArray.optJSONObject(i));
                            }
                            if (jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION) != null) {
                                if (jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_LIKES_COUNT) == 1) {
                                    totalSocialInteractionCount.setText("" + jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_LIKES_COUNT) + " like");
                                } else {
                                    if (jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_LIKES_COUNT) == 0) {
                                        totalSocialInteractionCount.setText("No likes");
                                    } else {
                                        totalSocialInteractionCount.setText("" + jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_LIKES_COUNT) + " likes");
                                    }
                                }
                                for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                    listener.updateUI(jsonObject.optJSONObject(RestUtils.TAG_DATA).optInt(RestUtils.FEED_ID), jsonObject.optJSONObject("data").optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                }
                            }
                            CustomModel.getInstance().changeState(true, jsonObject.optJSONObject(RestUtils.TAG_DATA));

                            if (isFromOnScroll) {
                                commentsAdapter.notifyItemInserted(commentsList.size());
                            } else {
                                commentsAdapter.notifyDataSetChanged();
                            }

                            mRecyclerView.setVisibility(View.VISIBLE);
                            befirst_lay.setVisibility(View.GONE);
                        } else {
                            lastSocialInteractionId = jsonObject.optJSONObject(RestUtils.TAG_DATA).optInt(RestUtils.TAG_LAST_COMMENT_ID);
                            commentJsonArray = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.TAG_COMMENTS);
                            feed_id = jsonObject.optJSONObject(RestUtils.TAG_DATA).optInt(RestUtils.FEED_TYPE_ID);
                            commentsAdapter.setFeedId(feed_id);
                            if (isFromOnScroll) {
                                for (int i = 0; i < commentJsonArray.length(); i++) {
                                    commentsList.add(0, commentJsonArray.optJSONObject(i));
                                }
                            } else {
                                for (int i = commentJsonArray.length() - 1; i >= 0; i--) {
                                    commentsList.add(commentJsonArray.optJSONObject(i));
                                }
                            }
                            commentsAdapter.notifyItemInserted(commentsList.size());
                            if (jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION) != null) {

                                for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                    listener.updateUI(jsonObject.optJSONObject(RestUtils.TAG_DATA).optInt(RestUtils.FEED_TYPE_ID), jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                }
                                totalCommentsCount = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_COMMENTS_COUNT);
                                commentsCount = totalCommentsCount - commentsList.size();
                                if (commentsCount > 0) {
                                    totalSocialInteractionCount.setVisibility(View.VISIBLE);
                                } else {
                                    totalSocialInteractionCount.setVisibility(View.GONE);
                                }
                                if (jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_COMMENTS_COUNT) == 1) {
                                    totalSocialInteractionCount.setText("Pull down to view " + commentsCount + " comment");
                                } else {
                                    totalSocialInteractionCount.setText("Pull down to view " + commentsCount + " more comments");
                                }
                            }
                            CustomModel.getInstance().changeState(true, jsonObject.optJSONObject(RestUtils.TAG_DATA));

                            if (isFromOnScroll) {
                                commentsAdapter.notifyDataSetChanged();
                                if (commentsList.size() >= 11) {
                                    scrollListViewToPosition(10);
                                } else {
                                    scrollListViewToPosition(commentsAdapter.getItemCount() - 1);
                                }
                            } else {
                                commentsAdapter.notifyDataSetChanged();
                                scrollListViewToPosition(commentsAdapter.getItemCount() - 1);
                            }
                            commentsAdapter.setLoaded();
                            if (!isFromOnScroll) {
                                if (keyboard_enable) {
                                    commentEditText.setFocusable(true);

                                } else {
                                    getWindow().setSoftInputMode(
                                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                    );
                                }
                            }

                            if (commentsList.size() == 0) {
                                befirst_lay.setVisibility(View.VISIBLE);
                                totalSocialInteractionCount.setText("Comments");
                                mRecyclerView.setVisibility(View.GONE);
                            } else {
                                mRecyclerView.setVisibility(View.VISIBLE);
                                befirst_lay.setVisibility(View.GONE);

                            }
                        }
                    } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        String errorMsg = getResources().getString(R.string.unable_to_connect_server);
                        displayErrorScreen(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {

                if (loading_progress.getVisibility() == View.VISIBLE) {
                    loading_progress.setVisibility(View.GONE);
                }
                if (isFromOnScroll) {
                    commentsList.remove(0);
                    commentsAdapter.notifyDataSetChanged();
                }
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();

    }

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("FeedID", feed_type_id);
                if (channelID != 0) {
                    jsonObject.put("ChannelID", channelID);
                }
                String eventName = "";
                if (navigation != null && navigation.equalsIgnoreCase("fromLikesCount")) {
                    eventName = "FeedLikesDeviceBackTapped";
                } else {
                    eventName = "FeedCommentsDeviceBackTapped";
                }
                AppUtil.logUserUpShotEvent(eventName, AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String commentText = commentEditText.getText().toString();
        if (!TextUtils.isEmpty(commentText)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    if (isAttachmentContains || !commentedText.isEmpty()) {
                        update_cancel_lay.setVisibility(View.GONE);
                        edit_delete_overlay.setVisibility(View.GONE);
                        delete_btn.setVisibility(View.GONE);
                        edit_imageView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        send_Button.setVisibility(View.VISIBLE);
                        commentEditText.setText("");
                        commentEditText.setBackgroundResource(0);
                        commentEditText.setPadding(0, 0, 0, 0);
                        isAttachmentContains = false;
                        commentedText = "";


                    } else {
                        finish();
                    }
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

            customBackButton = true;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("FeedID", feed_type_id);
                if (channelID != 0) {
                    jsonObject.put("ChannelID", channelID);
                }
                String eventName = "";
                if (navigation != null && navigation.equalsIgnoreCase("fromLikesCount")) {
                    eventName = "FeedLikesBackTapped";
                } else {
                    eventName = "FeedCommentsBackTapped";
                }
                AppUtil.logUserUpShotEvent(eventName, AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));

        selectedImagePath = null;
        postName = null;
        tempStringArray = null;
        if (!realm.isClosed())
            realm.close();
    }

    @Override
    public void onUiUpdateForComments(JSONObject mObj) {
        Log.i(TAG, "onUiUpdateForComments()");
    }

    public void selectImage() {
        Log.i(TAG, "selectImage()");
        final CharSequence[] items = {getString(R.string.label_take_photo), getString(R.string.label_choose_library)};
        AlertDialog.Builder builder = new AlertDialog.Builder(CommentsActivity.this);
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
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                mCapturedImageURI = getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                takePictureIntent
                        .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                launchCameraActivityResults.launch(takePictureIntent);
            }
        } else {
            Toast.makeText(
                            getApplicationContext(),
                            String.format(getString(R.string.attachment_limit_exceeded), 1),
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void chooseFromLibrary() {
        Log.i(TAG, "chooseFromLibrary()");
        Intent intent = new Intent(CommentsActivity.this, ImageSelectActivity.class);
        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1);
        launchLibraryActivityResults.launch(intent);
    }

    public void alertMessage(String permissionType) {
        Log.i(TAG, "alertMessage()");
        AlertDialog.Builder builder = new AlertDialog.Builder(CommentsActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, getString(R.string._onActivityResult));
        if (resultCode == RESULT_OK) {
            App_Application.setCurrentActivity(CommentsActivity.this);
            if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri, CommentsActivity.this);
                resizedBitmap = AppUtil.bitmapCompression(selectedImagePath);
            }
        } else if (resultCode == 18) {
            Log.i(TAG, "previewdata");
            if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                String value = data.getExtras().getString("imagepath");
                String text = data.getExtras().getString("commentText");

                JSONArray attachmentArray = new JSONArray();
                if (value != null && !value.isEmpty()) {
                    currentUpload = 1;
                    String element = value;
                    final JSONObject attachmentObj = new JSONObject();
                    //attFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.Whitecoats/Comment_images/" + element);
                    attFile = AppUtil.getExternalStoragePathFile(this, ".Whitecoats/Comment_images/" + element);
                    if (element.length() == 0) {
                        //mProgressDialog.dismiss();
                        Toast.makeText(mContext, "Unable to upload due to corrupted image file", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showProgress();

                    new AWSHelperClass(CommentsActivity.this, awsKeys, attFile, doctorId, realmBasicInfo.getUserUUID(), new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String url) {
                            if (url != null && !url.isEmpty()) {
                                String extension = attFile.getAbsolutePath().substring(attFile.getAbsolutePath().lastIndexOf(".") + 1);
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
                                    }
                                    attachmentArray.put(attachmentObj);
                                    commentText = text;
                                    requestdata(commentText, attachmentArray);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ShowSimpleDialog(getResources().getString(R.string.unabletosave), getResources().getString(R.string.trylater));
                                finish();
                            }
                        }
                    });
                }
            } else if (AppUtil.getUserVerifiedStatus() == 1) {
                AppUtil.AccessErrorPrompt(CommentsActivity.this, getString(R.string.mca_not_uploaded));
            } else if (AppUtil.getUserVerifiedStatus() == 2) {
                AppUtil.AccessErrorPrompt(CommentsActivity.this, getString(R.string.mca_uploaded_but_not_verified));
            }

        }
    }


    private void createDirIfNotExists() {
        myDirectory = AppUtil.getExternalStoragePathFile(this, ".Whitecoats");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        folder = new File(myDirectory + "/Comment_images");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
    }

    public void cleanUpPostImages(ArrayList<String> attachmentsList) {
        Log.i(TAG, "cleanUpPostImages()");
        int size = attachmentsList.size();
        for (int i = 0; i < size; i++) {
            Log.i(TAG, "Position " + i + 1 + " - " + attachmentsList.get(i));
            File file = AppUtil.getExternalStoragePathFile(this, ".Whitecoats/Post_images/" + attachmentsList.get(i));
            boolean isDeleted = file.delete();
            Log.i(TAG, "Position " + i + 1 + " : isDeleted - " + isDeleted);
        }
    }

    public void requestdata(String previewText, JSONArray attachmentArray) {
        if (isConnectingToInternet()) {

            commentEditText.setText("");
            JSONObject commentTextjsonObject = new JSONObject();
            JSONObject socialInteractionjsonObject = new JSONObject();
            try {
                socialInteractionjsonObject.put(RestUtils.TAG_TYPE, "Comment");
                socialInteractionjsonObject.put("comment", previewText);
                socialInteractionjsonObject.put("attachment_details", attachmentArray);
                commentTextjsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
                commentTextjsonObject.put(RestUtils.CHANNEL_ID, channelID);
                commentTextjsonObject.put(RestUtils.FEED_TYPE_ID, feed_type_id);

                commentTextjsonObject.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionjsonObject);

                showProgress();
                new VolleySinglePartStringRequest(CommentsActivity.this, Request.Method.POST, RestApiConstants.SOCIAL_INTERACTIONS, commentTextjsonObject.toString().replaceAll("(\\\\r\\\\n|\\\\n)", "\\n"), "GET_SOCIAL_INTERACTIONS", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        hideProgress();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(successResponse);
                            if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                if (attFile != null && attFile.exists()) {
                                    boolean del = attFile.delete();
                                    Log.i("deleteFile", "Is file deleted" + del);
                                }
                                requestJsonObject = new JSONObject();
                                try {
                                    requestJsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
                                    requestJsonObject.put(RestUtils.CHANNEL_ID, channelID);
                                    requestJsonObject.put(RestUtils.FEED_TYPE_ID, feed_type_id);
                                    if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) {
                                        requestJsonObject.put(RestUtils.TAG_LAST_LIKE_ID, 0);
                                    } else {
                                        requestJsonObject.put(RestUtils.TAG_LAST_COMMENT_ID, 0);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                loadComments(false, true);
                                CustomModel.getInstance().changeState(true, jsonObject.optJSONObject(RestUtils.TAG_DATA));
                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                String errorMsg = getResources().getString(R.string.unable_to_connect_server);
                                if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(CommentsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        hideProgress();
                        Toast.makeText(CommentsActivity.this, getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();

                    }
                }).sendSinglePartRequest();


            } catch (JSONException e) {
                e.printStackTrace();
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

    public static int getTotalCommnetsCount() {
        return totalCommentsCount;
    }

    @Override
    public void onCompressCompleted(String[] filePaths) {
        Log.i(TAG, "onCompressCompleted(String[] filePaths)");
        int tempArrayLength = tempStringArray.length;
        int length = filePaths.length;

    }

    @Override
    public void onCompressCompletedWithArrayList(ArrayList<String> filePaths) {
        Log.i(TAG, "onCompressCompletedWithArrayList(ArrayList<String> filePaths)");
        if (isAttachmentContains || (commentedText != null && !commentedText.isEmpty())) {
            edit_imageView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            edit_delete_overlay.setVisibility(View.GONE);
            delete_btn.setVisibility(View.VISIBLE);
            updatedImageFile = AppUtil.getExternalStoragePathFile(this, ".Whitecoats/Comment_images/" + filePaths.get(0));
            Uri uri = Uri.fromFile(updatedImageFile);
            edit_imageView.setImageURI(uri);
            commentText = commentEditText.getText().toString();
            totalSocialInteractionCount.setVisibility(View.GONE);

        } else {
            commentText = commentEditText.getText().toString();
            Intent intent = new Intent(CommentsActivity.this, CommentsPreviewActivity.class);
            intent.putExtra(ConstantsCustomGallery.INTENT_PREVIEW_IMAGE, filePaths.get(0));
            intent.putExtra("posttitle", postName);
            intent.putExtra("commentText", commentText);
            intent.putExtra("channel_id", channelID);
            intent.putExtra("feed_id", feed_id);
            launchImagesPreviewActivityResults.launch(intent);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.CAMERA_PERMISSION_REQUEST_CODE:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                boolean camera = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    perms.put(Manifest.permission.READ_MEDIA_IMAGES, PackageManager.PERMISSION_GRANTED);
                    // Fill with results
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    boolean mediaImage = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                        createDirIfNotExists();
                        cameraClick();
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                            perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                        if (!camera && !mediaImage) {
                            AppConstants.neverAskAgain_Camera = true;
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && !mediaImage) {
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
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
