package com.example.mobilebanking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilebanking.Model.DepositModel;
import com.example.mobilebanking.Model.MemberModel;
import com.example.mobilebanking.Model.Response;
import com.example.mobilebanking.R;
import com.example.mobilebanking.Rest.ApiClient;
import com.example.mobilebanking.Rest.ApiInterface;
import com.example.mobilebanking.Utilities.Constants;
import com.example.mobilebanking.myactivities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class DepositActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    static SQLiteDatabase db;
    @BindView(R.id.amount) EditText amount;
    @BindView(R.id.pin) EditText pin;
    @BindView(R.id.sNo) EditText sNo;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(DepositActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressDoalog.setMessage("Please wait...");
//                progressDoalog.show();
//                deposit();
                Intent intent = new Intent(getApplicationContext(), FingeprintActivity.class);
                intent.putExtra("operation", "deposit");
                intent.putExtra("amount", amount.getText().toString());
                intent.putExtra("fingurePrint", "");
                intent.putExtra("supplierNo", sNo.getText().toString());
                intent.putExtra("pin", pin.getText().toString());
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

    private void deposit() {
        Double depositAmount = Double.parseDouble(amount.getText().toString());
        DepositModel depositModel = new DepositModel("deposit", depositAmount, "", pin.getText().toString(), sNo.getText().toString());
        Call<Response> call = apiService.deposit(depositModel);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressDoalog.dismiss();
                Response responseData = response.body();
                String message = responseData.getMessage() == null ? "Sorry, Invalid username or password" : responseData.getMessage();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Sorry, An error occurred", Toast.LENGTH_LONG).show();
            }
        });
        Print();
    }

    private void Print() {
        Double depositAmount = Double.parseDouble(amount.getText().toString());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("amount", depositAmount);
        intent.putExtra("fingurePrint", "");
        intent.putExtra("supplierNo", sNo.getText().toString());
        intent.putExtra("pin", pin.getText().toString());
        startActivity(intent);
    }
}