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
public class com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmCaseRoomInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface {

    static final class RealmCaseRoomInfoColumnInfo extends ColumnInfo {
        long caseroom_summary_idColKey;
        long docidColKey;
        long titleColKey;
        long specialityColKey;
        long sub_specialityColKey;
        long queryColKey;
        long statusColKey;
        long createddateColKey;
        long modifieddateColKey;
        long p_detailsColKey;
        long attachmentsColKey;
        long last_messageColKey;
        long last_message_timeColKey;
        long caseroom_dialog_idColKey;

        RealmCaseRoomInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(14);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmCaseRoomInfo");
            this.caseroom_summary_idColKey = addColumnDetails("caseroom_summary_id", "caseroom_summary_id", objectSchemaInfo);
            this.docidColKey = addColumnDetails("docid", "docid", objectSchemaInfo);
            this.titleColKey = addColumnDetails("title", "title", objectSchemaInfo);
            this.specialityColKey = addColumnDetails("speciality", "speciality", objectSchemaInfo);
            this.sub_specialityColKey = addColumnDetails("sub_speciality", "sub_speciality", objectSchemaInfo);
            this.queryColKey = addColumnDetails("query", "query", objectSchemaInfo);
            this.statusColKey = addColumnDetails("status", "status", objectSchemaInfo);
            this.createddateColKey = addColumnDetails("createddate", "createddate", objectSchemaInfo);
            this.modifieddateColKey = addColumnDetails("modifieddate", "modifieddate", objectSchemaInfo);
            this.p_detailsColKey = addColumnDetails("p_details", "p_details", objectSchemaInfo);
            this.attachmentsColKey = addColumnDetails("attachments", "attachments", objectSchemaInfo);
            this.last_messageColKey = addColumnDetails("last_message", "last_message", objectSchemaInfo);
            this.last_message_timeColKey = addColumnDetails("last_message_time", "last_message_time", objectSchemaInfo);
            this.caseroom_dialog_idColKey = addColumnDetails("caseroom_dialog_id", "caseroom_dialog_id", objectSchemaInfo);
        }

        RealmCaseRoomInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmCaseRoomInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmCaseRoomInfoColumnInfo src = (RealmCaseRoomInfoColumnInfo) rawSrc;
            final RealmCaseRoomInfoColumnInfo dst = (RealmCaseRoomInfoColumnInfo) rawDst;
            dst.caseroom_summary_idColKey = src.caseroom_summary_idColKey;
            dst.docidColKey = src.docidColKey;
            dst.titleColKey = src.titleColKey;
            dst.specialityColKey = src.specialityColKey;
            dst.sub_specialityColKey = src.sub_specialityColKey;
            dst.queryColKey = src.queryColKey;
            dst.statusColKey = src.statusColKey;
            dst.createddateColKey = src.createddateColKey;
            dst.modifieddateColKey = src.modifieddateColKey;
            dst.p_detailsColKey = src.p_detailsColKey;
            dst.attachmentsColKey = src.attachmentsColKey;
            dst.last_messageColKey = src.last_messageColKey;
            dst.last_message_timeColKey = src.last_message_timeColKey;
            dst.caseroom_dialog_idColKey = src.caseroom_dialog_idColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmCaseRoomInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmCaseRoomInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'caseroom_summary_id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$docid() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.docidColKey);
    }

    @Override
    public void realmSet$docid(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.docidColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.docidColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$title() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.titleColKey);
    }

    @Override
    public void realmSet$title(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.titleColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.titleColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.titleColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.titleColKey, value);
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
    public String realmGet$sub_speciality() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.sub_specialityColKey);
    }

    @Override
    public void realmSet$sub_speciality(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.sub_specialityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.sub_specialityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.sub_specialityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.sub_specialityColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$query() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.queryColKey);
    }

    @Override
    public void realmSet$query(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.queryColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.queryColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.queryColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.queryColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$status() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.statusColKey);
    }

    @Override
    public void realmSet$status(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.statusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.statusColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$createddate() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.createddateColKey);
    }

    @Override
    public void realmSet$createddate(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.createddateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.createddateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$modifieddate() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.modifieddateColKey);
    }

    @Override
    public void realmSet$modifieddate(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.modifieddateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.modifieddateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$p_details() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.p_detailsColKey);
    }

    @Override
    public void realmSet$p_details(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.p_detailsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.p_detailsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$attachments() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.attachmentsColKey);
    }

    @Override
    public void realmSet$attachments(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.attachmentsColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.attachmentsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.attachmentsColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.attachmentsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$last_message() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.last_messageColKey);
    }

    @Override
    public void realmSet$last_message(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.last_messageColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.last_messageColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.last_messageColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.last_messageColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$last_message_time() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.last_message_timeColKey);
    }

    @Override
    public void realmSet$last_message_time(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.last_message_timeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.last_message_timeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caseroom_dialog_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.caseroom_dialog_idColKey);
    }

    @Override
    public void realmSet$caseroom_dialog_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.caseroom_dialog_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.caseroom_dialog_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.caseroom_dialog_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.caseroom_dialog_idColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmCaseRoomInfo", 14, 0);
        builder.addPersistedProperty("caseroom_summary_id", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("docid", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("speciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("sub_speciality", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("query", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("createddate", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("modifieddate", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("p_details", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("attachments", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("last_message", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("last_message_time", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("caseroom_dialog_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmCaseRoomInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmCaseRoomInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmCaseRoomInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmCaseRoomInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmCaseRoomInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmCaseRoomInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
            RealmCaseRoomInfoColumnInfo columnInfo = (RealmCaseRoomInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
            long pkColumnKey = columnInfo.caseroom_summary_idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("caseroom_summary_id")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("caseroom_summary_id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("caseroom_summary_id")) {
                if (json.isNull("caseroom_summary_id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class, json.getString("caseroom_summary_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'caseroom_summary_id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) obj;
        if (json.has("docid")) {
            if (json.isNull("docid")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'docid' to null.");
            } else {
                objProxy.realmSet$docid((int) json.getInt("docid"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("speciality")) {
            if (json.isNull("speciality")) {
                objProxy.realmSet$speciality(null);
            } else {
                objProxy.realmSet$speciality((String) json.getString("speciality"));
            }
        }
        if (json.has("sub_speciality")) {
            if (json.isNull("sub_speciality")) {
                objProxy.realmSet$sub_speciality(null);
            } else {
                objProxy.realmSet$sub_speciality((String) json.getString("sub_speciality"));
            }
        }
        if (json.has("query")) {
            if (json.isNull("query")) {
                objProxy.realmSet$query(null);
            } else {
                objProxy.realmSet$query((String) json.getString("query"));
            }
        }
        if (json.has("status")) {
            if (json.isNull("status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
            } else {
                objProxy.realmSet$status((int) json.getInt("status"));
            }
        }
        if (json.has("createddate")) {
            if (json.isNull("createddate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'createddate' to null.");
            } else {
                objProxy.realmSet$createddate((long) json.getLong("createddate"));
            }
        }
        if (json.has("modifieddate")) {
            if (json.isNull("modifieddate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'modifieddate' to null.");
            } else {
                objProxy.realmSet$modifieddate((long) json.getLong("modifieddate"));
            }
        }
        if (json.has("p_details")) {
            if (json.isNull("p_details")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'p_details' to null.");
            } else {
                objProxy.realmSet$p_details((boolean) json.getBoolean("p_details"));
            }
        }
        if (json.has("attachments")) {
            if (json.isNull("attachments")) {
                objProxy.realmSet$attachments(null);
            } else {
                objProxy.realmSet$attachments((String) json.getString("attachments"));
            }
        }
        if (json.has("last_message")) {
            if (json.isNull("last_message")) {
                objProxy.realmSet$last_message(null);
            } else {
                objProxy.realmSet$last_message((String) json.getString("last_message"));
            }
        }
        if (json.has("last_message_time")) {
            if (json.isNull("last_message_time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'last_message_time' to null.");
            } else {
                objProxy.realmSet$last_message_time((long) json.getLong("last_message_time"));
            }
        }
        if (json.has("caseroom_dialog_id")) {
            if (json.isNull("caseroom_dialog_id")) {
                objProxy.realmSet$caseroom_dialog_id(null);
            } else {
                objProxy.realmSet$caseroom_dialog_id((String) json.getString("caseroom_dialog_id"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmCaseRoomInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmCaseRoomInfo obj = new com.vam.whitecoats.core.realm.RealmCaseRoomInfo();
        final com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("caseroom_summary_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_summary_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroom_summary_id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("docid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$docid((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'docid' to null.");
                }
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else if (name.equals("speciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$speciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$speciality(null);
                }
            } else if (name.equals("sub_speciality")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$sub_speciality((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$sub_speciality(null);
                }
            } else if (name.equals("query")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$query((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$query(null);
                }
            } else if (name.equals("status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'status' to null.");
                }
            } else if (name.equals("createddate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$createddate((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'createddate' to null.");
                }
            } else if (name.equals("modifieddate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$modifieddate((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'modifieddate' to null.");
                }
            } else if (name.equals("p_details")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$p_details((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'p_details' to null.");
                }
            } else if (name.equals("attachments")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$attachments((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$attachments(null);
                }
            } else if (name.equals("last_message")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$last_message((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$last_message(null);
                }
            } else if (name.equals("last_message_time")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$last_message_time((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'last_message_time' to null.");
                }
            } else if (name.equals("caseroom_dialog_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_dialog_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroom_dialog_id(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'caseroom_summary_id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomInfo copyOrUpdate(Realm realm, RealmCaseRoomInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmCaseRoomInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
            long pkColumnKey = columnInfo.caseroom_summary_idColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_summary_id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomInfo copy(Realm realm, RealmCaseRoomInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.caseroom_summary_idColKey, realmObjectSource.realmGet$caseroom_summary_id());
        builder.addInteger(columnInfo.docidColKey, realmObjectSource.realmGet$docid());
        builder.addString(columnInfo.titleColKey, realmObjectSource.realmGet$title());
        builder.addString(columnInfo.specialityColKey, realmObjectSource.realmGet$speciality());
        builder.addString(columnInfo.sub_specialityColKey, realmObjectSource.realmGet$sub_speciality());
        builder.addString(columnInfo.queryColKey, realmObjectSource.realmGet$query());
        builder.addInteger(columnInfo.statusColKey, realmObjectSource.realmGet$status());
        builder.addInteger(columnInfo.createddateColKey, realmObjectSource.realmGet$createddate());
        builder.addInteger(columnInfo.modifieddateColKey, realmObjectSource.realmGet$modifieddate());
        builder.addBoolean(columnInfo.p_detailsColKey, realmObjectSource.realmGet$p_details());
        builder.addString(columnInfo.attachmentsColKey, realmObjectSource.realmGet$attachments());
        builder.addString(columnInfo.last_messageColKey, realmObjectSource.realmGet$last_message());
        builder.addInteger(columnInfo.last_message_timeColKey, realmObjectSource.realmGet$last_message_time());
        builder.addString(columnInfo.caseroom_dialog_idColKey, realmObjectSource.realmGet$caseroom_dialog_id());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomInfoColumnInfo columnInfo = (RealmCaseRoomInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        long pkColumnKey = columnInfo.caseroom_summary_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.docidColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$docid(), false);
        String realmGet$title = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleColKey, colKey, realmGet$title, false);
        }
        String realmGet$speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$speciality();
        if (realmGet$speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.specialityColKey, colKey, realmGet$speciality, false);
        }
        String realmGet$sub_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$sub_speciality();
        if (realmGet$sub_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sub_specialityColKey, colKey, realmGet$sub_speciality, false);
        }
        String realmGet$query = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$query();
        if (realmGet$query != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.queryColKey, colKey, realmGet$query, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$status(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.createddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$createddate(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.modifieddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$modifieddate(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.p_detailsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$p_details(), false);
        String realmGet$attachments = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$attachments();
        if (realmGet$attachments != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.attachmentsColKey, colKey, realmGet$attachments, false);
        }
        String realmGet$last_message = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$last_message();
        if (realmGet$last_message != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_messageColKey, colKey, realmGet$last_message, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.last_message_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$last_message_time(), false);
        String realmGet$caseroom_dialog_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_dialog_id();
        if (realmGet$caseroom_dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_dialog_idColKey, colKey, realmGet$caseroom_dialog_id, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomInfoColumnInfo columnInfo = (RealmCaseRoomInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        long pkColumnKey = columnInfo.caseroom_summary_idColKey;
        com.vam.whitecoats.core.realm.RealmCaseRoomInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.docidColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$docid(), false);
            String realmGet$title = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleColKey, colKey, realmGet$title, false);
            }
            String realmGet$speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$speciality();
            if (realmGet$speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.specialityColKey, colKey, realmGet$speciality, false);
            }
            String realmGet$sub_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$sub_speciality();
            if (realmGet$sub_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.sub_specialityColKey, colKey, realmGet$sub_speciality, false);
            }
            String realmGet$query = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$query();
            if (realmGet$query != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.queryColKey, colKey, realmGet$query, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$status(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.createddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$createddate(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.modifieddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$modifieddate(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.p_detailsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$p_details(), false);
            String realmGet$attachments = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$attachments();
            if (realmGet$attachments != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.attachmentsColKey, colKey, realmGet$attachments, false);
            }
            String realmGet$last_message = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$last_message();
            if (realmGet$last_message != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_messageColKey, colKey, realmGet$last_message, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.last_message_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$last_message_time(), false);
            String realmGet$caseroom_dialog_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_dialog_id();
            if (realmGet$caseroom_dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_dialog_idColKey, colKey, realmGet$caseroom_dialog_id, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomInfoColumnInfo columnInfo = (RealmCaseRoomInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        long pkColumnKey = columnInfo.caseroom_summary_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.docidColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$docid(), false);
        String realmGet$title = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleColKey, colKey, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleColKey, colKey, false);
        }
        String realmGet$speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$speciality();
        if (realmGet$speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.specialityColKey, colKey, realmGet$speciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.specialityColKey, colKey, false);
        }
        String realmGet$sub_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$sub_speciality();
        if (realmGet$sub_speciality != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.sub_specialityColKey, colKey, realmGet$sub_speciality, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sub_specialityColKey, colKey, false);
        }
        String realmGet$query = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$query();
        if (realmGet$query != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.queryColKey, colKey, realmGet$query, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.queryColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$status(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.createddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$createddate(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.modifieddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$modifieddate(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.p_detailsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$p_details(), false);
        String realmGet$attachments = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$attachments();
        if (realmGet$attachments != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.attachmentsColKey, colKey, realmGet$attachments, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.attachmentsColKey, colKey, false);
        }
        String realmGet$last_message = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$last_message();
        if (realmGet$last_message != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_messageColKey, colKey, realmGet$last_message, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.last_messageColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.last_message_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$last_message_time(), false);
        String realmGet$caseroom_dialog_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_dialog_id();
        if (realmGet$caseroom_dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_dialog_idColKey, colKey, realmGet$caseroom_dialog_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_dialog_idColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomInfoColumnInfo columnInfo = (RealmCaseRoomInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        long pkColumnKey = columnInfo.caseroom_summary_idColKey;
        com.vam.whitecoats.core.realm.RealmCaseRoomInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.docidColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$docid(), false);
            String realmGet$title = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleColKey, colKey, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleColKey, colKey, false);
            }
            String realmGet$speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$speciality();
            if (realmGet$speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.specialityColKey, colKey, realmGet$speciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.specialityColKey, colKey, false);
            }
            String realmGet$sub_speciality = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$sub_speciality();
            if (realmGet$sub_speciality != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.sub_specialityColKey, colKey, realmGet$sub_speciality, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.sub_specialityColKey, colKey, false);
            }
            String realmGet$query = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$query();
            if (realmGet$query != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.queryColKey, colKey, realmGet$query, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.queryColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$status(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.createddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$createddate(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.modifieddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$modifieddate(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.p_detailsColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$p_details(), false);
            String realmGet$attachments = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$attachments();
            if (realmGet$attachments != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.attachmentsColKey, colKey, realmGet$attachments, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.attachmentsColKey, colKey, false);
            }
            String realmGet$last_message = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$last_message();
            if (realmGet$last_message != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_messageColKey, colKey, realmGet$last_message, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.last_messageColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.last_message_timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$last_message_time(), false);
            String realmGet$caseroom_dialog_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) object).realmGet$caseroom_dialog_id();
            if (realmGet$caseroom_dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_dialog_idColKey, colKey, realmGet$caseroom_dialog_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_dialog_idColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmCaseRoomInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmCaseRoomInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmCaseRoomInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$caseroom_summary_id(realmSource.realmGet$caseroom_summary_id());
        unmanagedCopy.realmSet$docid(realmSource.realmGet$docid());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$speciality(realmSource.realmGet$speciality());
        unmanagedCopy.realmSet$sub_speciality(realmSource.realmGet$sub_speciality());
        unmanagedCopy.realmSet$query(realmSource.realmGet$query());
        unmanagedCopy.realmSet$status(realmSource.realmGet$status());
        unmanagedCopy.realmSet$createddate(realmSource.realmGet$createddate());
        unmanagedCopy.realmSet$modifieddate(realmSource.realmGet$modifieddate());
        unmanagedCopy.realmSet$p_details(realmSource.realmGet$p_details());
        unmanagedCopy.realmSet$attachments(realmSource.realmGet$attachments());
        unmanagedCopy.realmSet$last_message(realmSource.realmGet$last_message());
        unmanagedCopy.realmSet$last_message_time(realmSource.realmGet$last_message_time());
        unmanagedCopy.realmSet$caseroom_dialog_id(realmSource.realmGet$caseroom_dialog_id());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmCaseRoomInfo update(Realm realm, RealmCaseRoomInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomInfo realmObject, com.vam.whitecoats.core.realm.RealmCaseRoomInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.caseroom_summary_idColKey, realmObjectSource.realmGet$caseroom_summary_id());
        builder.addInteger(columnInfo.docidColKey, realmObjectSource.realmGet$docid());
        builder.addString(columnInfo.titleColKey, realmObjectSource.realmGet$title());
        builder.addString(columnInfo.specialityColKey, realmObjectSource.realmGet$speciality());
        builder.addString(columnInfo.sub_specialityColKey, realmObjectSource.realmGet$sub_speciality());
        builder.addString(columnInfo.queryColKey, realmObjectSource.realmGet$query());
        builder.addInteger(columnInfo.statusColKey, realmObjectSource.realmGet$status());
        builder.addInteger(columnInfo.createddateColKey, realmObjectSource.realmGet$createddate());
        builder.addInteger(columnInfo.modifieddateColKey, realmObjectSource.realmGet$modifieddate());
        builder.addBoolean(columnInfo.p_detailsColKey, realmObjectSource.realmGet$p_details());
        builder.addString(columnInfo.attachmentsColKey, realmObjectSource.realmGet$attachments());
        builder.addString(columnInfo.last_messageColKey, realmObjectSource.realmGet$last_message());
        builder.addInteger(columnInfo.last_message_timeColKey, realmObjectSource.realmGet$last_message_time());
        builder.addString(columnInfo.caseroom_dialog_idColKey, realmObjectSource.realmGet$caseroom_dialog_id());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmCaseRoomInfo = proxy[");
        stringBuilder.append("{caseroom_summary_id:");
        stringBuilder.append(realmGet$caseroom_summary_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{docid:");
        stringBuilder.append(realmGet$docid());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{speciality:");
        stringBuilder.append(realmGet$speciality() != null ? realmGet$speciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{sub_speciality:");
        stringBuilder.append(realmGet$sub_speciality() != null ? realmGet$sub_speciality() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{query:");
        stringBuilder.append(realmGet$query() != null ? realmGet$query() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{status:");
        stringBuilder.append(realmGet$status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{createddate:");
        stringBuilder.append(realmGet$createddate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{modifieddate:");
        stringBuilder.append(realmGet$modifieddate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{p_details:");
        stringBuilder.append(realmGet$p_details());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{attachments:");
        stringBuilder.append(realmGet$attachments() != null ? realmGet$attachments() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_message:");
        stringBuilder.append(realmGet$last_message() != null ? realmGet$last_message() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_message_time:");
        stringBuilder.append(realmGet$last_message_time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{caseroom_dialog_id:");
        stringBuilder.append(realmGet$caseroom_dialog_id() != null ? realmGet$caseroom_dialog_id() : "null");
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
        com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy aRealmCaseRoomInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmCaseRoomInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmCaseRoomInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmCaseRoomInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
