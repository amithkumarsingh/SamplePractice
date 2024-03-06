// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomViewBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnContactOk;

  @NonNull
  public final TextView contactLabel;

  private CustomViewBinding(@NonNull RelativeLayout rootView, @NonNull Button btnContactOk,
      @NonNull TextView contactLabel) {
    this.rootView = rootView;
    this.btnContactOk = btnContactOk;
    this.contactLabel = contactLabel;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_contact_ok;
      Button btnContactOk = ViewBindings.findChildViewById(rootView, id);
      if (btnContactOk == null) {
        break missingId;
      }

      id = R.id.contact_label;
      TextView contactLabel = ViewBindings.findChildViewById(rootView, id);
      if (contactLabel == null) {
        break missingId;
      }

      return new CustomViewBinding((RelativeLayout) rootView, btnContactOk, contactLabel);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}