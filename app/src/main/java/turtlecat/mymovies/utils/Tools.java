package turtlecat.mymovies.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import turtlecat.mymovies.App;

/**
 * Created by Alex on 3/19/2016.
 */
public class Tools {
    public static String TAG = "OMDB";

    public static void log(Object o) {
        Log.d(TAG, o + "");
    }

    public static boolean isLollipopOrNewer() {
        return getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }


    public static int getStatusBarHeight(Context c) {
        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void showToast(Context c, String text) {
        Toast.makeText(c, text, Toast.LENGTH_SHORT).show();
    }

}
