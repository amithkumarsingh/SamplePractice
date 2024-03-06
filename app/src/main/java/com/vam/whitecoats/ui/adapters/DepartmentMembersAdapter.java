package com.vam.whitecoats.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ImageDownloaderTask;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.Member;
import com.vam.whitecoats.ui.activities.DepartmentMembersActivity;
import com.vam.whitecoats.ui.activities.InactiveMembersCard;
import com.vam.whitecoats.ui.activities.InviteRequestActivity;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowCard;
import com.vam.whitecoats.utils.ShowProfileCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyasarathim on 20-06-2016.
 */
public class DepartmentMembersAdapter extends BaseAdapter implements View.OnClickListener {

    public static final String TAG = DepartmentMembersAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<Member> membersList;
    ViewHolder holder;
    int doctorId;
    String deptTitle;
    private Member member;

    public ProgressDialogFragement progress;
    public AlertDialog.Builder builder;
    private int currentPosition;

    public DepartmentMembersAdapter(Context context, int doctorId, List<Member> membersList, String deptTitle) {
        this.mContext = context;
        this.doctorId = doctorId;
        this.deptTitle = deptTitle;
        this.membersList = (ArrayList<Member>) membersList;
    }

    @Override
    public int getCount() {
        return membersList.size();
    }

    @Override
    public Object getItem(int position) {
        return membersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        DepartmentMembersActivity.selectedPosition = position;
        switch (v.getId()) {
            case R.id.imageurl:
                if (doctorId == membersList.get(position).getDoctorId() || membersList.get(position).getNetworkStatus() == 4) {
                    return;
                }
                ContactsInfo contactsInfo = new ContactsInfo();
                contactsInfo.setDoc_id(membersList.get(position).getDoctorId());
                contactsInfo.setName(membersList.get(position).getFullName());
                contactsInfo.setWorkplace(membersList.get(position).getWorkplace());
                contactsInfo.setSpeciality(membersList.get(position).getSpeciality());
                contactsInfo.setSubSpeciality(membersList.get(position).getSubspeciality());
                contactsInfo.setDegree(membersList.get(position).getDegrees());
                contactsInfo.setEmail(membersList.get(position).getContactEmail());
                contactsInfo.setNetworkStatus("" + membersList.get(position).getNetworkStatus());
                contactsInfo.setPic_name(membersList.get(position).getProfilePicName());
                contactsInfo.setPic_url(membersList.get(position).getProfilePicUrl());
                contactsInfo.setPic_data(membersList.get(position).getProfilePicName());
                contactsInfo.setQb_userid(membersList.get(position).getQbUserId());
                contactsInfo.setLocation(membersList.get(position).getLocation());
                contactsInfo.setPhno(membersList.get(position).getContactNumber());
                contactsInfo.setUserSalutation(membersList.get(position).getUserSalutation());
                contactsInfo.setUserTypeId(membersList.get(position).getUserTypeId());
                contactsInfo.setPhno_vis(membersList.get(position).getPhno_vis());
                contactsInfo.setEmail_vis(membersList.get(position).getEmail_vis());
                if (contactsInfo != null) {
                    ShowProfileCard cardInfo = new ShowProfileCard(mContext, contactsInfo);
                    cardInfo.popupProfileInfo();
                }
                break;
            case R.id.tile_layout:
                if (doctorId == membersList.get(position).getDoctorId()) {
                    return;
                }
                if (membersList.get(position).getNetworkStatus()== 0) {
                    Intent intent = new Intent(mContext, InviteRequestActivity.class);
                    intent.putExtra("searchContactsInfo", convertToContact(membersList.get(position)));
                    intent.putExtra("search_query", "");
                    intent.putExtra(RestUtils.NAVIGATATE_FROM, DepartmentMembersActivity.TAG);
                    mContext.startActivity(intent);
                } else if (membersList.get(position).getNetworkStatus()== 4) {
                    String desingnation = "";
                    Intent intent = new Intent(mContext, InactiveMembersCard.class);
                    intent.putExtra(mContext.getString(R.string.key_inactive_member), membersList.get(position));
                    intent.putExtra(RestUtils.DEPT_OR_DESIG, deptTitle);
                    if (membersList.get(position).getCommunityDesignation() != null && !membersList.get(position).getCommunityDesignation().isEmpty()) {
                        desingnation = membersList.get(position).getCommunityDesignation();
                    }
                    intent.putExtra(RestUtils.TAG_DESIGNATION, desingnation);
                    mContext.startActivity(intent);

                } else if (membersList.get(position).getNetworkStatus()== 3) {
                    //new ShowCard(mContext, "", convertToContact(membersList.get(position))).goToChatWindow("Department_Activity");
                }
                break;
            case R.id.connect_text:
                if (membersList.get(position).getNetworkStatus()== 1) {
                    return;
                }
                Intent intent = new Intent(mContext, InviteRequestActivity.class);
                intent.putExtra("searchContactsInfo", convertToContact(membersList.get(position)));
                intent.putExtra("search_query", "");
                intent.putExtra(RestUtils.NAVIGATATE_FROM, DepartmentMembersActivity.TAG);
                mContext.startActivity(intent);
                break;
            case R.id.chat_img:
                new ShowCard(mContext, "", convertToContact(membersList.get(position))).goToChatWindow("Department_Activity");
                break;
            default:
                break;
        }

    }

    private static class ViewHolder {
        RelativeLayout tileLayout;
        RoundedImageView profileImageview;
        private ImageView chatImageview;
        private TextView nameTextview, specialtyTextview, connectTextview;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        currentPosition = position;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_contacts_listitem, parent, false);
            holder.tileLayout = (RelativeLayout) convertView.findViewById(R.id.tile_layout);
            holder.nameTextview = (TextView) convertView.findViewById(R.id.name_txt);
            holder.connectTextview = (TextView) convertView.findViewById(R.id.connect_text);
            holder.specialtyTextview = (TextView) convertView.findViewById(R.id.speciality_txt);
            holder.chatImageview = (ImageView) convertView.findViewById(R.id.chat_img);
            holder.profileImageview = (RoundedImageView) convertView.findViewById(R.id.imageurl);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        member = membersList.get(position);
        holder.nameTextview.setText(member.getUserSalutation()+" "+member.getFullName());
        if (member.getCommunityDesignation().isEmpty() || member.getLocation().isEmpty()) {
            holder.specialtyTextview.setText(member.getCommunityDesignation() + "" + member.getLocation());
        } else {
            holder.specialtyTextview.setText(member.getCommunityDesignation() + ", " + member.getLocation());
        }
        holder.tileLayout.setTag(position);
        //holder.specialtyTextview.setText(member.getCommunityDesignation());
        /**
         * Network status 0 - No connection
         *                  1 - Pending
         *                  2 - Waiting
         *                  3 - Connected
         *                  4 - Not in WhiteCoats
         */
        if (member.getProfilePicUrl() != null && !member.getProfilePicUrl().isEmpty()) {
            /*Picasso.with(mContext)
                    .load(member.getProfilePicUrl().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.profileImageview);*/
            AppUtil.loadCircularImageUsingLib(mContext,member.getProfilePicUrl().trim(),holder.profileImageview,R.drawable.default_profilepic);

        } else if (member.getProfilePicName() != null && !member.getProfilePicName().equals("null") && !member.getProfilePicName().equals("")) {
            File myFile = AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/Doc_Profiles/" + member.getProfilePicName());
            if (myFile.exists()) {
                /*Picasso.with(mContext)
                        .load(myFile)
                        .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image, default profile image would be displayed
                        .into(holder.profileImageview);*/
                Glide.with(mContext)
                        .load(myFile)
                        .circleCrop()
                        .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_profilepic)))
                        .into(holder.profileImageview);
            } else {
                new ImageDownloaderTask(holder.profileImageview, mContext, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String s) {
                        if (s != null) {
                            if (s.equals("SocketTimeoutException") || s.equals("Exception")) {
                                Log.i(TAG, "onTaskCompleted(String response) " + s);
                                hideProgress();
                                ShowSimpleDialog("Error", "Network Error,pls try after some time.");
                            } else {
                                try {
                                    JSONObject responseJson = new JSONObject(s);
                                    String original_link = responseJson.optString(RestUtils.TAG_ORIGINAL_LINK);
                                    String image_path = responseJson.optString(RestUtils.TAG_IMAGE_PATH);
                                    Uri uri = Uri.fromFile(new File(image_path));
                                    if (uri != null) {
                                        Glide.with(mContext)
                                                .load(new File(image_path))
                                                .circleCrop()
                                                .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_profilepic)))
                                                .into(holder.profileImageview);
                                    } else {
                                        holder.profileImageview.setImageResource(R.drawable.default_profilepic);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).execute(member.getProfilePicName(), "profile", member.getDoctorId() + "");

            }
//         holder.profileImageview.setImageBitmap(StringToBitMap(member.getProfilePicName()));
        } else {
            holder.profileImageview.setImageResource(R.drawable.default_profilepic);
        }
        if (member.getNetworkStatus() == 4) {
            holder.profileImageview.setVisibility(View.VISIBLE);
            holder.connectTextview.setVisibility(View.GONE);
            holder.chatImageview.setVisibility(View.GONE);
            //holder.profileImageview.setEnabled(false);
            holder.tileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selected_position = (Integer) v.getTag();
                    String designation = "";
                    Intent intent = new Intent(mContext, InactiveMembersCard.class);
                    intent.putExtra(mContext.getString(R.string.key_inactive_member), member);
                    intent.putExtra(RestUtils.DEPT_OR_DESIG, deptTitle);
                    if (membersList.get(selected_position).getCommunityDesignation() != null && !membersList.get(selected_position).getCommunityDesignation().isEmpty()) {
                        designation = membersList.get(selected_position).getCommunityDesignation();
                    }
                    intent.putExtra(RestUtils.TAG_DESIGNATION, designation);
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.tileLayout.setClickable(false);
            if (member.getNetworkStatus()== 3) {
                holder.chatImageview.setVisibility(View.GONE);
                holder.connectTextview.setVisibility(View.GONE);
                holder.profileImageview.setVisibility(View.VISIBLE);
                holder.connectTextview.setText(R.string.str_connect);
                holder.connectTextview.setTextColor(mContext.getResources().getColor(R.color.app_green));
                holder.connectTextview.setBackgroundResource(R.drawable.border_text);
            } else if (member.getNetworkStatus()== 0) {
                holder.profileImageview.setVisibility(View.VISIBLE);
                holder.chatImageview.setVisibility(View.GONE);
                holder.connectTextview.setVisibility(View.VISIBLE);
                holder.connectTextview.setText(R.string.str_connect);
                holder.connectTextview.setTextColor(mContext.getResources().getColor(R.color.app_green));
                holder.connectTextview.setBackgroundResource(R.drawable.border_text);
            } else if (member.getNetworkStatus()== 1) {
                holder.profileImageview.setVisibility(View.VISIBLE);
                holder.connectTextview.setVisibility(View.VISIBLE);
                holder.chatImageview.setVisibility(View.GONE);
                holder.connectTextview.setText(R.string.str_invited);
                holder.connectTextview.setTypeface(null, Typeface.BOLD);
                holder.connectTextview.setTextColor(mContext.getResources().getColor(R.color.gray_border_color));
                holder.connectTextview.setBackgroundResource(R.drawable.border_gray_text);
                //holder.connectTextview.setEnabled(false);
            } else if (member.getNetworkStatus() == 2) {
                holder.profileImageview.setVisibility(View.VISIBLE);
                holder.connectTextview.setVisibility(View.VISIBLE);
                holder.chatImageview.setVisibility(View.GONE);
                holder.connectTextview.setText(R.string.str_pending);
                holder.connectTextview.setTypeface(null, Typeface.BOLD);
                holder.connectTextview.setTextColor(mContext.getResources().getColor(R.color.gray_border_color));
                holder.connectTextview.setBackgroundResource(R.drawable.border_gray_text);
                holder.connectTextview.setEnabled(false);
            }

            /**
             * Check whether the logged in user is present in the members list
             * or not, If so we would hide the connect button, disable all clicks.
             */
            if (doctorId == member.getDoctorId()) {
                holder.tileLayout.setClickable(false);
                //holder.profileImageview.setEnabled(false);
                holder.connectTextview.setVisibility(View.GONE);
                holder.nameTextview.setText(mContext.getString(R.string.label_you));
            }


        }
        holder.connectTextview.setOnClickListener(this);
        holder.connectTextview.setTag(currentPosition);
        holder.chatImageview.setOnClickListener(this);
        holder.chatImageview.setTag(currentPosition);
        holder.tileLayout.setOnClickListener(this);
        holder.tileLayout.setTag(currentPosition);
        holder.profileImageview.setOnClickListener(this);
        holder.profileImageview.setTag(currentPosition);
        return convertView;
    }

    private ContactsInfo convertToContact(Member member) {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setDoc_id(member.getDoctorId());
        contactsInfo.setName(member.getFullName());
        contactsInfo.setSpeciality(member.getSpeciality());
        contactsInfo.setSubSpeciality(member.getSubspeciality());
        contactsInfo.setDegree(member.getDegrees());
        contactsInfo.setWorkplace(member.getWorkplace());
        contactsInfo.setLocation(member.getLocation());
        contactsInfo.setEmail(member.getContactEmail());
        contactsInfo.setPhno(member.getContactNumber());
        contactsInfo.setQb_userid(member.getQbUserId());
        contactsInfo.setPic_name(member.getProfilePicName());
        contactsInfo.setPic_url(member.getProfilePicUrl());
        contactsInfo.setNetworkStatus(member.getNetworkStatus() + "");
        contactsInfo.setUserSalutation(member.getUserSalutation());
        contactsInfo.setUserTypeId(member.getUserTypeId());
        contactsInfo.setPhno_vis(member.getPhno_vis());
        contactsInfo.setEmail_vis(member.getEmail_vis());
        return contactsInfo;
    }

    public synchronized void hideProgress() {
        if (progress != null && progress.getActivity() != null) {
            progress.dismissAllowingStateLoss();
        }
    }

    public void ShowSimpleDialog(final String title, final String message) {
        try {
            builder = new AlertDialog.Builder(mContext);
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("OK", null);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}