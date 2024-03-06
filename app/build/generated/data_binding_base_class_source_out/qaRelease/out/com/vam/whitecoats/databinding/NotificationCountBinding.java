// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NotificationCountBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final TextView notificationCountText;

  @NonNull
  public final ImageView notificationImg;

  private NotificationCountBinding(@NonNull FrameLayout rootView,
      @NonNull TextView notificationCountText, @NonNull ImageView notificationImg) {
    this.rootView = rootView;
    this.notificationCountText = notificationCountText;
    this.notificationImg = notificationImg;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NotificationCountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NotificationCountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.notification_count, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NotificationCountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.notification_count_text;
      TextView notificationCountText = ViewBindings.findChildViewById(rootView, id);
      if (notificationCountText == null) {
        break missingId;
      }

      id = R.id.notification_img;
      ImageView notificationImg = ViewBindings.findChildViewById(rootView, id);
      if (notificationImg == null) {
        break missingId;
      }

      return new NotificationCountBinding((FrameLayout) rootView, notificationCountText,
          notificationImg);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}