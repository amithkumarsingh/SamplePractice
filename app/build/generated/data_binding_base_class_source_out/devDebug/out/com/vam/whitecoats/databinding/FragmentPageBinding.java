// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPageBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ActivityMediaplayerBinding audioPlayerViewInViewpager;

  @NonNull
  public final ImageView imageView;

  private FragmentPageBinding(@NonNull RelativeLayout rootView,
      @NonNull ActivityMediaplayerBinding audioPlayerViewInViewpager,
      @NonNull ImageView imageView) {
    this.rootView = rootView;
    this.audioPlayerViewInViewpager = audioPlayerViewInViewpager;
    this.imageView = imageView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.audio_player_view_in_viewpager;
      View audioPlayerViewInViewpager = ViewBindings.findChildViewById(rootView, id);
      if (audioPlayerViewInViewpager == null) {
        break missingId;
      }
      ActivityMediaplayerBinding binding_audioPlayerViewInViewpager = ActivityMediaplayerBinding.bind(audioPlayerViewInViewpager);

      id = R.id.image_view;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      return new FragmentPageBinding((RelativeLayout) rootView, binding_audioPlayerViewInViewpager,
          imageView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}