package com.aratek.trustfinger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aratek.trustfinger.R;

public class RefreshVerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_verify);
        Intent intent = new Intent(getApplicationContext(), FingeprintActivity.class);
        startActivity(intent);
    }
}