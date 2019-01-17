package com.gtv.hanhee.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

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

    /**
     * This is the onClick called from the XML to set a new notification
     */
    public void onDateSelectedButtonClick(View v){
        // Get the date from our datepicker
        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year = picker.getYear();

        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 07);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Calendar d = Calendar.getInstance();

        d.set(Calendar.HOUR_OF_DAY, 1);
        d.set(Calendar.MINUTE, 07);
        d.set(Calendar.SECOND, 0);
        d.set(Calendar.MILLISECOND, 0);

        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c, 1, "Title 1", "Noi dung 1");
        scheduleClient.setAlarmForNotification(d, 2, "Title 2", "Noi dung 2");
        scheduleClient.setAlarmForNotification(d, 3, "Title 3", "Noi dung 3, Noi dung 3 Noi dung 3 Noi dung 3Noi dung 3 Noi dung 3Noi dung 3 Noi dung 3");
        // Notify the user what they just did
        Toast.makeText(this, "Notification set for: "+ day +"/"+ (month+1) +"/"+ year, Toast.LENGTH_SHORT).show();
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
        PendingIntent pendingIntent = PendingIntent.getService(this,
                1, intent, 0);
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
// Cancel the `PendingIntent` after you've canceled the alarm
        pendingIntent.cancel();
    }
}
