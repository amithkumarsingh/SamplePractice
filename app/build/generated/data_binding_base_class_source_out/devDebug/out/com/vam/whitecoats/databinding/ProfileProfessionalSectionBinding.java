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

public final class ProfileProfessionalSectionBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView experienceLabel;

  @NonNull
  public final ImageView professionalAddImg;

  @NonNull
  public final RelativeLayout professionalAddImgLay;

  @NonNull
  public final NonScrollListView professionalList;

  @NonNull
  public final TextView titleText;

  @NonNull
  public final TextView viewAllProfessional;

  private ProfileProfessionalSectionBinding(@NonNull LinearLayout rootView,
      @NonNull TextView experienceLabel, @NonNull ImageView professionalAddImg,
      @NonNull RelativeLayout professionalAddImgLay, @NonNull NonScrollListView professionalList,
      @NonNull TextView titleText, @NonNull TextView viewAllProfessional) {
    this.rootView = rootView;
    this.experienceLabel = experienceLabel;
    this.professionalAddImg = professionalAddImg;
    this.professionalAddImgLay = professionalAddImgLay;
    this.professionalList = professionalList;
    this.titleText = titleText;
    this.viewAllProfessional = viewAllProfessional;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ProfileProfessionalSectionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProfileProfessionalSectionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.profile_professional_section, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProfileProfessionalSectionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.experience_label;
      TextView experienceLabel = ViewBindings.findChildViewById(rootView, id);
      if (experienceLabel == null) {
        break missingId;
      }

      id = R.id.professional_add_img;
      ImageView professionalAddImg = ViewBindings.findChildViewById(rootView, id);
      if (professionalAddImg == null) {
        break missingId;
      }

      id = R.id.professional_add_img_lay;
      RelativeLayout professionalAddImgLay = ViewBindings.findChildViewById(rootView, id);
      if (professionalAddImgLay == null) {
        break missingId;
      }

      id = R.id.professional_list;
      NonScrollListView professionalList = ViewBindings.findChildViewById(rootView, id);
      if (professionalList == null) {
        break missingId;
      }

      id = R.id.title_text;
      TextView titleText = ViewBindings.findChildViewById(rootView, id);
      if (titleText == null) {
        break missingId;
      }

      id = R.id.view_all_professional;
      TextView viewAllProfessional = ViewBindings.findChildViewById(rootView, id);
      if (viewAllProfessional == null) {
        break missingId;
      }

      return new ProfileProfessionalSectionBinding((LinearLayout) rootView, experienceLabel,
          professionalAddImg, professionalAddImgLay, professionalList, titleText,
          viewAllProfessional);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
