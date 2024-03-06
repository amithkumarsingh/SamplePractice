package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.WeekDay;
import com.vam.whitecoats.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.DataObjectHolder> {

    List<WeekDay> mWeekDays;
    Context mContext;

    public WeekDayAdapter(List<WeekDay> weekDays, Context context) {
        this.mWeekDays = weekDays;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new TextView(parent.getContext());
        DataObjectHolder viewHolder = new DataObjectHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, int position) {
        WeekDay weekDay = mWeekDays.get(position);
        holder.nameTxtVw.setText(weekDay.getShortName());
        if (weekDay.isChecked()) {
            holder.nameTxtVw.setBackground(mContext.getResources().getDrawable(R.drawable.radio_btn_selected));
        } else {
            holder.nameTxtVw.setBackground(mContext.getResources().getDrawable(R.drawable.radio_btn_unselected));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!weekDay.isChecked()) {
                    holder.nameTxtVw.setBackground(mContext.getResources().getDrawable(R.drawable.radio_btn_selected));
                    weekDay.setChecked(true);
                } else {
                    holder.nameTxtVw.setBackground(mContext.getResources().getDrawable(R.drawable.radio_btn_unselected));
                    weekDay.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWeekDays.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView nameTxtVw;

        public DataObjectHolder(View mItemView) {
            super(mItemView);
            nameTxtVw = (TextView) mItemView;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(2, 0, 2, 0);
//            params.height = 60;
            int width = (AppUtil.getDeviceWidth(mContext) - 100) / 7;
            params.width = width;
            nameTxtVw.setLayoutParams(params);
            nameTxtVw.setPadding(0,12,0,12);
            nameTxtVw.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            nameTxtVw.setGravity(Gravity.CENTER);
            nameTxtVw.setTypeface(Typeface.DEFAULT);
        }
    }

    public String updatedAvailableDays() {
        ArrayList daysList = new ArrayList();
        for (WeekDay weekDay : mWeekDays) {
            if (weekDay.isChecked())
                daysList.add(weekDay.getDaySerialNumber());
        }
        return TextUtils.join(",", daysList);
    }
}
