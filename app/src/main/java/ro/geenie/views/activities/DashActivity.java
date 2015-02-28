package ro.geenie.views.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.models.DashItem;
import ro.geenie.models.Member;
import ro.geenie.models.exception.NoOwnerException;
import ro.geenie.models.orm.OrmActivity;
import ro.geenie.util.Utils;
import ro.geenie.views.adapters.DashAdapter;

/**
 * Created by loopiezlol on 26.02.2015.
 */
public class DashActivity extends OrmActivity {

    RecyclerView recyclerView;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private List<DashItem> mockList = new ArrayList<DashItem>();

    private String ownerName;


    @InjectView(R.id.fab_new_dash_item)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.inject(this);

        initDrawer();

        try {
            Member owner = getHelper().getOwner();
            ownerName = owner.getUser();
        } catch (SQLException | NoOwnerException e) {
            Utils.toastLog(this, e.getMessage());
        }
        mockList.add(new DashItem(ownerName, " first post by owner"));

        initView(mockList, R.id.dash_recycler_view, R.layout.dash_card);
    }



    private void initDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
    }

    public void initView(List<DashItem> items, int list, int item) {
        DashAdapter adapter = new DashAdapter(this, items, item);
        recyclerView = (RecyclerView) findViewById(list);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        fab.attachToRecyclerView(recyclerView);
    }

    @OnClick(R.id.fab_new_dash_item) void newDashItem() {
        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title("New Post")
                .customView(R.layout.dialog_new_dash, wrapInScrollView)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        EditText postMessage = (EditText)
                                dialog.getCustomView()
                                        .findViewById(R.id.edittext_new_dash_item);
                        String enteredText = postMessage.getText().toString();
                        mockList.add(new DashItem(ownerName, enteredText));
                        recyclerView.invalidate();
                    }
                })
                .positiveText("Done")
                .build()
                .show();
    }
}
