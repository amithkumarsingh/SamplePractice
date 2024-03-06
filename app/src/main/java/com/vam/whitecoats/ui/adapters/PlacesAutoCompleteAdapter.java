package com.vam.whitecoats.ui.adapters;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.Prediction;
import com.vam.whitecoats.core.models.Predictions;
import com.vam.whitecoats.ui.interfaces.GooglePlacesAPI;
import com.vam.whitecoats.ui.interfaces.GooglePlacesAPIWithFilter;
import com.vam.whitecoats.utils.APIClient;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.SingletonRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class PlacesAutoCompleteAdapter extends ArrayAdapter<Prediction> {

    private final String api_key;
    private Context context;
    private List<Prediction> predictions;
    boolean cityFilter;
    private Call<Predictions> call;
    private int doc_id;


    public PlacesAutoCompleteAdapter(Context context, List<Prediction> predictions, boolean mCityFilter, String mApi_key,int docId) {
        super(context, R.layout.place_row_layout, predictions);
        this.context = context;
        this.predictions = predictions;
        this.cityFilter = mCityFilter;
        this.api_key=mApi_key;
        this.doc_id=docId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_row_layout, null);
        if (predictions != null && predictions.size() > 0) {
            Prediction prediction = predictions.get(position);
            TextView textViewName = view.findViewById(R.id.textViewName);
            ImageView location_image = view.findViewById(R.id.location);
            if(position==0){
                location_image.setVisibility(View.VISIBLE);
            }else{
                location_image.setVisibility(View.GONE);
            }
            textViewName.setText(prediction.getDescription());
            String apiKey = api_key;
        }
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new PlacesAutoCompleteFilter(this, context);
    }

    private class PlacesAutoCompleteFilter extends Filter {

        private PlacesAutoCompleteAdapter placesAutoCompleteAdapter;
        private Context context;

        public PlacesAutoCompleteFilter(PlacesAutoCompleteAdapter placesAutoCompleteAdapter, Context context) {
            super();
            this.placesAutoCompleteAdapter = placesAutoCompleteAdapter;
            this.context = context;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            try {
                placesAutoCompleteAdapter.predictions.clear();
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.values = new ArrayList<Prediction>();
                    filterResults.count = 0;

                } else {
                    if(call!=null){
                        call.cancel();
                    }
                    if (cityFilter) {
                        GooglePlacesAPIWithFilter googleMapAPI = APIClient.getClient().create(GooglePlacesAPIWithFilter.class);
                        call = googleMapAPI.getPlacesAutoComplete(charSequence.toString(), "(cities)", "en", api_key);
                    } else {
                        GooglePlacesAPI googleMapAPI = APIClient.getClient().create(GooglePlacesAPI.class);
                        call = googleMapAPI.getPlacesAutoComplete(charSequence.toString(), "en", api_key);
                    }
                    if (call != null) {
                        call.enqueue(new Callback<Predictions>() {
                            @Override
                            public void onResponse(Call<Predictions> call, retrofit2.Response<Predictions> response) {
                                Predictions predictionsResonse = response.body();
                                Prediction temp=new Prediction();
                                temp.setDescription("Use Current Location");
                                temp.setId("-1");
                                predictionsResonse.addPrediction(temp,0);
                                filterResults.values = predictionsResonse.getPredictions();
                                filterResults.count = predictionsResonse.getPredictions().size();
                                publishResults(charSequence,filterResults);
                            }

                            @Override
                            public void onFailure(Call<Predictions> call, Throwable t) {
                                //call.cancel();
                                String errorMsg = "Unable to fetch google search results";
                                JSONObject errorObj=new JSONObject();
                                try {
                                    errorObj.put("errorMsg",errorMsg);
                                    AppUtil.logUserActionEvent(doc_id, "FetchGoogleSearchResultsFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),context);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }                            }
                        });
                    }
                        /*RequestForPlacesAPI(charSequence.toString(), new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {
                                //VolleyLog.wtf(response, "utf-8");
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Predictions predictions = mGson.fromJson(response, Predictions.class);
                                if(predictions!=null && predictions.getPredictions()!=null) {
                                    filterResults.values = predictions.getPredictions();
                                    filterResults.count = predictions.getPredictions().size();
                                    publishResults(charSequence, filterResults);
                                }
                            }
                        });*/

                }
                return filterResults;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults == null || filterResults.values == null) {
                return;
            }
            placesAutoCompleteAdapter.predictions.clear();
            System.out.println(filterResults.values);
            placesAutoCompleteAdapter.predictions.addAll((List<Prediction>) filterResults.values);
            placesAutoCompleteAdapter.notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Prediction prediction = (Prediction) resultValue;
            return prediction.getDescription();
        }
    }

    private void RequestForPlacesAPI(String input, final VolleyCallback callback) {
        predictions.clear();
        //VolleyLog.DEBUG = true;
        String BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=";

        String BASE_URL_PLACEID = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
        RequestQueue queue = SingletonRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
        String uri_page_one = null;
        String cityFilterKey = "";
        try {
            if (cityFilter) {
                cityFilterKey = "&types=(cities)";
            }
            uri_page_one = BASE_URL + URLEncoder.encode(input, "UTF-8") + cityFilterKey + "&key=" + api_key;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        queue.cancelAll("PLACES_TAG");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uri_page_one, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response.toString());
            }
        }, errorListener) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Request.Priority getPriority() {
                return Request.Priority.IMMEDIATE;
            }
        };
        jsonObjectRequest.setTag("PLACES_TAG");
        queue.add(jsonObjectRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                try {
                    if (request.getCacheEntry() != null) {
                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    public interface VolleyCallback {
        void onSuccess(String result);
    }

}