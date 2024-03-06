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
public class com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy extends com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog
    implements RealmObjectProxy, com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface {

    static final class RealmQBGroupDialogColumnInfo extends ColumnInfo {
        long dialog_idColKey;
        long dialog_titleColKey;
        long dialog_pic_nameColKey;
        long dialog_pic_urlColKey;
        long last_msg_sent_qb_idColKey;
        long local_dialog_typeColKey;
        long last_msgColKey;
        long last_msg_timeColKey;
        long dialog_room_jidColKey;
        long opponent_doc_idColKey;
        long dialog_creation_dateColKey;
        long member_added_dateColKey;

        RealmQBGroupDialogColumnInfo(OsSchemaInfo schemaInfo) {
            super(12);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("RealmQBGroupDialog");
            this.dialog_idColKey = addColumnDetails("dialog_id", "dialog_id", objectSchemaInfo);
            this.dialog_titleColKey = addColumnDetails("dialog_title", "dialog_title", objectSchemaInfo);
            this.dialog_pic_nameColKey = addColumnDetails("dialog_pic_name", "dialog_pic_name", objectSchemaInfo);
            this.dialog_pic_urlColKey = addColumnDetails("dialog_pic_url", "dialog_pic_url", objectSchemaInfo);
            this.last_msg_sent_qb_idColKey = addColumnDetails("last_msg_sent_qb_id", "last_msg_sent_qb_id", objectSchemaInfo);
            this.local_dialog_typeColKey = addColumnDetails("local_dialog_type", "local_dialog_type", objectSchemaInfo);
            this.last_msgColKey = addColumnDetails("last_msg", "last_msg", objectSchemaInfo);
            this.last_msg_timeColKey = addColumnDetails("last_msg_time", "last_msg_time", objectSchemaInfo);
            this.dialog_room_jidColKey = addColumnDetails("dialog_room_jid", "dialog_room_jid", objectSchemaInfo);
            this.opponent_doc_idColKey = addColumnDetails("opponent_doc_id", "opponent_doc_id", objectSchemaInfo);
            this.dialog_creation_dateColKey = addColumnDetails("dialog_creation_date", "dialog_creation_date", objectSchemaInfo);
            this.member_added_dateColKey = addColumnDetails("member_added_date", "member_added_date", objectSchemaInfo);
        }

        RealmQBGroupDialogColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new RealmQBGroupDialogColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final RealmQBGroupDialogColumnInfo src = (RealmQBGroupDialogColumnInfo) rawSrc;
            final RealmQBGroupDialogColumnInfo dst = (RealmQBGroupDialogColumnInfo) rawDst;
            dst.dialog_idColKey = src.dialog_idColKey;
            dst.dialog_titleColKey = src.dialog_titleColKey;
            dst.dialog_pic_nameColKey = src.dialog_pic_nameColKey;
            dst.dialog_pic_urlColKey = src.dialog_pic_urlColKey;
            dst.last_msg_sent_qb_idColKey = src.last_msg_sent_qb_idColKey;
            dst.local_dialog_typeColKey = src.local_dialog_typeColKey;
            dst.last_msgColKey = src.last_msgColKey;
            dst.last_msg_timeColKey = src.last_msg_timeColKey;
            dst.dialog_room_jidColKey = src.dialog_room_jidColKey;
            dst.opponent_doc_idColKey = src.opponent_doc_idColKey;
            dst.dialog_creation_dateColKey = src.dialog_creation_dateColKey;
            dst.member_added_dateColKey = src.member_added_dateColKey;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private RealmQBGroupDialogColumnInfo columnInfo;
    private ProxyState<com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog> proxyState;

    com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (RealmQBGroupDialogColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
                throw new IllegalArgumentException("Trying to set non-nullable field 'dialog_id' to null.");
            }
            row.getTable().setString(columnInfo.dialog_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'dialog_id' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialog_title() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialog_titleColKey);
    }

    @Override
    public void realmSet$dialog_title(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dialog_titleColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dialog_titleColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dialog_titleColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_titleColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialog_pic_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialog_pic_nameColKey);
    }

    @Override
    public void realmSet$dialog_pic_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dialog_pic_nameColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dialog_pic_nameColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dialog_pic_nameColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_pic_nameColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialog_pic_url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialog_pic_urlColKey);
    }

    @Override
    public void realmSet$dialog_pic_url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dialog_pic_urlColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dialog_pic_urlColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dialog_pic_urlColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_pic_urlColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$last_msg_sent_qb_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.last_msg_sent_qb_idColKey);
    }

    @Override
    public void realmSet$last_msg_sent_qb_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.last_msg_sent_qb_idColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.last_msg_sent_qb_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.last_msg_sent_qb_idColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.last_msg_sent_qb_idColKey, value);
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
    public String realmGet$last_msg_time() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.last_msg_timeColKey);
    }

    @Override
    public void realmSet$last_msg_time(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.last_msg_timeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.last_msg_timeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.last_msg_timeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.last_msg_timeColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$dialog_room_jid() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dialog_room_jidColKey);
    }

    @Override
    public void realmSet$dialog_room_jid(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dialog_room_jidColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.dialog_room_jidColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dialog_room_jidColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dialog_room_jidColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$opponent_doc_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.opponent_doc_idColKey);
    }

    @Override
    public void realmSet$opponent_doc_id(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.opponent_doc_idColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.opponent_doc_idColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$dialog_creation_date() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.dialog_creation_dateColKey);
    }

    @Override
    public void realmSet$dialog_creation_date(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.dialog_creation_dateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.dialog_creation_dateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$member_added_date() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.member_added_dateColKey);
    }

    @Override
    public void realmSet$member_added_date(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.member_added_dateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.member_added_dateColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("RealmQBGroupDialog", 12, 0);
        builder.addPersistedProperty("dialog_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("dialog_title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dialog_pic_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dialog_pic_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("last_msg_sent_qb_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("local_dialog_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("last_msg", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("last_msg_time", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("dialog_room_jid", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("opponent_doc_id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("dialog_creation_date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("member_added_date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static RealmQBGroupDialogColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new RealmQBGroupDialogColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "RealmQBGroupDialog";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "RealmQBGroupDialog";
    }

    @SuppressWarnings("cast")
    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog obj = realm.createObjectInternal(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class, true, excludeFields);

        final com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface objProxy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) obj;
        if (json.has("dialog_id")) {
            if (json.isNull("dialog_id")) {
                objProxy.realmSet$dialog_id(null);
            } else {
                objProxy.realmSet$dialog_id((String) json.getString("dialog_id"));
            }
        }
        if (json.has("dialog_title")) {
            if (json.isNull("dialog_title")) {
                objProxy.realmSet$dialog_title(null);
            } else {
                objProxy.realmSet$dialog_title((String) json.getString("dialog_title"));
            }
        }
        if (json.has("dialog_pic_name")) {
            if (json.isNull("dialog_pic_name")) {
                objProxy.realmSet$dialog_pic_name(null);
            } else {
                objProxy.realmSet$dialog_pic_name((String) json.getString("dialog_pic_name"));
            }
        }
        if (json.has("dialog_pic_url")) {
            if (json.isNull("dialog_pic_url")) {
                objProxy.realmSet$dialog_pic_url(null);
            } else {
                objProxy.realmSet$dialog_pic_url((String) json.getString("dialog_pic_url"));
            }
        }
        if (json.has("last_msg_sent_qb_id")) {
            if (json.isNull("last_msg_sent_qb_id")) {
                objProxy.realmSet$last_msg_sent_qb_id(null);
            } else {
                objProxy.realmSet$last_msg_sent_qb_id((String) json.getString("last_msg_sent_qb_id"));
            }
        }
        if (json.has("local_dialog_type")) {
            if (json.isNull("local_dialog_type")) {
                objProxy.realmSet$local_dialog_type(null);
            } else {
                objProxy.realmSet$local_dialog_type((String) json.getString("local_dialog_type"));
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
                objProxy.realmSet$last_msg_time(null);
            } else {
                objProxy.realmSet$last_msg_time((String) json.getString("last_msg_time"));
            }
        }
        if (json.has("dialog_room_jid")) {
            if (json.isNull("dialog_room_jid")) {
                objProxy.realmSet$dialog_room_jid(null);
            } else {
                objProxy.realmSet$dialog_room_jid((String) json.getString("dialog_room_jid"));
            }
        }
        if (json.has("opponent_doc_id")) {
            if (json.isNull("opponent_doc_id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'opponent_doc_id' to null.");
            } else {
                objProxy.realmSet$opponent_doc_id((int) json.getInt("opponent_doc_id"));
            }
        }
        if (json.has("dialog_creation_date")) {
            if (json.isNull("dialog_creation_date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'dialog_creation_date' to null.");
            } else {
                objProxy.realmSet$dialog_creation_date((long) json.getLong("dialog_creation_date"));
            }
        }
        if (json.has("member_added_date")) {
            if (json.isNull("member_added_date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'member_added_date' to null.");
            } else {
                objProxy.realmSet$member_added_date((long) json.getLong("member_added_date"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog obj = new com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog();
        final com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface objProxy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("dialog_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_id(null);
                }
            } else if (name.equals("dialog_title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_title(null);
                }
            } else if (name.equals("dialog_pic_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_pic_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_pic_name(null);
                }
            } else if (name.equals("dialog_pic_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_pic_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_pic_url(null);
                }
            } else if (name.equals("last_msg_sent_qb_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$last_msg_sent_qb_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$last_msg_sent_qb_id(null);
                }
            } else if (name.equals("local_dialog_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$local_dialog_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$local_dialog_type(null);
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
                    objProxy.realmSet$last_msg_time((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$last_msg_time(null);
                }
            } else if (name.equals("dialog_room_jid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_room_jid((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$dialog_room_jid(null);
                }
            } else if (name.equals("opponent_doc_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$opponent_doc_id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'opponent_doc_id' to null.");
                }
            } else if (name.equals("dialog_creation_date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$dialog_creation_date((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'dialog_creation_date' to null.");
                }
            } else if (name.equals("member_added_date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$member_added_date((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'member_added_date' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class), false, Collections.<String>emptyList());
        io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy obj = new io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog copyOrUpdate(Realm realm, RealmQBGroupDialogColumnInfo columnInfo, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog copy(Realm realm, RealmQBGroupDialogColumnInfo columnInfo, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) cachedRealmObject;
        }

        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface realmObjectSource = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) newObject;

        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.dialog_idColKey, realmObjectSource.realmGet$dialog_id());
        builder.addString(columnInfo.dialog_titleColKey, realmObjectSource.realmGet$dialog_title());
        builder.addString(columnInfo.dialog_pic_nameColKey, realmObjectSource.realmGet$dialog_pic_name());
        builder.addString(columnInfo.dialog_pic_urlColKey, realmObjectSource.realmGet$dialog_pic_url());
        builder.addString(columnInfo.last_msg_sent_qb_idColKey, realmObjectSource.realmGet$last_msg_sent_qb_id());
        builder.addString(columnInfo.local_dialog_typeColKey, realmObjectSource.realmGet$local_dialog_type());
        builder.addString(columnInfo.last_msgColKey, realmObjectSource.realmGet$last_msg());
        builder.addString(columnInfo.last_msg_timeColKey, realmObjectSource.realmGet$last_msg_time());
        builder.addString(columnInfo.dialog_room_jidColKey, realmObjectSource.realmGet$dialog_room_jid());
        builder.addInteger(columnInfo.opponent_doc_idColKey, realmObjectSource.realmGet$opponent_doc_id());
        builder.addInteger(columnInfo.dialog_creation_dateColKey, realmObjectSource.realmGet$dialog_creation_date());
        builder.addInteger(columnInfo.member_added_dateColKey, realmObjectSource.realmGet$member_added_date());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBGroupDialogColumnInfo columnInfo = (RealmQBGroupDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_id();
        if (realmGet$dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
        }
        String realmGet$dialog_title = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_title();
        if (realmGet$dialog_title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_titleColKey, colKey, realmGet$dialog_title, false);
        }
        String realmGet$dialog_pic_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_pic_name();
        if (realmGet$dialog_pic_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_pic_nameColKey, colKey, realmGet$dialog_pic_name, false);
        }
        String realmGet$dialog_pic_url = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_pic_url();
        if (realmGet$dialog_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_pic_urlColKey, colKey, realmGet$dialog_pic_url, false);
        }
        String realmGet$last_msg_sent_qb_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg_sent_qb_id();
        if (realmGet$last_msg_sent_qb_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, realmGet$last_msg_sent_qb_id, false);
        }
        String realmGet$local_dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$local_dialog_type();
        if (realmGet$local_dialog_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, realmGet$local_dialog_type, false);
        }
        String realmGet$last_msg = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg();
        if (realmGet$last_msg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_msgColKey, colKey, realmGet$last_msg, false);
        }
        String realmGet$last_msg_time = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg_time();
        if (realmGet$last_msg_time != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, realmGet$last_msg_time, false);
        }
        String realmGet$dialog_room_jid = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_room_jid();
        if (realmGet$dialog_room_jid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_room_jidColKey, colKey, realmGet$dialog_room_jid, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.opponent_doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$opponent_doc_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.dialog_creation_dateColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_creation_date(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.member_added_dateColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$member_added_date(), false);
        return colKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBGroupDialogColumnInfo columnInfo = (RealmQBGroupDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_id();
            if (realmGet$dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
            }
            String realmGet$dialog_title = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_title();
            if (realmGet$dialog_title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_titleColKey, colKey, realmGet$dialog_title, false);
            }
            String realmGet$dialog_pic_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_pic_name();
            if (realmGet$dialog_pic_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_pic_nameColKey, colKey, realmGet$dialog_pic_name, false);
            }
            String realmGet$dialog_pic_url = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_pic_url();
            if (realmGet$dialog_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_pic_urlColKey, colKey, realmGet$dialog_pic_url, false);
            }
            String realmGet$last_msg_sent_qb_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg_sent_qb_id();
            if (realmGet$last_msg_sent_qb_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, realmGet$last_msg_sent_qb_id, false);
            }
            String realmGet$local_dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$local_dialog_type();
            if (realmGet$local_dialog_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, realmGet$local_dialog_type, false);
            }
            String realmGet$last_msg = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg();
            if (realmGet$last_msg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_msgColKey, colKey, realmGet$last_msg, false);
            }
            String realmGet$last_msg_time = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg_time();
            if (realmGet$last_msg_time != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, realmGet$last_msg_time, false);
            }
            String realmGet$dialog_room_jid = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_room_jid();
            if (realmGet$dialog_room_jid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_room_jidColKey, colKey, realmGet$dialog_room_jid, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.opponent_doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$opponent_doc_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.dialog_creation_dateColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_creation_date(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.member_added_dateColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$member_added_date(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBGroupDialogColumnInfo columnInfo = (RealmQBGroupDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        long colKey = OsObject.createRow(table);
        cache.put(object, colKey);
        String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_id();
        if (realmGet$dialog_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_idColKey, colKey, false);
        }
        String realmGet$dialog_title = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_title();
        if (realmGet$dialog_title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_titleColKey, colKey, realmGet$dialog_title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_titleColKey, colKey, false);
        }
        String realmGet$dialog_pic_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_pic_name();
        if (realmGet$dialog_pic_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_pic_nameColKey, colKey, realmGet$dialog_pic_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_pic_nameColKey, colKey, false);
        }
        String realmGet$dialog_pic_url = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_pic_url();
        if (realmGet$dialog_pic_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_pic_urlColKey, colKey, realmGet$dialog_pic_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_pic_urlColKey, colKey, false);
        }
        String realmGet$last_msg_sent_qb_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg_sent_qb_id();
        if (realmGet$last_msg_sent_qb_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, realmGet$last_msg_sent_qb_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, false);
        }
        String realmGet$local_dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$local_dialog_type();
        if (realmGet$local_dialog_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, realmGet$local_dialog_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, false);
        }
        String realmGet$last_msg = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg();
        if (realmGet$last_msg != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_msgColKey, colKey, realmGet$last_msg, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.last_msgColKey, colKey, false);
        }
        String realmGet$last_msg_time = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg_time();
        if (realmGet$last_msg_time != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, realmGet$last_msg_time, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, false);
        }
        String realmGet$dialog_room_jid = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_room_jid();
        if (realmGet$dialog_room_jid != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dialog_room_jidColKey, colKey, realmGet$dialog_room_jid, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dialog_room_jidColKey, colKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.opponent_doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$opponent_doc_id(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.dialog_creation_dateColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_creation_date(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.member_added_dateColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$member_added_date(), false);
        return colKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        long tableNativePtr = table.getNativePtr();
        RealmQBGroupDialogColumnInfo columnInfo = (RealmQBGroupDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog object = null;
        while (objects.hasNext()) {
            object = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long colKey = OsObject.createRow(table);
            cache.put(object, colKey);
            String realmGet$dialog_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_id();
            if (realmGet$dialog_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_idColKey, colKey, realmGet$dialog_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_idColKey, colKey, false);
            }
            String realmGet$dialog_title = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_title();
            if (realmGet$dialog_title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_titleColKey, colKey, realmGet$dialog_title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_titleColKey, colKey, false);
            }
            String realmGet$dialog_pic_name = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_pic_name();
            if (realmGet$dialog_pic_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_pic_nameColKey, colKey, realmGet$dialog_pic_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_pic_nameColKey, colKey, false);
            }
            String realmGet$dialog_pic_url = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_pic_url();
            if (realmGet$dialog_pic_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_pic_urlColKey, colKey, realmGet$dialog_pic_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_pic_urlColKey, colKey, false);
            }
            String realmGet$last_msg_sent_qb_id = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg_sent_qb_id();
            if (realmGet$last_msg_sent_qb_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, realmGet$last_msg_sent_qb_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.last_msg_sent_qb_idColKey, colKey, false);
            }
            String realmGet$local_dialog_type = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$local_dialog_type();
            if (realmGet$local_dialog_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, realmGet$local_dialog_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.local_dialog_typeColKey, colKey, false);
            }
            String realmGet$last_msg = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg();
            if (realmGet$last_msg != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_msgColKey, colKey, realmGet$last_msg, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.last_msgColKey, colKey, false);
            }
            String realmGet$last_msg_time = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$last_msg_time();
            if (realmGet$last_msg_time != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, realmGet$last_msg_time, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.last_msg_timeColKey, colKey, false);
            }
            String realmGet$dialog_room_jid = ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_room_jid();
            if (realmGet$dialog_room_jid != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.dialog_room_jidColKey, colKey, realmGet$dialog_room_jid, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dialog_room_jidColKey, colKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.opponent_doc_idColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$opponent_doc_id(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.dialog_creation_dateColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$dialog_creation_date(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.member_added_dateColKey, colKey, ((com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) object).realmGet$member_added_date(), false);
        }
    }

    public static com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog createDetachedCopy(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) cachedObject.object;
            }
            unmanagedObject = (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface unmanagedCopy = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) unmanagedObject;
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface realmSource = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$dialog_id(realmSource.realmGet$dialog_id());
        unmanagedCopy.realmSet$dialog_title(realmSource.realmGet$dialog_title());
        unmanagedCopy.realmSet$dialog_pic_name(realmSource.realmGet$dialog_pic_name());
        unmanagedCopy.realmSet$dialog_pic_url(realmSource.realmGet$dialog_pic_url());
        unmanagedCopy.realmSet$last_msg_sent_qb_id(realmSource.realmGet$last_msg_sent_qb_id());
        unmanagedCopy.realmSet$local_dialog_type(realmSource.realmGet$local_dialog_type());
        unmanagedCopy.realmSet$last_msg(realmSource.realmGet$last_msg());
        unmanagedCopy.realmSet$last_msg_time(realmSource.realmGet$last_msg_time());
        unmanagedCopy.realmSet$dialog_room_jid(realmSource.realmGet$dialog_room_jid());
        unmanagedCopy.realmSet$opponent_doc_id(realmSource.realmGet$opponent_doc_id());
        unmanagedCopy.realmSet$dialog_creation_date(realmSource.realmGet$dialog_creation_date());
        unmanagedCopy.realmSet$member_added_date(realmSource.realmGet$member_added_date());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmQBGroupDialog = proxy[");
        stringBuilder.append("{dialog_id:");
        stringBuilder.append(realmGet$dialog_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialog_title:");
        stringBuilder.append(realmGet$dialog_title() != null ? realmGet$dialog_title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialog_pic_name:");
        stringBuilder.append(realmGet$dialog_pic_name() != null ? realmGet$dialog_pic_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialog_pic_url:");
        stringBuilder.append(realmGet$dialog_pic_url() != null ? realmGet$dialog_pic_url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_msg_sent_qb_id:");
        stringBuilder.append(realmGet$last_msg_sent_qb_id() != null ? realmGet$last_msg_sent_qb_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{local_dialog_type:");
        stringBuilder.append(realmGet$local_dialog_type() != null ? realmGet$local_dialog_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_msg:");
        stringBuilder.append(realmGet$last_msg() != null ? realmGet$last_msg() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{last_msg_time:");
        stringBuilder.append(realmGet$last_msg_time() != null ? realmGet$last_msg_time() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialog_room_jid:");
        stringBuilder.append(realmGet$dialog_room_jid() != null ? realmGet$dialog_room_jid() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{opponent_doc_id:");
        stringBuilder.append(realmGet$opponent_doc_id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dialog_creation_date:");
        stringBuilder.append(realmGet$dialog_creation_date());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{member_added_date:");
        stringBuilder.append(realmGet$member_added_date());
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
        com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy aRealmQBGroupDialog = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aRealmQBGroupDialog.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aRealmQBGroupDialog.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aRealmQBGroupDialog.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
