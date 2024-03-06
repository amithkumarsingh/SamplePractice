package com.vam.whitecoats.ui.adapters;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ConnectNotificationsAsync;
import com.vam.whitecoats.constants.NotificationType;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmNotifications;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.DateUtils;
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
import java.util.Hashtable;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by swathim on 21-08-2015.
 */
public class NotificationInviteAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
//    private final ArrayList<ContactsInfo> notifyConnecstInviteInfo;
    private Realm realm;
    private RealmManager realmManager;
    private AlertDialog.Builder builder;
    private long mLastClickTime = 0;
    private File myDirectory,file;
    private FileOutputStream fos;
    private ViewHolder holder;
    private Hashtable<String,Integer> img_status = new Hashtable<String,Integer>();
    RealmResults<RealmNotifications> notifications;
    public static final String TAG = NotificationInviteAdapter.class.getSimpleName();

    public NotificationInviteAdapter(Context mContext, RealmResults<RealmNotifications> notifyConnecstInviteInfoArrayList) {
        this.mContext                   = mContext;
        mInflater                       = LayoutInflater.from(mContext);
//        this.notifyConnecstInviteInfo   = notifyConnecstInviteInfoArrayList;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);
        this.notifications = notifyConnecstInviteInfoArrayList;
        for( RealmNotifications contactsInfo:notifications)
            if(!contactsInfo.getDoc_pic().equals(""))
                img_status.put(contactsInfo.getDoc_pic(),0);

    }

    public void setNotifications(RealmResults<RealmNotifications> notifications) {
        this.notifications = notifications;
        for( RealmNotifications contactsInfo:this.notifications)
            if(!contactsInfo.getDoc_pic().equals(""))
                img_status.put(contactsInfo.getDoc_pic(),0);
    }

    @Override
    public int getCount() {
        if(notifications == null) {
            return 0;
        }
        return notifications.size();
//        return notifyConnecstInviteInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(final int position,View convertView, ViewGroup parent) {

            //ViewHolder holder ;

            if(convertView == null) {
                holder                   = new ViewHolder();
                convertView              = mInflater.inflate(R.layout.connects_notify_list_item, null);
                holder. ig_notify        = (ImageView) convertView.findViewById(R.id.notification_img);
                holder.tv_name           = (TextView) convertView.findViewById(R.id.notify_item_name_txt);
                holder.tv_splty          = (TextView) convertView.findViewById(R.id.notify_item_splty_txt);
                holder.tv_message        = (TextView) convertView.findViewById(R.id.message_text);
                holder.tv_time           = (TextView) convertView.findViewById(R.id.invitation_time);
                holder.ig_accept         = (ImageView) convertView.findViewById(R.id.notify_accept_ig);
                holder.ig_reject         = (ImageView) convertView.findViewById(R.id.notify_reject_ig);
                holder.centeredProgerssBar             = (ProgressBar)convertView.findViewById(R.id.centered_progressbar);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
        if(notifications.get(position).getNotification_type().equals(NotificationType.INVITE_USER_FOR_CONNECT.name())) {
            holder.tv_message.setVisibility(View.VISIBLE);
            holder.ig_accept.setVisibility(View.VISIBLE);
            holder.ig_reject.setVisibility(View.VISIBLE);

            holder.tv_name.setText(notifications.get(position).getUser_salutation()+" "+notifications.get(position).getDoc_name());
            holder.tv_splty.setText(notifications.get(position).getDoc_speciality()+", "+notifications.get(position).getDoc_location());
            if(!notifications.get(position).getMessage().equals("")) {
                holder.tv_message.setText(notifications.get(position).getMessage());
            }else {
                holder.tv_message.setVisibility(View.GONE);
            }
            holder.tv_time.setText(DateUtils.convertLongtoDate(notifications.get(position).getTime()));

            String pic_name ="";
            int doc_id=notifications.get(position).getDoc_id();
            if(notifications.get(position).getDoc_pic()!=null)
                    pic_name =notifications.get(position).getDoc_pic();
            if (notifications.get(position).getDoc_pic_url() != null && !notifications.get(position).getDoc_pic_url().isEmpty()) {
                /*Picasso.with(mContext)
                        .load(notifications.get(position).getDoc_pic_url().trim())
                        .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                        .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(holder.ig_notify);*/
                AppUtil.loadCircularImageUsingLib(mContext,notifications.get(position).getDoc_pic_url().trim(),holder.ig_notify,R.drawable.default_profilepic);

            }
            else if(!pic_name.equals("null") && !pic_name.equals("")){
                if(FileHelper.isFilePresent(notifications.get(position).getDoc_pic(),"profile",mContext)) {
                        img_status.put(pic_name,2);
                    Uri uri = Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/" + notifications.get(position).getDoc_pic()));
                    if(uri!=null)
                      holder.ig_notify.setImageURI(uri);
                    else
                        holder.ig_notify.setImageResource(R.drawable.default_profilepic);
                }else{
                    if(img_status.containsKey(pic_name) && img_status.get(pic_name)==0) {
                        img_status.put(pic_name, 1);
                        if (AppUtil.isConnectingToInternet(mContext)) {
                            new ImageDownloaderTask(holder.ig_notify, holder).execute(pic_name, "profile", String.valueOf(doc_id));
                        }else{
                            holder.ig_notify.setImageResource(R.drawable.default_profilepic);
                        }
                    }
                }
            }else{
                holder.ig_notify.setImageResource(R.drawable.default_profilepic);
            }

        }else if(notifications.get(position).getNotification_type().equals(NotificationType.INVITE_USER_FOR_CONNECT_RESPONSE.name()) ||notifications.get(position).getNotification_type().equals(NotificationType.DEFAULT_USER_CONNECT.name()) ){
            holder.tv_name.setText(notifications.get(position).getUser_salutation()+" "+notifications.get(position).getDoc_name());
            holder.tv_splty.setText(notifications.get(position).getMessage());
            holder.tv_time.setText(DateUtils.convertLongtoDate(notifications.get(position).getTime()));
            holder.tv_message.setVisibility(View.GONE);
            holder.ig_accept.setVisibility(View.GONE);
            holder.ig_reject.setVisibility(View.GONE);

            String pic_name ="";
            int doc_id=notifications.get(position).getDoc_id();
            if(notifications.get(position).getDoc_pic()!=null)
                pic_name =notifications.get(position).getDoc_pic();
            if(!pic_name.equals("null") && !pic_name.equals("")){
                if(FileHelper.isFilePresent(notifications.get(position).getDoc_pic(), "profile",mContext)) {
                        img_status.put(pic_name,2);
                    Uri uri = Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/" + notifications.get(position).getDoc_pic()));
                    if(uri!=null) {
                        holder.ig_notify.setImageURI(uri);
                    }else{
                        holder.ig_notify.setImageResource(R.drawable.default_profilepic);
                    }
                }else{
                    if(img_status.containsKey(pic_name) && img_status.get(pic_name)==0) {
                        img_status.put(pic_name, 1);
                        if (AppUtil.isConnectingToInternet(mContext)) {
                            new ImageDownloaderTask(holder.ig_notify, holder).execute(pic_name.toString(), "profile", String.valueOf(doc_id));
                        }else{
                            holder.ig_notify.setImageResource(R.drawable.default_profilepic);
                        }
                    }
                }
            }else{
                holder.ig_notify.setImageResource(R.drawable.default_profilepic);
            }
        }

        holder.ig_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForConnectOrReject("accept",position);
            }
        });

        holder.ig_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForConnectOrReject("reject",position);
            }
        });

        holder.ig_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_card_dialog);
                final ImageView ig_card = (ImageView) dialog.findViewById(R.id.visit_card_pic_img);
                final TextView tv_name = (TextView) dialog.findViewById(R.id.card_visit_other_name);
                final TextView tv_specialty = (TextView) dialog.findViewById(R.id.card_visit_others_specialty);
                final TextView tv_sub_specialty = (TextView) dialog.findViewById(R.id.card_sub_specialty);
                final TextView tv_workplace = (TextView) dialog.findViewById(R.id.card_visit_others_workplace);
                final TextView tv_location = (TextView) dialog.findViewById(R.id.card_visit_others_location);
                final TextView tv_view_complete_profile = (TextView) dialog.findViewById(R.id.view_complete_profile_tv);
                final ImageView btn_invite = (ImageView) dialog.findViewById(R.id.add_req_btn);
                final ImageView btn_msg = (ImageView) dialog.findViewById(R.id.message_btn);
                final TextView tv_email = (TextView) dialog.findViewById(R.id.card_email_id);
                final TextView tv_contactno = (TextView) dialog.findViewById(R.id.card_contact_id);
                final TextView tv_verify_email = (TextView) dialog.findViewById(R.id.verify_email);
                final TextView tv_verify_phone = (TextView) dialog.findViewById(R.id.verify_phone);

                tv_verify_email.setVisibility(View.GONE);
                tv_verify_phone.setVisibility(View.GONE);
                tv_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
                tv_contactno.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);
                tv_name.setText(notifications.get(position).getUser_salutation()+" "+notifications.get(position).getDoc_name());
                tv_specialty.setText(notifications.get(position).getDoc_speciality());
                if(notifications.get(position).getDoc_sub_speciality()!=null && !notifications.get(position).getDoc_sub_speciality().isEmpty()){
                    tv_sub_specialty.setVisibility(View.VISIBLE);
                    tv_sub_specialty.setText(notifications.get(position).getDoc_sub_speciality());
                }
                tv_workplace.setText(notifications.get(position).getDoc_workplace());
                tv_location.setText(notifications.get(position).getDoc_location());
                if (notifications.get(position).getDoc_pic() != null && !notifications.get(position).getDoc_pic().equals("null"))
                    if (FileHelper.isFilePresent(notifications.get(position).getDoc_pic(), "profile",mContext)) {
                        Uri uri = Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/" + notifications.get(position).getDoc_pic()));
                        if(uri!=null) {
                            ig_card.setImageURI(uri);
                        }else{
                            ig_card.setImageResource(R.drawable.default_profilepic);
                        }
                    } else {
                        ig_card.setImageResource(R.drawable.default_profilepic);
                    }
                else
                    ig_card.setImageResource(R.drawable.default_profilepic);

                if (!TextUtils.isEmpty(notifications.get(position).getDoc_email())) {
                    String yourString = notifications.get(position).getDoc_email();
                    tv_email.setText(yourString);
                    /*if (yourString.length() > 12) {
                        yourString = yourString.substring(0, 12) + "...";
                        tv_email.setText(yourString);
                    } else {
                        tv_email.setText(yourString); //Dont do any change
                    }*/
                } else {
                    tv_email.setVisibility(View.GONE);
                }

                if (notifications.get(position).getNotification_type().equals(NotificationType.INVITE_USER_FOR_CONNECT.name())) {
                    btn_invite.setVisibility(View.GONE);
                    btn_msg.setVisibility(View.GONE);
//                    btn_share.setVisibility(View.GONE);
                } else if (notifications.get(position).getNotification_type().equals(NotificationType.INVITE_USER_FOR_CONNECT_RESPONSE.name()) || notifications.get(position).getNotification_type().equals(NotificationType.DEFAULT_USER_CONNECT.name() )) {
                    btn_msg.setVisibility(View.GONE);
                    btn_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new ShowCard(mContext, AppUtil.convertConnectNotificationToPojo(notifications.get(position))).goToChatWindow();
                            dialog.dismiss();
                        }
                    });
                }

                if (!TextUtils.isEmpty(notifications.get(position).getDoc_phno())) {
                    tv_contactno.setText(notifications.get(position).getDoc_phno());
//                    btn_msg.setVisibility(View.GONE);
//                    btn_share.setVisibility(View.GONE);
                } else {
                    tv_contactno.setText("Not Shared");
                }
                tv_contactno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tv_contactno.getText().toString().isEmpty() || tv_contactno.getText().toString().equalsIgnoreCase("Not Shared")){
                            return;
                        }
                        AppUtil.makePhoneCall(mContext,tv_contactno.getText().toString());
                    }
                });
                tv_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tv_email.getText().toString().isEmpty()|| tv_email.getText().toString().equalsIgnoreCase("Not Shared")){
                            return;
                        }
                        AppUtil.sendEmail(mContext,tv_email.getText().toString(),"WhiteCoats","");
                    }
                });
                tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (AppUtil.isConnectingToInternet(mContext)) {
                            Intent visit_intent = new Intent(mContext, VisitOtherProfile.class);
                            visit_intent.putExtra("searchinfo", AppUtil.convertConnectNotificationToPojo(notifications.get(position)));
                            mContext.startActivity(visit_intent);
                            dialog.dismiss();
                        } else {
                            try {
                                dialog.dismiss();
                                builder = new AlertDialog.Builder(mContext);
                                builder.setMessage(Html.fromHtml(mContext.getResources().getString(R.string.sNetworkError)));
                                builder.setPositiveButton("OK", null);
                                builder.create().show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                });

                dialog.show();
            }
        });
        /**
         * Close Realm Database after processing the last item
         */
        if(position==notifications.size()-1){
            if(!realm.isClosed())
                realm.close();
        }

        return convertView;
    }

    private void requestForConnectOrReject(String action,int position) {
        if (AppUtil.isConnectingToInternet(mContext)){

            if(AppUtil.getUserVerifiedStatus() == 3) {
                try {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    } else {
                        mLastClickTime = SystemClock.elapsedRealtime();
                        JSONObject object = new JSONObject();
                        object.put("from_doc_id", realmManager.getDoc_id(realm));
                        object.put("to_doc_id", notifications.get(position).getDoc_id());
                        object.put("resp_status", action);
                        String reqData = object.toString();

                        new ConnectNotificationsAsync(mContext, RestApiConstants.CONNECT_INVITE, AppUtil.convertConnectNotificationToPojo(notifications.get(position)), new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String s) {
                                notifyDataSetChanged();
                            }
                        }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), reqData, "connects_notification");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (AppUtil.getUserVerifiedStatus() == 1) {
                AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_not_uploaded));
            } else if (AppUtil.getUserVerifiedStatus() == 2) {
                AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified));
            }
        }
    }

    private static class ViewHolder{
        TextView tv_name,tv_splty,tv_message,tv_time;
        ImageView ig_notify,ig_accept,ig_reject;
        ProgressBar centeredProgerssBar;

    }



    public class ImageDownloaderTask extends AsyncTask<String,Void,String> {
        private final WeakReference<ImageView> imageViewReference;
        private String response;
        private String tag,id;
        private Drawable placeholder;
        private String pic_name="";
        ViewHolder p_holder;

        public ImageDownloaderTask(ImageView imageView,ViewHolder holder) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            p_holder=holder;
        }

        @Override
        protected void onPreExecute() {
            p_holder.centeredProgerssBar.setVisibility(View.VISIBLE);
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
            p_holder.centeredProgerssBar.setVisibility(View.GONE);
            if (isCancelled()) {
                path = null;
            }
            try {
                if (imageViewReference != null) {
                    ImageView imageView = imageViewReference.get();
                    if (imageView != null) {
                        if (path != null) {
                            if (tag.equals("profile")) {
                                img_status.put(pic_name, 2);
                                Uri uri = Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/" + pic_name));
                                if(uri!=null) {
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
                p_holder.centeredProgerssBar.setVisibility(View.GONE);
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
