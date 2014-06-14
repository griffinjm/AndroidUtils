package ie.jgriffin.androidutils.view;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by JGriffin on 14/06/2014.
 */
public class ViewUtils {

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

}
