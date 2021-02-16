package com.aratek.trustfinger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aratek.trustfinger.R;

public class RefreshEnrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_enroll);
        Intent intent = new Intent(getApplicationContext(), VerifyActivity.class);
        startActivity(intent);
    }
}