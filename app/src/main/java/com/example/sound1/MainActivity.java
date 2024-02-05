package com.example.sound1;

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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button soundButton;
    // private Button shakeButton;
    private TextView textView;
    private Button stopButton;
    private TextView amplitudeTextView;
    private static int MICROPHONE_PERMISSION_CODE=200;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text);
        soundButton= findViewById(R.id.soundButton);
        stopButton=findViewById(R.id.stopButton);
       // shakeButton=findViewById(R.id.ShakeButton);
        amplitudeTextView=findViewById(R.id.text);
        //Sounderkennung soundDetectionService= new Sounderkennung();


        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMicrophonePresent()){
                    getMicrophonePermission();

                    Intent soundIntent = new Intent(MainActivity.this, Sounderkennung2.class);
                    startActivity(soundIntent);
                    // notification code block

                    Toast.makeText(MainActivity.this, "Don't forget to drink Water!", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(MainActivity.this,NotificationCostume.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent, PendingIntent.FLAG_IMMUTABLE);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    long timeAtBottonClick = System.currentTimeMillis();
                    long oneMinInMilis = 1000 * 60 ;

                    alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtBottonClick + oneMinInMilis,pendingIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Schließe ein Mikrophon an", Toast.LENGTH_SHORT).show();



                }
            }
        });
      /**stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intent = new Intent(MainActivity.this, Sounderkennung2.class);

                }
                {
                    Toast.makeText(MainActivity.this, "Schließe ein Mikrophon an", Toast.LENGTH_SHORT).show();
                }
            });

        /**shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shakeIntent = new Intent(MainActivity.this, NewVibrationsSensor.class);
                startActivity(shakeIntent);
            }
        });  **/
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
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                ==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    MICROPHONE_PERMISSION_CODE);

        }
    }
    // hier geht's weiter mit notification code block
    private void createNotificationChanel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WaterReminderChanel";
            String description= "Don't forget to drink water";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel = new NotificationChannel("notifyWater",name,importance);
            chanel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chanel);
        }


    }
   /* protected void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent(this,
                SoundDetectionService.class);
        stopService(serviceIntent);
    }*/
}