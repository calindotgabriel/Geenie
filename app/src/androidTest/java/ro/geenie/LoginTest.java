package ro.geenie;

import android.app.Application;
import android.test.ApplicationTestCase;

import ro.geenie.controller.LocalOwnerController;
import ro.geenie.controller.MemberController;
import ro.geenie.models.Member;
import ro.geenie.models.exception.MemberProviderException;
import ro.geenie.models.exception.NoOwnerException;

/**
 * Created by motan on 09.03.2015.
 */
public class LoginTest extends ApplicationTestCase<Application> {


    public LoginTest() {
        super(Application.class);
    }

    public void testValidLogin() throws MemberProviderException, NoOwnerException{
        String enteredMemberName = "ion";

        MemberController memberController = new MemberController(getContext());
        LocalOwnerController ownerController = new LocalOwnerController(getContext());

        if (memberController.isMemberValid(enteredMemberName)) {
            ownerController.registerLocalOwner(enteredMemberName);
        }

        Member owner = ownerController.getOwner();
        assertEquals(enteredMemberName, owner.getName());

    }

    public void testMockOwnerRegistration() throws NoOwnerException{
        LocalOwnerController ownerController = new LocalOwnerController(getContext());
        String mockUserName = "ionutz likid";
        ownerController.registerLocalOwner(mockUserName);
        assertTrue(ownerController.isOwnerRegistered());
        assertEquals(ownerController.getOwner().getName(), mockUserName);
    }

}
