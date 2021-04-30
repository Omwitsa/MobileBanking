package com.larry.trustfinger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.larry.trustfinger.R;

public class RefreshFingerprintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_fingerprint);
        System.exit(0);
        Intent intent = new Intent(getApplicationContext(), FingeprintActivity.class);
        startActivity(intent);
    }
}