package ro.geenie.cloud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ro.geenie.models.Member;
import ro.geenie.models.exception.MemberProviderException;
import ro.geenie.network.GoogleDbConnection;
import ro.geenie.util.Keys;

/**
 * Created by motan on 18.03.2015.
 */
public class MemberProvider {

    public static List<Member> getAllMembers() throws MemberProviderException {
        List<Member> members = new ArrayList<>();
        try {
            Connection connection =
                    GoogleDbConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM members");

            while (resultSet.next()) {
                int id = resultSet.getInt(Keys.KEY_ID);
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Member member = new Member(id, username);
                members.add(member);
            }

            GoogleDbConnection.finish();

        } catch (Exception e) {
            throw new MemberProviderException(e.getMessage());
        }
        return members;
    }
}
