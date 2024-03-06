package com.vam.whitecoats.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;

/**
 * A simple {@link DialogFragment} subclass.
 *
 */
public class SpecialityDialogFragment extends DialogFragment {
    public static final String TAG = SpecialityDialogFragment.class.getSimpleName();
    private String navigationKey;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogListItemSelect(String selectedItem);
    }
    NoticeDialogListener mListener;
    CharSequence[] specialities;

    public SpecialityDialogFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param specialities Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static SpecialityDialogFragment newInstance(CharSequence[] specialities,String navigation) {
        SpecialityDialogFragment fragment = new SpecialityDialogFragment();
        Bundle args = new Bundle();
        args.putCharSequenceArray("Key_Speciality_List",specialities);
        args.putString("navigation_key",navigation);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG,"onCreateDialog()");
        if (getArguments() != null) {
            specialities = getArguments().getCharSequenceArray("Key_Speciality_List");
            navigationKey=getArguments().getString("navigation_key");
        }
        Log.i(TAG,"Specialities : "+specialities);
            String title="I am a";
        if(navigationKey!=null && navigationKey.equalsIgnoreCase("Network_search_specialities")){
            title="";
        }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title).
                    setItems(specialities, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(!navigationKey.equalsIgnoreCase("fromAboutJob")) {
                                mListener.onDialogListItemSelect(specialities[which].toString());
                            }
                        }
                    });

        return builder.create();
    }
    public void setUpListener(NoticeDialogListener noticeDialogListener){
        mListener=noticeDialogListener;

    }
}


