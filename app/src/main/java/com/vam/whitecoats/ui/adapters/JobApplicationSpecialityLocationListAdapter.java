package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.JobLocationSpecializationInfo;
import com.vam.whitecoats.ui.interfaces.JobSpecializationLocationInterface;

import java.util.ArrayList;

public class JobApplicationSpecialityLocationListAdapter extends BaseAdapter {

    private final JobSpecializationLocationInterface jobSpecializationLocationClick;
    private final String navigation;
    private ArrayList<JobLocationSpecializationInfo> specializationLocationArrayList = new ArrayList<>();
    public Context mContext;
    private boolean checkBoxSelection;

    public JobApplicationSpecialityLocationListAdapter(Context context, ArrayList<JobLocationSpecializationInfo> specializationLocList, String navigation, JobSpecializationLocationInterface onClickListener) {
        mContext = context;
        this.specializationLocationArrayList = specializationLocList;
        jobSpecializationLocationClick=onClickListener;
        this.navigation=navigation;
    }

    @Override
    public int getCount() {
        return specializationLocationArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.bottom_sheet_child_lay_jobs, viewGroup, false);
            viewHolder.speciality_or_location_name = (TextView) convertView.findViewById(R.id.speciality_or_location_name);
            viewHolder.selection_checkbox=(CheckBox)convertView.findViewById(R.id.selection_checkbox);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.speciality_or_location_name.setText(specializationLocationArrayList.get(position).getName());
        if(!navigation.equalsIgnoreCase("AboutJob")) {
            viewHolder.selection_checkbox.setVisibility(View.VISIBLE);
            if (specializationLocationArrayList.get(position).getSelection()) {
                viewHolder.selection_checkbox.setChecked(true);
            } else {
                viewHolder.selection_checkbox.setChecked(false);
            }
        }else{
            viewHolder.selection_checkbox.setVisibility(View.GONE);
        }
        viewHolder.selection_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if( viewHolder.selection_checkbox.isChecked()){
                   checkBoxSelection=true;
               }else{
                   checkBoxSelection=false;
               }
                jobSpecializationLocationClick.OnCustomClick(position,checkBoxSelection,specializationLocationArrayList.get(position));
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        TextView speciality_or_location_name;
        CheckBox selection_checkbox;
    }
}
