package com.example.mvpandsensorexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;

    private TextView textViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);
        presenter.startLocationService();

        textViewLocation = findViewById(R.id.textViewLocation);
    }

    @Override
    protected void onStop() {
        presenter.stopLocationService();
        super.onStop();
    }

    public void updateLocationTextView(String location){
        textViewLocation.setText(location);
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