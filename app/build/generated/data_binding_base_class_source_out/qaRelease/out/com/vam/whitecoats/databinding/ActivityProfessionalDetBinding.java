// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfessionalDetBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button addButton;

  @NonNull
  public final LinearLayout availableParentLayout;

  @NonNull
  public final TextView checkErrorText;

  @NonNull
  public final AutoCompleteTextView cityEdit;

  @NonNull
  public final Button clearText;

  @NonNull
  public final FrameLayout createPasswordLay;

  @NonNull
  public final ImageView deleteIg;

  @NonNull
  public final RelativeLayout deleteLayout;

  @NonNull
  public final EditText designationEdit;

  @NonNull
  public final EditText endTimeEdt;

  @NonNull
  public final EditText fromdateEdit;

  @NonNull
  public final TextView fromdateError;

  @NonNull
  public final TextInputLayout fromdateLabel;

  @NonNull
  public final TextView locationErrorText;

  @NonNull
  public final RadioGroup opdOtRg;

  @NonNull
  public final RadioButton opdRadioBtn;

  @NonNull
  public final RadioButton otRadioBtn;

  @NonNull
  public final EditText startTimeEdt;

  @NonNull
  public final TextView tilldateText;

  @NonNull
  public final EditText todateEdit;

  @NonNull
  public final TextInputLayout todateEditTextInputLay;

  @NonNull
  public final ToggleButton toggleButton1;

  @NonNull
  public final RecyclerView weekDaysRecyclerView;

  @NonNull
  public final CheckBox workingCheck;

  @NonNull
  public final AutoCompleteTextView workplaceEdit;

  @NonNull
  public final TextView workplaceErrorText;

  private ActivityProfessionalDetBinding(@NonNull ScrollView rootView, @NonNull Button addButton,
      @NonNull LinearLayout availableParentLayout, @NonNull TextView checkErrorText,
      @NonNull AutoCompleteTextView cityEdit, @NonNull Button clearText,
      @NonNull FrameLayout createPasswordLay, @NonNull ImageView deleteIg,
      @NonNull RelativeLayout deleteLayout, @NonNull EditText designationEdit,
      @NonNull EditText endTimeEdt, @NonNull EditText fromdateEdit, @NonNull TextView fromdateError,
      @NonNull TextInputLayout fromdateLabel, @NonNull TextView locationErrorText,
      @NonNull RadioGroup opdOtRg, @NonNull RadioButton opdRadioBtn,
      @NonNull RadioButton otRadioBtn, @NonNull EditText startTimeEdt,
      @NonNull TextView tilldateText, @NonNull EditText todateEdit,
      @NonNull TextInputLayout todateEditTextInputLay, @NonNull ToggleButton toggleButton1,
      @NonNull RecyclerView weekDaysRecyclerView, @NonNull CheckBox workingCheck,
      @NonNull AutoCompleteTextView workplaceEdit, @NonNull TextView workplaceErrorText) {
    this.rootView = rootView;
    this.addButton = addButton;
    this.availableParentLayout = availableParentLayout;
    this.checkErrorText = checkErrorText;
    this.cityEdit = cityEdit;
    this.clearText = clearText;
    this.createPasswordLay = createPasswordLay;
    this.deleteIg = deleteIg;
    this.deleteLayout = deleteLayout;
    this.designationEdit = designationEdit;
    this.endTimeEdt = endTimeEdt;
    this.fromdateEdit = fromdateEdit;
    this.fromdateError = fromdateError;
    this.fromdateLabel = fromdateLabel;
    this.locationErrorText = locationErrorText;
    this.opdOtRg = opdOtRg;
    this.opdRadioBtn = opdRadioBtn;
    this.otRadioBtn = otRadioBtn;
    this.startTimeEdt = startTimeEdt;
    this.tilldateText = tilldateText;
    this.todateEdit = todateEdit;
    this.todateEditTextInputLay = todateEditTextInputLay;
    this.toggleButton1 = toggleButton1;
    this.weekDaysRecyclerView = weekDaysRecyclerView;
    this.workingCheck = workingCheck;
    this.workplaceEdit = workplaceEdit;
    this.workplaceErrorText = workplaceErrorText;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfessionalDetBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfessionalDetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_professional_det, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfessionalDetBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_button;
      Button addButton = ViewBindings.findChildViewById(rootView, id);
      if (addButton == null) {
        break missingId;
      }

      id = R.id.available_parent_layout;
      LinearLayout availableParentLayout = ViewBindings.findChildViewById(rootView, id);
      if (availableParentLayout == null) {
        break missingId;
      }

      id = R.id.check_error_text;
      TextView checkErrorText = ViewBindings.findChildViewById(rootView, id);
      if (checkErrorText == null) {
        break missingId;
      }

      id = R.id.city_edit;
      AutoCompleteTextView cityEdit = ViewBindings.findChildViewById(rootView, id);
      if (cityEdit == null) {
        break missingId;
      }

      id = R.id.clear_text;
      Button clearText = ViewBindings.findChildViewById(rootView, id);
      if (clearText == null) {
        break missingId;
      }

      id = R.id.create_password_lay;
      FrameLayout createPasswordLay = ViewBindings.findChildViewById(rootView, id);
      if (createPasswordLay == null) {
        break missingId;
      }

      id = R.id.delete_ig;
      ImageView deleteIg = ViewBindings.findChildViewById(rootView, id);
      if (deleteIg == null) {
        break missingId;
      }

      id = R.id.delete_layout;
      RelativeLayout deleteLayout = ViewBindings.findChildViewById(rootView, id);
      if (deleteLayout == null) {
        break missingId;
      }

      id = R.id.designation_edit;
      EditText designationEdit = ViewBindings.findChildViewById(rootView, id);
      if (designationEdit == null) {
        break missingId;
      }

      id = R.id.endTime_edt;
      EditText endTimeEdt = ViewBindings.findChildViewById(rootView, id);
      if (endTimeEdt == null) {
        break missingId;
      }

      id = R.id.fromdate_edit;
      EditText fromdateEdit = ViewBindings.findChildViewById(rootView, id);
      if (fromdateEdit == null) {
        break missingId;
      }

      id = R.id.fromdate_error;
      TextView fromdateError = ViewBindings.findChildViewById(rootView, id);
      if (fromdateError == null) {
        break missingId;
      }

      id = R.id.fromdate_label;
      TextInputLayout fromdateLabel = ViewBindings.findChildViewById(rootView, id);
      if (fromdateLabel == null) {
        break missingId;
      }

      id = R.id.location_error_text;
      TextView locationErrorText = ViewBindings.findChildViewById(rootView, id);
      if (locationErrorText == null) {
        break missingId;
      }

      id = R.id.opd_ot_rg;
      RadioGroup opdOtRg = ViewBindings.findChildViewById(rootView, id);
      if (opdOtRg == null) {
        break missingId;
      }

      id = R.id.opd_radio_btn;
      RadioButton opdRadioBtn = ViewBindings.findChildViewById(rootView, id);
      if (opdRadioBtn == null) {
        break missingId;
      }

      id = R.id.ot_radio_btn;
      RadioButton otRadioBtn = ViewBindings.findChildViewById(rootView, id);
      if (otRadioBtn == null) {
        break missingId;
      }

      id = R.id.startTime_edt;
      EditText startTimeEdt = ViewBindings.findChildViewById(rootView, id);
      if (startTimeEdt == null) {
        break missingId;
      }

      id = R.id.tilldate_text;
      TextView tilldateText = ViewBindings.findChildViewById(rootView, id);
      if (tilldateText == null) {
        break missingId;
      }

      id = R.id.todate_edit;
      EditText todateEdit = ViewBindings.findChildViewById(rootView, id);
      if (todateEdit == null) {
        break missingId;
      }

      id = R.id.todate_edit_text_input_lay;
      TextInputLayout todateEditTextInputLay = ViewBindings.findChildViewById(rootView, id);
      if (todateEditTextInputLay == null) {
        break missingId;
      }

      id = R.id.toggleButton1;
      ToggleButton toggleButton1 = ViewBindings.findChildViewById(rootView, id);
      if (toggleButton1 == null) {
        break missingId;
      }

      id = R.id.week_days_recycler_view;
      RecyclerView weekDaysRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (weekDaysRecyclerView == null) {
        break missingId;
      }

      id = R.id.working_check;
      CheckBox workingCheck = ViewBindings.findChildViewById(rootView, id);
      if (workingCheck == null) {
        break missingId;
      }

      id = R.id.workplace_edit;
      AutoCompleteTextView workplaceEdit = ViewBindings.findChildViewById(rootView, id);
      if (workplaceEdit == null) {
        break missingId;
      }

      id = R.id.workplace_error_text;
      TextView workplaceErrorText = ViewBindings.findChildViewById(rootView, id);
      if (workplaceErrorText == null) {
        break missingId;
      }

      return new ActivityProfessionalDetBinding((ScrollView) rootView, addButton,
          availableParentLayout, checkErrorText, cityEdit, clearText, createPasswordLay, deleteIg,
          deleteLayout, designationEdit, endTimeEdt, fromdateEdit, fromdateError, fromdateLabel,
          locationErrorText, opdOtRg, opdRadioBtn, otRadioBtn, startTimeEdt, tilldateText,
          todateEdit, todateEditTextInputLay, toggleButton1, weekDaysRecyclerView, workingCheck,
          workplaceEdit, workplaceErrorText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}