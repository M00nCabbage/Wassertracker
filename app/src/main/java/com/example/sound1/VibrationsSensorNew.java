package com.example.sound1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VibrationsSensorNew extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private double SHAKE_THRESHOLD=4;
    private TextView textView;
    private Button shakeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text);
        shakeButton=findViewById(R.id.ShakeButton);
        shakeButton.setText("Jetzt wird Vibration erkannt");
        textView.setText(("in VibrationsSensorNew class"));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
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
