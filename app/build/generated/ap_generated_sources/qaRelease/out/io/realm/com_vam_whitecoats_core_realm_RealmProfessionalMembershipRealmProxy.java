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
public class com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy extends com.vam.whitecoats.core.realm.RealmProfessionalMembership
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface {

    static final class RealmProfessionalMembershipColumnInfo extends ColumnInfo {
        long prof_mem_idColKey;
        long membership_nameColKey;
        long typeColKey;
        long award_nameColKey;
        long award_idColKey;
        long award_yearColKey;
        long presented_atColKey;

        RealmProfessionalMembershipColumnInfo(OsSchemaInfo schemaInfo) {
            super(7);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmProfessionalMembership");
            this.prof_mem_idColKey = addColumnDetails("prof_mem_id", "prof_mem_id", objectSchemaInfo);
            this.membership_nameColKey = addColumnDetails("membership_name", "membership_name", objectSchemaInfo);
            this.typeColKey = addColumnDetails("type", "type", objectSchemaInfo);
            this.award_nameColKey = addColumnDetails("award_name", "award_name", objectSchemaInfo);
            this.award_idColKey = addColumnDetails("award_id", "award_id", objectSchemaInfo);
            this.award_yearColKey = addColumnDetails("award_year", "award_year", objectSchemaInfo);
            this.presented_atColKey = addColumnDetails("presented_at", "presented_at", objectSchemaInfo);
        }

        RealmProfessionalMembershipColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmProfessionalMembershipColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmProfessionalMembershipColumnInfo src = (RealmProfessionalMembershipColumnInfo) rawSrc;
            final RealmProfessionalMembershipColumnInfo dst = (RealmProfessionalMembershipColumnInfo) rawDst;
            dst.prof_mem_idColKey = src.prof_mem_idColKey;
            dst.membership_nameColKey = src.membership_nameColKey;
            dst.typeColKey = src.typeColKey;
            dst.award_nameColKey = src.award_nameColKey;
            dst.award_idColKey = src.award_idColKey;
            dst.award_yearColKey = src.award_yearColKey;
            dst.presented_atColKey = src.presented_atColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmProfessionalMembershipColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmProfessionalMembership> proxyState;

    com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmProfessionalMembershipColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmProfessionalMembership>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$prof_mem_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.prof_mem_idColKey);
    }

    @Override
    public void realmSet$prof_mem_id(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'prof_mem_id' to null.");
            }
            row.getTable().setLong(columnInfo.prof_mem_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'prof_mem_id' to null.");
        }
        proxyState.getRow$realm().setLong(columnInfo.prof_mem_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$membership_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.membership_nameColKey);
    }

    @Override
    public void realmSet$membership_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.membership_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.membership_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.membership_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.membership_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.typeColKey);
    }

    @Override
    public void realmSet$type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.typeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$award_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.award_nameColKey);
    }

    @Override
    public void realmSet$award_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.award_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.award_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.award_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.award_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$award_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.award_idColKey);
    }

    @Override
    public void realmSet$award_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.award_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.award_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$award_year() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.award_yearColKey);
    }

    @Override
    public void realmSet$award_year(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.award_yearColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.award_yearColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$presented_at() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.presented_atColKey);
    }

    @Override
    public void realmSet$presented_at(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.presented_atColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.presented_atColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.presented_atColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.presented_atColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmProfessionalMembership", 7, 0);
        builder.addPersistedProperty("prof_mem_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("membership_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("award_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("award_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("award_year", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("presented_at", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmProfessionalMembershipColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmProfessionalMembershipColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmProfessionalMembership";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmProfessionalMembership";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmProfessionalMembership createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmProfessionalMembership obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) obj;
        if (json.has("prof_mem_id")) {
            if (json.isNull("prof_mem_id")) {
                objProxy.realmSet$prof_mem_id(null);
            } else {
                objProxy.realmSet$prof_mem_id((int) json.getInt("prof_mem_id"));
            }
        }
        if (json.has("membership_name")) {
            if (json.isNull("membership_name")) {
                objProxy.realmSet$membership_name(null);
            } else {
                objProxy.realmSet$membership_name((String) json.getString("membership_name"));
            }
        }
        if (json.has("type")) {
            if (json.isNull("type")) {
                objProxy.realmSet$type(null);
            } else {
                objProxy.realmSet$type((String) json.getString("type"));
            }
        }
        if (json.has("award_name")) {
            if (json.isNull("award_name")) {
                objProxy.realmSet$award_name(null);
            } else {
                objProxy.realmSet$award_name((String) json.getString("award_name"));
            }
        }
        if (json.has("award_id")) {
            if (json.isNull("award_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'award_id' to null.");
            } else {
                objProxy.realmSet$award_id((int) json.getInt("award_id"));
            }
        }
        if (json.has("award_year")) {
            if (json.isNull("award_year")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'award_year' to null.");
            } else {
                objProxy.realmSet$award_year((long) json.getLong("award_year"));
            }
        }
        if (json.has("presented_at")) {
            if (json.isNull("presented_at")) {
                objProxy.realmSet$presented_at(null);
            } else {
                objProxy.realmSet$presented_at((String) json.getString("presented_at"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmProfessionalMembership createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmProfessionalMembership obj = new com.vam.whitecoats.core.realm.RealmProfessionalMembership();
        final com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("prof_mem_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$prof_mem_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$prof_mem_id(null);
                }
            } else if (name.equals("membership_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$membership_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$membership_name(null);
                }
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$type(null);
                }
            } else if (name.equals("award_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$award_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$award_name(null);
                }
            } else if (name.equals("award_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$award_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'award_id' to null.");
                }
            } else if (name.equals("award_year")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$award_year((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'award_year' to null.");
                }
            } else if (name.equals("presented_at")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$presented_at((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$presented_at(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmProfessionalMembership copyOrUpdate(Realm realm, RealmProfessionalMembershipColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmProfessionalMembership object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmProfessionalMembership) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmProfessionalMembership copy(Realm realm, RealmProfessionalMembershipColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmProfessionalMembership newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmProfessionalMembership) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.prof_mem_idColKey, realmObjectSource.realmGet$prof_mem_id());
        builder.addString(columnInfo.membership_nameColKey, realmObjectSource.realmGet$membership_name());
        builder.addString(columnInfo.typeColKey, realmObjectSource.realmGet$type());
        builder.addString(columnInfo.award_nameColKey, realmObjectSource.realmGet$award_name());
        builder.addInteger(columnInfo.award_idColKey, realmObjectSource.realmGet$award_id());
        builder.addInteger(columnInfo.award_yearColKey, realmObjectSource.realmGet$award_year());
        builder.addString(columnInfo.presented_atColKey, realmObjectSource.realmGet$presented_at());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmProfessionalMembership object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        long tableNativePtr = table.getNativePtr();
        RealmProfessionalMembershipColumnInfo columnInfo = (RealmProfessionalMembershipColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$prof_mem_id = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$prof_mem_id();
        if (realmGet$prof_mem_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.prof_mem_idColKey, colKey, realmGet$prof_mem_id.longValue(), false);
        }
        String realmGet$membership_name = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$membership_name();
        if (realmGet$membership_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.membership_nameColKey, colKey, realmGet$membership_name, false);
        }
        String realmGet$type = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, colKey, realmGet$type, false);
        }
        String realmGet$award_name = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_name();
        if (realmGet$award_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.award_nameColKey, colKey, realmGet$award_name, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.award_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.award_yearColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_year(), false);
        String realmGet$presented_at = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$presented_at();
        if (realmGet$presented_at != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.presented_atColKey, colKey, realmGet$presented_at, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        long tableNativePtr = table.getNativePtr();
        RealmProfessionalMembershipColumnInfo columnInfo = (RealmProfessionalMembershipColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        com.vam.whitecoats.core.realm.RealmProfessionalMembership object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmProfessionalMembership) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$prof_mem_id = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$prof_mem_id();
            if (realmGet$prof_mem_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.prof_mem_idColKey, colKey, realmGet$prof_mem_id.longValue(), false);
            }
            String realmGet$membership_name = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$membership_name();
            if (realmGet$membership_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.membership_nameColKey, colKey, realmGet$membership_name, false);
            }
            String realmGet$type = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, colKey, realmGet$type, false);
            }
            String realmGet$award_name = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_name();
            if (realmGet$award_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.award_nameColKey, colKey, realmGet$award_name, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.award_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.award_yearColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_year(), false);
            String realmGet$presented_at = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$presented_at();
            if (realmGet$presented_at != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.presented_atColKey, colKey, realmGet$presented_at, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmProfessionalMembership object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        long tableNativePtr = table.getNativePtr();
        RealmProfessionalMembershipColumnInfo columnInfo = (RealmProfessionalMembershipColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$prof_mem_id = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$prof_mem_id();
        if (realmGet$prof_mem_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.prof_mem_idColKey, colKey, realmGet$prof_mem_id.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.prof_mem_idColKey, colKey, false);
        }
        String realmGet$membership_name = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$membership_name();
        if (realmGet$membership_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.membership_nameColKey, colKey, realmGet$membership_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.membership_nameColKey, colKey, false);
        }
        String realmGet$type = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, colKey, realmGet$type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.typeColKey, colKey, false);
        }
        String realmGet$award_name = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_name();
        if (realmGet$award_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.award_nameColKey, colKey, realmGet$award_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.award_nameColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.award_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.award_yearColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_year(), false);
        String realmGet$presented_at = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$presented_at();
        if (realmGet$presented_at != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.presented_atColKey, colKey, realmGet$presented_at, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.presented_atColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        long tableNativePtr = table.getNativePtr();
        RealmProfessionalMembershipColumnInfo columnInfo = (RealmProfessionalMembershipColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        com.vam.whitecoats.core.realm.RealmProfessionalMembership object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmProfessionalMembership) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$prof_mem_id = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$prof_mem_id();
            if (realmGet$prof_mem_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.prof_mem_idColKey, colKey, realmGet$prof_mem_id.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.prof_mem_idColKey, colKey, false);
            }
            String realmGet$membership_name = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$membership_name();
            if (realmGet$membership_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.membership_nameColKey, colKey, realmGet$membership_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.membership_nameColKey, colKey, false);
            }
            String realmGet$type = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, colKey, realmGet$type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.typeColKey, colKey, false);
            }
            String realmGet$award_name = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_name();
            if (realmGet$award_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.award_nameColKey, colKey, realmGet$award_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.award_nameColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.award_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.award_yearColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$award_year(), false);
            String realmGet$presented_at = ((com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) object).realmGet$presented_at();
            if (realmGet$presented_at != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.presented_atColKey, colKey, realmGet$presented_at, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.presented_atColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmProfessionalMembership createDetachedCopy(com.vam.whitecoats.core.realm.RealmProfessionalMembership realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmProfessionalMembership unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmProfessionalMembership();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmProfessionalMembership) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmProfessionalMembership) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$prof_mem_id(realmSource.realmGet$prof_mem_id());
        unmanagedCopy.realmSet$membership_name(realmSource.realmGet$membership_name());
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());
        unmanagedCopy.realmSet$award_name(realmSource.realmGet$award_name());
        unmanagedCopy.realmSet$award_id(realmSource.realmGet$award_id());
        unmanagedCopy.realmSet$award_year(realmSource.realmGet$award_year());
        unmanagedCopy.realmSet$presented_at(realmSource.realmGet$presented_at());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmProfessionalMembership = proxy[");
        stringBuilder.append("{prof_mem_id:");
        stringBuilder.append(realmGet$prof_mem_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{membership_name:");
        stringBuilder.append(realmGet$membership_name() != null ? realmGet$membership_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type() != null ? realmGet$type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{award_name:");
        stringBuilder.append(realmGet$award_name() != null ? realmGet$award_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{award_id:");
        stringBuilder.append(realmGet$award_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{award_year:");
        stringBuilder.append(realmGet$award_year());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{presented_at:");
        stringBuilder.append(realmGet$presented_at() != null ? realmGet$presented_at() : "null");
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
        com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy aRealmProfessionalMembership = (com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmProfessionalMembership.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmProfessionalMembership.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmProfessionalMembership.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
