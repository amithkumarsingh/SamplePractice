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
public class com_vam_whitecoats_core_realm_UserEventsRealmProxy extends com.vam.whitecoats.core.realm.UserEvents
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface {

    static final class UserEventsColumnInfo extends ColumnInfo {
        long eventNameColKey;
        long eventDataColKey;
        long eventTimeColKey;

        UserEventsColumnInfo(OsSchemaInfo schemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("UserEvents");
            this.eventNameColKey = addColumnDetails("eventName", "eventName", objectSchemaInfo);
            this.eventDataColKey = addColumnDetails("eventData", "eventData", objectSchemaInfo);
            this.eventTimeColKey = addColumnDetails("eventTime", "eventTime", objectSchemaInfo);
        }

        UserEventsColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new UserEventsColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final UserEventsColumnInfo src = (UserEventsColumnInfo) rawSrc;
            final UserEventsColumnInfo dst = (UserEventsColumnInfo) rawDst;
            dst.eventNameColKey = src.eventNameColKey;
            dst.eventDataColKey = src.eventDataColKey;
            dst.eventTimeColKey = src.eventTimeColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private UserEventsColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.UserEvents> proxyState;

    com_vam_whitecoats_core_realm_UserEventsRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UserEventsColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.UserEvents>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$eventName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.eventNameColKey);
    }

    @Override
    public void realmSet$eventName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.eventNameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.eventNameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.eventNameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.eventNameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$eventData() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.eventDataColKey);
    }

    @Override
    public void realmSet$eventData(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.eventDataColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.eventDataColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.eventDataColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.eventDataColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$eventTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.eventTimeColKey);
    }

    @Override
    public void realmSet$eventTime(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.eventTimeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.eventTimeColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("UserEvents", 3, 0);
        builder.addPersistedProperty("eventName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("eventData", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("eventTime", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static UserEventsColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new UserEventsColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "UserEvents";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "UserEvents";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.UserEvents createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.UserEvents obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.UserEvents.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) obj;
        if (json.has("eventName")) {
            if (json.isNull("eventName")) {
                objProxy.realmSet$eventName(null);
            } else {
                objProxy.realmSet$eventName((String) json.getString("eventName"));
            }
        }
        if (json.has("eventData")) {
            if (json.isNull("eventData")) {
                objProxy.realmSet$eventData(null);
            } else {
                objProxy.realmSet$eventData((String) json.getString("eventData"));
            }
        }
        if (json.has("eventTime")) {
            if (json.isNull("eventTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'eventTime' to null.");
            } else {
                objProxy.realmSet$eventTime((long) json.getLong("eventTime"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.UserEvents createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.UserEvents obj = new com.vam.whitecoats.core.realm.UserEvents();
        final com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("eventName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$eventName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$eventName(null);
                }
            } else if (name.equals("eventData")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$eventData((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$eventData(null);
                }
            } else if (name.equals("eventTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$eventTime((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'eventTime' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_UserEventsRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.UserEvents.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.UserEvents copyOrUpdate(Realm realm, UserEventsColumnInfo columnInfo, com.vam.whitecoats.core.realm.UserEvents object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.UserEvents) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.UserEvents copy(Realm realm, UserEventsColumnInfo columnInfo, com.vam.whitecoats.core.realm.UserEvents newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.UserEvents) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.UserEvents.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.eventNameColKey, realmObjectSource.realmGet$eventName());
        builder.addString(columnInfo.eventDataColKey, realmObjectSource.realmGet$eventData());
        builder.addInteger(columnInfo.eventTimeColKey, realmObjectSource.realmGet$eventTime());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.UserEvents object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.UserEvents.class);
        long tableNativePtr = table.getNativePtr();
        UserEventsColumnInfo columnInfo = (UserEventsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.UserEvents.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$eventName = ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventName();
        if (realmGet$eventName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.eventNameColKey, colKey, realmGet$eventName, false);
        }
        String realmGet$eventData = ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventData();
        if (realmGet$eventData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.eventDataColKey, colKey, realmGet$eventData, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.eventTimeColKey, colKey, ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventTime(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.UserEvents.class);
        long tableNativePtr = table.getNativePtr();
        UserEventsColumnInfo columnInfo = (UserEventsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.UserEvents.class);
        com.vam.whitecoats.core.realm.UserEvents object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.UserEvents) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$eventName = ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventName();
            if (realmGet$eventName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.eventNameColKey, colKey, realmGet$eventName, false);
            }
            String realmGet$eventData = ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventData();
            if (realmGet$eventData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.eventDataColKey, colKey, realmGet$eventData, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.eventTimeColKey, colKey, ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventTime(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.UserEvents object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.UserEvents.class);
        long tableNativePtr = table.getNativePtr();
        UserEventsColumnInfo columnInfo = (UserEventsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.UserEvents.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$eventName = ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventName();
        if (realmGet$eventName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.eventNameColKey, colKey, realmGet$eventName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.eventNameColKey, colKey, false);
        }
        String realmGet$eventData = ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventData();
        if (realmGet$eventData != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.eventDataColKey, colKey, realmGet$eventData, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.eventDataColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.eventTimeColKey, colKey, ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventTime(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.UserEvents.class);
        long tableNativePtr = table.getNativePtr();
        UserEventsColumnInfo columnInfo = (UserEventsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.UserEvents.class);
        com.vam.whitecoats.core.realm.UserEvents object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.UserEvents) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$eventName = ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventName();
            if (realmGet$eventName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.eventNameColKey, colKey, realmGet$eventName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.eventNameColKey, colKey, false);
            }
            String realmGet$eventData = ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventData();
            if (realmGet$eventData != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.eventDataColKey, colKey, realmGet$eventData, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.eventDataColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.eventTimeColKey, colKey, ((com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) object).realmGet$eventTime(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.UserEvents createDetachedCopy(com.vam.whitecoats.core.realm.UserEvents realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.UserEvents unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.UserEvents();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.UserEvents) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.UserEvents) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_UserEventsRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$eventName(realmSource.realmGet$eventName());
        unmanagedCopy.realmSet$eventData(realmSource.realmGet$eventData());
        unmanagedCopy.realmSet$eventTime(realmSource.realmGet$eventTime());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("UserEvents = proxy[");
        stringBuilder.append("{eventName:");
        stringBuilder.append(realmGet$eventName() != null ? realmGet$eventName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{eventData:");
        stringBuilder.append(realmGet$eventData() != null ? realmGet$eventData() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{eventTime:");
        stringBuilder.append(realmGet$eventTime());
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
        com_vam_whitecoats_core_realm_UserEventsRealmProxy aUserEvents = (com_vam_whitecoats_core_realm_UserEventsRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aUserEvents.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUserEvents.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aUserEvents.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
