package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.TouchImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by praveenkumars on 24-05-2017.
 */

public class ImageFullView_Adapter extends PagerAdapter {

    private boolean loadUsingPicasso;
    Context mContext;
    ArrayList<String> list;
    LayoutInflater mLayoutInflater;

    public ImageFullView_Adapter(Context context, ArrayList<String> list,boolean mLoadImageUsingPicasso) {
        this.list = list;
        mContext = context;
        loadUsingPicasso=mLoadImageUsingPicasso;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*View view = mLayoutInflater.inflate(R.layout.image_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_view_pager);*/
        RelativeLayout root_layout= new RelativeLayout(mContext);
        root_layout.setBackgroundResource(R.color.black);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        TouchImageView img = new TouchImageView(mContext);
        img.setLayoutParams(layoutParams);
        img.setMaxZoom(4f);
        root_layout.addView(img);
        if(loadUsingPicasso && list.get(position)!=null && !list.get(position).isEmpty()) {
            AppUtil.loadImageUsingGlide(mContext,list.get(position),img,R.drawable.default_image_feed);

        }else {
            if(list.get(position)!=null && !list.get(position).isEmpty()) {
                File imgFiles = new File(list.get(position));
                if (imgFiles.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFiles.getAbsolutePath());
                    img.setImageBitmap(myBitmap);
                } else {
                    Uri uri = Uri.parse("android.resource://com.vam.whitecoats/" + R.drawable.default_grouppic);
                    img.setImageURI(uri);
                }
            }else {
                Uri uri = Uri.parse("android.resource://com.vam.whitecoats/" + R.drawable.default_image_feed);
                img.setImageURI(uri);
            }
        }
        container.addView(root_layout);
        return root_layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}

