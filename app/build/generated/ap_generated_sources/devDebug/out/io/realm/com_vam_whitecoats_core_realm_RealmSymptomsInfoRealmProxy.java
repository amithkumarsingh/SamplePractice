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
public class com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmSymptomsInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface {

    static final class RealmSymptomsInfoColumnInfo extends ColumnInfo {
        long sym_idColKey;
        long addSymptonsColKey;
        long durationColKey;
        long detailsColKey;

        RealmSymptomsInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmSymptomsInfo");
            this.sym_idColKey = addColumnDetails("sym_id", "sym_id", objectSchemaInfo);
            this.addSymptonsColKey = addColumnDetails("addSymptons", "addSymptons", objectSchemaInfo);
            this.durationColKey = addColumnDetails("duration", "duration", objectSchemaInfo);
            this.detailsColKey = addColumnDetails("details", "details", objectSchemaInfo);
        }

        RealmSymptomsInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmSymptomsInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmSymptomsInfoColumnInfo src = (RealmSymptomsInfoColumnInfo) rawSrc;
            final RealmSymptomsInfoColumnInfo dst = (RealmSymptomsInfoColumnInfo) rawDst;
            dst.sym_idColKey = src.sym_idColKey;
            dst.addSymptonsColKey = src.addSymptonsColKey;
            dst.durationColKey = src.durationColKey;
            dst.detailsColKey = src.detailsColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmSymptomsInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmSymptomsInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmSymptomsInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmSymptomsInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$sym_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.sym_idColKey);
    }

    @Override
    public void realmSet$sym_id(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'sym_id' to null.");
            }
            row.getTable().setLong(columnInfo.sym_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'sym_id' to null.");
        }
        proxyState.getRow$realm().setLong(columnInfo.sym_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$addSymptons() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.addSymptonsColKey);
    }

    @Override
    public void realmSet$addSymptons(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.addSymptonsColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.addSymptonsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.addSymptonsColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.addSymptonsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$duration() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.durationColKey);
    }

    @Override
    public void realmSet$duration(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.durationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.durationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.durationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.durationColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$details() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.detailsColKey);
    }

    @Override
    public void realmSet$details(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.detailsColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.detailsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.detailsColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.detailsColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmSymptomsInfo", 4, 0);
        builder.addPersistedProperty("sym_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("addSymptons", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("duration", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("details", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmSymptomsInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmSymptomsInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmSymptomsInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmSymptomsInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmSymptomsInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmSymptomsInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) obj;
        if (json.has("sym_id")) {
            if (json.isNull("sym_id")) {
                objProxy.realmSet$sym_id(null);
            } else {
                objProxy.realmSet$sym_id((int) json.getInt("sym_id"));
            }
        }
        if (json.has("addSymptons")) {
            if (json.isNull("addSymptons")) {
                objProxy.realmSet$addSymptons(null);
            } else {
                objProxy.realmSet$addSymptons((String) json.getString("addSymptons"));
            }
        }
        if (json.has("duration")) {
            if (json.isNull("duration")) {
                objProxy.realmSet$duration(null);
            } else {
                objProxy.realmSet$duration((String) json.getString("duration"));
            }
        }
        if (json.has("details")) {
            if (json.isNull("details")) {
                objProxy.realmSet$details(null);
            } else {
                objProxy.realmSet$details((String) json.getString("details"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmSymptomsInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmSymptomsInfo obj = new com.vam.whitecoats.core.realm.RealmSymptomsInfo();
        final com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("sym_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$sym_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$sym_id(null);
                }
            } else if (name.equals("addSymptons")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$addSymptons((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$addSymptons(null);
                }
            } else if (name.equals("duration")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$duration((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$duration(null);
                }
            } else if (name.equals("details")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$details((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$details(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmSymptomsInfo copyOrUpdate(Realm realm, RealmSymptomsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmSymptomsInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmSymptomsInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmSymptomsInfo copy(Realm realm, RealmSymptomsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmSymptomsInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmSymptomsInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.sym_idColKey, realmObjectSource.realmGet$sym_id());
        builder.addString(columnInfo.addSymptonsColKey, realmObjectSource.realmGet$addSymptons());
        builder.addString(columnInfo.durationColKey, realmObjectSource.realmGet$duration());
        builder.addString(columnInfo.detailsColKey, realmObjectSource.realmGet$details());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmSymptomsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmSymptomsInfoColumnInfo columnInfo = (RealmSymptomsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$sym_id = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$sym_id();
        if (realmGet$sym_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.sym_idColKey, colKey, realmGet$sym_id.longValue(), false);
        }
        String realmGet$addSymptons = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$addSymptons();
        if (realmGet$addSymptons != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.addSymptonsColKey, colKey, realmGet$addSymptons, false);
        }
        String realmGet$duration = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$duration();
        if (realmGet$duration != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.durationColKey, colKey, realmGet$duration, false);
        }
        String realmGet$details = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$details();
        if (realmGet$details != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.detailsColKey, colKey, realmGet$details, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmSymptomsInfoColumnInfo columnInfo = (RealmSymptomsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        com.vam.whitecoats.core.realm.RealmSymptomsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmSymptomsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$sym_id = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$sym_id();
            if (realmGet$sym_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.sym_idColKey, colKey, realmGet$sym_id.longValue(), false);
            }
            String realmGet$addSymptons = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$addSymptons();
            if (realmGet$addSymptons != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.addSymptonsColKey, colKey, realmGet$addSymptons, false);
            }
            String realmGet$duration = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$duration();
            if (realmGet$duration != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.durationColKey, colKey, realmGet$duration, false);
            }
            String realmGet$details = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$details();
            if (realmGet$details != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.detailsColKey, colKey, realmGet$details, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmSymptomsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmSymptomsInfoColumnInfo columnInfo = (RealmSymptomsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$sym_id = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$sym_id();
        if (realmGet$sym_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.sym_idColKey, colKey, realmGet$sym_id.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.sym_idColKey, colKey, false);
        }
        String realmGet$addSymptons = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$addSymptons();
        if (realmGet$addSymptons != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.addSymptonsColKey, colKey, realmGet$addSymptons, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.addSymptonsColKey, colKey, false);
        }
        String realmGet$duration = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$duration();
        if (realmGet$duration != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.durationColKey, colKey, realmGet$duration, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.durationColKey, colKey, false);
        }
        String realmGet$details = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$details();
        if (realmGet$details != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.detailsColKey, colKey, realmGet$details, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.detailsColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmSymptomsInfoColumnInfo columnInfo = (RealmSymptomsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        com.vam.whitecoats.core.realm.RealmSymptomsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmSymptomsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$sym_id = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$sym_id();
            if (realmGet$sym_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.sym_idColKey, colKey, realmGet$sym_id.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.sym_idColKey, colKey, false);
            }
            String realmGet$addSymptons = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$addSymptons();
            if (realmGet$addSymptons != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.addSymptonsColKey, colKey, realmGet$addSymptons, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.addSymptonsColKey, colKey, false);
            }
            String realmGet$duration = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$duration();
            if (realmGet$duration != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.durationColKey, colKey, realmGet$duration, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.durationColKey, colKey, false);
            }
            String realmGet$details = ((com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) object).realmGet$details();
            if (realmGet$details != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.detailsColKey, colKey, realmGet$details, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.detailsColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmSymptomsInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmSymptomsInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmSymptomsInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmSymptomsInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmSymptomsInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmSymptomsInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$sym_id(realmSource.realmGet$sym_id());
        unmanagedCopy.realmSet$addSymptons(realmSource.realmGet$addSymptons());
        unmanagedCopy.realmSet$duration(realmSource.realmGet$duration());
        unmanagedCopy.realmSet$details(realmSource.realmGet$details());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmSymptomsInfo = proxy[");
        stringBuilder.append("{sym_id:");
        stringBuilder.append(realmGet$sym_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{addSymptons:");
        stringBuilder.append(realmGet$addSymptons() != null ? realmGet$addSymptons() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{duration:");
        stringBuilder.append(realmGet$duration() != null ? realmGet$duration() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{details:");
        stringBuilder.append(realmGet$details() != null ? realmGet$details() : "null");
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
        com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy aRealmSymptomsInfo = (com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmSymptomsInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmSymptomsInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmSymptomsInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
