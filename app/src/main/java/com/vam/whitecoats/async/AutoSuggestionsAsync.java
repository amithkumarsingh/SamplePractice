package com.vam.whitecoats.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.ui.activities.AcademicActivity;
import com.vam.whitecoats.ui.activities.AwardsAndMemberships;
import com.vam.whitecoats.ui.activities.BasicProfileActivity;
import com.vam.whitecoats.ui.activities.EditPublicationsActivity;
import com.vam.whitecoats.ui.activities.MandatoryProfileInfo;
import com.vam.whitecoats.ui.activities.ProfessionalDetActivity;
import com.vam.whitecoats.ui.activities.ProfessionalMemActivity;
import com.vam.whitecoats.ui.activities.PublicationsActivity;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by swathim on 06-07-2015.
 */
public class AutoSuggestionsAsync extends AsyncTask<String, String, String> {


    
    Context mContext;
    String response;
    private AcademicActivity academicActivity;
    private MandatoryProfileInfo mandatoryProfileInfo;
    private PublicationsActivity publicationsActivity;
    private ProfessionalDetActivity professionalDetActivity;
    private EditPublicationsActivity editPublicationsActivity;
    private BasicProfileActivity basicProfileActivity;
    private ProfessionalMemActivity professionalMemActivity;
   // private CreateCaseroomActivity createCaseroomActivity;
    private AwardsAndMemberships awardsMembershipActivity;

    private OnTaskCompleted listener;


    protected ProgressDialogFragement progress;
    private ArrayList<String> searchKeys = new ArrayList<>();

    public AutoSuggestionsAsync(Context mContext, ArrayList<String> searchkeys) {
        this.mContext = mContext;
        if (mContext instanceof AcademicActivity) {
            academicActivity = (AcademicActivity) mContext;
        } else if (mContext instanceof MandatoryProfileInfo) {
            mandatoryProfileInfo = (MandatoryProfileInfo) mContext;

        } else if (mContext instanceof PublicationsActivity) {
            publicationsActivity = (PublicationsActivity) mContext;

        } else if (mContext instanceof ProfessionalDetActivity) {
            professionalDetActivity = (ProfessionalDetActivity) mContext;
        } else if (mContext instanceof EditPublicationsActivity) {
            editPublicationsActivity = (EditPublicationsActivity) mContext;
        } else if (mContext instanceof BasicProfileActivity) {
            basicProfileActivity = (BasicProfileActivity) mContext;
        } else if (mContext instanceof ProfessionalMemActivity) {
            professionalMemActivity = (ProfessionalMemActivity) mContext;
        } /*else if (mContext instanceof CreateCaseroomActivity) {
            createCaseroomActivity = (CreateCaseroomActivity) mContext;
        }*/

        listener = (OnTaskCompleted) mContext;
        this.searchKeys = searchkeys;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
    }
    public AutoSuggestionsAsync(Context mContext, ArrayList<String> searchKeys,OnTaskCompleted listener) {
        this.mContext = mContext;
        if (mContext instanceof AwardsAndMemberships) {
            awardsMembershipActivity = (AwardsAndMemberships) mContext;
        }
        this.listener = listener;
        this.searchKeys = searchKeys;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
    }

    @Override
    protected void onPreExecute() {
        if (mContext instanceof AcademicActivity) {

            academicActivity.showProgress();
        } else if (mContext instanceof MandatoryProfileInfo) {

            mandatoryProfileInfo.showProgress();

        } else if (mContext instanceof PublicationsActivity) {
            publicationsActivity.showProgress();
        } else if (mContext instanceof ProfessionalDetActivity) {

            professionalDetActivity.showProgress();

        } else if (mContext instanceof EditPublicationsActivity) {

            editPublicationsActivity.showProgress();

        } else if (mContext instanceof BasicProfileActivity) {

            basicProfileActivity.showProgress();

        } else if (mContext instanceof ProfessionalMemActivity) {

            professionalMemActivity.showProgress();

        } /*else if (mContext instanceof CreateCaseroomActivity) {

            createCaseroomActivity.showProgress();

        }*/ else if (mContext instanceof AwardsAndMemberships) {

            awardsMembershipActivity.showProgress();

        }
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            HttpClient client = new HttpClient(mContext,RestApiConstants.ACADEMIC_SUGGESTIONS);
            client.connectForSinglepartWithOutCookie(mContext);
            JSONObject object = new JSONObject();
            for (String key : searchKeys) {
                object.put(key, key);
            }
            String reqData = object.toString();
            client.addpara(RestUtils.TAG_REQ_DATA, reqData);
            response = client.getResponse();
            Log.v("auto_responce", response);
        } catch (SocketTimeoutException e) {
            response = "SocketTimeoutException";
            e.printStackTrace();
            return response;
        } catch (Exception e) {
            response = "Exception";
            e.printStackTrace();
            return response;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("auto response", "response" + s);
        if (mContext instanceof AcademicActivity) {

            academicActivity.hideProgress();
        } else if (mContext instanceof MandatoryProfileInfo) {

            mandatoryProfileInfo.hideProgress();

        } else if (mContext instanceof PublicationsActivity) {
            publicationsActivity.hideProgress();
        } else if (mContext instanceof ProfessionalDetActivity) {

            professionalDetActivity.hideProgress();

        } else if (mContext instanceof EditPublicationsActivity) {

            editPublicationsActivity.hideProgress();

        } else if (mContext instanceof BasicProfileActivity) {

            basicProfileActivity.hideProgress();

        } else if (mContext instanceof ProfessionalMemActivity) {

            professionalMemActivity.hideProgress();

        } /*else if (mContext instanceof CreateCaseroomActivity) {

            createCaseroomActivity.hideProgress();

        } */else if (mContext instanceof AwardsAndMemberships) {

            awardsMembershipActivity.hideProgress();

        }
        listener.onTaskCompleted(response);
        super.onPostExecute(s);
    }
}
