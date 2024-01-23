package com.example.sound1;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Sounderkennung extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private Handler handler;
    private Boolean isSoundDetected=false;
    private static final long TIME_INTERVAL = 30 * 60 * 1000;
    private TextView text;
    private Button soundButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundButton=findViewById(R.id.SoundButton);
        soundButton.setText("Stopp");
        
        try {
            startRecording();
        }
        catch (IOException | IllegalStateException e){
            e.printStackTrace();
        }

    }

    private void startRecording() throws IllegalStateException, IOException {
        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        mediaRecorder.setOutputFile("/dev/null");
        mediaRecorder.prepare();
        //mit folgender Zeile läuft es nicht mehr
        //text.setText("Vorm aufnehmen");

        try {
            mediaRecorder.start();
            text.setText("Es wird Ton aufgenommen");
            updateSoundDetection();
        } catch (Exception e) {
            e.printStackTrace();
            //auch hier Fehler
            //text.setText("im Catch Block");
        }
    }
    private void updateSoundDetection() {
        handler.postDelayed(new Runnable() {
            //Kommt nicht hierzu
            @Override
            public void run() {
                double amplitude = getAmplitude();

                if (amplitude > 0) {
                    isSoundDetected = true;

                } else {
                        text.setText("Noch nichts erkannt");
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
    private void notifyNoSoundDetected() {

        NotificationCostum.createNotification(this, "Water Reminder", "No sound detected for 1 minute.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}
