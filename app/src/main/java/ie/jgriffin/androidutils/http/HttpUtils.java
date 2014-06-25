package ie.jgriffin.androidutils.http;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.util.Locale;

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

    /**
     * Generate an appropriate UserAgent string for use in web requests or logging.
     *
     * @param activity The current activity of the application
     * @return A custom UserAgent String.
     */
    public static String generateUserAgent(Activity activity){
        String versionName = "";
        try {
            versionName = activity.getApplication().getPackageManager().getPackageInfo(
                    activity.getApplication().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        StringBuilder ret = new StringBuilder();
        ret.append(activity.getApplication().getPackageName());
        ret.append("/");
        ret.append(versionName);
        ret.append(" (");
        ret.append("Linux; U; Android ");
        ret.append(Build.VERSION.RELEASE);
        ret.append("; ");
        ret.append(Locale.getDefault());
        ret.append("; ");
        ret.append(Build.PRODUCT);
        ret.append("; ");
        ret.append(Build.BRAND);
        ret.append("; ");
        ret.append(Build.MANUFACTURER);
        ret.append("; ");
        ret.append(Build.MODEL);
        ret.append(")");

        return ret.toString();
    }

}
