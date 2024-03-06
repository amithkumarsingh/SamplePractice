package com.vam.whitecoats.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.adapters.MediaSectionRecycleViewDataAdapter;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;


public class Media_Fragment extends Fragment implements UiUpdateListener{

    public static final String TAG = Media_Fragment.class.getSimpleName();



    private String doc_id;

    private int channel_id;


    public ProgressDialogFragement progress;
    public AlertDialog.Builder builder;
    private ArrayList<JSONObject> mediaList=new ArrayList<>();
    private JSONObject mediaJsonObjectRequest=null;
    private int doctorId;
    private JSONArray mediaChannelJsonArrayrequest;
    private MediaSectionRecycleViewDataAdapter adapter;
    private ProgressDialog mProgressDialog;
    private Realm realmInstance;
    private RealmManager realmManager;
    private AVLoadingIndicatorView aviInMediaLib;
    public TextView txtview;


    public Media_Fragment() {
        // Required empty public constructor
    }


    public static Media_Fragment newInstance(String param1, String param2) {
        Log.i(TAG, "Media_Fragment newInstance");
        Media_Fragment fragment = new Media_Fragment();
        Bundle args = new Bundle();
        args.putString(RestUtils.TAG_DOC_ID, param1);
        args.putString(RestUtils.COMMUNITY_ID, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // For retaining the fragment on screen rotation
        //setRetainInstance(true);
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        if (getArguments() != null) {
            doc_id = getArguments().getString(RestUtils.TAG_DOC_ID);
            channel_id = Integer.parseInt(getArguments().getString(RestUtils.COMMUNITY_ID));
        }
        realmInstance = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        doctorId = realmManager.getDoc_id(realmInstance);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));
        CallbackCollectionManager.getInstance().registerListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, getString(R.string._onDetach));
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, getString(R.string._onDestroy));
        super.onDestroy();
        CallbackCollectionManager.getInstance().removeListener(this);
    }

    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    public synchronized void showProgress() {
        //if (progress == null && progress.getActivity() == null) {
        try {
            if(mProgressDialog!=null && !mProgressDialog.isShowing()){
                mProgressDialog.show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //}
    }


    public void ShowSimpleDialog(final String title, final String message) {
        try {
            builder = new AlertDialog.Builder(getActivity());
            AlertDialog alertDialog = builder.create();
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("OK", null);
            Log.d("Dialog result", "" + alertDialog.isShowing());
            if (alertDialog.isShowing()) {
                builder.create().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getString(R.string._onCreateView));
        View rootView = inflater.inflate(R.layout.fragment_media, null);
        RecyclerView my_recycler_view = (RecyclerView)rootView.findViewById(R.id.media_tab_list);
        txtview=rootView.findViewById(R.id.no_media_available);
        my_recycler_view.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        my_recycler_view.setLayoutManager(mLayoutManager);
        adapter = new MediaSectionRecycleViewDataAdapter(getActivity(), mediaList,channel_id);
        my_recycler_view.setAdapter(adapter);
        aviInMediaLib=(AVLoadingIndicatorView)rootView.findViewById(R.id.aviInMediaFragment);
        requestMediaService();
        return rootView;
    }

    public void requestMediaService() {
        if(mediaJsonObjectRequest==null || AppConstants.isMediaServiceRequired) {
            if (AppUtil.isConnectingToInternet(getActivity())) {
                mediaList.clear();
                AppConstants.isMediaServiceRequired=false;
                mediaJsonObjectRequest = new JSONObject();
                try {
                    mediaJsonObjectRequest.put(RestUtils.TAG_DOC_ID, doctorId);
                    mediaChannelJsonArrayrequest = new JSONArray();
                    mediaChannelJsonArrayrequest.put(channel_id);
                    mediaJsonObjectRequest.put(RestUtils.CHANNEL_IDS, mediaChannelJsonArrayrequest);
                    //showProgress();
                    if(aviInMediaLib!=null) {
                        aviInMediaLib.show();
                    }
                    new VolleySinglePartStringRequest(getActivity(), Request.Method.POST,RestApiConstants.MEDIA_COMPLETE_SERVICE,mediaJsonObjectRequest.toString(),"Media Fragment", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            if(aviInMediaLib!=null) {
                                aviInMediaLib.hide();
                            }
                            JSONObject mediaTabJsonResponseObject =null;
                            try {
                                 mediaTabJsonResponseObject = new JSONObject(successResponse);
                                if (mediaTabJsonResponseObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                    if (mediaTabJsonResponseObject.has(RestUtils.TAG_DATA)) {
                                        JSONArray mediaJsonArray = mediaTabJsonResponseObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray("mediaSections");
                                        if(mediaJsonArray.length()==0)
                                        {
                                            txtview.setVisibility(View.VISIBLE);
                                            txtview.setText(getString(R.string.no_data_available));
                                        }
                                        else {
                                            for (int i = 0; i < mediaJsonArray.length(); i++) {
                                                mediaList.add(mediaJsonArray.optJSONObject(i));
                                            }
                                        }
                                            if (adapter != null) {
                                                adapter.notifyDataSetChanged();
                                            }


                                    }

                                }
                                else if(mediaTabJsonResponseObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR))
                                {
                                    String errorMsg="Something went wrong,please try again.";
                                    if(mediaTabJsonResponseObject.has(RestUtils.TAG_ERROR_MESSAGE)){
                                        errorMsg=mediaTabJsonResponseObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    }

                                    if(mediaTabJsonResponseObject.optString((RestUtils.TAG_ERROR_CODE)).equals("4045")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage(errorMsg);
                                        builder.setCancelable(true);
                                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(!(getActivity() instanceof DashboardActivity)){
                                        getActivity().finish();
                                    }
                                            }
                                        }).create().show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            if(aviInMediaLib!=null) {
                                aviInMediaLib.hide();
                            }
                            if(!(getActivity() instanceof DashboardActivity)) {
                                if(getActivity()!=null) {
                                    ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
                                }
                            }
                        }
                    }).sendSinglePartRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                if(aviInMediaLib!=null && aviInMediaLib.isEnabled()){
                    aviInMediaLib.hide();
                }
            }
        }
    }


    @Override
    public void updateUI(int feedId, JSONObject socialInteractionObj) {

    }

    @Override
    public void notifyUIWithNewData(JSONObject newUpdate) {

    }

    @Override
    public void notifyUIWithDeleteFeed(int feedId, JSONObject deletedFeedObj) {
        if(AppUtil.isConnectingToInternet(getActivity())) {
            AppConstants.isMediaServiceRequired = true;
            mediaJsonObjectRequest = null;
            requestMediaService();
        }
    }

    @Override
    public void onBookmark(boolean isBookmarked, int feedID,boolean isAutoRefresh,JSONObject socialInteractionObj) {

    }

    @Override
    public void notifyUIWithFeedSurveyResponse(int feedId, JSONObject surveyResponse) {

    }

    @Override
    public void notifyUIWithFeedWebinarResponse(int feedId, JSONObject webinarRegisterResponse) {

    }

    @Override
    public void notifyUIWithJobApplyStatus(int feedId, JSONObject jobApplyResponse) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
          //  requestMediaService();
        }
    }

    @Override
    public void onPause() {
        adapter.pauseAudio();
        super.onPause();
    }
}
