package com.vam.whitecoats.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import kotlin.TypeCastException;

public class ScrollViewWithBottomAppBar extends AppBarLayout.ScrollingViewBehavior {

    private int bottomMargin = 0;
    public ScrollViewWithBottomAppBar(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency) || dependency instanceof BottomAppBar;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        boolean result = super.onDependentViewChanged(parent, child, dependency);
        if (dependency instanceof BottomAppBar && ((BottomAppBar)dependency).getHeight() != this.bottomMargin) {
            this.bottomMargin = ((BottomAppBar)dependency).getHeight();
            ViewGroup.LayoutParams var10000 = child.getLayoutParams();
            if (var10000 == null) {
                throw new TypeCastException("null cannot be cast to non-null type androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams");
            } else {
                androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams layout = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams)var10000;
                layout.bottomMargin = this.bottomMargin;
                child.requestLayout();
                return true;
            }
        } else {
            return result;
        }
    }
}
