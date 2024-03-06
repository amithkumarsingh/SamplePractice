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

public final class ActionbarAttachementsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView delIcon;

  @NonNull
  public final ImageView editIcon;

  @NonNull
  public final TextView nextButton;

  @NonNull
  public final TextView titleEdit;

  private ActionbarAttachementsBinding(@NonNull RelativeLayout rootView, @NonNull ImageView delIcon,
      @NonNull ImageView editIcon, @NonNull TextView nextButton, @NonNull TextView titleEdit) {
    this.rootView = rootView;
    this.delIcon = delIcon;
    this.editIcon = editIcon;
    this.nextButton = nextButton;
    this.titleEdit = titleEdit;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActionbarAttachementsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActionbarAttachementsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.actionbar_attachements, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActionbarAttachementsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.delIcon;
      ImageView delIcon = ViewBindings.findChildViewById(rootView, id);
      if (delIcon == null) {
        break missingId;
      }

      id = R.id.editIcon;
      ImageView editIcon = ViewBindings.findChildViewById(rootView, id);
      if (editIcon == null) {
        break missingId;
      }

      id = R.id.next_button;
      TextView nextButton = ViewBindings.findChildViewById(rootView, id);
      if (nextButton == null) {
        break missingId;
      }

      id = R.id.title_edit;
      TextView titleEdit = ViewBindings.findChildViewById(rootView, id);
      if (titleEdit == null) {
        break missingId;
      }

      return new ActionbarAttachementsBinding((RelativeLayout) rootView, delIcon, editIcon,
          nextButton, titleEdit);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}