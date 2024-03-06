package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;

import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.IntroSlides_PagerAdapter;
import com.vam.whitecoats.ui.interfaces.OnHomePressedListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HomeWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class IntroSlidesActivity extends Activity {
    ViewPager mViewPager;
    IntroSlides_PagerAdapter mCustomPagerAdapter;
    LinearLayout pagination_layout;
    FrameLayout frameLayout;
    int count = 0;
    static TextView page_text[];
    Button next, skip;
    MySharedPref mySharedPref;
    private boolean introslides;
    boolean isRegistrationComplete, nextButtonClick;

    private Realm realm;
    private RealmManager realmManager;
    private HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introslides);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        int[] mResources = {R.drawable.slide_01, R.drawable.slide_02, R.drawable.slide_03};
        final int[] bg_colors = {R.color.slide1, R.color.slide2, R.color.slide3};


        pagination_layout = (LinearLayout) findViewById(R.id.image_count);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        next = (Button) findViewById(R.id.next);
        skip = (Button) findViewById(R.id.skip);

        mySharedPref = new MySharedPref(this);
        introslides = mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_INTROSLIDES, false);
        isRegistrationComplete = mySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_REGISTRATION_FLAG, false);
        mCustomPagerAdapter = new IntroSlides_PagerAdapter(this, mResources, bg_colors, next, skip);
        mViewPager = (ViewPager) findViewById(R.id.gallery);
        mViewPager.setAdapter(mCustomPagerAdapter);

        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("X-DEVICE-ID", android_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //JSONObject jsonObject = new JSONObject();
                if (mViewPager.getCurrentItem() == 0) {
                 /* try {
                      jsonObject.put("X-DEVICE-ID", android_id);
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }*/
                    AppUtil.logUserWithEventName(0, "Intro1Skip", jsonObject, IntroSlidesActivity.this);
                } else if (mViewPager.getCurrentItem() == 1) {
                 /* try {
                      jsonObject.put("X-DEVICE-ID", android_id);
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }*/
                    AppUtil.logUserWithEventName(0, "Intro2Skip", jsonObject, IntroSlidesActivity.this);
                }
                callLogin_Actvity();
            }
        });

        //Pagination will be done here
        /*Cheching Null check condition for mViewPager.getAdapter() method to avoid the getCount NullPointerException crash*/
        if (mViewPager.getAdapter() != null) {
            count = mViewPager.getAdapter().getCount();
        }
        page_text = new TextView[count];
        for (int i = 0; i < count; i++) {
            page_text[i] = new TextView(this);
            page_text[i].setText(".");
            page_text[i].setTextSize(50);
            page_text[i].setTextColor(Color.parseColor("#42231F20"));
            page_text[i].setTypeface(null, Typeface.BOLD);
            page_text[i].setPadding(0, 0, 10, 0);
            pagination_layout.addView(page_text[i]);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButtonClick = true;
                int currentItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(currentItem + 1);
               /* JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                if (currentItem == 0) {
                    //JSONObject jsonObject = new JSONObject();
                  /*  try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    AppUtil.logUserWithEventName(0, "Intro1_Next", jsonObject, IntroSlidesActivity.this);
                } else if (currentItem == 1) {
                    //JSONObject jsonObject = new JSONObject();
                  /*  try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    AppUtil.logUserWithEventName(0, "Intro2_Next", jsonObject, IntroSlidesActivity.this);
                } else if (currentItem == 2) {
                    //JSONObject jsonObject = new JSONObjecIntro2_RightSwipet();
                 /*   try {
                        jsonObject.put("X-DEVICE-ID", android_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    AppUtil.logUserWithEventName(0, "Intro3_GetStarted", jsonObject, IntroSlidesActivity.this);
                }
                if (currentItem == count - 1) {
                    next.setText("Get Started");
                    skip.setText("");
                    callLogin_Actvity();
                }

            }
        });
        /*Checking if page_text array object is empty or not*/
        if (page_text.length > 0) {
            IntroSlidesActivity.page_text[0].setTextColor(Color.WHITE);
        }
        frameLayout.setBackgroundResource(bg_colors[0]);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            float sumpositionAndpostioOffset;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
/*                if(positionOffset>0.2){
                    mViewPager.setCurrentItem(position+1);
                }*/
                /*JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                if (!nextButtonClick) {
                    if (position + positionOffset > sumpositionAndpostioOffset) {
                        if (position == 0) {

                            AppUtil.logUserWithEventName(0, "Intro1_LeftSwipe", jsonObject, IntroSlidesActivity.this);
                        } else if (position == 1) {
                      /*  JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("X-DEVICE-ID", android_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                            AppUtil.logUserWithEventName(0, "Intro2_LeftSwipe", jsonObject, IntroSlidesActivity.this);
                        } else if (position == 2) {
                        /*JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("X-DEVICE-ID", android_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppUtil.logUserWithEventName(0, "Intro3_LeftSwipe", jsonObject);*/
                        }
                        //Toast.makeText(IntroSlidesActivity.this, "right to left", Toast.LENGTH_SHORT).show();

                    } else {
                        //Toast.makeText(IntroSlidesActivity.this, "left to right", Toast.LENGTH_SHORT).show();
                        if (position == 0) {
                        /*JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("X-DEVICE-ID", android_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppUtil.logUserWithEventName(0, "Intro1_RightSwipe", jsonObject);*/
                        } else if (position == 1) {
                      /*  JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("X-DEVICE-ID", android_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                            AppUtil.logUserWithEventName(0, "Intro2_RightSwipe", jsonObject, IntroSlidesActivity.this);
                        } else if (position == 2) {
                        /*JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("X-DEVICE-ID", android_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                            AppUtil.logUserWithEventName(0, "Intro3_RightSwipe", jsonObject, IntroSlidesActivity.this);
                        }
                    }
                    sumpositionAndpostioOffset = position + positionOffset;
                }
                nextButtonClick = false;

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < count; i++) {
                    page_text[i].setTextColor(Color.parseColor("#42231F20"));
                }
               /* JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("X-DEVICE-ID", android_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                AppUtil.logUserWithEventName(0, "SplashScreenImpressions", jsonObject, IntroSlidesActivity.this);
                /*Checking if page_text array object is empty or not*/
                if (page_text.length > 0) {
                    IntroSlidesActivity.page_text[position].setTextColor(Color.WHITE);
                }
                frameLayout.setBackgroundResource(bg_colors[position]);
                if (position == 2) {
                    skip.setVisibility(View.INVISIBLE);
                    next.setText("Get Started");
                }
                if (position == 0) {
                    AppUtil.logUserWithEventName(0, "Intro1_Impressions", jsonObject, IntroSlidesActivity.this);
                } else if (position == 1) {
                    AppUtil.logUserWithEventName(0, "Intro2_Impressions", jsonObject, IntroSlidesActivity.this);
                } else if (position == 2) {
                    AppUtil.logUserWithEventName(0, "Intro3_Impressions", jsonObject, IntroSlidesActivity.this);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
               /* JSONObject jsonObject=new JSONObject();
                try {
                    AppUtil.logUserUpShotEvent("GetStartedHomeTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                String event_name = "";
                if (mViewPager.getCurrentItem() == 0) {
                    event_name = "Intro1HomeTapped";
                } else if (mViewPager.getCurrentItem() == 1) {
                    event_name = "Intro2HomeTapped";
                } else if (mViewPager.getCurrentItem() == 2) {
                    event_name = "Intro3HomeTapped";
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    AppUtil.logUserUpShotEvent(event_name, AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onHomeLongPressed() {

            }
        });
        mHomeWatcher.startWatch();
    }

    private void callLogin_Actvity() {
        mySharedPref.savePref(mySharedPref.PREF_INTROSLIDES, true);
        if (realmManager.getDoc_id(realm) != 0) {
            Intent i = new Intent(IntroSlidesActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(IntroSlidesActivity.this, GetStartedActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        String event_name = "";
        if (mViewPager.getCurrentItem() == 0) {
            event_name = "Intro1DeviceBackTapped";
        } else if (mViewPager.getCurrentItem() == 1) {
            event_name = "Intro2DeviceBackTapped";
        } else if (mViewPager.getCurrentItem() == 2) {
            event_name = "Intro3DeviceBackTapped";
        }
        JSONObject jsonObject = new JSONObject();
        try {
            AppUtil.logUserUpShotEvent(event_name, AppUtil.convertJsonToHashMap(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mViewPager.getCurrentItem() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to exit ?");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            switch (mViewPager.getCurrentItem()) {
                case 1:
                    mViewPager.setCurrentItem(0);
                    break;
                case 2:
                    mViewPager.setCurrentItem(1);
                    break;
                case 3:
                    mViewPager.setCurrentItem(2);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeWatcher.stopWatch();
    }
}


