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
import ro.geenie.R;
import ro.geenie.models.DashItem;
import ro.geenie.views.adapters.DashAdapter;

/**
 * Created by loopiezlol on 26.02.2015.
 */
public class DashActivity extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private List<DashItem> mockList = new ArrayList<DashItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.inject(this);


        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml

        set(navMenuTitles, navMenuIcons);

        mockList.add(new DashItem("ya", " alo multe droguri"));
        mockList.add(new DashItem("ya2", " asfanifaiofbasiofipasbfiabiopfbipafbaps"));
        mockList.add(new DashItem("ya3", " asofjapsfpafipasfipashfipashfiopashfipafip"));


        initView(mockList, R.id.dash_recycler_view, R.layout.dash_card);
    }

    public void initView(List<DashItem> items, int list, int item) {

        DashAdapter adapter = new DashAdapter(this, items, item);
        RecyclerView recyclerView = (RecyclerView) findViewById(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);


    }
}
