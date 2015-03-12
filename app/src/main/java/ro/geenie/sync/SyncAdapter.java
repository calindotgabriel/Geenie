package ro.geenie.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.geenie.db.PostsProvider;
import ro.geenie.models.Post;
import ro.geenie.models.exception.PostsProviderException;
import ro.geenie.provider.PostContract;
import ro.geenie.util.Utils;

/**
 * Created by motan on 08.03.2015.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String TAG = "SyncAdapter";
    private final ContentResolver contentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            Log.i(TAG, "Starting sync.");
            List<Post> posts = PostsProvider.getAllPosts();

            ArrayList<ContentProviderOperation> batch = new ArrayList<>();

            // build hash table of incoming posts
            HashMap<String, Post> postsMap = new HashMap<>();
            for (Post post : posts) {
                postsMap.put("id", post);
            }

            //get local posts
            Log.i(TAG, "Fetching local entries for merge");
            Uri uri = PostContract.POSTS_URI;
            Cursor c = contentResolver.query(uri, null, null, null, null);
            Log.i(TAG, "Found " + c.getCount() + " local entries. Computing merge solution...");

            while (c.moveToNext()) {//TODO unify key definitions
                String id = c.getString(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                String text = c.getString(c.getColumnIndex("text"));
                Post match = postsMap.get(id);
                if (match != null) {
                    // post already exists locally, remove it to new insert it later
                    postsMap.remove(id);
                    // check to see if it needs update
                    Uri existingUri = Utils.getIdUri(Integer.parseInt(id));
                    if ((match.getMemberName() != null && !match.getMemberName().equals(name)) ||
                            (match.getMessage() != null && !match.getMessage().equals(text))) {
                        // update it
//                        batch.add(ContentProviderOperation.newUpdate(existingUri)
//                                    .withValue())
                    }
                }
            }

        } catch (PostsProviderException e) {
            e.printStackTrace();
            Utils.toastLog(getContext(), e.getMessage());
        }
    }
}
