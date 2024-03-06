package com.vam.whitecoats.databinding;
import com.vam.whitecoats.R;
import com.vam.whitecoats.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FeedsNotofictionRowLayoutBindingImpl extends FeedsNotofictionRowLayoutBinding implements com.vam.whitecoats.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView2;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FeedsNotofictionRowLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private FeedsNotofictionRowLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[3]
            );
        this.imageView.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.time.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.vam.whitecoats.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
        if (BR.position == variableId) {
            setPosition((java.lang.Integer) variable);
        }
        else if (BR.itemClickListener == variableId) {
            setItemClickListener((com.vam.whitecoats.ui.interfaces.NotificationItemClickListener) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.vam.whitecoats.viewmodel.NotificationItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPosition(@Nullable java.lang.Integer Position) {
        this.mPosition = Position;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.position);
        super.requestRebind();
    }
    public void setItemClickListener(@Nullable com.vam.whitecoats.ui.interfaces.NotificationItemClickListener ItemClickListener) {
        this.mItemClickListener = ItemClickListener;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.itemClickListener);
        super.requestRebind();
    }
    public void setViewModel(@Nullable com.vam.whitecoats.viewmodel.NotificationItemViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        com.vam.whitecoats.core.models.NotificationInfo viewModelNotificationInfo = null;
        boolean viewModelNotificationInfoIsNotificationRead = false;
        java.lang.Integer position = mPosition;
        com.vam.whitecoats.ui.interfaces.NotificationItemClickListener itemClickListener = mItemClickListener;
        com.vam.whitecoats.viewmodel.NotificationItemViewModel viewModel = mViewModel;
        android.text.Spannable viewModelTitle = null;
        java.lang.String viewModelTimeDifference = null;

        if ((dirtyFlags & 0xcL) != 0) {



                if (viewModel != null) {
                    // read viewModel.notificationInfo
                    viewModelNotificationInfo = viewModel.getNotificationInfo();
                    // read viewModel.title
                    viewModelTitle = viewModel.getTitle();
                    // read viewModel.timeDifference
                    viewModelTimeDifference = viewModel.getTimeDifference();
                }


                if (viewModelNotificationInfo != null) {
                    // read viewModel.notificationInfo.isNotificationRead()
                    viewModelNotificationInfoIsNotificationRead = viewModelNotificationInfo.isNotificationRead();
                }
        }
        // batch finished
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.notificationItemImage(this.imageView, viewModelNotificationInfo);
            com.vam.whitecoats.databinding.RecyclerViewDataBinding.notificationItemBackgroundColor(this.mboundView0, viewModelNotificationInfoIsNotificationRead);
            this.mboundView2.setText(viewModelTitle);
            this.time.setText(viewModelTimeDifference);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.mboundView0.setOnClickListener(mCallback1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel.notificationInfo
        com.vam.whitecoats.core.models.NotificationInfo viewModelNotificationInfo = null;
        // position
        java.lang.Integer position = mPosition;
        // itemClickListener
        com.vam.whitecoats.ui.interfaces.NotificationItemClickListener itemClickListener = mItemClickListener;
        // viewModel
        com.vam.whitecoats.viewmodel.NotificationItemViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;
        // itemClickListener != null
        boolean itemClickListenerJavaLangObjectNull = false;



        itemClickListenerJavaLangObjectNull = (itemClickListener) != (null);
        if (itemClickListenerJavaLangObjectNull) {



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelNotificationInfo = viewModel.getNotificationInfo();


                itemClickListener.onItemClick(viewModelNotificationInfo, position);
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): position
        flag 1 (0x2L): itemClickListener
        flag 2 (0x3L): viewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}