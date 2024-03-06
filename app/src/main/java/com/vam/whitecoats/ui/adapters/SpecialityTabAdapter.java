package com.vam.whitecoats.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.DrugClass;
import com.vam.whitecoats.core.models.SpecialitiesInfo;
import com.vam.whitecoats.ui.interfaces.SpecialityTabItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SpecialityTabAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final SpecialityTabItemClickListener listener;
    private List<SpecialitiesInfo> specialityList=new ArrayList<>();
    private ArrayList<Integer> groupData=new ArrayList<>();

    public SpecialityTabAdapter(SpecialityTabItemClickListener _listener) {
        this.listener=_listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.speciality_item, parent, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.drug_header, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                DataObjectHolder viewHolder = (DataObjectHolder) holder;
                SpecialitiesInfo dataModel = specialityList.get(position);
                viewHolder.speciality_name.setText(dataModel.getSpecialityName());
                viewHolder.headerText.setVisibility(View.GONE);
                if(groupData.get(position) == 0){
                    viewHolder.headerText.setVisibility(View.VISIBLE);
                    viewHolder.headerText.setText(specialityList.get(position).getSpecialityName().substring(0,1).toUpperCase());
                }
                else{
                    viewHolder.headerText.setVisibility(View.GONE);
                }
                if(dataModel.getFeedCount()!=null){
                    viewHolder.unread_count.setVisibility(View.VISIBLE);
                    if (dataModel.getFeedCount() > 99) {
                        viewHolder.unread_count.setText("99+");
                    } else {
                        viewHolder.unread_count.setText("" + dataModel.getFeedCount());
                    }
                }else{
                    viewHolder.unread_count.setVisibility(View.GONE);
                }
                viewHolder.speciality_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(dataModel);
                    }
                });
            } else if (holder instanceof ProgressViewHolder) {
                ProgressViewHolder viewHolder = (ProgressViewHolder) holder;
                viewHolder.drugHeader.setText(specialityList.get(position).getSpecialityName().substring(0,1));
            }
        }

    }

    @Override
    public int getItemCount() {
        if(specialityList!=null){
            return specialityList.size();
        }else {
            return 0;
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        private   TextView unread_count;
        private TextView speciality_name,headerText;
        private RelativeLayout speciality_item;
        public DataObjectHolder(View itemView) {
            super(itemView);
            speciality_name = itemView.findViewById(R.id.speciality_name);
            speciality_item =(RelativeLayout) itemView.findViewById(R.id.speciality_item);
            headerText = itemView.findViewById(R.id.speciality_header_text_main);
            unread_count=itemView.findViewById(R.id.unread_count);
        }

    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public TextView drugHeader;

        public ProgressViewHolder(View v) {
            super(v);
            drugHeader = (TextView) v.findViewById(R.id.drug_header_text);
        }
    }

    public void setSpecialityDataList(List<SpecialitiesInfo> specialitiesInfos, ArrayList<Integer> groupData) {
        this.specialityList=specialitiesInfos;
        this.groupData = groupData;
        notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
        return specialityList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }

    public void addDummyItemToList(){
        specialityList.add(null);
        notifyItemInserted(this.specialityList.size());
    }
    public void removeDummyItemFromList(){
        specialityList.remove(null);
        notifyItemRemoved(specialityList.size());
    }
}
