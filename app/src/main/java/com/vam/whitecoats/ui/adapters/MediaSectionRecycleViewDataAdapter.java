package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.activities.MediaFilesActivity;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tejaswini on 21-03-2017.
 */

public class MediaSectionRecycleViewDataAdapter extends RecyclerView.Adapter<MediaSectionRecycleViewDataAdapter.ItemRowHolder> {

    private ArrayList<JSONObject> dataList;
    private Context mContext;
    private int channed_Id;
    private SectionListDataAdapter itemListDataAdapter;

    public MediaSectionRecycleViewDataAdapter(Context context, ArrayList<JSONObject> dataList,int channelId) {
        this.dataList = dataList;
        this.mContext = context;
        this.channed_Id=channelId;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_tab_list_item_parent, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int position) {

        JSONObject currentObj = dataList.get(position);
        final String sectionName = currentObj.optString(RestUtils.TAG_SECTION_NAME);
        final int sectionCount = currentObj.optInt(RestUtils.TAG_COUNT);
        final JSONArray mediaTypes = currentObj.optJSONArray(RestUtils.TAG_MEDIA_TYPES);

        String next = "<font color='#EE0000'>sectionName</font>";


        StringBuilder sb = new StringBuilder(sectionName);
        for (int index = 0; index < sb.length(); index++) {
            char c = sb.charAt(index);
            if (Character.isLowerCase(c)) {
                sb.setCharAt(index, Character.toUpperCase(c));
            }

        }
        final SpannableStringBuilder sb1 = new SpannableStringBuilder(" "+"("+sectionCount+")");

        // Span to set text color to some RGB value
        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(63, 60, 61));
        // Set the text color for first 4 characters
        sb1.setSpan(fcs, 0, sb1.toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        ArrayList<JSONObject> filesList=new ArrayList<>();
        JSONArray filesArray = currentObj.optJSONArray("fileData");
        for(int i=0;i<filesArray.length();i++){
            filesList.add(filesArray.optJSONObject(i));
        }
        itemRowHolder.itemTitle.setText(sb.toString());
        itemRowHolder.itemTitle.append(sb1);
        if(sectionCount>3) {
            itemRowHolder.sectionArrowImageView.setVisibility(View.VISIBLE);
        }else{
            itemRowHolder.sectionArrowImageView.setVisibility(View.GONE);

        }
        itemRowHolder.sectionArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(mContext, MediaFilesActivity.class);
                in.putExtra(RestUtils.CHANNEL_ID,channed_Id);
                in.putExtra(RestUtils.TAG_MEDIA_TYPES,mediaTypes.toString());
                in.putExtra(RestUtils.TAG_SECTION_NAME,sectionName);
                in.putExtra(RestUtils.TAG_SECTION_COUNT,sectionCount);
                mContext.startActivity(in);
            }
        });

        itemRowHolder.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sectionCount>3) {
                    Intent in = new Intent(mContext, MediaFilesActivity.class);
                    in.putExtra(RestUtils.CHANNEL_ID, channed_Id);
                    in.putExtra(RestUtils.TAG_MEDIA_TYPES, mediaTypes.toString());
                    in.putExtra(RestUtils.TAG_SECTION_NAME, sectionName);
                    in.putExtra(RestUtils.TAG_SECTION_COUNT, sectionCount);
                    mContext.startActivity(in);
                }
            }
        });

        itemListDataAdapter = new SectionListDataAdapter(mContext, filesList,sectionName,sectionCount);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        private LinearLayout sectionArrowImageView;
        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.section_text_id);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.section_images_recycle_list);
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            int height = displayMetrics.heightPixels/6;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(displayMetrics.widthPixels,height,.90f);
            recycler_view_list.setLayoutParams(parms);
            this.sectionArrowImageView=(LinearLayout)view.findViewById(R.id.section_arrow_image);
        }
    }

    public void pauseAudio(){
        if(itemListDataAdapter!=null) {
            itemListDataAdapter.pauseAudio();
        }
    }
}
