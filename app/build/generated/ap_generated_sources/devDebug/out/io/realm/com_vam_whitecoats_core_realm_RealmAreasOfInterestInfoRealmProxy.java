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
public class com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface {

    static final class RealmAreasOfInterestInfoColumnInfo extends ColumnInfo {
        long interestIdColKey;
        long interestNameColKey;

        RealmAreasOfInterestInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmAreasOfInterestInfo");
            this.interestIdColKey = addColumnDetails("interestId", "interestId", objectSchemaInfo);
            this.interestNameColKey = addColumnDetails("interestName", "interestName", objectSchemaInfo);
        }

        RealmAreasOfInterestInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmAreasOfInterestInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmAreasOfInterestInfoColumnInfo src = (RealmAreasOfInterestInfoColumnInfo) rawSrc;
            final RealmAreasOfInterestInfoColumnInfo dst = (RealmAreasOfInterestInfoColumnInfo) rawDst;
            dst.interestIdColKey = src.interestIdColKey;
            dst.interestNameColKey = src.interestNameColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmAreasOfInterestInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmAreasOfInterestInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$interestId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.interestIdColKey);
    }

    @Override
    public void realmSet$interestId(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'interestId' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$interestName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.interestNameColKey);
    }

    @Override
    public void realmSet$interestName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.interestNameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.interestNameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.interestNameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.interestNameColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmAreasOfInterestInfo", 2, 0);
        builder.addPersistedProperty("interestId", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("interestName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmAreasOfInterestInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmAreasOfInterestInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmAreasOfInterestInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmAreasOfInterestInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
            RealmAreasOfInterestInfoColumnInfo columnInfo = (RealmAreasOfInterestInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
            long pkColumnKey = columnInfo.interestIdColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("interestId")) {
                colKey = table.findFirstLong(pkColumnKey, json.getLong("interestId"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("interestId")) {
                if (json.isNull("interestId")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class, json.getInt("interestId"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'interestId'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) obj;
        if (json.has("interestName")) {
            if (json.isNull("interestName")) {
                objProxy.realmSet$interestName(null);
            } else {
                objProxy.realmSet$interestName((String) json.getString("interestName"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo obj = new com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo();
        final com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("interestId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$interestId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'interestId' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("interestName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$interestName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$interestName(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'interestId'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo copyOrUpdate(Realm realm, RealmAreasOfInterestInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
            long pkColumnKey = columnInfo.interestIdColKey;
            long colKey = table.findFirstLong(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo copy(Realm realm, RealmAreasOfInterestInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.interestIdColKey, realmObjectSource.realmGet$interestId());
        builder.addString(columnInfo.interestNameColKey, realmObjectSource.realmGet$interestName());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAreasOfInterestInfoColumnInfo columnInfo = (RealmAreasOfInterestInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        long pkColumnKey = columnInfo.interestIdColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$interestName = ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestName();
        if (realmGet$interestName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.interestNameColKey, colKey, realmGet$interestName, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAreasOfInterestInfoColumnInfo columnInfo = (RealmAreasOfInterestInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        long pkColumnKey = columnInfo.interestIdColKey;
        com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$interestName = ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestName();
            if (realmGet$interestName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.interestNameColKey, colKey, realmGet$interestName, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAreasOfInterestInfoColumnInfo columnInfo = (RealmAreasOfInterestInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        long pkColumnKey = columnInfo.interestIdColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
        }
        cache.put(object, colKey);
        String realmGet$interestName = ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestName();
        if (realmGet$interestName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.interestNameColKey, colKey, realmGet$interestName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.interestNameColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAreasOfInterestInfoColumnInfo columnInfo = (RealmAreasOfInterestInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        long pkColumnKey = columnInfo.interestIdColKey;
        com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestId());
            }
            cache.put(object, colKey);
            String realmGet$interestName = ((com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) object).realmGet$interestName();
            if (realmGet$interestName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.interestNameColKey, colKey, realmGet$interestName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.interestNameColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$interestId(realmSource.realmGet$interestId());
        unmanagedCopy.realmSet$interestName(realmSource.realmGet$interestName());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo update(Realm realm, RealmAreasOfInterestInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo realmObject, com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addInteger(columnInfo.interestIdColKey, realmObjectSource.realmGet$interestId());
        builder.addString(columnInfo.interestNameColKey, realmObjectSource.realmGet$interestName());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmAreasOfInterestInfo = proxy[");
        stringBuilder.append("{interestId:");
        stringBuilder.append(realmGet$interestId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{interestName:");
        stringBuilder.append(realmGet$interestName() != null ? realmGet$interestName() : "null");
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
        com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy aRealmAreasOfInterestInfo = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmAreasOfInterestInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmAreasOfInterestInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmAreasOfInterestInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
