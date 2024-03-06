package com.vam.whitecoats.ui.customviews;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

public class AutoFillDisableEditText extends AppCompatEditText {
    public AutoFillDisableEditText(Context context) {
        super(context);
    }

    public AutoFillDisableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFillDisableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getAutofillType() {
        return AUTOFILL_TYPE_NONE;
    }
}
