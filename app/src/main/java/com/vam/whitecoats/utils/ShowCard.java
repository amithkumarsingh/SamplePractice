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
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;

import java.io.File;

import io.realm.Realm;

/*import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;*/

/**
 * Created by lokeshl on 10/5/2015.
 */
public class ShowCard {
    Context mContext;
    ContactsInfo mysearchinfo;
    Realm realm;
    RealmManager realmManager;
    String search_query;
    private AlertDialog.Builder builder;

    public ShowCard(Context mcontext, ContactsInfo searchinfo) {
        this.mContext = mcontext;
        mysearchinfo = searchinfo;
        realmManager = new RealmManager(mContext);
        realm = Realm.getDefaultInstance();
    }

    public ShowCard(Context mContext, String search_query, ContactsInfo searchinfo) {
        this.mContext = mContext;
        this.search_query = search_query;
        mysearchinfo = searchinfo;
        realmManager = new RealmManager(mContext);
        realm = Realm.getDefaultInstance();
    }

    public void showMyConnectCard() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_card_dialog);
        ImageView ig_card = (ImageView) dialog.findViewById(R.id.visit_card_pic_img);
        TextView tv_name = (TextView) dialog.findViewById(R.id.card_visit_other_name);
        TextView tv_specialty = (TextView) dialog.findViewById(R.id.card_visit_others_specialty);
        TextView tv_sub_specialty = (TextView) dialog.findViewById(R.id.card_sub_specialty);
        TextView tv_workplace = (TextView) dialog.findViewById(R.id.card_visit_others_workplace);
        TextView tv_location = (TextView) dialog.findViewById(R.id.card_visit_others_location);
        ImageView btn_invite = (ImageView) dialog.findViewById(R.id.add_req_btn);
        ImageView btn_msg = (ImageView) dialog.findViewById(R.id.message_btn);
        TextView tv_email = (TextView) dialog.findViewById(R.id.card_email_id);
        TextView tv_contactno = (TextView) dialog.findViewById(R.id.card_contact_id);
        TextView view_complete_profile_tv = (TextView) dialog.findViewById(R.id.view_complete_profile_tv);
        btn_invite.setVisibility(View.GONE);
        btn_msg.setVisibility(View.GONE);
        TextView tv_verify_email = (TextView) dialog.findViewById(R.id.verify_email);
        TextView tv_verify_phone = (TextView) dialog.findViewById(R.id.verify_phone);
        TextView tv_emailid = (TextView) dialog.findViewById(R.id.card_email_id);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        tv_name.setText(mysearchinfo.getUserSalutation() + " " + mysearchinfo.getName());
        tv_specialty.setText(mysearchinfo.getSpeciality());
        if (mysearchinfo.getSubSpeciality() != null && !mysearchinfo.getSubSpeciality().isEmpty()) {
            tv_sub_specialty.setVisibility(View.VISIBLE);
            tv_sub_specialty.setText(mysearchinfo.getSubSpeciality());
        }
        if (mysearchinfo.getPic_url() != null && !mysearchinfo.getPic_url().isEmpty()) {
            /*Picasso.with(mContext)
                    .load(mysearchinfo.getPic_url().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(ig_card);*/
            AppUtil.loadCircularImageUsingLib(mContext, mysearchinfo.getPic_url().trim(), ig_card, R.drawable.default_profilepic);
        } else if (mysearchinfo.getPic_data() != null && !mysearchinfo.getPic_data().equals("")) {
            if (FileHelper.isFilePresent(mysearchinfo.getPic_data(), "profile", mContext))
                ig_card.setImageURI(Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/Doc_Profiles/" + mysearchinfo.getPic_data())));
        } else {
            ig_card.setImageResource(R.drawable.default_profilepic);
        }

        if (mysearchinfo.getNetworkStatus().equalsIgnoreCase("3")) {
            if (mysearchinfo.getEmail_vis() != null && mysearchinfo.getEmail_vis().equalsIgnoreCase("SHOW_ALL")) {
                if (!TextUtils.isEmpty(mysearchinfo.getEmail())) {
                    String yourString = mysearchinfo.getEmail();
            /*if (yourString.length() > 12) {
                yourString = yourString.substring(0, 12) + "...";
                tv_email.setText(yourString);
            } else {
                tv_email.setText(yourString); //Dont do any change
            }*/
                    tv_email.setText(yourString); //Dont do any change
                }
            } else {
                tv_email.setText("Not shared");
            }

            if (mysearchinfo.getPhno_vis() != null && mysearchinfo.getPhno_vis().equalsIgnoreCase("SHOW_ALL")) {
                if (!TextUtils.isEmpty(mysearchinfo.getPhno())) {
                    tv_contactno.setText(mysearchinfo.getPhno());
                } else {
                    tv_contactno.setText("Not Shared");
                }
            } else {
                tv_contactno.setText("Not Shared");
            }

        } else {
            tv_email.setText("Not shared");
            tv_contactno.setText("Not shared");
        }


//        if (!TextUtils.isEmpty(mysearchinfo.getEmail())) {
//            String yourString = mysearchinfo.getEmail();
//            /*if (yourString.length() > 12) {
//                yourString = yourString.substring(0, 12) + "...";
//                tv_email.setText(yourString);
//            } else {
//                tv_email.setText(yourString); //Dont do any change
//            }*/
//            tv_email.setText(yourString); //Dont do any change
//        }
//
//        if (!TextUtils.isEmpty(mysearchinfo.getPhno())) {
//            tv_contactno.setText(mysearchinfo.getPhno());
//        } else {
//            tv_contactno.setText("Not Shared");
//        }

        tv_contactno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_contactno.getText().toString().isEmpty() || tv_contactno.getText().toString().equalsIgnoreCase("Not shared")) {
                    return;
                }
                AppUtil.makePhoneCall(mContext, tv_contactno.getText().toString());
            }
        });
        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_email.getText().toString().isEmpty() || tv_email.getText().toString().equalsIgnoreCase("Not shared")) {
                    return;
                }
                AppUtil.sendEmail(mContext, tv_email.getText().toString(), "WhiteCoats", "");
            }
        });
        tv_workplace.setText(mysearchinfo.getWorkplace());
        tv_location.setText(mysearchinfo.getLocation());
        ValidationUtils validationUtils = new ValidationUtils(mContext, new TextView[]{tv_name, tv_specialty, tv_workplace, tv_location, tv_email, tv_contactno});
        validationUtils.hideViewIfnull();

        view_complete_profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(mContext)) {
                    dialog.dismiss();
                    Intent intent = new Intent(mContext, VisitOtherProfile.class);
                    intent.putExtra("searchinfo", mysearchinfo);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                } else {
                    dialog.dismiss();
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(Html.fromHtml(mContext.getResources().getString(R.string.sNetworkError)));
                    builder.setPositiveButton("OK", null);
                    builder.create().show();
                }
            }
        });
        if (mysearchinfo.getPic_url() != null && !mysearchinfo.getPic_url().isEmpty()) {
            /*Picasso.with(mContext)
                    .load(mysearchinfo.getPic_url().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(ig_card);*/
            AppUtil.loadCircularImageUsingLib(mContext, mysearchinfo.getPic_url().trim(), ig_card, R.drawable.default_profilepic);
        } else if (mysearchinfo.getNetworkStatus() != null && !mysearchinfo.getNetworkStatus().equals("3"))
            btn_msg.setVisibility(View.GONE);
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goToChatWindow();
            }
        });
        dialog.show();

        tv_verify_email.setVisibility(View.GONE);
        tv_verify_phone.setVisibility(View.GONE);
        tv_emailid.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
        tv_contactno.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);
    }

    public void showMyConnectCard(final String from) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_card_dialog);
        ImageView ig_card = (ImageView) dialog.findViewById(R.id.visit_card_pic_img);
        TextView tv_name = (TextView) dialog.findViewById(R.id.card_visit_other_name);
        TextView tv_specialty = (TextView) dialog.findViewById(R.id.card_visit_others_specialty);
        TextView tv_sub_specialty = (TextView) dialog.findViewById(R.id.card_sub_specialty);
        TextView tv_workplace = (TextView) dialog.findViewById(R.id.card_visit_others_workplace);
        TextView tv_location = (TextView) dialog.findViewById(R.id.card_visit_others_location);
        ImageView btn_invite = (ImageView) dialog.findViewById(R.id.add_req_btn);
        ImageView btn_msg = (ImageView) dialog.findViewById(R.id.message_btn);
        TextView tv_email = (TextView) dialog.findViewById(R.id.card_email_id);
        TextView tv_contactno = (TextView) dialog.findViewById(R.id.card_contact_id);
        TextView tv_emailid = (TextView) dialog.findViewById(R.id.card_email_id);
        TextView view_complete_profile_tv = (TextView) dialog.findViewById(R.id.view_complete_profile_tv);
        btn_invite.setVisibility(View.GONE);
        btn_msg.setVisibility(View.GONE);
        //verification
        TextView tv_verify_email = (TextView) dialog.findViewById(R.id.verify_email);
        TextView tv_verify_phone = (TextView) dialog.findViewById(R.id.verify_phone);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        tv_name.setText(mysearchinfo.getUserSalutation() + " " + mysearchinfo.getName());
        tv_specialty.setText(mysearchinfo.getSpeciality());
        if (mysearchinfo.getSubSpeciality() != null && !mysearchinfo.getSubSpeciality().isEmpty()) {
            tv_sub_specialty.setVisibility(View.VISIBLE);
            tv_sub_specialty.setText(mysearchinfo.getSubSpeciality());
        }
        if (mysearchinfo.getPic_url() != null && !mysearchinfo.getPic_url().isEmpty()) {
            /*Picasso.with(mContext)
                    .load(mysearchinfo.getPic_url().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(ig_card);*/
            AppUtil.loadCircularImageUsingLib(mContext, mysearchinfo.getPic_url().trim(), ig_card, R.drawable.default_profilepic);
        } else if (mysearchinfo.getPic_data() != null && !mysearchinfo.getPic_data().equals("")) {
            if (FileHelper.isFilePresent(mysearchinfo.getPic_data(), "profile", mContext))
                ig_card.setImageURI(Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/Doc_Profiles/" + mysearchinfo.getPic_data())));
            else
                ig_card.setImageResource(R.drawable.default_profilepic);
        }
        if (!TextUtils.isEmpty(mysearchinfo.getEmail())) {
            String yourString = mysearchinfo.getEmail();
            /*if (yourString.length() > 12) {
                yourString = yourString.substring(0, 12) + "...";
                tv_email.setText(yourString);
            } else {
                tv_email.setText(yourString); //Dont do any change
            }*/
            tv_email.setText(yourString);
        }

        if (!TextUtils.isEmpty(mysearchinfo.getPhno())) {
            tv_contactno.setText(mysearchinfo.getPhno());
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
                if (tv_email.getText().toString().isEmpty()) {
                    return;
                }
                AppUtil.sendEmail(mContext, tv_email.getText().toString(), "WhiteCoats", "");
            }
        });
        tv_workplace.setText(mysearchinfo.getWorkplace());
        tv_location.setText(mysearchinfo.getLocation());
        ValidationUtils validationUtils = new ValidationUtils(mContext, new TextView[]{tv_name, tv_specialty, tv_workplace, tv_location, tv_email, tv_contactno});
        validationUtils.hideViewIfnull();

        view_complete_profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(mContext)) {
                    dialog.dismiss();
                    Intent intent = new Intent(mContext, VisitOtherProfile.class);
                    intent.putExtra("searchinfo", mysearchinfo);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                } else {
                    dialog.dismiss();
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(Html.fromHtml(mContext.getResources().getString(R.string.sNetworkError)));
                    builder.setPositiveButton("OK", null);
                    builder.create().show();
                }
            }
        });
        if (mysearchinfo.getNetworkStatus() != null && !mysearchinfo.getNetworkStatus().equals("3"))
            btn_msg.setVisibility(View.GONE);
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goToChatWindow(from);
            }
        });
        dialog.show();
        tv_verify_email.setVisibility(View.GONE);
        tv_verify_phone.setVisibility(View.GONE);
        tv_emailid.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
        tv_contactno.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);
    }

    public void showselectContactsGroupcard() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_card_dialog);
        ImageView ig_card = (ImageView) dialog.findViewById(R.id.visit_card_pic_img);
        TextView tv_name = (TextView) dialog.findViewById(R.id.card_visit_other_name);
        TextView tv_specialty = (TextView) dialog.findViewById(R.id.card_visit_others_specialty);
        TextView tv_workplace = (TextView) dialog.findViewById(R.id.card_visit_others_workplace);
        TextView tv_location = (TextView) dialog.findViewById(R.id.card_visit_others_location);
        ImageView btn_invite = (ImageView) dialog.findViewById(R.id.add_req_btn);
        ImageView btn_msg = (ImageView) dialog.findViewById(R.id.message_btn);
        TextView tv_email = (TextView) dialog.findViewById(R.id.card_email_id);
        TextView tv_contactno = (TextView) dialog.findViewById(R.id.card_contact_id);
        TextView view_complete_profile_tv = (TextView) dialog.findViewById(R.id.view_complete_profile_tv);
        btn_invite.setVisibility(View.GONE);
        btn_msg.setVisibility(View.VISIBLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        tv_name.setText(mysearchinfo.getUserSalutation() + " " + mysearchinfo.getName());
        tv_specialty.setText(mysearchinfo.getSpeciality());
        if (mysearchinfo.getPic_url() != null && !mysearchinfo.getPic_url().isEmpty()) {
            /*Picasso.with(mContext)
                    .load(mysearchinfo.getPic_url().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(ig_card);*/
            AppUtil.loadCircularImageUsingLib(mContext, mysearchinfo.getPic_url().trim(), ig_card, R.drawable.default_profilepic);
        } else if (mysearchinfo.getPic_name() != null && !mysearchinfo.getPic_name().equals("")) {
            if (FileHelper.isFilePresent(mysearchinfo.getPic_name(), "profile", mContext)) {
                ig_card.setImageURI(Uri.fromFile(AppUtil.getExternalStoragePathFile(mContext, ".Whitecoats/Doc_Profiles/" + mysearchinfo.getPic_name())));
            } else {
                ig_card.setImageResource(R.drawable.default_profilepic);
            }
        }
        if (!TextUtils.isEmpty(mysearchinfo.getEmail())) {
            String yourString = mysearchinfo.getEmail();
            /*if (yourString.length() > 12) {
                yourString = yourString.substring(0, 12) + "...";
                tv_email.setText(yourString);
            } else {
                tv_email.setText(yourString); //Dont do any change
            }*/
            tv_email.setText(yourString);
        }

        if (!TextUtils.isEmpty(mysearchinfo.getPhno())) {
            tv_contactno.setText(mysearchinfo.getPhno());
        } else {
            tv_contactno.setText("Not Shared");
        }

        tv_workplace.setText(mysearchinfo.getWorkplace());
        tv_location.setText(mysearchinfo.getLocation());
        ValidationUtils validationUtils = new ValidationUtils(mContext, new TextView[]{tv_name, tv_specialty, tv_workplace, tv_location, tv_email, tv_contactno});
        validationUtils.hideViewIfnull();

        view_complete_profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(mContext)) {
                    dialog.dismiss();
                    Intent intent = new Intent(mContext, VisitOtherProfile.class);
                    intent.putExtra("searchinfo", mysearchinfo);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                } else {
                    dialog.dismiss();
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(Html.fromHtml(mContext.getResources().getString(R.string.sNetworkError)));
                    builder.setPositiveButton("OK", null);
                    builder.create().show();
                }
            }
        });
        if (mysearchinfo.getNetworkStatus() != null && !mysearchinfo.getNetworkStatus().equals("3"))
            btn_msg.setVisibility(View.GONE);
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goToChatWindow();
            }
        });


        dialog.show();
    }

    public void goToChatWindow() {

        /*try {
            if (AppUtil.isConnectingToInternet(mContext)) {
                QBChatDialog dialog = DialogUtils.buildPrivateDialog(mysearchinfo.getQb_userid());
                QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle params) {
                        if (realmManager.checkDialoginDB(realm, dialog.getDialogId()) == 0) {
                            realmManager.insertQBDialog(realm, dialog);

                        }
                        if (mysearchinfo != null && QBLogin.chatService!=null && QBLogin.chatService.getPrivateChatManager()!=null && QBLogin.chatService.getPrivateChatManager().getChat(mysearchinfo.getQb_userid()) == null) {
                            QBLogin.chatService.getPrivateChatManager().createChat(mysearchinfo.getQb_userid(), QBLogin.privateChatMessageListener);
                        }
                        Intent intent = new Intent(mContext, ChatActivity.class);
                        if (!TextUtils.isEmpty(search_query)) {
                            intent.putExtra("search_query", search_query);
                        }
                        intent.putExtra("group", "private");
                        intent.putExtra(ChatActivity.EXTRA_DIALOG, dialog);
                        intent.putExtra(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onError(QBResponseException responseException) {
                        QBChatDialog selectedDialog = realmManager.getQBDialogByoccId(realm, mysearchinfo.getDoc_id());
                        if (selectedDialog.getDialogId() != null) {
                            Intent intent = new Intent(mContext, ChatActivity.class);
                            intent.putExtra("group", "private");
                            intent.putExtra(ChatActivity.EXTRA_DIALOG, selectedDialog);
                            intent.putExtra(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                            mContext.startActivity(intent);
                        }
                    }
                });

            } else {
                QBChatDialog selectedDialog = realmManager.getQBDialogByoccId(realm, mysearchinfo.getDoc_id());
                if (selectedDialog.getDialogId() != null && !selectedDialog.getDialogId().isEmpty()) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra("group", "private");
                    intent.putExtra(ChatActivity.EXTRA_DIALOG, selectedDialog);
                    intent.putExtra(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, R.string.no_internet_small, Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }*/
    }

    public void goToChatWindow(final String from) {

        //Code for offline 1-1 chat tile deletion

//        QBPrivateChat privateChat = QBLogin.chatService.getPrivateChatManager().getChat(mysearchinfo.getQb_userid()
       /* QBDialog tempDialog = realmManager.getQBDialogByoccId(realm,mysearchinfo.getQb_userid());
        if(tempDialog.getDialogId() !=null ){

                realmManager.setDialogTYpe(realm,tempDialog.getDialogId(),tempDialog.getType()+"");
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("group", "private");
                intent.putExtra(ChatActivity.EXTRA_DIALOG, tempDialog);
                intent.putExtra(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                mContext.startActivity(intent);

      }else {*/
        /*try {
            if (AppUtil.isConnectingToInternet(mContext)) {
                QBChatDialog dialog = DialogUtils.buildPrivateDialog(mysearchinfo.getQb_userid());
                QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle params) {
                        if (realmManager.checkDialoginDB(realm, dialog.getDialogId()) == 0) {
                            realmManager.insertQBDialog(realm, dialog);
                        }
                        if (mysearchinfo != null && QBLogin.chatService!=null && QBLogin.chatService.getPrivateChatManager()!=null && QBLogin.chatService.getPrivateChatManager().getChat(mysearchinfo.getQb_userid()) == null) {
                            QBLogin.chatService.getPrivateChatManager().createChat(mysearchinfo.getQb_userid(), QBLogin.privateChatMessageListener);
                        }
                        Intent intent = new Intent(mContext, ChatActivity.class);
                        if (!TextUtils.isEmpty(search_query)) {
                            intent.putExtra("search_query", search_query);
                        }
                        intent.putExtra("group", "private");
                        intent.putExtra(ChatActivity.EXTRA_DIALOG, dialog);
                        intent.putExtra(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                        intent.putExtra("from", from);
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onError(QBResponseException responseException) {
                        QBChatDialog selectedDialog = realmManager.getQBDialogByoccId(realm, mysearchinfo.getDoc_id());
                        if (selectedDialog.getDialogId() != null && !selectedDialog.getDialogId().isEmpty()) {
                            Intent intent = new Intent(mContext, ChatActivity.class);
                            intent.putExtra("group", "private");
                            intent.putExtra("from", from);
                            intent.putExtra(ChatActivity.EXTRA_DIALOG, selectedDialog);
                            intent.putExtra(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                            mContext.startActivity(intent);
                        }
                    }
                });

            } else {
                QBChatDialog selectedDialog = realmManager.getQBDialogByoccId(realm, mysearchinfo.getDoc_id());
                if (selectedDialog.getDialogId() != null && !selectedDialog.getDialogId().isEmpty()) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra("group", "private");
                    intent.putExtra(ChatActivity.EXTRA_DIALOG, selectedDialog);
                    intent.putExtra(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, R.string.no_internet_small, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }*/

    }
//}

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
}
