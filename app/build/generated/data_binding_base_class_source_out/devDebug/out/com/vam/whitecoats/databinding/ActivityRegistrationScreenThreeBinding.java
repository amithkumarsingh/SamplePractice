// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegistrationScreenThreeBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final RelativeLayout activityRegistrationScreenThree;

  @NonNull
  public final Button attachButton;

  @NonNull
  public final ImageView deleteImg;

  @NonNull
  public final TextView learnmoreText;

  @NonNull
  public final LinearLayout llParentPdfView;

  @NonNull
  public final TextView mciDetailedText;

  @NonNull
  public final ImageView mciImage;

  @NonNull
  public final FrameLayout mciImageLay;

  @NonNull
  public final RelativeLayout overlayLayout;

  @NonNull
  public final TextView pdfFileName;

  @NonNull
  public final LinearLayout submitBtnLay;

  @NonNull
  public final Button submitButton;

  @NonNull
  public final TextView verifyLaterText;

  private ActivityRegistrationScreenThreeBinding(@NonNull ScrollView rootView,
      @NonNull RelativeLayout activityRegistrationScreenThree, @NonNull Button attachButton,
      @NonNull ImageView deleteImg, @NonNull TextView learnmoreText,
      @NonNull LinearLayout llParentPdfView, @NonNull TextView mciDetailedText,
      @NonNull ImageView mciImage, @NonNull FrameLayout mciImageLay,
      @NonNull RelativeLayout overlayLayout, @NonNull TextView pdfFileName,
      @NonNull LinearLayout submitBtnLay, @NonNull Button submitButton,
      @NonNull TextView verifyLaterText) {
    this.rootView = rootView;
    this.activityRegistrationScreenThree = activityRegistrationScreenThree;
    this.attachButton = attachButton;
    this.deleteImg = deleteImg;
    this.learnmoreText = learnmoreText;
    this.llParentPdfView = llParentPdfView;
    this.mciDetailedText = mciDetailedText;
    this.mciImage = mciImage;
    this.mciImageLay = mciImageLay;
    this.overlayLayout = overlayLayout;
    this.pdfFileName = pdfFileName;
    this.submitBtnLay = submitBtnLay;
    this.submitButton = submitButton;
    this.verifyLaterText = verifyLaterText;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegistrationScreenThreeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegistrationScreenThreeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_registration_screen_three, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegistrationScreenThreeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.activity_registration_screen_three;
      RelativeLayout activityRegistrationScreenThree = ViewBindings.findChildViewById(rootView, id);
      if (activityRegistrationScreenThree == null) {
        break missingId;
      }

      id = R.id.attach_button;
      Button attachButton = ViewBindings.findChildViewById(rootView, id);
      if (attachButton == null) {
        break missingId;
      }

      id = R.id.delete_img;
      ImageView deleteImg = ViewBindings.findChildViewById(rootView, id);
      if (deleteImg == null) {
        break missingId;
      }

      id = R.id.learnmore_text;
      TextView learnmoreText = ViewBindings.findChildViewById(rootView, id);
      if (learnmoreText == null) {
        break missingId;
      }

      id = R.id.ll_parent_Pdf_view;
      LinearLayout llParentPdfView = ViewBindings.findChildViewById(rootView, id);
      if (llParentPdfView == null) {
        break missingId;
      }

      id = R.id.mci_detailedText;
      TextView mciDetailedText = ViewBindings.findChildViewById(rootView, id);
      if (mciDetailedText == null) {
        break missingId;
      }

      id = R.id.mci_image;
      ImageView mciImage = ViewBindings.findChildViewById(rootView, id);
      if (mciImage == null) {
        break missingId;
      }

      id = R.id.mci_image_lay;
      FrameLayout mciImageLay = ViewBindings.findChildViewById(rootView, id);
      if (mciImageLay == null) {
        break missingId;
      }

      id = R.id.overlayLayout;
      RelativeLayout overlayLayout = ViewBindings.findChildViewById(rootView, id);
      if (overlayLayout == null) {
        break missingId;
      }

      id = R.id.pdfFileName;
      TextView pdfFileName = ViewBindings.findChildViewById(rootView, id);
      if (pdfFileName == null) {
        break missingId;
      }

      id = R.id.submitBtn_lay;
      LinearLayout submitBtnLay = ViewBindings.findChildViewById(rootView, id);
      if (submitBtnLay == null) {
        break missingId;
      }

      id = R.id.submit_button;
      Button submitButton = ViewBindings.findChildViewById(rootView, id);
      if (submitButton == null) {
        break missingId;
      }

      id = R.id.verify_laterText;
      TextView verifyLaterText = ViewBindings.findChildViewById(rootView, id);
      if (verifyLaterText == null) {
        break missingId;
      }

      return new ActivityRegistrationScreenThreeBinding((ScrollView) rootView,
          activityRegistrationScreenThree, attachButton, deleteImg, learnmoreText, llParentPdfView,
          mciDetailedText, mciImage, mciImageLay, overlayLayout, pdfFileName, submitBtnLay,
          submitButton, verifyLaterText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}