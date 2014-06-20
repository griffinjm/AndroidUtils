package ie.jgriffin.androidutils.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by JGriffin on 20/06/2014.
 */
public class HttpUtils {

    /**
     * Checks whether or not the device has a data connection.
     *
     * @param context Current context of the application.
     * @return boolean based on whether the device has a data connection or not.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    /**
     * Checks whether or not the device has a data connection or is establishing a data connection.
     *
     * @param context Current context of the application.
     * @return boolean based on whether the device has a data connection or not.
     */
    public static boolean isConnectingOrOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}
