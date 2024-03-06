package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tejaswini on 01-05-2017.
 */

public class ThumbnailViewAdapter extends RecyclerView.Adapter<ThumbnailViewAdapter.SingleItemRowHolder> {
    private final int width;
    private ViewPager originalImageViewPager;
    private Context mContext;
    private ArrayList<JSONObject> mThumnailsList=new ArrayList<>();
    int selected_position = 0;

    public ThumbnailViewAdapter(Context context, ArrayList<JSONObject> thumbnalisList, ViewPager viewPager) {
        this.mContext=context;
        this.mThumnailsList=thumbnalisList;
        this.originalImageViewPager=viewPager;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        width = (displayMetrics.widthPixels-8)/5;
    }

    @Override
    public ThumbnailViewAdapter.SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imagethumbnail, null);
        ThumbnailViewAdapter.SingleItemRowHolder currentView = new ThumbnailViewAdapter.SingleItemRowHolder(v);
        return currentView;
    }

    @Override
    public void onBindViewHolder(ThumbnailViewAdapter.SingleItemRowHolder holder, final int position) {
        holder.thumnailImage.setBackgroundColor(Color.TRANSPARENT);
        if(mThumnailsList.get(position).optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)){
            holder.attachment_icon.setVisibility(View.VISIBLE);
            holder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_pdf);
            AppUtil.loadImageUsingGlide(mContext,mThumnailsList.get(position).optString(RestUtils.ATTACH_SMALL_URL),holder.thumnailImage,R.drawable.default_image_feed);
        }else if(mThumnailsList.get(position).optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)){
            holder.attachment_icon.setVisibility(View.VISIBLE);
            holder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
            AppUtil.loadImageUsingGlide(mContext,mThumnailsList.get(position).optString(RestUtils.ATTACH_SMALL_URL),holder.thumnailImage,R.drawable.default_image_feed);
        }else if(mThumnailsList.get(position).optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)){
            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(width, width);
            relativeParams.setMargins(4, 0, 0, 0);
            holder.thumnailImage.setLayoutParams(relativeParams);
            holder.thumnailImage.setBackgroundColor(Color.parseColor("#E8E8E8"));
            holder.attachment_icon.setVisibility(View.GONE);
            holder.thumnailImage.setImageResource(R.drawable.ic_attachment_type_audio);
        }else{
            if(mThumnailsList.get(position).optString(RestUtils.ATTACH_SMALL_URL).contains(RestUtils.TAG_TYPE_GIF)){
                holder.attachment_icon.setVisibility(View.VISIBLE);
                holder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_gif);
            }else{
                holder.attachment_icon.setVisibility(View.GONE);
            }
            AppUtil.loadImageUsingGlide(mContext,mThumnailsList.get(position).optString(RestUtils.ATTACH_SMALL_URL),holder.thumnailImage,R.drawable.default_image_feed);
        }

        if(selected_position == position){
            if(mThumnailsList.get(position).optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)){
                holder.thumnailImage.setPadding(25, 15, 25, 15);
            }else {
                holder.thumnailImage.setPadding(10, 10, 10, 10);
            }
        }
        else{
            holder.thumnailImage.setPadding(2, 2, 2, 2);
        }
        holder.thumnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(originalImageViewPager!=null) {
                    originalImageViewPager.setCurrentItem(position);
                }
                notifyItemChanged(selected_position);
                selected_position = position;
                notifyItemChanged(selected_position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mThumnailsList.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        private ImageView thumnailImage,attachment_icon;
        public SingleItemRowHolder(View view) {
            super(view);
            thumnailImage=(ImageView)view.findViewById(R.id.img_thumb);
            attachment_icon=(ImageView)view.findViewById(R.id.attachment_icon);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
            thumnailImage.setLayoutParams(params);
            thumnailImage.setPadding(8, 0, 0, 0);
        }
    }

    public void setItemSelectedPosition(int position){
        selected_position=position;
    }

    public int getItemSelectedPosition(){
        return  selected_position;
    }
}
