package ro.geenie.network;

import java.util.List;

import ro.geenie.models.Member;
import ro.geenie.util.Keys;

/**
 * Created by motan on 27.02.2015.
 */
public class CloudConnection {

    private List<Member> members;

    private final String db_url = Keys.DB_URL;
    private final String db_username = Keys.DB_ROOT_USERNAME;
    private final String db_password = Keys.DB_ROOT_PASSWORD;

    public List<Member> getAllMembers() {
        return null;
    }


}
