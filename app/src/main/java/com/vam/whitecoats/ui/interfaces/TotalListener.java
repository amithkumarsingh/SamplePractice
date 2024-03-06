package com.vam.whitecoats.ui.interfaces;

/**
 * Created by ABHISHEK on 5/5/2015.
 */
public interface TotalListener {
    void onTotalChanged(int sum);

    void expandGroupEvent(int groupPosition, boolean isExpanded);
}
