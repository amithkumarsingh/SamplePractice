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
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DrugCustomTabBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView drugItemIcon;

  @NonNull
  public final RelativeLayout drugItemLayout;

  @NonNull
  public final TextView drugItemName;

  private DrugCustomTabBinding(@NonNull RelativeLayout rootView, @NonNull ImageView drugItemIcon,
      @NonNull RelativeLayout drugItemLayout, @NonNull TextView drugItemName) {
    this.rootView = rootView;
    this.drugItemIcon = drugItemIcon;
    this.drugItemLayout = drugItemLayout;
    this.drugItemName = drugItemName;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DrugCustomTabBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DrugCustomTabBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.drug_custom_tab, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DrugCustomTabBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.drug_item_icon;
      ImageView drugItemIcon = ViewBindings.findChildViewById(rootView, id);
      if (drugItemIcon == null) {
        break missingId;
      }

      id = R.id.drug_item_layout;
      RelativeLayout drugItemLayout = ViewBindings.findChildViewById(rootView, id);
      if (drugItemLayout == null) {
        break missingId;
      }

      id = R.id.drug_item_name;
      TextView drugItemName = ViewBindings.findChildViewById(rootView, id);
      if (drugItemName == null) {
        break missingId;
      }

      return new DrugCustomTabBinding((RelativeLayout) rootView, drugItemIcon, drugItemLayout,
          drugItemName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}