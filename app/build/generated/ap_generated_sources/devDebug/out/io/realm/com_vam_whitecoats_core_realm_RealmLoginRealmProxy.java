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
public class com_vam_whitecoats_core_realm_RealmLoginRealmProxy extends com.vam.whitecoats.core.realm.RealmLogin
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface {

    static final class RealmLoginColumnInfo extends ColumnInfo {
        long qb_user_idColKey;
        long qb_user_loginColKey;
        long qb_user_passwordColKey;

        RealmLoginColumnInfo(OsSchemaInfo schemaInfo) {
            super(3);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmLogin");
            this.qb_user_idColKey = addColumnDetails("qb_user_id", "qb_user_id", objectSchemaInfo);
            this.qb_user_loginColKey = addColumnDetails("qb_user_login", "qb_user_login", objectSchemaInfo);
            this.qb_user_passwordColKey = addColumnDetails("qb_user_password", "qb_user_password", objectSchemaInfo);
        }

        RealmLoginColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmLoginColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmLoginColumnInfo src = (RealmLoginColumnInfo) rawSrc;
            final RealmLoginColumnInfo dst = (RealmLoginColumnInfo) rawDst;
            dst.qb_user_idColKey = src.qb_user_idColKey;
            dst.qb_user_loginColKey = src.qb_user_loginColKey;
            dst.qb_user_passwordColKey = src.qb_user_passwordColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmLoginColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmLogin> proxyState;

    com_vam_whitecoats_core_realm_RealmLoginRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmLoginColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmLogin>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$qb_user_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.qb_user_idColKey);
    }

    @Override
    public void realmSet$qb_user_id(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'qb_user_id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$qb_user_login() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.qb_user_loginColKey);
    }

    @Override
    public void realmSet$qb_user_login(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.qb_user_loginColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.qb_user_loginColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.qb_user_loginColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.qb_user_loginColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$qb_user_password() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.qb_user_passwordColKey);
    }

    @Override
    public void realmSet$qb_user_password(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.qb_user_passwordColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.qb_user_passwordColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.qb_user_passwordColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.qb_user_passwordColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmLogin", 3, 0);
        builder.addPersistedProperty("qb_user_id", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("qb_user_login", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("qb_user_password", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmLoginColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmLoginColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmLogin";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmLogin";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmLogin createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmLogin obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmLogin.class);
            RealmLoginColumnInfo columnInfo = (RealmLoginColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmLogin.class);
            long pkColumnKey = columnInfo.qb_user_idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("qb_user_id")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("qb_user_id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmLogin.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("qb_user_id")) {
                if (json.isNull("qb_user_id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmLogin.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmLogin.class, json.getString("qb_user_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'qb_user_id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) obj;
        if (json.has("qb_user_login")) {
            if (json.isNull("qb_user_login")) {
                objProxy.realmSet$qb_user_login(null);
            } else {
                objProxy.realmSet$qb_user_login((String) json.getString("qb_user_login"));
            }
        }
        if (json.has("qb_user_password")) {
            if (json.isNull("qb_user_password")) {
                objProxy.realmSet$qb_user_password(null);
            } else {
                objProxy.realmSet$qb_user_password((String) json.getString("qb_user_password"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmLogin createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmLogin obj = new com.vam.whitecoats.core.realm.RealmLogin();
        final com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("qb_user_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$qb_user_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$qb_user_id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("qb_user_login")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$qb_user_login((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$qb_user_login(null);
                }
            } else if (name.equals("qb_user_password")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$qb_user_password((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$qb_user_password(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'qb_user_id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmLoginRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmLogin.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmLogin copyOrUpdate(Realm realm, RealmLoginColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmLogin object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmLogin) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmLogin realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmLogin.class);
            long pkColumnKey = columnInfo.qb_user_idColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmLogin copy(Realm realm, RealmLoginColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmLogin newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmLogin) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmLogin.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.qb_user_idColKey, realmObjectSource.realmGet$qb_user_id());
        builder.addString(columnInfo.qb_user_loginColKey, realmObjectSource.realmGet$qb_user_login());
        builder.addString(columnInfo.qb_user_passwordColKey, realmObjectSource.realmGet$qb_user_password());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmLogin object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmLogin.class);
        long tableNativePtr = table.getNativePtr();
        RealmLoginColumnInfo columnInfo = (RealmLoginColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmLogin.class);
        long pkColumnKey = columnInfo.qb_user_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$qb_user_login = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_login();
        if (realmGet$qb_user_login != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_user_loginColKey, colKey, realmGet$qb_user_login, false);
        }
        String realmGet$qb_user_password = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_password();
        if (realmGet$qb_user_password != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_user_passwordColKey, colKey, realmGet$qb_user_password, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmLogin.class);
        long tableNativePtr = table.getNativePtr();
        RealmLoginColumnInfo columnInfo = (RealmLoginColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmLogin.class);
        long pkColumnKey = columnInfo.qb_user_idColKey;
        com.vam.whitecoats.core.realm.RealmLogin object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmLogin) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$qb_user_login = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_login();
            if (realmGet$qb_user_login != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_user_loginColKey, colKey, realmGet$qb_user_login, false);
            }
            String realmGet$qb_user_password = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_password();
            if (realmGet$qb_user_password != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_user_passwordColKey, colKey, realmGet$qb_user_password, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmLogin object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmLogin.class);
        long tableNativePtr = table.getNativePtr();
        RealmLoginColumnInfo columnInfo = (RealmLoginColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmLogin.class);
        long pkColumnKey = columnInfo.qb_user_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$qb_user_login = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_login();
        if (realmGet$qb_user_login != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_user_loginColKey, colKey, realmGet$qb_user_login, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.qb_user_loginColKey, colKey, false);
        }
        String realmGet$qb_user_password = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_password();
        if (realmGet$qb_user_password != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.qb_user_passwordColKey, colKey, realmGet$qb_user_password, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.qb_user_passwordColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmLogin.class);
        long tableNativePtr = table.getNativePtr();
        RealmLoginColumnInfo columnInfo = (RealmLoginColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmLogin.class);
        long pkColumnKey = columnInfo.qb_user_idColKey;
        com.vam.whitecoats.core.realm.RealmLogin object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmLogin) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$qb_user_login = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_login();
            if (realmGet$qb_user_login != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_user_loginColKey, colKey, realmGet$qb_user_login, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.qb_user_loginColKey, colKey, false);
            }
            String realmGet$qb_user_password = ((com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) object).realmGet$qb_user_password();
            if (realmGet$qb_user_password != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.qb_user_passwordColKey, colKey, realmGet$qb_user_password, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.qb_user_passwordColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmLogin createDetachedCopy(com.vam.whitecoats.core.realm.RealmLogin realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmLogin unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmLogin();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmLogin) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmLogin) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$qb_user_id(realmSource.realmGet$qb_user_id());
        unmanagedCopy.realmSet$qb_user_login(realmSource.realmGet$qb_user_login());
        unmanagedCopy.realmSet$qb_user_password(realmSource.realmGet$qb_user_password());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmLogin update(Realm realm, RealmLoginColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmLogin realmObject, com.vam.whitecoats.core.realm.RealmLogin newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmLoginRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmLogin.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.qb_user_idColKey, realmObjectSource.realmGet$qb_user_id());
        builder.addString(columnInfo.qb_user_loginColKey, realmObjectSource.realmGet$qb_user_login());
        builder.addString(columnInfo.qb_user_passwordColKey, realmObjectSource.realmGet$qb_user_password());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmLogin = proxy[");
        stringBuilder.append("{qb_user_id:");
        stringBuilder.append(realmGet$qb_user_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{qb_user_login:");
        stringBuilder.append(realmGet$qb_user_login() != null ? realmGet$qb_user_login() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{qb_user_password:");
        stringBuilder.append(realmGet$qb_user_password() != null ? realmGet$qb_user_password() : "null");
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
        com_vam_whitecoats_core_realm_RealmLoginRealmProxy aRealmLogin = (com_vam_whitecoats_core_realm_RealmLoginRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmLogin.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmLogin.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmLogin.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
