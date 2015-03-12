package ro.geenie;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ApplicationTestCase;
import android.util.Log;

import ro.geenie.provider.PostContract;
import ro.geenie.util.Utils;

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

        Uri expected_uri = Utils.getIdUri(id);
        assertEquals(expected_uri, uri);
        assertEquals(getCurrentCount(), before + 1);
    }

    /***
     * Tries to the delete the top-most record in the db.
     */
    public void testDelete() {
        int count = getCurrentCount();
        assertNotSame(0, count); // can't delete if empty db

        int deleted = resolver.delete(Utils.getIdUri(count), null, null);
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
        values.put(KEY_NAME, "ionutz likid");
        values.put(KEY_TEXT, "cee face baba ?");

        int updated = resolver.update(Utils.getIdUri(id), values, null, null);
        assertNotSame(0, updated); // did update smth

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
