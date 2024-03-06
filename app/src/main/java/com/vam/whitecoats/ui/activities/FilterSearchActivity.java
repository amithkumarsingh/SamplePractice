package com.vam.whitecoats.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.view.ViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;

public class FilterSearchActivity extends BaseActionBarActivity {
    public static final String TAG = FilterSearchActivity.class.getSimpleName();
    AutoCompleteTextView search_by_city;
    EditText search_by_workplace;
    TextView city_error_text, mTitleTextView;
    ArrayAdapter<String> cities_adpt;
    String city = "", workplace = "", radioButtonText;
    private ArrayList<String> citiesList = new ArrayList<>();
    String deptTitle;
    LinearLayout layout_Location, layout_WorkPlace;
    TextView next_button;
    RadioButton radioBtn_WorkPlace;
    RadioGroup workplaceradiogroup;
    private String navigation;
    private Realm realm;
    private RealmManager realmManager;
    ArrayList<ProfessionalInfo> professionalInfoList;
    private Bundle bundle;
    RadioButton radioButton;
    private HashMap<String, String> criteriaFilterMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtersearch);
        setupActionbar();

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        professionalInfoList = new ArrayList<>();
        professionalInfoList.addAll(realmManager.getProfessionalInfo(realm));

        bundle = getIntent().getExtras();

        criteriaFilterMap = (HashMap<String, String>) bundle.getSerializable("filter_map");
        navigation = criteriaFilterMap.get("selectedFilter");
        initilize();
        /*validationUtils = new ValidationUtils(FilterSearchActivity.this,
                new EditText[]{search_by_city,search_by_workplace},
                new TextView[]{ city_error_text,workplace_error_text});*/
        next_button
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppUtil.isConnectingToInternet(FilterSearchActivity.this)) {


                            city = search_by_city.getText().toString().trim();
                            workplace = search_by_workplace.getText().toString().trim();
                            if (navigation.equalsIgnoreCase("fromlocation") && city.length() == 0) {
                                Toast.makeText(FilterSearchActivity.this, R.string.Enter_location, Toast.LENGTH_SHORT).show();
                                return;
                            } else if (navigation.equalsIgnoreCase("fromworkplace") && workplace.length() == 0) {
                                Toast.makeText(FilterSearchActivity.this, R.string.Enter_workplace, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent locationIntent = new Intent(FilterSearchActivity.this, UserSearchResultsActivity.class);
                            criteriaFilterMap.put("Location", city);
                            criteriaFilterMap.put("Workplace", workplace);

                            locationIntent.putExtra("criteriaFilter", criteriaFilterMap);
//                            locationIntent.putExtra(RestUtils.NAVIGATATION, "fromFilter");
                            setResult(RESULT_OK, locationIntent);
                            finish();

                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void initilize() {
        search_by_city = _findViewById(R.id.searchbycity);
        search_by_workplace = _findViewById(R.id.searchbyclinic);
        layout_WorkPlace = _findViewById(R.id.layout_workplace);
        layout_Location = _findViewById(R.id.layout_location);
        workplaceradiogroup = findViewById(R.id.workplaceradiogroup);

        if (navigation != null && navigation.equalsIgnoreCase("fromLocation")) {
            mTitleTextView.setText("Location");
            layout_WorkPlace.setVisibility(View.GONE);
            layout_Location.setVisibility(View.VISIBLE);
        } else {
            if (navigation != null && navigation.equalsIgnoreCase("fromworkplace")) {
                mTitleTextView.setText("Workplace");
                layout_WorkPlace.setVisibility(View.VISIBLE);
                layout_Location.setVisibility(View.GONE);
            }
        }

        //get cities list
        ArrayList<String> searchkeys = new ArrayList<String>();
        searchkeys.add("cities");
        city = search_by_city.getText().toString().trim();
        if (AppUtil.isConnectingToInternet(mContext)) {
            new AutoSuggestionsAsync(FilterSearchActivity.this, searchkeys).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
        }
        cities_adpt = new ArrayAdapter<String>(FilterSearchActivity.this, android.R.layout.simple_list_item_1, citiesList);
        search_by_city.setAdapter(cities_adpt);
        search_by_city.setThreshold(1);

        search_by_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);
            }
        });

        RadioGroup.LayoutParams rprms;
        for (int i = 0; i < professionalInfoList.size(); i++) {
            radioButton = new RadioButton(this);
            ViewCompat.setLayoutDirection(radioButton, ViewCompat.LAYOUT_DIRECTION_RTL);
            radioButton.setText(professionalInfoList.get(i).getWorkplace());
            radioButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            radioButton.setId(100 + i);
            rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            workplaceradiogroup.addView(radioButton, rprms);
        }
        workplaceradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = _findViewById(i);
                radioButtonText = radioButton.getText().toString();
                radioButton.setChecked(false);
                search_by_workplace.setText(radioButtonText);
            }
        });

        search_by_workplace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    workplaceradiogroup.setVisibility(View.GONE);
                } else {
                    workplaceradiogroup.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /*
        action bar set up
         */
    private void setupActionbar() {
        Log.i(TAG, "setupActionbar()");
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_filtersearch, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_filter);
        next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        //mTitleTextView.setText("Location");
        next_button.setText(getString(R.string.action_done));

        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onTaskCompleted(String response) {

        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
                hideProgress();
                ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);

                        JSONArray citiesjArray = data.getJSONArray("cities");

                        int citieslen = citiesjArray.length();
                        if (citieslen > 0) {
                            for (int i = 0; i < citieslen; i++) {
                                citiesList.add(citiesjArray.get(i).toString());
                                cities_adpt.notifyDataSetChanged();
                            }
                        }

                    } else if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        hideProgress();
                        String errorMsg = getResources().getString(R.string.unknown_server_error);
                        if (!jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE).isEmpty()) {
                            errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                        }
                        ShowSimpleDialog("Error", errorMsg);
                    }
                } catch (Exception e) {
                    hideProgress();
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
