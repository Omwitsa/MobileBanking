package com.example.mobilebanking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobilebanking.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.deposit) CardView deposit;
    @BindView(R.id.withdraw) CardView withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);



        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DepositActivity.class);
                //Intent intent = new Intent(getApplicationContext(), Reports.class);

                startActivity(intent);
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);

                startActivity(intent);
            }
        });
    }
}