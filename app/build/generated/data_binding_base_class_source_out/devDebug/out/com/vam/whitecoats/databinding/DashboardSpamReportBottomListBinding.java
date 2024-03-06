// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DashboardSpamReportBottomListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView buttonName;

  @NonNull
  public final ImageView communityIcon;

  private DashboardSpamReportBottomListBinding(@NonNull LinearLayout rootView,
      @NonNull TextView buttonName, @NonNull ImageView communityIcon) {
    this.rootView = rootView;
    this.buttonName = buttonName;
    this.communityIcon = communityIcon;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DashboardSpamReportBottomListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DashboardSpamReportBottomListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dashboard_spam_report_bottom_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DashboardSpamReportBottomListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonName;
      TextView buttonName = ViewBindings.findChildViewById(rootView, id);
      if (buttonName == null) {
        break missingId;
      }

      id = R.id.community_icon;
      ImageView communityIcon = ViewBindings.findChildViewById(rootView, id);
      if (communityIcon == null) {
        break missingId;
      }

      return new DashboardSpamReportBottomListBinding((LinearLayout) rootView, buttonName,
          communityIcon);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}