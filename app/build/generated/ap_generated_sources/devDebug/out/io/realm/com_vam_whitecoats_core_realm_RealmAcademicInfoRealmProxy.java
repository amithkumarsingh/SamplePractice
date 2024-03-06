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
public class com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmAcademicInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface {

    static final class RealmAcademicInfoColumnInfo extends ColumnInfo {
        long acad_idColKey;
        long degreeColKey;
        long universityColKey;
        long collegeColKey;
        long passing_yearColKey;
        long currently_pursuingColKey;

        RealmAcademicInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(6);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmAcademicInfo");
            this.acad_idColKey = addColumnDetails("acad_id", "acad_id", objectSchemaInfo);
            this.degreeColKey = addColumnDetails("degree", "degree", objectSchemaInfo);
            this.universityColKey = addColumnDetails("university", "university", objectSchemaInfo);
            this.collegeColKey = addColumnDetails("college", "college", objectSchemaInfo);
            this.passing_yearColKey = addColumnDetails("passing_year", "passing_year", objectSchemaInfo);
            this.currently_pursuingColKey = addColumnDetails("currently_pursuing", "currently_pursuing", objectSchemaInfo);
        }

        RealmAcademicInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmAcademicInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmAcademicInfoColumnInfo src = (RealmAcademicInfoColumnInfo) rawSrc;
            final RealmAcademicInfoColumnInfo dst = (RealmAcademicInfoColumnInfo) rawDst;
            dst.acad_idColKey = src.acad_idColKey;
            dst.degreeColKey = src.degreeColKey;
            dst.universityColKey = src.universityColKey;
            dst.collegeColKey = src.collegeColKey;
            dst.passing_yearColKey = src.passing_yearColKey;
            dst.currently_pursuingColKey = src.currently_pursuingColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmAcademicInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmAcademicInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmAcademicInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmAcademicInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$acad_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.acad_idColKey);
    }

    @Override
    public void realmSet$acad_id(Integer value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'acad_id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$degree() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.degreeColKey);
    }

    @Override
    public void realmSet$degree(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.degreeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.degreeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.degreeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.degreeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$university() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.universityColKey);
    }

    @Override
    public void realmSet$university(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.universityColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.universityColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.universityColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.universityColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$college() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.collegeColKey);
    }

    @Override
    public void realmSet$college(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.collegeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.collegeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.collegeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.collegeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$passing_year() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.passing_yearColKey);
    }

    @Override
    public void realmSet$passing_year(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.passing_yearColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.passing_yearColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$currently_pursuing() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.currently_pursuingColKey);
    }

    @Override
    public void realmSet$currently_pursuing(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.currently_pursuingColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.currently_pursuingColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmAcademicInfo", 6, 0);
        builder.addPersistedProperty("acad_id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("degree", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("university", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("college", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("passing_year", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("currently_pursuing", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmAcademicInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmAcademicInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmAcademicInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmAcademicInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmAcademicInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmAcademicInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
            RealmAcademicInfoColumnInfo columnInfo = (RealmAcademicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
            long pkColumnKey = columnInfo.acad_idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("acad_id")) {
                colKey = table.findFirstLong(pkColumnKey, json.getLong("acad_id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAcademicInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("acad_id")) {
                if (json.isNull("acad_id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmAcademicInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmAcademicInfo.class, json.getInt("acad_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'acad_id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) obj;
        if (json.has("degree")) {
            if (json.isNull("degree")) {
                objProxy.realmSet$degree(null);
            } else {
                objProxy.realmSet$degree((String) json.getString("degree"));
            }
        }
        if (json.has("university")) {
            if (json.isNull("university")) {
                objProxy.realmSet$university(null);
            } else {
                objProxy.realmSet$university((String) json.getString("university"));
            }
        }
        if (json.has("college")) {
            if (json.isNull("college")) {
                objProxy.realmSet$college(null);
            } else {
                objProxy.realmSet$college((String) json.getString("college"));
            }
        }
        if (json.has("passing_year")) {
            if (json.isNull("passing_year")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'passing_year' to null.");
            } else {
                objProxy.realmSet$passing_year((int) json.getInt("passing_year"));
            }
        }
        if (json.has("currently_pursuing")) {
            if (json.isNull("currently_pursuing")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'currently_pursuing' to null.");
            } else {
                objProxy.realmSet$currently_pursuing((boolean) json.getBoolean("currently_pursuing"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmAcademicInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmAcademicInfo obj = new com.vam.whitecoats.core.realm.RealmAcademicInfo();
        final com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("acad_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$acad_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$acad_id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("degree")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$degree((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$degree(null);
                }
            } else if (name.equals("university")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$university((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$university(null);
                }
            } else if (name.equals("college")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$college((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$college(null);
                }
            } else if (name.equals("passing_year")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$passing_year((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'passing_year' to null.");
                }
            } else if (name.equals("currently_pursuing")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$currently_pursuing((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'currently_pursuing' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'acad_id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAcademicInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmAcademicInfo copyOrUpdate(Realm realm, RealmAcademicInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmAcademicInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmAcademicInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmAcademicInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
            long pkColumnKey = columnInfo.acad_idColKey;
            long colKey = table.findFirstLong(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmAcademicInfo copy(Realm realm, RealmAcademicInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmAcademicInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmAcademicInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.acad_idColKey, realmObjectSource.realmGet$acad_id());
        builder.addString(columnInfo.degreeColKey, realmObjectSource.realmGet$degree());
        builder.addString(columnInfo.universityColKey, realmObjectSource.realmGet$university());
        builder.addString(columnInfo.collegeColKey, realmObjectSource.realmGet$college());
        builder.addInteger(columnInfo.passing_yearColKey, realmObjectSource.realmGet$passing_year());
        builder.addBoolean(columnInfo.currently_pursuingColKey, realmObjectSource.realmGet$currently_pursuing());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmAcademicInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAcademicInfoColumnInfo columnInfo = (RealmAcademicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        long pkColumnKey = columnInfo.acad_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$degree = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$degree();
        if (realmGet$degree != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.degreeColKey, colKey, realmGet$degree, false);
        }
        String realmGet$university = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$university();
        if (realmGet$university != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.universityColKey, colKey, realmGet$university, false);
        }
        String realmGet$college = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$college();
        if (realmGet$college != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.collegeColKey, colKey, realmGet$college, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.passing_yearColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$passing_year(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.currently_pursuingColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$currently_pursuing(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAcademicInfoColumnInfo columnInfo = (RealmAcademicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        long pkColumnKey = columnInfo.acad_idColKey;
        com.vam.whitecoats.core.realm.RealmAcademicInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmAcademicInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$degree = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$degree();
            if (realmGet$degree != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.degreeColKey, colKey, realmGet$degree, false);
            }
            String realmGet$university = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$university();
            if (realmGet$university != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.universityColKey, colKey, realmGet$university, false);
            }
            String realmGet$college = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$college();
            if (realmGet$college != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.collegeColKey, colKey, realmGet$college, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.passing_yearColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$passing_year(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.currently_pursuingColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$currently_pursuing(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmAcademicInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAcademicInfoColumnInfo columnInfo = (RealmAcademicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        long pkColumnKey = columnInfo.acad_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
        }
        cache.put(object, colKey);
        String realmGet$degree = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$degree();
        if (realmGet$degree != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.degreeColKey, colKey, realmGet$degree, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.degreeColKey, colKey, false);
        }
        String realmGet$university = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$university();
        if (realmGet$university != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.universityColKey, colKey, realmGet$university, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.universityColKey, colKey, false);
        }
        String realmGet$college = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$college();
        if (realmGet$college != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.collegeColKey, colKey, realmGet$college, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.collegeColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.passing_yearColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$passing_year(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.currently_pursuingColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$currently_pursuing(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAcademicInfoColumnInfo columnInfo = (RealmAcademicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        long pkColumnKey = columnInfo.acad_idColKey;
        com.vam.whitecoats.core.realm.RealmAcademicInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmAcademicInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$acad_id());
            }
            cache.put(object, colKey);
            String realmGet$degree = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$degree();
            if (realmGet$degree != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.degreeColKey, colKey, realmGet$degree, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.degreeColKey, colKey, false);
            }
            String realmGet$university = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$university();
            if (realmGet$university != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.universityColKey, colKey, realmGet$university, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.universityColKey, colKey, false);
            }
            String realmGet$college = ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$college();
            if (realmGet$college != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.collegeColKey, colKey, realmGet$college, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.collegeColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.passing_yearColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$passing_year(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.currently_pursuingColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) object).realmGet$currently_pursuing(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmAcademicInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmAcademicInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmAcademicInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmAcademicInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmAcademicInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmAcademicInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$acad_id(realmSource.realmGet$acad_id());
        unmanagedCopy.realmSet$degree(realmSource.realmGet$degree());
        unmanagedCopy.realmSet$university(realmSource.realmGet$university());
        unmanagedCopy.realmSet$college(realmSource.realmGet$college());
        unmanagedCopy.realmSet$passing_year(realmSource.realmGet$passing_year());
        unmanagedCopy.realmSet$currently_pursuing(realmSource.realmGet$currently_pursuing());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmAcademicInfo update(Realm realm, RealmAcademicInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmAcademicInfo realmObject, com.vam.whitecoats.core.realm.RealmAcademicInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addInteger(columnInfo.acad_idColKey, realmObjectSource.realmGet$acad_id());
        builder.addString(columnInfo.degreeColKey, realmObjectSource.realmGet$degree());
        builder.addString(columnInfo.universityColKey, realmObjectSource.realmGet$university());
        builder.addString(columnInfo.collegeColKey, realmObjectSource.realmGet$college());
        builder.addInteger(columnInfo.passing_yearColKey, realmObjectSource.realmGet$passing_year());
        builder.addBoolean(columnInfo.currently_pursuingColKey, realmObjectSource.realmGet$currently_pursuing());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmAcademicInfo = proxy[");
        stringBuilder.append("{acad_id:");
        stringBuilder.append(realmGet$acad_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{degree:");
        stringBuilder.append(realmGet$degree() != null ? realmGet$degree() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{university:");
        stringBuilder.append(realmGet$university() != null ? realmGet$university() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{college:");
        stringBuilder.append(realmGet$college() != null ? realmGet$college() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{passing_year:");
        stringBuilder.append(realmGet$passing_year());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{currently_pursuing:");
        stringBuilder.append(realmGet$currently_pursuing());
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
        com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy aRealmAcademicInfo = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmAcademicInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmAcademicInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmAcademicInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
