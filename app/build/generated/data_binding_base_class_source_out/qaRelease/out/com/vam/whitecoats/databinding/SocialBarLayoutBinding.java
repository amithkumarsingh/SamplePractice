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
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SocialBarLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout commentActionLayout;

  @NonNull
  public final TextView commentCount;

  @NonNull
  public final ImageView commentIconDashboard;

  @NonNull
  public final TextView commentTextviewDashboard;

  @NonNull
  public final LinearLayout likeActionLayout;

  @NonNull
  public final TextView likeCount;

  @NonNull
  public final ImageView likeIcon;

  @NonNull
  public final TextView likeTextview;

  @NonNull
  public final ImageView shareIconDashboard;

  @NonNull
  public final LinearLayout shareLayoutDashboard;

  @NonNull
  public final TextView shareTextviewDashboard;

  @NonNull
  public final LinearLayout socialBarAction;

  @NonNull
  public final RelativeLayout socialBarCount;

  @NonNull
  public final TextView tvShareCount;

  @NonNull
  public final TextView viewCount;

  private SocialBarLayoutBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout commentActionLayout, @NonNull TextView commentCount,
      @NonNull ImageView commentIconDashboard, @NonNull TextView commentTextviewDashboard,
      @NonNull LinearLayout likeActionLayout, @NonNull TextView likeCount,
      @NonNull ImageView likeIcon, @NonNull TextView likeTextview,
      @NonNull ImageView shareIconDashboard, @NonNull LinearLayout shareLayoutDashboard,
      @NonNull TextView shareTextviewDashboard, @NonNull LinearLayout socialBarAction,
      @NonNull RelativeLayout socialBarCount, @NonNull TextView tvShareCount,
      @NonNull TextView viewCount) {
    this.rootView = rootView;
    this.commentActionLayout = commentActionLayout;
    this.commentCount = commentCount;
    this.commentIconDashboard = commentIconDashboard;
    this.commentTextviewDashboard = commentTextviewDashboard;
    this.likeActionLayout = likeActionLayout;
    this.likeCount = likeCount;
    this.likeIcon = likeIcon;
    this.likeTextview = likeTextview;
    this.shareIconDashboard = shareIconDashboard;
    this.shareLayoutDashboard = shareLayoutDashboard;
    this.shareTextviewDashboard = shareTextviewDashboard;
    this.socialBarAction = socialBarAction;
    this.socialBarCount = socialBarCount;
    this.tvShareCount = tvShareCount;
    this.viewCount = viewCount;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SocialBarLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SocialBarLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.social_bar_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SocialBarLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.comment_action_layout;
      LinearLayout commentActionLayout = ViewBindings.findChildViewById(rootView, id);
      if (commentActionLayout == null) {
        break missingId;
      }

      id = R.id.comment_count;
      TextView commentCount = ViewBindings.findChildViewById(rootView, id);
      if (commentCount == null) {
        break missingId;
      }

      id = R.id.comment_icon_dashboard;
      ImageView commentIconDashboard = ViewBindings.findChildViewById(rootView, id);
      if (commentIconDashboard == null) {
        break missingId;
      }

      id = R.id.comment_textview_dashboard;
      TextView commentTextviewDashboard = ViewBindings.findChildViewById(rootView, id);
      if (commentTextviewDashboard == null) {
        break missingId;
      }

      id = R.id.like_action_layout;
      LinearLayout likeActionLayout = ViewBindings.findChildViewById(rootView, id);
      if (likeActionLayout == null) {
        break missingId;
      }

      id = R.id.like_count;
      TextView likeCount = ViewBindings.findChildViewById(rootView, id);
      if (likeCount == null) {
        break missingId;
      }

      id = R.id.like_icon;
      ImageView likeIcon = ViewBindings.findChildViewById(rootView, id);
      if (likeIcon == null) {
        break missingId;
      }

      id = R.id.like_textview;
      TextView likeTextview = ViewBindings.findChildViewById(rootView, id);
      if (likeTextview == null) {
        break missingId;
      }

      id = R.id.share_icon_dashboard;
      ImageView shareIconDashboard = ViewBindings.findChildViewById(rootView, id);
      if (shareIconDashboard == null) {
        break missingId;
      }

      id = R.id.share_layout_dashboard;
      LinearLayout shareLayoutDashboard = ViewBindings.findChildViewById(rootView, id);
      if (shareLayoutDashboard == null) {
        break missingId;
      }

      id = R.id.share_textview_dashboard;
      TextView shareTextviewDashboard = ViewBindings.findChildViewById(rootView, id);
      if (shareTextviewDashboard == null) {
        break missingId;
      }

      id = R.id.social_bar_action;
      LinearLayout socialBarAction = ViewBindings.findChildViewById(rootView, id);
      if (socialBarAction == null) {
        break missingId;
      }

      id = R.id.socialBar_count;
      RelativeLayout socialBarCount = ViewBindings.findChildViewById(rootView, id);
      if (socialBarCount == null) {
        break missingId;
      }

      id = R.id.tv_share_count;
      TextView tvShareCount = ViewBindings.findChildViewById(rootView, id);
      if (tvShareCount == null) {
        break missingId;
      }

      id = R.id.view_count;
      TextView viewCount = ViewBindings.findChildViewById(rootView, id);
      if (viewCount == null) {
        break missingId;
      }

      return new SocialBarLayoutBinding((LinearLayout) rootView, commentActionLayout, commentCount,
          commentIconDashboard, commentTextviewDashboard, likeActionLayout, likeCount, likeIcon,
          likeTextview, shareIconDashboard, shareLayoutDashboard, shareTextviewDashboard,
          socialBarAction, socialBarCount, tvShareCount, viewCount);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}