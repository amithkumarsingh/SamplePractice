package com.vam.whitecoats.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.DrugSubClass;
import com.vam.whitecoats.ui.interfaces.DrugSubClassListener;

import java.util.ArrayList;
import java.util.List;

public class DrugAdapter extends RecyclerView.Adapter {
    List<DrugSubClass> drugClassList = new ArrayList<>();
    private DrugSubClassListener listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public DrugAdapter(DrugSubClassListener listener){
        this.listener = listener;
    }

    public void setDataList(List<DrugSubClass> data){
        drugClassList=data;
        notifyDataSetChanged();
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
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.drug_item, viewGroup, false);
            viewHolder = new DrugAdapter.DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);
            viewHolder = new DrugAdapter.ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return drugClassList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }
    public void setListExhausted(){

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder != null) {
            if (holder instanceof DrugAdapter.DataObjectHolder) {
                DrugAdapter.DataObjectHolder viewHolder = (DrugAdapter.DataObjectHolder) holder;
                DrugSubClass dataModel = drugClassList.get(i);
                viewHolder.name.setText(dataModel.getDrugName());
                viewHolder.headerText.setVisibility(View.GONE);
                viewHolder.drugItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(dataModel,i);
                    }
                });
            } else if (holder instanceof ExploreAdapter.ProgressViewHolder) {
                ((ExploreAdapter.ProgressViewHolder) holder).progressBar.setPadding(0, 40, 0, 40);
            }
        }

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
