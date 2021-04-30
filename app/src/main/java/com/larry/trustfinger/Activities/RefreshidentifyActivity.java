package com.larry.trustfinger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.larry.trustfinger.R;

public class RefreshidentifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshidentify);
        Intent intent = new Intent(getApplicationContext(), IdentificationActivity.class);
        startActivity(intent);

    }
}