// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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

public final class ActivityPreviewcommentsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout commentInputLayout;

  @NonNull
  public final ImageButton previewCameraBtn;

  @NonNull
  public final EditText previewEditText;

  @NonNull
  public final ImageView previewImage;

  @NonNull
  public final ImageButton previewSendBtn;

  private ActivityPreviewcommentsBinding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout commentInputLayout, @NonNull ImageButton previewCameraBtn,
      @NonNull EditText previewEditText, @NonNull ImageView previewImage,
      @NonNull ImageButton previewSendBtn) {
    this.rootView = rootView;
    this.commentInputLayout = commentInputLayout;
    this.previewCameraBtn = previewCameraBtn;
    this.previewEditText = previewEditText;
    this.previewImage = previewImage;
    this.previewSendBtn = previewSendBtn;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPreviewcommentsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPreviewcommentsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_previewcomments, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPreviewcommentsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.comment_input_layout;
      RelativeLayout commentInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (commentInputLayout == null) {
        break missingId;
      }

      id = R.id.preview_cameraBtn;
      ImageButton previewCameraBtn = ViewBindings.findChildViewById(rootView, id);
      if (previewCameraBtn == null) {
        break missingId;
      }

      id = R.id.preview_EditText;
      EditText previewEditText = ViewBindings.findChildViewById(rootView, id);
      if (previewEditText == null) {
        break missingId;
      }

      id = R.id.preview_image;
      ImageView previewImage = ViewBindings.findChildViewById(rootView, id);
      if (previewImage == null) {
        break missingId;
      }

      id = R.id.preview_sendBtn;
      ImageButton previewSendBtn = ViewBindings.findChildViewById(rootView, id);
      if (previewSendBtn == null) {
        break missingId;
      }

      return new ActivityPreviewcommentsBinding((RelativeLayout) rootView, commentInputLayout,
          previewCameraBtn, previewEditText, previewImage, previewSendBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
