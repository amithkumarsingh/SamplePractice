package com.vam.whitecoats.ui.activities;

import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vam.whitecoats.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.model.Atoms.getCurrentUrl;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ContentFullViewTest {

    @Rule
    public ActivityTestRule<ContentFullView> mActivityTestRule = new ActivityTestRule<ContentFullView>(ContentFullView.class){
        @Override
        protected void afterActivityLaunched() {
            onWebView(withId(R.id.feed_description_web)).forceJavascriptEnabled();
        }
    };


    /*@Rule
    public IntentsTestRule<ContentFullView> contentFullviewActivity = new IntentsTestRule<ContentFullView>(
            ContentFullView.class);*/
    private ContentFullView mActivity;
    //Instrumentation.ActivityMonitor monitor= getInstrumentation().addMonitor(WebViewActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mActivity=null;
    }

    @Test
    public void checkWebViewNull(){
        assertNotNull(mActivity.findViewById(R.id.feed_description_web));
    }

    @Test
    public void webViewTest(){
        onWebView()
                .withElement(findElement(Locator.TAG_NAME, "a"))
                .check(webMatches(getCurrentUrl(), containsString("https://whitecoats.com/external/uptodate")))
                .perform(webClick());

        //onView(withId(R.id.webView_Url)).check(matches(isDisplayed()));
        /*Intents.init();
        intended(hasComponent(WebViewActivity.class.getName()));*/
        /*Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, WebViewActivity.class);
        intent.putExtra("EXTERNAL_LINK", "https://whitecoats.com/external/uptodate");

        mActivityTestRule.launchActivity(intent);*/
        /*Activity secondAcitivty = getInstrumentation().waitForMonitorWithTimeout(monitor, 2000);
        assertNotNull(secondAcitivty);
        assertNotNull(secondAcitivty.findViewById(R.id.webView_Url));*/
    }
    @Test
    public void onCreate() throws Exception {

    }
}
