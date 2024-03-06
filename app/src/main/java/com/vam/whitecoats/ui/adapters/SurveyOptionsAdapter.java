package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.FeedSurvey;
import com.vam.whitecoats.core.models.SurveyOption;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom RecyclerView with different Options for respective survey questions.
 * Sections are displayed in the same order they were added from JSON.
 * <p>
 * Created by satyasarathim on 25-05-2018.
 */

public class SurveyOptionsAdapter extends RecyclerView.Adapter<SurveyOptionsAdapter.DataObjectHolder> {
    Context mContext;
    FeedSurvey mFeedSurvey;
    boolean refreshView = false;
    int selectedPosition = -1;
    int mQuestionPosition;
    List optionsList;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    //Implement this interface to get current position of viewpager
    public interface OnOptionForgotListener {
        public void onOptionForgot(int position);
    }

    public SurveyOptionsAdapter(Context context, FeedSurvey feedSurvey, int questionPosition) {
        this.mContext = context;
        this.mFeedSurvey = feedSurvey;
        this.mQuestionPosition = questionPosition;
        optionsList = new ArrayList<>();
    }

    public void resetRefreshViewValue() {
        refreshView = false;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        RadioButton radioButton;
        CheckBox checkBox;
        TextView optionText;
        TextView optionPercent;
        View surveyRowLayout;

        public DataObjectHolder(View mItemView) {
            super(mItemView);
            radioButton = mItemView.findViewById(R.id.option_radio_btn);
            checkBox = mItemView.findViewById(R.id.option_chk_box);
            optionText = mItemView.findViewById(R.id.option_txt_vw);
            optionPercent = mItemView.findViewById(R.id.option_percent_txt);
            surveyRowLayout = mItemView;
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_option_item, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        SurveyOption surveyOption = mFeedSurvey.getQuestions().get(mQuestionPosition).getOptions().get(position);
        // setting row margin to 16dp
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 16);
        holder.surveyRowLayout.setLayoutParams(params);
        //Display survey option text
        holder.optionText.setVisibility(View.VISIBLE);
        holder.optionText.setText(surveyOption.getOption());
        // if view has to be refreshed based on item click, item background to be highlighted and
        // to be checked. Other items has to be grayed out and unchecked.
        if (refreshView) {
            if (!itemStateArray.get(position, false)) {
                //optionsList.clear();
//                FeedsSummary.questionsMap.put(mFeedSurvey.getQuestions().get(mQuestionPosition).getQuestionId(), optionsList);
                holder.radioButton.setChecked(false);
                holder.surveyRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.option_shape_grey));
            } else {
                holder.radioButton.setChecked(true);
                holder.surveyRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_text));
                // update question map here
                optionsList.clear();
                optionsList.add(surveyOption.getOptionId());

            }


        } else {
            if (surveyOption.isSelected()) {
                holder.surveyRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_text));
                if (mFeedSurvey.getQuestions().get(mQuestionPosition).isMultiSelect()) {
                    holder.checkBox.setVisibility(View.VISIBLE);
                    holder.checkBox.setChecked(true);
                    holder.radioButton.setVisibility(View.GONE);
                } else {
                    holder.checkBox.setVisibility(View.GONE);
                    holder.radioButton.setVisibility(View.VISIBLE);
                    holder.radioButton.setChecked(true);
                }
            } else {
                holder.surveyRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.option_shape_grey));
                if (mFeedSurvey.getQuestions().get(mQuestionPosition).isMultiSelect()) {
                    holder.checkBox.setVisibility(View.VISIBLE);
                    holder.radioButton.setVisibility(View.GONE);
                    holder.checkBox.setChecked(false);
                } else {
                    holder.checkBox.setVisibility(View.GONE);
                    holder.radioButton.setVisibility(View.VISIBLE);
                    holder.radioButton.setChecked(false);
                }
            }

            //If Survey is open (and) user not participated (and) user is eligible, then enable UI
            if (mFeedSurvey.isOpen() && !mFeedSurvey.isParticipated()) {
                if (mFeedSurvey.isEligible()) {
                    holder.surveyRowLayout.setEnabled(true);
                    // display all options except percent
                    holder.optionPercent.setVisibility(View.GONE);
                    if (mFeedSurvey.getQuestions().get(mQuestionPosition).isMultiSelect()) {
                        holder.checkBox.setVisibility(View.VISIBLE);
                    } else {
                        holder.radioButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.surveyRowLayout.setEnabled(false);
                    holder.optionPercent.setVisibility(View.VISIBLE);
                    if (mFeedSurvey.getQuestions().get(mQuestionPosition).isMultiSelect()) {
                        holder.checkBox.setVisibility(View.GONE);
                    } else {
                        holder.radioButton.setVisibility(View.GONE);
                    }
                }

                //submit button, closing time defined  in Activity #FeedsSummary

            } else if (mFeedSurvey.isParticipated()) { // If user already participated in survey
                holder.surveyRowLayout.setEnabled(false);
                if (mFeedSurvey.getQuestions().get(mQuestionPosition).isMultiSelect()) {
                    holder.checkBox.setVisibility(View.GONE);
                } else {
                    holder.radioButton.setVisibility(View.GONE);
                }
                if (!mFeedSurvey.isOpen() || (mFeedSurvey.isImmediate() && mFeedSurvey.isOpen())) {// If survey is closed
                    holder.optionPercent.setVisibility(View.VISIBLE);
                    holder.optionPercent.setText(surveyOption.getParticipatedPercent() + "%");
                    if (surveyOption.getParticipatedPercent() == mFeedSurvey.getQuestions().get(mQuestionPosition).getHighPercentage()) {
                        holder.optionPercent.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_dark_blue));
                    } else {
                        holder.optionPercent.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_light_blue));
                    }
                    // hide select options
                    holder.checkBox.setVisibility(View.GONE);
                    holder.radioButton.setVisibility(View.GONE);
                    //submit button, closing time, greet text defined  in Activity #FeedsSummary
                }
                if (!mFeedSurvey.isEligible()) { // If user not eligible to participate in survey
                    holder.surveyRowLayout.setEnabled(false);
                    holder.optionPercent.setVisibility(View.GONE);
                    // hide select options
                    holder.checkBox.setVisibility(View.GONE);
                    holder.radioButton.setVisibility(View.GONE);
                    //submit button, closing time, greet text, ineligible message defined  in Activity #FeedsSummary
                }


            } else if (!mFeedSurvey.isOpen()) { //If survey is closed
                // Disable item rows
                holder.surveyRowLayout.setEnabled(false);
                // Percentage
                holder.optionPercent.setVisibility(View.VISIBLE);
                holder.optionPercent.setText(surveyOption.getParticipatedPercent() + "%");
                if (surveyOption.getParticipatedPercent() == mFeedSurvey.getQuestions().get(mQuestionPosition).getHighPercentage()) {
                    holder.optionPercent.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_dark_blue));
                } else {
                    holder.optionPercent.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_light_blue));
                }
                // Hide select options
                holder.checkBox.setVisibility(View.GONE);
                holder.radioButton.setVisibility(View.GONE);
                //submit button, closing time defined  in Activity #FeedsSummary
            }
        }
        //since only one radio button is allowed to be selected, this condition un-checks previous selections
        holder.surveyRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean exists = true;
                if (!itemStateArray.get(position, false)) {
                    itemStateArray.put(position, true);
                    exists = false;
                } else {
                    itemStateArray.put(position, false);
                }
                //notifyDataSetChanged();
                if (mFeedSurvey.getQuestions().get(mQuestionPosition).isMultiSelect()) {
                    if (!itemStateArray.get(position, false)) {
                        optionsList.remove(Integer.valueOf(surveyOption.getOptionId()));
                        holder.checkBox.setChecked(false);
                        holder.surveyRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.option_shape_grey));
                    } else {
                        optionsList.add(surveyOption.getOptionId());
                        holder.checkBox.setChecked(true);
                        holder.surveyRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_text));
                    }
                    // update map
                    FeedsSummary.questionsMap.put(mFeedSurvey.getQuestions().get(mQuestionPosition).getQuestionId(), optionsList);
                } else {
                    selectedPosition = position;
                    itemStateArray.clear();
                    if (!exists) {
                        itemStateArray.put(position, true);
                    }
                    refreshView = true;
                    notifyDataSetChanged();
                    if(itemStateArray.size()>0){
                        //ArrayList tempList=new ArrayList();
                        //tempList.addAll(optionsList);
                        FeedsSummary.questionsMap.put(mFeedSurvey.getQuestions().get(mQuestionPosition).getQuestionId(), optionsList);
                    }else{
                        FeedsSummary.questionsMap.put(mFeedSurvey.getQuestions().get(mQuestionPosition).getQuestionId(), new ArrayList());
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeedSurvey.getQuestions().get(mQuestionPosition).getOptions().size();
    }

    public JSONObject getSurveyRequest(int feedId, int channelId, int userId) {
        JSONObject requestObj = null;
        try {
            requestObj = new JSONObject();
            requestObj.put(RestUtils.TAG_FEED_ID, feedId);
            requestObj.put(RestUtils.CHANNEL_ID, channelId);
            requestObj.put(RestUtils.TAG_USER_ID, userId);

            JSONArray questionArray = new JSONArray();
            int count = 0;
            for (int key : FeedsSummary.questionsMap.keySet()) {
                List options = FeedsSummary.questionsMap.get(key);
                if (options.size() == 0) { // if no options selected
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.no_survey_options_selected), Toast.LENGTH_SHORT).show();
                    if (mContext instanceof FeedsSummary)
                        ((FeedsSummary) mContext).onOptionForgot(count);
                    return null;
                } else {
                    JSONArray optionsArray = new JSONArray();
                    for (int index = 0; index < options.size(); index++) {
                        optionsArray.put(new JSONObject().put(RestUtils.KEY_OPTION_ID, options.get(index)));
                    }
                    questionArray.put(new JSONObject().put(RestUtils.KEY_QUESTION_ID, key).put(RestUtils.KEY_OPTIONS, optionsArray));
                    requestObj.put(RestUtils.KEY_FEED_SURVEY, new JSONObject().put(RestUtils.KEY_QUESTIONS, questionArray));
                }
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestObj;
    }
}
