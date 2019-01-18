package com.gtv.hanhee.notification.broadcast;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.gtv.hanhee.notification.NotifyService;
import com.gtv.hanhee.notification.R;
import com.gtv.hanhee.notification.ScheduleClient;
import com.gtv.hanhee.notification.SecondActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ScheduleClient scheduleClient;
    // This is the date picker used to select the date for our notification
    private DatePicker picker;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        // Get a reference to our date picker
        picker = (DatePicker) findViewById(R.id.scheduleTimePicker);

    }

    public void scheduleNotification2() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 0);
        cal.add(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);




    }
    public void scheduleNotification(Context context, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification
        Intent intent = new Intent(context, SecondActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Title1")
                .setContentText("content 1")
                .setAutoCancel(true)
                .setContentIntent(activity)
                .setOngoing(true)
                .setSmallIcon(R.drawable.baseline_notification_important_black_18dp)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        Intent notificationIntent = new Intent(context, PollReceiver.class);
        notificationIntent.putExtra(PollReceiver.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(PollReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, 0);

        Calendar c = Calendar.getInstance();

//        c.set(Calendar.HOUR_OF_DAY, 14);
        c.set(Calendar.MINUTE, 3);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
//
//        Calendar d = Calendar.getInstance();
//
//        d.set(Calendar.HOUR_OF_DAY, 13);
//        d.set(Calendar.MINUTE, 29);
//        d.set(Calendar.SECOND, 0);
//        d.set(Calendar.MILLISECOND, 0);


//        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent);
    }

    /**
     * This is the onClick called from the XML to set a new notification
     */
    public void onDateSelectedButtonClick(View v){
        scheduleNotification2();
        scheduleNotification(this, 0, 1);
        scheduleNotification(this, 0, 2);
        scheduleNotification(this, 0, 3);

//        // Get the date from our datepicker
//        int day = picker.getDayOfMonth();
//        int month = picker.getMonth();
//        int year = picker.getYear();
//
//        // Create a new calendar set to the date chosen
//        // we set the time to midnight (i.e. the first minute of that day)
//        Calendar c = Calendar.getInstance();
//
//        c.set(Calendar.HOUR_OF_DAY, 13);
//        c.set(Calendar.MINUTE, 29);
//        c.set(Calendar.SECOND, 0);
//        c.set(Calendar.MILLISECOND, 0);
//
//        Calendar d = Calendar.getInstance();
//
//        d.set(Calendar.HOUR_OF_DAY, 13);
//        d.set(Calendar.MINUTE, 29);
//        d.set(Calendar.SECOND, 0);
//        d.set(Calendar.MILLISECOND, 0);
//
//        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
//        scheduleClient.setAlarmForNotification(c, 1, "Title 1", "Noi dung 1");
//        scheduleClient.setAlarmForNotification(d, 2, "Title 2", "Noi dung 2");
//        scheduleClient.setAlarmForNotification(d, 3, "Title 3", "Noi dung 3, Noi dung 3 Noi dung 3 Noi dung 3Noi dung 3 Noi dung 3Noi dung 3 Noi dung 3");
//        // Notify the user what they just did
        Toast.makeText(this, "Notification set for: ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

    public void onCancelSchedule(View view) {
        Intent intent = new Intent(this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                1, intent, 0);
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
// Cancel the `PendingIntent` after you've canceled the alarm
        pendingIntent.cancel();
    }
}
