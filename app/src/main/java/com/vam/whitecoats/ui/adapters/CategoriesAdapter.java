package com.vam.whitecoats.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.ui.interfaces.OnCategoryClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CategoryItemClickEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter {
    private int pxs;

    private  ArrayList<Category> categoryList;

    private Context mContext;
    private OnCategoryClickListener categoryClickListener;

    public CategoriesAdapter(Context context, ArrayList _categoryList) {
        this.mContext=context;
        this.categoryList=_categoryList;
        /*DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels/4;
        height = displayMetrics.heightPixels/2;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);*/
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_tile_dashboard, parent, false);
        viewHolder = new DataObjectHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category currentCategory = categoryList.get(position);
        ((DataObjectHolder)holder).category_title.setText(currentCategory.getCategoryName());
        if(currentCategory.getImageUrl()!=null && currentCategory.getCategoryId()!=-1){
            AppUtil.loadImageUsingGlide(mContext,currentCategory.getImageUrl(),((DataObjectHolder)holder).category_image,R.drawable.default_channel_logo);
        }else if(currentCategory.getCategoryId()==-1){
            ((DataObjectHolder)holder).category_image.setImageResource(R.drawable.explore_more);
        }
        if(currentCategory.getUnreadCount()>0){
            ((DataObjectHolder)holder).category_unread_count.setVisibility(View.VISIBLE);
            int unreadCount=currentCategory.getUnreadCount();
            if(unreadCount > 99){
                ((DataObjectHolder)holder).category_unread_count.setText("99+");
            }else {
                ((DataObjectHolder) holder).category_unread_count.setText("" + unreadCount);
            }
        }else{
            ((DataObjectHolder)holder).category_unread_count.setVisibility(View.GONE);
        }
        ((DataObjectHolder)holder).category_tile.setOnClickListener(v -> {
            if(categoryClickListener!=null){
                categoryClickListener.onCategoryItemClick(currentCategory);
            }
            /*if(currentCategory.getCategoryId()==-1){
                EventBus.getDefault().post(new CategoryItemClickEvent("OnMoreClick",currentCategory));
            }else{
                *//*if (currentCategory.getUnreadCount() > 0) {
                    ((DataObjectHolder)holder).category_unread_count.setVisibility(View.GONE);
                }*//*
                EventBus.getDefault().post(new CategoryItemClickEvent("OnItemClick",currentCategory));

            }*/

        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    private class DataObjectHolder extends RecyclerView.ViewHolder {
        private ImageView category_image;
        private TextView category_title,category_unread_count;
        private RelativeLayout category_tile;
        public DataObjectHolder(View view) {
            super(view);
            category_image = (ImageView) view.findViewById(R.id.explore_item_icon);
            category_tile = (RelativeLayout) view.findViewById(R.id.explore_tile);
            category_title = (TextView) view.findViewById(R.id.explore_item_name);
            category_unread_count=view.findViewById(R.id.explore_item_count);
            /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
            category_tile.setLayoutParams(params);*/
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            //if you need three fix imageview in width
            double itemWidth = displaymetrics.widthPixels / 4.77;

            //double devicewidth = (itemWidth - dpFromPx(mContext, 16)) / 4;
            //if you need 4-5-6 anything fix imageview in height
            //int deviceheight = displaymetrics.heightPixels / 2;
            category_tile.getLayoutParams().width = (int) itemWidth;
        }
    }

    public int dpFromPx(final Context context, final float px) {
        pxs= (int) (px * context.getResources().getDisplayMetrics().density);
        return pxs;
    }

    public void setCategoryClickListener(OnCategoryClickListener _listener){
        categoryClickListener=_listener;
    }
    public void setListExhausted(){

    }

}
