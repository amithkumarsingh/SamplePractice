// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.BottomSheetListView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DashboardBottomSheetModalReportSpamBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RelativeLayout bottomSheetCloseButton;

  @NonNull
  public final BottomSheetListView communityList;

  @NonNull
  public final RelativeLayout createPostHeadingLayout;

  @NonNull
  public final TextView headerText;

  @NonNull
  public final TextView headingContent;

  @NonNull
  public final TextView headingText;

  @NonNull
  public final View headingView;

  private DashboardBottomSheetModalReportSpamBinding(@NonNull LinearLayout rootView,
      @NonNull RelativeLayout bottomSheetCloseButton, @NonNull BottomSheetListView communityList,
      @NonNull RelativeLayout createPostHeadingLayout, @NonNull TextView headerText,
      @NonNull TextView headingContent, @NonNull TextView headingText, @NonNull View headingView) {
    this.rootView = rootView;
    this.bottomSheetCloseButton = bottomSheetCloseButton;
    this.communityList = communityList;
    this.createPostHeadingLayout = createPostHeadingLayout;
    this.headerText = headerText;
    this.headingContent = headingContent;
    this.headingText = headingText;
    this.headingView = headingView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DashboardBottomSheetModalReportSpamBinding inflate(
      @NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DashboardBottomSheetModalReportSpamBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dashboard_bottom_sheet_modal_report_spam, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DashboardBottomSheetModalReportSpamBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomSheetCloseButton;
      RelativeLayout bottomSheetCloseButton = ViewBindings.findChildViewById(rootView, id);
      if (bottomSheetCloseButton == null) {
        break missingId;
      }

      id = R.id.communityList;
      BottomSheetListView communityList = ViewBindings.findChildViewById(rootView, id);
      if (communityList == null) {
        break missingId;
      }

      id = R.id.createPostHeadingLayout;
      RelativeLayout createPostHeadingLayout = ViewBindings.findChildViewById(rootView, id);
      if (createPostHeadingLayout == null) {
        break missingId;
      }

      id = R.id.headerText;
      TextView headerText = ViewBindings.findChildViewById(rootView, id);
      if (headerText == null) {
        break missingId;
      }

      id = R.id.headingContent;
      TextView headingContent = ViewBindings.findChildViewById(rootView, id);
      if (headingContent == null) {
        break missingId;
      }

      id = R.id.headingText;
      TextView headingText = ViewBindings.findChildViewById(rootView, id);
      if (headingText == null) {
        break missingId;
      }

      id = R.id.headingView;
      View headingView = ViewBindings.findChildViewById(rootView, id);
      if (headingView == null) {
        break missingId;
      }

      return new DashboardBottomSheetModalReportSpamBinding((LinearLayout) rootView,
          bottomSheetCloseButton, communityList, createPostHeadingLayout, headerText,
          headingContent, headingText, headingView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
