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
public class com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy extends com.vam.whitecoats.core.realm.RealmCardInfo
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface {

    static final class RealmCardInfoColumnInfo extends ColumnInfo {
        long qb_user_idColKey;
        long full_nameColKey;
        long spltyColKey;
        long notify_picColKey;
        long phoneColKey;
        long locationColKey;
        long emailColKey;
        long workplaceColKey;
        long degreesColKey;

        RealmCardInfoColumnInfo(OsSchemaInfo schemaInfo) {
            super(9);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmCardInfo");
            this.qb_user_idColKey = addColumnDetails("qb_user_id", "qb_user_id", objectSchemaInfo);
            this.full_nameColKey = addColumnDetails("full_name", "full_name", objectSchemaInfo);
            this.spltyColKey = addColumnDetails("splty", "splty", objectSchemaInfo);
            this.notify_picColKey = addColumnDetails("notify_pic", "notify_pic", objectSchemaInfo);
            this.phoneColKey = addColumnDetails("phone", "phone", objectSchemaInfo);
            this.locationColKey = addColumnDetails("location", "location", objectSchemaInfo);
            this.emailColKey = addColumnDetails("email", "email", objectSchemaInfo);
            this.workplaceColKey = addColumnDetails("workplace", "workplace", objectSchemaInfo);
            this.degreesColKey = addColumnDetails("degrees", "degrees", objectSchemaInfo);
        }

        RealmCardInfoColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmCardInfoColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmCardInfoColumnInfo src = (RealmCardInfoColumnInfo) rawSrc;
            final RealmCardInfoColumnInfo dst = (RealmCardInfoColumnInfo) rawDst;
            dst.qb_user_idColKey = src.qb_user_idColKey;
            dst.full_nameColKey = src.full_nameColKey;
            dst.spltyColKey = src.spltyColKey;
            dst.notify_picColKey = src.notify_picColKey;
            dst.phoneColKey = src.phoneColKey;
            dst.locationColKey = src.locationColKey;
            dst.emailColKey = src.emailColKey;
            dst.workplaceColKey = src.workplaceColKey;
            dst.degreesColKey = src.degreesColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmCardInfoColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmCardInfo> proxyState;

    com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmCardInfoColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmCardInfo>(this);
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
    public String realmGet$full_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.full_nameColKey);
    }

    @Override
    public void realmSet$full_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.full_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.full_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.full_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.full_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$splty() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.spltyColKey);
    }

    @Override
    public void realmSet$splty(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.spltyColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.spltyColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.spltyColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.spltyColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$notify_pic() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.notify_picColKey);
    }

    @Override
    public void realmSet$notify_pic(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.notify_picColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.notify_picColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.notify_picColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.notify_picColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$phone() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phoneColKey);
    }

    @Override
    public void realmSet$phone(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phoneColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.phoneColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phoneColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phoneColKey, value);
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
    public String realmGet$email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailColKey);
    }

    @Override
    public void realmSet$email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.emailColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailColKey, value);
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
    public String realmGet$degrees() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.degreesColKey);
    }

    @Override
    public void realmSet$degrees(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.degreesColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.degreesColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.degreesColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.degreesColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmCardInfo", 9, 0);
        builder.addPersistedProperty("qb_user_id", RealmFieldType.STRING, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("full_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("splty", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("notify_pic", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("phone", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("location", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("workplace", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("degrees", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmCardInfoColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmCardInfoColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmCardInfo";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmCardInfo";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmCardInfo createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmCardInfo obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCardInfo.class);
            RealmCardInfoColumnInfo columnInfo = (RealmCardInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCardInfo.class);
            long pkColumnKey = columnInfo.qb_user_idColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("qb_user_id")) {
                colKey = table.findFirstString(pkColumnKey, json.getString("qb_user_id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCardInfo.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("qb_user_id")) {
                if (json.isNull("qb_user_id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCardInfo.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmCardInfo.class, json.getString("qb_user_id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'qb_user_id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) obj;
        if (json.has("full_name")) {
            if (json.isNull("full_name")) {
                objProxy.realmSet$full_name(null);
            } else {
                objProxy.realmSet$full_name((String) json.getString("full_name"));
            }
        }
        if (json.has("splty")) {
            if (json.isNull("splty")) {
                objProxy.realmSet$splty(null);
            } else {
                objProxy.realmSet$splty((String) json.getString("splty"));
            }
        }
        if (json.has("notify_pic")) {
            if (json.isNull("notify_pic")) {
                objProxy.realmSet$notify_pic(null);
            } else {
                objProxy.realmSet$notify_pic((String) json.getString("notify_pic"));
            }
        }
        if (json.has("phone")) {
            if (json.isNull("phone")) {
                objProxy.realmSet$phone(null);
            } else {
                objProxy.realmSet$phone((String) json.getString("phone"));
            }
        }
        if (json.has("location")) {
            if (json.isNull("location")) {
                objProxy.realmSet$location(null);
            } else {
                objProxy.realmSet$location((String) json.getString("location"));
            }
        }
        if (json.has("email")) {
            if (json.isNull("email")) {
                objProxy.realmSet$email(null);
            } else {
                objProxy.realmSet$email((String) json.getString("email"));
            }
        }
        if (json.has("workplace")) {
            if (json.isNull("workplace")) {
                objProxy.realmSet$workplace(null);
            } else {
                objProxy.realmSet$workplace((String) json.getString("workplace"));
            }
        }
        if (json.has("degrees")) {
            if (json.isNull("degrees")) {
                objProxy.realmSet$degrees(null);
            } else {
                objProxy.realmSet$degrees((String) json.getString("degrees"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmCardInfo createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmCardInfo obj = new com.vam.whitecoats.core.realm.RealmCardInfo();
        final com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) obj;
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
            } else if (name.equals("full_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$full_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$full_name(null);
                }
            } else if (name.equals("splty")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$splty((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$splty(null);
                }
            } else if (name.equals("notify_pic")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$notify_pic((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$notify_pic(null);
                }
            } else if (name.equals("phone")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phone((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phone(null);
                }
            } else if (name.equals("location")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$location((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$location(null);
                }
            } else if (name.equals("email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$email(null);
                }
            } else if (name.equals("workplace")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$workplace((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$workplace(null);
                }
            } else if (name.equals("degrees")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$degrees((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$degrees(null);
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

    private static com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCardInfo.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmCardInfo copyOrUpdate(Realm realm, RealmCardInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCardInfo object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmCardInfo) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmCardInfo realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCardInfo.class);
            long pkColumnKey = columnInfo.qb_user_idColKey;
            long colKey = table.findFirstString(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$qb_user_id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmCardInfo copy(Realm realm, RealmCardInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCardInfo newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmCardInfo) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.qb_user_idColKey, realmObjectSource.realmGet$qb_user_id());
        builder.addString(columnInfo.full_nameColKey, realmObjectSource.realmGet$full_name());
        builder.addString(columnInfo.spltyColKey, realmObjectSource.realmGet$splty());
        builder.addString(columnInfo.notify_picColKey, realmObjectSource.realmGet$notify_pic());
        builder.addString(columnInfo.phoneColKey, realmObjectSource.realmGet$phone());
        builder.addString(columnInfo.locationColKey, realmObjectSource.realmGet$location());
        builder.addString(columnInfo.emailColKey, realmObjectSource.realmGet$email());
        builder.addString(columnInfo.workplaceColKey, realmObjectSource.realmGet$workplace());
        builder.addString(columnInfo.degreesColKey, realmObjectSource.realmGet$degrees());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmCardInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCardInfoColumnInfo columnInfo = (RealmCardInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        long pkColumnKey = columnInfo.qb_user_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$qb_user_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$full_name = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$full_name();
        if (realmGet$full_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.full_nameColKey, colKey, realmGet$full_name, false);
        }
        String realmGet$splty = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$splty();
        if (realmGet$splty != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.spltyColKey, colKey, realmGet$splty, false);
        }
        String realmGet$notify_pic = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$notify_pic();
        if (realmGet$notify_pic != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.notify_picColKey, colKey, realmGet$notify_pic, false);
        }
        String realmGet$phone = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$phone();
        if (realmGet$phone != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneColKey, colKey, realmGet$phone, false);
        }
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        }
        String realmGet$email = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
        }
        String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$workplace();
        if (realmGet$workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
        }
        String realmGet$degrees = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$degrees();
        if (realmGet$degrees != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.degreesColKey, colKey, realmGet$degrees, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCardInfoColumnInfo columnInfo = (RealmCardInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        long pkColumnKey = columnInfo.qb_user_idColKey;
        com.vam.whitecoats.core.realm.RealmCardInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCardInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$qb_user_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$full_name = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$full_name();
            if (realmGet$full_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.full_nameColKey, colKey, realmGet$full_name, false);
            }
            String realmGet$splty = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$splty();
            if (realmGet$splty != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.spltyColKey, colKey, realmGet$splty, false);
            }
            String realmGet$notify_pic = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$notify_pic();
            if (realmGet$notify_pic != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.notify_picColKey, colKey, realmGet$notify_pic, false);
            }
            String realmGet$phone = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$phone();
            if (realmGet$phone != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneColKey, colKey, realmGet$phone, false);
            }
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            }
            String realmGet$email = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
            }
            String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$workplace();
            if (realmGet$workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
            }
            String realmGet$degrees = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$degrees();
            if (realmGet$degrees != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.degreesColKey, colKey, realmGet$degrees, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmCardInfo object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCardInfoColumnInfo columnInfo = (RealmCardInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        long pkColumnKey = columnInfo.qb_user_idColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$qb_user_id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
        }
        cache.put(object, colKey);
        String realmGet$full_name = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$full_name();
        if (realmGet$full_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.full_nameColKey, colKey, realmGet$full_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.full_nameColKey, colKey, false);
        }
        String realmGet$splty = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$splty();
        if (realmGet$splty != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.spltyColKey, colKey, realmGet$splty, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.spltyColKey, colKey, false);
        }
        String realmGet$notify_pic = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$notify_pic();
        if (realmGet$notify_pic != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.notify_picColKey, colKey, realmGet$notify_pic, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.notify_picColKey, colKey, false);
        }
        String realmGet$phone = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$phone();
        if (realmGet$phone != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneColKey, colKey, realmGet$phone, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phoneColKey, colKey, false);
        }
        String realmGet$location = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$location();
        if (realmGet$location != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
        }
        String realmGet$email = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailColKey, colKey, false);
        }
        String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$workplace();
        if (realmGet$workplace != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.workplaceColKey, colKey, false);
        }
        String realmGet$degrees = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$degrees();
        if (realmGet$degrees != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.degreesColKey, colKey, realmGet$degrees, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.degreesColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        long tableNativePtr = table.getNativePtr();
        RealmCardInfoColumnInfo columnInfo = (RealmCardInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        long pkColumnKey = columnInfo.qb_user_idColKey;
        com.vam.whitecoats.core.realm.RealmCardInfo object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmCardInfo) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$qb_user_id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstString(tableNativePtr, pkColumnKey, (String)primaryKeyValue);
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, primaryKeyValue);
            }
            cache.put(object, colKey);
            String realmGet$full_name = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$full_name();
            if (realmGet$full_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.full_nameColKey, colKey, realmGet$full_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.full_nameColKey, colKey, false);
            }
            String realmGet$splty = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$splty();
            if (realmGet$splty != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.spltyColKey, colKey, realmGet$splty, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.spltyColKey, colKey, false);
            }
            String realmGet$notify_pic = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$notify_pic();
            if (realmGet$notify_pic != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.notify_picColKey, colKey, realmGet$notify_pic, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.notify_picColKey, colKey, false);
            }
            String realmGet$phone = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$phone();
            if (realmGet$phone != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneColKey, colKey, realmGet$phone, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phoneColKey, colKey, false);
            }
            String realmGet$location = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$location();
            if (realmGet$location != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locationColKey, colKey, realmGet$location, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.locationColKey, colKey, false);
            }
            String realmGet$email = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailColKey, colKey, realmGet$email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.emailColKey, colKey, false);
            }
            String realmGet$workplace = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$workplace();
            if (realmGet$workplace != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.workplaceColKey, colKey, realmGet$workplace, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.workplaceColKey, colKey, false);
            }
            String realmGet$degrees = ((com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) object).realmGet$degrees();
            if (realmGet$degrees != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.degreesColKey, colKey, realmGet$degrees, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.degreesColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmCardInfo createDetachedCopy(com.vam.whitecoats.core.realm.RealmCardInfo realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmCardInfo unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmCardInfo();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmCardInfo) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmCardInfo) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$qb_user_id(realmSource.realmGet$qb_user_id());
        unmanagedCopy.realmSet$full_name(realmSource.realmGet$full_name());
        unmanagedCopy.realmSet$splty(realmSource.realmGet$splty());
        unmanagedCopy.realmSet$notify_pic(realmSource.realmGet$notify_pic());
        unmanagedCopy.realmSet$phone(realmSource.realmGet$phone());
        unmanagedCopy.realmSet$location(realmSource.realmGet$location());
        unmanagedCopy.realmSet$email(realmSource.realmGet$email());
        unmanagedCopy.realmSet$workplace(realmSource.realmGet$workplace());
        unmanagedCopy.realmSet$degrees(realmSource.realmGet$degrees());

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmCardInfo update(Realm realm, RealmCardInfoColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmCardInfo realmObject, com.vam.whitecoats.core.realm.RealmCardInfo newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addString(columnInfo.qb_user_idColKey, realmObjectSource.realmGet$qb_user_id());
        builder.addString(columnInfo.full_nameColKey, realmObjectSource.realmGet$full_name());
        builder.addString(columnInfo.spltyColKey, realmObjectSource.realmGet$splty());
        builder.addString(columnInfo.notify_picColKey, realmObjectSource.realmGet$notify_pic());
        builder.addString(columnInfo.phoneColKey, realmObjectSource.realmGet$phone());
        builder.addString(columnInfo.locationColKey, realmObjectSource.realmGet$location());
        builder.addString(columnInfo.emailColKey, realmObjectSource.realmGet$email());
        builder.addString(columnInfo.workplaceColKey, realmObjectSource.realmGet$workplace());
        builder.addString(columnInfo.degreesColKey, realmObjectSource.realmGet$degrees());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmCardInfo = proxy[");
        stringBuilder.append("{qb_user_id:");
        stringBuilder.append(realmGet$qb_user_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{full_name:");
        stringBuilder.append(realmGet$full_name() != null ? realmGet$full_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{splty:");
        stringBuilder.append(realmGet$splty() != null ? realmGet$splty() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{notify_pic:");
        stringBuilder.append(realmGet$notify_pic() != null ? realmGet$notify_pic() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phone:");
        stringBuilder.append(realmGet$phone() != null ? realmGet$phone() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{location:");
        stringBuilder.append(realmGet$location() != null ? realmGet$location() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email:");
        stringBuilder.append(realmGet$email() != null ? realmGet$email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{workplace:");
        stringBuilder.append(realmGet$workplace() != null ? realmGet$workplace() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{degrees:");
        stringBuilder.append(realmGet$degrees() != null ? realmGet$degrees() : "null");
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
        com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy aRealmCardInfo = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmCardInfo.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmCardInfo.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmCardInfo.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
