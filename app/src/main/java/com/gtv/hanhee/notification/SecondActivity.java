package com.gtv.hanhee.notification;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        txt= (TextView) findViewById(R.id.txt1);
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        txt.setText("Notification ID: "+getIntent().getExtras().getInt("notificationID"));
//        // Cancel notification
//        nm.cancel(getIntent().getExtras().getInt("notificationID"));
    }
}
