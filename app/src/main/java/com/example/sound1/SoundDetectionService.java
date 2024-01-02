package com.example.sound1;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
public class SoundDetectionService extends Service {
    private static final String CHANNEL_ID = "SoundDetectionChannel";
    private static final int NOTIFICATION_ID = 123;
    private static final long TIME_INTERVAL = 30 * 60 * 1000;
    private MediaRecorder mediaRecorder;
    private Handler handler;
    private boolean isSoundDetected = false;
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this,
                CHANNEL_ID)
                .setContentTitle("Sound Detection Service")
                .setContentText("Listening for sound...")
// .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(NOTIFICATION_ID, notification);
        handler = new Handler(Looper.getMainLooper());
        startRecording();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sound Detection";
            String description = "Channel for Sound Detection Service";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new
                    NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile("/dev/null");
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            updateSoundDetection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateSoundDetection() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                double amplitude = getAmplitude();

                if (amplitude > 0) {
                    isSoundDetected = true;
                    resetTimer();
                } else {
                    if (!isSoundDetected) {
                        notifyNoSoundDetected();
                    }
                    isSoundDetected = false;
                }
                updateSoundDetection();
            }
        }, TIME_INTERVAL);
    }
    protected double getAmplitude() {
        if (mediaRecorder != null) {
            return 20 * Math.log10(mediaRecorder.getMaxAmplitude() /
                    32767.0);
        }
        return 0;
    }
    private void resetTimer() {
        // Reset the timer if sound is detected within the time interval
        handler.removeCallbacksAndMessages(null);
        updateSoundDetection();
    }
    private void notifyNoSoundDetected() {
// Notify user if no sound is detected within the time interval
        Toast.makeText(this, "No sound detected for 30 minutes!",
                Toast.LENGTH_SHORT).show();
// You can also trigger a notification here
// Create and show a notification to alert the user about no sound
// detection
// ...
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        handler.removeCallbacksAndMessages(null);
    }
}
