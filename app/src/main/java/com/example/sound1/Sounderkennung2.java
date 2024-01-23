package com.example.sound1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Sounderkennung2 extends AppCompatActivity {

    private TextView text;
    private Button soundButton;
    private Button shakeButton;
    private static int MICROPHONE_PERMISSION_CODE=200;
    MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);


                soundButton=findViewById(R.id.SoundButton);
                soundButton.setBackgroundColor(Color.RED);

                shakeButton=findViewById(R.id.ShakeButton);
                shakeButton.setBackgroundColor(Color.GRAY);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    stopRecording();
                    Toast.makeText(Sounderkennung2.this, "Recording has ended", Toast.LENGTH_LONG).show();
                    Intent soundIntent = new Intent(Sounderkennung2.this, MainActivity.class);
                    startActivity(soundIntent);
            }
        });

        if (isMicrophonePresent()) {
            getMicrophonePermission();
            startRecording();

        }



    }

    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else{
            return false;
        }
    }

    private void getMicrophonePermission () {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                ==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    MICROPHONE_PERMISSION_CODE);

        }
    }
    //Sound von Skipy leran in Svm schmeisen
    //Beschleunigung, Kompassapp livedemonstration
    //evtl. statt Fouriertransformation mit Zeitreihe, FFt für Java
    //Testdaten am Anfang von einer Stelle primär, später variable
    //bis nächste Woche aufgezeichnete Daten+App
    public void startRecording(){

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingFilePath()); //Idee in Buffer, evtl direkt von Mediarecorder, evtl. mit 2 Files
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();

            Toast.makeText(this, "Recording has startet", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Recording has not startet", Toast.LENGTH_LONG).show();
        }

    }

    public void stopRecording(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper =new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "testRecordingFile" + "mp3");
        return file.getPath();
    }
}