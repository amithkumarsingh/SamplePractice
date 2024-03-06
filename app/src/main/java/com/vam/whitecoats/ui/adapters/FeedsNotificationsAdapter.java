package com.vam.whitecoats.ui.adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.NotificationInfo;
import com.vam.whitecoats.databinding.FeedsNotofictionRowLayoutBinding;
import com.vam.whitecoats.ui.interfaces.NotificationItemClickListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.viewmodel.NotificationItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class FeedsNotificationsAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<NotificationInfo> dataList = new ArrayList<>();
    private NotificationItemClickListener listener;
    private OnTaskCompleted loadMoreListener;
    String userName, spec, loc, onBoard, loginDate;
    //private ArrayList<NotificationInfo> data=new ArrayList<>();

    public FeedsNotificationsAdapter(NotificationItemClickListener listener, String name, String speciality, String location, String onBoardDate, String lastLoginDate) {
        //this.data.addAll(list);
        //this.dataList=list;
        this.listener = listener;
        userName = name;
        spec = speciality;
        loc = location;
        onBoard = onBoardDate;
        loginDate = lastLoginDate;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.feeds_notofiction_row_layout, viewGroup, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                DataObjectHolder viewHolder = (DataObjectHolder) holder;
                NotificationInfo dataModel = dataList.get(i);

                viewHolder.setViewModel(new NotificationItemViewModel(dataModel, userName, spec, onBoard, loc, loginDate), i);
            } else if (holder instanceof ProgressViewHolder) {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }

    /*ENGG-3376 -- Scroll flickering issue */
    //Start
    @Override
    public long getItemId(int position) {
        if (dataList != null && dataList.get(position) != null &&
                dataList.get(position).getFeedId() != null) {
            return dataList.get(position).getFeedId();
        }
        return super.getItemId(position);
    }

    //End
    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        } else {
            return 0;
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        FeedsNotofictionRowLayoutBinding binding;

        public DataObjectHolder(View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(NotificationItemViewModel viewModel, int position) {
            if (binding != null) {
                binding.setViewModel(viewModel);
                binding.setPosition(position);
                binding.setItemClickListener(FeedsNotificationsAdapter.this.listener);
            }
        }
    }

    public void updateData(@Nullable List<NotificationInfo> data) {
        if (data == null || data.isEmpty()) {
            dataList.clear();
        } else {
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setDataList(List<NotificationInfo> data) {
        dataList = data;
        notifyDataSetChanged();
    }

    public void updateDataItem(int position, NotificationInfo notificationInfo) {
        dataList.set(position, notificationInfo);
        notifyItemChanged(position);
    }

    public void addDummyItemToList() {
        dataList.add(null);
        notifyItemInserted(this.dataList.size());
    }

    public void setListExhausted() {

    }

    public void removeDummyItemFromList() {
        this.dataList.remove(null);
        notifyItemRemoved(dataList.size());
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}
