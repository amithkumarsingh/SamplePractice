package com.vam.whitecoats.ui.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pardhasaradhid on 6/6/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        public DataObjectHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public MyRecyclerViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.DataObjectHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}