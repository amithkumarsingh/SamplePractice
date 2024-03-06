package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.CreatePostActivity;
import com.vam.whitecoats.ui.activities.TimeLine;
import com.vam.whitecoats.ui.fragments.DashboardUpdatesFragment;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContentChannelsAdapter extends RecyclerView.Adapter {
    private  int docId;
    private  boolean hasFullViewSeen=false;
    private  ArrayList<JSONObject> channelsList;
    private  Context context;
   // private JSONObject channelsData;
    public static final int POST_ACTION = 1212;
    private  int VIEW_ITEM = 1;
    private  int VIEW_PROG = 0;

    public ContentChannelsAdapter(Context mContext, ArrayList<JSONObject> mChannelsList, boolean mHasFullViewSeen, int docId) {
        this.context=mContext;
        this.channelsList=mChannelsList;
        this.hasFullViewSeen=mHasFullViewSeen;
        this.docId=docId;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subscription_cat_item, parent, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                final DataObjectHolder viewHolder = (DataObjectHolder) holder;
                JSONObject channelsData = channelsList.get(position);
                if (channelsList.get(position) != null) {
                    if (channelsList.get(position) != null && channelsList.get(position).optString("feed_provider_type").equalsIgnoreCase(RestUtils.CONTENT)) {
                        /*
                         * View.GONE- Makes the view invisible as well as free-up the space in UI
                         * View.INVISIBLE - Makes the view invisible but the view still occupy the UI space.
                         *
                         * <p> Based on different UI requirements we use one of the property, here also we need to use
                         * different properties for different UI elements as per our requirement. </p>
                         *
                         */
                        viewHolder.createPostLayout.setVisibility(View.GONE);
                        viewHolder.communityMembers.setVisibility(View.GONE);
                        if (!channelsList.get(position).optBoolean("is_mandatory")) {
                            viewHolder.follow_unFollow_btn.setVisibility(View.VISIBLE);
                        } else {
                            viewHolder.follow_unFollow_btn.setVisibility(View.GONE);
                        }
                        if (!channelsList.get(position).optBoolean("is_subscribed")) {
                            viewHolder.follow_unFollow_btn.setText("FOLLOW");
                            viewHolder.follow_unFollow_btn.setBackgroundResource(R.drawable.accept_button);
                            viewHolder.follow_unFollow_btn.setTextColor(context.getResources().getColor(R.color.white));
                        } else {
                            viewHolder.follow_unFollow_btn.setText("UNFOLLOW");
                            viewHolder.follow_unFollow_btn.setBackgroundResource(R.drawable.button_grey);
                            viewHolder.follow_unFollow_btn.setTextColor(context.getResources().getColor(R.color.black_radio));

                        }

                    }else{
                        viewHolder.follow_unFollow_btn.setVisibility(View.GONE);
                        if (channelsList.get(position).optBoolean("is_admin")) {
                            viewHolder.createPostLayout.setVisibility(View.VISIBLE);
                        } else {
                            viewHolder.createPostLayout.setVisibility(View.GONE);
                        }
                        viewHolder.communityMembers.setVisibility(View.VISIBLE);
                    }
                    /*
                     * (or) If user is not Admin in this particular #Community then hide
                     *  1. make a post button
                     */

                    // set channel logo through Glide, if logo not found set error place holder.
                    AppUtil.loadImageUsingGlide(context, channelsList.get(position).optString("channel_logo"), viewHolder.communityLogo, R.drawable.default_communitypic);
                    // Set community name
                    viewHolder.communityName.setText(channelsList.get(position).optString("feed_provider_name"));
                    if (channelsList.get(position).optInt("members_count") == 1) {
                        viewHolder.communityMembers.setText(AppUtil.suffixNumber(channelsList.get(position).optInt("members_count")) + " member");
                    } else {
                        viewHolder.communityMembers.setText(AppUtil.suffixNumber(channelsList.get(position).optInt("members_count")) + " members");
                    }
                    if (channelsList.get(position).optBoolean("is_subscribed")) {
                        if (channelsList.get(position).optInt("unread_feeds") > 0) {
                            viewHolder.postCount.setVisibility(View.VISIBLE);
                            if (channelsList.get(position).optInt("unread_feeds") > 99) {
                                viewHolder.postCount.setText("99+");
                            } else {
                                viewHolder.postCount.setText(channelsList.get(position).optInt("unread_feeds") + "");
                            }
                        } else {
                            viewHolder.postCount.setVisibility(View.GONE);
                        }
                    } else {
                        viewHolder.postCount.setVisibility(View.GONE);
                    }


                    /*
                     * If Create post button clicked, navigate into MakeAPost screen
                     * by passing required extra params.
                     */
                    viewHolder.createPost.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                                    /*
                                     *Create JSON object to pass to CreatePost
                                     */
                                    JSONObject channelObj = new JSONObject();
                                    channelObj.put(RestUtils.CHANNEL_ID, channelsList.get(position).optInt("channel_id"));
                                    channelObj.put(RestUtils.FEED_PROVIDER_NAME, channelsList.get(position).optString("feed_provider_name"));
                                    channelObj.put(RestUtils.TAG_FEED_PROVIDER_TYPE, channelsList.get(position).optString("feed_provider_type"));
                                    //Start Activity
                                    Intent in = new Intent(context, CreatePostActivity.class);
                                    in.putExtra(RestUtils.NAVIGATATION, "Channels");
                                    in.putExtra(RestUtils.KEY_SELECTED_CHANNEL, channelObj.toString());
                                    context.startActivity(in);
                                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                                    AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_not_uploaded));
                                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                                    AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_uploaded_but_not_verified));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Make the navigation value true and in #OnResume use it to call the service
                            boolean navigateToTimeLine = true;
                            if (channelsList.get(position) != null && channelsList.get(position).optString("feed_provider_type").equalsIgnoreCase(RestUtils.CONTENT)) {
                                if (!channelsList.get(position).optBoolean("is_subscribed")) {
                                    navigateToTimeLine = false;
                                }
                            }
                            if (navigateToTimeLine) {
                                hasFullViewSeen = true;
                                Intent intent = new Intent(context, TimeLine.class);
                                intent.putExtra(RestUtils.FEED_PROVIDER_NAME, channelsList.get(position).optString("feed_provider_name"));
                                intent.putExtra(RestUtils.CHANNEL_ID, channelsList.get(position).optInt("channel_id"));
                                intent.putExtra(RestUtils.TAG_IS_ADMIN, channelsList.get(position).optBoolean("is_admin"));
                                intent.putExtra(RestUtils.TAG_FEED_PROVIDER_TYPE, channelsList.get(position).optString("feed_provider_type"));
                                intent.putExtra(RestUtils.TAG_FEED_PROVIDER_SUBTYPE, channelsList.get(position).optString("feed_provider_subtype"));
                                context.startActivity(intent);
                            }
                        }
                    });

                    viewHolder.follow_unFollow_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (AppUtil.isConnectingToInternet(context)) {
                                //sendSubscribeUnsubscribeRequest(!channel.getIsSubscribed(),channel.getChannelId());
                                boolean isSubscribedValue = !channelsList.get(position).optBoolean("is_subscribed");
                                JSONObject subscribeJsonRequest = new JSONObject();
                                try {
                                    subscribeJsonRequest.put(RestUtils.TAG_USER_ID, docId);
                                    subscribeJsonRequest.put(RestUtils.CHANNEL_ID, channelsList.get(position).optInt("channel_id"));
                                    subscribeJsonRequest.put(RestUtils.TAG_IS_SUBSCRIBED, isSubscribedValue);
                                    // showProgress();

                                    new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.SUBSCRIPTION_SERVICE, subscribeJsonRequest.toString(), "SUBSCRIPTION_SERVICE", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            // hideProgress();
                                            try {
                                                channelsList.get(position).put("is_subscribed", isSubscribedValue);
                                                // channelsData.put("is_subscribed",isSubscribedValue);
                                                if (channelsList.get(position).optInt("unread_feeds") > 0) {
                                                    if (isSubscribedValue) {
                                                        AppConstants.unreadChannelsList.add(channelsList.get(position).optInt("channel_id"));
                                                    } else {
                                                        AppConstants.unreadChannelsList.remove(Integer.valueOf(channelsList.get(position).optInt("channel_id")));
                                                    }
                                                    Intent intent = new Intent("update_channels_count");
                                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                                }
                                                notifyDataSetChanged();
                                                DashboardUpdatesFragment.dashboardRefreshOnSubscription = true;
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onErrorResponse(String errorResponse) {
                                            //    hideProgress();
                                            if (errorResponse != null) {
                                                //revert back to pre stage
                                                try {
                                                    if (!errorResponse.isEmpty()) {
                                                        JSONObject jsonObject = new JSONObject(errorResponse);
                                                        String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                        if (errorMessage != null && !errorMessage.isEmpty()) {
                                                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                        }
                                    }).sendSinglePartRequest();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }
            }else if (holder instanceof ProgressViewHolder) {
                ((ProgressViewHolder) holder).progressBar.setPadding(0, 40, 0, 40);
            }
        }


    }

    @Override
    public int getItemCount() {
        if(channelsList!=null){
            return channelsList.size();
        }else {
            return 0;
        }
    }
    public class DataObjectHolder extends RecyclerView.ViewHolder  {

        private final View rootView;
        private final Button follow_unFollow_btn;
        LinearLayout createPostLayout;
        private final ImageView communityLogo;
        private final TextView communityName;
        private final TextView communityMembers;
        private final TextView postCount;
        private final TextView createPost;

        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.rootView);
            createPostLayout = (LinearLayout) itemView.findViewById(R.id.createPostLayout);
            communityLogo = (ImageView) itemView.findViewById(R.id.communityLogoImgVw);
            communityName = (TextView) itemView.findViewById(R.id.communityNameTxtVw);
            communityMembers = (TextView) itemView.findViewById(R.id.communityMembersTxtVw);
            postCount = (TextView) itemView.findViewById(R.id.postCountTxtVw);
            createPost = (TextView) itemView.findViewById(R.id.createPostButton);
            follow_unFollow_btn = (Button) itemView.findViewById(R.id.follow_unFollow_btn);
        }
    }

    public  class ProgressViewHolder extends RecyclerView.ViewHolder{

        private final AVLoadingIndicatorView progressBar;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (AVLoadingIndicatorView) itemView.findViewById(R.id.avIndicator);

        }
    }
    @Override
    public int getItemViewType(int position) {
        return channelsList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }


}
