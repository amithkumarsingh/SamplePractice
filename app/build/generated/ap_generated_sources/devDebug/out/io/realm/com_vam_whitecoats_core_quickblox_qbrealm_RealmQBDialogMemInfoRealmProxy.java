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
public class com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy extends com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface {

    static final class RealmQBDialogMemInfoColumnInfo extends ColumnInfo {
        long dialog_idColKey;
        long doc_idColKey;
        long invite_responseColKey;
        long is_adminColKey;

        RealmQBDialogMemInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmQBDialogMemInfo");
            this.dialog_idColKey = addColumnDetails("dialog_id", "dialog_id", objectSchemaInfo);
            this.doc_idColKey = addColumnDetails("doc_id", "doc_id", objectSchemaInfo);
            this.invite_responseColKey = addColumnDetails("invite_response", "invite_response", objectSchemaInfo);
            this.is_adminColKey = addColumnDetails("is_admin", "is_admin", objectSchemaInfo);
        }

        RealmQBDialogMemInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmQBDialogMemInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmQBDialogMemInfoColumnInfo src = (RealmQBDialogMemInfoColumnInfo) rawSrc;
            final RealmQBDialogMemInfoColumnInfo dst = (RealmQBDialogMemInfoColumnInfo) rawDst;
            dst.dialog_idColKey = src.dialog_idColKey;
            dst.doc_idColKey = src.doc_idColKey;
            dst.invite_responseColKey = src.invite_responseColKey;
            dst.is_adminColKey = src.is_adminColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmQBDialogMemInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo> proxyState;

    com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmQBDialogMemInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialog_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialog_idColKey);
    }

    @Override
    public void realmSet$dialog_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'dialog_id' to null.");
            }
            row.getTable().setString(columnInfo.dialog_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'dialog_id' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$doc_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.doc_idColKey);
    }

    @Override
    public void realmSet$doc_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.doc_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.doc_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$invite_response() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.invite_responseColKey);
    }

    @Override
    public void realmSet$invite_response(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.invite_responseColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.invite_responseColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$is_admin() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.is_adminColKey);
    }

    @Override
    public void realmSet$is_admin(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.is_adminColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.is_adminColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmQBDialogMemInfo", 4, 0);
        builder.addPersistedProperty("dialog_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("invite_response", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("is_admin", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmQBDialogMemInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmQBDialogMemInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmQBDialogMemInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmQBDialogMemInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) obj;
        if (json.has("dialog_id")) {
            if (json.isNull("dialog_id")) {
                objProxy.realmSet$dialog_id(null);
            } else {
                objProxy.realmSet$dialog_id((String) json.getString("dialog_id"));
            }
        }
        if (json.has("doc_id")) {
            if (json.isNull("doc_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'doc_id' to null.");
            } else {
                objProxy.realmSet$doc_id((int) json.getInt("doc_id"));
            }
        }
        if (json.has("invite_response")) {
            if (json.isNull("invite_response")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'invite_response' to null.");
            } else {
                objProxy.realmSet$invite_response((int) json.getInt("invite_response"));
            }
        }
        if (json.has("is_admin")) {
            if (json.isNull("is_admin")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'is_admin' to null.");
            } else {
                objProxy.realmSet$is_admin((boolean) json.getBoolean("is_admin"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo obj = new com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo();
        final com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("dialog_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_id(null);
                }
            } else if (name.equals("doc_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$doc_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'doc_id' to null.");
                }
            } else if (name.equals("invite_response")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$invite_response((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'invite_response' to null.");
                }
            } else if (name.equals("is_admin")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$is_admin((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'is_admin' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo copyOrUpdate(Realm realm, RealmQBDialogMemInfoColumnInfo columnInfo, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo copy(Realm realm, RealmQBDialogMemInfoColumnInfo columnInfo, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.dialog_idColKey, realmObjectSource.realmGet$dialog_id());
        builder.addInteger(columnInfo.doc_idColKey, realmObjectSource.realmGet$doc_id());
        builder.addInteger(columnInfo.invite_responseColKey, realmObjectSource.realmGet$invite_response());
        builder.addBoolean(columnInfo.is_adminColKey, realmObjectSource.realmGet$is_admin());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBDialogMemInfoColumnInfo columnInfo = (RealmQBDialogMemInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$dialog_id();
        if (realmGet$dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$doc_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.invite_responseColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$invite_response(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.is_adminColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$is_admin(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBDialogMemInfoColumnInfo columnInfo = (RealmQBDialogMemInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$dialog_id();
            if (realmGet$dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$doc_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.invite_responseColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$invite_response(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.is_adminColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$is_admin(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBDialogMemInfoColumnInfo columnInfo = (RealmQBDialogMemInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$dialog_id();
        if (realmGet$dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_idColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$doc_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.invite_responseColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$invite_response(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.is_adminColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$is_admin(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBDialogMemInfoColumnInfo columnInfo = (RealmQBDialogMemInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$dialog_id();
            if (realmGet$dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_idColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$doc_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.invite_responseColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$invite_response(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.is_adminColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) object).realmGet$is_admin(), false);
        }
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo createDetachedCopy(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$dialog_id(realmSource.realmGet$dialog_id());
        unmanagedCopy.realmSet$doc_id(realmSource.realmGet$doc_id());
        unmanagedCopy.realmSet$invite_response(realmSource.realmGet$invite_response());
        unmanagedCopy.realmSet$is_admin(realmSource.realmGet$is_admin());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmQBDialogMemInfo = proxy[");
        stringBuilder.append("{dialog_id:");
        stringBuilder.append(realmGet$dialog_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{doc_id:");
        stringBuilder.append(realmGet$doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{invite_response:");
        stringBuilder.append(realmGet$invite_response());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{is_admin:");
        stringBuilder.append(realmGet$is_admin());
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
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy aRealmQBDialogMemInfo = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmQBDialogMemInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmQBDialogMemInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmQBDialogMemInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
