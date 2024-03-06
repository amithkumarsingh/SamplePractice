package com.vam.whitecoats.ui.activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.WebViewUrlModel;
import com.vam.whitecoats.databinding.ActivityWebViewBinding;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FullViewModelWebClient;
import com.vam.whitecoats.viewmodel.WebViewViewModel;

public class WebViewActivity extends BaseActionBarActivity {
    private WebViewViewModel viewModel;
    private TextView mTitleTextView;
    private Bundle bundle;
    private String external_link;
    private WebView url_web_view;
    private RelativeLayout contentLayout;
    private LinearLayout webLinearLayout;
    private long downloadID;


    // using broadcast method
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(WebViewActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebViewUrlModel obj = new WebViewUrlModel();
        bundle=getIntent().getExtras();
        // using broadcast method
        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        external_link=bundle.getString("EXTERNAL_LINK");
        if(!external_link.startsWith("https://") && !external_link.startsWith("http://"))
        {
            external_link="http://"+external_link;
        }
        obj.setUrl(external_link);
        viewModel= ViewModelProviders.of(this).get(WebViewViewModel.class);
        ActivityWebViewBinding databindingutils = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        databindingutils.setViewModel(viewModel);
        contentLayout=databindingutils.webContentLayout;
        webLinearLayout=databindingutils.webLinearLayout;
        url_web_view=databindingutils.webViewUrl;
        url_web_view.getSettings().setJavaScriptEnabled(true);
        url_web_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            url_web_view.getSettings().setSafeBrowsingEnabled(false);
        }
        url_web_view.getSettings().setLoadWithOverviewMode(true);
        url_web_view.getSettings().setUseWideViewPort(true);
        url_web_view.getSettings().setAllowFileAccess(true);
        url_web_view.getSettings().setDomStorageEnabled(true);
        url_web_view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        url_web_view.setWebChromeClient(new FullViewModelWebClient(this));
        if(AppUtil.isConnectingToInternet(WebViewActivity.this)) {
            viewModel.setWebViewUrlModel(obj);
        }
        viewModel.displayLoader(true);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        mTitleTextView.setVisibility(View.GONE);
        actionBar.setDisplayHomeAsUpEnabled(true);

        url_web_view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if(URLUtil.isValidUrl(request.getUrl().toString())) {
                    if(request.getUrl().toString().endsWith(".pdf")){
                        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString())));
                        // downloading pdf using download manager
                        downloadPDFfile(request.getUrl().toString());
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, request);
                }else if(request.getUrl().toString().startsWith("www.")){
                    return super.shouldOverrideUrlLoading(view,"http://"+request.getUrl().toString());
                } else{
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(external_link));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                viewModel.displayLoader(false);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                viewModel.displayLoader(false);
                super.onReceivedError(view, request, error);
            }
        });
        //databindingutils.webViewUrl.loadUrl(external_link);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    private void downloadPDFfile(String pdfUrl) {
        // Create request for android download manager
        String fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(pdfUrl));
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        downloadRequest.setTitle(fileName);
        downloadRequest.setDescription("Downloading");

        downloadRequest.setAllowedOverRoaming(true);
        downloadRequest.allowScanningByMediaScanner();
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);
        downloadID=downloadManager.enqueue(downloadRequest);
        // using query method
        /*boolean finishDownload = false;
        int progress;
        while (!finishDownload) {
            Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(downloadID));
            if (cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (status) {
                    case DownloadManager.STATUS_FAILED: {
                        finishDownload = true;
                        break;
                    }
                    case DownloadManager.STATUS_PAUSED:
                        break;
                    case DownloadManager.STATUS_PENDING:
                        break;
                    case DownloadManager.STATUS_RUNNING: {
                        final long total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        if (total >= 0) {
                            final long downloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            progress = (int) ((downloaded * 100L) / total);
                            // if you use downloadmanger in async task, here you can use like this to display progress.
                            // Don't forget to do the division in long to get more digits rather than double.
                            //  publishProgress((int) ((downloaded * 100L) / total));
                        }
                        break;
                    }
                    case DownloadManager.STATUS_SUCCESSFUL: {
                        progress = 100;
                        // if you use aysnc task
                        // publishProgress(100);
                        finishDownload = true;
                        Toast.makeText(WebViewActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }*/

    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // using broadcast method
        unregisterReceiver(onDownloadComplete);
    }
}

