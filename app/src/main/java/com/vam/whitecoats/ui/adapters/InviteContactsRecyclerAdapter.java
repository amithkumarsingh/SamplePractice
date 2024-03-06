package com.vam.whitecoats.ui.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.InviteContactsActivity;
import com.vam.whitecoats.ui.activities.InviteRequestActivity;
import com.vam.whitecoats.ui.activities.Network_MyConnects;
import com.vam.whitecoats.ui.activities.NotificationsActivity;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowCard;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

import static com.vam.whitecoats.ui.activities.PublicationsActivity.mLastClickTime;

/**
 * Created by tejaswini on 30-05-2017.
 */

public class InviteContactsRecyclerAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_CONNECTS = 3;
    private static final int TYPE_LIST_HEADER = 4;

    private final ArrayList<JSONObject> connectsArray;
    private final ArrayList<JSONObject> pendingNotificationList;
    private DataObjectHolder mViewHolder;
    Context context;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleItemCount, totalItemCount, pastVisiblesItems, lastVisibleItem;
    private boolean loading;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private Realm realm;
    private RealmManager realmManager;
    private int doctorId;

    private AlertDialog.Builder builder;
    private ContactsInfo contactObj;
    public static int selectedPosition = -1;
    private ProgressDialog mProgressDialog;

    public InviteContactsRecyclerAdapter(Context mContext, ArrayList<JSONObject> connectsList, RecyclerView recyclerView, ArrayList<JSONObject> pendingNotificationsList) {
        context = mContext;
        connectsArray = connectsList;
        pendingNotificationList = pendingNotificationsList;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);
        doctorId = realmManager.getDoc_id(realm);

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(context.getString(R.string.dlg_wait_please));

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            if (dy > 0) {
                                visibleItemCount = linearLayoutManager.getChildCount();
                                totalItemCount = linearLayoutManager.getItemCount();
                                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                                if (!loading) {
                                    if (lastVisibleItem != RecyclerView.NO_POSITION && lastVisibleItem == (totalItemCount - 1)) {
                                        if (onLoadMoreListener != null && AppUtil.isConnectingToInternet(context)) {
                                            onLoadMoreListener.onLoadMore();
                                            loading = true;
                                        } else {
                                            loading = false;
                                        }
                                        //Do pagination.. i.e. fetch new data
                                    }
                                }
                            }
                        }
                    });
        }

    }

    class ConnectsViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView notification_header;
        public TextView btn_seeall;
        public View view_seperator;
        public LinearLayout addChildLayout;

        public ConnectsViewHolder(View connectsItemView) {
            super(connectsItemView);
            view = connectsItemView;
            // add your ui components here like this below
            btn_seeall = (TextView) view.findViewById(R.id.btnreq_seeall);
            // view_seperator = (View) view.findViewById(R.id.separator_btn);
            notification_header = (TextView) view.findViewById(R.id.notifi_header);
            addChildLayout = (LinearLayout) view.findViewById(R.id.add_child_layout);
        }
    }

    // The ViewHolders for Header
    class HeaderListViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private final TextView contacts_text;

        public HeaderListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            // add your ui components here like this below
            contacts_text = (TextView) view.findViewById(R.id.contacts_header);
        }
    }

    // The ViewHolders for Header
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View view;
        //private final TextView contacts_text;
        public LinearLayout invite_btn_layout, btn_connects;
        private TextView connects_count;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            btn_connects = (LinearLayout) view.findViewById(R.id.linear_connects);
            connects_count = (TextView) view.findViewById(R.id.network_text_cont_num);
            invite_btn_layout = (LinearLayout) view.findViewById(R.id.btn_invite_layout);
            // add your ui components here like this below
            //contacts_text = (TextView) View.findViewById(R.id.contacts_header);
        }
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private final View mParentView;
        private final RoundedImageView profileImageview;
        private final TextView doc_name, connectTextview, specialtyTextview;
        private final RelativeLayout tile_lay;

        public DataObjectHolder(View mItemView) {
            super(mItemView);
            mParentView = mItemView;

            profileImageview = (RoundedImageView) mParentView.findViewById(R.id.imageurl);
            doc_name = (TextView) mParentView.findViewById(R.id.name_txt);
            connectTextview = (TextView) mParentView.findViewById(R.id.connect_text);
            specialtyTextview = (TextView) mParentView.findViewById(R.id.speciality_txt);
            tile_lay = (RelativeLayout) mParentView.findViewById(R.id.tile_layout);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_contacts_listitem, parent, false);
        mViewHolder = new DataObjectHolder(view);
        return mViewHolder;*/

        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_contacts_listitem, parent, false);
            viewHolder = new DataObjectHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_connects, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == TYPE_CONNECTS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_notify_pending, parent, false);
            return new ConnectsViewHolder(view);
        } else if (viewType == TYPE_LIST_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_network_header, parent, false);
            return new HeaderListViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                ((DataObjectHolder) holder).doc_name.setText(connectsArray.get(position).optString(RestUtils.TAG_USER_SALUTAION) + " " + connectsArray.get(position).optString(RestUtils.TAG_USER_FULL_NAME, ""));
                if (connectsArray.get(position).optString(RestUtils.TAG_SPLTY, "").isEmpty() ||
                        connectsArray.get(position).optString(RestUtils.TAG_LOCATION, "").isEmpty()) {
                    ((DataObjectHolder) holder).specialtyTextview.setText(connectsArray.get(position).optString(RestUtils.TAG_SPLTY, "")
                            + "" + connectsArray.get(position).optString(RestUtils.TAG_LOCATION, ""));
                } else {
                    ((DataObjectHolder) holder).specialtyTextview.setText(connectsArray.get(position).optString(RestUtils.TAG_SPLTY, "")
                            + ", " + connectsArray.get(position).optString(RestUtils.TAG_LOCATION, ""));
                }
                if (connectsArray.get(position).optString(RestUtils.TAG_PROFILE_PIC_URL) != null && !connectsArray.get(position).optString(RestUtils.TAG_PROFILE_PIC_URL).isEmpty()) {
                    /*Picasso.with(context)
                            .load(connectsArray.get(position).optString(RestUtils.TAG_PROFILE_PIC_URL).trim())
                            .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                            .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                            .into(((DataObjectHolder) holder).profileImageview);*/
                    AppUtil.loadCircularImageUsingLib(context, connectsArray.get(position).optString(RestUtils.TAG_PROFILE_PIC_URL).trim(), ((DataObjectHolder) holder).profileImageview, R.drawable.default_profilepic);
                    ((DataObjectHolder) holder).profileImageview.setContentDescription(connectsArray.get(position).optString(RestUtils.TAG_PROFILE_PIC_URL).trim());
                } else {
                    ((DataObjectHolder) holder).profileImageview.setImageResource(R.drawable.default_profilepic);
                }
                final int connectStatus = connectsArray.get(position).optInt(RestUtils.TAG_CONNECT_STATUS);
                if (connectStatus == 1) {
                    ((DataObjectHolder) holder).connectTextview.setText(R.string.str_invited);
                    ((DataObjectHolder) holder).connectTextview.setTypeface(null, Typeface.BOLD);
                    ((DataObjectHolder) holder).connectTextview.setTextColor(context.getResources().getColor(R.color.gray_border_color));
                    ((DataObjectHolder) holder).connectTextview.setBackgroundResource(R.drawable.border_gray_text);
                } else if (connectStatus == 2) {
                    ((DataObjectHolder) holder).connectTextview.setText(R.string.str_pending);
                    ((DataObjectHolder) holder).connectTextview.setTextColor(context.getResources().getColor(R.color.gray_border_color));
                    ((DataObjectHolder) holder).connectTextview.setBackgroundResource(R.drawable.border_gray_text);
                } else if (connectStatus == 0) {
                    ((DataObjectHolder) holder).connectTextview.setText(context.getString(R.string.str_connect));
                    ((DataObjectHolder) holder).connectTextview.setTextColor(context.getResources().getColor(R.color.app_green));
                    ((DataObjectHolder) holder).connectTextview.setBackgroundResource(R.drawable.border_text);
                }
                JSONObject jsonObj = connectsArray.get(position);
                contactObj = new ContactsInfo();
                contactObj.setDoc_id(jsonObj.optInt(RestUtils.TAG_DOC_ID));
                contactObj.setName(jsonObj.optString(RestUtils.TAG_USER_FULL_NAME));
                contactObj.setSpeciality(jsonObj.optString(RestUtils.TAG_SPLTY));
                contactObj.setSubSpeciality(jsonObj.optString(RestUtils.TAG_SUB_SPLTY));
                contactObj.setDegree(jsonObj.optString(RestUtils.TAG_DEGREES));
                contactObj.setWorkplace(jsonObj.optString(RestUtils.TAG_WORKPLACE));
                contactObj.setLocation(jsonObj.optString(RestUtils.TAG_LOCATION));
                contactObj.setNetworkStatus(jsonObj.optString(RestUtils.TAG_CONNECT_STATUS));
                contactObj.setEmail(jsonObj.optString(RestUtils.TAG_CNT_EMAIL));
                contactObj.setPhno(jsonObj.optString(RestUtils.TAG_CNT_NUM));
                contactObj.setDesignation(jsonObj.optString(RestUtils.TAG_DESIGNATION));
                contactObj.setPic_name(jsonObj.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                contactObj.setPic_url(jsonObj.optString(RestUtils.TAG_PROFILE_PIC_URL));
                contactObj.setUserSalutation(jsonObj.optString(RestUtils.TAG_USER_SALUTAION));
                contactObj.setUserTypeId(jsonObj.optInt(RestUtils.TAG_USER_TYPE_ID));
                contactObj.setPhno_vis(jsonObj.optString(RestUtils.TAG_CNNTMUNVIS));
                contactObj.setEmail_vis(jsonObj.optString(RestUtils.TAG_CNNTEMAILVIS));
                ((DataObjectHolder) holder).connectTextview.setTag(contactObj);
                ((DataObjectHolder) holder).tile_lay.setTag(contactObj);
                ((DataObjectHolder) holder).profileImageview.setTag(contactObj);

                ((DataObjectHolder) holder).profileImageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InviteContactsActivity.selectedPosition = position;
                        final ContactsInfo selectedDocProfile = (ContactsInfo) v.getTag();

                        final Dialog dialog = new Dialog(context);
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
                        //verification process
                        final TextView tv_verify_email = (TextView) dialog.findViewById(R.id.verify_email);
                        final TextView tv_verify_phone = (TextView) dialog.findViewById(R.id.verify_phone);
                        tv_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
                        tv_contactno.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);
                        tv_verify_email.setVisibility(View.GONE);
                        tv_verify_phone.setVisibility(View.GONE);

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setCancelable(true);
                        tv_name.setText(selectedDocProfile.getUserSalutation() + " " + selectedDocProfile.getName());
                        tv_specialty.setText(selectedDocProfile.getSpeciality());
                        if (selectedDocProfile.getSubSpeciality() != null && !selectedDocProfile.getSubSpeciality().isEmpty()) {
                            tv_sub_specialty.setVisibility(View.VISIBLE);
                            tv_sub_specialty.setText(selectedDocProfile.getSubSpeciality());
                        }

                        tv_workplace.setText(selectedDocProfile.getWorkplace());
                        tv_location.setText(selectedDocProfile.getLocation());
                        if (selectedDocProfile.getPic_url() != null && !selectedDocProfile.getPic_url().isEmpty()) {
                            /*Picasso.with(context)
                                    .load(selectedDocProfile.getPic_url().trim())
                                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(ig_card);*/

                            AppUtil.loadCircularImageUsingLib(context, selectedDocProfile.getPic_url().trim(), ig_card, R.drawable.default_profilepic);
                        } else {
                            ig_card.setImageResource(R.drawable.default_profilepic);

                        }
                        if (!TextUtils.isEmpty(selectedDocProfile.getEmail())) {
                            String yourString = selectedDocProfile.getPhno();
                            /*if (yourString.length() > 12) {
                                yourString = yourString.substring(0, 12) + "...";
                                tv_email.setText(yourString);
                            } else {
                                tv_email.setText(yourString); //Dont do any change
                            }*/
                            tv_email.setText(yourString); //Dont do any change
                        } else {
                            tv_email.setVisibility(View.GONE);
                        }

                        tv_email.setText("Not shared");
                        tv_contactno.setText("Not shared");

                        if (selectedDocProfile.getNetworkStatus().equals("0")) {
                            btn_invite.setVisibility(View.VISIBLE);
                            btn_msg.setVisibility(View.GONE);
                        } else if (selectedDocProfile.getNetworkStatus().equals("3")) {
                            btn_invite.setVisibility(View.GONE);
                            btn_msg.setVisibility(View.GONE);

                            if (selectedDocProfile.getEmail_vis() != null && selectedDocProfile.getEmail_vis().equalsIgnoreCase("SHOW_ALL")) {
                                tv_email.setText(selectedDocProfile.getEmail());
                            } else {
                                tv_email.setText("Not shared");
                            }

                            if (selectedDocProfile.getPhno_vis() != null && selectedDocProfile.getPhno_vis().equalsIgnoreCase("SHOW_ALL")) {
                                if (!TextUtils.isEmpty(selectedDocProfile.getPhno())) {
                                    tv_contactno.setText(selectedDocProfile.getPhno());
                                }
                            } else {
                                tv_contactno.setText("Not Shared");
                            }

//                            tv_email.setText(selectedDocProfile.getEmail());
//
//                            if (!TextUtils.isEmpty(selectedDocProfile.getPhno())) {
//                                tv_contactno.setText(selectedDocProfile.getPhno());
//                            }
                        } else {
                            btn_invite.setVisibility(View.GONE);
                            btn_msg.setVisibility(View.GONE);
                        }
                        tv_contactno.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tv_contactno.getText().toString().isEmpty() || tv_contactno.getText().toString().equalsIgnoreCase("Not Shared")) {
                                    return;
                                }
                                AppUtil.makePhoneCall(context, tv_contactno.getText().toString());
                            }
                        });
                        tv_email.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tv_email.getText().toString().isEmpty() || tv_email.getText().toString().equalsIgnoreCase("Not Shared")) {
                                    return;
                                }
                                AppUtil.sendEmail(context, tv_email.getText().toString(), "WhiteCoats", "");
                            }
                        });

                        btn_invite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, InviteRequestActivity.class);
                                intent.putExtra("searchContactsInfo", selectedDocProfile);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        btn_msg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                new ShowCard(context, selectedDocProfile).goToChatWindow();
                            }
                        });

                        tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                                                                        @Override
                                                                        public boolean onTouch(View v, MotionEvent event) {
                                                                            if (AppUtil.isConnectingToInternet(context)) {
                                                                                Intent visit_intent = new Intent(context, VisitOtherProfile.class);
                                                                                visit_intent.putExtra("searchinfo", selectedDocProfile);
                                                                                context.startActivity(visit_intent);
                                                                                dialog.dismiss();
                                                                            } else {
                                                                                dialog.dismiss();
                                                                                builder = new AlertDialog.Builder(context);
                                                                                builder.setMessage(Html.fromHtml(context.getResources().getString(R.string.sNetworkError)));
                                                                                builder.setPositiveButton("OK", null);
                                                                                builder.create().show();
                                                                            }
                                                                            return false;
                                                                        }
                                                                    }

                        );

                        dialog.show();

                    }
                });

                ((DataObjectHolder) holder).connectTextview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContactsInfo selectedContact = (ContactsInfo) v.getTag();
                        if ((connectStatus == 0)) {
                            InviteContactsActivity.selectedPosition = position;
                            Intent intent = new Intent(context, InviteRequestActivity.class);
                            intent.putExtra("searchContactsInfo", selectedContact);
                            intent.putExtra(RestUtils.NAVIGATATE_FROM, InviteContactsActivity.TAG);
                            context.startActivity(intent);
                        }
                    }
                });

                ((DataObjectHolder) holder).tile_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContactsInfo selectedContact = (ContactsInfo) view.getTag();
                        if ((connectStatus == 0)) {
                            InviteContactsActivity.selectedPosition = position;
                            Intent intent = new Intent(context, InviteRequestActivity.class);
                            intent.putExtra("searchContactsInfo", selectedContact);
                            intent.putExtra(RestUtils.NAVIGATATE_FROM, InviteContactsActivity.TAG);
                            context.startActivity(intent);
                        }
                    }
                });

            } else if (holder instanceof HeaderListViewHolder) {
                ((HeaderListViewHolder) holder).contacts_text.setText(R.string.contacts_list);

            } else if (holder instanceof HeaderViewHolder) {

                ((HeaderViewHolder) holder).invite_btn_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String link_val = "https://whitecoats.com/invite";
                        String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.inviate_to_whiteCoats) + Html.fromHtml(body));
                        context.startActivity(Intent.createChooser(shareIntent, "Invite via"));
                    }
                });
                //connects count list
                int connects_cou = realmManager.getMyContactsDB(realm).size();
                ((HeaderViewHolder) holder).connects_count.setText("" + connects_cou);
                ((HeaderViewHolder) holder).btn_connects.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent inviteIntent = new Intent(context, Network_MyConnects.class);
                        context.startActivity(inviteIntent);
                    }
                });

            } else if (holder instanceof ConnectsViewHolder) {
                //int size = pendingList.size();
                int size = pendingNotificationList.size();
                if (size > 0) {
                    if (size > 2) {
                        ((ConnectsViewHolder) holder).btn_seeall.setVisibility(View.VISIBLE);
                        ((ConnectsViewHolder) holder).notification_header.setVisibility(View.VISIBLE);

                    } else {
                        ((ConnectsViewHolder) holder).btn_seeall.setVisibility(View.GONE);
                        ((ConnectsViewHolder) holder).notification_header.setVisibility(View.VISIBLE);
                    }

                    //int count = ((ConnectsViewHolder) holder).addChildLayout.getChildCount();
                    //if (count >0) {
                    ((ConnectsViewHolder) holder).addChildLayout.removeAllViews();
                    for (int i = 0; i < size; i++) {
                        View view = LayoutInflater.from(context).inflate(R.layout.notify_pending, null);
                        notifyitemdata(((ConnectsViewHolder) holder), view, i);
                        ((ConnectsViewHolder) holder).addChildLayout.addView(view);
                        if (i == 1) {
                            break;
                        }
                    }
                    //}
                    ((ConnectsViewHolder) holder).btn_seeall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent inviteIntent = new Intent(context, NotificationsActivity.class);
                            inviteIntent.putExtra("TAB_NUMBER", 1);
                            context.startActivity(inviteIntent);
                        }
                    });

                } else {
                    ((ConnectsViewHolder) holder).notification_header.setVisibility(View.GONE);
                    ((ConnectsViewHolder) holder).addChildLayout.removeAllViews();
                }

            } else if (holder instanceof ProgressViewHolder) {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }
    }

    public void notifyitemdata(final ConnectsViewHolder holder, final View view, final int i) {

        ImageView profile_notify, ig_accept, ig_reject;
        TextView tv_name, tv_splty;
        LinearLayout nwte_layout;

        String pic_name = "";
        //int doc_id = pendingList.get(i).getDoc_id();
        JSONObject notificationData = pendingNotificationList.get(i);


        ig_accept = (ImageView) view.findViewById(R.id.notify_accept_ig);
        ig_reject = (ImageView) view.findViewById(R.id.notify_reject_ig);

        tv_name = (TextView) view.findViewById(R.id.nwnotify_item_name_txt);
        tv_splty = (TextView) view.findViewById(R.id.nwnotify_item_splty_txt);
        nwte_layout = (LinearLayout) view.findViewById(R.id.nwre_layout);
        if (notificationData.has(RestUtils.TAG_NOTI_INFO)) {
            JSONObject fromDocJsonObject = notificationData.optJSONObject(RestUtils.TAG_NOTI_INFO);
            //tv_name.setText(fromDocJsonObject.optString(RestUtils.TAG_USER_SALUTAION)+" "+fromDocJsonObject.optString(RestUtils.TAG_FULL_NAME));
            tv_name.setText(fromDocJsonObject.optString(RestUtils.TAG_USER_SALUTAION) + fromDocJsonObject.optString(RestUtils.TAG_FNAME) + fromDocJsonObject.optString(RestUtils.TAG_LNAME));
            tv_splty.setText(fromDocJsonObject.optString(RestUtils.TAG_SPLTY) + ", " + fromDocJsonObject.optString(RestUtils.TAG_LOCATION));
            profile_notify = (ImageView) view.findViewById(R.id.nwnotification_img);
            String ConnectsImageUrl = fromDocJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL);
            if (ConnectsImageUrl != null && !ConnectsImageUrl.isEmpty()) {
                /*Picasso.with(context)
                        .load(fromDocJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL))
                        .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                        .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(profile_notify);*/

                AppUtil.loadCircularImageUsingLib(context, fromDocJsonObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL), profile_notify, R.drawable.default_profilepic);
            } else {
                profile_notify.setImageResource(R.drawable.default_profilepic);
            }

            nwte_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int doc_id = pendingList.get(i).getDoc_id();
                    int doc_id = fromDocJsonObject.optInt(RestUtils.TAG_USER_ID);
                    if (doctorId == doc_id) {
                        return;
                    }
                    if (AppUtil.isConnectingToInternet(context)) {
                        Intent visit_intent = new Intent(context, VisitOtherProfile.class);
                        visit_intent.putExtra(RestUtils.TAG_DOC_ID, doc_id);
                        context.startActivity(visit_intent);
                    }
                }
            });
        }
        //tv_name.setText(pendingList.get(i).getUser_salutation()+" "+pendingList.get(i).getDoc_name());
        //tv_splty.setText(pendingList.get(i).getDoc_speciality() + ", " + pendingList.get(i).getDoc_location());
        /* if(pendingList.get(i).getSpeciality().isEmpty() || pendingList.get(i).getLocation().isEmpty() ) {
            tv_splty.setText(pendingList.get(i).getSpeciality() + "" + pendingList.get(i).getLocation());
        }else{
            tv_splty.setText(pendingList.get(i).getSpeciality() + ", " + pendingList.get(i).getLocation());
        }*/
        /*if (pendingList.get(i).getDoc_pic_url() != null && !pendingList.get(i).getDoc_pic_url().isEmpty()) {
            Picasso.with(context)
                    .load(pendingList.get(i).getDoc_pic_url().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(profile_notify);
        } else {
            profile_notify.setImageResource(R.drawable.default_profilepic);
        }*/



       /* nwte_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int doc_id = pendingList.get(i).getDoc_id();
                if (doctorId == doc_id) {
                    return;
                }
                if (AppUtil.isConnectingToInternet(context)) {
                    Intent visit_intent = new Intent(context, VisitOtherProfile.class);
                    visit_intent.putExtra(RestUtils.TAG_DOC_ID, doc_id);
                    context.startActivity(visit_intent);
                }
            }
        });*/
        ig_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrRejectRequest(holder, i, "accept");
            }
        });

        ig_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                acceptOrRejectRequest(holder, i, "reject");
            }
        });

    }

    private void acceptOrRejectRequest(final ConnectsViewHolder holder, final int i, String respStatus) {
        if (AppUtil.isConnectingToInternet(context)) {

            if (AppUtil.getUserVerifiedStatus() == 3) {
                try {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    } else {
                        mLastClickTime = SystemClock.elapsedRealtime();
                        JSONObject object = new JSONObject();
                        JSONObject notificationData = pendingNotificationList.get(i);
                        if (notificationData.has(RestUtils.TAG_NOTI_INFO)) {

                            if (!notificationData.has(RestUtils.TAG_NOTI_INFO)) {
                                return;
                            }
                            JSONObject fromDocJsonObject = notificationData.optJSONObject(RestUtils.TAG_NOTI_INFO);
                            object.put("from_doc_id", realmManager.getDoc_id(realm));
                            object.put("to_doc_id", fromDocJsonObject.optString("user_id"));
                            object.put("resp_status", respStatus);
                            String reqData = object.toString();

                        /*object.put("from_doc_id", realmManager.getDoc_id(realm));
                        object.put("to_doc_id", pendingList.get(i).getDoc_id());
                        object.put("resp_status", respStatus);
                        String reqData = object.toString();*/


                            ContactsInfo notify_contactsInfo = new ContactsInfo();
                            notify_contactsInfo.setNotification_id(notificationData.optString("noti_id"));
                            notify_contactsInfo.setNotification_type(notificationData.optString(RestUtils.TAG_TYPE));
                            notify_contactsInfo.setMessage(notificationData.optString("notificationMsg"));
                            notify_contactsInfo.setTime(notificationData.optInt(RestUtils.TAG_TIME_RECEIVED));
                            if (notificationData.has(RestUtils.TAG_NOTI_INFO)) {
                                JSONObject fromDocjson = notificationData.optJSONObject(RestUtils.TAG_NOTI_INFO);
                                notify_contactsInfo.setDoc_id(fromDocjson.optInt(RestUtils.TAG_USER_ID));
                                notify_contactsInfo.setCnt_email(fromDocjson.optString(RestUtils.TAG_CNT_EMAIL));
                                notify_contactsInfo.setCnt_num(fromDocjson.optString(RestUtils.TAG_CNT_NUM));
                                notify_contactsInfo.setQb_userid(fromDocjson.optInt(RestUtils.TAG_QB_USER_ID));
                                notify_contactsInfo.setSpeciality(fromDocjson.optString(RestUtils.TAG_SPLTY));
                                notify_contactsInfo.setSubSpeciality(fromDocjson.optString(RestUtils.TAG_SUB_SPECIALITY));
                                notify_contactsInfo.setUserTypeId(fromDocjson.optInt(RestUtils.TAG_USER_TYPE_ID));
                                notify_contactsInfo.setUserSalutation(fromDocjson.optString(RestUtils.TAG_USER_SALUTAION));
                                notify_contactsInfo.setLocation(fromDocjson.optString(RestUtils.TAG_LOCATION));
                                notify_contactsInfo.setWorkplace(fromDocjson.optString(RestUtils.TAG_WORKPLACE));
                                notify_contactsInfo.setPic_url(fromDocjson.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                notify_contactsInfo.setName(fromDocjson.optString(RestUtils.TAG_USER_SALUTAION) + fromDocjson.optString(RestUtils.TAG_FNAME) + " " + fromDocjson.optString(RestUtils.TAG_LNAME));
                            }
                            showProgress();

                            new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.CONNECT_INVITE, reqData.toString(), "NOTIFICATION_CONNECT_ACCEPT_REJECT_NETWORK_TAB", new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    hideProgress();
                                    JSONObject responseObject;
                                    try {
                                        responseObject = new JSONObject(successResponse);
                                        if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                            if (responseObject.has("resp_status") && responseObject.optString("resp_status").equalsIgnoreCase("accept")) {
                                                notify_contactsInfo.setNetworkStatus("3");
                                                realmManager.insertMyContacts(realm, notify_contactsInfo);
                                                realmManager.updateConnectsNotificationReadStatus(notificationData.optString("noti_id"));
                                            }
                                            pendingNotificationList.remove(i);
                                            realmManager.deleteNetworkNotification(realm, notificationData.optString("noti_id"));
                                            holder.addChildLayout.removeAllViews();
                                            notifyDataSetChanged();
                                        } else if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR) && (responseObject.optInt(RestUtils.TAG_ERROR_CODE) == 108 || responseObject.optInt(RestUtils.TAG_ERROR_CODE) == 114|| responseObject.optInt(RestUtils.TAG_ERROR_CODE)==1042)) {
                                            String errorMsg = context.getResources().getString(R.string.somenthing_went_wrong);
                                            if (responseObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                                errorMsg = responseObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                            }
                                            if(responseObject.optInt(RestUtils.TAG_ERROR_CODE)==1042){
                                                pendingNotificationList.remove(i);
                                                realmManager.deleteNetworkNotification(realm, notificationData.optString("noti_id"));
                                                holder.addChildLayout.removeAllViews();
                                                notifyDataSetChanged();
                                            }
                                            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onErrorResponse(String errorResponse) {
                                    hideProgress();
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(errorResponse);
                                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                        String errorMsg = context.getResources().getString(R.string.somenthing_went_wrong);
                                        if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                            errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                        }
                                        if(jsonObject.has(RestUtils.TAG_ERROR_CODE) && jsonObject.optInt(RestUtils.TAG_ERROR_CODE)==1042){
                                            pendingNotificationList.remove(i);
                                            realmManager.deleteNetworkNotification(realm, notificationData.optString("noti_id"));
                                            holder.addChildLayout.removeAllViews();
                                            notifyDataSetChanged();
                                        }
                                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).sendSinglePartRequest();

/*
                            new ConnectNotificationsAsync(context, RestApiConstants.CONNECT_INVITE, AppUtil.convertNetworkTabNotificationToPojo(notificationData), new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String response) {
                                    if (response != null && !response.isEmpty()) {
                                        try {
                                            JSONObject responseObject = new JSONObject(response);
                                            if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                                if (responseObject.has("resp_status") && responseObject.optString("resp_status").equalsIgnoreCase("accept")) {
                                                    notify_contactsInfo.setNetworkStatus("3");
                                                    realmManager.insertMyContacts(realm, notify_contactsInfo);
                                                    realmManager.updateConnectsNotificationReadStatus(notificationData.optString("noti_id"));
                                                }
                                                pendingNotificationList.remove(i);
                                                realmManager.deleteNetworkNotification(realm, notificationData.optString("noti_id"));
                                                holder.addChildLayout.removeAllViews();
                                                notifyDataSetChanged();
                                            } else if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR) && (responseObject.optInt(RestUtils.TAG_ERROR_CODE) == 108 || responseObject.optInt(RestUtils.TAG_ERROR_CODE) == 114)) {
                                                String errorMsg = context.getResources().getString(R.string.somenthing_went_wrong);
                                                if (responseObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                                    errorMsg = responseObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                }
                                                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), reqData, "connects_notification");
*/
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (AppUtil.getUserVerifiedStatus() == 1) {
                AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_not_uploaded));
            } else if (AppUtil.getUserVerifiedStatus() == 2) {
                AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_uploaded_but_not_verified));
            }

        }
    }

    @Override
    public int getItemCount() {
        return (connectsArray.size());
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (connectsArray.get(position) != null && connectsArray.get(position).has("header_key")) {
            return TYPE_HEADER;
        } else if (connectsArray.get(position) != null && connectsArray.get(position).has("pending_connects_key")) {
            return TYPE_CONNECTS;
        } else if (connectsArray.get(position) != null && connectsArray.get(position).has("contacts_list_header_key")) {
            return TYPE_LIST_HEADER;
        }
        return connectsArray.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    public void setLoaded() {
        loading = false;
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public synchronized void showProgress() {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
