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
public class com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy extends com.vam.whitecoats.core.realm.RealmCaseRoomNotifications
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface {

    static final class RealmCaseRoomNotificationsColumnInfo extends ColumnInfo {
        long caseroom_notification_idColKey;
        long caseroom_idColKey;
        long caseroom_summary_idColKey;
        long caseroom_group_created_dateColKey;
        long caseroom_group_xmpp_jidColKey;
        long case_headingColKey;
        long case_specialityColKey;
        long case_sub_specialityColKey;
        long time_receivedColKey;
        long doc_idColKey;
        long doc_qb_user_idColKey;
        long doc_nameColKey;
        long doc_specialityColKey;
        long subSpecialityColKey;
        long doc_workplaceColKey;
        long doc_locationColKey;
        long doc_cnt_emailColKey;
        long doc_cnt_numColKey;
        long doc_email_visColKey;
        long doc_phno_visColKey;
        long doc_pic_nameColKey;
        long doc_pic_urlColKey;
        long caseroom_notify_typeColKey;
        long caseroom_ack_statusColKey;
        long count_statusColKey;
        long user_salutationColKey;
        long user_type_idColKey;

        RealmCaseRoomNotificationsColumnInfo(OsSchemaInfo schemaInfo) {
            super(27);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmCaseRoomNotifications");
            this.caseroom_notification_idColKey = addColumnDetails("caseroom_notification_id", "caseroom_notification_id", objectSchemaInfo);
            this.caseroom_idColKey = addColumnDetails("caseroom_id", "caseroom_id", objectSchemaInfo);
            this.caseroom_summary_idColKey = addColumnDetails("caseroom_summary_id", "caseroom_summary_id", objectSchemaInfo);
            this.caseroom_group_created_dateColKey = addColumnDetails("caseroom_group_created_date", "caseroom_group_created_date", objectSchemaInfo);
            this.caseroom_group_xmpp_jidColKey = addColumnDetails("caseroom_group_xmpp_jid", "caseroom_group_xmpp_jid", objectSchemaInfo);
            this.case_headingColKey = addColumnDetails("case_heading", "case_heading", objectSchemaInfo);
            this.case_specialityColKey = addColumnDetails("case_speciality", "case_speciality", objectSchemaInfo);
            this.case_sub_specialityColKey = addColumnDetails("case_sub_speciality", "case_sub_speciality", objectSchemaInfo);
            this.time_receivedColKey = addColumnDetails("time_received", "time_received", objectSchemaInfo);
            this.doc_idColKey = addColumnDetails("doc_id", "doc_id", objectSchemaInfo);
            this.doc_qb_user_idColKey = addColumnDetails("doc_qb_user_id", "doc_qb_user_id", objectSchemaInfo);
            this.doc_nameColKey = addColumnDetails("doc_name", "doc_name", objectSchemaInfo);
            this.doc_specialityColKey = addColumnDetails("doc_speciality", "doc_speciality", objectSchemaInfo);
            this.subSpecialityColKey = addColumnDetails("subSpeciality", "subSpeciality", objectSchemaInfo);
            this.doc_workplaceColKey = addColumnDetails("doc_workplace", "doc_workplace", objectSchemaInfo);
            this.doc_locationColKey = addColumnDetails("doc_location", "doc_location", objectSchemaInfo);
            this.doc_cnt_emailColKey = addColumnDetails("doc_cnt_email", "doc_cnt_email", objectSchemaInfo);
            this.doc_cnt_numColKey = addColumnDetails("doc_cnt_num", "doc_cnt_num", objectSchemaInfo);
            this.doc_email_visColKey = addColumnDetails("doc_email_vis", "doc_email_vis", objectSchemaInfo);
            this.doc_phno_visColKey = addColumnDetails("doc_phno_vis", "doc_phno_vis", objectSchemaInfo);
            this.doc_pic_nameColKey = addColumnDetails("doc_pic_name", "doc_pic_name", objectSchemaInfo);
            this.doc_pic_urlColKey = addColumnDetails("doc_pic_url", "doc_pic_url", objectSchemaInfo);
            this.caseroom_notify_typeColKey = addColumnDetails("caseroom_notify_type", "caseroom_notify_type", objectSchemaInfo);
            this.caseroom_ack_statusColKey = addColumnDetails("caseroom_ack_status", "caseroom_ack_status", objectSchemaInfo);
            this.count_statusColKey = addColumnDetails("count_status", "count_status", objectSchemaInfo);
            this.user_salutationColKey = addColumnDetails("user_salutation", "user_salutation", objectSchemaInfo);
            this.user_type_idColKey = addColumnDetails("user_type_id", "user_type_id", objectSchemaInfo);
        }

        RealmCaseRoomNotificationsColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmCaseRoomNotificationsColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmCaseRoomNotificationsColumnInfo src = (RealmCaseRoomNotificationsColumnInfo) rawSrc;
            final RealmCaseRoomNotificationsColumnInfo dst = (RealmCaseRoomNotificationsColumnInfo) rawDst;
            dst.caseroom_notification_idColKey = src.caseroom_notification_idColKey;
            dst.caseroom_idColKey = src.caseroom_idColKey;
            dst.caseroom_summary_idColKey = src.caseroom_summary_idColKey;
            dst.caseroom_group_created_dateColKey = src.caseroom_group_created_dateColKey;
            dst.caseroom_group_xmpp_jidColKey = src.caseroom_group_xmpp_jidColKey;
            dst.case_headingColKey = src.case_headingColKey;
            dst.case_specialityColKey = src.case_specialityColKey;
            dst.case_sub_specialityColKey = src.case_sub_specialityColKey;
            dst.time_receivedColKey = src.time_receivedColKey;
            dst.doc_idColKey = src.doc_idColKey;
            dst.doc_qb_user_idColKey = src.doc_qb_user_idColKey;
            dst.doc_nameColKey = src.doc_nameColKey;
            dst.doc_specialityColKey = src.doc_specialityColKey;
            dst.subSpecialityColKey = src.subSpecialityColKey;
            dst.doc_workplaceColKey = src.doc_workplaceColKey;
            dst.doc_locationColKey = src.doc_locationColKey;
            dst.doc_cnt_emailColKey = src.doc_cnt_emailColKey;
            dst.doc_cnt_numColKey = src.doc_cnt_numColKey;
            dst.doc_email_visColKey = src.doc_email_visColKey;
            dst.doc_phno_visColKey = src.doc_phno_visColKey;
            dst.doc_pic_nameColKey = src.doc_pic_nameColKey;
            dst.doc_pic_urlColKey = src.doc_pic_urlColKey;
            dst.caseroom_notify_typeColKey = src.caseroom_notify_typeColKey;
            dst.caseroom_ack_statusColKey = src.caseroom_ack_statusColKey;
            dst.count_statusColKey = src.count_statusColKey;
            dst.user_salutationColKey = src.user_salutationColKey;
            dst.user_type_idColKey = src.user_type_idColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmCaseRoomNotificationsColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomNotifications> proxyState;

    com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmCaseRoomNotificationsColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomNotifications>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caseroom_notification_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.caseroom_notification_idColKey);
    }

    @Override
    public void realmSet$caseroom_notification_id(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'caseroom_notification_id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caseroom_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.caseroom_idColKey);
    }

    @Override
    public void realmSet$caseroom_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.caseroom_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.caseroom_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.caseroom_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.caseroom_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caseroom_summary_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.caseroom_summary_idColKey);
    }

    @Override
    public void realmSet$caseroom_summary_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.caseroom_summary_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.caseroom_summary_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.caseroom_summary_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.caseroom_summary_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$caseroom_group_created_date() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.caseroom_group_created_dateColKey);
    }

    @Override
    public void realmSet$caseroom_group_created_date(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.caseroom_group_created_dateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.caseroom_group_created_dateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caseroom_group_xmpp_jid() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.caseroom_group_xmpp_jidColKey);
    }

    @Override
    public void realmSet$caseroom_group_xmpp_jid(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.caseroom_group_xmpp_jidColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.caseroom_group_xmpp_jidColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.caseroom_group_xmpp_jidColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.caseroom_group_xmpp_jidColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$case_heading() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.case_headingColKey);
    }

    @Override
    public void realmSet$case_heading(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.case_headingColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.case_headingColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.case_headingColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.case_headingColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$case_speciality() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.case_specialityColKey);
    }

    @Override
    public void realmSet$case_speciality(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.case_specialityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.case_specialityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.case_specialityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.case_specialityColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$case_sub_speciality() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.case_sub_specialityColKey);
    }

    @Override
    public void realmSet$case_sub_speciality(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.case_sub_specialityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.case_sub_specialityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.case_sub_specialityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.case_sub_specialityColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$time_received() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.time_receivedColKey);
    }

    @Override
    public void realmSet$time_received(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.time_receivedColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.time_receivedColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$doc_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.doc_idColKey);
    }

    @Override
    public void realmSet$doc_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.doc_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.doc_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_qb_user_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_qb_user_idColKey);
    }

    @Override
    public void realmSet$doc_qb_user_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_qb_user_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_qb_user_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_qb_user_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_qb_user_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_nameColKey);
    }

    @Override
    public void realmSet$doc_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_speciality() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_specialityColKey);
    }

    @Override
    public void realmSet$doc_speciality(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_specialityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_specialityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_specialityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_specialityColKey, value);
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
    public String realmGet$doc_workplace() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_workplaceColKey);
    }

    @Override
    public void realmSet$doc_workplace(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_workplaceColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_workplaceColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_workplaceColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_workplaceColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_location() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_locationColKey);
    }

    @Override
    public void realmSet$doc_location(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_locationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_locationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_locationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_locationColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_cnt_email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_cnt_emailColKey);
    }

    @Override
    public void realmSet$doc_cnt_email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_cnt_emailColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_cnt_emailColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_cnt_emailColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_cnt_emailColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_cnt_num() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_cnt_numColKey);
    }

    @Override
    public void realmSet$doc_cnt_num(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_cnt_numColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_cnt_numColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_cnt_numColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_cnt_numColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_email_vis() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_email_visColKey);
    }

    @Override
    public void realmSet$doc_email_vis(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_email_visColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_email_visColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_email_visColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_email_visColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_phno_vis() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_phno_visColKey);
    }

    @Override
    public void realmSet$doc_phno_vis(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_phno_visColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_phno_visColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_phno_visColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_phno_visColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_pic_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_pic_nameColKey);
    }

    @Override
    public void realmSet$doc_pic_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_pic_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_pic_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_pic_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_pic_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_pic_url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_pic_urlColKey);
    }

    @Override
    public void realmSet$doc_pic_url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_pic_urlColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_pic_urlColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_pic_urlColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_pic_urlColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caseroom_notify_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.caseroom_notify_typeColKey);
    }

    @Override
    public void realmSet$caseroom_notify_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.caseroom_notify_typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.caseroom_notify_typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.caseroom_notify_typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.caseroom_notify_typeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$caseroom_ack_status() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.caseroom_ack_statusColKey);
    }

    @Override
    public void realmSet$caseroom_ack_status(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.caseroom_ack_statusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.caseroom_ack_statusColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$count_status() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.count_statusColKey);
    }

    @Override
    public void realmSet$count_status(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.count_statusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.count_statusColKey, value);
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmCaseRoomNotifications", 27, 0);
        builder.addPersistedProperty("caseroom_notification_id", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("caseroom_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("caseroom_summary_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("caseroom_group_created_date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("caseroom_group_xmpp_jid", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("case_heading", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("case_speciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("case_sub_speciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("time_received", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("doc_qb_user_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_speciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("subSpeciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_workplace", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_location", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_cnt_email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_cnt_num", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_email_vis", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_phno_vis", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_pic_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_pic_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("caseroom_notify_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("caseroom_ack_status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("count_status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("user_salutation", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("user_type_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmCaseRoomNotificationsColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmCaseRoomNotificationsColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmCaseRoomNotifications";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmCaseRoomNotifications";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmCaseRoomNotifications createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmCaseRoomNotifications obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
            RealmCaseRoomNotificationsColumnInfo columnInfo = (RealmCaseRoomNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
            long pkColumnKey = columnInfo.caseroom_notification_idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("caseroom_notification_id")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("caseroom_notification_id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("caseroom_notification_id")) {
                if (json.isNull("caseroom_notification_id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class, json.getString("caseroom_notification_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'caseroom_notification_id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) obj;
        if (json.has("caseroom_id")) {
            if (json.isNull("caseroom_id")) {
                objProxy.realmSet$caseroom_id(null);
            } else {
                objProxy.realmSet$caseroom_id((String) json.getString("caseroom_id"));
            }
        }
        if (json.has("caseroom_summary_id")) {
            if (json.isNull("caseroom_summary_id")) {
                objProxy.realmSet$caseroom_summary_id(null);
            } else {
                objProxy.realmSet$caseroom_summary_id((String) json.getString("caseroom_summary_id"));
            }
        }
        if (json.has("caseroom_group_created_date")) {
            if (json.isNull("caseroom_group_created_date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'caseroom_group_created_date' to null.");
            } else {
                objProxy.realmSet$caseroom_group_created_date((long) json.getLong("caseroom_group_created_date"));
            }
        }
        if (json.has("caseroom_group_xmpp_jid")) {
            if (json.isNull("caseroom_group_xmpp_jid")) {
                objProxy.realmSet$caseroom_group_xmpp_jid(null);
            } else {
                objProxy.realmSet$caseroom_group_xmpp_jid((String) json.getString("caseroom_group_xmpp_jid"));
            }
        }
        if (json.has("case_heading")) {
            if (json.isNull("case_heading")) {
                objProxy.realmSet$case_heading(null);
            } else {
                objProxy.realmSet$case_heading((String) json.getString("case_heading"));
            }
        }
        if (json.has("case_speciality")) {
            if (json.isNull("case_speciality")) {
                objProxy.realmSet$case_speciality(null);
            } else {
                objProxy.realmSet$case_speciality((String) json.getString("case_speciality"));
            }
        }
        if (json.has("case_sub_speciality")) {
            if (json.isNull("case_sub_speciality")) {
                objProxy.realmSet$case_sub_speciality(null);
            } else {
                objProxy.realmSet$case_sub_speciality((String) json.getString("case_sub_speciality"));
            }
        }
        if (json.has("time_received")) {
            if (json.isNull("time_received")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'time_received' to null.");
            } else {
                objProxy.realmSet$time_received((long) json.getLong("time_received"));
            }
        }
        if (json.has("doc_id")) {
            if (json.isNull("doc_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'doc_id' to null.");
            } else {
                objProxy.realmSet$doc_id((int) json.getInt("doc_id"));
            }
        }
        if (json.has("doc_qb_user_id")) {
            if (json.isNull("doc_qb_user_id")) {
                objProxy.realmSet$doc_qb_user_id(null);
            } else {
                objProxy.realmSet$doc_qb_user_id((String) json.getString("doc_qb_user_id"));
            }
        }
        if (json.has("doc_name")) {
            if (json.isNull("doc_name")) {
                objProxy.realmSet$doc_name(null);
            } else {
                objProxy.realmSet$doc_name((String) json.getString("doc_name"));
            }
        }
        if (json.has("doc_speciality")) {
            if (json.isNull("doc_speciality")) {
                objProxy.realmSet$doc_speciality(null);
            } else {
                objProxy.realmSet$doc_speciality((String) json.getString("doc_speciality"));
            }
        }
        if (json.has("subSpeciality")) {
            if (json.isNull("subSpeciality")) {
                objProxy.realmSet$subSpeciality(null);
            } else {
                objProxy.realmSet$subSpeciality((String) json.getString("subSpeciality"));
            }
        }
        if (json.has("doc_workplace")) {
            if (json.isNull("doc_workplace")) {
                objProxy.realmSet$doc_workplace(null);
            } else {
                objProxy.realmSet$doc_workplace((String) json.getString("doc_workplace"));
            }
        }
        if (json.has("doc_location")) {
            if (json.isNull("doc_location")) {
                objProxy.realmSet$doc_location(null);
            } else {
                objProxy.realmSet$doc_location((String) json.getString("doc_location"));
            }
        }
        if (json.has("doc_cnt_email")) {
            if (json.isNull("doc_cnt_email")) {
                objProxy.realmSet$doc_cnt_email(null);
            } else {
                objProxy.realmSet$doc_cnt_email((String) json.getString("doc_cnt_email"));
            }
        }
        if (json.has("doc_cnt_num")) {
            if (json.isNull("doc_cnt_num")) {
                objProxy.realmSet$doc_cnt_num(null);
            } else {
                objProxy.realmSet$doc_cnt_num((String) json.getString("doc_cnt_num"));
            }
        }
        if (json.has("doc_email_vis")) {
            if (json.isNull("doc_email_vis")) {
                objProxy.realmSet$doc_email_vis(null);
            } else {
                objProxy.realmSet$doc_email_vis((String) json.getString("doc_email_vis"));
            }
        }
        if (json.has("doc_phno_vis")) {
            if (json.isNull("doc_phno_vis")) {
                objProxy.realmSet$doc_phno_vis(null);
            } else {
                objProxy.realmSet$doc_phno_vis((String) json.getString("doc_phno_vis"));
            }
        }
        if (json.has("doc_pic_name")) {
            if (json.isNull("doc_pic_name")) {
                objProxy.realmSet$doc_pic_name(null);
            } else {
                objProxy.realmSet$doc_pic_name((String) json.getString("doc_pic_name"));
            }
        }
        if (json.has("doc_pic_url")) {
            if (json.isNull("doc_pic_url")) {
                objProxy.realmSet$doc_pic_url(null);
            } else {
                objProxy.realmSet$doc_pic_url((String) json.getString("doc_pic_url"));
            }
        }
        if (json.has("caseroom_notify_type")) {
            if (json.isNull("caseroom_notify_type")) {
                objProxy.realmSet$caseroom_notify_type(null);
            } else {
                objProxy.realmSet$caseroom_notify_type((String) json.getString("caseroom_notify_type"));
            }
        }
        if (json.has("caseroom_ack_status")) {
            if (json.isNull("caseroom_ack_status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'caseroom_ack_status' to null.");
            } else {
                objProxy.realmSet$caseroom_ack_status((int) json.getInt("caseroom_ack_status"));
            }
        }
        if (json.has("count_status")) {
            if (json.isNull("count_status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'count_status' to null.");
            } else {
                objProxy.realmSet$count_status((int) json.getInt("count_status"));
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
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmCaseRoomNotifications createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmCaseRoomNotifications obj = new com.vam.whitecoats.core.realm.RealmCaseRoomNotifications();
        final com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("caseroom_notification_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_notification_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroom_notification_id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("caseroom_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroom_id(null);
                }
            } else if (name.equals("caseroom_summary_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_summary_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroom_summary_id(null);
                }
            } else if (name.equals("caseroom_group_created_date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_group_created_date((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'caseroom_group_created_date' to null.");
                }
            } else if (name.equals("caseroom_group_xmpp_jid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_group_xmpp_jid((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroom_group_xmpp_jid(null);
                }
            } else if (name.equals("case_heading")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$case_heading((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$case_heading(null);
                }
            } else if (name.equals("case_speciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$case_speciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$case_speciality(null);
                }
            } else if (name.equals("case_sub_speciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$case_sub_speciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$case_sub_speciality(null);
                }
            } else if (name.equals("time_received")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$time_received((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'time_received' to null.");
                }
            } else if (name.equals("doc_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'doc_id' to null.");
                }
            } else if (name.equals("doc_qb_user_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_qb_user_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_qb_user_id(null);
                }
            } else if (name.equals("doc_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_name(null);
                }
            } else if (name.equals("doc_speciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_speciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_speciality(null);
                }
            } else if (name.equals("subSpeciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$subSpeciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$subSpeciality(null);
                }
            } else if (name.equals("doc_workplace")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_workplace((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_workplace(null);
                }
            } else if (name.equals("doc_location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_location(null);
                }
            } else if (name.equals("doc_cnt_email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_cnt_email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_cnt_email(null);
                }
            } else if (name.equals("doc_cnt_num")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_cnt_num((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_cnt_num(null);
                }
            } else if (name.equals("doc_email_vis")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_email_vis((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_email_vis(null);
                }
            } else if (name.equals("doc_phno_vis")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_phno_vis((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_phno_vis(null);
                }
            } else if (name.equals("doc_pic_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_pic_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_pic_name(null);
                }
            } else if (name.equals("doc_pic_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_pic_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_pic_url(null);
                }
            } else if (name.equals("caseroom_notify_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_notify_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroom_notify_type(null);
                }
            } else if (name.equals("caseroom_ack_status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_ack_status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'caseroom_ack_status' to null.");
                }
            } else if (name.equals("count_status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$count_status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'count_status' to null.");
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
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'caseroom_notification_id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomNotifications copyOrUpdate(Realm realm, RealmCaseRoomNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomNotifications object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmCaseRoomNotifications realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
            long pkColumnKey = columnInfo.caseroom_notification_idColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notification_id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomNotifications copy(Realm realm, RealmCaseRoomNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomNotifications newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.caseroom_notification_idColKey, realmObjectSource.realmGet$caseroom_notification_id());
        builder.addString(columnInfo.caseroom_idColKey, realmObjectSource.realmGet$caseroom_id());
        builder.addString(columnInfo.caseroom_summary_idColKey, realmObjectSource.realmGet$caseroom_summary_id());
        builder.addInteger(columnInfo.caseroom_group_created_dateColKey, realmObjectSource.realmGet$caseroom_group_created_date());
        builder.addString(columnInfo.caseroom_group_xmpp_jidColKey, realmObjectSource.realmGet$caseroom_group_xmpp_jid());
        builder.addString(columnInfo.case_headingColKey, realmObjectSource.realmGet$case_heading());
        builder.addString(columnInfo.case_specialityColKey, realmObjectSource.realmGet$case_speciality());
        builder.addString(columnInfo.case_sub_specialityColKey, realmObjectSource.realmGet$case_sub_speciality());
        builder.addInteger(columnInfo.time_receivedColKey, realmObjectSource.realmGet$time_received());
        builder.addInteger(columnInfo.doc_idColKey, realmObjectSource.realmGet$doc_id());
        builder.addString(columnInfo.doc_qb_user_idColKey, realmObjectSource.realmGet$doc_qb_user_id());
        builder.addString(columnInfo.doc_nameColKey, realmObjectSource.realmGet$doc_name());
        builder.addString(columnInfo.doc_specialityColKey, realmObjectSource.realmGet$doc_speciality());
        builder.addString(columnInfo.subSpecialityColKey, realmObjectSource.realmGet$subSpeciality());
        builder.addString(columnInfo.doc_workplaceColKey, realmObjectSource.realmGet$doc_workplace());
        builder.addString(columnInfo.doc_locationColKey, realmObjectSource.realmGet$doc_location());
        builder.addString(columnInfo.doc_cnt_emailColKey, realmObjectSource.realmGet$doc_cnt_email());
        builder.addString(columnInfo.doc_cnt_numColKey, realmObjectSource.realmGet$doc_cnt_num());
        builder.addString(columnInfo.doc_email_visColKey, realmObjectSource.realmGet$doc_email_vis());
        builder.addString(columnInfo.doc_phno_visColKey, realmObjectSource.realmGet$doc_phno_vis());
        builder.addString(columnInfo.doc_pic_nameColKey, realmObjectSource.realmGet$doc_pic_name());
        builder.addString(columnInfo.doc_pic_urlColKey, realmObjectSource.realmGet$doc_pic_url());
        builder.addString(columnInfo.caseroom_notify_typeColKey, realmObjectSource.realmGet$caseroom_notify_type());
        builder.addInteger(columnInfo.caseroom_ack_statusColKey, realmObjectSource.realmGet$caseroom_ack_status());
        builder.addInteger(columnInfo.count_statusColKey, realmObjectSource.realmGet$count_status());
        builder.addString(columnInfo.user_salutationColKey, realmObjectSource.realmGet$user_salutation());
        builder.addInteger(columnInfo.user_type_idColKey, realmObjectSource.realmGet$user_type_id());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomNotifications object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomNotificationsColumnInfo columnInfo = (RealmCaseRoomNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        long pkColumnKey = columnInfo.caseroom_notification_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notification_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$caseroom_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_id();
        if (realmGet$caseroom_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_idColKey, colKey, realmGet$caseroom_id, false);
        }
        String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_summary_id();
        if (realmGet$caseroom_summary_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.caseroom_group_created_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_group_created_date(), false);
        String realmGet$caseroom_group_xmpp_jid = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_group_xmpp_jid();
        if (realmGet$caseroom_group_xmpp_jid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_group_xmpp_jidColKey, colKey, realmGet$caseroom_group_xmpp_jid, false);
        }
        String realmGet$case_heading = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_heading();
        if (realmGet$case_heading != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.case_headingColKey, colKey, realmGet$case_heading, false);
        }
        String realmGet$case_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_speciality();
        if (realmGet$case_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.case_specialityColKey, colKey, realmGet$case_speciality, false);
        }
        String realmGet$case_sub_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_sub_speciality();
        if (realmGet$case_sub_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.case_sub_specialityColKey, colKey, realmGet$case_sub_speciality, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.time_receivedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$time_received(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_id(), false);
        String realmGet$doc_qb_user_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_qb_user_id();
        if (realmGet$doc_qb_user_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, realmGet$doc_qb_user_id, false);
        }
        String realmGet$doc_name = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_name();
        if (realmGet$doc_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_nameColKey, colKey, realmGet$doc_name, false);
        }
        String realmGet$doc_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_speciality();
        if (realmGet$doc_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_specialityColKey, colKey, realmGet$doc_speciality, false);
        }
        String realmGet$subSpeciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$subSpeciality();
        if (realmGet$subSpeciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subSpecialityColKey, colKey, realmGet$subSpeciality, false);
        }
        String realmGet$doc_workplace = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_workplace();
        if (realmGet$doc_workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, realmGet$doc_workplace, false);
        }
        String realmGet$doc_location = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_location();
        if (realmGet$doc_location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_locationColKey, colKey, realmGet$doc_location, false);
        }
        String realmGet$doc_cnt_email = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_cnt_email();
        if (realmGet$doc_cnt_email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_cnt_emailColKey, colKey, realmGet$doc_cnt_email, false);
        }
        String realmGet$doc_cnt_num = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_cnt_num();
        if (realmGet$doc_cnt_num != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_cnt_numColKey, colKey, realmGet$doc_cnt_num, false);
        }
        String realmGet$doc_email_vis = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_email_vis();
        if (realmGet$doc_email_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_email_visColKey, colKey, realmGet$doc_email_vis, false);
        }
        String realmGet$doc_phno_vis = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_phno_vis();
        if (realmGet$doc_phno_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, realmGet$doc_phno_vis, false);
        }
        String realmGet$doc_pic_name = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_pic_name();
        if (realmGet$doc_pic_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_nameColKey, colKey, realmGet$doc_pic_name, false);
        }
        String realmGet$doc_pic_url = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_pic_url();
        if (realmGet$doc_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, realmGet$doc_pic_url, false);
        }
        String realmGet$caseroom_notify_type = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notify_type();
        if (realmGet$caseroom_notify_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_notify_typeColKey, colKey, realmGet$caseroom_notify_type, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.caseroom_ack_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_ack_status(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomNotificationsColumnInfo columnInfo = (RealmCaseRoomNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        long pkColumnKey = columnInfo.caseroom_notification_idColKey;
        com.vam.whitecoats.core.realm.RealmCaseRoomNotifications object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notification_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$caseroom_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_id();
            if (realmGet$caseroom_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_idColKey, colKey, realmGet$caseroom_id, false);
            }
            String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_summary_id();
            if (realmGet$caseroom_summary_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.caseroom_group_created_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_group_created_date(), false);
            String realmGet$caseroom_group_xmpp_jid = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_group_xmpp_jid();
            if (realmGet$caseroom_group_xmpp_jid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_group_xmpp_jidColKey, colKey, realmGet$caseroom_group_xmpp_jid, false);
            }
            String realmGet$case_heading = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_heading();
            if (realmGet$case_heading != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.case_headingColKey, colKey, realmGet$case_heading, false);
            }
            String realmGet$case_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_speciality();
            if (realmGet$case_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.case_specialityColKey, colKey, realmGet$case_speciality, false);
            }
            String realmGet$case_sub_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_sub_speciality();
            if (realmGet$case_sub_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.case_sub_specialityColKey, colKey, realmGet$case_sub_speciality, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.time_receivedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$time_received(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_id(), false);
            String realmGet$doc_qb_user_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_qb_user_id();
            if (realmGet$doc_qb_user_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, realmGet$doc_qb_user_id, false);
            }
            String realmGet$doc_name = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_name();
            if (realmGet$doc_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_nameColKey, colKey, realmGet$doc_name, false);
            }
            String realmGet$doc_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_speciality();
            if (realmGet$doc_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_specialityColKey, colKey, realmGet$doc_speciality, false);
            }
            String realmGet$subSpeciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$subSpeciality();
            if (realmGet$subSpeciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.subSpecialityColKey, colKey, realmGet$subSpeciality, false);
            }
            String realmGet$doc_workplace = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_workplace();
            if (realmGet$doc_workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, realmGet$doc_workplace, false);
            }
            String realmGet$doc_location = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_location();
            if (realmGet$doc_location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_locationColKey, colKey, realmGet$doc_location, false);
            }
            String realmGet$doc_cnt_email = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_cnt_email();
            if (realmGet$doc_cnt_email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_cnt_emailColKey, colKey, realmGet$doc_cnt_email, false);
            }
            String realmGet$doc_cnt_num = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_cnt_num();
            if (realmGet$doc_cnt_num != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_cnt_numColKey, colKey, realmGet$doc_cnt_num, false);
            }
            String realmGet$doc_email_vis = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_email_vis();
            if (realmGet$doc_email_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_email_visColKey, colKey, realmGet$doc_email_vis, false);
            }
            String realmGet$doc_phno_vis = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_phno_vis();
            if (realmGet$doc_phno_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, realmGet$doc_phno_vis, false);
            }
            String realmGet$doc_pic_name = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_pic_name();
            if (realmGet$doc_pic_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_nameColKey, colKey, realmGet$doc_pic_name, false);
            }
            String realmGet$doc_pic_url = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_pic_url();
            if (realmGet$doc_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, realmGet$doc_pic_url, false);
            }
            String realmGet$caseroom_notify_type = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notify_type();
            if (realmGet$caseroom_notify_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_notify_typeColKey, colKey, realmGet$caseroom_notify_type, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.caseroom_ack_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_ack_status(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomNotifications object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomNotificationsColumnInfo columnInfo = (RealmCaseRoomNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        long pkColumnKey = columnInfo.caseroom_notification_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notification_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$caseroom_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_id();
        if (realmGet$caseroom_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_idColKey, colKey, realmGet$caseroom_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_idColKey, colKey, false);
        }
        String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_summary_id();
        if (realmGet$caseroom_summary_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.caseroom_group_created_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_group_created_date(), false);
        String realmGet$caseroom_group_xmpp_jid = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_group_xmpp_jid();
        if (realmGet$caseroom_group_xmpp_jid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_group_xmpp_jidColKey, colKey, realmGet$caseroom_group_xmpp_jid, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_group_xmpp_jidColKey, colKey, false);
        }
        String realmGet$case_heading = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_heading();
        if (realmGet$case_heading != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.case_headingColKey, colKey, realmGet$case_heading, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.case_headingColKey, colKey, false);
        }
        String realmGet$case_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_speciality();
        if (realmGet$case_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.case_specialityColKey, colKey, realmGet$case_speciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.case_specialityColKey, colKey, false);
        }
        String realmGet$case_sub_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_sub_speciality();
        if (realmGet$case_sub_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.case_sub_specialityColKey, colKey, realmGet$case_sub_speciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.case_sub_specialityColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.time_receivedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$time_received(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_id(), false);
        String realmGet$doc_qb_user_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_qb_user_id();
        if (realmGet$doc_qb_user_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, realmGet$doc_qb_user_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, false);
        }
        String realmGet$doc_name = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_name();
        if (realmGet$doc_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_nameColKey, colKey, realmGet$doc_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_nameColKey, colKey, false);
        }
        String realmGet$doc_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_speciality();
        if (realmGet$doc_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_specialityColKey, colKey, realmGet$doc_speciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_specialityColKey, colKey, false);
        }
        String realmGet$subSpeciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$subSpeciality();
        if (realmGet$subSpeciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subSpecialityColKey, colKey, realmGet$subSpeciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.subSpecialityColKey, colKey, false);
        }
        String realmGet$doc_workplace = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_workplace();
        if (realmGet$doc_workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, realmGet$doc_workplace, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, false);
        }
        String realmGet$doc_location = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_location();
        if (realmGet$doc_location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_locationColKey, colKey, realmGet$doc_location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_locationColKey, colKey, false);
        }
        String realmGet$doc_cnt_email = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_cnt_email();
        if (realmGet$doc_cnt_email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_cnt_emailColKey, colKey, realmGet$doc_cnt_email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_cnt_emailColKey, colKey, false);
        }
        String realmGet$doc_cnt_num = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_cnt_num();
        if (realmGet$doc_cnt_num != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_cnt_numColKey, colKey, realmGet$doc_cnt_num, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_cnt_numColKey, colKey, false);
        }
        String realmGet$doc_email_vis = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_email_vis();
        if (realmGet$doc_email_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_email_visColKey, colKey, realmGet$doc_email_vis, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_email_visColKey, colKey, false);
        }
        String realmGet$doc_phno_vis = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_phno_vis();
        if (realmGet$doc_phno_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, realmGet$doc_phno_vis, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, false);
        }
        String realmGet$doc_pic_name = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_pic_name();
        if (realmGet$doc_pic_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_nameColKey, colKey, realmGet$doc_pic_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_pic_nameColKey, colKey, false);
        }
        String realmGet$doc_pic_url = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_pic_url();
        if (realmGet$doc_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, realmGet$doc_pic_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, false);
        }
        String realmGet$caseroom_notify_type = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notify_type();
        if (realmGet$caseroom_notify_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_notify_typeColKey, colKey, realmGet$caseroom_notify_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_notify_typeColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.caseroom_ack_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_ack_status(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomNotificationsColumnInfo columnInfo = (RealmCaseRoomNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        long pkColumnKey = columnInfo.caseroom_notification_idColKey;
        com.vam.whitecoats.core.realm.RealmCaseRoomNotifications object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notification_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$caseroom_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_id();
            if (realmGet$caseroom_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_idColKey, colKey, realmGet$caseroom_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_idColKey, colKey, false);
            }
            String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_summary_id();
            if (realmGet$caseroom_summary_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.caseroom_group_created_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_group_created_date(), false);
            String realmGet$caseroom_group_xmpp_jid = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_group_xmpp_jid();
            if (realmGet$caseroom_group_xmpp_jid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_group_xmpp_jidColKey, colKey, realmGet$caseroom_group_xmpp_jid, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_group_xmpp_jidColKey, colKey, false);
            }
            String realmGet$case_heading = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_heading();
            if (realmGet$case_heading != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.case_headingColKey, colKey, realmGet$case_heading, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.case_headingColKey, colKey, false);
            }
            String realmGet$case_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_speciality();
            if (realmGet$case_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.case_specialityColKey, colKey, realmGet$case_speciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.case_specialityColKey, colKey, false);
            }
            String realmGet$case_sub_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$case_sub_speciality();
            if (realmGet$case_sub_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.case_sub_specialityColKey, colKey, realmGet$case_sub_speciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.case_sub_specialityColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.time_receivedColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$time_received(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_id(), false);
            String realmGet$doc_qb_user_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_qb_user_id();
            if (realmGet$doc_qb_user_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, realmGet$doc_qb_user_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, false);
            }
            String realmGet$doc_name = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_name();
            if (realmGet$doc_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_nameColKey, colKey, realmGet$doc_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_nameColKey, colKey, false);
            }
            String realmGet$doc_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_speciality();
            if (realmGet$doc_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_specialityColKey, colKey, realmGet$doc_speciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_specialityColKey, colKey, false);
            }
            String realmGet$subSpeciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$subSpeciality();
            if (realmGet$subSpeciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.subSpecialityColKey, colKey, realmGet$subSpeciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.subSpecialityColKey, colKey, false);
            }
            String realmGet$doc_workplace = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_workplace();
            if (realmGet$doc_workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, realmGet$doc_workplace, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, false);
            }
            String realmGet$doc_location = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_location();
            if (realmGet$doc_location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_locationColKey, colKey, realmGet$doc_location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_locationColKey, colKey, false);
            }
            String realmGet$doc_cnt_email = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_cnt_email();
            if (realmGet$doc_cnt_email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_cnt_emailColKey, colKey, realmGet$doc_cnt_email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_cnt_emailColKey, colKey, false);
            }
            String realmGet$doc_cnt_num = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_cnt_num();
            if (realmGet$doc_cnt_num != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_cnt_numColKey, colKey, realmGet$doc_cnt_num, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_cnt_numColKey, colKey, false);
            }
            String realmGet$doc_email_vis = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_email_vis();
            if (realmGet$doc_email_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_email_visColKey, colKey, realmGet$doc_email_vis, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_email_visColKey, colKey, false);
            }
            String realmGet$doc_phno_vis = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_phno_vis();
            if (realmGet$doc_phno_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, realmGet$doc_phno_vis, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, false);
            }
            String realmGet$doc_pic_name = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_pic_name();
            if (realmGet$doc_pic_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_nameColKey, colKey, realmGet$doc_pic_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_pic_nameColKey, colKey, false);
            }
            String realmGet$doc_pic_url = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$doc_pic_url();
            if (realmGet$doc_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, realmGet$doc_pic_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, false);
            }
            String realmGet$caseroom_notify_type = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_notify_type();
            if (realmGet$caseroom_notify_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_notify_typeColKey, colKey, realmGet$caseroom_notify_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_notify_typeColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.caseroom_ack_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$caseroom_ack_status(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomNotifications createDetachedCopy(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmCaseRoomNotifications unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmCaseRoomNotifications();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$caseroom_notification_id(realmSource.realmGet$caseroom_notification_id());
        unmanagedCopy.realmSet$caseroom_id(realmSource.realmGet$caseroom_id());
        unmanagedCopy.realmSet$caseroom_summary_id(realmSource.realmGet$caseroom_summary_id());
        unmanagedCopy.realmSet$caseroom_group_created_date(realmSource.realmGet$caseroom_group_created_date());
        unmanagedCopy.realmSet$caseroom_group_xmpp_jid(realmSource.realmGet$caseroom_group_xmpp_jid());
        unmanagedCopy.realmSet$case_heading(realmSource.realmGet$case_heading());
        unmanagedCopy.realmSet$case_speciality(realmSource.realmGet$case_speciality());
        unmanagedCopy.realmSet$case_sub_speciality(realmSource.realmGet$case_sub_speciality());
        unmanagedCopy.realmSet$time_received(realmSource.realmGet$time_received());
        unmanagedCopy.realmSet$doc_id(realmSource.realmGet$doc_id());
        unmanagedCopy.realmSet$doc_qb_user_id(realmSource.realmGet$doc_qb_user_id());
        unmanagedCopy.realmSet$doc_name(realmSource.realmGet$doc_name());
        unmanagedCopy.realmSet$doc_speciality(realmSource.realmGet$doc_speciality());
        unmanagedCopy.realmSet$subSpeciality(realmSource.realmGet$subSpeciality());
        unmanagedCopy.realmSet$doc_workplace(realmSource.realmGet$doc_workplace());
        unmanagedCopy.realmSet$doc_location(realmSource.realmGet$doc_location());
        unmanagedCopy.realmSet$doc_cnt_email(realmSource.realmGet$doc_cnt_email());
        unmanagedCopy.realmSet$doc_cnt_num(realmSource.realmGet$doc_cnt_num());
        unmanagedCopy.realmSet$doc_email_vis(realmSource.realmGet$doc_email_vis());
        unmanagedCopy.realmSet$doc_phno_vis(realmSource.realmGet$doc_phno_vis());
        unmanagedCopy.realmSet$doc_pic_name(realmSource.realmGet$doc_pic_name());
        unmanagedCopy.realmSet$doc_pic_url(realmSource.realmGet$doc_pic_url());
        unmanagedCopy.realmSet$caseroom_notify_type(realmSource.realmGet$caseroom_notify_type());
        unmanagedCopy.realmSet$caseroom_ack_status(realmSource.realmGet$caseroom_ack_status());
        unmanagedCopy.realmSet$count_status(realmSource.realmGet$count_status());
        unmanagedCopy.realmSet$user_salutation(realmSource.realmGet$user_salutation());
        unmanagedCopy.realmSet$user_type_id(realmSource.realmGet$user_type_id());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmCaseRoomNotifications update(Realm realm, RealmCaseRoomNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomNotifications realmObject, com.vam.whitecoats.core.realm.RealmCaseRoomNotifications newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.caseroom_notification_idColKey, realmObjectSource.realmGet$caseroom_notification_id());
        builder.addString(columnInfo.caseroom_idColKey, realmObjectSource.realmGet$caseroom_id());
        builder.addString(columnInfo.caseroom_summary_idColKey, realmObjectSource.realmGet$caseroom_summary_id());
        builder.addInteger(columnInfo.caseroom_group_created_dateColKey, realmObjectSource.realmGet$caseroom_group_created_date());
        builder.addString(columnInfo.caseroom_group_xmpp_jidColKey, realmObjectSource.realmGet$caseroom_group_xmpp_jid());
        builder.addString(columnInfo.case_headingColKey, realmObjectSource.realmGet$case_heading());
        builder.addString(columnInfo.case_specialityColKey, realmObjectSource.realmGet$case_speciality());
        builder.addString(columnInfo.case_sub_specialityColKey, realmObjectSource.realmGet$case_sub_speciality());
        builder.addInteger(columnInfo.time_receivedColKey, realmObjectSource.realmGet$time_received());
        builder.addInteger(columnInfo.doc_idColKey, realmObjectSource.realmGet$doc_id());
        builder.addString(columnInfo.doc_qb_user_idColKey, realmObjectSource.realmGet$doc_qb_user_id());
        builder.addString(columnInfo.doc_nameColKey, realmObjectSource.realmGet$doc_name());
        builder.addString(columnInfo.doc_specialityColKey, realmObjectSource.realmGet$doc_speciality());
        builder.addString(columnInfo.subSpecialityColKey, realmObjectSource.realmGet$subSpeciality());
        builder.addString(columnInfo.doc_workplaceColKey, realmObjectSource.realmGet$doc_workplace());
        builder.addString(columnInfo.doc_locationColKey, realmObjectSource.realmGet$doc_location());
        builder.addString(columnInfo.doc_cnt_emailColKey, realmObjectSource.realmGet$doc_cnt_email());
        builder.addString(columnInfo.doc_cnt_numColKey, realmObjectSource.realmGet$doc_cnt_num());
        builder.addString(columnInfo.doc_email_visColKey, realmObjectSource.realmGet$doc_email_vis());
        builder.addString(columnInfo.doc_phno_visColKey, realmObjectSource.realmGet$doc_phno_vis());
        builder.addString(columnInfo.doc_pic_nameColKey, realmObjectSource.realmGet$doc_pic_name());
        builder.addString(columnInfo.doc_pic_urlColKey, realmObjectSource.realmGet$doc_pic_url());
        builder.addString(columnInfo.caseroom_notify_typeColKey, realmObjectSource.realmGet$caseroom_notify_type());
        builder.addInteger(columnInfo.caseroom_ack_statusColKey, realmObjectSource.realmGet$caseroom_ack_status());
        builder.addInteger(columnInfo.count_statusColKey, realmObjectSource.realmGet$count_status());
        builder.addString(columnInfo.user_salutationColKey, realmObjectSource.realmGet$user_salutation());
        builder.addInteger(columnInfo.user_type_idColKey, realmObjectSource.realmGet$user_type_id());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmCaseRoomNotifications = proxy[");
        stringBuilder.append("{caseroom_notification_id:");
        stringBuilder.append(realmGet$caseroom_notification_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{caseroom_id:");
        stringBuilder.append(realmGet$caseroom_id() != null ? realmGet$caseroom_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{caseroom_summary_id:");
        stringBuilder.append(realmGet$caseroom_summary_id() != null ? realmGet$caseroom_summary_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{caseroom_group_created_date:");
        stringBuilder.append(realmGet$caseroom_group_created_date());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{caseroom_group_xmpp_jid:");
        stringBuilder.append(realmGet$caseroom_group_xmpp_jid() != null ? realmGet$caseroom_group_xmpp_jid() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{case_heading:");
        stringBuilder.append(realmGet$case_heading() != null ? realmGet$case_heading() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{case_speciality:");
        stringBuilder.append(realmGet$case_speciality() != null ? realmGet$case_speciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{case_sub_speciality:");
        stringBuilder.append(realmGet$case_sub_speciality() != null ? realmGet$case_sub_speciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time_received:");
        stringBuilder.append(realmGet$time_received());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_id:");
        stringBuilder.append(realmGet$doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_qb_user_id:");
        stringBuilder.append(realmGet$doc_qb_user_id() != null ? realmGet$doc_qb_user_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_name:");
        stringBuilder.append(realmGet$doc_name() != null ? realmGet$doc_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_speciality:");
        stringBuilder.append(realmGet$doc_speciality() != null ? realmGet$doc_speciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{subSpeciality:");
        stringBuilder.append(realmGet$subSpeciality() != null ? realmGet$subSpeciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_workplace:");
        stringBuilder.append(realmGet$doc_workplace() != null ? realmGet$doc_workplace() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_location:");
        stringBuilder.append(realmGet$doc_location() != null ? realmGet$doc_location() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_cnt_email:");
        stringBuilder.append(realmGet$doc_cnt_email() != null ? realmGet$doc_cnt_email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_cnt_num:");
        stringBuilder.append(realmGet$doc_cnt_num() != null ? realmGet$doc_cnt_num() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_email_vis:");
        stringBuilder.append(realmGet$doc_email_vis() != null ? realmGet$doc_email_vis() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_phno_vis:");
        stringBuilder.append(realmGet$doc_phno_vis() != null ? realmGet$doc_phno_vis() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_pic_name:");
        stringBuilder.append(realmGet$doc_pic_name() != null ? realmGet$doc_pic_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_pic_url:");
        stringBuilder.append(realmGet$doc_pic_url() != null ? realmGet$doc_pic_url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{caseroom_notify_type:");
        stringBuilder.append(realmGet$caseroom_notify_type() != null ? realmGet$caseroom_notify_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{caseroom_ack_status:");
        stringBuilder.append(realmGet$caseroom_ack_status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{count_status:");
        stringBuilder.append(realmGet$count_status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_salutation:");
        stringBuilder.append(realmGet$user_salutation() != null ? realmGet$user_salutation() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_type_id:");
        stringBuilder.append(realmGet$user_type_id());
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
        com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy aRealmCaseRoomNotifications = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmCaseRoomNotifications.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmCaseRoomNotifications.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmCaseRoomNotifications.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
