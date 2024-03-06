package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.DateUtils;

import java.util.List;

/**
 * @author satyasarathim
 */
public class AvailabilityAdapter extends BaseAdapter {

    public Context mContext;
    private List<ProfessionalInfo> mAvailabilityInfoList;

    public AvailabilityAdapter(Context context, List<ProfessionalInfo> availabilityInfoList) {
        this.mContext = context;
        this.mAvailabilityInfoList = availabilityInfoList;
    }

    @Override
    public int getCount() {
        return mAvailabilityInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.item_available_layout, null);
            holder = new ViewHolder();
            holder.workPlaceTxtVw = (TextView) view.findViewById(R.id.work_place_txt);
            holder.workLocationTxtVw = (TextView) view.findViewById(R.id.work_city_txt);
            holder.designationTxtVw = (TextView) view.findViewById(R.id.designation_txt);
            holder.availabilityInfoTxtVw = (TextView) view.findViewById(R.id.availability_info_txt);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ProfessionalInfo professionalInfo = mAvailabilityInfoList.get(i);
        String workPlace = "";
        String workLocation = "";
        String designation = "";
        String department = "";
        String availableDays = "";
        if (professionalInfo.getWorkplace() != null && !professionalInfo.getWorkplace().isEmpty())
            workPlace = professionalInfo.getWorkplace();
        if (professionalInfo.getLocation() != null && !professionalInfo.getLocation().isEmpty())
            workLocation = professionalInfo.getLocation();
        if (professionalInfo.getDesignation() != null && !professionalInfo.getDesignation().isEmpty())
            designation = professionalInfo.getDesignation();
        if (professionalInfo.getWorkOptions() != null && !professionalInfo.getWorkOptions().isEmpty())
            department = professionalInfo.getWorkOptions();
        if (professionalInfo.getAvailableDays() != null && !professionalInfo.getAvailableDays().isEmpty())
            availableDays = AppUtil.formatAvailableDays(professionalInfo.getAvailableDays());
        String startTime=DateUtils.parseMillisIntoDateWithDefaultValue(professionalInfo.getStartTime(),"hh:mm a");
        String endTime=DateUtils.parseMillisIntoDateWithDefaultValue(professionalInfo.getEndTime(),"hh:mm a");
        if (!professionalInfo.isShowOncard()) {
            holder.workLocationTxtVw.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.workLocationTxtVw.setDrawingCacheEnabled(false);
        } else {
            if (professionalInfo.isShowOncard()) {
                holder.workLocationTxtVw.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_card, 0);
                holder.workLocationTxtVw.setDrawingCacheEnabled(true);
                holder.workLocationTxtVw.setVisibility(View.VISIBLE);
            } else {
                holder.workLocationTxtVw.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                holder.workLocationTxtVw.setDrawingCacheEnabled(false);
            }
        }
        holder.workPlaceTxtVw.setText(workPlace);
        holder.workLocationTxtVw.setText(workLocation);
        holder.designationTxtVw.setText(designation);
        String availableTimings=startTime.isEmpty() || endTime.isEmpty()?startTime+endTime:startTime+"-"+endTime;
        holder.availabilityInfoTxtVw.setText(getAvailabilityInfo(department,availableDays,availableTimings));
        return view;
    }

    public static class ViewHolder {
        TextView workPlaceTxtVw;
        TextView workLocationTxtVw;
        TextView designationTxtVw;
        TextView availabilityInfoTxtVw;
    }

    /**
     *
     * @param department
     * @param days
     * @param timings
     * @return comma separated strings of department+days+timings
     */
    private String getAvailabilityInfo(String department,String days,String timings){
        String finalInfo="";
        finalInfo=department.isEmpty() || days.isEmpty()?department+days:department+", "+days;
        finalInfo=finalInfo.isEmpty() || timings.isEmpty()?finalInfo+timings:finalInfo+", "+timings;
        return finalInfo;
    }


}
