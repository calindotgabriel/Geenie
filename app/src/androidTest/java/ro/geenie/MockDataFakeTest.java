package ro.geenie;

import ro.geenie.models.Member;
import ro.geenie.models.Post;
import ro.geenie.models.orm.OrmAppTestCase;

/**
 * Created by motan on 20.03.2015.
 */
public class MockDataFakeTest extends OrmAppTestCase {

    public void testMockDataAdding() {
        getHelper().getMemberDao().createOrUpdate(new Member(1, "Ion", true));
        getHelper().getMemberDao().createOrUpdate(new Member(2, "Andra"));
        getHelper().getMemberDao().createOrUpdate(new Member(3, "Gabriel"));
        getHelper().getMemberDao().createOrUpdate(new Member(4, "Catalin"));

        getHelper().getPostDao().createOrUpdate(new Post(1, "Gabriel",
                "Don't forget guys, tomorrow's maths class is canceled! Yey!"));
    }
}
