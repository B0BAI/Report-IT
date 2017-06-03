package me.bobaikato.app.report;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Author: Bobai Kato
 * Date: 6/2/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 */

public class Permissions {

    /*Permissions Connection Check point*/
    public static void checkNetwork(View view, final Context context) {
        if (isNetworkAvailable(context) == false) {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkNetwork(view, context); //recursive call.
                        }
                    }).show();
        }
    }

    /*Network Availability*/
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public static boolean checkLocation(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
