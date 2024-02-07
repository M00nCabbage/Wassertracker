package com.example.sounderz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatButton;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;


import android.os.Bundle;

import java.util.Locale;


public class MainActivity extends AppCompatActivity  {

    private Button soundbutton;
    private TextView shakeText;
    private static final long oneMin = 60000;
    private TextView textViewCountDown;
    private boolean timerRunning;
    private long timeLeftInMillis = oneMin;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewCountDown = (findViewById(R.id.textViewCountDown));
        soundbutton = (findViewById(R.id.soundButton));
        shakeText = (findViewById(R.id.shakeText));


        soundbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {

                Intent magnetIntent = new Intent(MainActivity.this, Magnetsensor.class);
                startActivity(magnetIntent);
            }
        });



        soundbutton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                timeLeftInMillis = oneMin;

                resetTimer();
                soundbutton.setText("Start Tracking");


                return false;
            }
        });
    }





        private synchronized void startTimer(){
            CountDownTimer countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public synchronized void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateCountdownText();
                    timerRunning = true;
                }

                @Override
                public void onFinish() {

                    timerRunning = false;
                }
            }.start();

    }


            private boolean pauseTimer(){
               return timerRunning= false ;

            }
            private void resetTimer(){
            timeLeftInMillis= oneMin;
            updateCountdownText();

            }
            private void updateCountdownText(){
            int minutes = (int)(timeLeftInMillis/1000)/60;
            int seconds = (int)(timeLeftInMillis/1000)%60;
            String timeLeftFormatted= String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
            textViewCountDown.setText(timeLeftFormatted);

        }



    }



