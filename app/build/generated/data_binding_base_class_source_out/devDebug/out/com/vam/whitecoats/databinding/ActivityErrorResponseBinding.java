// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityErrorResponseBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button errorActionBtn;

  @NonNull
  public final TextView errorDescTxt;

  @NonNull
  public final ImageView errorImage;

  @NonNull
  public final TextView errorTitleTxt;

  private ActivityErrorResponseBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button errorActionBtn, @NonNull TextView errorDescTxt, @NonNull ImageView errorImage,
      @NonNull TextView errorTitleTxt) {
    this.rootView = rootView;
    this.errorActionBtn = errorActionBtn;
    this.errorDescTxt = errorDescTxt;
    this.errorImage = errorImage;
    this.errorTitleTxt = errorTitleTxt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityErrorResponseBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityErrorResponseBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_error_response, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityErrorResponseBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.error_action_btn;
      Button errorActionBtn = ViewBindings.findChildViewById(rootView, id);
      if (errorActionBtn == null) {
        break missingId;
      }

      id = R.id.error_desc_txt;
      TextView errorDescTxt = ViewBindings.findChildViewById(rootView, id);
      if (errorDescTxt == null) {
        break missingId;
      }

      id = R.id.error_image;
      ImageView errorImage = ViewBindings.findChildViewById(rootView, id);
      if (errorImage == null) {
        break missingId;
      }

      id = R.id.error_title_txt;
      TextView errorTitleTxt = ViewBindings.findChildViewById(rootView, id);
      if (errorTitleTxt == null) {
        break missingId;
      }

      return new ActivityErrorResponseBinding((ConstraintLayout) rootView, errorActionBtn,
          errorDescTxt, errorImage, errorTitleTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}