package com.example.sound1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent serviceIntent = new Intent(this,
                SoundDetectionService.class);
        startService(serviceIntent);
    }
    protected void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent(this,
                SoundDetectionService.class);
        stopService(serviceIntent);
    }
}