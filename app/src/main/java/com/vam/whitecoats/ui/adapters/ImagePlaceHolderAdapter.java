package com.vam.whitecoats.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.vam.whitecoats.R;

/**
 * Created by pardhasaradhid on 8/19/2017.
 */

public class ImagePlaceHolderAdapter extends BaseAdapter {

    private Context mContext;
    View view;
    ImageView imageView;

    @Override
    public int getCount() {
        return 0;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            view = new View(mContext);
            view = inflater.inflate(R.layout.imgplaceholder, null);
            imageView = (ImageView) view.findViewById(R.id.img_postattachment);
        } else {
            view = (View) convertView;
        }


        return view;
    }
}
