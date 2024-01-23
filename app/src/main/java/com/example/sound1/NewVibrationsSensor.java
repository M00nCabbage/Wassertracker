package com.example.sound1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewVibrationsSensor extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private double SHAKE_THRESHOLD=4;
    private TextView textView;
    private Button soundButton;
    private Button shakeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text);
        textView.setText(("Noch keine Vibration erkannt"));
        soundButton=findViewById(R.id.SoundButton);
        soundButton.setBackgroundColor(Color.GRAY);
        shakeButton=findViewById(R.id.ShakeButton);
        shakeButton.setText("Stopp");
        shakeButton.setBackgroundColor(Color.RED);

        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent soundIntent = new Intent(NewVibrationsSensor.this, MainActivity.class);
                startActivity(soundIntent);
            }
        });



        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] accelertion= event.values;
        double accelerationMagniture=0;
        for(int i=0; i<accelertion.length; i++){
            accelerationMagniture += Math.pow(accelertion[i], 2);
            accelerationMagniture = Math.sqrt(accelerationMagniture);
        }

        if(accelerationMagniture > SHAKE_THRESHOLD){
            textView.setText("es wurde Vibration erkannt");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
