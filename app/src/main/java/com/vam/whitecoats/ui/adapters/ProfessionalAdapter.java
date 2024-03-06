package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.ProfessionalInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lokeshl on 7/1/2015.
 */
public class ProfessionalAdapter extends BaseAdapter {
    public Context context;
    private boolean showoncard;

    private List<ProfessionalInfo> professionalInfos;

    public ProfessionalAdapter(Context context, List<ProfessionalInfo> professionalInfos, boolean showoncard) {
        this.context = context;
        this.professionalInfos = professionalInfos;
        this.showoncard = showoncard;
    }


    @Override
    public int getCount() {
        return professionalInfos.size();
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
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_professionaladapter, null);
            holder = new ViewHolder();
            holder.designation_text = (TextView) convertView.findViewById(R.id.designation_text);
            holder.workplace_text = (TextView) convertView.findViewById(R.id.workplace_text);
            holder.location_text = (TextView) convertView.findViewById(R.id.location_text);
            holder.date_text = (TextView) convertView.findViewById(R.id.date_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (professionalInfos != null && professionalInfos.size() != 0) {
            ProfessionalInfo professionalInfo = professionalInfos.get(position);
            holder.designation_text.setText(professionalInfo.getDesignation());
            holder.workplace_text.setText(professionalInfo.getWorkplace());
            holder.location_text.setText(professionalInfo.getLocation());
            holder.location_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.location_text.setDrawingCacheEnabled(false);
            String date_data = "";


            if (professionalInfo.getStart_date() != 0 && professionalInfo.getEnd_date() != 0) {
                if (convertLongtoDate(professionalInfo.getStart_date()).equals(convertLongtoDate(professionalInfo.getEnd_date()))) {
                    date_data = convertLongToMonth(professionalInfo.getStart_date()) + " - " + convertLongToMonth(professionalInfo.getEnd_date());
                } else {
                    date_data = convertLongtoDate(professionalInfo.getStart_date()) + " - " + convertLongtoDate(professionalInfo.getEnd_date());
                }
            } else if (professionalInfo.getStart_date() != 0) {
                date_data = "Since " + convertLongtoDate(professionalInfo.getStart_date());
            }
            holder.date_text.setText(date_data);

            if (holder.designation_text.getText().toString().trim().isEmpty()) {
                holder.designation_text.setVisibility(View.GONE);
            } else {
                holder.designation_text.setVisibility(View.VISIBLE);

            }
            if (holder.date_text.getText().toString().trim().isEmpty()) {
                holder.date_text.setVisibility(View.GONE);
            } else {
                holder.date_text.setVisibility(View.VISIBLE);

            }


        }
        return convertView;
    }

    public static class ViewHolder {
        TextView designation_text;
        TextView workplace_text;
        TextView location_text;
        TextView date_text;
    }

    private String convertLongtoDate(Long millisec) {
        Long millisec_ = millisec;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millisec);
            return formatter.format(calendar.getTime());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    private String convertLongToMonth(Long millisec) {
        Long millisec_ = millisec;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millisec);
            //    int month=(calendar.get(Calendar.MONTH)+1);

            java.util.Date d = new java.util.Date(calendar.getTimeInMillis());
            System.out.println("Month Name :" + new SimpleDateFormat("MMMM").format(d));

            String date = "" + new SimpleDateFormat("MMM").format(d) + " " + calendar.get(Calendar.YEAR);
//            return formatter.format(calendar.getTime());
            return date;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

}
