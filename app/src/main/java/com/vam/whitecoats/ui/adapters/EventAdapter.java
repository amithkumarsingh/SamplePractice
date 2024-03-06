package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.EventInfo;
import com.vam.whitecoats.ui.activities.ProfileViewActivity;
import com.vam.whitecoats.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventAdapter extends BaseAdapter {

    public Context context;

    private List<EventInfo> eventInfolist;

    public EventAdapter(Context context, List<EventInfo> eventInfo) {
        this.context = context;
        this.eventInfolist = eventInfo;
    }


    @Override
    public int getCount() {
        return eventInfolist.size();
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
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.listitem_eventcalenderadapter, null);
            holder = new ViewHolder();
            holder.date_text = view.findViewById(R.id.date_text);
            holder.title_text = view.findViewById(R.id.txt_title);
            holder.location_text = view.findViewById(R.id.txt_location);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        if(eventInfolist!=null && eventInfolist.size()!=0){
            EventInfo eventInfo = eventInfolist.get(i);
            holder.title_text.setText(eventInfo.getEventTitle());
            holder.location_text.setText(eventInfo.getLocation());

            holder.date_text.setText(DateUtils.convertLongtoDates(eventInfo.getStartDate()) + " - " + DateUtils.convertLongtoDates(eventInfo.getEndDate()));

            if(holder.location_text.getText().toString().trim().isEmpty()){
                holder.location_text.setVisibility(View.GONE);
            }else{
                holder.location_text.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    public static class ViewHolder {
        TextView title_text;
        TextView location_text;
        TextView date_text;
    }
}
