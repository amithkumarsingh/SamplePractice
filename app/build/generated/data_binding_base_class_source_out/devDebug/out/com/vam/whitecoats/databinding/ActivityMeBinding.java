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

public final class ActivityMeBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ActivityCardDialogBinding addPublicationsLayout1;

  @NonNull
  public final LinearLayout dashLayoutContatcs;

  @NonNull
  public final LinearLayout dashLayoutCoserooms;

  @NonNull
  public final LinearLayout dashLayoutGroup;

  @NonNull
  public final TextView dashTextContGroups;

  @NonNull
  public final TextView dashTextContNum;

  @NonNull
  public final TextView dashTextCoseroomsNum;

  @NonNull
  public final View separator;

  @NonNull
  public final View separator1;

  @NonNull
  public final TextView textView11;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView textView9;

  private ActivityMeBinding(@NonNull LinearLayout rootView,
      @NonNull ActivityCardDialogBinding addPublicationsLayout1,
      @NonNull LinearLayout dashLayoutContatcs, @NonNull LinearLayout dashLayoutCoserooms,
      @NonNull LinearLayout dashLayoutGroup, @NonNull TextView dashTextContGroups,
      @NonNull TextView dashTextContNum, @NonNull TextView dashTextCoseroomsNum,
      @NonNull View separator, @NonNull View separator1, @NonNull TextView textView11,
      @NonNull TextView textView7, @NonNull TextView textView9) {
    this.rootView = rootView;
    this.addPublicationsLayout1 = addPublicationsLayout1;
    this.dashLayoutContatcs = dashLayoutContatcs;
    this.dashLayoutCoserooms = dashLayoutCoserooms;
    this.dashLayoutGroup = dashLayoutGroup;
    this.dashTextContGroups = dashTextContGroups;
    this.dashTextContNum = dashTextContNum;
    this.dashTextCoseroomsNum = dashTextCoseroomsNum;
    this.separator = separator;
    this.separator1 = separator1;
    this.textView11 = textView11;
    this.textView7 = textView7;
    this.textView9 = textView9;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_me, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_publications_layout1;
      View addPublicationsLayout1 = ViewBindings.findChildViewById(rootView, id);
      if (addPublicationsLayout1 == null) {
        break missingId;
      }
      ActivityCardDialogBinding binding_addPublicationsLayout1 = ActivityCardDialogBinding.bind(addPublicationsLayout1);

      id = R.id.dash_layout_contatcs;
      LinearLayout dashLayoutContatcs = ViewBindings.findChildViewById(rootView, id);
      if (dashLayoutContatcs == null) {
        break missingId;
      }

      id = R.id.dash_layout_coserooms;
      LinearLayout dashLayoutCoserooms = ViewBindings.findChildViewById(rootView, id);
      if (dashLayoutCoserooms == null) {
        break missingId;
      }

      id = R.id.dash_layout_group;
      LinearLayout dashLayoutGroup = ViewBindings.findChildViewById(rootView, id);
      if (dashLayoutGroup == null) {
        break missingId;
      }

      id = R.id.dash_text_cont_groups;
      TextView dashTextContGroups = ViewBindings.findChildViewById(rootView, id);
      if (dashTextContGroups == null) {
        break missingId;
      }

      id = R.id.dash_text_cont_num;
      TextView dashTextContNum = ViewBindings.findChildViewById(rootView, id);
      if (dashTextContNum == null) {
        break missingId;
      }

      id = R.id.dash_text_coserooms_num;
      TextView dashTextCoseroomsNum = ViewBindings.findChildViewById(rootView, id);
      if (dashTextCoseroomsNum == null) {
        break missingId;
      }

      id = R.id.separator;
      View separator = ViewBindings.findChildViewById(rootView, id);
      if (separator == null) {
        break missingId;
      }

      id = R.id.separator1;
      View separator1 = ViewBindings.findChildViewById(rootView, id);
      if (separator1 == null) {
        break missingId;
      }

      id = R.id.textView11;
      TextView textView11 = ViewBindings.findChildViewById(rootView, id);
      if (textView11 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = ViewBindings.findChildViewById(rootView, id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.textView9;
      TextView textView9 = ViewBindings.findChildViewById(rootView, id);
      if (textView9 == null) {
        break missingId;
      }

      return new ActivityMeBinding((LinearLayout) rootView, binding_addPublicationsLayout1,
          dashLayoutContatcs, dashLayoutCoserooms, dashLayoutGroup, dashTextContGroups,
          dashTextContNum, dashTextCoseroomsNum, separator, separator1, textView11, textView7,
          textView9);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}