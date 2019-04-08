package com.kubatov.serviceapp;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class MyIntentService extends IntentService {

    boolean stopped;
    public MyIntentService() {
        super("myIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int i;
        if (intent !=null) {
           i =  intent.getIntExtra("number", 10);

            startTask(i);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
        stopped = true;
    }


    private void startTask(int count){
        showNotification();
        for (int i = 0; i < count; i++){
            if (stopped)
                break;

            Log.e("Tag", "i :" + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void showNotification(){

        /*NotificationChannel channel = new NotificationChannel("myChannel", "not", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);*/

       /* NotificationCompat.Builder notification = new NotificationCompat.Builder(getBaseContext(), "myChannel");
        notification.setContentTitle("Service App");
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setContentText("Loading...");
        startForeground(1, notification.build());*/

        RemoteViews bigView = new RemoteViews(getPackageName(), R.layout.music_notification);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myChannel");
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 12345, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.ic_play_circle_outline_black_24dp);
        builder.setAutoCancel(true);
        builder.setCustomContentView(bigView);
        builder.setCustomBigContentView(bigView);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText("now is playing...");
        startForeground(1, builder.build());
    }

}
