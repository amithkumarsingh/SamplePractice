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
public class com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy extends com.vam.whitecoats.core.realm.RealmGroupNotifications
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface {

    static final class RealmGroupNotificationsColumnInfo extends ColumnInfo {
        long group_notification_idColKey;
        long group_ackStatusColKey;
        long group_readStatusColKey;
        long group_idColKey;
        long group_nameColKey;
        long group_picColKey;
        long group_pic_urlColKey;
        long group_creation_timeColKey;
        long group_admin_nameColKey;
        long group_admin_picColKey;
        long group_admin_Doc_idColKey;
        long group_admin_specialtyColKey;
        long group_admin_sub_specialtyColKey;
        long group_admin_workplaceColKey;
        long group_admin_emailColKey;
        long group_admin_phnoColKey;
        long group_admin_email_visColKey;
        long group_admin_phno_visColKey;
        long group_admin_locationColKey;
        long group_admin_qb_user_idColKey;
        long group_notification_typeColKey;
        long group_notification_timeColKey;
        long count_statusColKey;
        long group_admin_pic_urlColKey;
        long user_type_idColKey;
        long user_salutationColKey;

        RealmGroupNotificationsColumnInfo(OsSchemaInfo schemaInfo) {
            super(26);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmGroupNotifications");
            this.group_notification_idColKey = addColumnDetails("group_notification_id", "group_notification_id", objectSchemaInfo);
            this.group_ackStatusColKey = addColumnDetails("group_ackStatus", "group_ackStatus", objectSchemaInfo);
            this.group_readStatusColKey = addColumnDetails("group_readStatus", "group_readStatus", objectSchemaInfo);
            this.group_idColKey = addColumnDetails("group_id", "group_id", objectSchemaInfo);
            this.group_nameColKey = addColumnDetails("group_name", "group_name", objectSchemaInfo);
            this.group_picColKey = addColumnDetails("group_pic", "group_pic", objectSchemaInfo);
            this.group_pic_urlColKey = addColumnDetails("group_pic_url", "group_pic_url", objectSchemaInfo);
            this.group_creation_timeColKey = addColumnDetails("group_creation_time", "group_creation_time", objectSchemaInfo);
            this.group_admin_nameColKey = addColumnDetails("group_admin_name", "group_admin_name", objectSchemaInfo);
            this.group_admin_picColKey = addColumnDetails("group_admin_pic", "group_admin_pic", objectSchemaInfo);
            this.group_admin_Doc_idColKey = addColumnDetails("group_admin_Doc_id", "group_admin_Doc_id", objectSchemaInfo);
            this.group_admin_specialtyColKey = addColumnDetails("group_admin_specialty", "group_admin_specialty", objectSchemaInfo);
            this.group_admin_sub_specialtyColKey = addColumnDetails("group_admin_sub_specialty", "group_admin_sub_specialty", objectSchemaInfo);
            this.group_admin_workplaceColKey = addColumnDetails("group_admin_workplace", "group_admin_workplace", objectSchemaInfo);
            this.group_admin_emailColKey = addColumnDetails("group_admin_email", "group_admin_email", objectSchemaInfo);
            this.group_admin_phnoColKey = addColumnDetails("group_admin_phno", "group_admin_phno", objectSchemaInfo);
            this.group_admin_email_visColKey = addColumnDetails("group_admin_email_vis", "group_admin_email_vis", objectSchemaInfo);
            this.group_admin_phno_visColKey = addColumnDetails("group_admin_phno_vis", "group_admin_phno_vis", objectSchemaInfo);
            this.group_admin_locationColKey = addColumnDetails("group_admin_location", "group_admin_location", objectSchemaInfo);
            this.group_admin_qb_user_idColKey = addColumnDetails("group_admin_qb_user_id", "group_admin_qb_user_id", objectSchemaInfo);
            this.group_notification_typeColKey = addColumnDetails("group_notification_type", "group_notification_type", objectSchemaInfo);
            this.group_notification_timeColKey = addColumnDetails("group_notification_time", "group_notification_time", objectSchemaInfo);
            this.count_statusColKey = addColumnDetails("count_status", "count_status", objectSchemaInfo);
            this.group_admin_pic_urlColKey = addColumnDetails("group_admin_pic_url", "group_admin_pic_url", objectSchemaInfo);
            this.user_type_idColKey = addColumnDetails("user_type_id", "user_type_id", objectSchemaInfo);
            this.user_salutationColKey = addColumnDetails("user_salutation", "user_salutation", objectSchemaInfo);
        }

        RealmGroupNotificationsColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmGroupNotificationsColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmGroupNotificationsColumnInfo src = (RealmGroupNotificationsColumnInfo) rawSrc;
            final RealmGroupNotificationsColumnInfo dst = (RealmGroupNotificationsColumnInfo) rawDst;
            dst.group_notification_idColKey = src.group_notification_idColKey;
            dst.group_ackStatusColKey = src.group_ackStatusColKey;
            dst.group_readStatusColKey = src.group_readStatusColKey;
            dst.group_idColKey = src.group_idColKey;
            dst.group_nameColKey = src.group_nameColKey;
            dst.group_picColKey = src.group_picColKey;
            dst.group_pic_urlColKey = src.group_pic_urlColKey;
            dst.group_creation_timeColKey = src.group_creation_timeColKey;
            dst.group_admin_nameColKey = src.group_admin_nameColKey;
            dst.group_admin_picColKey = src.group_admin_picColKey;
            dst.group_admin_Doc_idColKey = src.group_admin_Doc_idColKey;
            dst.group_admin_specialtyColKey = src.group_admin_specialtyColKey;
            dst.group_admin_sub_specialtyColKey = src.group_admin_sub_specialtyColKey;
            dst.group_admin_workplaceColKey = src.group_admin_workplaceColKey;
            dst.group_admin_emailColKey = src.group_admin_emailColKey;
            dst.group_admin_phnoColKey = src.group_admin_phnoColKey;
            dst.group_admin_email_visColKey = src.group_admin_email_visColKey;
            dst.group_admin_phno_visColKey = src.group_admin_phno_visColKey;
            dst.group_admin_locationColKey = src.group_admin_locationColKey;
            dst.group_admin_qb_user_idColKey = src.group_admin_qb_user_idColKey;
            dst.group_notification_typeColKey = src.group_notification_typeColKey;
            dst.group_notification_timeColKey = src.group_notification_timeColKey;
            dst.count_statusColKey = src.count_statusColKey;
            dst.group_admin_pic_urlColKey = src.group_admin_pic_urlColKey;
            dst.user_type_idColKey = src.user_type_idColKey;
            dst.user_salutationColKey = src.user_salutationColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmGroupNotificationsColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmGroupNotifications> proxyState;

    com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmGroupNotificationsColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmGroupNotifications>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_notification_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_notification_idColKey);
    }

    @Override
    public void realmSet$group_notification_id(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'group_notification_id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$group_ackStatus() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.group_ackStatusColKey);
    }

    @Override
    public void realmSet$group_ackStatus(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.group_ackStatusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.group_ackStatusColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$group_readStatus() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.group_readStatusColKey);
    }

    @Override
    public void realmSet$group_readStatus(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.group_readStatusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.group_readStatusColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_idColKey);
    }

    @Override
    public void realmSet$group_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_nameColKey);
    }

    @Override
    public void realmSet$group_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_pic() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_picColKey);
    }

    @Override
    public void realmSet$group_pic(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_picColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_picColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_picColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_picColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_pic_url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_pic_urlColKey);
    }

    @Override
    public void realmSet$group_pic_url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_pic_urlColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_pic_urlColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_pic_urlColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_pic_urlColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$group_creation_time() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.group_creation_timeColKey);
    }

    @Override
    public void realmSet$group_creation_time(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.group_creation_timeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.group_creation_timeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_nameColKey);
    }

    @Override
    public void realmSet$group_admin_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_pic() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_picColKey);
    }

    @Override
    public void realmSet$group_admin_pic(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_picColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_picColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_picColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_picColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$group_admin_Doc_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.group_admin_Doc_idColKey);
    }

    @Override
    public void realmSet$group_admin_Doc_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.group_admin_Doc_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.group_admin_Doc_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_specialty() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_specialtyColKey);
    }

    @Override
    public void realmSet$group_admin_specialty(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_specialtyColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_specialtyColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_specialtyColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_specialtyColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_sub_specialty() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_sub_specialtyColKey);
    }

    @Override
    public void realmSet$group_admin_sub_specialty(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_sub_specialtyColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_sub_specialtyColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_sub_specialtyColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_sub_specialtyColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_workplace() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_workplaceColKey);
    }

    @Override
    public void realmSet$group_admin_workplace(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_workplaceColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_workplaceColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_workplaceColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_workplaceColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_emailColKey);
    }

    @Override
    public void realmSet$group_admin_email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_emailColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_emailColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_emailColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_emailColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_phno() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_phnoColKey);
    }

    @Override
    public void realmSet$group_admin_phno(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_phnoColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_phnoColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_phnoColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_phnoColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_email_vis() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_email_visColKey);
    }

    @Override
    public void realmSet$group_admin_email_vis(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_email_visColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_email_visColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_email_visColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_email_visColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_phno_vis() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_phno_visColKey);
    }

    @Override
    public void realmSet$group_admin_phno_vis(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_phno_visColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_phno_visColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_phno_visColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_phno_visColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_location() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_locationColKey);
    }

    @Override
    public void realmSet$group_admin_location(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_locationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_locationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_locationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_locationColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_admin_qb_user_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_qb_user_idColKey);
    }

    @Override
    public void realmSet$group_admin_qb_user_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_qb_user_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_qb_user_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_qb_user_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_qb_user_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_notification_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_notification_typeColKey);
    }

    @Override
    public void realmSet$group_notification_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_notification_typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_notification_typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_notification_typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_notification_typeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$group_notification_time() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.group_notification_timeColKey);
    }

    @Override
    public void realmSet$group_notification_time(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.group_notification_timeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.group_notification_timeColKey, value);
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
    public String realmGet$group_admin_pic_url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_admin_pic_urlColKey);
    }

    @Override
    public void realmSet$group_admin_pic_url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_admin_pic_urlColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_admin_pic_urlColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_admin_pic_urlColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_admin_pic_urlColKey, value);
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmGroupNotifications", 26, 0);
        builder.addPersistedProperty("group_notification_id", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("group_ackStatus", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("group_readStatus", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("group_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_pic", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_pic_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_creation_time", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("group_admin_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_pic", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_Doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("group_admin_specialty", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_sub_specialty", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_workplace", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_phno", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_email_vis", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_phno_vis", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_location", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_admin_qb_user_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_notification_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_notification_time", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("count_status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("group_admin_pic_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("user_type_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("user_salutation", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmGroupNotificationsColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmGroupNotificationsColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmGroupNotifications";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmGroupNotifications";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmGroupNotifications createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmGroupNotifications obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
            RealmGroupNotificationsColumnInfo columnInfo = (RealmGroupNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
            long pkColumnKey = columnInfo.group_notification_idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("group_notification_id")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("group_notification_id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmGroupNotifications.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("group_notification_id")) {
                if (json.isNull("group_notification_id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmGroupNotifications.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmGroupNotifications.class, json.getString("group_notification_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'group_notification_id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) obj;
        if (json.has("group_ackStatus")) {
            if (json.isNull("group_ackStatus")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'group_ackStatus' to null.");
            } else {
                objProxy.realmSet$group_ackStatus((int) json.getInt("group_ackStatus"));
            }
        }
        if (json.has("group_readStatus")) {
            if (json.isNull("group_readStatus")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'group_readStatus' to null.");
            } else {
                objProxy.realmSet$group_readStatus((int) json.getInt("group_readStatus"));
            }
        }
        if (json.has("group_id")) {
            if (json.isNull("group_id")) {
                objProxy.realmSet$group_id(null);
            } else {
                objProxy.realmSet$group_id((String) json.getString("group_id"));
            }
        }
        if (json.has("group_name")) {
            if (json.isNull("group_name")) {
                objProxy.realmSet$group_name(null);
            } else {
                objProxy.realmSet$group_name((String) json.getString("group_name"));
            }
        }
        if (json.has("group_pic")) {
            if (json.isNull("group_pic")) {
                objProxy.realmSet$group_pic(null);
            } else {
                objProxy.realmSet$group_pic((String) json.getString("group_pic"));
            }
        }
        if (json.has("group_pic_url")) {
            if (json.isNull("group_pic_url")) {
                objProxy.realmSet$group_pic_url(null);
            } else {
                objProxy.realmSet$group_pic_url((String) json.getString("group_pic_url"));
            }
        }
        if (json.has("group_creation_time")) {
            if (json.isNull("group_creation_time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'group_creation_time' to null.");
            } else {
                objProxy.realmSet$group_creation_time((long) json.getLong("group_creation_time"));
            }
        }
        if (json.has("group_admin_name")) {
            if (json.isNull("group_admin_name")) {
                objProxy.realmSet$group_admin_name(null);
            } else {
                objProxy.realmSet$group_admin_name((String) json.getString("group_admin_name"));
            }
        }
        if (json.has("group_admin_pic")) {
            if (json.isNull("group_admin_pic")) {
                objProxy.realmSet$group_admin_pic(null);
            } else {
                objProxy.realmSet$group_admin_pic((String) json.getString("group_admin_pic"));
            }
        }
        if (json.has("group_admin_Doc_id")) {
            if (json.isNull("group_admin_Doc_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'group_admin_Doc_id' to null.");
            } else {
                objProxy.realmSet$group_admin_Doc_id((int) json.getInt("group_admin_Doc_id"));
            }
        }
        if (json.has("group_admin_specialty")) {
            if (json.isNull("group_admin_specialty")) {
                objProxy.realmSet$group_admin_specialty(null);
            } else {
                objProxy.realmSet$group_admin_specialty((String) json.getString("group_admin_specialty"));
            }
        }
        if (json.has("group_admin_sub_specialty")) {
            if (json.isNull("group_admin_sub_specialty")) {
                objProxy.realmSet$group_admin_sub_specialty(null);
            } else {
                objProxy.realmSet$group_admin_sub_specialty((String) json.getString("group_admin_sub_specialty"));
            }
        }
        if (json.has("group_admin_workplace")) {
            if (json.isNull("group_admin_workplace")) {
                objProxy.realmSet$group_admin_workplace(null);
            } else {
                objProxy.realmSet$group_admin_workplace((String) json.getString("group_admin_workplace"));
            }
        }
        if (json.has("group_admin_email")) {
            if (json.isNull("group_admin_email")) {
                objProxy.realmSet$group_admin_email(null);
            } else {
                objProxy.realmSet$group_admin_email((String) json.getString("group_admin_email"));
            }
        }
        if (json.has("group_admin_phno")) {
            if (json.isNull("group_admin_phno")) {
                objProxy.realmSet$group_admin_phno(null);
            } else {
                objProxy.realmSet$group_admin_phno((String) json.getString("group_admin_phno"));
            }
        }
        if (json.has("group_admin_email_vis")) {
            if (json.isNull("group_admin_email_vis")) {
                objProxy.realmSet$group_admin_email_vis(null);
            } else {
                objProxy.realmSet$group_admin_email_vis((String) json.getString("group_admin_email_vis"));
            }
        }
        if (json.has("group_admin_phno_vis")) {
            if (json.isNull("group_admin_phno_vis")) {
                objProxy.realmSet$group_admin_phno_vis(null);
            } else {
                objProxy.realmSet$group_admin_phno_vis((String) json.getString("group_admin_phno_vis"));
            }
        }
        if (json.has("group_admin_location")) {
            if (json.isNull("group_admin_location")) {
                objProxy.realmSet$group_admin_location(null);
            } else {
                objProxy.realmSet$group_admin_location((String) json.getString("group_admin_location"));
            }
        }
        if (json.has("group_admin_qb_user_id")) {
            if (json.isNull("group_admin_qb_user_id")) {
                objProxy.realmSet$group_admin_qb_user_id(null);
            } else {
                objProxy.realmSet$group_admin_qb_user_id((String) json.getString("group_admin_qb_user_id"));
            }
        }
        if (json.has("group_notification_type")) {
            if (json.isNull("group_notification_type")) {
                objProxy.realmSet$group_notification_type(null);
            } else {
                objProxy.realmSet$group_notification_type((String) json.getString("group_notification_type"));
            }
        }
        if (json.has("group_notification_time")) {
            if (json.isNull("group_notification_time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'group_notification_time' to null.");
            } else {
                objProxy.realmSet$group_notification_time((long) json.getLong("group_notification_time"));
            }
        }
        if (json.has("count_status")) {
            if (json.isNull("count_status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'count_status' to null.");
            } else {
                objProxy.realmSet$count_status((int) json.getInt("count_status"));
            }
        }
        if (json.has("group_admin_pic_url")) {
            if (json.isNull("group_admin_pic_url")) {
                objProxy.realmSet$group_admin_pic_url(null);
            } else {
                objProxy.realmSet$group_admin_pic_url((String) json.getString("group_admin_pic_url"));
            }
        }
        if (json.has("user_type_id")) {
            if (json.isNull("user_type_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'user_type_id' to null.");
            } else {
                objProxy.realmSet$user_type_id((int) json.getInt("user_type_id"));
            }
        }
        if (json.has("user_salutation")) {
            if (json.isNull("user_salutation")) {
                objProxy.realmSet$user_salutation(null);
            } else {
                objProxy.realmSet$user_salutation((String) json.getString("user_salutation"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmGroupNotifications createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmGroupNotifications obj = new com.vam.whitecoats.core.realm.RealmGroupNotifications();
        final com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("group_notification_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_notification_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_notification_id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("group_ackStatus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_ackStatus((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'group_ackStatus' to null.");
                }
            } else if (name.equals("group_readStatus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_readStatus((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'group_readStatus' to null.");
                }
            } else if (name.equals("group_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_id(null);
                }
            } else if (name.equals("group_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_name(null);
                }
            } else if (name.equals("group_pic")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_pic((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_pic(null);
                }
            } else if (name.equals("group_pic_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_pic_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_pic_url(null);
                }
            } else if (name.equals("group_creation_time")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_creation_time((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'group_creation_time' to null.");
                }
            } else if (name.equals("group_admin_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_name(null);
                }
            } else if (name.equals("group_admin_pic")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_pic((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_pic(null);
                }
            } else if (name.equals("group_admin_Doc_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_Doc_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'group_admin_Doc_id' to null.");
                }
            } else if (name.equals("group_admin_specialty")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_specialty((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_specialty(null);
                }
            } else if (name.equals("group_admin_sub_specialty")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_sub_specialty((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_sub_specialty(null);
                }
            } else if (name.equals("group_admin_workplace")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_workplace((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_workplace(null);
                }
            } else if (name.equals("group_admin_email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_email(null);
                }
            } else if (name.equals("group_admin_phno")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_phno((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_phno(null);
                }
            } else if (name.equals("group_admin_email_vis")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_email_vis((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_email_vis(null);
                }
            } else if (name.equals("group_admin_phno_vis")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_phno_vis((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_phno_vis(null);
                }
            } else if (name.equals("group_admin_location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_location(null);
                }
            } else if (name.equals("group_admin_qb_user_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_qb_user_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_qb_user_id(null);
                }
            } else if (name.equals("group_notification_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_notification_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_notification_type(null);
                }
            } else if (name.equals("group_notification_time")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_notification_time((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'group_notification_time' to null.");
                }
            } else if (name.equals("count_status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$count_status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'count_status' to null.");
                }
            } else if (name.equals("group_admin_pic_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_admin_pic_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_admin_pic_url(null);
                }
            } else if (name.equals("user_type_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$user_type_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'user_type_id' to null.");
                }
            } else if (name.equals("user_salutation")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$user_salutation((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$user_salutation(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'group_notification_id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmGroupNotifications.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmGroupNotifications copyOrUpdate(Realm realm, RealmGroupNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmGroupNotifications object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmGroupNotifications) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmGroupNotifications realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
            long pkColumnKey = columnInfo.group_notification_idColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmGroupNotifications copy(Realm realm, RealmGroupNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmGroupNotifications newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmGroupNotifications) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.group_notification_idColKey, realmObjectSource.realmGet$group_notification_id());
        builder.addInteger(columnInfo.group_ackStatusColKey, realmObjectSource.realmGet$group_ackStatus());
        builder.addInteger(columnInfo.group_readStatusColKey, realmObjectSource.realmGet$group_readStatus());
        builder.addString(columnInfo.group_idColKey, realmObjectSource.realmGet$group_id());
        builder.addString(columnInfo.group_nameColKey, realmObjectSource.realmGet$group_name());
        builder.addString(columnInfo.group_picColKey, realmObjectSource.realmGet$group_pic());
        builder.addString(columnInfo.group_pic_urlColKey, realmObjectSource.realmGet$group_pic_url());
        builder.addInteger(columnInfo.group_creation_timeColKey, realmObjectSource.realmGet$group_creation_time());
        builder.addString(columnInfo.group_admin_nameColKey, realmObjectSource.realmGet$group_admin_name());
        builder.addString(columnInfo.group_admin_picColKey, realmObjectSource.realmGet$group_admin_pic());
        builder.addInteger(columnInfo.group_admin_Doc_idColKey, realmObjectSource.realmGet$group_admin_Doc_id());
        builder.addString(columnInfo.group_admin_specialtyColKey, realmObjectSource.realmGet$group_admin_specialty());
        builder.addString(columnInfo.group_admin_sub_specialtyColKey, realmObjectSource.realmGet$group_admin_sub_specialty());
        builder.addString(columnInfo.group_admin_workplaceColKey, realmObjectSource.realmGet$group_admin_workplace());
        builder.addString(columnInfo.group_admin_emailColKey, realmObjectSource.realmGet$group_admin_email());
        builder.addString(columnInfo.group_admin_phnoColKey, realmObjectSource.realmGet$group_admin_phno());
        builder.addString(columnInfo.group_admin_email_visColKey, realmObjectSource.realmGet$group_admin_email_vis());
        builder.addString(columnInfo.group_admin_phno_visColKey, realmObjectSource.realmGet$group_admin_phno_vis());
        builder.addString(columnInfo.group_admin_locationColKey, realmObjectSource.realmGet$group_admin_location());
        builder.addString(columnInfo.group_admin_qb_user_idColKey, realmObjectSource.realmGet$group_admin_qb_user_id());
        builder.addString(columnInfo.group_notification_typeColKey, realmObjectSource.realmGet$group_notification_type());
        builder.addInteger(columnInfo.group_notification_timeColKey, realmObjectSource.realmGet$group_notification_time());
        builder.addInteger(columnInfo.count_statusColKey, realmObjectSource.realmGet$count_status());
        builder.addString(columnInfo.group_admin_pic_urlColKey, realmObjectSource.realmGet$group_admin_pic_url());
        builder.addInteger(columnInfo.user_type_idColKey, realmObjectSource.realmGet$user_type_id());
        builder.addString(columnInfo.user_salutationColKey, realmObjectSource.realmGet$user_salutation());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmGroupNotifications object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmGroupNotificationsColumnInfo columnInfo = (RealmGroupNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        long pkColumnKey = columnInfo.group_notification_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.group_ackStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_ackStatus(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.group_readStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_readStatus(), false);
        String realmGet$group_id = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_id();
        if (realmGet$group_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_idColKey, colKey, realmGet$group_id, false);
        }
        String realmGet$group_name = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_name();
        if (realmGet$group_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_nameColKey, colKey, realmGet$group_name, false);
        }
        String realmGet$group_pic = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_pic();
        if (realmGet$group_pic != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_picColKey, colKey, realmGet$group_pic, false);
        }
        String realmGet$group_pic_url = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_pic_url();
        if (realmGet$group_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, realmGet$group_pic_url, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.group_creation_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_creation_time(), false);
        String realmGet$group_admin_name = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_name();
        if (realmGet$group_admin_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_nameColKey, colKey, realmGet$group_admin_name, false);
        }
        String realmGet$group_admin_pic = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_pic();
        if (realmGet$group_admin_pic != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_picColKey, colKey, realmGet$group_admin_pic, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.group_admin_Doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_Doc_id(), false);
        String realmGet$group_admin_specialty = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_specialty();
        if (realmGet$group_admin_specialty != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_specialtyColKey, colKey, realmGet$group_admin_specialty, false);
        }
        String realmGet$group_admin_sub_specialty = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_sub_specialty();
        if (realmGet$group_admin_sub_specialty != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_sub_specialtyColKey, colKey, realmGet$group_admin_sub_specialty, false);
        }
        String realmGet$group_admin_workplace = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_workplace();
        if (realmGet$group_admin_workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_workplaceColKey, colKey, realmGet$group_admin_workplace, false);
        }
        String realmGet$group_admin_email = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_email();
        if (realmGet$group_admin_email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_emailColKey, colKey, realmGet$group_admin_email, false);
        }
        String realmGet$group_admin_phno = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_phno();
        if (realmGet$group_admin_phno != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_phnoColKey, colKey, realmGet$group_admin_phno, false);
        }
        String realmGet$group_admin_email_vis = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_email_vis();
        if (realmGet$group_admin_email_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_email_visColKey, colKey, realmGet$group_admin_email_vis, false);
        }
        String realmGet$group_admin_phno_vis = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_phno_vis();
        if (realmGet$group_admin_phno_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_phno_visColKey, colKey, realmGet$group_admin_phno_vis, false);
        }
        String realmGet$group_admin_location = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_location();
        if (realmGet$group_admin_location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_locationColKey, colKey, realmGet$group_admin_location, false);
        }
        String realmGet$group_admin_qb_user_id = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_qb_user_id();
        if (realmGet$group_admin_qb_user_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_qb_user_idColKey, colKey, realmGet$group_admin_qb_user_id, false);
        }
        String realmGet$group_notification_type = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_type();
        if (realmGet$group_notification_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_notification_typeColKey, colKey, realmGet$group_notification_type, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.group_notification_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_time(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
        String realmGet$group_admin_pic_url = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_pic_url();
        if (realmGet$group_admin_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_pic_urlColKey, colKey, realmGet$group_admin_pic_url, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmGroupNotificationsColumnInfo columnInfo = (RealmGroupNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        long pkColumnKey = columnInfo.group_notification_idColKey;
        com.vam.whitecoats.core.realm.RealmGroupNotifications object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmGroupNotifications) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.group_ackStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_ackStatus(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.group_readStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_readStatus(), false);
            String realmGet$group_id = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_id();
            if (realmGet$group_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_idColKey, colKey, realmGet$group_id, false);
            }
            String realmGet$group_name = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_name();
            if (realmGet$group_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_nameColKey, colKey, realmGet$group_name, false);
            }
            String realmGet$group_pic = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_pic();
            if (realmGet$group_pic != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_picColKey, colKey, realmGet$group_pic, false);
            }
            String realmGet$group_pic_url = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_pic_url();
            if (realmGet$group_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, realmGet$group_pic_url, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.group_creation_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_creation_time(), false);
            String realmGet$group_admin_name = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_name();
            if (realmGet$group_admin_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_nameColKey, colKey, realmGet$group_admin_name, false);
            }
            String realmGet$group_admin_pic = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_pic();
            if (realmGet$group_admin_pic != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_picColKey, colKey, realmGet$group_admin_pic, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.group_admin_Doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_Doc_id(), false);
            String realmGet$group_admin_specialty = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_specialty();
            if (realmGet$group_admin_specialty != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_specialtyColKey, colKey, realmGet$group_admin_specialty, false);
            }
            String realmGet$group_admin_sub_specialty = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_sub_specialty();
            if (realmGet$group_admin_sub_specialty != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_sub_specialtyColKey, colKey, realmGet$group_admin_sub_specialty, false);
            }
            String realmGet$group_admin_workplace = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_workplace();
            if (realmGet$group_admin_workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_workplaceColKey, colKey, realmGet$group_admin_workplace, false);
            }
            String realmGet$group_admin_email = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_email();
            if (realmGet$group_admin_email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_emailColKey, colKey, realmGet$group_admin_email, false);
            }
            String realmGet$group_admin_phno = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_phno();
            if (realmGet$group_admin_phno != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_phnoColKey, colKey, realmGet$group_admin_phno, false);
            }
            String realmGet$group_admin_email_vis = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_email_vis();
            if (realmGet$group_admin_email_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_email_visColKey, colKey, realmGet$group_admin_email_vis, false);
            }
            String realmGet$group_admin_phno_vis = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_phno_vis();
            if (realmGet$group_admin_phno_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_phno_visColKey, colKey, realmGet$group_admin_phno_vis, false);
            }
            String realmGet$group_admin_location = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_location();
            if (realmGet$group_admin_location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_locationColKey, colKey, realmGet$group_admin_location, false);
            }
            String realmGet$group_admin_qb_user_id = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_qb_user_id();
            if (realmGet$group_admin_qb_user_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_qb_user_idColKey, colKey, realmGet$group_admin_qb_user_id, false);
            }
            String realmGet$group_notification_type = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_type();
            if (realmGet$group_notification_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_notification_typeColKey, colKey, realmGet$group_notification_type, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.group_notification_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_time(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
            String realmGet$group_admin_pic_url = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_pic_url();
            if (realmGet$group_admin_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_pic_urlColKey, colKey, realmGet$group_admin_pic_url, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmGroupNotifications object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmGroupNotificationsColumnInfo columnInfo = (RealmGroupNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        long pkColumnKey = columnInfo.group_notification_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.group_ackStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_ackStatus(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.group_readStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_readStatus(), false);
        String realmGet$group_id = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_id();
        if (realmGet$group_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_idColKey, colKey, realmGet$group_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_idColKey, colKey, false);
        }
        String realmGet$group_name = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_name();
        if (realmGet$group_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_nameColKey, colKey, realmGet$group_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_nameColKey, colKey, false);
        }
        String realmGet$group_pic = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_pic();
        if (realmGet$group_pic != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_picColKey, colKey, realmGet$group_pic, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_picColKey, colKey, false);
        }
        String realmGet$group_pic_url = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_pic_url();
        if (realmGet$group_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, realmGet$group_pic_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.group_creation_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_creation_time(), false);
        String realmGet$group_admin_name = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_name();
        if (realmGet$group_admin_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_nameColKey, colKey, realmGet$group_admin_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_nameColKey, colKey, false);
        }
        String realmGet$group_admin_pic = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_pic();
        if (realmGet$group_admin_pic != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_picColKey, colKey, realmGet$group_admin_pic, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_picColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.group_admin_Doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_Doc_id(), false);
        String realmGet$group_admin_specialty = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_specialty();
        if (realmGet$group_admin_specialty != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_specialtyColKey, colKey, realmGet$group_admin_specialty, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_specialtyColKey, colKey, false);
        }
        String realmGet$group_admin_sub_specialty = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_sub_specialty();
        if (realmGet$group_admin_sub_specialty != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_sub_specialtyColKey, colKey, realmGet$group_admin_sub_specialty, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_sub_specialtyColKey, colKey, false);
        }
        String realmGet$group_admin_workplace = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_workplace();
        if (realmGet$group_admin_workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_workplaceColKey, colKey, realmGet$group_admin_workplace, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_workplaceColKey, colKey, false);
        }
        String realmGet$group_admin_email = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_email();
        if (realmGet$group_admin_email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_emailColKey, colKey, realmGet$group_admin_email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_emailColKey, colKey, false);
        }
        String realmGet$group_admin_phno = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_phno();
        if (realmGet$group_admin_phno != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_phnoColKey, colKey, realmGet$group_admin_phno, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_phnoColKey, colKey, false);
        }
        String realmGet$group_admin_email_vis = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_email_vis();
        if (realmGet$group_admin_email_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_email_visColKey, colKey, realmGet$group_admin_email_vis, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_email_visColKey, colKey, false);
        }
        String realmGet$group_admin_phno_vis = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_phno_vis();
        if (realmGet$group_admin_phno_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_phno_visColKey, colKey, realmGet$group_admin_phno_vis, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_phno_visColKey, colKey, false);
        }
        String realmGet$group_admin_location = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_location();
        if (realmGet$group_admin_location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_locationColKey, colKey, realmGet$group_admin_location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_locationColKey, colKey, false);
        }
        String realmGet$group_admin_qb_user_id = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_qb_user_id();
        if (realmGet$group_admin_qb_user_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_qb_user_idColKey, colKey, realmGet$group_admin_qb_user_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_qb_user_idColKey, colKey, false);
        }
        String realmGet$group_notification_type = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_type();
        if (realmGet$group_notification_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_notification_typeColKey, colKey, realmGet$group_notification_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_notification_typeColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.group_notification_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_time(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
        String realmGet$group_admin_pic_url = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_pic_url();
        if (realmGet$group_admin_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_admin_pic_urlColKey, colKey, realmGet$group_admin_pic_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_pic_urlColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmGroupNotificationsColumnInfo columnInfo = (RealmGroupNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        long pkColumnKey = columnInfo.group_notification_idColKey;
        com.vam.whitecoats.core.realm.RealmGroupNotifications object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmGroupNotifications) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.group_ackStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_ackStatus(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.group_readStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_readStatus(), false);
            String realmGet$group_id = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_id();
            if (realmGet$group_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_idColKey, colKey, realmGet$group_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_idColKey, colKey, false);
            }
            String realmGet$group_name = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_name();
            if (realmGet$group_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_nameColKey, colKey, realmGet$group_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_nameColKey, colKey, false);
            }
            String realmGet$group_pic = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_pic();
            if (realmGet$group_pic != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_picColKey, colKey, realmGet$group_pic, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_picColKey, colKey, false);
            }
            String realmGet$group_pic_url = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_pic_url();
            if (realmGet$group_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, realmGet$group_pic_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.group_creation_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_creation_time(), false);
            String realmGet$group_admin_name = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_name();
            if (realmGet$group_admin_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_nameColKey, colKey, realmGet$group_admin_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_nameColKey, colKey, false);
            }
            String realmGet$group_admin_pic = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_pic();
            if (realmGet$group_admin_pic != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_picColKey, colKey, realmGet$group_admin_pic, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_picColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.group_admin_Doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_Doc_id(), false);
            String realmGet$group_admin_specialty = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_specialty();
            if (realmGet$group_admin_specialty != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_specialtyColKey, colKey, realmGet$group_admin_specialty, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_specialtyColKey, colKey, false);
            }
            String realmGet$group_admin_sub_specialty = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_sub_specialty();
            if (realmGet$group_admin_sub_specialty != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_sub_specialtyColKey, colKey, realmGet$group_admin_sub_specialty, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_sub_specialtyColKey, colKey, false);
            }
            String realmGet$group_admin_workplace = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_workplace();
            if (realmGet$group_admin_workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_workplaceColKey, colKey, realmGet$group_admin_workplace, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_workplaceColKey, colKey, false);
            }
            String realmGet$group_admin_email = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_email();
            if (realmGet$group_admin_email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_emailColKey, colKey, realmGet$group_admin_email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_emailColKey, colKey, false);
            }
            String realmGet$group_admin_phno = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_phno();
            if (realmGet$group_admin_phno != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_phnoColKey, colKey, realmGet$group_admin_phno, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_phnoColKey, colKey, false);
            }
            String realmGet$group_admin_email_vis = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_email_vis();
            if (realmGet$group_admin_email_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_email_visColKey, colKey, realmGet$group_admin_email_vis, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_email_visColKey, colKey, false);
            }
            String realmGet$group_admin_phno_vis = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_phno_vis();
            if (realmGet$group_admin_phno_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_phno_visColKey, colKey, realmGet$group_admin_phno_vis, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_phno_visColKey, colKey, false);
            }
            String realmGet$group_admin_location = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_location();
            if (realmGet$group_admin_location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_locationColKey, colKey, realmGet$group_admin_location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_locationColKey, colKey, false);
            }
            String realmGet$group_admin_qb_user_id = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_qb_user_id();
            if (realmGet$group_admin_qb_user_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_qb_user_idColKey, colKey, realmGet$group_admin_qb_user_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_qb_user_idColKey, colKey, false);
            }
            String realmGet$group_notification_type = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_type();
            if (realmGet$group_notification_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_notification_typeColKey, colKey, realmGet$group_notification_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_notification_typeColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.group_notification_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_notification_time(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
            String realmGet$group_admin_pic_url = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$group_admin_pic_url();
            if (realmGet$group_admin_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_admin_pic_urlColKey, colKey, realmGet$group_admin_pic_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_admin_pic_urlColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmGroupNotifications createDetachedCopy(com.vam.whitecoats.core.realm.RealmGroupNotifications realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmGroupNotifications unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmGroupNotifications();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmGroupNotifications) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmGroupNotifications) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$group_notification_id(realmSource.realmGet$group_notification_id());
        unmanagedCopy.realmSet$group_ackStatus(realmSource.realmGet$group_ackStatus());
        unmanagedCopy.realmSet$group_readStatus(realmSource.realmGet$group_readStatus());
        unmanagedCopy.realmSet$group_id(realmSource.realmGet$group_id());
        unmanagedCopy.realmSet$group_name(realmSource.realmGet$group_name());
        unmanagedCopy.realmSet$group_pic(realmSource.realmGet$group_pic());
        unmanagedCopy.realmSet$group_pic_url(realmSource.realmGet$group_pic_url());
        unmanagedCopy.realmSet$group_creation_time(realmSource.realmGet$group_creation_time());
        unmanagedCopy.realmSet$group_admin_name(realmSource.realmGet$group_admin_name());
        unmanagedCopy.realmSet$group_admin_pic(realmSource.realmGet$group_admin_pic());
        unmanagedCopy.realmSet$group_admin_Doc_id(realmSource.realmGet$group_admin_Doc_id());
        unmanagedCopy.realmSet$group_admin_specialty(realmSource.realmGet$group_admin_specialty());
        unmanagedCopy.realmSet$group_admin_sub_specialty(realmSource.realmGet$group_admin_sub_specialty());
        unmanagedCopy.realmSet$group_admin_workplace(realmSource.realmGet$group_admin_workplace());
        unmanagedCopy.realmSet$group_admin_email(realmSource.realmGet$group_admin_email());
        unmanagedCopy.realmSet$group_admin_phno(realmSource.realmGet$group_admin_phno());
        unmanagedCopy.realmSet$group_admin_email_vis(realmSource.realmGet$group_admin_email_vis());
        unmanagedCopy.realmSet$group_admin_phno_vis(realmSource.realmGet$group_admin_phno_vis());
        unmanagedCopy.realmSet$group_admin_location(realmSource.realmGet$group_admin_location());
        unmanagedCopy.realmSet$group_admin_qb_user_id(realmSource.realmGet$group_admin_qb_user_id());
        unmanagedCopy.realmSet$group_notification_type(realmSource.realmGet$group_notification_type());
        unmanagedCopy.realmSet$group_notification_time(realmSource.realmGet$group_notification_time());
        unmanagedCopy.realmSet$count_status(realmSource.realmGet$count_status());
        unmanagedCopy.realmSet$group_admin_pic_url(realmSource.realmGet$group_admin_pic_url());
        unmanagedCopy.realmSet$user_type_id(realmSource.realmGet$user_type_id());
        unmanagedCopy.realmSet$user_salutation(realmSource.realmGet$user_salutation());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmGroupNotifications update(Realm realm, RealmGroupNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmGroupNotifications realmObject, com.vam.whitecoats.core.realm.RealmGroupNotifications newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.group_notification_idColKey, realmObjectSource.realmGet$group_notification_id());
        builder.addInteger(columnInfo.group_ackStatusColKey, realmObjectSource.realmGet$group_ackStatus());
        builder.addInteger(columnInfo.group_readStatusColKey, realmObjectSource.realmGet$group_readStatus());
        builder.addString(columnInfo.group_idColKey, realmObjectSource.realmGet$group_id());
        builder.addString(columnInfo.group_nameColKey, realmObjectSource.realmGet$group_name());
        builder.addString(columnInfo.group_picColKey, realmObjectSource.realmGet$group_pic());
        builder.addString(columnInfo.group_pic_urlColKey, realmObjectSource.realmGet$group_pic_url());
        builder.addInteger(columnInfo.group_creation_timeColKey, realmObjectSource.realmGet$group_creation_time());
        builder.addString(columnInfo.group_admin_nameColKey, realmObjectSource.realmGet$group_admin_name());
        builder.addString(columnInfo.group_admin_picColKey, realmObjectSource.realmGet$group_admin_pic());
        builder.addInteger(columnInfo.group_admin_Doc_idColKey, realmObjectSource.realmGet$group_admin_Doc_id());
        builder.addString(columnInfo.group_admin_specialtyColKey, realmObjectSource.realmGet$group_admin_specialty());
        builder.addString(columnInfo.group_admin_sub_specialtyColKey, realmObjectSource.realmGet$group_admin_sub_specialty());
        builder.addString(columnInfo.group_admin_workplaceColKey, realmObjectSource.realmGet$group_admin_workplace());
        builder.addString(columnInfo.group_admin_emailColKey, realmObjectSource.realmGet$group_admin_email());
        builder.addString(columnInfo.group_admin_phnoColKey, realmObjectSource.realmGet$group_admin_phno());
        builder.addString(columnInfo.group_admin_email_visColKey, realmObjectSource.realmGet$group_admin_email_vis());
        builder.addString(columnInfo.group_admin_phno_visColKey, realmObjectSource.realmGet$group_admin_phno_vis());
        builder.addString(columnInfo.group_admin_locationColKey, realmObjectSource.realmGet$group_admin_location());
        builder.addString(columnInfo.group_admin_qb_user_idColKey, realmObjectSource.realmGet$group_admin_qb_user_id());
        builder.addString(columnInfo.group_notification_typeColKey, realmObjectSource.realmGet$group_notification_type());
        builder.addInteger(columnInfo.group_notification_timeColKey, realmObjectSource.realmGet$group_notification_time());
        builder.addInteger(columnInfo.count_statusColKey, realmObjectSource.realmGet$count_status());
        builder.addString(columnInfo.group_admin_pic_urlColKey, realmObjectSource.realmGet$group_admin_pic_url());
        builder.addInteger(columnInfo.user_type_idColKey, realmObjectSource.realmGet$user_type_id());
        builder.addString(columnInfo.user_salutationColKey, realmObjectSource.realmGet$user_salutation());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmGroupNotifications = proxy[");
        stringBuilder.append("{group_notification_id:");
        stringBuilder.append(realmGet$group_notification_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_ackStatus:");
        stringBuilder.append(realmGet$group_ackStatus());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_readStatus:");
        stringBuilder.append(realmGet$group_readStatus());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_id:");
        stringBuilder.append(realmGet$group_id() != null ? realmGet$group_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_name:");
        stringBuilder.append(realmGet$group_name() != null ? realmGet$group_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_pic:");
        stringBuilder.append(realmGet$group_pic() != null ? realmGet$group_pic() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_pic_url:");
        stringBuilder.append(realmGet$group_pic_url() != null ? realmGet$group_pic_url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_creation_time:");
        stringBuilder.append(realmGet$group_creation_time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_name:");
        stringBuilder.append(realmGet$group_admin_name() != null ? realmGet$group_admin_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_pic:");
        stringBuilder.append(realmGet$group_admin_pic() != null ? realmGet$group_admin_pic() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_Doc_id:");
        stringBuilder.append(realmGet$group_admin_Doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_specialty:");
        stringBuilder.append(realmGet$group_admin_specialty() != null ? realmGet$group_admin_specialty() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_sub_specialty:");
        stringBuilder.append(realmGet$group_admin_sub_specialty() != null ? realmGet$group_admin_sub_specialty() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_workplace:");
        stringBuilder.append(realmGet$group_admin_workplace() != null ? realmGet$group_admin_workplace() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_email:");
        stringBuilder.append(realmGet$group_admin_email() != null ? realmGet$group_admin_email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_phno:");
        stringBuilder.append(realmGet$group_admin_phno() != null ? realmGet$group_admin_phno() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_email_vis:");
        stringBuilder.append(realmGet$group_admin_email_vis() != null ? realmGet$group_admin_email_vis() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_phno_vis:");
        stringBuilder.append(realmGet$group_admin_phno_vis() != null ? realmGet$group_admin_phno_vis() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_location:");
        stringBuilder.append(realmGet$group_admin_location() != null ? realmGet$group_admin_location() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_qb_user_id:");
        stringBuilder.append(realmGet$group_admin_qb_user_id() != null ? realmGet$group_admin_qb_user_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_notification_type:");
        stringBuilder.append(realmGet$group_notification_type() != null ? realmGet$group_notification_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_notification_time:");
        stringBuilder.append(realmGet$group_notification_time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{count_status:");
        stringBuilder.append(realmGet$count_status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_admin_pic_url:");
        stringBuilder.append(realmGet$group_admin_pic_url() != null ? realmGet$group_admin_pic_url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_type_id:");
        stringBuilder.append(realmGet$user_type_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user_salutation:");
        stringBuilder.append(realmGet$user_salutation() != null ? realmGet$user_salutation() : "null");
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
        com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy aRealmGroupNotifications = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmGroupNotifications.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmGroupNotifications.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmGroupNotifications.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
