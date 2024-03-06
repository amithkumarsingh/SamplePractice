package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.fragments.Feeds_Fragment;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

public class PreferencesActivity extends BaseActionBarActivity {
    private static final String TAG = PreferencesActivity.class.getSimpleName();

    private ListView mListView;
    protected LayoutInflater mInflater;
    protected View mCustomView;
    private CheckBox selectAllPreferenceCheckBox;
    PreferencesAdapter preferenceAdapter;
    ArrayList<HashMap<String, String>> specialityList;
    TextView selectAllLabelTxtVw;
    int channelId;
    int docId;
    int selectedItemCount,totalItemsCount;
    private RealmBasicInfo basicInfo;
    private boolean customBackButton=false;
    private Realm realm;
    private RealmManager rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        setContentView(R.layout.activity_preferences);
         realm = Realm.getDefaultInstance();
         rm = new RealmManager(this);
        basicInfo = rm.getRealmBasicInfo(realm);
        selectAllPreferenceCheckBox = (CheckBox) findViewById(R.id.selectAllPref_ChkBox);
        selectAllLabelTxtVw = (TextView) findViewById(R.id.selectAllLabel);
        mListView = (ListView) findViewById(R.id.list_view);
        specialityList = new ArrayList<HashMap<String, String>>();
//        set tool bar
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        mTitleTextView.setText(getString(R.string.label_set_preferences));
        next_button.setText(getString(R.string.action_done));
        setActionBar();
        /*
         * Get Doc ID
         * Get params from Bundle
         */

        docId = rm.getDoc_id(realm);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            channelId = getIntent().getExtras().getInt(RestUtils.CHANNEL_ID);
        }
        totalItemsCount=specialityList.size();
        /**
         *  Define all Listeners here
         */
        selectAllPreferenceCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectAllPreferenceCheckBox.isChecked()) {
                    selectedItemCount=totalItemsCount;
                    checkAllOrNone(true, totalItemsCount);
                    Toast.makeText(PreferencesActivity.this, getString(R.string.receive_all_article_info), Toast.LENGTH_SHORT).show();
                } else {
                    selectedItemCount=0;
                    checkAllOrNone(false, totalItemsCount);
                }
            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(PreferencesActivity.this)) {
                    try {
                        JSONArray specialityIds = getSpecialityIds();
                        //JSONArray specialityNames = getSpecialityNames();
                        if (specialityIds.length() > 0) {
                            showProgress();
                            JSONObject requestData = new JSONObject();
                            requestData.put(RestUtils.TAG_DOC_ID, docId);
                            requestData.put(RestUtils.CHANNEL_ID, channelId);
                            requestData.put(RestUtils.CHANNEL_PREF_LIST, specialityIds);
                            setChannelPreferences(requestData,getSpecialityNames());
                            //comminted urban airship set tags
                            /*Set<String> prefSet= new HashSet<String>();
                            for(int i=0;i<specialityIds.length();i++){
                                prefSet.add(specialityIds.optString(i));
                            }
                            UAirship.shared().getPushManager().editTags()
                                    .addTags(prefSet).apply();*/

                        } else {
                            Toast.makeText(PreferencesActivity.this, getString(R.string.label_no_speciality_selected), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Prepare the preference adapter with empty list
        displayPreferenceList(specialityList);
        /*
         * Get preferences from server
         */
        JSONObject requestObj = new JSONObject();
        try {
            if (AppUtil.isConnectingToInternet(PreferencesActivity.this)) {
                showProgress();
                requestObj.put(RestUtils.TAG_DOC_ID, docId);
                requestObj.put(RestUtils.CHANNEL_ID, channelId);
                getChannelPreferences(requestObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hideProgress();
        }
    }

    @Override
    protected void setCurrentActivity() {

    }

    private JSONArray getSpecialityIds() {
        Log.i(TAG, "getSpecialityIds()");
        JSONArray specialityIds = new JSONArray();
        for (HashMap<String, String> speciality : specialityList) {
            if (Boolean.parseBoolean(speciality.get(RestUtils.IS_PREFERENCE_SET))) {
                specialityIds.put(speciality.get(RestUtils.SPECIALITY_ID));
            }
        }
        return specialityIds;
    }

    private String getSpecialityNames(){
        Log.i(TAG, "getSpecialityNames()");
        String result = "";
        //String[] specialityNames=new String[]{};
        //JSONArray specialityNames = new JSONArray();
        StringBuilder sb = new StringBuilder();
        for (HashMap<String, String> speciality : specialityList) {
            if (Boolean.parseBoolean(speciality.get(RestUtils.IS_PREFERENCE_SET))) {
                sb.append(speciality.get(RestUtils.SPECIALITY_NAME)).append(",");
            }
        }
        if(sb.length()>0){
            result = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return result;
    }

    private void getChannelPreferences(JSONObject request) {
        Log.i(TAG, "getChannelPreferences()");
        new VolleySinglePartStringRequest(PreferencesActivity.this, Request.Method.POST, RestApiConstants.GET_CHANNEL_PREFERENCES, request.toString(), "PREFERENCES_ACTIVITY_GET_CHANNEL_PREFERENCES", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i(TAG, "onSuccessResponse()");
                JSONObject response = null;
                try {
                    response = new JSONObject(successResponse);
                    if (response.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        JSONArray preferencesArray = response.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.PREFERENCES_LIST);
                        JSONArray specialitiesArray = response.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.DOC_SPECIALITY_LIST);
                        int prefArrayLen = preferencesArray.length();
                        for (int index = 0; index < prefArrayLen; index++) {
                            JSONObject prefObject = preferencesArray.optJSONObject(index);
                            HashMap<String, String> specialityMap = new HashMap<String, String>();
                            specialityMap.put(RestUtils.SPECIALITY_ID, prefObject.optString(RestUtils.SPECIALITY_ID));
                            specialityMap.put(RestUtils.SPECIALITY_NAME, prefObject.optString(RestUtils.SPECIALITY_NAME));
                            specialityMap.put(RestUtils.IS_PREFERENCE_SET, Boolean.toString(prefObject.optBoolean(RestUtils.IS_PREFERENCE_SET)));
                            specialityList.add(specialityMap);
                        }
                        // Sort here
                        ArrayList<HashMap<String, String>> sortedList= new ArrayList<HashMap<String, String>>();
                        sortedList.addAll((ArrayList<HashMap<String, String>>) getSortedItemList(specialityList));
                        // add to main list
                        specialityList.clear();
                        specialityList.addAll(sortedList);
                        // Check/UnCheck "Select all" checkbox
                        totalItemsCount=specialityList.size();
                        if(selectedItemCount==totalItemsCount){
                            selectAllPreferenceCheckBox.setChecked(true);
                        }else{
                            selectAllPreferenceCheckBox.setChecked(false);
                        }
//                      Refresh preferences List
                        preferenceAdapter.notifyDataSetChanged();
                        hideProgress();
                    } else if (response.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        hideProgress();
                        displayErrorScreen(successResponse);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                Log.i(TAG, "onErrorResponse()");
                hideProgress();
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();
    }

    private void setChannelPreferences(JSONObject request,String specialityNames) {
        Log.i(TAG, "setChannelPreferences()");
        new VolleySinglePartStringRequest(PreferencesActivity.this, Request.Method.POST, RestApiConstants.SET_CHANNEL_PREFERENCES, request.toString(), "PREFERENCES_ACTIVITY_SET_CHANNEL_PREFERENCES", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i(TAG, "onSuccessResponse()");
                hideProgress();
                String resultMessage = "";
                try {
                    HashMap<String , Object> data = new HashMap<>();
                    data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
                    data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
                    data.put(RestUtils.EVENT_PREFERENCE, specialityNames);
                    AppUtil.logUserEventWithHashMap("MyPreferenceChanging",basicInfo.getDoc_id(),data,PreferencesActivity.this);
                    JSONObject responseData = new JSONObject(successResponse);
                    JSONObject dataObj = responseData.optJSONObject(RestUtils.TAG_DATA);
                    resultMessage = dataObj.optString(RestUtils.TAG_MESSAGE);
                    if (resultMessage.length() == 0) {
                        resultMessage = "Speciality preferences updated successfully.";
                    }
                    setResult(Feeds_Fragment.PREFERENCE_ACTION);
                    Toast.makeText(PreferencesActivity.this, resultMessage, Toast.LENGTH_SHORT).show();
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                Log.i(TAG, "onErrorResponse()");
                hideProgress();
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();
    }

    /**
     *
     * @param itemList
     * @return sorted list
     */
    private List<HashMap<String, String>> getSortedItemList(List<HashMap<String, String>> itemList) {
        Log.i(TAG, "getSortedItemList()");
        /*
         * Prepare two list of maps having selected & unselected items
         */
        List<HashMap<String, String>> selectedItemList = new ArrayList<>();
        List<HashMap<String, String>> nonSelectedItemList = new ArrayList<>();
        for (HashMap<String, String> item : itemList) {
            if (Boolean.parseBoolean(item.get(RestUtils.IS_PREFERENCE_SET))) {
                selectedItemList.add(item);
            } else {
                nonSelectedItemList.add(item);
            }
        }

        /*
         * Sort both of them
         */
        itemList.clear();
        int selectedItemsSize=selectedItemList.size();
        if (selectedItemsSize > 0) {
            selectedItemCount=selectedItemsSize;
            selectedItemList = sortAlphabetically(selectedItemList);
            itemList.addAll(selectedItemList);
        }
        if (nonSelectedItemList.size() > 0) {
            nonSelectedItemList = sortAlphabetically(nonSelectedItemList);
            itemList.addAll(nonSelectedItemList);
        }
        return itemList;

    }

    /**
     *
     * @param listItems
     * @return alphabetically sorted list items
     */
    private List<HashMap<String, String>> sortAlphabetically(List<HashMap<String, String>> listItems) {
        Log.i(TAG, "sortAlphabetically()");
        Collections.sort(listItems, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> map1, HashMap<String, String> map2) {
                return (map1.get(RestUtils.SPECIALITY_NAME).toLowerCase().compareTo(map2.get(RestUtils.SPECIALITY_NAME).toLowerCase()));
            }
        });
        return listItems;
    }

    /**
     *
     * @param preferencesList
     */
    private void displayPreferenceList(List<HashMap<String, String>> preferencesList) {
        Log.i(TAG, "displayPreferenceList()");
        preferenceAdapter = new PreferencesAdapter(PreferencesActivity.this, (ArrayList<HashMap<String, String>>) preferencesList);
        mListView.setAdapter(preferenceAdapter);
    }

    private void setActionBar() {
        Log.i(TAG, "setActionBar()");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    /**
     *
     * @param isChecked
     * @param count
     */
    private void checkAllOrNone(boolean isChecked, int count) {
        Log.i(TAG, "checkAllOrNone()");
        for (int index = 0; index < count; index++) {
            specialityList.get(index).put(RestUtils.IS_PREFERENCE_SET, Boolean.toString(isChecked));
        }
        preferenceAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /*
     * PreferencesAdapter
     */
    public class PreferencesAdapter extends BaseAdapter {

        Activity mActivity;
        ArrayList<HashMap<String, String>> mSpecialityList;

        public PreferencesAdapter(Activity activity, ArrayList<HashMap<String, String>> specialityList) {
            this.mActivity = activity;
            this.mSpecialityList = specialityList;
        }

        @Override
        public int getCount() {
            /*
             * Length of listView
             */
            return mSpecialityList.size();
        }

        @Override
        public Object getItem(int position) {
            /*
             * Current Item
             */
            return position;
        }

        @Override
        public long getItemId(int position) {
            /*
             * Current Item's ID
             */
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ItemViewHolder itemViewHolder;
            HashMap<String, String> item = mSpecialityList.get(position);
            if (convertView == null) {
                itemViewHolder = new ItemViewHolder();
                final LayoutInflater sInflater = (LayoutInflater) mActivity.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = sInflater.inflate(R.layout.preferences_child, null, false);
                itemViewHolder.itemTextView = (TextView) convertView.findViewById(R.id.speciality_textView);
                itemViewHolder.itemCheckBox = (CheckBox) convertView.findViewById(R.id.speciality_checkBox);
                convertView.setTag(itemViewHolder);
            } else {
                itemViewHolder = (ItemViewHolder) convertView.getTag();
            }
            itemViewHolder.itemTextView.setText(item.get(RestUtils.SPECIALITY_NAME));
            itemViewHolder.itemTextView.setEllipsize(TextUtils.TruncateAt.END);
            itemViewHolder.itemTextView.setSingleLine(true);
            if (Boolean.parseBoolean(item.get(RestUtils.IS_PREFERENCE_SET))) {
                itemViewHolder.itemCheckBox.setChecked(true);
            } else {
                itemViewHolder.itemCheckBox.setChecked(false);
            }
            itemViewHolder.itemCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                     * On click of Check box
                     */
                    if (itemViewHolder.itemCheckBox.isChecked()) {
                        //Update the Check status as true in main List Object
                        specialityList.get(position).put(RestUtils.IS_PREFERENCE_SET, Boolean.toString(true));
                        selectedItemCount += 1;
                    } else {
                        //Update the Check status as false in main List Object
                        specialityList.get(position).put(RestUtils.IS_PREFERENCE_SET, Boolean.toString(false));
                        selectedItemCount -= 1;
                    }
                    if (selectedItemCount == getCount()) {
                        selectAllPreferenceCheckBox.setChecked(true);
                    } else {
                        selectAllPreferenceCheckBox.setChecked(false);

                    }
                }
            });
            return convertView;
        }

    }

    static class ItemViewHolder {
        TextView itemTextView;
        CheckBox itemCheckBox;
    }


    /*protected boolean isAllValuesChecked() {
        for (int i = 0; i < count; i++) {
            if (!mChecked.get(i)) {
                return false;
            }
        }
        return true;
    }*/

   /* private boolean checkForDocDefaultSpeciality(int speciality_id) {
        for (int j = 0; j < doc_speciality_arraylist.size(); j++) {
            if (doc_speciality_arraylist.get(j) == speciality_id) {
                return true;
            }
        }
        return false;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                customBackButton=true;
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",rm.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("SpecialityPreferenceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",rm.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("SpecialityPreferenceDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        finish();
    }
}
