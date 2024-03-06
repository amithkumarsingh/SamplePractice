package com.vam.whitecoats.ui.adapters;

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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.Member;
import com.vam.whitecoats.ui.activities.DepartmentMembersActivity;
import com.vam.whitecoats.ui.activities.InactiveMembersCard;
import com.vam.whitecoats.ui.activities.InviteRequestActivity;
import com.vam.whitecoats.ui.activities.SearchContactsActivity;
import com.vam.whitecoats.ui.activities.TimeLine;
import com.vam.whitecoats.ui.activities.ViewContactsActivity;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.interfaces.NavigateScreenListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowCard;

import java.util.ArrayList;

/**
 * Created by swathim on 6/8/2015.
 */
public class ContactsAdapter extends BaseAdapter {
    public static final String TAG = ContactsAdapter.class.getSimpleName();
    public Context mContext;
    private LayoutInflater mInflater;
    private ImageView chat_ig, profile_pic;
    private TextView names, specialty, tx_connect;
    private RelativeLayout rv_tile;
    private ArrayList<ContactsInfo> searchinfo;
    private SearchContactsActivity searchContactsActivity = null;
    private ViewContactsActivity viewContactsActivity = null;
    private String search_query;
    private AlertDialog.Builder builder;
    String navigateFrom;
    /*Refactoring the deprecated startActivityForResults*/
    private NavigateScreenListener contactsClickListener;
    /*Refactoring the deprecated startActivityForResults passing launcher object in adapter*/
    public ContactsAdapter(Context context, ArrayList<ContactsInfo> searchinfolist, String search_query, String navigateFrom) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.searchinfo = searchinfolist;
        this.search_query = search_query;
        this.navigateFrom = navigateFrom;
        searchContactsActivity = (SearchContactsActivity) context;
        this.contactsClickListener=(NavigateScreenListener)context;
    }

    public ContactsAdapter(Context context, ArrayList<ContactsInfo> searchinfolist) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.searchinfo = searchinfolist;
        viewContactsActivity = (ViewContactsActivity) context;
    }


    @Override
    public int getCount() {
        return searchinfo.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        vi = mInflater.inflate(R.layout.search_contacts_listitem, null);
        rv_tile = (RelativeLayout) vi.findViewById(R.id.tile_layout);
        names = (TextView) vi.findViewById(R.id.name_txt);
        specialty = (TextView) vi.findViewById(R.id.speciality_txt);
        chat_ig = (ImageView) vi.findViewById(R.id.chat_img);
        profile_pic = (ImageView) vi.findViewById(R.id.imageurl);
        tx_connect = (TextView) vi.findViewById(R.id.connect_text);

        if (searchinfo.get(position).getUserSalutation().equalsIgnoreCase("null")) {
            names.setText(searchinfo.get(position).getName());
        } else {
            names.setText(searchinfo.get(position).getUserSalutation() + " " + searchinfo.get(position).getName());
        }
        /**
         * If it's a normal search then we need to set the Doctor's Speciality
         * else if it is a Community Search then we need to set the doctor's
         * Designation.
         */
        if (navigateFrom != null && (navigateFrom.equalsIgnoreCase(DepartmentMembersActivity.TAG) || navigateFrom.equalsIgnoreCase(TimeLine.TAG))) {
            if (searchinfo.get(position).getCommunity_designation().equalsIgnoreCase("") && searchinfo.get(position).getLocation().equalsIgnoreCase("")) {
                specialty.setText(searchinfo.get(position).getCommunity_designation() + searchinfo.get(position).getLocation());
            } else if (searchinfo.get(position).getCommunity_designation().equalsIgnoreCase("") && searchinfo.get(position).getLocation() != null) {
                specialty.setText(searchinfo.get(position).getCommunity_designation() + searchinfo.get(position).getLocation());
            } else if (searchinfo.get(position).getCommunity_designation() != null && searchinfo.get(position).getLocation().equalsIgnoreCase("")) {
                specialty.setText(searchinfo.get(position).getCommunity_designation() + searchinfo.get(position).getLocation());
            } else {
                specialty.setText(searchinfo.get(position).getCommunity_designation() + ", " + searchinfo.get(position).getLocation());
            }
        } else {
            if (searchinfo.get(position).getCommunity_designation().equalsIgnoreCase("") && searchinfo.get(position).getLocation().equalsIgnoreCase("")) {
                specialty.setText(searchinfo.get(position).getSpeciality() + searchinfo.get(position).getLocation());
            } else {
                specialty.setText(searchinfo.get(position).getSpeciality() + ", " + searchinfo.get(position).getLocation());
            }
        }
      /*  if (searchinfo.get(position).getPic_data() != null && !searchinfo.get(position).getPic_data().equals("")) {//&& !searchinfo.get(position).getPic().equals("null")) {
            profile_pic.setImageBitmap(StringToBitMap(searchinfo.get(position).getPic_data()));
        } else {
            profile_pic.setImageResource(R.drawable.default_profilepic);
        }*/

        if (searchinfo.get(position).getPic_url() != null && !searchinfo.get(position).getPic_url().isEmpty()) {
           /* Picasso.with(mContext)
                    .load(searchinfo.get(position).getPic_url().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(profile_pic);*/

            AppUtil.loadCircularImageUsingLib(mContext, searchinfo.get(position).getPic_url().trim(), profile_pic, R.drawable.default_profilepic);

        } else {
            profile_pic.setImageResource(R.drawable.default_profilepic);
        }


        rv_tile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchinfo.get(position).getNetworkStatus().equals("0")) {
                    if (tx_connect.getText().toString().equals("Connect")) {
                        Intent intent = new Intent(mContext, InviteRequestActivity.class);
                        intent.putExtra("search_query", search_query);
                        intent.putExtra("searchContactsInfo", searchinfo.get(position));
                        intent.putExtra("search", "search");
                        contactsClickListener.onScreenNavigate(intent);
                        /*else
                            viewContactsActivity.startActivityForResult(intent, 1);*/
                    }

                } else if (searchinfo.get(position).getNetworkStatus().equals("3")) {
                    //new ShowCard(mContext, search_query, searchinfo.get(position)).goToChatWindow();
                } else if (searchinfo.get(position).getNetworkStatus().equals("4")) {
                    Intent intent = new Intent(mContext, InactiveMembersCard.class);
                    intent.putExtra(mContext.getString(R.string.key_inactive_member), convertContactsToMember(searchinfo.get(position)));
                    intent.putExtra(RestUtils.DEPT_OR_DESIG, "");
                    intent.putExtra(RestUtils.TAG_DESIGNATION, searchinfo.get(position).getCommunity_designation());
                    mContext.startActivity(intent);
                }
            }
        });

        tx_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchinfo.get(position).getNetworkStatus().equals("0")) {
                    Intent intent = new Intent(mContext, InviteRequestActivity.class);
                    intent.putExtra("search_query", search_query);
                    intent.putExtra("searchContactsInfo", searchinfo.get(position));
                    intent.putExtra("search", "search");
                    contactsClickListener.onScreenNavigate(intent);
                    /*else
                        viewContactsActivity.startActivityForResult(intent, 1);*/

                }
            }
        });


        if (searchinfo.get(position).getNetworkStatus().equals("1")) {
            tx_connect.setText(R.string.str_invited);
            tx_connect.setTypeface(null, Typeface.BOLD);
            tx_connect.setTextColor(mContext.getResources().getColor(R.color.gray_border_color));
            tx_connect.setBackgroundResource(R.drawable.border_gray_text);
        } else if (searchinfo.get(position).getNetworkStatus().equals("2")) {
            tx_connect.setText(R.string.str_pending);
            tx_connect.setTextColor(mContext.getResources().getColor(R.color.gray_border_color));
            tx_connect.setBackgroundResource(R.drawable.border_gray_text);
        } else if (searchinfo.get(position).getNetworkStatus().equals("3")) {
            tx_connect.setVisibility(View.GONE);
            chat_ig.setVisibility(View.GONE);
        } else if (searchinfo.get(position).getNetworkStatus().equals("4")) {
            tx_connect.setVisibility(View.GONE);
            chat_ig.setVisibility(View.GONE);
            profile_pic.setVisibility(View.VISIBLE);
            profile_pic.setEnabled(false);
            rv_tile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, InactiveMembersCard.class);
                    intent.putExtra(mContext.getString(R.string.key_inactive_member), convertContactsToMember(searchinfo.get(position)));
                    intent.putExtra(RestUtils.DEPT_OR_DESIG, "");
                    intent.putExtra(RestUtils.TAG_DESIGNATION, searchinfo.get(position).getCommunity_designation());
                    mContext.startActivity(intent);
                }
            });
        }

        profile_pic.setOnClickListener(new View.OnClickListener() {
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
                //verification process
                final TextView tv_verify_email = (TextView) dialog.findViewById(R.id.verify_email);
                final TextView tv_verify_phone = (TextView) dialog.findViewById(R.id.verify_phone);
                tv_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_email, 0, 0, 0);
                tv_contactno.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_phone, 0, 0, 0);
                tv_verify_email.setVisibility(View.GONE);
                tv_verify_phone.setVisibility(View.GONE);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);
                tv_name.setText(searchinfo.get(position).getUserSalutation() + " " + searchinfo.get(position).getName());
                tv_specialty.setText(searchinfo.get(position).getSpeciality() + ", " + searchinfo.get(position).getLocation());
                //tv_sub_specialty.setText(searchinfo.get(position).getSubSpeciality());

                if (searchinfo.get(position).getSubSpeciality() != null && !searchinfo.get(position).getSubSpeciality().isEmpty()) {
                    tv_sub_specialty.setVisibility(View.VISIBLE);
                    tv_sub_specialty.setText(searchinfo.get(position).getSubSpeciality());
                }
                tv_workplace.setText(searchinfo.get(position).getWorkplace());
                tv_location.setText(searchinfo.get(position).getLocation());
                if (!searchinfo.get(position).getPic_name().equals(""))//&& !searchinfo.get(position).getPic().equals("null"))
                    ig_card.setImageBitmap(StringToBitMap(searchinfo.get(position).getPic_data()));
                if (!TextUtils.isEmpty(searchinfo.get(position).getEmail())) {
                    String yourString = searchinfo.get(position).getPhno();
                    /*if (yourString.length() > 12) {
                        yourString = yourString.substring(0, 12) + "...";
                        tv_email.setText(yourString);
                    } else {
                        tv_email.setText(yourString); //Dont do any change
                    }*/
                    tv_email.setText(yourString);
                } else {
                    tv_email.setVisibility(View.GONE);
                }

                tv_email.setText("Not shared");
                tv_contactno.setText("Not shared");

                if (searchinfo.get(position).getNetworkStatus().equals("0")) {
                    btn_invite.setVisibility(View.VISIBLE);
                    btn_msg.setVisibility(View.GONE);
                    tv_email.setText("Not shared");
                    tv_contactno.setText("Not shared");
                } else if (searchinfo.get(position).getNetworkStatus().equals("3")) {
                    btn_invite.setVisibility(View.GONE);
                    btn_msg.setVisibility(View.GONE);
                    if (searchinfo.get(position).getPhno_vis().equalsIgnoreCase("SHOW_ALL")) {
                            if (!TextUtils.isEmpty(searchinfo.get(position).getPhno())) {
                                tv_contactno.setText(searchinfo.get(position).getPhno());
                            }
                        } else {
                            tv_contactno.setText("Not Shared");
                        }

                        if (searchinfo.get(position).getEmail_vis().equalsIgnoreCase("SHOW_ALL")) {
                            tv_email.setText(searchinfo.get(position).getEmail());

                        } else {
                            tv_email.setText("Not shared");
                        }
                } else {
                    btn_invite.setVisibility(View.GONE);
                    btn_msg.setVisibility(View.GONE);
                    tv_email.setText("Not shared");
                    tv_contactno.setText("Not shared");
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

                btn_invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, InviteRequestActivity.class);
                        intent.putExtra("searchContactsInfo", searchinfo.get(position));
                        intent.putExtra("search_query", search_query);
                        intent.putExtra("search", "search");
                        contactsClickListener.onScreenNavigate(intent);

                      /*  else
                            viewContactsActivity.startActivityForResult(intent, 1);*/
                        dialog.dismiss();
                    }
                });
                btn_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        new ShowCard(mContext, search_query, searchinfo.get(position)).goToChatWindow();
                    }
                });

                tv_view_complete_profile.setOnTouchListener(new View.OnTouchListener() {
                                                                @Override
                                                                public boolean onTouch(View v, MotionEvent event) {
                                                                    if (AppUtil.isConnectingToInternet(mContext)) {
                                                                        Intent visit_intent = new Intent(mContext, VisitOtherProfile.class);
                                                                        visit_intent.putExtra("searchinfo", searchinfo.get(position));
                                                                        visit_intent.putExtra("search_query", search_query);
                                                                        mContext.startActivity(visit_intent);
                                                                        dialog.dismiss();
                                                                    } else {
                                                                        dialog.dismiss();
                                                                        builder = new AlertDialog.Builder(mContext);
                                                                        builder.setMessage(Html.fromHtml(mContext.getResources().getString(R.string.sNetworkError)));
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
        return vi;
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
        member.setContactEmail(contactsInfo.getEmail());
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
