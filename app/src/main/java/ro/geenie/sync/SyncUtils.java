package ro.geenie.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

import ro.geenie.auth.AuthenticatorService;
import ro.geenie.provider.PostContract;

/**
 * Created by motan on 08.03.2015.
 */
public class SyncUtils {

    private static final String CONTENT_AUTHORITY = PostContract.AUTHORITY;
    private static final String PREF_SETUP_COMPLETE = "setup_complete";
    public static final String ACCOUNT_TYPE = "ro.geenie.sync";
    private static final long SYNC_FREQUENCY = 60 * 60 * 4;  // every 4 hours (in seconds)



    public static void CreateSyncAccount(Context context) {
        boolean newAccount = false;
        boolean setupComplete = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

        Account account = AuthenticatorService.GetAccount(ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager)
                context.getSystemService(Context.ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(account, null, null)) { // TODO password
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, new Bundle(), SYNC_FREQUENCY);
            newAccount = true;
        }

        if (newAccount || !setupComplete) { // we got a problem
            TriggerRefresh();
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean(PREF_SETUP_COMPLETE, true).commit();
        }

    }

    public static void TriggerRefresh() {
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                AuthenticatorService.GetAccount(ACCOUNT_TYPE),
                CONTENT_AUTHORITY,
                b
        );
    }
}
