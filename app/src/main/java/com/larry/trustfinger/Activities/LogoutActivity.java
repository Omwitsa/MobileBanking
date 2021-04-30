package com.larry.trustfinger.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.larry.trustfinger.R;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        finishAffinity();
        System.exit(0);
    }
}