// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAboutmeBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final LinearLayout blogImageLayout;

  @NonNull
  public final EditText blogPageEdit;

  @NonNull
  public final ImageView blogPageIg;

  @NonNull
  public final TextInputLayout blogpageLabel;

  @NonNull
  public final CountryCodePicker ccpBasicprofile;

  @NonNull
  public final TextInputLayout contactError;

  @NonNull
  public final EditText contactNoEdit;

  @NonNull
  public final TextView contactNoText;

  @NonNull
  public final CheckBox contactNumberVisibility;

  @NonNull
  public final TextInputLayout emailAddError;

  @NonNull
  public final TextView emailError;

  @NonNull
  public final CheckBox emailIDVisibility;

  @NonNull
  public final EditText emailText;

  @NonNull
  public final ImageView facebookPageIg;

  @NonNull
  public final EditText facebookpageEdit;

  @NonNull
  public final TextInputLayout facebookpageLabel;

  @NonNull
  public final LinearLayout fbImageLayout;

  @NonNull
  public final EditText instaEdit;

  @NonNull
  public final ImageView instaImg;

  @NonNull
  public final TextInputLayout instaLabel;

  @NonNull
  public final LinearLayout instaLayout;

  @NonNull
  public final EditText linkedInEdit;

  @NonNull
  public final ImageView linkedInImg;

  @NonNull
  public final TextInputLayout linkedInLabel;

  @NonNull
  public final LinearLayout linkedInLayout;

  @NonNull
  public final Spinner numVisibilitySpinner;

  @NonNull
  public final EditText personalizedUrl;

  @NonNull
  public final TextView prefixUrlTv;

  @NonNull
  public final RelativeLayout profileUrlLayout;

  @NonNull
  public final EditText twitterEdit;

  @NonNull
  public final ImageView twitterImg;

  @NonNull
  public final TextInputLayout twitterLabel;

  @NonNull
  public final LinearLayout twitterLayout;

  @NonNull
  public final LinearLayout webImageLayout;

  @NonNull
  public final EditText websiteEdit;

  @NonNull
  public final ImageView websiteIg;

  @NonNull
  public final TextInputLayout websiteLabel;

  @NonNull
  public final ImageView whiteCoatsIg;

  private ActivityAboutmeBinding(@NonNull ScrollView rootView,
      @NonNull LinearLayout blogImageLayout, @NonNull EditText blogPageEdit,
      @NonNull ImageView blogPageIg, @NonNull TextInputLayout blogpageLabel,
      @NonNull CountryCodePicker ccpBasicprofile, @NonNull TextInputLayout contactError,
      @NonNull EditText contactNoEdit, @NonNull TextView contactNoText,
      @NonNull CheckBox contactNumberVisibility, @NonNull TextInputLayout emailAddError,
      @NonNull TextView emailError, @NonNull CheckBox emailIDVisibility,
      @NonNull EditText emailText, @NonNull ImageView facebookPageIg,
      @NonNull EditText facebookpageEdit, @NonNull TextInputLayout facebookpageLabel,
      @NonNull LinearLayout fbImageLayout, @NonNull EditText instaEdit, @NonNull ImageView instaImg,
      @NonNull TextInputLayout instaLabel, @NonNull LinearLayout instaLayout,
      @NonNull EditText linkedInEdit, @NonNull ImageView linkedInImg,
      @NonNull TextInputLayout linkedInLabel, @NonNull LinearLayout linkedInLayout,
      @NonNull Spinner numVisibilitySpinner, @NonNull EditText personalizedUrl,
      @NonNull TextView prefixUrlTv, @NonNull RelativeLayout profileUrlLayout,
      @NonNull EditText twitterEdit, @NonNull ImageView twitterImg,
      @NonNull TextInputLayout twitterLabel, @NonNull LinearLayout twitterLayout,
      @NonNull LinearLayout webImageLayout, @NonNull EditText websiteEdit,
      @NonNull ImageView websiteIg, @NonNull TextInputLayout websiteLabel,
      @NonNull ImageView whiteCoatsIg) {
    this.rootView = rootView;
    this.blogImageLayout = blogImageLayout;
    this.blogPageEdit = blogPageEdit;
    this.blogPageIg = blogPageIg;
    this.blogpageLabel = blogpageLabel;
    this.ccpBasicprofile = ccpBasicprofile;
    this.contactError = contactError;
    this.contactNoEdit = contactNoEdit;
    this.contactNoText = contactNoText;
    this.contactNumberVisibility = contactNumberVisibility;
    this.emailAddError = emailAddError;
    this.emailError = emailError;
    this.emailIDVisibility = emailIDVisibility;
    this.emailText = emailText;
    this.facebookPageIg = facebookPageIg;
    this.facebookpageEdit = facebookpageEdit;
    this.facebookpageLabel = facebookpageLabel;
    this.fbImageLayout = fbImageLayout;
    this.instaEdit = instaEdit;
    this.instaImg = instaImg;
    this.instaLabel = instaLabel;
    this.instaLayout = instaLayout;
    this.linkedInEdit = linkedInEdit;
    this.linkedInImg = linkedInImg;
    this.linkedInLabel = linkedInLabel;
    this.linkedInLayout = linkedInLayout;
    this.numVisibilitySpinner = numVisibilitySpinner;
    this.personalizedUrl = personalizedUrl;
    this.prefixUrlTv = prefixUrlTv;
    this.profileUrlLayout = profileUrlLayout;
    this.twitterEdit = twitterEdit;
    this.twitterImg = twitterImg;
    this.twitterLabel = twitterLabel;
    this.twitterLayout = twitterLayout;
    this.webImageLayout = webImageLayout;
    this.websiteEdit = websiteEdit;
    this.websiteIg = websiteIg;
    this.websiteLabel = websiteLabel;
    this.whiteCoatsIg = whiteCoatsIg;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAboutmeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAboutmeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_aboutme, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAboutmeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.blog_image_layout;
      LinearLayout blogImageLayout = ViewBindings.findChildViewById(rootView, id);
      if (blogImageLayout == null) {
        break missingId;
      }

      id = R.id.blog_page_edit;
      EditText blogPageEdit = ViewBindings.findChildViewById(rootView, id);
      if (blogPageEdit == null) {
        break missingId;
      }

      id = R.id.blog_page_ig;
      ImageView blogPageIg = ViewBindings.findChildViewById(rootView, id);
      if (blogPageIg == null) {
        break missingId;
      }

      id = R.id.blogpage_label;
      TextInputLayout blogpageLabel = ViewBindings.findChildViewById(rootView, id);
      if (blogpageLabel == null) {
        break missingId;
      }

      id = R.id.ccp_basicprofile;
      CountryCodePicker ccpBasicprofile = ViewBindings.findChildViewById(rootView, id);
      if (ccpBasicprofile == null) {
        break missingId;
      }

      id = R.id.contact_error;
      TextInputLayout contactError = ViewBindings.findChildViewById(rootView, id);
      if (contactError == null) {
        break missingId;
      }

      id = R.id.contact_no_edit;
      EditText contactNoEdit = ViewBindings.findChildViewById(rootView, id);
      if (contactNoEdit == null) {
        break missingId;
      }

      id = R.id.contact_no_text;
      TextView contactNoText = ViewBindings.findChildViewById(rootView, id);
      if (contactNoText == null) {
        break missingId;
      }

      id = R.id.contactNumberVisibility;
      CheckBox contactNumberVisibility = ViewBindings.findChildViewById(rootView, id);
      if (contactNumberVisibility == null) {
        break missingId;
      }

      id = R.id.email_add_error;
      TextInputLayout emailAddError = ViewBindings.findChildViewById(rootView, id);
      if (emailAddError == null) {
        break missingId;
      }

      id = R.id.email_error;
      TextView emailError = ViewBindings.findChildViewById(rootView, id);
      if (emailError == null) {
        break missingId;
      }

      id = R.id.emailIDVisibility;
      CheckBox emailIDVisibility = ViewBindings.findChildViewById(rootView, id);
      if (emailIDVisibility == null) {
        break missingId;
      }

      id = R.id.email_text;
      EditText emailText = ViewBindings.findChildViewById(rootView, id);
      if (emailText == null) {
        break missingId;
      }

      id = R.id.facebook_page_ig;
      ImageView facebookPageIg = ViewBindings.findChildViewById(rootView, id);
      if (facebookPageIg == null) {
        break missingId;
      }

      id = R.id.facebookpage_edit;
      EditText facebookpageEdit = ViewBindings.findChildViewById(rootView, id);
      if (facebookpageEdit == null) {
        break missingId;
      }

      id = R.id.facebookpage_label;
      TextInputLayout facebookpageLabel = ViewBindings.findChildViewById(rootView, id);
      if (facebookpageLabel == null) {
        break missingId;
      }

      id = R.id.fb_image_layout;
      LinearLayout fbImageLayout = ViewBindings.findChildViewById(rootView, id);
      if (fbImageLayout == null) {
        break missingId;
      }

      id = R.id.insta_edit;
      EditText instaEdit = ViewBindings.findChildViewById(rootView, id);
      if (instaEdit == null) {
        break missingId;
      }

      id = R.id.insta_img;
      ImageView instaImg = ViewBindings.findChildViewById(rootView, id);
      if (instaImg == null) {
        break missingId;
      }

      id = R.id.insta_label;
      TextInputLayout instaLabel = ViewBindings.findChildViewById(rootView, id);
      if (instaLabel == null) {
        break missingId;
      }

      id = R.id.insta_layout;
      LinearLayout instaLayout = ViewBindings.findChildViewById(rootView, id);
      if (instaLayout == null) {
        break missingId;
      }

      id = R.id.linkedIn_edit;
      EditText linkedInEdit = ViewBindings.findChildViewById(rootView, id);
      if (linkedInEdit == null) {
        break missingId;
      }

      id = R.id.linkedIn_img;
      ImageView linkedInImg = ViewBindings.findChildViewById(rootView, id);
      if (linkedInImg == null) {
        break missingId;
      }

      id = R.id.linkedIn_label;
      TextInputLayout linkedInLabel = ViewBindings.findChildViewById(rootView, id);
      if (linkedInLabel == null) {
        break missingId;
      }

      id = R.id.linkedIn_layout;
      LinearLayout linkedInLayout = ViewBindings.findChildViewById(rootView, id);
      if (linkedInLayout == null) {
        break missingId;
      }

      id = R.id.num_visibility_spinner;
      Spinner numVisibilitySpinner = ViewBindings.findChildViewById(rootView, id);
      if (numVisibilitySpinner == null) {
        break missingId;
      }

      id = R.id.personalized_url;
      EditText personalizedUrl = ViewBindings.findChildViewById(rootView, id);
      if (personalizedUrl == null) {
        break missingId;
      }

      id = R.id.prefix_url_tv;
      TextView prefixUrlTv = ViewBindings.findChildViewById(rootView, id);
      if (prefixUrlTv == null) {
        break missingId;
      }

      id = R.id.profile_url_layout;
      RelativeLayout profileUrlLayout = ViewBindings.findChildViewById(rootView, id);
      if (profileUrlLayout == null) {
        break missingId;
      }

      id = R.id.twitter_edit;
      EditText twitterEdit = ViewBindings.findChildViewById(rootView, id);
      if (twitterEdit == null) {
        break missingId;
      }

      id = R.id.twitter_img;
      ImageView twitterImg = ViewBindings.findChildViewById(rootView, id);
      if (twitterImg == null) {
        break missingId;
      }

      id = R.id.twitter_label;
      TextInputLayout twitterLabel = ViewBindings.findChildViewById(rootView, id);
      if (twitterLabel == null) {
        break missingId;
      }

      id = R.id.twitter_layout;
      LinearLayout twitterLayout = ViewBindings.findChildViewById(rootView, id);
      if (twitterLayout == null) {
        break missingId;
      }

      id = R.id.web_image_layout;
      LinearLayout webImageLayout = ViewBindings.findChildViewById(rootView, id);
      if (webImageLayout == null) {
        break missingId;
      }

      id = R.id.website_edit;
      EditText websiteEdit = ViewBindings.findChildViewById(rootView, id);
      if (websiteEdit == null) {
        break missingId;
      }

      id = R.id.website_ig;
      ImageView websiteIg = ViewBindings.findChildViewById(rootView, id);
      if (websiteIg == null) {
        break missingId;
      }

      id = R.id.website_label;
      TextInputLayout websiteLabel = ViewBindings.findChildViewById(rootView, id);
      if (websiteLabel == null) {
        break missingId;
      }

      id = R.id.whiteCoats_ig;
      ImageView whiteCoatsIg = ViewBindings.findChildViewById(rootView, id);
      if (whiteCoatsIg == null) {
        break missingId;
      }

      return new ActivityAboutmeBinding((ScrollView) rootView, blogImageLayout, blogPageEdit,
          blogPageIg, blogpageLabel, ccpBasicprofile, contactError, contactNoEdit, contactNoText,
          contactNumberVisibility, emailAddError, emailError, emailIDVisibility, emailText,
          facebookPageIg, facebookpageEdit, facebookpageLabel, fbImageLayout, instaEdit, instaImg,
          instaLabel, instaLayout, linkedInEdit, linkedInImg, linkedInLabel, linkedInLayout,
          numVisibilitySpinner, personalizedUrl, prefixUrlTv, profileUrlLayout, twitterEdit,
          twitterImg, twitterLabel, twitterLayout, webImageLayout, websiteEdit, websiteIg,
          websiteLabel, whiteCoatsIg);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}