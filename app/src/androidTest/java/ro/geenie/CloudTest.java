package ro.geenie;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ro.geenie.models.Member;
import ro.geenie.util.Keys;

/**
 * Created by motan on 09.03.2015.
 */
public class CloudTest extends ApplicationTestCase<Application> {

    final String db_url = Keys.DB_URL;
    final String db_username = Keys.DB_ROOT_USERNAME;
    final String db_password = Keys.DB_ROOT_PASSWORD;

    public CloudTest() {
        super(Application.class);
    }


    public void testCloud(){
        List<Member> members = new ArrayList<>();

        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection =
                    DriverManager.getConnection(db_url, db_username, db_password);
             st = connection.createStatement();
             resultSet = st.executeQuery("SELECT * FROM members");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("name");
                String password = resultSet.getString("password");
                Member member = new Member(id, username);
                members.add(member);
            }
            assertEquals(members.size(), 1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (st != null) st.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void testInsertion() {

    }


}
