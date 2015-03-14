package ro.geenie.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by motan on 08.03.2015.
 */
public class AuthenticatorService extends Service {

    private Authenticator authenticator;
    public static final String ACCOUNT_NAME = "Gigel";

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }


}
