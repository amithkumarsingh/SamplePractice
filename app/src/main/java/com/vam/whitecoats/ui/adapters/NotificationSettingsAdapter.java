package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo;
import com.vam.whitecoats.ui.interfaces.NotificationSettingsSelection;
//import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;


public class NotificationSettingsAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private NotificationSettingsSelection callback;
    private Context mContext;
    private LayoutInflater mInflater;
    private int docId = 0;
    private RealmManager realmManager;
    private Realm realm;
    public static final String TAG = NotificationSettingsAdapter.class.getSimpleName();
    private RealmResults<RealmNotificationSettingsInfo> channelList;
    private boolean toggleState = true;
    private int selectedItemCount;


    public NotificationSettingsAdapter(Context context, RealmResults<RealmNotificationSettingsInfo> mChannelList, NotificationSettingsSelection callback) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.realm = Realm.getDefaultInstance();
        this.realmManager = new RealmManager(mContext);
        docId = realmManager.getDoc_id(realm);
        this.channelList = mChannelList;
        this.callback=callback;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_settings_row, viewGroup, false);

        /*RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.notification_settings_row, viewGroup, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);
            viewHolder = new CommentsAdapter.ProgressViewHolder(view);
        }*/
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int position) {
        DataObjectHolder dataObjectHolder = (DataObjectHolder) viewHolder;
        if (viewHolder != null) {
            if (viewHolder instanceof DataObjectHolder) {
                try {
                    dataObjectHolder.progressBar.setVisibility(View.GONE);
                    dataObjectHolder.tg_channelButton.setVisibility(View.VISIBLE);
                    RealmNotificationSettingsInfo dataObj = channelList.get(position);
                    JSONObject dataJson = new JSONObject(dataObj.getJsonData());
                    dataObjectHolder.tv_channelName.setText(dataJson.optString(RestUtils.TAG_CATEGORY_NAME));
                    dataObjectHolder.tv_channelDesc.setText(dataJson.optString(RestUtils.TAG_CATEGORY_DESC));
                    dataObjectHolder.tg_channelButton.setChecked(dataObj.isEnabled());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        ((DataObjectHolder) viewHolder).tg_channelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmNotificationSettingsInfo dataObj = channelList.get(position);
                boolean isChecked = ((DataObjectHolder) viewHolder).tg_channelButton.isChecked();
                if(isChecked){
                    selectedItemCount+=1;
                }else{
                    selectedItemCount-=1;
                }
                ((DataObjectHolder) viewHolder).tg_channelButton.setVisibility(View.GONE);
                ((DataObjectHolder) viewHolder).progressBar.setVisibility(View.VISIBLE);
                callback.toggleTapped(isChecked,position);
            }
        });

    }




    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tv_channelName, tv_channelDesc;
        ToggleButton tg_channelButton;
        ProgressBar progressBar;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tv_channelName = itemView.findViewById(R.id.channel_title);
            tv_channelDesc = itemView.findViewById(R.id.channel_desc);
            tg_channelButton = itemView.findViewById(R.id.channel_toggle_button);
            progressBar=itemView.findViewById(R.id.category_item_progress);

        }
    }

    public void setSelectedItemCount(int selectedCount){
        this.selectedItemCount=selectedCount;
    }
}
