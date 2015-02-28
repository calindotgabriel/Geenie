package ro.geenie.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by motan on 27.02.2015.
 */
public class GoogleDbConnection {

    private static GoogleDbConnection instance;
    private Connection connection;

    public static GoogleDbConnection getInstance() {
        if (instance == null) {
            instance = new GoogleDbConnection();
        }
        return instance;
    }

    public GoogleDbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String db_url = "jdbc:mysql://173.194.253.100/defaultGroup";
            final String db_username = "root";
            final String db_password = "iubiresucces95";

            connection =
                    DriverManager.getConnection(
                            db_url,
                            db_username,
                            db_password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
