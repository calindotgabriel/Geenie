package ro.geenie.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by motan on 28.02.2015.
 */
public class Utils {

    public static void toastLog(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}