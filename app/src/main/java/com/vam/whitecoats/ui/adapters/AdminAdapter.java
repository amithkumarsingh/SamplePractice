package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.CommunityAdmin;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by pardhasaradhid on 6/13/2016.
 */
public class AdminAdapter extends BaseAdapter {
    List<CommunityAdmin> adminsList;
    Context context;
    public AdminAdapter(Context context, List<CommunityAdmin> adminsList) {
        this.adminsList=adminsList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return adminsList.size();
    }

    @Override
    public Object getItem(int position) {
        return adminsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return adminsList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CommunityAdmin admin=adminsList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.admin_list,null);
            holder = new ViewHolder();
            holder.adminNameTxt = (TextView) convertView.findViewById(R.id.admin_name_txt);
            holder.designationTxt = (TextView) convertView.findViewById(R.id.admin_designation_txt);
            holder.chatImgvw=(ImageView) convertView.findViewById(R.id.admin_chat_img);
            holder.adminProfileImgvw=(RoundedImageView) convertView.findViewById(R.id.admin_profile_pic);
            holder.connectTxt=(TextView) convertView.findViewById(R.id.admin_connect_text);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();

        }
        //CommunityAdmin admin=adminsList.get(position);
        Log.e("AdminAdpter","admin position **************:"+position);
        Log.e("AdminAdpter","admin name **************:"+admin.getFullName());
        /**
         * Set all values
         */

        holder.adminNameTxt.setText(admin.getFullName());
        holder.designationTxt.setText(admin.getSpeciality());
        //<editor-fold desc="connect status">
        /**
         * Connect status 0 - No connection
         *                  1 - Pending
         *                  2 - Waiting
         *                  3 - Connected
         *                  4 - Not in WhiteCoats
         */
        //</editor-fold>
        if(admin.getNetworkStatus()!=4){
            holder.chatImgvw.setImageResource(R.drawable.ic_message);
            holder.connectTxt.setVisibility(View.GONE);
        }else{
            holder.connectTxt.setVisibility(View.VISIBLE);
        }
        /**
         * Set Admin's profile pic
         */
        String profilePicName=admin.getProfilePicName();
        if (profilePicName != null && !profilePicName.equals("")) {
            Drawable yourDrawable = null;
            if (FileHelper.isFilePresent(profilePicName, "Profile_Pic",context)) {
                try {
                    Uri drawableuri = Uri.fromFile(AppUtil.getExternalStoragePathFile(context,".Whitecoats/Profile_Pic/" + profilePicName));
                    InputStream inputStream = context.getContentResolver().openInputStream(drawableuri);
                    yourDrawable = Drawable.createFromStream(inputStream, drawableuri.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                holder.adminProfileImgvw.setImageDrawable(yourDrawable);
            } else
                holder.adminProfileImgvw.setImageResource(R.drawable.default_profilepic);
        }

        return convertView;
    }
    public static class ViewHolder {
        TextView adminNameTxt;
        TextView designationTxt;
        TextView connectTxt;
        ImageView chatImgvw;
        RoundedImageView adminProfileImgvw;
    }
}
