package com.vam.whitecoats.ui.interfaces;

import com.google.common.collect.ArrayListMultimap;
import com.vam.whitecoats.core.models.PublicationsInfo;

/**
 * Created by swathim on 5/29/2015.
 */
public interface DataTransferInterface {
     ArrayListMultimap<String,String> getDeletedValues(ArrayListMultimap<String, String> al);
     ArrayListMultimap<String,String> getUpdatedValue(ArrayListMultimap<String,String> ul);
}
