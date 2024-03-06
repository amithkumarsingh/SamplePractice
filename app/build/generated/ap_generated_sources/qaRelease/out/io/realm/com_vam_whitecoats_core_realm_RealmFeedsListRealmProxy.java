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
public class com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy extends com.vam.whitecoats.core.realm.RealmFeedsList
    implements RealmObjectProxy, com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface {

    static final class RealmFeedsListColumnInfo extends ColumnInfo {
        long feed_IdColKey;
        long feedsListColKey;

        RealmFeedsListColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmFeedsList");
            this.feed_IdColKey = addColumnDetails("feed_Id", "feed_Id", objectSchemaInfo);
            this.feedsListColKey = addColumnDetails("feedsList", "feedsList", objectSchemaInfo);
        }

        RealmFeedsListColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmFeedsListColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmFeedsListColumnInfo src = (RealmFeedsListColumnInfo) rawSrc;
            final RealmFeedsListColumnInfo dst = (RealmFeedsListColumnInfo) rawDst;
            dst.feed_IdColKey = src.feed_IdColKey;
            dst.feedsListColKey = src.feedsListColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmFeedsListColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.realm.RealmFeedsList> proxyState;
    private RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListRealmList;

    com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmFeedsListColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.realm.RealmFeedsList>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$feed_Id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.feed_IdColKey);
    }

    @Override
    public void realmSet$feed_Id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'feed_Id' cannot be changed after object was created.");
    }

    @Override
    public RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> realmGet$feedsList() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (feedsListRealmList != null) {
            return feedsListRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.feedsListColKey);
            feedsListRealmList = new RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo>(com.vam.whitecoats.core.realm.RealmFeedInfo.class, osList, proxyState.getRealm$realm());
            return feedsListRealmList;
        }
    }

    @Override
    public void realmSet$feedsList(RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("feedsList")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> original = value;
                value = new RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo>();
                for (com.vam.whitecoats.core.realm.RealmFeedInfo item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.feedsListColKey);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.vam.whitecoats.core.realm.RealmFeedInfo linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getObjectKey());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.vam.whitecoats.core.realm.RealmFeedInfo linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getObjectKey());
            }
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmFeedsList", 2, 0);
        builder.addPersistedProperty("feed_Id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("feedsList", RealmFieldType.LIST, "RealmFeedInfo");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmFeedsListColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmFeedsListColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmFeedsList";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmFeedsList";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.realm.RealmFeedsList createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        com.vam.whitecoats.core.realm.RealmFeedsList obj = null;
        if (update) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmFeedsList.class);
            RealmFeedsListColumnInfo columnInfo = (RealmFeedsListColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedsList.class);
            long pkColumnKey = columnInfo.feed_IdColKey;
            long colKey = Table.NO_MATCH;
            if (!json.isNull("feed_Id")) {
                colKey = table.findFirstLong(pkColumnKey, json.getLong("feed_Id"));
            }
            if (colKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedsList.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("feedsList")) {
                excludeFields.add("feedsList");
            }
            if (json.has("feed_Id")) {
                if (json.isNull("feed_Id")) {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmFeedsList.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy) realm.createObjectInternal(com.vam.whitecoats.core.realm.RealmFeedsList.class, json.getInt("feed_Id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'feed_Id'.");
            }
        }

        final com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) obj;
        if (json.has("feedsList")) {
            if (json.isNull("feedsList")) {
                objProxy.realmSet$feedsList(null);
            } else {
                objProxy.realmGet$feedsList().clear();
                JSONArray array = json.getJSONArray("feedsList");
                for (int i = 0; i < array.length(); i++) {
                    com.vam.whitecoats.core.realm.RealmFeedInfo item = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$feedsList().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.realm.RealmFeedsList createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.vam.whitecoats.core.realm.RealmFeedsList obj = new com.vam.whitecoats.core.realm.RealmFeedsList();
        final com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface objProxy = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("feed_Id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$feed_Id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'feed_Id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("feedsList")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$feedsList(null);
                } else {
                    objProxy.realmSet$feedsList(new RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.vam.whitecoats.core.realm.RealmFeedInfo item = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$feedsList().add(item);
                    }
                    reader.endArray();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'feed_Id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedsList.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy obj = new io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.realm.RealmFeedsList copyOrUpdate(Realm realm, RealmFeedsListColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmFeedsList object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.realm.RealmFeedsList) cachedRealmObject;
        }

        com.vam.whitecoats.core.realm.RealmFeedsList realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmFeedsList.class);
            long pkColumnKey = columnInfo.feed_IdColKey;
            long colKey = table.findFirstLong(pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
            if (colKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(colKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.realm.RealmFeedsList copy(Realm realm, RealmFeedsListColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmFeedsList newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.realm.RealmFeedsList) cachedRealmObject;
        }

        com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.feed_IdColKey, realmObjectSource.realmGet$feed_Id());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        // Finally add all fields that reference other Realm Objects, either directly or through a list
        RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListList = realmObjectSource.realmGet$feedsList();
        if (feedsListList != null) {
            RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListRealmList = realmObjectCopy.realmGet$feedsList();
            feedsListRealmList.clear();
            for (int i = 0; i < feedsListList.size(); i++) {
                com.vam.whitecoats.core.realm.RealmFeedInfo feedsListItem = feedsListList.get(i);
                com.vam.whitecoats.core.realm.RealmFeedInfo cachefeedsList = (com.vam.whitecoats.core.realm.RealmFeedInfo) cache.get(feedsListItem);
                if (cachefeedsList != null) {
                    feedsListRealmList.add(cachefeedsList);
                } else {
                    feedsListRealmList.add(com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.copyOrUpdate(realm, (com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.RealmFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedInfo.class), feedsListItem, update, cache, flags));
                }
            }
        }

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.realm.RealmFeedsList object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        long tableNativePtr = table.getNativePtr();
        RealmFeedsListColumnInfo columnInfo = (RealmFeedsListColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        long pkColumnKey = columnInfo.feed_IdColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, colKey);

        RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListList = ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feedsList();
        if (feedsListList != null) {
            OsList feedsListOsList = new OsList(table.getUncheckedRow(colKey), columnInfo.feedsListColKey);
            for (com.vam.whitecoats.core.realm.RealmFeedInfo feedsListItem : feedsListList) {
                Long cacheItemIndexfeedsList = cache.get(feedsListItem);
                if (cacheItemIndexfeedsList == null) {
                    cacheItemIndexfeedsList = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insert(realm, feedsListItem, cache);
                }
                feedsListOsList.addRow(cacheItemIndexfeedsList);
            }
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        long tableNativePtr = table.getNativePtr();
        RealmFeedsListColumnInfo columnInfo = (RealmFeedsListColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        long pkColumnKey = columnInfo.feed_IdColKey;
        com.vam.whitecoats.core.realm.RealmFeedsList object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmFeedsList) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, colKey);

            RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListList = ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feedsList();
            if (feedsListList != null) {
                OsList feedsListOsList = new OsList(table.getUncheckedRow(colKey), columnInfo.feedsListColKey);
                for (com.vam.whitecoats.core.realm.RealmFeedInfo feedsListItem : feedsListList) {
                    Long cacheItemIndexfeedsList = cache.get(feedsListItem);
                    if (cacheItemIndexfeedsList == null) {
                        cacheItemIndexfeedsList = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insert(realm, feedsListItem, cache);
                    }
                    feedsListOsList.addRow(cacheItemIndexfeedsList);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.realm.RealmFeedsList object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        long tableNativePtr = table.getNativePtr();
        RealmFeedsListColumnInfo columnInfo = (RealmFeedsListColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        long pkColumnKey = columnInfo.feed_IdColKey;
        long colKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id();
        if (primaryKeyValue != null) {
            colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
        }
        if (colKey == Table.NO_MATCH) {
            colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
        }
        cache.put(object, colKey);

        OsList feedsListOsList = new OsList(table.getUncheckedRow(colKey), columnInfo.feedsListColKey);
        RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListList = ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feedsList();
        if (feedsListList != null && feedsListList.size() == feedsListOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = feedsListList.size();
            for (int i = 0; i < objects; i++) {
                com.vam.whitecoats.core.realm.RealmFeedInfo feedsListItem = feedsListList.get(i);
                Long cacheItemIndexfeedsList = cache.get(feedsListItem);
                if (cacheItemIndexfeedsList == null) {
                    cacheItemIndexfeedsList = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insertOrUpdate(realm, feedsListItem, cache);
                }
                feedsListOsList.setRow(i, cacheItemIndexfeedsList);
            }
        } else {
            feedsListOsList.removeAll();
            if (feedsListList != null) {
                for (com.vam.whitecoats.core.realm.RealmFeedInfo feedsListItem : feedsListList) {
                    Long cacheItemIndexfeedsList = cache.get(feedsListItem);
                    if (cacheItemIndexfeedsList == null) {
                        cacheItemIndexfeedsList = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insertOrUpdate(realm, feedsListItem, cache);
                    }
                    feedsListOsList.addRow(cacheItemIndexfeedsList);
                }
            }
        }

        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        long tableNativePtr = table.getNativePtr();
        RealmFeedsListColumnInfo columnInfo = (RealmFeedsListColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        long pkColumnKey = columnInfo.feed_IdColKey;
        com.vam.whitecoats.core.realm.RealmFeedsList object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.realm.RealmFeedsList) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id();
            if (primaryKeyValue != null) {
                colKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
            }
            if (colKey == Table.NO_MATCH) {
                colKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feed_Id());
            }
            cache.put(object, colKey);

            OsList feedsListOsList = new OsList(table.getUncheckedRow(colKey), columnInfo.feedsListColKey);
            RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListList = ((com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) object).realmGet$feedsList();
            if (feedsListList != null && feedsListList.size() == feedsListOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = feedsListList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.vam.whitecoats.core.realm.RealmFeedInfo feedsListItem = feedsListList.get(i);
                    Long cacheItemIndexfeedsList = cache.get(feedsListItem);
                    if (cacheItemIndexfeedsList == null) {
                        cacheItemIndexfeedsList = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insertOrUpdate(realm, feedsListItem, cache);
                    }
                    feedsListOsList.setRow(i, cacheItemIndexfeedsList);
                }
            } else {
                feedsListOsList.removeAll();
                if (feedsListList != null) {
                    for (com.vam.whitecoats.core.realm.RealmFeedInfo feedsListItem : feedsListList) {
                        Long cacheItemIndexfeedsList = cache.get(feedsListItem);
                        if (cacheItemIndexfeedsList == null) {
                            cacheItemIndexfeedsList = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insertOrUpdate(realm, feedsListItem, cache);
                        }
                        feedsListOsList.addRow(cacheItemIndexfeedsList);
                    }
                }
            }

        }
    }

    public static com.vam.whitecoats.core.realm.RealmFeedsList createDetachedCopy(com.vam.whitecoats.core.realm.RealmFeedsList realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.realm.RealmFeedsList unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.realm.RealmFeedsList();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.realm.RealmFeedsList) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.realm.RealmFeedsList) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface realmSource = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$feed_Id(realmSource.realmGet$feed_Id());

        // Deep copy of feedsList
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$feedsList(null);
        } else {
            RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> managedfeedsListList = realmSource.realmGet$feedsList();
            RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> unmanagedfeedsListList = new RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo>();
            unmanagedCopy.realmSet$feedsList(unmanagedfeedsListList);
            int nextDepth = currentDepth + 1;
            int size = managedfeedsListList.size();
            for (int i = 0; i < size; i++) {
                com.vam.whitecoats.core.realm.RealmFeedInfo item = com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.createDetachedCopy(managedfeedsListList.get(i), nextDepth, maxDepth, cache);
                unmanagedfeedsListList.add(item);
            }
        }

        return unmanagedObject;
    }

    static com.vam.whitecoats.core.realm.RealmFeedsList update(Realm realm, RealmFeedsListColumnInfo columnInfo, com.vam.whitecoats.core.realm.RealmFeedsList realmObject, com.vam.whitecoats.core.realm.RealmFeedsList newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface realmObjectTarget = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) realmObject;
        com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxyInterface) newObject;
        Table table = realm.getTable(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addInteger(columnInfo.feed_IdColKey, realmObjectSource.realmGet$feed_Id());

        RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListList = realmObjectSource.realmGet$feedsList();
        if (feedsListList != null) {
            RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo> feedsListManagedCopy = new RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo>();
            for (int i = 0; i < feedsListList.size(); i++) {
                com.vam.whitecoats.core.realm.RealmFeedInfo feedsListItem = feedsListList.get(i);
                com.vam.whitecoats.core.realm.RealmFeedInfo cachefeedsList = (com.vam.whitecoats.core.realm.RealmFeedInfo) cache.get(feedsListItem);
                if (cachefeedsList != null) {
                    feedsListManagedCopy.add(cachefeedsList);
                } else {
                    feedsListManagedCopy.add(com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.copyOrUpdate(realm, (com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.RealmFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedInfo.class), feedsListItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.feedsListColKey, feedsListManagedCopy);
        } else {
            builder.addObjectList(columnInfo.feedsListColKey, new RealmList<com.vam.whitecoats.core.realm.RealmFeedInfo>());
        }

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmFeedsList = proxy[");
        stringBuilder.append("{feed_Id:");
        stringBuilder.append(realmGet$feed_Id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{feedsList:");
        stringBuilder.append("RealmList<RealmFeedInfo>[").append(realmGet$feedsList().size()).append("]");
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
        com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy aRealmFeedsList = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmFeedsList.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmFeedsList.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmFeedsList.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
