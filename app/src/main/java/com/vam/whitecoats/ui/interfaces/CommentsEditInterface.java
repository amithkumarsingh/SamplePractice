package com.vam.whitecoats.ui.interfaces;

import org.json.JSONArray;

public interface CommentsEditInterface {
    void commentsEditInterface(String s,int i,JSONArray attachmentsArray,int itemId);
    void removeReportInterface(String action, String otherDocName);
}
