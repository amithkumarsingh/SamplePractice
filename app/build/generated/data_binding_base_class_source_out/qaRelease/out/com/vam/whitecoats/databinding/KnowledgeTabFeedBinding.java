// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class KnowledgeTabFeedBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RecyclerView categoryListDashboard;

  @NonNull
  public final FrameLayout childFragmentContainer;

  private KnowledgeTabFeedBinding(@NonNull LinearLayout rootView,
      @NonNull RecyclerView categoryListDashboard, @NonNull FrameLayout childFragmentContainer) {
    this.rootView = rootView;
    this.categoryListDashboard = categoryListDashboard;
    this.childFragmentContainer = childFragmentContainer;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static KnowledgeTabFeedBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static KnowledgeTabFeedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.knowledge_tab_feed, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static KnowledgeTabFeedBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.category_list_dashboard;
      RecyclerView categoryListDashboard = ViewBindings.findChildViewById(rootView, id);
      if (categoryListDashboard == null) {
        break missingId;
      }

      id = R.id.child_fragment_container;
      FrameLayout childFragmentContainer = ViewBindings.findChildViewById(rootView, id);
      if (childFragmentContainer == null) {
        break missingId;
      }

      return new KnowledgeTabFeedBinding((LinearLayout) rootView, categoryListDashboard,
          childFragmentContainer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
