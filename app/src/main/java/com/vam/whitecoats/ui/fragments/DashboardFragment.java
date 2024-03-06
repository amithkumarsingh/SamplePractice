package com.vam.whitecoats.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.ProfileViewActivity;

import java.io.File;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView dash_text_name,dash_text_phonum,dash_text_email,viewprofile,text_connects_count,text_caserooms_count,text_groups_count;
    TextView card_visit_others_specialty,card_visit_others_workplace,card_visit_others_location;
    View lineview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RelativeLayout card_area;
    private ImageView chat_img;

    ImageView dash_img_prof_pic;

    private OnFragmentInteractionListener mListener;
    private Realm realm;
    private RealmManager realmManager;

    BasicInfo basicInfo=new BasicInfo();


    /** Share Dialog variables **/
    private ListView share_list;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        realm = Realm.getDefaultInstance();
        realmManager=new RealmManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater1, ViewGroup container,
                             Bundle savedInstanceState) {
        View view          = inflater1.inflate(R.layout.fragment_dashboard, container, false);

        card_area             = (RelativeLayout)view.findViewById(R.id.card_area);
        chat_img              = (ImageView)view.findViewById(R.id.message_btn);
        chat_img.setVisibility(View.GONE);
        dash_text_name       =(TextView)view.findViewById(R.id.card_visit_other_name);
        dash_text_phonum     =(TextView)view.findViewById(R.id.card_contact_id);
        dash_text_email      =(TextView)view.findViewById(R.id.card_email_id);

        card_visit_others_specialty      =(TextView)view.findViewById(R.id.card_visit_others_specialty);
        card_visit_others_workplace      =(TextView)view.findViewById(R.id.card_visit_others_workplace);
        card_visit_others_location       =(TextView)view.findViewById(R.id.card_visit_others_location);


        dash_img_prof_pic     =(ImageView)view.findViewById(R.id.visit_card_pic_img);
        viewprofile           =(TextView)view.findViewById(R.id.view_complete_profile_tv);
        lineview              = view.findViewById(R.id.separator01);

        text_connects_count       = (TextView)view.findViewById(R.id.dash_text_cont_num);
        text_groups_count         = (TextView)view.findViewById(R.id.dash_text_cont_groups);
        text_caserooms_count      = (TextView)view.findViewById(R.id.dash_text_coserooms_num);


        lineview.setVisibility(View.GONE);
        viewprofile.setVisibility(View.GONE);
        RealmBasicInfo realmBasicInfo=null;
        try {
            realmBasicInfo = realmManager.getRealmBasicInfo(realm);

            int connects_count = realmManager.getMyContactsDB(realm).size();
            int groups_count  = realmManager.getQBGroupDialogsForCount(realm).size();
            int caseroom_count = realmManager.getCRQBDialogsForCount(realm).size();

            text_connects_count.setText(""+connects_count);
            text_groups_count.setText(""+groups_count);
            text_caserooms_count.setText(""+caseroom_count);

        }catch(NullPointerException e){
              e.printStackTrace();
        }


        if(realmBasicInfo != null){
            basicInfo.setFname(realmBasicInfo.getFname());
            basicInfo.setLname(realmBasicInfo.getLname());
            basicInfo.setEmail(realmBasicInfo.getEmail());
            basicInfo.setPhone_num(realmBasicInfo.getPhone_num());
            basicInfo.setSplty(realmBasicInfo.getSplty());
            basicInfo.setProfile_pic_path(realmBasicInfo.getProfile_pic_path());


            dash_text_name.setText("Dr. " + realmBasicInfo.getFname()+" "+ realmBasicInfo.getLname());
            dash_text_phonum.setText("91- " + realmBasicInfo.getPhone_num());
            dash_text_email.setText(realmBasicInfo.getEmail());
            card_visit_others_specialty.setText(realmBasicInfo.getSplty());
            ProfessionalInfo professionalInfo=realmManager.getProfessionalInfoOfShowoncard(realm);
            if(professionalInfo != null) {
                card_visit_others_workplace.setText(professionalInfo.getWorkplace());
                card_visit_others_location.setText(professionalInfo.getLocation());
            }


            try {
                if (realmBasicInfo.getPhone_num().equals("")) {
                    dash_text_phonum.setVisibility(View.INVISIBLE);
                    dash_text_phonum.getLayoutParams().height = 0;
                }
                if (!realmBasicInfo.getProfile_pic_path().equals("")) {
                    File imgFile = new File(realmBasicInfo.getProfile_pic_path());
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        if(myBitmap!=null) {
                            dash_img_prof_pic.setImageBitmap(myBitmap);
                        }
                    }
                }
            }catch(Exception e){
              e.printStackTrace();
            }


        }

        card_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ProfileViewActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        if(!realm.isClosed())
            realm.close();
    }

}
