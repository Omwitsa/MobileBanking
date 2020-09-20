package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aratek.trustfinger.MainActivity;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepositActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.amount) EditText amount;
    //    @BindView(R.id.pin) EditText pin;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(DepositActivity.this);
        //getting device model and serial number
        String Machineid = android.os.Build.MODEL+""+ Build.SERIAL;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bal = amount.getText().toString();
                String SupNo = "";
                String Pinn = "";

                if (bal.isEmpty()) {
                    Toast.makeText(DepositActivity.this, "Kindly provide the amount", Toast.LENGTH_LONG).show();
                }
                else {

//                    insertDataToSqlite(bal, Pinn, SupNo);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("operation", "deposit");
                    intent.putExtra("amount", bal);
                    intent.putExtra("fingurePrint", "");
                    intent.putExtra("supplierNo", SupNo);
                    intent.putExtra("pin", Pinn);
                    startActivity(intent);
                }
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

    private SQLiteDatabase getWritableDatabase() {
        // TODO Auto-generated method stub
        return null;
    }
}