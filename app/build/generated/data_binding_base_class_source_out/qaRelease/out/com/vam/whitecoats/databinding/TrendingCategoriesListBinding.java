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
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class TrendingCategoriesListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RecyclerView trendingCategoriesList;

  @NonNull
  public final TextView trendingCategoriesText;

  private TrendingCategoriesListBinding(@NonNull LinearLayout rootView,
      @NonNull RecyclerView trendingCategoriesList, @NonNull TextView trendingCategoriesText) {
    this.rootView = rootView;
    this.trendingCategoriesList = trendingCategoriesList;
    this.trendingCategoriesText = trendingCategoriesText;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static TrendingCategoriesListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TrendingCategoriesListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.trending_categories_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TrendingCategoriesListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.trending_categories_list;
      RecyclerView trendingCategoriesList = ViewBindings.findChildViewById(rootView, id);
      if (trendingCategoriesList == null) {
        break missingId;
      }

      id = R.id.trending_categories_text;
      TextView trendingCategoriesText = ViewBindings.findChildViewById(rootView, id);
      if (trendingCategoriesText == null) {
        break missingId;
      }

      return new TrendingCategoriesListBinding((LinearLayout) rootView, trendingCategoriesList,
          trendingCategoriesText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}