package ro.geenie.views.activities;

import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.fragments.NewEventDialog;
import ro.geenie.models.Event;

/**
 * Created by loopiezlol on 09.02.2015.
 */
public class ScheduleActivity extends BaseActivity implements WeekView.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener, NewEventDialog.scheduleActivityListener, WeekView.EmptyViewLongPressListener {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private static final int TYPE_WEEK_VIEW = 3;
    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    int id = 0;
    List<Event> eventsextended = new ArrayList<>();
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private WeekView mWeekView;
    private TypedArray ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        ButterKnife.inject(this);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml

        set(navMenuTitles, navMenuIcons);

        mWeekView = (WeekView) findViewById(R.id.weekView);

        mWeekView.setOnEventClickListener(this);

        mWeekView.setMonthChangeListener(this);

        mWeekView.setEventLongPressListener(this);

        mWeekView.setEmptyViewLongPressListener(this);

        ta = getResources().obtainTypedArray(R.array.colors);

        mWeekView.goToHour(7);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_today:
                mWeekView.goToToday();
                mWeekView.goToHour(7);
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.goToHour(7);
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.goToHour(7);
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.goToHour(7);
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF rectF) {
    }

    @Override
    public void onEmptyViewLongPress(Calendar calendar) {
        Bundle bundle = new Bundle();
        bundle.putInt("startHour", calendar.get(Calendar.HOUR_OF_DAY));
        bundle.putInt("endHour", calendar.get(Calendar.HOUR_OF_DAY) + 1);
        bundle.putInt("dayOfWeek", calendar.get(Calendar.DAY_OF_WEEK));
        NewEventDialog dialog = new NewEventDialog();
        dialog.setArguments(bundle);
        dialog.show(ScheduleActivity.this);

        //Toast.makeText(this,Integer.toString(calendar.get(Calendar.HOUR_OF_DAY))+ Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)+1),Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fab_new_assignment_item)
    void openDialog() {
        new NewEventDialog().show(ScheduleActivity.this);
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF rectF) {
        Bundle bundle = new Bundle();
        bundle.putString("eventName", event.getName());
        bundle.putInt("startHour", event.getStartTime().get(Calendar.HOUR_OF_DAY));
        bundle.putInt("endHour", event.getEndTime().get(Calendar.HOUR_OF_DAY));
        int colorIndex = 0;
        int localColor = event.getColor();
        for (int i = 0; i < ta.length(); i++) {
            if (localColor == ta.getColor(i, 0))
                colorIndex = i;
        }
        bundle.putInt("color", colorIndex);
        bundle.putInt("dayOfWeek", event.getStartTime().get(Calendar.DAY_OF_WEEK));
        boolean repeat = false;
        for (Event extended : eventsextended) {

            if (event.getId() == extended.getEvent().getId()) {
                repeat = extended.getRepeat();

            }
        }
        bundle.putBoolean("repeat", repeat);
        NewEventDialog dialog = new NewEventDialog();
        dialog.setArguments(bundle);
        dialog.show(ScheduleActivity.this);

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        List<WeekViewEvent> weekViewEvents = new ArrayList<WeekViewEvent>();
        List<Event> allEvents = new ArrayList<>();


        for (Event parent : eventsextended) {

            int startHour = parent.getEvent().getStartTime().get(Calendar.HOUR_OF_DAY);
            int dayOfWeek = parent.getEvent().getStartTime().get(Calendar.DAY_OF_WEEK);
            int endHour = parent.getEvent().getEndTime().get(Calendar.HOUR_OF_DAY);
            String eventName = parent.getEvent().getName();
            boolean repeat = parent.getRepeat();

            if (!repeat) {
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, startHour);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                endTime.set(Calendar.HOUR_OF_DAY, endHour);
                endTime.add(Calendar.MINUTE, -1);
                WeekViewEvent childEvent = new WeekViewEvent(parent.getEvent().getId(), eventName, startTime, endTime);
                childEvent.setColor(parent.getEvent().getColor());
                Event childExtended = new Event(childEvent, repeat);
                allEvents.add(childExtended);
            } else {
                for (int i = -1000; i <= 1000; i = i + 7) {
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, startHour);
                    startTime.set(Calendar.MINUTE, 0);
                    startTime.set(Calendar.DAY_OF_WEEK, dayOfWeek - 1);
                    startTime.add(Calendar.DATE, i);
                    startTime.add(Calendar.SECOND, 1);
                    Calendar endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, endHour);
                    endTime.set(Calendar.MINUTE, 59);
                    WeekViewEvent childEvent = new WeekViewEvent(parent.getEvent().getId(), eventName, startTime, endTime);
                    childEvent.setColor(parent.getEvent().getColor());
                    Event childExtended = new Event(childEvent, repeat);
                    allEvents.add(childExtended);
                }
            }


        }

        for (Event event : allEvents) {
            if (event.getEvent().getStartTime().get(Calendar.MONTH) == newMonth && event.getEvent().getStartTime().get(Calendar.YEAR) == newYear) {
                weekViewEvents.add(event.getEvent());
            }
        }

        return weekViewEvents;


    }

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));

    }

    public void createEvent(String eventName, int startHour, int endHour, int colorIndex, int dayOfWeek, boolean repeat) {


        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, startHour);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        //startTime.add(Calendar.MILLISECOND,1);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, endHour);
        WeekViewEvent event = new WeekViewEvent(id++, eventName, startTime, endTime);
        int[] mColors = new int[ta.length()];
        for (int j = 0; j < ta.length(); j++)
            mColors[j] = ta.getColor(j, 0);
        event.setColor(mColors[colorIndex]);
        Event extended = new Event(event, repeat);
        eventsextended.add(extended);

        mWeekView.notifyDatasetChanged();


    }

    public void deleteEvent(String eventName) {
        Iterator<Event> iter = eventsextended.iterator();
        while (iter.hasNext()) {
            Event event = iter.next();
            if (event.getEvent().getName().equals(eventName)) {
                iter.remove();
            }
        }

        mWeekView.notifyDatasetChanged();
    }


}


//TODO - make events recurring inside onMonthCreate BAJA
