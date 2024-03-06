package com.vam.whitecoats.ui.activities;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by pardhasaradhid on 8/24/2017.
 */
public class BasicProfileActivityTest {
    @Rule
    public ActivityTestRule<BasicProfileActivity> mBasicProfileActivityTestRule = new ActivityTestRule<BasicProfileActivity>(BasicProfileActivity.class);




    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }



}