package com.example.sound1;

import android.app.Notification;
        import android.app.NotificationChannel;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.app.Service;
        import android.content.Intent;
        import android.media.MediaRecorder;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.IBinder;
        import android.os.Looper;
        import android.widget.TextView;
        import android.widget.Toast;
        import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
public class Sounderkennung extends AppCompatActivity {
    private static final String CHANNEL_ID = "SoundDetectionChannel";
    private MediaRecorder mediaRecorder;
    private Handler handler;
    private Boolean isSoundDetected=false;
    private static final long TIME_INTERVAL = 30 * 60 * 1000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startRecording();
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
