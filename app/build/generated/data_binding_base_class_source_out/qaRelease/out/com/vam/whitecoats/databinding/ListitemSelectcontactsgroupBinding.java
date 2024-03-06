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
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListitemSelectcontactsgroupBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CheckBox checkbox;

  @NonNull
  public final LinearLayout contactGroupItemLayout;

  @NonNull
  public final LinearLayout groupItemLayout;

  @NonNull
  public final LinearLayout linearlayout;

  @NonNull
  public final TextView name;

  @NonNull
  public final RoundedImageView profilepic;

  @NonNull
  public final TextView speciality;

  private ListitemSelectcontactsgroupBinding(@NonNull LinearLayout rootView,
      @NonNull CheckBox checkbox, @NonNull LinearLayout contactGroupItemLayout,
      @NonNull LinearLayout groupItemLayout, @NonNull LinearLayout linearlayout,
      @NonNull TextView name, @NonNull RoundedImageView profilepic, @NonNull TextView speciality) {
    this.rootView = rootView;
    this.checkbox = checkbox;
    this.contactGroupItemLayout = contactGroupItemLayout;
    this.groupItemLayout = groupItemLayout;
    this.linearlayout = linearlayout;
    this.name = name;
    this.profilepic = profilepic;
    this.speciality = speciality;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListitemSelectcontactsgroupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListitemSelectcontactsgroupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.listitem_selectcontactsgroup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListitemSelectcontactsgroupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.checkbox;
      CheckBox checkbox = ViewBindings.findChildViewById(rootView, id);
      if (checkbox == null) {
        break missingId;
      }

      id = R.id.contact_group_item_layout;
      LinearLayout contactGroupItemLayout = ViewBindings.findChildViewById(rootView, id);
      if (contactGroupItemLayout == null) {
        break missingId;
      }

      id = R.id.group_item_layout;
      LinearLayout groupItemLayout = ViewBindings.findChildViewById(rootView, id);
      if (groupItemLayout == null) {
        break missingId;
      }

      id = R.id.linearlayout;
      LinearLayout linearlayout = ViewBindings.findChildViewById(rootView, id);
      if (linearlayout == null) {
        break missingId;
      }

      id = R.id.name;
      TextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      id = R.id.profilepic;
      RoundedImageView profilepic = ViewBindings.findChildViewById(rootView, id);
      if (profilepic == null) {
        break missingId;
      }

      id = R.id.speciality;
      TextView speciality = ViewBindings.findChildViewById(rootView, id);
      if (speciality == null) {
        break missingId;
      }

      return new ListitemSelectcontactsgroupBinding((LinearLayout) rootView, checkbox,
          contactGroupItemLayout, groupItemLayout, linearlayout, name, profilepic, speciality);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
