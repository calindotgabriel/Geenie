package ro.geenie;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ApplicationTestCase;
import android.util.Log;

import ro.geenie.provider.PostContract;

/**
 * Created by motan on 09.03.2015.
 */
public class ContentProviderTest extends ApplicationTestCase<Application> {

    ContentResolver resolver;

    public static final String TAG = "ContentProviderTest";
    public static final Uri POSTS_ROUTE = PostContract.POSTS_URI;
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TEXT = "text";

    public ContentProviderTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        resolver = getContext().getContentResolver();
        assertNotNull(resolver);
    }



//    public void testPostContentProvider() {
////        int first = resolver.delete(Uri.withAppendedPath(POSTS_ROUTE, Integer.toString(6)), null, null);
////        assertNotSame(0, first);
//        ContentValues tempValues =
//        resolver.insert(Uri.withAppendedPath(POSTS_ROUTE, Integer.toString(4)))
//
//
//        int old_count = c.getCount();
//        assertNotSame(0, old_count);
//        logRecords(c);
//
//        // create a post
//        ContentValues cv = new ContentValues();
//        int id = old_count + 1;
//        cv.put(KEY_ID, id);
//        cv.put(KEY_NAME, "dede");
//        cv.put(KEY_TEXT, "aka aka");
//        Uri uri = resolver.insert(POSTS_ROUTE, cv);
//        assertNotNull(uri);
//        Uri expected_uri = Uri.withAppendedPath(POSTS_ROUTE, Integer.toString(id));
//        assertEquals(expected_uri, uri);
//        assertEquals(getCurrentCount(resolver), old_count + 1);
//
//        // now delete it
//        int delete_count = resolver.delete(expected_uri, null, null);
//        assertNotSame(0, delete_count);
//        assertEquals(getCurrentCount(resolver), old_count);
//
//
//    }

    public void testQuery() {
        Cursor c = queryAllPosts();
        assertNotNull(c);
        logRecords(c);
    }

    /***
     * Tries to insert a new random post at the top of the db.
     */
    public void testInsert() {
        int before = getCurrentCount();

        ContentValues values = new ContentValues();
        int id = before + 1; // insert at next position ( normally auto-generated )
        values.put(KEY_ID, id);
        values.put(KEY_NAME, "ion");
        values.put(KEY_TEXT, "am boala pamantului");
        Uri uri = resolver.insert(POSTS_ROUTE, values);

        Uri expected_uri = getIdUri(id);
        assertEquals(expected_uri, uri);
        assertEquals(getCurrentCount(), before + 1);
    }

    /***
     * Tries to the delete the top-most record in the db.
     */
    public void testDelete() {
        int count = getCurrentCount();
        assertNotSame(0, count); // can't delete if empty db

        int deleted = resolver.delete(getIdUri(count), null, null);
        assertNotSame(0, deleted); // did delete something

        assertEquals(getCurrentCount(), count - 1);
    }

    /***
     * Tries to update the top-most record.
     */
    public void testUpdate() {
        int before = getCurrentCount();

        ContentValues values = new ContentValues();
        int id = before;
        values.put(KEY_ID, id);
        values.put(KEY_NAME, "ionutz likid");
        values.put(KEY_TEXT, "cee face baba ?");

        int updated = resolver.update(getIdUri(id), values, null, null);
        assertNotSame(0, updated); // did update smth

    }


    //TODO move this to Util
    private Uri getIdUri(int id) {
        return Uri.withAppendedPath(PostContract.POSTS_URI, Integer.toString(id));
    }

    private void logRecords(Cursor c) {
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex(KEY_ID));
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            String text = c.getString(c.getColumnIndex(KEY_TEXT));
            Log.d(TAG, "Post (" + id + ", " + name + ", " + text + ")");
        }
    }
    private Cursor queryAllPosts() {
        return resolver.query(
                POSTS_ROUTE,
                null,
                null,
                null,
                null
        );
    }
    private int getCurrentCount(){
        return queryAllPosts().getCount();
    }
}
