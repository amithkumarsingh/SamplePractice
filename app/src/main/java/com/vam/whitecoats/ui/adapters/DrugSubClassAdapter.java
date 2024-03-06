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
import com.vam.whitecoats.core.models.DrugSubClass;
import com.vam.whitecoats.ui.interfaces.DrugClassClickListener;
import com.vam.whitecoats.ui.interfaces.DrugSubClassListener;

import java.util.ArrayList;
import java.util.List;

public class DrugSubClassAdapter extends RecyclerView.Adapter<DrugSubClassAdapter.DrugSubClassViewHolder> {
    List<DrugSubClass> drugSubClassList = new ArrayList<>();
    private List<Integer> groupData = new ArrayList<>();
    private DrugSubClassListener listener;
    public DrugSubClassAdapter(DrugSubClassListener drugSubClassListener){
        this.listener = drugSubClassListener;
    }

    public void setDataList(List<DrugSubClass> data,List<Integer> groupData){
        drugSubClassList=data;
        this.groupData = groupData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DrugSubClassAdapter.DrugSubClassViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        DrugSubClassAdapter.DrugSubClassViewHolder viewHolder = null;
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.drug_item, viewGroup, false);
        viewHolder = new DrugSubClassViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrugSubClassAdapter.DrugSubClassViewHolder holder, int i) {
        if (holder != null) {
            if (holder instanceof DrugSubClassAdapter.DrugSubClassViewHolder) {
                DrugSubClassAdapter.DrugSubClassViewHolder viewHolder = (DrugSubClassAdapter.DrugSubClassViewHolder) holder;
                DrugSubClass dataModel = drugSubClassList.get(i);
                viewHolder.name.setText(dataModel.getDrugName());
                viewHolder.headerText.setVisibility(View.GONE);
                if(groupData.get(i) == 0){
                    viewHolder.headerText.setVisibility(View.VISIBLE);
                    viewHolder.headerText.setText(drugSubClassList.get(i).getDrugName().substring(0,1).toUpperCase());
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
            }
        }

    }
    public class DrugSubClassViewHolder extends RecyclerView.ViewHolder {
        private TextView name,headerText;
        private LinearLayout drugItem;
        public DrugSubClassViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.d_name);
            drugItem = itemView.findViewById(R.id.drug_item);
            headerText = itemView.findViewById(R.id.drug_header_text_main);
        }

    }


    public void setListExhausted(){

    }
    @Override
    public int getItemCount() {
        if(drugSubClassList!=null){
            return drugSubClassList.size();
        }else {
            return 0;
        }
    }
}
