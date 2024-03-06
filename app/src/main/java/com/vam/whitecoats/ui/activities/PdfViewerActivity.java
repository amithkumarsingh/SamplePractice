package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;

import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import io.realm.Realm;

/**
 * Created by venuv on 10/19/2016.
 */
public class PdfViewerActivity extends BaseActionBarActivity implements DownloadFile.Listener {

    private TextView mTitleTextView, nextBtn, mPageCount;
    private ProgressBar loading_progress;
    private String navigation;
    private RealmManager realmManager;
    private Realm realm;
    private int docID;
    private int feed_id, channel_id;
    private ViewGroup root;
    PDFPagerAdapter pdfPagerAdapter;
    RemotePDFViewPager remotePDFViewPager;
    public static final String TAG = PdfViewerActivity.class.getSimpleName();
    private Handler mHandler = new Handler();
    private int mProgressStatus = 0;
    private boolean isFileFromLocalStorage;
    private PDFViewPager pdfViewPager;
    private boolean customBackButton = false;
    private String attachment_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view);
        root = (ViewGroup) findViewById(R.id.rootLayout);
        Bundle bundle = getIntent().getExtras();
        String attachment_name = bundle.getString(RestUtils.ATTACHMENT_NAME);
        String attachment_original_url = bundle.getString(RestUtils.ATTACH_ORIGINAL_URL);
        attachment_type = bundle.getString(RestUtils.ATTACHMENT_TYPE);
        isFileFromLocalStorage = bundle.getBoolean("loadFromLocal", false);
        mInflater = LayoutInflater.from(this);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_attachements, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        mPageCount = findViewById(R.id.page_count);
        nextBtn = (TextView) mCustomView.findViewById(R.id.next_button);
        navigation = bundle.getString(RestUtils.NAVIGATATION);
        if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_POST_BTN_ENABLE)) {
            nextBtn.setVisibility(View.VISIBLE);
            nextBtn.setText("VIEW POST");
        } else {
            nextBtn.setVisibility(View.GONE);
        }
        docID = realmManager.getDoc_id(realm);
        feed_id = bundle.getInt(RestUtils.TAG_FEED_ID);
        channel_id = bundle.getInt(RestUtils.CHANNEL_ID);
        upshotEventData(feed_id, channel_id, 0, "", "", "", attachment_type, "", false);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject requestJsonObject = new JSONObject();
                try {
                    requestJsonObject.put(RestUtils.TAG_DOC_ID, docID);
                    requestJsonObject.put(RestUtils.CHANNEL_ID, channel_id);
                    requestJsonObject.put(RestUtils.FEED_ID, feed_id);
                    showProgress();
                    new VolleySinglePartStringRequest(PdfViewerActivity.this, Request.Method.POST, RestApiConstants.FEED_FULL_VIEW_UPDATED, requestJsonObject.toString(), "IMAGE_VIEWER", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            hideProgress();
                            try {
                                JSONObject feedData = new JSONObject(successResponse);
                                JSONObject completedFeedObj = feedData.optJSONObject(RestUtils.TAG_DATA);
                                JSONObject feedObject = completedFeedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
                                String channelName = completedFeedObj.optString(RestUtils.FEED_PROVIDER_NAME);
                                String feedProviderType = completedFeedObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
                                String feedType = feedObject.optString(RestUtils.FEED_TYPE);
                                int channelId = completedFeedObj.optInt(RestUtils.CHANNEL_ID);
                                Intent intent = new Intent();
                                if (feedType.equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                                    intent.setClass(getApplicationContext(), ContentFullView.class);
                                    intent.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelName);
                                    intent.putExtra(RestUtils.TAG_CONTENT_OBJECT, completedFeedObj.toString());
                                    //intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, true);
                                } else if (feedType.equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
                                    intent.setClass(getApplicationContext(), JobFeedCompleteView.class);
                                } else {
                                    intent.setClass(getApplicationContext(), FeedsSummary.class);
                                }
                                if (feedProviderType.equalsIgnoreCase("Network")) {
                                    intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, true);
                                } else {
                                    intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
                                }
                                intent.putExtra(RestUtils.CHANNEL_NAME, channelName);
                                intent.putExtra("feed_obj", completedFeedObj.toString());
                                intent.putExtra("position", 0);
                                intent.putExtra(RestUtils.CHANNEL_ID, channelId);
                                intent.putExtra("fromMedia", true);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        loading_progress = (ProgressBar) findViewById(R.id.loading_progress);
        mTitleTextView.setText(attachment_name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);

        if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {

            //download and store to file system
            //new DownloadFileFromURL().execute(attachment_original_url);
            if (isFileFromLocalStorage) {
                loading_progress.setVisibility(View.GONE);
                pdfViewPager = new PDFViewPager(this, attachment_original_url);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pagerView);
                pdfViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                linearLayout.addView(pdfViewPager);
                pdfViewPager.setHorizontalScrollBarEnabled(true);
                pdfViewPager.setOffscreenPageLimit(2);
                mPageCount.setText(String.valueOf(pdfViewPager.getCurrentItem() + 1 + "/" + pdfViewPager.getAdapter().getCount()));

                pdfViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        Log.e(TAG, "onPageScrolled - " + position);
                    }

                    @Override
                    public void onPageSelected(int position) {
                        Log.e(TAG, "onPageSelected - " + position);
                        mPageCount.setText(String.valueOf(position + 1 + "/" + pdfViewPager.getAdapter().getCount()));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            } else {
                remotePDFViewPager =
                        new RemotePDFViewPager(mContext, attachment_original_url, this);
            }
        }
    }

    @Override
    protected void setCurrentActivity() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    jsonObject.put("FeedID", feed_id);
                    jsonObject.put("ChannelID", channel_id);
                    jsonObject.put("AttachmentType", attachment_type);
                    AppUtil.logUserUpShotEvent("MediaPreviewBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("ondestroy", "message");
        try {
            pdfPagerAdapter.close();
            if (pdfViewPager != null) {
                ((PDFPagerAdapter) pdfViewPager.getAdapter()).close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        loading_progress.setVisibility(View.GONE);
        pdfPagerAdapter = new PDFPagerAdapter(this, destinationPath);
        if (pdfPagerAdapter.getCount() > 1) {
            Toast.makeText(this, R.string.swipe_right_toread, Toast.LENGTH_LONG).show();
        }
        remotePDFViewPager.setAdapter(pdfPagerAdapter);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pagerView);
        remotePDFViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(remotePDFViewPager);
//        setContentView(remotePDFViewPager);
        remotePDFViewPager.setHorizontalScrollBarEnabled(true);
        remotePDFViewPager.setOffscreenPageLimit(2);
        mPageCount.setText(String.valueOf(remotePDFViewPager.getCurrentItem() + 1 + "/" + pdfPagerAdapter.getCount()));

        remotePDFViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "onPageScrolled - " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected - " + position);
                mPageCount.setText(String.valueOf(position + 1 + "/" + pdfPagerAdapter.getCount()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        doprogress();
    }

    public void doprogress() {

        new Thread(new Runnable() {
            public void run() {
                final int percentage = 0;
                while (mProgressStatus < 100) {
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            loading_progress.setProgress(mProgressStatus);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();

                    }
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("FeedID", feed_id);
                jsonObject.put("ChannelID", channel_id);
                jsonObject.put("AttachmentType", attachment_type);
                AppUtil.logUserUpShotEvent("MediaPreviewDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }
}
