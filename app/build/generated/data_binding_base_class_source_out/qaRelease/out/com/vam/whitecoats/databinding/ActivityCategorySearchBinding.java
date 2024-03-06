// Generated by data binding compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.adapters.FeedsCategoryDistributionAdapter;
import com.vam.whitecoats.viewmodel.CategorySearchViewModel;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityCategorySearchBinding extends ViewDataBinding {
  @NonNull
  public final AVLoadingIndicatorView aviInExplore;

  @NonNull
  public final RecyclerView categorySearchList;

  @Bindable
  protected CategorySearchViewModel mViewModel;

  @Bindable
  protected FeedsCategoryDistributionAdapter mAdapter;

  protected ActivityCategorySearchBinding(Object _bindingComponent, View _root,
      int _localFieldCount, AVLoadingIndicatorView aviInExplore, RecyclerView categorySearchList) {
    super(_bindingComponent, _root, _localFieldCount);
    this.aviInExplore = aviInExplore;
    this.categorySearchList = categorySearchList;
  }

  public abstract void setViewModel(@Nullable CategorySearchViewModel viewModel);

  @Nullable
  public CategorySearchViewModel getViewModel() {
    return mViewModel;
  }

  public abstract void setAdapter(@Nullable FeedsCategoryDistributionAdapter adapter);

  @Nullable
  public FeedsCategoryDistributionAdapter getAdapter() {
    return mAdapter;
  }

  @NonNull
  public static ActivityCategorySearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_category_search, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityCategorySearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityCategorySearchBinding>inflateInternal(inflater, R.layout.activity_category_search, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityCategorySearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_category_search, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityCategorySearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityCategorySearchBinding>inflateInternal(inflater, R.layout.activity_category_search, null, false, component);
  }

  public static ActivityCategorySearchBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityCategorySearchBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityCategorySearchBinding)bind(component, view, R.layout.activity_category_search);
  }
}
