package com.miti.meeti.MitiExecutors;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.core.app.NotificationCompat;

import com.miti.meeti.MainActivity;
import com.miti.meeti.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MitiService {
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = MainActivity.MainActivityContext.getString(R.string.channel_name);
//            String description = MainActivity.MainActivityContext.getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = MainActivity.MainActivityContext.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
        }else{

        }
    }
    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
//        Intent intent = new Intent(this, NotificationReceiverActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake

        Notification noti = new Notification.Builder(MainActivity.MainActivityContext)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject").setSmallIcon(R.drawable.ic_bell).build();
        NotificationManager notificationManager = (NotificationManager) MainActivity.MainActivityContext.getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, noti);

    }
}
