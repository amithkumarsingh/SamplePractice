// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public final class ActivityAcademicBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button addAnotherBtn;

  @NonNull
  public final AutoCompleteTextView collegeAutoedit;

  @NonNull
  public final CheckBox currentlyPursuingCheckbox;

  @NonNull
  public final AutoCompleteTextView degreeEdit;

  @NonNull
  public final TextView degreeErrorText;

  @NonNull
  public final ImageView deleteIg;

  @NonNull
  public final RelativeLayout deleteLayout;

  @NonNull
  public final EditText passingYearEdit;

  @NonNull
  public final AutoCompleteTextView universityAutoedit;

  @NonNull
  public final TextView yearErrorText;

  private ActivityAcademicBinding(@NonNull ScrollView rootView, @NonNull Button addAnotherBtn,
      @NonNull AutoCompleteTextView collegeAutoedit, @NonNull CheckBox currentlyPursuingCheckbox,
      @NonNull AutoCompleteTextView degreeEdit, @NonNull TextView degreeErrorText,
      @NonNull ImageView deleteIg, @NonNull RelativeLayout deleteLayout,
      @NonNull EditText passingYearEdit, @NonNull AutoCompleteTextView universityAutoedit,
      @NonNull TextView yearErrorText) {
    this.rootView = rootView;
    this.addAnotherBtn = addAnotherBtn;
    this.collegeAutoedit = collegeAutoedit;
    this.currentlyPursuingCheckbox = currentlyPursuingCheckbox;
    this.degreeEdit = degreeEdit;
    this.degreeErrorText = degreeErrorText;
    this.deleteIg = deleteIg;
    this.deleteLayout = deleteLayout;
    this.passingYearEdit = passingYearEdit;
    this.universityAutoedit = universityAutoedit;
    this.yearErrorText = yearErrorText;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAcademicBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAcademicBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_academic, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAcademicBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_another_btn;
      Button addAnotherBtn = ViewBindings.findChildViewById(rootView, id);
      if (addAnotherBtn == null) {
        break missingId;
      }

      id = R.id.college_autoedit;
      AutoCompleteTextView collegeAutoedit = ViewBindings.findChildViewById(rootView, id);
      if (collegeAutoedit == null) {
        break missingId;
      }

      id = R.id.currently_pursuing_checkbox;
      CheckBox currentlyPursuingCheckbox = ViewBindings.findChildViewById(rootView, id);
      if (currentlyPursuingCheckbox == null) {
        break missingId;
      }

      id = R.id.degree_edit;
      AutoCompleteTextView degreeEdit = ViewBindings.findChildViewById(rootView, id);
      if (degreeEdit == null) {
        break missingId;
      }

      id = R.id.degree_error_text;
      TextView degreeErrorText = ViewBindings.findChildViewById(rootView, id);
      if (degreeErrorText == null) {
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

      id = R.id.passing_year_edit;
      EditText passingYearEdit = ViewBindings.findChildViewById(rootView, id);
      if (passingYearEdit == null) {
        break missingId;
      }

      id = R.id.university_autoedit;
      AutoCompleteTextView universityAutoedit = ViewBindings.findChildViewById(rootView, id);
      if (universityAutoedit == null) {
        break missingId;
      }

      id = R.id.year_error_text;
      TextView yearErrorText = ViewBindings.findChildViewById(rootView, id);
      if (yearErrorText == null) {
        break missingId;
      }

      return new ActivityAcademicBinding((ScrollView) rootView, addAnotherBtn, collegeAutoedit,
          currentlyPursuingCheckbox, degreeEdit, degreeErrorText, deleteIg, deleteLayout,
          passingYearEdit, universityAutoedit, yearErrorText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}