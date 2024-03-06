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
public class com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy extends com.vam.whitecoats.core.realm.RealmPrintPub
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface {

    static final class RealmPrintPubColumnInfo extends ColumnInfo {
        long idColKey;
        long printIdColKey;
        long print_nameColKey;

        RealmPrintPubColumnInfo(OsSchemaInfo schemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmPrintPub");
            this.idColKey = addColumnDetails("id", "id", objectSchemaInfo);
            this.printIdColKey = addColumnDetails("printId", "printId", objectSchemaInfo);
            this.print_nameColKey = addColumnDetails("print_name", "print_name", objectSchemaInfo);
        }

        RealmPrintPubColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmPrintPubColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmPrintPubColumnInfo src = (RealmPrintPubColumnInfo) rawSrc;
            final RealmPrintPubColumnInfo dst = (RealmPrintPubColumnInfo) rawDst;
            dst.idColKey = src.idColKey;
            dst.printIdColKey = src.printIdColKey;
            dst.print_nameColKey = src.print_nameColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmPrintPubColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmPrintPub> proxyState;

    com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmPrintPubColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmPrintPub>(this);
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
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
            }
            row.getTable().setString(columnInfo.idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$printId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.printIdColKey);
    }

    @Override
    public void realmSet$printId(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.printIdColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.printIdColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$print_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.print_nameColKey);
    }

    @Override
    public void realmSet$print_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.print_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.print_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.print_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.print_nameColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmPrintPub", 3, 0);
        builder.addPersistedProperty("id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("printId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("print_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmPrintPubColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmPrintPubColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmPrintPub";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmPrintPub";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmPrintPub createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmPrintPub obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmPrintPub.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) obj;
        if (json.has("id")) {
            if (json.isNull("id")) {
                objProxy.realmSet$id(null);
            } else {
                objProxy.realmSet$id((String) json.getString("id"));
            }
        }
        if (json.has("printId")) {
            if (json.isNull("printId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'printId' to null.");
            } else {
                objProxy.realmSet$printId((int) json.getInt("printId"));
            }
        }
        if (json.has("print_name")) {
            if (json.isNull("print_name")) {
                objProxy.realmSet$print_name(null);
            } else {
                objProxy.realmSet$print_name((String) json.getString("print_name"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmPrintPub createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmPrintPub obj = new com.vam.whitecoats.core.realm.RealmPrintPub();
        final com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) obj;
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
            } else if (name.equals("printId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$printId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'printId' to null.");
                }
            } else if (name.equals("print_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$print_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$print_name(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPrintPub.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmPrintPub copyOrUpdate(Realm realm, RealmPrintPubColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmPrintPub object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmPrintPub) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmPrintPub copy(Realm realm, RealmPrintPubColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmPrintPub newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmPrintPub) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.idColKey, realmObjectSource.realmGet$id());
        builder.addInteger(columnInfo.printIdColKey, realmObjectSource.realmGet$printId());
        builder.addString(columnInfo.print_nameColKey, realmObjectSource.realmGet$print_name());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmPrintPub object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        long tableNativePtr = table.getNativePtr();
        RealmPrintPubColumnInfo columnInfo = (RealmPrintPubColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$id = ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idColKey, colKey, realmGet$id, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.printIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$printId(), false);
        String realmGet$print_name = ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$print_name();
        if (realmGet$print_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.print_nameColKey, colKey, realmGet$print_name, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        long tableNativePtr = table.getNativePtr();
        RealmPrintPubColumnInfo columnInfo = (RealmPrintPubColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        com.vam.whitecoats.core.realm.RealmPrintPub object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmPrintPub) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$id = ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$id();
            if (realmGet$id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.idColKey, colKey, realmGet$id, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.printIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$printId(), false);
            String realmGet$print_name = ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$print_name();
            if (realmGet$print_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.print_nameColKey, colKey, realmGet$print_name, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmPrintPub object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        long tableNativePtr = table.getNativePtr();
        RealmPrintPubColumnInfo columnInfo = (RealmPrintPubColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$id = ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idColKey, colKey, realmGet$id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.idColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.printIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$printId(), false);
        String realmGet$print_name = ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$print_name();
        if (realmGet$print_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.print_nameColKey, colKey, realmGet$print_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.print_nameColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        long tableNativePtr = table.getNativePtr();
        RealmPrintPubColumnInfo columnInfo = (RealmPrintPubColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        com.vam.whitecoats.core.realm.RealmPrintPub object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmPrintPub) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$id = ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$id();
            if (realmGet$id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.idColKey, colKey, realmGet$id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.idColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.printIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$printId(), false);
            String realmGet$print_name = ((com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) object).realmGet$print_name();
            if (realmGet$print_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.print_nameColKey, colKey, realmGet$print_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.print_nameColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmPrintPub createDetachedCopy(com.vam.whitecoats.core.realm.RealmPrintPub realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmPrintPub unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmPrintPub();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmPrintPub) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmPrintPub) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmPrintPubRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$printId(realmSource.realmGet$printId());
        unmanagedCopy.realmSet$print_name(realmSource.realmGet$print_name());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmPrintPub = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{printId:");
        stringBuilder.append(realmGet$printId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{print_name:");
        stringBuilder.append(realmGet$print_name() != null ? realmGet$print_name() : "null");
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
        com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy aRealmPrintPub = (com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmPrintPub.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmPrintPub.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmPrintPub.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
