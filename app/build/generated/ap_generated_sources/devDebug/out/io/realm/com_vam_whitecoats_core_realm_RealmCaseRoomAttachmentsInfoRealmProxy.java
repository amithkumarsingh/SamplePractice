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
public class com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface {

    static final class RealmCaseRoomAttachmentsInfoColumnInfo extends ColumnInfo {
        long caseroom_summary_idColKey;
        long attachnameColKey;
        long attachuploadstatusColKey;
        long qb_attach_idColKey;

        RealmCaseRoomAttachmentsInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmCaseRoomAttachmentsInfo");
            this.caseroom_summary_idColKey = addColumnDetails("caseroom_summary_id", "caseroom_summary_id", objectSchemaInfo);
            this.attachnameColKey = addColumnDetails("attachname", "attachname", objectSchemaInfo);
            this.attachuploadstatusColKey = addColumnDetails("attachuploadstatus", "attachuploadstatus", objectSchemaInfo);
            this.qb_attach_idColKey = addColumnDetails("qb_attach_id", "qb_attach_id", objectSchemaInfo);
        }

        RealmCaseRoomAttachmentsInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmCaseRoomAttachmentsInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmCaseRoomAttachmentsInfoColumnInfo src = (RealmCaseRoomAttachmentsInfoColumnInfo) rawSrc;
            final RealmCaseRoomAttachmentsInfoColumnInfo dst = (RealmCaseRoomAttachmentsInfoColumnInfo) rawDst;
            dst.caseroom_summary_idColKey = src.caseroom_summary_idColKey;
            dst.attachnameColKey = src.attachnameColKey;
            dst.attachuploadstatusColKey = src.attachuploadstatusColKey;
            dst.qb_attach_idColKey = src.qb_attach_idColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmCaseRoomAttachmentsInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmCaseRoomAttachmentsInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo>(this);
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
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'caseroom_summary_id' to null.");
            }
            row.getTable().setString(columnInfo.caseroom_summary_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'caseroom_summary_id' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.caseroom_summary_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$attachname() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.attachnameColKey);
    }

    @Override
    public void realmSet$attachname(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.attachnameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.attachnameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.attachnameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.attachnameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$attachuploadstatus() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.attachuploadstatusColKey);
    }

    @Override
    public void realmSet$attachuploadstatus(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.attachuploadstatusColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.attachuploadstatusColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$qb_attach_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.qb_attach_idColKey);
    }

    @Override
    public void realmSet$qb_attach_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.qb_attach_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.qb_attach_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.qb_attach_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.qb_attach_idColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmCaseRoomAttachmentsInfo", 4, 0);
        builder.addPersistedProperty("caseroom_summary_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("attachname", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("attachuploadstatus", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("qb_attach_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmCaseRoomAttachmentsInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmCaseRoomAttachmentsInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmCaseRoomAttachmentsInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmCaseRoomAttachmentsInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) obj;
        if (json.has("caseroom_summary_id")) {
            if (json.isNull("caseroom_summary_id")) {
                objProxy.realmSet$caseroom_summary_id(null);
            } else {
                objProxy.realmSet$caseroom_summary_id((String) json.getString("caseroom_summary_id"));
            }
        }
        if (json.has("attachname")) {
            if (json.isNull("attachname")) {
                objProxy.realmSet$attachname(null);
            } else {
                objProxy.realmSet$attachname((String) json.getString("attachname"));
            }
        }
        if (json.has("attachuploadstatus")) {
            if (json.isNull("attachuploadstatus")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'attachuploadstatus' to null.");
            } else {
                objProxy.realmSet$attachuploadstatus((int) json.getInt("attachuploadstatus"));
            }
        }
        if (json.has("qb_attach_id")) {
            if (json.isNull("qb_attach_id")) {
                objProxy.realmSet$qb_attach_id(null);
            } else {
                objProxy.realmSet$qb_attach_id((String) json.getString("qb_attach_id"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo obj = new com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo();
        final com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) obj;
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
            } else if (name.equals("attachname")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$attachname((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$attachname(null);
                }
            } else if (name.equals("attachuploadstatus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$attachuploadstatus((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'attachuploadstatus' to null.");
                }
            } else if (name.equals("qb_attach_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$qb_attach_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$qb_attach_id(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo copyOrUpdate(Realm realm, RealmCaseRoomAttachmentsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo copy(Realm realm, RealmCaseRoomAttachmentsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.caseroom_summary_idColKey, realmObjectSource.realmGet$caseroom_summary_id());
        builder.addString(columnInfo.attachnameColKey, realmObjectSource.realmGet$attachname());
        builder.addInteger(columnInfo.attachuploadstatusColKey, realmObjectSource.realmGet$attachuploadstatus());
        builder.addString(columnInfo.qb_attach_idColKey, realmObjectSource.realmGet$qb_attach_id());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomAttachmentsInfoColumnInfo columnInfo = (RealmCaseRoomAttachmentsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
        if (realmGet$caseroom_summary_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
        }
        String realmGet$attachname = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$attachname();
        if (realmGet$attachname != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.attachnameColKey, colKey, realmGet$attachname, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.attachuploadstatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$attachuploadstatus(), false);
        String realmGet$qb_attach_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$qb_attach_id();
        if (realmGet$qb_attach_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_attach_idColKey, colKey, realmGet$qb_attach_id, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomAttachmentsInfoColumnInfo columnInfo = (RealmCaseRoomAttachmentsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
            if (realmGet$caseroom_summary_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
            }
            String realmGet$attachname = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$attachname();
            if (realmGet$attachname != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.attachnameColKey, colKey, realmGet$attachname, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.attachuploadstatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$attachuploadstatus(), false);
            String realmGet$qb_attach_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$qb_attach_id();
            if (realmGet$qb_attach_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_attach_idColKey, colKey, realmGet$qb_attach_id, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomAttachmentsInfoColumnInfo columnInfo = (RealmCaseRoomAttachmentsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
        if (realmGet$caseroom_summary_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, false);
        }
        String realmGet$attachname = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$attachname();
        if (realmGet$attachname != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.attachnameColKey, colKey, realmGet$attachname, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.attachnameColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.attachuploadstatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$attachuploadstatus(), false);
        String realmGet$qb_attach_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$qb_attach_id();
        if (realmGet$qb_attach_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_attach_idColKey, colKey, realmGet$qb_attach_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.qb_attach_idColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomAttachmentsInfoColumnInfo columnInfo = (RealmCaseRoomAttachmentsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
            if (realmGet$caseroom_summary_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, false);
            }
            String realmGet$attachname = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$attachname();
            if (realmGet$attachname != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.attachnameColKey, colKey, realmGet$attachname, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.attachnameColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.attachuploadstatusColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$attachuploadstatus(), false);
            String realmGet$qb_attach_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) object).realmGet$qb_attach_id();
            if (realmGet$qb_attach_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_attach_idColKey, colKey, realmGet$qb_attach_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.qb_attach_idColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$caseroom_summary_id(realmSource.realmGet$caseroom_summary_id());
        unmanagedCopy.realmSet$attachname(realmSource.realmGet$attachname());
        unmanagedCopy.realmSet$attachuploadstatus(realmSource.realmGet$attachuploadstatus());
        unmanagedCopy.realmSet$qb_attach_id(realmSource.realmGet$qb_attach_id());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmCaseRoomAttachmentsInfo = proxy[");
        stringBuilder.append("{caseroom_summary_id:");
        stringBuilder.append(realmGet$caseroom_summary_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{attachname:");
        stringBuilder.append(realmGet$attachname() != null ? realmGet$attachname() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{attachuploadstatus:");
        stringBuilder.append(realmGet$attachuploadstatus());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{qb_attach_id:");
        stringBuilder.append(realmGet$qb_attach_id() != null ? realmGet$qb_attach_id() : "null");
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
        com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy aRealmCaseRoomAttachmentsInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmCaseRoomAttachmentsInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmCaseRoomAttachmentsInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmCaseRoomAttachmentsInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
