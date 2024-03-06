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

public final class ActivitySplashScreenBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout layoutNointernet;

  @NonNull
  public final Button tryagainBtn;

  @NonNull
  public final TextView tvNointernet;

  private ActivitySplashScreenBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout layoutNointernet, @NonNull Button tryagainBtn,
      @NonNull TextView tvNointernet) {
    this.rootView = rootView;
    this.layoutNointernet = layoutNointernet;
    this.tryagainBtn = tryagainBtn;
    this.tvNointernet = tvNointernet;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySplashScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySplashScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_splash_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySplashScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.layout_nointernet;
      LinearLayout layoutNointernet = ViewBindings.findChildViewById(rootView, id);
      if (layoutNointernet == null) {
        break missingId;
      }

      id = R.id.tryagain_btn;
      Button tryagainBtn = ViewBindings.findChildViewById(rootView, id);
      if (tryagainBtn == null) {
        break missingId;
      }

      id = R.id.tv_nointernet;
      TextView tvNointernet = ViewBindings.findChildViewById(rootView, id);
      if (tvNointernet == null) {
        break missingId;
      }

      return new ActivitySplashScreenBinding((RelativeLayout) rootView, layoutNointernet,
          tryagainBtn, tvNointernet);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}