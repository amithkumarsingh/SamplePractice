package com.vam.whitecoats.utils;


public class UpdateCategoryUnreadCountEvent {
    private String message;
    private int countNeedtoUpdate;
    private int categoryId;

    public UpdateCategoryUnreadCountEvent(String _message, int count,int _categoryId){
        this.message=_message;
        this.countNeedtoUpdate=count;
        this.categoryId=_categoryId;

    }
    public String getMessage(){
        return message;
    }





    public int getCountNeedtoUpdate() {
        return countNeedtoUpdate;
    }

    public int getCategoryId() {
        return categoryId;
    }

}
