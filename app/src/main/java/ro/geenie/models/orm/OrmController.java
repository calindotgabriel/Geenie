package ro.geenie.models.orm;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ro.geenie.db.DatabaseHelper;

/**
 * Created by motan on 18.03.2015.
 */
public class OrmController {


    public DatabaseHelper dbHelper;
    protected Context context;

    public OrmController(Context context) {
        this.context = context;
    }

    public DatabaseHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return dbHelper;
    }

    public void releaseHelper() {
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

}
