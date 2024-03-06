package com.vam.whitecoats.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.brandkinesis.utils.BKUtilLogger;
import com.vam.whitecoats.R;

import java.util.List;

import static com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes.BKACTIVITY_BG_COLOR;
import static com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes.BKACTIVITY_BOTTOM_COLOR;
import static com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes.BKACTIVITY_SURVEY_HEADER_COLOR;

public class BKUIComponents implements BKUIPrefComponents {
    @Override
    public void setPreferencesForRelativeLayout(RelativeLayout relativeLayout, BKActivityTypes bkActivityTypes, BKActivityRelativeLayoutTypes bkActivityRelativeLayoutTypes, boolean b) {

    }

    @Override
    public void setPreferencesForImageButton(ImageButton imageButton, BKActivityTypes bkActivityTypes, BKActivityImageButtonTypes bkActivityImageButtonTypes) {

    }

    @Override
    public void setPreferencesForButton(Button button, BKActivityTypes bkActivityTypes, BKActivityButtonTypes bkActivityButtonTypes) {
        button.setBackgroundColor(Color.parseColor("#00A76D"));
        button.setTextColor(Color.parseColor("#FFFFFF"));
        BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization Survey BKACTIVITY_SURVEY_CONTINUE_BUTTON");
        /*switch (bkActivityButtonTypes) {

            case BACTIVITY_RATING_LIKE_BUTTON:

//                button.setBackgroundColor(Color.GREEN);
//                button.setBackgroundResource(R.drawable.upshot_rating_like_selector);
                break;
            case BACTIVITY_RATING_DISLIKE_BUTTON:
//                button.setBackgroundColor(Color.BLUE);
//                button.setBackgroundResource(R.drawable.upshot_rating_dislike_selector);
                break;
            default:

                break;
        }*/

    }

    @Override
    public void setPreferencesForTextView(TextView textView, BKActivityTypes bkActivityTypes, BKActivityTextViewTypes bkActivityTextViewTypes) {

    }

    @Override
    public void setPreferencesForImageView(ImageView imageView, BKActivityTypes bkActivityTypes, BKActivityImageViewType bkActivityImageViewType) {

    }

    @Override
    public void setPreferencesForOptionsSeparatorView(View view, BKActivityTypes bkActivityTypes) {

    }

    @Override
    public void setCheckBoxRadioSelectorResource(BKUICheckBox bkuiCheckBox, BKActivityTypes bkActivityTypes, boolean b) {

    }

    @Override
    public void setRatingSelectorResource(List<Bitmap> list, List<Bitmap> list1, BKActivityTypes bkActivityTypes, BKActivityRatingTypes bkActivityRatingTypes) {

    }

    @Override
    public void setPreferencesForUIColor(BKBGColors bkbgColors, BKActivityTypes bkActivityTypes, BKActivityColorTypes bkActivityColorTypes) {
        //switch (bkActivityColorTypes) {
            /*case BKACTIVITY_SURVEY_HEADER_COLOR:
            case BKACTIVITY_TRIVIA_HEADER_COLOR:
                bkbgColors.setColor(R.color.app_green);
                break;*/

        switch (bkActivityTypes) {
            case ACTIVITY_TRIVIA:
                switch (bkActivityColorTypes) {
                    case BKACTIVITY_SURVEY_HEADER_COLOR:
                        bkbgColors.setColor(Color.parseColor("#00A76D"));
                        break;
                    case BKACTIVITY_TRIVIA_TITLE_COLOR:
                        bkbgColors.setColor(Color.parseColor("#00A76D"));
                        break;
                    case BKACTIVITY_TRIVIA_HEADER_COLOR:
                        bkbgColors.setColor(Color.parseColor("#00A76D"));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setPreferencesForGraphColor(BKGraphType bkGraphType, List<Integer> list, BKActivityTypes bkActivityTypes) {

    }

    @Override
    public int getPositionPercentageFromBottom(BKActivityTypes bkActivityTypes, BKViewType bkViewType) {
        return 0;
    }

    @Override
    public void setPreferencesForSeekBar(SeekBar seekBar, BKActivityTypes bkActivityTypes, BKActivitySeekBarTypes bkActivitySeekBarTypes) {

    }

    @Override
    public void setPreferencesForEditText(EditText editText, BKActivityTypes bkActivityTypes, BKActivityEditTextTypes bkActivityEditTextTypes) {

    }

    @Override
    public void setPreferencesForLinearLayout(LinearLayout linearLayout, BKActivityTypes bkActivityTypes, BKActivityLinearLayoutTypes bkActivityLinearLayoutTypes) {

    }
}
