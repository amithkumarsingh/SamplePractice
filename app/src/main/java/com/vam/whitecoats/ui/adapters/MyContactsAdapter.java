package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FileHelper;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowCard;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;

/**
 * Created by swathim on 6/8/2015.
 */
public class MyContactsAdapter extends BaseAdapter {

    public Context mContext;

    private ArrayList<ContactsInfo> searchinfo;
    private File myDirectory,file;
    private FileOutputStream fos;
    private HashMap<String,Integer> img_status = new HashMap< String,Integer>();
    private ViewHolder holder;
    private LayoutInflater mInflater;


    public MyContactsAdapter(Context mContext, ArrayList<ContactsInfo> searchinfolist) {
        this.mContext        = mContext;
        mInflater            = LayoutInflater.from(mContext);
        this.searchinfo = searchinfolist;
        for(ContactsInfo contactsInfo:searchinfolist)
            if (!contactsInfo.getPic_name().equals(""))
                img_status.put(contactsInfo.getPic_name(), 0);

    }


    @Override
    public int getCount() {
        return searchinfo.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder                   = new ViewHolder();
            convertView              = mInflater.inflate(R.layout.my_contacts_listitem, null);
            holder.names        = (TextView)convertView.findViewById(R.id.name_txt);
            holder.specialty    = (TextView)convertView.findViewById(R.id.speciality_txt);
            holder.profile_pic  = (ImageView)convertView.findViewById(R.id.imageurl);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.names.setText(searchinfo.get(position).getName());
        holder.specialty.setText(searchinfo.get(position).getSpeciality());
        String pic_name ="";
        int doc_id=searchinfo.get(position).getDoc_id();
        if(searchinfo.get(position).getPic_name()!=null)
            pic_name =searchinfo.get(position).getPic_name();
        if(!pic_name.equals("") && !pic_name.equals("null")){
            if(FileHelper.isFilePresent(pic_name,"profile",mContext)) {
                    img_status.put(pic_name, 2);
                    Uri uri=Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/"+pic_name));
                    if(uri!=null){
                        holder.profile_pic.setImageURI(uri);
                    }else{
                        holder.profile_pic.setImageResource(R.drawable.default_profilepic);
                    }
            }else{
                if(img_status.containsKey(pic_name) && img_status.get(pic_name)==0) {
                    img_status.put(pic_name, 1);
                    if (AppUtil.isConnectingToInternet(mContext)) {
                        holder.profile_pic.setTag(pic_name.toString());
                        new ImageDownloaderTask(holder.profile_pic, holder).execute(pic_name.toString(), "profile", String.valueOf(doc_id));
                    }else{
                        holder.profile_pic.setImageResource(R.drawable.default_profilepic);
                    }
                }
            }
        } else {
            holder.profile_pic.setImageResource(R.drawable.default_profilepic);
        }
        holder.profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowCard(mContext, searchinfo.get(position)).showMyConnectCard("MyConnects");
            }
        });
        return convertView;
    }
    private static class ViewHolder{
        private ImageView profile_pic;
        private TextView names,specialty;

    }

    public class ImageDownloaderTask extends AsyncTask<String,Void,String> {
        private final WeakReference<ImageView> imageViewReference;
        private String response,picName;
        private String tag,id;
        private Drawable placeholder;
        private String pic_name="";

        public ImageDownloaderTask(ImageView imageView,ViewHolder holder) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            picName=imageView.getTag().toString();
        }

        @Override
        protected void onPreExecute() {
            //holder.centeredProgerssBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                HttpClient client=new HttpClient(mContext,RestApiConstants.DOWNLOAD_IMAGE_LINK);
                client.connectForSinglepart(mContext);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("file_name", params[0]);
                pic_name=params[0];
                if(params[1].equals("profile")) {
                    jsonObject.put("category", "DOC_PROFILE");
                }
                tag = params[1];
                id  = params[2];
                String reqData = jsonObject.toString();
                Log.d("", "reqData" + reqData);
                client.addpara("reqData", reqData);
                response = client.getResponse();
                Log.d("DownloadImage", "Links" + response);
                response=downloadBitmap(response,params[0],tag);

            }catch (Exception e){
                img_status.put(pic_name, 0);
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String path) {
            //holder.centeredProgerssBar.setVisibility(View.GONE);
            ImageView imageView = imageViewReference.get();
            if (isCancelled()) {
                path = null;
            }
            if(!imageView.getTag().toString().equals(picName))
            {
                return;
            }
            try {
                if (imageViewReference != null) {

                    if (imageView != null) {
                        if (path != null) {
                            if (tag.equals("profile")) {
                                img_status.put(pic_name, 2);
                                Uri uri=Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/"+pic_name));
                                if(uri!=null){
                                    imageView.setImageURI(uri);
                                }else{
                                    imageView.setImageResource(R.drawable.default_profilepic);
                                }
                            }
                        } else {
                            if (tag.equals("profile")) {
                                placeholder = imageView.getContext().getResources().getDrawable(R.drawable.default_profilepic);
                            }
                            imageView.setImageDrawable(placeholder);
                        }
                    }
                }
            }catch (Exception e){
                img_status.put(pic_name, 0);
                //holder.centeredProgerssBar.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }

    }
    private String downloadBitmap(String response,String pic_name,String tag) {
        HttpURLConnection urlConnection=null;
        String small_link="";
        String fileName = "";
        try {
            JSONObject connectjObject = new JSONObject(response);
            if (connectjObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) && connectjObject.has(RestUtils.TAG_DATA)) {
                JSONObject imagelinksjObject = connectjObject.getJSONObject(RestUtils.TAG_DATA);
                small_link = imagelinksjObject.getString(RestUtils.TAG_SMALL_LINK);
            }
            if(tag.equals("profile")){
                fileName = pic_name;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            int size = 1024;
            byte[] buf;

            URL download_url = new URL(small_link);
            myDirectory = AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles");

            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }

            file = new File(myDirectory + "/" + fileName);

            urlConnection = (HttpURLConnection)download_url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            /** Read bytes to the Buffer until there is nothing more to read(-1). **/
            buf = new byte[size];

            while ((len = bis.read(buf, 0, size)) != -1) {
                baos.write(buf, 0, len);
            }
            buf = baos.toByteArray();

            fos = new FileOutputStream(file);
            fos.write(buf);
            fos.close();
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            //urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + response);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return file.getAbsolutePath();
    }
}
