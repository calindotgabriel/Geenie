package ro.geenie.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import ro.geenie.db.PostsProvider;
import ro.geenie.models.exception.PostsProviderException;

/**
 * Created by motan on 08.03.2015.
 */
public class PostSyncAdapter extends AbstractThreadedSyncAdapter {

    private final ContentResolver contentResolver;

    public PostSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            PostsProvider.getAllPosts();
        } catch (PostsProviderException e) {
            e.printStackTrace();
        }
    }
}
