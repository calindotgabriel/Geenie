package ro.geenie.views.activities;

import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import ro.geenie.R;
import ro.geenie.fragments.NewEventDialog;

/**
 * Created by loopiezlol on 09.02.2015.
 */
public class ScheduleActivity extends BaseActivity implements WeekView.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener, NewEventDialog.scheduleActivityListener {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private static final int TYPE_WEEK_VIEW = 3;
    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    int id = 0;
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

        /*mWeekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar calendar) {

                new NewEventDialog().show(ScheduleActivity.this);
            }
        });*/

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

            case R.id.action_new_event:
                new NewEventDialog().show(ScheduleActivity.this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF rectF) {
        Toast.makeText(ScheduleActivity.this, "Clicked " + getEventTitle(event.getStartTime()), Toast.LENGTH_SHORT).show();
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
        //bundle.putInt("position", events.indexOf(event));
        NewEventDialog dialog = new NewEventDialog();
        dialog.setArguments(bundle);
        dialog.show(ScheduleActivity.this);

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> weekViewEvents = new ArrayList<WeekViewEvent>();

        for (WeekViewEvent event : events) {
            if (event.getStartTime().get(Calendar.MONTH) + 1 == newMonth && event.getStartTime().get(Calendar.YEAR) == newYear) {
                weekViewEvents.add(event);
            }
        }


        return weekViewEvents;

    }

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));

    }

    public void createEvent(String eventName, int startHour, int endHour, int colorIndex, int dayOfWeek) {


        for (int i = 0; i <= 1000; i = i + 7) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, startHour);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            startTime.add(Calendar.DATE, i);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, endHour);
            endTime.add(Calendar.SECOND, -1);
            WeekViewEvent event = new WeekViewEvent(id++, eventName, startTime, endTime);
            int[] mColors = new int[ta.length()];
            for (int j = 0; j < ta.length(); j++)
                mColors[j] = ta.getColor(j, 0);
            event.setColor(mColors[colorIndex]);
            events.add(event);
        }

        for (int i = 0; i >= -1000; i = i - 7) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, startHour);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            startTime.add(Calendar.DATE, i);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, endHour);
            endTime.add(Calendar.SECOND, -1);
            WeekViewEvent event = new WeekViewEvent(id++, eventName, startTime, endTime);
            int[] mColors = new int[ta.length()];
            for (int j = 0; j < ta.length(); j++)
                mColors[j] = ta.getColor(j, 0);
            event.setColor(mColors[colorIndex]);
            events.add(event);
        }


        mWeekView.notifyDatasetChanged();


    }

    public void deleteEvent(String eventName) {
        Iterator<WeekViewEvent> iter = events.iterator();
        while (iter.hasNext()) {
            WeekViewEvent event = iter.next();
            if (event.getName().equals(eventName)) {
                iter.remove();
            }
        }

        mWeekView.notifyDatasetChanged();
    }
}

