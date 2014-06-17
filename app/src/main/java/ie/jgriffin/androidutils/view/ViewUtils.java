package ie.jgriffin.androidutils.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by JGriffin on 14/06/2014.
 */
public class ViewUtils {

    /**
     * A simple utility method to resolve the ActionBar's height as defined in the theme.
     *
     * @param context the current context of the application
     * @param tag the app's tag for logging
     * @return
     */
    public static int getActionBarHeight(Context context, String tag){
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        try {
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            }
        }catch(NullPointerException npe){
            if(tag != null){
                Log.e(tag, npe.getMessage());
            }
        }
        return actionBarHeight;
    }


    /**
     * A utility method to close the soft keyboard.
     *
     * @param activity The current activity of the application
     * @param v A view which allows us to get a handle on the window
     */
    public static void closeKeyboard(Activity activity, View v) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * A utility method to close the soft keyboard.
     * This could throw a possible NPE if current focus is null.
     * External checks should be carried out first.
     *
     * @param activity The current activity of the application
     */
    public static void closeKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * A utility method to open the soft keyboard.
     *
     * @param activity The current activity of the application
     */
    public static void openKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * A utility method to open the soft keyboard.
     *
     * @param activity The current activity of the application
     * @param view A view currently instantiated in the activity
     */
    public static void openKeyboard(Activity activity, View view){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     * Utility method to show a short toast to the user.
     *
     * @param context Current context of the application.
     * @param message Message to display to the user.
     */
    public static void showStandardToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Utility method to show a toast to the user.
     * This toast is customised to display for the specified time only.
     *
     * @param context Current context of the application.
     * @param message Message to display to the user.
     */
    public static void showCustomLengthToastShort(Context context, String message) {
        //custom display time
        final long DISPLAY_TIME = 500;//millis
        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

        toast.show();

        //setDuration here using a handler to cancel after DISPLAY_TIME
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, DISPLAY_TIME);
    }

}
