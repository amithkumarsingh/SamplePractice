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
public class com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmChannelsInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface {

    static final class RealmChannelsInfoColumnInfo extends ColumnInfo {
        long channelsListKeyColKey;
        long channelsListColKey;

        RealmChannelsInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmChannelsInfo");
            this.channelsListKeyColKey = addColumnDetails("channelsListKey", "channelsListKey", objectSchemaInfo);
            this.channelsListColKey = addColumnDetails("channelsList", "channelsList", objectSchemaInfo);
        }

        RealmChannelsInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmChannelsInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmChannelsInfoColumnInfo src = (RealmChannelsInfoColumnInfo) rawSrc;
            final RealmChannelsInfoColumnInfo dst = (RealmChannelsInfoColumnInfo) rawDst;
            dst.channelsListKeyColKey = src.channelsListKeyColKey;
            dst.channelsListColKey = src.channelsListColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmChannelsInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmChannelsInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmChannelsInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmChannelsInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$channelsListKey() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.channelsListKeyColKey);
    }

    @Override
    public void realmSet$channelsListKey(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.channelsListKeyColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.channelsListKeyColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.channelsListKeyColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.channelsListKeyColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$channelsList() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.channelsListColKey);
    }

    @Override
    public void realmSet$channelsList(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.channelsListColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.channelsListColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.channelsListColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.channelsListColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmChannelsInfo", 2, 0);
        builder.addPersistedProperty("channelsListKey", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("channelsList", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmChannelsInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmChannelsInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmChannelsInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmChannelsInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmChannelsInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmChannelsInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmChannelsInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) obj;
        if (json.has("channelsListKey")) {
            if (json.isNull("channelsListKey")) {
                objProxy.realmSet$channelsListKey(null);
            } else {
                objProxy.realmSet$channelsListKey((String) json.getString("channelsListKey"));
            }
        }
        if (json.has("channelsList")) {
            if (json.isNull("channelsList")) {
                objProxy.realmSet$channelsList(null);
            } else {
                objProxy.realmSet$channelsList((String) json.getString("channelsList"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmChannelsInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmChannelsInfo obj = new com.vam.whitecoats.core.realm.RealmChannelsInfo();
        final com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("channelsListKey")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$channelsListKey((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$channelsListKey(null);
                }
            } else if (name.equals("channelsList")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$channelsList((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$channelsList(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelsInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmChannelsInfo copyOrUpdate(Realm realm, RealmChannelsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmChannelsInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmChannelsInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmChannelsInfo copy(Realm realm, RealmChannelsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmChannelsInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmChannelsInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.channelsListKeyColKey, realmObjectSource.realmGet$channelsListKey());
        builder.addString(columnInfo.channelsListColKey, realmObjectSource.realmGet$channelsList());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmChannelsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmChannelsInfoColumnInfo columnInfo = (RealmChannelsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$channelsListKey = ((com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) object).realmGet$channelsListKey();
        if (realmGet$channelsListKey != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.channelsListKeyColKey, colKey, realmGet$channelsListKey, false);
        }
        String realmGet$channelsList = ((com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) object).realmGet$channelsList();
        if (realmGet$channelsList != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.channelsListColKey, colKey, realmGet$channelsList, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmChannelsInfoColumnInfo columnInfo = (RealmChannelsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        com.vam.whitecoats.core.realm.RealmChannelsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmChannelsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$channelsListKey = ((com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) object).realmGet$channelsListKey();
            if (realmGet$channelsListKey != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.channelsListKeyColKey, colKey, realmGet$channelsListKey, false);
            }
            String realmGet$channelsList = ((com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) object).realmGet$channelsList();
            if (realmGet$channelsList != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.channelsListColKey, colKey, realmGet$channelsList, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmChannelsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmChannelsInfoColumnInfo columnInfo = (RealmChannelsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$channelsListKey = ((com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) object).realmGet$channelsListKey();
        if (realmGet$channelsListKey != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.channelsListKeyColKey, colKey, realmGet$channelsListKey, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.channelsListKeyColKey, colKey, false);
        }
        String realmGet$channelsList = ((com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) object).realmGet$channelsList();
        if (realmGet$channelsList != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.channelsListColKey, colKey, realmGet$channelsList, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.channelsListColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmChannelsInfoColumnInfo columnInfo = (RealmChannelsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        com.vam.whitecoats.core.realm.RealmChannelsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmChannelsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$channelsListKey = ((com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) object).realmGet$channelsListKey();
            if (realmGet$channelsListKey != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.channelsListKeyColKey, colKey, realmGet$channelsListKey, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.channelsListKeyColKey, colKey, false);
            }
            String realmGet$channelsList = ((com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) object).realmGet$channelsList();
            if (realmGet$channelsList != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.channelsListColKey, colKey, realmGet$channelsList, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.channelsListColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmChannelsInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmChannelsInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmChannelsInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmChannelsInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmChannelsInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmChannelsInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$channelsListKey(realmSource.realmGet$channelsListKey());
        unmanagedCopy.realmSet$channelsList(realmSource.realmGet$channelsList());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmChannelsInfo = proxy[");
        stringBuilder.append("{channelsListKey:");
        stringBuilder.append(realmGet$channelsListKey() != null ? realmGet$channelsListKey() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{channelsList:");
        stringBuilder.append(realmGet$channelsList() != null ? realmGet$channelsList() : "null");
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
        com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy aRealmChannelsInfo = (com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmChannelsInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmChannelsInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmChannelsInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
