package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.utils.ValidationUtils;

import java.util.ArrayList;

/**
 * Created by lokeshl on 7/1/2015.
 */
public class SystemticAdapter extends BaseAdapter{
    public Context context;

    //HashMap<String,String> getSystemicInfo_list=new HashMap<>();
    ArrayList<String> keys=new ArrayList<>();
    ArrayList<String> values=new ArrayList<>();


    public SystemticAdapter(Context context, ArrayList<String> keys,ArrayList<String> values) {
        this.context        = context;
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
            convertView = mInflater.inflate(R.layout.listitem_systematicadapter, null);
            holder = new ViewHolder();
            holder.title_text = (TextView) convertView.findViewById(R.id.title_text);
            holder.details_text = (TextView) convertView.findViewById(R.id.details_text);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
            //if (!values.get(position).toString().trim().equals("")) {
                holder.title_text.setText(keys.get(position).toString().trim());
                holder.details_text.setText(values.get(position).toString().trim());
            //}

        return convertView;
    }
    public static class ViewHolder {
        TextView title_text;
        TextView details_text;

    }

}
