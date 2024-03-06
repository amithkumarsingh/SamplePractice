// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ImgplaceholderBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final FrameLayout containerWithDottedBorder;

  @NonNull
  public final ImageView imgPostattachment;

  private ImgplaceholderBinding(@NonNull LinearLayout rootView,
      @NonNull FrameLayout containerWithDottedBorder, @NonNull ImageView imgPostattachment) {
    this.rootView = rootView;
    this.containerWithDottedBorder = containerWithDottedBorder;
    this.imgPostattachment = imgPostattachment;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ImgplaceholderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ImgplaceholderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.imgplaceholder, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ImgplaceholderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.container_with_dotted_border;
      FrameLayout containerWithDottedBorder = ViewBindings.findChildViewById(rootView, id);
      if (containerWithDottedBorder == null) {
        break missingId;
      }

      id = R.id.img_postattachment;
      ImageView imgPostattachment = ViewBindings.findChildViewById(rootView, id);
      if (imgPostattachment == null) {
        break missingId;
      }

      return new ImgplaceholderBinding((LinearLayout) rootView, containerWithDottedBorder,
          imgPostattachment);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}