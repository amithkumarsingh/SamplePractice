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
public class com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface {

    static final class RealmShareFailedFeedInfoColumnInfo extends ColumnInfo {
        long idColKey;
        long feedDataColKey;

        RealmShareFailedFeedInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmShareFailedFeedInfo");
            this.idColKey = addColumnDetails("id", "id", objectSchemaInfo);
            this.feedDataColKey = addColumnDetails("feedData", "feedData", objectSchemaInfo);
        }

        RealmShareFailedFeedInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmShareFailedFeedInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmShareFailedFeedInfoColumnInfo src = (RealmShareFailedFeedInfoColumnInfo) rawSrc;
            final RealmShareFailedFeedInfoColumnInfo dst = (RealmShareFailedFeedInfoColumnInfo) rawDst;
            dst.idColKey = src.idColKey;
            dst.feedDataColKey = src.feedDataColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmShareFailedFeedInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmShareFailedFeedInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.idColKey);
    }

    @Override
    public void realmSet$id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$feedData() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.feedDataColKey);
    }

    @Override
    public void realmSet$feedData(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.feedDataColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.feedDataColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.feedDataColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.feedDataColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmShareFailedFeedInfo", 2, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("feedData", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmShareFailedFeedInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmShareFailedFeedInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmShareFailedFeedInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmShareFailedFeedInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
            RealmShareFailedFeedInfoColumnInfo columnInfo = (RealmShareFailedFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
            long pkColumnKey = columnInfo.idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("id")) {
                colKey = table.findFirstLong(pkColumnKey, json.getLong("id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) obj;
        if (json.has("feedData")) {
            if (json.isNull("feedData")) {
                objProxy.realmSet$feedData(null);
            } else {
                objProxy.realmSet$feedData((String) json.getString("feedData"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo obj = new com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo();
        final com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("feedData")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$feedData((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$feedData(null);
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

    private static com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo copyOrUpdate(Realm realm, RealmShareFailedFeedInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
            long pkColumnKey = columnInfo.idColKey;
            long colKey = table.findFirstLong(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo copy(Realm realm, RealmShareFailedFeedInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.idColKey, realmObjectSource.realmGet$id());
        builder.addString(columnInfo.feedDataColKey, realmObjectSource.realmGet$feedData());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmShareFailedFeedInfoColumnInfo columnInfo = (RealmShareFailedFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        long pkColumnKey = columnInfo.idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$feedData = ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$feedData();
        if (realmGet$feedData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.feedDataColKey, colKey, realmGet$feedData, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmShareFailedFeedInfoColumnInfo columnInfo = (RealmShareFailedFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        long pkColumnKey = columnInfo.idColKey;
        com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$feedData = ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$feedData();
            if (realmGet$feedData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.feedDataColKey, colKey, realmGet$feedData, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmShareFailedFeedInfoColumnInfo columnInfo = (RealmShareFailedFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        long pkColumnKey = columnInfo.idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, colKey);
        String realmGet$feedData = ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$feedData();
        if (realmGet$feedData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.feedDataColKey, colKey, realmGet$feedData, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.feedDataColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmShareFailedFeedInfoColumnInfo columnInfo = (RealmShareFailedFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        long pkColumnKey = columnInfo.idColKey;
        com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, colKey);
            String realmGet$feedData = ((com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) object).realmGet$feedData();
            if (realmGet$feedData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.feedDataColKey, colKey, realmGet$feedData, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.feedDataColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$feedData(realmSource.realmGet$feedData());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo update(Realm realm, RealmShareFailedFeedInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo realmObject, com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addInteger(columnInfo.idColKey, realmObjectSource.realmGet$id());
        builder.addString(columnInfo.feedDataColKey, realmObjectSource.realmGet$feedData());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmShareFailedFeedInfo = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{feedData:");
        stringBuilder.append(realmGet$feedData() != null ? realmGet$feedData() : "null");
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
        com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy aRealmShareFailedFeedInfo = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmShareFailedFeedInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmShareFailedFeedInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmShareFailedFeedInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
