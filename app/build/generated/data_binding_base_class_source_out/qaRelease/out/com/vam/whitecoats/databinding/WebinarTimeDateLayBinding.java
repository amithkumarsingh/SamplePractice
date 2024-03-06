// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public final class WebinarTimeDateLayBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView alreadyRegisteredIcon;

  @NonNull
  public final TextView alreadyRegisteredText;

  @NonNull
  public final TextView dateWebinar;

  @NonNull
  public final TextView endedText;

  @NonNull
  public final Button joinNow;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final Button registerNow;

  @NonNull
  public final RelativeLayout registeredJoinLay;

  @NonNull
  public final ProgressBar simplePB;

  @NonNull
  public final TextView tvOrganizer;

  @NonNull
  public final TextView tvWebinarDateTimeLabel;

  @NonNull
  public final Button viewRecordingBtn;

  @NonNull
  public final TextView webinarTime;

  @NonNull
  public final LinearLayout webinarTimeDateInnerLay;

  private WebinarTimeDateLayBinding(@NonNull LinearLayout rootView,
      @NonNull ImageView alreadyRegisteredIcon, @NonNull TextView alreadyRegisteredText,
      @NonNull TextView dateWebinar, @NonNull TextView endedText, @NonNull Button joinNow,
      @NonNull ProgressBar progressBar, @NonNull Button registerNow,
      @NonNull RelativeLayout registeredJoinLay, @NonNull ProgressBar simplePB,
      @NonNull TextView tvOrganizer, @NonNull TextView tvWebinarDateTimeLabel,
      @NonNull Button viewRecordingBtn, @NonNull TextView webinarTime,
      @NonNull LinearLayout webinarTimeDateInnerLay) {
    this.rootView = rootView;
    this.alreadyRegisteredIcon = alreadyRegisteredIcon;
    this.alreadyRegisteredText = alreadyRegisteredText;
    this.dateWebinar = dateWebinar;
    this.endedText = endedText;
    this.joinNow = joinNow;
    this.progressBar = progressBar;
    this.registerNow = registerNow;
    this.registeredJoinLay = registeredJoinLay;
    this.simplePB = simplePB;
    this.tvOrganizer = tvOrganizer;
    this.tvWebinarDateTimeLabel = tvWebinarDateTimeLabel;
    this.viewRecordingBtn = viewRecordingBtn;
    this.webinarTime = webinarTime;
    this.webinarTimeDateInnerLay = webinarTimeDateInnerLay;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static WebinarTimeDateLayBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static WebinarTimeDateLayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.webinar_time_date_lay, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static WebinarTimeDateLayBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.already_registered_icon;
      ImageView alreadyRegisteredIcon = ViewBindings.findChildViewById(rootView, id);
      if (alreadyRegisteredIcon == null) {
        break missingId;
      }

      id = R.id.already_registered_text;
      TextView alreadyRegisteredText = ViewBindings.findChildViewById(rootView, id);
      if (alreadyRegisteredText == null) {
        break missingId;
      }

      id = R.id.date_webinar;
      TextView dateWebinar = ViewBindings.findChildViewById(rootView, id);
      if (dateWebinar == null) {
        break missingId;
      }

      id = R.id.ended_text;
      TextView endedText = ViewBindings.findChildViewById(rootView, id);
      if (endedText == null) {
        break missingId;
      }

      id = R.id.join_now;
      Button joinNow = ViewBindings.findChildViewById(rootView, id);
      if (joinNow == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.register_now;
      Button registerNow = ViewBindings.findChildViewById(rootView, id);
      if (registerNow == null) {
        break missingId;
      }

      id = R.id.registered_join_lay;
      RelativeLayout registeredJoinLay = ViewBindings.findChildViewById(rootView, id);
      if (registeredJoinLay == null) {
        break missingId;
      }

      id = R.id.simplePB;
      ProgressBar simplePB = ViewBindings.findChildViewById(rootView, id);
      if (simplePB == null) {
        break missingId;
      }

      id = R.id.tv_organizer;
      TextView tvOrganizer = ViewBindings.findChildViewById(rootView, id);
      if (tvOrganizer == null) {
        break missingId;
      }

      id = R.id.tv_webinar_date_time_label;
      TextView tvWebinarDateTimeLabel = ViewBindings.findChildViewById(rootView, id);
      if (tvWebinarDateTimeLabel == null) {
        break missingId;
      }

      id = R.id.view_recording_btn;
      Button viewRecordingBtn = ViewBindings.findChildViewById(rootView, id);
      if (viewRecordingBtn == null) {
        break missingId;
      }

      id = R.id.webinar_time;
      TextView webinarTime = ViewBindings.findChildViewById(rootView, id);
      if (webinarTime == null) {
        break missingId;
      }

      id = R.id.webinar_time_date_inner_lay;
      LinearLayout webinarTimeDateInnerLay = ViewBindings.findChildViewById(rootView, id);
      if (webinarTimeDateInnerLay == null) {
        break missingId;
      }

      return new WebinarTimeDateLayBinding((LinearLayout) rootView, alreadyRegisteredIcon,
          alreadyRegisteredText, dateWebinar, endedText, joinNow, progressBar, registerNow,
          registeredJoinLay, simplePB, tvOrganizer, tvWebinarDateTimeLabel, viewRecordingBtn,
          webinarTime, webinarTimeDateInnerLay);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}