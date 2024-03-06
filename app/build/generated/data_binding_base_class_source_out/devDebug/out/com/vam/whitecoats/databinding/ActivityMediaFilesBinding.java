// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMediaFilesBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RecyclerView mediaFilesRecycler;

  @NonNull
  public final SwipeRefreshLayout mediaSwipeContainer;

  private ActivityMediaFilesBinding(@NonNull LinearLayout rootView,
      @NonNull RecyclerView mediaFilesRecycler, @NonNull SwipeRefreshLayout mediaSwipeContainer) {
    this.rootView = rootView;
    this.mediaFilesRecycler = mediaFilesRecycler;
    this.mediaSwipeContainer = mediaSwipeContainer;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMediaFilesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMediaFilesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_media_files, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMediaFilesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.media_files_recycler;
      RecyclerView mediaFilesRecycler = ViewBindings.findChildViewById(rootView, id);
      if (mediaFilesRecycler == null) {
        break missingId;
      }

      id = R.id.mediaSwipeContainer;
      SwipeRefreshLayout mediaSwipeContainer = ViewBindings.findChildViewById(rootView, id);
      if (mediaSwipeContainer == null) {
        break missingId;
      }

      return new ActivityMediaFilesBinding((LinearLayout) rootView, mediaFilesRecycler,
          mediaSwipeContainer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
