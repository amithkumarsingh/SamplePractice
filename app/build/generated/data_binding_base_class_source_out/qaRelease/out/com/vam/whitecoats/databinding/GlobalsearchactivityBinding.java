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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class GlobalsearchactivityBinding implements ViewBinding {
  @NonNull
  private final SwipeRefreshLayout rootView;

  @NonNull
  public final NestedScrollView activityGlobalSearchResults;

  @NonNull
  public final AVLoadingIndicatorView aviInBookmarksList;

  @NonNull
  public final Button feedsAll;

  @NonNull
  public final LinearLayout layoutBtns;

  @NonNull
  public final LinearLayout layoutFeeds;

  @NonNull
  public final LinearLayout layoutUsers;

  @NonNull
  public final TextView noGlobalResultsTxt;

  @NonNull
  public final RecyclerView recyclerviewFeeds;

  @NonNull
  public final RecyclerView recyclerviewSearchType;

  @NonNull
  public final RecyclerView recyclerviewUsers;

  @NonNull
  public final SwipeRefreshLayout swipeContainerGlobal;

  @NonNull
  public final Button userViewAll;

  private GlobalsearchactivityBinding(@NonNull SwipeRefreshLayout rootView,
      @NonNull NestedScrollView activityGlobalSearchResults,
      @NonNull AVLoadingIndicatorView aviInBookmarksList, @NonNull Button feedsAll,
      @NonNull LinearLayout layoutBtns, @NonNull LinearLayout layoutFeeds,
      @NonNull LinearLayout layoutUsers, @NonNull TextView noGlobalResultsTxt,
      @NonNull RecyclerView recyclerviewFeeds, @NonNull RecyclerView recyclerviewSearchType,
      @NonNull RecyclerView recyclerviewUsers, @NonNull SwipeRefreshLayout swipeContainerGlobal,
      @NonNull Button userViewAll) {
    this.rootView = rootView;
    this.activityGlobalSearchResults = activityGlobalSearchResults;
    this.aviInBookmarksList = aviInBookmarksList;
    this.feedsAll = feedsAll;
    this.layoutBtns = layoutBtns;
    this.layoutFeeds = layoutFeeds;
    this.layoutUsers = layoutUsers;
    this.noGlobalResultsTxt = noGlobalResultsTxt;
    this.recyclerviewFeeds = recyclerviewFeeds;
    this.recyclerviewSearchType = recyclerviewSearchType;
    this.recyclerviewUsers = recyclerviewUsers;
    this.swipeContainerGlobal = swipeContainerGlobal;
    this.userViewAll = userViewAll;
  }

  @Override
  @NonNull
  public SwipeRefreshLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static GlobalsearchactivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static GlobalsearchactivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.globalsearchactivity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static GlobalsearchactivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.activity_global_search_results;
      NestedScrollView activityGlobalSearchResults = ViewBindings.findChildViewById(rootView, id);
      if (activityGlobalSearchResults == null) {
        break missingId;
      }

      id = R.id.aviInBookmarksList;
      AVLoadingIndicatorView aviInBookmarksList = ViewBindings.findChildViewById(rootView, id);
      if (aviInBookmarksList == null) {
        break missingId;
      }

      id = R.id.feeds_all;
      Button feedsAll = ViewBindings.findChildViewById(rootView, id);
      if (feedsAll == null) {
        break missingId;
      }

      id = R.id.layout_btns;
      LinearLayout layoutBtns = ViewBindings.findChildViewById(rootView, id);
      if (layoutBtns == null) {
        break missingId;
      }

      id = R.id.layout_feeds;
      LinearLayout layoutFeeds = ViewBindings.findChildViewById(rootView, id);
      if (layoutFeeds == null) {
        break missingId;
      }

      id = R.id.layout_users;
      LinearLayout layoutUsers = ViewBindings.findChildViewById(rootView, id);
      if (layoutUsers == null) {
        break missingId;
      }

      id = R.id.noGlobalResults_txt;
      TextView noGlobalResultsTxt = ViewBindings.findChildViewById(rootView, id);
      if (noGlobalResultsTxt == null) {
        break missingId;
      }

      id = R.id.recyclerview_feeds;
      RecyclerView recyclerviewFeeds = ViewBindings.findChildViewById(rootView, id);
      if (recyclerviewFeeds == null) {
        break missingId;
      }

      id = R.id.recyclerview_search_type;
      RecyclerView recyclerviewSearchType = ViewBindings.findChildViewById(rootView, id);
      if (recyclerviewSearchType == null) {
        break missingId;
      }

      id = R.id.recyclerview_users;
      RecyclerView recyclerviewUsers = ViewBindings.findChildViewById(rootView, id);
      if (recyclerviewUsers == null) {
        break missingId;
      }

      SwipeRefreshLayout swipeContainerGlobal = (SwipeRefreshLayout) rootView;

      id = R.id.user_view_all;
      Button userViewAll = ViewBindings.findChildViewById(rootView, id);
      if (userViewAll == null) {
        break missingId;
      }

      return new GlobalsearchactivityBinding((SwipeRefreshLayout) rootView,
          activityGlobalSearchResults, aviInBookmarksList, feedsAll, layoutBtns, layoutFeeds,
          layoutUsers, noGlobalResultsTxt, recyclerviewFeeds, recyclerviewSearchType,
          recyclerviewUsers, swipeContainerGlobal, userViewAll);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
