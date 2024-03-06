package com.vam.whitecoats.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.ui.interfaces.ExploreItemClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter{
    List<Category> exploreItemList = new ArrayList<>();
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ExploreItemClickListener listener;


    public ExploreAdapter(ExploreItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.explore_item, viewGroup, false);
            viewHolder = new ExploreAdapter.DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);
            viewHolder = new ExploreAdapter.ProgressViewHolder(view);
        }
        return viewHolder;
    }
    public void updateData(@Nullable List<Category> data) {
        if (data == null || data.isEmpty()) {
            exploreItemList.clear();
        } else {
            exploreItemList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setDataList(List<Category> data){
        exploreItemList=data;
        notifyDataSetChanged();
    }

    public void updateDataItem(int position,Category exploreItem){
        exploreItemList.set(position,exploreItem);
        notifyItemChanged(position);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        private ImageView categoryIcon;
        private TextView count,name;
        private RelativeLayout exploreItem;
        public DataObjectHolder(View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.explore_item_icon);
            count = itemView.findViewById(R.id.explore_item_count);
            name = itemView.findViewById(R.id.explore_item_name);
            exploreItem = itemView.findViewById(R.id.explore_item_layout);
        }

    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public AVLoadingIndicatorView progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (AVLoadingIndicatorView) v.findViewById(R.id.avIndicator);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder != null) {
            if (holder instanceof ExploreAdapter.DataObjectHolder) {
                ExploreAdapter.DataObjectHolder viewHolder = (ExploreAdapter.DataObjectHolder) holder;
                Category dataModel = exploreItemList.get(i);
                viewHolder.name.setText(dataModel.getCategoryName());
                viewHolder.exploreItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*if (dataModel.getUnreadCount() > 0) {
                            viewHolder.count.setVisibility(View.GONE);
                        }*/
                        listener.onItemClick(dataModel,i);
                    }
                });
                if (dataModel.getUnreadCount() > 0) {
                    viewHolder.count.setVisibility(View.VISIBLE);
                    if (dataModel.getUnreadCount() > 99) {
                        viewHolder.count.setText("99+");
                    } else {
                        viewHolder.count.setText("" + dataModel.getUnreadCount());
                    }
                } else {
                    viewHolder.count.setVisibility(View.GONE);
                }
                AppUtil.loadImageUsingGlide(((DataObjectHolder) holder).categoryIcon.getContext(),dataModel.getImageUrl(),((DataObjectHolder) holder).categoryIcon,R.drawable.default_channel_logo);
            } else if (holder instanceof ExploreAdapter.ProgressViewHolder) {
                ((ProgressViewHolder) holder).progressBar.setPadding(0, 40, 0, 40);
            }
        }
    }

    public void addDummyItemToList(){
        exploreItemList.add(null);
        notifyItemInserted(this.exploreItemList.size());
    }

    public void removeDummyItemFromList(){
        exploreItemList.remove(null);
        notifyItemRemoved(exploreItemList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return exploreItemList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }
    public void setListExhausted(){

    }


    @Override
    public int getItemCount() {
        if(exploreItemList!=null){
            return exploreItemList.size();
        }else {
            return 0;
        }
    }
}
