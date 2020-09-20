package com.example.smartalarm;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class goToSleep extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Button option5;
    private Button option6;

    private int sleepDelay = 15;
    private final int MINIMUM_FIRST_CYCLE_DURATION = 70;
    private final int MAXIMUM_FIRST_CYCLE_DURATION = 100;
    private final int MINIMUM_LATER_CYCLE_DURATION = 90;
    private final int MAXIMUM_LATER_CYCLE_DURATION = 120;
    private final int firstCycleDuration = 85;
    private final int laterCyclesDuration = 105;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_to_sleep);

        option1 = findViewById(R.id.option1_button);
        option2 = findViewById(R.id.option2_button);
        option3 = findViewById(R.id.option3_button);
        option4 = findViewById(R.id.option4_button);
        option5 = findViewById(R.id.option5_button);
        option6 = findViewById(R.id.option6_button);

        final Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        calendar.add(Calendar.MINUTE, sleepDelay);

        setAlarmOptions(calendar, option1, firstCycleDuration);
        setAlarmOptions(calendar, option2, laterCyclesDuration);
        setAlarmOptions(calendar, option3, laterCyclesDuration);
        setAlarmOptions(calendar, option4, laterCyclesDuration);
        setAlarmOptions(calendar, option5, laterCyclesDuration);
        setAlarmOptions(calendar, option6, laterCyclesDuration);

        calendar.setTime(trialTime);

        setAlarm(calendar, option1, 1);
        setAlarm(calendar, option2, 2);
        setAlarm(calendar, option3, 3);
        setAlarm(calendar, option4, 4);
        setAlarm(calendar, option5, 5);
        setAlarm(calendar, option6, 6);

        notificationManager = NotificationManagerCompat.from(this);

    }

    private void setAlarmOptions(Calendar calendar, Button option, int duration) {
        calendar.add(Calendar.MINUTE, duration);
        if (calendar.get(Calendar.MINUTE) < 10){
            option.setText(calendar.get(Calendar.HOUR_OF_DAY)+" : 0"+ calendar.get(Calendar.MINUTE));
        } else {
            option.setText(calendar.get(Calendar.HOUR_OF_DAY)+" : "+ calendar.get(Calendar.MINUTE));
        }
    }

    private void setAlarm(final Calendar calendar, Button option, final int i) {
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MINUTE, sleepDelay + firstCycleDuration + (i-1) * laterCyclesDuration);
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
                intent.putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE));

                startActivity(intent);
            }
        });
    }

    public void sendOnChannel1(View view) {
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Alarm")
                .setContentText("Are you fresh?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1, notification);

    }
}
