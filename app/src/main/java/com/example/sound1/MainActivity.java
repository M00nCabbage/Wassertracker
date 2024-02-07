package com.example.sound1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button soundButton;
    private Button shakeButton;
    private TextView textView;
    private TextView amplitudeTextView;
    private static int MICROPHONE_PERMISSION_CODE=200;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text);
        soundButton=findViewById(R.id.SoundButton);
        shakeButton=findViewById(R.id.ShakeButton);

        queue = Volley.newRequestQueue(this);
        webrequest();
        amplitudeTextView=findViewById(R.id.text);


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
    String URL="http://192.168.178.60:5000";

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, URL, null,
            new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    textView.setText(response.toString());
                }
            }, new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("DEBUG", ""+error);
        }
    });
    queue.add(jsonObjectRequest);
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
}