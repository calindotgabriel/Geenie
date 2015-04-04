package ro.geenie.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
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
import ro.geenie.db.PostLoader;
import ro.geenie.models.Item;
import ro.geenie.models.Post;
import ro.geenie.models.exception.NoOwnerException;
import ro.geenie.models.orm.OrmActivity;
import ro.geenie.util.Utils;
import ro.geenie.views.adapters.CardAdapter;

/**
 */
public class DashActivity extends OrmActivity
        implements LoaderManager.LoaderCallbacks<List<Post>> {

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

//
//        FragmentManager fm = getFragmentManager();
//        PostFragment posts = new PostFragment();
//        fm.beginTransaction().add(posts, "PostFragment").commit();



        setContentView(R.layout.activity_dash);
        ButterKnife.inject(this);
        initView();
        initDrawer();



//        initView(new ArrayList<Item>(posts));
    }

    void initDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
    }

    private void initView() {
        adapter = new CardAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setClickable(true);
        fab.attachToRecyclerView(recyclerView);
    }

    @Override
    public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
        return new PostLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {
        List<Item> items = new ArrayList<Item>(data);
        adapter.setData(items);
        Utils.toastLog(this, "Finished loading records.");
        recyclerView.getAdapter().notifyDataSetChanged();
//        recyclerView.swapAdapter(adapter, false);
    }

    @Override
    public void onLoaderReset(Loader<List<Post>> loader) {
        adapter.setData(null);
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
//                        getHelper().getPostDao().create(new Post())
                        //todo helper class for getting owner username

                    }
                })
                .positiveText("Done")
                .build()
                .show();
    }



    public static class PostFragment extends ListFragment { }

}
