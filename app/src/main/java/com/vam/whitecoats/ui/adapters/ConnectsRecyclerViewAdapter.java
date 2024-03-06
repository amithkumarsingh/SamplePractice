package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmMyContactsInfo;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.utils.AppUtil;
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

import io.realm.RealmResults;

/**
 * Created by tejaswini on 23-05-2017.
 */

public class ConnectsRecyclerViewAdapter extends RecyclerView.Adapter {

    public Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ContactsInfo> searchinfo;
    private HashMap<String, Integer> img_status = new HashMap<>();
    private DataObjectHolder mViewHolder;
    private File myDirectory, file;
    private FileOutputStream fos;
    private RealmResults<RealmMyContactsInfo> connectsList;

    public ConnectsRecyclerViewAdapter(Context mContext, ArrayList<ContactsInfo> searchinfolist) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.searchinfo = searchinfolist;
        for (ContactsInfo contactsInfo : searchinfolist)
            if (!contactsInfo.getPic_name().equals(""))
                img_status.put(contactsInfo.getPic_name(), 0);

    }

    public void setConnectsList(RealmResults<RealmMyContactsInfo> connectsList) {
        this.connectsList = connectsList;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final View mParentView;
        private final TextView names;
        private final TextView specialty;
        private final ImageView profile_pic;

        public DataObjectHolder(View mItemView) {
            super(mItemView);
            mParentView = mItemView;
            mParentView.setOnClickListener(this);
            names = (TextView) mParentView.findViewById(R.id.name_txt);
            specialty = (TextView) mParentView.findViewById(R.id.speciality_txt);
            profile_pic = (ImageView) mParentView.findViewById(R.id.imageurl);
        }

        @Override
        public void onClick(View v) {
            if (connectsList.get(getAdapterPosition()).getNetworkStatus() != null && connectsList.get(getAdapterPosition()).getNetworkStatus().equalsIgnoreCase("3")) {
                //new ShowCard(mContext, prepareConnectInfo(connectsList.get(getAdapterPosition()))).goToChatWindow("MyConnects");
            }
        }

    }

    private ContactsInfo prepareConnectInfo(RealmMyContactsInfo hallos) {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setDoc_id(hallos.getDoc_id());
        contactsInfo.setName(hallos.getName());
        contactsInfo.setSpeciality(hallos.getSpeciality());
        contactsInfo.setSubSpeciality(hallos.getSubspeciality());
        contactsInfo.setWorkplace(hallos.getWorkplace());
        contactsInfo.setLocation(hallos.getLocation());
        contactsInfo.setPic_name(hallos.getPic_name());
        contactsInfo.setPic_url(hallos.getPic_url());
        contactsInfo.setPic_data(hallos.getPic_name());
        contactsInfo.setNetworkStatus(hallos.getNetworkStatus());
        contactsInfo.setEmail(hallos.getEmail());
        contactsInfo.setPhno(hallos.getPhno());
        contactsInfo.setEmail_vis(hallos.getEmail_vis());
        contactsInfo.setPhno_vis(hallos.getPhno_vis());
        contactsInfo.setNetworkStatus(hallos.getNetworkStatus());
        contactsInfo.setQb_userid(hallos.getQb_userid());
        contactsInfo.setPic_url(hallos.getPic_url());
        contactsInfo.setUserSalutation(hallos.getUser_salutation());
        contactsInfo.setUserTypeId(hallos.getUser_type_id());
        contactsInfo.setPhno_vis(hallos.getPhno_vis());
        contactsInfo.setEmail_vis(hallos.getEmail_vis());
        return contactsInfo;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_contacts_listitem, parent, false);
        mViewHolder = new DataObjectHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((DataObjectHolder) holder).names.setText(connectsList.get(position).getUser_salutation() + " " + connectsList.get(position).getName());
        ((DataObjectHolder) holder).specialty.setText(connectsList.get(position).getSpeciality() + ", " + connectsList.get(position).getLocation());

        if (connectsList.get(position).getPic_url() != null && !connectsList.get(position).getPic_url().isEmpty()) {
            AppUtil.loadCircularImageUsingLib(mContext, connectsList.get(position).getPic_url().trim(), ((DataObjectHolder) holder).profile_pic, R.drawable.default_profilepic);
        } else {
            ((DataObjectHolder) holder).profile_pic.setImageResource(R.drawable.default_profilepic);

        }

        ((DataObjectHolder) holder).profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowCard(mContext, prepareConnectInfo(connectsList.get(position))).showMyConnectCard();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (connectsList == null) {
            return 0;
        }
        return connectsList.size();
    }

    public class ImageDownloaderTask extends AsyncTask<String, Void, String> {
        private final WeakReference<ImageView> imageViewReference;
        private String response, picName;
        private String tag, id;
        private Drawable placeholder;
        private String pic_name = "";

        public ImageDownloaderTask(ImageView imageView, DataObjectHolder holder) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            picName = imageView.getTag().toString();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient client = new HttpClient(mContext, RestApiConstants.DOWNLOAD_IMAGE_LINK);
                client.connectForSinglepart(mContext);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("file_name", params[0]);
                pic_name = params[0];
                if (params[1].equals("profile")) {
                    jsonObject.put("category", "DOC_PROFILE");
                }
                tag = params[1];
                id = params[2];
                String reqData = jsonObject.toString();
                Log.d("", "reqData" + reqData);
                client.addpara("reqData", reqData);
                response = client.getResponse();
                Log.d("DownloadImage", "Links" + response);
                response = downloadBitmap(response, params[0], tag);

            } catch (Exception e) {
                img_status.put(pic_name, 0);
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String path) {
            ImageView imageView = imageViewReference.get();
            if (isCancelled()) {
                path = null;
            }
            if (!imageView.getTag().toString().equals(picName)) {
                return;
            }
            try {

                if (path != null) {
                    if (tag.equals("profile")) {
                        img_status.put(pic_name, 2);
                        Uri uri = Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/Doc_Profiles/" + pic_name));
                        if (uri != null) {
                            imageView.setImageURI(uri);
                        } else {
                            imageView.setImageResource(R.drawable.default_profilepic);
                        }
                    }
                } else {
                    if (tag.equals("profile")) {
                        placeholder = imageView.getContext().getResources().getDrawable(R.drawable.default_profilepic);
                    }
                    imageView.setImageDrawable(placeholder);
                }
            } catch (Exception e) {
                img_status.put(pic_name, 0);
                e.printStackTrace();
            }
        }

    }

    private String downloadBitmap(String response, String pic_name, String tag) {
        HttpURLConnection urlConnection = null;
        String small_link = "";
        String fileName = "";
        try {
            JSONObject connectjObject = new JSONObject(response);
            if (connectjObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) && connectjObject.has(RestUtils.TAG_DATA)) {
                JSONObject imagelinksjObject = connectjObject.getJSONObject(RestUtils.TAG_DATA);
                small_link = imagelinksjObject.getString(RestUtils.TAG_SMALL_LINK);
            }
            if (tag.equals("profile")) {
                fileName = pic_name;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            int size = 1024;
            byte[] buf;

            URL download_url = new URL(small_link);
            myDirectory = AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/Doc_Profiles");

            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }

            file = new File(myDirectory + "/" + fileName);

            urlConnection = (HttpURLConnection) download_url.openConnection();
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
