package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.MyBookmarksActivity;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;

public class TrendingCategoriesAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private Realm realm;
    private RealmManager realmManager;
    private RealmBasicInfo basicInfo;
    Context mContext;
    private ArrayList<JSONObject> trendingCategoriesList = new ArrayList<>();
    private int lastPosition = -1;

    public TrendingCategoriesAdapter(Context context, ArrayList<JSONObject> trendingCategoriesArray) {
        this.mContext=context;
        this.trendingCategoriesList=trendingCategoriesArray;
        realm = Realm.getDefaultInstance();//getInstance(this);
        realmManager = new RealmManager(context);
        basicInfo = realmManager.getRealmBasicInfo(realm);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trending_categories_child, parent, false);
            viewHolder = new DataObjectHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        ((DataObjectHolder)holder).category_name.setText(trendingCategoriesList.get(position).optString(RestUtils.TAG_DISPLAY_NAME));

        String attachType = trendingCategoriesList.get(position).optString(RestUtils.ATTACHMENT_TYPE);
        String attachment_url = trendingCategoriesList.get(position).optString(RestUtils.ATTACH_ORIGINAL_URL);
        if(attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)&&  attachment_url!=null && !attachment_url.isEmpty()){
            AppUtil.loadImageUsingGlide(mContext,attachment_url,((DataObjectHolder)holder).category_image,R.drawable.default_channel_logo);
        }else if(attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO) && attachment_url!=null && !attachment_url.isEmpty()){
            AppUtil.loadImageUsingGlide(mContext,attachment_url,((DataObjectHolder)holder).category_image,R.drawable.default_channel_logo);
        }else if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)&& attachment_url!=null && !attachment_url.isEmpty()) {
            ((DataObjectHolder)holder).category_image.setBackgroundColor(Color.parseColor("#E8E8E8"));
            ((DataObjectHolder)holder).category_image.setImageResource(R.drawable.ic_attachment_type_audio);
            ((DataObjectHolder)holder).category_image.setScaleType(ImageView.ScaleType.CENTER);
        } else if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE) && attachment_url!=null && !attachment_url.isEmpty()) {

            if(!attachment_url.contains(RestUtils.TAG_TYPE_GIF)) {
                AppUtil.loadImageUsingGlide(mContext, attachment_url, ((DataObjectHolder) holder).category_image,R.drawable.default_channel_logo);
            }
        }else {
            ((DataObjectHolder)holder).category_image.setImageResource(R.drawable.default_channel_logo);
        }

        ((DataObjectHolder)holder).category_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int category_id = trendingCategoriesList.get(position).optInt(RestUtils.TAG_CATEGORY_ID);
                String categoryName = trendingCategoriesList.get(position).optString(RestUtils.TAG_DISPLAY_NAME);

                HashMap<String , Object> data = new HashMap<>();
                data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
                data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
                data.put(RestUtils.EVENT_TRENDING_CATEGORY_NAME,categoryName);
                AppUtil.logUserEventWithHashMap("TappingTrendingCateogry",basicInfo.getDoc_id(),data,mContext);
                Intent intent=new Intent(mContext, MyBookmarksActivity.class);
                intent.putExtra(RestUtils.TAG_CATEGORY_ID,category_id);
                intent.putExtra("categoryName",categoryName);
                intent.putExtra(RestUtils.NAVIGATATION, "fromCategoriesList");
                mContext.startActivity(intent);
            }
        });
        //setAnimation(holder.itemView, position);

    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            /*AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            itemView.startAnimation(anim);*/
            /*Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            animation.setDuration(FADE_DURATION);
            itemView.startAnimation(animation);*/
            ScaleAnimation anim = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(350);
            anim.setFillAfter(true);
            itemView.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return trendingCategoriesList.size();
    }

    private class DataObjectHolder extends RecyclerView.ViewHolder {
        private final LinearLayout category_item;
        private final TextView category_name;
        private final RoundedImageView category_image;
        private final View mParentView;

        public DataObjectHolder(View view) {
            super(view);
            mParentView=view;
            category_item=(LinearLayout)view.findViewById(R.id.category_item);
            category_name=(TextView)view.findViewById(R.id.category_name);
            /*category_image=(ImageView)view.findViewById(R.id.category_image);
            attach_type_pdf=(ImageView)view.findViewById(R.id.attach_type_pdf);
            attach_type_video=(ImageView)view.findViewById(R.id.attach_type_video);*/
            category_image=(RoundedImageView)view.findViewById(R.id.trending_categories_img);
        }

        public void clearAnimation() {
            mParentView.clearAnimation();
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                //((DataObjectHolder) holder).clearAnimation();
            }
        }
    }
}
