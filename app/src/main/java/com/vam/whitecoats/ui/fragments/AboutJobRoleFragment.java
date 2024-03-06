package com.vam.whitecoats.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.JobLocationSpecializationInfo;
import com.vam.whitecoats.ui.adapters.JobApplicationSpecialityLocationListAdapter;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.JobSpecializationLocationInterface;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutJobRoleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutJobRoleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private JSONObject completeFeedObj;

    // TODO: Rename and change types of parameters
    private String feedDataString;
    private int userId;
    private LinearLayout contentLayout, otherRequirements;
    private TextView jobTitle, jobOrg, jobRole, jobLocation, expCompensation, applicationDeadline, jobExp, jobSpecilization, jobQualification, focusAreas, otherCriteria1, otherCriteria2, otherCriteria3, jobDescTop;
    private JSONObject feedInfoObj;
    private View latestCommentLayout;
    private RoundedImageView commented_user_img;
    private TextView commented_user_name;
    private TextView comment_text;
    private TableRow focus_area_row;
    private TextView tvOtherRequirement;
    private ArrayList<JobLocationSpecializationInfo> specializationsArray=new ArrayList<>();
    private ArrayList<JobLocationSpecializationInfo> locationsArray=new ArrayList<>();
    private JobApplicationSpecialityLocationListAdapter jobApplicationSpecialityLocationListAdapter;


    public AboutJobRoleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutJobRoleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutJobRoleFragment newInstance(String param1, int param2) {
        AboutJobRoleFragment fragment = new AboutJobRoleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feedDataString = getArguments().getString(ARG_PARAM1);
            userId = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_job_role, container, false);
        try {
            completeFeedObj = new JSONObject(feedDataString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        contentLayout = view.findViewById(R.id.content_about_layout);
        jobDescTop = view.findViewById(R.id.tv_job_desc_top);
        jobTitle = view.findViewById(R.id.tv_job_title);
        jobOrg = view.findViewById(R.id.tv_organization);
        jobRole = view.findViewById(R.id.tv_job_role);
        jobLocation = view.findViewById(R.id.tv_job_location);
        expCompensation = view.findViewById(R.id.tv_compensation);
        applicationDeadline = view.findViewById(R.id.tv_deadline);
        jobExp = view.findViewById(R.id.tv_experience);
        jobSpecilization = view.findViewById(R.id.tv_specialization);
        jobQualification = view.findViewById(R.id.tv_qualification);
        focusAreas = view.findViewById(R.id.tv_focus_areas);
        otherCriteria1 = view.findViewById(R.id.tv_criteria_1);
        otherCriteria2 = view.findViewById(R.id.tv_criteria_2);
        otherCriteria3 = view.findViewById(R.id.tv_criteria_3);
        focus_area_row = view.findViewById(R.id.focus_area_row);
        tvOtherRequirement = view.findViewById(R.id.tv_other_requirement);


        latestCommentLayout = view.findViewById(R.id.job_fullview_comment_layout);
        commented_user_img = latestCommentLayout.findViewById(R.id.commented_doc_pic);
        commented_user_name = latestCommentLayout.findViewById(R.id.commented_doc_name);
        comment_text = latestCommentLayout.findViewById(R.id.latest_comment);


        comment_text.setMaxLines(3);
        if (completeFeedObj != null) {
            feedInfoObj = completeFeedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
            if (feedInfoObj != null) {

                if ((feedInfoObj.has("specializations") && feedInfoObj.optJSONArray("specializations") != null && feedInfoObj.optJSONArray("specializations").length()>0) || (feedInfoObj.has("locations") && feedInfoObj.optJSONArray("locations") != null && feedInfoObj.optJSONArray("locations").length()>0)) {
                    specializationsArray.clear();
                    locationsArray.clear();
                    for (int i = 0; i < feedInfoObj.optJSONArray("specializations").length(); i++) {
                        if (feedInfoObj.optJSONArray("specializations").optJSONObject(i) != null) {
                            JobLocationSpecializationInfo jobLocationSpecializationInfo=new JobLocationSpecializationInfo();
                            jobLocationSpecializationInfo.setName(feedInfoObj.optJSONArray("specializations").optJSONObject(i).optString("specialization"));
                            jobLocationSpecializationInfo.setId(feedInfoObj.optJSONArray("specializations").optJSONObject(i).optInt("specialization_id"));
                            jobLocationSpecializationInfo.setType("specializations");
                            jobLocationSpecializationInfo.setSelection(false);
                            specializationsArray.add(jobLocationSpecializationInfo);
                        }
                    }
                    for (int i = 0; i < feedInfoObj.optJSONArray("locations").length(); i++) {
                        if (feedInfoObj.optJSONArray("locations").optJSONObject(i) != null) {
                            JobLocationSpecializationInfo jobLocationSpecializationInfo=new JobLocationSpecializationInfo();
                            jobLocationSpecializationInfo.setName(feedInfoObj.optJSONArray("locations").optJSONObject(i).optString("location"));
                            jobLocationSpecializationInfo.setId(feedInfoObj.optJSONArray("locations").optJSONObject(i).optInt("location_id"));
                            jobLocationSpecializationInfo.setType("locations");
                            jobLocationSpecializationInfo.setSelection(false);
                            locationsArray.add(jobLocationSpecializationInfo);
                        }
                    }

                }

                Spannable feedDesc = AppUtil.linkifyHtml(feedInfoObj.optString(RestUtils.FEED_DESC).replaceAll("<img .*?/>", ""), Linkify.WEB_URLS);
                jobDescTop.setText(feedDesc);
                jobTitle.setText(feedInfoObj.optString(RestUtils.TITLE));
                jobOrg.setText(feedInfoObj.optString("organization"));
                jobRole.setText(feedInfoObj.optString("role_type"));
                if(locationsArray.size()>0){
                    jobLocation.setText(locationsArray.get(0).getName());
                    int locationsArraySize=0;
                    if(locationsArray.size()>1) {
                         locationsArraySize = locationsArray.size() - 1;
                    }else{
                         locationsArraySize = locationsArray.size();
                    }
                    if(locationsArray.size()>1) {
                        final SpannableStringBuilder sb = new SpannableStringBuilder("\n" + "+" +locationsArraySize+ " More");
                        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0, 167, 109));
                        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                        sb.setSpan(fcs, 0, sb.toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        sb.setSpan(bss, 0, sb.toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        jobLocation.append(sb);
                    }

                }else{
                jobLocation.setText(feedInfoObj.optString("location"));
                }
                expCompensation.setText(feedInfoObj.optString("compensation_range"));
                applicationDeadline.setText(DateUtils.convertLongtoDates(feedInfoObj.optLong("application_deadline")));

                if (feedInfoObj.has("max_experience") && !feedInfoObj.optString("max_experience").equalsIgnoreCase("0")) {
                    jobExp.setText(feedInfoObj.optString("min_experience") + "-" + feedInfoObj.optString("max_experience") + " Years");
                } else {
                    if (feedInfoObj.optString("min_experience").equalsIgnoreCase("0") ||
                            feedInfoObj.optString("min_experience").equalsIgnoreCase("1")) {
                        jobExp.setText(feedInfoObj.optString("min_experience") + " Year");
                    } else {
                        jobExp.setText(feedInfoObj.optString("min_experience") + " Years");
                    }
                }
                if(specializationsArray.size()>0) {
                    int specializationArraySize=0;
                    if(specializationsArray.size()>1) {
                        specializationArraySize = specializationsArray.size() - 1;
                    }else{
                        specializationArraySize = specializationsArray.size();
                    }
                    jobSpecilization.setText(specializationsArray.get(0).getName());
                    if (specializationsArray.size() > 1){
                        final SpannableStringBuilder sb = new SpannableStringBuilder("\n" + "+" + specializationArraySize + " More");
                        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0, 167, 109));
                        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                        sb.setSpan(fcs, 0, sb.toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        sb.setSpan(bss, 0, sb.toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        jobSpecilization.append(sb);
                }

                }else{
                    jobSpecilization.setText(feedInfoObj.optString("specialization"));
                }

                jobQualification.setText(feedInfoObj.optString("min_qualification"));

                /*Checking the focus area filed if available the showing the view or else hiding the view*/
                if (!feedInfoObj.optString("focus_areas").equalsIgnoreCase("")) {
                    focus_area_row.setVisibility(View.VISIBLE);
                } else {
                    focus_area_row.setVisibility(View.GONE);
                }
                focusAreas.setText(feedInfoObj.optString("focus_areas"));

                /*Checking the other criteria 1,2 and 3 filed if any of the filed is available then showing the header text
                 * other requirement or else hiding it.*/
                if (!feedInfoObj.optString("other_criteria_1").equalsIgnoreCase("") ||
                        !feedInfoObj.optString("other_criteria_2").equalsIgnoreCase("") ||
                        !feedInfoObj.optString("other_criteria_3").equalsIgnoreCase("")) {
                    tvOtherRequirement.setVisibility(View.VISIBLE);
                } else {
                    tvOtherRequirement.setVisibility(View.GONE);
                }
                otherCriteria1.setText(feedInfoObj.optString("other_criteria_1"));
                otherCriteria2.setText(feedInfoObj.optString("other_criteria_2"));
                otherCriteria3.setText(feedInfoObj.optString("other_criteria_3"));

                JSONObject socialInteractionObj = feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                    latestCommentLayout.setVisibility(View.VISIBLE);
                    AppUtil.displayLatestCommentUI(getActivity(), userId, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), commented_user_img, commented_user_name, comment_text);
                } else {
                    latestCommentLayout.setVisibility(View.GONE);
                }

            }

            jobLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(locationsArray.size()>1){
                        /*
                         * Sort the list in alphabetical order
                         */

                        displayDialogForLocationAndSpecialityList(locationsArray,"fromLocation");
                    }
                }
            });

            jobSpecilization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(specializationsArray.size()>1){
                        displayDialogForLocationAndSpecialityList(specializationsArray,"fromSpecilization");
                    }
                }
            });



        }
        return view;
    }

    private void displayDialogForLocationAndSpecialityList(ArrayList<JobLocationSpecializationInfo> locationsOrSpecialitiesArray, String from) {

        Collections.sort(locationsOrSpecialitiesArray, new Comparator() {
            @Override
            public int compare(Object obj1, Object obj2) {
                JobLocationSpecializationInfo jobLocationSpecializationInfo1 = (JobLocationSpecializationInfo)obj1;
                JobLocationSpecializationInfo jobLocationSpecializationInfo2 = (JobLocationSpecializationInfo)obj2;
                return jobLocationSpecializationInfo1.getName().compareToIgnoreCase(jobLocationSpecializationInfo2.getName());
            }
        });
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        final View sheetView = getActivity().getLayoutInflater().inflate(R.layout.about_job_bottom_sheet, null);
        final ListView specialityList = (ListView) sheetView.findViewById(R.id.job_speciality_location_list);
        TextView Preferred_location_speciality_text = (TextView) sheetView.findViewById(R.id.Preferred_location_speciality_text);
        Button done_btn = (Button) sheetView.findViewById(R.id.done_btn);
        if(from.equalsIgnoreCase("fromLocation")) {
            Preferred_location_speciality_text.setText("Location");
        }else{
            Preferred_location_speciality_text.setText("Specialization");
        }
        done_btn.setVisibility(View.GONE);

        jobApplicationSpecialityLocationListAdapter = new JobApplicationSpecialityLocationListAdapter(getActivity(),locationsOrSpecialitiesArray,"AboutJob", new JobSpecializationLocationInterface() {
            @Override
            public void OnCustomClick(int position, boolean b, JobLocationSpecializationInfo jobLocationSpecializationInfo) {

            }
        });
        specialityList.setAdapter(jobApplicationSpecialityLocationListAdapter);
        mBottomSheetDialog.setContentView(sheetView);
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) sheetView.getParent());
        //mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetDialog.show();
    }

    public void setLatestSocialInteractionData(JSONObject socialInteractionObj) {
        if (socialInteractionObj != null) {
            if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                latestCommentLayout.setVisibility(View.VISIBLE);
                AppUtil.displayLatestCommentUI(getActivity(), userId, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), commented_user_img, commented_user_name, comment_text);
            } else {
                latestCommentLayout.setVisibility(View.GONE);
            }
        }
    }
}