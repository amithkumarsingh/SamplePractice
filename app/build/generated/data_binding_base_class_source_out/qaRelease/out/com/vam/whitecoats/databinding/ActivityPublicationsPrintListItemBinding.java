// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPublicationsPrintListItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final LinearLayout editLayout;

  @NonNull
  public final EditText itemEditText;

  @NonNull
  public final TextView printText;

  @NonNull
  public final RelativeLayout removeLayout;

  @NonNull
  public final TextView removePublicationTx;

  private ActivityPublicationsPrintListItemBinding(@NonNull LinearLayout rootView,
      @NonNull CardView cardView, @NonNull LinearLayout editLayout, @NonNull EditText itemEditText,
      @NonNull TextView printText, @NonNull RelativeLayout removeLayout,
      @NonNull TextView removePublicationTx) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.editLayout = editLayout;
    this.itemEditText = itemEditText;
    this.printText = printText;
    this.removeLayout = removeLayout;
    this.removePublicationTx = removePublicationTx;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPublicationsPrintListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPublicationsPrintListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_publications_print_list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPublicationsPrintListItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.card_view;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.edit_layout;
      LinearLayout editLayout = ViewBindings.findChildViewById(rootView, id);
      if (editLayout == null) {
        break missingId;
      }

      id = R.id.item_edit_text;
      EditText itemEditText = ViewBindings.findChildViewById(rootView, id);
      if (itemEditText == null) {
        break missingId;
      }

      id = R.id.print_text;
      TextView printText = ViewBindings.findChildViewById(rootView, id);
      if (printText == null) {
        break missingId;
      }

      id = R.id.remove_layout;
      RelativeLayout removeLayout = ViewBindings.findChildViewById(rootView, id);
      if (removeLayout == null) {
        break missingId;
      }

      id = R.id.remove_publication_tx;
      TextView removePublicationTx = ViewBindings.findChildViewById(rootView, id);
      if (removePublicationTx == null) {
        break missingId;
      }

      return new ActivityPublicationsPrintListItemBinding((LinearLayout) rootView, cardView,
          editLayout, itemEditText, printText, removeLayout, removePublicationTx);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
