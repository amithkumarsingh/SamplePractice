package com.vam.whitecoats.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.ui.activities.DepartmentMembersActivity;
import com.vam.whitecoats.ui.activities.InviteRequestActivity;
import com.vam.whitecoats.ui.activities.TimeLine;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;

import java.io.File;

/**
 * Created by tejaswini on 28-06-2016.
 */

public class ShowProfileCard {


    private final Context context;
    private final ContactsInfo profileInfo;

    public ShowProfileCard(Context mContext, ContactsInfo contactsInfo) {
        context = mContext;
        profileInfo = contactsInfo;
    }

    public void popupProfileInfo() {
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
        final TextView tv_verify_email = (TextView) dialog.findViewById(R.id.verify_email);
        final TextView tv_verify_phone = (TextView) dialog.findViewById(R.id.verify_phone);


        if (context.getClass().getSimpleName().equalsIgnoreCase(DepartmentMembersActivity.TAG)) {
            tv_verify_email.setVisibility(View.INVISIBLE);
            tv_verify_phone.setVisibility(View.INVISIBLE);
            tv_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
            tv_contactno.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);

        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        tv_name.setText(profileInfo.getUserSalutation() + " " + profileInfo.getName());
        tv_specialty.setText(profileInfo.getSpeciality());
        if (profileInfo.getSubSpeciality() != null && !profileInfo.getSubSpeciality().isEmpty()) {
            tv_sub_specialty.setVisibility(View.VISIBLE);
            tv_sub_specialty.setText(profileInfo.getSubSpeciality());
        }
        tv_workplace.setText(profileInfo.getWorkplace());
        tv_location.setText(profileInfo.getLocation());

        if (profileInfo.getPic_url() != null && !profileInfo.getPic_url().isEmpty()) {
            /*Picasso.with(context)
                    .load(profileInfo.getPic_url().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(ig_card);*/
            AppUtil.loadCircularImageUsingLib(context, profileInfo.getPic_url().trim(), ig_card, R.drawable.default_profilepic);

        } else if (profileInfo.getPic_data() != null && !profileInfo.getPic_data().equals("")) {
            if (FileHelper.isFilePresent(profileInfo.getPic_data(), "profile", context))
                ig_card.setImageURI(Uri.fromFile(AppUtil.getExternalStoragePathFile(context, ".Whitecoats/Doc_Profiles/" + profileInfo.getPic_data())));
            else
                ig_card.setImageResource(R.drawable.default_profilepic);
        } else {
            ig_card.setImageResource(R.drawable.default_profilepic);
        }
       /* if(!profileInfo.getPic_name().equals("") )//&& !searchinfo.get(position).getPic().equals("null"))
            ig_card.setImageBitmap(StringToBitMap(profileInfo.getPic_data()));*/
        if (!TextUtils.isEmpty(profileInfo.getEmail())) {
            String yourString = profileInfo.getEmail();
            /*if(yourString.length()>12){
                yourString  =  yourString.substring(0,12)+"...";
                tv_email.setText(yourString);
            }else{
                tv_email.setText(yourString); //Dont do any change
            }*/
            tv_email.setText(yourString);
        } else {
            tv_email.setVisibility(View.GONE);
        }

        tv_email.setText("Not shared");
        tv_contactno.setText("Not shared");
        /**
         * Temporarily commented upto BALSAM RELEASE.
         */
        if (context instanceof TimeLine) {
            btn_invite.setVisibility(View.INVISIBLE);
            btn_msg.setVisibility(View.INVISIBLE);
        } else {
            if (profileInfo.getNetworkStatus().equals("0")) {
                btn_invite.setVisibility(View.VISIBLE);
                btn_msg.setVisibility(View.GONE);
            } else if (profileInfo.getNetworkStatus().equals("3")) {
                btn_invite.setVisibility(View.GONE);
                btn_msg.setVisibility(View.GONE);

                if (profileInfo.getEmail_vis() != null && profileInfo.getEmail_vis().equalsIgnoreCase("SHOW_ALL")) {
                    tv_email.setText(profileInfo.getEmail());

                } else {
                    tv_email.setText("Not shared");
                }

                if (profileInfo.getPhno_vis() != null && profileInfo.getPhno_vis().equalsIgnoreCase("SHOW_ALL")) {
                    if (!TextUtils.isEmpty(profileInfo.getPhno())) {
                        tv_contactno.setText(profileInfo.getPhno());
                    }
                } else {
                    tv_contactno.setText("Not Shared");
                }


//                tv_email.setText(profileInfo.getEmail());
//
//                if (!TextUtils.isEmpty(profileInfo.getPhno())) {
//                    tv_contactno.setText(profileInfo.getPhno());
//                }
            } else {
                btn_invite.setVisibility(View.GONE);
                btn_msg.setVisibility(View.GONE);
            }
        }
        tv_contactno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileInfo.getNetworkStatus().equals("3") && !tv_contactno.getText().toString().equalsIgnoreCase("Not Shared")) {
                    AppUtil.makePhoneCall(context, tv_contactno.getText().toString());
                }
            }
        });
        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileInfo.getNetworkStatus().equals("3") && !tv_email.getText().toString().equalsIgnoreCase("Not Shared")) {
                    AppUtil.sendEmail(context, tv_email.getText().toString(), "WhiteCoats", "");
                }

            }
        });
        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InviteRequestActivity.class);
                intent.putExtra("searchContactsInfo", profileInfo);
                //intent.putExtra("search_query", search_query);
                //intent.putExtra("search","search");
                context.startActivity(intent);


                dialog.dismiss();
            }
        });
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new ShowCard(context, profileInfo).goToChatWindow("About_Community");


            }
        });

        tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                                                        @Override
                                                        public boolean onTouch(View v, MotionEvent event) {
                                                            if (AppUtil.isConnectingToInternet(context)) {
                                                                Intent visit_intent = new Intent(context, VisitOtherProfile.class);
                                                                visit_intent.putExtra("searchinfo", profileInfo);
                                                                //visit_intent.putExtra("search_query", search_query);
                                                                context.startActivity(visit_intent);
                                                                dialog.dismiss();
                                                            } else {
                                                                dialog.dismiss();
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    private Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
