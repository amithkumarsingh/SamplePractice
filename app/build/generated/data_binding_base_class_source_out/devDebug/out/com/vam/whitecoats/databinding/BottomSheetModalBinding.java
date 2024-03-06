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
import com.vam.whitecoats.utils.BottomSheetListView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class BottomSheetModalBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final View aboutMeBorder2;

  @NonNull
  public final BottomSheetListView communityList;

  @NonNull
  public final LinearLayout communitySelectionLayout;

  @NonNull
  public final LinearLayout networkSelectionLayout;

  @NonNull
  public final TextView shareOnlyTextView;

  @NonNull
  public final TextView shareWithEveryOneTextView;

  private BottomSheetModalBinding(@NonNull LinearLayout rootView, @NonNull View aboutMeBorder2,
      @NonNull BottomSheetListView communityList, @NonNull LinearLayout communitySelectionLayout,
      @NonNull LinearLayout networkSelectionLayout, @NonNull TextView shareOnlyTextView,
      @NonNull TextView shareWithEveryOneTextView) {
    this.rootView = rootView;
    this.aboutMeBorder2 = aboutMeBorder2;
    this.communityList = communityList;
    this.communitySelectionLayout = communitySelectionLayout;
    this.networkSelectionLayout = networkSelectionLayout;
    this.shareOnlyTextView = shareOnlyTextView;
    this.shareWithEveryOneTextView = shareWithEveryOneTextView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetModalBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetModalBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_modal, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetModalBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.about_me_border2;
      View aboutMeBorder2 = ViewBindings.findChildViewById(rootView, id);
      if (aboutMeBorder2 == null) {
        break missingId;
      }

      id = R.id.communityList;
      BottomSheetListView communityList = ViewBindings.findChildViewById(rootView, id);
      if (communityList == null) {
        break missingId;
      }

      id = R.id.community_selection_layout;
      LinearLayout communitySelectionLayout = ViewBindings.findChildViewById(rootView, id);
      if (communitySelectionLayout == null) {
        break missingId;
      }

      id = R.id.network_selection_layout;
      LinearLayout networkSelectionLayout = ViewBindings.findChildViewById(rootView, id);
      if (networkSelectionLayout == null) {
        break missingId;
      }

      id = R.id.share_only_textView;
      TextView shareOnlyTextView = ViewBindings.findChildViewById(rootView, id);
      if (shareOnlyTextView == null) {
        break missingId;
      }

      id = R.id.share_with_everyOne_textView;
      TextView shareWithEveryOneTextView = ViewBindings.findChildViewById(rootView, id);
      if (shareWithEveryOneTextView == null) {
        break missingId;
      }

      return new BottomSheetModalBinding((LinearLayout) rootView, aboutMeBorder2, communityList,
          communitySelectionLayout, networkSelectionLayout, shareOnlyTextView,
          shareWithEveryOneTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}