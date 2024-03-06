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

public final class ActivityGetstartedBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView alreadyUser;

  @NonNull
  public final TextView createnewTextView;

  @NonNull
  public final LinearLayout loginInfoLayout;

  @NonNull
  public final TextView loginTextview;

  @NonNull
  public final Button registerButton;

  @NonNull
  public final LinearLayout registerInfoLayout;

  private ActivityGetstartedBinding(@NonNull RelativeLayout rootView, @NonNull TextView alreadyUser,
      @NonNull TextView createnewTextView, @NonNull LinearLayout loginInfoLayout,
      @NonNull TextView loginTextview, @NonNull Button registerButton,
      @NonNull LinearLayout registerInfoLayout) {
    this.rootView = rootView;
    this.alreadyUser = alreadyUser;
    this.createnewTextView = createnewTextView;
    this.loginInfoLayout = loginInfoLayout;
    this.loginTextview = loginTextview;
    this.registerButton = registerButton;
    this.registerInfoLayout = registerInfoLayout;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityGetstartedBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityGetstartedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_getstarted, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityGetstartedBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.already_user;
      TextView alreadyUser = ViewBindings.findChildViewById(rootView, id);
      if (alreadyUser == null) {
        break missingId;
      }

      id = R.id.createnew_textView;
      TextView createnewTextView = ViewBindings.findChildViewById(rootView, id);
      if (createnewTextView == null) {
        break missingId;
      }

      id = R.id.login_info_layout;
      LinearLayout loginInfoLayout = ViewBindings.findChildViewById(rootView, id);
      if (loginInfoLayout == null) {
        break missingId;
      }

      id = R.id.login_textview;
      TextView loginTextview = ViewBindings.findChildViewById(rootView, id);
      if (loginTextview == null) {
        break missingId;
      }

      id = R.id.register_button;
      Button registerButton = ViewBindings.findChildViewById(rootView, id);
      if (registerButton == null) {
        break missingId;
      }

      id = R.id.register_info_layout;
      LinearLayout registerInfoLayout = ViewBindings.findChildViewById(rootView, id);
      if (registerInfoLayout == null) {
        break missingId;
      }

      return new ActivityGetstartedBinding((RelativeLayout) rootView, alreadyUser,
          createnewTextView, loginInfoLayout, loginTextview, registerButton, registerInfoLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}