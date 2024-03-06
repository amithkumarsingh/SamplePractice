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
public class com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface {

    static final class RealmNotificationSettingsInfoColumnInfo extends ColumnInfo {
        long categoryIdColKey;
        long isEnabledColKey;
        long jsonDataColKey;

        RealmNotificationSettingsInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmNotificationSettingsInfo");
            this.categoryIdColKey = addColumnDetails("categoryId", "categoryId", objectSchemaInfo);
            this.isEnabledColKey = addColumnDetails("isEnabled", "isEnabled", objectSchemaInfo);
            this.jsonDataColKey = addColumnDetails("jsonData", "jsonData", objectSchemaInfo);
        }

        RealmNotificationSettingsInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmNotificationSettingsInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmNotificationSettingsInfoColumnInfo src = (RealmNotificationSettingsInfoColumnInfo) rawSrc;
            final RealmNotificationSettingsInfoColumnInfo dst = (RealmNotificationSettingsInfoColumnInfo) rawDst;
            dst.categoryIdColKey = src.categoryIdColKey;
            dst.isEnabledColKey = src.isEnabledColKey;
            dst.jsonDataColKey = src.jsonDataColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmNotificationSettingsInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmNotificationSettingsInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$categoryId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.categoryIdColKey);
    }

    @Override
    public void realmSet$categoryId(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.categoryIdColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.categoryIdColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$isEnabled() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isEnabledColKey);
    }

    @Override
    public void realmSet$isEnabled(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.isEnabledColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.isEnabledColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$jsonData() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.jsonDataColKey);
    }

    @Override
    public void realmSet$jsonData(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.jsonDataColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.jsonDataColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.jsonDataColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.jsonDataColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmNotificationSettingsInfo", 3, 0);
        builder.addPersistedProperty("categoryId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("isEnabled", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("jsonData", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmNotificationSettingsInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmNotificationSettingsInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmNotificationSettingsInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmNotificationSettingsInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) obj;
        if (json.has("categoryId")) {
            if (json.isNull("categoryId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'categoryId' to null.");
            } else {
                objProxy.realmSet$categoryId((int) json.getInt("categoryId"));
            }
        }
        if (json.has("isEnabled")) {
            if (json.isNull("isEnabled")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isEnabled' to null.");
            } else {
                objProxy.realmSet$isEnabled((boolean) json.getBoolean("isEnabled"));
            }
        }
        if (json.has("jsonData")) {
            if (json.isNull("jsonData")) {
                objProxy.realmSet$jsonData(null);
            } else {
                objProxy.realmSet$jsonData((String) json.getString("jsonData"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo obj = new com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo();
        final com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("categoryId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$categoryId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'categoryId' to null.");
                }
            } else if (name.equals("isEnabled")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isEnabled((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isEnabled' to null.");
                }
            } else if (name.equals("jsonData")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$jsonData((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$jsonData(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo copyOrUpdate(Realm realm, RealmNotificationSettingsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo copy(Realm realm, RealmNotificationSettingsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.categoryIdColKey, realmObjectSource.realmGet$categoryId());
        builder.addBoolean(columnInfo.isEnabledColKey, realmObjectSource.realmGet$isEnabled());
        builder.addString(columnInfo.jsonDataColKey, realmObjectSource.realmGet$jsonData());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationSettingsInfoColumnInfo columnInfo = (RealmNotificationSettingsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.categoryIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$categoryId(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isEnabledColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$isEnabled(), false);
        String realmGet$jsonData = ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$jsonData();
        if (realmGet$jsonData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.jsonDataColKey, colKey, realmGet$jsonData, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationSettingsInfoColumnInfo columnInfo = (RealmNotificationSettingsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.categoryIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$categoryId(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isEnabledColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$isEnabled(), false);
            String realmGet$jsonData = ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$jsonData();
            if (realmGet$jsonData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.jsonDataColKey, colKey, realmGet$jsonData, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationSettingsInfoColumnInfo columnInfo = (RealmNotificationSettingsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.categoryIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$categoryId(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isEnabledColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$isEnabled(), false);
        String realmGet$jsonData = ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$jsonData();
        if (realmGet$jsonData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.jsonDataColKey, colKey, realmGet$jsonData, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.jsonDataColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmNotificationSettingsInfoColumnInfo columnInfo = (RealmNotificationSettingsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.categoryIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$categoryId(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isEnabledColKey, colKey, ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$isEnabled(), false);
            String realmGet$jsonData = ((com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) object).realmGet$jsonData();
            if (realmGet$jsonData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.jsonDataColKey, colKey, realmGet$jsonData, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.jsonDataColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$categoryId(realmSource.realmGet$categoryId());
        unmanagedCopy.realmSet$isEnabled(realmSource.realmGet$isEnabled());
        unmanagedCopy.realmSet$jsonData(realmSource.realmGet$jsonData());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmNotificationSettingsInfo = proxy[");
        stringBuilder.append("{categoryId:");
        stringBuilder.append(realmGet$categoryId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isEnabled:");
        stringBuilder.append(realmGet$isEnabled());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{jsonData:");
        stringBuilder.append(realmGet$jsonData() != null ? realmGet$jsonData() : "null");
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
        com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy aRealmNotificationSettingsInfo = (com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmNotificationSettingsInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmNotificationSettingsInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmNotificationSettingsInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
