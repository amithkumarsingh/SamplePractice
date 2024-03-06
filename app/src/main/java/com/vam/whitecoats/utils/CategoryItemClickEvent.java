package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.Category;

public class CategoryItemClickEvent {

    private Category category;
    private String message;

    public CategoryItemClickEvent(String _message, Category _category){
        this.message=_message;
        this.category=_category;

    }
    public String getMessage(){
        return message;
    }

    public Category getCategory() {
        return category;
    }
}
