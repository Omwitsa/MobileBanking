package com.aratek.trustfinger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aratek.trustfinger.R;

public class RefreshOperatorFingerprintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_operator_fingerprint);
        Intent intent = new Intent(getApplicationContext(), OperatoerFingerprintActivity.class);
        startActivity(intent);
    }
}