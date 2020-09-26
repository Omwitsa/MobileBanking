package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    @BindView(R.id.sNo) EditText sNo;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(BalanceActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String SupNo = sNo.getText().toString();
                Intent intent = new Intent(getApplicationContext(), FingeprintActivity.class);
                intent.putExtra("operation", "balance");
                intent.putExtra("amount", "0");
                intent.putExtra("fingurePrint", "");
                intent.putExtra("supplierNo", SupNo);
                intent.putExtra("pin", "");

                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}