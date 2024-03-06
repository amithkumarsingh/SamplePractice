package com.vam.whitecoats.utils;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.vam.whitecoats.constants.VIEWTYPE;

public class ViewFactory {
    private static final ViewFactory instance = new ViewFactory();
    //private constructor to avoid client applications to use constructor
    private ViewFactory(){}

    public static ViewFactory getInstance(){
        return instance;
    }

    public View getView(VIEWTYPE viewType, Context context){
        if(viewType.equals(VIEWTYPE.CHECK_BOX))
            return new CheckBox(context);
        else if(viewType.equals(VIEWTYPE.RADIO_BUTTON))
            return new RadioButton(context);
        else if(viewType.equals(VIEWTYPE.TEXT_VIEW))
            return new TextView(context);
        else if(viewType.equals(VIEWTYPE.BUTTON))
            return new Button(context);
        else if(viewType.equals(VIEWTYPE.LINEAR_LAYOUT))
            return new LinearLayout(context);
        else
            return null;
    }
}
