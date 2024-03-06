package com.vam.whitecoats.databinding;
import com.vam.whitecoats.R;
import com.vam.whitecoats.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentFeedNotificationBindingImpl extends FragmentFeedNotificationBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.device_notification_on_rl, 5);
        sViewsWithIds.put(R.id.notification_on_iv, 6);
        sViewsWithIds.put(R.id.notification_on_close_iv, 7);
        sViewsWithIds.put(R.id.notification_on_header_tv, 8);
        sViewsWithIds.put(R.id.notification_on_desc_tv, 9);
        sViewsWithIds.put(R.id.notification_on_tv, 10);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView3;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentFeedNotificationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private FragmentFeedNotificationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (com.wang.avi.AVLoadingIndicatorView) bindings[4]
            , (androidx.cardview.widget.CardView) bindings[1]
            , (android.widget.RelativeLayout) bindings[5]
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.TextView) bindings[10]
            );
        this.aviInNotifications.setTag(null);
        this.deviceNotificationOnCv.setTag(null);
        this.feedsNotificationList.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x40L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.adapter == variableId) {
            setAdapter((com.vam.whitecoats.ui.adapters.FeedsNotificationsAdapter) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.vam.whitecoats.viewmodel.NotificationViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAdapter(@Nullable com.vam.whitecoats.ui.adapters.FeedsNotificationsAdapter Adapter) {
        this.mAdapter = Adapter;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.adapter);
        super.requestRebind();
    }
    public void setViewModel(@Nullable com.vam.whitecoats.viewmodel.NotificationViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x20L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelIsNotificationsEnabled((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 1 :
                return onChangeViewModelIsLoaderVisible((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 2 :
                return onChangeViewModelIsListVisible((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 3 :
                return onChangeViewModelIsEmptyMsgVisible((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelIsNotificationsEnabled(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelIsNotificationsEnabled, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelIsLoaderVisible(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelIsLoaderVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelIsListVisible(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelIsListVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelIsEmptyMsgVisible(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelIsEmptyMsgVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelIsNotificationsEnabled = null;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelIsLoaderVisible = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoaderVisibleGet = false;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelIsListVisible = null;
        java.lang.Boolean viewModelIsListVisibleGet = null;
        java.lang.Boolean viewModelIsNotificationsEnabledGet = null;
        java.lang.Boolean viewModelIsLoaderVisibleGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelIsListVisibleGet = false;
        java.lang.Boolean viewModelIsEmptyMsgVisibleGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelIsEmptyMsgVisibleGet = false;
        com.vam.whitecoats.ui.adapters.FeedsNotificationsAdapter adapter = mAdapter;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelIsNotificationsEnabledGet = false;
        com.vam.whitecoats.viewmodel.NotificationViewModel viewModel = mViewModel;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelIsEmptyMsgVisible = null;

        if ((dirtyFlags & 0x50L) != 0) {
        }
        if ((dirtyFlags & 0x6fL) != 0) {


            if ((dirtyFlags & 0x61L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.isNotificationsEnabled
                        viewModelIsNotificationsEnabled = viewModel.isNotificationsEnabled;
                    }
                    updateRegistration(0, viewModelIsNotificationsEnabled);


                    if (viewModelIsNotificationsEnabled != null) {
                        // read viewModel.isNotificationsEnabled.get()
                        viewModelIsNotificationsEnabledGet = viewModelIsNotificationsEnabled.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.isNotificationsEnabled.get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelIsNotificationsEnabledGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelIsNotificationsEnabledGet);
            }
            if ((dirtyFlags & 0x62L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.isLoaderVisible
                        viewModelIsLoaderVisible = viewModel.isLoaderVisible;
                    }
                    updateRegistration(1, viewModelIsLoaderVisible);


                    if (viewModelIsLoaderVisible != null) {
                        // read viewModel.isLoaderVisible.get()
                        viewModelIsLoaderVisibleGet = viewModelIsLoaderVisible.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.isLoaderVisible.get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoaderVisibleGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelIsLoaderVisibleGet);
            }
            if ((dirtyFlags & 0x64L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.isListVisible
                        viewModelIsListVisible = viewModel.isListVisible;
                    }
                    updateRegistration(2, viewModelIsListVisible);


                    if (viewModelIsListVisible != null) {
                        // read viewModel.isListVisible.get()
                        viewModelIsListVisibleGet = viewModelIsListVisible.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.isListVisible.get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelIsListVisibleGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelIsListVisibleGet);
            }
            if ((dirtyFlags & 0x68L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.isEmptyMsgVisible
                        viewModelIsEmptyMsgVisible = viewModel.isEmptyMsgVisible;
                    }
                    updateRegistration(3, viewModelIsEmptyMsgVisible);


                    if (viewModelIsEmptyMsgVisible != null) {
                        // read viewModel.isEmptyMsgVisible.get()
                        viewModelIsEmptyMsgVisibleGet = viewModelIsEmptyMsgVisible.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.isEmptyMsgVisible.get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelIsEmptyMsgVisibleGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelIsEmptyMsgVisibleGet);
            }
        }
        // batch finished
        if ((dirtyFlags & 0x62L) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.listVisibility(this.aviInNotifications, androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoaderVisibleGet);
        }
        if ((dirtyFlags & 0x61L) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.listVisibility(this.deviceNotificationOnCv, androidxDatabindingViewDataBindingSafeUnboxViewModelIsNotificationsEnabledGet);
        }
        if ((dirtyFlags & 0x50L) != 0) {
            // api target 1

            this.feedsNotificationList.setAdapter(adapter);
        }
        if ((dirtyFlags & 0x64L) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.listVisibility(this.feedsNotificationList, androidxDatabindingViewDataBindingSafeUnboxViewModelIsListVisibleGet);
        }
        if ((dirtyFlags & 0x68L) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.listVisibility(this.mboundView3, androidxDatabindingViewDataBindingSafeUnboxViewModelIsEmptyMsgVisibleGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.isNotificationsEnabled
        flag 1 (0x2L): viewModel.isLoaderVisible
        flag 2 (0x3L): viewModel.isListVisible
        flag 3 (0x4L): viewModel.isEmptyMsgVisible
        flag 4 (0x5L): adapter
        flag 5 (0x6L): viewModel
        flag 6 (0x7L): null
    flag mapping end*/
    //end
}