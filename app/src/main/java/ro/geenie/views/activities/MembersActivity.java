package ro.geenie.views.activities;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ro.geenie.R;
import ro.geenie.models.Item;
import ro.geenie.models.orm.OrmActivity;
import ro.geenie.views.adapters.CardAdapter;

/**
 * Created by motan on 18.03.2015.
 */
public class MembersActivity extends OrmActivity {
    private String[] navMenuTitles; //TODO move declarations to BaseActivity
    private TypedArray navMenuIcons;


    @InjectView(R.id.members_recycler_view)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        ButterKnife.inject(this);
        initDrawer();
        initView();
    }

    private void initView() {
        List<Item> items = new ArrayList<Item>(getHelper().getMemberDao().queryForAll());
        CardAdapter cardAdapter = new CardAdapter(
                this,
                items
        );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
    }

    protected void initDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
    }
}
