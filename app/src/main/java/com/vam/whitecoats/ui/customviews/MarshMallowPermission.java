package com.vam.whitecoats.ui.customviews;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.widget.Toast;

import com.vam.whitecoats.constants.PermissionsConstants;
import com.vam.whitecoats.ui.activities.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;

/**
 * Created by tejaswini on 18-04-2016.
 */
public class MarshMallowPermission {


    Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissionForRecord() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    public boolean checkPermissionForCamera() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPermissionForPhone() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionForPhone() {
        if (shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
            Toast.makeText(activity, "Phone state permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PermissionsConstants.PHONE_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForRecord() {
        if (shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, PermissionsConstants.RECORD_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean requestPermissionForCamera(boolean isAudioPermissionReq) {
            int cameraPermission = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.CAMERA);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                int externalStoragePermissionImage = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES);
                int externalStoragePermissionVideo = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO);
                int externalStoragePermissionAudio = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO);

                if (externalStoragePermissionImage != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
                    listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_VIDEO);

                }
                if(isAudioPermissionReq){
                    if (externalStoragePermissionAudio != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_AUDIO);
                    }
                }
            } else {
                int externalStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PermissionsConstants.CAMERA_PERMISSION_REQUEST_CODE);
                return false;
        }
        return true;
    }

    public boolean requestPermissionForStorage(boolean isAudioPermissionReq) {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            int externalStoragePermissionImage = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES);
            int externalStoragePermissionVideo = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO);
            int externalStoragePermissionAudio = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO);

            if (externalStoragePermissionImage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_VIDEO);
            }
            if(isAudioPermissionReq) {
                if (externalStoragePermissionAudio != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_AUDIO);
                }
            }
        } else {
            int externalStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PermissionsConstants.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    public boolean requestPermissionForStorage(int requestCode,boolean isAudioPermissionReq) {

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            int externalStoragePermissionImage = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES);
            int externalStoragePermissionVideo = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO);
            int externalStoragePermissionAudio = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO);

            if (externalStoragePermissionImage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_VIDEO);

            }
            if(isAudioPermissionReq) {
                if (externalStoragePermissionAudio != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_AUDIO);
                }
            }
        } else {
            int externalStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), requestCode);
            return false;
        }

        return true;
    }

    public boolean requestPermissionForContacts() {
        int readPhoneState = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PermissionsConstants.PHONE_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    public boolean requestPhoneAndMediaPermissions() {
        int readPhoneState = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PermissionsConstants.REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public boolean checkPermissionsAccepted() {
        int readPhoneState = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        return readPhoneState == PackageManager.PERMISSION_GRANTED;
    }


    public boolean requestPermissionForLocation() {
        int LocationPermission = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (LocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PermissionsConstants.LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }


}
