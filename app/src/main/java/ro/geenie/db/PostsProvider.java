package ro.geenie.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ro.geenie.models.Post;
import ro.geenie.models.exception.PostsProviderException;
import ro.geenie.network.GoogleDbConnection;
import ro.geenie.util.Keys;

/**
 * Created by motan on 28.02.2015.
 */
public class PostsProvider {

    public static List<Post> getAllPosts() throws PostsProviderException {
        List<Post> posts = new ArrayList<>();
        try {
            Connection connection =
                    GoogleDbConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM posts");

            while (resultSet.next()) {
                int id = resultSet.getInt(Keys.KEY_ID);
                String poster = resultSet.getString(Keys.KEY_POSTER);
                String msg = resultSet.getString(Keys.KEY_MESSAGE);
                Post post = new Post(id, poster, msg);
                posts.add(post);
            }

            GoogleDbConnection.finish();

        } catch (Exception e) {
            throw new PostsProviderException(e.getMessage());
        }
        return posts;
    }
}
