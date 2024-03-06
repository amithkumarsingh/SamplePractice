package com.vam.whitecoats.viewmodel;

import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.core.models.Category;

public class ExploreItemViewModel extends ViewModel {

    private Category exploreItem;

    public ExploreItemViewModel(Category exploreItem){
        this.exploreItem =exploreItem;
    }
    public Category getExploreItem(){
        return this.exploreItem;
    }
    public String getTitle(){
        return  exploreItem.getCategoryName();
    }
    public String getCount(){
        return String.valueOf(exploreItem.getUnreadCount());
    }
}
