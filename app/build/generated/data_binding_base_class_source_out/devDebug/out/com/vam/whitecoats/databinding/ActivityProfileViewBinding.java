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
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.ControllableAppBarLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfileViewBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final LinearLayout activityCountLayout;

  @NonNull
  public final TextView addPhotoText;

  @NonNull
  public final ControllableAppBarLayout appbar;

  @NonNull
  public final CollapsingToolbarLayout collapsingToolbar;

  @NonNull
  public final TextView connectCountText;

  @NonNull
  public final LinearLayout connectCountTextLay;

  @NonNull
  public final TextView connectText;

  @NonNull
  public final CardView deleteAccountCv;

  @NonNull
  public final ImageView experienceIcon;

  @NonNull
  public final RelativeLayout experienceLay;

  @NonNull
  public final TextView followersCount;

  @NonNull
  public final TextView followersCountText;

  @NonNull
  public final LinearLayout followersCountTextLay;

  @NonNull
  public final TextView followingCount;

  @NonNull
  public final TextView followingCountText;

  @NonNull
  public final LinearLayout followingCountTextLay;

  @NonNull
  public final CoordinatorLayout mainContent;

  @NonNull
  public final LinearLayout meSectionVerify;

  @NonNull
  public final TextView nameText;

  @NonNull
  public final NestedScrollView nestedScroll;

  @NonNull
  public final ImageView noImageOverLay;

  @NonNull
  public final TextView overAllExperience;

  @NonNull
  public final ImageView personalInfoEditIcon;

  @NonNull
  public final RelativeLayout personalInfoEditIconLay;

  @NonNull
  public final TextView postCount;

  @NonNull
  public final TextView postCountText;

  @NonNull
  public final LinearLayout postCountTextLay;

  @NonNull
  public final TextView specialityText;

  @NonNull
  public final LinearLayout specificAskLayout;

  @NonNull
  public final TextView specificAskText;

  @NonNull
  public final TextView subSpecialityText;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final ImageView userProfilePicture;

  @NonNull
  public final TextView uspText;

  @NonNull
  public final TextView verifyNowText;

  private ActivityProfileViewBinding(@NonNull CoordinatorLayout rootView,
      @NonNull LinearLayout activityCountLayout, @NonNull TextView addPhotoText,
      @NonNull ControllableAppBarLayout appbar, @NonNull CollapsingToolbarLayout collapsingToolbar,
      @NonNull TextView connectCountText, @NonNull LinearLayout connectCountTextLay,
      @NonNull TextView connectText, @NonNull CardView deleteAccountCv,
      @NonNull ImageView experienceIcon, @NonNull RelativeLayout experienceLay,
      @NonNull TextView followersCount, @NonNull TextView followersCountText,
      @NonNull LinearLayout followersCountTextLay, @NonNull TextView followingCount,
      @NonNull TextView followingCountText, @NonNull LinearLayout followingCountTextLay,
      @NonNull CoordinatorLayout mainContent, @NonNull LinearLayout meSectionVerify,
      @NonNull TextView nameText, @NonNull NestedScrollView nestedScroll,
      @NonNull ImageView noImageOverLay, @NonNull TextView overAllExperience,
      @NonNull ImageView personalInfoEditIcon, @NonNull RelativeLayout personalInfoEditIconLay,
      @NonNull TextView postCount, @NonNull TextView postCountText,
      @NonNull LinearLayout postCountTextLay, @NonNull TextView specialityText,
      @NonNull LinearLayout specificAskLayout, @NonNull TextView specificAskText,
      @NonNull TextView subSpecialityText, @NonNull Toolbar toolbar,
      @NonNull ImageView userProfilePicture, @NonNull TextView uspText,
      @NonNull TextView verifyNowText) {
    this.rootView = rootView;
    this.activityCountLayout = activityCountLayout;
    this.addPhotoText = addPhotoText;
    this.appbar = appbar;
    this.collapsingToolbar = collapsingToolbar;
    this.connectCountText = connectCountText;
    this.connectCountTextLay = connectCountTextLay;
    this.connectText = connectText;
    this.deleteAccountCv = deleteAccountCv;
    this.experienceIcon = experienceIcon;
    this.experienceLay = experienceLay;
    this.followersCount = followersCount;
    this.followersCountText = followersCountText;
    this.followersCountTextLay = followersCountTextLay;
    this.followingCount = followingCount;
    this.followingCountText = followingCountText;
    this.followingCountTextLay = followingCountTextLay;
    this.mainContent = mainContent;
    this.meSectionVerify = meSectionVerify;
    this.nameText = nameText;
    this.nestedScroll = nestedScroll;
    this.noImageOverLay = noImageOverLay;
    this.overAllExperience = overAllExperience;
    this.personalInfoEditIcon = personalInfoEditIcon;
    this.personalInfoEditIconLay = personalInfoEditIconLay;
    this.postCount = postCount;
    this.postCountText = postCountText;
    this.postCountTextLay = postCountTextLay;
    this.specialityText = specialityText;
    this.specificAskLayout = specificAskLayout;
    this.specificAskText = specificAskText;
    this.subSpecialityText = subSpecialityText;
    this.toolbar = toolbar;
    this.userProfilePicture = userProfilePicture;
    this.uspText = uspText;
    this.verifyNowText = verifyNowText;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.activity_count_layout;
      LinearLayout activityCountLayout = ViewBindings.findChildViewById(rootView, id);
      if (activityCountLayout == null) {
        break missingId;
      }

      id = R.id.add_photo_text;
      TextView addPhotoText = ViewBindings.findChildViewById(rootView, id);
      if (addPhotoText == null) {
        break missingId;
      }

      id = R.id.appbar;
      ControllableAppBarLayout appbar = ViewBindings.findChildViewById(rootView, id);
      if (appbar == null) {
        break missingId;
      }

      id = R.id.collapsing_toolbar;
      CollapsingToolbarLayout collapsingToolbar = ViewBindings.findChildViewById(rootView, id);
      if (collapsingToolbar == null) {
        break missingId;
      }

      id = R.id.connect_count_text;
      TextView connectCountText = ViewBindings.findChildViewById(rootView, id);
      if (connectCountText == null) {
        break missingId;
      }

      id = R.id.connect_count_text_lay;
      LinearLayout connectCountTextLay = ViewBindings.findChildViewById(rootView, id);
      if (connectCountTextLay == null) {
        break missingId;
      }

      id = R.id.connect_text;
      TextView connectText = ViewBindings.findChildViewById(rootView, id);
      if (connectText == null) {
        break missingId;
      }

      id = R.id.delete_account_cv;
      CardView deleteAccountCv = ViewBindings.findChildViewById(rootView, id);
      if (deleteAccountCv == null) {
        break missingId;
      }

      id = R.id.experience_icon;
      ImageView experienceIcon = ViewBindings.findChildViewById(rootView, id);
      if (experienceIcon == null) {
        break missingId;
      }

      id = R.id.experience_lay;
      RelativeLayout experienceLay = ViewBindings.findChildViewById(rootView, id);
      if (experienceLay == null) {
        break missingId;
      }

      id = R.id.followers_count;
      TextView followersCount = ViewBindings.findChildViewById(rootView, id);
      if (followersCount == null) {
        break missingId;
      }

      id = R.id.followers_count_text;
      TextView followersCountText = ViewBindings.findChildViewById(rootView, id);
      if (followersCountText == null) {
        break missingId;
      }

      id = R.id.followers_count_text_lay;
      LinearLayout followersCountTextLay = ViewBindings.findChildViewById(rootView, id);
      if (followersCountTextLay == null) {
        break missingId;
      }

      id = R.id.following_count;
      TextView followingCount = ViewBindings.findChildViewById(rootView, id);
      if (followingCount == null) {
        break missingId;
      }

      id = R.id.following_count_text;
      TextView followingCountText = ViewBindings.findChildViewById(rootView, id);
      if (followingCountText == null) {
        break missingId;
      }

      id = R.id.following_count_text_lay;
      LinearLayout followingCountTextLay = ViewBindings.findChildViewById(rootView, id);
      if (followingCountTextLay == null) {
        break missingId;
      }

      CoordinatorLayout mainContent = (CoordinatorLayout) rootView;

      id = R.id.me_section_verify;
      LinearLayout meSectionVerify = ViewBindings.findChildViewById(rootView, id);
      if (meSectionVerify == null) {
        break missingId;
      }

      id = R.id.name_text;
      TextView nameText = ViewBindings.findChildViewById(rootView, id);
      if (nameText == null) {
        break missingId;
      }

      id = R.id.nestedScroll;
      NestedScrollView nestedScroll = ViewBindings.findChildViewById(rootView, id);
      if (nestedScroll == null) {
        break missingId;
      }

      id = R.id.noImage_over_lay;
      ImageView noImageOverLay = ViewBindings.findChildViewById(rootView, id);
      if (noImageOverLay == null) {
        break missingId;
      }

      id = R.id.over_all_experience;
      TextView overAllExperience = ViewBindings.findChildViewById(rootView, id);
      if (overAllExperience == null) {
        break missingId;
      }

      id = R.id.personalInfoEditIcon;
      ImageView personalInfoEditIcon = ViewBindings.findChildViewById(rootView, id);
      if (personalInfoEditIcon == null) {
        break missingId;
      }

      id = R.id.personalInfoEditIcon_lay;
      RelativeLayout personalInfoEditIconLay = ViewBindings.findChildViewById(rootView, id);
      if (personalInfoEditIconLay == null) {
        break missingId;
      }

      id = R.id.post_count;
      TextView postCount = ViewBindings.findChildViewById(rootView, id);
      if (postCount == null) {
        break missingId;
      }

      id = R.id.post_count_text;
      TextView postCountText = ViewBindings.findChildViewById(rootView, id);
      if (postCountText == null) {
        break missingId;
      }

      id = R.id.post_count_text_lay;
      LinearLayout postCountTextLay = ViewBindings.findChildViewById(rootView, id);
      if (postCountTextLay == null) {
        break missingId;
      }

      id = R.id.speciality_text;
      TextView specialityText = ViewBindings.findChildViewById(rootView, id);
      if (specialityText == null) {
        break missingId;
      }

      id = R.id.specific_ask_layout;
      LinearLayout specificAskLayout = ViewBindings.findChildViewById(rootView, id);
      if (specificAskLayout == null) {
        break missingId;
      }

      id = R.id.specific_ask_text;
      TextView specificAskText = ViewBindings.findChildViewById(rootView, id);
      if (specificAskText == null) {
        break missingId;
      }

      id = R.id.subSpecialityText;
      TextView subSpecialityText = ViewBindings.findChildViewById(rootView, id);
      if (subSpecialityText == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.userProfilePicture;
      ImageView userProfilePicture = ViewBindings.findChildViewById(rootView, id);
      if (userProfilePicture == null) {
        break missingId;
      }

      id = R.id.usp_text;
      TextView uspText = ViewBindings.findChildViewById(rootView, id);
      if (uspText == null) {
        break missingId;
      }

      id = R.id.verify_now_text;
      TextView verifyNowText = ViewBindings.findChildViewById(rootView, id);
      if (verifyNowText == null) {
        break missingId;
      }

      return new ActivityProfileViewBinding((CoordinatorLayout) rootView, activityCountLayout,
          addPhotoText, appbar, collapsingToolbar, connectCountText, connectCountTextLay,
          connectText, deleteAccountCv, experienceIcon, experienceLay, followersCount,
          followersCountText, followersCountTextLay, followingCount, followingCountText,
          followingCountTextLay, mainContent, meSectionVerify, nameText, nestedScroll,
          noImageOverLay, overAllExperience, personalInfoEditIcon, personalInfoEditIconLay,
          postCount, postCountText, postCountTextLay, specialityText, specificAskLayout,
          specificAskText, subSpecialityText, toolbar, userProfilePicture, uspText, verifyNowText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}