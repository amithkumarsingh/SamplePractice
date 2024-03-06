// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.NonScrollListView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ProfilePublicationSectionBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView addPublicationsHeading;

  @NonNull
  public final ImageView publicationAddImg;

  @NonNull
  public final RelativeLayout publicationAddImgLay;

  @NonNull
  public final NonScrollListView publicationsPrintList;

  @NonNull
  public final TextView titleText;

  @NonNull
  public final TextView viewAllPublications;

  private ProfilePublicationSectionBinding(@NonNull LinearLayout rootView,
      @NonNull TextView addPublicationsHeading, @NonNull ImageView publicationAddImg,
      @NonNull RelativeLayout publicationAddImgLay,
      @NonNull NonScrollListView publicationsPrintList, @NonNull TextView titleText,
      @NonNull TextView viewAllPublications) {
    this.rootView = rootView;
    this.addPublicationsHeading = addPublicationsHeading;
    this.publicationAddImg = publicationAddImg;
    this.publicationAddImgLay = publicationAddImgLay;
    this.publicationsPrintList = publicationsPrintList;
    this.titleText = titleText;
    this.viewAllPublications = viewAllPublications;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ProfilePublicationSectionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProfilePublicationSectionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.profile_publication_section, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProfilePublicationSectionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_publications_heading;
      TextView addPublicationsHeading = ViewBindings.findChildViewById(rootView, id);
      if (addPublicationsHeading == null) {
        break missingId;
      }

      id = R.id.publication_add_img;
      ImageView publicationAddImg = ViewBindings.findChildViewById(rootView, id);
      if (publicationAddImg == null) {
        break missingId;
      }

      id = R.id.publication_add_img_lay;
      RelativeLayout publicationAddImgLay = ViewBindings.findChildViewById(rootView, id);
      if (publicationAddImgLay == null) {
        break missingId;
      }

      id = R.id.publications_print_list;
      NonScrollListView publicationsPrintList = ViewBindings.findChildViewById(rootView, id);
      if (publicationsPrintList == null) {
        break missingId;
      }

      id = R.id.title_text;
      TextView titleText = ViewBindings.findChildViewById(rootView, id);
      if (titleText == null) {
        break missingId;
      }

      id = R.id.view_all_publications;
      TextView viewAllPublications = ViewBindings.findChildViewById(rootView, id);
      if (viewAllPublications == null) {
        break missingId;
      }

      return new ProfilePublicationSectionBinding((LinearLayout) rootView, addPublicationsHeading,
          publicationAddImg, publicationAddImgLay, publicationsPrintList, titleText,
          viewAllPublications);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
