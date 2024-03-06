package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class AssociationsListAdapter extends BaseAdapter {

    private ArrayList<JSONObject> jsonArrayChannels;
    public Context mContext;
    boolean isDefaultChannel;


    public AssociationsListAdapter(Context context, ArrayList<JSONObject> communityList) {
        mContext = context;
        jsonArrayChannels = communityList;
    }

    @Override
    public int getCount() {
        return jsonArrayChannels.size();
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
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.community_list, viewGroup, false);
            viewHolder.community_name = (TextView) view.findViewById(R.id.community_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String associationName = jsonArrayChannels.get(i).optString(RestUtils.FEED_PROVIDER_NAME);
        if (jsonArrayChannels.get(i).optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Network")) {
            associationName = "All Feeds";
        }
        viewHolder.community_name.setText(associationName);
        isDefaultChannel=jsonArrayChannels.get(i).optBoolean("is_user_default_channel");
        view.setSelected(true);
            if (isDefaultChannel) {
                    view.setSelected(true);
            }
        return view;
    }

    public static class ViewHolder {
        TextView community_name;
    }
}
