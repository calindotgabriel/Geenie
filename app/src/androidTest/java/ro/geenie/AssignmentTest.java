package ro.geenie;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ro.geenie.models.Assignment;
import ro.geenie.models.orm.OrmAppTestCase;

/**
 * Created by motan on 19.03.2015.
 */
public class AssignmentTest extends OrmAppTestCase{

    public void testAssignmentDb() {
        Calendar calendar = new GregorianCalendar(2015, 3, 25);
        getHelper().getAssignmentDao().create(new Assignment("Droghhely", 0, calendar));
        assertNotSame(getHelper().getAssignmentDao().queryForAll().size(), 0);
    }
}
