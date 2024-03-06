// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.CustomViewPager;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MainActivityAppBarBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final BottomAppBar bottomAppBar;

  @NonNull
  public final RecyclerView categoryListDashboard;

  @NonNull
  public final View categoryListDummyView;

  @NonNull
  public final LinearLayout categoryListLayout;

  @NonNull
  public final CoordinatorLayout coordinatorLayout;

  @NonNull
  public final AppBarLayout dashBoardAppBarLayout;

  @NonNull
  public final CustomViewPager dashViewpager;

  @NonNull
  public final FloatingActionButton fabButtonDashboard;

  @NonNull
  public final TextView fabPostText;

  @NonNull
  public final BottomNavigationView navigation;

  @NonNull
  public final FloatingActionButton postFab;

  @NonNull
  public final TextView searchMagifier;

  @NonNull
  public final Toolbar toolbarDashboard;

  private MainActivityAppBarBinding(@NonNull CoordinatorLayout rootView,
      @NonNull BottomAppBar bottomAppBar, @NonNull RecyclerView categoryListDashboard,
      @NonNull View categoryListDummyView, @NonNull LinearLayout categoryListLayout,
      @NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout dashBoardAppBarLayout,
      @NonNull CustomViewPager dashViewpager, @NonNull FloatingActionButton fabButtonDashboard,
      @NonNull TextView fabPostText, @NonNull BottomNavigationView navigation,
      @NonNull FloatingActionButton postFab, @NonNull TextView searchMagifier,
      @NonNull Toolbar toolbarDashboard) {
    this.rootView = rootView;
    this.bottomAppBar = bottomAppBar;
    this.categoryListDashboard = categoryListDashboard;
    this.categoryListDummyView = categoryListDummyView;
    this.categoryListLayout = categoryListLayout;
    this.coordinatorLayout = coordinatorLayout;
    this.dashBoardAppBarLayout = dashBoardAppBarLayout;
    this.dashViewpager = dashViewpager;
    this.fabButtonDashboard = fabButtonDashboard;
    this.fabPostText = fabPostText;
    this.navigation = navigation;
    this.postFab = postFab;
    this.searchMagifier = searchMagifier;
    this.toolbarDashboard = toolbarDashboard;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MainActivityAppBarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MainActivityAppBarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.main_activity_app_bar, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MainActivityAppBarBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomAppBar;
      BottomAppBar bottomAppBar = ViewBindings.findChildViewById(rootView, id);
      if (bottomAppBar == null) {
        break missingId;
      }

      id = R.id.category_list_dashboard;
      RecyclerView categoryListDashboard = ViewBindings.findChildViewById(rootView, id);
      if (categoryListDashboard == null) {
        break missingId;
      }

      id = R.id.category_list_dummy_view;
      View categoryListDummyView = ViewBindings.findChildViewById(rootView, id);
      if (categoryListDummyView == null) {
        break missingId;
      }

      id = R.id.category_list_layout;
      LinearLayout categoryListLayout = ViewBindings.findChildViewById(rootView, id);
      if (categoryListLayout == null) {
        break missingId;
      }

      CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;

      id = R.id.dashBoardAppBarLayout;
      AppBarLayout dashBoardAppBarLayout = ViewBindings.findChildViewById(rootView, id);
      if (dashBoardAppBarLayout == null) {
        break missingId;
      }

      id = R.id.dash_viewpager;
      CustomViewPager dashViewpager = ViewBindings.findChildViewById(rootView, id);
      if (dashViewpager == null) {
        break missingId;
      }

      id = R.id.fab_button_dashboard;
      FloatingActionButton fabButtonDashboard = ViewBindings.findChildViewById(rootView, id);
      if (fabButtonDashboard == null) {
        break missingId;
      }

      id = R.id.fab_post_text;
      TextView fabPostText = ViewBindings.findChildViewById(rootView, id);
      if (fabPostText == null) {
        break missingId;
      }

      id = R.id.navigation;
      BottomNavigationView navigation = ViewBindings.findChildViewById(rootView, id);
      if (navigation == null) {
        break missingId;
      }

      id = R.id.post_fab;
      FloatingActionButton postFab = ViewBindings.findChildViewById(rootView, id);
      if (postFab == null) {
        break missingId;
      }

      id = R.id.search_magifier;
      TextView searchMagifier = ViewBindings.findChildViewById(rootView, id);
      if (searchMagifier == null) {
        break missingId;
      }

      id = R.id.toolbar_dashboard;
      Toolbar toolbarDashboard = ViewBindings.findChildViewById(rootView, id);
      if (toolbarDashboard == null) {
        break missingId;
      }

      return new MainActivityAppBarBinding((CoordinatorLayout) rootView, bottomAppBar,
          categoryListDashboard, categoryListDummyView, categoryListLayout, coordinatorLayout,
          dashBoardAppBarLayout, dashViewpager, fabButtonDashboard, fabPostText, navigation,
          postFab, searchMagifier, toolbarDashboard);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}