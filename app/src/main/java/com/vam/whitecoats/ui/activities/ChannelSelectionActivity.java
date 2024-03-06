package com.vam.whitecoats.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.CommunityListAdapter;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;

public class ChannelSelectionActivity extends BaseActionBarActivity {

    private Realm realm;
    private RealmManager realmManager;
    private String networkChannelObj;
    private ArrayList<JSONObject> listOfChannels = new ArrayList<>();
    private ListView communityListView;
    private ViewGroup network_selection_layout;
    private String sharedTypeValue;
    private String sharedText;
    private Uri fileUri;
    ArrayList<Uri> multipleFileUris;
    private MySharedPref sharedPrefObj;
    private Boolean isRememberMe;
    private String security_token;
    private ViewGroup community_selection_layout;
    String fileType;
    private MarshMallowPermission marshMallowPermission;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet_modal);
        marshMallowPermission = new MarshMallowPermission(this);
        communityListView = (ListView) findViewById(R.id.communityList);
        network_selection_layout = (ViewGroup) findViewById(R.id.network_selection_layout);
        community_selection_layout = (ViewGroup) findViewById(R.id.community_selection_layout);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        sharedPrefObj = new MySharedPref(this);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        isRememberMe = sharedPrefObj.getPref(MySharedPref.STAY_LOGGED_IN, false);
        security_token = sharedPrefObj.getPref(MySharedPref.PREF_SESSION_TOKEN, "");
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type) && intent.getParcelableExtra(Intent.EXTRA_STREAM) == null) {
                // Handle text being sent
                sharedTypeValue = "text";
                fileType = "";
                sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            } else if (type.startsWith("image/")) {
                // Handle single image being sent
                sharedTypeValue = "singleFile";
                fileType = "image";
                fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            } else if (type.startsWith("video/")) {
                // Handle single image being sent
                sharedTypeValue = "singleFile";
                fileType = "video";
                fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            } else if (type.startsWith("audio/")) {
                // Handle single image being sent
                sharedTypeValue = "singleFile";
                fileType = "audio";
                fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            } else if (type.startsWith("application/")) {
                // Handle single image being sent
                sharedTypeValue = "singleFile";
                fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                fileType = "";
                String fileName = fileUri.toString();
                if (fileUri.toString().startsWith("content:")) {
                    fileName = AppUtil.getFileNameFromUri(ChannelSelectionActivity.this, fileUri);
                }
                if (!fileName.toLowerCase().endsWith(".pdf")) {
                    Toast.makeText(this, "File format not supported", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(this, "File format not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                // Handle multiple images being sent
                sharedTypeValue = "multipleFiles";
                fileType = "image";
                multipleFileUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            } else if (type.startsWith("audio/")) {
                // Handle multiple images being sent
                sharedTypeValue = "multipleFiles";
                fileType = "audio";
                multipleFileUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            } else if (type.contains("application/pdf")) {
                // Handle multiple images being sent
                sharedTypeValue = "multipleFiles";
                fileType = "";
                multipleFileUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                for (int i = 0; i < multipleFileUris.size(); i++) {
                    String fileName = multipleFileUris.get(i).toString();
                    if (fileName.startsWith("content:")) {
                        fileName = AppUtil.getFileNameFromUri(ChannelSelectionActivity.this, multipleFileUris.get(i));
                    }
                    if (!fileName.toLowerCase().endsWith(".pdf")) {
                        multipleFileUris.remove(i);
                    }
                }
            } else if (type.contains("application/*") || type.startsWith("*/")) {
                // Handle multiple images being sent
                sharedTypeValue = "multipleFiles";
                fileType = "";
                multipleFileUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                for (int i = 0; i < multipleFileUris.size(); i++) {
                    String fileName = multipleFileUris.get(i).toString();
                    String fileTypeFromURI = AppUtil.getMimeType(multipleFileUris.get(i).toString());
                    if (fileName.startsWith("content:")) {
                        fileTypeFromURI = AppUtil.getMimeTypeFromUri(ChannelSelectionActivity.this, multipleFileUris.get(i));
                    }
                    if (fileTypeFromURI != null && !fileTypeFromURI.contains("image") && !fileTypeFromURI.contains("audio")
                            && !fileTypeFromURI.contains("video") && !fileTypeFromURI.contains("application/pdf")) {
                        multipleFileUris.remove(i);
                    }
                }
            } else {
                Toast.makeText(this, "File format not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            // Handle other intents, such as being started from the home screen
            sharedTypeValue = "text";
        }
        if (!isRememberMe || security_token == null || security_token.length() == 0) {
            Intent i = new Intent(ChannelSelectionActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        JSONArray updatedChannelsList = realmManager.getChannelsListFromDB("listofChannels");
        if (updatedChannelsList != null) {
            for (int x = 0; x < updatedChannelsList.length(); x++) {
                JSONObject currentChannelObj = updatedChannelsList.optJSONObject(x);
                if (currentChannelObj != null && currentChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Network")) {
                    networkChannelObj = currentChannelObj.toString();
                }
                if (currentChannelObj != null && currentChannelObj.optBoolean(RestUtils.TAG_IS_ADMIN)) {
                    listOfChannels.add(currentChannelObj);
                }
            }
        }
        if (listOfChannels.size() == 0) {
            community_selection_layout.setVisibility(View.GONE);
            if (networkChannelObj != null) {
                processPostData(networkChannelObj.toString());
            } else {
                network_selection_layout.setVisibility(View.GONE);
                Toast.makeText(ChannelSelectionActivity.this, "No channels are available to post", Toast.LENGTH_SHORT).show();
            }
        }
        /*
         * Sort the list in alphabetical order
         */
        Collections.sort(listOfChannels, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                try {
                    return (lhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase().compareTo(rhs.getString(RestUtils.FEED_PROVIDER_NAME).toLowerCase()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        /*
         * Set adapter
         */
        CommunityListAdapter communityListAdapter = new CommunityListAdapter(this, listOfChannels);
        communityListView.setAdapter(communityListAdapter);
        communityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                processPostData(listOfChannels.get(position).toString());
            }
        });
        network_selection_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Send customized params when sharing to Network channel
                 */
                processPostData(networkChannelObj.toString());

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void processPostData(String channelObj) {
        if (sharedPrefObj != null && sharedPrefObj.getPref(MySharedPref.PREF_IS_USER_VERIFIED, 3) == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
            intent = new Intent(ChannelSelectionActivity.this, CreatePostActivity.class);
            intent.putExtra("SharedType", sharedTypeValue);
            intent.putExtra(RestUtils.NAVIGATATION, "ChannelSelectionActivity");
            if (networkChannelObj != null) {
                intent.putExtra(RestUtils.KEY_SELECTED_CHANNEL, channelObj);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (sharedTypeValue.equalsIgnoreCase("text")) {
                if (sharedText != null) {
                    intent.putExtra("SharedText", sharedText);
                    intent.putExtra("fileType", fileType);
                }
                startActivity(intent);
                finish();
            } else {
                if (!marshMallowPermission.requestPermissionForStorage(false)) {
                    if (AppConstants.neverAskAgain_Library) {
                        AppUtil.alertMessage(ChannelSelectionActivity.this, getString(R.string.label_files));
                    }
                } else {
                    handleFileSharing(intent);
                }
            }


        } else if (AppUtil.getUserVerifiedStatus() == 1) {
            AppUtil.AccessErrorPrompt(ChannelSelectionActivity.this, getString(R.string.mca_not_uploaded));
        } else if (AppUtil.getUserVerifiedStatus() == 2) {
            AppUtil.AccessErrorPrompt(ChannelSelectionActivity.this, "Your verification is in progress. You can share to WhiteCoats once your profile is verified");
        }
    }

    private void handleFileSharing(Intent intent) {
        if (sharedTypeValue.equalsIgnoreCase("singleFile")) {
            if (fileUri != null) {
                intent.putExtra("FileUri", fileUri.toString());
                intent.putExtra("fileType", fileType);
            }
        } else if (sharedTypeValue.equalsIgnoreCase("multipleFiles")) {
            if (multipleFileUris != null) {
                intent.putExtra("MultipleFileUri", multipleFileUris);
                intent.putExtra("fileType", fileType);
            }
        }
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (intent != null) {
                        handleFileSharing(intent);
                    }
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    } else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            AppConstants.neverAskAgain_Library = true;
                        }
                    }
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


}



