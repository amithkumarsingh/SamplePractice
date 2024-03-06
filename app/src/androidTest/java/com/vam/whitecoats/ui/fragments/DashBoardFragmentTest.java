package com.vam.whitecoats.ui.fragments;



import android.content.SharedPreferences;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.viewpager.widget.ViewPager;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.utils.CustomViewPager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;








@RunWith(AndroidJUnit4.class)
public class DashBoardFragmentTest {

    @Rule
    public ActivityTestRule<DashboardActivity> mDashboardActivityActivityTestRule=new ActivityTestRule<DashboardActivity>(DashboardActivity.class);

    private DashboardActivity mDashboardActivity=null;
    private Realm realm;
    private RealmManager realmManager;
    private MySharedPref sharedPrefs;


    @Before
    public void setUp() throws Exception {

        mDashboardActivity=mDashboardActivityActivityTestRule.getActivity();
        realmManager = new RealmManager(mDashboardActivity);
        sharedPrefs = new MySharedPref(mDashboardActivity);
        sharedPrefs.savePref(MySharedPref.PREF_COACHMARK_INCREMENTER,1);

    }


    @Test
    public void testLaunch() {

        CustomViewPager customViewPager=(CustomViewPager) mDashboardActivity.findViewById(R.id.dash_viewpager);
        assertNotNull(customViewPager);

        AssociationDashboardFragment dashboardUpdatesFragment=new AssociationDashboardFragment();
        mDashboardActivity.getSupportFragmentManager().beginTransaction().add(R.id.dash_viewpager, dashboardUpdatesFragment).commit();

        getInstrumentation().waitForIdleSync();
        View view = dashboardUpdatesFragment.getView().findViewById(R.id.dashboardFragment);
        assertNotNull(view);
    }


    @Test
    public void testSampleRecyclerVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.channel_recycler_view))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(mDashboardActivityActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCaseForRecyclerScroll() {
        int position=realmManager.getFeedDBPosition(realmManager.getRealmFeedInfoObj(-4,0));
           if(position!=-1) {
               // Get total item of RecyclerView
               RecyclerView recyclerView = mDashboardActivityActivityTestRule.getActivity().findViewById(R.id.channel_recycler_view);

               // Scroll to end of page with position
               Espresso.onView(ViewMatchers.withId(R.id.channel_recycler_view))
                       .inRoot(RootMatchers.withDecorView(
                               Matchers.is(mDashboardActivityActivityTestRule.getActivity().getWindow().getDecorView())))
                       .perform(RecyclerViewActions.scrollToPosition(position));
           }
    }
    @Test
    public void testCaseForRecyclerItemView() {
        int position=realmManager.getFeedDBPosition(realmManager.getRealmFeedInfoObj(-4,0));

        if(position!=-1) {

            Espresso.onView(ViewMatchers.withId(R.id.channel_recycler_view))
                    .inRoot(RootMatchers.withDecorView(
                            Matchers.is(mDashboardActivityActivityTestRule.getActivity().getWindow().getDecorView())))
                    .perform(RecyclerViewActions.scrollToPosition(position));

            Espresso.onView(ViewMatchers.withId(R.id.channel_recycler_view))
                    .inRoot(RootMatchers.withDecorView(
                            Matchers.is(mDashboardActivityActivityTestRule.getActivity().getWindow().getDecorView())))
                    .check(matches(withViewAtPosition(position, Matchers.allOf(
                            ViewMatchers.withId(R.id.horizontal_list), isDisplayed()))));
        }
    }

    public Matcher<View> withViewAtPosition(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                final RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }


    @After
    public void tearDown() throws Exception {
        mDashboardActivity=null;
        mDashboardActivityActivityTestRule=null;
        realmManager=null;
        sharedPrefs=null;
    }
}
