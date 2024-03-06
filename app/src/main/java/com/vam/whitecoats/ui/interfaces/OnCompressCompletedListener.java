package com.vam.whitecoats.ui.interfaces;

import java.util.ArrayList;

/**
 * Created by satyasarathim on 28-01-2016.
 */
public interface OnCompressCompletedListener {
    public void onCompressCompleted(String[] filePaths);
    public void onCompressCompletedWithArrayList(ArrayList<String> filePaths);
}
