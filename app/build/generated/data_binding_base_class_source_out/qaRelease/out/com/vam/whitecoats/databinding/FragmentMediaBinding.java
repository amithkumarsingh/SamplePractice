// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.CustomRecycleView;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMediaBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final AVLoadingIndicatorView aviInMediaFragment;

  @NonNull
  public final CustomRecycleView mediaTabList;

  @NonNull
  public final TextView noMediaAvailable;

  private FragmentMediaBinding(@NonNull RelativeLayout rootView,
      @NonNull AVLoadingIndicatorView aviInMediaFragment, @NonNull CustomRecycleView mediaTabList,
      @NonNull TextView noMediaAvailable) {
    this.rootView = rootView;
    this.aviInMediaFragment = aviInMediaFragment;
    this.mediaTabList = mediaTabList;
    this.noMediaAvailable = noMediaAvailable;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMediaBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMediaBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_media, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMediaBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.aviInMediaFragment;
      AVLoadingIndicatorView aviInMediaFragment = ViewBindings.findChildViewById(rootView, id);
      if (aviInMediaFragment == null) {
        break missingId;
      }

      id = R.id.media_tab_list;
      CustomRecycleView mediaTabList = ViewBindings.findChildViewById(rootView, id);
      if (mediaTabList == null) {
        break missingId;
      }

      id = R.id.no_media_available;
      TextView noMediaAvailable = ViewBindings.findChildViewById(rootView, id);
      if (noMediaAvailable == null) {
        break missingId;
      }

      return new FragmentMediaBinding((RelativeLayout) rootView, aviInMediaFragment, mediaTabList,
          noMediaAvailable);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
