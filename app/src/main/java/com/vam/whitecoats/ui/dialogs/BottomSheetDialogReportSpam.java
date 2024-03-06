package com.vam.whitecoats.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.DashBoardSpamReportListModel;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.activities.LoginActivity;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.adapters.DashBoardSpamReportListAdapter;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.vam.whitecoats.viewmodel.UserReviewViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BottomSheetDialogReportSpam extends BottomSheetDialogFragment {

    private ArrayList<DashBoardSpamReportListModel> dashBoardSpamReportListModel;
    private UserReviewViewModel userReviewViewModel;
    private Integer feedId, docId, otherDocId=0, reportFlag;
    private String report = "",callingFrom="ReportFeed";
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState) {
        View sheetView = inflater.inflate(R.layout.dashboard_bottom_sheet_modal_report_spam,
                container, false);
        activity = requireActivity();
        if (getArguments() != null) {
            feedId = getArguments().getInt("feedId");
            docId = getArguments().getInt("docId");
            if (getArguments().containsKey("callingFrom")) {
                otherDocId = getArguments().getInt("otherDocId");
                reportFlag = 1;
                callingFrom=getArguments().getString("callingFrom");
            } else {
                reportFlag = 2;
            }
        }

        userReviewViewModel = ViewModelProviders.of(this).get(UserReviewViewModel.class);

        RelativeLayout bottomSheetCloseButton = (RelativeLayout) sheetView.findViewById(R.id.bottomSheetCloseButton);
        TextView headerText = sheetView.findViewById(R.id.headerText);
        TextView heading = sheetView.findViewById(R.id.headingText);
        TextView headingContent = sheetView.findViewById(R.id.headingContent);
        final ListView dashBoardReportSpamListView = (ListView) sheetView.findViewById(R.id.communityList);
        headerText.setText("Report");

        if (callingFrom.equalsIgnoreCase("ReportOtherProfileUser")) {
            heading.setText("What do you want to do?");
            headingContent.setVisibility(View.GONE);
            dashBoardSpamReportListModel = new ArrayList<>();
            DashBoardSpamReportListModel temp = new DashBoardSpamReportListModel();
            temp.setReportSpamButton("Report content on user profile");
            dashBoardSpamReportListModel.add(temp);

            temp = new DashBoardSpamReportListModel();
            temp.setReportSpamButton("Report post or comment");
            dashBoardSpamReportListModel.add(temp);

        } else {
            dashBoardSpamReportListModel = new ArrayList<>();
            DashBoardSpamReportListModel temp = new DashBoardSpamReportListModel();
            temp.setReportSpamButton("Suspicious,spam,or fake");
            dashBoardSpamReportListModel.add(temp);

            temp = new DashBoardSpamReportListModel();
            temp.setReportSpamButton("Harassment or hateful content");
            dashBoardSpamReportListModel.add(temp);

            temp = new DashBoardSpamReportListModel();
            temp.setReportSpamButton("Violence or physical harm");
            dashBoardSpamReportListModel.add(temp);

            temp = new DashBoardSpamReportListModel();
            temp.setReportSpamButton("Pornography content");
            dashBoardSpamReportListModel.add(temp);

            temp = new DashBoardSpamReportListModel();
            temp.setReportSpamButton("Intellectual property infringement or defamation");
            dashBoardSpamReportListModel.add(temp);
        }

        /*
         * Set adapter
         */
        DashBoardSpamReportListAdapter dashBoardListAdapter = new DashBoardSpamReportListAdapter(getActivity(), dashBoardSpamReportListModel);
        dashBoardReportSpamListView.setAdapter(dashBoardListAdapter);
        dashBoardReportSpamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                report = dashBoardSpamReportListModel.get(position).getReportSpamButton();
                    JSONObject resObj = null;
                    try {
                        resObj = new JSONObject();
                        resObj.put("feedId", feedId);
                        resObj.put("docId", docId);
                        resObj.put("report", report);
                        resObj.put("otherDocId", otherDocId);
                        resObj.put("reportFlag", reportFlag);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    new VolleySinglePartStringRequest(requireActivity(), Request.Method.POST, RestApiConstants.saveUserReportFeed, resObj.toString(), "UserReportFeed", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            try {

                                JSONObject jsonObject = new JSONObject(successResponse);
                                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                                    Dialog dialog = new Dialog(activity);
                                    dialog.setContentView(R.layout.report_spam_dialog);
                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dialog.setCancelable(false);
                                    RelativeLayout bottomSheetCloseButton = dialog.findViewById(R.id.bottomSheetCloseButton);
                                    bottomSheetCloseButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {

                        }
                    }).sendSinglePartRequest();
                dismiss();
            }
        });


        bottomSheetCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return sheetView;
    }
}
