// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

public final class CustomDialogBoxBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnContactOk;

  @NonNull
  public final TextView contactLabel;

  @NonNull
  public final LinearLayout contactList;

  private CustomDialogBoxBinding(@NonNull RelativeLayout rootView, @NonNull Button btnContactOk,
      @NonNull TextView contactLabel, @NonNull LinearLayout contactList) {
    this.rootView = rootView;
    this.btnContactOk = btnContactOk;
    this.contactLabel = contactLabel;
    this.contactList = contactList;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomDialogBoxBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomDialogBoxBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_dialog_box, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomDialogBoxBinding bind(@NonNull View rootView) {
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

      id = R.id.contact_list;
      LinearLayout contactList = ViewBindings.findChildViewById(rootView, id);
      if (contactList == null) {
        break missingId;
      }

      return new CustomDialogBoxBinding((RelativeLayout) rootView, btnContactOk, contactLabel,
          contactList);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}