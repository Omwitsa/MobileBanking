package com.aratek.trustfinger.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aratek.trustfinger.R;
import com.aratek.trustfinger.fragment.IdentifyFragment;

public class TransactionActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        initFragment();

    }

    private void initFragment() {
        android.support.v4.app.FragmentManager fragmentManager = TransactionActivity.this.getSupportFragmentManager();
        IdentifyFragment fragment = new IdentifyFragment();
        //fm.beginTransaction().add(R.id.details_frame, fragment).commit();
        if(fragment == null) {
            fragment = new IdentifyFragment();
        }
        else {
            fragment = new IdentifyFragment();
        }
        fragmentManager.beginTransaction().add(R.id.details, fragment).commit();
    }
}