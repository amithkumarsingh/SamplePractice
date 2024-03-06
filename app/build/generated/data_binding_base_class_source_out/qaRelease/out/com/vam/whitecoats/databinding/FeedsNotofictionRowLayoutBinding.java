// Generated by data binding compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.interfaces.NotificationItemClickListener;
import com.vam.whitecoats.viewmodel.NotificationItemViewModel;
import java.lang.Deprecated;
import java.lang.Integer;
import java.lang.Object;

public abstract class FeedsNotofictionRowLayoutBinding extends ViewDataBinding {
  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextView time;

  @Bindable
  protected Integer mPosition;

  @Bindable
  protected NotificationItemViewModel mViewModel;

  @Bindable
  protected NotificationItemClickListener mItemClickListener;

  protected FeedsNotofictionRowLayoutBinding(Object _bindingComponent, View _root,
      int _localFieldCount, ImageView imageView, TextView time) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imageView = imageView;
    this.time = time;
  }

  public abstract void setPosition(@Nullable Integer position);

  @Nullable
  public Integer getPosition() {
    return mPosition;
  }

  public abstract void setViewModel(@Nullable NotificationItemViewModel viewModel);

  @Nullable
  public NotificationItemViewModel getViewModel() {
    return mViewModel;
  }

  public abstract void setItemClickListener(
      @Nullable NotificationItemClickListener itemClickListener);

  @Nullable
  public NotificationItemClickListener getItemClickListener() {
    return mItemClickListener;
  }

  @NonNull
  public static FeedsNotofictionRowLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.feeds_notofiction_row_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FeedsNotofictionRowLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FeedsNotofictionRowLayoutBinding>inflateInternal(inflater, R.layout.feeds_notofiction_row_layout, root, attachToRoot, component);
  }

  @NonNull
  public static FeedsNotofictionRowLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.feeds_notofiction_row_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FeedsNotofictionRowLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FeedsNotofictionRowLayoutBinding>inflateInternal(inflater, R.layout.feeds_notofiction_row_layout, null, false, component);
  }

  public static FeedsNotofictionRowLayoutBinding bind(@NonNull View view) {
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
  public static FeedsNotofictionRowLayoutBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (FeedsNotofictionRowLayoutBinding)bind(component, view, R.layout.feeds_notofiction_row_layout);
  }
}
