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
public class com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy extends com.vam.whitecoats.core.realm.RealmNotifications
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface {

    static final class RealmNotificationsColumnInfo extends ColumnInfo {
        long notification_idColKey;
        long notification_typeColKey;
        long ackStatusColKey;
        long readStatusColKey;
        long doc_idColKey;
        long doc_picColKey;
        long doc_pic_urlColKey;
        long doc_nameColKey;
        long doc_specialityColKey;
        long doc_sub_specialityColKey;
        long doc_workplaceColKey;
        long doc_emailColKey;
        long doc_phnoColKey;
        long doc_locationColKey;
        long doc_email_visColKey;
        long doc_phno_visColKey;
        long doc_qb_user_idColKey;
        long timeColKey;
        long messageColKey;
        long count_statusColKey;
        long user_salutationColKey;
        long user_type_idColKey;

        RealmNotificationsColumnInfo(OsSchemaInfo schemaInfo) {
            super(22);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmNotifications");
            this.notification_idColKey = addColumnDetails("notification_id", "notification_id", objectSchemaInfo);
            this.notification_typeColKey = addColumnDetails("notification_type", "notification_type", objectSchemaInfo);
            this.ackStatusColKey = addColumnDetails("ackStatus", "ackStatus", objectSchemaInfo);
            this.readStatusColKey = addColumnDetails("readStatus", "readStatus", objectSchemaInfo);
            this.doc_idColKey = addColumnDetails("doc_id", "doc_id", objectSchemaInfo);
            this.doc_picColKey = addColumnDetails("doc_pic", "doc_pic", objectSchemaInfo);
            this.doc_pic_urlColKey = addColumnDetails("doc_pic_url", "doc_pic_url", objectSchemaInfo);
            this.doc_nameColKey = addColumnDetails("doc_name", "doc_name", objectSchemaInfo);
            this.doc_specialityColKey = addColumnDetails("doc_speciality", "doc_speciality", objectSchemaInfo);
            this.doc_sub_specialityColKey = addColumnDetails("doc_sub_speciality", "doc_sub_speciality", objectSchemaInfo);
            this.doc_workplaceColKey = addColumnDetails("doc_workplace", "doc_workplace", objectSchemaInfo);
            this.doc_emailColKey = addColumnDetails("doc_email", "doc_email", objectSchemaInfo);
            this.doc_phnoColKey = addColumnDetails("doc_phno", "doc_phno", objectSchemaInfo);
            this.doc_locationColKey = addColumnDetails("doc_location", "doc_location", objectSchemaInfo);
            this.doc_email_visColKey = addColumnDetails("doc_email_vis", "doc_email_vis", objectSchemaInfo);
            this.doc_phno_visColKey = addColumnDetails("doc_phno_vis", "doc_phno_vis", objectSchemaInfo);
            this.doc_qb_user_idColKey = addColumnDetails("doc_qb_user_id", "doc_qb_user_id", objectSchemaInfo);
            this.timeColKey = addColumnDetails("time", "time", objectSchemaInfo);
            this.messageColKey = addColumnDetails("message", "message", objectSchemaInfo);
            this.count_statusColKey = addColumnDetails("count_status", "count_status", objectSchemaInfo);
            this.user_salutationColKey = addColumnDetails("user_salutation", "user_salutation", objectSchemaInfo);
            this.user_type_idColKey = addColumnDetails("user_type_id", "user_type_id", objectSchemaInfo);
        }

        RealmNotificationsColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmNotificationsColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmNotificationsColumnInfo src = (RealmNotificationsColumnInfo) rawSrc;
            final RealmNotificationsColumnInfo dst = (RealmNotificationsColumnInfo) rawDst;
            dst.notification_idColKey = src.notification_idColKey;
            dst.notification_typeColKey = src.notification_typeColKey;
            dst.ackStatusColKey = src.ackStatusColKey;
            dst.readStatusColKey = src.readStatusColKey;
            dst.doc_idColKey = src.doc_idColKey;
            dst.doc_picColKey = src.doc_picColKey;
            dst.doc_pic_urlColKey = src.doc_pic_urlColKey;
            dst.doc_nameColKey = src.doc_nameColKey;
            dst.doc_specialityColKey = src.doc_specialityColKey;
            dst.doc_sub_specialityColKey = src.doc_sub_specialityColKey;
            dst.doc_workplaceColKey = src.doc_workplaceColKey;
            dst.doc_emailColKey = src.doc_emailColKey;
            dst.doc_phnoColKey = src.doc_phnoColKey;
            dst.doc_locationColKey = src.doc_locationColKey;
            dst.doc_email_visColKey = src.doc_email_visColKey;
            dst.doc_phno_visColKey = src.doc_phno_visColKey;
            dst.doc_qb_user_idColKey = src.doc_qb_user_idColKey;
            dst.timeColKey = src.timeColKey;
            dst.messageColKey = src.messageColKey;
            dst.count_statusColKey = src.count_statusColKey;
            dst.user_salutationColKey = src.user_salutationColKey;
            dst.user_type_idColKey = src.user_type_idColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmNotificationsColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmNotifications> proxyState;

    com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmNotificationsColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmNotifications>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$notification_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.notification_idColKey);
    }

    @Override
    public void realmSet$notification_id(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'notification_id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$notification_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.notification_typeColKey);
    }

    @Override
    public void realmSet$notification_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.notification_typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.notification_typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.notification_typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.notification_typeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$ackStatus() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.ackStatusColKey);
    }

    @Override
    public void realmSet$ackStatus(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.ackStatusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.ackStatusColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$readStatus() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.readStatusColKey);
    }

    @Override
    public void realmSet$readStatus(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.readStatusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.readStatusColKey, value);
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
    public String realmGet$doc_pic() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_picColKey);
    }

    @Override
    public void realmSet$doc_pic(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_picColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_picColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_picColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_picColKey, value);
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
    public String realmGet$doc_sub_speciality() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_sub_specialityColKey);
    }

    @Override
    public void realmSet$doc_sub_speciality(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_sub_specialityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_sub_specialityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_sub_specialityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_sub_specialityColKey, value);
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
    public String realmGet$doc_email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_emailColKey);
    }

    @Override
    public void realmSet$doc_email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_emailColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_emailColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_emailColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_emailColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$doc_phno() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.doc_phnoColKey);
    }

    @Override
    public void realmSet$doc_phno(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.doc_phnoColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.doc_phnoColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.doc_phnoColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.doc_phnoColKey, value);
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
    public int realmGet$doc_qb_user_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.doc_qb_user_idColKey);
    }

    @Override
    public void realmSet$doc_qb_user_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.doc_qb_user_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.doc_qb_user_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$time() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.timeColKey);
    }

    @Override
    public void realmSet$time(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.timeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.timeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$message() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.messageColKey);
    }

    @Override
    public void realmSet$message(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.messageColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.messageColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.messageColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.messageColKey, value);
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
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmNotifications", 22, 0);
        builder.addPersistedProperty("notification_id", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("notification_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("ackStatus", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("readStatus", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("doc_pic", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_pic_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_speciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_sub_speciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_workplace", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_phno", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_location", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_email_vis", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_phno_vis", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("doc_qb_user_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("time", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("message", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("count_status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("user_salutation", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("user_type_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmNotificationsColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmNotificationsColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmNotifications";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmNotifications";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmNotifications createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmNotifications obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotifications.class);
            RealmNotificationsColumnInfo columnInfo = (RealmNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotifications.class);
            long pkColumnKey = columnInfo.notification_idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("notification_id")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("notification_id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotifications.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("notification_id")) {
                if (json.isNull("notification_id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmNotifications.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmNotifications.class, json.getString("notification_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'notification_id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) obj;
        if (json.has("notification_type")) {
            if (json.isNull("notification_type")) {
                objProxy.realmSet$notification_type(null);
            } else {
                objProxy.realmSet$notification_type((String) json.getString("notification_type"));
            }
        }
        if (json.has("ackStatus")) {
            if (json.isNull("ackStatus")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'ackStatus' to null.");
            } else {
                objProxy.realmSet$ackStatus((int) json.getInt("ackStatus"));
            }
        }
        if (json.has("readStatus")) {
            if (json.isNull("readStatus")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'readStatus' to null.");
            } else {
                objProxy.realmSet$readStatus((boolean) json.getBoolean("readStatus"));
            }
        }
        if (json.has("doc_id")) {
            if (json.isNull("doc_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'doc_id' to null.");
            } else {
                objProxy.realmSet$doc_id((int) json.getInt("doc_id"));
            }
        }
        if (json.has("doc_pic")) {
            if (json.isNull("doc_pic")) {
                objProxy.realmSet$doc_pic(null);
            } else {
                objProxy.realmSet$doc_pic((String) json.getString("doc_pic"));
            }
        }
        if (json.has("doc_pic_url")) {
            if (json.isNull("doc_pic_url")) {
                objProxy.realmSet$doc_pic_url(null);
            } else {
                objProxy.realmSet$doc_pic_url((String) json.getString("doc_pic_url"));
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
        if (json.has("doc_sub_speciality")) {
            if (json.isNull("doc_sub_speciality")) {
                objProxy.realmSet$doc_sub_speciality(null);
            } else {
                objProxy.realmSet$doc_sub_speciality((String) json.getString("doc_sub_speciality"));
            }
        }
        if (json.has("doc_workplace")) {
            if (json.isNull("doc_workplace")) {
                objProxy.realmSet$doc_workplace(null);
            } else {
                objProxy.realmSet$doc_workplace((String) json.getString("doc_workplace"));
            }
        }
        if (json.has("doc_email")) {
            if (json.isNull("doc_email")) {
                objProxy.realmSet$doc_email(null);
            } else {
                objProxy.realmSet$doc_email((String) json.getString("doc_email"));
            }
        }
        if (json.has("doc_phno")) {
            if (json.isNull("doc_phno")) {
                objProxy.realmSet$doc_phno(null);
            } else {
                objProxy.realmSet$doc_phno((String) json.getString("doc_phno"));
            }
        }
        if (json.has("doc_location")) {
            if (json.isNull("doc_location")) {
                objProxy.realmSet$doc_location(null);
            } else {
                objProxy.realmSet$doc_location((String) json.getString("doc_location"));
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
        if (json.has("doc_qb_user_id")) {
            if (json.isNull("doc_qb_user_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'doc_qb_user_id' to null.");
            } else {
                objProxy.realmSet$doc_qb_user_id((int) json.getInt("doc_qb_user_id"));
            }
        }
        if (json.has("time")) {
            if (json.isNull("time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
            } else {
                objProxy.realmSet$time((long) json.getLong("time"));
            }
        }
        if (json.has("message")) {
            if (json.isNull("message")) {
                objProxy.realmSet$message(null);
            } else {
                objProxy.realmSet$message((String) json.getString("message"));
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
    public static com.vam.whitecoats.core.realm.RealmNotifications createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmNotifications obj = new com.vam.whitecoats.core.realm.RealmNotifications();
        final com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("notification_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$notification_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$notification_id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("notification_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$notification_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$notification_type(null);
                }
            } else if (name.equals("ackStatus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$ackStatus((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'ackStatus' to null.");
                }
            } else if (name.equals("readStatus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$readStatus((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'readStatus' to null.");
                }
            } else if (name.equals("doc_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'doc_id' to null.");
                }
            } else if (name.equals("doc_pic")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_pic((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_pic(null);
                }
            } else if (name.equals("doc_pic_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_pic_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_pic_url(null);
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
            } else if (name.equals("doc_sub_speciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_sub_speciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_sub_speciality(null);
                }
            } else if (name.equals("doc_workplace")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_workplace((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_workplace(null);
                }
            } else if (name.equals("doc_email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_email(null);
                }
            } else if (name.equals("doc_phno")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_phno((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_phno(null);
                }
            } else if (name.equals("doc_location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$doc_location(null);
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
            } else if (name.equals("doc_qb_user_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_qb_user_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'doc_qb_user_id' to null.");
                }
            } else if (name.equals("time")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$time((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
                }
            } else if (name.equals("message")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$message((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$message(null);
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
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'notification_id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotifications.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmNotifications copyOrUpdate(Realm realm, RealmNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmNotifications object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmNotifications) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmNotifications realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotifications.class);
            long pkColumnKey = columnInfo.notification_idColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmNotifications copy(Realm realm, RealmNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmNotifications newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmNotifications) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotifications.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.notification_idColKey, realmObjectSource.realmGet$notification_id());
        builder.addString(columnInfo.notification_typeColKey, realmObjectSource.realmGet$notification_type());
        builder.addInteger(columnInfo.ackStatusColKey, realmObjectSource.realmGet$ackStatus());
        builder.addBoolean(columnInfo.readStatusColKey, realmObjectSource.realmGet$readStatus());
        builder.addInteger(columnInfo.doc_idColKey, realmObjectSource.realmGet$doc_id());
        builder.addString(columnInfo.doc_picColKey, realmObjectSource.realmGet$doc_pic());
        builder.addString(columnInfo.doc_pic_urlColKey, realmObjectSource.realmGet$doc_pic_url());
        builder.addString(columnInfo.doc_nameColKey, realmObjectSource.realmGet$doc_name());
        builder.addString(columnInfo.doc_specialityColKey, realmObjectSource.realmGet$doc_speciality());
        builder.addString(columnInfo.doc_sub_specialityColKey, realmObjectSource.realmGet$doc_sub_speciality());
        builder.addString(columnInfo.doc_workplaceColKey, realmObjectSource.realmGet$doc_workplace());
        builder.addString(columnInfo.doc_emailColKey, realmObjectSource.realmGet$doc_email());
        builder.addString(columnInfo.doc_phnoColKey, realmObjectSource.realmGet$doc_phno());
        builder.addString(columnInfo.doc_locationColKey, realmObjectSource.realmGet$doc_location());
        builder.addString(columnInfo.doc_email_visColKey, realmObjectSource.realmGet$doc_email_vis());
        builder.addString(columnInfo.doc_phno_visColKey, realmObjectSource.realmGet$doc_phno_vis());
        builder.addInteger(columnInfo.doc_qb_user_idColKey, realmObjectSource.realmGet$doc_qb_user_id());
        builder.addInteger(columnInfo.timeColKey, realmObjectSource.realmGet$time());
        builder.addString(columnInfo.messageColKey, realmObjectSource.realmGet$message());
        builder.addInteger(columnInfo.count_statusColKey, realmObjectSource.realmGet$count_status());
        builder.addString(columnInfo.user_salutationColKey, realmObjectSource.realmGet$user_salutation());
        builder.addInteger(columnInfo.user_type_idColKey, realmObjectSource.realmGet$user_type_id());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmNotifications object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationsColumnInfo columnInfo = (RealmNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotifications.class);
        long pkColumnKey = columnInfo.notification_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$notification_type = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_type();
        if (realmGet$notification_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.notification_typeColKey, colKey, realmGet$notification_type, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.ackStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$ackStatus(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.readStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$readStatus(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_id(), false);
        String realmGet$doc_pic = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_pic();
        if (realmGet$doc_pic != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_picColKey, colKey, realmGet$doc_pic, false);
        }
        String realmGet$doc_pic_url = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_pic_url();
        if (realmGet$doc_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, realmGet$doc_pic_url, false);
        }
        String realmGet$doc_name = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_name();
        if (realmGet$doc_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_nameColKey, colKey, realmGet$doc_name, false);
        }
        String realmGet$doc_speciality = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_speciality();
        if (realmGet$doc_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_specialityColKey, colKey, realmGet$doc_speciality, false);
        }
        String realmGet$doc_sub_speciality = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_sub_speciality();
        if (realmGet$doc_sub_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_sub_specialityColKey, colKey, realmGet$doc_sub_speciality, false);
        }
        String realmGet$doc_workplace = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_workplace();
        if (realmGet$doc_workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, realmGet$doc_workplace, false);
        }
        String realmGet$doc_email = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_email();
        if (realmGet$doc_email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_emailColKey, colKey, realmGet$doc_email, false);
        }
        String realmGet$doc_phno = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_phno();
        if (realmGet$doc_phno != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_phnoColKey, colKey, realmGet$doc_phno, false);
        }
        String realmGet$doc_location = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_location();
        if (realmGet$doc_location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_locationColKey, colKey, realmGet$doc_location, false);
        }
        String realmGet$doc_email_vis = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_email_vis();
        if (realmGet$doc_email_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_email_visColKey, colKey, realmGet$doc_email_vis, false);
        }
        String realmGet$doc_phno_vis = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_phno_vis();
        if (realmGet$doc_phno_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, realmGet$doc_phno_vis, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_qb_user_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$time(), false);
        String realmGet$message = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$message();
        if (realmGet$message != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.messageColKey, colKey, realmGet$message, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationsColumnInfo columnInfo = (RealmNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotifications.class);
        long pkColumnKey = columnInfo.notification_idColKey;
        com.vam.whitecoats.core.realm.RealmNotifications object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmNotifications) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$notification_type = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_type();
            if (realmGet$notification_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.notification_typeColKey, colKey, realmGet$notification_type, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.ackStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$ackStatus(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.readStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$readStatus(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_id(), false);
            String realmGet$doc_pic = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_pic();
            if (realmGet$doc_pic != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_picColKey, colKey, realmGet$doc_pic, false);
            }
            String realmGet$doc_pic_url = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_pic_url();
            if (realmGet$doc_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, realmGet$doc_pic_url, false);
            }
            String realmGet$doc_name = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_name();
            if (realmGet$doc_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_nameColKey, colKey, realmGet$doc_name, false);
            }
            String realmGet$doc_speciality = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_speciality();
            if (realmGet$doc_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_specialityColKey, colKey, realmGet$doc_speciality, false);
            }
            String realmGet$doc_sub_speciality = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_sub_speciality();
            if (realmGet$doc_sub_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_sub_specialityColKey, colKey, realmGet$doc_sub_speciality, false);
            }
            String realmGet$doc_workplace = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_workplace();
            if (realmGet$doc_workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, realmGet$doc_workplace, false);
            }
            String realmGet$doc_email = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_email();
            if (realmGet$doc_email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_emailColKey, colKey, realmGet$doc_email, false);
            }
            String realmGet$doc_phno = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_phno();
            if (realmGet$doc_phno != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_phnoColKey, colKey, realmGet$doc_phno, false);
            }
            String realmGet$doc_location = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_location();
            if (realmGet$doc_location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_locationColKey, colKey, realmGet$doc_location, false);
            }
            String realmGet$doc_email_vis = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_email_vis();
            if (realmGet$doc_email_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_email_visColKey, colKey, realmGet$doc_email_vis, false);
            }
            String realmGet$doc_phno_vis = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_phno_vis();
            if (realmGet$doc_phno_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, realmGet$doc_phno_vis, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_qb_user_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$time(), false);
            String realmGet$message = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$message();
            if (realmGet$message != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.messageColKey, colKey, realmGet$message, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmNotifications object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationsColumnInfo columnInfo = (RealmNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotifications.class);
        long pkColumnKey = columnInfo.notification_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$notification_type = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_type();
        if (realmGet$notification_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.notification_typeColKey, colKey, realmGet$notification_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.notification_typeColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.ackStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$ackStatus(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.readStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$readStatus(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_id(), false);
        String realmGet$doc_pic = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_pic();
        if (realmGet$doc_pic != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_picColKey, colKey, realmGet$doc_pic, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_picColKey, colKey, false);
        }
        String realmGet$doc_pic_url = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_pic_url();
        if (realmGet$doc_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, realmGet$doc_pic_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, false);
        }
        String realmGet$doc_name = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_name();
        if (realmGet$doc_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_nameColKey, colKey, realmGet$doc_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_nameColKey, colKey, false);
        }
        String realmGet$doc_speciality = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_speciality();
        if (realmGet$doc_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_specialityColKey, colKey, realmGet$doc_speciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_specialityColKey, colKey, false);
        }
        String realmGet$doc_sub_speciality = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_sub_speciality();
        if (realmGet$doc_sub_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_sub_specialityColKey, colKey, realmGet$doc_sub_speciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_sub_specialityColKey, colKey, false);
        }
        String realmGet$doc_workplace = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_workplace();
        if (realmGet$doc_workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, realmGet$doc_workplace, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, false);
        }
        String realmGet$doc_email = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_email();
        if (realmGet$doc_email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_emailColKey, colKey, realmGet$doc_email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_emailColKey, colKey, false);
        }
        String realmGet$doc_phno = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_phno();
        if (realmGet$doc_phno != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_phnoColKey, colKey, realmGet$doc_phno, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_phnoColKey, colKey, false);
        }
        String realmGet$doc_location = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_location();
        if (realmGet$doc_location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_locationColKey, colKey, realmGet$doc_location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_locationColKey, colKey, false);
        }
        String realmGet$doc_email_vis = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_email_vis();
        if (realmGet$doc_email_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_email_visColKey, colKey, realmGet$doc_email_vis, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_email_visColKey, colKey, false);
        }
        String realmGet$doc_phno_vis = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_phno_vis();
        if (realmGet$doc_phno_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, realmGet$doc_phno_vis, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_qb_user_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$time(), false);
        String realmGet$message = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$message();
        if (realmGet$message != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.messageColKey, colKey, realmGet$message, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.messageColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotifications.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationsColumnInfo columnInfo = (RealmNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotifications.class);
        long pkColumnKey = columnInfo.notification_idColKey;
        com.vam.whitecoats.core.realm.RealmNotifications object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmNotifications) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$notification_type = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$notification_type();
            if (realmGet$notification_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.notification_typeColKey, colKey, realmGet$notification_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.notification_typeColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.ackStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$ackStatus(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.readStatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$readStatus(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_id(), false);
            String realmGet$doc_pic = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_pic();
            if (realmGet$doc_pic != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_picColKey, colKey, realmGet$doc_pic, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_picColKey, colKey, false);
            }
            String realmGet$doc_pic_url = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_pic_url();
            if (realmGet$doc_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, realmGet$doc_pic_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_pic_urlColKey, colKey, false);
            }
            String realmGet$doc_name = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_name();
            if (realmGet$doc_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_nameColKey, colKey, realmGet$doc_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_nameColKey, colKey, false);
            }
            String realmGet$doc_speciality = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_speciality();
            if (realmGet$doc_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_specialityColKey, colKey, realmGet$doc_speciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_specialityColKey, colKey, false);
            }
            String realmGet$doc_sub_speciality = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_sub_speciality();
            if (realmGet$doc_sub_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_sub_specialityColKey, colKey, realmGet$doc_sub_speciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_sub_specialityColKey, colKey, false);
            }
            String realmGet$doc_workplace = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_workplace();
            if (realmGet$doc_workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, realmGet$doc_workplace, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_workplaceColKey, colKey, false);
            }
            String realmGet$doc_email = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_email();
            if (realmGet$doc_email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_emailColKey, colKey, realmGet$doc_email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_emailColKey, colKey, false);
            }
            String realmGet$doc_phno = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_phno();
            if (realmGet$doc_phno != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_phnoColKey, colKey, realmGet$doc_phno, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_phnoColKey, colKey, false);
            }
            String realmGet$doc_location = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_location();
            if (realmGet$doc_location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_locationColKey, colKey, realmGet$doc_location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_locationColKey, colKey, false);
            }
            String realmGet$doc_email_vis = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_email_vis();
            if (realmGet$doc_email_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_email_visColKey, colKey, realmGet$doc_email_vis, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_email_visColKey, colKey, false);
            }
            String realmGet$doc_phno_vis = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_phno_vis();
            if (realmGet$doc_phno_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, realmGet$doc_phno_vis, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_phno_visColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_qb_user_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$doc_qb_user_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$time(), false);
            String realmGet$message = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$message();
            if (realmGet$message != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.messageColKey, colKey, realmGet$message, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.messageColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.count_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$count_status(), false);
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) object).realmGet$user_type_id(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmNotifications createDetachedCopy(com.vam.whitecoats.core.realm.RealmNotifications realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmNotifications unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmNotifications();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmNotifications) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmNotifications) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$notification_id(realmSource.realmGet$notification_id());
        unmanagedCopy.realmSet$notification_type(realmSource.realmGet$notification_type());
        unmanagedCopy.realmSet$ackStatus(realmSource.realmGet$ackStatus());
        unmanagedCopy.realmSet$readStatus(realmSource.realmGet$readStatus());
        unmanagedCopy.realmSet$doc_id(realmSource.realmGet$doc_id());
        unmanagedCopy.realmSet$doc_pic(realmSource.realmGet$doc_pic());
        unmanagedCopy.realmSet$doc_pic_url(realmSource.realmGet$doc_pic_url());
        unmanagedCopy.realmSet$doc_name(realmSource.realmGet$doc_name());
        unmanagedCopy.realmSet$doc_speciality(realmSource.realmGet$doc_speciality());
        unmanagedCopy.realmSet$doc_sub_speciality(realmSource.realmGet$doc_sub_speciality());
        unmanagedCopy.realmSet$doc_workplace(realmSource.realmGet$doc_workplace());
        unmanagedCopy.realmSet$doc_email(realmSource.realmGet$doc_email());
        unmanagedCopy.realmSet$doc_phno(realmSource.realmGet$doc_phno());
        unmanagedCopy.realmSet$doc_location(realmSource.realmGet$doc_location());
        unmanagedCopy.realmSet$doc_email_vis(realmSource.realmGet$doc_email_vis());
        unmanagedCopy.realmSet$doc_phno_vis(realmSource.realmGet$doc_phno_vis());
        unmanagedCopy.realmSet$doc_qb_user_id(realmSource.realmGet$doc_qb_user_id());
        unmanagedCopy.realmSet$time(realmSource.realmGet$time());
        unmanagedCopy.realmSet$message(realmSource.realmGet$message());
        unmanagedCopy.realmSet$count_status(realmSource.realmGet$count_status());
        unmanagedCopy.realmSet$user_salutation(realmSource.realmGet$user_salutation());
        unmanagedCopy.realmSet$user_type_id(realmSource.realmGet$user_type_id());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmNotifications update(Realm realm, RealmNotificationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmNotifications realmObject, com.vam.whitecoats.core.realm.RealmNotifications newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotifications.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.notification_idColKey, realmObjectSource.realmGet$notification_id());
        builder.addString(columnInfo.notification_typeColKey, realmObjectSource.realmGet$notification_type());
        builder.addInteger(columnInfo.ackStatusColKey, realmObjectSource.realmGet$ackStatus());
        builder.addBoolean(columnInfo.readStatusColKey, realmObjectSource.realmGet$readStatus());
        builder.addInteger(columnInfo.doc_idColKey, realmObjectSource.realmGet$doc_id());
        builder.addString(columnInfo.doc_picColKey, realmObjectSource.realmGet$doc_pic());
        builder.addString(columnInfo.doc_pic_urlColKey, realmObjectSource.realmGet$doc_pic_url());
        builder.addString(columnInfo.doc_nameColKey, realmObjectSource.realmGet$doc_name());
        builder.addString(columnInfo.doc_specialityColKey, realmObjectSource.realmGet$doc_speciality());
        builder.addString(columnInfo.doc_sub_specialityColKey, realmObjectSource.realmGet$doc_sub_speciality());
        builder.addString(columnInfo.doc_workplaceColKey, realmObjectSource.realmGet$doc_workplace());
        builder.addString(columnInfo.doc_emailColKey, realmObjectSource.realmGet$doc_email());
        builder.addString(columnInfo.doc_phnoColKey, realmObjectSource.realmGet$doc_phno());
        builder.addString(columnInfo.doc_locationColKey, realmObjectSource.realmGet$doc_location());
        builder.addString(columnInfo.doc_email_visColKey, realmObjectSource.realmGet$doc_email_vis());
        builder.addString(columnInfo.doc_phno_visColKey, realmObjectSource.realmGet$doc_phno_vis());
        builder.addInteger(columnInfo.doc_qb_user_idColKey, realmObjectSource.realmGet$doc_qb_user_id());
        builder.addInteger(columnInfo.timeColKey, realmObjectSource.realmGet$time());
        builder.addString(columnInfo.messageColKey, realmObjectSource.realmGet$message());
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
        StringBuilder stringBuilder = new StringBuilder("RealmNotifications = proxy[");
        stringBuilder.append("{notification_id:");
        stringBuilder.append(realmGet$notification_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{notification_type:");
        stringBuilder.append(realmGet$notification_type() != null ? realmGet$notification_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ackStatus:");
        stringBuilder.append(realmGet$ackStatus());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{readStatus:");
        stringBuilder.append(realmGet$readStatus());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_id:");
        stringBuilder.append(realmGet$doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_pic:");
        stringBuilder.append(realmGet$doc_pic() != null ? realmGet$doc_pic() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_pic_url:");
        stringBuilder.append(realmGet$doc_pic_url() != null ? realmGet$doc_pic_url() : "null");
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
        stringBuilder.append("{doc_sub_speciality:");
        stringBuilder.append(realmGet$doc_sub_speciality() != null ? realmGet$doc_sub_speciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_workplace:");
        stringBuilder.append(realmGet$doc_workplace() != null ? realmGet$doc_workplace() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_email:");
        stringBuilder.append(realmGet$doc_email() != null ? realmGet$doc_email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_phno:");
        stringBuilder.append(realmGet$doc_phno() != null ? realmGet$doc_phno() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_location:");
        stringBuilder.append(realmGet$doc_location() != null ? realmGet$doc_location() : "null");
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
        stringBuilder.append("{doc_qb_user_id:");
        stringBuilder.append(realmGet$doc_qb_user_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time:");
        stringBuilder.append(realmGet$time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{message:");
        stringBuilder.append(realmGet$message() != null ? realmGet$message() : "null");
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
        com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy aRealmNotifications = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmNotifications.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmNotifications.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmNotifications.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
