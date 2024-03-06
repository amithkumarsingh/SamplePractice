package com.vam.whitecoats.core.models;

import java.util.ArrayList;

public class HorizontalListDataObj {

    private String horizontalListType;
    private int parentFeedId;
    private ArrayList<Integer> childFeedIds=new ArrayList<>();
    private int pagedIndex;
    private int lastFeedId;
    private String horizontalListTitle;
    private boolean isMoreVisible;
    private int selectedPosition=0;
    private int parentChannelId;

    public String getHorizontalListType() {
        return horizontalListType;
    }

    public void setHorizontalListType(String horizontalListType) {
        this.horizontalListType = horizontalListType;
    }

    public int getParentFeedId() {
        return parentFeedId;
    }

    public void setParentFeedId(int parentFeedId) {
        this.parentFeedId = parentFeedId;
    }

    public ArrayList<Integer> getChildFeedIds() {
        return childFeedIds;
    }

    public void setChildFeedIds(ArrayList<Integer> childFeedIds) {
        this.childFeedIds = childFeedIds;
    }

    public int getPagedIndex() {
        return pagedIndex;
    }

    public void setPagedIndex(int pagedIndex) {
        this.pagedIndex = pagedIndex;
    }

    public int getLastFeedId() {
        return lastFeedId;
    }

    public void setLastFeedId(int lastFeedId) {
        this.lastFeedId = lastFeedId;
    }

    public String getHorizontalListTitle() {
        return horizontalListTitle;
    }

    public void setHorizontalListTitle(String horizontalListTitle) {
        this.horizontalListTitle = horizontalListTitle;
    }

    public boolean isMoreVisible() {
        return isMoreVisible;
    }

    public void setMoreVisible(boolean moreVisible) {
        isMoreVisible = moreVisible;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getParentChannelId() {
        return parentChannelId;
    }

    public void setParentChannelId(int parentChannelId) {
        this.parentChannelId = parentChannelId;
    }
}
