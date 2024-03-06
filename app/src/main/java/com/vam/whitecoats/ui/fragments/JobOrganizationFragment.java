package com.vam.whitecoats.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobOrganizationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobOrganizationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String feedDataString;
    private int userId;
    private JSONObject completeFeedObj;
    private TextView aboutOrganization;
    private JSONObject feedInfoObj;
    private View latestCommentLayout;
    private RoundedImageView commented_user_img;
    private TextView commented_user_name;
    private TextView comment_text;
    private TextView contactDetails;

    public JobOrganizationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobOrganizationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobOrganizationFragment newInstance(String param1, int param2) {
        JobOrganizationFragment fragment = new JobOrganizationFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_organization, container, false);
        aboutOrganization = view.findViewById(R.id.tv_about_organization);
        contactDetails = view.findViewById(R.id.tv_job_contact);
        latestCommentLayout = view.findViewById(R.id.job_fullview_comment_layout);
        commented_user_img = latestCommentLayout.findViewById(R.id.commented_doc_pic);
        commented_user_name = latestCommentLayout.findViewById(R.id.commented_doc_name);
        comment_text = latestCommentLayout.findViewById(R.id.latest_comment);
        comment_text.setMaxLines(3);
        try {
            completeFeedObj = new JSONObject(feedDataString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (completeFeedObj != null) {
            feedInfoObj = completeFeedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
            if (feedInfoObj != null) {
                if (!feedInfoObj.isNull("about_us")) {
                    aboutOrganization.setText(feedInfoObj.optString("about_us"));
                }
                if (feedInfoObj.optBoolean("display_status")) {
                    contactDetails.setVisibility(View.VISIBLE);
                    contactDetails.setText(getString(R.string.contact_details_with_underline) + "\n");
                    contactDetails.append(feedInfoObj.optString("role_contact_email") + "\n");
                    contactDetails.append(feedInfoObj.optString("role_contact_number"));
                } else {
                    contactDetails.setVisibility(View.GONE);
                }
                JSONObject socialInteractionObj = feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                    latestCommentLayout.setVisibility(View.VISIBLE);
                    AppUtil.displayLatestCommentUI(getActivity(), userId, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), commented_user_img, commented_user_name, comment_text);
                } else {
                    latestCommentLayout.setVisibility(View.GONE);
                }
            }
        }
        return view;
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