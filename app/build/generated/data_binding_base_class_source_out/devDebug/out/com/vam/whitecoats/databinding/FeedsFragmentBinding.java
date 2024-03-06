// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FeedsFragmentBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AVLoadingIndicatorView aviInTimeline;

  @NonNull
  public final ImageButton makepost;

  @NonNull
  public final TextView noFeedsTextView;

  @NonNull
  public final PostAnUpdateLayoutBinding postUpdateLayoutTimeline;

  @NonNull
  public final RecyclerView recyclerView;

  private FeedsFragmentBinding(@NonNull LinearLayout rootView,
      @NonNull AVLoadingIndicatorView aviInTimeline, @NonNull ImageButton makepost,
      @NonNull TextView noFeedsTextView,
      @NonNull PostAnUpdateLayoutBinding postUpdateLayoutTimeline,
      @NonNull RecyclerView recyclerView) {
    this.rootView = rootView;
    this.aviInTimeline = aviInTimeline;
    this.makepost = makepost;
    this.noFeedsTextView = noFeedsTextView;
    this.postUpdateLayoutTimeline = postUpdateLayoutTimeline;
    this.recyclerView = recyclerView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FeedsFragmentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FeedsFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.feeds_fragment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FeedsFragmentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.aviInTimeline;
      AVLoadingIndicatorView aviInTimeline = ViewBindings.findChildViewById(rootView, id);
      if (aviInTimeline == null) {
        break missingId;
      }

      id = R.id.makepost;
      ImageButton makepost = ViewBindings.findChildViewById(rootView, id);
      if (makepost == null) {
        break missingId;
      }

      id = R.id.noFeeds_textView;
      TextView noFeedsTextView = ViewBindings.findChildViewById(rootView, id);
      if (noFeedsTextView == null) {
        break missingId;
      }

      id = R.id.postUpdateLayout_Timeline;
      View postUpdateLayoutTimeline = ViewBindings.findChildViewById(rootView, id);
      if (postUpdateLayoutTimeline == null) {
        break missingId;
      }
      PostAnUpdateLayoutBinding binding_postUpdateLayoutTimeline = PostAnUpdateLayoutBinding.bind(postUpdateLayoutTimeline);

      id = R.id.recycler_view;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      return new FeedsFragmentBinding((LinearLayout) rootView, aviInTimeline, makepost,
          noFeedsTextView, binding_postUpdateLayoutTimeline, recyclerView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
