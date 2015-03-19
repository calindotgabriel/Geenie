package ro.geenie.views.activities;

import android.content.res.TypedArray;
import android.os.Bundle;

import butterknife.ButterKnife;
import ro.geenie.R;

/**
 * Created by loopiezlol on 18.03.2015.
 */
public class SettingsActivity extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);

        initDrawer();

        getSupportActionBar().setTitle("Settings");
    }

    private void initDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
    }
}
