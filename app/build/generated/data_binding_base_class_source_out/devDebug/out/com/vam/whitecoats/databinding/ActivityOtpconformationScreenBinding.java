// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public final class ActivityOtpconformationScreenBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final View aboutMeBorder1;

  @NonNull
  public final LinearLayout activityOtpconformationScreen;

  @NonNull
  public final TextView contactSupportAction;

  @NonNull
  public final TextView countdownTimer;

  @NonNull
  public final TextView emailIdSentto;

  @NonNull
  public final Button gotologinAction;

  @NonNull
  public final LinearLayout haventLayout;

  @NonNull
  public final TextView resendOtpAction;

  @NonNull
  public final TextView titleTextotp;

  private ActivityOtpconformationScreenBinding(@NonNull LinearLayout rootView,
      @NonNull View aboutMeBorder1, @NonNull LinearLayout activityOtpconformationScreen,
      @NonNull TextView contactSupportAction, @NonNull TextView countdownTimer,
      @NonNull TextView emailIdSentto, @NonNull Button gotologinAction,
      @NonNull LinearLayout haventLayout, @NonNull TextView resendOtpAction,
      @NonNull TextView titleTextotp) {
    this.rootView = rootView;
    this.aboutMeBorder1 = aboutMeBorder1;
    this.activityOtpconformationScreen = activityOtpconformationScreen;
    this.contactSupportAction = contactSupportAction;
    this.countdownTimer = countdownTimer;
    this.emailIdSentto = emailIdSentto;
    this.gotologinAction = gotologinAction;
    this.haventLayout = haventLayout;
    this.resendOtpAction = resendOtpAction;
    this.titleTextotp = titleTextotp;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOtpconformationScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOtpconformationScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_otpconformation_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOtpconformationScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.about_me_border1;
      View aboutMeBorder1 = ViewBindings.findChildViewById(rootView, id);
      if (aboutMeBorder1 == null) {
        break missingId;
      }

      LinearLayout activityOtpconformationScreen = (LinearLayout) rootView;

      id = R.id.contact_support_action;
      TextView contactSupportAction = ViewBindings.findChildViewById(rootView, id);
      if (contactSupportAction == null) {
        break missingId;
      }

      id = R.id.countdown_timer;
      TextView countdownTimer = ViewBindings.findChildViewById(rootView, id);
      if (countdownTimer == null) {
        break missingId;
      }

      id = R.id.email_id_sentto;
      TextView emailIdSentto = ViewBindings.findChildViewById(rootView, id);
      if (emailIdSentto == null) {
        break missingId;
      }

      id = R.id.gotologin_action;
      Button gotologinAction = ViewBindings.findChildViewById(rootView, id);
      if (gotologinAction == null) {
        break missingId;
      }

      id = R.id.havent_layout;
      LinearLayout haventLayout = ViewBindings.findChildViewById(rootView, id);
      if (haventLayout == null) {
        break missingId;
      }

      id = R.id.resend_otp_action;
      TextView resendOtpAction = ViewBindings.findChildViewById(rootView, id);
      if (resendOtpAction == null) {
        break missingId;
      }

      id = R.id.title_textotp;
      TextView titleTextotp = ViewBindings.findChildViewById(rootView, id);
      if (titleTextotp == null) {
        break missingId;
      }

      return new ActivityOtpconformationScreenBinding((LinearLayout) rootView, aboutMeBorder1,
          activityOtpconformationScreen, contactSupportAction, countdownTimer, emailIdSentto,
          gotologinAction, haventLayout, resendOtpAction, titleTextotp);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
