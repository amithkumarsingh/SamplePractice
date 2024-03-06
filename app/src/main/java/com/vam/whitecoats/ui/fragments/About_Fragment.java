package com.vam.whitecoats.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.soundcloud.android.crop.Crop;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ImageDownloaderTask;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.AboutInfo;
import com.vam.whitecoats.core.models.CommunityAdmin;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.activities.InactiveMembersCard;
import com.vam.whitecoats.ui.adapters.AdminAdapter;
import com.vam.whitecoats.ui.customviews.DynamicImageView;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FileHelper;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.ShowProfileCard;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import io.realm.Realm;

public class About_Fragment extends Fragment implements View.OnClickListener {

    public static final String TAG = About_Fragment.class.getSimpleName();
    ImageButton imageButton_about;

    private ArrayList<AboutInfo> aboutInfoArray;
    JSONObject jsonAboutObject = null;
    public ProgressDialogFragement progress;
    public AlertDialog.Builder builder;

    AdminAdapter adminAdapter;
    AboutInfo aboutInfos;
    MarshMallowPermission marshMallowPermission;
    private File folder, myDirectory;
    String selectedImagePath = "";
    private static Uri picUri;
    private int channel_id = 0;
    private Bitmap selected_img;
    DynamicImageView selected_image;
    LinearLayout communityNameEdit, communityContactDetails_edit, communityadminadd;
    private ArrayList<CommunityAdmin> adminsList;
    TextView tv_communityName, tv_communityDescp, tv_contactsValidation;
    ListView adminListview;
    LinearLayout admins_list_layout;

    RelativeLayout adminInfoLayout;
    TextView adminNameTxt, designationTxt, connectTxt;
    ImageView chatImgvw;
    RoundedImageView adminProfileImgvw;
    int doctorId;
    JSONObject requestAboutData;
    private String feed_provide_type;
    private String aboutURL;
    private RelativeLayout admins_layout;
    private ProgressDialog mProgressDialog;
    private Realm realmInstance;
    private RealmManager realmManager;
    private AVLoadingIndicatorView aviAboutFrag;
    private ViewGroup contactDetailsContainer;
    private ActivityResultLauncher<Intent> launchGalleryResults, launchCameraResults;


    public About_Fragment() {
        Log.i(TAG, "About_Fragment() Constructor");
    }

    public static About_Fragment newInstance(String param1, String param2, String mFeed_provider_type) {
        Log.i(TAG, "About_Fragment newInstance");
        About_Fragment fragment = new About_Fragment();
        Bundle args = new Bundle();
        args.putString(RestUtils.TAG_FEED_DATA_OBJ, param1);
        args.putString(RestUtils.CHANNEL_ID, param2);
        args.putString(RestUtils.TAG_FEED_PROVIDER_TYPE, mFeed_provider_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        try {

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage(getString(R.string.dlg_wait_please));
            marshMallowPermission = new MarshMallowPermission(getActivity());
            myDirectory = AppUtil.getExternalStoragePathFile(getActivity(), ".Whitecoats");
            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }
            folder = new File(myDirectory + "/feed_pic");
            aboutInfoArray = new ArrayList<AboutInfo>();
            if (!folder.exists()) {
                folder.mkdir();
            }
            if (getArguments() != null) {
                channel_id = Integer.parseInt(getArguments().getString(RestUtils.CHANNEL_ID));
                feed_provide_type = getArguments().getString(RestUtils.TAG_FEED_PROVIDER_TYPE);
            }
            realmInstance = Realm.getDefaultInstance();
            realmManager = new RealmManager(getActivity());
            doctorId = realmManager.getDoc_id(realmInstance);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, getString(R.string._onCreateView));
        View view = inflater.inflate(R.layout.about__fragment, container, false);
        // logo imageButton
        //adminListview = (ListView) view.findViewById(R.id.admin_list);
        admins_list_layout = (LinearLayout) view.findViewById(R.id.adminlinearlist);
        selected_image = (DynamicImageView) view.findViewById(R.id.logo_image);
        imageButton_about = (ImageButton) view.findViewById(R.id.camera_btn);
        tv_communityName = (TextView) view.findViewById(R.id.tv_community_Name);
        tv_communityDescp = (TextView) view.findViewById(R.id.tv_abtcommunity_descp);
        /*tv_communityWebsite = (TextView) view.findViewById(R.id.tv_commwebsite);
        tv_communityFacebook = (TextView) view.findViewById(R.id.tv_commfacebook);
        tv_commnitycontact = (TextView) view.findViewById(R.id.tv_commucall);
        tv_communitymail = (TextView) view.findViewById(R.id.tv_commumail);*/
        tv_contactsValidation = (TextView) view.findViewById(R.id.contactsValidationTxtvw);
        //tv_communitylocation = (TextView) view.findViewById(R.id.tv_commlocation);
        communityNameEdit = (LinearLayout) view.findViewById(R.id.communityname_edit);
        communityContactDetails_edit = (LinearLayout) view.findViewById(R.id.edit_community_contact);
        communityadminadd = (LinearLayout) view.findViewById(R.id.community_addadminadd);
        admins_layout = (RelativeLayout) view.findViewById(R.id.admins_layout);
        aviAboutFrag = (AVLoadingIndicatorView) view.findViewById(R.id.aviInAboutFragment);
        contactDetailsContainer = (ViewGroup) view.findViewById(R.id.contact_details_layout);
        // logo imageButton listener
        imageButton_about.setOnClickListener(this);
        /**
         * Set UI elements
         */
        if (feed_provide_type != null && feed_provide_type.equalsIgnoreCase(RestUtils.CONTENT)) {
            admins_layout.setVisibility(View.GONE);
            tv_contactsValidation.setVisibility(View.GONE);
            tv_communityName.setText("About");
        }
        requestAboutService();
        setupUI();
        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchCameraResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 1313
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        File latestFile = FileHelper.getLatestFilefromDir(getActivity().getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/feed_pic");
                        selectedImagePath = latestFile.getAbsolutePath();
                        selected_img = loadImage(selectedImagePath);
                        ExifInterface exif;
                        try {
                            exif = new ExifInterface(selectedImagePath);

                            int orientation = exif.getAttributeInt(
                                    ExifInterface.TAG_ORIENTATION, 0);
                            Log.d("EXIF", "Exif: " + orientation);
                            Matrix matrix = new Matrix();
                            if (orientation == 6) {
                                matrix.postRotate(90);
                                Log.d("EXIF", "Exif: " + orientation);
                            } else if (orientation == 3) {
                                matrix.postRotate(180);
                                Log.d("EXIF", "Exif: " + orientation);
                            } else if (orientation == 8) {
                                matrix.postRotate(270);
                                Log.d("EXIF", "Exif: " + orientation);
                            }
                            selected_img = Bitmap.createBitmap(selected_img, 0, 0,
                                    selected_img.getWidth(), selected_img.getHeight(), matrix,
                                    true);

                        } catch (IOException e) {
                            selected_img = BitmapFactory.decodeFile(selectedImagePath);
                            e.printStackTrace();
                        } catch (Exception e) {
                            selected_img = BitmapFactory.decodeFile(selectedImagePath);

                            e.printStackTrace();
                        }
                        //relative_preview.setVisibility(View.VISIBLE);
                        selected_image.setImageBitmap(selected_img);
                        /*selected_image.setScaleType(ImageView.ScaleType.FIT_XY);*/
                        //img_btns_layout.setVisibility(View.GONE);

                    }
                });
        launchGalleryResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code 1
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        Uri selectedImageUri = data.getData();
                        selectedImagePath = getPath(selectedImageUri, getActivity());
                        selected_img = loadImage(selectedImagePath);
                        //relative_preview.setVisibility(View.VISIBLE);
                        selected_image.setImageBitmap(selected_img);
                        /*selected_image.setScaleType(ImageView.ScaleType.FIT_XY);*/

                        //img_btns_layout.setVisibility(View.GONE);
                    }
                });
        //End
        return view;
    }

    public void setAdminListview() {
        admins_list_layout.removeAllViews();
        for (int i = 0; i < adminsList.size(); i++) {
            final CommunityAdmin admin = adminsList.get(i);
            LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View convertView = vi.inflate(R.layout.admin_list, null);
            //admins_list_layout = (LinearLayout) convertView.findViewById(R.id.adminlinearlist);
            adminNameTxt = (TextView) convertView.findViewById(R.id.admin_name_txt);
            designationTxt = (TextView) convertView.findViewById(R.id.admin_designation_txt);
            chatImgvw = (ImageView) convertView.findViewById(R.id.admin_chat_img);
            adminProfileImgvw = (RoundedImageView) convertView.findViewById(R.id.admin_profile_pic);
            connectTxt = (TextView) convertView.findViewById(R.id.admin_connect_text);
            adminInfoLayout = (RelativeLayout) convertView.findViewById(R.id.tile_layout);
            /**
             * Set the values and add to view
             */
            //designationTxt.setText(admin.getDesignation());
            designationTxt.setText(admin.getCommunityDesignation());
            admins_list_layout.addView(convertView);
            adminProfileImgvw.setTag(i);

            adminProfileImgvw.setOnClickListener(this);

            View seperatorView = new View(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1);
            lp.setMargins(150, 16, 0, 16);
            seperatorView.setLayoutParams(lp);
            seperatorView.setBackgroundColor(Color.parseColor("#26231f20"));
            /**
             * Connect status 0 - No connection
             *                  1 - Pending
             *                  2 - Waiting
             *                  3 - Connected
             *                  4 - Not in WhiteCoats
             */
            /**
             * Hide Connect and Chat Buttons in Admins List
             */
            connectTxt.setVisibility(View.GONE);
            chatImgvw.setVisibility(View.GONE);

            if (admin.getNetworkStatus() == 4) {
                adminProfileImgvw.setVisibility(View.GONE);
                adminInfoLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), InactiveMembersCard.class);
                        intent.putExtra(getActivity().getString(R.string.key_inactive_member), admin);
                        intent.putExtra(RestUtils.DEPT_OR_DESIG, aboutInfos.getTitle());
                        startActivity(intent);
                    }
                });
            }

            /**
             * If the user is Admin
             */
            if (doctorId == admin.getDoctorId()) {
                adminNameTxt.setText(getString(R.string.label_you));
                connectTxt.setVisibility(View.GONE);
                chatImgvw.setVisibility(View.GONE);
                adminProfileImgvw.setEnabled(false);
                adminInfoLayout.setClickable(false);
            } else {
                adminNameTxt.setText(admin.getFullName());
            }

            //setting profile image
            try {
                if (admin.getProfilePicName() != null && !admin.getProfilePicName().equals("")) {
                    convertView.getTag(admin.getDoctorId());
                    if (FileHelper.isFilePresent(admin.getProfilePicName(), "profile", getActivity())) {
                        adminProfileImgvw.setImageURI(Uri.fromFile(AppUtil.getExternalStoragePathFile(getActivity(), ".Whitecoats/Doc_Profiles/" + admin.getProfilePicName())));
                    } else {
                        new ImageDownloaderTask(adminProfileImgvw, getActivity(), new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String s) {
                                try {
                                    JSONObject responseJson = new JSONObject(s);
                                    String original_link = responseJson.optString(RestUtils.TAG_ORIGINAL_LINK);
                                    AppUtil.loadCircularImageUsingLib(getActivity(), original_link, adminProfileImgvw, R.drawable.default_profilepic);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).execute(admin.getProfilePicName().toString(), "profile", "");
                    }
                } else {
                    adminProfileImgvw.setImageURI(Uri.parse("android.resource://com.vam.whitecoats/" + R.drawable.default_profilepic));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i != 2) {
                admins_list_layout.addView(seperatorView);
            }
            if (i == 2) {
                break;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));

    }

    public void requestAboutService() {
        Log.i(TAG, "requestAboutService()");
        if (jsonAboutObject == null) {
            requestAboutData = new JSONObject();
            try {
                requestAboutData.put(RestUtils.TAG_DOC_ID, doctorId);
                requestAboutData.put(RestUtils.CHANNEL_ID, channel_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (feed_provide_type != null) {
                aboutURL = RestApiConstants.Channel_About;
            }
            /**
             * Call the service here.
             */
            if (AppUtil.isConnectingToInternet(getActivity())) {
                Log.d(TAG, "About Service call");
                //showProgress();
                if (aviAboutFrag != null) {
                    aviAboutFrag.show();
                }
                new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, aboutURL, requestAboutData.toString(), "ABOUT_SERVICE", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        //hideProgress();
                        if (getActivity() != null && isAdded()) {
                            if (aviAboutFrag != null) {
                                aviAboutFrag.hide();
                            }
                            try {
                                jsonAboutObject = new JSONObject(successResponse);
                                if (jsonAboutObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                    aboutInfos = new AboutInfo();
                                    JSONObject lObj = jsonAboutObject.optJSONObject(RestUtils.TAG_DATA);
                                    aboutInfos.setIs_admin(lObj.optBoolean(RestUtils.TAG_IS_ADMIN));
                                    //contact details
                                    JSONObject contactDetailsJson = lObj.optJSONObject(RestUtils.TAG_CONTACT_DETAILS);

                                    if (contactDetailsJson != null) {
                                        Iterator<String> iter = contactDetailsJson.keys();
                                        while (iter.hasNext()) {
                                            String key = iter.next();
                                            try {
                                                String value = contactDetailsJson.getString(key);
                                                TextView text_value = new TextView(getActivity());
                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                params.setMargins(0, 12, 0, 12);
                                                text_value.setLayoutParams(params);
                                                text_value.setTextSize(16);
                                                text_value.setMaxLines(1);
                                                text_value.setEllipsize(TextUtils.TruncateAt.END);

                                                View seperatorView = new View(getActivity());
                                                seperatorView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                                                seperatorView.setBackgroundColor(Color.parseColor("#1A231f20"));
                                                if (key.equalsIgnoreCase(RestUtils.TAG_LOCATION)) {
                                                    setContactDetailsAndIcon(value, R.drawable.ic_location, text_value, contactDetailsContainer, seperatorView);

                                                } else if (key.equalsIgnoreCase(RestUtils.TAG_EMAIL)) {
                                                    setContactDetailsAndIcon(value, R.drawable.ic_email, text_value, contactDetailsContainer, seperatorView);
                                                } else if (key.equalsIgnoreCase(RestUtils.TAG_WEBSITE)) {
                                                    setContactDetailsAndIcon(value, R.drawable.ic_website, text_value, contactDetailsContainer, seperatorView);
                                                } else if (key.equalsIgnoreCase(RestUtils.TAG_FACEBOOK_PAGE)) {
                                                    setContactDetailsAndIcon(value, R.drawable.ic_facebook, text_value, contactDetailsContainer, seperatorView);
                                                } else if (key.equalsIgnoreCase(RestUtils.TAG_CONTACT_NUMBER)) {
                                                    setContactDetailsAndIcon(value, R.drawable.ic_phone, text_value, contactDetailsContainer, seperatorView);
                                                } else {
                                                    if (value != null && !value.isEmpty()) {
                                                        text_value.setText(value);
                                                        contactDetailsContainer.addView(text_value);
                                                        contactDetailsContainer.addView(seperatorView);
                                                    }
                                                }
                                                text_value.setMovementMethod(LinkMovementMethod.getInstance());
                                                Linkify.addLinks(text_value, Linkify.ALL);
                                            } catch (JSONException e) {
                                                // Something went wrong!
                                            }
                                        }
                                    }
                                    aboutInfos.setLocation(contactDetailsJson.optString(RestUtils.TAG_LOCATION));
                                    aboutInfos.setWebsite(contactDetailsJson.optString(RestUtils.TAG_WEBSITE));
                                    aboutInfos.setFacebook_page(contactDetailsJson.optString(RestUtils.TAG_FACEBOOK_PAGE));
                                    aboutInfos.setContact_number(contactDetailsJson.optString(RestUtils.TAG_CONTACT_NUMBER));
                                    aboutInfos.setEmail(contactDetailsJson.optString(RestUtils.TAG_EMAIL));
                                    if (feed_provide_type != null) {
                                        aboutInfos.setChannelID(lObj.optString(RestUtils.CHANNEL_ID));
                                        aboutInfos.setChannelType(lObj.optString(RestUtils.CHANNEL_TYPE));
                                        aboutInfos.setTitle(lObj.optString(RestUtils.TAG_TITLE));
                                        aboutInfos.setLogo(lObj.optString(RestUtils.LOGO_URL));
                                        aboutInfos.setLogoSmall(lObj.optString(RestUtils.LOGO_URL_SMALL));
                                        aboutInfos.setDescription(lObj.optString(RestUtils.DESCRIPTION));
                                        String aboutLogoString = aboutInfos.getLogo();
                                        if (aboutLogoString != null && !aboutLogoString.isEmpty() && !aboutLogoString.equalsIgnoreCase("null") && (feed_provide_type != null && !feed_provide_type.equalsIgnoreCase(RestUtils.CONTENT))) {
                                            File myFile = AppUtil.getExternalStoragePathFile(getActivity(), ".Whitecoats/About_Community_Pic/" + aboutLogoString);
                                            if (myFile.exists()) {
                                                /*Picasso.with(getActivity())
                                                        .load(myFile).resize(500, 400).centerInside().placeholder(R.drawable.default_image_feed)
                                                        .error(R.drawable.default_image_feed)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                                        .into(selected_image);*/
                                                Glide.with(getActivity())
                                                        .load(myFile)
                                                        .apply(AppUtil.getRequestOptions(getActivity(), ContextCompat.getDrawable(getActivity(), R.drawable.default_image_feed)).override(500, 400)).centerInside()
                                                        .into(selected_image);
                                            } else {
                                                new ImageDownloaderTask(selected_image, getActivity(), new OnTaskCompleted() {
                                                    @Override
                                                    public void onTaskCompleted(String s) {
                                                        if (s != null) {
                                                            if (s.equals("SocketTimeoutException") || s.equals("Exception")) {
                                                                Log.i(TAG, "onTaskCompleted(String response) " + s);
                                                                hideProgress();
                                                                ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
                                                            } else {
                                                                try {
                                                                    JSONObject responseJson = new JSONObject(s);
                                                                    String original_link = responseJson.optString(RestUtils.TAG_ORIGINAL_LINK);
                                                                    String image_path = responseJson.optString(RestUtils.TAG_IMAGE_PATH);
                                                                    /*Picasso.with(getActivity())
                                                                            .load(original_link).resize(500, 400).centerInside().placeholder(R.drawable.default_image_feed)
                                                                            .error(R.drawable.default_image_feed)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                                                            .into(selected_image);*/
                                                                    Glide.with(getActivity())
                                                                            .load(original_link)
                                                                            .apply(AppUtil.getRequestOptions(getActivity(), ContextCompat.getDrawable(getActivity(), R.drawable.default_image_feed)).override(500, 400)).centerInside()
                                                                            .into(selected_image);
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }).execute(aboutLogoString, "about_pic", "");
                                            }
                                        }
                                        JSONArray adminsJsonArray = lObj.optJSONArray(RestUtils.TAG_ADMIN_LIST);
                                        int size = adminsJsonArray.length();
                                        adminsList = new ArrayList<>();
                                        for (int i = 0; i < size; i++) {
                                            JSONObject adminObject = adminsJsonArray.optJSONObject(i);
                                            CommunityAdmin admin = new CommunityAdmin();
                                            admin.setDoctorId(adminObject.optInt(RestUtils.TAG_DOC_ID));
                                            admin.setContactEmail(adminObject.optString(RestUtils.TAG_CNT_EMAIL));
                                            admin.setContactNumber(adminObject.optString(RestUtils.TAG_CNT_NUM));
                                            admin.setQbUserId(adminObject.optInt(RestUtils.TAG_QB_USER_ID));
                                            admin.setFullName(adminObject.optString(RestUtils.TAG_USER_FULL_NAME));
                                            admin.setLocation(adminObject.optString(RestUtils.TAG_LOCATION));
                                            admin.setWorkplace(adminObject.optString(RestUtils.TAG_WORKPLACE));
                                            admin.setSpeciality(adminObject.optString(RestUtils.TAG_SPLTY));
                                            admin.setSub_speciality(adminObject.optString(RestUtils.TAG_SUB_SPLTY, ""));
                                            admin.setDesignation(adminObject.optString(RestUtils.TAG_DESIGNATION));
                                            admin.setDegrees(adminObject.optString(RestUtils.TAG_DEGREES));
                                            admin.setProfilePicName(adminObject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                            admin.setNetworkStatus(adminObject.optInt(RestUtils.TAG_NETWORK_STATUS));
                                            admin.setUserSalutation(adminObject.optString(RestUtils.TAG_USER_SALUTAION));
                                            admin.setUserTypeId(adminObject.optInt(RestUtils.TAG_USER_TYPE_ID));
                                            admin.setCommunityDesignation((adminObject.optString(RestUtils.COMMUNITY_DESIGNATION).equals("null")) ? "" : adminObject.optString(RestUtils.COMMUNITY_DESIGNATION));
                                            adminsList.add(admin);
                                        }
                                        aboutInfos.setAdminList(adminsList);
                                        aboutInfoArray.add(aboutInfos);
                                    }
                                    setupUI();
                                } else if (jsonAboutObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                    String errorMsg = "Something went wrong,please try again.";
                                    if (jsonAboutObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                        errorMsg = jsonAboutObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                    }

                                    if (jsonAboutObject.optString((RestUtils.TAG_ERROR_CODE)).equals("4045")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setMessage(errorMsg);
                                        builder.setCancelable(true);
                                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (!(getActivity() instanceof DashboardActivity)) {
                                                    getActivity().finish();
                                                }
                                            }
                                        }).create().show();
                                    } else {
                                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        if (getActivity() != null && isAdded()) {
                            if (aviAboutFrag != null) {
                                aviAboutFrag.hide();
                            }
                        }
                        if (!(getActivity() instanceof DashboardActivity)) {
                            if (getActivity() != null) {
                                ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
                            }
                        }
                    }

                }).sendSinglePartRequest();
            } else {
                if (aviAboutFrag != null && aviAboutFrag.isEnabled()) {
                    aviAboutFrag.hide();
                }
            }
        }
    }

    private void setContactDetailsAndIcon(String value, int icon, TextView text_value, ViewGroup contactDetailsContainer, View seperator) {
        if (value != null && !value.isEmpty()) {
            text_value.setText(value);
            text_value.setCompoundDrawablesWithIntrinsicBounds(
                    icon, 0, 0, 0);
            text_value.setCompoundDrawablePadding(16);
            contactDetailsContainer.addView(text_value);
            contactDetailsContainer.addView(seperator);
        }
    }

    private void setupUI() {
        Log.i(TAG, "setupUI()");
        try {
            if (aboutInfos != null) {
                tv_communityName.setText(aboutInfos.getTitle());
                tv_communityDescp.setText(Html.fromHtml(aboutInfos.getDescription()));
                tv_contactsValidation.setText(getContactsValidationText(aboutInfos));

                if (aboutInfos.getIs_admin()) {
                    communityNameEdit.setVisibility(View.GONE);
                    communityContactDetails_edit.setVisibility(View.GONE);
                    communityadminadd.setVisibility(View.GONE);
                    imageButton_about.setVisibility(View.GONE);
                }

                communityNameEdit.setVisibility(View.GONE);
                communityContactDetails_edit.setVisibility(View.GONE);
                communityadminadd.setVisibility(View.GONE);
                imageButton_about.setVisibility(View.GONE);
                if (adminsList != null) {
                    setAdminListview();
                }
                admins_layout.setVisibility(View.GONE);
                tv_contactsValidation.setVisibility(View.GONE);
                if (aboutInfos.getLogo() != null && !aboutInfos.getLogo().isEmpty() && !aboutInfos.getLogo().equalsIgnoreCase("null")) {
                    if (feed_provide_type != null && feed_provide_type.equalsIgnoreCase(RestUtils.CONTENT)) {
                        /*Picasso.with(getActivity())
                                .load(aboutInfos.getLogo()).placeholder(R.drawable.default_image_feed)
                                .error(R.drawable.default_image_feed)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(selected_image);*/
                        AppUtil.loadImageUsingGlide(getActivity(), aboutInfos.getLogo().trim(), selected_image, R.drawable.default_image_feed);
                    } else {
                        File myFile = AppUtil.getExternalStoragePathFile(getActivity(), ".Whitecoats/About_Community_Pic/" + aboutInfos.getLogo());
                        if (myFile.exists()) {
                            /*Picasso.with(getActivity())
                                    .load(myFile).resize(500, 400).centerInside().placeholder(R.drawable.default_image_feed)
                                    .error(R.drawable.default_image_feed)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(selected_image);*/
                            Glide.with(getActivity())
                                    .load(myFile)
                                    .apply(AppUtil.getRequestOptions(getActivity(), ContextCompat.getDrawable(getActivity(), R.drawable.default_image_feed)).override(500, 400)).centerInside()
                                    .into(selected_image);
                        } else {
                            new ImageDownloaderTask(selected_image, getActivity(), new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String s) {
                                    try {
                                        JSONObject responseJson = new JSONObject(s);
                                        String original_link = responseJson.optString(RestUtils.TAG_ORIGINAL_LINK);
                                        String image_path = responseJson.optString(RestUtils.TAG_IMAGE_PATH);
                                        /*Picasso.with(getActivity())
                                                .load(original_link).resize(500, 400).centerInside().placeholder(R.drawable.default_image_feed)
                                                .error(R.drawable.default_image_feed)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                                .into(selected_image);*/
                                        Glide.with(getActivity())
                                                .load(original_link)
                                                .apply(AppUtil.getRequestOptions(getActivity(), ContextCompat.getDrawable(getActivity(), R.drawable.default_image_feed)).override(500, 400)).centerInside()
                                                .into(selected_image);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).execute(aboutInfos.getLogo(), "about_pic", "");
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //camera button logic
    @Override
    public void onClick(View v) {
//        Toast.makeText(getActivity(), "camera", Toast.LENGTH_SHORT).show();

        switch (v.getId()) {
            case R.id.camera_btn:
                selectImage();
                break;
            case R.id.admin_profile_pic:
                int position = (int) v.getTag();
                ContactsInfo contactsInfo = new ContactsInfo();
                contactsInfo.setDoc_id(adminsList.get(position).getDoctorId());
                contactsInfo.setName(adminsList.get(position).getFullName());
                contactsInfo.setWorkplace(adminsList.get(position).getWorkplace());
                contactsInfo.setSpeciality(adminsList.get(position).getSpeciality());
                contactsInfo.setSubSpeciality(adminsList.get(position).getSub_speciality());
                contactsInfo.setDegree(adminsList.get(position).getDegrees());
                contactsInfo.setEmail(adminsList.get(position).getContactEmail());
                contactsInfo.setNetworkStatus("" + adminsList.get(position).getNetworkStatus());
                contactsInfo.setPic_data(adminsList.get(position).getProfilePicName());
                contactsInfo.setPic_name(adminsList.get(position).getProfilePicName());
                contactsInfo.setQb_userid(adminsList.get(position).getQbUserId());
                contactsInfo.setLocation(adminsList.get(position).getLocation());
                contactsInfo.setPhno(adminsList.get(position).getContactNumber());
                if (contactsInfo != null) {
                    ShowProfileCard cardInfo = new ShowProfileCard(getActivity(), contactsInfo);
                    cardInfo.popupProfileInfo();
                    //new ShowCard(getActivity(), contactsInfo).showMyConnectCard();
                }
                break;
            default:
                break;
        }
        //selectImage();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    if (!marshMallowPermission.requestPermissionForCamera(false)) {
                    } else {
                        cameraClick();
                    }

                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    launchGalleryResults.launch(Intent.createChooser(intent, "Select File"));

                }

            }
        });
        builder.show();
    }

    private void cameraClick() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        Date now = new Date();
        final String fileName = "community_logo" + "_" + formatter.format(now) + ".jpg";
        File f = new File(folder, fileName);
        selectedImagePath = f.getAbsolutePath();
        picUri = Uri.fromFile(f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        launchCameraResults.launch(intent);

    }

    private String getContactsValidationText(AboutInfo aboutInfo) {
        Log.i(TAG, "getContactsValidationText(AboutInfo aboutInfo)");
        String[] contactFields = new String[]{"phone", "email", "website", "facebook link", "location"};
        String finalText = "Provide ";

        if (aboutInfo.getLocation() != null && !aboutInfo.getLocation().isEmpty()) {
            //tv_communitylocation.setVisibility(View.VISIBLE);
            contactFields = ArrayUtils.removeElement(contactFields, RestUtils.TAG_LOCATION);
        } else {
            //tv_communitylocation.setVisibility(View.GONE);
        }
        if (aboutInfo.getContact_number().length() > 1) {
            //tv_commnitycontact.setVisibility(View.VISIBLE);
            contactFields = ArrayUtils.removeElement(contactFields, "phone");

        } else {
            //tv_commnitycontact.setVisibility(View.GONE);
        }
        if (aboutInfo.getEmail().length() > 0) {
            //tv_communitymail.setVisibility(View.VISIBLE);
            contactFields = ArrayUtils.removeElement(contactFields, "email");
        } else {
            //tv_communitymail.setVisibility(View.GONE);
        }
        if (aboutInfo.getWebsite().length() > 0) {
            //tv_communityWebsite.setVisibility(View.VISIBLE);
            contactFields = ArrayUtils.removeElement(contactFields, "website");
        } else {
            //tv_communityWebsite.setVisibility(View.GONE);
        }
        if (aboutInfo.getFacebook_page().length() > 0) {
            //tv_communityFacebook.setVisibility(View.VISIBLE);
            contactFields = ArrayUtils.removeElement(contactFields, "facebook link");
        } else {
            //tv_communityFacebook.setVisibility(View.GONE);
        }

        int length = contactFields.length;
        /**
         * If length is zero, return no validation text.
         * Else we collect the unvalidated text and displays
         * to the user.
         */
        if (length == 0) {
            finalText = "";
        } else {
            for (int i = 0; i < length; i++) {
                finalText = finalText + contactFields[i];
                if (i != (length - 1))
                    finalText += ",";
            }
            int lastIndexOfComma = finalText.lastIndexOf(',');
            if (lastIndexOfComma != -1)
                finalText = finalText.substring(0, lastIndexOfComma) + " and " + finalText.substring(lastIndexOfComma + 1);
        }
        return finalText;
    }

    private Bitmap loadImage(String imgPath) {
        BitmapFactory.Options options;
        try {
            options = new BitmapFactory.Options();
            options.inSampleSize = 4;// 1/4 of origin image size from width and height
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPath(Uri uri, Activity activity) {
        try {
            String[] projection = {MediaStore.MediaColumns.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
    }

    public void ShowSimpleDialog(final String title, final String message) {
        try {
            builder = new AlertDialog.Builder(getActivity());
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("OK", null);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, getString(R.string._onDetach));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            //  requestAboutService();
        }
    }
}
