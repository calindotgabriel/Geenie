package ro.geenie.views.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.fragments.NewAssignmentDialog;
import ro.geenie.models.AssignmentItem;
import ro.geenie.util.AlarmReceiver;
import ro.geenie.views.adapters.AssignmentAdapter;


public class AssignmentsActivity extends BaseActivity implements NewAssignmentDialog.assignmentActivityListener {

    public RecyclerView recyclerView;
    public List<AssignmentItem> mockList = new ArrayList<AssignmentItem>();
    @InjectView(R.id.fab_new_assignment_item)
    FloatingActionButton fab;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private AlarmManager manager;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;
    private AssignmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        ButterKnife.inject(this);

        initDrawer();

        initView(mockList, R.id.assignment_recycler_view, R.layout.assignement_card);

    }


    private void initDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
    }

    public void initView(List<AssignmentItem> items, int list, int item) {
        adapter = new AssignmentAdapter(this, items, item);
        recyclerView = (RecyclerView) findViewById(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        fab.attachToRecyclerView(recyclerView);
    }


    public void createAssignment(String assignmentTitle, Calendar calendar, String assignmentTag) {
        mockList.add(new AssignmentItem(assignmentTitle, 0, calendar, assignmentTag));
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab_new_assignment_item)
    void openDialog() {
        new NewAssignmentDialog().show(this);
    }

    public void startAlarm(int id, String name, Calendar calendar) {
        alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("name", name);
        alarmIntent.putExtra("id", id);
        pendingIntent = PendingIntent.getBroadcast(this, id, alarmIntent, 0);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(int id) {

        if (manager != null) {
            pendingIntent = PendingIntent.getBroadcast(this, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            manager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }

    }


    //TODO CHANGE SWITCH - AFTER DB IMPLEMENTATION


}
