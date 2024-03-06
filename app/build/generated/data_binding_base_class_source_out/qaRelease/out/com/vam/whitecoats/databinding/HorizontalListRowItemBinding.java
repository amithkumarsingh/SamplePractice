// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class HorizontalListRowItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView attachmentTypePdf;

  @NonNull
  public final ImageView attachmentTypeVideo;

  @NonNull
  public final RelativeLayout blank;

  @NonNull
  public final RoundedImageView docImageOrChannelImage;

  @NonNull
  public final TextView docNameOrChannelName;

  @NonNull
  public final RelativeLayout horizontalContentLayout;

  @NonNull
  public final TextView horizontalFeedDescription;

  @NonNull
  public final ImageView horizontalFeedImageView;

  @NonNull
  public final FrameLayout horizontalFeedImageViewLay;

  @NonNull
  public final TextView horizontalFeedTitle;

  @NonNull
  public final CardView imageViewCardView;

  @NonNull
  public final TextView numOfView;

  @NonNull
  public final TextView postMadeTime;

  @NonNull
  public final TextView postType;

  @NonNull
  public final RelativeLayout topLayout;

  private HorizontalListRowItemBinding(@NonNull CardView rootView,
      @NonNull ImageView attachmentTypePdf, @NonNull ImageView attachmentTypeVideo,
      @NonNull RelativeLayout blank, @NonNull RoundedImageView docImageOrChannelImage,
      @NonNull TextView docNameOrChannelName, @NonNull RelativeLayout horizontalContentLayout,
      @NonNull TextView horizontalFeedDescription, @NonNull ImageView horizontalFeedImageView,
      @NonNull FrameLayout horizontalFeedImageViewLay, @NonNull TextView horizontalFeedTitle,
      @NonNull CardView imageViewCardView, @NonNull TextView numOfView,
      @NonNull TextView postMadeTime, @NonNull TextView postType,
      @NonNull RelativeLayout topLayout) {
    this.rootView = rootView;
    this.attachmentTypePdf = attachmentTypePdf;
    this.attachmentTypeVideo = attachmentTypeVideo;
    this.blank = blank;
    this.docImageOrChannelImage = docImageOrChannelImage;
    this.docNameOrChannelName = docNameOrChannelName;
    this.horizontalContentLayout = horizontalContentLayout;
    this.horizontalFeedDescription = horizontalFeedDescription;
    this.horizontalFeedImageView = horizontalFeedImageView;
    this.horizontalFeedImageViewLay = horizontalFeedImageViewLay;
    this.horizontalFeedTitle = horizontalFeedTitle;
    this.imageViewCardView = imageViewCardView;
    this.numOfView = numOfView;
    this.postMadeTime = postMadeTime;
    this.postType = postType;
    this.topLayout = topLayout;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static HorizontalListRowItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static HorizontalListRowItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.horizontal_list_row_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static HorizontalListRowItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.attachment_type_pdf;
      ImageView attachmentTypePdf = ViewBindings.findChildViewById(rootView, id);
      if (attachmentTypePdf == null) {
        break missingId;
      }

      id = R.id.attachment_type_video;
      ImageView attachmentTypeVideo = ViewBindings.findChildViewById(rootView, id);
      if (attachmentTypeVideo == null) {
        break missingId;
      }

      id = R.id.blank;
      RelativeLayout blank = ViewBindings.findChildViewById(rootView, id);
      if (blank == null) {
        break missingId;
      }

      id = R.id.doc_image_or_channel_image;
      RoundedImageView docImageOrChannelImage = ViewBindings.findChildViewById(rootView, id);
      if (docImageOrChannelImage == null) {
        break missingId;
      }

      id = R.id.doc_name_or_channel_name;
      TextView docNameOrChannelName = ViewBindings.findChildViewById(rootView, id);
      if (docNameOrChannelName == null) {
        break missingId;
      }

      id = R.id.horizontal_content_layout;
      RelativeLayout horizontalContentLayout = ViewBindings.findChildViewById(rootView, id);
      if (horizontalContentLayout == null) {
        break missingId;
      }

      id = R.id.horizontal_feed_description;
      TextView horizontalFeedDescription = ViewBindings.findChildViewById(rootView, id);
      if (horizontalFeedDescription == null) {
        break missingId;
      }

      id = R.id.horizontal_feed_image_view;
      ImageView horizontalFeedImageView = ViewBindings.findChildViewById(rootView, id);
      if (horizontalFeedImageView == null) {
        break missingId;
      }

      id = R.id.horizontal_feed_image_view_lay;
      FrameLayout horizontalFeedImageViewLay = ViewBindings.findChildViewById(rootView, id);
      if (horizontalFeedImageViewLay == null) {
        break missingId;
      }

      id = R.id.horizontal_feed_title;
      TextView horizontalFeedTitle = ViewBindings.findChildViewById(rootView, id);
      if (horizontalFeedTitle == null) {
        break missingId;
      }

      id = R.id.image_view_cardView;
      CardView imageViewCardView = ViewBindings.findChildViewById(rootView, id);
      if (imageViewCardView == null) {
        break missingId;
      }

      id = R.id.num_of_view;
      TextView numOfView = ViewBindings.findChildViewById(rootView, id);
      if (numOfView == null) {
        break missingId;
      }

      id = R.id.post_made_time;
      TextView postMadeTime = ViewBindings.findChildViewById(rootView, id);
      if (postMadeTime == null) {
        break missingId;
      }

      id = R.id.post_type;
      TextView postType = ViewBindings.findChildViewById(rootView, id);
      if (postType == null) {
        break missingId;
      }

      id = R.id.top_layout;
      RelativeLayout topLayout = ViewBindings.findChildViewById(rootView, id);
      if (topLayout == null) {
        break missingId;
      }

      return new HorizontalListRowItemBinding((CardView) rootView, attachmentTypePdf,
          attachmentTypeVideo, blank, docImageOrChannelImage, docNameOrChannelName,
          horizontalContentLayout, horizontalFeedDescription, horizontalFeedImageView,
          horizontalFeedImageViewLay, horizontalFeedTitle, imageViewCardView, numOfView,
          postMadeTime, postType, topLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}