package com.vam.whitecoats.ui.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ConnectNotificationsAsync;
import com.vam.whitecoats.constants.NotificationType;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CaseroomNotifyInfo;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.GroupNotifyInfo;
import com.vam.whitecoats.core.models.RealmNotificationInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.NotificationsActivity;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.FileHelper;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowCard;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;


public class NotificationsTabAdapter extends RecyclerView.Adapter {
    public static final String TAG = NotificationsTabAdapter.class.getSimpleName();
    private final NotificationsActivity notificationsActivity;

    private Context mContext;
    private int TYPE_CONNECT_NOTIFICATION = 1;
    private int TYPE_CASEROOM_NOTIFICATION = 2;
    private int TYPE_GROUP_NOTIFICATION = 3;
    private int TYPE_CONNECT_RESPONSE_NOTIFICATION = 4;
    private RealmResults<RealmNotificationInfo> notificationsList;
    private long mLastClickTime = 0;
    private Realm realm;
    private RealmManager realmManager;
    private AlertDialog.Builder builder;

    public NotificationsTabAdapter(Context mContext, RealmResults<RealmNotificationInfo> _notificationList) {
        this.mContext = mContext;
        this.notificationsList = _notificationList;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);
        notificationsActivity = (NotificationsActivity) mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_CONNECT_NOTIFICATION) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.connects_notify_list_item, viewGroup, false);
            viewHolder = new ConnectsViewHolder(view);
        } else if (viewType == TYPE_CASEROOM_NOTIFICATION) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.caseroom_notify_list_item, viewGroup, false);
            return new CaseroomsViewHolder(view);
        } else if (viewType == TYPE_GROUP_NOTIFICATION) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notificationgroupadapter_listitem, viewGroup, false);
            return new GroupsViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        if (viewHolder != null) {
            if (viewHolder instanceof ConnectsViewHolder) {
                try {
                    ConnectsViewHolder connectsViewHolder = (ConnectsViewHolder) viewHolder;
                    String data = notificationsList.get(position).getNotifyData();
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optString("type").equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT_RESPONSE.name())
                            || jsonObject.optString("type").equalsIgnoreCase(NotificationType.DEFAULT_USER_CONNECT.name())) {

                        connectsViewHolder.ig_accept.setVisibility(View.GONE);
                        connectsViewHolder.ig_reject.setVisibility(View.GONE);
                        connectsViewHolder.tv_message.setVisibility(View.GONE);
                        connectsViewHolder.tv_time.setText(DateUtils.convertLongtoDate(jsonObject.optLong(RestUtils.TAG_TIME_RECEIVED)));
                        if (jsonObject.has(RestUtils.TAG_NOTI_INFO)) {
                            JSONObject fromDocJsonObject = jsonObject.optJSONObject(RestUtils.TAG_NOTI_INFO);
                            connectsViewHolder.tv_name.setText(fromDocJsonObject.optString(RestUtils.TAG_USER_SALUTAION)+fromDocJsonObject.optString(RestUtils.TAG_FNAME)+" "+fromDocJsonObject.optString(RestUtils.TAG_LNAME));
                            if(jsonObject.optString("type").equalsIgnoreCase(NotificationType.DEFAULT_USER_CONNECT.name())) {
                                connectsViewHolder.tv_splty.setText(jsonObject.optString("notificationMsg"));
                            }else{
                                connectsViewHolder.tv_splty.setText(fromDocJsonObject.optString(RestUtils.TAG_INVITE_TEXT));
                            }

                            String ConnectsImageUrl = fromDocJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL);
                            if (ConnectsImageUrl != null && !ConnectsImageUrl.isEmpty()) {
                                loadImageUsingLib(mContext,fromDocJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL),connectsViewHolder.ig_notify,R.drawable.default_profilepic);
                            }
                        }
                        connectsViewHolder.tv_splty.setText(jsonObject.optString("notificationMsg"));
                        ((ConnectsViewHolder) viewHolder).ig_notify.setOnClickListener(new View.OnClickListener() {
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
                                String data = notificationsList.get(position).getNotifyData();
                                try {
                                    ContactsInfo connectsInfo = AppUtil.convertNetworkNotificationToPojo(notificationsList.get(position));
                                    tv_name.setText(connectsInfo.getName());
                                    tv_specialty.setText(connectsInfo.getSpeciality());
                                    if (connectsInfo.getSubSpeciality() != null && !connectsInfo.getSubSpeciality().isEmpty()) {
                                        tv_sub_specialty.setVisibility(View.VISIBLE);
                                        tv_sub_specialty.setText(connectsInfo.getSubSpeciality());
                                    }
                                    tv_workplace.setText(connectsInfo.getWorkplace());
                                    tv_location.setText(connectsInfo.getLocation());
                                    if (connectsInfo.getPic_url() != null && !connectsInfo.getPic_url().equals("null")) {

                                        loadImageUsingLib(mContext,connectsInfo.getPic_url().trim(),ig_card,R.drawable.default_profilepic);
                                    }
                                    else {
                                        ig_card.setImageResource(R.drawable.default_profilepic);
                                    }

                                    if (!TextUtils.isEmpty(connectsInfo.getCnt_email())) {
                                        String yourString = connectsInfo.getCnt_email();
                                        tv_email.setText(yourString);
                                        if (yourString.length() > 12) {
                                            yourString = yourString.substring(0, 12) + "...";
                                            tv_email.setText(yourString);
                                        } else {
                                            tv_email.setText(yourString); //Dont do any change
                                        }
                                    } else {
                                        tv_email.setVisibility(View.GONE);
                                    }


                                    if (!TextUtils.isEmpty(connectsInfo.getCnt_num())) {
                                        tv_contactno.setText(connectsInfo.getCnt_num());
                                    } else {
                                        tv_contactno.setText("Not Shared");
                                    }
                                    tv_contactno.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (tv_contactno.getText().toString().isEmpty() || tv_contactno.getText().toString().equalsIgnoreCase("Not Shared")) {
                                                return;
                                            }
                                            AppUtil.makePhoneCall(mContext, tv_contactno.getText().toString());
                                        }
                                    });
                                    tv_email.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (tv_email.getText().toString().isEmpty() || tv_email.getText().toString().equalsIgnoreCase("Not Shared")) {
                                                return;
                                            }
                                            AppUtil.sendEmail(mContext, tv_email.getText().toString(), "WhiteCoats", "");
                                        }
                                    });
                                    tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (AppUtil.isConnectingToInternet(mContext)) {
                                                Intent visit_intent = new Intent(mContext, VisitOtherProfile.class);
                                                visit_intent.putExtra("searchinfo", AppUtil.convertNetworkNotificationToPojo(notificationsList.get(position)));
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
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                       } else {
                        connectsViewHolder.ig_accept.setVisibility(View.VISIBLE);
                        connectsViewHolder.ig_reject.setVisibility(View.VISIBLE);
                        connectsViewHolder.tv_message.setVisibility(View.VISIBLE);
                        connectsViewHolder.tv_time.setText(DateUtils.convertLongtoDate(jsonObject.optLong(RestUtils.TAG_TIME_RECEIVED)));

                        if (jsonObject.has(RestUtils.TAG_NOTI_INFO)) {
                            JSONObject fromDocJsonObject = jsonObject.optJSONObject(RestUtils.TAG_NOTI_INFO);
                            connectsViewHolder.tv_name.setText(fromDocJsonObject.optString(RestUtils.TAG_USER_SALUTAION)+fromDocJsonObject.optString(RestUtils.TAG_FNAME)+" "+fromDocJsonObject.optString(RestUtils.TAG_LNAME));
                            connectsViewHolder.tv_splty.setText(fromDocJsonObject.optString(RestUtils.TAG_SPLTY) + ", " + fromDocJsonObject.optString(RestUtils.TAG_LOCATION));
                            connectsViewHolder.tv_message.setText(fromDocJsonObject.optString(RestUtils.TAG_INVITE_TEXT));
                            String ConnectsImageUrl = fromDocJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL);
                            if (ConnectsImageUrl != null && !ConnectsImageUrl.isEmpty()) {
                                loadImageUsingLib(mContext,fromDocJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL),connectsViewHolder.ig_notify,R.drawable.default_profilepic);
                            }
                        }
                        ((ConnectsViewHolder) viewHolder).ig_accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestForConnectOrReject("accept", position);
                            }
                        });
                        ((ConnectsViewHolder) viewHolder).ig_reject.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestForConnectOrReject("reject", position);
                            }
                        });
                    ((ConnectsViewHolder) viewHolder).ig_notify.setOnClickListener(new View.OnClickListener() {
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

                            btn_msg.setVisibility(View.GONE);
                            tv_verify_email.setVisibility(View.GONE);
                            tv_verify_phone.setVisibility(View.GONE);
                            tv_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
                            tv_contactno.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setCancelable(true);
                            String data = notificationsList.get(position).getNotifyData();
                            try {
                                ContactsInfo connectsInfo = AppUtil.convertNetworkNotificationToPojo(notificationsList.get(position));
                                tv_name.setText(connectsInfo.getName());
                                tv_specialty.setText(connectsInfo.getSpeciality());
                                if (connectsInfo.getSubSpeciality() != null && !connectsInfo.getSubSpeciality().isEmpty()) {
                                    tv_sub_specialty.setVisibility(View.VISIBLE);
                                    tv_sub_specialty.setText(connectsInfo.getSubSpeciality());
                                }
                                tv_workplace.setText(connectsInfo.getWorkplace());
                                tv_location.setText(connectsInfo.getLocation());
                                if (connectsInfo.getPic_url() != null && !connectsInfo.getPic_url().equals("null")){
                                    loadImageUsingLib(mContext,connectsInfo.getPic_url().trim(),ig_card,R.drawable.default_profilepic);
                                }
                                else{
                                    ig_card.setImageResource(R.drawable.default_profilepic);
                                }

                                if (!TextUtils.isEmpty(connectsInfo.getCnt_email())) {
                                    String yourString = connectsInfo.getCnt_email();
                                    tv_email.setText(yourString);
                                    if (yourString.length() > 12) {
                                        yourString = yourString.substring(0, 12) + "...";
                                        tv_email.setText(yourString);
                                    } else {
                                        tv_email.setText(yourString); //Dont do any change
                                    }
                                } else {
                                    tv_email.setVisibility(View.GONE);
                                }

                                if (!TextUtils.isEmpty(connectsInfo.getCnt_num())) {
                                    tv_contactno.setText(connectsInfo.getCnt_num());
                                } else {
                                    tv_contactno.setText("Not Shared");
                                }
                                tv_contactno.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (tv_contactno.getText().toString().isEmpty() || tv_contactno.getText().toString().equalsIgnoreCase("Not Shared")) {
                                            return;
                                        }
                                        AppUtil.makePhoneCall(mContext, tv_contactno.getText().toString());
                                    }
                                });
                                tv_email.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (tv_email.getText().toString().isEmpty() || tv_email.getText().toString().equalsIgnoreCase("Not Shared")) {
                                            return;
                                        }
                                        AppUtil.sendEmail(mContext, tv_email.getText().toString(), "WhiteCoats", "");
                                    }
                                });
                                tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        if (AppUtil.isConnectingToInternet(mContext)) {
                                            Intent visit_intent = new Intent(mContext, VisitOtherProfile.class);
                                            visit_intent.putExtra("searchinfo", AppUtil.convertNetworkNotificationToPojo(notificationsList.get(position)));
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                } catch(JSONException jsonExeption){
                    jsonExeption.printStackTrace();
                }
            } else if (viewHolder instanceof CaseroomsViewHolder) {
                try {
                    CaseroomsViewHolder caseroomsViewHolder = (CaseroomsViewHolder) viewHolder;
                    String data = notificationsList.get(position).getNotifyData();
                    JSONObject jsonObject = new JSONObject(data);
                    caseroomsViewHolder.tv_time.setText(DateUtils.convertLongtoDate(jsonObject.optLong(RestUtils.TAG_TIME_RECEIVED)));

                    if (jsonObject.has(RestUtils.TAG_CASE_ROOM_INVITE_INFO)) {
                        JSONObject CaseroomInviteInfoJsonObject = jsonObject.optJSONObject(RestUtils.TAG_CASE_ROOM_INVITE_INFO);
                        caseroomsViewHolder.tv_case_title.setText(CaseroomInviteInfoJsonObject.optString(RestUtils.TAG_CASE_ROOM_TITLE));
                        caseroomsViewHolder.tv_case_specialities.setText(CaseroomInviteInfoJsonObject.optString(RestUtils.TAG_SPLTY));
                    }
                    if (jsonObject.has(RestUtils.TAG_INVITER_INFO)) {
                        JSONObject caseroomInviterInfoJsonObject = jsonObject.optJSONObject(RestUtils.TAG_INVITER_INFO);
                        caseroomsViewHolder.tv_doc_name.setText(caseroomInviterInfoJsonObject.optString(RestUtils.TAG_FULL_NAME));
                        caseroomsViewHolder.tv_doc_speciality.setText(caseroomInviterInfoJsonObject.optString(RestUtils.TAG_SPLTY) + ", " + caseroomInviterInfoJsonObject.optString(RestUtils.TAG_LOCATION));
                        String CaseroomImageUrl = caseroomInviterInfoJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL);
                        if (CaseroomImageUrl != null && !CaseroomImageUrl.isEmpty()) {
                            loadImageUsingLib(mContext,caseroomInviterInfoJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL),caseroomsViewHolder.ig_case_admin_pic,R.drawable.default_profilepic);
                        }
                    }
                    ((CaseroomsViewHolder) viewHolder).ig_accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestForAcceptOrRejectCase(true, position);
                        }
                    });

                    ((CaseroomsViewHolder) viewHolder).ig_reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestForAcceptOrRejectCase(false, position);
                        }
                    });
                    ((CaseroomsViewHolder) viewHolder).ig_case_admin_pic.setOnClickListener(new View.OnClickListener() {
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
                            CaseroomNotifyInfo caseroomNotifyInfo = AppUtil.convertCaseRoomNetworkNotificationToPojo(notificationsList.get(position));
                            tv_name.setText(caseroomNotifyInfo.getUser_salutation() + " " + caseroomNotifyInfo.getDoc_name());
                            tv_specialty.setText(caseroomNotifyInfo.getDoc_speciality());
                            //pardha added for sub-speciality
                            if (caseroomNotifyInfo.getCase_sub_speciality() != null && !caseroomNotifyInfo.getCase_sub_speciality().isEmpty()) {
                                tv_sub_specialty.setVisibility(View.VISIBLE);
                                tv_sub_specialty.setText(caseroomNotifyInfo.getCase_sub_speciality());
                            }
                            tv_workplace.setText(caseroomNotifyInfo.getDoc_workplace());
                            tv_location.setText(caseroomNotifyInfo.getDoc_location());
                            if (caseroomNotifyInfo.getDoc_pic_name() != null && !caseroomNotifyInfo.getDoc_pic_name().equals("null"))
                                if (FileHelper.isFilePresent(caseroomNotifyInfo.getDoc_pic_name(), "profile",mContext)) {
                                    ig_card.setImageURI(Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/" + caseroomNotifyInfo.getDoc_pic_name())));
                                } else {
                                    ig_card.setImageResource(R.drawable.default_profilepic);
                                }
                            else
                                ig_card.setImageResource(R.drawable.default_profilepic);

                            if (!TextUtils.isEmpty(caseroomNotifyInfo.getDoc_cnt_email())) {
                                String yourString = caseroomNotifyInfo.getDoc_cnt_email();
                                tv_email.setText(yourString);

                            } else {
                                tv_email.setVisibility(View.GONE);
                            }

                            btn_invite.setVisibility(View.GONE);

                            if (!TextUtils.isEmpty(caseroomNotifyInfo.getDoc_cnt_num())) {
                                tv_contactno.setText(caseroomNotifyInfo.getDoc_cnt_num());
                            } else {
                                tv_contactno.setText("Not Shared");
                            }

                            tv_contactno.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (tv_contactno.getText().toString().isEmpty() || tv_contactno.getText().toString().equalsIgnoreCase("Not Shared")) {
                                        return;
                                    }
                                    AppUtil.makePhoneCall(mContext, tv_contactno.getText().toString());
                                }
                            });
                            tv_email.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (tv_email.getText().toString().isEmpty() || tv_email.getText().toString().equalsIgnoreCase("Not Shared")) {
                                        return;
                                    }
                                    AppUtil.sendEmail(mContext, tv_email.getText().toString(), "WhiteCoats", "");
                                }
                            });
                            tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if (AppUtil.isConnectingToInternet(mContext)) {
                                        Intent visit_intent = new Intent(mContext, VisitOtherProfile.class);
                                        ContactsInfo contactsInfo = new ContactsInfo();
                                        contactsInfo.setDoc_id(caseroomNotifyInfo.getDoc_id());
                                        contactsInfo.setQb_userid(Integer.parseInt(caseroomNotifyInfo.getDoc_qb_user_id()));
                                        contactsInfo.setName(caseroomNotifyInfo.getDoc_name());
                                        contactsInfo.setPic_data(caseroomNotifyInfo.getDoc_pic_name());
                                        contactsInfo.setWorkplace(caseroomNotifyInfo.getDoc_workplace());
                                        contactsInfo.setLocation(caseroomNotifyInfo.getDoc_location());
                                        contactsInfo.setEmail(caseroomNotifyInfo.getDoc_cnt_email());
                                        contactsInfo.setEmail_vis(caseroomNotifyInfo.getDoc_email_vis());
                                        contactsInfo.setPhno(caseroomNotifyInfo.getDoc_cnt_num());
                                        contactsInfo.setPhno_vis(caseroomNotifyInfo.getDoc_phno_vis());
                                        contactsInfo.setPic_name(caseroomNotifyInfo.getDoc_pic_name());
                                        contactsInfo.setPic_url(caseroomNotifyInfo.getDoc_pic_url());
                                        contactsInfo.setUserSalutation(caseroomNotifyInfo.getUser_salutation());
                                        contactsInfo.setUserTypeId(caseroomNotifyInfo.getUser_type_id());
                                        visit_intent.putExtra("searchinfo", contactsInfo);
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

                            btn_msg.setVisibility(View.GONE);
                            btn_msg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ContactsInfo contactsInfo = new ContactsInfo();
                                    contactsInfo.setDoc_id(caseroomNotifyInfo.getDoc_id());
                                    contactsInfo.setQb_userid(Integer.parseInt(caseroomNotifyInfo.getDoc_qb_user_id()));
                                    contactsInfo.setName(caseroomNotifyInfo.getDoc_name());
                                    contactsInfo.setPic_data(caseroomNotifyInfo.getDoc_pic_name());
                                    contactsInfo.setWorkplace(caseroomNotifyInfo.getDoc_workplace());
                                    contactsInfo.setLocation(caseroomNotifyInfo.getDoc_location());
                                    contactsInfo.setEmail(caseroomNotifyInfo.getDoc_cnt_email());
                                    contactsInfo.setEmail_vis(caseroomNotifyInfo.getDoc_email_vis());
                                    contactsInfo.setPhno(caseroomNotifyInfo.getDoc_cnt_num());
                                    contactsInfo.setPhno_vis(caseroomNotifyInfo.getDoc_phno_vis());
                                    contactsInfo.setPic_name(caseroomNotifyInfo.getDoc_pic_name());
                                    contactsInfo.setPic_url(caseroomNotifyInfo.getDoc_pic_url());
                                    contactsInfo.setUserSalutation(caseroomNotifyInfo.getUser_salutation());
                                    contactsInfo.setUserTypeId(caseroomNotifyInfo.getUser_type_id());
                                    new ShowCard(mContext, contactsInfo).goToChatWindow("CaseRoomNotificationAdapter");
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    ((CaseroomsViewHolder) viewHolder).tv_case_summary.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (AppUtil.isConnectingToInternet(mContext)) {
                                try {
                                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {

                                    } else {
                                        mLastClickTime = SystemClock.elapsedRealtime();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                builder = new AlertDialog.Builder(mContext);
                                builder.setMessage(Html.fromHtml(mContext.getResources().getString(R.string.sNetworkError)));
                                builder.setPositiveButton("OK", null);
                                builder.create().show();

                            }
                        }
                    });

                } catch (JSONException jsonExeption) {
                    jsonExeption.printStackTrace();
                }

            } else if (viewHolder instanceof GroupsViewHolder) {
                try {
                    GroupsViewHolder groupsViewHolder = (GroupsViewHolder) viewHolder;

                    String data = notificationsList.get(position).getNotifyData();
                    JSONObject jsonObject = new JSONObject(data);
                    groupsViewHolder.tv_group_notifyTime.setText(DateUtils.convertLongtoDate(jsonObject.optLong(RestUtils.TAG_TIME_RECEIVED)));
                    if (jsonObject.has(RestUtils.TAG_GROUP_INFO)) {
                        JSONObject groupInviteInfoJsonObject = jsonObject.optJSONObject(RestUtils.TAG_GROUP_INFO);
                        groupsViewHolder.tv_groupName.setText(groupInviteInfoJsonObject.optString(RestUtils.TAG_GROUP_TITLE));
                        //groupsViewHolder.setText(GroupInviteInfoJsonObject.optString(RestUtils.TAG_GROUP_TITLE));
                        String GroupInfoImageUrl = groupInviteInfoJsonObject.optString(RestUtils.TAG_LOG_SMALL_URL);

                        if (GroupInfoImageUrl != null && !GroupInfoImageUrl.isEmpty()) {
                            loadImageUsingLib(mContext,groupInviteInfoJsonObject.optString(RestUtils.TAG_LOG_SMALL_URL),groupsViewHolder.ig_group,R.drawable.default_profilepic);
                        }
                    }
                    if (jsonObject.has(RestUtils.TAG_INVITER_INFO)) {
                        JSONObject groupInviterInfoJsonObject = jsonObject.optJSONObject(RestUtils.TAG_INVITER_INFO);
                        groupsViewHolder.tv_group_adminName.setText(groupInviterInfoJsonObject.optString(RestUtils.TAG_FULL_NAME));
                        groupsViewHolder.tv_group_adminSplty.setText(groupInviterInfoJsonObject.optString(RestUtils.TAG_SPLTY) + ", " + groupInviterInfoJsonObject.optString(RestUtils.TAG_LOCATION));
                        String GroupImageUrl = groupInviterInfoJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL);
                        if (GroupImageUrl != null && !GroupImageUrl.isEmpty()) {
                            loadImageUsingLib(mContext,groupInviterInfoJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL),groupsViewHolder.ig_admin,R.drawable.default_profilepic);
                        }


                        ((GroupsViewHolder) viewHolder).ig_accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestForAcceptOrRejectGrp(true, position);
                            }
                        });

                        ((GroupsViewHolder) viewHolder).ig_reject.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestForAcceptOrRejectGrp(false, position);
                            }
                        });

                        ((GroupsViewHolder) viewHolder).ig_admin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
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
                                    GroupNotifyInfo groupNotifyInfo = AppUtil.convertNetworkGroupNotificationToPojo(notificationsList.get(position));

                                    tv_name.setText(groupNotifyInfo.getUser_salutation() + " " + groupNotifyInfo.getGroup_admin_name());
                                    tv_specialty.setText(groupNotifyInfo.getGroup_admin_specialty());
                                    if (groupNotifyInfo.getGroup_admin_sub_specialty() != null && !groupNotifyInfo.getGroup_admin_sub_specialty().isEmpty()) {
                                        tv_sub_specialty.setVisibility(View.VISIBLE);
                                        tv_sub_specialty.setText(groupNotifyInfo.getGroup_admin_sub_specialty());
                                    }
                                    tv_workplace.setText(groupNotifyInfo.getGroup_admin_workplace());
                                    tv_location.setText(groupNotifyInfo.getGroup_admin_location());
                                    if (!groupNotifyInfo.getGroup_admin_pic().equals("null")) {
                                        if (FileHelper.isFilePresent(groupNotifyInfo.getGroup_admin_pic(), "profile",mContext))
                                            ig_card.setImageURI(Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/" + groupNotifyInfo.getGroup_admin_pic())));
                                        else
                                            ig_card.setImageResource(R.drawable.default_profilepic);
                                    }
                                    if (!TextUtils.isEmpty(groupNotifyInfo.getGroup_admin_email())) {
                                        String yourString = groupNotifyInfo.getGroup_admin_email();

                                        tv_email.setText(yourString);
                                    } else {
                                        tv_email.setVisibility(View.GONE);
                                    }

                                    if (!TextUtils.isEmpty(groupNotifyInfo.getGroup_admin_phno())) {
                                        tv_contactno.setText(groupNotifyInfo.getGroup_admin_phno());
                                    } else {
                                        tv_contactno.setText("Not Shared");
                                    }

                                    tv_contactno.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (tv_contactno.getText().toString().isEmpty() || tv_contactno.getText().toString().equalsIgnoreCase("Not Shared")) {
                                                return;
                                            }
                                            AppUtil.makePhoneCall(mContext, tv_contactno.getText().toString());
                                        }
                                    });
                                    tv_email.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (tv_email.getText().toString().isEmpty() || tv_email.getText().toString().equalsIgnoreCase("Not Shared")) {
                                                return;
                                            }
                                            AppUtil.sendEmail(mContext, tv_email.getText().toString(), "WhiteCoats", "");
                                        }
                                    });
                                    tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (AppUtil.isConnectingToInternet(mContext)) {
                                                Intent visit_intent = new Intent(mContext, VisitOtherProfile.class);
                                                ContactsInfo contactsInfo = new ContactsInfo();
                                                contactsInfo.setDoc_id(groupNotifyInfo.getGroup_admin_Doc_id());
                                                contactsInfo.setQb_userid(Integer.parseInt(groupNotifyInfo.getGroup_admin_qb_user_id()));
                                                contactsInfo.setName(groupNotifyInfo.getGroup_admin_name());
                                                contactsInfo.setPic_data(groupNotifyInfo.getGroup_admin_pic());
                                                contactsInfo.setWorkplace(groupNotifyInfo.getGroup_admin_workplace());
                                                contactsInfo.setLocation(groupNotifyInfo.getGroup_admin_location());
                                                contactsInfo.setEmail(groupNotifyInfo.getGroup_admin_email());
                                                contactsInfo.setEmail_vis(groupNotifyInfo.getGroup_admin_email_vis());
                                                contactsInfo.setPhno(groupNotifyInfo.getGroup_admin_phno());
                                                contactsInfo.setPhno_vis(groupNotifyInfo.getGroup_admin_phno_vis());
                                                contactsInfo.setPic_name(groupNotifyInfo.getGroup_admin_pic());
                                                contactsInfo.setPic_url(groupNotifyInfo.getGroup_admin_pic_url());
                                                contactsInfo.setUserSalutation(groupNotifyInfo.getUser_salutation());
                                                contactsInfo.setUserTypeId(groupNotifyInfo.getUser_type_id());
                                                visit_intent.putExtra("searchinfo", contactsInfo);
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

                                    btn_msg.setVisibility(View.GONE);
                                    btn_msg.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ContactsInfo contactsInfo = new ContactsInfo();
                                            contactsInfo.setDoc_id(groupNotifyInfo.getGroup_admin_Doc_id());
                                            contactsInfo.setQb_userid(Integer.parseInt(groupNotifyInfo.getGroup_admin_qb_user_id()));
                                            contactsInfo.setName(groupNotifyInfo.getGroup_admin_name());
                                            contactsInfo.setPic_data(groupNotifyInfo.getGroup_admin_pic());
                                            contactsInfo.setWorkplace(groupNotifyInfo.getGroup_admin_workplace());
                                            contactsInfo.setLocation(groupNotifyInfo.getGroup_admin_location());
                                            contactsInfo.setEmail(groupNotifyInfo.getGroup_admin_email());
                                            contactsInfo.setEmail_vis(groupNotifyInfo.getGroup_admin_email_vis());
                                            contactsInfo.setPhno(groupNotifyInfo.getGroup_admin_phno());
                                            contactsInfo.setPhno_vis(groupNotifyInfo.getGroup_admin_phno_vis());
                                            contactsInfo.setPic_name(groupNotifyInfo.getGroup_admin_pic());
                                            contactsInfo.setPic_url(groupNotifyInfo.getGroup_admin_pic_url());
                                            contactsInfo.setUserSalutation(groupNotifyInfo.getUser_salutation());
                                            contactsInfo.setUserTypeId(groupNotifyInfo.getUser_type_id());
                                            new ShowCard(mContext, contactsInfo).goToChatWindow("GroupNotificationAdapter");
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                }
                            }
                        });
                    }
                } catch (JSONException jsonExeption) {
                    jsonExeption.printStackTrace();
                }
            }
        }
    }

    private void loadImageUsingLib(Context mContext, String imgUrl, ImageView imgView, int default_profilepic) {
        AppUtil.loadCircularImageUsingLib(mContext,imgUrl,imgView,default_profilepic);
    }

    @Override
    public int getItemCount() {
        return notificationsList != null ? notificationsList.size() : 0;
    }


    private void requestForConnectOrReject(String action, int position) {
        if (AppUtil.isConnectingToInternet(mContext)) {

            if (AppUtil.getUserVerifiedStatus() == 3) {
                try {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    } else {
                        //String data = notificationsList.get(position).getNotifyData();
                        mLastClickTime = SystemClock.elapsedRealtime();
                        JSONObject object = new JSONObject();
                        JSONObject dataObject = new JSONObject(notificationsList.get(position).getNotifyData());
                        if (!dataObject.has(RestUtils.TAG_NOTI_INFO)) {
                            return;
                        }
                        JSONObject fromDocjson = dataObject.optJSONObject(RestUtils.TAG_NOTI_INFO);
                        object.put("from_doc_id", realmManager.getDoc_id(realm));
                        object.put("to_doc_id", fromDocjson.optString(RestUtils.TAG_USER_ID));
                        object.put("resp_status", action);
                        String reqData = object.toString();
                        ContactsInfo notificationsInfo = AppUtil.convertNetworkNotificationToPojo(notificationsList.get(position));
                        notificationsActivity.showProgress();
                        new VolleySinglePartStringRequest(mContext, Request.Method.POST,RestApiConstants.CONNECT_INVITE,object.toString(),"NOTIFICATION_CONNECT_ACCEPT_REJECT", new OnReceiveResponse() {
                            @Override
                            public void onSuccessResponse(String successResponse) {
                                notificationsActivity.hideProgress();
                                JSONObject responseObj = null;
                                try {
                                    responseObj = new JSONObject(successResponse);
                                    if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                        if (responseObj.has("resp_status") && responseObj.optString("resp_status").equalsIgnoreCase("accept")) {
                                            Toast.makeText(mContext, "Connect request accepted", Toast.LENGTH_LONG).show();
                                            notificationsInfo.setNetworkStatus("3");
                                            realmManager.insertMyContacts(realm, notificationsInfo);
                                        }
                                        realmManager.deleteNetworkNotification(realm, notificationsInfo.getNotification_id());
                                        notifyDataSetChanged();
                                }else if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    String errorMsg = mContext.getResources().getString(R.string.somenthing_went_wrong);
                                    if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                        errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    }
                                    if(responseObj.has(RestUtils.TAG_ERROR_CODE) && responseObj.optInt(RestUtils.TAG_ERROR_CODE)==1042){
                                        handleAccountDeleteError(notificationsInfo.getNotification_id());
                                    }
                                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(String errorResponse) {
                                notificationsActivity.hideProgress();
                                JSONObject errorObj = null;
                                try {
                                    errorObj = new JSONObject(errorResponse);
                                    Toast.makeText(mContext,"Error",Toast.LENGTH_SHORT).show();
                                    String errorMsg = mContext.getResources().getString(R.string.somenthing_went_wrong);
                                    if (errorObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                        errorMsg = errorObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    }
                                    if(errorObj.has(RestUtils.TAG_ERROR_CODE) && errorObj.optInt(RestUtils.TAG_ERROR_CODE)==1042){
                                        handleAccountDeleteError(notificationsInfo.getNotification_id());
                                    }
                                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).sendSinglePartRequest();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (AppUtil.getUserVerifiedStatus() == 1) {
                AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_not_uploaded));
            } else if (AppUtil.getUserVerifiedStatus() == 2) {
                AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified));
            }
        }
    }

    private void handleAccountDeleteError(String notification_id) {
        realmManager.deleteNetworkNotification(realm, notification_id);
        notifyDataSetChanged();
    }

    private void requestForAcceptOrRejectCase(boolean action, int position) {
        if (AppUtil.isConnectingToInternet(mContext)) {
            try {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime();
                    JSONObject jsonObject = new JSONObject();
                    final int doc_id = realmManager.getDoc_id(realm);
                    final int qb_id = realmManager.getQB_user_id(realm);
                    final String doc_name = realmManager.getDoc_name(realm);
                    String salutation = realmManager.getDocSalutation(realm);
                    JSONObject dataObject = new JSONObject(notificationsList.get(position).getNotifyData());
                    if (!dataObject.has("caseroom_invite_info")) {
                        return;
                    }
                    JSONObject fromCaseInviteInfo = dataObject.optJSONObject("caseroom_invite_info");
                    jsonObject.put("caseroom_id", fromCaseInviteInfo.optString("caseroom_id"));
                    jsonObject.put("invitee_doc_id", doc_id);
                    jsonObject.put("invitee_qb_id", qb_id);
                    jsonObject.put("accepted", action);
                    jsonObject.put("invitee_doc_name", salutation + " " + doc_name);
                    jsonObject.put("caseroom_summary_id", fromCaseInviteInfo.optString("caseroom_summary_id"));
                    String reqData = jsonObject.toString();

                    //new ConnectNotificationsAsync(mContext, RestApiConstants.CASEROOM_INVITE_RESPONSE, AppUtil.convertCaseRoomNetworkNotificationToPojo(notificationsList.get(position))).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), reqData.toString(), "caseroom_notifications");
                    new ConnectNotificationsAsync(mContext, RestApiConstants.CASEROOM_INVITE_RESPONSE, AppUtil.convertCaseRoomNetworkNotificationToPojo(notificationsList.get(position))).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), reqData.toString(), "caseroom_notifications");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void requestForAcceptOrRejectGrp(boolean action, int position) {
        if (AppUtil.isConnectingToInternet(mContext)) {
            try {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                } else {
                    mLastClickTime = SystemClock.elapsedRealtime();
                    JSONObject jsonObject = new JSONObject();
                    final int doc_id = realmManager.getDoc_id(realm);
                    final int qb_id = realmManager.getQB_user_id(realm);
                    final String doc_name = realmManager.getDoc_name(realm);
                    String salutation = realmManager.getDocSalutation(realm);
                    JSONObject dataObject = new JSONObject(notificationsList.get(position).getNotifyData());
                    if (!dataObject.has("group_info")) {
                        return;
                    }
                    JSONObject group_info = dataObject.optJSONObject("group_info");
                    jsonObject.put("group_id", group_info.optString("group_id"));
                    jsonObject.put("invitee_doc_id", doc_id);
                    jsonObject.put("invitee_qb_id", qb_id);
                    jsonObject.put("accepted", action);
                    jsonObject.put("invitee_doc_name", salutation + " " + doc_name);
                    String reqData = jsonObject.toString();

                    new ConnectNotificationsAsync(mContext, RestApiConstants.GROUP_INVITE_RESPONSE, AppUtil.convertNetworkGroupNotificationToPojo(notificationsList.get(position))).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), reqData.toString(), "group_notifications");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ConnectResponseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View mParentView;
        ImageView ig_notify, ig_accept, ig_reject;
        TextView tv_name, tv_splty, tv_message, tv_time;

        public ConnectResponseViewHolder(@NonNull View itemView) {
            super(itemView);
            mParentView = itemView;
            mParentView.setOnClickListener(this);
            ig_notify = mParentView.findViewById(R.id.notification_img);
            tv_name = mParentView.findViewById(R.id.notify_item_name_txt);
            tv_splty = mParentView.findViewById(R.id.notify_item_splty_txt);
            tv_message = mParentView.findViewById(R.id.message_text);
            tv_time = mParentView.findViewById(R.id.invitation_time);
            ig_accept = mParentView.findViewById(R.id.notify_accept_ig);
            ig_reject = mParentView.findViewById(R.id.notify_reject_ig);

            ig_accept.setVisibility(View.GONE);
            ig_reject.setVisibility(View.GONE);
            tv_message.setVisibility(View.GONE);
            tv_splty.setVisibility(View.GONE);

        }

        @Override
        public void onClick(View v) {

        }
    }

    public class ConnectsViewHolder extends RecyclerView.ViewHolder  {

        private final View mParentView;
        ImageView ig_notify, ig_accept, ig_reject;
        TextView tv_name, tv_splty, tv_message, tv_time;

        public ConnectsViewHolder(View itemView) {
            super(itemView);
            mParentView = itemView;
            ig_notify = mParentView.findViewById(R.id.notification_img);
            tv_name = mParentView.findViewById(R.id.notify_item_name_txt);
            tv_splty = mParentView.findViewById(R.id.notify_item_splty_txt);
            tv_message = mParentView.findViewById(R.id.message_text);
            tv_time = mParentView.findViewById(R.id.invitation_time);
            ig_accept = mParentView.findViewById(R.id.notify_accept_ig);
            ig_reject = mParentView.findViewById(R.id.notify_reject_ig);
        }


    }

    public class CaseroomsViewHolder extends RecyclerView.ViewHolder {
        private View mParentView;
        TextView tv_case_title, tv_case_specialities, tv_doc_name, tv_doc_speciality, tv_time, tv_case_summary;
        ImageView ig_case_admin_pic, ig_accept, ig_reject;

        public CaseroomsViewHolder(@NonNull View itemView) {
            super(itemView);
            mParentView = itemView;
            ig_case_admin_pic = (ImageView) mParentView.findViewById(R.id.caseroom_admin_image);
            tv_case_title = (TextView) mParentView.findViewById(R.id.caseroom_title);
            tv_time = (TextView) mParentView.findViewById(R.id.caseroom_time);
            tv_case_specialities = (TextView) mParentView.findViewById(R.id.caseroom_specialty);
            ig_accept = (ImageView) mParentView.findViewById(R.id.notify_caseroom_accept_ig);
            ig_reject = (ImageView) mParentView.findViewById(R.id.notify_caseroom_reject_ig);
            tv_doc_name = (TextView) mParentView.findViewById(R.id.caseroom_admin_name);
            tv_doc_speciality = (TextView) mParentView.findViewById(R.id.caseroom_admin_speciality);
            tv_case_summary = (TextView) mParentView.findViewById(R.id.caseroom_view_case_summary);

        }
    }

    public class GroupsViewHolder extends RecyclerView.ViewHolder  {
        private View mParentView;
        private TextView tv_groupName, tv_group_adminName, tv_group_adminSplty, tv_group_notifyTime;
        private ImageView ig_group, ig_admin;
        private ImageView ig_accept, ig_reject;

        public GroupsViewHolder(View itemView) {
            super(itemView);
            mParentView = itemView;
            tv_groupName = (TextView) mParentView.findViewById(R.id.groupName);
            tv_group_adminName = (TextView) mParentView.findViewById(R.id.adiminName);
            tv_group_adminSplty = (TextView) mParentView.findViewById(R.id.adminspeciality);
            tv_group_notifyTime = (TextView) mParentView.findViewById(R.id.grp_notify_time);
            ig_group = (ImageView) mParentView.findViewById(R.id.group_pic);
            ig_admin = (ImageView) mParentView.findViewById(R.id.adminProfilePic);
            ig_accept = (ImageView) mParentView.findViewById(R.id.notify_group_accept_ig);
            ig_reject = (ImageView) mParentView.findViewById(R.id.notify_group_reject_ig);

        }

    }


    @Override
    public int getItemViewType(int position) {
        try {
            JSONObject notificationJson = new JSONObject(notificationsList.get(position).getNotifyData());
            if (notificationJson.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT.name())
                    || notificationJson.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT_RESPONSE.name())
                    || notificationJson.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.DEFAULT_USER_CONNECT.name())) {
                return TYPE_CONNECT_NOTIFICATION;
            } else if (notificationJson.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.CASEROOM_INVITE.name())) {
                return TYPE_CASEROOM_NOTIFICATION;
            } else if (notificationJson.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.GROUP_CHAT_INVITE.name())) {
                return TYPE_GROUP_NOTIFICATION;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return notificationsList.get(position).hashCode();
    }
}
