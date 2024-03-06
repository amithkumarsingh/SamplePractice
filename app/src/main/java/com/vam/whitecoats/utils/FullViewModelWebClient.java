package com.vam.whitecoats.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;

public class FullViewModelWebClient extends WebChromeClient {
    private Context context;
    private View mCustomView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    protected FrameLayout mFullscreenContainer;
    private int mOriginalOrientation;
    private int mOriginalSystemUiVisibility;

    public FullViewModelWebClient(Context _context){
        this.context=_context;
    }
    public Bitmap getDefaultVideoPoster()
    {
        if (context == null) {
            return null;
        }
        return BitmapFactory.decodeResource(context.getApplicationContext().getResources(), 2130837573);
    }

    public void onHideCustomView()
    {
        ((FrameLayout)((Activity)context).getWindow().getDecorView()).removeView(this.mCustomView);
        this.mCustomView = null;
        ((Activity)context).getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
        ((Activity)context).setRequestedOrientation(this.mOriginalOrientation);
        this.mCustomViewCallback.onCustomViewHidden();
        this.mCustomViewCallback = null;
    }

    public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
    {
        if (this.mCustomView != null)
        {
            onHideCustomView();
            return;
        }
        this.mCustomView = paramView;
        this.mOriginalSystemUiVisibility = ((Activity)context).getWindow().getDecorView().getSystemUiVisibility();
        this.mOriginalOrientation = ((Activity)context).getRequestedOrientation();
        this.mCustomViewCallback = paramCustomViewCallback;
        ((FrameLayout)((Activity)context).getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
        ((Activity)context).getWindow().getDecorView().setSystemUiVisibility(3846);
    }
}
