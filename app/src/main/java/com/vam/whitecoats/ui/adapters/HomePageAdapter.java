package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.DataObjectHolder>{

    private  JSONArray associationArrayList;
    private  Context context;
    private String[] mData;
    private  SingleClickListener sClickListener;
    private  int sSelected = -1;
    private int mPosition;

    public HomePageAdapter(String[] mData) {
        this.mData = mData;
    }

    public HomePageAdapter(Context mContext, JSONArray associationArray) {
        this.context=mContext;
        this.associationArrayList=associationArray;

    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RadioButton mRadioButton;
        TextView mTextView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.mTextView = (TextView) itemView.findViewById(R.id.single_list_item_text);
            this.mRadioButton = (RadioButton) itemView.findViewById(R.id.single_list_item_check_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view);
            //notifyDataSetChanged();

        }
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SingleClickListener clickListener) {
        sClickListener = clickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_page_child, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        String associationName = associationArrayList.optJSONObject(position).optString(RestUtils.FEED_PROVIDER_NAME);
        if (associationArrayList.optJSONObject(position).optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Network")) {
            associationName = "All Feeds";
        }
        holder.mTextView.setText(associationName);

        if (sSelected == position) {
            holder.mRadioButton.setChecked(true);
            holder.mTextView.setTextColor(context.getResources().getColor(R.color.app_green));
        } else {
            holder.mRadioButton.setChecked(false);
            holder.mTextView.setTextColor(context.getResources().getColor(R.color.actionbar_tittle_black));
        }

    }

    @Override
    public int getItemCount() {
        return associationArrayList.length();
    }

    public interface SingleClickListener {
        void onItemClickListener(int position, View view);
    }

}