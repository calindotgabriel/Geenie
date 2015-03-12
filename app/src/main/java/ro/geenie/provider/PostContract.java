package ro.geenie.provider;

import android.net.Uri;

/**
 * Created by motan on 09.03.2015.
 */
public class PostContract {
    public static final String AUTHORITY = "ro.geenie.provider.PostContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String TABLE_NAME = "posts";
    public static final Uri POSTS_URI =
            Uri.withAppendedPath(CONTENT_URI, TABLE_NAME);


    public static final String POST = "post";
    public static final String KEY_ID = "id" + "=?";
}
