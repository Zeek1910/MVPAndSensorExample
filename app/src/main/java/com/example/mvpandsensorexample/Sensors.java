package com.example.mvpandsensorexample;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class Sensors implements SensorEventListener {

    private Context context;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor light;
    private OnSensorValueChangeListener onSensorValueChangeListener;

    public Sensors(Context context){
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(accelerometer != null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(context,"Your device does't have accelerometer sensor",Toast.LENGTH_SHORT).show();
        }
        if(light != null){
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(context,"Your device does't have light sensor",Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnSensorValueChangeListener(OnSensorValueChangeListener onSensorValueChangeListener) {
        this.onSensorValueChangeListener = onSensorValueChangeListener;
    }

    public String getAccelerometerSensorName(){
        return accelerometer.getName();
    }

    public String getLightSensorName(){
        return light.getName();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(onSensorValueChangeListener != null){
            switch (event.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    onSensorValueChangeListener.onAccelerometerSensorValueChange(event.values[0],event.values[1],event.values[2]);
                    break;
                case Sensor.TYPE_LIGHT:
                    onSensorValueChangeListener.onLightSensorValueChange(event.values[0]);
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface OnSensorValueChangeListener {
        void onAccelerometerSensorValueChange(float x, float y, float z);
        void onLightSensorValueChange(float lightLevel);
    }
}
