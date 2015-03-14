package ro.geenie.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by motan on 27.02.2015.
 */
public class GoogleDbConnection {

    private static GoogleDbConnection instance;
    private static Connection connection;

    public static GoogleDbConnection getInstance()
            throws SQLException, ClassNotFoundException{
        if (instance == null) {
            instance = new GoogleDbConnection();
        }
        return instance;
    }

    public GoogleDbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        final String db_url = "jdbc:mysql://173.194.253.100/defaultGroup";
        final String db_username = "root";
        final String db_password = "iubiresucces95";

        connection = DriverManager.getConnection(
                        db_url,
                        db_username,
                        db_password
        );

    }

    public static void finish() {
        if (null == instance) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
