package ro.geenie.util;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import ro.geenie.provider.PostContract;

/**
 * Created by motan on 28.02.2015.
 */
public class Utils {

    public static void toastLog(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    //TODO move this to Util
    public static Uri getIdUri(int id) {
        return Uri.withAppendedPath(PostContract.POSTS_URI, Integer.toString(id));
    }
}
