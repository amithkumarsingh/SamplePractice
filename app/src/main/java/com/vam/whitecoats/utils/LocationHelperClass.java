package com.vam.whitecoats.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.customviews.MarshMallowPermission;
import com.vam.whitecoats.ui.interfaces.LocationCaputerListner;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class LocationHelperClass implements LocationListener {


    private final Realm realm;
    private final RealmManager realmManager;
    private int login_doc_id = 0;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCaputerListner locationCaptureListener;
    private Context context;
    private GoogleApiClient googleApiClient = null;
    private LocationManager locationManager;
    private MarshMallowPermission marshMallowPermission;

    public LocationHelperClass(Context mContext, LocationCaputerListner listner) {
        this.context = mContext;
        this.locationCaptureListener = listner;

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(context);
        login_doc_id = realmManager.getDoc_id(realm);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        /*if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                           // Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if(location!=null) {
                                        listner.onLocationCapture(location);
                                    }
                                }
                            });
                            _location=getLocation();
                            if(_location!=null) {
                                listner.onLocationCapture(_location);
                            }
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            //Toast.makeText(context, "suspended", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            //Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }).build();
        }
        googleApiClient.connect();*/
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval((long) 10 * 1000);
        locationRequest.setFastestInterval((long) 2 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        // **************************
        builder.setAlwaysShow(true); // this is the key ingredient
        // **************************


        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (checkLocationPermission()) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            listner.onLocationCapture(location);
                        } else {
                            if (checkLocationPermission()) {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mFusedLocationClient.removeLocationUpdates(this);
                                        if (locationResult.getLocations().size() > 0) {
                                            int latestIndexLocation = locationResult.getLocations().size() - 1;
                                            Location lastKnowLocation = locationResult.getLocations().get(latestIndexLocation);
                                            if (lastKnowLocation != null) {
                                                listner.onLocationCapture(lastKnowLocation);
                                            }
                                        }
                                    }
                                }, null);

                            }
                        }
                    }
                });
            }
        } else {
            Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(mContext).checkLocationSettings(builder.build());
            task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                @Override
                public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                    try {
                        LocationSettingsResponse response = task.getResult(ApiException.class);
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        if (checkLocationPermission()) {
                            mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    mFusedLocationClient.removeLocationUpdates(this);
                                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                                        int latestIndexLocation = locationResult.getLocations().size() - 1;
                                        Location lastKnowLocation = locationResult.getLocations().get(latestIndexLocation);
                                        if (lastKnowLocation != null) {
                                            listner.onLocationCapture(lastKnowLocation);
                                        }
                                    }

                                }
                            }, Looper.getMainLooper());

                        }

                    } catch (ApiException exception) {
                        switch (exception.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                // Location settings are not satisfied. But could be fixed by showing the
                                // user a dialog.
                                try {
                                    // Cast to a resolvable exception.
                                    ResolvableApiException resolvable = (ResolvableApiException) exception;
                                    // Show the dialog by calling startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    String activityName = context.getClass().getSimpleName();
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        if (activityName.equalsIgnoreCase("MandatoryProfileInfo")) {
                                            jsonObject.put("screen", "SignUpUser");
                                        } else if (activityName.equalsIgnoreCase("LocationAndExperienceActivity")) {
                                            jsonObject.put("screen", "LocationUpdate");
                                        } else if (activityName.equalsIgnoreCase("ProfessionalDetActivity")) {
                                            jsonObject.put("screen", "UserProfile");
                                        }
                                        AppUtil.logUserActionEvent(login_doc_id, "LocationServiceIsDisabled", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), mContext);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    resolvable.startResolutionForResult((Activity) mContext, 1000);
                                    break;
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                } catch (ClassCastException e) {
                                    // Ignore, should be an impossible error.
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                // Location settings are not satisfied. However, we have no way to fix the
                                // settings so we won't show the dialog.

                                break;
                        }
                    }
                }
            });
        }

    }


    public void getLocation(LocationCaputerListner listener) {
        if (mFusedLocationClient != null) {
            if (checkLocationPermission()) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            listener.onLocationCapture(location);
                        } else {
                            if (checkLocationPermission()) {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mFusedLocationClient.removeLocationUpdates(this);
                                        if (locationResult.getLocations().size() > 0) {
                                            int latestIndexLocation = locationResult.getLocations().size() - 1;
                                            Location lastKnowLocation = locationResult.getLocations().get(latestIndexLocation);
                                            if (lastKnowLocation != null) {
                                                listener.onLocationCapture(lastKnowLocation);
                                            }
                                        }
                                    }
                                }, null);

                            }
                        }
                    }
                });

            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        } else {
            return true;
        }
    }
}
