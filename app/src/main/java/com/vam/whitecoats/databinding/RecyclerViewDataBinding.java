package com.vam.whitecoats.databinding;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.NotificationInfo;
import com.vam.whitecoats.ui.adapters.FeedsNotificationsAdapter;
import com.vam.whitecoats.utils.AppUtil;

import java.util.List;

public class RecyclerViewDataBinding {
    @BindingAdapter({"app:adapter", "app:data"})
    public void bind(RecyclerView recyclerView, FeedsNotificationsAdapter adapter, List<NotificationInfo> data) {
        recyclerView.setAdapter(adapter);
        adapter.updateData(data);
    }

    @BindingAdapter("app:countText")
    public static void exploreCountText(TextView textView,String count){
        int countInt = Integer.parseInt(count);
        if(countInt <= 0){
            textView.setVisibility(View.GONE);
        }
        else if(countInt > 0 && countInt < 100){
            textView.setVisibility(View.VISIBLE);
            textView.setText(count);
        }
        else{
            textView.setVisibility(View.VISIBLE);
            textView.setText("99+");
        }
    }
    @BindingAdapter("app:exploreImage")
    public static void exploreItemImage(ImageView imageView, Category exploreItem){

        if(imageView != null && exploreItem != null){
            AppUtil.loadImageUsingGlide(imageView.getContext(),exploreItem.getImageUrl(),imageView,R.drawable.default_channel_logo);
        }
    }

    @BindingAdapter("app:notificationImage")
    public static void notificationItemImage(ImageView imageView,NotificationInfo notificationInfo){
        if(imageView!=null && notificationInfo!=null){
            if(notificationInfo.getCMsgType()==5){
                if(notificationInfo.getType().equalsIgnoreCase("survey")){
                    if(notificationInfo.isNotificationRead()){
                        imageView.setImageResource(R.drawable.notification_survey_read_icon);
                    }else{
                        imageView.setImageResource(R.drawable.notification_survey_unread_icon);
                    }
                }else{
                    if(notificationInfo.isNotificationRead()){
                        imageView.setImageResource(R.drawable.notification_case_read_icon);
                    }else{
                        imageView.setImageResource(R.drawable.notification_case_unread_icon);
                    }
                }
            }else if(notificationInfo.getCMsgType()==6){
                if(notificationInfo.isNotificationRead()){
                    imageView.setImageResource(R.drawable.notification_article_read_icon);
                }else{
                    imageView.setImageResource(R.drawable.notification_article_unread_icon);
                }
            }
            else if(notificationInfo.getCMsgType()==8){
                if(notificationInfo.isNotificationRead()){
                    imageView.setImageResource(R.drawable.notification_like_read_icon);
                }else{
                    imageView.setImageResource(R.drawable.notification_like_unread_icon);
                }
            }
            else if(notificationInfo.getCMsgType()==9){
                if(notificationInfo.isNotificationRead()){
                    imageView.setImageResource(R.drawable.notification_comment_read_icon);
                }else{
                    imageView.setImageResource(R.drawable.notification_comment_unread_icon);
                }
            }else{
                imageView.setImageResource(R.drawable.notification_article_unread_icon);
            }
        }

    }

    @BindingAdapter("app:notificationItemBackground")
    public static void notificationItemBackgroundColor(ViewGroup layout,boolean readStatus){
        if(layout!=null) {
            if (readStatus) {
                layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            } else {
                layout.setBackgroundColor(Color.parseColor("#E7F6F1"));
            }
        }
    }

    @BindingAdapter("app:customVisibility")
    public static void listVisibility(View view,boolean isVisible){
        if(isVisible){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("app:loadUrl")
    public static void loadWebUrl(WebView view, String url){
        view.loadUrl(url);
    }
}
