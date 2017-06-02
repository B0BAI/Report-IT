package me.bobaikato.app.report;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Author: Bobai Kato
 * Date: 6/2/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 */

public class InternetConnection {

    /*InternetConnection Connection Check point*/
    public void checkNetwork(View view, final Context context) {
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
    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    InternetConnection(View view, Context context) {
        checkNetwork(view, context);
    }
}
