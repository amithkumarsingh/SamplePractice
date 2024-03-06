// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogMandatoryPageBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final AutoCompleteTextView cityEdit;

  @NonNull
  public final FrameLayout cityEditLay;

  @NonNull
  public final TextView cityEditLayText;

  @NonNull
  public final TextView cityErrorText;

  @NonNull
  public final RelativeLayout cityLayout;

  @NonNull
  public final Button clearText;

  @NonNull
  public final AutoCompleteTextView experienceAutocompleteView;

  @NonNull
  public final TextView experienceErrorText;

  @NonNull
  public final RelativeLayout experienceLayout;

  @NonNull
  public final RelativeLayout getStartedButtonLayout;

  @NonNull
  public final ImageView logo;

  @NonNull
  public final TextView overAllExperienceText;

  @NonNull
  public final AutoCompleteTextView specialityEdit;

  @NonNull
  public final TextView specialityEditLay;

  @NonNull
  public final TextView specialityErrorText;

  @NonNull
  public final RelativeLayout specialityLay;

  @NonNull
  public final RelativeLayout toGetStartedLayout;

  @NonNull
  public final TextView toGetStartedText;

  @NonNull
  public final TextView welcomeToWhiteCoats;

  @NonNull
  public final TextView welcomeToWhiteCoatsSubText;

  @NonNull
  public final RelativeLayout whiteCoatsLabelLayout;

  @NonNull
  public final RelativeLayout whiteCoatsSubLabelLayout;

  private DialogMandatoryPageBinding(@NonNull ScrollView rootView,
      @NonNull AutoCompleteTextView cityEdit, @NonNull FrameLayout cityEditLay,
      @NonNull TextView cityEditLayText, @NonNull TextView cityErrorText,
      @NonNull RelativeLayout cityLayout, @NonNull Button clearText,
      @NonNull AutoCompleteTextView experienceAutocompleteView,
      @NonNull TextView experienceErrorText, @NonNull RelativeLayout experienceLayout,
      @NonNull RelativeLayout getStartedButtonLayout, @NonNull ImageView logo,
      @NonNull TextView overAllExperienceText, @NonNull AutoCompleteTextView specialityEdit,
      @NonNull TextView specialityEditLay, @NonNull TextView specialityErrorText,
      @NonNull RelativeLayout specialityLay, @NonNull RelativeLayout toGetStartedLayout,
      @NonNull TextView toGetStartedText, @NonNull TextView welcomeToWhiteCoats,
      @NonNull TextView welcomeToWhiteCoatsSubText, @NonNull RelativeLayout whiteCoatsLabelLayout,
      @NonNull RelativeLayout whiteCoatsSubLabelLayout) {
    this.rootView = rootView;
    this.cityEdit = cityEdit;
    this.cityEditLay = cityEditLay;
    this.cityEditLayText = cityEditLayText;
    this.cityErrorText = cityErrorText;
    this.cityLayout = cityLayout;
    this.clearText = clearText;
    this.experienceAutocompleteView = experienceAutocompleteView;
    this.experienceErrorText = experienceErrorText;
    this.experienceLayout = experienceLayout;
    this.getStartedButtonLayout = getStartedButtonLayout;
    this.logo = logo;
    this.overAllExperienceText = overAllExperienceText;
    this.specialityEdit = specialityEdit;
    this.specialityEditLay = specialityEditLay;
    this.specialityErrorText = specialityErrorText;
    this.specialityLay = specialityLay;
    this.toGetStartedLayout = toGetStartedLayout;
    this.toGetStartedText = toGetStartedText;
    this.welcomeToWhiteCoats = welcomeToWhiteCoats;
    this.welcomeToWhiteCoatsSubText = welcomeToWhiteCoatsSubText;
    this.whiteCoatsLabelLayout = whiteCoatsLabelLayout;
    this.whiteCoatsSubLabelLayout = whiteCoatsSubLabelLayout;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogMandatoryPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogMandatoryPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_mandatory_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogMandatoryPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.city_edit;
      AutoCompleteTextView cityEdit = ViewBindings.findChildViewById(rootView, id);
      if (cityEdit == null) {
        break missingId;
      }

      id = R.id.city_edit_lay;
      FrameLayout cityEditLay = ViewBindings.findChildViewById(rootView, id);
      if (cityEditLay == null) {
        break missingId;
      }

      id = R.id.city_edit_lay_text;
      TextView cityEditLayText = ViewBindings.findChildViewById(rootView, id);
      if (cityEditLayText == null) {
        break missingId;
      }

      id = R.id.city_error_text;
      TextView cityErrorText = ViewBindings.findChildViewById(rootView, id);
      if (cityErrorText == null) {
        break missingId;
      }

      id = R.id.cityLayout;
      RelativeLayout cityLayout = ViewBindings.findChildViewById(rootView, id);
      if (cityLayout == null) {
        break missingId;
      }

      id = R.id.clear_text;
      Button clearText = ViewBindings.findChildViewById(rootView, id);
      if (clearText == null) {
        break missingId;
      }

      id = R.id.experience_autocomplete_view;
      AutoCompleteTextView experienceAutocompleteView = ViewBindings.findChildViewById(rootView, id);
      if (experienceAutocompleteView == null) {
        break missingId;
      }

      id = R.id.experience_error_text;
      TextView experienceErrorText = ViewBindings.findChildViewById(rootView, id);
      if (experienceErrorText == null) {
        break missingId;
      }

      id = R.id.experienceLayout;
      RelativeLayout experienceLayout = ViewBindings.findChildViewById(rootView, id);
      if (experienceLayout == null) {
        break missingId;
      }

      id = R.id.getStartedButtonLayout;
      RelativeLayout getStartedButtonLayout = ViewBindings.findChildViewById(rootView, id);
      if (getStartedButtonLayout == null) {
        break missingId;
      }

      id = R.id.logo;
      ImageView logo = ViewBindings.findChildViewById(rootView, id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.over_all_experience_text;
      TextView overAllExperienceText = ViewBindings.findChildViewById(rootView, id);
      if (overAllExperienceText == null) {
        break missingId;
      }

      id = R.id.speciality_edit;
      AutoCompleteTextView specialityEdit = ViewBindings.findChildViewById(rootView, id);
      if (specialityEdit == null) {
        break missingId;
      }

      id = R.id.speciality_edit_lay;
      TextView specialityEditLay = ViewBindings.findChildViewById(rootView, id);
      if (specialityEditLay == null) {
        break missingId;
      }

      id = R.id.speciality_error_text;
      TextView specialityErrorText = ViewBindings.findChildViewById(rootView, id);
      if (specialityErrorText == null) {
        break missingId;
      }

      id = R.id.speciality_lay;
      RelativeLayout specialityLay = ViewBindings.findChildViewById(rootView, id);
      if (specialityLay == null) {
        break missingId;
      }

      id = R.id.toGetStartedLayout;
      RelativeLayout toGetStartedLayout = ViewBindings.findChildViewById(rootView, id);
      if (toGetStartedLayout == null) {
        break missingId;
      }

      id = R.id.toGetStartedText;
      TextView toGetStartedText = ViewBindings.findChildViewById(rootView, id);
      if (toGetStartedText == null) {
        break missingId;
      }

      id = R.id.welcomeToWhiteCoats;
      TextView welcomeToWhiteCoats = ViewBindings.findChildViewById(rootView, id);
      if (welcomeToWhiteCoats == null) {
        break missingId;
      }

      id = R.id.welcomeToWhiteCoatsSubText;
      TextView welcomeToWhiteCoatsSubText = ViewBindings.findChildViewById(rootView, id);
      if (welcomeToWhiteCoatsSubText == null) {
        break missingId;
      }

      id = R.id.whiteCoatsLabelLayout;
      RelativeLayout whiteCoatsLabelLayout = ViewBindings.findChildViewById(rootView, id);
      if (whiteCoatsLabelLayout == null) {
        break missingId;
      }

      id = R.id.whiteCoatsSubLabelLayout;
      RelativeLayout whiteCoatsSubLabelLayout = ViewBindings.findChildViewById(rootView, id);
      if (whiteCoatsSubLabelLayout == null) {
        break missingId;
      }

      return new DialogMandatoryPageBinding((ScrollView) rootView, cityEdit, cityEditLay,
          cityEditLayText, cityErrorText, cityLayout, clearText, experienceAutocompleteView,
          experienceErrorText, experienceLayout, getStartedButtonLayout, logo,
          overAllExperienceText, specialityEdit, specialityEditLay, specialityErrorText,
          specialityLay, toGetStartedLayout, toGetStartedText, welcomeToWhiteCoats,
          welcomeToWhiteCoatsSubText, whiteCoatsLabelLayout, whiteCoatsSubLabelLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
