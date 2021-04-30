package com.larry.trustfinger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.larry.trustfinger.R;
import com.larry.trustfinger.Reports.AdvancesreportActivity;
import com.larry.trustfinger.Reports.DepositreportActivity;
import com.larry.trustfinger.Reports.WithdrawalsreportActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Reports extends AppCompatActivity {
    @BindView(R.id.deposit) CardView Deposit;
    @BindView(R.id.with) CardView Withdraw;
    @BindView(R.id.adv) CardView Advance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);


        Deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DepositreportActivity.class);
                startActivity(intent);

            }
        });

        Withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WithdrawalsreportActivity.class);
                startActivity(intent);
            }
        });
        Advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdvancesreportActivity.class);
                startActivity(intent);
            }
        });

    }





}
