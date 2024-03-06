// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FullviewPagerItemBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final TextView articleTypeCommunity;

  @NonNull
  public final ImageView attachmentIconSummary;

  @NonNull
  public final ImageView attachmentVideoType;

  @NonNull
  public final ActivityMediaplayerBinding audioPlayerLayout;

  @NonNull
  public final RelativeLayout blank;

  @NonNull
  public final LinearLayout bookmarkCommunityFullView;

  @NonNull
  public final ImageView bookmarkCommunityFullViewImageView;

  @NonNull
  public final LinearLayout communityAttachmentLayoutSummary;

  @NonNull
  public final ImageView contextImage;

  @NonNull
  public final TextView edited;

  @NonNull
  public final NestedScrollView feedContentScrollView;

  @NonNull
  public final LinearLayout feedDescLayout;

  @NonNull
  public final LatestCommentViewgroupBinding feedFullviewCommentLayout;

  @NonNull
  public final TextView feedPostedDate;

  @NonNull
  public final TextView feedTitle;

  @NonNull
  public final LinearLayout fullContentLayout;

  @NonNull
  public final TextView fullPostCreatedBy;

  @NonNull
  public final RecyclerView fullViewThumbnailView;

  @NonNull
  public final Button ifInterestedButton;

  @NonNull
  public final TextView labelAttachmentNameSummary;

  @NonNull
  public final RoundedImageView prfLogoFullview;

  @NonNull
  public final LinearLayout surveyRootFullLayout;

  @NonNull
  public final LinearLayout typeBookmarkLay;

  @NonNull
  public final RelativeLayout typeWebinarLay;

  @NonNull
  public final ViewPager viewPager;

  @NonNull
  public final TextView webinarStatus;

  @NonNull
  public final LinearLayout webinarTimeDate;

  @NonNull
  public final TextView webinarType;

  private FullviewPagerItemBinding(@NonNull NestedScrollView rootView,
      @NonNull TextView articleTypeCommunity, @NonNull ImageView attachmentIconSummary,
      @NonNull ImageView attachmentVideoType, @NonNull ActivityMediaplayerBinding audioPlayerLayout,
      @NonNull RelativeLayout blank, @NonNull LinearLayout bookmarkCommunityFullView,
      @NonNull ImageView bookmarkCommunityFullViewImageView,
      @NonNull LinearLayout communityAttachmentLayoutSummary, @NonNull ImageView contextImage,
      @NonNull TextView edited, @NonNull NestedScrollView feedContentScrollView,
      @NonNull LinearLayout feedDescLayout,
      @NonNull LatestCommentViewgroupBinding feedFullviewCommentLayout,
      @NonNull TextView feedPostedDate, @NonNull TextView feedTitle,
      @NonNull LinearLayout fullContentLayout, @NonNull TextView fullPostCreatedBy,
      @NonNull RecyclerView fullViewThumbnailView, @NonNull Button ifInterestedButton,
      @NonNull TextView labelAttachmentNameSummary, @NonNull RoundedImageView prfLogoFullview,
      @NonNull LinearLayout surveyRootFullLayout, @NonNull LinearLayout typeBookmarkLay,
      @NonNull RelativeLayout typeWebinarLay, @NonNull ViewPager viewPager,
      @NonNull TextView webinarStatus, @NonNull LinearLayout webinarTimeDate,
      @NonNull TextView webinarType) {
    this.rootView = rootView;
    this.articleTypeCommunity = articleTypeCommunity;
    this.attachmentIconSummary = attachmentIconSummary;
    this.attachmentVideoType = attachmentVideoType;
    this.audioPlayerLayout = audioPlayerLayout;
    this.blank = blank;
    this.bookmarkCommunityFullView = bookmarkCommunityFullView;
    this.bookmarkCommunityFullViewImageView = bookmarkCommunityFullViewImageView;
    this.communityAttachmentLayoutSummary = communityAttachmentLayoutSummary;
    this.contextImage = contextImage;
    this.edited = edited;
    this.feedContentScrollView = feedContentScrollView;
    this.feedDescLayout = feedDescLayout;
    this.feedFullviewCommentLayout = feedFullviewCommentLayout;
    this.feedPostedDate = feedPostedDate;
    this.feedTitle = feedTitle;
    this.fullContentLayout = fullContentLayout;
    this.fullPostCreatedBy = fullPostCreatedBy;
    this.fullViewThumbnailView = fullViewThumbnailView;
    this.ifInterestedButton = ifInterestedButton;
    this.labelAttachmentNameSummary = labelAttachmentNameSummary;
    this.prfLogoFullview = prfLogoFullview;
    this.surveyRootFullLayout = surveyRootFullLayout;
    this.typeBookmarkLay = typeBookmarkLay;
    this.typeWebinarLay = typeWebinarLay;
    this.viewPager = viewPager;
    this.webinarStatus = webinarStatus;
    this.webinarTimeDate = webinarTimeDate;
    this.webinarType = webinarType;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FullviewPagerItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FullviewPagerItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fullview_pager_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FullviewPagerItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.article_type_community;
      TextView articleTypeCommunity = ViewBindings.findChildViewById(rootView, id);
      if (articleTypeCommunity == null) {
        break missingId;
      }

      id = R.id.attachment_icon_summary;
      ImageView attachmentIconSummary = ViewBindings.findChildViewById(rootView, id);
      if (attachmentIconSummary == null) {
        break missingId;
      }

      id = R.id.attachment_video_type;
      ImageView attachmentVideoType = ViewBindings.findChildViewById(rootView, id);
      if (attachmentVideoType == null) {
        break missingId;
      }

      id = R.id.audio_player_layout;
      View audioPlayerLayout = ViewBindings.findChildViewById(rootView, id);
      if (audioPlayerLayout == null) {
        break missingId;
      }
      ActivityMediaplayerBinding binding_audioPlayerLayout = ActivityMediaplayerBinding.bind(audioPlayerLayout);

      id = R.id.blank;
      RelativeLayout blank = ViewBindings.findChildViewById(rootView, id);
      if (blank == null) {
        break missingId;
      }

      id = R.id.bookmark_community_fullView;
      LinearLayout bookmarkCommunityFullView = ViewBindings.findChildViewById(rootView, id);
      if (bookmarkCommunityFullView == null) {
        break missingId;
      }

      id = R.id.bookmark_community_fullView_ImageView;
      ImageView bookmarkCommunityFullViewImageView = ViewBindings.findChildViewById(rootView, id);
      if (bookmarkCommunityFullViewImageView == null) {
        break missingId;
      }

      id = R.id.community_attachment_layout_summary;
      LinearLayout communityAttachmentLayoutSummary = ViewBindings.findChildViewById(rootView, id);
      if (communityAttachmentLayoutSummary == null) {
        break missingId;
      }

      id = R.id.context_image;
      ImageView contextImage = ViewBindings.findChildViewById(rootView, id);
      if (contextImage == null) {
        break missingId;
      }

      id = R.id.edited;
      TextView edited = ViewBindings.findChildViewById(rootView, id);
      if (edited == null) {
        break missingId;
      }

      NestedScrollView feedContentScrollView = (NestedScrollView) rootView;

      id = R.id.feed_desc_layout;
      LinearLayout feedDescLayout = ViewBindings.findChildViewById(rootView, id);
      if (feedDescLayout == null) {
        break missingId;
      }

      id = R.id.feed_fullview_comment_layout;
      View feedFullviewCommentLayout = ViewBindings.findChildViewById(rootView, id);
      if (feedFullviewCommentLayout == null) {
        break missingId;
      }
      LatestCommentViewgroupBinding binding_feedFullviewCommentLayout = LatestCommentViewgroupBinding.bind(feedFullviewCommentLayout);

      id = R.id.feed_posted_date;
      TextView feedPostedDate = ViewBindings.findChildViewById(rootView, id);
      if (feedPostedDate == null) {
        break missingId;
      }

      id = R.id.feed_title;
      TextView feedTitle = ViewBindings.findChildViewById(rootView, id);
      if (feedTitle == null) {
        break missingId;
      }

      id = R.id.full_content_layout;
      LinearLayout fullContentLayout = ViewBindings.findChildViewById(rootView, id);
      if (fullContentLayout == null) {
        break missingId;
      }

      id = R.id.fullPost_createdBy;
      TextView fullPostCreatedBy = ViewBindings.findChildViewById(rootView, id);
      if (fullPostCreatedBy == null) {
        break missingId;
      }

      id = R.id.fullView_thumbnail_view;
      RecyclerView fullViewThumbnailView = ViewBindings.findChildViewById(rootView, id);
      if (fullViewThumbnailView == null) {
        break missingId;
      }

      id = R.id.if_interested_button;
      Button ifInterestedButton = ViewBindings.findChildViewById(rootView, id);
      if (ifInterestedButton == null) {
        break missingId;
      }

      id = R.id.label_attachment_name_summary;
      TextView labelAttachmentNameSummary = ViewBindings.findChildViewById(rootView, id);
      if (labelAttachmentNameSummary == null) {
        break missingId;
      }

      id = R.id.prfLogo_fullview;
      RoundedImageView prfLogoFullview = ViewBindings.findChildViewById(rootView, id);
      if (prfLogoFullview == null) {
        break missingId;
      }

      id = R.id.survey_root_full_layout;
      LinearLayout surveyRootFullLayout = ViewBindings.findChildViewById(rootView, id);
      if (surveyRootFullLayout == null) {
        break missingId;
      }

      id = R.id.type_bookmark_lay;
      LinearLayout typeBookmarkLay = ViewBindings.findChildViewById(rootView, id);
      if (typeBookmarkLay == null) {
        break missingId;
      }

      id = R.id.type_webinar_lay;
      RelativeLayout typeWebinarLay = ViewBindings.findChildViewById(rootView, id);
      if (typeWebinarLay == null) {
        break missingId;
      }

      id = R.id.view_pager;
      ViewPager viewPager = ViewBindings.findChildViewById(rootView, id);
      if (viewPager == null) {
        break missingId;
      }

      id = R.id.webinar_status;
      TextView webinarStatus = ViewBindings.findChildViewById(rootView, id);
      if (webinarStatus == null) {
        break missingId;
      }

      id = R.id.webinar_time_date;
      LinearLayout webinarTimeDate = ViewBindings.findChildViewById(rootView, id);
      if (webinarTimeDate == null) {
        break missingId;
      }

      id = R.id.webinar_type;
      TextView webinarType = ViewBindings.findChildViewById(rootView, id);
      if (webinarType == null) {
        break missingId;
      }

      return new FullviewPagerItemBinding((NestedScrollView) rootView, articleTypeCommunity,
          attachmentIconSummary, attachmentVideoType, binding_audioPlayerLayout, blank,
          bookmarkCommunityFullView, bookmarkCommunityFullViewImageView,
          communityAttachmentLayoutSummary, contextImage, edited, feedContentScrollView,
          feedDescLayout, binding_feedFullviewCommentLayout, feedPostedDate, feedTitle,
          fullContentLayout, fullPostCreatedBy, fullViewThumbnailView, ifInterestedButton,
          labelAttachmentNameSummary, prfLogoFullview, surveyRootFullLayout, typeBookmarkLay,
          typeWebinarLay, viewPager, webinarStatus, webinarTimeDate, webinarType);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
