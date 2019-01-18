package com.gtv.hanhee.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Random;

public class AlarmTask implements Runnable{
    // The date selected for the alarm
    private final Calendar date;
    // The android system alarm manager
    private final AlarmManager am;
    // Your context to retrieve the alarm manager from
    private final Context context;
    private int requestCode;
    private String title;
    private String content;

    public AlarmTask(Context context, Calendar date, int requestCode, String title, String content) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
        this.requestCode = requestCode;
        this.title = title;
        this.content = content;
    }

    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra("NotificationId", requestCode);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, date.getTimeInMillis(), pendingIntent);
    }
}
