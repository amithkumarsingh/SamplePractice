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
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MyContactsListitemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RoundedImageView imageurl;

  @NonNull
  public final TextView nameTxt;

  @NonNull
  public final TextView specialityTxt;

  private MyContactsListitemBinding(@NonNull LinearLayout rootView,
      @NonNull RoundedImageView imageurl, @NonNull TextView nameTxt,
      @NonNull TextView specialityTxt) {
    this.rootView = rootView;
    this.imageurl = imageurl;
    this.nameTxt = nameTxt;
    this.specialityTxt = specialityTxt;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MyContactsListitemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MyContactsListitemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.my_contacts_listitem, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MyContactsListitemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageurl;
      RoundedImageView imageurl = ViewBindings.findChildViewById(rootView, id);
      if (imageurl == null) {
        break missingId;
      }

      id = R.id.name_txt;
      TextView nameTxt = ViewBindings.findChildViewById(rootView, id);
      if (nameTxt == null) {
        break missingId;
      }

      id = R.id.speciality_txt;
      TextView specialityTxt = ViewBindings.findChildViewById(rootView, id);
      if (specialityTxt == null) {
        break missingId;
      }

      return new MyContactsListitemBinding((LinearLayout) rootView, imageurl, nameTxt,
          specialityTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
