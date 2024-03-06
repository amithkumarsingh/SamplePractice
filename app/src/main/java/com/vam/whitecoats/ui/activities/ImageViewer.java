package com.vam.whitecoats.ui.activities;

/**
 * Created by lokeshl on 11/1/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.droidninja.imageeditengine.ImageEditor;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.ConstsCore;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CaseRoomAttachmentsInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.ImageFullView_Adapter;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;

public class ImageViewer extends BaseActionBarActivity {
    public static final String TAG = ImageViewer.class.getSimpleName();
    TextView mTitleTextView, nextBtn;
    ImageView editIcon, delIcon;
    String pic, caseroom_id, attachname, profile_image;
    CaseRoomAttachmentsInfo caseRoomAttachmentsInfo;
    private Realm realm;
    boolean flag;
    boolean isOptionsEnable;
    private RealmManager realmManager;
    // We can be in one of these 3 states
    static final int NONE = 0;
    // Remember some things for zooming
    PointF start = new PointF();
    private boolean loadImageUsingPicasso, profileloadUsingPicasso;
    private String navigation;
    private Bundle bundle;
    private int docID;
    private String feed_id, channel_id;
    ViewPager mViewPager;
    private int selectedImagePosition;
    private RealmBasicInfo realmBasicInfo;
    private boolean isFromCreatePost;
    private boolean customBackButton=false;
    private String attachement_type;
    private int channelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_imagedisplay);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        realmBasicInfo = realmManager.getRealmBasicInfo(realm);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_attachements, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        nextBtn = (TextView) mCustomView.findViewById(R.id.next_button);
        editIcon = (ImageView) mCustomView.findViewById(R.id.editIcon);
        delIcon = (ImageView) mCustomView.findViewById(R.id.delIcon);
        //profileView = (ImageView) mCustomView.findViewById(R.id.profileview);
        editIcon.setVisibility(View.VISIBLE);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            navigation = bundle.getString(RestUtils.NAVIGATATION);
        }
        if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_POST_BTN_ENABLE)) {
            nextBtn.setVisibility(View.VISIBLE);
            nextBtn.setText("VIEW POST");
        } else {
            nextBtn.setVisibility(View.GONE);
        }
        docID = realmManager.getDoc_id(realm);
        if(bundle!=null){
            feed_id = bundle.getString(RestUtils.TAG_FEED_ID);
            channel_id = bundle.getString(RestUtils.CHANNEL_ID);
            attachement_type=bundle.getString(RestUtils.ATTACHMENT_TYPE);
            channelId=bundle.getInt(RestUtils.CHANNEL_ID);
        }
        if(navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_POST_BTN_ENABLE)) {
            upshotEventData(Integer.parseInt(feed_id), Integer.parseInt(channel_id), 0, "", "", "", attachement_type, "",false);
        }
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject requestJsonObject = new JSONObject();
                try {
                    if (AppUtil.isConnectingToInternet(ImageViewer.this)) {
                        requestJsonObject.put(RestUtils.TAG_DOC_ID, docID);
                        requestJsonObject.put(RestUtils.CHANNEL_ID, channel_id);
                        requestJsonObject.put(RestUtils.FEED_ID, feed_id);
                        showProgress();
                        new VolleySinglePartStringRequest(ImageViewer.this, Request.Method.POST, RestApiConstants.FEED_FULL_VIEW_UPDATED, requestJsonObject.toString(), "IMAGE_VIEWER", new OnReceiveResponse() {
                            @Override
                            public void onSuccessResponse(String successResponse) {
                                hideProgress();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(successResponse);
                                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                        JSONObject completeResponseObj = new JSONObject(successResponse);
                                        JSONObject feedDataObj = completeResponseObj.optJSONObject(RestUtils.TAG_DATA);
                                        JSONObject feedObject = feedDataObj.optJSONObject(RestUtils.TAG_FEED_INFO);
                                        String channelName = feedDataObj.optString(RestUtils.FEED_PROVIDER_NAME);
                                        String feedProviderType = feedDataObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
                                        String feedType = feedObject.optString(RestUtils.FEED_TYPE);
                                        int channelId = feedDataObj.optInt(RestUtils.CHANNEL_ID);
                                        Intent intent = new Intent();
                                        if (feedType.equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                                            intent.setClass(getApplicationContext(),ContentFullView.class);
                                            intent.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelName);
                                            intent.putExtra(RestUtils.TAG_CONTENT_OBJECT, feedDataObj.toString());
                                            //intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                                        }
                                        else if(feedType.equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)){
                                            intent.setClass(getApplicationContext(),JobFeedCompleteView.class);
                                        }else{
                                            intent.setClass(getApplicationContext(),FeedsSummary.class);
                                        }
                                        if (feedProviderType.equalsIgnoreCase("Network")) {
                                            intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL,true);
                                        } else {
                                            intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL,false);
                                        }
                                        intent.putExtra(RestUtils.CHANNEL_NAME, channelName);
                                        intent.putExtra(RestUtils.TAG_FEED_OBJECT, feedDataObj.toString());
                                        intent.putExtra(RestUtils.TAG_POSITION, 0);
                                        intent.putExtra(RestUtils.CHANNEL_ID, channelId);
                                        intent.putExtra("fromMedia", true);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                        displayErrorScreen(successResponse);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(String errorResponse) {
                                hideProgress();
                                displayErrorScreen(errorResponse);
                            }
                        }).sendSinglePartRequest();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        delIcon.setVisibility(View.VISIBLE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);

        mViewPager = (ViewPager) findViewById(R.id.gallery);
        Intent intent = getIntent();
        pic = intent.getStringExtra(RestUtils.TAG_FILE_PATH);
        profile_image = intent.getStringExtra("profilefilepath");
        ArrayList<String> list = intent.getStringArrayListExtra("URLList");
        caseroom_id = intent.getStringExtra("caseroomId");
        attachname = intent.getStringExtra("attachname");
        selectedImagePosition = intent.getIntExtra("selectedImagePostion", -1);
        isOptionsEnable = intent.getBooleanExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, true);
        loadImageUsingPicasso = intent.getBooleanExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
        profileloadUsingPicasso = intent.getBooleanExtra("profileloadUsingPicasso", false);
        if (isOptionsEnable) {
            editIcon.setVisibility(View.VISIBLE);
            delIcon.setVisibility(View.VISIBLE);
        } else {
            editIcon.setVisibility(View.GONE);
            delIcon.setVisibility(View.GONE);
        }

        String imagePositionText = intent.getStringExtra(RestUtils.TAG_IMAGE_POSITION);
        mTitleTextView.setText(imagePositionText);
        flag = intent.getBooleanExtra("isChat", false);
        isFromCreatePost = intent.getBooleanExtra("isPost", false);
        if(isFromCreatePost){
            String navigatiedFrom = "createPost";
            upshotEventData(0,channelId,0,"",navigatiedFrom,"","","",false);
        }
        if (!flag && !isFromCreatePost)
            caseRoomAttachmentsInfo = (CaseRoomAttachmentsInfo) intent.getSerializableExtra("attachment");
        if (list != null && list.size() > 0) {
            ImageFullView_Adapter imageFullView_adapter = new ImageFullView_Adapter(this, list, loadImageUsingPicasso);
            mViewPager.setAdapter(imageFullView_adapter);
            if (selectedImagePosition != -1) {
                mViewPager.setCurrentItem(selectedImagePosition);
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "onPageScrolled - " + position);
                if (mViewPager.getAdapter().getCount() > 1) {
                    mTitleTextView.setText((position + 1) + " of " + mViewPager.getAdapter().getCount());
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected - " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged - " + state);
            }
        });


        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageViewer.this, CaseRoomEditAttachmentActivity.class);
                File file = null;
                String foldername = null;
                if (isFromCreatePost) {
                    CreatePostActivity.editedImagePosition=selectedImagePosition;
                    foldername = "Post_images";
                    new ImageEditor.Builder(ImageViewer.this, attachname)
                            .open();
                    //finish();
                } else {
                    if (flag) {
                        foldername = "Chat_images";
                    } else {
                        foldername = "CaseRoom_Pic";
                    }

                    new ImageEditor.Builder(ImageViewer.this, mContext.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/" + foldername + "/" + attachname)
                            .open();
                }


            }
        });
        delIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFromCreatePost) {
                    CreatePostActivity.isDeletedFromImageFullView = true;
                    Toast.makeText(ImageViewer.this, "Image deleted successfully!", Toast.LENGTH_SHORT).show();
                    ConstsCore.POST_ATTACHMENT_COUNT -= 1;
                    finish();
                } else {
                    realmManager.deleteCaseRoomAttachmentsInfo(realm, caseRoomAttachmentsInfo);
                    Toast.makeText(ImageViewer.this, "Image deleted successfully!", Toast.LENGTH_SHORT).show();
                    if (flag)
                        ConstsCore.ATTACHMENT_COUNT_CHAT -= 1;
                    else
                        ConstsCore.ATTACHMENT_COUNT -= 1;
                    /*
                     * Update the main DatSet
                     */
                    List<String> listMain = new ArrayList<String>(Arrays.asList(ConstsCore.selectedImages));
                    listMain.remove(attachname);
                    ConstsCore.selectedImages = listMain.toArray(new String[0]);
                    ConstsCore.ATTACHMENT_COUNT_CHAT -= 1;
                    finish();
                }
            }
        });
    }

    @Override
    protected void setCurrentActivity() {

    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(isFromCreatePost){
                    customBackButton = true;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID",realmManager.getUserUUID(realm));
                        jsonObject.put("ChannelID",channelId);
                        AppUtil.logUserUpShotEvent("EditAttachmentBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_POST_BTN_ENABLE)) {
                    customBackButton = true;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID",realmManager.getUserUUID(realm));
                        jsonObject.put("FeedID", Integer.parseInt(feed_id));
                        jsonObject.put("ChannelID", Integer.parseInt(channel_id));
                        jsonObject.put("AttachmentType", attachement_type);
                        AppUtil.logUserUpShotEvent("MediaPreviewBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(isFromCreatePost){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                jsonObject.put("ChannelID", channelId);
                AppUtil.logUserUpShotEvent("EditAttachmentDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_POST_BTN_ENABLE)){
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    jsonObject.put("FeedID", Integer.parseInt(feed_id));
                    jsonObject.put("ChannelID", Integer.parseInt(channel_id));
                    jsonObject.put("AttachmentType", attachement_type);
                AppUtil.logUserUpShotEvent("MediaPreviewDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (!realm.isClosed())
            realm.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageEditor.RC_IMAGE_EDITOR) {
                String editedImagePath = data.getStringExtra(ImageEditor.EXTRA_EDITED_PATH);
                Intent intent = new Intent();
                intent.putExtra(ImageEditor.EXTRA_EDITED_PATH, editedImagePath);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        }
    }
}
