package ro.geenie.models.orm;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ro.geenie.db.DatabaseHelper;
import ro.geenie.views.activities.BaseActivity;

/**
 * Created by motan on 27.02.2015.
 */
public abstract class OrmActivity extends BaseActivity {
    public DatabaseHelper dbHelper;



    public DatabaseHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return dbHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }
}
