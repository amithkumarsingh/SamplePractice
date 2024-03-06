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
public class com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmEventsInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface {

    static final class RealmEventsInfoColumnInfo extends ColumnInfo {
        long eventIdColKey;
        long eventTitleColKey;
        long locationColKey;
        long startDateColKey;
        long endDateColKey;

        RealmEventsInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(5);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmEventsInfo");
            this.eventIdColKey = addColumnDetails("eventId", "eventId", objectSchemaInfo);
            this.eventTitleColKey = addColumnDetails("eventTitle", "eventTitle", objectSchemaInfo);
            this.locationColKey = addColumnDetails("location", "location", objectSchemaInfo);
            this.startDateColKey = addColumnDetails("startDate", "startDate", objectSchemaInfo);
            this.endDateColKey = addColumnDetails("endDate", "endDate", objectSchemaInfo);
        }

        RealmEventsInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmEventsInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmEventsInfoColumnInfo src = (RealmEventsInfoColumnInfo) rawSrc;
            final RealmEventsInfoColumnInfo dst = (RealmEventsInfoColumnInfo) rawDst;
            dst.eventIdColKey = src.eventIdColKey;
            dst.eventTitleColKey = src.eventTitleColKey;
            dst.locationColKey = src.locationColKey;
            dst.startDateColKey = src.startDateColKey;
            dst.endDateColKey = src.endDateColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmEventsInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmEventsInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmEventsInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmEventsInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$eventId() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.eventIdColKey);
    }

    @Override
    public void realmSet$eventId(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'eventId' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$eventTitle() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.eventTitleColKey);
    }

    @Override
    public void realmSet$eventTitle(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.eventTitleColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.eventTitleColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.eventTitleColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.eventTitleColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$location() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.locationColKey);
    }

    @Override
    public void realmSet$location(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.locationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.locationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.locationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.locationColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$startDate() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.startDateColKey);
    }

    @Override
    public void realmSet$startDate(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.startDateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.startDateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$endDate() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.endDateColKey);
    }

    @Override
    public void realmSet$endDate(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.endDateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.endDateColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmEventsInfo", 5, 0);
        builder.addPersistedProperty("eventId", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("eventTitle", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("location", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("startDate", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("endDate", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmEventsInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmEventsInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmEventsInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmEventsInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmEventsInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmEventsInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
            RealmEventsInfoColumnInfo columnInfo = (RealmEventsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
            long pkColumnKey = columnInfo.eventIdColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("eventId")) {
                colKey = table.findFirstLong(pkColumnKey, json.getLong("eventId"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmEventsInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("eventId")) {
                if (json.isNull("eventId")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmEventsInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmEventsInfo.class, json.getInt("eventId"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'eventId'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) obj;
        if (json.has("eventTitle")) {
            if (json.isNull("eventTitle")) {
                objProxy.realmSet$eventTitle(null);
            } else {
                objProxy.realmSet$eventTitle((String) json.getString("eventTitle"));
            }
        }
        if (json.has("location")) {
            if (json.isNull("location")) {
                objProxy.realmSet$location(null);
            } else {
                objProxy.realmSet$location((String) json.getString("location"));
            }
        }
        if (json.has("startDate")) {
            if (json.isNull("startDate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'startDate' to null.");
            } else {
                objProxy.realmSet$startDate((long) json.getLong("startDate"));
            }
        }
        if (json.has("endDate")) {
            if (json.isNull("endDate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'endDate' to null.");
            } else {
                objProxy.realmSet$endDate((long) json.getLong("endDate"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmEventsInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmEventsInfo obj = new com.vam.whitecoats.core.realm.RealmEventsInfo();
        final com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("eventId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$eventId((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'eventId' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("eventTitle")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$eventTitle((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$eventTitle(null);
                }
            } else if (name.equals("location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$location(null);
                }
            } else if (name.equals("startDate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$startDate((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'startDate' to null.");
                }
            } else if (name.equals("endDate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$endDate((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'endDate' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'eventId'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmEventsInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmEventsInfo copyOrUpdate(Realm realm, RealmEventsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmEventsInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmEventsInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmEventsInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
            long pkColumnKey = columnInfo.eventIdColKey;
            long colKey = table.findFirstLong(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmEventsInfo copy(Realm realm, RealmEventsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmEventsInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmEventsInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.eventIdColKey, realmObjectSource.realmGet$eventId());
        builder.addString(columnInfo.eventTitleColKey, realmObjectSource.realmGet$eventTitle());
        builder.addString(columnInfo.locationColKey, realmObjectSource.realmGet$location());
        builder.addInteger(columnInfo.startDateColKey, realmObjectSource.realmGet$startDate());
        builder.addInteger(columnInfo.endDateColKey, realmObjectSource.realmGet$endDate());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmEventsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmEventsInfoColumnInfo columnInfo = (RealmEventsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        long pkColumnKey = columnInfo.eventIdColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$eventTitle = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventTitle();
        if (realmGet$eventTitle != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.eventTitleColKey, colKey, realmGet$eventTitle, false);
        }
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.startDateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$startDate(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.endDateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$endDate(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmEventsInfoColumnInfo columnInfo = (RealmEventsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        long pkColumnKey = columnInfo.eventIdColKey;
        com.vam.whitecoats.core.realm.RealmEventsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmEventsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$eventTitle = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventTitle();
            if (realmGet$eventTitle != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.eventTitleColKey, colKey, realmGet$eventTitle, false);
            }
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.startDateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$startDate(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.endDateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$endDate(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmEventsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmEventsInfoColumnInfo columnInfo = (RealmEventsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        long pkColumnKey = columnInfo.eventIdColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
        }
        cache.put(object, colKey);
        String realmGet$eventTitle = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventTitle();
        if (realmGet$eventTitle != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.eventTitleColKey, colKey, realmGet$eventTitle, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.eventTitleColKey, colKey, false);
        }
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.startDateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$startDate(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.endDateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$endDate(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmEventsInfoColumnInfo columnInfo = (RealmEventsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        long pkColumnKey = columnInfo.eventIdColKey;
        com.vam.whitecoats.core.realm.RealmEventsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmEventsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventId());
            }
            cache.put(object, colKey);
            String realmGet$eventTitle = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$eventTitle();
            if (realmGet$eventTitle != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.eventTitleColKey, colKey, realmGet$eventTitle, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.eventTitleColKey, colKey, false);
            }
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.startDateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$startDate(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.endDateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) object).realmGet$endDate(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmEventsInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmEventsInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmEventsInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmEventsInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmEventsInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmEventsInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$eventId(realmSource.realmGet$eventId());
        unmanagedCopy.realmSet$eventTitle(realmSource.realmGet$eventTitle());
        unmanagedCopy.realmSet$location(realmSource.realmGet$location());
        unmanagedCopy.realmSet$startDate(realmSource.realmGet$startDate());
        unmanagedCopy.realmSet$endDate(realmSource.realmGet$endDate());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmEventsInfo update(Realm realm, RealmEventsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmEventsInfo realmObject, com.vam.whitecoats.core.realm.RealmEventsInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addInteger(columnInfo.eventIdColKey, realmObjectSource.realmGet$eventId());
        builder.addString(columnInfo.eventTitleColKey, realmObjectSource.realmGet$eventTitle());
        builder.addString(columnInfo.locationColKey, realmObjectSource.realmGet$location());
        builder.addInteger(columnInfo.startDateColKey, realmObjectSource.realmGet$startDate());
        builder.addInteger(columnInfo.endDateColKey, realmObjectSource.realmGet$endDate());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmEventsInfo = proxy[");
        stringBuilder.append("{eventId:");
        stringBuilder.append(realmGet$eventId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{eventTitle:");
        stringBuilder.append(realmGet$eventTitle() != null ? realmGet$eventTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{location:");
        stringBuilder.append(realmGet$location() != null ? realmGet$location() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{startDate:");
        stringBuilder.append(realmGet$startDate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{endDate:");
        stringBuilder.append(realmGet$endDate());
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
        com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy aRealmEventsInfo = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmEventsInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmEventsInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmEventsInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
