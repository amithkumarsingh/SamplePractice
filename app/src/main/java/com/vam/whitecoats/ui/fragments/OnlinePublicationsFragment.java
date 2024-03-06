package com.vam.whitecoats.ui.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.core.models.PublicationsInfo;
import com.vam.whitecoats.ui.activities.PublicationsActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OnlinePublicationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlinePublicationsFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private static OnlinePublicationsFragment instance;
    public static EditText edt_title,edt_authors,edt_webpage_link;
    public static AutoCompleteTextView auto_journals;
    private ArrayAdapter<String> online_journal_adapter;
    private Button add_online_btn;

    private PublicationsInfo publicationsInfo;


    // Declare UI elements
//    private final BroadcastReceiver online_receiver = new MyOnlineBroadcastReceiver();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnlinePublicationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnlinePublicationsFragment newInstance(String param1, String param2) {
        OnlinePublicationsFragment fragment = new OnlinePublicationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static OnlinePublicationsFragment getInstance() {
        if (instance == null)
            instance = new OnlinePublicationsFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view         = inflater.inflate(R.layout.fragment_online_publications, container, false);

        edt_title         = (EditText)view.findViewById(R.id.onlinePub_title);
        edt_authors       = (EditText)view.findViewById(R.id.onlineauthors_name);
        auto_journals     = (AutoCompleteTextView)view.findViewById(R.id.onlinejournal_name);
        edt_webpage_link  = (EditText)view.findViewById(R.id.webpage_link);
        add_online_btn    = (Button)view.findViewById(R.id.online_add_btn);


        auto_journals.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                online_journal_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PublicationsActivity.journal_list);
                auto_journals.setThreshold(1);
                auto_journals.setAdapter(online_journal_adapter);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AppConstants.str_journals = s.toString();
            }
        });

        edt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               AppConstants.str_title = s.toString();
            }
        });

        edt_authors.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                AppConstants.str_authors = s.toString();
            }
        });
        edt_webpage_link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AppConstants.str_webpage = s.toString();
            }
        });

        add_online_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(AppConstants.str_title) || !TextUtils.isEmpty(AppConstants.str_authors)|| !TextUtils.isEmpty(AppConstants.str_journals)||!TextUtils.isEmpty(AppConstants.str_webpage)){
                    publicationsInfo = new PublicationsInfo();
                    publicationsInfo.setTitle(AppConstants.str_title);
                    publicationsInfo.setType("online");
                    publicationsInfo.setAuthors(AppConstants.str_authors);
                    publicationsInfo.setJournal(AppConstants.str_journals);
                    publicationsInfo.setWeb_page(AppConstants.str_webpage);
                    PublicationsActivity publicationsActivity=new PublicationsActivity();
                    if (SystemClock.elapsedRealtime() - PublicationsActivity.mLastClickTime < 2000) {
                    } else {
                        PublicationsActivity.mLastClickTime = SystemClock.elapsedRealtime();
                        publicationsActivity.serverCallForAdd(getActivity(), "add", publicationsInfo, "fragment");
                    }
                }
                else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.publication_emptysave), Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();
        edt_title.requestFocus();
    }
    public void clearUIFields(){
        auto_journals.setText("");
        edt_title.setText("");
        edt_authors.setText("");
        edt_webpage_link.setText("");
        AppConstants.str_authors = "";
        AppConstants.str_journals = "";
        AppConstants.str_title = "";
        AppConstants.str_webpage = "";
    }



}
