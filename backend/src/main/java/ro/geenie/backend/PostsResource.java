/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package ro.geenie.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{database}/posts")
public class PostsResource extends HttpServlet{
    // --> {groupDatabase}/objects/{objectId}

    public static final String GOOGLE_CLOUD_SQL_BASE_URL =
            "jdbc:mysql://173.194.253.100/";
    public static final String USER = "root";
    public static final String PASSWORD = "iubiresucces95";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllPosts(@PathParam("database") String dbName) throws Exception{
        Map<String, String> properties = new HashMap();
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(GOOGLE_CLOUD_SQL_BASE_URL + dbName,
                                                            USER,
                                                            PASSWORD);

        connection.close();
        return "Connected to " + dbName;
    }


}
