package ro.geenie.models.orm;

import android.app.ListFragment;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ro.geenie.db.DatabaseHelper;

/**
 * Created by motan on 18.03.2015.
 */
public class OrmListFragment extends ListFragment {

    public DatabaseHelper dbHelper;

    public DatabaseHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return dbHelper;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }
}
