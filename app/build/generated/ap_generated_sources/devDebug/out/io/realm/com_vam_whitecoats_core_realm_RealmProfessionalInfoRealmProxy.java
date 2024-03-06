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
public class com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmProfessionalInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface {

    static final class RealmProfessionalInfoColumnInfo extends ColumnInfo {
        long prof_idColKey;
        long workplaceColKey;
        long designationColKey;
        long locationColKey;
        long availableDaysColKey;
        long workOptionsColKey;
        long start_dateColKey;
        long end_dateColKey;
        long startTimeColKey;
        long endTimeColKey;
        long working_hereColKey;
        long showOncardColKey;

        RealmProfessionalInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(12);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmProfessionalInfo");
            this.prof_idColKey = addColumnDetails("prof_id", "prof_id", objectSchemaInfo);
            this.workplaceColKey = addColumnDetails("workplace", "workplace", objectSchemaInfo);
            this.designationColKey = addColumnDetails("designation", "designation", objectSchemaInfo);
            this.locationColKey = addColumnDetails("location", "location", objectSchemaInfo);
            this.availableDaysColKey = addColumnDetails("availableDays", "availableDays", objectSchemaInfo);
            this.workOptionsColKey = addColumnDetails("workOptions", "workOptions", objectSchemaInfo);
            this.start_dateColKey = addColumnDetails("start_date", "start_date", objectSchemaInfo);
            this.end_dateColKey = addColumnDetails("end_date", "end_date", objectSchemaInfo);
            this.startTimeColKey = addColumnDetails("startTime", "startTime", objectSchemaInfo);
            this.endTimeColKey = addColumnDetails("endTime", "endTime", objectSchemaInfo);
            this.working_hereColKey = addColumnDetails("working_here", "working_here", objectSchemaInfo);
            this.showOncardColKey = addColumnDetails("showOncard", "showOncard", objectSchemaInfo);
        }

        RealmProfessionalInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmProfessionalInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmProfessionalInfoColumnInfo src = (RealmProfessionalInfoColumnInfo) rawSrc;
            final RealmProfessionalInfoColumnInfo dst = (RealmProfessionalInfoColumnInfo) rawDst;
            dst.prof_idColKey = src.prof_idColKey;
            dst.workplaceColKey = src.workplaceColKey;
            dst.designationColKey = src.designationColKey;
            dst.locationColKey = src.locationColKey;
            dst.availableDaysColKey = src.availableDaysColKey;
            dst.workOptionsColKey = src.workOptionsColKey;
            dst.start_dateColKey = src.start_dateColKey;
            dst.end_dateColKey = src.end_dateColKey;
            dst.startTimeColKey = src.startTimeColKey;
            dst.endTimeColKey = src.endTimeColKey;
            dst.working_hereColKey = src.working_hereColKey;
            dst.showOncardColKey = src.showOncardColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmProfessionalInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmProfessionalInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmProfessionalInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmProfessionalInfo>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$prof_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.prof_idColKey);
    }

    @Override
    public void realmSet$prof_id(Integer value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'prof_id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$workplace() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.workplaceColKey);
    }

    @Override
    public void realmSet$workplace(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.workplaceColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.workplaceColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.workplaceColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.workplaceColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$designation() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.designationColKey);
    }

    @Override
    public void realmSet$designation(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.designationColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.designationColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.designationColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.designationColKey, value);
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
    public String realmGet$availableDays() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.availableDaysColKey);
    }

    @Override
    public void realmSet$availableDays(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.availableDaysColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.availableDaysColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.availableDaysColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.availableDaysColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$workOptions() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.workOptionsColKey);
    }

    @Override
    public void realmSet$workOptions(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.workOptionsColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.workOptionsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.workOptionsColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.workOptionsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$start_date() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.start_dateColKey);
    }

    @Override
    public void realmSet$start_date(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.start_dateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.start_dateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$end_date() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.end_dateColKey);
    }

    @Override
    public void realmSet$end_date(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.end_dateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.end_dateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$startTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.startTimeColKey);
    }

    @Override
    public void realmSet$startTime(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.startTimeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.startTimeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$endTime() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.endTimeColKey);
    }

    @Override
    public void realmSet$endTime(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.endTimeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.endTimeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$working_here() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.working_hereColKey);
    }

    @Override
    public void realmSet$working_here(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.working_hereColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.working_hereColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$showOncard() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.showOncardColKey);
    }

    @Override
    public void realmSet$showOncard(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.showOncardColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.showOncardColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmProfessionalInfo", 12, 0);
        builder.addPersistedProperty("prof_id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("workplace", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("designation", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("location", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("availableDays", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("workOptions", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("start_date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("end_date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("startTime", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("endTime", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("working_here", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("showOncard", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmProfessionalInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmProfessionalInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmProfessionalInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmProfessionalInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmProfessionalInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmProfessionalInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
            RealmProfessionalInfoColumnInfo columnInfo = (RealmProfessionalInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
            long pkColumnKey = columnInfo.prof_idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("prof_id")) {
                colKey = table.findFirstLong(pkColumnKey, json.getLong("prof_id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("prof_id")) {
                if (json.isNull("prof_id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class, json.getInt("prof_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'prof_id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) obj;
        if (json.has("workplace")) {
            if (json.isNull("workplace")) {
                objProxy.realmSet$workplace(null);
            } else {
                objProxy.realmSet$workplace((String) json.getString("workplace"));
            }
        }
        if (json.has("designation")) {
            if (json.isNull("designation")) {
                objProxy.realmSet$designation(null);
            } else {
                objProxy.realmSet$designation((String) json.getString("designation"));
            }
        }
        if (json.has("location")) {
            if (json.isNull("location")) {
                objProxy.realmSet$location(null);
            } else {
                objProxy.realmSet$location((String) json.getString("location"));
            }
        }
        if (json.has("availableDays")) {
            if (json.isNull("availableDays")) {
                objProxy.realmSet$availableDays(null);
            } else {
                objProxy.realmSet$availableDays((String) json.getString("availableDays"));
            }
        }
        if (json.has("workOptions")) {
            if (json.isNull("workOptions")) {
                objProxy.realmSet$workOptions(null);
            } else {
                objProxy.realmSet$workOptions((String) json.getString("workOptions"));
            }
        }
        if (json.has("start_date")) {
            if (json.isNull("start_date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'start_date' to null.");
            } else {
                objProxy.realmSet$start_date((long) json.getLong("start_date"));
            }
        }
        if (json.has("end_date")) {
            if (json.isNull("end_date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'end_date' to null.");
            } else {
                objProxy.realmSet$end_date((long) json.getLong("end_date"));
            }
        }
        if (json.has("startTime")) {
            if (json.isNull("startTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'startTime' to null.");
            } else {
                objProxy.realmSet$startTime((long) json.getLong("startTime"));
            }
        }
        if (json.has("endTime")) {
            if (json.isNull("endTime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'endTime' to null.");
            } else {
                objProxy.realmSet$endTime((long) json.getLong("endTime"));
            }
        }
        if (json.has("working_here")) {
            if (json.isNull("working_here")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'working_here' to null.");
            } else {
                objProxy.realmSet$working_here((boolean) json.getBoolean("working_here"));
            }
        }
        if (json.has("showOncard")) {
            if (json.isNull("showOncard")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'showOncard' to null.");
            } else {
                objProxy.realmSet$showOncard((boolean) json.getBoolean("showOncard"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmProfessionalInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmProfessionalInfo obj = new com.vam.whitecoats.core.realm.RealmProfessionalInfo();
        final com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("prof_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$prof_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$prof_id(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("workplace")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$workplace((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$workplace(null);
                }
            } else if (name.equals("designation")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$designation((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$designation(null);
                }
            } else if (name.equals("location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$location(null);
                }
            } else if (name.equals("availableDays")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$availableDays((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$availableDays(null);
                }
            } else if (name.equals("workOptions")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$workOptions((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$workOptions(null);
                }
            } else if (name.equals("start_date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$start_date((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'start_date' to null.");
                }
            } else if (name.equals("end_date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$end_date((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'end_date' to null.");
                }
            } else if (name.equals("startTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$startTime((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'startTime' to null.");
                }
            } else if (name.equals("endTime")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$endTime((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'endTime' to null.");
                }
            } else if (name.equals("working_here")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$working_here((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'working_here' to null.");
                }
            } else if (name.equals("showOncard")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$showOncard((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'showOncard' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'prof_id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmProfessionalInfo copyOrUpdate(Realm realm, RealmProfessionalInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmProfessionalInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmProfessionalInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmProfessionalInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
            long pkColumnKey = columnInfo.prof_idColKey;
            long colKey = table.findFirstLong(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmProfessionalInfo copy(Realm realm, RealmProfessionalInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmProfessionalInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmProfessionalInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.prof_idColKey, realmObjectSource.realmGet$prof_id());
        builder.addString(columnInfo.workplaceColKey, realmObjectSource.realmGet$workplace());
        builder.addString(columnInfo.designationColKey, realmObjectSource.realmGet$designation());
        builder.addString(columnInfo.locationColKey, realmObjectSource.realmGet$location());
        builder.addString(columnInfo.availableDaysColKey, realmObjectSource.realmGet$availableDays());
        builder.addString(columnInfo.workOptionsColKey, realmObjectSource.realmGet$workOptions());
        builder.addInteger(columnInfo.start_dateColKey, realmObjectSource.realmGet$start_date());
        builder.addInteger(columnInfo.end_dateColKey, realmObjectSource.realmGet$end_date());
        builder.addInteger(columnInfo.startTimeColKey, realmObjectSource.realmGet$startTime());
        builder.addInteger(columnInfo.endTimeColKey, realmObjectSource.realmGet$endTime());
        builder.addBoolean(columnInfo.working_hereColKey, realmObjectSource.realmGet$working_here());
        builder.addBoolean(columnInfo.showOncardColKey, realmObjectSource.realmGet$showOncard());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmProfessionalInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmProfessionalInfoColumnInfo columnInfo = (RealmProfessionalInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        long pkColumnKey = columnInfo.prof_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$workplace();
        if (realmGet$workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
        }
        String realmGet$designation = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$designation();
        if (realmGet$designation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.designationColKey, colKey, realmGet$designation, false);
        }
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        }
        String realmGet$availableDays = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$availableDays();
        if (realmGet$availableDays != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.availableDaysColKey, colKey, realmGet$availableDays, false);
        }
        String realmGet$workOptions = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$workOptions();
        if (realmGet$workOptions != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.workOptionsColKey, colKey, realmGet$workOptions, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.start_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$start_date(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.end_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$end_date(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.startTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$startTime(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.endTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$endTime(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.working_hereColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$working_here(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.showOncardColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$showOncard(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmProfessionalInfoColumnInfo columnInfo = (RealmProfessionalInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        long pkColumnKey = columnInfo.prof_idColKey;
        com.vam.whitecoats.core.realm.RealmProfessionalInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmProfessionalInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$workplace();
            if (realmGet$workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
            }
            String realmGet$designation = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$designation();
            if (realmGet$designation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.designationColKey, colKey, realmGet$designation, false);
            }
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            }
            String realmGet$availableDays = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$availableDays();
            if (realmGet$availableDays != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.availableDaysColKey, colKey, realmGet$availableDays, false);
            }
            String realmGet$workOptions = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$workOptions();
            if (realmGet$workOptions != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.workOptionsColKey, colKey, realmGet$workOptions, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.start_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$start_date(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.end_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$end_date(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.startTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$startTime(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.endTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$endTime(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.working_hereColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$working_here(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.showOncardColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$showOncard(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmProfessionalInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmProfessionalInfoColumnInfo columnInfo = (RealmProfessionalInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        long pkColumnKey = columnInfo.prof_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
        }
        cache.put(object, colKey);
        String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$workplace();
        if (realmGet$workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.workplaceColKey, colKey, false);
        }
        String realmGet$designation = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$designation();
        if (realmGet$designation != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.designationColKey, colKey, realmGet$designation, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.designationColKey, colKey, false);
        }
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
        }
        String realmGet$availableDays = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$availableDays();
        if (realmGet$availableDays != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.availableDaysColKey, colKey, realmGet$availableDays, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.availableDaysColKey, colKey, false);
        }
        String realmGet$workOptions = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$workOptions();
        if (realmGet$workOptions != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.workOptionsColKey, colKey, realmGet$workOptions, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.workOptionsColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.start_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$start_date(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.end_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$end_date(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.startTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$startTime(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.endTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$endTime(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.working_hereColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$working_here(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.showOncardColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$showOncard(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmProfessionalInfoColumnInfo columnInfo = (RealmProfessionalInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        long pkColumnKey = columnInfo.prof_idColKey;
        com.vam.whitecoats.core.realm.RealmProfessionalInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmProfessionalInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$prof_id());
            }
            cache.put(object, colKey);
            String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$workplace();
            if (realmGet$workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.workplaceColKey, colKey, false);
            }
            String realmGet$designation = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$designation();
            if (realmGet$designation != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.designationColKey, colKey, realmGet$designation, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.designationColKey, colKey, false);
            }
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
            }
            String realmGet$availableDays = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$availableDays();
            if (realmGet$availableDays != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.availableDaysColKey, colKey, realmGet$availableDays, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.availableDaysColKey, colKey, false);
            }
            String realmGet$workOptions = ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$workOptions();
            if (realmGet$workOptions != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.workOptionsColKey, colKey, realmGet$workOptions, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.workOptionsColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.start_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$start_date(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.end_dateColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$end_date(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.startTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$startTime(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.endTimeColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$endTime(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.working_hereColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$working_here(), false);
            Table.nativeSetBoolean(tableNativePtr, columnInfo.showOncardColKey, colKey, ((com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) object).realmGet$showOncard(), false);
        }
    }

    public static com.vam.whitecoats.core.realm.RealmProfessionalInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmProfessionalInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmProfessionalInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmProfessionalInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmProfessionalInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmProfessionalInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$prof_id(realmSource.realmGet$prof_id());
        unmanagedCopy.realmSet$workplace(realmSource.realmGet$workplace());
        unmanagedCopy.realmSet$designation(realmSource.realmGet$designation());
        unmanagedCopy.realmSet$location(realmSource.realmGet$location());
        unmanagedCopy.realmSet$availableDays(realmSource.realmGet$availableDays());
        unmanagedCopy.realmSet$workOptions(realmSource.realmGet$workOptions());
        unmanagedCopy.realmSet$start_date(realmSource.realmGet$start_date());
        unmanagedCopy.realmSet$end_date(realmSource.realmGet$end_date());
        unmanagedCopy.realmSet$startTime(realmSource.realmGet$startTime());
        unmanagedCopy.realmSet$endTime(realmSource.realmGet$endTime());
        unmanagedCopy.realmSet$working_here(realmSource.realmGet$working_here());
        unmanagedCopy.realmSet$showOncard(realmSource.realmGet$showOncard());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmProfessionalInfo update(Realm realm, RealmProfessionalInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmProfessionalInfo realmObject, com.vam.whitecoats.core.realm.RealmProfessionalInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addInteger(columnInfo.prof_idColKey, realmObjectSource.realmGet$prof_id());
        builder.addString(columnInfo.workplaceColKey, realmObjectSource.realmGet$workplace());
        builder.addString(columnInfo.designationColKey, realmObjectSource.realmGet$designation());
        builder.addString(columnInfo.locationColKey, realmObjectSource.realmGet$location());
        builder.addString(columnInfo.availableDaysColKey, realmObjectSource.realmGet$availableDays());
        builder.addString(columnInfo.workOptionsColKey, realmObjectSource.realmGet$workOptions());
        builder.addInteger(columnInfo.start_dateColKey, realmObjectSource.realmGet$start_date());
        builder.addInteger(columnInfo.end_dateColKey, realmObjectSource.realmGet$end_date());
        builder.addInteger(columnInfo.startTimeColKey, realmObjectSource.realmGet$startTime());
        builder.addInteger(columnInfo.endTimeColKey, realmObjectSource.realmGet$endTime());
        builder.addBoolean(columnInfo.working_hereColKey, realmObjectSource.realmGet$working_here());
        builder.addBoolean(columnInfo.showOncardColKey, realmObjectSource.realmGet$showOncard());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmProfessionalInfo = proxy[");
        stringBuilder.append("{prof_id:");
        stringBuilder.append(realmGet$prof_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{workplace:");
        stringBuilder.append(realmGet$workplace() != null ? realmGet$workplace() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{designation:");
        stringBuilder.append(realmGet$designation() != null ? realmGet$designation() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{location:");
        stringBuilder.append(realmGet$location() != null ? realmGet$location() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{availableDays:");
        stringBuilder.append(realmGet$availableDays() != null ? realmGet$availableDays() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{workOptions:");
        stringBuilder.append(realmGet$workOptions() != null ? realmGet$workOptions() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{start_date:");
        stringBuilder.append(realmGet$start_date());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{end_date:");
        stringBuilder.append(realmGet$end_date());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{startTime:");
        stringBuilder.append(realmGet$startTime());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{endTime:");
        stringBuilder.append(realmGet$endTime());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{working_here:");
        stringBuilder.append(realmGet$working_here());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{showOncard:");
        stringBuilder.append(realmGet$showOncard());
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
        com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy aRealmProfessionalInfo = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmProfessionalInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmProfessionalInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmProfessionalInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
