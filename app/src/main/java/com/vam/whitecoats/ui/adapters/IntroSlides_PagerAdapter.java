package com.vam.whitecoats.ui.adapters;

/**
 * Created by praveenkumars on 05-08-2016.
 */

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vam.whitecoats.R;

public class IntroSlides_PagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    int[] mResources;
    int[] bg_colors;
    ImageView imageView;
    Button next,skip;

    public IntroSlides_PagerAdapter(Context context, int[] mResources, int[] bg_colors, Button next, Button skip) {
        mContext = context;
        this.mResources = mResources;
        this.bg_colors = bg_colors;
        this.next = next;
        this.skip = skip;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.introslides_image_display, container, false);
        imageView = (ImageView) itemView.findViewById(R.id.img);
        imageView.setImageResource(mResources[position]);
        container.addView(itemView);
        itemView.setBackgroundResource(bg_colors[position]);
        if(position==4){
            skip.setVisibility(View.INVISIBLE);
            next.setText("Get Started");
        }
        else{
            skip.setVisibility(View.VISIBLE);
            next.setText("Next");
            skip.setText("Skip");
        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}
