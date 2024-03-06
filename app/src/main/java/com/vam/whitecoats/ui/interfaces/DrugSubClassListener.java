package com.vam.whitecoats.ui.interfaces;

import com.vam.whitecoats.core.models.DrugClass;
import com.vam.whitecoats.core.models.DrugSubClass;

public interface DrugSubClassListener {
    void onItemClick(DrugSubClass drugSubClass, Integer position);
}
