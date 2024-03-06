package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.RestUtils;

import java.util.ArrayList;

/**
 * Created by lokeshl on 7/1/2015.
 */
public class VitalsInfoAdapter extends BaseAdapter{
    public Context context;

    //HashMap<String,String> getVitalsInfo_list=new HashMap<>();
    ArrayList<String> keys=new ArrayList<>();
    ArrayList<String> values=new ArrayList<>();


    public VitalsInfoAdapter(Context context, ArrayList<String> keys,ArrayList<String> values) {
        this.context        = context;
        /*this.getVitalsInfo_list=getVitalsInfo_list;
        for(Map.Entry getVitalsInfo:getVitalsInfo_list.entrySet()) {

        }*/
        this.keys=keys;
        this.values=values;

    }


    @Override
    public int getCount() {
        return keys.size();
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
        final ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_vitalsinfoadapter, null);
            holder = new ViewHolder();
            holder.title_text = (TextView) convertView.findViewById(R.id.title_text);
            holder.details_text = (TextView) convertView.findViewById(R.id.details_text);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
                String key=keys.get(position).toString().trim();

                switch (key) {
                    case RestUtils.HR_PRB:
                        holder.title_text.setText(R.string.str_hr_pr);
                        holder.details_text.setText(values.get(position).toString()+ " "+getString(R.string.str_mm));
                        break;
                    case RestUtils.RESP_RATE:
                        holder.title_text.setText(R.string.str_rest_rate);
                        holder.details_text.setText(values.get(position).toString()+ " "+getString(R.string.str_min));
                        break;
                    case RestUtils.BP :
                    case RestUtils.BP1:
                        holder.title_text.setText(R.string.str_bp);
                        holder.details_text.setText(values.get(position).toString()+ " "+getString(R.string.str_mmhg));
                        break;
                    case RestUtils.TEMPERATURE:
                        holder.title_text.setText(R.string.str_temp);
                        holder.details_text.setText(values.get(position).toString()+ " "+getString(R.string.str_farenheat));
                        break;
                    case RestUtils.SP02:
                        holder.title_text.setText(R.string.str_spo);
                        holder.details_text.setText(values.get(position).toString()+ " "+getString(R.string.str_percentage));
                        break;
                    case RestUtils.WEIGHT:
                        holder.title_text.setText(R.string.str_weight);
                        holder.details_text.setText(values.get(position).toString()+ " "+getString(R.string.str_kg));
                        break;
                    case RestUtils.HEIGHT:
                        holder.title_text.setText(R.string.str_height);
                        holder.details_text.setText(values.get(position).toString()+ " "+getString(R.string.str_cms));
                        break;
                    case RestUtils.BMI:
                        holder.title_text.setText("BMI");
                        holder.details_text.setText(values.get(position).toString());
                        break;
                    case RestUtils.Others:
                        holder.title_text.setText("other");
                        holder.details_text.setText(values.get(position).toString());
                        break;
                }


        return convertView;
    }
    public static class ViewHolder {
        TextView title_text;
        TextView details_text;

    }

    private String getString(int id){
        return context.getResources().getString(id);
    }

}
