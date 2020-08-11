package com.example.mobilebanking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class DepositActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ProgressDialog progressDoalog;
    ApiInterface apiService;
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
        db = openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS deposits(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR);");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bal = amount.getText().toString();
                String Pinn = pin.getText().toString();
                String SupNo = sNo.getText().toString();
                if (bal.isEmpty() && Pinn.isEmpty() && SupNo.isEmpty() ) {
                    //Snackbar.make(getView(), "Field(s) are empty !", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(DepositActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
                }else if(Pinn.length()>4){
                    //Snackbar.make(getView(), "Password should have a minimum of 8 characters", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(DepositActivity.this, "Pin shoud have a maximum of 4 characters", Toast.LENGTH_LONG).show();
                }else {

                    insertDataToSqlite(bal, Pinn, SupNo);

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


    public void insertDataToSqlite(String bal, String pinn, String supNo) {

        Calendar cc = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_pp = sdf.format(cc.getTime());

//
//
//            //db.rawQuery("SELECT * FROM deposits", null);
//        Cursor c = db.rawQuery("SELECT * FROM deposits ", null);
//        if (c.getCount() == 0) {
//            Toast.makeText(getApplicationContext(), "No record Found", Toast.LENGTH_LONG).show();
//            return;
//        }
//        else{
//            while (c.moveToNext()) {
//                StringBuffer buffer = new StringBuffer();
//
//                buffer.append(c.getString(0) + "\t" + c.getString(1) + " \t" + c.getString(2)  +"\n");
//                Toast.makeText(getApplicationContext(), "No record Found", Toast.LENGTH_LONG).show();
//
//            }
//        }
            db.execSQL("INSERT INTO deposits VALUES('" + bal + "','"  + pinn+ "','" + supNo + "','" + date_pp + "','0');");

             Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
        //progressDoalog.setMessage("Please wait...");
        //progressDoalog.show();
             deposit();



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
            //Cursor c = db.rawQuery("SELECT * FROM MobileDB  ", null);
        });


        Print();
    }

    private void Print() {
        Intent registerIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(registerIntent);
    }
    private SQLiteDatabase getWritableDatabase() {
        // TODO Auto-generated method stub
        return null;
    }
}