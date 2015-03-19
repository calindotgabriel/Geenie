package ro.geenie.controller;

import android.content.Context;

import java.util.List;

import ro.geenie.models.Member;
import ro.geenie.models.exception.MemberProviderException;
import ro.geenie.models.orm.OrmController;

/**
 * Created by motan on 18.03.2015.
 */
public class MemberController extends OrmController{
    /***
     * Convention: the Owner always stays on index 1 @ local db.
     */

    public MemberController(Context context) {
        super(context);
    }

    public boolean isMemberValid(String memberName) throws MemberProviderException{
        boolean valid = false;
        List<Member> allMembers = getHelper().getMemberDao().queryForAll();
        for (Member m : allMembers) {
            if (m.getName().equals(memberName)) {
                valid = true;
                break;
            }
        }
        return valid;
    }

}
