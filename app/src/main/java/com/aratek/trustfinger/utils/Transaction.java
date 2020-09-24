package com.aratek.trustfinger.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.Model.TransactionModel;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class Transaction<_transaction> {
    private Context context;
    private TransactionModel _transaction;
    static SQLiteDatabase db;
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;

    public Transaction(Context mContext, TransactionModel transaction) {
        context = mContext;
        _transaction = transaction;
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(context);
        db = context.openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS withdrawals(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS deposits(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS advance(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
        //db.execSQL("ALTER TABLE withdrawals  ADD transdate  varchar");


    }
    public void transact(TransactionModel _transaction) {
        //insertDataToSqlite();
        if (_transaction.getOperation().equals("deposit")){
            Call<Response> call = apiService.deposit(_transaction);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    progressDoalog.dismiss();
                    Response responseData = response.body();
                    String message = responseData.getMessage() == null ? "Sorry, Invalid username or password" : responseData.getMessage();
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(context, "Sorry, An error occurred", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if (_transaction.getOperation().equals("withdraw")){
            Call<Response> call = apiService.withdraw(_transaction);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    progressDoalog.dismiss();

                    Response responseData = response.body();
                    String message = responseData.getMessage() == null ? "Sorry, Invalid username or password" : responseData.getMessage();
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(context, "Sorry, An error occurred", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if (_transaction.getOperation().equals("balance")){
            Call<Response> call = apiService.getBalance(_transaction);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    progressDoalog.dismiss();
                    Response responseData = response.body();
                    Print(responseData.getMessage());
                    Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(context, "Sorry, An error occurred", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if (_transaction.getOperation().equals("advance")){
            Call<Response> call = apiService.applyAdvance(_transaction);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    progressDoalog.dismiss();
                    Response responseData = response.body();
                    Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(context, "Sorry, An error occurred", Toast.LENGTH_LONG).show();
                }
            });
        }

        if (!_transaction.getOperation().equals("balance")){
            Print("");
        }
    }

    private void Print(String amount) {
        amount = amount.isEmpty() ? _transaction.getAmount().toString() : amount;
        Double damount=Double.parseDouble(amount);
        amount= String.format("%.2f", damount);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("transaction", _transaction.getOperation());
        intent.putExtra("amount", amount);
        intent.putExtra("fingurePrint", "");
        intent.putExtra("supplierNo", _transaction.getsNo());
        intent.putExtra("pin", _transaction.getPin());
        context.startActivity(intent);
    }

    public void insertDataToSqlite()  {
        String bal = String.format("%.2f", _transaction.getAmount());
        String pinn = _transaction.getPin();
        String supNo = _transaction.getsNo();
        Calendar cc = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_pp = sdf.format(cc.getTime());
        SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
        String trans= ff.format(cc.getTime());

        if (_transaction.getOperation().equals("deposit")){
            db.execSQL("INSERT INTO deposits VALUES('" + bal + "','"  + pinn+ "','" + supNo + "','" + date_pp + "','0','" + trans + "');");
            Toast.makeText(context, "Deposit saved successfully", Toast.LENGTH_LONG).show();
        }
        else if (_transaction.getOperation().equals("withdraw")){
            db.execSQL("INSERT INTO withdrawals VALUES('" + bal + "','"  + pinn+ "','" + supNo + "','" + date_pp + "','0','" + trans + "');");
            Toast.makeText(context, "Withdrawal saved successfully", Toast.LENGTH_LONG).show();
        }
        else if (_transaction.getOperation().equals("advance")){
            db.execSQL("INSERT INTO advance VALUES('" + bal + "','"  + pinn+ "','" + supNo + "','" + date_pp + "','0','" + trans + "');");
            Toast.makeText(context, "Advance saved successfully", Toast.LENGTH_LONG).show();
        }
        else {

        }


    }
}
