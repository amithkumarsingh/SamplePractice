// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RecommendationslistLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AVLoadingIndicatorView aviInRecommendationsList;

  @NonNull
  public final TextView interestedArticleTextView;

  @NonNull
  public final RecyclerView recommendationsList;

  private RecommendationslistLayoutBinding(@NonNull LinearLayout rootView,
      @NonNull AVLoadingIndicatorView aviInRecommendationsList,
      @NonNull TextView interestedArticleTextView, @NonNull RecyclerView recommendationsList) {
    this.rootView = rootView;
    this.aviInRecommendationsList = aviInRecommendationsList;
    this.interestedArticleTextView = interestedArticleTextView;
    this.recommendationsList = recommendationsList;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RecommendationslistLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RecommendationslistLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.recommendationslist_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RecommendationslistLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.aviInRecommendationsList;
      AVLoadingIndicatorView aviInRecommendationsList = ViewBindings.findChildViewById(rootView, id);
      if (aviInRecommendationsList == null) {
        break missingId;
      }

      id = R.id.interested_article_textView;
      TextView interestedArticleTextView = ViewBindings.findChildViewById(rootView, id);
      if (interestedArticleTextView == null) {
        break missingId;
      }

      id = R.id.recommendations_list;
      RecyclerView recommendationsList = ViewBindings.findChildViewById(rootView, id);
      if (recommendationsList == null) {
        break missingId;
      }

      return new RecommendationslistLayoutBinding((LinearLayout) rootView, aviInRecommendationsList,
          interestedArticleTextView, recommendationsList);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}