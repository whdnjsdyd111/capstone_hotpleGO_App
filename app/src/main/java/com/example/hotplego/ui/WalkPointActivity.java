package com.example.hotplego.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.hotplego.R;

import static android.hardware.Sensor.TYPE_STEP_COUNTER;

public class WalkPointActivity extends AppCompatActivity implements CircleProgressBar.ProgressFormatter, SensorEventListener {

    private static final String DEFAULT_PATTERN="%d%%";

    CircleProgressBar circleProgressBar;
    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    TextView stepCount;

    @SuppressLint("ServiceCast")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_point);

        stepCount = (TextView)findViewById(R.id.textViewwalk);

        circleProgressBar = findViewById(R.id.progressBar);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

    }

  @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        float step = event.values[0];
        step = (float) (Math.round(step)/100);
        stepCount.setText(String.valueOf(step) + "p");
        circleProgressBar.setProgress((int)step);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}