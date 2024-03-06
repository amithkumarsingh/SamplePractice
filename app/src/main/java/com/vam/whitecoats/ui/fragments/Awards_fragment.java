package com.vam.whitecoats.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.ui.customviews.MonthYearPickerDialog;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ProfessionalMembershipInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.AwardsAndMemberships;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Awards_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Awards_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Awards_fragment extends Fragment implements OnTaskCompleted {
    public static final String TAG = Awards_fragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static Awards_fragment instance;
    private OnFragmentInteractionListener mListener;
    private EditText awardsTitle, presentedAt, yearEdtTxt;
    private TextView titleError;
    Button addAwardButton;
    private Realm realm;
    private RealmManager realmManager;
    int doctorID;

    public Awards_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Awards_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Awards_fragment newInstance(String param1, String param2) {
        Awards_fragment fragment = new Awards_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Awards_fragment getInstance() {
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
        setHasOptionsMenu(true); // To display the menu in fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_awards, container, false);
        awardsTitle = (EditText) view.findViewById(R.id.awards_title);
        presentedAt = (EditText) view.findViewById(R.id.presented_at);
        yearEdtTxt = (EditText) view.findViewById(R.id.year_awards);
        titleError = (TextView) view.findViewById(R.id.awards_title_error);
        titleError = (TextView) view.findViewById(R.id.awards_title_error);
        addAwardButton = (Button) view.findViewById(R.id.add_award_button);
        final String allowCharacterSet = "abcdefghijklmnopqrstuvwxyzABCEDEFGHIJKLMNOPQRSTUVWXYZ- ,'().&";
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dEst, int dStart, int dEnd) {
                for (int i = start; i < end; i++) {
                    if (source != null && !(allowCharacterSet.contains(("" + source.charAt(source.length() - 1))))) {
                        return source.subSequence(start, source.length() - 1);
                    }
                }
                return null;
            }
        };
        awardsTitle.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(500)});
        presentedAt.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(500)});
        yearEdtTxt.setInputType(InputType.TYPE_NULL);
        yearEdtTxt.requestFocus();
        yearEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity());
            }
        });
        awardsTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AppConstants.awards_title = s.toString();
            }
        });
        presentedAt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AppConstants.presented_at = s.toString();
            }
        });
        yearEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AppConstants.year_awarded = s.toString();
            }
        });
        addAwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!TextUtils.isEmpty(awardsTitle.getText().toString().trim())) {
                        addAwardServiceCall(getAwardRequestJson(), "add");
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.warning_empty_award_title), Toast.LENGTH_SHORT).show();
                    }
                    AppConstants.awards_title = "";
                    AppConstants.presented_at = "";
                    AppConstants.year_awarded = "";
                    awardsTitle.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        awardsTitle.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_awards) {
            try {
                if (!TextUtils.isEmpty(awardsTitle.getText().toString().trim())) {
//                    Memberships_fragment.getInstance().checkForServiceCall("");// If Membership value saved, then call create Membership service
                    addAwardServiceCall(getAwardRequestJson(), "save");
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.warning_empty_award_title), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private JSONObject getAwardRequestJson() {
        JSONObject requestObject = new JSONObject();
        try {
            JSONObject awardObj = new JSONObject();
            awardObj.put(RestUtils.TITLE, AppConstants.awards_title);
            awardObj.put(RestUtils.TAG_PRESENTED_AT, AppConstants.presented_at);
            awardObj.put(RestUtils.TAG_YEAR, AppConstants.year_awarded);
            awardObj.put(RestUtils.TAG_TYPE, "award");
            requestObject.put(RestUtils.TAG_USER_ID, doctorID);
            requestObject.put(RestUtils.TAG_AWARD_HISTORY, new JSONArray().put(awardObj));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject;
    }

    private void showDatePickerDialog(FragmentActivity activity) {

        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yearEdtTxt.setText("" + year);
            }
        });
        pd.show(activity.getFragmentManager(), "MonthYearPickerDialog");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_awards_membership, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public void checkForServiceCall(String actionType) {
        if (!TextUtils.isEmpty(AppConstants.awards_title)) {
            addAwardServiceCall(getAwardRequestJson(), actionType);
        }
    }

    private void addAwardServiceCall(JSONObject requestObj, final String actionType) {
        try {
            if (AppUtil.isConnectingToInternet(getActivity())) {
                if (!actionType.isEmpty())
                    ((AwardsAndMemberships) getActivity()).showProgress();
                new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.CREATE_USER_PROFILE, requestObj.toString(), "ADD_AWARDS", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        if (!actionType.isEmpty())
                            ((AwardsAndMemberships) getActivity()).hideProgress();
                        onTaskCompleted(successResponse);
                        if (actionType.equalsIgnoreCase("add")) {
                           clearUIFields();
                        } else if (actionType.equalsIgnoreCase("save")) {
                            Intent in = new Intent();
                            getActivity().setResult(Activity.RESULT_OK, in);
                            getActivity().finish();
                        }
                        else {
                            clearUIFields();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        ((AwardsAndMemberships) getActivity()).hideProgress();
                        ((AwardsAndMemberships)getActivity()).displayErrorScreen(errorResponse);
                    }
                }).sendSinglePartRequest();
            } else {
                ((AwardsAndMemberships) getActivity()).hideProgress();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(String response) {
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
                            if (data.has(RestUtils.TAG_AWARD_HISTORY)) {
                                JSONArray awardsArray = data.optJSONArray(RestUtils.TAG_AWARD_HISTORY);
                                int len = awardsArray.length();
                                for (int i = 0; i < len; i++) {
                                    JSONObject awardObj = awardsArray.optJSONObject(i);
                                    ProfessionalMembershipInfo professionalMembershipInfo = new ProfessionalMembershipInfo();
                                    professionalMembershipInfo.setAward_id(awardObj.optInt(RestUtils.TAG_AWARD_ID));
                                    professionalMembershipInfo.setAward_name(awardObj.optString(RestUtils.TITLE));
                                    professionalMembershipInfo.setAward_year(awardObj.optLong(RestUtils.TAG_YEAR));
                                    professionalMembershipInfo.setPresented_at(awardObj.optString(RestUtils.TAG_PRESENTED_AT));
                                    professionalMembershipInfo.setType(awardObj.optString(RestUtils.TAG_TYPE));
                                    realmManager.insertProfessionalMemData(realm, professionalMembershipInfo);
                                }
                            }

                        }
//                        ((AwardsAndMemberships) getActivity()).hideProgress();
                    } else {
                        ((AwardsAndMemberships) getActivity()).hideProgress();
                        AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), getActivity());

                    }
                } catch (Exception e) {
                    if (response.contains("FileNotFoundException")) {
                        AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), getActivity());

                    }
                    ((AwardsAndMemberships) getActivity()).hideProgress();
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void clearUIFields() {
        awardsTitle.setText("");
        presentedAt.setText("");
        yearEdtTxt.setText("");
        AppConstants.awards_title = "";
        AppConstants.presented_at = "";
        AppConstants.year_awarded = "";
    }
}
