package ro.geenie.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by motan on 08.03.2015.
 */
public class SyncService extends Service {

    private final String TAG = getClass().getName();

    private static final Object syncAdapterLock = new Object();
    private static PostSyncAdapter syncAdapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        synchronized (syncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = new PostSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    /**
     * Logging-only destructor.
     */
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }

    /**
     * Return Binder handle for IPC communication with {@link PostSyncAdapter}.
     *
     * <p>New sync requests will be sent directly to the PostSyncAdapter using this channel.
     *
     * @param intent Calling intent
     * @return Binder handle for {@link PostSyncAdapter}
     */
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
