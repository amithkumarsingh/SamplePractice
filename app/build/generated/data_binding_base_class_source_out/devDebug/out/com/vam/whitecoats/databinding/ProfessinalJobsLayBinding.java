// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ProfessinalJobsLayBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView btnClear;

  @NonNull
  public final TextView btnLocation;

  @NonNull
  public final TextView btnSpeciality;

  @NonNull
  public final RecyclerView categoryFilterList;

  @NonNull
  public final RecyclerView feedsList;

  @NonNull
  public final LinearLayout jobsFiltersList;

  @NonNull
  public final ProgressBar loadingProgressBookMarks;

  @NonNull
  public final TextView noBookMarks;

  @NonNull
  public final RelativeLayout parentLayout;

  @NonNull
  public final SwipeRefreshLayout swipeContainerContentFeeds;

  @NonNull
  public final HorizontalScrollView userFiltersList;

  private ProfessinalJobsLayBinding(@NonNull RelativeLayout rootView, @NonNull TextView btnClear,
      @NonNull TextView btnLocation, @NonNull TextView btnSpeciality,
      @NonNull RecyclerView categoryFilterList, @NonNull RecyclerView feedsList,
      @NonNull LinearLayout jobsFiltersList, @NonNull ProgressBar loadingProgressBookMarks,
      @NonNull TextView noBookMarks, @NonNull RelativeLayout parentLayout,
      @NonNull SwipeRefreshLayout swipeContainerContentFeeds,
      @NonNull HorizontalScrollView userFiltersList) {
    this.rootView = rootView;
    this.btnClear = btnClear;
    this.btnLocation = btnLocation;
    this.btnSpeciality = btnSpeciality;
    this.categoryFilterList = categoryFilterList;
    this.feedsList = feedsList;
    this.jobsFiltersList = jobsFiltersList;
    this.loadingProgressBookMarks = loadingProgressBookMarks;
    this.noBookMarks = noBookMarks;
    this.parentLayout = parentLayout;
    this.swipeContainerContentFeeds = swipeContainerContentFeeds;
    this.userFiltersList = userFiltersList;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ProfessinalJobsLayBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProfessinalJobsLayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.professinal_jobs_lay, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProfessinalJobsLayBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_clear;
      TextView btnClear = ViewBindings.findChildViewById(rootView, id);
      if (btnClear == null) {
        break missingId;
      }

      id = R.id.btn_Location;
      TextView btnLocation = ViewBindings.findChildViewById(rootView, id);
      if (btnLocation == null) {
        break missingId;
      }

      id = R.id.btn_speciality;
      TextView btnSpeciality = ViewBindings.findChildViewById(rootView, id);
      if (btnSpeciality == null) {
        break missingId;
      }

      id = R.id.category_filter_list;
      RecyclerView categoryFilterList = ViewBindings.findChildViewById(rootView, id);
      if (categoryFilterList == null) {
        break missingId;
      }

      id = R.id.feeds_list;
      RecyclerView feedsList = ViewBindings.findChildViewById(rootView, id);
      if (feedsList == null) {
        break missingId;
      }

      id = R.id.jobs_filters_list;
      LinearLayout jobsFiltersList = ViewBindings.findChildViewById(rootView, id);
      if (jobsFiltersList == null) {
        break missingId;
      }

      id = R.id.loading_progress_bookMarks;
      ProgressBar loadingProgressBookMarks = ViewBindings.findChildViewById(rootView, id);
      if (loadingProgressBookMarks == null) {
        break missingId;
      }

      id = R.id.no_bookMarks;
      TextView noBookMarks = ViewBindings.findChildViewById(rootView, id);
      if (noBookMarks == null) {
        break missingId;
      }

      RelativeLayout parentLayout = (RelativeLayout) rootView;

      id = R.id.swipeContainer_content_feeds;
      SwipeRefreshLayout swipeContainerContentFeeds = ViewBindings.findChildViewById(rootView, id);
      if (swipeContainerContentFeeds == null) {
        break missingId;
      }

      id = R.id.user_filters_list;
      HorizontalScrollView userFiltersList = ViewBindings.findChildViewById(rootView, id);
      if (userFiltersList == null) {
        break missingId;
      }

      return new ProfessinalJobsLayBinding((RelativeLayout) rootView, btnClear, btnLocation,
          btnSpeciality, categoryFilterList, feedsList, jobsFiltersList, loadingProgressBookMarks,
          noBookMarks, parentLayout, swipeContainerContentFeeds, userFiltersList);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}