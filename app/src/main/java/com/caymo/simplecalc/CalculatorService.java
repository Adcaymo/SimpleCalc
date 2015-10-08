package com.caymo.simplecalc;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class CalculatorService extends Service {

    private Timer timer;

    @Override
    public void onCreate() {
        //Log.i("Calculator", "Service created");
        startTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.i("Calculator", "Service started");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Log.i("Calculator", "Service bound - not used!");
        return null;
    }

    @Override
    public void onDestroy() {
        //Log.i("Calculator", "Service destroyed");
        stopTimer();
    }

    private void startTimer() {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                //Log.i("Calculator", "Timer task started");
                sendNotification("Buy Ad-Free Version today!");
            }
        };

        timer = new Timer(true);
        int delay = 10 * 60 * 60;      // 36 seconds
        int interval = 10 * 60 * 60;   // 36 seconds
        timer.schedule(task, delay, interval);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void sendNotification(String text) {
        // create the intent for the notification
        Intent notificationIntent = new Intent(this, CalculatorActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // create the pending intent
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, flags);

        // create the variables for the notification
        int icon = R.mipmap.ic_launcher;
        CharSequence tickerText = "Testing ticker text";
        CharSequence contentTitle = getText(R.string.app_name);
        CharSequence contentText = text;

        // create the notification and set its data
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(icon)
                        .setTicker(tickerText)
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();

        // display the notification
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        final int NOTIFICATION_ID = 1;
        manager.notify(NOTIFICATION_ID, notification);
    }
}