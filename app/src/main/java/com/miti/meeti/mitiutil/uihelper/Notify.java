package com.miti.meeti.mitiutil.uihelper;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.core.app.NotificationCompat;

import com.miti.meeti.MainActivity;
import com.miti.meeti.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notify {
    private static NotificationManager notificationManager;
    public static void Notification(Context c,String Message,int notificationid){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.MainActivityContext, "MEETi")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("MEETi")
                .setContentText(Message).setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = c.getString(R.string.channel_name);
            String description = c.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MEETi", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = MainActivity.MainActivityContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }else{
            notificationManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        }
        notificationManager.notify(notificationid,builder.build());
    }
}
