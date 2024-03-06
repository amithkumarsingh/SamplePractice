package com.vam.whitecoats.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.DrugClass;
import com.vam.whitecoats.ui.interfaces.DrugClassClickListener;

import java.util.ArrayList;
import java.util.List;

public class DrugClassAdapter extends RecyclerView.Adapter {
    List<DrugClass> drugClassList = new ArrayList<>();
    private List<Integer> groupData = new ArrayList<>();
    private DrugClassClickListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public DrugClassAdapter(DrugClassClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.drug_item, viewGroup, false);
            viewHolder = new DrugClassAdapter.DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.drug_header, viewGroup, false);
            viewHolder = new DrugClassAdapter.ProgressViewHolder(view);
        }
        return viewHolder;
    }
    public void setDataList(List<DrugClass> data,List<Integer> groupData){
        drugClassList=data;
        this.groupData = groupData;
        notifyDataSetChanged();
    }
    public void resetDataList(){
        drugClassList.clear();
        groupData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder != null) {
            if (holder instanceof DrugClassAdapter.DataObjectHolder) {
                DrugClassAdapter.DataObjectHolder viewHolder = (DrugClassAdapter.DataObjectHolder) holder;
                DrugClass dataModel = drugClassList.get(i);
                viewHolder.name.setText(dataModel.getDrugName());
                viewHolder.headerText.setVisibility(View.GONE);
                if(groupData.get(i) == 0){
                    viewHolder.headerText.setVisibility(View.VISIBLE);
                    viewHolder.headerText.setText(drugClassList.get(i).getDrugName().substring(0,1).toUpperCase());
                }
                else{
                    viewHolder.headerText.setVisibility(View.GONE);
                }
                viewHolder.drugItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(dataModel,i);
                    }
                });
            } else if (holder instanceof DrugClassAdapter.ProgressViewHolder) {
                DrugClassAdapter.ProgressViewHolder viewHolder = (DrugClassAdapter.ProgressViewHolder) holder;
                viewHolder.drugHeader.setText(drugClassList.get(i).getDrugName().substring(0,1));
            }
        }

    }
    public class DataObjectHolder extends RecyclerView.ViewHolder {
        private TextView name,headerText;
        private LinearLayout drugItem;
        public DataObjectHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.d_name);
            drugItem = itemView.findViewById(R.id.drug_item);
            headerText = itemView.findViewById(R.id.drug_header_text_main);
        }

    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public TextView drugHeader;

        public ProgressViewHolder(View v) {
            super(v);
            drugHeader = (TextView) v.findViewById(R.id.drug_header_text);
        }
    }

    public void addDummyItemToList(){
        drugClassList.add(null);
        notifyItemInserted(this.drugClassList.size());
    }

    public void removeDummyItemFromList(){
        drugClassList.remove(null);
        notifyItemRemoved(drugClassList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return  VIEW_ITEM;
    }
    public void setListExhausted(){

    }


    @Override
    public int getItemCount() {
        if(drugClassList!=null){
            return drugClassList.size();
        }else {
            return 0;
        }
    }
}
