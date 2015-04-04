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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.controller.LocalOwnerController;
import ro.geenie.models.Item;
import ro.geenie.models.Post;
import ro.geenie.models.exception.NoOwnerException;
import ro.geenie.models.orm.OrmActivity;
import ro.geenie.util.Utils;
import ro.geenie.views.adapters.CardAdapter;

/**
 */
public class DashActivity extends OrmActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private List<Post> posts = new ArrayList<Post>();

    @InjectView(R.id.dash_recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.fab_new_dash_item)
    FloatingActionButton fab;

    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.inject(this);
        initDrawer();

        //todo move from UI Thread
        posts = getHelper().getPostDao().queryForAll();
        initView(new ArrayList<Item>(posts));
    }



    private void initView(List<Item> items) {
        adapter = new CardAdapter(this, items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        fab.attachToRecyclerView(recyclerView);
    }


    @OnClick(R.id.fab_new_dash_item)
    void newDashItem() {
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

                        LocalOwnerController ownerController =
                                new LocalOwnerController(getBaseContext());

                        String ownerName = null;
                        try {
                            ownerName = ownerController.getOwner().getName();
                            getHelper().getPostDao().create(new Post(posts.size(), ownerName, enteredText));
                        } catch (NoOwnerException e) {
                            Utils.toastLog(getBaseContext(), e.getMessage());
                        }

                        quickLocallyShow(ownerName, enteredText);
                    }

                    private void quickLocallyShow(String ownerName, String enteredText) {
                        posts.add(new Post(ownerName, enteredText));
                        //todo notify
                        recyclerView.swapAdapter(new CardAdapter(getBaseContext(),
                                                 new ArrayList<Item>(posts)),
                                                 false);
                    }
                })
                .positiveText("Done")
                .build()
                .show();
    }


    private void initDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
    }


}
