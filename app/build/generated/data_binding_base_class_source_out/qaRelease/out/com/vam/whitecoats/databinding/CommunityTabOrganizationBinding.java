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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.adapters.DrugClassAdapter;
import com.vam.whitecoats.viewmodel.DrugClassViewModel;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class CommunityTabOrganizationBinding extends ViewDataBinding {
  @NonNull
  public final AVLoadingIndicatorView aviInExplore;

  @NonNull
  public final SwipeRefreshLayout drugClassRefresh;

  @NonNull
  public final RecyclerView drugRecycler;

  @NonNull
  public final SearchLayoutBinding drugSearchLayout;

  @Bindable
  protected DrugClassViewModel mViewModel;

  @Bindable
  protected DrugClassAdapter mAdapter;

  protected CommunityTabOrganizationBinding(Object _bindingComponent, View _root,
      int _localFieldCount, AVLoadingIndicatorView aviInExplore,
      SwipeRefreshLayout drugClassRefresh, RecyclerView drugRecycler,
      SearchLayoutBinding drugSearchLayout) {
    super(_bindingComponent, _root, _localFieldCount);
    this.aviInExplore = aviInExplore;
    this.drugClassRefresh = drugClassRefresh;
    this.drugRecycler = drugRecycler;
    this.drugSearchLayout = drugSearchLayout;
  }

  public abstract void setViewModel(@Nullable DrugClassViewModel viewModel);

  @Nullable
  public DrugClassViewModel getViewModel() {
    return mViewModel;
  }

  public abstract void setAdapter(@Nullable DrugClassAdapter adapter);

  @Nullable
  public DrugClassAdapter getAdapter() {
    return mAdapter;
  }

  @NonNull
  public static CommunityTabOrganizationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.community_tab_organization, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static CommunityTabOrganizationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<CommunityTabOrganizationBinding>inflateInternal(inflater, R.layout.community_tab_organization, root, attachToRoot, component);
  }

  @NonNull
  public static CommunityTabOrganizationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.community_tab_organization, null, false, component)
   */
  @NonNull
  @Deprecated
  public static CommunityTabOrganizationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<CommunityTabOrganizationBinding>inflateInternal(inflater, R.layout.community_tab_organization, null, false, component);
  }

  public static CommunityTabOrganizationBinding bind(@NonNull View view) {
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
  public static CommunityTabOrganizationBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (CommunityTabOrganizationBinding)bind(component, view, R.layout.community_tab_organization);
  }
}
