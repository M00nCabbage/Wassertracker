package com.example.sound1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button soundButton;
    private Button shakeButton;
    private TextView textView;
    private static int MICROPHONE_PERMISSION_CODE=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text);
        soundButton=findViewById(R.id.SoundButton);
        shakeButton=findViewById(R.id.ShakeButton);

        webrequest();
        //Sounderkennung soundDetectionService= new Sounderkennung();


        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMicrophonePresent()){
                    getMicrophonePermission();

                    Intent soundIntent = new Intent(MainActivity.this, Sounderkennung2.class);
                    startActivity(soundIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Schließe ein Mikrophon an", Toast.LENGTH_SHORT).show();
                }
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


    private void webrequest(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request=new Request.Builder().url("http://132.180.217.157:5000").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                textView.setText("nicht mit Server verbunden");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //textView.setText(response.body().string());
                textView.setText("mit google verbunden");
            }
        });
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

   /* protected void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent(this,
                SoundDetectionService.class);
        stopService(serviceIntent);
    }*/
}