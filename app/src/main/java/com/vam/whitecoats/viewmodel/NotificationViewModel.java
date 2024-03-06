package com.vam.whitecoats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableField;

import com.vam.whitecoats.core.models.NotificationInfo;
import com.vam.whitecoats.databinding.NotificationsRepository;
import com.vam.whitecoats.utils.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationViewModel extends ViewModel {
    //private FeedsNotificationsAdapter adapter;
    private NotificationsRepository mRepo =new NotificationsRepository();
    List<NotificationInfo> data= new ArrayList<>();
    MutableLiveData<NotificationInfo> notificationInfoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ApiResponse> notificationsList=new MutableLiveData<>();
    public ObservableField<Boolean> isListVisible=new ObservableField<>();
    public ObservableField<Boolean> isEmptyMsgVisible=new ObservableField<>();
    public ObservableField<Boolean> isLoaderVisible=new ObservableField<>();
    public ObservableField<Boolean> isNotificationsEnabled=new ObservableField<>();

    public NotificationViewModel(){
    }

    /*private NotificationItemClickListener listener = new NotificationItemClickListener() {
        @Override
        public void onItemClick(NotificationInfo notificationInfo) {
            notificationInfoMutableLiveData.setValue(notificationInfo);
        }
    };*/
    /*public void setAdapter(){
        adapter= new FeedsNotificationsAdapter(listener);
    }*/
    public void setRequestData(int requestType, String url, String request, String mTag, Map<String,String> headers){
        mRepo.initRequest(requestType,url,request,mTag,headers);
        //notificationsList= mRepo.getNotificationList();
    }
    public LiveData<ApiResponse> getAllNotifications() {
        return mRepo.getNotificationList();
    }

    public LiveData<Boolean> isListExhausted(){
        return mRepo.isListExhausted();
    }

    public void setListVisibility(boolean visibility){
        isListVisible.set(visibility);
    }

    public void setIsEmptyMsgVisibility(boolean visibility){
        isEmptyMsgVisible.set(visibility);
    }

    public void setIsNotificationsEnabled(boolean isEnabled){
        isNotificationsEnabled.set(isEnabled);
    }
    public void setIsLoaderVisible(boolean visible){
        isLoaderVisible.set(visible);
    }
    public void displayUIBasedOnCount(int count){
        if(count>0) {
            setListVisibility(true);
            setIsEmptyMsgVisibility(false);
        }else{
            setListVisibility(false);
            setIsEmptyMsgVisibility(true);
        }
        setIsLoaderVisible(false);
    }

    public void displayLoader(){
        setListVisibility(false);
        setIsEmptyMsgVisibility(false);
        setIsLoaderVisible(true);
    }
    /*public LiveData<NotificationInfo> getClickedItem() {
        return notificationInfoMutableLiveData;
    }*/
    //@Bindable
    public List<NotificationInfo> getData() {
        return this.data;
    }

    //@Bindable
    /*public FeedsNotificationsAdapter getAdapter() {
        return this.adapter;
    }

    public void setAdapter(FeedsNotificationsAdapter adapter) {
        this.adapter = adapter;
    }*/



    public void setData(List<NotificationInfo> data) {
        this.data = data;
    }



    /*public void updateList(List<NotificationInfo> data){
        this.data.addAll(data);
        this.adapter.notifyDataSetChanged();
    }*/

}
