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
public class com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy extends com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog
    implements RealmObjectProxy, com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface {

    static final class RealmQBDialogColumnInfo extends ColumnInfo {
        long other_doc_idColKey;
        long last_msgColKey;
        long last_msg_timeColKey;
        long dialog_idColKey;
        long dialog_typeColKey;
        long unread_countColKey;
        long local_dialog_typeColKey;
        long dialog_nameColKey;
        long group_pic_pathColKey;
        long other_doc_nameColKey;
        long last_msg_sent_qb_idColKey;
        long group_roomjidColKey;
        long occupants_idsColKey;
        long group_pic_urlColKey;

        RealmQBDialogColumnInfo(OsSchemaInfo schemaInfo) {
            super(14);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmQBDialog");
            this.other_doc_idColKey = addColumnDetails("other_doc_id", "other_doc_id", objectSchemaInfo);
            this.last_msgColKey = addColumnDetails("last_msg", "last_msg", objectSchemaInfo);
            this.last_msg_timeColKey = addColumnDetails("last_msg_time", "last_msg_time", objectSchemaInfo);
            this.dialog_idColKey = addColumnDetails("dialog_id", "dialog_id", objectSchemaInfo);
            this.dialog_typeColKey = addColumnDetails("dialog_type", "dialog_type", objectSchemaInfo);
            this.unread_countColKey = addColumnDetails("unread_count", "unread_count", objectSchemaInfo);
            this.local_dialog_typeColKey = addColumnDetails("local_dialog_type", "local_dialog_type", objectSchemaInfo);
            this.dialog_nameColKey = addColumnDetails("dialog_name", "dialog_name", objectSchemaInfo);
            this.group_pic_pathColKey = addColumnDetails("group_pic_path", "group_pic_path", objectSchemaInfo);
            this.other_doc_nameColKey = addColumnDetails("other_doc_name", "other_doc_name", objectSchemaInfo);
            this.last_msg_sent_qb_idColKey = addColumnDetails("last_msg_sent_qb_id", "last_msg_sent_qb_id", objectSchemaInfo);
            this.group_roomjidColKey = addColumnDetails("group_roomjid", "group_roomjid", objectSchemaInfo);
            this.occupants_idsColKey = addColumnDetails("occupants_ids", "occupants_ids", objectSchemaInfo);
            this.group_pic_urlColKey = addColumnDetails("group_pic_url", "group_pic_url", objectSchemaInfo);
        }

        RealmQBDialogColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmQBDialogColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmQBDialogColumnInfo src = (RealmQBDialogColumnInfo) rawSrc;
            final RealmQBDialogColumnInfo dst = (RealmQBDialogColumnInfo) rawDst;
            dst.other_doc_idColKey = src.other_doc_idColKey;
            dst.last_msgColKey = src.last_msgColKey;
            dst.last_msg_timeColKey = src.last_msg_timeColKey;
            dst.dialog_idColKey = src.dialog_idColKey;
            dst.dialog_typeColKey = src.dialog_typeColKey;
            dst.unread_countColKey = src.unread_countColKey;
            dst.local_dialog_typeColKey = src.local_dialog_typeColKey;
            dst.dialog_nameColKey = src.dialog_nameColKey;
            dst.group_pic_pathColKey = src.group_pic_pathColKey;
            dst.other_doc_nameColKey = src.other_doc_nameColKey;
            dst.last_msg_sent_qb_idColKey = src.last_msg_sent_qb_idColKey;
            dst.group_roomjidColKey = src.group_roomjidColKey;
            dst.occupants_idsColKey = src.occupants_idsColKey;
            dst.group_pic_urlColKey = src.group_pic_urlColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmQBDialogColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog> proxyState;

    com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmQBDialogColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$other_doc_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.other_doc_idColKey);
    }

    @Override
    public void realmSet$other_doc_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.other_doc_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.other_doc_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$last_msg() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.last_msgColKey);
    }

    @Override
    public void realmSet$last_msg(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.last_msgColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.last_msgColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.last_msgColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.last_msgColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$last_msg_time() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.last_msg_timeColKey);
    }

    @Override
    public void realmSet$last_msg_time(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.last_msg_timeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.last_msg_timeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialog_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialog_idColKey);
    }

    @Override
    public void realmSet$dialog_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dialog_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dialog_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dialog_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialog_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialog_typeColKey);
    }

    @Override
    public void realmSet$dialog_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dialog_typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dialog_typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dialog_typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_typeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$unread_count() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.unread_countColKey);
    }

    @Override
    public void realmSet$unread_count(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.unread_countColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.unread_countColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$local_dialog_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.local_dialog_typeColKey);
    }

    @Override
    public void realmSet$local_dialog_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.local_dialog_typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.local_dialog_typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.local_dialog_typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.local_dialog_typeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialog_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialog_nameColKey);
    }

    @Override
    public void realmSet$dialog_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dialog_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dialog_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dialog_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_pic_path() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_pic_pathColKey);
    }

    @Override
    public void realmSet$group_pic_path(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_pic_pathColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_pic_pathColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_pic_pathColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_pic_pathColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$other_doc_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.other_doc_nameColKey);
    }

    @Override
    public void realmSet$other_doc_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.other_doc_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.other_doc_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.other_doc_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.other_doc_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$last_msg_sent_qb_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.last_msg_sent_qb_idColKey);
    }

    @Override
    public void realmSet$last_msg_sent_qb_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.last_msg_sent_qb_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.last_msg_sent_qb_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_roomjid() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_roomjidColKey);
    }

    @Override
    public void realmSet$group_roomjid(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_roomjidColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_roomjidColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_roomjidColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_roomjidColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$occupants_ids() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.occupants_idsColKey);
    }

    @Override
    public void realmSet$occupants_ids(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.occupants_idsColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.occupants_idsColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.occupants_idsColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.occupants_idsColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$group_pic_url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.group_pic_urlColKey);
    }

    @Override
    public void realmSet$group_pic_url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.group_pic_urlColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.group_pic_urlColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.group_pic_urlColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.group_pic_urlColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmQBDialog", 14, 0);
        builder.addPersistedProperty("other_doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("last_msg", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("last_msg_time", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("dialog_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dialog_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("unread_count", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("local_dialog_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dialog_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_pic_path", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("other_doc_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("last_msg_sent_qb_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("group_roomjid", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("occupants_ids", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("group_pic_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmQBDialogColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmQBDialogColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmQBDialog";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmQBDialog";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog obj = realm.createObjectInternal(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class, true, excludeFields);

        final com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface objProxy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) obj;
        if (json.has("other_doc_id")) {
            if (json.isNull("other_doc_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'other_doc_id' to null.");
            } else {
                objProxy.realmSet$other_doc_id((int) json.getInt("other_doc_id"));
            }
        }
        if (json.has("last_msg")) {
            if (json.isNull("last_msg")) {
                objProxy.realmSet$last_msg(null);
            } else {
                objProxy.realmSet$last_msg((String) json.getString("last_msg"));
            }
        }
        if (json.has("last_msg_time")) {
            if (json.isNull("last_msg_time")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'last_msg_time' to null.");
            } else {
                objProxy.realmSet$last_msg_time((long) json.getLong("last_msg_time"));
            }
        }
        if (json.has("dialog_id")) {
            if (json.isNull("dialog_id")) {
                objProxy.realmSet$dialog_id(null);
            } else {
                objProxy.realmSet$dialog_id((String) json.getString("dialog_id"));
            }
        }
        if (json.has("dialog_type")) {
            if (json.isNull("dialog_type")) {
                objProxy.realmSet$dialog_type(null);
            } else {
                objProxy.realmSet$dialog_type((String) json.getString("dialog_type"));
            }
        }
        if (json.has("unread_count")) {
            if (json.isNull("unread_count")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'unread_count' to null.");
            } else {
                objProxy.realmSet$unread_count((int) json.getInt("unread_count"));
            }
        }
        if (json.has("local_dialog_type")) {
            if (json.isNull("local_dialog_type")) {
                objProxy.realmSet$local_dialog_type(null);
            } else {
                objProxy.realmSet$local_dialog_type((String) json.getString("local_dialog_type"));
            }
        }
        if (json.has("dialog_name")) {
            if (json.isNull("dialog_name")) {
                objProxy.realmSet$dialog_name(null);
            } else {
                objProxy.realmSet$dialog_name((String) json.getString("dialog_name"));
            }
        }
        if (json.has("group_pic_path")) {
            if (json.isNull("group_pic_path")) {
                objProxy.realmSet$group_pic_path(null);
            } else {
                objProxy.realmSet$group_pic_path((String) json.getString("group_pic_path"));
            }
        }
        if (json.has("other_doc_name")) {
            if (json.isNull("other_doc_name")) {
                objProxy.realmSet$other_doc_name(null);
            } else {
                objProxy.realmSet$other_doc_name((String) json.getString("other_doc_name"));
            }
        }
        if (json.has("last_msg_sent_qb_id")) {
            if (json.isNull("last_msg_sent_qb_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'last_msg_sent_qb_id' to null.");
            } else {
                objProxy.realmSet$last_msg_sent_qb_id((int) json.getInt("last_msg_sent_qb_id"));
            }
        }
        if (json.has("group_roomjid")) {
            if (json.isNull("group_roomjid")) {
                objProxy.realmSet$group_roomjid(null);
            } else {
                objProxy.realmSet$group_roomjid((String) json.getString("group_roomjid"));
            }
        }
        if (json.has("occupants_ids")) {
            if (json.isNull("occupants_ids")) {
                objProxy.realmSet$occupants_ids(null);
            } else {
                objProxy.realmSet$occupants_ids((String) json.getString("occupants_ids"));
            }
        }
        if (json.has("group_pic_url")) {
            if (json.isNull("group_pic_url")) {
                objProxy.realmSet$group_pic_url(null);
            } else {
                objProxy.realmSet$group_pic_url((String) json.getString("group_pic_url"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog obj = new com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog();
        final com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface objProxy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("other_doc_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$other_doc_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'other_doc_id' to null.");
                }
            } else if (name.equals("last_msg")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$last_msg((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$last_msg(null);
                }
            } else if (name.equals("last_msg_time")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$last_msg_time((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'last_msg_time' to null.");
                }
            } else if (name.equals("dialog_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_id(null);
                }
            } else if (name.equals("dialog_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_type(null);
                }
            } else if (name.equals("unread_count")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$unread_count((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'unread_count' to null.");
                }
            } else if (name.equals("local_dialog_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$local_dialog_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$local_dialog_type(null);
                }
            } else if (name.equals("dialog_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_name(null);
                }
            } else if (name.equals("group_pic_path")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_pic_path((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_pic_path(null);
                }
            } else if (name.equals("other_doc_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$other_doc_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$other_doc_name(null);
                }
            } else if (name.equals("last_msg_sent_qb_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$last_msg_sent_qb_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'last_msg_sent_qb_id' to null.");
                }
            } else if (name.equals("group_roomjid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_roomjid((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_roomjid(null);
                }
            } else if (name.equals("occupants_ids")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$occupants_ids((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$occupants_ids(null);
                }
            } else if (name.equals("group_pic_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$group_pic_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$group_pic_url(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy obj = new io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog copyOrUpdate(Realm realm, RealmQBDialogColumnInfo columnInfo, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog copy(Realm realm, RealmQBDialogColumnInfo columnInfo, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) cachedRealmObject;
        }

        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.other_doc_idColKey, realmObjectSource.realmGet$other_doc_id());
        builder.addString(columnInfo.last_msgColKey, realmObjectSource.realmGet$last_msg());
        builder.addInteger(columnInfo.last_msg_timeColKey, realmObjectSource.realmGet$last_msg_time());
        builder.addString(columnInfo.dialog_idColKey, realmObjectSource.realmGet$dialog_id());
        builder.addString(columnInfo.dialog_typeColKey, realmObjectSource.realmGet$dialog_type());
        builder.addInteger(columnInfo.unread_countColKey, realmObjectSource.realmGet$unread_count());
        builder.addString(columnInfo.local_dialog_typeColKey, realmObjectSource.realmGet$local_dialog_type());
        builder.addString(columnInfo.dialog_nameColKey, realmObjectSource.realmGet$dialog_name());
        builder.addString(columnInfo.group_pic_pathColKey, realmObjectSource.realmGet$group_pic_path());
        builder.addString(columnInfo.other_doc_nameColKey, realmObjectSource.realmGet$other_doc_name());
        builder.addInteger(columnInfo.last_msg_sent_qb_idColKey, realmObjectSource.realmGet$last_msg_sent_qb_id());
        builder.addString(columnInfo.group_roomjidColKey, realmObjectSource.realmGet$group_roomjid());
        builder.addString(columnInfo.occupants_idsColKey, realmObjectSource.realmGet$occupants_ids());
        builder.addString(columnInfo.group_pic_urlColKey, realmObjectSource.realmGet$group_pic_url());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBDialogColumnInfo columnInfo = (RealmQBDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.other_doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$other_doc_id(), false);
        String realmGet$last_msg = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg();
        if (realmGet$last_msg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_msgColKey, colKey, realmGet$last_msg, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg_time(), false);
        String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_id();
        if (realmGet$dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
        }
        String realmGet$dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_type();
        if (realmGet$dialog_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_typeColKey, colKey, realmGet$dialog_type, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.unread_countColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$unread_count(), false);
        String realmGet$local_dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$local_dialog_type();
        if (realmGet$local_dialog_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, realmGet$local_dialog_type, false);
        }
        String realmGet$dialog_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_name();
        if (realmGet$dialog_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_nameColKey, colKey, realmGet$dialog_name, false);
        }
        String realmGet$group_pic_path = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_pic_path();
        if (realmGet$group_pic_path != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_pic_pathColKey, colKey, realmGet$group_pic_path, false);
        }
        String realmGet$other_doc_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$other_doc_name();
        if (realmGet$other_doc_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.other_doc_nameColKey, colKey, realmGet$other_doc_name, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg_sent_qb_id(), false);
        String realmGet$group_roomjid = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_roomjid();
        if (realmGet$group_roomjid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_roomjidColKey, colKey, realmGet$group_roomjid, false);
        }
        String realmGet$occupants_ids = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$occupants_ids();
        if (realmGet$occupants_ids != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.occupants_idsColKey, colKey, realmGet$occupants_ids, false);
        }
        String realmGet$group_pic_url = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_pic_url();
        if (realmGet$group_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, realmGet$group_pic_url, false);
        }
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBDialogColumnInfo columnInfo = (RealmQBDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.other_doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$other_doc_id(), false);
            String realmGet$last_msg = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg();
            if (realmGet$last_msg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_msgColKey, colKey, realmGet$last_msg, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg_time(), false);
            String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_id();
            if (realmGet$dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
            }
            String realmGet$dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_type();
            if (realmGet$dialog_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_typeColKey, colKey, realmGet$dialog_type, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.unread_countColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$unread_count(), false);
            String realmGet$local_dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$local_dialog_type();
            if (realmGet$local_dialog_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, realmGet$local_dialog_type, false);
            }
            String realmGet$dialog_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_name();
            if (realmGet$dialog_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_nameColKey, colKey, realmGet$dialog_name, false);
            }
            String realmGet$group_pic_path = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_pic_path();
            if (realmGet$group_pic_path != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_pic_pathColKey, colKey, realmGet$group_pic_path, false);
            }
            String realmGet$other_doc_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$other_doc_name();
            if (realmGet$other_doc_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.other_doc_nameColKey, colKey, realmGet$other_doc_name, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg_sent_qb_id(), false);
            String realmGet$group_roomjid = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_roomjid();
            if (realmGet$group_roomjid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_roomjidColKey, colKey, realmGet$group_roomjid, false);
            }
            String realmGet$occupants_ids = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$occupants_ids();
            if (realmGet$occupants_ids != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.occupants_idsColKey, colKey, realmGet$occupants_ids, false);
            }
            String realmGet$group_pic_url = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_pic_url();
            if (realmGet$group_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, realmGet$group_pic_url, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBDialogColumnInfo columnInfo = (RealmQBDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        Table.nativeSetLong(tableNativePtr, columnInfo.other_doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$other_doc_id(), false);
        String realmGet$last_msg = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg();
        if (realmGet$last_msg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_msgColKey, colKey, realmGet$last_msg, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.last_msgColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg_time(), false);
        String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_id();
        if (realmGet$dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_idColKey, colKey, false);
        }
        String realmGet$dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_type();
        if (realmGet$dialog_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_typeColKey, colKey, realmGet$dialog_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_typeColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.unread_countColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$unread_count(), false);
        String realmGet$local_dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$local_dialog_type();
        if (realmGet$local_dialog_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, realmGet$local_dialog_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, false);
        }
        String realmGet$dialog_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_name();
        if (realmGet$dialog_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_nameColKey, colKey, realmGet$dialog_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_nameColKey, colKey, false);
        }
        String realmGet$group_pic_path = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_pic_path();
        if (realmGet$group_pic_path != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_pic_pathColKey, colKey, realmGet$group_pic_path, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_pic_pathColKey, colKey, false);
        }
        String realmGet$other_doc_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$other_doc_name();
        if (realmGet$other_doc_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.other_doc_nameColKey, colKey, realmGet$other_doc_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.other_doc_nameColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg_sent_qb_id(), false);
        String realmGet$group_roomjid = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_roomjid();
        if (realmGet$group_roomjid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_roomjidColKey, colKey, realmGet$group_roomjid, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_roomjidColKey, colKey, false);
        }
        String realmGet$occupants_ids = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$occupants_ids();
        if (realmGet$occupants_ids != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.occupants_idsColKey, colKey, realmGet$occupants_ids, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.occupants_idsColKey, colKey, false);
        }
        String realmGet$group_pic_url = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_pic_url();
        if (realmGet$group_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, realmGet$group_pic_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, false);
        }
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBDialogColumnInfo columnInfo = (RealmQBDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            Table.nativeSetLong(tableNativePtr, columnInfo.other_doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$other_doc_id(), false);
            String realmGet$last_msg = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg();
            if (realmGet$last_msg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_msgColKey, colKey, realmGet$last_msg, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.last_msgColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg_time(), false);
            String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_id();
            if (realmGet$dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_idColKey, colKey, false);
            }
            String realmGet$dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_type();
            if (realmGet$dialog_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_typeColKey, colKey, realmGet$dialog_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_typeColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.unread_countColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$unread_count(), false);
            String realmGet$local_dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$local_dialog_type();
            if (realmGet$local_dialog_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, realmGet$local_dialog_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, false);
            }
            String realmGet$dialog_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$dialog_name();
            if (realmGet$dialog_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_nameColKey, colKey, realmGet$dialog_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_nameColKey, colKey, false);
            }
            String realmGet$group_pic_path = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_pic_path();
            if (realmGet$group_pic_path != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_pic_pathColKey, colKey, realmGet$group_pic_path, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_pic_pathColKey, colKey, false);
            }
            String realmGet$other_doc_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$other_doc_name();
            if (realmGet$other_doc_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.other_doc_nameColKey, colKey, realmGet$other_doc_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.other_doc_nameColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$last_msg_sent_qb_id(), false);
            String realmGet$group_roomjid = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_roomjid();
            if (realmGet$group_roomjid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_roomjidColKey, colKey, realmGet$group_roomjid, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_roomjidColKey, colKey, false);
            }
            String realmGet$occupants_ids = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$occupants_ids();
            if (realmGet$occupants_ids != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.occupants_idsColKey, colKey, realmGet$occupants_ids, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.occupants_idsColKey, colKey, false);
            }
            String realmGet$group_pic_url = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) object).realmGet$group_pic_url();
            if (realmGet$group_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, realmGet$group_pic_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.group_pic_urlColKey, colKey, false);
            }
        }
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog createDetachedCopy(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface realmSource = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$other_doc_id(realmSource.realmGet$other_doc_id());
        unmanagedCopy.realmSet$last_msg(realmSource.realmGet$last_msg());
        unmanagedCopy.realmSet$last_msg_time(realmSource.realmGet$last_msg_time());
        unmanagedCopy.realmSet$dialog_id(realmSource.realmGet$dialog_id());
        unmanagedCopy.realmSet$dialog_type(realmSource.realmGet$dialog_type());
        unmanagedCopy.realmSet$unread_count(realmSource.realmGet$unread_count());
        unmanagedCopy.realmSet$local_dialog_type(realmSource.realmGet$local_dialog_type());
        unmanagedCopy.realmSet$dialog_name(realmSource.realmGet$dialog_name());
        unmanagedCopy.realmSet$group_pic_path(realmSource.realmGet$group_pic_path());
        unmanagedCopy.realmSet$other_doc_name(realmSource.realmGet$other_doc_name());
        unmanagedCopy.realmSet$last_msg_sent_qb_id(realmSource.realmGet$last_msg_sent_qb_id());
        unmanagedCopy.realmSet$group_roomjid(realmSource.realmGet$group_roomjid());
        unmanagedCopy.realmSet$occupants_ids(realmSource.realmGet$occupants_ids());
        unmanagedCopy.realmSet$group_pic_url(realmSource.realmGet$group_pic_url());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmQBDialog = proxy[");
        stringBuilder.append("{other_doc_id:");
        stringBuilder.append(realmGet$other_doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_msg:");
        stringBuilder.append(realmGet$last_msg() != null ? realmGet$last_msg() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_msg_time:");
        stringBuilder.append(realmGet$last_msg_time());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialog_id:");
        stringBuilder.append(realmGet$dialog_id() != null ? realmGet$dialog_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialog_type:");
        stringBuilder.append(realmGet$dialog_type() != null ? realmGet$dialog_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{unread_count:");
        stringBuilder.append(realmGet$unread_count());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{local_dialog_type:");
        stringBuilder.append(realmGet$local_dialog_type() != null ? realmGet$local_dialog_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialog_name:");
        stringBuilder.append(realmGet$dialog_name() != null ? realmGet$dialog_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_pic_path:");
        stringBuilder.append(realmGet$group_pic_path() != null ? realmGet$group_pic_path() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{other_doc_name:");
        stringBuilder.append(realmGet$other_doc_name() != null ? realmGet$other_doc_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_msg_sent_qb_id:");
        stringBuilder.append(realmGet$last_msg_sent_qb_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_roomjid:");
        stringBuilder.append(realmGet$group_roomjid() != null ? realmGet$group_roomjid() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{occupants_ids:");
        stringBuilder.append(realmGet$occupants_ids() != null ? realmGet$occupants_ids() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group_pic_url:");
        stringBuilder.append(realmGet$group_pic_url() != null ? realmGet$group_pic_url() : "null");
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
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy aRealmQBDialog = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmQBDialog.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmQBDialog.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmQBDialog.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
