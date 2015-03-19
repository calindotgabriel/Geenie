package ro.geenie.models.orm;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ro.geenie.db.DatabaseHelper;

/**
 * Created by motan on 19.03.2015.
 */
public class OrmAppTestCase extends ApplicationTestCase<Application> {

    public DatabaseHelper dbHelper;


    public OrmAppTestCase() {
        super(Application.class);
    }


    public DatabaseHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
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
