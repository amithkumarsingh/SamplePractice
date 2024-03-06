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
public class com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmMyContactsInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface {

    static final class RealmMyContactsInfoColumnInfo extends ColumnInfo {
        long doc_idColKey;
        long nameColKey;
        long specialityColKey;
        long subspecialityColKey;
        long pic_nameColKey;
        long degreeColKey;
        long workplaceColKey;
        long locationColKey;
        long networkStatusColKey;
        long emailColKey;
        long email_visColKey;
        long phnoColKey;
        long phno_visColKey;
        long pic_urlColKey;
        long user_salutationColKey;
        long user_type_idColKey;
        long UUIDColKey;
        long qb_useridColKey;

        RealmMyContactsInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(18);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmMyContactsInfo");
            this.doc_idColKey = addColumnDetails("doc_id", "doc_id", objectSchemaInfo);
            this.nameColKey = addColumnDetails("name", "name", objectSchemaInfo);
            this.specialityColKey = addColumnDetails("speciality", "speciality", objectSchemaInfo);
            this.subspecialityColKey = addColumnDetails("subspeciality", "subspeciality", objectSchemaInfo);
            this.pic_nameColKey = addColumnDetails("pic_name", "pic_name", objectSchemaInfo);
            this.degreeColKey = addColumnDetails("degree", "degree", objectSchemaInfo);
            this.workplaceColKey = addColumnDetails("workplace", "workplace", objectSchemaInfo);
            this.locationColKey = addColumnDetails("location", "location", objectSchemaInfo);
            this.networkStatusColKey = addColumnDetails("networkStatus", "networkStatus", objectSchemaInfo);
            this.emailColKey = addColumnDetails("email", "email", objectSchemaInfo);
            this.email_visColKey = addColumnDetails("email_vis", "email_vis", objectSchemaInfo);
            this.phnoColKey = addColumnDetails("phno", "phno", objectSchemaInfo);
            this.phno_visColKey = addColumnDetails("phno_vis", "phno_vis", objectSchemaInfo);
            this.pic_urlColKey = addColumnDetails("pic_url", "pic_url", objectSchemaInfo);
            this.user_salutationColKey = addColumnDetails("user_salutation", "user_salutation", objectSchemaInfo);
            this.user_type_idColKey = addColumnDetails("user_type_id", "user_type_id", objectSchemaInfo);
            this.UUIDColKey = addColumnDetails("UUID", "UUID", objectSchemaInfo);
            this.qb_useridColKey = addColumnDetails("qb_userid", "qb_userid", objectSchemaInfo);
        }

        RealmMyContactsInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmMyContactsInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmMyContactsInfoColumnInfo src = (RealmMyContactsInfoColumnInfo) rawSrc;
            final RealmMyContactsInfoColumnInfo dst = (RealmMyContactsInfoColumnInfo) rawDst;
            dst.doc_idColKey = src.doc_idColKey;
            dst.nameColKey = src.nameColKey;
            dst.specialityColKey = src.specialityColKey;
            dst.subspecialityColKey = src.subspecialityColKey;
            dst.pic_nameColKey = src.pic_nameColKey;
            dst.degreeColKey = src.degreeColKey;
            dst.workplaceColKey = src.workplaceColKey;
            dst.locationColKey = src.locationColKey;
            dst.networkStatusColKey = src.networkStatusColKey;
            dst.emailColKey = src.emailColKey;
            dst.email_visColKey = src.email_visColKey;
            dst.phnoColKey = src.phnoColKey;
            dst.phno_visColKey = src.phno_visColKey;
            dst.pic_urlColKey = src.pic_urlColKey;
            dst.user_salutationColKey = src.user_salutationColKey;
            dst.user_type_idColKey = src.user_type_idColKey;
            dst.UUIDColKey = src.UUIDColKey;
            dst.qb_useridColKey = src.qb_useridColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmMyContactsInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmMyContactsInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmMyContactsInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmMyContactsInfo>(this);
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
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameColKey);
    }

    @Override
    public void realmSet$name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$speciality() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.specialityColKey);
    }

    @Override
    public void realmSet$speciality(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.specialityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.specialityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.specialityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.specialityColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$subspeciality() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.subspecialityColKey);
    }

    @Override
    public void realmSet$subspeciality(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.subspecialityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.subspecialityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.subspecialityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.subspecialityColKey, value);
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
    public String realmGet$degree() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.degreeColKey);
    }

    @Override
    public void realmSet$degree(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.degreeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.degreeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.degreeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.degreeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$workplace() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.workplaceColKey);
    }

    @Override
    public void realmSet$workplace(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.workplaceColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.workplaceColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.workplaceColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.workplaceColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$location() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.locationColKey);
    }

    @Override
    public void realmSet$location(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.locationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.locationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.locationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.locationColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$networkStatus() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.networkStatusColKey);
    }

    @Override
    public void realmSet$networkStatus(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.networkStatusColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.networkStatusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.networkStatusColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.networkStatusColKey, value);
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
    public String realmGet$email_vis() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.email_visColKey);
    }

    @Override
    public void realmSet$email_vis(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.email_visColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.email_visColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.email_visColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.email_visColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$phno() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phnoColKey);
    }

    @Override
    public void realmSet$phno(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phnoColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.phnoColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phnoColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phnoColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$phno_vis() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phno_visColKey);
    }

    @Override
    public void realmSet$phno_vis(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phno_visColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.phno_visColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phno_visColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phno_visColKey, value);
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
    public String realmGet$UUID() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.UUIDColKey);
    }

    @Override
    public void realmSet$UUID(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.UUIDColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.UUIDColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.UUIDColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.UUIDColKey, value);
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmMyContactsInfo", 18, 0);
        builder.addPersistedProperty("doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("speciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("subspeciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("pic_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("degree", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("workplace", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("location", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("networkStatus", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("email_vis", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("phno", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("phno_vis", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("pic_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("user_salutation", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("user_type_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("UUID", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("qb_userid", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmMyContactsInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmMyContactsInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmMyContactsInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmMyContactsInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmMyContactsInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmMyContactsInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) obj;
        if (json.has("doc_id")) {
            if (json.isNull("doc_id")) {
                objProxy.realmSet$doc_id(null);
            } else {
                objProxy.realmSet$doc_id((int) json.getInt("doc_id"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                objProxy.realmSet$name(null);
            } else {
                objProxy.realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("speciality")) {
            if (json.isNull("speciality")) {
                objProxy.realmSet$speciality(null);
            } else {
                objProxy.realmSet$speciality((String) json.getString("speciality"));
            }
        }
        if (json.has("subspeciality")) {
            if (json.isNull("subspeciality")) {
                objProxy.realmSet$subspeciality(null);
            } else {
                objProxy.realmSet$subspeciality((String) json.getString("subspeciality"));
            }
        }
        if (json.has("pic_name")) {
            if (json.isNull("pic_name")) {
                objProxy.realmSet$pic_name(null);
            } else {
                objProxy.realmSet$pic_name((String) json.getString("pic_name"));
            }
        }
        if (json.has("degree")) {
            if (json.isNull("degree")) {
                objProxy.realmSet$degree(null);
            } else {
                objProxy.realmSet$degree((String) json.getString("degree"));
            }
        }
        if (json.has("workplace")) {
            if (json.isNull("workplace")) {
                objProxy.realmSet$workplace(null);
            } else {
                objProxy.realmSet$workplace((String) json.getString("workplace"));
            }
        }
        if (json.has("location")) {
            if (json.isNull("location")) {
                objProxy.realmSet$location(null);
            } else {
                objProxy.realmSet$location((String) json.getString("location"));
            }
        }
        if (json.has("networkStatus")) {
            if (json.isNull("networkStatus")) {
                objProxy.realmSet$networkStatus(null);
            } else {
                objProxy.realmSet$networkStatus((String) json.getString("networkStatus"));
            }
        }
        if (json.has("email")) {
            if (json.isNull("email")) {
                objProxy.realmSet$email(null);
            } else {
                objProxy.realmSet$email((String) json.getString("email"));
            }
        }
        if (json.has("email_vis")) {
            if (json.isNull("email_vis")) {
                objProxy.realmSet$email_vis(null);
            } else {
                objProxy.realmSet$email_vis((String) json.getString("email_vis"));
            }
        }
        if (json.has("phno")) {
            if (json.isNull("phno")) {
                objProxy.realmSet$phno(null);
            } else {
                objProxy.realmSet$phno((String) json.getString("phno"));
            }
        }
        if (json.has("phno_vis")) {
            if (json.isNull("phno_vis")) {
                objProxy.realmSet$phno_vis(null);
            } else {
                objProxy.realmSet$phno_vis((String) json.getString("phno_vis"));
            }
        }
        if (json.has("pic_url")) {
            if (json.isNull("pic_url")) {
                objProxy.realmSet$pic_url(null);
            } else {
                objProxy.realmSet$pic_url((String) json.getString("pic_url"));
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
        if (json.has("UUID")) {
            if (json.isNull("UUID")) {
                objProxy.realmSet$UUID(null);
            } else {
                objProxy.realmSet$UUID((String) json.getString("UUID"));
            }
        }
        if (json.has("qb_userid")) {
            if (json.isNull("qb_userid")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'qb_userid' to null.");
            } else {
                objProxy.realmSet$qb_userid((int) json.getInt("qb_userid"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmMyContactsInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmMyContactsInfo obj = new com.vam.whitecoats.core.realm.RealmMyContactsInfo();
        final com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) obj;
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
            } else if (name.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$name(null);
                }
            } else if (name.equals("speciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$speciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$speciality(null);
                }
            } else if (name.equals("subspeciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$subspeciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$subspeciality(null);
                }
            } else if (name.equals("pic_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$pic_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$pic_name(null);
                }
            } else if (name.equals("degree")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$degree((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$degree(null);
                }
            } else if (name.equals("workplace")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$workplace((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$workplace(null);
                }
            } else if (name.equals("location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$location(null);
                }
            } else if (name.equals("networkStatus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$networkStatus((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$networkStatus(null);
                }
            } else if (name.equals("email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$email(null);
                }
            } else if (name.equals("email_vis")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$email_vis((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$email_vis(null);
                }
            } else if (name.equals("phno")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phno((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phno(null);
                }
            } else if (name.equals("phno_vis")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phno_vis((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phno_vis(null);
                }
            } else if (name.equals("pic_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$pic_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$pic_url(null);
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
            } else if (name.equals("UUID")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$UUID((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$UUID(null);
                }
            } else if (name.equals("qb_userid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$qb_userid((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'qb_userid' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmMyContactsInfo copyOrUpdate(Realm realm, RealmMyContactsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmMyContactsInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmMyContactsInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmMyContactsInfo copy(Realm realm, RealmMyContactsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmMyContactsInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmMyContactsInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.doc_idColKey, realmObjectSource.realmGet$doc_id());
        builder.addString(columnInfo.nameColKey, realmObjectSource.realmGet$name());
        builder.addString(columnInfo.specialityColKey, realmObjectSource.realmGet$speciality());
        builder.addString(columnInfo.subspecialityColKey, realmObjectSource.realmGet$subspeciality());
        builder.addString(columnInfo.pic_nameColKey, realmObjectSource.realmGet$pic_name());
        builder.addString(columnInfo.degreeColKey, realmObjectSource.realmGet$degree());
        builder.addString(columnInfo.workplaceColKey, realmObjectSource.realmGet$workplace());
        builder.addString(columnInfo.locationColKey, realmObjectSource.realmGet$location());
        builder.addString(columnInfo.networkStatusColKey, realmObjectSource.realmGet$networkStatus());
        builder.addString(columnInfo.emailColKey, realmObjectSource.realmGet$email());
        builder.addString(columnInfo.email_visColKey, realmObjectSource.realmGet$email_vis());
        builder.addString(columnInfo.phnoColKey, realmObjectSource.realmGet$phno());
        builder.addString(columnInfo.phno_visColKey, realmObjectSource.realmGet$phno_vis());
        builder.addString(columnInfo.pic_urlColKey, realmObjectSource.realmGet$pic_url());
        builder.addString(columnInfo.user_salutationColKey, realmObjectSource.realmGet$user_salutation());
        builder.addInteger(columnInfo.user_type_idColKey, realmObjectSource.realmGet$user_type_id());
        builder.addString(columnInfo.UUIDColKey, realmObjectSource.realmGet$UUID());
        builder.addInteger(columnInfo.qb_useridColKey, realmObjectSource.realmGet$qb_userid());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmMyContactsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmMyContactsInfoColumnInfo columnInfo = (RealmMyContactsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$doc_id = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$doc_id();
        if (realmGet$doc_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, realmGet$doc_id.longValue(), false);
        }
        String realmGet$name = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameColKey, colKey, realmGet$name, false);
        }
        String realmGet$speciality = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$speciality();
        if (realmGet$speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.specialityColKey, colKey, realmGet$speciality, false);
        }
        String realmGet$subspeciality = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$subspeciality();
        if (realmGet$subspeciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subspecialityColKey, colKey, realmGet$subspeciality, false);
        }
        String realmGet$pic_name = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$pic_name();
        if (realmGet$pic_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pic_nameColKey, colKey, realmGet$pic_name, false);
        }
        String realmGet$degree = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$degree();
        if (realmGet$degree != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.degreeColKey, colKey, realmGet$degree, false);
        }
        String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$workplace();
        if (realmGet$workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
        }
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        }
        String realmGet$networkStatus = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$networkStatus();
        if (realmGet$networkStatus != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.networkStatusColKey, colKey, realmGet$networkStatus, false);
        }
        String realmGet$email = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
        }
        String realmGet$email_vis = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$email_vis();
        if (realmGet$email_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.email_visColKey, colKey, realmGet$email_vis, false);
        }
        String realmGet$phno = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$phno();
        if (realmGet$phno != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phnoColKey, colKey, realmGet$phno, false);
        }
        String realmGet$phno_vis = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$phno_vis();
        if (realmGet$phno_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phno_visColKey, colKey, realmGet$phno_vis, false);
        }
        String realmGet$pic_url = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$pic_url();
        if (realmGet$pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pic_urlColKey, colKey, realmGet$pic_url, false);
        }
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$user_type_id(), false);
        String realmGet$UUID = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$UUID();
        if (realmGet$UUID != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.UUIDColKey, colKey, realmGet$UUID, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.qb_useridColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$qb_userid(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmMyContactsInfoColumnInfo columnInfo = (RealmMyContactsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        com.vam.whitecoats.core.realm.RealmMyContactsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmMyContactsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$doc_id = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$doc_id();
            if (realmGet$doc_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, realmGet$doc_id.longValue(), false);
            }
            String realmGet$name = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameColKey, colKey, realmGet$name, false);
            }
            String realmGet$speciality = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$speciality();
            if (realmGet$speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.specialityColKey, colKey, realmGet$speciality, false);
            }
            String realmGet$subspeciality = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$subspeciality();
            if (realmGet$subspeciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.subspecialityColKey, colKey, realmGet$subspeciality, false);
            }
            String realmGet$pic_name = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$pic_name();
            if (realmGet$pic_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pic_nameColKey, colKey, realmGet$pic_name, false);
            }
            String realmGet$degree = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$degree();
            if (realmGet$degree != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.degreeColKey, colKey, realmGet$degree, false);
            }
            String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$workplace();
            if (realmGet$workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
            }
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            }
            String realmGet$networkStatus = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$networkStatus();
            if (realmGet$networkStatus != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.networkStatusColKey, colKey, realmGet$networkStatus, false);
            }
            String realmGet$email = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
            }
            String realmGet$email_vis = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$email_vis();
            if (realmGet$email_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.email_visColKey, colKey, realmGet$email_vis, false);
            }
            String realmGet$phno = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$phno();
            if (realmGet$phno != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phnoColKey, colKey, realmGet$phno, false);
            }
            String realmGet$phno_vis = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$phno_vis();
            if (realmGet$phno_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phno_visColKey, colKey, realmGet$phno_vis, false);
            }
            String realmGet$pic_url = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$pic_url();
            if (realmGet$pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pic_urlColKey, colKey, realmGet$pic_url, false);
            }
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$user_type_id(), false);
            String realmGet$UUID = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$UUID();
            if (realmGet$UUID != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.UUIDColKey, colKey, realmGet$UUID, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.qb_useridColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$qb_userid(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmMyContactsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmMyContactsInfoColumnInfo columnInfo = (RealmMyContactsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$doc_id = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$doc_id();
        if (realmGet$doc_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, realmGet$doc_id.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.doc_idColKey, colKey, false);
        }
        String realmGet$name = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameColKey, colKey, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameColKey, colKey, false);
        }
        String realmGet$speciality = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$speciality();
        if (realmGet$speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.specialityColKey, colKey, realmGet$speciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.specialityColKey, colKey, false);
        }
        String realmGet$subspeciality = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$subspeciality();
        if (realmGet$subspeciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.subspecialityColKey, colKey, realmGet$subspeciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.subspecialityColKey, colKey, false);
        }
        String realmGet$pic_name = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$pic_name();
        if (realmGet$pic_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pic_nameColKey, colKey, realmGet$pic_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.pic_nameColKey, colKey, false);
        }
        String realmGet$degree = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$degree();
        if (realmGet$degree != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.degreeColKey, colKey, realmGet$degree, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.degreeColKey, colKey, false);
        }
        String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$workplace();
        if (realmGet$workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.workplaceColKey, colKey, false);
        }
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
        }
        String realmGet$networkStatus = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$networkStatus();
        if (realmGet$networkStatus != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.networkStatusColKey, colKey, realmGet$networkStatus, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.networkStatusColKey, colKey, false);
        }
        String realmGet$email = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailColKey, colKey, false);
        }
        String realmGet$email_vis = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$email_vis();
        if (realmGet$email_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.email_visColKey, colKey, realmGet$email_vis, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.email_visColKey, colKey, false);
        }
        String realmGet$phno = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$phno();
        if (realmGet$phno != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phnoColKey, colKey, realmGet$phno, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phnoColKey, colKey, false);
        }
        String realmGet$phno_vis = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$phno_vis();
        if (realmGet$phno_vis != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phno_visColKey, colKey, realmGet$phno_vis, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phno_visColKey, colKey, false);
        }
        String realmGet$pic_url = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$pic_url();
        if (realmGet$pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.pic_urlColKey, colKey, realmGet$pic_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.pic_urlColKey, colKey, false);
        }
        String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$user_salutation();
        if (realmGet$user_salutation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$user_type_id(), false);
        String realmGet$UUID = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$UUID();
        if (realmGet$UUID != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.UUIDColKey, colKey, realmGet$UUID, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.UUIDColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.qb_useridColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$qb_userid(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmMyContactsInfoColumnInfo columnInfo = (RealmMyContactsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        com.vam.whitecoats.core.realm.RealmMyContactsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmMyContactsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$doc_id = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$doc_id();
            if (realmGet$doc_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, realmGet$doc_id.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.doc_idColKey, colKey, false);
            }
            String realmGet$name = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameColKey, colKey, realmGet$name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameColKey, colKey, false);
            }
            String realmGet$speciality = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$speciality();
            if (realmGet$speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.specialityColKey, colKey, realmGet$speciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.specialityColKey, colKey, false);
            }
            String realmGet$subspeciality = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$subspeciality();
            if (realmGet$subspeciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.subspecialityColKey, colKey, realmGet$subspeciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.subspecialityColKey, colKey, false);
            }
            String realmGet$pic_name = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$pic_name();
            if (realmGet$pic_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pic_nameColKey, colKey, realmGet$pic_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.pic_nameColKey, colKey, false);
            }
            String realmGet$degree = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$degree();
            if (realmGet$degree != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.degreeColKey, colKey, realmGet$degree, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.degreeColKey, colKey, false);
            }
            String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$workplace();
            if (realmGet$workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.workplaceColKey, colKey, false);
            }
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
            }
            String realmGet$networkStatus = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$networkStatus();
            if (realmGet$networkStatus != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.networkStatusColKey, colKey, realmGet$networkStatus, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.networkStatusColKey, colKey, false);
            }
            String realmGet$email = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.emailColKey, colKey, false);
            }
            String realmGet$email_vis = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$email_vis();
            if (realmGet$email_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.email_visColKey, colKey, realmGet$email_vis, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.email_visColKey, colKey, false);
            }
            String realmGet$phno = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$phno();
            if (realmGet$phno != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phnoColKey, colKey, realmGet$phno, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phnoColKey, colKey, false);
            }
            String realmGet$phno_vis = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$phno_vis();
            if (realmGet$phno_vis != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phno_visColKey, colKey, realmGet$phno_vis, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phno_visColKey, colKey, false);
            }
            String realmGet$pic_url = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$pic_url();
            if (realmGet$pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.pic_urlColKey, colKey, realmGet$pic_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.pic_urlColKey, colKey, false);
            }
            String realmGet$user_salutation = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$user_salutation();
            if (realmGet$user_salutation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.user_salutationColKey, colKey, realmGet$user_salutation, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.user_salutationColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.user_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$user_type_id(), false);
            String realmGet$UUID = ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$UUID();
            if (realmGet$UUID != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.UUIDColKey, colKey, realmGet$UUID, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.UUIDColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.qb_useridColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) object).realmGet$qb_userid(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmMyContactsInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmMyContactsInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmMyContactsInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmMyContactsInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmMyContactsInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmMyContactsInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$doc_id(realmSource.realmGet$doc_id());
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());
        unmanagedCopy.realmSet$speciality(realmSource.realmGet$speciality());
        unmanagedCopy.realmSet$subspeciality(realmSource.realmGet$subspeciality());
        unmanagedCopy.realmSet$pic_name(realmSource.realmGet$pic_name());
        unmanagedCopy.realmSet$degree(realmSource.realmGet$degree());
        unmanagedCopy.realmSet$workplace(realmSource.realmGet$workplace());
        unmanagedCopy.realmSet$location(realmSource.realmGet$location());
        unmanagedCopy.realmSet$networkStatus(realmSource.realmGet$networkStatus());
        unmanagedCopy.realmSet$email(realmSource.realmGet$email());
        unmanagedCopy.realmSet$email_vis(realmSource.realmGet$email_vis());
        unmanagedCopy.realmSet$phno(realmSource.realmGet$phno());
        unmanagedCopy.realmSet$phno_vis(realmSource.realmGet$phno_vis());
        unmanagedCopy.realmSet$pic_url(realmSource.realmGet$pic_url());
        unmanagedCopy.realmSet$user_salutation(realmSource.realmGet$user_salutation());
        unmanagedCopy.realmSet$user_type_id(realmSource.realmGet$user_type_id());
        unmanagedCopy.realmSet$UUID(realmSource.realmGet$UUID());
        unmanagedCopy.realmSet$qb_userid(realmSource.realmGet$qb_userid());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmMyContactsInfo = proxy[");
        stringBuilder.append("{doc_id:");
        stringBuilder.append(realmGet$doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{speciality:");
        stringBuilder.append(realmGet$speciality() != null ? realmGet$speciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{subspeciality:");
        stringBuilder.append(realmGet$subspeciality() != null ? realmGet$subspeciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{pic_name:");
        stringBuilder.append(realmGet$pic_name() != null ? realmGet$pic_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{degree:");
        stringBuilder.append(realmGet$degree() != null ? realmGet$degree() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{workplace:");
        stringBuilder.append(realmGet$workplace() != null ? realmGet$workplace() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{location:");
        stringBuilder.append(realmGet$location() != null ? realmGet$location() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{networkStatus:");
        stringBuilder.append(realmGet$networkStatus() != null ? realmGet$networkStatus() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email:");
        stringBuilder.append(realmGet$email() != null ? realmGet$email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email_vis:");
        stringBuilder.append(realmGet$email_vis() != null ? realmGet$email_vis() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phno:");
        stringBuilder.append(realmGet$phno() != null ? realmGet$phno() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phno_vis:");
        stringBuilder.append(realmGet$phno_vis() != null ? realmGet$phno_vis() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{pic_url:");
        stringBuilder.append(realmGet$pic_url() != null ? realmGet$pic_url() : "null");
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
        stringBuilder.append("{UUID:");
        stringBuilder.append(realmGet$UUID() != null ? realmGet$UUID() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{qb_userid:");
        stringBuilder.append(realmGet$qb_userid());
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
        com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy aRealmMyContactsInfo = (com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmMyContactsInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmMyContactsInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmMyContactsInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
