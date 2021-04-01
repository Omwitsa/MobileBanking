package com.aratek.trustfinger.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.aratek.trustfinger.Activities.RegisterPhone;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.Model.TransactionModel;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

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



    public  Transaction(Context mContext, TransactionModel transaction) {
        context = mContext;
        _transaction = transaction;
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(context);
        editor = sharedpreferences.edit();

    }

    public void transact(final TransactionModel _transaction) {
        editor = sharedpreferences.edit();

        //insertDataToSqlite();
        if (_transaction.getOperation().equals("deposit")){
            Call<Response> call = apiService.deposit(_transaction);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    progressDoalog.dismiss();
                    Response responseData = response.body();
                    String feedback = responseData.getMessage();

                    String[] roleList = feedback.split(",");
                    String name1 = roleList[0];
                    String name2 = roleList[1];
                    String datas="Deposit Successfull";
                    if (name1.equals(datas))
                    {
                        Toast.makeText(context, name1, Toast.LENGTH_LONG).show();
                        String amount="";
                        amount =  _transaction.getAmount().toString();
                        Double damount=Double.parseDouble(amount);
                        amount= String.format("%.2f", damount);
                        Intent intent = new Intent(context, MainActivity.class);
                        editor.putString("transaction", _transaction.getOperation());
                        editor.putString("amount", amount);
                        editor.putString("fingurePrint", "");
                        editor.putString("Vno", name2);
                        editor.putString("supplierNo", _transaction.getsNo());
                        editor.putString("pin", _transaction.getPin());
                        editor.commit();
                        context.startActivity(intent);


                    }
                    else if(!name1.equals(datas))
                        {
                            Toast.makeText(context, name1, Toast.LENGTH_LONG).show();
                            Intent homeIntent = new Intent(context, RegisterPhone.class);
                            context.startActivity(homeIntent);

                        }


                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    progressDoalog.dismiss();
                    Intent homeIntent = new Intent(context, RegisterPhone.class);
                    context.startActivity(homeIntent);
                    //Toast.makeText(context, "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
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
                    String feedback = responseData.getMessage();
                    String[] roleList = feedback.split(",");
                    String name1 = roleList[0];
                    String name2 = roleList[1];
                    String datas = "Withdrawal successful";
                    if (name1.equals(datas)) {
                        Toast.makeText(context, name1, Toast.LENGTH_LONG).show();
                        Toast.makeText(context, name1, Toast.LENGTH_LONG).show();
                        String amount="";
                        amount =  _transaction.getAmount().toString();
                        Double damount=Double.parseDouble(amount);
                        amount= String.format("%.2f", damount);
                        Intent intent = new Intent(context, MainActivity.class);
                        editor.putString("transaction", _transaction.getOperation());
                        editor.putString("amount", amount);
                        editor.putString("fingurePrint", "");
                        editor.putString("Vno", name2);
                        editor.putString("supplierNo", _transaction.getsNo());
                        editor.putString("pin", _transaction.getPin());
                        editor.commit();
                        context.startActivity(intent);

                    } else if(!name1.equals(datas)) {

                        Toast.makeText(context, name1, Toast.LENGTH_LONG).show();
                        Intent homeIntent = new Intent(context, RegisterPhone.class);
                        context.startActivity(homeIntent);

                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    progressDoalog.dismiss();
                    Intent homeIntent = new Intent(context, RegisterPhone.class);
                    context.startActivity(homeIntent);
                    //Toast.makeText(context, "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
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
                    Intent homeIntent = new Intent(context, RegisterPhone.class);
                    context.startActivity(homeIntent);
                    //Toast.makeText(context, "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
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
                    String feedback = responseData.getMessage();
                    Toast.makeText(context, feedback, Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(context, RegisterPhone.class);
                    context.startActivity(homeIntent);

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    progressDoalog.dismiss();
                    Intent homeIntent = new Intent(context, RegisterPhone.class);
                    context.startActivity(homeIntent);
                    //Toast.makeText(context, "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
                }
            });
        }

        if (_transaction.getOperation().equals("balance")){
            Print("");
        }
    }


    private void Print(String amount) {
        amount = amount.isEmpty() ? _transaction.getAmount().toString() : amount;
        Double damount=Double.parseDouble(amount);
        amount= String.format("%.2f", damount);
        String version = "Balance";
        Intent intent = new Intent(context, MainActivity.class);
        editor.putString("transaction", _transaction.getOperation());
        editor.putString("amount", amount);
        editor.putString("fingurePrint", "");
        editor.putString("Vno", version);
        editor.putString("supplierNo", _transaction.getsNo());
        editor.putString("pin", _transaction.getPin());
        editor.commit();
        context.startActivity(intent);

    }

}
