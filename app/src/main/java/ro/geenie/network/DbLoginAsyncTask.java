package ro.geenie.network;

import android.content.Context;
import android.content.Intent;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import ro.geenie.models.LoginTaskParams;
import ro.geenie.models.Member;
import ro.geenie.models.exception.DbNotAuthorisedException;
import ro.geenie.models.exception.InvalidLoginException;
import ro.geenie.models.orm.OrmContextAsyncTask;
import ro.geenie.util.Utils;
import ro.geenie.views.activities.DashActivity;

/**
 * Created by motan on 22.02.2015.
 */
public class DbLoginAsyncTask extends OrmContextAsyncTask<LoginTaskParams, String, String> {

    Context context = null;
    boolean validUser = false;
    Member owner = null;
    Connection connection = null;

    @Override
    protected String doInBackground(LoginTaskParams... params) {
        String logMessage = "Loggin you in!";
        context = params[0].context;
        registerContext(context);
        final String lUsername = params[0].name;
        final String lPassword = params[0].password;


        try {
            checkLoginInfo(lUsername, lPassword);

        } catch (Exception e) {
            logMessage = e.getMessage();
        }

        return logMessage;
    }

    private void checkLoginInfo(String lUsername, String lPassword)
            throws Exception {
        connection = GoogleDbConnection.getInstance().getConnection();
        if (connection == null) {
            throw new DbNotAuthorisedException("Cannot connect to Google db.");
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()) {
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            if (username.equals(lUsername) || password.equals(lPassword)) {
                validUser = true;
                registerOwner(lUsername);
            }
        }

        if (!validUser) {
            throw new InvalidLoginException("Invalid login. Try again.");
        }
    }

    private void registerOwner(String lUsername) {
        owner = new Member(lUsername, false, true);
        RuntimeExceptionDao<Member, Integer> dao = getHelper().getMemberRuntimeDao();
        dao.createOrUpdate(owner);
    }

    @Override
    protected void onPostExecute(String logMessage) {
        Utils.toastLog(context, logMessage);

        if (validUser) {
            GoogleDbConnection.finish();
            Intent intent = new Intent(context, DashActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void registerContext(Context context) {
        super.context = context;
    }
}
