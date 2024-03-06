package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;

import java.util.List;

/**
 * Created by lokeshl on 7/1/2015.
 */
public class MembershipAdapter extends BaseAdapter {
    public Context context;

    private List<ProfessionalMembershipInfo> professionalMembershipInfo;

    public MembershipAdapter(Context context, List<ProfessionalMembershipInfo> professionalMembershipInfo) {
        this.context = context;
        this.professionalMembershipInfo = professionalMembershipInfo;
    }


    @Override
    public int getCount() {
        return professionalMembershipInfo.size();
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
        String type = "";
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_membershipdapter, null);
            holder = new ViewHolder();
            holder.title_text = (TextView) convertView.findViewById(R.id.title_text);
            holder.presented_at = (TextView) convertView.findViewById(R.id.presented_at);
            holder.presented_year = (TextView) convertView.findViewById(R.id.presented_year);
            holder.awards_image=(ImageView)convertView.findViewById(R.id.awards_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        type = professionalMembershipInfo.get(position).getType();
        if (type == null || type.isEmpty())
            type = "membership";
        if (type.equalsIgnoreCase("award")) {
            holder.title_text.setText(professionalMembershipInfo.get(position).getAward_name());
            holder.presented_at.setVisibility(View.VISIBLE);
            holder.presented_year.setVisibility(View.VISIBLE);
            holder.presented_at.setText(professionalMembershipInfo.get(position).getPresented_at());
            String awardYear=professionalMembershipInfo.get(position).getAward_year()==0?"":""+professionalMembershipInfo.get(position).getAward_year();
            holder.presented_year.setText((awardYear));
            holder.awards_image.setImageResource(R.drawable.ic_awards);

            if(holder.title_text.getText().toString().trim().isEmpty()){
                holder.title_text.setVisibility(View.GONE);
            }else{
                holder.title_text.setVisibility(View.VISIBLE);
            }

            if(holder.presented_at.getText().toString().trim().isEmpty()){
                holder.presented_at.setVisibility(View.GONE);
            }else{
                holder.presented_at.setVisibility(View.VISIBLE);
            }
            if(holder.presented_year.getText().toString().trim().isEmpty()){
                holder.presented_year.setVisibility(View.GONE);
            }else{
                holder.presented_year.setVisibility(View.VISIBLE);
            }
        } else {
            holder.title_text.setText(professionalMembershipInfo.get(position).getMembership_name());
            holder.presented_at.setVisibility(View.GONE);
            holder.presented_year.setVisibility(View.GONE);
            holder.awards_image.setImageResource(R.drawable.ic_memberships);

        }

        return convertView;
    }

    public static class ViewHolder {
        TextView title_text,presented_at,presented_year;

        public ImageView awards_image;
    }

}
