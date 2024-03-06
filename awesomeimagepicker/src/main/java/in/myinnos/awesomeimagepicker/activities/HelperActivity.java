package in.myinnos.awesomeimagepicker.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;

import in.myinnos.awesomeimagepicker.R;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;


/**
 * Created by MyInnos on 03-11-2016.
 */
public class HelperActivity extends Activity {
    protected View view;

    private final int maxLines = 4;
    private final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private final String[] permissionsRequiredSDK33Higher = new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO};


    protected void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(HelperActivity.this, permissionsRequiredSDK33Higher[0]) == PackageManager.PERMISSION_GRANTED) {
                permissionGranted();
            } else {
                ActivityCompat.requestPermissions(HelperActivity.this, permissionsRequiredSDK33Higher, ConstantsCustomGallery.PERMISSION_REQUEST_CODE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                permissionGranted();
            } else {
                ActivityCompat.requestPermissions(this, permissions, ConstantsCustomGallery.PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                showRequestPermissionRationale();
            } else {
                showAppPermissionSettings();
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showRequestPermissionRationale();
            } else {
                showAppPermissionSettings();
            }
        }

    }

    private void showRequestPermissionRationale() {
        Snackbar snackbar = Snackbar.make(
                        view,
                        getString(R.string.permission_info),
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.permission_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(
                                HelperActivity.this,
                                permissions,
                                ConstantsCustomGallery.PERMISSION_REQUEST_CODE);
                    }
                });

        /*((TextView) snackbar.getView()
                .findViewById(android.support.design.R.id.snackbar_text)).setMaxLines(maxLines);*/
        snackbar.show();
    }

    private void showAppPermissionSettings() {
        Snackbar snackbar = Snackbar.make(
                        view,
                        getString(R.string.permission_force),
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.permission_settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.fromParts(
                                getString(R.string.permission_package),
                                HelperActivity.this.getPackageName(),
                                null);

                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.setData(uri);
                        startActivityForResult(intent, ConstantsCustomGallery.PERMISSION_REQUEST_CODE);
                    }
                });

        /*((TextView) snackbar.getView()
                .findViewById(android.support.design.R.id.snackbar_text)).setMaxLines(maxLines);*/
        snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != ConstantsCustomGallery.PERMISSION_REQUEST_CODE
                || grantResults.length == 0
                || grantResults[0] == PackageManager.PERMISSION_DENIED) {
            permissionDenied();

        } else {
            permissionGranted();
        }
    }

    protected void permissionGranted() {
    }

    private void permissionDenied() {
        hideViews();
        requestPermission();
    }

    protected void hideViews() {
    }

    protected void setView(View view) {
        this.view = view;
    }
}
