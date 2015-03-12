package ro.geenie.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import ro.geenie.R;
import ro.geenie.views.activities.AssignmentsActivity;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent arg1) {

        Intent intent = new Intent(context, AssignmentsActivity.class);
        intent.putExtra("id", arg1.getIntExtra("id", 0));
        PendingIntent pintent = PendingIntent.getActivity(context, arg1.getIntExtra("id", 0), intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_check_dark)
                .setContentTitle("Get to work!")
                .setContentText(arg1.getStringExtra("name"))
                .addAction(R.drawable.ic_check_dark, "Done", pintent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(arg1.getIntExtra("id", 0), mBuilder.build());

    }

}