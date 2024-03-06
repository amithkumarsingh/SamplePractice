package com.vam.whitecoats.ui.interfaces;

import com.vam.whitecoats.utils.FileDetails;

import java.util.ArrayList;

public interface OnCopyCompletedListener {

    void onCopyCompleted(ArrayList<FileDetails> paths, String fileType);

}
