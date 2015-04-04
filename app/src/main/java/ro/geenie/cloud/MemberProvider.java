package ro.geenie.cloud;

/**
 * Created by motan on 18.03.2015.
 */
public class MemberProvider {
    /*public static List<Member> getAllMembers() throws MemberProviderException {
        List<Member> members = new ArrayList<>();
        try {
            Connection connection =
                    CloudConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                int id = resultSet.getInt(Keys.KEY_ID);
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Member member = new Member(id, username);
                members.add(member);
            }
        } catch (Exception e) {
            throw new MemberProviderException(e.getMessage());
        }
        finally {
            CloudConnection.finish();
        }
        return members;
    }*/

}
