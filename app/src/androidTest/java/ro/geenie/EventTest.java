package ro.geenie;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ro.geenie.models.Event;
import ro.geenie.models.orm.OrmAppTestCase;

/**
 * Created by motan on 19.03.2015.
 */
public class EventTest extends OrmAppTestCase{

    public void testEventsDb() {
        Calendar startOfEvent = new GregorianCalendar(2015, 3, 25);
        Calendar endOfEvent = new GregorianCalendar(2015, 3, 26);
        WeekViewEvent weekViewEvent = new WeekViewEvent(1, "Leapsa", startOfEvent, endOfEvent);
        getHelper().getEventDao().create(new Event(weekViewEvent, false));
        assertNotSame(getHelper().getEventDao().queryForAll().size(), 0);
    }
}
