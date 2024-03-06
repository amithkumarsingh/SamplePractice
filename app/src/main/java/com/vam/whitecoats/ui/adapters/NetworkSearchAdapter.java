package com.vam.whitecoats.ui.adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.Member;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.GlobalSearchActivity;
import com.vam.whitecoats.ui.activities.InactiveMembersCard;
import com.vam.whitecoats.ui.activities.InviteRequestActivity;
import com.vam.whitecoats.ui.activities.NetworkSearchActivity;
import com.vam.whitecoats.ui.activities.ProfileViewActivity;
import com.vam.whitecoats.ui.activities.UserSearchResultsActivity;
import com.vam.whitecoats.ui.activities.ViewContactsActivity;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.fragments.DashboardUpdatesFragment;
import com.vam.whitecoats.ui.interfaces.NavigateScreenListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowCard;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import io.realm.Realm;

/**
 * Created by pardhasaradhid on 1/8/2018.
 */

public class NetworkSearchAdapter extends RecyclerView.Adapter {
    public static final String TAG = NetworkSearchAdapter.class.getSimpleName();
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static final int VIEW_FOOTER = 2;
    private final RealmBasicInfo basicInfo;
    private LayoutInflater mInflater;
    Context context;
    List<ContactsInfo> searchResults;
    private AlertDialog.Builder builder;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleItemCount, totalItemCount, pastVisiblesItems, lastVisibleItem;
    private int docid = 0;
    private Realm realm;
    private RealmManager realmManager;
    RecyclerView mSearchRecycleView;
    String searchglobalText;
    /*Refactoring the deprecated startActivityForResults*/
    private NavigateScreenListener screenNavigate;

    public NetworkSearchAdapter(Context mContext, List<ContactsInfo> searchResults, RecyclerView searchRecycleView) {
        this.context = mContext;
        this.searchResults = searchResults;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        this.docid = realmManager.getDoc_id(realm);
        mInflater = LayoutInflater.from(context);
        this.mSearchRecycleView = searchRecycleView;
        /*this.searchContactsActivity = (SearchContactsActivity) context;
        this.viewContactsActivity = (ViewContactsActivity) context;*/
        this.screenNavigate = (NavigateScreenListener) mContext;

        if (mSearchRecycleView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mSearchRecycleView
                    .getLayoutManager();
            mSearchRecycleView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            //if (dy > 0) {
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
                            // }
                        }
                    });
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_contacts_listitem, parent, false);
            viewHolder = new DataObjectHolder(view);
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

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private View mParentView;
        private RoundedImageView profileImageview;
        private ImageView chatIcon;
        private TextView nameTxtVw, connectTxtVw, specialtyTxtVw;
        private RelativeLayout rv_tile;
        private Button followBtn;

        public DataObjectHolder(View mItemView) {
            super(mItemView);
            mParentView = mItemView;
            rv_tile = (RelativeLayout) mParentView.findViewById(R.id.tile_layout);
            profileImageview = (RoundedImageView) mParentView.findViewById(R.id.imageurl);
            chatIcon = (ImageView) mParentView.findViewById(R.id.chat_img);
            nameTxtVw = (TextView) mParentView.findViewById(R.id.name_txt);
            connectTxtVw = (TextView) mParentView.findViewById(R.id.connect_text);
            specialtyTxtVw = (TextView) mParentView.findViewById(R.id.speciality_txt);
            followBtn = (Button) mParentView.findViewById(R.id.followBtn);

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

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                final ContactsInfo contactsInfo = searchResults.get(position);
                ((DataObjectHolder) holder).chatIcon.setVisibility(View.GONE);
                ((DataObjectHolder) holder).connectTxtVw.setVisibility(View.VISIBLE);
                ((DataObjectHolder) holder).followBtn.setVisibility(View.GONE);
                ((DataObjectHolder) holder).nameTxtVw.setText(contactsInfo.getUserSalutation() + " " + contactsInfo.getFull_name());
                //((DataObjectHolder) holder).specialtyTxtVw.setText(contactsInfo.getSpeciality());
                ((DataObjectHolder) holder).specialtyTxtVw.setText(contactsInfo.getSpecialist() + ", " + contactsInfo.getLocation());

                if (docid == contactsInfo.getDoc_id()) {
                    ((DataObjectHolder) holder).nameTxtVw.setText(basicInfo.getUser_salutation() + " " + basicInfo.getFname() + " " + basicInfo.getLname());
                    ((DataObjectHolder) holder).specialtyTxtVw.setText(basicInfo.getSplty() + ", " + contactsInfo.getLocation());
                    if (!AppUtil.checkWriteExternalPermission(context)) {
                        if (basicInfo.getPic_url() != null && !basicInfo.getPic_url().isEmpty()) {
                            /*Picasso.with(context)
                                    .load(basicInfo.getPic_url().trim())
                                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(((DataObjectHolder) holder).profileImageview);*/
                            loadImagesUsingLib(context, basicInfo.getPic_url().trim(), ((DataObjectHolder) holder).profileImageview, R.drawable.default_profilepic);

                        }
                    } else if (basicInfo != null && basicInfo.getProfile_pic_path() != null && !basicInfo.getProfile_pic_path().equals("")) {
                        File imgFile = new File(basicInfo.getProfile_pic_path());
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            ((DataObjectHolder) holder).profileImageview.setImageBitmap(myBitmap);
                        } else {
                            ((DataObjectHolder) holder).profileImageview.setImageResource(R.drawable.default_profilepic);
                        }
                    } else if (basicInfo.getPic_url() != null && !basicInfo.getPic_url().isEmpty()) {
                        /*Picasso.with(context)
                                .load(basicInfo.getPic_url().trim())
                                .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                                .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(((DataObjectHolder) holder).profileImageview);*/
                        loadImagesUsingLib(context, basicInfo.getPic_url().trim(), ((DataObjectHolder) holder).profileImageview, R.drawable.default_profilepic);
                    }
                } else if (contactsInfo.getProfile_pic_small_url() != null && !contactsInfo.getProfile_pic_small_url().isEmpty()) {
                    /*Picasso.with(context)
                            .load(contactsInfo.getProfile_pic_small_url().trim())
                            .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                            .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                            .into(((DataObjectHolder) holder).profileImageview);*/
                    loadImagesUsingLib(context, contactsInfo.getProfile_pic_small_url().trim(), ((DataObjectHolder) holder).profileImageview, R.drawable.default_profilepic);

                } else {
                    ((DataObjectHolder) holder).profileImageview.setImageResource(R.drawable.default_profilepic);
                }

                ((DataObjectHolder) holder).rv_tile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (docid == contactsInfo.getDoc_id()) {
                            return;
                        } else if (contactsInfo.getFollow_status() != null) {
                            Intent visit_intent = new Intent(context, VisitOtherProfile.class);
                            visit_intent.putExtra("searchinfo", contactsInfo);
                            visit_intent.putExtra("search_query", "");
                            if (context instanceof ViewContactsActivity) {
                                /*Refactoring the deprecated startActivityForResults*/
                                screenNavigate.onScreenNavigate(visit_intent);
                            }
                        } else if (contactsInfo.getNetworkStatus().equals("0")) {
                            UserSearchResultsActivity.selectedPosition = position;
                            GlobalSearchActivity.selectedPosition = position;
                            if (((DataObjectHolder) holder).connectTxtVw.getText().toString().equals("Connect")) {
                                Intent intent = new Intent(context, InviteRequestActivity.class);
                                intent.putExtra("searchContactsInfo", contactsInfo);
                                intent.putExtra("search", "search");
                                //context.startActivity(intent);
                                /*Refactoring the deprecated startActivityForResults*/
                                screenNavigate.onScreenNavigate(intent);

                               /* if (context instanceof NetworkSearchActivity) {
                                   launcherInviteOrVisitProfileResults.launch(intent);
                                } else if (context instanceof ViewContactsActivity) {
                                    launcherInviteOrVisitProfileResults.launch(intent);
                                } else if (context instanceof GlobalSearchActivity) {
                                    launcherInviteOrVisitProfileResults.launch(intent);
                                } else if (context instanceof UserSearchResultsActivity) {
                                    launcherInviteOrVisitProfileResults.launch(intent);
                                }*/

                            }
                        } else if (contactsInfo.getNetworkStatus().equals("3")) {
                            //new ShowCard(context, "", searchResults.get(position)).goToChatWindow();
                        } else if (contactsInfo.getNetworkStatus().equals("4")) {
                            Intent intent = new Intent(context, InactiveMembersCard.class);
                            intent.putExtra(context.getString(R.string.key_inactive_member), convertContactsToMember(contactsInfo));
                            intent.putExtra(RestUtils.DEPT_OR_DESIG, "");
                            intent.putExtra(RestUtils.TAG_DESIGNATION, contactsInfo.getCommunity_designation());
                            context.startActivity(intent);
                        }
                    }
                });
                if (contactsInfo.getFollow_status() != null) {
                    ((DataObjectHolder) holder).chatIcon.setVisibility(View.GONE);
                    ((DataObjectHolder) holder).connectTxtVw.setVisibility(View.GONE);
                    ((DataObjectHolder) holder).followBtn.setVisibility(View.VISIBLE);
                    if (contactsInfo.getFollow_status().equalsIgnoreCase("1")) {
                        ((DataObjectHolder) holder).followBtn.setText("Unfollow");
                    } else {
                        ((DataObjectHolder) holder).followBtn.setText("Follow");
                    }
                } else {
                    if (contactsInfo.getNetworkStatus().equals("1")) {
                        ((DataObjectHolder) holder).connectTxtVw.setText(R.string.str_invited);
                        ((DataObjectHolder) holder).connectTxtVw.setTypeface(null, Typeface.BOLD);
                        ((DataObjectHolder) holder).connectTxtVw.setTextColor(context.getResources().getColor(R.color.gray_border_color));
                        ((DataObjectHolder) holder).connectTxtVw.setBackgroundResource(R.drawable.border_gray_text);
                    } else if (contactsInfo.getNetworkStatus().equals("2")) {
                        ((DataObjectHolder) holder).connectTxtVw.setText(R.string.str_pending);
                        ((DataObjectHolder) holder).connectTxtVw.setTextColor(context.getResources().getColor(R.color.gray_border_color));
                        ((DataObjectHolder) holder).connectTxtVw.setBackgroundResource(R.drawable.border_gray_text);
                    } else if (contactsInfo.getNetworkStatus().equals("3")) {
                        if (docid == contactsInfo.getDoc_id()) {
                            ((DataObjectHolder) holder).connectTxtVw.setText("You");
                            //((DataObjectHolder) holder).connectTxtVw.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                            ((DataObjectHolder) holder).connectTxtVw.setVisibility(View.VISIBLE);
                            ((DataObjectHolder) holder).connectTxtVw.setTypeface(null, Typeface.BOLD);
                            ((DataObjectHolder) holder).connectTxtVw.setBackground(null);
                            ((DataObjectHolder) holder).connectTxtVw.setTextColor(context.getResources().getColor(R.color.gray_border_color));
                            ((DataObjectHolder) holder).chatIcon.setVisibility(View.GONE);

                        } else {
                            ((DataObjectHolder) holder).connectTxtVw.setVisibility(View.GONE);
                            ((DataObjectHolder) holder).chatIcon.setVisibility(View.GONE);
                        }
                    } else if (contactsInfo.getNetworkStatus().equals("0")) {
                        ((DataObjectHolder) holder).connectTxtVw.setText(context.getString(R.string.str_connect));
                        ((DataObjectHolder) holder).connectTxtVw.setTextColor(context.getResources().getColor(R.color.app_green));
                        ((DataObjectHolder) holder).connectTxtVw.setBackgroundResource(R.drawable.border_text);
                        ((DataObjectHolder) holder).connectTxtVw.setVisibility(View.VISIBLE);
                    }
                }
                ((DataObjectHolder) holder).followBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!AppUtil.isConnectingToInternet(context)) {
                            Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (context instanceof ViewContactsActivity) {
                            ((ViewContactsActivity) context).showProgress();
                        }
                        JSONObject requestObj = new JSONObject();
                        try {
                            requestObj.put(RestUtils.TAG_USER_ID, docid);
                            requestObj.put(RestUtils.TAG_OTHER_USER_ID, contactsInfo.getDoc_id());
                            requestObj.put(RestUtils.TAG_IS_FOLLOW, false);
                            new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.USER_FOLLOW_REST_API, requestObj.toString(), "USER_FOLLOW", new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    if (context instanceof ViewContactsActivity) {
                                        ((ViewContactsActivity) context).hideProgress();
                                    }
                                    if (successResponse != null && !successResponse.isEmpty()) {
                                        JSONObject responseObj = null;
                                        try {
                                            responseObj = new JSONObject(successResponse);
                                            if (responseObj != null && responseObj.has(RestUtils.TAG_STATUS)) {
                                                if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                                    updateUIchangeUnfollow(position, true);

                                                } else if (responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                                                    if (responseObj.optInt(RestUtils.TAG_ERROR_CODE) == 1039) {
                                                        updateUIchangeUnfollow(position, false);
                                                    }
                                                    Toast.makeText(context, responseObj.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }

                                private void updateUIchangeUnfollow(int position, boolean onSuccess) {
                                    DashboardUpdatesFragment.dashboardRefreshOnSubscription = true;
                                    searchResults.remove(position);
                                    notifyDataSetChanged();
                                    if (onSuccess) {
                                        realmManager.updateFollowingCount(false);
                                    }
                                }

                                @Override
                                public void onErrorResponse(String errorResponse) {
                                    if (context instanceof ViewContactsActivity) {
                                        ((ViewContactsActivity) context).hideProgress();
                                    }
                                }
                            }).sendSinglePartRequest();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                ((DataObjectHolder) holder).connectTxtVw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (docid == contactsInfo.getDoc_id()) {
                            return;
                        } else if (contactsInfo.getNetworkStatus().equals("0")) {
                            UserSearchResultsActivity.selectedPosition = position;
                            GlobalSearchActivity.selectedPosition = position;
                            Intent intent = new Intent(context, InviteRequestActivity.class);
                            intent.putExtra("searchContactsInfo", contactsInfo);
                            intent.putExtra("search", "search");
                            intent.putExtra(RestUtils.NAVIGATATE_FROM, NetworkSearchActivity.TAG);

                            /*Refactoring the deprecated startActivityForResults*/
                            screenNavigate.onScreenNavigate(intent);

                          /*  if (context instanceof NetworkSearchActivity) {
                                launcherInviteOrVisitProfileResults.launch(intent);
                            } else if (context instanceof ViewContactsActivity) {
                                launcherInviteOrVisitProfileResults.launch(intent);
                            } else if (context instanceof GlobalSearchActivity) {
                                launcherInviteOrVisitProfileResults.launch(intent);
                            } else if (context instanceof UserSearchResultsActivity) {
                                launcherInviteOrVisitProfileResults.launch(intent);
                            }*/
                        }
                    }
                });

                ((DataObjectHolder) holder).profileImageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contactsInfo.isCardPopupNotNeeded()) {
                            Intent visit_intent = new Intent(context, VisitOtherProfile.class);
                            visit_intent.putExtra("searchinfo", contactsInfo);
                            visit_intent.putExtra("search_query", "");
                            if (context instanceof ViewContactsActivity) {
                                /*Refactoring the deprecated startActivityForResults*/
                                screenNavigate.onScreenNavigate(visit_intent);
                            }
                        } else {
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
                            tv_name.setText(contactsInfo.getUserSalutation() + " " + contactsInfo.getFull_name());
                            //tv_specialty.setText(contactsInfo.getSpeciality());
                            tv_specialty.setText(contactsInfo.getSpecialist());
                            if (contactsInfo.getSubSpeciality() != null && !contactsInfo.getSubSpeciality().isEmpty()) {
                                tv_sub_specialty.setVisibility(View.VISIBLE);
                                tv_sub_specialty.setText(contactsInfo.getSubSpeciality());
                            }
                            tv_workplace.setText(contactsInfo.getWorkplace());
                            tv_location.setText(contactsInfo.getLocation());

                            if (docid == contactsInfo.getDoc_id()) {
                                if (!AppUtil.checkWriteExternalPermission(context)) {
                                    if (basicInfo.getPic_url() != null && !basicInfo.getPic_url().isEmpty()) {
                                    /*Picasso.with(context)
                                            .load(basicInfo.getPic_url().trim())
                                            .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                                            .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                            .into(ig_card);*/
                                        loadImagesUsingLib(context, basicInfo.getPic_url().trim(), ig_card, R.drawable.default_profilepic);
                                    }
                                } else if (basicInfo != null && basicInfo.getProfile_pic_path() != null && !basicInfo.getProfile_pic_path().equals("")) {
                                    if (basicInfo.getPic_url() != null && !basicInfo.getPic_url().isEmpty()) {
                                    /*Picasso.with(context)
                                            .load(basicInfo.getPic_url().trim())
                                            .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                                            .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                            .into(ig_card);*/
                                        loadImagesUsingLib(context, basicInfo.getPic_url().trim(), ig_card, R.drawable.default_profilepic);
                                    } else {
                                        File imgFile = new File(basicInfo.getProfile_pic_path());
                                        if (imgFile.exists()) {
                                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                            ig_card.setImageBitmap(myBitmap);
                                        } else {
                                            ig_card.setImageResource(R.drawable.default_profilepic);
                                        }
                                    }
                                }
                            } else if (contactsInfo.getProfile_pic_small_url() != null && !contactsInfo.getProfile_pic_small_url().isEmpty()) {
                            /*Picasso.with(context)
                                    .load(contactsInfo.getProfile_pic_small_url().trim())
                                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(ig_card);*/
                                loadImagesUsingLib(context, contactsInfo.getProfile_pic_small_url().trim(), ig_card, R.drawable.default_profilepic);
                            } else {
                                ig_card.setImageResource(R.drawable.default_profilepic);

                            }
                            if (!TextUtils.isEmpty(contactsInfo.getCnt_email())) {
                                String yourString = contactsInfo.getCnt_num();

                                tv_email.setText(yourString);
                            } else {
                                tv_email.setVisibility(View.GONE);
                            }

                            tv_email.setText("Not shared");
                            tv_contactno.setText("Not shared");


                            if (contactsInfo.getNetworkStatus().equals("0")) {
                                btn_invite.setVisibility(View.VISIBLE);
                                btn_msg.setVisibility(View.GONE);
                            } else if (contactsInfo.getNetworkStatus().equals("3")) {
                                btn_invite.setVisibility(View.GONE);
                                btn_msg.setVisibility(View.GONE);
                                if (contactsInfo.getEmail_vis() != null && contactsInfo.getEmail_vis().equalsIgnoreCase("SHOW_ALL")) {
                                    tv_email.setText(contactsInfo.getCnt_email());
                                } else {
                                    tv_email.setText("Not shared");
                                }

                                if (contactsInfo.getPhno_vis() != null && contactsInfo.getPhno_vis().equalsIgnoreCase("SHOW_ALL")) {
                                    if (!TextUtils.isEmpty(contactsInfo.getCnt_num())) {
                                        tv_contactno.setText(contactsInfo.getCnt_num());
                                    }
                                } else {
                                    tv_contactno.setText("Not Shared");
                                }

//                            tv_email.setText(contactsInfo.getCnt_email());
//
//                            if (!TextUtils.isEmpty(contactsInfo.getCnt_num())) {
//                                tv_contactno.setText(contactsInfo.getCnt_num());
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
                                    if (docid == contactsInfo.getDoc_id()) {
                                        return;
                                    } else if (contactsInfo.getNetworkStatus().equals("0")) {
                                        Intent intent = new Intent(context, InviteRequestActivity.class);
                                        intent.putExtra("searchContactsInfo", contactsInfo);
                                        intent.putExtra("search", "search");
                                        intent.putExtra(RestUtils.NAVIGATATE_FROM, NetworkSearchActivity.TAG);

                                        /*Refactoring the deprecated startActivityForResults*/
                                        screenNavigate.onScreenNavigate(intent);

                                 /*   if (context instanceof NetworkSearchActivity) {
                                        launcherInviteOrVisitProfileResults.launch(intent);
                                    } else if (context instanceof ViewContactsActivity) {
                                        launcherInviteOrVisitProfileResults.launch(intent);
                                    } else if (context instanceof GlobalSearchActivity) {
                                        launcherInviteOrVisitProfileResults.launch(intent);
                                    } else if (context instanceof UserSearchResultsActivity) {
                                        launcherInviteOrVisitProfileResults.launch(intent);
                                    }*/

                                    dialog.dismiss();

                                    }
                                }
                            });
                            btn_msg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    new ShowCard(context, "", contactsInfo).goToChatWindow();
                                }
                            });

                            tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                                                                            @Override
                                                                            public boolean onTouch(View v, MotionEvent event) {
                                                                                if (AppUtil.isConnectingToInternet(context)) {
                                                                                    if (docid == contactsInfo.getDoc_id()) {
                                                                                        Intent intent = new Intent(context, ProfileViewActivity.class);
                                                                                        context.startActivity(intent);
                                                                                        dialog.dismiss();
                                                                                        // finish();
                                                                                    } else {
                                                                                        Intent visit_intent = new Intent(context, VisitOtherProfile.class);
                                                                                        visit_intent.putExtra("searchinfo", contactsInfo);
                                                                                        visit_intent.putExtra("search_query", "");

                                                                                        /*Refactoring the deprecated startActivityForResults*/
                                                                                        screenNavigate.onScreenNavigate(visit_intent);

                                                                                       /* if (context instanceof NetworkSearchActivity) {
                                                                                            launcherInviteOrVisitProfileResults.launch(visit_intent);
                                                                                        } else if (context instanceof ViewContactsActivity) {
                                                                                            launcherInviteOrVisitProfileResults.launch(visit_intent);
                                                                                        } else if (context instanceof GlobalSearchActivity) {
                                                                                            launcherInviteOrVisitProfileResults.launch(visit_intent);
                                                                                        } else if (context instanceof UserSearchResultsActivity) {
                                                                                            launcherInviteOrVisitProfileResults.launch(visit_intent);
                                                                                        }*/

                                                                                        // context.startActivity(visit_intent);
                                                                                        dialog.dismiss();
                                                                                    }
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
                    }
                });

            } else {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);

            }
        }
    }

    private void loadImagesUsingLib(Context context, String imageUrl, ImageView profileImageview, int default_profilepic) {
        AppUtil.loadCircularImageUsingLib(context, imageUrl, profileImageview, default_profilepic);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return searchResults.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * @param contactsInfo
     * @return A Member object
     * @Desc As both search results object and Member object is different, this method manually  converts the searchInfo
     * object into Member object to be used in required places.
     */
    private Member convertContactsToMember(ContactsInfo contactsInfo) {
        Log.i(TAG, "convertContactsToMember(ContactsInfo contactsInfo)");
        Member member = new Member();
        member.setDoctorId(contactsInfo.getDoc_id());
        member.setQbUserId(contactsInfo.getQb_userid());
        member.setNetworkStatus(Integer.parseInt(contactsInfo.getNetworkStatus()));
        member.setContactEmail(contactsInfo.getCnt_email());
        member.setContactNumber(contactsInfo.getPhno());
        member.setSpeciality(contactsInfo.getSpeciality());
        member.setSubspeciality(contactsInfo.getSubSpeciality());
        member.setFullName(contactsInfo.getName());
        member.setLocation(contactsInfo.getLocation());
        member.setWorkplace(contactsInfo.getWorkplace());
        member.setDesignation(contactsInfo.getDesignation());
        member.setDegrees(contactsInfo.getDegree());
        member.setProfilePicName(contactsInfo.getPic_name());
        member.setCommunityDesignation(contactsInfo.getCommunity_designation());
        return member;
    }
}
