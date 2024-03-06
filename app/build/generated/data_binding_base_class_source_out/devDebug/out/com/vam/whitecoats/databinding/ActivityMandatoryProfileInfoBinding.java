// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMandatoryProfileInfoBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final RelativeLayout activityRegistartionScreenTwo;

  @NonNull
  public final AutoCompleteTextView citiesAutocompleteView;

  @NonNull
  public final AutoCompleteTextView cityEdit;

  @NonNull
  public final TextView cityErrorText;

  @NonNull
  public final TextInputLayout cityLay;

  @NonNull
  public final AutoCompleteTextView experienceAutocompleteView;

  @NonNull
  public final TextInputLayout experienceAutocompleteViewLay;

  @NonNull
  public final TextView experienceErrorText;

  @NonNull
  public final RoundedImageView profPicImg;

  @NonNull
  public final ImageView profPicSelection;

  @NonNull
  public final FrameLayout profilePicLay;

  @NonNull
  public final Button profileSubmitAction;

  @NonNull
  public final TextView specialityErrorText;

  @NonNull
  public final TextInputLayout specialityLay;

  @NonNull
  public final EditText specialityTxtVw;

  @NonNull
  public final EditText subSpecialityEditTxt;

  @NonNull
  public final TextInputLayout subSpecialityLay;

  @NonNull
  public final TextView workplaceErrorText;

  @NonNull
  public final TextInputLayout workplaceLay;

  private ActivityMandatoryProfileInfoBinding(@NonNull ScrollView rootView,
      @NonNull RelativeLayout activityRegistartionScreenTwo,
      @NonNull AutoCompleteTextView citiesAutocompleteView, @NonNull AutoCompleteTextView cityEdit,
      @NonNull TextView cityErrorText, @NonNull TextInputLayout cityLay,
      @NonNull AutoCompleteTextView experienceAutocompleteView,
      @NonNull TextInputLayout experienceAutocompleteViewLay, @NonNull TextView experienceErrorText,
      @NonNull RoundedImageView profPicImg, @NonNull ImageView profPicSelection,
      @NonNull FrameLayout profilePicLay, @NonNull Button profileSubmitAction,
      @NonNull TextView specialityErrorText, @NonNull TextInputLayout specialityLay,
      @NonNull EditText specialityTxtVw, @NonNull EditText subSpecialityEditTxt,
      @NonNull TextInputLayout subSpecialityLay, @NonNull TextView workplaceErrorText,
      @NonNull TextInputLayout workplaceLay) {
    this.rootView = rootView;
    this.activityRegistartionScreenTwo = activityRegistartionScreenTwo;
    this.citiesAutocompleteView = citiesAutocompleteView;
    this.cityEdit = cityEdit;
    this.cityErrorText = cityErrorText;
    this.cityLay = cityLay;
    this.experienceAutocompleteView = experienceAutocompleteView;
    this.experienceAutocompleteViewLay = experienceAutocompleteViewLay;
    this.experienceErrorText = experienceErrorText;
    this.profPicImg = profPicImg;
    this.profPicSelection = profPicSelection;
    this.profilePicLay = profilePicLay;
    this.profileSubmitAction = profileSubmitAction;
    this.specialityErrorText = specialityErrorText;
    this.specialityLay = specialityLay;
    this.specialityTxtVw = specialityTxtVw;
    this.subSpecialityEditTxt = subSpecialityEditTxt;
    this.subSpecialityLay = subSpecialityLay;
    this.workplaceErrorText = workplaceErrorText;
    this.workplaceLay = workplaceLay;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMandatoryProfileInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMandatoryProfileInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_mandatory_profile_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMandatoryProfileInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.activity_registartion_screen_two;
      RelativeLayout activityRegistartionScreenTwo = ViewBindings.findChildViewById(rootView, id);
      if (activityRegistartionScreenTwo == null) {
        break missingId;
      }

      id = R.id.cities_autocomplete_view;
      AutoCompleteTextView citiesAutocompleteView = ViewBindings.findChildViewById(rootView, id);
      if (citiesAutocompleteView == null) {
        break missingId;
      }

      id = R.id.city_edit;
      AutoCompleteTextView cityEdit = ViewBindings.findChildViewById(rootView, id);
      if (cityEdit == null) {
        break missingId;
      }

      id = R.id.city_error_text;
      TextView cityErrorText = ViewBindings.findChildViewById(rootView, id);
      if (cityErrorText == null) {
        break missingId;
      }

      id = R.id.city_lay;
      TextInputLayout cityLay = ViewBindings.findChildViewById(rootView, id);
      if (cityLay == null) {
        break missingId;
      }

      id = R.id.experience_autocomplete_view;
      AutoCompleteTextView experienceAutocompleteView = ViewBindings.findChildViewById(rootView, id);
      if (experienceAutocompleteView == null) {
        break missingId;
      }

      id = R.id.experience_autocomplete_view_lay;
      TextInputLayout experienceAutocompleteViewLay = ViewBindings.findChildViewById(rootView, id);
      if (experienceAutocompleteViewLay == null) {
        break missingId;
      }

      id = R.id.experience_error_text;
      TextView experienceErrorText = ViewBindings.findChildViewById(rootView, id);
      if (experienceErrorText == null) {
        break missingId;
      }

      id = R.id.prof_pic_img;
      RoundedImageView profPicImg = ViewBindings.findChildViewById(rootView, id);
      if (profPicImg == null) {
        break missingId;
      }

      id = R.id.prof_pic_selection;
      ImageView profPicSelection = ViewBindings.findChildViewById(rootView, id);
      if (profPicSelection == null) {
        break missingId;
      }

      id = R.id.profilePic_lay;
      FrameLayout profilePicLay = ViewBindings.findChildViewById(rootView, id);
      if (profilePicLay == null) {
        break missingId;
      }

      id = R.id.profile_submit_action;
      Button profileSubmitAction = ViewBindings.findChildViewById(rootView, id);
      if (profileSubmitAction == null) {
        break missingId;
      }

      id = R.id.speciality_error_text;
      TextView specialityErrorText = ViewBindings.findChildViewById(rootView, id);
      if (specialityErrorText == null) {
        break missingId;
      }

      id = R.id.speciality_lay;
      TextInputLayout specialityLay = ViewBindings.findChildViewById(rootView, id);
      if (specialityLay == null) {
        break missingId;
      }

      id = R.id.specialityTxtVw;
      EditText specialityTxtVw = ViewBindings.findChildViewById(rootView, id);
      if (specialityTxtVw == null) {
        break missingId;
      }

      id = R.id.subSpecialityEditTxt;
      EditText subSpecialityEditTxt = ViewBindings.findChildViewById(rootView, id);
      if (subSpecialityEditTxt == null) {
        break missingId;
      }

      id = R.id.subSpeciality_lay;
      TextInputLayout subSpecialityLay = ViewBindings.findChildViewById(rootView, id);
      if (subSpecialityLay == null) {
        break missingId;
      }

      id = R.id.workplace_error_text;
      TextView workplaceErrorText = ViewBindings.findChildViewById(rootView, id);
      if (workplaceErrorText == null) {
        break missingId;
      }

      id = R.id.workplace_lay;
      TextInputLayout workplaceLay = ViewBindings.findChildViewById(rootView, id);
      if (workplaceLay == null) {
        break missingId;
      }

      return new ActivityMandatoryProfileInfoBinding((ScrollView) rootView,
          activityRegistartionScreenTwo, citiesAutocompleteView, cityEdit, cityErrorText, cityLay,
          experienceAutocompleteView, experienceAutocompleteViewLay, experienceErrorText,
          profPicImg, profPicSelection, profilePicLay, profileSubmitAction, specialityErrorText,
          specialityLay, specialityTxtVw, subSpecialityEditTxt, subSpecialityLay,
          workplaceErrorText, workplaceLay);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
