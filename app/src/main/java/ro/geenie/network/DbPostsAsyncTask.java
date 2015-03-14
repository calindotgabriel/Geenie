package ro.geenie.network;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ro.geenie.R;
import ro.geenie.db.PostsProvider;
import ro.geenie.models.Post;
import ro.geenie.models.exception.PostsProviderException;
import ro.geenie.models.orm.OrmActivityAsyncTask;
import ro.geenie.util.Utils;

/**
 * Created by motan on 02.03.2015.
 */
public class DbPostsAsyncTask extends OrmActivityAsyncTask<String, String, List<Post>> {

    public DbPostsAsyncTask(Activity activity) {
        super(activity);
    }

    String message = "Loaded posts!";

    @Override
    protected List<Post> doInBackground(String... params) {

        List<Post> posts = getPostsFromServer();
        addPostsToLocalDb(posts);

        return posts;
    }

    private List<Post> getPostsFromServer() {
        List<Post> posts = new ArrayList<>();
        try {
            posts = PostsProvider.getAllPosts();
        } catch (PostsProviderException e) {
            message = e.getMessage();
        }
        if (posts.size() == 0) {
            message = "Empty posts list";
        }
        return posts;
    }

    private void addPostsToLocalDb(List<Post> posts) {
        for (Post post : posts) {
            getHelper().getPostRuntimeDao().createOrUpdate(post);
        }
    }

    @Override
    protected void onPostExecute(List<Post> posts) {
        Utils.toastLog(getActivity(), message);
        RecyclerView recyclerView =
                (RecyclerView) getActivity().findViewById(R.id.dash_recycler_view);
        recyclerView.invalidate();
    }







}
