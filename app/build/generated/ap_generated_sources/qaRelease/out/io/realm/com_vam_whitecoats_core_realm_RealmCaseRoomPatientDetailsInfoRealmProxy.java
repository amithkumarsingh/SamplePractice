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
public class com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface {

    static final class RealmCaseRoomPatientDetailsInfoColumnInfo extends ColumnInfo {
        long caseroom_summary_idColKey;
        long patgenderColKey;
        long patageColKey;
        long symptomsColKey;
        long historyColKey;
        long vitals_anthropometryColKey;
        long general_examinationColKey;
        long systemic_examinationColKey;

        RealmCaseRoomPatientDetailsInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(8);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmCaseRoomPatientDetailsInfo");
            this.caseroom_summary_idColKey = addColumnDetails("caseroom_summary_id", "caseroom_summary_id", objectSchemaInfo);
            this.patgenderColKey = addColumnDetails("patgender", "patgender", objectSchemaInfo);
            this.patageColKey = addColumnDetails("patage", "patage", objectSchemaInfo);
            this.symptomsColKey = addColumnDetails("symptoms", "symptoms", objectSchemaInfo);
            this.historyColKey = addColumnDetails("history", "history", objectSchemaInfo);
            this.vitals_anthropometryColKey = addColumnDetails("vitals_anthropometry", "vitals_anthropometry", objectSchemaInfo);
            this.general_examinationColKey = addColumnDetails("general_examination", "general_examination", objectSchemaInfo);
            this.systemic_examinationColKey = addColumnDetails("systemic_examination", "systemic_examination", objectSchemaInfo);
        }

        RealmCaseRoomPatientDetailsInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmCaseRoomPatientDetailsInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmCaseRoomPatientDetailsInfoColumnInfo src = (RealmCaseRoomPatientDetailsInfoColumnInfo) rawSrc;
            final RealmCaseRoomPatientDetailsInfoColumnInfo dst = (RealmCaseRoomPatientDetailsInfoColumnInfo) rawDst;
            dst.caseroom_summary_idColKey = src.caseroom_summary_idColKey;
            dst.patgenderColKey = src.patgenderColKey;
            dst.patageColKey = src.patageColKey;
            dst.symptomsColKey = src.symptomsColKey;
            dst.historyColKey = src.historyColKey;
            dst.vitals_anthropometryColKey = src.vitals_anthropometryColKey;
            dst.general_examinationColKey = src.general_examinationColKey;
            dst.systemic_examinationColKey = src.systemic_examinationColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmCaseRoomPatientDetailsInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmCaseRoomPatientDetailsInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$caseroom_summary_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.caseroom_summary_idColKey);
    }

    @Override
    public void realmSet$caseroom_summary_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'caseroom_summary_id' to null.");
            }
            row.getTable().setString(columnInfo.caseroom_summary_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'caseroom_summary_id' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.caseroom_summary_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$patgender() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.patgenderColKey);
    }

    @Override
    public void realmSet$patgender(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.patgenderColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.patgenderColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.patgenderColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.patgenderColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$patage() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.patageColKey);
    }

    @Override
    public void realmSet$patage(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.patageColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.patageColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.patageColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.patageColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$symptoms() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.symptomsColKey);
    }

    @Override
    public void realmSet$symptoms(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.symptomsColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.symptomsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.symptomsColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.symptomsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$history() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.historyColKey);
    }

    @Override
    public void realmSet$history(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.historyColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.historyColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.historyColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.historyColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$vitals_anthropometry() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.vitals_anthropometryColKey);
    }

    @Override
    public void realmSet$vitals_anthropometry(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.vitals_anthropometryColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.vitals_anthropometryColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.vitals_anthropometryColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.vitals_anthropometryColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$general_examination() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.general_examinationColKey);
    }

    @Override
    public void realmSet$general_examination(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.general_examinationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.general_examinationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.general_examinationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.general_examinationColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$systemic_examination() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.systemic_examinationColKey);
    }

    @Override
    public void realmSet$systemic_examination(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.systemic_examinationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.systemic_examinationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.systemic_examinationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.systemic_examinationColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmCaseRoomPatientDetailsInfo", 8, 0);
        builder.addPersistedProperty("caseroom_summary_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("patgender", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("patage", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("symptoms", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("history", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("vitals_anthropometry", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("general_examination", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("systemic_examination", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmCaseRoomPatientDetailsInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmCaseRoomPatientDetailsInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmCaseRoomPatientDetailsInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmCaseRoomPatientDetailsInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) obj;
        if (json.has("caseroom_summary_id")) {
            if (json.isNull("caseroom_summary_id")) {
                objProxy.realmSet$caseroom_summary_id(null);
            } else {
                objProxy.realmSet$caseroom_summary_id((String) json.getString("caseroom_summary_id"));
            }
        }
        if (json.has("patgender")) {
            if (json.isNull("patgender")) {
                objProxy.realmSet$patgender(null);
            } else {
                objProxy.realmSet$patgender((String) json.getString("patgender"));
            }
        }
        if (json.has("patage")) {
            if (json.isNull("patage")) {
                objProxy.realmSet$patage(null);
            } else {
                objProxy.realmSet$patage((String) json.getString("patage"));
            }
        }
        if (json.has("symptoms")) {
            if (json.isNull("symptoms")) {
                objProxy.realmSet$symptoms(null);
            } else {
                objProxy.realmSet$symptoms((String) json.getString("symptoms"));
            }
        }
        if (json.has("history")) {
            if (json.isNull("history")) {
                objProxy.realmSet$history(null);
            } else {
                objProxy.realmSet$history((String) json.getString("history"));
            }
        }
        if (json.has("vitals_anthropometry")) {
            if (json.isNull("vitals_anthropometry")) {
                objProxy.realmSet$vitals_anthropometry(null);
            } else {
                objProxy.realmSet$vitals_anthropometry((String) json.getString("vitals_anthropometry"));
            }
        }
        if (json.has("general_examination")) {
            if (json.isNull("general_examination")) {
                objProxy.realmSet$general_examination(null);
            } else {
                objProxy.realmSet$general_examination((String) json.getString("general_examination"));
            }
        }
        if (json.has("systemic_examination")) {
            if (json.isNull("systemic_examination")) {
                objProxy.realmSet$systemic_examination(null);
            } else {
                objProxy.realmSet$systemic_examination((String) json.getString("systemic_examination"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo obj = new com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo();
        final com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("caseroom_summary_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$caseroom_summary_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$caseroom_summary_id(null);
                }
            } else if (name.equals("patgender")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$patgender((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$patgender(null);
                }
            } else if (name.equals("patage")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$patage((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$patage(null);
                }
            } else if (name.equals("symptoms")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$symptoms((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$symptoms(null);
                }
            } else if (name.equals("history")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$history((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$history(null);
                }
            } else if (name.equals("vitals_anthropometry")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$vitals_anthropometry((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$vitals_anthropometry(null);
                }
            } else if (name.equals("general_examination")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$general_examination((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$general_examination(null);
                }
            } else if (name.equals("systemic_examination")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$systemic_examination((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$systemic_examination(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo copyOrUpdate(Realm realm, RealmCaseRoomPatientDetailsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo copy(Realm realm, RealmCaseRoomPatientDetailsInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.caseroom_summary_idColKey, realmObjectSource.realmGet$caseroom_summary_id());
        builder.addString(columnInfo.patgenderColKey, realmObjectSource.realmGet$patgender());
        builder.addString(columnInfo.patageColKey, realmObjectSource.realmGet$patage());
        builder.addString(columnInfo.symptomsColKey, realmObjectSource.realmGet$symptoms());
        builder.addString(columnInfo.historyColKey, realmObjectSource.realmGet$history());
        builder.addString(columnInfo.vitals_anthropometryColKey, realmObjectSource.realmGet$vitals_anthropometry());
        builder.addString(columnInfo.general_examinationColKey, realmObjectSource.realmGet$general_examination());
        builder.addString(columnInfo.systemic_examinationColKey, realmObjectSource.realmGet$systemic_examination());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomPatientDetailsInfoColumnInfo columnInfo = (RealmCaseRoomPatientDetailsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
        if (realmGet$caseroom_summary_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
        }
        String realmGet$patgender = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$patgender();
        if (realmGet$patgender != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.patgenderColKey, colKey, realmGet$patgender, false);
        }
        String realmGet$patage = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$patage();
        if (realmGet$patage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.patageColKey, colKey, realmGet$patage, false);
        }
        String realmGet$symptoms = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$symptoms();
        if (realmGet$symptoms != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.symptomsColKey, colKey, realmGet$symptoms, false);
        }
        String realmGet$history = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$history();
        if (realmGet$history != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.historyColKey, colKey, realmGet$history, false);
        }
        String realmGet$vitals_anthropometry = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$vitals_anthropometry();
        if (realmGet$vitals_anthropometry != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.vitals_anthropometryColKey, colKey, realmGet$vitals_anthropometry, false);
        }
        String realmGet$general_examination = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$general_examination();
        if (realmGet$general_examination != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.general_examinationColKey, colKey, realmGet$general_examination, false);
        }
        String realmGet$systemic_examination = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$systemic_examination();
        if (realmGet$systemic_examination != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.systemic_examinationColKey, colKey, realmGet$systemic_examination, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomPatientDetailsInfoColumnInfo columnInfo = (RealmCaseRoomPatientDetailsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
            if (realmGet$caseroom_summary_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
            }
            String realmGet$patgender = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$patgender();
            if (realmGet$patgender != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.patgenderColKey, colKey, realmGet$patgender, false);
            }
            String realmGet$patage = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$patage();
            if (realmGet$patage != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.patageColKey, colKey, realmGet$patage, false);
            }
            String realmGet$symptoms = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$symptoms();
            if (realmGet$symptoms != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.symptomsColKey, colKey, realmGet$symptoms, false);
            }
            String realmGet$history = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$history();
            if (realmGet$history != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.historyColKey, colKey, realmGet$history, false);
            }
            String realmGet$vitals_anthropometry = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$vitals_anthropometry();
            if (realmGet$vitals_anthropometry != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.vitals_anthropometryColKey, colKey, realmGet$vitals_anthropometry, false);
            }
            String realmGet$general_examination = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$general_examination();
            if (realmGet$general_examination != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.general_examinationColKey, colKey, realmGet$general_examination, false);
            }
            String realmGet$systemic_examination = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$systemic_examination();
            if (realmGet$systemic_examination != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.systemic_examinationColKey, colKey, realmGet$systemic_examination, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomPatientDetailsInfoColumnInfo columnInfo = (RealmCaseRoomPatientDetailsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
        if (realmGet$caseroom_summary_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, false);
        }
        String realmGet$patgender = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$patgender();
        if (realmGet$patgender != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.patgenderColKey, colKey, realmGet$patgender, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.patgenderColKey, colKey, false);
        }
        String realmGet$patage = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$patage();
        if (realmGet$patage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.patageColKey, colKey, realmGet$patage, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.patageColKey, colKey, false);
        }
        String realmGet$symptoms = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$symptoms();
        if (realmGet$symptoms != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.symptomsColKey, colKey, realmGet$symptoms, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.symptomsColKey, colKey, false);
        }
        String realmGet$history = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$history();
        if (realmGet$history != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.historyColKey, colKey, realmGet$history, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.historyColKey, colKey, false);
        }
        String realmGet$vitals_anthropometry = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$vitals_anthropometry();
        if (realmGet$vitals_anthropometry != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.vitals_anthropometryColKey, colKey, realmGet$vitals_anthropometry, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.vitals_anthropometryColKey, colKey, false);
        }
        String realmGet$general_examination = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$general_examination();
        if (realmGet$general_examination != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.general_examinationColKey, colKey, realmGet$general_examination, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.general_examinationColKey, colKey, false);
        }
        String realmGet$systemic_examination = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$systemic_examination();
        if (realmGet$systemic_examination != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.systemic_examinationColKey, colKey, realmGet$systemic_examination, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.systemic_examinationColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCaseRoomPatientDetailsInfoColumnInfo columnInfo = (RealmCaseRoomPatientDetailsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$caseroom_summary_id = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$caseroom_summary_id();
            if (realmGet$caseroom_summary_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, realmGet$caseroom_summary_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.caseroom_summary_idColKey, colKey, false);
            }
            String realmGet$patgender = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$patgender();
            if (realmGet$patgender != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.patgenderColKey, colKey, realmGet$patgender, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.patgenderColKey, colKey, false);
            }
            String realmGet$patage = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$patage();
            if (realmGet$patage != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.patageColKey, colKey, realmGet$patage, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.patageColKey, colKey, false);
            }
            String realmGet$symptoms = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$symptoms();
            if (realmGet$symptoms != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.symptomsColKey, colKey, realmGet$symptoms, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.symptomsColKey, colKey, false);
            }
            String realmGet$history = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$history();
            if (realmGet$history != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.historyColKey, colKey, realmGet$history, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.historyColKey, colKey, false);
            }
            String realmGet$vitals_anthropometry = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$vitals_anthropometry();
            if (realmGet$vitals_anthropometry != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.vitals_anthropometryColKey, colKey, realmGet$vitals_anthropometry, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.vitals_anthropometryColKey, colKey, false);
            }
            String realmGet$general_examination = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$general_examination();
            if (realmGet$general_examination != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.general_examinationColKey, colKey, realmGet$general_examination, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.general_examinationColKey, colKey, false);
            }
            String realmGet$systemic_examination = ((com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) object).realmGet$systemic_examination();
            if (realmGet$systemic_examination != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.systemic_examinationColKey, colKey, realmGet$systemic_examination, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.systemic_examinationColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$caseroom_summary_id(realmSource.realmGet$caseroom_summary_id());
        unmanagedCopy.realmSet$patgender(realmSource.realmGet$patgender());
        unmanagedCopy.realmSet$patage(realmSource.realmGet$patage());
        unmanagedCopy.realmSet$symptoms(realmSource.realmGet$symptoms());
        unmanagedCopy.realmSet$history(realmSource.realmGet$history());
        unmanagedCopy.realmSet$vitals_anthropometry(realmSource.realmGet$vitals_anthropometry());
        unmanagedCopy.realmSet$general_examination(realmSource.realmGet$general_examination());
        unmanagedCopy.realmSet$systemic_examination(realmSource.realmGet$systemic_examination());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmCaseRoomPatientDetailsInfo = proxy[");
        stringBuilder.append("{caseroom_summary_id:");
        stringBuilder.append(realmGet$caseroom_summary_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{patgender:");
        stringBuilder.append(realmGet$patgender() != null ? realmGet$patgender() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{patage:");
        stringBuilder.append(realmGet$patage() != null ? realmGet$patage() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{symptoms:");
        stringBuilder.append(realmGet$symptoms() != null ? realmGet$symptoms() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{history:");
        stringBuilder.append(realmGet$history() != null ? realmGet$history() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{vitals_anthropometry:");
        stringBuilder.append(realmGet$vitals_anthropometry() != null ? realmGet$vitals_anthropometry() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{general_examination:");
        stringBuilder.append(realmGet$general_examination() != null ? realmGet$general_examination() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{systemic_examination:");
        stringBuilder.append(realmGet$systemic_examination() != null ? realmGet$systemic_examination() : "null");
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
        com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy aRealmCaseRoomPatientDetailsInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmCaseRoomPatientDetailsInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmCaseRoomPatientDetailsInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmCaseRoomPatientDetailsInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
