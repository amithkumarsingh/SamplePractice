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
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.Directory;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.adapters.DirectoryAdapter;
import com.vam.whitecoats.ui.customviews.CustomRecycleView;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class Directory_Fragment extends Fragment {
    public static final String TAG = Directory_Fragment.class.getSimpleName();
    CustomRecycleView departmentRecyclerView;
    List<Directory> directoryList;
    int communityId;
    int communityMemCount;
    int channelid;
    int doctorId;
    JSONObject requestData = null;
    /**
     * For the first time we need to send lastRecordId as 0(zero). Afterwards
     * We get it from server response and saves to the variable in the next Activity i.e.
     * #DepartmentMembersActivity . Once we are going back from this activity,
     * we are resetting the value to 0(zero) again in onDestroy() method.
     */
    public static int lastRecordId = 0;
    private int channel_id;
    JSONObject dirJsonObject = null;
    public ProgressDialogFragement progress;
    public AlertDialog.Builder builder;
    private ProgressDialog mProgressDialog;
    private Realm realmInstance;
    private RealmManager realmManager;
    private AVLoadingIndicatorView aviDirFragment;

    public Directory_Fragment() {
        Log.i(TAG, "Directory_Fragment() Constructor");
    }

    public static Directory_Fragment newInstance(String param1, String param2) {
        Log.i(TAG, "Directory_Fragment newInstance");
        Directory_Fragment fragment = new Directory_Fragment();
        Bundle args = new Bundle();
        args.putString(RestUtils.TAG_FEED_DATA_OBJ, param1);
        args.putString(RestUtils.CHANNEL_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        if (getArguments() != null) {
            channel_id = Integer.parseInt(getArguments().getString("channel_id"));
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getString(R.string._onCreateView));
        View view = inflater.inflate(R.layout.directory__fragment, container, false);
        aviDirFragment=(AVLoadingIndicatorView)view.findViewById(R.id.aviInDirFragment);
        departmentRecyclerView = (CustomRecycleView) view.findViewById(R.id.deptRecycleview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        departmentRecyclerView.setLayoutManager(mLayoutManager);
        requestDirectoryService();
        if (directoryList != null)
            setupList();
        return view;
    }

    private void setupList() {
        Log.i(TAG, "setupList()");
        //View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.directory_list_header, null, false);
        DirectoryAdapter directoryAdapter = new DirectoryAdapter(getActivity(), directoryList, channel_id, doctorId);

        departmentRecyclerView.setAdapter(directoryAdapter);
        departmentRecyclerView.addItemDecoration(HeaderDecoration.with(departmentRecyclerView)
                .inflate(R.layout.directory_list_header)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(communityMemCount, getActivity()));

    }

    public void requestDirectoryService() {
        if(getActivity()!=null && isAdded()) {
            Log.i(TAG, "requestDirectoryService()");
            if (dirJsonObject == null) {
                try {
                    requestData = new JSONObject();
                    requestData.put(RestUtils.CHANNEL_ID, channel_id);
                    requestData.put(RestUtils.TAG_DOC_ID, doctorId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /**
                 * Get the members directory information
                 */
                if (AppUtil.isConnectingToInternet(getActivity())) {

                    Log.d(TAG, "Directory Service call");
                    //showProgress();
                    if(aviDirFragment!=null){
                        aviDirFragment.show();
                    }
                    new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.MEMBER_DIRECTORY, requestData.toString(), "DIRECTORY_FRAGMENT", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            //hideProgress();
                            if(aviDirFragment!=null){
                                aviDirFragment.hide();
                            }
                            try {
                                dirJsonObject = new JSONObject(successResponse);
                                if (dirJsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                    directoryList = new ArrayList<Directory>();
                                    JSONArray dirJsonArray = dirJsonObject.optJSONArray(RestUtils.TAG_DATA);
                                    communityMemCount = dirJsonObject.getInt("community_member_count");
                                    int size = dirJsonArray.length();
                                    for (int position = 0; position < size; position++) {
                                        JSONObject dirObj = dirJsonArray.getJSONObject(position);
                                        Directory directory = new Directory();
                                        directory.setDepartmentId(dirObj.getInt("department_id"));
                                        directory.setDepartmentName(dirObj.getString("department_name"));
                                        directory.setDeptMembersCount(dirObj.getInt("department_member_count"));
                                        directoryList.add(directory);
                                    }
                       /*
                        * Set the adapter
                        */
                                    setupList();
                                }
                                else if(dirJsonObject .optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR))
                                {
                                    String errorMsg="Something went wrong,please try again.";
                                    if( dirJsonObject.has(RestUtils.TAG_ERROR_MESSAGE)){
                                        errorMsg= dirJsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    }

                                    if( dirJsonObject.optString((RestUtils.TAG_ERROR_CODE)).equals("4045")) {
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

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            //hideProgress();
                            if(aviDirFragment!=null){
                                aviDirFragment.hide();
                            }
                            if(!(getActivity() instanceof DashboardActivity)) {
                                if(getActivity()!=null) {
                                    ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
                                }
                            }
                        }
                    }).sendSinglePartRequest();
                } else {
                    if(aviDirFragment!=null && aviDirFragment.isEnabled()){
                        aviDirFragment.hide();
                    }
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        lastRecordId = 0;
    }

    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    public synchronized void showProgress() {
        //if (progress == null && progress.getActivity() == null) {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
           // requestDirectoryService();
        }
    }
}
