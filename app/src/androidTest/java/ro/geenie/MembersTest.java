package ro.geenie;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

import ro.geenie.db.DatabaseHelper;
import ro.geenie.models.Member;

/**
 * Created by motan on 09.03.2015.
 */
public class MembersTest extends ApplicationTestCase<Application> {

    public static final String TAG = "MembersTest";
    public DatabaseHelper dbHelper;

    public MembersTest() {
        super(Application.class);
    }

    public void testMembers() {
        getHelper().getMemberDao().createOrUpdate(new Member(1, "ion", true, true));
        getHelper().getMemberDao().createOrUpdate(new Member(2, "gabi", true, true));
        getHelper().getMemberDao().createOrUpdate(new Member(3, "ibag", true, true));

        List<Member> members = getHelper().getMemberDao().queryForAll();
        for (Member m : members) {
            Log.d(TAG, "found user: " + m.getName());
        }
        releaseHelper();
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
