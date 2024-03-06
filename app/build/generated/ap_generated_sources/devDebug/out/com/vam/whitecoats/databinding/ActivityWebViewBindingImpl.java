package com.vam.whitecoats.databinding;
import com.vam.whitecoats.R;
import com.vam.whitecoats.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityWebViewBindingImpl extends ActivityWebViewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.web_linear_layout, 3);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityWebViewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private ActivityWebViewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.wang.avi.AVLoadingIndicatorView) bindings[2]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.LinearLayout) bindings[3]
            , (android.webkit.WebView) bindings[1]
            );
        this.aviInUrl.setTag(null);
        this.webContentLayout.setTag(null);
        this.webViewUrl.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.viewModel == variableId) {
            setViewModel((com.vam.whitecoats.viewmodel.WebViewViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.vam.whitecoats.viewmodel.WebViewViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelIsLoaderVisible((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
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

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.Boolean viewModelIsLoaderVisibleGet = null;
        com.vam.whitecoats.viewmodel.WebViewViewModel viewModel = mViewModel;
        java.lang.String viewModelWebViewUrl = null;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelIsLoaderVisible = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoaderVisibleGet = false;

        if ((dirtyFlags & 0x7L) != 0) {


            if ((dirtyFlags & 0x6L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.webViewUrl
                        viewModelWebViewUrl = viewModel.webViewUrl();
                    }
            }

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
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.listVisibility(this.aviInUrl, androidxDatabindingViewDataBindingSafeUnboxViewModelIsLoaderVisibleGet);
        }
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            com.vam.whitecoats.databinding.RecyclerViewDataBinding.loadWebUrl(this.webViewUrl, viewModelWebViewUrl);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.isLoaderVisible
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}