package com.example.sound1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent serviceIntent = new Intent(this,
                SoundDetectionService.class);
        startService(serviceIntent);

        textView=findViewById(R.id.text);
        button=findViewById(R.id.startbutton);
        SoundDetectionService soundDetectionService= new SoundDetectionService();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(button.getText().toString()!=
                 //       "Jetzt wird Sound aufgenommen"){
                    button.setText("Jetzt wird Sound aufgenommen");


                    textView.setText("Die aktuelle Amplitude ist: " +
                            Double.toString(soundDetectionService.getAmplitude()));
              //  }
            /*    else{
                    button.setText("Start recording");
                }*/
            }
        });
    }
    protected void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent(this,
                SoundDetectionService.class);
        stopService(serviceIntent);
    }
}