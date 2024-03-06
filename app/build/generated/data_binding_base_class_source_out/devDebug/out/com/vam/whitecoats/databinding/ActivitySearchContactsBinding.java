// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySearchContactsBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final TextView noResultsTxt;

  @NonNull
  public final ListView searcbListView;

  @NonNull
  public final SwipeRefreshLayout swipeContainer;

  private ActivitySearchContactsBinding(@NonNull FrameLayout rootView,
      @NonNull TextView noResultsTxt, @NonNull ListView searcbListView,
      @NonNull SwipeRefreshLayout swipeContainer) {
    this.rootView = rootView;
    this.noResultsTxt = noResultsTxt;
    this.searcbListView = searcbListView;
    this.swipeContainer = swipeContainer;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySearchContactsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySearchContactsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_search_contacts, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySearchContactsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.noResults_txt;
      TextView noResultsTxt = ViewBindings.findChildViewById(rootView, id);
      if (noResultsTxt == null) {
        break missingId;
      }

      id = R.id.searcb_listView;
      ListView searcbListView = ViewBindings.findChildViewById(rootView, id);
      if (searcbListView == null) {
        break missingId;
      }

      id = R.id.swipeContainer;
      SwipeRefreshLayout swipeContainer = ViewBindings.findChildViewById(rootView, id);
      if (swipeContainer == null) {
        break missingId;
      }

      return new ActivitySearchContactsBinding((FrameLayout) rootView, noResultsTxt, searcbListView,
          swipeContainer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
