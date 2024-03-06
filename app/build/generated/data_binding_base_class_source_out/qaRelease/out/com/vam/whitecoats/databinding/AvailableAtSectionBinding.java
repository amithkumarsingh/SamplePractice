// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.NonScrollListView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AvailableAtSectionBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView availableAddImg;

  @NonNull
  public final RelativeLayout availableAddImgLay;

  @NonNull
  public final TextView availableAtLabel;

  @NonNull
  public final NonScrollListView availableList;

  @NonNull
  public final TextView titleText;

  @NonNull
  public final TextView viewAllAvailability;

  private AvailableAtSectionBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView availableAddImg, @NonNull RelativeLayout availableAddImgLay,
      @NonNull TextView availableAtLabel, @NonNull NonScrollListView availableList,
      @NonNull TextView titleText, @NonNull TextView viewAllAvailability) {
    this.rootView = rootView;
    this.availableAddImg = availableAddImg;
    this.availableAddImgLay = availableAddImgLay;
    this.availableAtLabel = availableAtLabel;
    this.availableList = availableList;
    this.titleText = titleText;
    this.viewAllAvailability = viewAllAvailability;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AvailableAtSectionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AvailableAtSectionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.available_at_section, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AvailableAtSectionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.available_add_img;
      ImageView availableAddImg = ViewBindings.findChildViewById(rootView, id);
      if (availableAddImg == null) {
        break missingId;
      }

      id = R.id.available_add_img_lay;
      RelativeLayout availableAddImgLay = ViewBindings.findChildViewById(rootView, id);
      if (availableAddImgLay == null) {
        break missingId;
      }

      id = R.id.available_at_label;
      TextView availableAtLabel = ViewBindings.findChildViewById(rootView, id);
      if (availableAtLabel == null) {
        break missingId;
      }

      id = R.id.available_list;
      NonScrollListView availableList = ViewBindings.findChildViewById(rootView, id);
      if (availableList == null) {
        break missingId;
      }

      id = R.id.title_text;
      TextView titleText = ViewBindings.findChildViewById(rootView, id);
      if (titleText == null) {
        break missingId;
      }

      id = R.id.view_all_availability;
      TextView viewAllAvailability = ViewBindings.findChildViewById(rootView, id);
      if (viewAllAvailability == null) {
        break missingId;
      }

      return new AvailableAtSectionBinding((ConstraintLayout) rootView, availableAddImg,
          availableAddImgLay, availableAtLabel, availableList, titleText, viewAllAvailability);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
