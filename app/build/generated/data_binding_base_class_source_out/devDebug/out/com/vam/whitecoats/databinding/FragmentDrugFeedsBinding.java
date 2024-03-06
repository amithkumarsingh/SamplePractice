// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDrugFeedsBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final AVLoadingIndicatorView drugFeedsLoader;

  @NonNull
  public final RecyclerView drugFeedsNotificationList;

  @NonNull
  public final SwipeRefreshLayout drugFeedsSwipeRefresh;

  @NonNull
  public final TextView noDrugFeeds;

  private FragmentDrugFeedsBinding(@NonNull FrameLayout rootView,
      @NonNull AVLoadingIndicatorView drugFeedsLoader,
      @NonNull RecyclerView drugFeedsNotificationList,
      @NonNull SwipeRefreshLayout drugFeedsSwipeRefresh, @NonNull TextView noDrugFeeds) {
    this.rootView = rootView;
    this.drugFeedsLoader = drugFeedsLoader;
    this.drugFeedsNotificationList = drugFeedsNotificationList;
    this.drugFeedsSwipeRefresh = drugFeedsSwipeRefresh;
    this.noDrugFeeds = noDrugFeeds;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDrugFeedsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDrugFeedsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_drug_feeds, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDrugFeedsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.drug_feeds_loader;
      AVLoadingIndicatorView drugFeedsLoader = ViewBindings.findChildViewById(rootView, id);
      if (drugFeedsLoader == null) {
        break missingId;
      }

      id = R.id.drug_feeds_notification_list;
      RecyclerView drugFeedsNotificationList = ViewBindings.findChildViewById(rootView, id);
      if (drugFeedsNotificationList == null) {
        break missingId;
      }

      id = R.id.drug_feeds_swipe_refresh;
      SwipeRefreshLayout drugFeedsSwipeRefresh = ViewBindings.findChildViewById(rootView, id);
      if (drugFeedsSwipeRefresh == null) {
        break missingId;
      }

      id = R.id.no_drug_feeds;
      TextView noDrugFeeds = ViewBindings.findChildViewById(rootView, id);
      if (noDrugFeeds == null) {
        break missingId;
      }

      return new FragmentDrugFeedsBinding((FrameLayout) rootView, drugFeedsLoader,
          drugFeedsNotificationList, drugFeedsSwipeRefresh, noDrugFeeds);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}