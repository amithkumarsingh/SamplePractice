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
public class com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy extends com.vam.whitecoats.core.realm.RealmPublications
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface {

    static final class RealmPublicationsColumnInfo extends ColumnInfo {
        long pub_idColKey;
        long titleColKey;
        long authorsColKey;
        long journalColKey;
        long web_pageColKey;
        long typeColKey;

        RealmPublicationsColumnInfo(OsSchemaInfo schemaInfo) {
            super(6);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmPublications");
            this.pub_idColKey = addColumnDetails("pub_id", "pub_id", objectSchemaInfo);
            this.titleColKey = addColumnDetails("title", "title", objectSchemaInfo);
            this.authorsColKey = addColumnDetails("authors", "authors", objectSchemaInfo);
            this.journalColKey = addColumnDetails("journal", "journal", objectSchemaInfo);
            this.web_pageColKey = addColumnDetails("web_page", "web_page", objectSchemaInfo);
            this.typeColKey = addColumnDetails("type", "type", objectSchemaInfo);
        }

        RealmPublicationsColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmPublicationsColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmPublicationsColumnInfo src = (RealmPublicationsColumnInfo) rawSrc;
            final RealmPublicationsColumnInfo dst = (RealmPublicationsColumnInfo) rawDst;
            dst.pub_idColKey = src.pub_idColKey;
            dst.titleColKey = src.titleColKey;
            dst.authorsColKey = src.authorsColKey;
            dst.journalColKey = src.journalColKey;
            dst.web_pageColKey = src.web_pageColKey;
            dst.typeColKey = src.typeColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmPublicationsColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmPublications> proxyState;

    com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmPublicationsColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmPublications>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$pub_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.pub_idColKey);
    }

    @Override
    public void realmSet$pub_id(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'pub_id' to null.");
            }
            row.getTable().setLong(columnInfo.pub_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'pub_id' to null.");
        }
        proxyState.getRow$realm().setLong(columnInfo.pub_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$title() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.titleColKey);
    }

    @Override
    public void realmSet$title(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.titleColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.titleColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.titleColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.titleColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$authors() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.authorsColKey);
    }

    @Override
    public void realmSet$authors(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.authorsColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.authorsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.authorsColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.authorsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$journal() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.journalColKey);
    }

    @Override
    public void realmSet$journal(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.journalColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.journalColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.journalColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.journalColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$web_page() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.web_pageColKey);
    }

    @Override
    public void realmSet$web_page(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.web_pageColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.web_pageColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.web_pageColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.web_pageColKey, value);
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmPublications", 6, 0);
        builder.addPersistedProperty("pub_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("authors", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("journal", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("web_page", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmPublicationsColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmPublicationsColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmPublications";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmPublications";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmPublications createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.realm.RealmPublications obj = realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmPublications.class, true, excludeFields);

        final com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) obj;
        if (json.has("pub_id")) {
            if (json.isNull("pub_id")) {
                objProxy.realmSet$pub_id(null);
            } else {
                objProxy.realmSet$pub_id((int) json.getInt("pub_id"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                objProxy.realmSet$title(null);
            } else {
                objProxy.realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("authors")) {
            if (json.isNull("authors")) {
                objProxy.realmSet$authors(null);
            } else {
                objProxy.realmSet$authors((String) json.getString("authors"));
            }
        }
        if (json.has("journal")) {
            if (json.isNull("journal")) {
                objProxy.realmSet$journal(null);
            } else {
                objProxy.realmSet$journal((String) json.getString("journal"));
            }
        }
        if (json.has("web_page")) {
            if (json.isNull("web_page")) {
                objProxy.realmSet$web_page(null);
            } else {
                objProxy.realmSet$web_page((String) json.getString("web_page"));
            }
        }
        if (json.has("type")) {
            if (json.isNull("type")) {
                objProxy.realmSet$type(null);
            } else {
                objProxy.realmSet$type((String) json.getString("type"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmPublications createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.realm.RealmPublications obj = new com.vam.whitecoats.core.realm.RealmPublications();
        final com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("pub_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$pub_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$pub_id(null);
                }
            } else if (name.equals("title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$title(null);
                }
            } else if (name.equals("authors")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$authors((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$authors(null);
                }
            } else if (name.equals("journal")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$journal((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$journal(null);
                }
            } else if (name.equals("web_page")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$web_page((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$web_page(null);
                }
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$type(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPublications.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmPublications copyOrUpdate(Realm realm, RealmPublicationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmPublications object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmPublications) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmPublications copy(Realm realm, RealmPublicationsColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmPublications newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmPublications) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPublications.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.pub_idColKey, realmObjectSource.realmGet$pub_id());
        builder.addString(columnInfo.titleColKey, realmObjectSource.realmGet$title());
        builder.addString(columnInfo.authorsColKey, realmObjectSource.realmGet$authors());
        builder.addString(columnInfo.journalColKey, realmObjectSource.realmGet$journal());
        builder.addString(columnInfo.web_pageColKey, realmObjectSource.realmGet$web_page());
        builder.addString(columnInfo.typeColKey, realmObjectSource.realmGet$type());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmPublications object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPublications.class);
        long tableNativePtr = table.getNativePtr();
        RealmPublicationsColumnInfo columnInfo = (RealmPublicationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPublications.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$pub_id = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$pub_id();
        if (realmGet$pub_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.pub_idColKey, colKey, realmGet$pub_id.longValue(), false);
        }
        String realmGet$title = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleColKey, colKey, realmGet$title, false);
        }
        String realmGet$authors = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$authors();
        if (realmGet$authors != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.authorsColKey, colKey, realmGet$authors, false);
        }
        String realmGet$journal = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$journal();
        if (realmGet$journal != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.journalColKey, colKey, realmGet$journal, false);
        }
        String realmGet$web_page = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$web_page();
        if (realmGet$web_page != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.web_pageColKey, colKey, realmGet$web_page, false);
        }
        String realmGet$type = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, colKey, realmGet$type, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPublications.class);
        long tableNativePtr = table.getNativePtr();
        RealmPublicationsColumnInfo columnInfo = (RealmPublicationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPublications.class);
        com.vam.whitecoats.core.realm.RealmPublications object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmPublications) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$pub_id = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$pub_id();
            if (realmGet$pub_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.pub_idColKey, colKey, realmGet$pub_id.longValue(), false);
            }
            String realmGet$title = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleColKey, colKey, realmGet$title, false);
            }
            String realmGet$authors = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$authors();
            if (realmGet$authors != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.authorsColKey, colKey, realmGet$authors, false);
            }
            String realmGet$journal = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$journal();
            if (realmGet$journal != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.journalColKey, colKey, realmGet$journal, false);
            }
            String realmGet$web_page = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$web_page();
            if (realmGet$web_page != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.web_pageColKey, colKey, realmGet$web_page, false);
            }
            String realmGet$type = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, colKey, realmGet$type, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmPublications object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPublications.class);
        long tableNativePtr = table.getNativePtr();
        RealmPublicationsColumnInfo columnInfo = (RealmPublicationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPublications.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Number realmGet$pub_id = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$pub_id();
        if (realmGet$pub_id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.pub_idColKey, colKey, realmGet$pub_id.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.pub_idColKey, colKey, false);
        }
        String realmGet$title = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleColKey, colKey, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleColKey, colKey, false);
        }
        String realmGet$authors = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$authors();
        if (realmGet$authors != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.authorsColKey, colKey, realmGet$authors, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.authorsColKey, colKey, false);
        }
        String realmGet$journal = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$journal();
        if (realmGet$journal != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.journalColKey, colKey, realmGet$journal, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.journalColKey, colKey, false);
        }
        String realmGet$web_page = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$web_page();
        if (realmGet$web_page != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.web_pageColKey, colKey, realmGet$web_page, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.web_pageColKey, colKey, false);
        }
        String realmGet$type = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, colKey, realmGet$type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.typeColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmPublications.class);
        long tableNativePtr = table.getNativePtr();
        RealmPublicationsColumnInfo columnInfo = (RealmPublicationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPublications.class);
        com.vam.whitecoats.core.realm.RealmPublications object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmPublications) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Number realmGet$pub_id = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$pub_id();
            if (realmGet$pub_id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.pub_idColKey, colKey, realmGet$pub_id.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.pub_idColKey, colKey, false);
            }
            String realmGet$title = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$title();
            if (realmGet$title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.titleColKey, colKey, realmGet$title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.titleColKey, colKey, false);
            }
            String realmGet$authors = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$authors();
            if (realmGet$authors != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.authorsColKey, colKey, realmGet$authors, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.authorsColKey, colKey, false);
            }
            String realmGet$journal = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$journal();
            if (realmGet$journal != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.journalColKey, colKey, realmGet$journal, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.journalColKey, colKey, false);
            }
            String realmGet$web_page = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$web_page();
            if (realmGet$web_page != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.web_pageColKey, colKey, realmGet$web_page, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.web_pageColKey, colKey, false);
            }
            String realmGet$type = ((com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, colKey, realmGet$type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.typeColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.realm.RealmPublications createDetachedCopy(com.vam.whitecoats.core.realm.RealmPublications realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmPublications unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmPublications();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmPublications) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmPublications) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmPublicationsRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$pub_id(realmSource.realmGet$pub_id());
        unmanagedCopy.realmSet$title(realmSource.realmGet$title());
        unmanagedCopy.realmSet$authors(realmSource.realmGet$authors());
        unmanagedCopy.realmSet$journal(realmSource.realmGet$journal());
        unmanagedCopy.realmSet$web_page(realmSource.realmGet$web_page());
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmPublications = proxy[");
        stringBuilder.append("{pub_id:");
        stringBuilder.append(realmGet$pub_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{authors:");
        stringBuilder.append(realmGet$authors() != null ? realmGet$authors() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{journal:");
        stringBuilder.append(realmGet$journal() != null ? realmGet$journal() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{web_page:");
        stringBuilder.append(realmGet$web_page() != null ? realmGet$web_page() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type() != null ? realmGet$type() : "null");
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
        com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy aRealmPublications = (com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmPublications.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmPublications.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmPublications.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
