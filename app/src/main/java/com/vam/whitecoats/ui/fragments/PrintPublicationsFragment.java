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


public class PrintPublicationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static PrintPublicationsFragment instance;
    public static EditText printpub_title, printauthors_name;
    public static AutoCompleteTextView printjournal_name;
    private Button add_print_btn;

    private ArrayAdapter<String> journal_adapter;

    // Declare UI elements
    // private final BroadcastReceiver receiver = new MyBroadcastReceiver();


    // TODO: Rename and change types and number of parameters
    public static PrintPublicationsFragment newInstance(String param1, String param2) {
        PrintPublicationsFragment fragment = new PrintPublicationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static PrintPublicationsFragment getInstance() {
        if (instance == null)
            instance = new PrintPublicationsFragment();
        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        instance = this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_print_fragement, container, false);

        printpub_title = (EditText) view.findViewById(R.id.printpub_title);
        printauthors_name = (EditText) view.findViewById(R.id.printauthors_name);
        printjournal_name = (AutoCompleteTextView) view.findViewById(R.id.printjournal_name);
        add_print_btn = (Button) view.findViewById(R.id.print_add_btn);


        printjournal_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                journal_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, PublicationsActivity.journal_list);
                printjournal_name.setThreshold(1);
                printjournal_name.setAdapter(journal_adapter);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AppConstants.str_journals = s.toString();

            }
        });


        printpub_title.addTextChangedListener(new TextWatcher() {
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

        printauthors_name.addTextChangedListener(new TextWatcher() {
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


        add_print_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(AppConstants.str_title) || !TextUtils.isEmpty(AppConstants.str_authors) || !TextUtils.isEmpty(AppConstants.str_journals)) {
                    PublicationsInfo publicationsInfo = new PublicationsInfo();
                    publicationsInfo.setTitle(AppConstants.str_title == null ? "" : AppConstants.str_title);
                    publicationsInfo.setType("print");
                    publicationsInfo.setAuthors(AppConstants.str_authors == null ? "" : AppConstants.str_authors);
                    publicationsInfo.setJournal(AppConstants.str_journals == null ? "" : AppConstants.str_journals);
                    PublicationsActivity publicationsActivity = new PublicationsActivity();
                    if (SystemClock.elapsedRealtime() - PublicationsActivity.mLastClickTime < 2000) {
                    } else {
                        PublicationsActivity.mLastClickTime = SystemClock.elapsedRealtime();
                        publicationsActivity.serverCallForAdd(getActivity(), "add", publicationsInfo, "fragment");
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.publication_emptysave), Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        printpub_title.requestFocus();
    }

    public void clearUIFields() {
        printpub_title.setText("");
        printauthors_name.setText("");
        printjournal_name.setText("");
        AppConstants.str_authors = "";
        AppConstants.str_journals = "";
        AppConstants.str_title = "";
    }


}
