package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.Directory;
import com.vam.whitecoats.ui.activities.DepartmentMembersActivity;
import com.vam.whitecoats.ui.fragments.Directory_Fragment;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyasarathi.m on 20-06-2016.
 */
public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.MyRecycleViewHolder> {

    ArrayList<Directory> directoryList;
    Context context;
    int communityId;
    int channelid;
    int doctorId;


    public DirectoryAdapter(Context context, List<Directory> directoryList,int mChannelid,int doctorId) {
        this.directoryList = (ArrayList<Directory>) directoryList;
        this.context = context;
        this.channelid = mChannelid;
        this.doctorId = doctorId;
    }
    public class MyRecycleViewHolder extends RecyclerView.ViewHolder {
        TextView communityMembersCntTxt;
        TextView deptMembersCntTxt;
        TextView deptNameTxt;
        RelativeLayout directoryParentLayout;

        public MyRecycleViewHolder(View itemView) {
            super(itemView);
            directoryParentLayout = (RelativeLayout) itemView.findViewById(R.id.directoryParentLayout);
            deptNameTxt = (TextView) itemView.findViewById(R.id.departmentNameTxt);
            deptMembersCntTxt = (TextView) itemView.findViewById(R.id.departmentCountTxt);

        }
    }

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directory_row, parent, false);
        return new MyRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecycleViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof MyRecycleViewHolder) {
                Directory directory = directoryList.get(position);
                ((MyRecycleViewHolder) holder).deptNameTxt.setText(directory.getDepartmentName());
                ((MyRecycleViewHolder) holder).deptMembersCntTxt.setText(directory.getDeptMembersCount()+"");
            }
            holder.directoryParentLayout.setOnClickListener(clickListener);
            holder.directoryParentLayout.setTag(holder);

        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MyRecycleViewHolder holder = (MyRecycleViewHolder) view.getTag();
            int position = holder.getAdapterPosition();
            Directory directory = directoryList.get(position);
            Intent intent = new Intent(context, DepartmentMembersActivity.class);
            intent.putExtra(RestUtils.CHANNEL_ID, channelid);
            intent.putExtra(RestUtils.DEPARTMENT_ID, directory.getDepartmentId());
            intent.putExtra(RestUtils.TAG_DOC_ID, doctorId);
            intent.putExtra(RestUtils.LAST_MEMBER_ID, Directory_Fragment.lastRecordId);
            intent.putExtra(RestUtils.DEPARTMENT_NAME, directory.getDepartmentName());
            if(AppUtil.isConnectingToInternet(context)) {
                context.startActivity(intent);
            }

        }
    };


    @Override
    public int getItemCount() {
        return directoryList.size();
    }

}
