package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.ui.activities.CommentsActivity;
import com.vam.whitecoats.ui.activities.ImageViewer;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.activities.WebViewActivity;
import com.vam.whitecoats.ui.activities.YoutubeVideoViewActivity;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.CommentsEditInterface;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.BetterLinkMovementMethod;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.RestUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tejaswini on 18-11-2016.
 */

public class CommentsAdapter extends RecyclerView.Adapter {
    private final ArrayList<JSONObject> commentsArray;
    Context context;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loading;
    private int visibleItemCount, totalItemCount, pastVisiblesItems, lastVisibleItem;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    //final Dialog dialog = new Dialog(context);
    private android.app.AlertDialog.Builder builder;
    int loginDoc_id, channel_id, feedID = 0;
    private String navigation;
    private int selectedPosition = -1;
    private CommentsEditInterface callBackListner;
    private boolean isEditable, isDeletable;
    int currentDoc_id = 0;

    public CommentsAdapter(Context mContext, ArrayList<JSONObject> commentJsonArray, RecyclerView recyclerView, int doctorId, String mNavigation, int channelID, int feed_id, CommentsEditInterface listener) {
        context = mContext;
        commentsArray = commentJsonArray;
        loginDoc_id = doctorId;
        channel_id = channelID;
        feedID = feed_id;
        navigation = mNavigation;
        callBackListner = listener;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            if (dy > 0 && navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) //check for scroll down
                            {
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
                                    }
                                }
                            } else if (dy < 0) {
                                visibleItemCount = linearLayoutManager.getChildCount();
                                totalItemCount = linearLayoutManager.getItemCount();
                                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                                if (!loading) {
                                    if (pastVisiblesItems != RecyclerView.NO_POSITION && pastVisiblesItems == 0 && CommentsActivity.getTotalCommnetsCount() != totalItemCount) {
                                        if (onLoadMoreListener != null && AppUtil.isConnectingToInternet(context)) {
                                            onLoadMoreListener.onLoadMore();
                                            loading = true;
                                        } else {
                                            loading = false;
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private RoundedImageView doctor_profile_pic;
        private RelativeLayout tileLayout, context_menu;
        private TextView doctor_name, comment_time, Comment_text;
        private ImageView image_attachment;


        public DataObjectHolder(View itemView) {
            super(itemView);
            doctor_profile_pic = (RoundedImageView) itemView.findViewById(R.id.doctor_profile_pic);
            doctor_name = (TextView) itemView.findViewById(R.id.doctor_name);
            comment_time = (TextView) itemView.findViewById(R.id.comment_time);
            Comment_text = (TextView) itemView.findViewById(R.id.comment_text);
            tileLayout = (RelativeLayout) itemView.findViewById(R.id.tileLayout);
            image_attachment = itemView.findViewById(R.id.attach_image);
            context_menu = (RelativeLayout) itemView.findViewById(R.id.context_menu);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comments_row_layout, parent, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }
        //DataObjectHolder dataObjectHolder =
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                ((DataObjectHolder) holder).image_attachment.setVisibility(View.GONE);
                if (navigation != null && navigation.equalsIgnoreCase(RestUtils.TAG_FROM_LIKES_COUNT)) {
                    ((DataObjectHolder) holder).comment_time.setVisibility(View.GONE);
                    ((DataObjectHolder) holder).context_menu.setVisibility(View.GONE);
                    ((DataObjectHolder) holder).Comment_text.setText(commentsArray.get(position).optString("speciality", "") + ", " + commentsArray.get(position).optString("location", ""));
                    ((DataObjectHolder) holder).Comment_text.setMaxLines(2);
                    ((DataObjectHolder) holder).Comment_text.setEllipsize(TextUtils.TruncateAt.END);

                } else {
                    ((DataObjectHolder) holder).comment_time.setVisibility(View.VISIBLE);
                    ((DataObjectHolder) holder).context_menu.setVisibility(View.GONE);
                    ((DataObjectHolder) holder).Comment_text.setText(StringEscapeUtils.unescapeJava(commentsArray.get(position).optString("comment", "")));
                    //((DataObjectHolder) holder).Comment_text.setMovementMethod(LinkMovementMethod.getInstance());
                    ((DataObjectHolder) holder).Comment_text.setMovementMethod(BetterLinkMovementMethod.getInstance());
                    isEditable = commentsArray.get(position).optBoolean("is_editable");
                    isDeletable = commentsArray.get(position).optBoolean("is_deletable");
                    if (isEditable) {
                        ((DataObjectHolder) holder).context_menu.setVisibility(View.VISIBLE);
                    } else {
                        ((DataObjectHolder) holder).context_menu.setVisibility(View.GONE);
                    }

                    //Linkify.addLinks( ((DataObjectHolder) holder).Comment_text, Linkify.ALL);
                    BetterLinkMovementMethod
                            .linkify(Linkify.ALL, ((DataObjectHolder) holder).Comment_text).setOnLinkClickListener(new BetterLinkMovementMethod.OnLinkClickListener() {
                                @Override
                                public boolean onClick(TextView textView, String url) {
                                    if (AppUtil.isFbUrl(url.toLowerCase())) {
                                        AppUtil.openFbLink(context, url.toLowerCase());
                                        return true;
                                    } else if (AppUtil.isYoutubeUrl(url)) {
                                        Intent in = new Intent(context, YoutubeVideoViewActivity.class);
                                        in.putExtra("video_id", AppUtil.getVideoIdFromYoutubeUrl(url));
                                        in.putExtra("login_user_id", loginDoc_id);
                                        context.startActivity(in);
                                        return true;
                                    } else if (AppUtil.isWhitecoatsUrl(url)) {
                                        return false;
                                    } else if (URLUtil.isValidUrl(url)) {
                                        Intent intent = new Intent(context, WebViewActivity.class);
                                        intent.putExtra("EXTERNAL_LINK", url);
                                        context.startActivity(intent);
                                        return true;
                                    }
                                    return false;
                                }
                            });
                    //((DataObjectHolder) holder).comment_time.setText(DateUtils.longToMessageListHeaderDate(Long.parseLong(commentsArray.get(position).optString("timestamp", ""))));
                    ((DataObjectHolder) holder).comment_time.setText(DateUtils.longToMessageListHeaderTimeandDate(commentsArray.get(position).optLong("timestamp")));
                    JSONArray attachmentDetailsArray = commentsArray.get(position).optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                    if (attachmentDetailsArray != null && attachmentDetailsArray.length() > 0) {
                        String attachment_small_url = null;
                        try {
                            attachment_small_url = attachmentDetailsArray.getJSONObject(0).optString(RestUtils.ATTACH_ORIGINAL_URL);
                            if (attachment_small_url != null && !attachment_small_url.isEmpty()) {
                                ((DataObjectHolder) holder).image_attachment.setVisibility(View.VISIBLE);
                                AppUtil.loadImageUsingGlide(context, attachment_small_url, ((DataObjectHolder) holder).image_attachment, R.drawable.default_profilepic);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (commentsArray.get(position).has(RestUtils.TAG_DOC_ID)) {
                    currentDoc_id = commentsArray.get(position).optInt(RestUtils.TAG_DOC_ID);
                } else {
                    currentDoc_id = commentsArray.get(position).optInt(RestUtils.TAG_USER_ID);
                }
                if (loginDoc_id == currentDoc_id) {
                    ((DataObjectHolder) holder).doctor_name.setText("You");
                } else {
                    ((DataObjectHolder) holder).doctor_name.setText(commentsArray.get(position).optString(RestUtils.TAG_USER_SALUTAION) + " " + commentsArray.get(position).optString(RestUtils.TAG_USER_FULL_NAME));
                }

                ((DataObjectHolder) holder).doctor_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int doc_id = commentsArray.get(position).optInt(RestUtils.TAG_DOC_ID);
                        if (loginDoc_id == doc_id) {
                            return;
                        }

                        if (commentsArray.get(position).optInt(RestUtils.TAG_IS_REMOVE) == 1) {
                            callBackListner.removeReportInterface("REMOVE_INTERFACE", commentsArray.get(position).optString(RestUtils.TAG_USER_SALUTAION) + " " + commentsArray.get(position).optString(RestUtils.TAG_USER_FULL_NAME));
                        } else {
                            if (AppUtil.isConnectingToInternet(context)) {
                                Intent intent = new Intent(context, VisitOtherProfile.class);
                                intent.putExtra(RestUtils.TAG_DOC_ID, doc_id);
                                intent.putExtra("searchinfo", convertToContact(commentsArray.get(position)));
                                context.startActivity(intent);
                            }
                        }
                    }
                });


                ((DataObjectHolder) holder).image_attachment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String image_url = "";
                        ArrayList<String> URLList = new ArrayList<String>();
                        URLList.clear();
                        JSONArray attachmentDetailsArray = commentsArray.get(position).optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                        String attachment_small_url = null;
                        try {
                            attachment_small_url = attachmentDetailsArray.getJSONObject(0).optString(RestUtils.ATTACH_ORIGINAL_URL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (attachment_small_url != null && attachment_small_url.length() > 0) {
                            Intent intent = new Intent(context, ImageViewer.class);
                            if (attachment_small_url != null && !attachment_small_url.isEmpty()) {
                                intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                                intent.putExtra(RestUtils.TAG_FILE_PATH, attachment_small_url);
                                URLList.add(attachment_small_url);
                            } else {
                                intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                                intent.putExtra(RestUtils.TAG_FILE_PATH, "");
                            }
                            intent.putStringArrayListExtra("URLList", URLList);
                            intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                            context.startActivity(intent);
                        }

                    }
                });


                ((DataObjectHolder) holder).doctor_profile_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int doc_id = commentsArray.get(position).optInt(RestUtils.TAG_DOC_ID);
                        if (loginDoc_id == doc_id) {
                            return;
                        }
                        if (commentsArray.get(position).optInt(RestUtils.TAG_IS_REMOVE) == 1) {
                            callBackListner.removeReportInterface("REMOVE_INTERFACE", commentsArray.get(position).optString(RestUtils.TAG_USER_SALUTAION) + " " + commentsArray.get(position).optString(RestUtils.TAG_USER_FULL_NAME));
                        } else {
                            if (AppUtil.isConnectingToInternet(context)) {
                                Intent intent = new Intent(context, VisitOtherProfile.class);
                                intent.putExtra(RestUtils.TAG_DOC_ID, doc_id);
                                intent.putExtra("searchinfo", convertToContact(commentsArray.get(position)));
                                context.startActivity(intent);
                            }
                        }

                    }
                });

                if (commentsArray.get(position).optString("url") != null && !commentsArray.get(position).optString("url").isEmpty()) {
                    /*Picasso.with(context)
                            .load(commentsArray.get(position).optString("url").trim())
                            .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                            .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                            .into(((DataObjectHolder) holder).doctor_profile_pic);*/
                    AppUtil.loadCircularImageUsingLib(context, commentsArray.get(position).optString("url").trim(), ((DataObjectHolder) holder).doctor_profile_pic, R.drawable.default_profilepic);
                } else {
                    ((DataObjectHolder) holder).doctor_profile_pic.setImageResource(R.drawable.default_profilepic);
                }

                ((DataObjectHolder) holder).context_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppUtil.isConnectingToInternet(context)) {
                            //creating a popup menu
                            PopupMenu popup = new PopupMenu(context, ((DataObjectHolder) holder).context_menu);
                            //inflating menu from xml resource
                            popup.inflate(R.menu.comments_menu);
                            //adding click listener
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    if (AppUtil.isConnectingToInternet(context)) {
                                        selectedPosition = position;
                                        String commentedText = ((DataObjectHolder) holder).Comment_text.getText().toString();
                                        int social_interaction_id = commentsArray.get(position).optInt(RestUtils.TAG_SOCIAL_INTERACTION_ID);
                                        JSONArray attachmentDetailsArray = commentsArray.get(position).optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                                        callBackListner.commentsEditInterface(commentedText, social_interaction_id, attachmentDetailsArray != null ? attachmentDetailsArray : new JSONArray(), item.getItemId());
                                    } else {

                                    }
                                    return false;
                                }
                            });
                            //displaying the popup
                            popup.show();

                        }
                    }
                });
            } else {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentsArray.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return commentsArray.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void setLoaded() {
        loading = false;
    }

    private ContactsInfo convertToContact(JSONObject jsonObject) {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setDoc_id(jsonObject.optInt(RestUtils.TAG_DOC_ID, -1));
        contactsInfo.setName((jsonObject.has(RestUtils.TAG_USER_FULL_NAME)) ? jsonObject.optString(RestUtils.TAG_USER_FULL_NAME) : jsonObject.optString(RestUtils.TAG_NAME, ""));
        contactsInfo.setSpeciality(jsonObject.optString(RestUtils.TAG_SPLTY, ""));
        contactsInfo.setSubSpeciality(jsonObject.optString(RestUtils.TAG_SUB_SPLTY, ""));
        contactsInfo.setDegree(jsonObject.optString(RestUtils.TAG_DEGREE, ""));
        contactsInfo.setWorkplace(jsonObject.optString(RestUtils.TAG_WORKPLACE, ""));
        contactsInfo.setLocation(jsonObject.optString(RestUtils.TAG_LOCATION, ""));
        contactsInfo.setEmail(jsonObject.optString(RestUtils.TAG_EMAIL, ""));
        contactsInfo.setPhno(jsonObject.optString(RestUtils.TAG_CONTACT_NUMBER, ""));
        contactsInfo.setQb_userid(jsonObject.optInt(RestUtils.TAG_QB_USER_ID, -1));
        contactsInfo.setPic_name(jsonObject.optString(RestUtils.TAG_PROFILE_PIC_NAME, ""));
        contactsInfo.setPic_url(jsonObject.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
        contactsInfo.setNetworkStatus(jsonObject.optString(RestUtils.TAG_NETWORK_STATUS, ""));
        contactsInfo.setUserSalutation(jsonObject.optString(RestUtils.TAG_USER_SALUTAION));
        contactsInfo.setUserTypeId(jsonObject.optInt(RestUtils.TAG_USER_TYPE_ID));
        return contactsInfo;
    }

    public int getSelectedPostion() {
        return selectedPosition;

    }


    public void setFeedId(int mFeedID) {
        feedID = mFeedID;
    }
}


