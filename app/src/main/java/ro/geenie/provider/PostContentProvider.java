package ro.geenie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ro.geenie.db.DatabaseHelper;
import ro.geenie.models.Post;

/**
 * Created by motan on 09.03.2015.
 */
public class PostContentProvider extends ContentProvider {

    private DatabaseHelper dbHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int ROUTE_POSTS = 1;
    public static final int ROUTE_POST = 2;

    static {
        uriMatcher.addURI(PostContract.AUTHORITY, "posts", ROUTE_POSTS);
        uriMatcher.addURI(PostContract.AUTHORITY, "posts/*", ROUTE_POST);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)) {
            case ROUTE_POSTS:
                return PostContract.TABLE_NAME;
            case ROUTE_POST:
                return PostContract.POST;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri.toString());
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        SQLiteDatabase db = getHelper().getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case ROUTE_POSTS:
                c = db.rawQuery("SELECT * FROM " + PostContract.TABLE_NAME, null);
                break;
            case ROUTE_POST:
                String id = uri.getLastPathSegment();
                c = db.rawQuery("SELECT * FROM " + PostContract.TABLE_NAME +
                                " WHERE " + Post.KEY_ID + " = " + id, null);
                break;
        }
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result = null;
        switch (uriMatcher.match(uri)) {
            case ROUTE_POSTS:
                SQLiteDatabase db = getHelper().getWritableDatabase();
                long id = db.insertOrThrow(PostContract.TABLE_NAME, null, values);
                result = Uri.parse(PostContract.POSTS_URI + "/" + id);
        }
        getContext().getContentResolver()
                .notifyChange(uri, null);
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = getHelper().getReadableDatabase();
        int count;
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ROUTE_POST:
                String id = uri.getLastPathSegment();
                count = db.update(PostContract.TABLE_NAME, values, Post.KEY_ID + "=" + Integer.parseInt(id), null);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ROUTE_POST:
                String id = uri.getLastPathSegment();
                count = getHelper().getPostDao().deleteById(Integer.parseInt(id));
                break;
            default:
                throw new UnsupportedOperationException();
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context ctx = getContext();
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    public DatabaseHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        }
        return dbHelper;
    }
}
