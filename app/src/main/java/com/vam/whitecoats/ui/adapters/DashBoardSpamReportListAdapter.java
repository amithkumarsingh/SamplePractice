package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.DashBoardSpamReportListModel;

import java.util.ArrayList;

/**
 * Created by dileep on 16-01-2023.
 */

public class DashBoardSpamReportListAdapter extends BaseAdapter {

    private ArrayList<DashBoardSpamReportListModel> dashBoardSpamReportListModels = new ArrayList<>();
    public Context mContext;

    public DashBoardSpamReportListAdapter(Context context, ArrayList<DashBoardSpamReportListModel> chooseCommunityJsonObj) {
        mContext = context;
        dashBoardSpamReportListModels = chooseCommunityJsonObj;
    }

    @Override
    public int getCount() {
        return dashBoardSpamReportListModels.size();
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
            convertView = inflater.inflate(R.layout.dashboard_spam_report_bottom_list, viewGroup, false);
            viewHolder.buttonName = (TextView) convertView.findViewById(R.id.buttonName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.buttonName.setText(dashBoardSpamReportListModels.get(position).getReportSpamButton());

        return convertView;
    }

    public static class ViewHolder {
        TextView buttonName;
    }
}
