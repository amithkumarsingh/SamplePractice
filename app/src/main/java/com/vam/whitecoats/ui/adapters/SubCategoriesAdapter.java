package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.SubCategoriesInfo;
import com.vam.whitecoats.ui.interfaces.SubCategoriesItemClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class SubCategoriesAdapter extends RecyclerView.Adapter {
    private  Context mContext;
    private  int VIEW_ITEM = 1;
    private  int VIEW_PROG = 0;
    private  SubCategoriesItemClickListener listener;
    private ArrayList<SubCategoriesInfo> dataList;

    public SubCategoriesAdapter(Context context,SubCategoriesItemClickListener listener) {
        this.listener=listener;
        this.mContext=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
       /* View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sub_categories_item, viewGroup, false);
        viewHolder = new DataObjectHolder(view);*/
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.sub_categories_item, viewGroup, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                DataObjectHolder viewHolder = (DataObjectHolder) holder;
                SubCategoriesInfo dataModel = dataList.get(position);
                viewHolder.sub_category_title.setText(dataModel.getSubCategoryName());
                if (dataModel.getUnreadCount() > 0) {
                    viewHolder.category_item_count.setVisibility(View.VISIBLE);
                    if (dataModel.getUnreadCount() > 99) {
                        viewHolder.category_item_count.setText("99+");
                    } else {
                        viewHolder.category_item_count.setText("" + dataModel.getUnreadCount());
                    }
                } else {
                    viewHolder.category_item_count.setVisibility(View.GONE);
                }
                AppUtil.loadImageUsingGlide(mContext,dataModel.getImageUrl(),((DataObjectHolder)holder).sub_category_image,R.drawable.default_channel_logo);
                ((DataObjectHolder)holder).sub_category_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(dataModel.getUnreadCount() > 0){
                            viewHolder.category_item_count.setVisibility(View.GONE);
                        }
                        listener.onItemClicked(position);
                    }
                });
            } else if (holder instanceof ProgressViewHolder) {
                ((ProgressViewHolder) holder).progressBar.setPadding(0, 40, 0, 40);
            }
        }

    }

    @Override
    public int getItemCount() {
        if(dataList!=null){
            return dataList.size();
        }else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }

    public void setDataList(ArrayList<SubCategoriesInfo> categoriesList) {
        dataList=categoriesList;
        notifyDataSetChanged();
    }
    public void addDummyItemToList(){
        dataList.add(null);
        notifyItemInserted(this.dataList.size());
    }
    public void removeDummyItemFromList(){
        dataList.remove(null);
        notifyItemRemoved(dataList.size());
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder  {

        private final RelativeLayout sub_category_item;
        private TextView category_item_count;
        private TextView sub_category_title;
        private ImageView sub_category_image;

        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            sub_category_image = itemView.findViewById(R.id.sub_category_image);
            sub_category_title = itemView.findViewById(R.id.sub_category_title);
            category_item_count=itemView.findViewById(R.id.category_item_count);
            sub_category_item=itemView.findViewById(R.id.sub_category_item);
        }
    }

    public  class ProgressViewHolder extends RecyclerView.ViewHolder{

        private final AVLoadingIndicatorView progressBar;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (AVLoadingIndicatorView) itemView.findViewById(R.id.avIndicator);

        }
    }
}
