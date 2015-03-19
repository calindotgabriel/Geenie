package ro.geenie.controller;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import ro.geenie.models.Member;
import ro.geenie.models.exception.NoOwnerException;
import ro.geenie.models.orm.OrmController;

/**
 * Created by motan on 19.03.2015.
 */
public class LocalOwnerController extends OrmController {

    public LocalOwnerController(Context context) {
        super(context);
    }

    public boolean isOwnerRegistered() {
        return getHelper().getMemberDao().idExists(1);
    }

    public void registerLocalOwner(String memberName) {
        getHelper().getMemberDao().createOrUpdate(new Member(1, memberName, true));
    }


    public Member getOwner() throws NoOwnerException {
        if (!isOwnerRegistered()){
            throw new NoOwnerException();
        }
        Map<String, Object> map = new HashMap<>();
        map.put(Member.KEY_OWNER, true);
        Member owner = getHelper().getMemberDao().queryForFieldValues(map).get(0);
        return owner;
    }
}
