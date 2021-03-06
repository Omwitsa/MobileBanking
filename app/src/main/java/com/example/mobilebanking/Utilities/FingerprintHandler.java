package com.example.mobilebanking.Utilities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mobilebanking.Model.Response;
import com.example.mobilebanking.Model.TransactionModel;
import com.example.mobilebanking.R;
import com.example.mobilebanking.Rest.ApiClient;
import com.example.mobilebanking.Rest.ApiInterface;
import com.example.mobilebanking.myactivities.MainActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    static SQLiteDatabase db;
    private TransactionModel _transaction;
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;

    public FingerprintHandler(Context mContext, TransactionModel transaction) {
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

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("account_no", _transaction.getsNo());
        editor.commit();
        insertDataToSqlite(_transaction);
    }

    public void update(String e, Boolean success){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
        if(success){
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        }
    }

    public void insertDataToSqlite(TransactionModel _transaction) {
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

        transact(_transaction);
    }

    private void transact(TransactionModel _transaction) {
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
}
