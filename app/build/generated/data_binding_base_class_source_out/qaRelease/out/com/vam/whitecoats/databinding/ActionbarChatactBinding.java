// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActionbarChatactBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView nameTxt;

  @NonNull
  public final RoundedImageView profilePicImg;

  @NonNull
  public final TextView titleEdit;

  @NonNull
  public final LinearLayout titleLayout;

  @NonNull
  public final TextView typestatusTextView;

  private ActionbarChatactBinding(@NonNull LinearLayout rootView, @NonNull TextView nameTxt,
      @NonNull RoundedImageView profilePicImg, @NonNull TextView titleEdit,
      @NonNull LinearLayout titleLayout, @NonNull TextView typestatusTextView) {
    this.rootView = rootView;
    this.nameTxt = nameTxt;
    this.profilePicImg = profilePicImg;
    this.titleEdit = titleEdit;
    this.titleLayout = titleLayout;
    this.typestatusTextView = typestatusTextView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActionbarChatactBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActionbarChatactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.actionbar_chatact, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActionbarChatactBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.name_txt;
      TextView nameTxt = ViewBindings.findChildViewById(rootView, id);
      if (nameTxt == null) {
        break missingId;
      }

      id = R.id.profile_pic_img;
      RoundedImageView profilePicImg = ViewBindings.findChildViewById(rootView, id);
      if (profilePicImg == null) {
        break missingId;
      }

      id = R.id.title_edit;
      TextView titleEdit = ViewBindings.findChildViewById(rootView, id);
      if (titleEdit == null) {
        break missingId;
      }

      id = R.id.title_layout;
      LinearLayout titleLayout = ViewBindings.findChildViewById(rootView, id);
      if (titleLayout == null) {
        break missingId;
      }

      id = R.id.typestatusTextView;
      TextView typestatusTextView = ViewBindings.findChildViewById(rootView, id);
      if (typestatusTextView == null) {
        break missingId;
      }

      return new ActionbarChatactBinding((LinearLayout) rootView, nameTxt, profilePicImg, titleEdit,
          titleLayout, typestatusTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}