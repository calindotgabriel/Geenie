package ro.geenie.models.orm;

import android.app.Activity;
import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ro.geenie.db.DatabaseHelper;

/**
 * Created by motan on 28.02.2015.
 */
public abstract class OrmActivityAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    public Activity activity;
    public DatabaseHelper dbHelper;

    protected OrmActivityAsyncTask(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public DatabaseHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(activity, DatabaseHelper.class);
        }
        return dbHelper;
    }

    protected abstract Result doInBackground(Params... params);
}
