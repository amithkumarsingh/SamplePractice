// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

public final class BottomSheetChildLayJobsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CheckBox selectionCheckbox;

  @NonNull
  public final TextView specialityOrLocationName;

  private BottomSheetChildLayJobsBinding(@NonNull LinearLayout rootView,
      @NonNull CheckBox selectionCheckbox, @NonNull TextView specialityOrLocationName) {
    this.rootView = rootView;
    this.selectionCheckbox = selectionCheckbox;
    this.specialityOrLocationName = specialityOrLocationName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetChildLayJobsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetChildLayJobsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_child_lay_jobs, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetChildLayJobsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.selection_checkbox;
      CheckBox selectionCheckbox = ViewBindings.findChildViewById(rootView, id);
      if (selectionCheckbox == null) {
        break missingId;
      }

      id = R.id.speciality_or_location_name;
      TextView specialityOrLocationName = ViewBindings.findChildViewById(rootView, id);
      if (specialityOrLocationName == null) {
        break missingId;
      }

      return new BottomSheetChildLayJobsBinding((LinearLayout) rootView, selectionCheckbox,
          specialityOrLocationName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}