package com.vam.whitecoats.core.realm;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.NotificationType;
import com.vam.whitecoats.core.models.AcademicInfo;
import com.vam.whitecoats.core.models.AreasOfInterest;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.CaseRoomAttachmentsInfo;
import com.vam.whitecoats.core.models.CaseRoomInfo;
import com.vam.whitecoats.core.models.CaseRoomPatientDetailsInfo;
import com.vam.whitecoats.core.models.CaseroomNotifyInfo;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.EventInfo;
import com.vam.whitecoats.core.models.GroupNotifyInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.core.models.RealmNotificationInfo;
import com.vam.whitecoats.core.models.SymptomsInfo;
import com.vam.whitecoats.core.quickblox.qbmodels.MessageModel;
import com.vam.whitecoats.core.quickblox.qbmodels.QBDialogMemInfo;
import com.vam.whitecoats.core.quickblox.qbmodels.QBDialogModel;
import com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog;
import com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo;
import com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog;
import com.vam.whitecoats.ui.activities.QBLogin;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmException;

/*import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialogCustomData;
import com.quickblox.chat.model.QBDialogType;*/

/**
 * Created by lokeshl on 4/24/2015.
 */
public class RealmManager {

    int current_user_id;
    Context c;
    public static final String TAG = RealmManager.class.getSimpleName();
    private static RealmManager realmManagerInstance = null;

    public RealmManager(Context c) {
        this.c = c;
    }

    public static RealmManager getInstance(Context context) {
        if (realmManagerInstance == null) {
            realmManagerInstance = new RealmManager(context);
        }

        return realmManagerInstance;
    }

    public void updateQBBasicLogin(Realm realm, String qb_user_id, String qb_user_login, String qb_user_password, String qb_hidden_dialog_id) {
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setQb_login(qb_user_login);
                hallos.get(0).setPsswd(qb_user_password);
                hallos.get(0).setQb_userid(Integer.parseInt(qb_user_id));
                hallos.get(0).setQb_hidden_dialog_id(qb_hidden_dialog_id);

            }
            realm.commitTransaction();

        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updateQBqb_hidden_dialog_id(Realm realm, String qb_hidden_dialog_id) {
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setQb_hidden_dialog_id(qb_hidden_dialog_id);
            }
            realm.commitTransaction();

        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updateBasicLoginEmail(Realm realm, String email) {
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setEmail(email);
            }
            realm.commitTransaction();

        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

    }

    public void updateBasicLoginEmailstatus(Realm realm, String status) {
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setEmailvalidation(status);
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    // code for 1-1 chat tile deletion
    /*public void setDialogTYpe(Realm realm, String dialogid, String dialog_type) {

        try {
            realm.beginTransaction();
            RealmResults<RealmQBDialog> hallos = realm.where(RealmQBDialog.class).equalTo("dialog_id", dialogid).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setDialog_type(dialog_type);
                hallos.get(0).setDialog_type(0 + "");
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }*/

    public RealmBasicInfo getRealmBasicInfo(Realm realm) {
        RealmBasicInfo realmBasicInfo = new RealmBasicInfo();
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                realmBasicInfo = hallos.get(0);
                if (hallos.get(0).getProfile_pic_path() != null && !hallos.get(0).getProfile_pic_path().isEmpty()) {
                    realmBasicInfo.setProfile_pic_path(hallos.get(0).getProfile_pic_path().replace("/Whitecoats", "/.Whitecoats"));
                }
                if (hallos.get(0).getUser_salutation() == null) {
                    hallos.get(0).setUser_salutation("Dr.");
                }
                if (hallos.get(0).getUser_type_id() == 0) {
                    hallos.get(0).setUser_type_id(1);
                }
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

        return realmBasicInfo;
    }

    public int getBasicInfoCount(Realm realm) {
        int count = 0;
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() >= 1) {
                count = hallos.size();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

        return count;
    }

    public List<String> getemailstatusBasicInfo(Realm realm) {
        List<String> emailstatus = new ArrayList<>();
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                emailstatus.add(hallos.get(0).getEmail());
                emailstatus.add(hallos.get(0).getEmailvalidation());
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

        return emailstatus;
    }

    public boolean insertBasicInfo(Realm realm, BasicInfo basicInfo) {
        try {
            realm.beginTransaction();
            RealmBasicInfo realmBasicInfo = realm.createObject(RealmBasicInfo.class);
            realmBasicInfo.setFname(basicInfo.getFname());
            realmBasicInfo.setLname(basicInfo.getLname());
            realmBasicInfo.setEmail(basicInfo.getEmail());
            realmBasicInfo.setReg_card_path(basicInfo.getReg_card_path());
            realmBasicInfo.setEmailvalidation(basicInfo.getEmailvalidation());
            realmBasicInfo.setEmailVerified(basicInfo.getEmail_verify());
            realmBasicInfo.setMobileVerified(basicInfo.getPhone_verify());
            realmBasicInfo.setOverAllExperience(basicInfo.getOverAllExperience());

            realmBasicInfo.setDoc_id(basicInfo.getDoc_id());
            realmBasicInfo.setSplty(basicInfo.getSplty());
            realmBasicInfo.setSubSpeciality("");
            realmBasicInfo.setProfile_pic_path("");
            realmBasicInfo.setPhone_num(basicInfo.getPhone_num());
            realmBasicInfo.setPhone_num_visibility(basicInfo.getPhone_num_visibility());
            realmBasicInfo.setEmail_visibility(basicInfo.getEmail_visibility());


            realmBasicInfo.setRememberMe(false);
            realmBasicInfo.setPsswd("");

            realmBasicInfo.setAbout_me("");
            realmBasicInfo.setWebsite("");
            realmBasicInfo.setBlog_page("");
            realmBasicInfo.setFb_page("");
            realmBasicInfo.setDocProfileURL(basicInfo.getDocProfileURL());
            realmBasicInfo.setDocProfilePdfURL(basicInfo.getDocProfilePdfURL());
            realmBasicInfo.setTot_contacts(0);
            realmBasicInfo.setTot_groups(0);
            realmBasicInfo.setTot_caserooms(0);

            realmBasicInfo.setQb_login("");
            realmBasicInfo.setQb_userid(1);
            realmBasicInfo.setQb_hidden_dialog_id("");

            realmBasicInfo.setUser_salutation(basicInfo.getUserSalutation());
            realmBasicInfo.setUser_type_id(basicInfo.getUserType());

            realm.commitTransaction();
            return true;
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
            return false;

        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
            return false;
        }
    }

    public void insertRestoreBasicInfo(Realm realm, BasicInfo basicInfo) {
        try {
            realm.beginTransaction();
            RealmBasicInfo realmBasicInfo = realm.createObject(RealmBasicInfo.class);
            realmBasicInfo.setFname(basicInfo.getFname());
            realmBasicInfo.setLname(basicInfo.getLname());
            realmBasicInfo.setEmail(basicInfo.getEmail());
            realmBasicInfo.setReg_card_path(basicInfo.getReg_card_path());
            realmBasicInfo.setEmailvalidation(basicInfo.getEmailvalidation());
            realmBasicInfo.setOverAllExperience(basicInfo.getOverAllExperience());

            realmBasicInfo.setDoc_id(basicInfo.getDoc_id());
            realmBasicInfo.setSplty(basicInfo.getSplty());
            realmBasicInfo.setProfile_pic_path(basicInfo.getProfile_pic_path());
            realmBasicInfo.setPhone_num(basicInfo.getPhone_num());
            realmBasicInfo.setPhone_num_visibility(basicInfo.getPhone_num_visibility());
            realmBasicInfo.setEmail_visibility(basicInfo.getEmail_visibility());
            realmBasicInfo.setSubSpeciality(basicInfo.getSubSpeciality());


            realmBasicInfo.setRememberMe(false);
            realmBasicInfo.setPsswd(basicInfo.getPsswd());

            realmBasicInfo.setAbout_me(basicInfo.getAbout_me());
            realmBasicInfo.setDocProfileURL(basicInfo.getDocProfileURL());
            realmBasicInfo.setWebsite(basicInfo.getWebsite());
            realmBasicInfo.setBlog_page(basicInfo.getBlog_page());
            realmBasicInfo.setFb_page(basicInfo.getFb_page());
            realmBasicInfo.setTot_contacts(basicInfo.getTot_contacts());
            realmBasicInfo.setTot_groups(basicInfo.getTot_groups());
            realmBasicInfo.setTot_caserooms(basicInfo.getTot_caserooms());

            realmBasicInfo.setQb_login(basicInfo.getQb_login());
            realmBasicInfo.setQb_userid(basicInfo.getQb_userid());
            realmBasicInfo.setQb_hidden_dialog_id(basicInfo.getQb_hidden_dialog_id());
            realmBasicInfo.setEmailVerified(basicInfo.getEmail_verify());
            realmBasicInfo.setMobileVerified(basicInfo.getPhone_verify());
            realmBasicInfo.setPic_url(basicInfo.getPic_url());
            realmBasicInfo.setDocProfilePdfURL(basicInfo.getDocProfilePdfURL());
            realmBasicInfo.setUserUUID(basicInfo.getUserUUID());
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updateUserid(Realm realm, int i, String fname, String lname, String email, String phNum, String userUUID, JSONObject data) {
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setDoc_id(i);
                hallos.get(0).setFname(fname);
                hallos.get(0).setLname(lname);
                if (!email.isEmpty()) {
                    hallos.get(0).setEmail(email);
                }
                if (!phNum.isEmpty()) {
                    hallos.get(0).setPhone_num(phNum);
                }
                if (!userUUID.isEmpty()) {
                    hallos.get(0).setUserUUID(userUUID);
                }
            } else {
                RealmBasicInfo realmBasicInfo = realm.createObject(RealmBasicInfo.class);
                realmBasicInfo.setFname(fname);
                realmBasicInfo.setLname(lname);
                realmBasicInfo.setEmail(email);
                realmBasicInfo.setReg_card_path("");
                realmBasicInfo.setEmailvalidation("true");

                realmBasicInfo.setDoc_id(i);
                realmBasicInfo.setSplty("");
                realmBasicInfo.setProfile_pic_path("");
                realmBasicInfo.setPhone_num(phNum);
                List<String> visibilityList = new ArrayList<String>(Arrays.asList(c.getResources().getStringArray(R.array.number_visibility_arrays_for_srever)));
                if (visibilityList.get(0).equals(data.optString(RestUtils.TAG_CNNTMUNVIS))) {
                    realmBasicInfo.setPhone_num_visibility(0);

                } else if (visibilityList.get(1).equals(data.optString(RestUtils.TAG_CNNTMUNVIS))) {
                    realmBasicInfo.setPhone_num_visibility(1);

                } else if (visibilityList.get(2).equals(data.optString(RestUtils.TAG_CNNTMUNVIS))) {
                    realmBasicInfo.setPhone_num_visibility(2);
                }
                if (visibilityList.get(0).equals(data.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                    realmBasicInfo.setEmail_visibility(0);

                } else if (visibilityList.get(1).equals(data.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                    realmBasicInfo.setEmail_visibility(1);

                } else if (visibilityList.get(2).equals(data.optString(RestUtils.TAG_CNNTEMAILVIS))) {
                    realmBasicInfo.setEmail_visibility(2);
                }
                realmBasicInfo.setRememberMe(false);
                realmBasicInfo.setPsswd("");

                realmBasicInfo.setAbout_me("");
                realmBasicInfo.setWebsite("");
                realmBasicInfo.setBlog_page("");
                realmBasicInfo.setFb_page("");
                realmBasicInfo.setTot_contacts(0);
                realmBasicInfo.setTot_groups(0);
                realmBasicInfo.setTot_caserooms(0);

                realmBasicInfo.setQb_login("");
                realmBasicInfo.setQb_userid(1);
                realmBasicInfo.setQb_hidden_dialog_id("");
                if (!userUUID.isEmpty()) {
                    realmBasicInfo.setUserUUID(userUUID);
                }
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateUserid " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " updateUserid" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    /**
     * We call this method to get the doc_id of the current user who logged in.
     * As there will be only one user, we are accessing the 0th position from database.
     *
     * @param realm
     * @return Doc_id
     */
    public int getDoc_id(Realm realm) {
        int i = 0;
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                i = hallos.get(0).getDoc_id();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        }
        return i;
    }


    public String getDoc_EmailId(Realm realm) {
        String emailId = "";
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                emailId = hallos.get(0).getEmail();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        }
        return emailId;
    }

    public String getUserPhoneNumber(Realm realm) {
        String phNum = "";
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                phNum = hallos.get(0).getPhone_num();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        }
        return phNum;
    }

    public String getDoc_name(Realm realm) {
        String flname = "";
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                flname = hallos.get(0).getFname() + " " + hallos.get(0).getLname();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        }
        return flname;
    }

    public String getDocSalutation(Realm realm) {
        String salutation = "";
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                salutation = hallos.get(0).getUser_salutation();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        }
        return salutation;
    }



    public int getQB_user_id(Realm realm) {
        int i = 0;
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                i = hallos.get(0).getQb_userid();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " getQB_user_id" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getQB_user_id" + e);
            e.printStackTrace();
        }
        return i;
    }
    public String getUserUUID(Realm realm) {
        String UUID = "";
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                UUID = hallos.get(0).getUserUUID();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        }
        return UUID;
    }


    public String getDocSpeciality(Realm realm) {
        String speciality = "";
        try {
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                speciality = hallos.get(0).getSplty();
            }
        } catch (RealmException e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getDoc_id" + e);
            e.printStackTrace();
        }
        return speciality;
    }

    public void updateBasicInfo(Realm realm, BasicInfo basicInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setFname(basicInfo.getFname());
                hallos.get(0).setLname(basicInfo.getLname());
                hallos.get(0).setAbout_me(basicInfo.getAbout_me());
                hallos.get(0).setSpecificAsk(basicInfo.getSpecificAsk());
                hallos.get(0).setSplty(basicInfo.getSplty());
                hallos.get(0).setPic_name(basicInfo.getPic_name());
                hallos.get(0).setPic_url(basicInfo.getPic_url());
                if (basicInfo.getProfile_pic_path() != null) {
                    hallos.get(0).setProfile_pic_path(basicInfo.getProfile_pic_path());
                }
                hallos.get(0).setSubSpeciality(basicInfo.getSubSpeciality());
                hallos.get(0).setUser_salutation(basicInfo.getUserSalutation());
                hallos.get(0).setUser_type_id(basicInfo.getUserType());
                hallos.get(0).setFeedCount(basicInfo.getFeedCount());
                hallos.get(0).setLikesCount(basicInfo.getLikeCount());
                hallos.get(0).setCommentsCount(basicInfo.getCommentCount());
                hallos.get(0).setShareCount(basicInfo.getShareCount());
                hallos.get(0).setFollowersCount(basicInfo.getFollowersCount());
                hallos.get(0).setFollowingCount(basicInfo.getFollowingCount());
                if (basicInfo.getUserUUID() != null && !basicInfo.getUserUUID().isEmpty()) {
                    hallos.get(0).setUserUUID(basicInfo.getUserUUID());
                }
                if (basicInfo.getDocProfilePdfURL() != null && !basicInfo.getDocProfilePdfURL().isEmpty()) {
                    hallos.get(0).setDocProfilePdfURL(basicInfo.getDocProfilePdfURL());
                }
                hallos.get(0).setOverAllExperience(basicInfo.getOverAllExperience());
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updateAboutmeBasicInfo(Realm realm, BasicInfo basicInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setWebsite(basicInfo.getWebsite());
                hallos.get(0).setBlog_page(basicInfo.getBlog_page());
                hallos.get(0).setFb_page(basicInfo.getFb_page());
                hallos.get(0).setLinkedInPg(basicInfo.getLinkedInPg());
                hallos.get(0).setTwitterPg(basicInfo.getTwitterPg());
                hallos.get(0).setInstagramPg(basicInfo.getInstagramPg());
                hallos.get(0).setPhone_num_visibility(basicInfo.getPhone_num_visibility());
                hallos.get(0).setEmail_visibility(basicInfo.getEmail_visibility());
                hallos.get(0).setMobileVerified(basicInfo.getPhone_verify());
                hallos.get(0).setEmailVerified(basicInfo.getEmail_verify());
                hallos.get(0).setEmail(basicInfo.getEmail());
                hallos.get(0).setPhone_num(basicInfo.getPhone_num());
                hallos.get(0).setDocProfileURL(basicInfo.getDocProfileURL());
                hallos.get(0).setDocProfilePdfURL(basicInfo.getDocProfilePdfURL());
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updateSpltyBasicInfo(Realm realm, BasicInfo basicInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setSplty(basicInfo.getSplty());
                hallos.get(0).setSubSpeciality(basicInfo.getSubSpeciality());
                hallos.get(0).setPic_name(basicInfo.getPic_name());
                hallos.get(0).setPic_url(basicInfo.getPic_url());
                hallos.get(0).setProfile_pic_path(basicInfo.getProfile_pic_path());
                hallos.get(0).setOverAllExperience(basicInfo.getOverAllExperience());
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    public boolean isProfessionalInfoExists(Realm realm, int profID) {
        boolean isExists = false;
        try {
            RealmResults<RealmProfessionalInfo> hallos = realm.where(RealmProfessionalInfo.class).equalTo(RestUtils.TAG_PROF_ID, profID).findAll();
            if (hallos.size() > 0) {
                isExists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExists;
    }




    public void insertAcademic(Realm realm, AcademicInfo academicInfo) {
        try {
            realm.beginTransaction();
            RealmAcademicInfo realmAcademicInfo = realm.createObject(RealmAcademicInfo.class, academicInfo.getAcad_id());
            realmAcademicInfo.setDegree(academicInfo.getDegree());
            realmAcademicInfo.setUniversity(academicInfo.getUniversity());
            realmAcademicInfo.setCollege(academicInfo.getCollege());
            realmAcademicInfo.setPassing_year(academicInfo.getPassing_year());
            realmAcademicInfo.setCurrently_pursuing(academicInfo.isCurrently_pursuing());
            realm.commitTransaction();

        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

    }

    public void updateAcademicInfo(Realm realm, AcademicInfo academicInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmAcademicInfo> hallos = realm.where(RealmAcademicInfo.class).equalTo("acad_id", academicInfo.getAcad_id()).findAll();
//            hallos.get(0).setAcad_id(academicInfo.getAcad_id());
            hallos.get(0).setDegree(academicInfo.getDegree());
            hallos.get(0).setUniversity(academicInfo.getUniversity());
            hallos.get(0).setCollege(academicInfo.getCollege());
            hallos.get(0).setPassing_year(academicInfo.getPassing_year());
            hallos.get(0).setCurrently_pursuing(academicInfo.isCurrently_pursuing());
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void deleteAcademicInfo(Realm realm, int acadId) {
        try {
            realm.beginTransaction();
            RealmResults<RealmAcademicInfo> hallos = realm.where(RealmAcademicInfo.class).equalTo("acad_id", acadId).findAll();
            hallos.deleteFromRealm(0);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void insertAreaOfInterest(Realm realm, AreasOfInterest areasOfInterest) {
        try {
            realm.beginTransaction();
            RealmAreasOfInterestInfo realmAreasOfInterestInfo = realm.createObject(RealmAreasOfInterestInfo.class, areasOfInterest.getInterestId());
            realmAreasOfInterestInfo.setInterestName(areasOfInterest.getInterestName());
            realm.commitTransaction();

        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

    }

    public void updateAreaOfInterestInfo(Realm realm, AreasOfInterest areasOfInterest) {
        try {
            realm.beginTransaction();
            RealmResults<RealmAreasOfInterestInfo> hallos = realm.where(RealmAreasOfInterestInfo.class).equalTo("interestId", areasOfInterest.getInterestId()).findAll();
            hallos.get(0).setInterestName(areasOfInterest.getInterestName());
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void deleteAreaOfInterestInfo(Realm realm, int interestId) {
        try {
            realm.beginTransaction();
            RealmResults<RealmAreasOfInterestInfo> hallos = realm.where(RealmAreasOfInterestInfo.class).equalTo("interestId", interestId).findAll();
            hallos.deleteFromRealm(0);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public List<AcademicInfo> getAcademicinfo(Realm realm) {
        List<AcademicInfo> academicInfolist = new ArrayList<AcademicInfo>();
        try {

            String[] fieldNames = {"currently_pursuing", "passing_year", "acad_id"};
            Sort sort[] = {Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING};
            RealmResults<RealmAcademicInfo> hallos = realm.where(RealmAcademicInfo.class).sort(fieldNames, sort).findAll();
            //hallos.sort(fieldNames,sort);
            for (int i = 0; i < hallos.size(); i++) {
                AcademicInfo academicInfo = new AcademicInfo();
                academicInfo.setAcad_id(hallos.get(i).getAcad_id());
                academicInfo.setDegree(hallos.get(i).getDegree());
                academicInfo.setUniversity(hallos.get(i).getUniversity());
                academicInfo.setCollege(hallos.get(i).getCollege());
                academicInfo.setPassing_year(hallos.get(i).getPassing_year());
                academicInfo.setCurrently_pursuing(hallos.get(i).isCurrently_pursuing());
                academicInfolist.add(academicInfo);
            }
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        }

        return academicInfolist;
    }

    public List<ProfessionalInfo> getProfessionalInfo(Realm realm) {

        List<ProfessionalInfo> infoList = new ArrayList<ProfessionalInfo>();
        try {
            RealmResults<RealmProfessionalInfo> hallos = realm.where(RealmProfessionalInfo.class).equalTo("working_here", false).findAll();
            hallos = hallos.sort(new String[]{"working_here", "end_date", "start_date", "prof_id"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING});
            //new boolean[]{false, false, false, false});
            for (int i = 0; i < hallos.size(); i++) {
                ProfessionalInfo professionalInfo = new ProfessionalInfo();
                professionalInfo.setProf_id(hallos.get(i).getProf_id());
                professionalInfo.setWorkplace(hallos.get(i).getWorkplace());
                professionalInfo.setDesignation(hallos.get(i).getDesignation());
                professionalInfo.setStart_date(hallos.get(i).getStart_date());
                professionalInfo.setStartTime(hallos.get(i).getStartTime());
                professionalInfo.setEnd_date(hallos.get(i).getEnd_date());
                professionalInfo.setEndTime(hallos.get(i).getEndTime());
                professionalInfo.setWorking_here(hallos.get(i).isWorking_here());
                professionalInfo.setWorkOptions(hallos.get(i).getWorkOptions());
                professionalInfo.setAvailableDays(hallos.get(i).getAvailableDays());
                professionalInfo.setLocation(hallos.get(i).getLocation());
                professionalInfo.setShowOncard(hallos.get(i).isShowOncard());
                infoList.add(professionalInfo);
            }
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        }
        return infoList;

    }

    public List<ProfessionalInfo> getProfessionalInfoOfWorkingHere(Realm realm) {
        List<ProfessionalInfo> infoList = new ArrayList<ProfessionalInfo>();
        try {
            RealmResults<RealmProfessionalInfo> hallos = realm.where(RealmProfessionalInfo.class).equalTo("working_here", true).findAll();
            hallos = hallos.sort(new String[]{"working_here", "end_date", "start_date", "prof_id"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING});
            for (int i = 0; i < hallos.size(); i++) {
                ProfessionalInfo professionalInfo = new ProfessionalInfo();
                professionalInfo.setProf_id(hallos.get(i).getProf_id());
                professionalInfo.setWorkplace(hallos.get(i).getWorkplace());
                professionalInfo.setDesignation(hallos.get(i).getDesignation());
                professionalInfo.setStart_date(hallos.get(i).getStart_date());
                professionalInfo.setStartTime(hallos.get(i).getStartTime());
                professionalInfo.setEndTime(hallos.get(i).getEndTime());
                professionalInfo.setAvailableDays(hallos.get(i).getAvailableDays());
                professionalInfo.setWorkOptions(hallos.get(i).getWorkOptions());
                professionalInfo.setEnd_date(hallos.get(i).getEnd_date());
                professionalInfo.setWorking_here(hallos.get(i).isWorking_here());
                professionalInfo.setLocation(hallos.get(i).getLocation());
                professionalInfo.setShowOncard(hallos.get(i).isShowOncard());
                infoList.add(professionalInfo);
            }
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        }
        return infoList;
    }

    public ProfessionalInfo getProfessionalInfoOfShowoncard(Realm realm) {
        ProfessionalInfo professionalInfo = new ProfessionalInfo();
        try {
            RealmResults<RealmProfessionalInfo> hallos = realm.where(RealmProfessionalInfo.class).equalTo("showOncard", true).findAll();
            for (int i = 0; i < hallos.size(); i++) {
                professionalInfo.setProf_id(hallos.get(i).getProf_id());
                professionalInfo.setWorkplace(hallos.get(i).getWorkplace());
                professionalInfo.setDesignation(hallos.get(i).getDesignation());
                professionalInfo.setStart_date(hallos.get(i).getStart_date());
                professionalInfo.setEnd_date(hallos.get(i).getEnd_date());
                professionalInfo.setStartTime(hallos.get(i).getStartTime());
                professionalInfo.setEndTime(hallos.get(i).getEndTime());
                professionalInfo.setAvailableDays(hallos.get(i).getAvailableDays());
                professionalInfo.setWorkOptions(hallos.get(i).getWorkOptions());
                professionalInfo.setWorking_here(hallos.get(i).isWorking_here());
                professionalInfo.setLocation(hallos.get(i).getLocation());
                professionalInfo.setShowOncard(hallos.get(i).isShowOncard());
            }
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        }
        return professionalInfo;
    }

    public void deleteProfession(Realm realm, int profId) {
        try {
            realm.beginTransaction();
            RealmResults<RealmProfessionalInfo> hallos = realm.where(RealmProfessionalInfo.class).equalTo("prof_id", profId).findAll();
            hallos.deleteFromRealm(0);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void insertProfession(Realm realm, ProfessionalInfo professionalInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmProfessionalInfo> hallos = realm.where(RealmProfessionalInfo.class).findAll();
            //set remaining all showoncard false
            if (professionalInfo.isShowOncard()) {
                for (int i = 0; i < hallos.size(); i++) {
                    hallos.get(i).setShowOncard(false);
                }
            }
            realm.commitTransaction();
            realm.beginTransaction();
            RealmProfessionalInfo realmProfessionalInfo = realm.createObject(RealmProfessionalInfo.class, professionalInfo.getProf_id());
            realmProfessionalInfo.setWorkplace(professionalInfo.getWorkplace());
            realmProfessionalInfo.setDesignation(professionalInfo.getDesignation());
            realmProfessionalInfo.setStart_date(professionalInfo.getStart_date());
            realmProfessionalInfo.setStartTime(professionalInfo.getStartTime());
            realmProfessionalInfo.setEnd_date(professionalInfo.getEnd_date());
            realmProfessionalInfo.setEndTime(professionalInfo.getEndTime());
            realmProfessionalInfo.setWorking_here(professionalInfo.isWorking_here());
            realmProfessionalInfo.setWorkOptions(professionalInfo.getWorkOptions());
            realmProfessionalInfo.setAvailableDays(professionalInfo.getAvailableDays());
            realmProfessionalInfo.setLocation("" + professionalInfo.getLocation());
            realmProfessionalInfo.setShowOncard(professionalInfo.isShowOncard());
            realm.commitTransaction();

        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updateProfessionInfo(Realm realm, ProfessionalInfo professionalInfo) {
        try {
            realm.beginTransaction();

            if (professionalInfo.isShowOncard()) {
                RealmResults<RealmProfessionalInfo> hallos1 = realm.where(RealmProfessionalInfo.class).equalTo("showOncard", true).findAll();
                hallos1.get(0).setShowOncard(false);
            }

            RealmResults<RealmProfessionalInfo> hallos = realm.where(RealmProfessionalInfo.class).equalTo("prof_id", professionalInfo.getProf_id()).findAll();
//            hallos.get(0).setProf_id(professionalInfo.getProf_id());
            hallos.get(0).setWorkplace(professionalInfo.getWorkplace());
            hallos.get(0).setDesignation(professionalInfo.getDesignation());
            hallos.get(0).setStart_date(professionalInfo.getStart_date());
            hallos.get(0).setStartTime(professionalInfo.getStartTime());
            hallos.get(0).setEnd_date(professionalInfo.getEnd_date());
            hallos.get(0).setEndTime(professionalInfo.getEndTime());
            hallos.get(0).setWorking_here(professionalInfo.isWorking_here());
            hallos.get(0).setWorkOptions(professionalInfo.getWorkOptions());
            hallos.get(0).setAvailableDays(professionalInfo.getAvailableDays());
            hallos.get(0).setLocation("" + professionalInfo.getLocation());
            hallos.get(0).setShowOncard(professionalInfo.isShowOncard());
            hallos.get(0).setLocation("" + professionalInfo.getLocation());
            hallos.get(0).setShowOncard(professionalInfo.isShowOncard());
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public boolean isWorkTimingExists(Realm realm, ProfessionalInfo professionalInfo, boolean isUpdate) {
        boolean flag = false;
        try {
            String selectedDays = professionalInfo.getAvailableDays();
            long startTime = professionalInfo.getStartTime();
            long endTime = professionalInfo.getEndTime();
            if (startTime > endTime)
                endTime += 86400000; // add one day milliseconds
            if (selectedDays.isEmpty())
                return flag;
            RealmQuery<RealmProfessionalInfo> query = null;
            RealmQuery<RealmProfessionalInfo> tempQuery = null;
            if (isUpdate) {
                query = realm.where(RealmProfessionalInfo.class).equalTo("working_here", true).notEqualTo("prof_id", professionalInfo.getProf_id());
                tempQuery = realm.where(RealmProfessionalInfo.class).equalTo("working_here", true)
                        .notEqualTo("prof_id", professionalInfo.getProf_id());

            } else {
                query = realm.where(RealmProfessionalInfo.class).equalTo("working_here", true);
                tempQuery = realm.where(RealmProfessionalInfo.class).equalTo("working_here", true);
            }
            RealmResults<RealmProfessionalInfo> results = tempQuery.findAll();
            RealmResults<RealmProfessionalInfo> subResult1 = query.findAll();
//            int resultsSize = subResult1.size();
            String[] days = selectedDays.split(",");
//            for (int i = 0; i < resultsSize; i++) {
//            for (String dayID : days) {
//                query = query.contains("availableDays", dayID);
//                tempQuery = tempQuery.contains("availableDays", dayID);
//            }
//            }
            // RealmResults<RealmProfessionalInfo> subResult2 = tempQuery.findAll();
            query.beginGroup().beginGroup().greaterThanOrEqualTo("startTime", startTime).and().lessThanOrEqualTo("startTime", endTime).endGroup()
                       .or().
                       beginGroup().greaterThanOrEqualTo("endTime", startTime).and().lessThanOrEqualTo("endTime", endTime).endGroup().endGroup();
//            query = query
//                    .between("startTime", startTime, endTime)
//                    .or()
//                    .between("endTime", startTime, endTime);
            RealmResults<RealmProfessionalInfo> result0 = query.findAll();
            if (result0.size() > 0) {
                for (RealmProfessionalInfo info :
                        result0) {
                    for (String dayID : days) {
                        if (info.getAvailableDays().contains(dayID)) {
                            flag = true;
                            break;
                        }
                    }
                }
//                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public List<EventInfo> getEventInfo(Realm realm) {

        List<EventInfo> realmEventInfolist = new ArrayList<EventInfo>();
        try {
            RealmResults<RealmEventsInfo> hallos = realm.where(RealmEventsInfo.class).findAll();
            hallos = hallos.sort(new String[]{"eventId"}, new Sort[]{Sort.ASCENDING});
            //new boolean[]{false, false, false, false});
            for (int i = 0; i < hallos.size(); i++) {
                EventInfo eventInfo = new EventInfo();
                eventInfo.setEventTitle(hallos.get(i).getEventTitle());
                eventInfo.setEventId(hallos.get(i).getEventId());
                eventInfo.setStartDate(hallos.get(i).getStartDate());
                eventInfo.setEndDate(hallos.get(i).getEndDate());
                eventInfo.setLocation(hallos.get(i).getLocation());
                realmEventInfolist.add(eventInfo);
            }
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        }
        return realmEventInfolist;
    }

    public List<EventInfo> getEventCalendarInfo(Realm realm) {
        List<EventInfo> realmEventCalendarInfolist = new ArrayList<EventInfo>();
        try {
            RealmResults<RealmEventsInfo> hallos = realm.where(RealmEventsInfo.class).findAll();
            for (int i = 0; i < hallos.size(); i++) {
                EventInfo eventInfo = new EventInfo();
                eventInfo.setEventId(hallos.get(i).getEventId());
                eventInfo.setEventTitle(hallos.get(i).getEventTitle());
                eventInfo.setLocation(hallos.get(i).getLocation());
                eventInfo.setStartDate(hallos.get(i).getStartDate());
                eventInfo.setEndDate(hallos.get(i).getEndDate());
                realmEventCalendarInfolist.add(eventInfo);
            }
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
        }
        return realmEventCalendarInfolist;
    }

    public void insertEventCalendar(Realm realm, EventInfo eventInfo) {
        try {
            realm.beginTransaction();

            RealmEventsInfo realmEventsInfo = realm.createObject(RealmEventsInfo.class, eventInfo.getEventId());
            realmEventsInfo.setEventTitle(eventInfo.getEventTitle());
            realmEventsInfo.setLocation("" + eventInfo.getLocation());
            realmEventsInfo.setStartDate(eventInfo.getStartDate());
            realmEventsInfo.setEndDate(eventInfo.getEndDate());
            realm.commitTransaction();

        } catch (RealmException e) {
            Log.d("RealmException", "insert Login credentials" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updateEventInfo(Realm realm, EventInfo eventInfo) {
        try {
            realm.beginTransaction();

            RealmResults<RealmEventsInfo> hallos = realm.where(RealmEventsInfo.class).equalTo("eventId", eventInfo.getEventId()).findAll();
            hallos.get(0).setEventTitle(eventInfo.getEventTitle());
            hallos.get(0).setLocation("" + eventInfo.getLocation());
            hallos.get(0).setStartDate(eventInfo.getStartDate());
            hallos.get(0).setEndDate(eventInfo.getEndDate());
            realm.commitTransaction();

        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void deleteEventCalendar(Realm realm, int eventId) {
        try {
            realm.beginTransaction();
            RealmResults<RealmEventsInfo> hallos = realm.where(RealmEventsInfo.class).equalTo("eventId", eventId).findAll();
            hallos.deleteFromRealm(0);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public HashMap<String, String> getLogin(Realm realm) {

        HashMap<String, String> login_map = new HashMap<>();

        try {
            realm.beginTransaction();
            RealmResults<RealmLogin> hallos = realm.where(RealmLogin.class).findAll();
            if (hallos.size() > 0) {
                login_map.put("qb_user_login", hallos.get(0).getQb_user_login());
                login_map.put("qb_user_password", hallos.get(0).getQb_user_password());
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();

            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
        return login_map;
    }


    public void insertNotification(Realm realm, ContactsInfo notificationsInfo) {
        try {
            realm.beginTransaction();
            RealmNotifications notificationsCache = realm.createObject(RealmNotifications.class, notificationsInfo.getNotification_id());
            notificationsCache.setNotification_type(notificationsInfo.getNotification_type());
            notificationsCache.setDoc_name(notificationsInfo.getName());
            notificationsCache.setDoc_id(notificationsInfo.getDoc_id());
            notificationsCache.setDoc_speciality(notificationsInfo.getSpeciality());
            notificationsCache.setDoc_sub_speciality(notificationsInfo.getSubSpeciality());
            notificationsCache.setDoc_location(notificationsInfo.getLocation());
            notificationsCache.setDoc_workplace(notificationsInfo.getWorkplace());
            notificationsCache.setDoc_email(notificationsInfo.getEmail());
            notificationsCache.setDoc_phno(notificationsInfo.getPhno());
            notificationsCache.setDoc_email_vis("SHOW_ALL");
            notificationsCache.setDoc_phno_vis("SHOW_ALL");
            notificationsCache.setReadStatus(false);
            notificationsCache.setDoc_qb_user_id(notificationsInfo.getQb_userid());
            notificationsCache.setDoc_pic(notificationsInfo.getPic_name());
            notificationsCache.setDoc_pic_url(notificationsInfo.getPic_url());
            notificationsCache.setTime(notificationsInfo.getTime());
            notificationsCache.setMessage(notificationsInfo.getMessage());
            notificationsCache.setAckStatus(0);
            notificationsCache.setUser_salutation(notificationsInfo.getUserSalutation());
            notificationsCache.setUser_type_id(notificationsInfo.getUserTypeId());
            realm.commitTransaction();

        } catch (RealmException re) {
            Log.d("RealmException", "insert Notification" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insert Notification" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }




    public void deleteNetworkNotification(Realm realm, String id) {

        try {
            realm.beginTransaction();
            RealmResults<RealmNotificationInfo> hallos = realm.where(RealmNotificationInfo.class).equalTo("notificationID", id).findAll();
            hallos.deleteFromRealm(0);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " deleteCaseRoomNotification " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception re) {
            Log.d("RealmException", " deleteCaseRoomNotification " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        }
    }
    public boolean checkNotificationExists(Realm realm, String id) {
        try {
            RealmResults<RealmNotifications> hallos = realm.where(RealmNotifications.class).equalTo("notification_id", id).findAll();
            if (hallos.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkGroupNotificationExists(Realm realm, String id) {
        try {
            RealmResults<RealmGroupNotifications> hallos = realm.where(RealmGroupNotifications.class).contains("group_notification_id", id).findAll();
            if (hallos.get(0).getGroup_notification_id().equals(id)) {
                return true;
            }
        } catch (RealmException re) {
            Log.d("RealmException", " checkGroupNotificationExists " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception re) {
            Log.d("RealmException", " checkGroupNotificationExists " + re);
            re.printStackTrace();
            realm.cancelTransaction();

        }
        return false;
    }

    public void deleteNotification(Realm realm, String id, String tag) {

        try {
            RealmResults<RealmNotificationInfo> hallos;
            realm.beginTransaction();
            if (tag.contains(RestUtils.TAG_DOC_ID)) {
                hallos = realm.where(RealmNotificationInfo.class).contains("notifyData","\"user_id\":"+Integer.parseInt(id)).findAll();
                //hallos = realm.where(RealmNotifications.class).equalTo(RestUtils.TAG_DOC_ID, Integer.parseInt(id)).findAll();
            } else {
                hallos = realm.where(RealmNotificationInfo.class).equalTo("notification_id", id).findAll();
            }
            hallos.deleteFromRealm(0);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " insertMessage " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception re) {
            Log.d("RealmException", " insertMessage " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        }
    }


    public List<String> getAckNotification(Realm realm) {
        List<String> ids = new ArrayList<>();
        try {
            RealmResults<RealmNotifications> hallos = realm.where(RealmNotifications.class).equalTo("ackStatus", 0).findAll();
            RealmResults<RealmGroupNotifications> grouphallos = realm.where(RealmGroupNotifications.class).equalTo("group_ackStatus", 0).findAll();
            RealmResults<RealmCaseRoomNotifications> crhallos = realm.where(RealmCaseRoomNotifications.class).equalTo("caseroom_ack_status", 0).findAll();

            if (hallos.size() != 0) {
                for (int i = 0; i < hallos.size(); i++)
                    ids.add(hallos.get(i).getNotification_id());
            }
            if (grouphallos.size() != 0) {
                for (int i = 0; i < grouphallos.size(); i++)
                    ids.add(grouphallos.get(i).getGroup_notification_id());
            }
            if (crhallos.size() != 0) {
                for (int i = 0; i < crhallos.size(); i++)
                    ids.add(crhallos.get(i).getCaseroom_notification_id());
            }
            return ids;
        } catch (RealmException re) {
            Log.d("RealmException", " insertMessage " + re);
            re.printStackTrace();
        } catch (Exception re) {
            Log.d("RealmException", " insertMessage " + re);
            re.printStackTrace();
        }
        return ids;
    }


    public RealmResults<RealmNotifications> getNotificationDB(Realm realm) {
//        ArrayList<ContactsInfo> notificationsList = new ArrayList<>();

        try {
            RealmResults<RealmNotifications> hallos = realm.where(RealmNotifications.class).equalTo("notification_type", NotificationType.INVITE_USER_FOR_CONNECT.name()).or().equalTo("readStatus", false).sort("time", Sort.DESCENDING).findAll();
            return hallos;
        } catch (RealmException re) {
            Log.d("RealmException", " getNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getNotificationData " + e);
        }
        return null;
//        return notificationsList;
    }


    public RealmResults<RealmNotifications> getPendingNotificationDB(Realm realm) {

        try {
            RealmResults<RealmNotifications> hallos = realm.where(RealmNotifications.class).equalTo("notification_type", NotificationType.INVITE_USER_FOR_CONNECT.name()).findAll();
            hallos = hallos.sort("time", Sort.DESCENDING);
            return hallos;
        } catch (RealmException re) {
            Log.d("RealmException", " getNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getNotificationData " + e);
        }
        return null;
    }

    public ArrayList<ContactsInfo> getPendingNotification(Realm realm) {
        ArrayList<ContactsInfo> notificationsList = new ArrayList<>();

        try {
            RealmResults<RealmNotifications> hallos = realm.where(RealmNotifications.class).equalTo("notification_type", NotificationType.INVITE_USER_FOR_CONNECT.name()).findAll();
            hallos = hallos.sort("time", Sort.DESCENDING);
            for (int i = 0; i < hallos.size(); i++) {
                ContactsInfo notify_contactsInfo = new ContactsInfo();
                notify_contactsInfo.setNotification_id(hallos.get(i).getNotification_id());
                notify_contactsInfo.setNotification_type(hallos.get(i).getNotification_type());
                notify_contactsInfo.setName(hallos.get(i).getDoc_name());
                notify_contactsInfo.setDoc_id(hallos.get(i).getDoc_id());
                notify_contactsInfo.setQb_userid(hallos.get(i).getDoc_qb_user_id());
                notify_contactsInfo.setLocation(hallos.get(i).getDoc_location());
                notify_contactsInfo.setSpeciality(hallos.get(i).getDoc_speciality());
                notify_contactsInfo.setSubSpeciality(hallos.get(i).getDoc_sub_speciality());
                notify_contactsInfo.setWorkplace(hallos.get(i).getDoc_workplace());
                notify_contactsInfo.setPic_name(hallos.get(i).getDoc_pic());
                notify_contactsInfo.setPic_url(hallos.get(i).getDoc_pic_url());
                notify_contactsInfo.setEmail(hallos.get(i).getDoc_email());
                notify_contactsInfo.setPhno(hallos.get(i).getDoc_phno());
                notify_contactsInfo.setEmail_vis(hallos.get(i).getDoc_email_vis());
                notify_contactsInfo.setPhno_vis(hallos.get(i).getDoc_phno_vis());
                notify_contactsInfo.setMessage(hallos.get(i).getMessage());
                notify_contactsInfo.setTime(hallos.get(i).getTime());
                notify_contactsInfo.setReadStatus(hallos.get(i).isReadStatus());
                notify_contactsInfo.setUserSalutation(hallos.get(i).getUser_salutation());
                notify_contactsInfo.setUserTypeId(hallos.get(i).getUser_type_id());
                notificationsList.add(notify_contactsInfo);
            }
            return notificationsList;
        } catch (RealmException re) {
            Log.d("RealmException", " getNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getNotificationData " + e);
        }
        return notificationsList;
    }

    public void updateNotificationReadStatus(Realm realm, String notification_id) {
        try {
            realm.beginTransaction();
            RealmResults<RealmNotifications> hallos = realm.where(RealmNotifications.class).equalTo("notification_id", notification_id).findAll();
            if (hallos.size() > 0) {
                if (hallos.get(0).isReadStatus() == false)
                    hallos.get(0).setReadStatus(true);
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " getNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getNotificationData " + e);
        }
    }

    public void updateConnectsNotificationReadStatus(String notification_id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<RealmNotificationInfo> hallos = realm1.where(RealmNotificationInfo.class).equalTo("notificationID", notification_id).findAll();
            if (hallos.size() > 0) {
                if (hallos.get(0).isReadStatus() == false)
                    hallos.get(0).setReadStatus(true);
            }
        });

        if (!realm.isClosed()) {
            realm.close();
        }
    }




    public void updateDialog_memaddedDate(Realm realm, long mem_added_date, String dialogId) {
        try {
            realm.beginTransaction();
            RealmResults<RealmQBGroupDialog> hallos = realm.where(RealmQBGroupDialog.class).equalTo("dialog_id", dialogId).findAll();
            if (hallos.size() > 0) {
                hallos.get(0).setMember_added_date(mem_added_date);
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " updateMyContactsStatus" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "updateMyContactsStatus" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public int getGroupNotificationCount(Realm realm) {
        int count = 0;
        try {
            RealmResults<RealmGroupNotifications> hallos = realm.where(RealmGroupNotifications.class).notEqualTo("count_status", 1).findAll();
            count = hallos.size();
        } catch (RealmException re) {
            Log.d("RealmException", " getGroupNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getGroupNotificationData " + e);
        }
        return count;
    }

    public int getCRNotificationCount(Realm realm) {
        int count = 0;
        try {
            RealmResults<RealmCaseRoomNotifications> hallos = realm.where(RealmCaseRoomNotifications.class).notEqualTo("count_status", 1).findAll();
            count = hallos.size();
        } catch (RealmException re) {
            Log.d("RealmException", " getGroupNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getGroupNotificationData " + e);
        }
        return count;
    }

    public int getNotificationCount(Realm realm) {
        int count = 0;
        try {
            RealmResults<RealmNotifications> hallos = realm.where(RealmNotifications.class).equalTo("readStatus", false).findAll();
            count = hallos.size();
        } catch (RealmException re) {
            Log.d("RealmException", " getGroupNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getGroupNotificationData " + e);
        }
        return count;
    }

    public int getNotificationNetworkCount(Realm realm) {
        int count = 0;
        try {
            RealmResults<RealmNotificationInfo> hallos = realm.where(RealmNotificationInfo.class).equalTo("readStatus", false).findAll();
            count = hallos.size();
        } catch (RealmException re) {
            Log.d("RealmException", " getGroupNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getGroupNotificationData " + e);
        }
        return count;
    }

    public RealmResults<RealmGroupNotifications> getGroupNotificationDB(Realm realm) {
        try {
            RealmResults<RealmGroupNotifications> hallos = realm.where(RealmGroupNotifications.class).findAll();
            hallos = hallos.sort("group_creation_time", Sort.DESCENDING);
            return hallos;
        } catch (RealmException re) {
            Log.d("RealmException", " getGroupNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getGroupNotificationData " + e);
        }
        return null;
    }


    public RealmResults<RealmCaseRoomNotifications> getCaseRoomNotificationDB(Realm realm) {
        try {
            RealmResults<RealmCaseRoomNotifications> caseRoomNotifications = realm.where(RealmCaseRoomNotifications.class).findAll();

            return caseRoomNotifications;
        } catch (RealmException re) {
            Log.d("RealmException", " getGroupNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RealmException", " getGroupNotificationData " + e);
        }
        return null;
    }


    /**
     * Insert ProfessionalMem details
     **/
    public void insertProfessionalMemData(Realm realm, ProfessionalMembershipInfo professionalMembershipInfo) {
        try {
            realm.beginTransaction();
            RealmProfessionalMembership realmProfessionalMembership = realm.createObject(RealmProfessionalMembership.class);
            realmProfessionalMembership.setProf_mem_id(professionalMembershipInfo.getProf_mem_id());

            realmProfessionalMembership.setMembership_name(professionalMembershipInfo.getMembership_name());
            realmProfessionalMembership.setAward_id(professionalMembershipInfo.getAward_id());
            realmProfessionalMembership.setAward_name(professionalMembershipInfo.getAward_name());
            realmProfessionalMembership.setAward_year(professionalMembershipInfo.getAward_year());
            realmProfessionalMembership.setPresented_at(professionalMembershipInfo.getPresented_at());
            realmProfessionalMembership.setType(professionalMembershipInfo.getType());
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " insertMessage " + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " insertMessage" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    /**
     * Get action values from ProfessionalMem details
     **/
    public boolean getProfessionalMem(Realm realm, boolean isLessData, List<ProfessionalMembershipInfo> data) {
//        List<ProfessionalMembershipInfo> data = new ArrayList<ProfessionalMembershipInfo>();
        boolean showView = false;
        try {
            RealmResults<RealmProfessionalMembership> awardResults = realm.where(RealmProfessionalMembership.class).equalTo("type", "award").findAll();
            RealmResults<RealmProfessionalMembership> membershipResults = realm.where(RealmProfessionalMembership.class).equalTo("type", "membership").findAll();
            awardResults.sort("award_id", Sort.DESCENDING);//RealmResults.SORT_ORDER_DESCENDING);
            membershipResults.sort("prof_mem_id", Sort.DESCENDING);//RealmResults.SORT_ORDER_DESCENDING);
            int awardSize = awardResults.size();
            int membershipSize = membershipResults.size();
            if (awardSize > 2 || membershipSize > 2) {
                showView = true;
            }
            for (int i = 0; i < awardSize; i++) {
                if (isLessData && i == 2)
                    break;
                ProfessionalMembershipInfo memPublications = new ProfessionalMembershipInfo();
                memPublications.setAward_id(awardResults.get(i).getAward_id());
                memPublications.setAward_name(awardResults.get(i).getAward_name());
                memPublications.setAward_year(awardResults.get(i).getAward_year());
                memPublications.setPresented_at(awardResults.get(i).getPresented_at());
                memPublications.setProf_mem_id(awardResults.get(i).getProf_mem_id());
                memPublications.setMembership_name(awardResults.get(i).getMembership_name());
                memPublications.setType(awardResults.get(i).getType());
                data.add(memPublications);
            }
            for (int i = 0; i < membershipSize; i++) {
                if (isLessData && i == 2)
                    break;
                ProfessionalMembershipInfo memPublications = new ProfessionalMembershipInfo();
                memPublications.setProf_mem_id(membershipResults.get(i).getProf_mem_id());
                memPublications.setMembership_name(membershipResults.get(i).getMembership_name());
                memPublications.setAward_id(membershipResults.get(i).getAward_id());
                memPublications.setAward_name(membershipResults.get(i).getAward_name());
                memPublications.setAward_year(membershipResults.get(i).getAward_year());
                memPublications.setPresented_at(membershipResults.get(i).getPresented_at());
                memPublications.setType(membershipResults.get(i).getType());
                data.add(memPublications);
            }
            return showView;
        } catch (RealmException re) {
            Log.d("RealmException", " getNotificationData " + re);
            re.printStackTrace();
        } catch (Exception e) {
            Log.d("RealmException", " getNotificationData" + e);
            e.printStackTrace();
        }
        return showView;
    }


    public void deleteProfessionalMemName(Realm realm, int id, String type) {
        try {
            realm.beginTransaction();
            if (type.equalsIgnoreCase("award")) {
                RealmResults<RealmProfessionalMembership> hallos = realm.where(RealmProfessionalMembership.class).equalTo(RestUtils.TAG_AWARD_ID, id).findAll();
                hallos.deleteFromRealm(0);
            } else {
                RealmResults<RealmProfessionalMembership> hallos = realm.where(RealmProfessionalMembership.class).equalTo("prof_mem_id", id).findAll();
                hallos.deleteFromRealm(0);
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", "delete pubname" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " delete pubname" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

    }

    public void updateProfessionalMem(Realm realm, ProfessionalMembershipInfo professionalMembershipInfo, String type) {
        try {
            realm.beginTransaction();
            if (type.equalsIgnoreCase("award")) {
                RealmResults<RealmProfessionalMembership> hallos = realm.where(RealmProfessionalMembership.class).equalTo(RestUtils.TAG_AWARD_ID, professionalMembershipInfo.getAward_id()).findAll();
                hallos.get(0).setAward_name(professionalMembershipInfo.getAward_name());
                hallos.get(0).setPresented_at(professionalMembershipInfo.getPresented_at());
                hallos.get(0).setAward_year(professionalMembershipInfo.getAward_year());
                hallos.get(0).setType(professionalMembershipInfo.getType());
            } else {
                RealmResults<RealmProfessionalMembership> hallos = realm.where(RealmProfessionalMembership.class).equalTo("prof_mem_id", professionalMembershipInfo.getProf_mem_id()).findAll();
                hallos.get(0).setMembership_name(professionalMembershipInfo.getMembership_name());
                hallos.get(0).setType(professionalMembershipInfo.getType());
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateProfessionalMem " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " updateProfessionalMem" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    /**
     * First Time insert PrintPublications
     **/
    public void insertPublicationsInfo(Realm realm, PublicationsInfo publicationsInfo) {
        try {
            realm.beginTransaction();
            RealmPublications realmPublications = realm.createObject(RealmPublications.class);
            realmPublications.setPub_id(publicationsInfo.getPub_id());
            realmPublications.setTitle(publicationsInfo.getTitle());
            realmPublications.setAuthors(publicationsInfo.getAuthors());
            realmPublications.setJournal(publicationsInfo.getJournal());
            if (publicationsInfo.getWeb_page() != null) {
                realmPublications.setWeb_page(publicationsInfo.getWeb_page());
            }
            realmPublications.setType(publicationsInfo.getType());
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " insertPublicationsInfo " + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " insertPublicationsInfo" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    public boolean getPublications(Realm realm, boolean isLessData, List<PublicationsInfo> data) {
//        List<PublicationsInfo> data = new ArrayList<>();
        boolean showView = false;
        try {

            RealmResults<RealmPublications> printPubResults = realm.where(RealmPublications.class).equalTo("type", "print").findAll();
            RealmResults<RealmPublications> onlinePubResults = realm.where(RealmPublications.class).equalTo("type", "online").findAll();
            printPubResults.sort("pub_id", Sort.DESCENDING);//RealmResults.SORT_ORDER_DESCENDING);
            onlinePubResults.sort("pub_id", Sort.DESCENDING);//RealmResults.SORT_ORDER_DESCENDING);
            int printPubSize = printPubResults.size();
            int onlinePubSize = onlinePubResults.size();
            if (printPubResults.size() > 2 || onlinePubResults.size() > 2) {
                showView = true;
            }
            for (int i = 0; i < printPubSize; i++) {
                PublicationsInfo printPubInfo = new PublicationsInfo();
                if (i == 0)
                    printPubInfo.setFirstItem(true);
                if (isLessData && i == 2)
                    break;
                printPubInfo.setPub_id(printPubResults.get(i).getPub_id());
                printPubInfo.setTitle(printPubResults.get(i).getTitle());
                printPubInfo.setAuthors(printPubResults.get(i).getAuthors());
                printPubInfo.setJournal(printPubResults.get(i).getJournal());
                printPubInfo.setWeb_page(printPubResults.get(i).getWeb_page());
                printPubInfo.setType(printPubResults.get(i).getType());
                data.add(printPubInfo);
            }
            for (int j = 0; j < onlinePubSize; j++) {
                PublicationsInfo onlinePubInfo = new PublicationsInfo();
                if (j == 0)
                    onlinePubInfo.setFirstItem(true);
                if (isLessData && j == 2)
                    break;
                onlinePubInfo.setPub_id(onlinePubResults.get(j).getPub_id());
                onlinePubInfo.setTitle(onlinePubResults.get(j).getTitle());
                onlinePubInfo.setAuthors(onlinePubResults.get(j).getAuthors());
                onlinePubInfo.setJournal(onlinePubResults.get(j).getJournal());
                onlinePubInfo.setWeb_page(onlinePubResults.get(j).getWeb_page());
                onlinePubInfo.setType(onlinePubResults.get(j).getType());
                data.add(onlinePubInfo);
            }
            return showView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showView;
    }

    /**
     * delete print_name depends on id
     **/
    public void deletePublications(Realm realm, int pub_id) {
        try {
            realm.beginTransaction();
            RealmResults<RealmPublications> hallos = realm.where(RealmPublications.class).equalTo("pub_id", pub_id).findAll();
            hallos.deleteFromRealm(0);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", "deletePublications" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " deletePublications" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updatePublicationsInfo(Realm realm, PublicationsInfo publicationsInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmPublications> hallos = realm.where(RealmPublications.class).equalTo("pub_id", publicationsInfo.getPub_id()).findAll();
            hallos.get(0).setTitle(publicationsInfo.getTitle());
            hallos.get(0).setAuthors(publicationsInfo.getAuthors());
            hallos.get(0).setJournal(publicationsInfo.getJournal());
            if (publicationsInfo.getWeb_page() != null) {
                hallos.get(0).setWeb_page(publicationsInfo.getWeb_page());
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " Update print " + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update print" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    //save My contacts
    public void insertMyContacts(Realm realm, ContactsInfo contactsInfo, int status) {
        try {
            realm.beginTransaction();
            RealmMyContactsInfo realmMyContactsInfo = realm.createObject(RealmMyContactsInfo.class);
            realmMyContactsInfo.setDoc_id(contactsInfo.getDoc_id());
            realmMyContactsInfo.setName(contactsInfo.getName());
            realmMyContactsInfo.setSpeciality(contactsInfo.getSpeciality());
            realmMyContactsInfo.setSubspeciality(contactsInfo.getSubSpeciality());
            realmMyContactsInfo.setWorkplace(contactsInfo.getWorkplace());
            realmMyContactsInfo.setLocation(contactsInfo.getLocation());
            if (contactsInfo.getPic_name() == null || contactsInfo.getPic_name().equals("") || contactsInfo.getPic_name().equals("null")) {
                realmMyContactsInfo.setPic_name("");
            } else {
                realmMyContactsInfo.setPic_name(contactsInfo.getPic_name());
            }

            realmMyContactsInfo.setEmail(contactsInfo.getCnt_email());
            realmMyContactsInfo.setPhno(contactsInfo.getCnt_num());
            realmMyContactsInfo.setEmail_vis(contactsInfo.getEmail_vis());
            realmMyContactsInfo.setPhno_vis(contactsInfo.getPhno_vis());
            realmMyContactsInfo.setNetworkStatus(status + "");
            realmMyContactsInfo.setQb_userid(contactsInfo.getQb_userid());
            realmMyContactsInfo.setPic_url(contactsInfo.getPic_url());
            realmMyContactsInfo.setUser_salutation(contactsInfo.getUserSalutation());
            realmMyContactsInfo.setUser_type_id(contactsInfo.getUserTypeId());
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " insertMyContacts " + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " insertMyContacts" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    //save My contacts from group accept when doc_ids doesn't exists
    public void insertMyContacts(Realm realm, ContactsInfo contactsInfo) {
        Realm realm1 = Realm.getDefaultInstance();
        try {
            realm1.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmMyContactsInfo> updatehallos = realm.where(RealmMyContactsInfo.class).equalTo(RestUtils.TAG_DOC_ID, contactsInfo.getDoc_id()).findAll();
                    if (updatehallos.size() > 0) {
                        //realm.beginTransaction();
                        updatehallos.get(0).setName(contactsInfo.getName());
                        updatehallos.get(0).setSpeciality(contactsInfo.getSpeciality());
                        updatehallos.get(0).setSubspeciality(contactsInfo.getSubSpeciality());
                        updatehallos.get(0).setWorkplace(contactsInfo.getWorkplace());
                        updatehallos.get(0).setLocation(contactsInfo.getLocation());
                        updatehallos.get(0).setEmail(contactsInfo.getEmail());
                        updatehallos.get(0).setPhno(contactsInfo.getPhno());
                        updatehallos.get(0).setPic_name(contactsInfo.getPic_name());
                        updatehallos.get(0).setQb_userid(contactsInfo.getQb_userid());
                        updatehallos.get(0).setPic_url(contactsInfo.getPic_url());
                        updatehallos.get(0).setUser_salutation(contactsInfo.getUserSalutation());
                        updatehallos.get(0).setUser_type_id(contactsInfo.getUserTypeId());
                        updatehallos.get(0).setNetworkStatus(contactsInfo.getNetworkStatus());
                        updatehallos.get(0).setPhno_vis(contactsInfo.getPhno_vis());
                        updatehallos.get(0).setEmail_vis(contactsInfo.getEmail_vis());
                        //realm.commitTransaction();
                        Log.v("insertMyContacts", "insertMyContacts");
                    } else {
                        RealmResults<RealmBasicInfo> basicinfohallos = realm.where(RealmBasicInfo.class).equalTo(RestUtils.TAG_DOC_ID, contactsInfo.getDoc_id()).findAll();
                        if (basicinfohallos.size() == 0) {
                            //realm.beginTransaction();
                            RealmMyContactsInfo realmMyContactsInfo = realm.createObject(RealmMyContactsInfo.class);
                            realmMyContactsInfo.setDoc_id(contactsInfo.getDoc_id());
                            realmMyContactsInfo.setName(contactsInfo.getName());
                            realmMyContactsInfo.setSpeciality(contactsInfo.getSpeciality());
                            realmMyContactsInfo.setSubspeciality(contactsInfo.getSubSpeciality());
                            realmMyContactsInfo.setWorkplace(contactsInfo.getWorkplace());
                            realmMyContactsInfo.setLocation(contactsInfo.getLocation());
                            if (contactsInfo.getPic_name() == null || contactsInfo.getPic_name().equals("") || contactsInfo.getPic_name().equals("null")) {
                                realmMyContactsInfo.setPic_name("");
                            } else {
                                realmMyContactsInfo.setPic_name(contactsInfo.getPic_name());
                            }
                            realmMyContactsInfo.setPic_url(contactsInfo.getPic_url());
                            realmMyContactsInfo.setEmail(contactsInfo.getEmail());
                            realmMyContactsInfo.setPhno(contactsInfo.getPhno());
                            realmMyContactsInfo.setQb_userid(contactsInfo.getQb_userid());
                            realmMyContactsInfo.setUser_salutation(contactsInfo.getUserSalutation());
                            realmMyContactsInfo.setUser_type_id(contactsInfo.getUserTypeId());
                            realmMyContactsInfo.setNetworkStatus(contactsInfo.getNetworkStatus());
                            updatehallos.get(0).setPhno_vis(contactsInfo.getPhno_vis());
                            updatehallos.get(0).setEmail_vis(contactsInfo.getEmail_vis());
                           // realm.commitTransaction();
                        }
                    }
                }
            });
            if (!realm1.isClosed()) {
                realm1.close();
            }
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " insertMyContacts " + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " insertMyContacts" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    //save group chat members info
    public void insertDialogMem(Realm realm, ArrayList<QBDialogMemInfo> memInfo) {
        try {
            for (int i = 0; i < memInfo.size(); i++) {
                realm.beginTransaction();
                RealmQBDialogMemInfo realmQBGroupMemInfo = realm.createObject(RealmQBDialogMemInfo.class);
                realmQBGroupMemInfo.setDialog_id(memInfo.get(i).getDialog_id());
                realmQBGroupMemInfo.setDoc_id(memInfo.get(i).getDoc_id());
                realmQBGroupMemInfo.setInvite_response(memInfo.get(i).getInvite_response());
                realmQBGroupMemInfo.setIs_admin(memInfo.get(i).is_admin());
                realm.commitTransaction();
            }
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " insertMyContacts " + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " insertMyContacts" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void updateDialogMem(Realm realm, QBDialogMemInfo memInfos) {
        try {
            realm.beginTransaction();
            RealmResults<RealmQBDialogMemInfo> hallos = realm.where(RealmQBDialogMemInfo.class).equalTo(RestUtils.TAG_DOC_ID, memInfos.getDoc_id()).equalTo("dialog_id", memInfos.getDialog_id()).findAll();
            if (hallos.size() != 0) {
                hallos.get(0).setInvite_response(memInfos.getInvite_response());
            } else {
                RealmQBDialogMemInfo realmQBGroupMemInfo = realm.createObject(RealmQBDialogMemInfo.class);
                realmQBGroupMemInfo.setDialog_id(memInfos.getDialog_id());
                realmQBGroupMemInfo.setDoc_id(memInfos.getDoc_id());
                realmQBGroupMemInfo.setInvite_response(memInfos.getInvite_response());
                realmQBGroupMemInfo.setIs_admin(memInfos.is_admin());
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " insertMyContacts " + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " insertMyContacts" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    //update My contacts from group accept when doc_ids exists
    public void updateMyContacts(Realm realm, ContactsInfo contactsInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo(RestUtils.TAG_DOC_ID, contactsInfo.getDoc_id()).findAll();
            if (hallos.size() > 0) {
                hallos.get(0).setName(contactsInfo.getName());
                hallos.get(0).setSpeciality(contactsInfo.getSpeciality());
                hallos.get(0).setSubspeciality(contactsInfo.getSubSpeciality());
                hallos.get(0).setWorkplace(contactsInfo.getWorkplace());
                hallos.get(0).setLocation(contactsInfo.getLocation());
                hallos.get(0).setEmail(contactsInfo.getEmail());
                hallos.get(0).setPhno(contactsInfo.getPhno());
                hallos.get(0).setQb_userid(contactsInfo.getQb_userid());
                hallos.get(0).setUser_salutation(contactsInfo.getUserSalutation());
                hallos.get(0).setUser_type_id(contactsInfo.getUserTypeId());
                if (contactsInfo.getNetworkStatus() != null) {
                    hallos.get(0).setNetworkStatus(contactsInfo.getNetworkStatus());
                }
                hallos.get(0).setPhno_vis(contactsInfo.getPhno_vis());
                hallos.get(0).setEmail_vis(contactsInfo.getEmail_vis());
            } else {
                if (contactsInfo.getNetworkStatus() == null) {
                    contactsInfo.setNetworkStatus("0");
                }
                insertMyContacts(realm, contactsInfo);
            }
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " insertMyContacts " + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " insertMyContacts" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void clearConnects(){
        Realm realm1 = Realm.getDefaultInstance();
        realm1.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).findAll();
                hallos.deleteAllFromRealm();
            }
        });
    }

    public void updateMyContactsStatus(Realm realm, int doc_id, int status) {
        Realm realm1 = Realm.getDefaultInstance();
        try {
            realm1.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo(RestUtils.TAG_DOC_ID, doc_id).findAll();
                    if (hallos != null && hallos.size() > 0) {
                        hallos.get(0).setNetworkStatus(status + "");
                    }
                }
            });
            if (!realm1.isClosed()) {
                realm1.close();
            }
           /* realm.beginTransaction();
            RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo(RestUtils.TAG_DOC_ID, doc_id).findAll();
            if (hallos != null && hallos.size() > 0) {
                hallos.get(0).setNetworkStatus(status + "");
            }
            realm.commitTransaction();*/

        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " updateMyContactsStatus" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "updateMyContactsStatus" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public RealmResults<RealmMyContactsInfo> getMyContactsDB(Realm realm) {

        RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo("networkStatus", "3")
                .sort("name", Sort.ASCENDING).findAll();
        return hallos;

    }

    public ArrayList<ContactsInfo> getMyContacts(Realm realm) {
        ArrayList<ContactsInfo> data = new ArrayList<>();
        try {
            realm.beginTransaction();
            RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo("networkStatus", "3").findAll();

            hallos = hallos.sort("name", Sort.DESCENDING);//RealmResults.SORT_ORDER_DESCENDING);
            for (int i = 0; i < hallos.size(); i++) {
                if (hallos.get(i).getUser_salutation() == null) {
                    hallos.get(i).setUser_salutation("");
                }
                if (hallos.get(i).getUser_type_id() == 0) {
                    hallos.get(i).setUser_type_id(1);
                }
                ContactsInfo contactsInfo = new ContactsInfo();
                contactsInfo.setDoc_id(hallos.get(i).getDoc_id());
                contactsInfo.setName(hallos.get(i).getName());
                contactsInfo.setSpeciality(hallos.get(i).getSpeciality());
                contactsInfo.setSubSpeciality(hallos.get(i).getSubspeciality());
                contactsInfo.setWorkplace(hallos.get(i).getWorkplace());
                contactsInfo.setLocation(hallos.get(i).getLocation());
                contactsInfo.setPic_name(hallos.get(i).getPic_name());
                contactsInfo.setPic_url(hallos.get(i).getPic_url());
                contactsInfo.setPic_data(hallos.get(i).getPic_name());
                contactsInfo.setNetworkStatus(hallos.get(i).getNetworkStatus());
                contactsInfo.setEmail(hallos.get(i).getEmail());
                contactsInfo.setPhno(hallos.get(i).getPhno());
                contactsInfo.setEmail_vis(hallos.get(i).getEmail_vis());
                contactsInfo.setPhno_vis(hallos.get(i).getPhno_vis());
                contactsInfo.setNetworkStatus(hallos.get(i).getNetworkStatus());
                contactsInfo.setQb_userid(hallos.get(i).getQb_userid());
                contactsInfo.setPic_url(hallos.get(i).getPic_url());
                contactsInfo.setUserSalutation(hallos.get(i).getUser_salutation());
                contactsInfo.setUserTypeId(hallos.get(i).getUser_type_id());
                data.add(contactsInfo);
            }
            realm.commitTransaction();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<Integer> getMyContactsQBId(Realm realm) {
        ArrayList<Integer> data = new ArrayList<>();
        try {
            RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).findAll();
            for (int i = 0; i < hallos.size(); i++) {
                data.add(hallos.get(i).getQb_userid());
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getConnectsCount(Realm realm) {
        int resultCount = 0;
        try {
            RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).findAll();
            resultCount = hallos.size();
            return resultCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultCount;
    }



    public boolean isDoctorExists(Realm realm, int docId) {
        try {
            RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo(RestUtils.TAG_DOC_ID, docId).findAll();
            if (hallos.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getDialogsRoomJId(Realm realm) {
        ArrayList<String> data = new ArrayList<>();
        try {
            RealmResults<RealmQBDialog> hallos = realm.where(RealmQBDialog.class).findAll();
            for (int i = 0; i < hallos.size(); i++) {
                data.add(hallos.get(i).getGroup_roomjid());
            }

            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<Integer> getdocIds(Realm realm) {
        ArrayList<Integer> data = new ArrayList<>();
        try {
            RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo("networkStatus", "3").findAll();
            hallos = hallos.sort("name", Sort.DESCENDING);//RealmResults.SORT_ORDER_DESCENDING);
            for (int i = 0; i < hallos.size(); i++) {
                data.add(hallos.get(i).getDoc_id());
            }
            return data;
        } catch (RealmException re) {
            Log.d("RealmException", "getGroupInviteContacts" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "getGroupInviteContacts" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
        return data;
    }

    public ArrayList<ContactsInfo> getInviteContacts(Realm realm) {
        ArrayList<ContactsInfo> data = new ArrayList<>();

        try {
            RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo("networkStatus", "3").findAll();
            hallos = hallos.sort("name", Sort.DESCENDING);//RealmResults.SORT_ORDER_DESCENDING);
            for (int i = 0; i < hallos.size(); i++) {
                ContactsInfo contactsInfo = new ContactsInfo();
                contactsInfo.setDoc_id(hallos.get(i).getDoc_id());
                contactsInfo.setName(hallos.get(i).getName());
                contactsInfo.setSpeciality(hallos.get(i).getSpeciality());
                contactsInfo.setSubSpeciality(hallos.get(i).getSubspeciality());
                contactsInfo.setWorkplace(hallos.get(i).getWorkplace());
                contactsInfo.setLocation(hallos.get(i).getLocation());
                contactsInfo.setPic_name(hallos.get(i).getPic_name());
                contactsInfo.setPic_url(hallos.get(i).getPic_url());
                contactsInfo.setNetworkStatus(hallos.get(i).getNetworkStatus());
                contactsInfo.setEmail(hallos.get(i).getEmail());
                contactsInfo.setPhno(hallos.get(i).getPhno());
                contactsInfo.setEmail_vis(hallos.get(i).getEmail_vis());
                contactsInfo.setPhno_vis(hallos.get(i).getPhno_vis());
                contactsInfo.setNetworkStatus(hallos.get(i).getNetworkStatus());
                contactsInfo.setQb_userid(hallos.get(i).getQb_userid());
                contactsInfo.setUserSalutation((hallos.get(i).getUser_salutation() != null) ? hallos.get(i).getUser_salutation() : "");
                contactsInfo.setUserTypeId((hallos.get(i).getUser_type_id() == 0) ? 1 : hallos.get(i).getUser_type_id());
                data.add(contactsInfo);

            }
            return data;
        } catch (RealmException re) {
            Log.d("RealmException", "getGroupInviteContacts" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "getGroupInviteContacts" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
        return data;
    }



    public void updateMessageStatus(Realm realm, String messageId, int status) {
        try {
            realm.beginTransaction();
            RealmResults<RealmMessages> hallos = realm.where(RealmMessages.class).equalTo("id", messageId).findAll();
            hallos.get(0).setMessage_satus(status);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    public void insertMessage(Realm realm, MessageModel messageModel) {
        try {
            // current_user_id=((App_Application)c).getCurrentUser().getId();
            if (realm.where(RealmMessages.class).equalTo("id", messageModel.getId()).findFirst() == null) {
                realm.beginTransaction();
                RealmMessages messageCache = realm.createObject(RealmMessages.class, messageModel.getId()); // Create a new object
                messageCache.setMessage(messageModel.getMessage());
                messageCache.setDialogId(messageModel.getDialogId());
                messageCache.setAttachUrl(messageModel.getAttachUrl());
                messageCache.setAtt_type(messageModel.getAtt_type());
                messageCache.setSenderId(messageModel.getSenderId());
                messageCache.setMessage_satus((messageModel.getMessage_satus()));
                messageCache.setSync(true);
                messageCache.setTime(messageModel.getTime());
                messageCache.setMsg_type(messageModel.getMsg_type());
                messageCache.setAtt_qbid(messageModel.getAtt_qbid());
                messageCache.setAtt_status(messageModel.getAtt_status());
                realm.commitTransaction();
                if (getQBGroupDialogByID(realm, messageCache.getDialogId()).getLocal_dialog_type() != null && getQBGroupDialogByID(realm, messageCache.getDialogId()).getLocal_dialog_type().equals("caseroom"))
                    updateCaseRoomLastmessage(realm, messageCache.getDialogId(), messageCache.getMessage(), messageCache.getTime());
            }
        } catch (RealmException re) {
            Log.d("RealmException", " insertMessage " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (IllegalStateException re) {
            Log.d("RealmException", " insertMessage " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception re) {
            Log.d("RealmException", " insertMessage " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        }
    }



    public void updateMessagesTable(Realm realm, List<MessageModel> messageModels) {
        try {
            // current_user_id=((App_Application)c).getCurrentUser().getId();

            for (MessageModel messageModel : messageModels) {
                realm.beginTransaction();
                RealmMessages messageCache = realm.createObject(RealmMessages.class, messageModel.getId()); // Create a new object
                messageCache.setMessage(messageModel.getMessage());
                messageCache.setDialogId(messageModel.getDialogId());
                messageCache.setAttachUrl(messageModel.getAttachUrl());
                messageCache.setAtt_type(messageModel.getAtt_type());
                messageCache.setSenderId(messageModel.getSenderId());
                messageCache.setMessage_satus((messageModel.getMessage_satus()));
                messageCache.setSync(true);
                messageCache.setTime(messageModel.getTime());
            /*messageCache.setRead(false);
            messageCache.setDelivered(false);*/
                realm.commitTransaction();
            }
        } catch (RealmException re) {
            Log.d("RealmException", " insertMessage " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        }
    }


    public QBDialogModel getQBGroupDialogByID(Realm realm, String dialog_id) {
        QBDialogModel qbDialogModel = new QBDialogModel();
        try {
            RealmResults<RealmQBGroupDialog> hallos = realm.where(RealmQBGroupDialog.class).equalTo("dialog_id", dialog_id).findAll();
            if (hallos.size() > 0) {
                qbDialogModel.setDialog_title(hallos.get(0).getDialog_title());
                qbDialogModel.setDialog_pic_name(hallos.get(0).getDialog_pic_name());
                qbDialogModel.setLocal_dialog_type(hallos.get(0).getLocal_dialog_type());
                qbDialogModel.setDialog_pic_url(hallos.get(0).getDialog_pic_url());
            }
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", "getQBGroupDialogByID" + e);
        } catch (Exception e) {
            Log.d("RealmException", "getQBGroupDialogByID" + e);
            e.printStackTrace();
        }
        return qbDialogModel;
    }



    public long getUnreadDialogCount(Realm realm) {
        long unreadDialogCount = 0;
        try {
            //Fetching dialogs unread messages count except for hidden dialog
            unreadDialogCount = realm.where(RealmQBDialog.class).equalTo("local_dialog_type", "groupchat").notEqualTo("unread_count", 0).or().equalTo("local_dialog_type", "private").notEqualTo("unread_count", 0).notEqualTo("dialog_id", QBLogin.qb_hidden_dialog_id).notEqualTo("last_msg_time", 0).count();
        } catch (RealmException re) {
            Log.d("RealmException", " updateMessageStatus " + re);
            re.printStackTrace();
//            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", " Update Login" + e);
            e.printStackTrace();
//            realm.cancelTransaction();
        }
        return unreadDialogCount;
    }



    /**
     * @param realm
     * @param qbDialogId
     * @param dialogType This method used to update the local dialog type of the QB dialog and set other fields as null except
     *                   unread count. Unread count we keep to compare with QB unread count when user again comes online after deleting
     *                   a chat in offline.
     */
    public void updateQBDialog(Realm realm, String qbDialogId, String dialogType) {
        try {
            realm.beginTransaction();
            RealmQBDialog realmQBDialog = realm.where(RealmQBDialog.class).equalTo("dialog_id", qbDialogId).findFirst();
            realmQBDialog.setLocal_dialog_type(dialogType);
            realmQBDialog.setLast_msg("");
            realmQBDialog.setOther_doc_id(0);
            realmQBDialog.setLast_msg_time(0);
            realmQBDialog.setDialog_type("");
            realmQBDialog.setDialog_name("");
            realmQBDialog.setGroup_pic_path("");
            realmQBDialog.setOther_doc_name("");
            realmQBDialog.setLast_msg_sent_qb_id(0);
            realmQBDialog.setGroup_roomjid("");
            realmQBDialog.setOccupants_ids("");
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public long checkDialoginDB(Realm realm, String qbDialogid) {
        try {
            return realm.where(RealmQBDialog.class).equalTo("dialog_id", qbDialogid).count();
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " updateMyContactsStatus" + e);
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "updateMyContactsStatus" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
        return 0;
    }


    /**
     * getting private dialogs in offline
     **/
    public QBDialogModel getCRQBDialogs(Realm realm, String dialog_Id) {
        QBDialogModel qbDialogModel = null;
        try {
            RealmResults<RealmQBDialog> hallos = realm.where(RealmQBDialog.class).equalTo("dialog_id", dialog_Id).notEqualTo("local_dialog_type", "deleted_offline").findAll();
            if (hallos.size() > 0) {
                qbDialogModel = new QBDialogModel();
                qbDialogModel.setDialog_id(hallos.get(0).getDialog_id());
                qbDialogModel.setOther_doc_id(hallos.get(0).getOther_doc_id());
                qbDialogModel.setOpponent_doc_id(hallos.get(0).getOther_doc_id());
                qbDialogModel.setLast_msg(hallos.get(0).getLast_msg());
                qbDialogModel.setLast_msg_time(hallos.get(0).getLast_msg_time());
                qbDialogModel.setLast_msg_sent_qb_id(hallos.get(0).getLast_msg_sent_qb_id());
                qbDialogModel.setDialog_type(hallos.get(0).getDialog_type());
                qbDialogModel.setUnread_count(hallos.get(0).getUnread_count());
                qbDialogModel.setOccupants_ids(hallos.get(0).getOccupants_ids());
                qbDialogModel.setDialog_roomjid(hallos.get(0).getGroup_roomjid());
                qbDialogModel.setLocal_dialog_type(hallos.get(0).getLocal_dialog_type());
            }
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " updateMyContactsStatus" + e);
        } catch (Exception e) {
            Log.d("RealmException", "updateMyContactsStatus" + e);
            e.printStackTrace();
        }
        return qbDialogModel;
    }


    /**
     * getting private dialogs in offline
     **/
    public ArrayList<QBDialogModel> getCRQBDialogsForCount(Realm realm) {
        ArrayList<QBDialogModel> qbDialogModel = new ArrayList<>();
        try {
            RealmResults<RealmQBDialog> hallos = realm.where(RealmQBDialog.class).findAll();
            if (hallos.size() > 0) {
                for (int i = 0; i < hallos.size(); i++) {
                    if (hallos.get(i).getLocal_dialog_type().equals("caseroom")) {
                        QBDialogModel qbDialogModel1 = new QBDialogModel();
                        qbDialogModel1.setDialog_id(hallos.get(0).getDialog_id());
                        qbDialogModel1.setOther_doc_id(hallos.get(0).getOther_doc_id());
                        qbDialogModel1.setOpponent_doc_id(hallos.get(0).getOther_doc_id());
                        qbDialogModel1.setLast_msg(hallos.get(0).getLast_msg());
                        qbDialogModel1.setLast_msg_time(hallos.get(0).getLast_msg_time());
                        qbDialogModel1.setLast_msg_sent_qb_id(hallos.get(0).getLast_msg_sent_qb_id());
                        qbDialogModel1.setDialog_type(hallos.get(0).getDialog_type());
                        qbDialogModel1.setUnread_count(hallos.get(0).getUnread_count());
                        qbDialogModel1.setOccupants_ids(hallos.get(0).getOccupants_ids());
                        qbDialogModel1.setDialog_roomjid(hallos.get(0).getGroup_roomjid());
                        qbDialogModel1.setLocal_dialog_type(hallos.get(0).getLocal_dialog_type());
                        qbDialogModel.add(qbDialogModel1);
                    }
                }
            }
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " updateMyContactsStatus" + e);
        } catch (Exception e) {
            Log.d("RealmException", "updateMyContactsStatus" + e);
            e.printStackTrace();
        }
        return qbDialogModel;
    }


    /**
     * getting group dialogs in offline
     **/
    public ArrayList<QBDialogModel> getQBGroupDialogsForCount(Realm realm) {
        ArrayList<QBDialogModel> qbDialogModel = new ArrayList<>();
        try {
            RealmResults<RealmQBDialog> hallos = realm.where(RealmQBDialog.class).findAll();
            if (hallos.size() > 0) {
                for (int i = 0; i < hallos.size(); i++) {
                    if (hallos.get(i).getLocal_dialog_type().equals("groupchat")) {
                        QBDialogModel qbDialogModel1 = new QBDialogModel();
                        qbDialogModel1.setDialog_id(hallos.get(0).getDialog_id());
                        qbDialogModel1.setOther_doc_id(hallos.get(0).getOther_doc_id());
                        qbDialogModel1.setOpponent_doc_id(hallos.get(0).getOther_doc_id());
                        qbDialogModel1.setLast_msg(hallos.get(0).getLast_msg());
                        qbDialogModel1.setLast_msg_time(hallos.get(0).getLast_msg_time());
                        qbDialogModel1.setLast_msg_sent_qb_id(hallos.get(0).getLast_msg_sent_qb_id());
                        qbDialogModel1.setDialog_type(hallos.get(0).getDialog_type());
                        qbDialogModel1.setUnread_count(hallos.get(0).getUnread_count());
                        qbDialogModel1.setOccupants_ids(hallos.get(0).getOccupants_ids());
                        qbDialogModel1.setDialog_roomjid(hallos.get(0).getGroup_roomjid());
                        qbDialogModel1.setLocal_dialog_type(hallos.get(0).getLocal_dialog_type());
                        qbDialogModel.add(qbDialogModel1);
                    }
                }
            }
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " updateMyContactsStatus" + e);
        } catch (Exception e) {
            Log.d("RealmException", "updateMyContactsStatus" + e);
            e.printStackTrace();
        }
        return qbDialogModel;
    }




    public void insertCaseRoomInfo(Realm realm, CaseRoomInfo caseRoomInfo) {
        try {
            realm.beginTransaction();
            RealmCaseRoomInfo realmCaseRoomInfo = realm.createObject(RealmCaseRoomInfo.class, caseRoomInfo.getCaseroom_summary_id());

            if (!caseRoomInfo.getCaseroom_dialog_id().equals("") && !caseRoomInfo.getCaseroom_dialog_id().equals("null")) {
                realmCaseRoomInfo.setCaseroom_dialog_id(caseRoomInfo.getCaseroom_dialog_id());
            }
            realmCaseRoomInfo.setDocid(caseRoomInfo.getDocid());
            realmCaseRoomInfo.setTitle(caseRoomInfo.getTitle());
            realmCaseRoomInfo.setSpeciality(caseRoomInfo.getSpeciality());
            realmCaseRoomInfo.setSub_speciality(caseRoomInfo.getSub_speciality());
            realmCaseRoomInfo.setQuery(caseRoomInfo.getQuery());
            realmCaseRoomInfo.setStatus(caseRoomInfo.getStatus());
            realmCaseRoomInfo.setCreateddate(caseRoomInfo.getCreateddate());
            realmCaseRoomInfo.setModifieddate(caseRoomInfo.getModifieddate());
            realmCaseRoomInfo.setP_details(caseRoomInfo.isP_details());
            realmCaseRoomInfo.setLast_message(caseRoomInfo.getLast_message());
            realmCaseRoomInfo.setLast_message_time(caseRoomInfo.getLast_message_time());

            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " insertCaseRoomInfo " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertCaseRoomInfo" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

    }

    public void updateCaseRoomLastmessage(Realm realm, String crdialogId, String lastMessage, Long lastMessageTime) {
        try {
            realm.beginTransaction();
            RealmCaseRoomInfo hallos = realm.where(RealmCaseRoomInfo.class).equalTo("caseroom_dialog_id", crdialogId).findFirst();
            if (hallos != null) {
                hallos.setLast_message(lastMessage);
                hallos.setLast_message_time(lastMessageTime * 1000);
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " insertCaseRoomInfo " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertCaseRoomInfo" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

    }

    public CaseRoomInfo getCaseRoomInfo(Realm realm, String caseroom_summary_id) {
        CaseRoomInfo caseRoomInfo = new CaseRoomInfo();
        try {
            RealmResults<RealmCaseRoomInfo> hallos = realm.where(RealmCaseRoomInfo.class).equalTo("caseroom_summary_id", caseroom_summary_id).findAll();
            if (hallos.size() > 0) {
                caseRoomInfo.setCaseroom_summary_id(hallos.get(0).getCaseroom_summary_id());
                caseRoomInfo.setCaseroom_dialog_id(hallos.get(0).getCaseroom_dialog_id());
                caseRoomInfo.setDocid(hallos.get(0).getDocid());
                caseRoomInfo.setTitle(hallos.get(0).getTitle());
                caseRoomInfo.setSpeciality(hallos.get(0).getSpeciality());
                caseRoomInfo.setSub_speciality(hallos.get(0).getSub_speciality());
                caseRoomInfo.setQuery(hallos.get(0).getQuery());
                caseRoomInfo.setStatus(hallos.get(0).getStatus());
                caseRoomInfo.setCreateddate(hallos.get(0).getCreateddate());
                caseRoomInfo.setModifieddate(hallos.get(0).getModifieddate());
                caseRoomInfo.setAttachments(hallos.get(0).getAttachments());
                caseRoomInfo.setP_details(hallos.get(0).isP_details());
                return caseRoomInfo;
            }
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " getCaseRoomInfo" + e);
        } catch (Exception e) {
            Log.d("RealmException", "getCaseRoomInfo" + e);
            e.printStackTrace();
        }

        return null;
    }

    public int getCaseRoomInfostatus(Realm realm, String caseroom_summary_id) {
        try {
            RealmResults<RealmCaseRoomInfo> hallos = realm.where(RealmCaseRoomInfo.class).equalTo("caseroom_summary_id", caseroom_summary_id).findAll();
            /*for (RealmCaseRoomInfo realmCaseRoomInfo : hallos) {
                return realmCaseRoomInfo.getStatus();
            }*/
            if(hallos.size()>0){
                return hallos.get(0).getStatus();
            }
        } catch (RealmException e) {
            e.printStackTrace();
            Log.d("RealmException", " getCaseRoomInfo" + e);
        } catch (Exception e) {
            Log.d("RealmException", "getCaseRoomInfo" + e);
            e.printStackTrace();
        }

        return 0;
    }

    public void updateCaseRoomInfo(Realm realm, CaseRoomInfo caseRoomInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmCaseRoomInfo> hallos = realm.where(RealmCaseRoomInfo.class).equalTo("caseroom_summary_id", caseRoomInfo.getCaseroom_summary_id()).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setDocid(caseRoomInfo.getDocid());
                //hallos.get(0).setCaseroom_dialog_id(caseRoomInfo.getCaseroom_dialog_id());
                hallos.get(0).setTitle(caseRoomInfo.getTitle());
                hallos.get(0).setSpeciality(caseRoomInfo.getSpeciality());
                hallos.get(0).setSub_speciality(caseRoomInfo.getSub_speciality());
                hallos.get(0).setQuery(caseRoomInfo.getQuery());
                hallos.get(0).setStatus(caseRoomInfo.getStatus());
                //hallos.get(0).setCreateddate(caseRoomInfo.getCreateddate());
                hallos.get(0).setModifieddate(caseRoomInfo.getModifieddate());
                hallos.get(0).setP_details(caseRoomInfo.isP_details());
                //realm.commitTransaction();
            }
            realm.commitTransaction();


        } catch (RealmException re) {
            Log.d("RealmException", " updateCaseRoomInfo " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "updateCaseRoomInfo" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void insertCaseRoomPatientDetailsInfo(Realm realm, CaseRoomPatientDetailsInfo caseRoomPatientDetailsInfo) {
        try {
            realm.beginTransaction();
            RealmCaseRoomPatientDetailsInfo realmCaseRoomPatientDetailsInfo = realm.createObject(RealmCaseRoomPatientDetailsInfo.class);
            realmCaseRoomPatientDetailsInfo.setCaseroom_summary_id(caseRoomPatientDetailsInfo.getCaseroom_summary_id());
            realmCaseRoomPatientDetailsInfo.setPatgender(caseRoomPatientDetailsInfo.getPatgender());
            realmCaseRoomPatientDetailsInfo.setPatage(caseRoomPatientDetailsInfo.getPatage());
            realmCaseRoomPatientDetailsInfo.setSymptoms(caseRoomPatientDetailsInfo.getSymptoms());
            realmCaseRoomPatientDetailsInfo.setHistory(caseRoomPatientDetailsInfo.getHistory());
            realmCaseRoomPatientDetailsInfo.setVitals_anthropometry(caseRoomPatientDetailsInfo.getVitals_anthropometry());
            realmCaseRoomPatientDetailsInfo.setGeneral_examination(caseRoomPatientDetailsInfo.getGeneral_examination());
            realmCaseRoomPatientDetailsInfo.setSystemic_examination(caseRoomPatientDetailsInfo.getSystemic_examination());
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " insertCaseRoomPatientDetailsInfo " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertCaseRoomInfo" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }

    }

    public void insertCaseRoomAttachmentsInfo(Realm realm, CaseRoomAttachmentsInfo caseRoomAttachmentsInfo) {
        try {
            realm.beginTransaction();
            RealmCaseRoomAttachmentsInfo realmCaseRoomAttachmentsInfo = realm.createObject(RealmCaseRoomAttachmentsInfo.class);
            realmCaseRoomAttachmentsInfo.setCaseroom_summary_id(caseRoomAttachmentsInfo.getCaseroom_summary_id());
            realmCaseRoomAttachmentsInfo.setAttachname(caseRoomAttachmentsInfo.getAttachname());
            realmCaseRoomAttachmentsInfo.setAttachuploadstatus(caseRoomAttachmentsInfo.getAttachuploadstatus());
            realmCaseRoomAttachmentsInfo.setQb_attach_id(caseRoomAttachmentsInfo.getQb_attach_id());
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " insertCaseroomMemberInfo " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertCaseroomMemberInfo" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public void insertCaseRoomAttachmentsInfoArray(Realm realm, ArrayList<CaseRoomAttachmentsInfo> caseRoomAttachmentsInfo) {
        try {
            for (CaseRoomAttachmentsInfo caseRoomAttachmentsInfo1 : caseRoomAttachmentsInfo) {
                realm.beginTransaction();
                RealmCaseRoomAttachmentsInfo realmCaseRoomAttachmentsInfo = realm.createObject(RealmCaseRoomAttachmentsInfo.class);
                realmCaseRoomAttachmentsInfo.setCaseroom_summary_id(caseRoomAttachmentsInfo1.getCaseroom_summary_id());
                realmCaseRoomAttachmentsInfo.setAttachname(caseRoomAttachmentsInfo1.getAttachname());
                realmCaseRoomAttachmentsInfo.setAttachuploadstatus(caseRoomAttachmentsInfo1.getAttachuploadstatus());
                realmCaseRoomAttachmentsInfo.setQb_attach_id(caseRoomAttachmentsInfo1.getQb_attach_id());
                realm.commitTransaction();
            }
        } catch (RealmException re) {
            Log.d("RealmException", "insertCaseRoomAttachmentsInfoArray" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertCaseRoomAttachmentsInfoArray" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    public void updateCaseRoomAttachmentsInfo(Realm realm, CaseRoomAttachmentsInfo caseRoomAttachmentsInfo, String attachname) {
        try {
            realm.beginTransaction();
            RealmResults<RealmCaseRoomAttachmentsInfo> hallos = realm.where(RealmCaseRoomAttachmentsInfo.class).equalTo("caseroom_summary_id", caseRoomAttachmentsInfo.getCaseroom_summary_id()).equalTo("attachname", attachname).findAll();
            if (hallos.size() == 1) {
                hallos.get(0).setAttachuploadstatus(caseRoomAttachmentsInfo.getAttachuploadstatus());
                hallos.get(0).setQb_attach_id(caseRoomAttachmentsInfo.getQb_attach_id());
                hallos.get(0).setAttachname(caseRoomAttachmentsInfo.getAttachname());
            }
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateCaseRoomMemberInfo " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "updateCaseRoomMemberInfo" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    public void deleteCaseRoomAttachmentsInfo(Realm realm, CaseRoomAttachmentsInfo caseRoomAttachmentsInfo) {
        try {
            realm.beginTransaction();
            RealmResults<RealmCaseRoomAttachmentsInfo> hallos = realm.where(RealmCaseRoomAttachmentsInfo.class).equalTo("caseroom_summary_id", caseRoomAttachmentsInfo.getCaseroom_summary_id()).equalTo("attachname", caseRoomAttachmentsInfo.getAttachname()).findAll();
            hallos.deleteFromRealm(0);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", " updateCaseRoomMemberInfo " + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "updateCaseRoomMemberInfo" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }



    public long getCRAttcount(Realm realm, String caseroom_summary_id) {
        return realm.where(RealmCaseRoomAttachmentsInfo.class).equalTo("caseroom_summary_id", caseroom_summary_id).count();
    }





    //Insert feed data into RealmFeedInfo.class

    public void insertFeedDataIntoDB(int feedId,JSONObject feedJson, boolean insertAtIndex) {
        Realm realm = Realm.getDefaultInstance();
        try {
            //int feedId = 0;

            int index = 0;
            realm.beginTransaction();
            RealmResults<RealmFeedsList> feedsListObj = realm.where(RealmFeedsList.class).findAll();
            RealmFeedsList realmFeedsList;
            RealmFeedInfo realmFeedInfo;
            if (feedsListObj.size() > 0) {
                realmFeedsList = feedsListObj.get(0);
            } else {
                realmFeedsList = realm.createObject(RealmFeedsList.class, 0);
            }
            if (insertAtIndex) {

                //feedId = feedsArray.optJSONObject(j).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID);
                RealmResults<RealmFeedInfo> getJsonById = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", feedId + "_" + feedJson.optInt(RestUtils.CHANNEL_ID)).findAll();
                if (checkFeedExists(-6, 0)) {
                    index = 1;
                }
                if (getJsonById.size() > 0) {
                    realmFeedInfo=getJsonById.get(0);
                    realmFeedsList.getFeedsList().remove(realmFeedInfo);
                    //realmFeedsList.getFeedsList().add(index, getJsonById.get(0));
                } else {
                    realmFeedInfo = realm.createObject(RealmFeedInfo.class, feedId + "_" + feedJson.optInt(RestUtils.CHANNEL_ID));
                }
                realmFeedInfo.setFeedId(feedId);
                realmFeedInfo.setFeedsJson(feedJson.toString());
                realmFeedInfo.setChannelId(feedJson.optInt(RestUtils.CHANNEL_ID));
                realmFeedInfo.setDocId(feedJson.optJSONObject("post_creator") == null ? 0 : feedJson.optJSONObject("post_creator").optInt(RestUtils.TAG_DOC_ID, 0));
                realmFeedInfo.setCreatedOrUpdatedTime(feedJson.optJSONObject(RestUtils.TAG_FEED_INFO).optLong("updated_time"));
                realmFeedsList.getFeedsList().add(index, realmFeedInfo);

            } else {
                //feedId = feedsArray.optJSONObject(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID);
                RealmResults<RealmFeedInfo> getJsonById = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", feedId + "_" + feedJson.optInt(RestUtils.CHANNEL_ID)).findAll();
                if (getJsonById.size() > 0) {
                    realmFeedInfo=getJsonById.get(0);
                    realmFeedsList.getFeedsList().remove(realmFeedInfo);
                } else {
                    realmFeedInfo = realm.createObject(RealmFeedInfo.class, feedId + "_" + feedJson.optInt(RestUtils.CHANNEL_ID));
                }
                realmFeedInfo.setFeedId(feedId);
                realmFeedInfo.setFeedsJson(feedJson.toString());
                realmFeedInfo.setChannelId(feedJson.optInt(RestUtils.CHANNEL_ID, 0));
                realmFeedInfo.setDocId(feedJson.optJSONObject("post_creator") == null ? 0 : feedJson.optJSONObject("post_creator").optInt(RestUtils.TAG_DOC_ID, 0));
                realmFeedInfo.setCreatedOrUpdatedTime(feedJson.optJSONObject(RestUtils.TAG_FEED_INFO).optLong("updated_time"));
                realmFeedsList.getFeedsList().add(realmFeedInfo);

            }
            //realmFeedsList.setFeed_Id(feedId);
            realm.copyToRealmOrUpdate(realmFeedsList);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", "insertFeedDataIntoDB" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertFeedDataIntoDB" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
        if(!realm.isClosed()){
            realm.close();
        }
    }

    public synchronized RealmList<RealmFeedInfo> getFeedDataFromDB() {
        Realm realm = Realm.getDefaultInstance();
        RealmList<RealmFeedInfo> resultedList = null;
        RealmResults<RealmFeedsList> results = realm.where(RealmFeedsList.class).findAll();
        if (results.size() > 0) {
            resultedList = results.get(0).getFeedsList();
        }
        if (!realm.isClosed())
            realm.close();
        return resultedList;
    }

    public void insertChannelFeedDataIntoDB(Realm realm, JSONArray feedsArray, boolean insertAtIndex) {
        try {
            int feedId = 0;
            int index = 0;
            realm.beginTransaction();
            RealmResults<RealmChannelFeedsList> feedsListObj = realm.where(RealmChannelFeedsList.class).findAll();
            RealmChannelFeedsList realmFeedsList;
            if (feedsListObj.size() > 0) {
                realmFeedsList = feedsListObj.get(0);
            } else {
                realmFeedsList = realm.createObject(RealmChannelFeedsList.class, 0);
            }
            if (insertAtIndex) {
                for (int j = feedsArray.length() - 1; j >= 0; j--) {
                    feedId = feedsArray.optJSONObject(j).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID);
                    RealmResults<RealmChannelFeedInfo> getJsonById = realm.where(RealmChannelFeedInfo.class).equalTo("feedChannelId", feedId + "_" + feedsArray.optJSONObject(j).optInt(RestUtils.CHANNEL_ID)).findAll();
                    RealmChannelFeedInfo realmFeedInfo;
                    if (getJsonById.size() > 0) {
                        realmFeedInfo=getJsonById.get(0);
                        realmFeedsList.getFeedsList().remove(realmFeedInfo);
                    } else {
                        realmFeedInfo = realm.createObject(RealmChannelFeedInfo.class, feedId + "_" + feedsArray.optJSONObject(j).optInt(RestUtils.CHANNEL_ID));
                    }
                    realmFeedInfo.setFeedId(feedId);
                    realmFeedInfo.setFeedsJson(feedsArray.optJSONObject(j).toString());
                    realmFeedInfo.setChannelId(feedsArray.optJSONObject(j).optInt(RestUtils.CHANNEL_ID));
                    realmFeedInfo.setDocId(feedsArray.optJSONObject(j).optJSONObject("post_creator") == null ? 0 : feedsArray.optJSONObject(j).optJSONObject("post_creator").optInt(RestUtils.TAG_DOC_ID, 0));
                    realmFeedInfo.setCreatedOrUpdatedTime(feedsArray.optJSONObject(j).optJSONObject(RestUtils.TAG_FEED_INFO).optLong("updated_time"));
                    realmFeedsList.getFeedsList().add(0, realmFeedInfo);
                }
            } else {
                for (int i = 0; i < feedsArray.length(); i++) {
                    RealmChannelFeedInfo realmFeedInfo;
                    feedId = feedsArray.optJSONObject(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID);
                    RealmResults<RealmChannelFeedInfo> getJsonById = realm.where(RealmChannelFeedInfo.class).equalTo("feedChannelId", feedId + "_" + feedsArray.optJSONObject(i).optInt(RestUtils.CHANNEL_ID)).findAll();
                    if (getJsonById.size() > 0) {
                        realmFeedInfo = getJsonById.get(0);
                        realmFeedsList.getFeedsList().remove(realmFeedInfo);
                    }
                    else {
                        realmFeedInfo = realm.createObject(RealmChannelFeedInfo.class, feedId + "_" + feedsArray.optJSONObject(i).optInt(RestUtils.CHANNEL_ID));
                    }
                    realmFeedInfo.setFeedId(feedId);
                    realmFeedInfo.setFeedsJson(feedsArray.optJSONObject(i).toString());
                    realmFeedInfo.setChannelId(feedsArray.optJSONObject(i).optInt(RestUtils.CHANNEL_ID, 0));
                    realmFeedInfo.setDocId(feedsArray.optJSONObject(i).optJSONObject("post_creator") == null ? 0 : feedsArray.optJSONObject(i).optJSONObject("post_creator").optInt(RestUtils.TAG_DOC_ID, 0));
                    realmFeedInfo.setCreatedOrUpdatedTime(feedsArray.optJSONObject(i).optJSONObject(RestUtils.TAG_FEED_INFO).optLong("updated_time"));
                    realmFeedsList.getFeedsList().add(realmFeedInfo);
                }
            }
            //realmFeedsList.setFeed_Id(feedId);
            realm.copyToRealmOrUpdate(realmFeedsList);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", "insertFeedDataIntoDB" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertFeedDataIntoDB" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public synchronized RealmList<RealmChannelFeedInfo> getChannelFeedDataFromDB() {
        Realm realm = Realm.getDefaultInstance();
        RealmList<RealmChannelFeedInfo> resultedList = null;
        RealmResults<RealmChannelFeedsList> results = realm.where(RealmChannelFeedsList.class).findAll();
        if (results.size() > 0) {
            resultedList = results.get(0).getFeedsList();
        }
        if (!realm.isClosed())
            realm.close();
        return resultedList;
    }
    //Insert dummy feed data into RealmFeedInfo.class

    public void insertTestFeedDataIntoDB(Realm realm, int mfeedId, boolean insertAtIndex) {
        try {
            int index = 0;
            realm.beginTransaction();
            RealmResults<RealmFeedsList> feedsListObj = realm.where(RealmFeedsList.class).findAll();
            RealmFeedsList realmFeedsList;
            if (feedsListObj.size() > 0) {
                realmFeedsList = feedsListObj.get(0);
            } else {
                realmFeedsList = realm.createObject(RealmFeedsList.class, 0);
            }
            RealmResults<RealmFeedInfo> getJsonById = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", mfeedId + "_" + 0).findAll();
            if (getJsonById.size() > 0) {
                //realmFeedsList.getFeedsList().deleteFromRealm(getJsonById.get(0));
            } else {
                if (checkFeedExists(-6, 0)) {
                    index = 1;
                }
                RealmFeedInfo realmFeedInfo = realm.createObject(RealmFeedInfo.class, mfeedId + "_" + 0);
                realmFeedInfo.setFeedId(mfeedId);
                realmFeedInfo.setFeedsJson("");
                realmFeedInfo.setChannelId(0);
                realmFeedInfo.setDocId(0);
                realmFeedInfo.setCreatedOrUpdatedTime(0);
                if (insertAtIndex) {
                    realmFeedsList.getFeedsList().add(index, realmFeedInfo);
                } else {
                    realmFeedsList.getFeedsList().add(realmFeedInfo);
                }
            }
            //realmFeedsList.setFeed_Id(mfeedId);
            realm.copyToRealmOrUpdate(realmFeedsList);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", "insertFeedDataIntoDB" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertFeedDataIntoDB" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    // Insert data for horizontal list
    public void insertHorizontalListDataIntoDB(int mfeedId, int index,int channelId) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            RealmResults<RealmFeedsList> feedsListObj = realm.where(RealmFeedsList.class).findAll();
            RealmFeedsList realmFeedsList;
            if (feedsListObj.size() > 0) {
                realmFeedsList = feedsListObj.get(0);
            } else {
                realmFeedsList = realm.createObject(RealmFeedsList.class, 0);
            }
            //if(mfeedId==-4){
                RealmResults<RealmFeedInfo> getJsonById = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", mfeedId + "_" + channelId).findAll();
                if(getJsonById.size()>0) {
                    realmFeedsList.getFeedsList().remove(getJsonById.get(0));
                }
            //}
            RealmFeedInfo realmFeedInfo = realm.createObject(RealmFeedInfo.class, mfeedId + "_" + channelId);
            if(channelId!=-5 && channelId!=-6) {
                realmFeedInfo.setFeedId(mfeedId);
            }
            realmFeedInfo.setFeedsJson("");
            realmFeedInfo.setChannelId(channelId);
            realmFeedInfo.setDocId(0);
            realmFeedInfo.setCreatedOrUpdatedTime(0);
            realmFeedsList.getFeedsList().add(index, realmFeedInfo);
            /*if (insertAtIndex) {
                realmFeedsList.getFeedsList().add(index, realmFeedInfo);
            } else {
                realmFeedsList.getFeedsList().add(realmFeedInfo);
            }*/
            /*RealmResults<RealmFeedInfo> getJsonById = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", mfeedId + "_" + 0).findAll();
            if (getJsonById.size() > 0) {
                //realmFeedsList.getFeedsList().deleteFromRealm(getJsonById.get(0));
            } else {

            }*/
            //realmFeedsList.setFeed_Id(mfeedId);
            realm.copyToRealmOrUpdate(realmFeedsList);
            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", "insertFeedDataIntoDB" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        } catch (Exception e) {
            Log.d("RealmException", "insertFeedDataIntoDB" + e);
            e.printStackTrace();
            realm.cancelTransaction();
        }
        if(!realm.isClosed()){
            realm.close();
        }
    }

    // insert suggested feed into DB
    public void insertSuggestedFeedInDB(int feedId,JSONObject feedJson){
        Realm realm=Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmFeedInfo realmFeedInfo;
                RealmResults<RealmFeedInfo> getJsonById = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", feedId + "_" + feedJson.optInt(RestUtils.CHANNEL_ID)).findAll();
                if (getJsonById.size() > 0) {
                    realmFeedInfo=getJsonById.get(0);
                } else {
                    realmFeedInfo = realm.createObject(RealmFeedInfo.class, feedId + "_" + feedJson.optInt(RestUtils.CHANNEL_ID));
                }
                realmFeedInfo.setFeedId(feedId);
                realmFeedInfo.setFeedsJson(feedJson.toString());
                realmFeedInfo.setChannelId(feedJson.optInt(RestUtils.CHANNEL_ID, 0));
                realmFeedInfo.setDocId(feedJson.optJSONObject("post_creator") == null ? 0 : feedJson.optJSONObject("post_creator").optInt(RestUtils.TAG_DOC_ID, 0));
                realmFeedInfo.setCreatedOrUpdatedTime(feedJson.optJSONObject(RestUtils.TAG_FEED_INFO).optLong("updated_time"));
            }
        });
        if(!realm.isClosed()){
            realm.close();
        }
    }

    // get feed json data from DB by feedID
    public RealmFeedInfo getFeedJsonByFeedID(int feedId){
        Realm realm=Realm.getDefaultInstance();
        RealmFeedInfo feedObj=null;
        RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo(RestUtils.FEED_ID, feedId).findAll();
        if(results.size()>0){
            feedObj=results.get(0);
        }
        if(!realm.isClosed()){
            realm.close();
        }
        return feedObj;
    }

    public void removeFeedDataByFeedId(Realm mRealm, final int feedId) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmFeedInfo> rows = realm.where(RealmFeedInfo.class).equalTo(RestUtils.FEED_ID, feedId).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }

    public void UpdateFeedWithSocialInteraction(final int feedId, final JSONObject socialInteractionObj) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo(RestUtils.FEED_ID, feedId).findAll();
                    if (results.size() > 0) {
                        for (int i = 0; i < results.size(); i++) {
                            String feedObj = results.get(i).getFeedsJson();
                            try {
                                JSONObject feedData = new JSONObject(feedObj);
                                JSONObject feedJson = feedData.optJSONObject(RestUtils.TAG_FEED_INFO);
                                feedJson.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
                                results.get(i).setFeedsJson(feedData.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


        } catch (Exception e) {
            Log.d("RealmException", "UpdateFeedWithSocialInteraction :" + e);
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public RealmResults<RealmAreasOfInterestInfo> getAreaOfInterestID(String interestName) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmAreasOfInterestInfo> results = realm.where(RealmAreasOfInterestInfo.class).equalTo("interestName", interestName).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return results;
    }

    public boolean checkFeedExists(int mFeedId, int mChannelId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", mFeedId + "_" + mChannelId).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        if (results.size() > 0) {
            return true;
        }
        return false;
    }


    public void deleteFeedFromDB(final int mFeedId, final int mChannelId) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", mFeedId + "_" + mChannelId).findAll();
                results.deleteAllFromRealm();
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }

    }

    public void insertShareFailedFeedIntoDB(Realm realm, final String feedData) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Get the current max id in the users table
                Number maxId = realm.where(RealmShareFailedFeedInfo.class).max("id");
                // If there are no rows, currentId is null, so the next id must be 1
                // If currentId is not null, increment it by 1
                int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

                RealmShareFailedFeedInfo failedObj = realm.createObject(RealmShareFailedFeedInfo.class, nextId);
                failedObj.setFeedData(feedData);
            }
        });
    }

    public synchronized ArrayList<RealmShareFailedFeedInfo> getShareFailedFeeds() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<RealmShareFailedFeedInfo> listOfFeeds = new ArrayList(realm.where(RealmShareFailedFeedInfo.class).findAll());
        if (!realm.isClosed())
            realm.close();
        return listOfFeeds;
    }

    public void clearShareFailedFeedsData() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmShareFailedFeedInfo> results = realm.where(RealmShareFailedFeedInfo.class).findAll();
                results.deleteAllFromRealm();
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void updateUserFeedCount(final int latestCount) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmBasicInfo> hallos = realm.where(RealmBasicInfo.class).findAll();
                if (hallos.size() == 1) {
                    hallos.get(0).setFeedCount(latestCount);
                }
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void updateMyConnectData(final int docId, final ContactsInfo contactsInfo) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmMyContactsInfo> hallos = realm.where(RealmMyContactsInfo.class).equalTo("doc_id", docId).findAll();
                if (hallos.size() > 0) {
                    hallos.get(0).setDoc_id(contactsInfo.getDoc_id());
                    hallos.get(0).setName(contactsInfo.getName());
                    hallos.get(0).setSpeciality(contactsInfo.getSpeciality());
                    hallos.get(0).setSubspeciality(contactsInfo.getSubSpeciality());
                    hallos.get(0).setWorkplace(contactsInfo.getWorkplace());
                    hallos.get(0).setLocation(contactsInfo.getLocation());
                    if (contactsInfo.getPic_name() == null || contactsInfo.getPic_name().equals("") || contactsInfo.getPic_name().equals("null")) {
                        hallos.get(0).setPic_name("");
                    } else {
                        hallos.get(0).setPic_name(contactsInfo.getPic_name());
                    }
                    hallos.get(0).setPic_url(contactsInfo.getPic_url());
                    hallos.get(0).setEmail(contactsInfo.getEmail());
                    hallos.get(0).setPhno(contactsInfo.getPhno());
                    hallos.get(0).setQb_userid(contactsInfo.getQb_userid());
                    hallos.get(0).setUser_salutation(contactsInfo.getUserSalutation());
                    hallos.get(0).setUser_type_id(contactsInfo.getUserTypeId());
                    hallos.get(0).setUUID(contactsInfo.getUUID());
                }
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void UpdateFeedWithSurveyResponse(final int feedId, final JSONObject surveyResponse) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo(RestUtils.FEED_ID, feedId).findAll();
                    if (results.size() > 0) {
                        for (int i = 0; i < results.size(); i++) {
                            String feedObj = results.get(i).getFeedsJson();
                            try {
                                JSONObject feedData = new JSONObject(feedObj);
                                results.get(i).setFeedsJson(AppUtil.processFeedSurveyResponse(feedData, surveyResponse).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            Log.d("RealmException", "UpdateFeedWithSurveyResponse :" + e);
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public void UpdateFeedWithWebinarRegisterResponse(final int feedId, final JSONObject webinarResponse) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo(RestUtils.FEED_ID, feedId).findAll();
                    if (results.size() > 0) {
                        for (int i = 0; i < results.size(); i++) {
                            String feedObj = results.get(i).getFeedsJson();
                            try {
                                JSONObject feedData = new JSONObject(feedObj);
                                JSONObject feedJson = feedData.optJSONObject(RestUtils.TAG_FEED_INFO);
                                feedJson.put("event_details", webinarResponse);
                                results.get(i).setFeedsJson(feedData.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


        } catch (Exception e) {
            Log.d("RealmException", "UpdateFeedWithSocialInteraction :" + e);
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }

    public void UpdateFeedWithJobApplyStatus(final int feedId, final boolean applyStatus) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo(RestUtils.FEED_ID, feedId).findAll();
                    if (results.size() > 0) {
                        for (int i = 0; i < results.size(); i++) {
                            String feedObj = results.get(i).getFeedsJson();
                            try {
                                JSONObject feedData = new JSONObject(feedObj);
                                JSONObject feedJson = feedData.optJSONObject(RestUtils.TAG_FEED_INFO);
                                feedJson.put("is_applied", applyStatus);
                                results.get(i).setFeedsJson(feedData.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


        } catch (Exception e) {
            Log.d("RealmException", "UpdateFeedWithSocialInteraction :" + e);
            e.printStackTrace();
        } finally {
            if (!realm.isClosed()) {
                realm.close();
            }
        }
    }


    public RealmResults<RealmChannelsInfo> getChannelsListFromDB() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmChannelsInfo> channelsList = realm.where(RealmChannelsInfo.class).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return channelsList;
    }

    public RealmResults<RealmAreasOfInterestInfo> getAreasOfInterestList() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmAreasOfInterestInfo> areasOfInterestList = realm.where(RealmAreasOfInterestInfo.class).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return areasOfInterestList;
    }

    public void clearChannelRealmData() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmChannelFeedsList> rows = realm.where(RealmChannelFeedsList.class).findAll();
                if (rows.size() > 0) {
                    rows.get(0).setFeedsList(new RealmList<RealmChannelFeedInfo>());
                }
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmChannelFeedInfo> rows = realm.where(RealmChannelFeedInfo.class).findAll();
                rows.deleteAllFromRealm();
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void insertOrUpdateChannelsListInDB(String channelsListKey, String channelsList) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmChannelsInfo> listOfChannelInDB = realm.where(RealmChannelsInfo.class).findAll();
                if (listOfChannelInDB.size() > 0) {
                    RealmResults<RealmChannelsInfo> channelsListObject = realm.where(RealmChannelsInfo.class).equalTo("channelsListKey", channelsListKey).findAll();
                    if (channelsListObject.size() > 0) {
                        channelsListObject.get(0).setChannelsList(channelsList);
                    }
                } else {
                    RealmChannelsInfo realmChannelInfo = realm.createObject(RealmChannelsInfo.class);
                    realmChannelInfo.setChannelsListKey(channelsListKey);
                    realmChannelInfo.setChannelsList(channelsList);
                }
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }


    public JSONArray getChannelsListFromDB(String channelsListKey) {
        JSONArray channelArray = null;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmChannelsInfo> channelsListObject = realm.where(RealmChannelsInfo.class).equalTo("channelsListKey", channelsListKey).findAll();
        if (channelsListObject.size() > 0) {
            try {
                channelArray = new JSONArray(channelsListObject.get(0).getChannelsList());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!realm.isClosed()) {
            realm.close();
        }
        return channelArray;
    }


    public void updateFollowingCount(boolean isFollowing) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmBasicInfo> basicInfoResult = realm.where(RealmBasicInfo.class).findAll();
                if (basicInfoResult.size() > 0) {
                    int followingCount = basicInfoResult.get(0).getFollowingCount();
                    if (isFollowing) {
                        basicInfoResult.get(0).setFollowingCount(followingCount + 1);
                    } else {
                        if (followingCount > 0) {
                            basicInfoResult.get(0).setFollowingCount(followingCount - 1);
                        }
                    }
                }
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public RealmResults<RealmEventsInfo> getEventsListFromDB() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmEventsInfo> eventsList = realm.where(RealmEventsInfo.class).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return eventsList;
    }

    public void updatePhoneVerifyStatus(boolean isPhVerify) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmBasicInfo> basicInfoResult = realm.where(RealmBasicInfo.class).findAll();
                if (basicInfoResult.size() > 0) {
                    basicInfoResult.get(0).setMobileVerified(isPhVerify);
                }
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }


    //insert or update event data into DB
    public void insertOrUpdateEventDataDB(String eventName, String eventData, long eventTime) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserEvents userEventObj = realm.createObject(UserEvents.class);
                userEventObj.setEventName(eventName);
                userEventObj.setEventData(eventData);
                userEventObj.setEventTime(eventTime);
                /*RealmResults<UserEvents> eventsList = realm.where(UserEvents.class).findAll();
                if (eventsList.size() > 0) {
                    RealmResults<UserEvents> userEventObj = realm.where(UserEvents.class).equalTo("eventName", eventName).findAll();
                    if (userEventObj.size() > 0) {
                        userEventObj.get(0).setEventData(eventData);
                        userEventObj.get(0).setEventTime(eventTime);
                    }
                } else {
                    UserEvents userEventObj=realm.createObject(UserEvents.class);
                    userEventObj.setEventName(eventName);
                    userEventObj.setEventData(eventData);
                    userEventObj.setEventTime(eventTime);
                }*/
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    //clear event based data from DB
    public void deleteUserEventsDataFromDB(String eventName) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UserEvents> eventsList = realm.where(UserEvents.class).equalTo("eventName", eventName).findAll();
                if (eventsList.size() > 0) {
                    eventsList.deleteAllFromRealm();
                }
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }


    //get event data from DB
    public RealmResults<UserEvents> getEventDataFromDB(String eventName) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<UserEvents> eventsList = realm.where(UserEvents.class).equalTo("eventName", eventName).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return eventsList;
    }

    //get all events data from DB
    public RealmResults<UserEvents> getAllEventsDataFromDB() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<UserEvents> eventsList = realm.where(UserEvents.class).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return eventsList;
    }


    //clear all event based data from DB
    public void deleteAllUserEventsDataFromDB() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<UserEvents> eventsList = realm.where(UserEvents.class).findAll();
                if (eventsList.size() > 0) {
                    eventsList.deleteAllFromRealm();
                }
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    //update edit feeds
    public void updateFeedsDBWithLatestData(final int feedId, final JSONObject feedData) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo("feedId", feedId).findAll();
                    if (results.size() > 0) {
                        for (int i = 0; i < results.size(); i++) {
                            results.get(0).setFeedsJson(feedData.toString());
                                /*String feedObj = results.get(i).getFeedsJson();
                                try {
                                    *//*JSONObject feedData = new JSONObject(feedObj);
                                    JSONObject feedJson = feedData.optJSONObject(RestUtils.TAG_FEED_INFO);
                                    feedJson.put(RestUtils., feedData);*//*

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                        }
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //store notification data in migration
    public void insertNotificationInfoInMigration(LinkedHashMap<String, JSONObject> notificationList) {
        Realm realm = Realm.getDefaultInstance();
        try {
            Set<String> keys = notificationList.keySet();
            for (String key : keys) {
                //System.out.println(k+" -- "+notificationList.get(k));
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmNotificationInfo notificationsInfo = realm.createObject(RealmNotificationInfo.class, key);
                        notificationsInfo.setNotifyData(notificationList.get(key).toString());
                        notificationsInfo.setAcknowledged(true);
                        notificationsInfo.setReadStatus(true);
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //insert new notification data in DB
    public void insertNotifiInfoInMigration(Realm realm, String id, String data, boolean ack, boolean readStatus,long timeReceived) {
        try {
            Realm realm1 = Realm.getDefaultInstance();
            realm1.executeTransaction(realm2 -> {
                RealmNotificationInfo notificationsInfo = realm2.createObject(RealmNotificationInfo.class, id);
                //notificationsInfo.setNotificationID(id);
                notificationsInfo.setNotifyData(data);
                notificationsInfo.setAcknowledged(ack);
                notificationsInfo.setReadStatus(readStatus);
                notificationsInfo.setReceivedTime(timeReceived);
            });

            /*realm.beginTransaction();
            RealmNotificationInfo notificationsInfo = realm.createObject(RealmNotificationInfo.class, id);
            //notificationsInfo.setNotificationID(id);
            notificationsInfo.setNotifyData(data);
            notificationsInfo.setAcknowledged(ack);
            notificationsInfo.setReadStatus(readStatus);
            notificationsInfo.setReceivedTime(timeReceived);
            realm.commitTransaction();*/
            if (!realm1.isClosed()) {
                realm1.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get Notification data to display
    public RealmResults<RealmNotificationInfo> getNotificationDataFromDB() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmNotificationInfo> eventsList = realm.where(RealmNotificationInfo.class).sort("receivedTime",Sort.DESCENDING).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return eventsList;
    }
    public RealmResults<RealmNotificationInfo> getUnReadNotificationsFromDB() {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<RealmNotificationInfo> eventsList = realm.where(RealmNotificationInfo.class).sort("receivedTime",Sort.DESCENDING).equalTo("readStatus",false).findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return eventsList;
    }

    public RealmResults<RealmNotificationInfo> getNotificationDataFromDBOnlyReadStatusFalse() {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<RealmNotificationInfo> eventsList = realm.where(RealmNotificationInfo.class).sort("receivedTime",Sort.DESCENDING).equalTo("readStatus",false).contains("notifyData","INVITE_USER_FOR_CONNECT_RESPONSE").or().contains("notifyData","DEFAULT_USER_CONNECT").findAll();
        if (!realm.isClosed()) {
            realm.close();
        }
        return eventsList;
    }


    //insert notification settings data
   /* public void insertNotificationSettngsInfo(Realm realm, int id, boolean status, String obj) {
        try {
            realm.beginTransaction();
            RealmNotificationSettingsInfo realmNotificationSettingsInfo = realm.createObject(RealmNotificationSettingsInfo.class);
            realmNotificationSettingsInfo.setCategoryId(id);
            realmNotificationSettingsInfo.setEnabled(status);
            realmNotificationSettingsInfo.setJsonData(obj);

            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", "" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        }
    }*/

    public void insertNotificationSettngsInfo(Realm realm, int id, boolean status, String obj) {
        try {
            realm.beginTransaction();
            RealmNotificationSettingsInfo realmNotificationSettingsInfo = realm.createObject(RealmNotificationSettingsInfo.class);
            realmNotificationSettingsInfo.setCategoryId(id);
            realmNotificationSettingsInfo.setEnabled(status);
            realmNotificationSettingsInfo.setJsonData(obj);

            realm.commitTransaction();
        } catch (RealmException re) {
            Log.d("RealmException", "" + re);
            re.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public RealmResults<RealmNotificationSettingsInfo> getNotificationSettingsFromDB() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmNotificationSettingsInfo> notificationData = realm.where(RealmNotificationSettingsInfo.class).findAll();

        if (!realm.isClosed()) {
            realm.close();
        }
        return notificationData;
    }

    public void updateNotificationSettingsFromDB(boolean isChecked) {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmNotificationSettingsInfo> notificationSettingsData = realm.where(RealmNotificationSettingsInfo.class).findAll();
                    if (notificationSettingsData.size() > 0) {
                        for (int i = 0; i < notificationSettingsData.size(); i++) {
                            notificationSettingsData.get(i).setEnabled(isChecked);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateNotificationCategoryId(int categoryId,boolean isEnabled, String jsonData) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<RealmNotificationSettingsInfo> notificationSettingsCategoryData = realm.where(RealmNotificationSettingsInfo.class).equalTo("categoryId", categoryId).findAll();
                    if (notificationSettingsCategoryData.size() > 0) {
                        notificationSettingsCategoryData.get(0).setEnabled(isEnabled);
                        if(jsonData!=null) {
                            notificationSettingsCategoryData.get(0).setJsonData(jsonData);
                        }
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearNotificationCategoryRealmData() {
        Realm realm = Realm.getDefaultInstance();
       /* realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmNotificationSettingsInfo> rows = realm.where(RealmNotificationSettingsInfo.class).findAll();
                RealmResults<RealmNotificationSettingsInfo> rows = realm.where(RealmChannelFeedsList.class).findAll();
                if (rows.size() > 0) {
                    rows.get(0).setFeedsList(new RealmList<RealmChannelFeedInfo>());
                }
            }
        });*/

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmNotificationSettingsInfo> rows = realm.where(RealmNotificationSettingsInfo.class).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }


    // get Feed data from DB
    public RealmFeedInfo getRealmFeedInfoObj(int mFeedId, int mChannelId) {
        Realm realm = Realm.getDefaultInstance();
        RealmFeedInfo result = realm.where(RealmFeedInfo.class).equalTo("feedChannelId", mFeedId + "_" + mChannelId).findFirst();
        if (!realm.isClosed()) {
            realm.close();
        }
        return result;
    }

    // get Feed postion in DB
    public int getFeedDBPosition(RealmFeedInfo feedInfo){
        int position=-1;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmFeedsList> feedsListObj = realm.where(RealmFeedsList.class).findAll();
        if (feedsListObj.size() > 0) {
            position=feedsListObj.get(0).getFeedsList().indexOf(feedInfo);
        }
        if(!realm.isClosed()){
            realm.close();
        }
        return position;
    }


    //get channel object by using channel id
    public JSONObject getChannelFromDB(String channelsListKey,int channelId) {
        JSONObject channelObj=null;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmChannelsInfo> channelsListObject = realm.where(RealmChannelsInfo.class).equalTo("channelsListKey", channelsListKey).findAll();
        if (channelsListObject.size() > 0) {
            try {
                JSONArray channelArray = new JSONArray(channelsListObject.get(0).getChannelsList());
                for(int i=0;i<channelArray.length();i++){
                    if(channelArray.optJSONObject(i).optInt(RestUtils.CHANNEL_ID)==channelId){
                        channelObj=channelArray.optJSONObject(i);
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!realm.isClosed()) {
            realm.close();
        }
        return channelObj;
    }

    public void deleteFeedsByChannelId(int channelId){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmFeedInfo> results = realm.where(RealmFeedInfo.class).equalTo("channelId", channelId).findAll();
                results.deleteAllFromRealm();
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }

    }


    public void clearConnectNotificationsDB() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<RealmNotificationInfo> eventsList = realm1.where(RealmNotificationInfo.class).findAll();
            eventsList.deleteAllFromRealm();
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void addOrUpdateAdslot(JSONArray adDefinitionsArray) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            for(int i=0;i<adDefinitionsArray.length();i++) {
                JSONObject currentObj=adDefinitionsArray.optJSONObject(i);
                RealmResults<RealmAdSlotInfo> results = realm.where(RealmAdSlotInfo.class).equalTo("ad_location_type_id", currentObj.optInt("ad_location_type_id")).findAll();
                RealmAdSlotInfo realmAdSlotInfo;
                if(results.size()>0){
                    realmAdSlotInfo=results.get(0);
                }else {
                    realmAdSlotInfo = realm1.createObject(RealmAdSlotInfo.class);
                }
                realmAdSlotInfo.setSlot_id(currentObj.optInt("slot_id"));
                realmAdSlotInfo.setLocation(currentObj.optString("location"));
                realmAdSlotInfo.setDimensions(currentObj.optString("dimension"));
                realmAdSlotInfo.setAd_source(currentObj.optString("ad_source"));
                realmAdSlotInfo.setOccurance(currentObj.optInt("occurrence"));
                realmAdSlotInfo.setMax_limit(currentObj.optInt("max_limit"));
                realmAdSlotInfo.setSource_slot_id(currentObj.optString("source_slot_id"));
                realmAdSlotInfo.setLocation_type(currentObj.optInt("ad_location_type_id"));
                realmAdSlotInfo.setAd_slot_duration(currentObj.optInt("ad_slot_duration"));
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public RealmResults<RealmAdSlotInfo> getAdSlotInfoByLocation(int location_type){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmAdSlotInfo> results = realm.where(RealmAdSlotInfo.class).equalTo("ad_location_type_id", location_type).findAll();
        if(!realm.isClosed()){
            realm.close();
        }
        return results;
    }

    //clear ad slot definitions data
    public void clearAdSlotDefinitionsData() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmAdSlotInfo> results = realm.where(RealmAdSlotInfo.class).findAll();
                results.deleteAllFromRealm();
            }
        });
        if (!realm.isClosed()) {
            realm.close();
        }
    }


    // get actual feed count

    public int getActualFeedCount() {
        int feedCount=0;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmFeedInfo> resultedList = null;
        RealmResults<RealmFeedsList> results = realm.where(RealmFeedsList.class).findAll();
        if (results.size() > 0) {
            resultedList = results.get(0).getFeedsList().where().notEqualTo("feedId", -3).and().notEqualTo("feedId", -3).and().notEqualTo("feedId", -4).and().notEqualTo("channelId", -5).and().notEqualTo("feedId", -6).notEqualTo("channelId", -6).findAll();
        }
        if (!realm.isClosed())
            realm.close();
        if(resultedList!=null){
            feedCount=resultedList.size();
        }
        return feedCount;
    }

    public void deleteCompleteRealmData(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.deleteAll();
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        } finally {
            realm.close();
        }
    }
}
