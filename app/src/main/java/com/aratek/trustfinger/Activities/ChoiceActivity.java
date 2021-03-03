package com.aratek.trustfinger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aratek.trustfinger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoiceActivity extends AppCompatActivity {
    @BindView(R.id.main1) Button First;
    @BindView(R.id.main2) Button Second;
    @BindView(R.id.main3) Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        ButterKnife.bind(this);

        First.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IdentificationActivity.class);
                startActivity(intent);
            }
        });
        Second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Deposit2Account.class);
                startActivity(intent);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterPhone.class);
                startActivity(intent);
            }
        });
    }
}