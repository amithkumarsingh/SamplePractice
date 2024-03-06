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
public class com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmAdSlotInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface {

    static final class RealmAdSlotInfoColumnInfo extends ColumnInfo {
        long slot_idColKey;
        long locationColKey;
        long dimensionsColKey;
        long ad_sourceColKey;
        long occuranceColKey;
        long max_limitColKey;
        long source_slot_idColKey;
        long ad_location_type_idColKey;
        long ad_slot_durationColKey;

        RealmAdSlotInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(9);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmAdSlotInfo");
            this.slot_idColKey = addColumnDetails("slot_id", "slot_id", objectSchemaInfo);
            this.locationColKey = addColumnDetails("location", "location", objectSchemaInfo);
            this.dimensionsColKey = addColumnDetails("dimensions", "dimensions", objectSchemaInfo);
            this.ad_sourceColKey = addColumnDetails("ad_source", "ad_source", objectSchemaInfo);
            this.occuranceColKey = addColumnDetails("occurance", "occurance", objectSchemaInfo);
            this.max_limitColKey = addColumnDetails("max_limit", "max_limit", objectSchemaInfo);
            this.source_slot_idColKey = addColumnDetails("source_slot_id", "source_slot_id", objectSchemaInfo);
            this.ad_location_type_idColKey = addColumnDetails("ad_location_type_id", "ad_location_type_id", objectSchemaInfo);
            this.ad_slot_durationColKey = addColumnDetails("ad_slot_duration", "ad_slot_duration", objectSchemaInfo);
        }

        RealmAdSlotInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmAdSlotInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmAdSlotInfoColumnInfo src = (RealmAdSlotInfoColumnInfo) rawSrc;
            final RealmAdSlotInfoColumnInfo dst = (RealmAdSlotInfoColumnInfo) rawDst;
            dst.slot_idColKey = src.slot_idColKey;
            dst.locationColKey = src.locationColKey;
            dst.dimensionsColKey = src.dimensionsColKey;
            dst.ad_sourceColKey = src.ad_sourceColKey;
            dst.occuranceColKey = src.occuranceColKey;
            dst.max_limitColKey = src.max_limitColKey;
            dst.source_slot_idColKey = src.source_slot_idColKey;
            dst.ad_location_type_idColKey = src.ad_location_type_idColKey;
            dst.ad_slot_durationColKey = src.ad_slot_durationColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmAdSlotInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmAdSlotInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmAdSlotInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmAdSlotInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$slot_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.slot_idColKey);
    }

    @Override
    public void realmSet$slot_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.slot_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.slot_idColKey, value);
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
    public String realmGet$dimensions() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dimensionsColKey);
    }

    @Override
    public void realmSet$dimensions(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dimensionsColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dimensionsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dimensionsColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dimensionsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$ad_source() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.ad_sourceColKey);
    }

    @Override
    public void realmSet$ad_source(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.ad_sourceColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.ad_sourceColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.ad_sourceColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.ad_sourceColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$occurance() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.occuranceColKey);
    }

    @Override
    public void realmSet$occurance(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.occuranceColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.occuranceColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$max_limit() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.max_limitColKey);
    }

    @Override
    public void realmSet$max_limit(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.max_limitColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.max_limitColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$source_slot_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.source_slot_idColKey);
    }

    @Override
    public void realmSet$source_slot_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.source_slot_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.source_slot_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.source_slot_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.source_slot_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$ad_location_type_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.ad_location_type_idColKey);
    }

    @Override
    public void realmSet$ad_location_type_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.ad_location_type_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.ad_location_type_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$ad_slot_duration() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.ad_slot_durationColKey);
    }

    @Override
    public void realmSet$ad_slot_duration(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.ad_slot_durationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.ad_slot_durationColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmAdSlotInfo", 9, 0);
        builder.addPersistedProperty("slot_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("location", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dimensions", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("ad_source", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("occurance", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("max_limit", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("source_slot_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("ad_location_type_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("ad_slot_duration", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmAdSlotInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmAdSlotInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmAdSlotInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmAdSlotInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmAdSlotInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmAdSlotInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) obj;
        if (json.has("slot_id")) {
            if (json.isNull("slot_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'slot_id' to null.");
            } else {
                objProxy.realmSet$slot_id((int) json.getInt("slot_id"));
            }
        }
        if (json.has("location")) {
            if (json.isNull("location")) {
                objProxy.realmSet$location(null);
            } else {
                objProxy.realmSet$location((String) json.getString("location"));
            }
        }
        if (json.has("dimensions")) {
            if (json.isNull("dimensions")) {
                objProxy.realmSet$dimensions(null);
            } else {
                objProxy.realmSet$dimensions((String) json.getString("dimensions"));
            }
        }
        if (json.has("ad_source")) {
            if (json.isNull("ad_source")) {
                objProxy.realmSet$ad_source(null);
            } else {
                objProxy.realmSet$ad_source((String) json.getString("ad_source"));
            }
        }
        if (json.has("occurance")) {
            if (json.isNull("occurance")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'occurance' to null.");
            } else {
                objProxy.realmSet$occurance((int) json.getInt("occurance"));
            }
        }
        if (json.has("max_limit")) {
            if (json.isNull("max_limit")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'max_limit' to null.");
            } else {
                objProxy.realmSet$max_limit((int) json.getInt("max_limit"));
            }
        }
        if (json.has("source_slot_id")) {
            if (json.isNull("source_slot_id")) {
                objProxy.realmSet$source_slot_id(null);
            } else {
                objProxy.realmSet$source_slot_id((String) json.getString("source_slot_id"));
            }
        }
        if (json.has("ad_location_type_id")) {
            if (json.isNull("ad_location_type_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'ad_location_type_id' to null.");
            } else {
                objProxy.realmSet$ad_location_type_id((int) json.getInt("ad_location_type_id"));
            }
        }
        if (json.has("ad_slot_duration")) {
            if (json.isNull("ad_slot_duration")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'ad_slot_duration' to null.");
            } else {
                objProxy.realmSet$ad_slot_duration((int) json.getInt("ad_slot_duration"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmAdSlotInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmAdSlotInfo obj = new com.vam.whitecoats.core.realm.RealmAdSlotInfo();
        final com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("slot_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$slot_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'slot_id' to null.");
                }
            } else if (name.equals("location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$location(null);
                }
            } else if (name.equals("dimensions")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dimensions((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dimensions(null);
                }
            } else if (name.equals("ad_source")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$ad_source((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$ad_source(null);
                }
            } else if (name.equals("occurance")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$occurance((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'occurance' to null.");
                }
            } else if (name.equals("max_limit")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$max_limit((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'max_limit' to null.");
                }
            } else if (name.equals("source_slot_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$source_slot_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$source_slot_id(null);
                }
            } else if (name.equals("ad_location_type_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$ad_location_type_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'ad_location_type_id' to null.");
                }
            } else if (name.equals("ad_slot_duration")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$ad_slot_duration((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'ad_slot_duration' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmAdSlotInfo copyOrUpdate(Realm realm, RealmAdSlotInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmAdSlotInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmAdSlotInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmAdSlotInfo copy(Realm realm, RealmAdSlotInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmAdSlotInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmAdSlotInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.slot_idColKey, realmObjectSource.realmGet$slot_id());
        builder.addString(columnInfo.locationColKey, realmObjectSource.realmGet$location());
        builder.addString(columnInfo.dimensionsColKey, realmObjectSource.realmGet$dimensions());
        builder.addString(columnInfo.ad_sourceColKey, realmObjectSource.realmGet$ad_source());
        builder.addInteger(columnInfo.occuranceColKey, realmObjectSource.realmGet$occurance());
        builder.addInteger(columnInfo.max_limitColKey, realmObjectSource.realmGet$max_limit());
        builder.addString(columnInfo.source_slot_idColKey, realmObjectSource.realmGet$source_slot_id());
        builder.addInteger(columnInfo.ad_location_type_idColKey, realmObjectSource.realmGet$ad_location_type_id());
        builder.addInteger(columnInfo.ad_slot_durationColKey, realmObjectSource.realmGet$ad_slot_duration());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmAdSlotInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAdSlotInfoColumnInfo columnInfo = (RealmAdSlotInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.slot_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$slot_id(), false);
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        }
        String realmGet$dimensions = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$dimensions();
        if (realmGet$dimensions != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dimensionsColKey, colKey, realmGet$dimensions, false);
        }
        String realmGet$ad_source = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_source();
        if (realmGet$ad_source != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ad_sourceColKey, colKey, realmGet$ad_source, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.occuranceColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$occurance(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.max_limitColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$max_limit(), false);
        String realmGet$source_slot_id = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$source_slot_id();
        if (realmGet$source_slot_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.source_slot_idColKey, colKey, realmGet$source_slot_id, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.ad_location_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_location_type_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.ad_slot_durationColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_slot_duration(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAdSlotInfoColumnInfo columnInfo = (RealmAdSlotInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        com.vam.whitecoats.core.realm.RealmAdSlotInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmAdSlotInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.slot_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$slot_id(), false);
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            }
            String realmGet$dimensions = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$dimensions();
            if (realmGet$dimensions != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dimensionsColKey, colKey, realmGet$dimensions, false);
            }
            String realmGet$ad_source = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_source();
            if (realmGet$ad_source != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ad_sourceColKey, colKey, realmGet$ad_source, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.occuranceColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$occurance(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.max_limitColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$max_limit(), false);
            String realmGet$source_slot_id = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$source_slot_id();
            if (realmGet$source_slot_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.source_slot_idColKey, colKey, realmGet$source_slot_id, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.ad_location_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_location_type_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.ad_slot_durationColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_slot_duration(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmAdSlotInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAdSlotInfoColumnInfo columnInfo = (RealmAdSlotInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.slot_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$slot_id(), false);
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
        }
        String realmGet$dimensions = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$dimensions();
        if (realmGet$dimensions != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dimensionsColKey, colKey, realmGet$dimensions, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dimensionsColKey, colKey, false);
        }
        String realmGet$ad_source = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_source();
        if (realmGet$ad_source != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.ad_sourceColKey, colKey, realmGet$ad_source, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.ad_sourceColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.occuranceColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$occurance(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.max_limitColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$max_limit(), false);
        String realmGet$source_slot_id = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$source_slot_id();
        if (realmGet$source_slot_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.source_slot_idColKey, colKey, realmGet$source_slot_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.source_slot_idColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.ad_location_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_location_type_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.ad_slot_durationColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_slot_duration(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmAdSlotInfoColumnInfo columnInfo = (RealmAdSlotInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        com.vam.whitecoats.core.realm.RealmAdSlotInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmAdSlotInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.slot_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$slot_id(), false);
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
            }
            String realmGet$dimensions = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$dimensions();
            if (realmGet$dimensions != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dimensionsColKey, colKey, realmGet$dimensions, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dimensionsColKey, colKey, false);
            }
            String realmGet$ad_source = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_source();
            if (realmGet$ad_source != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.ad_sourceColKey, colKey, realmGet$ad_source, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.ad_sourceColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.occuranceColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$occurance(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.max_limitColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$max_limit(), false);
            String realmGet$source_slot_id = ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$source_slot_id();
            if (realmGet$source_slot_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.source_slot_idColKey, colKey, realmGet$source_slot_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.source_slot_idColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.ad_location_type_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_location_type_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.ad_slot_durationColKey, colKey, ((com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) object).realmGet$ad_slot_duration(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmAdSlotInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmAdSlotInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmAdSlotInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmAdSlotInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmAdSlotInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmAdSlotInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$slot_id(realmSource.realmGet$slot_id());
        unmanagedCopy.realmSet$location(realmSource.realmGet$location());
        unmanagedCopy.realmSet$dimensions(realmSource.realmGet$dimensions());
        unmanagedCopy.realmSet$ad_source(realmSource.realmGet$ad_source());
        unmanagedCopy.realmSet$occurance(realmSource.realmGet$occurance());
        unmanagedCopy.realmSet$max_limit(realmSource.realmGet$max_limit());
        unmanagedCopy.realmSet$source_slot_id(realmSource.realmGet$source_slot_id());
        unmanagedCopy.realmSet$ad_location_type_id(realmSource.realmGet$ad_location_type_id());
        unmanagedCopy.realmSet$ad_slot_duration(realmSource.realmGet$ad_slot_duration());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmAdSlotInfo = proxy[");
        stringBuilder.append("{slot_id:");
        stringBuilder.append(realmGet$slot_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{location:");
        stringBuilder.append(realmGet$location() != null ? realmGet$location() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dimensions:");
        stringBuilder.append(realmGet$dimensions() != null ? realmGet$dimensions() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ad_source:");
        stringBuilder.append(realmGet$ad_source() != null ? realmGet$ad_source() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{occurance:");
        stringBuilder.append(realmGet$occurance());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{max_limit:");
        stringBuilder.append(realmGet$max_limit());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{source_slot_id:");
        stringBuilder.append(realmGet$source_slot_id() != null ? realmGet$source_slot_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ad_location_type_id:");
        stringBuilder.append(realmGet$ad_location_type_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{ad_slot_duration:");
        stringBuilder.append(realmGet$ad_slot_duration());
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
        com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy aRealmAdSlotInfo = (com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmAdSlotInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmAdSlotInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmAdSlotInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
