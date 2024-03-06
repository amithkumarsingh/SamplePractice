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
public class com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface {

    static final class RealmCaseRoomMemberInfoColumnInfo extends ColumnInfo {
        long caseroomidColKey;
        long mem_idColKey;
        long inviteresponseColKey;
        long isadminColKey;
        long createddateColKey;
        long modifieddateColKey;

        RealmCaseRoomMemberInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(6);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmCaseRoomMemberInfo");
            this.caseroomidColKey = addColumnDetails("caseroomid", "caseroomid", objectSchemaInfo);
            this.mem_idColKey = addColumnDetails("mem_id", "mem_id", objectSchemaInfo);
            this.inviteresponseColKey = addColumnDetails("inviteresponse", "inviteresponse", objectSchemaInfo);
            this.isadminColKey = addColumnDetails("isadmin", "isadmin", objectSchemaInfo);
            this.createddateColKey = addColumnDetails("createddate", "createddate", objectSchemaInfo);
            this.modifieddateColKey = addColumnDetails("modifieddate", "modifieddate", objectSchemaInfo);
        }

        RealmCaseRoomMemberInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmCaseRoomMemberInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmCaseRoomMemberInfoColumnInfo src = (RealmCaseRoomMemberInfoColumnInfo) rawSrc;
            final RealmCaseRoomMemberInfoColumnInfo dst = (RealmCaseRoomMemberInfoColumnInfo) rawDst;
            dst.caseroomidColKey = src.caseroomidColKey;
            dst.mem_idColKey = src.mem_idColKey;
            dst.inviteresponseColKey = src.inviteresponseColKey;
            dst.isadminColKey = src.isadminColKey;
            dst.createddateColKey = src.createddateColKey;
            dst.modifieddateColKey = src.modifieddateColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmCaseRoomMemberInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmCaseRoomMemberInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caseroomid() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.caseroomidColKey);
    }

    @Override
    public void realmSet$caseroomid(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'caseroomid' to null.");
            }
            row.getTable().setString(columnInfo.caseroomidColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'caseroomid' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.caseroomidColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$mem_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.mem_idColKey);
    }

    @Override
    public void realmSet$mem_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.mem_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.mem_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$inviteresponse() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.inviteresponseColKey);
    }

    @Override
    public void realmSet$inviteresponse(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.inviteresponseColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.inviteresponseColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$isadmin() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.isadminColKey);
    }

    @Override
    public void realmSet$isadmin(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.isadminColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.isadminColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$createddate() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.createddateColKey);
    }

    @Override
    public void realmSet$createddate(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.createddateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.createddateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$modifieddate() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.modifieddateColKey);
    }

    @Override
    public void realmSet$modifieddate(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.modifieddateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.modifieddateColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmCaseRoomMemberInfo", 6, 0);
        builder.addPersistedProperty("caseroomid", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("mem_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("inviteresponse", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("isadmin", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("createddate", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("modifieddate", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmCaseRoomMemberInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmCaseRoomMemberInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmCaseRoomMemberInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmCaseRoomMemberInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) obj;
        if (json.has("caseroomid")) {
            if (json.isNull("caseroomid")) {
                objProxy.realmSet$caseroomid(null);
            } else {
                objProxy.realmSet$caseroomid((String) json.getString("caseroomid"));
            }
        }
        if (json.has("mem_id")) {
            if (json.isNull("mem_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'mem_id' to null.");
            } else {
                objProxy.realmSet$mem_id((int) json.getInt("mem_id"));
            }
        }
        if (json.has("inviteresponse")) {
            if (json.isNull("inviteresponse")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'inviteresponse' to null.");
            } else {
                objProxy.realmSet$inviteresponse((int) json.getInt("inviteresponse"));
            }
        }
        if (json.has("isadmin")) {
            if (json.isNull("isadmin")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'isadmin' to null.");
            } else {
                objProxy.realmSet$isadmin((boolean) json.getBoolean("isadmin"));
            }
        }
        if (json.has("createddate")) {
            if (json.isNull("createddate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'createddate' to null.");
            } else {
                objProxy.realmSet$createddate((long) json.getLong("createddate"));
            }
        }
        if (json.has("modifieddate")) {
            if (json.isNull("modifieddate")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'modifieddate' to null.");
            } else {
                objProxy.realmSet$modifieddate((long) json.getLong("modifieddate"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo obj = new com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo();
        final com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("caseroomid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroomid((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroomid(null);
                }
            } else if (name.equals("mem_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$mem_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'mem_id' to null.");
                }
            } else if (name.equals("inviteresponse")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$inviteresponse((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'inviteresponse' to null.");
                }
            } else if (name.equals("isadmin")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$isadmin((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'isadmin' to null.");
                }
            } else if (name.equals("createddate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$createddate((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'createddate' to null.");
                }
            } else if (name.equals("modifieddate")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$modifieddate((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'modifieddate' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo copyOrUpdate(Realm realm, RealmCaseRoomMemberInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo copy(Realm realm, RealmCaseRoomMemberInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.caseroomidColKey, realmObjectSource.realmGet$caseroomid());
        builder.addInteger(columnInfo.mem_idColKey, realmObjectSource.realmGet$mem_id());
        builder.addInteger(columnInfo.inviteresponseColKey, realmObjectSource.realmGet$inviteresponse());
        builder.addBoolean(columnInfo.isadminColKey, realmObjectSource.realmGet$isadmin());
        builder.addInteger(columnInfo.createddateColKey, realmObjectSource.realmGet$createddate());
        builder.addInteger(columnInfo.modifieddateColKey, realmObjectSource.realmGet$modifieddate());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomMemberInfoColumnInfo columnInfo = (RealmCaseRoomMemberInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$caseroomid = ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$caseroomid();
        if (realmGet$caseroomid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroomidColKey, colKey, realmGet$caseroomid, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.mem_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$mem_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.inviteresponseColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$inviteresponse(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isadminColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$isadmin(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.createddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$createddate(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.modifieddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$modifieddate(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomMemberInfoColumnInfo columnInfo = (RealmCaseRoomMemberInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$caseroomid = ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$caseroomid();
            if (realmGet$caseroomid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroomidColKey, colKey, realmGet$caseroomid, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.mem_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$mem_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.inviteresponseColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$inviteresponse(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isadminColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$isadmin(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.createddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$createddate(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.modifieddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$modifieddate(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomMemberInfoColumnInfo columnInfo = (RealmCaseRoomMemberInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$caseroomid = ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$caseroomid();
        if (realmGet$caseroomid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroomidColKey, colKey, realmGet$caseroomid, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.caseroomidColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.mem_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$mem_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.inviteresponseColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$inviteresponse(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.isadminColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$isadmin(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.createddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$createddate(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.modifieddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$modifieddate(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomMemberInfoColumnInfo columnInfo = (RealmCaseRoomMemberInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$caseroomid = ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$caseroomid();
            if (realmGet$caseroomid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroomidColKey, colKey, realmGet$caseroomid, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.caseroomidColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.mem_idColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$mem_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.inviteresponseColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$inviteresponse(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.isadminColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$isadmin(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.createddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$createddate(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.modifieddateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) object).realmGet$modifieddate(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$caseroomid(realmSource.realmGet$caseroomid());
        unmanagedCopy.realmSet$mem_id(realmSource.realmGet$mem_id());
        unmanagedCopy.realmSet$inviteresponse(realmSource.realmGet$inviteresponse());
        unmanagedCopy.realmSet$isadmin(realmSource.realmGet$isadmin());
        unmanagedCopy.realmSet$createddate(realmSource.realmGet$createddate());
        unmanagedCopy.realmSet$modifieddate(realmSource.realmGet$modifieddate());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmCaseRoomMemberInfo = proxy[");
        stringBuilder.append("{caseroomid:");
        stringBuilder.append(realmGet$caseroomid());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{mem_id:");
        stringBuilder.append(realmGet$mem_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{inviteresponse:");
        stringBuilder.append(realmGet$inviteresponse());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{isadmin:");
        stringBuilder.append(realmGet$isadmin());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{createddate:");
        stringBuilder.append(realmGet$createddate());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{modifieddate:");
        stringBuilder.append(realmGet$modifieddate());
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
        com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy aRealmCaseRoomMemberInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmCaseRoomMemberInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmCaseRoomMemberInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmCaseRoomMemberInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
