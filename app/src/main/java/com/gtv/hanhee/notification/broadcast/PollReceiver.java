package com.gtv.hanhee.notification.broadcast;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;

public class PollReceiver extends BroadcastReceiver {
    private static final int PERIOD=900000; // 15 minutes
    private static final int INITIAL_DELAY=5000; // 5 seconds

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager mgr= (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(context, ScheduledService.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 1, i, 0);
        // we set the time to midnight (i.e. the first minute of that day)

        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, 13);
        c.set(Calendar.MINUTE, 29);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Calendar d = Calendar.getInstance();

        d.set(Calendar.HOUR_OF_DAY, 13);
        d.set(Calendar.MINUTE, 29);
        d.set(Calendar.SECOND, 0);
        d.set(Calendar.MILLISECOND, 0);

//        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
//            calendar.add(Calendar.DATE, 1);
//        }

        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + INITIAL_DELAY,
                PERIOD, pi);

    }

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    static void scheduleAlarms(Context context, Intent intent) {

    }
}
