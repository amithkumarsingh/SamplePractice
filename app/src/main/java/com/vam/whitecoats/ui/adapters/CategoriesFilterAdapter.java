//local changes
package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.activities.FeedSearchActivity;
import com.vam.whitecoats.ui.activities.GlobalSearchActivity;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesFilterAdapter extends RecyclerView.Adapter {
    private  OnCustomClickListener clickListner;

    private ArrayList<JSONObject> filtersList = new ArrayList<>();
    private int row_index;
    Context mContext;
    private int lastPosition = -1;



    public CategoriesFilterAdapter(Context mContext, ArrayList<JSONObject> searchTypeResultList, OnCustomClickListener onCustomClickListener) {
        this.mContext = mContext;
        this.filtersList = searchTypeResultList;
        clickListner = onCustomClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_filter_item, parent, false);
        viewHolder = new DataObjectHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((DataObjectHolder) holder).filter_button_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                if (clickListner != null) {
                    clickListner.OnCustomClick(v, position);
                }
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            ((DataObjectHolder) holder).filter_button_categories.setBackgroundResource(R.drawable.filter_border);
            ((DataObjectHolder) holder).filter_button_categories.setTextColor(mContext.getResources().getColor(R.color.white));
            ((DataObjectHolder)holder).filter_button_categories.setBackgroundResource(R.drawable.buttons_profile);

        } else {
            ((DataObjectHolder) holder).filter_button_categories.setBackgroundResource(R.drawable.dashboard_rounded);
            ((DataObjectHolder) holder).filter_button_categories.setTextColor(mContext.getResources().getColor(R.color.black));
            ((DataObjectHolder)holder).filter_button_categories.setBackgroundResource(R.drawable.my_button_bg);

        }
        //for global search and feeds search buttons
        if (mContext instanceof GlobalSearchActivity) {
            if (filtersList != null) {
                String filterName = filtersList.get(position).optString("name");
                int filterId = filtersList.get(position).optInt("id");
                ((DataObjectHolder) holder).filter_button_categories.setText(filterName);
                ((DataObjectHolder) holder).filter_button_categories.setBackgroundResource(R.drawable.dashboard_rounded);
                ((DataObjectHolder) holder).filter_button_categories.setTextColor(mContext.getResources().getColor(R.color.gray));
            }
        } else if (mContext instanceof FeedSearchActivity) {
            if (filtersList != null) {
                String filterName = filtersList.get(position).optString("name");
                int filterId = filtersList.get(position).optInt("id");
                ((DataObjectHolder) holder).filter_button_categories.setText(filterName);
                //((DataObjectHolder) holder).filter_button_categories.setBackgroundResource(R.drawable.button_border);
            }
        }
        else {
            ((DataObjectHolder)holder).filter_button_categories.setText(filtersList.get(position).optString(RestUtils.TAG_NAME));
        }
        //setAnimation(((DataObjectHolder) holder).itemView, position);
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            /*AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            itemView.startAnimation(anim);*/
            /*Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            animation.setDuration(FADE_DURATION);
            itemView.startAnimation(animation);*/
            ScaleAnimation anim = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(350);
            anim.setFillAfter(true);
            itemView.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        /*if (mContext instanceof GlobalSearchActivity) {
            return filtersList.size();
        } else if (mContext instanceof FeedSearchActivity) {
            return filtersList.size();
        }
        else {
            return categoriesFilterList.size();
        }*/
        return filtersList.size();
    }

    private class DataObjectHolder extends RecyclerView.ViewHolder {
        private final Button filter_button_categories;

        public DataObjectHolder(View view) {
            super(view);
            filter_button_categories = (Button) view.findViewById(R.id.filter_button_categories);
        }
    }
}
