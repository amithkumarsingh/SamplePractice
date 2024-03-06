package com.vam.whitecoats.ui.customviews;

import android.graphics.Color;

/**
 * Created by swathim on 06-08-2015.
 */
public class Border {
    private int orientation;
    private int width;
    private int color = Color.GREEN;
    private int style;
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getStyle() {
        return style;
    }
    public void setStyle(int style) {
        this.style = style;
    }
    public int getOrientation() {
        return orientation;
    }
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    public Border(int Style) {
        this.style = Style;
    }
}
