package com.example.mobilebanking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilebanking.R;
import com.example.mobilebanking.Rest.ApiClient;
import com.example.mobilebanking.Rest.ApiInterface;

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
                String bal = amount.getText().toString();
                String SupNo = sNo.getText().toString();
                String Pinn = "";

                if (bal.isEmpty() && Pinn.isEmpty() && SupNo.isEmpty() ) {
                    //Snackbar.make(getView(), "Field(s) are empty !", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(AdvanceActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
                }else if(Pinn.length()>4){
                    //Snackbar.make(getView(), "Password should have a minimum of 8 characters", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(AdvanceActivity.this, "Pin shoud have a maximum of 4 characters", Toast.LENGTH_LONG).show();
                }else {

//                    insertDataToSqlite(bal, Pinn, SupNo);

                    Intent intent = new Intent(getApplicationContext(), FingeprintActivity.class);
                    intent.putExtra("operation", "advance");
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
}