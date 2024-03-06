package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ImportFlag;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.internal.objectstore.OsObjectBuilder;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmBasicInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface {

    static final class RealmBasicInfoColumnInfo extends ColumnInfo {
        long doc_idColKey;
        long fnameColKey;
        long lnameColKey;
        long emailColKey;
        long psswdColKey;
        long spltyColKey;
        long subSpecialityColKey;
        long mobileVerifiedColKey;
        long emailVerifiedColKey;
        long phone_numColKey;
        long about_meColKey;
        long profile_pic_pathColKey;
        long reg_card_pathColKey;
        long pic_nameColKey;
        long pic_urlColKey;
        long websiteColKey;
        long blog_pageColKey;
        long fb_pageColKey;
        long phone_num_visibilityColKey;
        long emailvalidationColKey;
        long email_visibilityColKey;
        long qb_loginColKey;
        long qb_hidden_dialog_idColKey;
        long user_salutationColKey;
        long user_type_idColKey;
        long feedCountColKey;
        long likesCountColKey;
        long shareCountColKey;
        long commentsCountColKey;
        long followersCountColKey;
        long followingCountColKey;
        long specificAskColKey;
        long linkedInPgColKey;
        long twitterPgColKey;
        long instagramPgColKey;
        long docProfileURLColKey;
        long docProfilePdfURLColKey;
        long userUUIDColKey;
        long overAllExperienceColKey;
        long qb_useridColKey;
        long tot_contactsColKey;
        long tot_groupsColKey;
        long tot_caseroomsColKey;
        long rememberMeColKey;

        RealmBasicInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(44);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmBasicInfo");
            this.doc_idColKey = addColumnDetails("doc_id", "doc_id", objectSchemaInfo);
            this.fnameColKey = addColumnDetails("fname", "fname", objectSchemaInfo);
            this.lnameColKey = addColumnDetails("lname", "lname", objectSchemaInfo);
            this.emailColKey = addColumnDetails("email", "email", objectSchemaInfo);
            this.psswdColKey = addColumnDetails("psswd", "psswd", objectSchemaInfo);
            this.spltyColKey = addColumnDetails("splty", "splty", objectSchemaInfo);
            this.subSpecialityColKey = addColumnDetails("subSpeciality", "subSpeciality", objectSchemaInfo);
            this.mobileVerifiedColKey = addColumnDetails("mobileVerified", "mobileVerified", objectSchemaInfo);
            this.emailVerifiedColKey = addColumnDetails("emailVerified", "emailVerified", objectSchemaInfo);
            this.phone_numColKey = addColumnDetails("phone_num", "phone_num", objectSchemaInfo);
            this.about_meColKey = addColumnDetails("about_me", "about_me", objectSchemaInfo);
            this.profile_pic_pathColKey = addColumnDetails("profile_pic_path", "profile_pic_path", objectSchemaInfo);
            this.reg_card_pathColKey = addColumnDetails("reg_card_path", "reg_card_path", objectSchemaInfo);
            this.pic_nameColKey = addColumnDetails("pic_name", "pic_name", objectSchemaInfo);
            this.pic_urlColKey = addColumnDetails("pic_url", "pic_url", objectSchemaInfo);
            this.websiteColKey = addColumnDetails("website", "website", objectSchemaInfo);
            this.blog_pageColKey = addColumnDetails("blog_page", "blog_page", objectSchemaInfo);
            this.fb_pageColKey = addColumnDetails("fb_page", "fb_page", objectSchemaInfo);
            this.phone_num_visibilityColKey = addColumnDetails("phone_num_visibility", "phone_num_visibility", objectSchemaInfo);
            this.emailvalidationColKey = addColumnDetails("emailvalidation", "emailvalidation", objectSchemaInfo);
            this.email_visibilityColKey = addColumnDetails("email_visibility", "email_visibility", objectSchemaInfo);
            this.qb_loginColKey = addColumnDetails("qb_login", "qb_login", objectSchemaInfo);
            this.qb_hidden_dialog_idColKey = addColumnDetails("qb_hidden_dialog_id", "qb_hidden_dialog_id", objectSchemaInfo);
            this.user_salutationColKey = addColumnDetails("user_salutation", "user_salutation", objectSchemaInfo);
            this.user_type_idColKey = addColumnDetails("user_type_id", "user_type_id", objectSchemaInfo);
            this.feedCountColKey = addColumnDetails("feedCount", "feedCount", objectSchemaInfo);
            this.likesCountColKey = addColumnDetails("likesCount", "likesCount", objectSchemaInfo);
            this.shareCountColKey = addColumnDetails("shareCount", "shareCount", objectSchemaInfo);
            this.commentsCountColKey = addColumnDetails("commentsCount", "commentsCount", objectSchemaInfo);
            this.followersCountColKey = addColumnDetails("followersCount", "followersCount", objectSchemaInfo);
            this.followingCountColKey = addColumnDetails("followingCount", "followingCount", objectSchemaInfo);
            this.specificAskColKey = addColumnDetails("specificAsk", "specificAsk", objectSchemaInfo);
            this.linkedInPgColKey = addColumnDetails("linkedInPg", "linkedInPg", objectSchemaInfo);
            this.twitterPgColKey = addColumnDetails("twitterPg", "twitterPg", objectSchemaInfo);
            this.instagramPgColKey = addColumnDetails("instagramPg", "instagramPg", objectSchemaInfo);
            this.docProfileURLColKey = addColumnDetails("docProfileURL", "docProfileURL", objectSchemaInfo);
            this.docProfilePdfURLColKey = addColumnDetails("docProfilePdfURL", "docProfilePdfURL", objectSchemaInfo);
            this.userUUIDColKey = addColumnDetails("userUUID", "userUUID", objectSchemaInfo);
            this.overAllExperienceColKey = addColumnDetails("overAllExperience", "overAllExperience", objectSchemaInfo);
            this.qb_useridColKey = addColumnDetails("qb_userid", "qb_userid", objectSchemaInfo);
            this.tot_contactsColKey = addColumnDetails("tot_contacts", "tot_contacts", objectSchemaInfo);
            this.tot_groupsColKey = addColumnDetails("tot_groups", "tot_groups", objectSchemaInfo);
            this.tot_caseroomsColKey = addColumnDetails("tot_caserooms", "tot_caserooms", objectSchemaInfo);
            this.rememberMeColKey = addColumnDetails("rememberMe", "rememberMe", objectSchemaInfo);
        }

        RealmBasicInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmBasicInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmBasicInfoColumnInfo src = (RealmBasicInfoColumnInfo) rawSrc;
            final RealmBasicInfoColumnInfo dst = (RealmBasicInfoColumnInfo) rawDst;
            dst.doc_idColKey = src.doc_idColKey;
            dst.fnameColKey = src.fnameColKey;
            dst.lnameColKey = src.lnameColKey;
            dst.emailColKey = src.emailColKey;
            dst.psswdColKey = src.psswdColKey;
            dst.spltyColKey = src.spltyColKey;
            dst.subSpecialityColKey = src.subSpecialityColKey;
            dst.mobileVerifiedColKey = src.mobileVerifiedColKey;
            dst.emailVerifiedColKey = src.emailVerifiedColKey;
            dst.phone_numColKey = src.phone_numColKey;
            dst.about_meColKey = src.about_meColKey;
            dst.profile_pic_pathColKey = src.profile_pic_pathColKey;
            dst.reg_card_pathColKey = src.reg_card_pathColKey;
            dst.pic_nameColKey = src.pic_nameColKey;
            dst.pic_urlColKey = src.pic_urlColKey;
            dst.websiteColKey = src.websiteColKey;
            dst.blog_pageColKey = src.blog_pageColKey;
            dst.fb_pageColKey = src.fb_pageColKey;
            dst.phone_num_visibilityColKey = src.phone_num_visibilityColKey;
            dst.emailvalidationColKey = src.emailvalidationColKey;
            dst.email_visibilityColKey = src.email_visibilityColKey;
            dst.qb_loginColKey = src.qb_loginColKey;
            dst.qb_hidden_dialog_idColKey = src.qb_hidden_dialog_idColKey;
            dst.user_salutationColKey = src.user_salutationColKey;
            dst.user_type_idColKey = src.user_type_idColKey;
            dst.feedCountColKey = src.feedCountColKey;
            dst.likesCountColKey = src.likesCountColKey;
            dst.shareCountColKey = src.shareCountColKey;
            dst.commentsCountColKey = src.commentsCountColKey;
            dst.followersCountColKey = src.followersCountColKey;
            dst.followingCountColKey = src.followingCountColKey;
            dst.specificAskColKey = src.specificAskColKey;
            dst.linkedInPgColKey = src.linkedInPgColKey;
            dst.twitterPgColKey = src.twitterPgColKey;
            dst.instagramPgColKey = src.instagramPgColKey;
            dst.docProfileURLColKey = src.docProfileURLColKey;
            dst.docProfilePdfURLColKey = src.docProfilePdfURLColKey;
            dst.userUUIDColKey = src.userUUIDColKey;
            dst.overAllExperienceColKey = src.overAllExperienceColKey;
            dst.qb_useridColKey = src.qb_useridColKey;
            dst.tot_contactsColKey = src.tot_contactsColKey;
            dst.tot_groupsColKey = src.tot_groupsColKey;
            dst.tot_caseroomsColKey = src.tot_caseroomsColKey;
            dst.rememberMeColKey = src.rememberMeColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmBasicInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmBasicInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmBasicInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmBasicInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$doc_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.doc_idColKey);
    }

    @Override
    public void realmSet$doc_id(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'doc_id' to null.");
            }
            row.getTable().setLong(columnInfo.doc_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'doc_id' to null.");
        }
        proxyState.getRow$realm().setLong(columnInfo.doc_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$fname() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.fnameColKey);
    }

    @Override
    public void realmSet$fname(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.fnameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.fnameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.fnameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.fnameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$lname() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.lnameColKey);
    }

    @Override
    public void realmSet$lname(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.lnameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.lnameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.lnameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.lnameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailColKey);
    }

    @Override
    public void realmSet$email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.emailColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$psswd() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.psswdColKey);
    }

    @Override
    public void realmSet$psswd(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.psswdColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.psswdColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.psswdColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.psswdColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$splty() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.spltyColKey);
    }

    @Override
    public void realmSet$splty(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.spltyColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.spltyColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.spltyColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.spltyColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$subSpeciality() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.subSpecialityColKey);
    }

    @Override
    public void realmSet$subSpeciality(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.subSpecialityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.subSpecialityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.subSpecialityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.subSpecialityColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$mobileVerified() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.mobileVerifiedColKey);
    }

    @Override
    public void realmSet$mobileVerified(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.mobileVerifiedColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.mobileVerifiedColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$emailVerified() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.emailVerifiedColKey);
    }

    @Override
    public void realmSet$emailVerified(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.emailVerifiedColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.emailVerifiedColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$phone_num() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phone_numColKey);
    }

    @Override
    public void realmSet$phone_num(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phone_numColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.phone_numColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phone_numColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phone_numColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$about_me() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.about_meColKey);
    }

    @Override
    public void realmSet$about_me(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.about_meColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.about_meColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.about_meColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.about_meColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$profile_pic_path() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.profile_pic_pathColKey);
    }

    @Override
    public void realmSet$profile_pic_path(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.profile_pic_pathColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.profile_pic_pathColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.profile_pic_pathColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.profile_pic_pathColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$reg_card_path() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.reg_card_pathColKey);
    }

    @Override
    public void realmSet$reg_card_path(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.reg_card_pathColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.reg_card_pathColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.reg_card_pathColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.reg_card_pathColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$pic_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.pic_nameColKey);
    }

    @Override
    public void realmSet$pic_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.pic_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.pic_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.pic_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.pic_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$pic_url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.pic_urlColKey);
    }

    @Override
    public void realmSet$pic_url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.pic_urlColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.pic_urlColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.pic_urlColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.pic_urlColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$website() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.websiteColKey);
    }

    @Override
    public void realmSet$website(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.websiteColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.websiteColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.websiteColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.websiteColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$blog_page() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.blog_pageColKey);
    }

    @Override
    public void realmSet$blog_page(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.blog_pageColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.blog_pageColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.blog_pageColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.blog_pageColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$fb_page() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.fb_pageColKey);
    }

    @Override
    public void realmSet$fb_page(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.fb_pageColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.fb_pageColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.fb_pageColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.fb_pageColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$phone_num_visibility() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.phone_num_visibilityColKey);
    }

    @Override
    public void realmSet$phone_num_visibility(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.phone_num_visibilityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.phone_num_visibilityColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$emailvalidation() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailvalidationColKey);
    }

    @Override
    public void realmSet$emailvalidation(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.emailvalidationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailvalidationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailvalidationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailvalidationColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$email_visibility() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.email_visibilityColKey);
    }

    @Override
    public void realmSet$email_visibility(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.email_visibilityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.email_visibilityColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$qb_login() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.qb_loginColKey);
    }

    @Override
    public void realmSet$qb_login(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.qb_loginColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.qb_loginColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.qb_loginColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.qb_loginColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$qb_hidden_dialog_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.qb_hidden_dialog_idColKey);
    }

    @Override
    public void realmSet$qb_hidden_dialog_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.qb_hidden_dialog_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.qb_hidden_dialog_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.qb_hidden_dialog_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.qb_hidden_dialog_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$user_salutation() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.user_salutationColKey);
    }

    @Override
    public void realmSet$user_salutation(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.user_salutationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.user_salutationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.user_salutationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.user_salutationColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$user_type_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.user_type_idColKey);
    }

    @Override
    public void realmSet$user_type_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.user_type_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.user_type_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$feedCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.feedCountColKey);
    }

    @Override
    public void realmSet$feedCount(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.feedCountColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.feedCountColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$likesCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.likesCountColKey);
    }

    @Override
    public void realmSet$likesCount(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.likesCountColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.likesCountColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$shareCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.shareCountColKey);
    }

    @Override
    public void realmSet$shareCount(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.shareCountColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.shareCountColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$commentsCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.commentsCountColKey);
    }

    @Override
    public void realmSet$commentsCount(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.commentsCountColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.commentsCountColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$followersCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.followersCountColKey);
    }

    @Override
    public void realmSet$followersCount(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.followersCountColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.followersCountColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$followingCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.followingCountColKey);
    }

    @Override
    public void realmSet$followingCount(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.followingCountColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.followingCountColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$specificAsk() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.specificAskColKey);
    }

    @Override
    public void realmSet$specificAsk(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.specificAskColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.specificAskColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.specificAskColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.specificAskColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$linkedInPg() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.linkedInPgColKey);
    }

    @Override
    public void realmSet$linkedInPg(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.linkedInPgColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.linkedInPgColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.linkedInPgColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.linkedInPgColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$twitterPg() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.twitterPgColKey);
    }

    @Override
    public void realmSet$twitterPg(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.twitterPgColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.twitterPgColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.twitterPgColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.twitterPgColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$instagramPg() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.instagramPgColKey);
    }

    @Override
    public void realmSet$instagramPg(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.instagramPgColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.instagramPgColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.instagramPgColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.instagramPgColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$docProfileURL() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.docProfileURLColKey);
    }

    @Override
    public void realmSet$docProfileURL(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.docProfileURLColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.docProfileURLColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.docProfileURLColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.docProfileURLColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$docProfilePdfURL() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.docProfilePdfURLColKey);
    }

    @Override
    public void realmSet$docProfilePdfURL(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.docProfilePdfURLColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.docProfilePdfURLColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.docProfilePdfURLColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.docProfilePdfURLColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$userUUID() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.userUUIDColKey);
    }

    @Override
    public void realmSet$userUUID(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.userUUIDColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.userUUIDColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.userUUIDColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.userUUIDColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$overAllExperience() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.overAllExperienceColKey);
    }

    @Override
    public void realmSet$overAllExperience(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.overAllExperienceColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.overAllExperienceColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$qb_userid() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.qb_useridColKey);
    }

    @Override
    public void realmSet$qb_userid(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.qb_useridColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.qb_useridColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$tot_contacts() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.tot_contactsColKey);
    }

    @Override
    public void realmSet$tot_contacts(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.tot_contactsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.tot_contactsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$tot_groups() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.tot_groupsColKey);
    }

    @Override
    public void realmSet$tot_groups(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.tot_groupsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.tot_groupsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$tot_caserooms() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.tot_caseroomsColKey);
    }

    @Override
    public void realmSet$tot_caserooms(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.tot_caseroomsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.tot_caseroomsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$rememberMe() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.rememberMeColKey);
    }

    @Override
    public void realmSet$rememberMe(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.rememberMeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.rememberMeColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmBasicInfo", 44, 0);
        builder.addPersistedProperty("doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("fname", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("lname", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("psswd", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("splty", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("subSpeciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("mobileVerified", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("emailVerified", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("phone_num", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("about_me", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("profile_pic_path", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("reg_card_path", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("pic_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("pic_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("website", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("blog_page", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("fb_page", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("phone_num_visibility", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("emailvalidation", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("email_visibility", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("qb_login", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("qb_hidden_dialog_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("user_salutation", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("user_type_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("feedCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("likesCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("shareCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("commentsCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("followersCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("followingCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("specificAsk", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("linkedInPg", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("twitterPg", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("instagramPg", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("docProfileURL", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("docProfilePdfURL", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("userUUID", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("overAllExperience", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("qb_userid", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("tot_contacts", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("tot_groups", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("tot_caserooms", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("rememberMe", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmBasicInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmBasicInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmBasicInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmBasicInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmBasicInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmBasicInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmBasicInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) obj;
        if (json.has("doc_id")) {
            if (json.isNull("doc_id")) {
                objProxy.realmSet$doc_id(null);
            } else {
                objProxy.realmSet$doc_id((int) json.getInt("doc_id"));
            }
        }
        if (json.has("fname")) {
            if (json.isNull("fname")) {
                objProxy.realmSet$fname(null);
            } else {
                objProxy.realmSet$fname((String) json.getString("fname"));
            }
        }
        if (json.has("lname")) {
            if (json.isNull("lname")) {
                objProxy.realmSet$lname(null);
            } else {
                objProxy.realmSet$lname((String) json.getString("lname"));
            }
        }
        if (json.has("email")) {
            if (json.isNull("email")) {
                objProxy.realmSet$email(null);
            } else {
                objProxy.realmSet$email((String) json.getString("email"));
            }
        }
        if (json.has("psswd")) {
            if (json.isNull("psswd")) {
                objProxy.realmSet$psswd(null);
            } else {
                objProxy.realmSet$psswd((String) json.getString("psswd"));
            }
        }
        if (json.has("splty")) {
            if (json.isNull("splty")) {
                objProxy.realmSet$splty(null);
            } else {
                objProxy.realmSet$splty((String) json.getString("splty"));
            }
        }
        if (json.has("subSpeciality")) {
            if (json.isNull("subSpeciality")) {
                objProxy.realmSet$subSpeciality(null);
            } else {
                objProxy.realmSet$subSpeciality((String) json.getString("subSpeciality"));
            }
        }
        if (json.has("mobileVerified")) {
            if (json.isNull("mobileVerified")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'mobileVerified' to null.");
            } else {
                objProxy.realmSet$mobileVerified((boolean) json.getBoolean("mobileVerified"));
            }
        }
        if (json.has("emailVerified")) {
            if (json.isNull("emailVerified")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'emailVerified' to null.");
            } else {
                objProxy.realmSet$emailVerified((boolean) json.getBoolean("emailVerified"));
            }
        }
        if (json.has("phone_num")) {
            if (json.isNull("phone_num")) {
                objProxy.realmSet$phone_num(null);
            } else {
                objProxy.realmSet$phone_num((String) json.getString("phone_num"));
            }
        }
        if (json.has("about_me")) {
            if (json.isNull("about_me")) {
                objProxy.realmSet$about_me(null);
            } else {
                objProxy.realmSet$about_me((String) json.getString("about_me"));
            }
        }
        if (json.has("profile_pic_path")) {
            if (json.isNull("profile_pic_path")) {
                objProxy.realmSet$profile_pic_path(null);
            } else {
                objProxy.realmSet$profile_pic_path((String) json.getString("profile_pic_path"));
            }
        }
        if (json.has("reg_card_path")) {
            if (json.isNull("reg_card_path")) {
                objProxy.realmSet$reg_card_path(null);
            } else {
                objProxy.realmSet$reg_card_path((String) json.getString("reg_card_path"));
            }
        }
        if (json.has("pic_name")) {
            if (json.isNull("pic_name")) {
                objProxy.realmSet$pic_name(null);
            } else {
                objProxy.realmSet$pic_name((String) json.getString("pic_name"));
            }
        }
        if (json.has("pic_url")) {
            if (json.isNull("pic_url")) {
                objProxy.realmSet$pic_url(null);
            } else {
                objProxy.realmSet$pic_url((String) json.getString("pic_url"));
            }
        }
        if (json.has("website")) {
            if (json.isNull("website")) {
                objProxy.realmSet$website(null);
            } else {
                objProxy.realmSet$website((String) json.getString("website"));
            }
        }
        if (json.has("blog_page")) {
            if (json.isNull("blog_page")) {
                objProxy.realmSet$blog_page(null);
            } else {
                objProxy.realmSet$blog_page((String) json.getString("blog_page"));
            }
        }
        if (json.has("fb_page")) {
            if (json.isNull("fb_page")) {
                objProxy.realmSet$fb_page(null);
            } else {
                objProxy.realmSet$fb_page((String) json.getString("fb_page"));
            }
        }
        if (json.has("phone_num_visibility")) {
            if (json.isNull("phone_num_visibility")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'phone_num_visibility' to null.");
            } else {
                objProxy.realmSet$phone_num_visibility((int) json.getInt("phone_num_visibility"));
            }
        }
        if (json.has("emailvalidation")) {
            if (json.isNull("emailvalidation")) {
                objProxy.realmSet$emailvalidation(null);
            } else {
                objProxy.realmSet$emailvalidation((String) json.getString("emailvalidation"));
            }
        }
        if (json.has("email_visibility")) {
            if (json.isNull("email_visibility")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'email_visibility' to null.");
            } else {
                objProxy.realmSet$email_visibility((int) json.getInt("email_visibility"));
            }
        }
        if (json.has("qb_login")) {
            if (json.isNull("qb_login")) {
                objProxy.realmSet$qb_login(null);
            } else {
                objProxy.realmSet$qb_login((String) json.getString("qb_login"));
            }
        }
        if (json.has("qb_hidden_dialog_id")) {
            if (json.isNull("qb_hidden_dialog_id")) {
                objProxy.realmSet$qb_hidden_dialog_id(null);
            } else {
                objProxy.realmSet$qb_hidden_dialog_id((String) json.getString("qb_hidden_dialog_id"));
            }
        }
        if (json.has("user_salutation")) {
            if (json.isNull("user_salutation")) {
                objProxy.realmSet$user_salutation(null);
            } else {
                objProxy.realmSet$user_salutation((String) json.getString("user_salutation"));
            }
        }
        if (json.has("user_type_id")) {
            if (json.isNull("user_type_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'user_type_id' to null.");
            } else {
                objProxy.realmSet$user_type_id((int) json.getInt("user_type_id"));
            }
        }
        if (json.has("feedCount")) {
            if (json.isNull("feedCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'feedCount' to null.");
            } else {
                objProxy.realmSet$feedCount((int) json.getInt("feedCount"));
            }
        }
        if (json.has("likesCount")) {
            if (json.isNull("likesCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'likesCount' to null.");
            } else {
                objProxy.realmSet$likesCount((int) json.getInt("likesCount"));
            }
        }
        if (json.has("shareCount")) {
            if (json.isNull("shareCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'shareCount' to null.");
            } else {
                objProxy.realmSet$shareCount((int) json.getInt("shareCount"));
            }
        }
        if (json.has("commentsCount")) {
            if (json.isNull("commentsCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'commentsCount' to null.");
            } else {
                objProxy.realmSet$commentsCount((int) json.getInt("commentsCount"));
            }
        }
        if (json.has("followersCount")) {
            if (json.isNull("followersCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'followersCount' to null.");
            } else {
                objProxy.realmSet$followersCount((int) json.getInt("followersCount"));
            }
        }
        if (json.has("followingCount")) {
            if (json.isNull("followingCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'followingCount' to null.");
            } else {
                objProxy.realmSet$followingCount((int) json.getInt("followingCount"));
            }
        }
        if (json.has("specificAsk")) {
            if (json.isNull("specificAsk")) {
                objProxy.realmSet$specificAsk(null);
            } else {
                objProxy.realmSet$specificAsk((String) json.getString("specificAsk"));
            }
        }
        if (json.has("linkedInPg")) {
            if (json.isNull("linkedInPg")) {
                objProxy.realmSet$linkedInPg(null);
            } else {
                objProxy.realmSet$linkedInPg((String) json.getString("linkedInPg"));
            }
        }
        if (json.has("twitterPg")) {
            if (json.isNull("twitterPg")) {
                objProxy.realmSet$twitterPg(null);
            } else {
                objProxy.realmSet$twitterPg((String) json.getString("twitterPg"));
            }
        }
        if (json.has("instagramPg")) {
            if (json.isNull("instagramPg")) {
                objProxy.realmSet$instagramPg(null);
            } else {
                objProxy.realmSet$instagramPg((String) json.getString("instagramPg"));
            }
        }
        if (json.has("docProfileURL")) {
            if (json.isNull("docProfileURL")) {
                objProxy.realmSet$docProfileURL(null);
            } else {
                objProxy.realmSet$docProfileURL((String) json.getString("docProfileURL"));
            }
        }
        if (json.has("docProfilePdfURL")) {
            if (json.isNull("docProfilePdfURL")) {
                objProxy.realmSet$docProfilePdfURL(null);
            } else {
                objProxy.realmSet$docProfilePdfURL((String) json.getString("docProfilePdfURL"));
            }
        }
        if (json.has("userUUID")) {
            if (json.isNull("userUUID")) {
                objProxy.realmSet$userUUID(null);
            } else {
                objProxy.realmSet$userUUID((String) json.getString("userUUID"));
            }
        }
        if (json.has("overAllExperience")) {
            if (json.isNull("overAllExperience")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'overAllExperience' to null.");
            } else {
                objProxy.realmSet$overAllExperience((int) json.getInt("overAllExperience"));
            }
        }
        if (json.has("qb_userid")) {
            if (json.isNull("qb_userid")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'qb_userid' to null.");
            } else {
                objProxy.realmSet$qb_userid((int) json.getInt("qb_userid"));
            }
        }
        if (json.has("tot_contacts")) {
            if (json.isNull("tot_contacts")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'tot_contacts' to null.");
            } else {
                objProxy.realmSet$tot_contacts((int) json.getInt("tot_contacts"));
            }
        }
        if (json.has("tot_groups")) {
            if (json.isNull("tot_groups")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'tot_groups' to null.");
            } else {
                objProxy.realmSet$tot_groups((int) json.getInt("tot_groups"));
            }
        }
        if (json.has("tot_caserooms")) {
            if (json.isNull("tot_caserooms")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'tot_caserooms' to null.");
            } else {
                objProxy.realmSet$tot_caserooms((int) json.getInt("tot_caserooms"));
            }
        }
        if (json.has("rememberMe")) {
            if (json.isNull("rememberMe")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'rememberMe' to null.");
            } else {
                objProxy.realmSet$rememberMe((boolean) json.getBoolean("rememberMe"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmBasicInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmBasicInfo obj = new com.vam.whitecoats.core.realm.RealmBasicInfo();
        final com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("doc_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_id(null);
                }
            } else if (name.equals("fname")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$fname((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$fname(null);
                }
            } else if (name.equals("lname")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$lname((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$lname(null);
                }
            } else if (name.equals("email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$email(null);
                }
            } else if (name.equals("psswd")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$psswd((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$psswd(null);
                }
            } else if (name.equals("splty")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$splty((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$splty(null);
                }
            } else if (name.equals("subSpeciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$subSpeciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$subSpeciality(null);
                }
            } else if (name.equals("mobileVerified")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$mobileVerified((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'mobileVerified' to null.");
                }
            } else if (name.equals("emailVerified")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$emailVerified((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'emailVerified' to null.");
                }
            } else if (name.equals("phone_num")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phone_num((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phone_num(null);
                }
            } else if (name.equals("about_me")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$about_me((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$about_me(null);
                }
            } else if (name.equals("profile_pic_path")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$profile_pic_path((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$profile_pic_path(null);
                }
            } else if (name.equals("reg_card_path")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$reg_card_path((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$reg_card_path(null);
                }
            } else if (name.equals("pic_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$pic_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$pic_name(null);
                }
            } else if (name.equals("pic_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$pic_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$pic_url(null);
                }
            } else if (name.equals("website")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$website((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$website(null);
                }
            } else if (name.equals("blog_page")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$blog_page((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$blog_page(null);
                }
            } else if (name.equals("fb_page")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$fb_page((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$fb_page(null);
                }
            } else if (name.equals("phone_num_visibility")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phone_num_visibility((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'phone_num_visibility' to null.");
                }
            } else if (name.equals("emailvalidation")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$emailvalidation((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$emailvalidation(null);
                }
            } else if (name.equals("email_visibility")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$email_visibility((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'email_visibility' to null.");
                }
            } else if (name.equals("qb_login")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$qb_login((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$qb_login(null);
                }
            } else if (name.equals("qb_hidden_dialog_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$qb_hidden_dialog_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$qb_hidden_dialog_id(null);
                }
            } else if (name.equals("user_salutation")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$user_salutation((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$user_salutation(null);
                }
            } else if (name.equals("user_type_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$user_type_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'user_type_id' to null.");
                }
            } else if (name.equals("feedCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$feedCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'feedCount' to null.");
                }
            } else if (name.equals("likesCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$likesCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'likesCount' to null.");
                }
            } else if (name.equals("shareCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$shareCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'shareCount' to null.");
                }
            } else if (name.equals("commentsCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$commentsCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'commentsCount' to null.");
                }
            } else if (name.equals("followersCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$followersCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'followersCount' to null.");
                }
            } else if (name.equals("followingCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$followingCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'followingCount' to null.");
                }
            } else if (name.equals("specificAsk")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$specificAsk((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$specificAsk(null);
                }
            } else if (name.equals("linkedInPg")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$linkedInPg((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$linkedInPg(null);
                }
            } else if (name.equals("twitterPg")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$twitterPg((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$twitterPg(null);
                }
            } else if (name.equals("instagramPg")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$instagramPg((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$instagramPg(null);
                }
            } else if (name.equals("docProfileURL")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$docProfileURL((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$docProfileURL(null);
                }
            } else if (name.equals("docProfilePdfURL")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$docProfilePdfURL((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$docProfilePdfURL(null);
                }
            } else if (name.equals("userUUID")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$userUUID((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$userUUID(null);
                }
            } else if (name.equals("overAllExperience")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$overAllExperience((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'overAllExperience' to null.");
                }
            } else if (name.equals("qb_userid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$qb_userid((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'qb_userid' to null.");
                }
            } else if (name.equals("tot_contacts")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$tot_contacts((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'tot_contacts' to null.");
                }
            } else if (name.equals("tot_groups")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$tot_groups((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'tot_groups' to null.");
                }
            } else if (name.equals("tot_caserooms")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$tot_caserooms((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'tot_caserooms' to null.");
                }
            } else if (name.equals("rememberMe")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$rememberMe((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'rememberMe' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmBasicInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmBasicInfo copyOrUpdate(Realm realm, RealmBasicInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmBasicInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmBasicInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmBasicInfo copy(Realm realm, RealmBasicInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmBasicInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmBasicInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.doc_idColKey, realmObjectSource.realmGet$doc_id());
        builder.addString(columnInfo.fnameColKey, realmObjectSource.realmGet$fname());
        builder.addString(columnInfo.lnameColKey, realmObjectSource.realmGet$lname());
        builder.addString(columnInfo.emailColKey, realmObjectSource.realmGet$email());
        builder.addString(columnInfo.psswdColKey, realmObjectSource.realmGet$psswd());
        builder.addString(columnInfo.spltyColKey, realmObjectSource.realmGet$splty());
        builder.addString(columnInfo.subSpecialityColKey, realmObjectSource.realmGet$subSpeciality());
        builder.addBoolean(columnInfo.mobileVerifiedColKey, realmObjectSource.realmGet$mobileVerified());
        builder.addBoolean(columnInfo.emailVerifiedColKey, realmObjectSource.realmGet$emailVerified());
        builder.addString(columnInfo.phone_numColKey, realmObjectSource.realmGet$phone_num());
        builder.addString(columnInfo.about_meColKey, realmObjectSource.realmGet$about_me());
        builder.addString(columnInfo.profile_pic_pathColKey, realmObjectSource.realmGet$profile_pic_path());
        builder.addString(columnInfo.reg_card_pathColKey, realmObjectSource.realmGet$reg_card_path());
        builder.addString(columnInfo.pic_nameColKey, realmObjectSource.realmGet$pic_name());
        builder.addString(columnInfo.pic_urlColKey, realmObjectSource.realmGet$pic_url());
        builder.addString(columnInfo.websiteColKey, realmObjectSource.realmGet$website());
        builder.addString(columnInfo.blog_pageColKey, realmObjectSource.realmGet$blog_page());
        builder.addString(columnInfo.fb_pageColKey, realmObjectSource.realmGet$fb_page());
        builder.addInteger(columnInfo.phone_num_visibilityColKey, realmObjectSource.realmGet$phone_num_visibility());
        builder.addString(columnInfo.emailvalidationColKey, realmObjectSource.realmGet$emailvalidation());
        builder.addInteger(columnInfo.email_visibilityColKey, realmObjectSource.realmGet$email_visibility());
        builder.addString(columnInfo.qb_loginColKey, realmObjectSource.realmGet$qb_login());
        builder.addString(columnInfo.qb_hidden_dialog_idColKey, realmObjectSource.realmGet$qb_hidden_dialog_id());
        builder.addString(columnInfo.user_salutationColKey, realmObjectSource.realmGet$user_salutation());
        builder.addInteger(columnInfo.user_type_idColKey, realmObjectSource.realmGet$user_type_id());
        builder.addInteger(columnInfo.feedCountColKey, realmObjectSource.realmGet$feedCount());
        builder.addInteger(columnInfo.likesCountColKey, realmObjectSource.realmGet$likesCount());
        builder.addInteger(columnInfo.shareCountColKey, realmObjectSource.realmGet$shareCount());
        builder.addInteger(columnInfo.commentsCountColKey, realmObjectSource.realmGet$commentsCount());
        builder.addInteger(columnInfo.followersCountColKey, realmObjectSource.realmGet$followersCount());
        builder.addInteger(columnInfo.followingCountColKey, realmObjectSource.realmGet$followingCount());
        builder.addString(columnInfo.specificAskColKey, realmObjectSource.realmGet$specificAsk());
        builder.addString(columnInfo.linkedInPgColKey, realmObjectSource.realmGet$linkedInPg());
        builder.addString(columnInfo.twitterPgColKey, realmObjectSource.realmGet$twitterPg());
        builder.addString(columnInfo.instagramPgColKey, realmObjectSource.realmGet$instagramPg());
        builder.addString(columnInfo.docProfileURLColKey, realmObjectSource.realmGet$docProfileURL());
        builder.addString(columnInfo.docProfilePdfURLColKey, realmObjectSource.realmGet$docProfilePdfURL());
        builder.addString(columnInfo.userUUIDColKey, realmObjectSource.realmGet$userUUID());
        builder.addInteger(columnInfo.overAllExperienceColKey, realmObjectSource.realmGet$overAllExperience());
        builder.addInteger(columnInfo.qb_useridColKey, realmObjectSource.realmGet$qb_userid());
        builder.addInteger(columnInfo.tot_contactsColKey, realmObjectSource.realmGet$tot_contacts());
        builder.addInteger(columnInfo.tot_groupsColKey, realmObjectSource.realmGet$tot_groups());
        builder.addInteger(columnInfo.tot_caseroomsColKey, realmObjectSource.realmGet$tot_caserooms());
        builder.addBoolean(columnInfo.rememberMeColKey, realmObjectSource.realmGet$rememberMe());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmBasicInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmBasicInfoColumnInfo columnInfo = (RealmBasicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$doc_id = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$doc_id();
        if (realmGet$doc_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, realmGet$doc_id.longValue(), false);
        }
        String realmGet$fname = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$fname();
        if (realmGet$fname != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fnameColKey, colKey, realmGet$fname, false);
        }
        String realmGet$lname = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$lname();
        if (realmGet$lname != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lnameColKey, colKey, realmGet$lname, false);
        }
        String realmGet$email = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
        }
        String realmGet$psswd = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$psswd();
        if (realmGet$psswd != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.psswdColKey, colKey, realmGet$psswd, false);
        }
        String realmGet$splty = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$splty();
        if (realmGet$splty != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.spltyColKey, colKey, realmGet$splty, false);
        }
        String realmGet$subSpeciality = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$subSpeciality();
        if (realmGet$subSpeciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subSpecialityColKey, colKey, realmGet$subSpeciality, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.mobileVerifiedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$mobileVerified(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.emailVerifiedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$emailVerified(), false);
        String realmGet$phone_num = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$phone_num();
        if (realmGet$phone_num != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phone_numColKey, colKey, realmGet$phone_num, false);
        }
        String realmGet$about_me = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$about_me();
        if (realmGet$about_me != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.about_meColKey, colKey, realmGet$about_me, false);
        }
        String realmGet$profile_pic_path = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$profile_pic_path();
        if (realmGet$profile_pic_path != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.profile_pic_pathColKey, colKey, realmGet$profile_pic_path, false);
        }
        String realmGet$reg_card_path = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$reg_card_path();
        if (realmGet$reg_card_path != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.reg_card_pathColKey, colKey, realmGet$reg_card_path, false);
        }
        String realmGet$pic_name = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$pic_name();
        if (realmGet$pic_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pic_nameColKey, colKey, realmGet$pic_name, false);
        }
        String realmGet$pic_url = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$pic_url();
        if (realmGet$pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pic_urlColKey, colKey, realmGet$pic_url, false);
        }
        String realmGet$website = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$website();
        if (realmGet$website != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.websiteColKey, colKey, realmGet$website, false);
        }
        String realmGet$blog_page = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$blog_page();
        if (realmGet$blog_page != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.blog_pageColKey, colKey, realmGet$blog_page, false);
        }
        String realmGet$fb_page = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$fb_page();
        if (realmGet$fb_page != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fb_pageColKey, colKey, realmGet$fb_page, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.phone_num_visibilityColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$phone_num_visibility(), false);
        String realmGet$emailvalidation = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$emailvalidation();
        if (realmGet$emailvalidation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailvalidationColKey, colKey, realmGet$emailvalidation, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.email_visibilityColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$email_visibility(), false);
        String realmGet$qb_login = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_login();
        if (realmGet$qb_login != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_loginColKey, colKey, realmGet$qb_login, false);
        }
        String realmGet$qb_hidden_dialog_id = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_hidden_dialog_id();
        if (realmGet$qb_hidden_dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_hidden_dialog_idColKey, colKey, realmGet$qb_hidden_dialog_id, false);
        }
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$user_type_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.feedCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$feedCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.likesCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$likesCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.shareCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$shareCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.commentsCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$commentsCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.followersCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$followersCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.followingCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$followingCount(), false);
        String realmGet$specificAsk = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$specificAsk();
        if (realmGet$specificAsk != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.specificAskColKey, colKey, realmGet$specificAsk, false);
        }
        String realmGet$linkedInPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$linkedInPg();
        if (realmGet$linkedInPg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.linkedInPgColKey, colKey, realmGet$linkedInPg, false);
        }
        String realmGet$twitterPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$twitterPg();
        if (realmGet$twitterPg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.twitterPgColKey, colKey, realmGet$twitterPg, false);
        }
        String realmGet$instagramPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$instagramPg();
        if (realmGet$instagramPg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.instagramPgColKey, colKey, realmGet$instagramPg, false);
        }
        String realmGet$docProfileURL = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$docProfileURL();
        if (realmGet$docProfileURL != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.docProfileURLColKey, colKey, realmGet$docProfileURL, false);
        }
        String realmGet$docProfilePdfURL = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$docProfilePdfURL();
        if (realmGet$docProfilePdfURL != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.docProfilePdfURLColKey, colKey, realmGet$docProfilePdfURL, false);
        }
        String realmGet$userUUID = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$userUUID();
        if (realmGet$userUUID != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.userUUIDColKey, colKey, realmGet$userUUID, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.overAllExperienceColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$overAllExperience(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.qb_useridColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_userid(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.tot_contactsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_contacts(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.tot_groupsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_groups(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.tot_caseroomsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_caserooms(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.rememberMeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$rememberMe(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmBasicInfoColumnInfo columnInfo = (RealmBasicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        com.vam.whitecoats.core.realm.RealmBasicInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmBasicInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$doc_id = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$doc_id();
            if (realmGet$doc_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, realmGet$doc_id.longValue(), false);
            }
            String realmGet$fname = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$fname();
            if (realmGet$fname != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.fnameColKey, colKey, realmGet$fname, false);
            }
            String realmGet$lname = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$lname();
            if (realmGet$lname != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lnameColKey, colKey, realmGet$lname, false);
            }
            String realmGet$email = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
            }
            String realmGet$psswd = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$psswd();
            if (realmGet$psswd != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.psswdColKey, colKey, realmGet$psswd, false);
            }
            String realmGet$splty = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$splty();
            if (realmGet$splty != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.spltyColKey, colKey, realmGet$splty, false);
            }
            String realmGet$subSpeciality = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$subSpeciality();
            if (realmGet$subSpeciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.subSpecialityColKey, colKey, realmGet$subSpeciality, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.mobileVerifiedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$mobileVerified(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.emailVerifiedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$emailVerified(), false);
            String realmGet$phone_num = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$phone_num();
            if (realmGet$phone_num != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phone_numColKey, colKey, realmGet$phone_num, false);
            }
            String realmGet$about_me = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$about_me();
            if (realmGet$about_me != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.about_meColKey, colKey, realmGet$about_me, false);
            }
            String realmGet$profile_pic_path = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$profile_pic_path();
            if (realmGet$profile_pic_path != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.profile_pic_pathColKey, colKey, realmGet$profile_pic_path, false);
            }
            String realmGet$reg_card_path = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$reg_card_path();
            if (realmGet$reg_card_path != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.reg_card_pathColKey, colKey, realmGet$reg_card_path, false);
            }
            String realmGet$pic_name = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$pic_name();
            if (realmGet$pic_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pic_nameColKey, colKey, realmGet$pic_name, false);
            }
            String realmGet$pic_url = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$pic_url();
            if (realmGet$pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pic_urlColKey, colKey, realmGet$pic_url, false);
            }
            String realmGet$website = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$website();
            if (realmGet$website != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.websiteColKey, colKey, realmGet$website, false);
            }
            String realmGet$blog_page = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$blog_page();
            if (realmGet$blog_page != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.blog_pageColKey, colKey, realmGet$blog_page, false);
            }
            String realmGet$fb_page = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$fb_page();
            if (realmGet$fb_page != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.fb_pageColKey, colKey, realmGet$fb_page, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.phone_num_visibilityColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$phone_num_visibility(), false);
            String realmGet$emailvalidation = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$emailvalidation();
            if (realmGet$emailvalidation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailvalidationColKey, colKey, realmGet$emailvalidation, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.email_visibilityColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$email_visibility(), false);
            String realmGet$qb_login = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_login();
            if (realmGet$qb_login != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_loginColKey, colKey, realmGet$qb_login, false);
            }
            String realmGet$qb_hidden_dialog_id = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_hidden_dialog_id();
            if (realmGet$qb_hidden_dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_hidden_dialog_idColKey, colKey, realmGet$qb_hidden_dialog_id, false);
            }
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$user_type_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.feedCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$feedCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.likesCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$likesCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.shareCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$shareCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.commentsCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$commentsCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.followersCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$followersCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.followingCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$followingCount(), false);
            String realmGet$specificAsk = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$specificAsk();
            if (realmGet$specificAsk != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.specificAskColKey, colKey, realmGet$specificAsk, false);
            }
            String realmGet$linkedInPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$linkedInPg();
            if (realmGet$linkedInPg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.linkedInPgColKey, colKey, realmGet$linkedInPg, false);
            }
            String realmGet$twitterPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$twitterPg();
            if (realmGet$twitterPg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.twitterPgColKey, colKey, realmGet$twitterPg, false);
            }
            String realmGet$instagramPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$instagramPg();
            if (realmGet$instagramPg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.instagramPgColKey, colKey, realmGet$instagramPg, false);
            }
            String realmGet$docProfileURL = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$docProfileURL();
            if (realmGet$docProfileURL != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.docProfileURLColKey, colKey, realmGet$docProfileURL, false);
            }
            String realmGet$docProfilePdfURL = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$docProfilePdfURL();
            if (realmGet$docProfilePdfURL != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.docProfilePdfURLColKey, colKey, realmGet$docProfilePdfURL, false);
            }
            String realmGet$userUUID = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$userUUID();
            if (realmGet$userUUID != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.userUUIDColKey, colKey, realmGet$userUUID, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.overAllExperienceColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$overAllExperience(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.qb_useridColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_userid(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.tot_contactsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_contacts(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.tot_groupsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_groups(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.tot_caseroomsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_caserooms(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.rememberMeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$rememberMe(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmBasicInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmBasicInfoColumnInfo columnInfo = (RealmBasicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$doc_id = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$doc_id();
        if (realmGet$doc_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, realmGet$doc_id.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_idColKey, colKey, false);
        }
        String realmGet$fname = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$fname();
        if (realmGet$fname != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fnameColKey, colKey, realmGet$fname, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.fnameColKey, colKey, false);
        }
        String realmGet$lname = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$lname();
        if (realmGet$lname != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lnameColKey, colKey, realmGet$lname, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.lnameColKey, colKey, false);
        }
        String realmGet$email = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailColKey, colKey, false);
        }
        String realmGet$psswd = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$psswd();
        if (realmGet$psswd != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.psswdColKey, colKey, realmGet$psswd, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.psswdColKey, colKey, false);
        }
        String realmGet$splty = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$splty();
        if (realmGet$splty != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.spltyColKey, colKey, realmGet$splty, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.spltyColKey, colKey, false);
        }
        String realmGet$subSpeciality = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$subSpeciality();
        if (realmGet$subSpeciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subSpecialityColKey, colKey, realmGet$subSpeciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.subSpecialityColKey, colKey, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.mobileVerifiedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$mobileVerified(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.emailVerifiedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$emailVerified(), false);
        String realmGet$phone_num = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$phone_num();
        if (realmGet$phone_num != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phone_numColKey, colKey, realmGet$phone_num, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phone_numColKey, colKey, false);
        }
        String realmGet$about_me = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$about_me();
        if (realmGet$about_me != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.about_meColKey, colKey, realmGet$about_me, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.about_meColKey, colKey, false);
        }
        String realmGet$profile_pic_path = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$profile_pic_path();
        if (realmGet$profile_pic_path != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.profile_pic_pathColKey, colKey, realmGet$profile_pic_path, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.profile_pic_pathColKey, colKey, false);
        }
        String realmGet$reg_card_path = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$reg_card_path();
        if (realmGet$reg_card_path != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.reg_card_pathColKey, colKey, realmGet$reg_card_path, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.reg_card_pathColKey, colKey, false);
        }
        String realmGet$pic_name = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$pic_name();
        if (realmGet$pic_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pic_nameColKey, colKey, realmGet$pic_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.pic_nameColKey, colKey, false);
        }
        String realmGet$pic_url = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$pic_url();
        if (realmGet$pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pic_urlColKey, colKey, realmGet$pic_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.pic_urlColKey, colKey, false);
        }
        String realmGet$website = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$website();
        if (realmGet$website != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.websiteColKey, colKey, realmGet$website, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.websiteColKey, colKey, false);
        }
        String realmGet$blog_page = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$blog_page();
        if (realmGet$blog_page != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.blog_pageColKey, colKey, realmGet$blog_page, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.blog_pageColKey, colKey, false);
        }
        String realmGet$fb_page = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$fb_page();
        if (realmGet$fb_page != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fb_pageColKey, colKey, realmGet$fb_page, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.fb_pageColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.phone_num_visibilityColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$phone_num_visibility(), false);
        String realmGet$emailvalidation = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$emailvalidation();
        if (realmGet$emailvalidation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailvalidationColKey, colKey, realmGet$emailvalidation, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailvalidationColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.email_visibilityColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$email_visibility(), false);
        String realmGet$qb_login = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_login();
        if (realmGet$qb_login != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_loginColKey, colKey, realmGet$qb_login, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.qb_loginColKey, colKey, false);
        }
        String realmGet$qb_hidden_dialog_id = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_hidden_dialog_id();
        if (realmGet$qb_hidden_dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_hidden_dialog_idColKey, colKey, realmGet$qb_hidden_dialog_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.qb_hidden_dialog_idColKey, colKey, false);
        }
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$user_type_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.feedCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$feedCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.likesCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$likesCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.shareCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$shareCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.commentsCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$commentsCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.followersCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$followersCount(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.followingCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$followingCount(), false);
        String realmGet$specificAsk = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$specificAsk();
        if (realmGet$specificAsk != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.specificAskColKey, colKey, realmGet$specificAsk, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.specificAskColKey, colKey, false);
        }
        String realmGet$linkedInPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$linkedInPg();
        if (realmGet$linkedInPg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.linkedInPgColKey, colKey, realmGet$linkedInPg, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.linkedInPgColKey, colKey, false);
        }
        String realmGet$twitterPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$twitterPg();
        if (realmGet$twitterPg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.twitterPgColKey, colKey, realmGet$twitterPg, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.twitterPgColKey, colKey, false);
        }
        String realmGet$instagramPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$instagramPg();
        if (realmGet$instagramPg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.instagramPgColKey, colKey, realmGet$instagramPg, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.instagramPgColKey, colKey, false);
        }
        String realmGet$docProfileURL = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$docProfileURL();
        if (realmGet$docProfileURL != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.docProfileURLColKey, colKey, realmGet$docProfileURL, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.docProfileURLColKey, colKey, false);
        }
        String realmGet$docProfilePdfURL = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$docProfilePdfURL();
        if (realmGet$docProfilePdfURL != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.docProfilePdfURLColKey, colKey, realmGet$docProfilePdfURL, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.docProfilePdfURLColKey, colKey, false);
        }
        String realmGet$userUUID = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$userUUID();
        if (realmGet$userUUID != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.userUUIDColKey, colKey, realmGet$userUUID, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.userUUIDColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.overAllExperienceColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$overAllExperience(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.qb_useridColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_userid(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.tot_contactsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_contacts(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.tot_groupsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_groups(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.tot_caseroomsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_caserooms(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.rememberMeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$rememberMe(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmBasicInfoColumnInfo columnInfo = (RealmBasicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        com.vam.whitecoats.core.realm.RealmBasicInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmBasicInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$doc_id = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$doc_id();
            if (realmGet$doc_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, realmGet$doc_id.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_idColKey, colKey, false);
            }
            String realmGet$fname = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$fname();
            if (realmGet$fname != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.fnameColKey, colKey, realmGet$fname, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.fnameColKey, colKey, false);
            }
            String realmGet$lname = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$lname();
            if (realmGet$lname != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.lnameColKey, colKey, realmGet$lname, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.lnameColKey, colKey, false);
            }
            String realmGet$email = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.emailColKey, colKey, false);
            }
            String realmGet$psswd = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$psswd();
            if (realmGet$psswd != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.psswdColKey, colKey, realmGet$psswd, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.psswdColKey, colKey, false);
            }
            String realmGet$splty = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$splty();
            if (realmGet$splty != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.spltyColKey, colKey, realmGet$splty, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.spltyColKey, colKey, false);
            }
            String realmGet$subSpeciality = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$subSpeciality();
            if (realmGet$subSpeciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.subSpecialityColKey, colKey, realmGet$subSpeciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.subSpecialityColKey, colKey, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.mobileVerifiedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$mobileVerified(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.emailVerifiedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$emailVerified(), false);
            String realmGet$phone_num = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$phone_num();
            if (realmGet$phone_num != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phone_numColKey, colKey, realmGet$phone_num, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phone_numColKey, colKey, false);
            }
            String realmGet$about_me = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$about_me();
            if (realmGet$about_me != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.about_meColKey, colKey, realmGet$about_me, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.about_meColKey, colKey, false);
            }
            String realmGet$profile_pic_path = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$profile_pic_path();
            if (realmGet$profile_pic_path != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.profile_pic_pathColKey, colKey, realmGet$profile_pic_path, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.profile_pic_pathColKey, colKey, false);
            }
            String realmGet$reg_card_path = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$reg_card_path();
            if (realmGet$reg_card_path != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.reg_card_pathColKey, colKey, realmGet$reg_card_path, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.reg_card_pathColKey, colKey, false);
            }
            String realmGet$pic_name = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$pic_name();
            if (realmGet$pic_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pic_nameColKey, colKey, realmGet$pic_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.pic_nameColKey, colKey, false);
            }
            String realmGet$pic_url = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$pic_url();
            if (realmGet$pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pic_urlColKey, colKey, realmGet$pic_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.pic_urlColKey, colKey, false);
            }
            String realmGet$website = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$website();
            if (realmGet$website != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.websiteColKey, colKey, realmGet$website, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.websiteColKey, colKey, false);
            }
            String realmGet$blog_page = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$blog_page();
            if (realmGet$blog_page != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.blog_pageColKey, colKey, realmGet$blog_page, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.blog_pageColKey, colKey, false);
            }
            String realmGet$fb_page = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$fb_page();
            if (realmGet$fb_page != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.fb_pageColKey, colKey, realmGet$fb_page, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.fb_pageColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.phone_num_visibilityColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$phone_num_visibility(), false);
            String realmGet$emailvalidation = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$emailvalidation();
            if (realmGet$emailvalidation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailvalidationColKey, colKey, realmGet$emailvalidation, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.emailvalidationColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.email_visibilityColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$email_visibility(), false);
            String realmGet$qb_login = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_login();
            if (realmGet$qb_login != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_loginColKey, colKey, realmGet$qb_login, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.qb_loginColKey, colKey, false);
            }
            String realmGet$qb_hidden_dialog_id = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_hidden_dialog_id();
            if (realmGet$qb_hidden_dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_hidden_dialog_idColKey, colKey, realmGet$qb_hidden_dialog_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.qb_hidden_dialog_idColKey, colKey, false);
            }
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$user_type_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.feedCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$feedCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.likesCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$likesCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.shareCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$shareCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.commentsCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$commentsCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.followersCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$followersCount(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.followingCountColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$followingCount(), false);
            String realmGet$specificAsk = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$specificAsk();
            if (realmGet$specificAsk != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.specificAskColKey, colKey, realmGet$specificAsk, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.specificAskColKey, colKey, false);
            }
            String realmGet$linkedInPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$linkedInPg();
            if (realmGet$linkedInPg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.linkedInPgColKey, colKey, realmGet$linkedInPg, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.linkedInPgColKey, colKey, false);
            }
            String realmGet$twitterPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$twitterPg();
            if (realmGet$twitterPg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.twitterPgColKey, colKey, realmGet$twitterPg, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.twitterPgColKey, colKey, false);
            }
            String realmGet$instagramPg = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$instagramPg();
            if (realmGet$instagramPg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.instagramPgColKey, colKey, realmGet$instagramPg, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.instagramPgColKey, colKey, false);
            }
            String realmGet$docProfileURL = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$docProfileURL();
            if (realmGet$docProfileURL != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.docProfileURLColKey, colKey, realmGet$docProfileURL, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.docProfileURLColKey, colKey, false);
            }
            String realmGet$docProfilePdfURL = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$docProfilePdfURL();
            if (realmGet$docProfilePdfURL != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.docProfilePdfURLColKey, colKey, realmGet$docProfilePdfURL, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.docProfilePdfURLColKey, colKey, false);
            }
            String realmGet$userUUID = ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$userUUID();
            if (realmGet$userUUID != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.userUUIDColKey, colKey, realmGet$userUUID, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.userUUIDColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.overAllExperienceColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$overAllExperience(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.qb_useridColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$qb_userid(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.tot_contactsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_contacts(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.tot_groupsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_groups(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.tot_caseroomsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$tot_caserooms(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.rememberMeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) object).realmGet$rememberMe(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmBasicInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmBasicInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmBasicInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmBasicInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmBasicInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmBasicInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$doc_id(realmSource.realmGet$doc_id());
        unmanagedCopy.realmSet$fname(realmSource.realmGet$fname());
        unmanagedCopy.realmSet$lname(realmSource.realmGet$lname());
        unmanagedCopy.realmSet$email(realmSource.realmGet$email());
        unmanagedCopy.realmSet$psswd(realmSource.realmGet$psswd());
        unmanagedCopy.realmSet$splty(realmSource.realmGet$splty());
        unmanagedCopy.realmSet$subSpeciality(realmSource.realmGet$subSpeciality());
        unmanagedCopy.realmSet$mobileVerified(realmSource.realmGet$mobileVerified());
        unmanagedCopy.realmSet$emailVerified(realmSource.realmGet$emailVerified());
        unmanagedCopy.realmSet$phone_num(realmSource.realmGet$phone_num());
        unmanagedCopy.realmSet$about_me(realmSource.realmGet$about_me());
        unmanagedCopy.realmSet$profile_pic_path(realmSource.realmGet$profile_pic_path());
        unmanagedCopy.realmSet$reg_card_path(realmSource.realmGet$reg_card_path());
        unmanagedCopy.realmSet$pic_name(realmSource.realmGet$pic_name());
        unmanagedCopy.realmSet$pic_url(realmSource.realmGet$pic_url());
        unmanagedCopy.realmSet$website(realmSource.realmGet$website());
        unmanagedCopy.realmSet$blog_page(realmSource.realmGet$blog_page());
        unmanagedCopy.realmSet$fb_page(realmSource.realmGet$fb_page());
        unmanagedCopy.realmSet$phone_num_visibility(realmSource.realmGet$phone_num_visibility());
        unmanagedCopy.realmSet$emailvalidation(realmSource.realmGet$emailvalidation());
        unmanagedCopy.realmSet$email_visibility(realmSource.realmGet$email_visibility());
        unmanagedCopy.realmSet$qb_login(realmSource.realmGet$qb_login());
        unmanagedCopy.realmSet$qb_hidden_dialog_id(realmSource.realmGet$qb_hidden_dialog_id());
        unmanagedCopy.realmSet$user_salutation(realmSource.realmGet$user_salutation());
        unmanagedCopy.realmSet$user_type_id(realmSource.realmGet$user_type_id());
        unmanagedCopy.realmSet$feedCount(realmSource.realmGet$feedCount());
        unmanagedCopy.realmSet$likesCount(realmSource.realmGet$likesCount());
        unmanagedCopy.realmSet$shareCount(realmSource.realmGet$shareCount());
        unmanagedCopy.realmSet$commentsCount(realmSource.realmGet$commentsCount());
        unmanagedCopy.realmSet$followersCount(realmSource.realmGet$followersCount());
        unmanagedCopy.realmSet$followingCount(realmSource.realmGet$followingCount());
        unmanagedCopy.realmSet$specificAsk(realmSource.realmGet$specificAsk());
        unmanagedCopy.realmSet$linkedInPg(realmSource.realmGet$linkedInPg());
        unmanagedCopy.realmSet$twitterPg(realmSource.realmGet$twitterPg());
        unmanagedCopy.realmSet$instagramPg(realmSource.realmGet$instagramPg());
        unmanagedCopy.realmSet$docProfileURL(realmSource.realmGet$docProfileURL());
        unmanagedCopy.realmSet$docProfilePdfURL(realmSource.realmGet$docProfilePdfURL());
        unmanagedCopy.realmSet$userUUID(realmSource.realmGet$userUUID());
        unmanagedCopy.realmSet$overAllExperience(realmSource.realmGet$overAllExperience());
        unmanagedCopy.realmSet$qb_userid(realmSource.realmGet$qb_userid());
        unmanagedCopy.realmSet$tot_contacts(realmSource.realmGet$tot_contacts());
        unmanagedCopy.realmSet$tot_groups(realmSource.realmGet$tot_groups());
        unmanagedCopy.realmSet$tot_caserooms(realmSource.realmGet$tot_caserooms());
        unmanagedCopy.realmSet$rememberMe(realmSource.realmGet$rememberMe());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmBasicInfo = proxy[");
        stringBuilder.append("{doc_id:");
        stringBuilder.append(realmGet$doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{fname:");
        stringBuilder.append(realmGet$fname() != null ? realmGet$fname() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lname:");
        stringBuilder.append(realmGet$lname() != null ? realmGet$lname() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email:");
        stringBuilder.append(realmGet$email() != null ? realmGet$email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{psswd:");
        stringBuilder.append(realmGet$psswd() != null ? realmGet$psswd() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{splty:");
        stringBuilder.append(realmGet$splty() != null ? realmGet$splty() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{subSpeciality:");
        stringBuilder.append(realmGet$subSpeciality() != null ? realmGet$subSpeciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{mobileVerified:");
        stringBuilder.append(realmGet$mobileVerified());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{emailVerified:");
        stringBuilder.append(realmGet$emailVerified());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phone_num:");
        stringBuilder.append(realmGet$phone_num() != null ? realmGet$phone_num() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{about_me:");
        stringBuilder.append(realmGet$about_me() != null ? realmGet$about_me() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{profile_pic_path:");
        stringBuilder.append(realmGet$profile_pic_path() != null ? realmGet$profile_pic_path() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{reg_card_path:");
        stringBuilder.append(realmGet$reg_card_path() != null ? realmGet$reg_card_path() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{pic_name:");
        stringBuilder.append(realmGet$pic_name() != null ? realmGet$pic_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{pic_url:");
        stringBuilder.append(realmGet$pic_url() != null ? realmGet$pic_url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{website:");
        stringBuilder.append(realmGet$website() != null ? realmGet$website() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{blog_page:");
        stringBuilder.append(realmGet$blog_page() != null ? realmGet$blog_page() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{fb_page:");
        stringBuilder.append(realmGet$fb_page() != null ? realmGet$fb_page() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phone_num_visibility:");
        stringBuilder.append(realmGet$phone_num_visibility());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{emailvalidation:");
        stringBuilder.append(realmGet$emailvalidation() != null ? realmGet$emailvalidation() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email_visibility:");
        stringBuilder.append(realmGet$email_visibility());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{qb_login:");
        stringBuilder.append(realmGet$qb_login() != null ? realmGet$qb_login() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{qb_hidden_dialog_id:");
        stringBuilder.append(realmGet$qb_hidden_dialog_id() != null ? realmGet$qb_hidden_dialog_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_salutation:");
        stringBuilder.append(realmGet$user_salutation() != null ? realmGet$user_salutation() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_type_id:");
        stringBuilder.append(realmGet$user_type_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{feedCount:");
        stringBuilder.append(realmGet$feedCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{likesCount:");
        stringBuilder.append(realmGet$likesCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{shareCount:");
        stringBuilder.append(realmGet$shareCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{commentsCount:");
        stringBuilder.append(realmGet$commentsCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{followersCount:");
        stringBuilder.append(realmGet$followersCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{followingCount:");
        stringBuilder.append(realmGet$followingCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{specificAsk:");
        stringBuilder.append(realmGet$specificAsk() != null ? realmGet$specificAsk() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{linkedInPg:");
        stringBuilder.append(realmGet$linkedInPg() != null ? realmGet$linkedInPg() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{twitterPg:");
        stringBuilder.append(realmGet$twitterPg() != null ? realmGet$twitterPg() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{instagramPg:");
        stringBuilder.append(realmGet$instagramPg() != null ? realmGet$instagramPg() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{docProfileURL:");
        stringBuilder.append(realmGet$docProfileURL() != null ? realmGet$docProfileURL() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{docProfilePdfURL:");
        stringBuilder.append(realmGet$docProfilePdfURL() != null ? realmGet$docProfilePdfURL() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userUUID:");
        stringBuilder.append(realmGet$userUUID() != null ? realmGet$userUUID() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{overAllExperience:");
        stringBuilder.append(realmGet$overAllExperience());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{qb_userid:");
        stringBuilder.append(realmGet$qb_userid());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tot_contacts:");
        stringBuilder.append(realmGet$tot_contacts());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tot_groups:");
        stringBuilder.append(realmGet$tot_groups());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tot_caserooms:");
        stringBuilder.append(realmGet$tot_caserooms());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{rememberMe:");
        stringBuilder.append(realmGet$rememberMe());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long colKey = proxyState.getRow$realm().getObjectKey();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (colKey ^ (colKey >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy aRealmBasicInfo = (com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmBasicInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmBasicInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmBasicInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
