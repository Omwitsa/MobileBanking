package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvanceActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.amount) EditText amount;
    @BindView(R.id.sNo) EditText sNo;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(AdvanceActivity.this);



        //getting device model and serial number
        String Machineid = android.os.Build.MODEL+""+ Build.SERIAL;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                final String Advanceproduct = extras.getString("supplierNo");
                String Accountno = extras.getString("Account");

                    Intent intent = new Intent(getApplicationContext(), SubmitTransactionActivity.class);
                    intent.putExtra("operation", "advance");
                    intent.putExtra("amount", "");
                    intent.putExtra("fingurePrint", "");
                    intent.putExtra("supplierNo", "");
                    intent.putExtra("pin", "");
                    intent.putExtra("accountNo", Accountno);
                    intent.putExtra("productDescription", Advanceproduct);

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