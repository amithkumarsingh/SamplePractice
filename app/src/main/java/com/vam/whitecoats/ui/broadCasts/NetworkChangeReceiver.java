package com.vam.whitecoats.ui.broadCasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.QBLogin;
import com.vam.whitecoats.ui.interfaces.NetworkConnectionListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.Foreground;
import com.vam.whitecoats.utils.NetworkConnectListenerManager;
import com.vam.whitecoats.utils.NetworkUtils;

/**
 * Created by praveenkumars on 13-06-2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    //int status = 1;
    @Override
    public void onReceive(final Context context, final Intent intent) {


        switch (NetworkUtils.getConnectivityStatus(context)) {
            case 0:
                Log.e("NetWorkChangeReceiver", "network disconnected");
                BaseActionBarActivity.displaySnackBar(context.getString(R.string.no_internet_connection));
                break;
            case 1:
            case 2:
                if(Foreground.get().isForeground() || !(Foreground.get().isDestroyed()) ) {
                    Log.e("NetWorkChangeReceiver", "network connection successfull");
                    BaseActionBarActivity.hideSnackBar();
                    /*QBLogin qbLogin = new QBLogin(context, "NetworkChangeListener", new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String s) {

                        }
                    });*/
                    for(NetworkConnectionListener listener: NetworkConnectListenerManager.getRegisterListeners()){
                        listener.onNetworkReconnection();
                    }
                }
                break;
            default:
                Toast.makeText(context, "Unknown case executed", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
