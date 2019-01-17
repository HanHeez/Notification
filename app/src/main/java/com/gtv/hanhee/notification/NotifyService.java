package com.gtv.hanhee.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class NotifyService extends Service {

    /**
     * Class for clients to access
     */
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    // Unique id to identify the notification.
    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFY";
    // The system notification manager
    private NotificationManager mNM;
    private String title;
    private String content;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if(intent.getBooleanExtra(INTENT_NOTIFY, false)) {
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            showNotification(intent.getIntExtra("NotificationId", 0));
        }

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification(int requestCode) {
        // This is the 'title' of the notification
        // This is the icon to use on the notification
        int icon = R.drawable.baseline_notification_important_black_18dp;
        // This is the scrolling text of the notification

        // What time to show on the notification
        long time = System.currentTimeMillis();


        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SecondActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .setSmallIcon(R.drawable.baseline_notification_important_black_18dp)
                .build();

              // Clear the notification when it is pressed
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Send the notification to the system.
        mNM.notify(requestCode, notification);

        // Stop the service when we are finished
        stopSelf();
    }
}
