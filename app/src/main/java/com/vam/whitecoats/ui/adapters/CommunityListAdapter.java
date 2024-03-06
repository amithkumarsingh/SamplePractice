package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.vam.whitecoats.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tejaswini on 31-07-2017.
 */

public class CommunityListAdapter extends BaseAdapter {

    private  ArrayList<JSONObject> mChooseCommunityJsonObj=new ArrayList<>();
    public Context mContext;

    public CommunityListAdapter(Context context,  ArrayList<JSONObject> chooseCommunityJsonObj) {
        mContext=context;
        mChooseCommunityJsonObj=chooseCommunityJsonObj;
    }

    @Override
    public int getCount() {
        return mChooseCommunityJsonObj.size();
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
            convertView = inflater.inflate(R.layout.community_list, viewGroup, false);
            viewHolder.community_name = (TextView) convertView.findViewById(R.id.community_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.community_name.setText(mChooseCommunityJsonObj.get(position).optString("feed_provider_name"));
        return convertView;
    }

    public static class ViewHolder {
        TextView community_name;
    }
}
