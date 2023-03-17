package com.parth.ygm.utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

public class InternetConnectionChecker {
    public static boolean isInternetReachable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

                    if (networkCapabilities != null) {
                        int uploadSpeed = networkCapabilities.getLinkUpstreamBandwidthKbps();

                        if (uploadSpeed >= 10) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
