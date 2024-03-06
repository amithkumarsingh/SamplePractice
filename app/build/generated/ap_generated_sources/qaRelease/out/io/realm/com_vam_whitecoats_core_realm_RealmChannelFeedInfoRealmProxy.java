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
public class com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmChannelFeedInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface {

    static final class RealmChannelFeedInfoColumnInfo extends ColumnInfo {
        long feedChannelIdColKey;
        long feedIdColKey;
        long createdOrUpdatedTimeColKey;
        long feedsJsonColKey;
        long channelIdColKey;
        long docIdColKey;

        RealmChannelFeedInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(6);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmChannelFeedInfo");
            this.feedChannelIdColKey = addColumnDetails("feedChannelId", "feedChannelId", objectSchemaInfo);
            this.feedIdColKey = addColumnDetails("feedId", "feedId", objectSchemaInfo);
            this.createdOrUpdatedTimeColKey = addColumnDetails("createdOrUpdatedTime", "createdOrUpdatedTime", objectSchemaInfo);
            this.feedsJsonColKey = addColumnDetails("feedsJson", "feedsJson", objectSchemaInfo);
            this.channelIdColKey = addColumnDetails("channelId", "channelId", objectSchemaInfo);
            this.docIdColKey = addColumnDetails("docId", "docId", objectSchemaInfo);
        }

        RealmChannelFeedInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmChannelFeedInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmChannelFeedInfoColumnInfo src = (RealmChannelFeedInfoColumnInfo) rawSrc;
            final RealmChannelFeedInfoColumnInfo dst = (RealmChannelFeedInfoColumnInfo) rawDst;
            dst.feedChannelIdColKey = src.feedChannelIdColKey;
            dst.feedIdColKey = src.feedIdColKey;
            dst.createdOrUpdatedTimeColKey = src.createdOrUpdatedTimeColKey;
            dst.feedsJsonColKey = src.feedsJsonColKey;
            dst.channelIdColKey = src.channelIdColKey;
            dst.docIdColKey = src.docIdColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmChannelFeedInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmChannelFeedInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmChannelFeedInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmChannelFeedInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$feedChannelId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.feedChannelIdColKey);
    }

    @Override
    public void realmSet$feedChannelId(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'feedChannelId' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$feedId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.feedIdColKey);
    }

    @Override
    public void realmSet$feedId(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.feedIdColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.feedIdColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$createdOrUpdatedTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.createdOrUpdatedTimeColKey);
    }

    @Override
    public void realmSet$createdOrUpdatedTime(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.createdOrUpdatedTimeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.createdOrUpdatedTimeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$feedsJson() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.feedsJsonColKey);
    }

    @Override
    public void realmSet$feedsJson(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.feedsJsonColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.feedsJsonColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.feedsJsonColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.feedsJsonColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$channelId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.channelIdColKey);
    }

    @Override
    public void realmSet$channelId(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.channelIdColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.channelIdColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$docId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.docIdColKey);
    }

    @Override
    public void realmSet$docId(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.docIdColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.docIdColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmChannelFeedInfo", 6, 0);
        builder.addPersistedProperty("feedChannelId", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("feedId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("createdOrUpdatedTime", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("feedsJson", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("channelId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("docId", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmChannelFeedInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmChannelFeedInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmChannelFeedInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmChannelFeedInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmChannelFeedInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmChannelFeedInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
            RealmChannelFeedInfoColumnInfo columnInfo = (RealmChannelFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
            long pkColumnKey = columnInfo.feedChannelIdColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("feedChannelId")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("feedChannelId"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("feedChannelId")) {
                if (json.isNull("feedChannelId")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class, json.getString("feedChannelId"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'feedChannelId'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) obj;
        if (json.has("feedId")) {
            if (json.isNull("feedId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'feedId' to null.");
            } else {
                objProxy.realmSet$feedId((int) json.getInt("feedId"));
            }
        }
        if (json.has("createdOrUpdatedTime")) {
            if (json.isNull("createdOrUpdatedTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'createdOrUpdatedTime' to null.");
            } else {
                objProxy.realmSet$createdOrUpdatedTime((long) json.getLong("createdOrUpdatedTime"));
            }
        }
        if (json.has("feedsJson")) {
            if (json.isNull("feedsJson")) {
                objProxy.realmSet$feedsJson(null);
            } else {
                objProxy.realmSet$feedsJson((String) json.getString("feedsJson"));
            }
        }
        if (json.has("channelId")) {
            if (json.isNull("channelId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'channelId' to null.");
            } else {
                objProxy.realmSet$channelId((int) json.getInt("channelId"));
            }
        }
        if (json.has("docId")) {
            if (json.isNull("docId")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'docId' to null.");
            } else {
                objProxy.realmSet$docId((int) json.getInt("docId"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmChannelFeedInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmChannelFeedInfo obj = new com.vam.whitecoats.core.realm.RealmChannelFeedInfo();
        final com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("feedChannelId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$feedChannelId((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$feedChannelId(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("feedId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$feedId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'feedId' to null.");
                }
            } else if (name.equals("createdOrUpdatedTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$createdOrUpdatedTime((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'createdOrUpdatedTime' to null.");
                }
            } else if (name.equals("feedsJson")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$feedsJson((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$feedsJson(null);
                }
            } else if (name.equals("channelId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$channelId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'channelId' to null.");
                }
            } else if (name.equals("docId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$docId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'docId' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'feedChannelId'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmChannelFeedInfo copyOrUpdate(Realm realm, RealmChannelFeedInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmChannelFeedInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmChannelFeedInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
            long pkColumnKey = columnInfo.feedChannelIdColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedChannelId());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmChannelFeedInfo copy(Realm realm, RealmChannelFeedInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmChannelFeedInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.feedChannelIdColKey, realmObjectSource.realmGet$feedChannelId());
        builder.addInteger(columnInfo.feedIdColKey, realmObjectSource.realmGet$feedId());
        builder.addInteger(columnInfo.createdOrUpdatedTimeColKey, realmObjectSource.realmGet$createdOrUpdatedTime());
        builder.addString(columnInfo.feedsJsonColKey, realmObjectSource.realmGet$feedsJson());
        builder.addInteger(columnInfo.channelIdColKey, realmObjectSource.realmGet$channelId());
        builder.addInteger(columnInfo.docIdColKey, realmObjectSource.realmGet$docId());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmChannelFeedInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmChannelFeedInfoColumnInfo columnInfo = (RealmChannelFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        long pkColumnKey = columnInfo.feedChannelIdColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedChannelId();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.feedIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedId(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.createdOrUpdatedTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$createdOrUpdatedTime(), false);
        String realmGet$feedsJson = ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedsJson();
        if (realmGet$feedsJson != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.feedsJsonColKey, colKey, realmGet$feedsJson, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.channelIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$channelId(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.docIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$docId(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmChannelFeedInfoColumnInfo columnInfo = (RealmChannelFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        long pkColumnKey = columnInfo.feedChannelIdColKey;
        com.vam.whitecoats.core.realm.RealmChannelFeedInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedChannelId();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.feedIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedId(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.createdOrUpdatedTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$createdOrUpdatedTime(), false);
            String realmGet$feedsJson = ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedsJson();
            if (realmGet$feedsJson != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.feedsJsonColKey, colKey, realmGet$feedsJson, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.channelIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$channelId(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.docIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$docId(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmChannelFeedInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmChannelFeedInfoColumnInfo columnInfo = (RealmChannelFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        long pkColumnKey = columnInfo.feedChannelIdColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedChannelId();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.feedIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedId(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.createdOrUpdatedTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$createdOrUpdatedTime(), false);
        String realmGet$feedsJson = ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedsJson();
        if (realmGet$feedsJson != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.feedsJsonColKey, colKey, realmGet$feedsJson, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.feedsJsonColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.channelIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$channelId(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.docIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$docId(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmChannelFeedInfoColumnInfo columnInfo = (RealmChannelFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        long pkColumnKey = columnInfo.feedChannelIdColKey;
        com.vam.whitecoats.core.realm.RealmChannelFeedInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedChannelId();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.feedIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedId(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.createdOrUpdatedTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$createdOrUpdatedTime(), false);
            String realmGet$feedsJson = ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$feedsJson();
            if (realmGet$feedsJson != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.feedsJsonColKey, colKey, realmGet$feedsJson, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.feedsJsonColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.channelIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$channelId(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.docIdColKey, colKey, ((com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) object).realmGet$docId(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmChannelFeedInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmChannelFeedInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmChannelFeedInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmChannelFeedInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$feedChannelId(realmSource.realmGet$feedChannelId());
        unmanagedCopy.realmSet$feedId(realmSource.realmGet$feedId());
        unmanagedCopy.realmSet$createdOrUpdatedTime(realmSource.realmGet$createdOrUpdatedTime());
        unmanagedCopy.realmSet$feedsJson(realmSource.realmGet$feedsJson());
        unmanagedCopy.realmSet$channelId(realmSource.realmGet$channelId());
        unmanagedCopy.realmSet$docId(realmSource.realmGet$docId());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmChannelFeedInfo update(Realm realm, RealmChannelFeedInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmChannelFeedInfo realmObject, com.vam.whitecoats.core.realm.RealmChannelFeedInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.feedChannelIdColKey, realmObjectSource.realmGet$feedChannelId());
        builder.addInteger(columnInfo.feedIdColKey, realmObjectSource.realmGet$feedId());
        builder.addInteger(columnInfo.createdOrUpdatedTimeColKey, realmObjectSource.realmGet$createdOrUpdatedTime());
        builder.addString(columnInfo.feedsJsonColKey, realmObjectSource.realmGet$feedsJson());
        builder.addInteger(columnInfo.channelIdColKey, realmObjectSource.realmGet$channelId());
        builder.addInteger(columnInfo.docIdColKey, realmObjectSource.realmGet$docId());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmChannelFeedInfo = proxy[");
        stringBuilder.append("{feedChannelId:");
        stringBuilder.append(realmGet$feedChannelId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{feedId:");
        stringBuilder.append(realmGet$feedId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{createdOrUpdatedTime:");
        stringBuilder.append(realmGet$createdOrUpdatedTime());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{feedsJson:");
        stringBuilder.append(realmGet$feedsJson() != null ? realmGet$feedsJson() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{channelId:");
        stringBuilder.append(realmGet$channelId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{docId:");
        stringBuilder.append(realmGet$docId());
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
        com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy aRealmChannelFeedInfo = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmChannelFeedInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmChannelFeedInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmChannelFeedInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
