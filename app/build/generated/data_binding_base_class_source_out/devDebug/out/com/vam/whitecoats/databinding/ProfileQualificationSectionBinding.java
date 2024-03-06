// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.NonScrollListView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ProfileQualificationSectionBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView academicsHeading;

  @NonNull
  public final ImageView qualificationAddImg;

  @NonNull
  public final RelativeLayout qualificationAddImgLay;

  @NonNull
  public final NonScrollListView qualificationList;

  @NonNull
  public final TextView titleText;

  @NonNull
  public final TextView viewAllQualifications;

  private ProfileQualificationSectionBinding(@NonNull LinearLayout rootView,
      @NonNull TextView academicsHeading, @NonNull ImageView qualificationAddImg,
      @NonNull RelativeLayout qualificationAddImgLay, @NonNull NonScrollListView qualificationList,
      @NonNull TextView titleText, @NonNull TextView viewAllQualifications) {
    this.rootView = rootView;
    this.academicsHeading = academicsHeading;
    this.qualificationAddImg = qualificationAddImg;
    this.qualificationAddImgLay = qualificationAddImgLay;
    this.qualificationList = qualificationList;
    this.titleText = titleText;
    this.viewAllQualifications = viewAllQualifications;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ProfileQualificationSectionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProfileQualificationSectionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.profile_qualification_section, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProfileQualificationSectionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.academics_heading;
      TextView academicsHeading = ViewBindings.findChildViewById(rootView, id);
      if (academicsHeading == null) {
        break missingId;
      }

      id = R.id.qualification_add_img;
      ImageView qualificationAddImg = ViewBindings.findChildViewById(rootView, id);
      if (qualificationAddImg == null) {
        break missingId;
      }

      id = R.id.qualification_add_img_lay;
      RelativeLayout qualificationAddImgLay = ViewBindings.findChildViewById(rootView, id);
      if (qualificationAddImgLay == null) {
        break missingId;
      }

      id = R.id.qualification_list;
      NonScrollListView qualificationList = ViewBindings.findChildViewById(rootView, id);
      if (qualificationList == null) {
        break missingId;
      }

      id = R.id.title_text;
      TextView titleText = ViewBindings.findChildViewById(rootView, id);
      if (titleText == null) {
        break missingId;
      }

      id = R.id.view_all_qualifications;
      TextView viewAllQualifications = ViewBindings.findChildViewById(rootView, id);
      if (viewAllQualifications == null) {
        break missingId;
      }

      return new ProfileQualificationSectionBinding((LinearLayout) rootView, academicsHeading,
          qualificationAddImg, qualificationAddImgLay, qualificationList, titleText,
          viewAllQualifications);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}