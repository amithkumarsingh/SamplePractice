package com.vam.whitecoats;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.vam.whitecoats.utils.AppUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class LocalUnitTest {

    private Context context;
    @Test
    public void testInternetConnection(){
        assertEquals(AppUtil.isConnectingToInternet(context),true);
    }

   @Before
   public void setUp(){
       context = ApplicationProvider.getApplicationContext();
   }
    @Test
    public void testSuffixStringMethod(){
        assertEquals(AppUtil.suffixNumber(1002),"1.0K+");
    }

    @Test
    public void testIsPhoneNumber(){
        assertEquals(AppUtil.isPhoneNumber("9247127800"),true);
    }

    @Test
    public void testValidateEmail(){
        assertEquals(AppUtil.validateEmail("example@email.com"),true);
    }

    @Test
    public void testisEmailAddress(){
        assertEquals(AppUtil.isEmailAddress("example@email.com"),true);
    }
    @Test
    public void testisWebUrl(){
        assertEquals(AppUtil.isWebUrl("www.google.com"),true);
    }

    @Test
    public void testisFBUrl(){
        assertEquals(AppUtil.isFbUrl("facebook.com"),true);
    }

   /* @Test
    public void testGetMimeType(){
        assertEquals(AppUtil.getMimeType("https://www.google.com/sample.jpeg"),"image");
    }*/


   @Test
   public void testunescapeJavaString(){
       try {
           assertEquals(AppUtil.unescapeJavaString("Hello"),"Hello");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   @Test
   public void testisFileSizeSupported(){
       assertEquals(AppUtil.isFileSizeSupported(new File("")),true);
   }
    @Test
    public void testIsJSONValid(){
        assertEquals(AppUtil.isJSONValid("test"),false);
    }

    @Test
    public void testmaskEmailId(){
       assertEquals(AppUtil.maskEmailId("sampletest@gmail.com"),"s********t@gmail.com");
    }

    @Test
    public void testIsWhitecoatsUrl(){
        assertEquals(AppUtil.isWhitecoatsUrl("https://wcts.app/Uaxut"),true);
    }

    @Test
    public void testIsYoutubeUrl(){
        assertEquals(AppUtil.isYoutubeUrl("http://www.youtube.com/watch?v=WK0YhfKqdaI"),true);
    }

    @Test
    public void testGetVideoIdFromYoutubeUrl(){
        assertEquals(AppUtil.getVideoIdFromYoutubeUrl("http://youtu.be/WK0YhfKqdaI"),"WK0YhfKqdaI");
    }

    /*@Test

    public void testUnescapeJavaString(){
        try {
            assertEquals(AppUtil.unescapeJavaString("hello\nhello"),"hello" +
                    "hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
