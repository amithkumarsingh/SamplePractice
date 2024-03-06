package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.AcademicInfo;

import java.util.List;

/**
 * Created by lokeshl on 7/1/2015.
 */
public class AcademicAdapter extends BaseAdapter{
    public Context context;

    private List<AcademicInfo> academicInfos;

    public AcademicAdapter(Context context, List<AcademicInfo> academicInfos) {
        this.context        = context;
        this.academicInfos=academicInfos;
    }


    @Override
    public int getCount() {
        return academicInfos.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_academicadapter, null);
            holder = new ViewHolder();
            holder.degree_text = (TextView) convertView.findViewById(R.id.degree_text);
            holder.college_text = (TextView) convertView.findViewById(R.id.college_text);
            holder.university_text=(TextView) convertView.findViewById(R.id.university_text);
            holder.date_text=(TextView) convertView.findViewById(R.id.date_text);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(academicInfos!=null && academicInfos.size()!=0) {
            AcademicInfo academicInfo = academicInfos.get(position);
            holder.degree_text.setText(academicInfo.getDegree());
            holder.college_text.setText(academicInfo.getCollege());
            holder.university_text.setText(academicInfo.getUniversity());
            if(academicInfo.getPassing_year()!=0)
                holder.date_text.setText(academicInfo.getPassing_year() + "");
            else
                holder.date_text.setText("Currently Pursuing");
            if(holder.degree_text.getText().toString().trim().isEmpty()){
                holder.degree_text.setVisibility(View.GONE);
            }
            else{
                holder.degree_text.setVisibility(View.VISIBLE);

            }
            if(holder.college_text.getText().toString().trim().isEmpty()){
                holder.college_text.setVisibility(View.GONE);
            } else{
                holder.college_text.setVisibility(View.VISIBLE);

            }
            if(holder.university_text.getText().toString().trim().isEmpty()){
                holder.university_text.setVisibility(View.GONE);
            } else{
                holder.university_text.setVisibility(View.VISIBLE);

            }
            if(holder.date_text.getText().toString().trim().isEmpty()){
                holder.date_text.setVisibility(View.GONE);
            } else{
                holder.date_text.setVisibility(View.VISIBLE);

            }
        }
        return convertView;
    }
    public static class ViewHolder {
        TextView degree_text;
        TextView college_text;
        TextView university_text;
        TextView date_text;
    }

}
