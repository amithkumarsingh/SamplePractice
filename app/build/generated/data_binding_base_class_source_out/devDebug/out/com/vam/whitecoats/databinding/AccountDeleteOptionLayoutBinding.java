// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class AccountDeleteOptionLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView deleteAccountTv;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final TextView textView;

  private AccountDeleteOptionLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView deleteAccountTv, @NonNull ImageView imageView2,
      @NonNull TextView textView) {
    this.rootView = rootView;
    this.deleteAccountTv = deleteAccountTv;
    this.imageView2 = imageView2;
    this.textView = textView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AccountDeleteOptionLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AccountDeleteOptionLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.account_delete_option_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AccountDeleteOptionLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.delete_account_tv;
      TextView deleteAccountTv = ViewBindings.findChildViewById(rootView, id);
      if (deleteAccountTv == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new AccountDeleteOptionLayoutBinding((ConstraintLayout) rootView, deleteAccountTv,
          imageView2, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}