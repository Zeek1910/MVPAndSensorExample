package com.example.mvpandsensorexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;

    private TextView textViewLocation, textViewAccelerometerSensorName, textViewAccelerometerValue,
            textViewLightSensorName, textViewLightSensorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLocation = findViewById(R.id.textViewLocation);
        textViewAccelerometerSensorName = findViewById(R.id.textViewAccelerometerSensorName);
        textViewAccelerometerValue = findViewById(R.id.textViewAccelerometerValue);
        textViewLightSensorName = findViewById(R.id.textViewLightSensorName);
        textViewLightSensorValue = findViewById(R.id.textViewLightSensorValue);

        presenter = new MainActivityPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startLocationService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopLocationService();
    }

    public void updateLocationTextView(String location){
        textViewLocation.setText(location);
    }

    public void updateAccelerometerSensorName(String sensorName){
        textViewAccelerometerSensorName.setText(sensorName);
    }

    public void updateAccelerometerSensorValue(String sensorValue){
        textViewAccelerometerValue.setText(sensorValue);
    }

    public void updateLightSensorName(String sensorName){
        textViewLightSensorName.setText(sensorName);
    }

    public void updateLightSensorValue(String sensorValue){
        textViewLightSensorValue.setText(sensorValue);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==MainActivityPresenter.PERMISSION_LOCATION_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                presenter.startLocationService();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}