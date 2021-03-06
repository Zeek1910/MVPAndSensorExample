package com.example.mvpandsensorexample;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

public class MainActivityPresenter implements Sensors.OnSensorValueChangeListener{

    public static final int PERMISSION_LOCATION_REQUEST_CODE = 111;

    private MainActivity view;
    private BroadcastReceiver broadcastReceiver;

    public MainActivityPresenter(MainActivity view) {
        this.view = view;
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent != null){
                    Bundle bundle = intent.getExtras();
                    if(bundle != null){
                        Location location = (Location) bundle.get(LocationTrackerService.KEY_LOCATION);
                        if (location != null){
                            view.updateLocationTextView("Latitude: " + location.getLatitude()+ "\nLongitude: "+ location.getLongitude());
                        }
                    }
                }
            }
        };
        view.registerReceiver(broadcastReceiver,new IntentFilter(LocationTrackerService.BROADCAST_ACTION));

        Sensors sensors = new Sensors(view.getBaseContext());
        sensors.setOnSensorValueChangeListener(this);
        view.updateAccelerometerSensorName(sensors.getAccelerometerSensorName());
        view.updateLightSensorName(sensors.getLightSensorName());
    }
    public void startLocationService(){
        if (ActivityCompat.checkSelfPermission(view.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            view.startService(new Intent(view.getApplicationContext(), LocationTrackerService.class));
        }else{
            ActivityCompat.requestPermissions(view, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);
        }
    }

    public void stopLocationService(){
        view.stopService(new Intent(view.getBaseContext(), LocationTrackerService.class));
    }

    @Override
    public void onAccelerometerSensorValueChange(float x, float y, float z) {
        String sensorValue = "X: " + x + "\nY: " + y + "\nZ: " + z;
        view.updateAccelerometerSensorValue(sensorValue);
    }

    @Override
    public void onLightSensorValueChange(float lightLevel) {
        String sensorValue = lightLevel + " lux";
        view.updateLightSensorValue(sensorValue);
    }
}
