package com.vam.whitecoats.ui.activities;

import androidx.test.rule.ActivityTestRule;

import com.vam.whitecoats.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

/**
 * Created by pardhasaradhid on 5/30/2017.
 */
public class MeActivityTest {
    @Rule
    public ActivityTestRule<ProfileViewActivity> mActivityTestRule = new ActivityTestRule<>(ProfileViewActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onButtonClick(){
        onView(withId(R.id.dash_layout_coserooms)).perform(click());

    }
    @Test
    public void onCreate() throws Exception {

    }

}