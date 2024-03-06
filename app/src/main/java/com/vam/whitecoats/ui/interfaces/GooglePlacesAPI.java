package com.vam.whitecoats.ui.interfaces;



import com.vam.whitecoats.core.models.Predictions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesAPI {

    @GET("place/autocomplete/json")
    public Call<Predictions> getPlacesAutoComplete(
            @Query("input") String input,
//            @Query("types") String types,
            @Query("language") String language,
            @Query("key") String key
    );

}