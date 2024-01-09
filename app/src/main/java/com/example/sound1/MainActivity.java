package com.example.sound1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button soundButton;
    private Button shakeButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text);
        soundButton=findViewById(R.id.SoundButton);
        shakeButton=findViewById(R.id.ShakeButton);
        //Sounderkennung soundDetectionService= new Sounderkennung();


        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent soundIntent = new Intent(MainActivity.this, Sounderkennung.class);
                startActivity(soundIntent);
            }
        });

        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shakeIntent = new Intent(MainActivity.this, NewVibrationsSensor.class);
                startActivity(shakeIntent);
            }
        });
    }

   /* protected void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent(this,
                SoundDetectionService.class);
        stopService(serviceIntent);
    }*/
}