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
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DrugItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView dName;

  @NonNull
  public final TextView drugHeaderTextMain;

  @NonNull
  public final LinearLayout drugItem;

  private DrugItemBinding(@NonNull LinearLayout rootView, @NonNull TextView dName,
      @NonNull TextView drugHeaderTextMain, @NonNull LinearLayout drugItem) {
    this.rootView = rootView;
    this.dName = dName;
    this.drugHeaderTextMain = drugHeaderTextMain;
    this.drugItem = drugItem;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DrugItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DrugItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.drug_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DrugItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.d_name;
      TextView dName = ViewBindings.findChildViewById(rootView, id);
      if (dName == null) {
        break missingId;
      }

      id = R.id.drug_header_text_main;
      TextView drugHeaderTextMain = ViewBindings.findChildViewById(rootView, id);
      if (drugHeaderTextMain == null) {
        break missingId;
      }

      id = R.id.drug_item;
      LinearLayout drugItem = ViewBindings.findChildViewById(rootView, id);
      if (drugItem == null) {
        break missingId;
      }

      return new DrugItemBinding((LinearLayout) rootView, dName, drugHeaderTextMain, drugItem);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
