package com.vam.whitecoats.databinding;
import com.vam.whitecoats.R;
import com.vam.whitecoats.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class NoSubCategoryDistributionItemBindingImpl extends NoSubCategoryDistributionItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.card_lay, 1);
        sViewsWithIds.put(R.id.category_feed_image, 2);
        sViewsWithIds.put(R.id.attachment_icon1, 3);
        sViewsWithIds.put(R.id.attachment_video_type, 4);
        sViewsWithIds.put(R.id.attachment_video_play, 5);
        sViewsWithIds.put(R.id.feed_title, 6);
        sViewsWithIds.put(R.id.feed_desc, 7);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public NoSubCategoryDistributionItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private NoSubCategoryDistributionItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.FrameLayout) bindings[1]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[6]
            , (android.widget.RelativeLayout) bindings[0]
            );
        this.feedsLay.setTag(null);
        setRootTag(root);
        // listeners
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
            setItemClickListener((com.vam.whitecoats.ui.interfaces.CategoryFeedsItemClickListener) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.vam.whitecoats.viewmodel.NoSubCategoriesItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPosition(@Nullable java.lang.Integer Position) {
        this.mPosition = Position;
    }
    public void setItemClickListener(@Nullable com.vam.whitecoats.ui.interfaces.CategoryFeedsItemClickListener ItemClickListener) {
        this.mItemClickListener = ItemClickListener;
    }
    public void setViewModel(@Nullable com.vam.whitecoats.viewmodel.NoSubCategoriesItemViewModel ViewModel) {
        this.mViewModel = ViewModel;
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
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
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