package ro.geenie;

import java.util.HashMap;
import java.util.List;

import ro.geenie.models.Post;
import ro.geenie.models.orm.OrmAppTestCase;

/**
 * Created by motan on 20.03.2015.
 */
public class SyncTest extends OrmAppTestCase {

    public void testSyncPosts() {
        List<Post> posts = getHelper().getPostDao().queryForAll();
        HashMap<String, Post> postsMap = new HashMap<>();
        for (Post post : posts) {
            postsMap.put(Post.KEY_ID, post);
        }

    }

}
