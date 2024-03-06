package com.vam.whitecoats.ui.interfaces;


import com.vam.whitecoats.core.models.SubCategoriesInfo;

public interface SubCategoriesItemClickListener {
    void onItemClicked(int position);

    void onItemClickedData(SubCategoriesInfo dataModel, boolean checkboxclicked);
}
