// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public final class FragmentAwardsBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button addAwardButton;

  @NonNull
  public final EditText awardsTitle;

  @NonNull
  public final TextView awardsTitleError;

  @NonNull
  public final EditText presentedAt;

  @NonNull
  public final EditText yearAwards;

  private FragmentAwardsBinding(@NonNull ScrollView rootView, @NonNull Button addAwardButton,
      @NonNull EditText awardsTitle, @NonNull TextView awardsTitleError,
      @NonNull EditText presentedAt, @NonNull EditText yearAwards) {
    this.rootView = rootView;
    this.addAwardButton = addAwardButton;
    this.awardsTitle = awardsTitle;
    this.awardsTitleError = awardsTitleError;
    this.presentedAt = presentedAt;
    this.yearAwards = yearAwards;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAwardsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAwardsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_awards, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAwardsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_award_button;
      Button addAwardButton = ViewBindings.findChildViewById(rootView, id);
      if (addAwardButton == null) {
        break missingId;
      }

      id = R.id.awards_title;
      EditText awardsTitle = ViewBindings.findChildViewById(rootView, id);
      if (awardsTitle == null) {
        break missingId;
      }

      id = R.id.awards_title_error;
      TextView awardsTitleError = ViewBindings.findChildViewById(rootView, id);
      if (awardsTitleError == null) {
        break missingId;
      }

      id = R.id.presented_at;
      EditText presentedAt = ViewBindings.findChildViewById(rootView, id);
      if (presentedAt == null) {
        break missingId;
      }

      id = R.id.year_awards;
      EditText yearAwards = ViewBindings.findChildViewById(rootView, id);
      if (yearAwards == null) {
        break missingId;
      }

      return new FragmentAwardsBinding((ScrollView) rootView, addAwardButton, awardsTitle,
          awardsTitleError, presentedAt, yearAwards);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}