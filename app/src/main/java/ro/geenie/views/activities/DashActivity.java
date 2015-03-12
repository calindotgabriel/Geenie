package ro.geenie.views.activities;

import android.content.ContentResolver;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ro.geenie.R;
import ro.geenie.models.Member;
import ro.geenie.models.Post;
import ro.geenie.models.exception.NoOwnerException;
import ro.geenie.models.orm.OrmActivity;
import ro.geenie.network.DbPostsAsyncTask;
import ro.geenie.util.Utils;
import ro.geenie.views.adapters.DashAdapter;

/**
 */
public class DashActivity extends OrmActivity {


    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private List<Post> posts = new ArrayList<Post>();

    private String ownerName;

    @InjectView(R.id.dash_recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.fab_new_dash_item)
    FloatingActionButton fab;

    ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        ButterKnife.inject(this);

        initDrawer();

        contentResolver = getContentResolver();

        try {
            Member owner = getHelper().getOwner();
            //todo move getOwner() to MemberProvider class.
            ownerName = owner.getUser();
        } catch (SQLException | NoOwnerException e) {
            Utils.toastLog(this, e.getMessage());
        }

        DbPostsAsyncTask dbPostsAsyncTask = new DbPostsAsyncTask(this);
        dbPostsAsyncTask.execute();

        posts = getHelper().getPostRuntimeDao().queryForAll();
        initView(posts, R.id.dash_recycler_view, R.layout.dash_card);
    }

    public void initView(List<Post> items, int recyclerViewId, int itemLayout) {
        DashAdapter adapter = new DashAdapter(this, items, itemLayout);
        recyclerView = (RecyclerView) this.findViewById(recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        fab.attachToRecyclerView(recyclerView);
    }



    private void initDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
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
//                        EditText postMessage = (EditText)
//                                dialog.getCustomView()
//                                        .findViewById(R.id.edittext_new_dash_item);
//                        String enteredText = postMessage.getText().toString();
//                        posts.add(new Post(ownerName, enteredText));
//                        recyclerView.invalidate();
                    }
                })
                .positiveText("Done")
                .build()
                .show();
    }


}
