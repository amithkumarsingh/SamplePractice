package com.vam.whitecoats.databinding;
import com.vam.whitecoats.R;
import com.vam.whitecoats.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class NoSubCategoryDistributionBindingImpl extends NoSubCategoryDistributionBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.search_text_view, 4);
    }
    // views
    @NonNull
    private final android.widget.TextView mboundView2;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public NoSubCategoryDistributionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private NoSubCategoryDistributionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (com.wang.avi.AVLoadingIndicatorView) bindings[3]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[0]
            , (androidx.recyclerview.widget.RecyclerView) bindings[1]
            , (android.widget.TextView) bindings[4]
            );
        this.aviInExplore.setTag(null);
        this.exploreSwipeRefresh.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.noSubCategoriesList.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
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
            setAdapter((com.vam.whitecoats.ui.adapters.FeedsCategoryDistributionAdapter) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.vam.whitecoats.viewmodel.FeedCategoriesViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAdapter(@Nullable com.vam.whitecoats.ui.adapters.FeedsCategoryDistributionAdapter Adapter) {
        this.mAdapter = Adapter;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.adapter);
        super.requestRebind();
    }
    public void setViewModel(@Nullable com.vam.whitecoats.viewmodel.FeedCategoriesViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelIsLoaderVisible((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 1 :
                return onChangeViewModelIsEmptyMsgVisible((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelIsLoaderVisible(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelIsLoaderVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelIsEmptyMsgVisible(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelIsEmptyMsgVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
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
        java.lang.Boolean viewModelIsLoaderVisibleGet = null;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelIsLoaderVisible = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoaderVisibleGet = false;
        java.lang.Boolean viewModelIsEmptyMsgVisibleGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelIsEmptyMsgVisibleGet = false;
        com.vam.whitecoats.ui.adapters.FeedsCategoryDistributionAdapter adapter = mAdapter;
        com.vam.whitecoats.viewmodel.FeedCategoriesViewModel viewModel = mViewModel;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelIsEmptyMsgVisible = null;

        if ((dirtyFlags & 0x14L) != 0) {
        }
        if ((dirtyFlags & 0x1bL) != 0) {


            if ((dirtyFlags & 0x19L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.isLoaderVisible
                        viewModelIsLoaderVisible = viewModel.isLoaderVisible;
                    }
                    updateRegistration(0, viewModelIsLoaderVisible);


                    if (viewModelIsLoaderVisible != null) {
                        // read viewModel.isLoaderVisible.get()
                        viewModelIsLoaderVisibleGet = viewModelIsLoaderVisible.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.isLoaderVisible.get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoaderVisibleGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelIsLoaderVisibleGet);
            }
            if ((dirtyFlags & 0x1aL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.isEmptyMsgVisible
                        viewModelIsEmptyMsgVisible = viewModel.isEmptyMsgVisible;
                    }
                    updateRegistration(1, viewModelIsEmptyMsgVisible);


                    if (viewModelIsEmptyMsgVisible != null) {
                        // read viewModel.isEmptyMsgVisible.get()
                        viewModelIsEmptyMsgVisibleGet = viewModelIsEmptyMsgVisible.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.isEmptyMsgVisible.get())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelIsEmptyMsgVisibleGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelIsEmptyMsgVisibleGet);
            }
        }
        // batch finished
        if ((dirtyFlags & 0x19L) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.listVisibility(this.aviInExplore, androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoaderVisibleGet);
        }
        if ((dirtyFlags & 0x1aL) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.listVisibility(this.mboundView2, androidxDatabindingViewDataBindingSafeUnboxViewModelIsEmptyMsgVisibleGet);
        }
        if ((dirtyFlags & 0x14L) != 0) {
            // api target 1

            this.noSubCategoriesList.setAdapter(adapter);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.isLoaderVisible
        flag 1 (0x2L): viewModel.isEmptyMsgVisible
        flag 2 (0x3L): adapter
        flag 3 (0x4L): viewModel
        flag 4 (0x5L): null
    flag mapping end*/
    //end
}