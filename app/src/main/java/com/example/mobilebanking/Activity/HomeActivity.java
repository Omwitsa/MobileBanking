package com.example.mobilebanking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobilebanking.R;
import com.example.mobilebanking.myactivities.Reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.deposit) CardView deposit;
    @BindView(R.id.withdraw) CardView withdraw;
    @BindView(R.id.member_register) CardView memberRegister;
    @BindView(R.id.balance_enquiry) CardView balanceEnquiry;
    @BindView(R.id.report) CardView report;
    @BindView(R.id.advance) CardView advance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DepositActivity.class);
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

        memberRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AgentMemberActivity.class);
                startActivity(intent);
            }
        });

        balanceEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BalanceActivity.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reports.class);
                startActivity(intent);
            }
        });

        advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });
    }
}