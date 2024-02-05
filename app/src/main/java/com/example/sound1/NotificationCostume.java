package com.example.sound1;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;

import android.content.Context;
import android.os.Build;




public class NotificationCostume extends android.app.Notification {

    private static final String CHANNEL_ID = "SoundDetectionChannel";
    private static final int NOTIFICATION_ID = 123;

    public static void createNotification(Context context, String title, String content) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context,"notifyWater");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Sound Detection", NotificationManager.IMPORTANCE_DEFAULT);
            notification.setNotificationSilent();
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)

                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = null;
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}

