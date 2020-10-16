package com.aratek.trustfinger.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    SharedPreferences.Editor editor;


    public Transaction(Context mContext, TransactionModel transaction) {
        context = mContext;
        _transaction = transaction;
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(context);
        editor = sharedpreferences.edit();

    }



    public void transact(TransactionModel _transaction) {
        editor = sharedpreferences.edit();

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
                    Toast.makeText(context, "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context, "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context, "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context, "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
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
        editor.putString("transaction", _transaction.getOperation());
        editor.putString("amount", amount);
        editor.putString("fingurePrint", "");
        editor.putString("supplierNo", _transaction.getsNo());
        editor.putString("pin", _transaction.getPin());
        editor.commit();
        context.startActivity(intent);
    }

}
