package com.vam.whitecoats.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.TabsTagId;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class ProfessonalSkillingFeeds extends Fragment {
    private ContentFeedsFragment dashBoardFeeds;
    private int pageNum=0;
    private Realm realm;
    private RealmManager realmManager;

    public static ProfessonalSkillingFeeds newInstance(String s) {

        Bundle args = new Bundle();

        ProfessonalSkillingFeeds fragment = new ProfessonalSkillingFeeds();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dashBoardFeeds= ContentFeedsFragment.newInstance("fromProfessonalSkilling");

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, dashBoardFeeds );
        fragmentTransaction.addToBackStack(dashBoardFeeds.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.professional_skilling_fragment, null);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());

        requestForData();

        return rootView;
    }

    private void requestForData() {
        new VolleySinglePartStringRequest(getContext(), Request.Method.POST, RestApiConstants.GET_CONTENT_FEEDS, prepareGetRequestData(pageNum), "CONTENT_FEEDS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {

                Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();

            }
        }).sendSinglePartRequest();
    }

    private String prepareGetRequestData(int page_num) {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
            reqObj.put(RestUtils.PG_NUM,page_num);
            reqObj.put("tag_id",TabsTagId.PROFESSIONAL_SKILLING.geTagId() );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }

}
