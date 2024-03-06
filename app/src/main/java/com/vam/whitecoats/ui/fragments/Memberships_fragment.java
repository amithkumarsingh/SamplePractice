package com.vam.whitecoats.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.async.AutoSuggestionsAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.AwardsAndMemberships;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Memberships_fragment} interface
 * to handle interaction events.
 * Use the {@link Memberships_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Memberships_fragment extends Fragment {
    public static final String TAG = Memberships_fragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Memberships_fragment instance;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AutoCompleteTextView memberShipEditTxt;
    Button addMemberButton;
    ArrayList<String> associationList = new ArrayList<>();
    ArrayAdapter<String> associationAdapter;
    private Realm realm;
    private RealmManager realmManager;
    int doctorID;
    public Memberships_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Memberships_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Memberships_fragment newInstance(String param1, String param2) {
        Memberships_fragment fragment = new Memberships_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Memberships_fragment getInstance() {
        if(instance==null)
            instance=newInstance("","");
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        doctorID = realmManager.getDoc_id(realm);
        setHasOptionsMenu(true);
        ArrayList<String> searchKeys = new ArrayList<String>();
        searchKeys.add("associations");
        if (AppUtil.isConnectingToInternet(getActivity())) {
            new AutoSuggestionsAsync(getActivity(), searchKeys, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String s) {
                    parseResponse(s);
                }
            }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor());
        }
        associationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, associationList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memberships, container, false);
        memberShipEditTxt = (AutoCompleteTextView) view.findViewById(R.id.add_membership_text);
        addMemberButton = (Button) view.findViewById(R.id.add_another_mem_btn);
        // Inflate the layout for this fragment
        memberShipEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                memberShipEditTxt.setAdapter(associationAdapter);
                memberShipEditTxt.setThreshold(1);
            }

            @Override
            public void afterTextChanged(Editable s) {
                AppConstants.add_membership = s.toString();
            }
        });
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!TextUtils.isEmpty(memberShipEditTxt.getText().toString().trim())) {
                        addMembershipServiceCall(getMembershipRequestJson(), "add");
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.warning_empty_membership_title), Toast.LENGTH_SHORT).show();
                    }
                    AppConstants.add_membership = "";

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_awards) {
            try {
                if (!TextUtils.isEmpty(memberShipEditTxt.getText().toString().trim())) {
//                    Awards_fragment.getInstance().checkForServiceCall(""); // If award value saved, then call create award service
                    addMembershipServiceCall(getMembershipRequestJson(), "save");
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.warning_empty_membership_title), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private JSONObject getMembershipRequestJson() {
        JSONObject requestObject = new JSONObject();
        try {
            JSONObject memberShipObj = new JSONObject();
            memberShipObj.put(RestUtils.TITLE, memberShipEditTxt.getText().toString().trim());
            memberShipObj.put(RestUtils.TAG_TYPE, "membership");
            requestObject.put(RestUtils.TAG_USER_ID, doctorID);
            requestObject.put(RestUtils.TAG_MEM_HISTORY, new JSONArray().put(memberShipObj));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    public void checkForServiceCall(String actionType) {
        if (!TextUtils.isEmpty(memberShipEditTxt.getText().toString().trim())) {
            addMembershipServiceCall(getMembershipRequestJson(), actionType);
        }
    }

    private void addMembershipServiceCall(JSONObject requestObj, final String actionType) {
        try {
            if (AppUtil.isConnectingToInternet(getActivity())) {
                if (!actionType.isEmpty())
                    ((AwardsAndMemberships) getActivity()).showProgress();
                new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.CREATE_USER_PROFILE, requestObj.toString(), "ADD_MEMBERSHIPS", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        if (!actionType.isEmpty()) {
                            ((AwardsAndMemberships) getActivity()).hideProgress();
                            parseResponse(successResponse);
                            if (actionType.equalsIgnoreCase("add"))
                                memberShipEditTxt.setText("");
                            else if (actionType.equalsIgnoreCase("save")) {
                                Intent in = new Intent();
                                getActivity().setResult(Activity.RESULT_OK, in);
                                getActivity().finish();
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        if (getActivity() != null && isAdded()) {
                            ((AwardsAndMemberships) getActivity()).hideProgress();
                        }
                        ((BaseActionBarActivity)getActivity()).displayErrorScreen(errorResponse);
                    }
                }).sendSinglePartRequest();
            } else {
                ((AwardsAndMemberships) getActivity()).hideProgress();
                ((AwardsAndMemberships) getActivity()).ShowSimpleDialog(null, getResources().getString(R.string.sNetworkError));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_awards_membership, menu);
    }

    public void parseResponse(String response) {
        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
                ((AwardsAndMemberships) getActivity()).hideProgress();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getResources().getString(R.string.we_unable_to_save));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });
                builder.show();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if (jsonObject.has(RestUtils.TAG_DATA)) {
                            JSONObject data = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                            if (data.has(RestUtils.TAG_MEM_HISTORY)) {
                                JSONArray membershipArray = data.optJSONArray(RestUtils.TAG_MEM_HISTORY);
                                int len = membershipArray.length();
                                for (int i = 0; i < len; i++) {
                                    JSONObject membershipObj = membershipArray.optJSONObject(i);
                                    ProfessionalMembershipInfo professionalMembershipInfo = new ProfessionalMembershipInfo();
                                    professionalMembershipInfo.setProf_mem_id(membershipObj.optInt(RestUtils.TAG_MEM_ID));
                                    professionalMembershipInfo.setMembership_name(membershipObj.optString(RestUtils.TITLE));
                                    professionalMembershipInfo.setType(membershipObj.optString(RestUtils.TAG_TYPE));
                                    realmManager.insertProfessionalMemData(realm, professionalMembershipInfo);
                                }
                            } else {
                                JSONArray associationArray = data.getJSONArray("associations");
                                int len = associationArray.length();
                                for (int i = 0; i < len; i++) {
                                    associationList.add(associationArray.get(i).toString());
                                    associationAdapter.notifyDataSetChanged();
                                }
                            }
                        }
//                        ((AwardsAndMemberships) getActivity()).hideProgress();

                    } else if(jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)){
                        ((AwardsAndMemberships) getActivity()).hideProgress();
                        if(jsonObject.optInt(RestUtils.TAG_ERROR_CODE)==106){
                            Toast.makeText(getActivity(),"Membership already exists",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE),Toast.LENGTH_SHORT).show();
                        }
                        //AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), getActivity());

                    }
                } catch (Exception e) {
                    if (response.contains("FileNotFoundException")) {
                        //AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), getActivity());

                    }
                    ((AwardsAndMemberships) getActivity()).hideProgress();
                    e.printStackTrace();
                }
            }

        }
    }

    public void clearUIFields() {
        memberShipEditTxt.setText("");
        AppConstants.add_membership = "";
    }

}
