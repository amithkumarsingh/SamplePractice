package com.vam.whitecoats.ui.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by venuv on 4/3/2018.
 */

public class FixedTextView extends androidx.appcompat.widget.AppCompatTextView {

    public FixedTextView(final Context context) {
        super(context);
    }

    public FixedTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int startSelection = getSelectionStart();
        int endSelection = getSelectionEnd();
        if (startSelection != endSelection) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                final CharSequence text = getText();
                setText(null);
                setText(text);
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
