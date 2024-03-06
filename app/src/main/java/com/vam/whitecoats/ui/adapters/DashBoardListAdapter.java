package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.DashBoardBottomListModel;

import org.json.JSONObject;

import java.util.ArrayList;

import in.myinnos.awesomeimagepicker.models.Image;

/**
 * Created by tejaswini on 31-07-2017.
 */

public class DashBoardListAdapter extends BaseAdapter {

    private ArrayList<DashBoardBottomListModel> mChooseCommunityJsonObj = new ArrayList<>();
    public Context mContext;

    public DashBoardListAdapter(Context context, ArrayList<DashBoardBottomListModel> chooseCommunityJsonObj) {
        mContext = context;
        mChooseCommunityJsonObj = chooseCommunityJsonObj;
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
            convertView = inflater.inflate(R.layout.dashboard_bottom_list, viewGroup, false);
            viewHolder.community_name = (TextView) convertView.findViewById(R.id.community_name);
            viewHolder.community_sub_name = (TextView) convertView.findViewById(R.id.community_sub_name);
            viewHolder.community_icon = (ImageView) convertView.findViewById(R.id.community_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.community_name.setText(mChooseCommunityJsonObj.get(position).optString("feed_provider_name"));
        viewHolder.community_name.setText(mChooseCommunityJsonObj.get(position).getCommunityName());
        viewHolder.community_sub_name.setText(mChooseCommunityJsonObj.get(position).getSubCommunityName());
        if (mChooseCommunityJsonObj.get(position).getIconCommunityName().equalsIgnoreCase("Case")) {
            viewHolder.community_icon.setImageResource(R.drawable.ic_case);
        } else if (mChooseCommunityJsonObj.get(position).getIconCommunityName().equalsIgnoreCase("Post")) {
            viewHolder.community_icon.setImageResource(R.drawable.ic_post_news);
        } else if (mChooseCommunityJsonObj.get(position).getIconCommunityName().equalsIgnoreCase("Question")) {
            viewHolder.community_icon.setImageResource(R.drawable.ic_ask_question);
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView community_name;
        TextView community_sub_name;
        ImageView community_icon;
    }
}
