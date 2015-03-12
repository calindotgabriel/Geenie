package ro.geenie.models.orm;

import android.content.Context;
import android.os.AsyncTask;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ro.geenie.db.DatabaseHelper;

/**
 * Created by motan on 28.02.2015.
 */
public abstract class OrmContextAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    public Context context;
    public DatabaseHelper dbHelper;

    public abstract void registerContext(Context context);

    public Context getContext() {
        return context;
    }

    public DatabaseHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return dbHelper;
    }

    protected abstract Result doInBackground(Params... params);
}
