package turtlecat.mymovies.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;

/**
 * Created by Alex on 3/19/2016.
 */

@EBean
public class Tools {
    public static String TAG = "OMDB";


    /**
     * @param o Pass anything it will be logged with TAG = "OMDB"
     */
    public static void log(Object o) {
        Log.d(TAG, o + "");
    }


    public static int getColor(Context c, int colorId) {
        if (getAndroidVersion() >= Build.VERSION_CODES.M) {
            return c.getResources().getColor(colorId, null);
        } else {
            return c.getResources().getColor(colorId);
        }
    }


    /**
     * @return true if OS version is higher than lollipop.
     */
    public static boolean isLollipopOrNewer() {
        return getAndroidVersion() >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.x;
    }


    /**
     * @return Android OS Version
     */
    public static int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * @param c Context for resources
     * @return height off toolbar in pixels
     */
    public static int getStatusBarHeight(Context c) {
        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * @param c
     * @param text
     */
    public static void showToast(Context c, String text) {
        Toast.makeText(c, text, Toast.LENGTH_SHORT).show();
    }

}
