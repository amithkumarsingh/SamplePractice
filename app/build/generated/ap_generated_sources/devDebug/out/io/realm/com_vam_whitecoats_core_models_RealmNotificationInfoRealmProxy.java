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
public class com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy extends com.vam.whitecoats.core.models.RealmNotificationInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface {

    static final class RealmNotificationInfoColumnInfo extends ColumnInfo {
        long notificationIDColKey;
        long notifyDataColKey;
        long isAcknowledgedColKey;
        long readStatusColKey;
        long receivedTimeColKey;

        RealmNotificationInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(5);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmNotificationInfo");
            this.notificationIDColKey = addColumnDetails("notificationID", "notificationID", objectSchemaInfo);
            this.notifyDataColKey = addColumnDetails("notifyData", "notifyData", objectSchemaInfo);
            this.isAcknowledgedColKey = addColumnDetails("isAcknowledged", "isAcknowledged", objectSchemaInfo);
            this.readStatusColKey = addColumnDetails("readStatus", "readStatus", objectSchemaInfo);
            this.receivedTimeColKey = addColumnDetails("receivedTime", "receivedTime", objectSchemaInfo);
        }

        RealmNotificationInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmNotificationInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmNotificationInfoColumnInfo src = (RealmNotificationInfoColumnInfo) rawSrc;
            final RealmNotificationInfoColumnInfo dst = (RealmNotificationInfoColumnInfo) rawDst;
            dst.notificationIDColKey = src.notificationIDColKey;
            dst.notifyDataColKey = src.notifyDataColKey;
            dst.isAcknowledgedColKey = src.isAcknowledgedColKey;
            dst.readStatusColKey = src.readStatusColKey;
            dst.receivedTimeColKey = src.receivedTimeColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmNotificationInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.models.RealmNotificationInfo> proxyState;

    com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmNotificationInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.models.RealmNotificationInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$notificationID() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.notificationIDColKey);
    }

    @Override
    public void realmSet$notificationID(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'notificationID' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$notifyData() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.notifyDataColKey);
    }

    @Override
    public void realmSet$notifyData(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.notifyDataColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.notifyDataColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.notifyDataColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.notifyDataColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$isAcknowledged() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isAcknowledgedColKey);
    }

    @Override
    public void realmSet$isAcknowledged(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.isAcknowledgedColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.isAcknowledgedColKey, value);
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
    public long realmGet$receivedTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.receivedTimeColKey);
    }

    @Override
    public void realmSet$receivedTime(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.receivedTimeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.receivedTimeColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmNotificationInfo", 5, 0);
        builder.addPersistedProperty("notificationID", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("notifyData", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("isAcknowledged", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("readStatus", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("receivedTime", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmNotificationInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmNotificationInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmNotificationInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmNotificationInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.models.RealmNotificationInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.models.RealmNotificationInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
            RealmNotificationInfoColumnInfo columnInfo = (RealmNotificationInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
            long pkColumnKey = columnInfo.notificationIDColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("notificationID")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("notificationID"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.models.RealmNotificationInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("notificationID")) {
                if (json.isNull("notificationID")) {
                    obj = (io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.models.RealmNotificationInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.models.RealmNotificationInfo.class, json.getString("notificationID"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'notificationID'.");
            }
        }

        final com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) obj;
        if (json.has("notifyData")) {
            if (json.isNull("notifyData")) {
                objProxy.realmSet$notifyData(null);
            } else {
                objProxy.realmSet$notifyData((String) json.getString("notifyData"));
            }
        }
        if (json.has("isAcknowledged")) {
            if (json.isNull("isAcknowledged")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isAcknowledged' to null.");
            } else {
                objProxy.realmSet$isAcknowledged((boolean) json.getBoolean("isAcknowledged"));
            }
        }
        if (json.has("readStatus")) {
            if (json.isNull("readStatus")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'readStatus' to null.");
            } else {
                objProxy.realmSet$readStatus((boolean) json.getBoolean("readStatus"));
            }
        }
        if (json.has("receivedTime")) {
            if (json.isNull("receivedTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'receivedTime' to null.");
            } else {
                objProxy.realmSet$receivedTime((long) json.getLong("receivedTime"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.models.RealmNotificationInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.models.RealmNotificationInfo obj = new com.vam.whitecoats.core.models.RealmNotificationInfo();
        final com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("notificationID")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$notificationID((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$notificationID(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("notifyData")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$notifyData((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$notifyData(null);
                }
            } else if (name.equals("isAcknowledged")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isAcknowledged((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isAcknowledged' to null.");
                }
            } else if (name.equals("readStatus")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$readStatus((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'readStatus' to null.");
                }
            } else if (name.equals("receivedTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$receivedTime((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'receivedTime' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'notificationID'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.models.RealmNotificationInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.models.RealmNotificationInfo copyOrUpdate(Realm realm, RealmNotificationInfoColumnInfo columnInfo, com.vam.whitecoats.core.models.RealmNotificationInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.models.RealmNotificationInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.models.RealmNotificationInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
            long pkColumnKey = columnInfo.notificationIDColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notificationID());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.models.RealmNotificationInfo copy(Realm realm, RealmNotificationInfoColumnInfo columnInfo, com.vam.whitecoats.core.models.RealmNotificationInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.models.RealmNotificationInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.notificationIDColKey, realmObjectSource.realmGet$notificationID());
        builder.addString(columnInfo.notifyDataColKey, realmObjectSource.realmGet$notifyData());
        builder.addBoolean(columnInfo.isAcknowledgedColKey, realmObjectSource.realmGet$isAcknowledged());
        builder.addBoolean(columnInfo.readStatusColKey, realmObjectSource.realmGet$readStatus());
        builder.addInteger(columnInfo.receivedTimeColKey, realmObjectSource.realmGet$receivedTime());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.models.RealmNotificationInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationInfoColumnInfo columnInfo = (RealmNotificationInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        long pkColumnKey = columnInfo.notificationIDColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notificationID();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$notifyData = ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notifyData();
        if (realmGet$notifyData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.notifyDataColKey, colKey, realmGet$notifyData, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isAcknowledgedColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$isAcknowledged(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.readStatusColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$readStatus(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.receivedTimeColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$receivedTime(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationInfoColumnInfo columnInfo = (RealmNotificationInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        long pkColumnKey = columnInfo.notificationIDColKey;
        com.vam.whitecoats.core.models.RealmNotificationInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.models.RealmNotificationInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notificationID();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$notifyData = ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notifyData();
            if (realmGet$notifyData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.notifyDataColKey, colKey, realmGet$notifyData, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isAcknowledgedColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$isAcknowledged(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.readStatusColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$readStatus(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.receivedTimeColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$receivedTime(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.models.RealmNotificationInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationInfoColumnInfo columnInfo = (RealmNotificationInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        long pkColumnKey = columnInfo.notificationIDColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notificationID();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$notifyData = ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notifyData();
        if (realmGet$notifyData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.notifyDataColKey, colKey, realmGet$notifyData, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.notifyDataColKey, colKey, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isAcknowledgedColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$isAcknowledged(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.readStatusColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$readStatus(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.receivedTimeColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$receivedTime(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationInfoColumnInfo columnInfo = (RealmNotificationInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        long pkColumnKey = columnInfo.notificationIDColKey;
        com.vam.whitecoats.core.models.RealmNotificationInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.models.RealmNotificationInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notificationID();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$notifyData = ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$notifyData();
            if (realmGet$notifyData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.notifyDataColKey, colKey, realmGet$notifyData, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.notifyDataColKey, colKey, false);
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isAcknowledgedColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$isAcknowledged(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.readStatusColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$readStatus(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.receivedTimeColKey, colKey, ((com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) object).realmGet$receivedTime(), false);
        }
    }

    public static com.vam.whitecoats.core.models.RealmNotificationInfo createDetachedCopy(com.vam.whitecoats.core.models.RealmNotificationInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.models.RealmNotificationInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.models.RealmNotificationInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.models.RealmNotificationInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.models.RealmNotificationInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$notificationID(realmSource.realmGet$notificationID());
        unmanagedCopy.realmSet$notifyData(realmSource.realmGet$notifyData());
        unmanagedCopy.realmSet$isAcknowledged(realmSource.realmGet$isAcknowledged());
        unmanagedCopy.realmSet$readStatus(realmSource.realmGet$readStatus());
        unmanagedCopy.realmSet$receivedTime(realmSource.realmGet$receivedTime());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.models.RealmNotificationInfo update(Realm realm, RealmNotificationInfoColumnInfo columnInfo, com.vam.whitecoats.core.models.RealmNotificationInfo realmObject, com.vam.whitecoats.core.models.RealmNotificationInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.notificationIDColKey, realmObjectSource.realmGet$notificationID());
        builder.addString(columnInfo.notifyDataColKey, realmObjectSource.realmGet$notifyData());
        builder.addBoolean(columnInfo.isAcknowledgedColKey, realmObjectSource.realmGet$isAcknowledged());
        builder.addBoolean(columnInfo.readStatusColKey, realmObjectSource.realmGet$readStatus());
        builder.addInteger(columnInfo.receivedTimeColKey, realmObjectSource.realmGet$receivedTime());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmNotificationInfo = proxy[");
        stringBuilder.append("{notificationID:");
        stringBuilder.append(realmGet$notificationID());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{notifyData:");
        stringBuilder.append(realmGet$notifyData() != null ? realmGet$notifyData() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isAcknowledged:");
        stringBuilder.append(realmGet$isAcknowledged());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{readStatus:");
        stringBuilder.append(realmGet$readStatus());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{receivedTime:");
        stringBuilder.append(realmGet$receivedTime());
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
        com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy aRealmNotificationInfo = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmNotificationInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmNotificationInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmNotificationInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
