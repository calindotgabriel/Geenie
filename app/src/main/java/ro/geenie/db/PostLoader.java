package ro.geenie.db;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import ro.geenie.models.Post;

/**
 * Created by motan on 04.04.2015.
 */
public class PostLoader extends AsyncTaskLoader<List<Post>> {

    public DatabaseHelper dbHelper;
    private List<Post> mData;

    public PostLoader(Context context) { super(context); }

    @Override
    public List<Post> loadInBackground() {
        return getHelper().getPostDao().queryForAll();
    }

    @Override
    public void deliverResult(List<Post> data) {
        if (isReset()) {
            releaseHelper();
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }

    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        releaseHelper();
    }

    @Override
    public void onCanceled(List<Post> data) {
        releaseHelper();
    }

    private DatabaseHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        }
        return dbHelper;
    }

    private void releaseHelper() {
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }


}
