package com.example.sound1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
public class VibrationSensorActivity extends AppCompatActivity implements
        SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isVibrating = false;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer =
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
// Calculate acceleration magnitude
        double magnitude = Math.sqrt(x * x + y * y + z * z);
        double threshold = 15.0;
        if (magnitude > threshold && !isVibrating) {
            vibrator.vibrate(500);
            isVibrating = true;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}