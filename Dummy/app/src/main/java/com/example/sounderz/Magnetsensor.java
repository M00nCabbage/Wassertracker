package com.example.sounderz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Magnetsensor extends AppCompatActivity implements SensorEventListener {

    private CountDownTimer timer;
    private static SensorManager sensorManager;
    private Sensor sensor;

    private TextView magnetText;
    private TextView timeText;
    private Button button;

    private boolean bottle=false;
    private boolean drinking=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopsec);

        magnetText = (findViewById(R.id.shakeText));
        timeText=(findViewById(R.id.textViewCountDown));
        button = (findViewById(R.id.stopButton));

        long millisInFuture = 1000*60;

        if (timer != null){
            timer.cancel();
        }

        timer=new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes =millisUntilFinished/1000/60;
                long seconds =(millisUntilFinished/1000)%60;
                timeText.setText(minutes + ":" + seconds);
            }

            @Override
            public void onFinish() {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
                v.vibrate(5000);
                Toast.makeText(Magnetsensor.this, "Drink something", Toast.LENGTH_LONG).show();
                Intent magnetIntent = new Intent(Magnetsensor.this, MainActivity.class);

                startActivity(magnetIntent);
            }
        };
        timer.start();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {

                Intent magnetIntent = new Intent(Magnetsensor.this, MainActivity.class);
                startActivity(magnetIntent);
            }
        });
    }


    protected void onResume(){
        super.onResume();
        if(sensor != null){
            sensorManager.registerListener(Magnetsensor.this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(Magnetsensor.this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth = Math.round(event.values[0]);
        float pitch = Math.round(event.values[1]);
        float roll = Math.round(event.values[2]);

        double tesla=Math.sqrt(azimuth*azimuth+pitch*pitch+roll*roll);

        if (tesla>80){                  //je nachdem wie stark das Erdmagnetfeld ist/Elektromagnetfeld, muss die Mikroteslaanzahl angepasst werden.
           if(drinking&& tesla>80){     //auch Plus und Minuspol beachten, ggf. > und < vertauschen
               if (timer != null){
                   timer.cancel();
               }
               Intent magnetIntent = new Intent(Magnetsensor.this, Magnetsensor.class);
               startActivity(magnetIntent);
           }
               magnetText.setText("Bottle recognized");
               bottle=true;
               startTimer();

        }
        else if (tesla<80&&bottle){
            magnetText.setText("Kepp on Going! ");
            drinking=true;
        }
        else{
            magnetText.setText("Please place your bottle with a magnet");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void startTimer(){

    }
}