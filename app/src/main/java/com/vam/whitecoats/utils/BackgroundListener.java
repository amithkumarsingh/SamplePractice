package com.vam.whitecoats.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


public class BackgroundListener implements LifecycleObserver {
    private Context context;
    public BackgroundListener(Context mContext) {
        context=mContext;
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onBackground() {
        /*QBChatService.getInstance().logout(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                QBChatService.getInstance().destroy();
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });*/
    }
}
