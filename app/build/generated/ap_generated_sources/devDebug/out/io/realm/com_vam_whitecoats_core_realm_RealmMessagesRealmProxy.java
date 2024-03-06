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
public class com_vam_whitecoats_core_realm_RealmMessagesRealmProxy extends com.vam.whitecoats.core.realm.RealmMessages
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface {

    static final class RealmMessagesColumnInfo extends ColumnInfo {
        long idColKey;
        long dialogIdColKey;
        long messageColKey;
        long timeColKey;
        long other_doc_idColKey;
        long message_typeColKey;
        long att_typeColKey;
        long message_satusColKey;
        long att_statusColKey;
        long att_qbidColKey;
        long attachUrlColKey;
        long isSyncColKey;
        long senderIdColKey;
        long msg_typeColKey;

        RealmMessagesColumnInfo(OsSchemaInfo schemaInfo) {
            super(14);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmMessages");
            this.idColKey = addColumnDetails("id", "id", objectSchemaInfo);
            this.dialogIdColKey = addColumnDetails("dialogId", "dialogId", objectSchemaInfo);
            this.messageColKey = addColumnDetails("message", "message", objectSchemaInfo);
            this.timeColKey = addColumnDetails("time", "time", objectSchemaInfo);
            this.other_doc_idColKey = addColumnDetails("other_doc_id", "other_doc_id", objectSchemaInfo);
            this.message_typeColKey = addColumnDetails("message_type", "message_type", objectSchemaInfo);
            this.att_typeColKey = addColumnDetails("att_type", "att_type", objectSchemaInfo);
            this.message_satusColKey = addColumnDetails("message_satus", "message_satus", objectSchemaInfo);
            this.att_statusColKey = addColumnDetails("att_status", "att_status", objectSchemaInfo);
            this.att_qbidColKey = addColumnDetails("att_qbid", "att_qbid", objectSchemaInfo);
            this.attachUrlColKey = addColumnDetails("attachUrl", "attachUrl", objectSchemaInfo);
            this.isSyncColKey = addColumnDetails("isSync", "isSync", objectSchemaInfo);
            this.senderIdColKey = addColumnDetails("senderId", "senderId", objectSchemaInfo);
            this.msg_typeColKey = addColumnDetails("msg_type", "msg_type", objectSchemaInfo);
        }

        RealmMessagesColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmMessagesColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmMessagesColumnInfo src = (RealmMessagesColumnInfo) rawSrc;
            final RealmMessagesColumnInfo dst = (RealmMessagesColumnInfo) rawDst;
            dst.idColKey = src.idColKey;
            dst.dialogIdColKey = src.dialogIdColKey;
            dst.messageColKey = src.messageColKey;
            dst.timeColKey = src.timeColKey;
            dst.other_doc_idColKey = src.other_doc_idColKey;
            dst.message_typeColKey = src.message_typeColKey;
            dst.att_typeColKey = src.att_typeColKey;
            dst.message_satusColKey = src.message_satusColKey;
            dst.att_statusColKey = src.att_statusColKey;
            dst.att_qbidColKey = src.att_qbidColKey;
            dst.attachUrlColKey = src.attachUrlColKey;
            dst.isSyncColKey = src.isSyncColKey;
            dst.senderIdColKey = src.senderIdColKey;
            dst.msg_typeColKey = src.msg_typeColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmMessagesColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmMessages> proxyState;

    com_vam_whitecoats_core_realm_RealmMessagesRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmMessagesColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmMessages>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.idColKey);
    }

    @Override
    public void realmSet$id(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialogId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialogIdColKey);
    }

    @Override
    public void realmSet$dialogId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dialogIdColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dialogIdColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dialogIdColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dialogIdColKey, value);
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
    public int realmGet$other_doc_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.other_doc_idColKey);
    }

    @Override
    public void realmSet$other_doc_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.other_doc_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.other_doc_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$message_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.message_typeColKey);
    }

    @Override
    public void realmSet$message_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.message_typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.message_typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.message_typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.message_typeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$att_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.att_typeColKey);
    }

    @Override
    public void realmSet$att_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.att_typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.att_typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.att_typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.att_typeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$message_satus() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.message_satusColKey);
    }

    @Override
    public void realmSet$message_satus(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.message_satusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.message_satusColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$att_status() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.att_statusColKey);
    }

    @Override
    public void realmSet$att_status(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.att_statusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.att_statusColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$att_qbid() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.att_qbidColKey);
    }

    @Override
    public void realmSet$att_qbid(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.att_qbidColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.att_qbidColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.att_qbidColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.att_qbidColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$attachUrl() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.attachUrlColKey);
    }

    @Override
    public void realmSet$attachUrl(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.attachUrlColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.attachUrlColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.attachUrlColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.attachUrlColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$isSync() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isSyncColKey);
    }

    @Override
    public void realmSet$isSync(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.isSyncColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.isSyncColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$senderId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.senderIdColKey);
    }

    @Override
    public void realmSet$senderId(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.senderIdColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.senderIdColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$msg_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.msg_typeColKey);
    }

    @Override
    public void realmSet$msg_type(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.msg_typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.msg_typeColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmMessages", 14, 0);
        builder.addPersistedProperty("id", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("dialogId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("message", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("time", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("other_doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("message_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("att_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("message_satus", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("att_status", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("att_qbid", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("attachUrl", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("isSync", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("senderId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("msg_type", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmMessagesColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmMessagesColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmMessages";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmMessages";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmMessages createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmMessages obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMessages.class);
            RealmMessagesColumnInfo columnInfo = (RealmMessagesColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMessages.class);
            long pkColumnKey = columnInfo.idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("id")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMessages.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmMessages.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmMessages.class, json.getString("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) obj;
        if (json.has("dialogId")) {
            if (json.isNull("dialogId")) {
                objProxy.realmSet$dialogId(null);
            } else {
                objProxy.realmSet$dialogId((String) json.getString("dialogId"));
            }
        }
        if (json.has("message")) {
            if (json.isNull("message")) {
                objProxy.realmSet$message(null);
            } else {
                objProxy.realmSet$message((String) json.getString("message"));
            }
        }
        if (json.has("time")) {
            if (json.isNull("time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
            } else {
                objProxy.realmSet$time((long) json.getLong("time"));
            }
        }
        if (json.has("other_doc_id")) {
            if (json.isNull("other_doc_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'other_doc_id' to null.");
            } else {
                objProxy.realmSet$other_doc_id((int) json.getInt("other_doc_id"));
            }
        }
        if (json.has("message_type")) {
            if (json.isNull("message_type")) {
                objProxy.realmSet$message_type(null);
            } else {
                objProxy.realmSet$message_type((String) json.getString("message_type"));
            }
        }
        if (json.has("att_type")) {
            if (json.isNull("att_type")) {
                objProxy.realmSet$att_type(null);
            } else {
                objProxy.realmSet$att_type((String) json.getString("att_type"));
            }
        }
        if (json.has("message_satus")) {
            if (json.isNull("message_satus")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'message_satus' to null.");
            } else {
                objProxy.realmSet$message_satus((int) json.getInt("message_satus"));
            }
        }
        if (json.has("att_status")) {
            if (json.isNull("att_status")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'att_status' to null.");
            } else {
                objProxy.realmSet$att_status((int) json.getInt("att_status"));
            }
        }
        if (json.has("att_qbid")) {
            if (json.isNull("att_qbid")) {
                objProxy.realmSet$att_qbid(null);
            } else {
                objProxy.realmSet$att_qbid((String) json.getString("att_qbid"));
            }
        }
        if (json.has("attachUrl")) {
            if (json.isNull("attachUrl")) {
                objProxy.realmSet$attachUrl(null);
            } else {
                objProxy.realmSet$attachUrl((String) json.getString("attachUrl"));
            }
        }
        if (json.has("isSync")) {
            if (json.isNull("isSync")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isSync' to null.");
            } else {
                objProxy.realmSet$isSync((boolean) json.getBoolean("isSync"));
            }
        }
        if (json.has("senderId")) {
            if (json.isNull("senderId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'senderId' to null.");
            } else {
                objProxy.realmSet$senderId((int) json.getInt("senderId"));
            }
        }
        if (json.has("msg_type")) {
            if (json.isNull("msg_type")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'msg_type' to null.");
            } else {
                objProxy.realmSet$msg_type((int) json.getInt("msg_type"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmMessages createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmMessages obj = new com.vam.whitecoats.core.realm.RealmMessages();
        final com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("dialogId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialogId((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialogId(null);
                }
            } else if (name.equals("message")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$message((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$message(null);
                }
            } else if (name.equals("time")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$time((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'time' to null.");
                }
            } else if (name.equals("other_doc_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$other_doc_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'other_doc_id' to null.");
                }
            } else if (name.equals("message_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$message_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$message_type(null);
                }
            } else if (name.equals("att_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$att_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$att_type(null);
                }
            } else if (name.equals("message_satus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$message_satus((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'message_satus' to null.");
                }
            } else if (name.equals("att_status")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$att_status((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'att_status' to null.");
                }
            } else if (name.equals("att_qbid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$att_qbid((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$att_qbid(null);
                }
            } else if (name.equals("attachUrl")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$attachUrl((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$attachUrl(null);
                }
            } else if (name.equals("isSync")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isSync((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isSync' to null.");
                }
            } else if (name.equals("senderId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$senderId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'senderId' to null.");
                }
            } else if (name.equals("msg_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$msg_type((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'msg_type' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmMessagesRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMessages.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmMessages copyOrUpdate(Realm realm, RealmMessagesColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmMessages object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmMessages) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmMessages realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMessages.class);
            long pkColumnKey = columnInfo.idColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmMessages copy(Realm realm, RealmMessagesColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmMessages newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmMessages) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMessages.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.idColKey, realmObjectSource.realmGet$id());
        builder.addString(columnInfo.dialogIdColKey, realmObjectSource.realmGet$dialogId());
        builder.addString(columnInfo.messageColKey, realmObjectSource.realmGet$message());
        builder.addInteger(columnInfo.timeColKey, realmObjectSource.realmGet$time());
        builder.addInteger(columnInfo.other_doc_idColKey, realmObjectSource.realmGet$other_doc_id());
        builder.addString(columnInfo.message_typeColKey, realmObjectSource.realmGet$message_type());
        builder.addString(columnInfo.att_typeColKey, realmObjectSource.realmGet$att_type());
        builder.addInteger(columnInfo.message_satusColKey, realmObjectSource.realmGet$message_satus());
        builder.addInteger(columnInfo.att_statusColKey, realmObjectSource.realmGet$att_status());
        builder.addString(columnInfo.att_qbidColKey, realmObjectSource.realmGet$att_qbid());
        builder.addString(columnInfo.attachUrlColKey, realmObjectSource.realmGet$attachUrl());
        builder.addBoolean(columnInfo.isSyncColKey, realmObjectSource.realmGet$isSync());
        builder.addInteger(columnInfo.senderIdColKey, realmObjectSource.realmGet$senderId());
        builder.addInteger(columnInfo.msg_typeColKey, realmObjectSource.realmGet$msg_type());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmMessages object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMessages.class);
        long tableNativePtr = table.getNativePtr();
        RealmMessagesColumnInfo columnInfo = (RealmMessagesColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMessages.class);
        long pkColumnKey = columnInfo.idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$dialogId = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$dialogId();
        if (realmGet$dialogId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialogIdColKey, colKey, realmGet$dialogId, false);
        }
        String realmGet$message = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message();
        if (realmGet$message != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.messageColKey, colKey, realmGet$message, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$time(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.other_doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$other_doc_id(), false);
        String realmGet$message_type = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message_type();
        if (realmGet$message_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.message_typeColKey, colKey, realmGet$message_type, false);
        }
        String realmGet$att_type = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_type();
        if (realmGet$att_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.att_typeColKey, colKey, realmGet$att_type, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.message_satusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message_satus(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.att_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_status(), false);
        String realmGet$att_qbid = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_qbid();
        if (realmGet$att_qbid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.att_qbidColKey, colKey, realmGet$att_qbid, false);
        }
        String realmGet$attachUrl = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$attachUrl();
        if (realmGet$attachUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.attachUrlColKey, colKey, realmGet$attachUrl, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isSyncColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$isSync(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.senderIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$senderId(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.msg_typeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$msg_type(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMessages.class);
        long tableNativePtr = table.getNativePtr();
        RealmMessagesColumnInfo columnInfo = (RealmMessagesColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMessages.class);
        long pkColumnKey = columnInfo.idColKey;
        com.vam.whitecoats.core.realm.RealmMessages object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmMessages) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$dialogId = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$dialogId();
            if (realmGet$dialogId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialogIdColKey, colKey, realmGet$dialogId, false);
            }
            String realmGet$message = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message();
            if (realmGet$message != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.messageColKey, colKey, realmGet$message, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$time(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.other_doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$other_doc_id(), false);
            String realmGet$message_type = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message_type();
            if (realmGet$message_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.message_typeColKey, colKey, realmGet$message_type, false);
            }
            String realmGet$att_type = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_type();
            if (realmGet$att_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.att_typeColKey, colKey, realmGet$att_type, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.message_satusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message_satus(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.att_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_status(), false);
            String realmGet$att_qbid = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_qbid();
            if (realmGet$att_qbid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.att_qbidColKey, colKey, realmGet$att_qbid, false);
            }
            String realmGet$attachUrl = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$attachUrl();
            if (realmGet$attachUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.attachUrlColKey, colKey, realmGet$attachUrl, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isSyncColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$isSync(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.senderIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$senderId(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.msg_typeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$msg_type(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmMessages object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMessages.class);
        long tableNativePtr = table.getNativePtr();
        RealmMessagesColumnInfo columnInfo = (RealmMessagesColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMessages.class);
        long pkColumnKey = columnInfo.idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$dialogId = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$dialogId();
        if (realmGet$dialogId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialogIdColKey, colKey, realmGet$dialogId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialogIdColKey, colKey, false);
        }
        String realmGet$message = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message();
        if (realmGet$message != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.messageColKey, colKey, realmGet$message, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.messageColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$time(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.other_doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$other_doc_id(), false);
        String realmGet$message_type = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message_type();
        if (realmGet$message_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.message_typeColKey, colKey, realmGet$message_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.message_typeColKey, colKey, false);
        }
        String realmGet$att_type = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_type();
        if (realmGet$att_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.att_typeColKey, colKey, realmGet$att_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.att_typeColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.message_satusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message_satus(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.att_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_status(), false);
        String realmGet$att_qbid = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_qbid();
        if (realmGet$att_qbid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.att_qbidColKey, colKey, realmGet$att_qbid, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.att_qbidColKey, colKey, false);
        }
        String realmGet$attachUrl = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$attachUrl();
        if (realmGet$attachUrl != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.attachUrlColKey, colKey, realmGet$attachUrl, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.attachUrlColKey, colKey, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isSyncColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$isSync(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.senderIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$senderId(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.msg_typeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$msg_type(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMessages.class);
        long tableNativePtr = table.getNativePtr();
        RealmMessagesColumnInfo columnInfo = (RealmMessagesColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMessages.class);
        long pkColumnKey = columnInfo.idColKey;
        com.vam.whitecoats.core.realm.RealmMessages object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmMessages) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$dialogId = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$dialogId();
            if (realmGet$dialogId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialogIdColKey, colKey, realmGet$dialogId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialogIdColKey, colKey, false);
            }
            String realmGet$message = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message();
            if (realmGet$message != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.messageColKey, colKey, realmGet$message, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.messageColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.timeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$time(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.other_doc_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$other_doc_id(), false);
            String realmGet$message_type = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message_type();
            if (realmGet$message_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.message_typeColKey, colKey, realmGet$message_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.message_typeColKey, colKey, false);
            }
            String realmGet$att_type = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_type();
            if (realmGet$att_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.att_typeColKey, colKey, realmGet$att_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.att_typeColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.message_satusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$message_satus(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.att_statusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_status(), false);
            String realmGet$att_qbid = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$att_qbid();
            if (realmGet$att_qbid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.att_qbidColKey, colKey, realmGet$att_qbid, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.att_qbidColKey, colKey, false);
            }
            String realmGet$attachUrl = ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$attachUrl();
            if (realmGet$attachUrl != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.attachUrlColKey, colKey, realmGet$attachUrl, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.attachUrlColKey, colKey, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isSyncColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$isSync(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.senderIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$senderId(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.msg_typeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) object).realmGet$msg_type(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmMessages createDetachedCopy(com.vam.whitecoats.core.realm.RealmMessages realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmMessages unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmMessages();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmMessages) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmMessages) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$dialogId(realmSource.realmGet$dialogId());
        unmanagedCopy.realmSet$message(realmSource.realmGet$message());
        unmanagedCopy.realmSet$time(realmSource.realmGet$time());
        unmanagedCopy.realmSet$other_doc_id(realmSource.realmGet$other_doc_id());
        unmanagedCopy.realmSet$message_type(realmSource.realmGet$message_type());
        unmanagedCopy.realmSet$att_type(realmSource.realmGet$att_type());
        unmanagedCopy.realmSet$message_satus(realmSource.realmGet$message_satus());
        unmanagedCopy.realmSet$att_status(realmSource.realmGet$att_status());
        unmanagedCopy.realmSet$att_qbid(realmSource.realmGet$att_qbid());
        unmanagedCopy.realmSet$attachUrl(realmSource.realmGet$attachUrl());
        unmanagedCopy.realmSet$isSync(realmSource.realmGet$isSync());
        unmanagedCopy.realmSet$senderId(realmSource.realmGet$senderId());
        unmanagedCopy.realmSet$msg_type(realmSource.realmGet$msg_type());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmMessages update(Realm realm, RealmMessagesColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmMessages realmObject, com.vam.whitecoats.core.realm.RealmMessages newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmMessages.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.idColKey, realmObjectSource.realmGet$id());
        builder.addString(columnInfo.dialogIdColKey, realmObjectSource.realmGet$dialogId());
        builder.addString(columnInfo.messageColKey, realmObjectSource.realmGet$message());
        builder.addInteger(columnInfo.timeColKey, realmObjectSource.realmGet$time());
        builder.addInteger(columnInfo.other_doc_idColKey, realmObjectSource.realmGet$other_doc_id());
        builder.addString(columnInfo.message_typeColKey, realmObjectSource.realmGet$message_type());
        builder.addString(columnInfo.att_typeColKey, realmObjectSource.realmGet$att_type());
        builder.addInteger(columnInfo.message_satusColKey, realmObjectSource.realmGet$message_satus());
        builder.addInteger(columnInfo.att_statusColKey, realmObjectSource.realmGet$att_status());
        builder.addString(columnInfo.att_qbidColKey, realmObjectSource.realmGet$att_qbid());
        builder.addString(columnInfo.attachUrlColKey, realmObjectSource.realmGet$attachUrl());
        builder.addBoolean(columnInfo.isSyncColKey, realmObjectSource.realmGet$isSync());
        builder.addInteger(columnInfo.senderIdColKey, realmObjectSource.realmGet$senderId());
        builder.addInteger(columnInfo.msg_typeColKey, realmObjectSource.realmGet$msg_type());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmMessages = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialogId:");
        stringBuilder.append(realmGet$dialogId() != null ? realmGet$dialogId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{message:");
        stringBuilder.append(realmGet$message() != null ? realmGet$message() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{time:");
        stringBuilder.append(realmGet$time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{other_doc_id:");
        stringBuilder.append(realmGet$other_doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{message_type:");
        stringBuilder.append(realmGet$message_type() != null ? realmGet$message_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{att_type:");
        stringBuilder.append(realmGet$att_type() != null ? realmGet$att_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{message_satus:");
        stringBuilder.append(realmGet$message_satus());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{att_status:");
        stringBuilder.append(realmGet$att_status());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{att_qbid:");
        stringBuilder.append(realmGet$att_qbid() != null ? realmGet$att_qbid() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{attachUrl:");
        stringBuilder.append(realmGet$attachUrl() != null ? realmGet$attachUrl() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isSync:");
        stringBuilder.append(realmGet$isSync());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{senderId:");
        stringBuilder.append(realmGet$senderId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{msg_type:");
        stringBuilder.append(realmGet$msg_type());
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
        com_vam_whitecoats_core_realm_RealmMessagesRealmProxy aRealmMessages = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmMessages.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmMessages.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmMessages.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
