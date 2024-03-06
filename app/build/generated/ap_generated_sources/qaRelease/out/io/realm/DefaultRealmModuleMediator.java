package io.realm;


import android.util.JsonReader;
import io.realm.ImportFlag;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>(34);
        modelClasses.add(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
        modelClasses.add(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
        modelClasses.add(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
        modelClasses.add(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmCardInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmFeedInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmFeedsList.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmLogin.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmMessages.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmNotifications.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmOnlinePub.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmPrintPub.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmPublications.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
        modelClasses.add(com.vam.whitecoats.core.realm.UserEvents.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        Map<Class<? extends RealmModel>, OsObjectSchemaInfo> infoMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>(34);
        infoMap.put(com.vam.whitecoats.core.models.RealmNotificationInfo.class, io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class, io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class, io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class, io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmAcademicInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmBasicInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmCardInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class, io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class, io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmChannelsInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmEventsInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmFeedInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmFeedsList.class, io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmGroupNotifications.class, io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmLogin.class, io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmMessages.class, io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmNotifications.class, io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmOnlinePub.class, io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmPrintPub.class, io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class, io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmPublications.class, io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class, io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.vam.whitecoats.core.realm.UserEvents.class, io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.getExpectedObjectSchemaInfo());
        return infoMap;
    }

    @Override
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> clazz, OsSchemaInfo schemaInfo) {
        checkClass(clazz);

        if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
            return io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
            return io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
            return io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
            return io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
            return io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
            return io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.createColumnInfo(schemaInfo);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public String getSimpleClassNameImpl(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
            return "RealmNotificationInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
            return "RealmQBDialog";
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
            return "RealmQBDialogMemInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
            return "RealmQBGroupDialog";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
            return "RealmAcademicInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
            return "RealmAdSlotInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
            return "RealmAreasOfInterestInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
            return "RealmBasicInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
            return "RealmCardInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
            return "RealmCaseRoomAttachmentsInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
            return "RealmCaseRoomInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
            return "RealmCaseRoomMemberInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
            return "RealmCaseRoomNotifications";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
            return "RealmCaseRoomPatientDetailsInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
            return "RealmChannelFeedInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
            return "RealmChannelFeedsList";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
            return "RealmChannelsInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
            return "RealmEventsInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
            return "RealmFeedInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
            return "RealmFeedsList";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
            return "RealmGroupNotifications";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
            return "RealmLogin";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
            return "RealmMessages";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
            return "RealmMyContactsInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
            return "RealmNotifications";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
            return "RealmNotificationSettingsInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
            return "RealmOnlinePub";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
            return "RealmPrintPub";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
            return "RealmProfessionalInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
            return "RealmProfessionalMembership";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
            return "RealmPublications";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
            return "RealmShareFailedFeedInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
            return "RealmSymptomsInfo";
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
            return "UserEvents";
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy());
            }
            if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
                return clazz.cast(new io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy());
            }
            throw getMissingProxyClassException(clazz);
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
            com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.RealmNotificationInfoColumnInfo columnInfo = (com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.RealmNotificationInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.models.RealmNotificationInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.models.RealmNotificationInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
            com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.RealmQBDialogColumnInfo columnInfo = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.RealmQBDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
            com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.RealmQBDialogMemInfoColumnInfo columnInfo = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.RealmQBDialogMemInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
            com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.RealmQBGroupDialogColumnInfo columnInfo = (com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.RealmQBGroupDialogColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
            com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.RealmAcademicInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.RealmAcademicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAcademicInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmAcademicInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
            com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.RealmAdSlotInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.RealmAdSlotInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmAdSlotInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
            com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.RealmAreasOfInterestInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.RealmAreasOfInterestInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
            com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.RealmBasicInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.RealmBasicInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmBasicInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmBasicInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
            com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.RealmCardInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.RealmCardInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCardInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmCardInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
            com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.RealmCaseRoomAttachmentsInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.RealmCaseRoomAttachmentsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
            com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.RealmCaseRoomInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.RealmCaseRoomInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
            com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.RealmCaseRoomMemberInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.RealmCaseRoomMemberInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
            com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.RealmCaseRoomNotificationsColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.RealmCaseRoomNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
            com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.RealmCaseRoomPatientDetailsInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.RealmCaseRoomPatientDetailsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
            com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.RealmChannelFeedInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.RealmChannelFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
            com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.RealmChannelFeedsListColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.RealmChannelFeedsListColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmChannelFeedsList) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
            com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.RealmChannelsInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.RealmChannelsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmChannelsInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmChannelsInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
            com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.RealmEventsInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.RealmEventsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmEventsInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmEventsInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
            com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.RealmFeedInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.RealmFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmFeedInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
            com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.RealmFeedsListColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.RealmFeedsListColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmFeedsList.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmFeedsList) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
            com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.RealmGroupNotificationsColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.RealmGroupNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmGroupNotifications.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmGroupNotifications) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
            com_vam_whitecoats_core_realm_RealmLoginRealmProxy.RealmLoginColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmLoginRealmProxy.RealmLoginColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmLogin.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmLogin) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
            com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.RealmMessagesColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.RealmMessagesColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMessages.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmMessages) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
            com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.RealmMyContactsInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.RealmMyContactsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmMyContactsInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
            com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.RealmNotificationsColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.RealmNotificationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotifications.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmNotifications) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
            com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.RealmNotificationSettingsInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.RealmNotificationSettingsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
            com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.RealmOnlinePubColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.RealmOnlinePubColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmOnlinePub.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmOnlinePub) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
            com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.RealmPrintPubColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.RealmPrintPubColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPrintPub.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmPrintPub) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
            com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.RealmProfessionalInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.RealmProfessionalInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmProfessionalInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
            com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.RealmProfessionalMembershipColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.RealmProfessionalMembershipColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmProfessionalMembership) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
            com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.RealmPublicationsColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.RealmPublicationsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmPublications.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmPublications) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
            com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.RealmShareFailedFeedInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.RealmShareFailedFeedInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
            com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.RealmSymptomsInfoColumnInfo columnInfo = (com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.RealmSymptomsInfoColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.RealmSymptomsInfo) obj, update, cache, flags));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
            com_vam_whitecoats_core_realm_UserEventsRealmProxy.UserEventsColumnInfo columnInfo = (com_vam_whitecoats_core_realm_UserEventsRealmProxy.UserEventsColumnInfo) realm.getSchema().getColumnInfo(com.vam.whitecoats.core.realm.UserEvents.class);
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.copyOrUpdate(realm, columnInfo, (com.vam.whitecoats.core.realm.UserEvents) obj, update, cache, flags));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
            io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.models.RealmNotificationInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
            io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.insert(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
            io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
            io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.insert(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmAcademicInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmAdSlotInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmBasicInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCardInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmChannelFeedsList) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmChannelsInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmEventsInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmFeedInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmFeedsList) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmGroupNotifications) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmLogin) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmMessages) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmMyContactsInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmNotifications) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmOnlinePub) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmPrintPub) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmProfessionalInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmProfessionalMembership) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmPublications) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmSymptomsInfo) object, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
            io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.UserEvents) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
                io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.models.RealmNotificationInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
                io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.insert(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
                io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
                io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.insert(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmAcademicInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmAdSlotInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmBasicInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCardInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmChannelFeedsList) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmChannelsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmEventsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmFeedInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmFeedsList) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmGroupNotifications) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmLogin) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmMessages) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmMyContactsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmNotifications) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmOnlinePub) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmPrintPub) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmProfessionalInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmProfessionalMembership) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmPublications) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.RealmSymptomsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
                io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.insert(realm, (com.vam.whitecoats.core.realm.UserEvents) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
                    io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
                    io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
                    io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
                    io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
                    io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
            io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.models.RealmNotificationInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
            io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
            io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
            io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmAcademicInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmAdSlotInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmBasicInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCardInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmChannelFeedsList) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmChannelsInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmEventsInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmFeedInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmFeedsList) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmGroupNotifications) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmLogin) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmMessages) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmMyContactsInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmNotifications) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmOnlinePub) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmPrintPub) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmProfessionalInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmProfessionalMembership) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmPublications) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
            io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmSymptomsInfo) obj, cache);
        } else if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
            io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.UserEvents) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
                io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.models.RealmNotificationInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
                io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
                io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
                io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmAcademicInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmAdSlotInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmBasicInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCardInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmChannelFeedInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmChannelFeedsList) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmChannelsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmEventsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmFeedInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmFeedsList) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmGroupNotifications) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmLogin) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmMessages) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmMyContactsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmNotifications) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmOnlinePub) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmPrintPub) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmProfessionalInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmProfessionalMembership) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmPublications) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
                io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.RealmSymptomsInfo) object, cache);
            } else if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
                io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.insertOrUpdate(realm, (com.vam.whitecoats.core.realm.UserEvents) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
                    io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
                    io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
                    io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
                    io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
                    io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
                    io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.createUsingJsonStream(realm, reader));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(com.vam.whitecoats.core.models.RealmNotificationInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_models_RealmNotificationInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.models.RealmNotificationInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogRealmProxy.createDetachedCopy((com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBDialogMemInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialogMemInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_quickblox_qbrealm_RealmQBGroupDialogRealmProxy.createDetachedCopy((com.vam.whitecoats.core.quickblox.qbrealm.RealmQBGroupDialog) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAcademicInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAcademicInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmAcademicInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAdSlotInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAdSlotInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmAdSlotInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmAreasOfInterestInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmAreasOfInterestInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmBasicInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmBasicInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmBasicInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCardInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCardInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmCardInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomAttachmentsInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmCaseRoomAttachmentsInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmCaseRoomInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomMemberInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmCaseRoomMemberInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomNotificationsRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmCaseRoomNotifications) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmCaseRoomPatientDetailsInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmCaseRoomPatientDetailsInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmChannelFeedInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelFeedsList.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelFeedsListRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmChannelFeedsList) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmChannelsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmChannelsInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmChannelsInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmEventsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmEventsInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmEventsInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmFeedInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmFeedInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmFeedsList.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmFeedsListRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmFeedsList) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmGroupNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmGroupNotificationsRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmGroupNotifications) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmLogin.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmLoginRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmLogin) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMessages.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmMessagesRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmMessages) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmMyContactsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmMyContactsInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmMyContactsInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotifications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmNotificationsRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmNotifications) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmNotificationSettingsInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmOnlinePub.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmOnlinePubRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmOnlinePub) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPrintPub.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmPrintPubRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmPrintPub) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmProfessionalInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmProfessionalInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmProfessionalMembership.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmProfessionalMembershipRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmProfessionalMembership) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmPublications.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmPublicationsRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmPublications) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmShareFailedFeedInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmShareFailedFeedInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.RealmSymptomsInfo.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_RealmSymptomsInfoRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.RealmSymptomsInfo) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.vam.whitecoats.core.realm.UserEvents.class)) {
            return clazz.cast(io.realm.com_vam_whitecoats_core_realm_UserEventsRealmProxy.createDetachedCopy((com.vam.whitecoats.core.realm.UserEvents) realmObject, 0, maxDepth, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

}
