package com.example.mobilebanking.Utilities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

    public FingerprintHandler(Context mContext, TransactionModel transaction) {
        context = mContext;

        _transaction = transaction;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(context);
        db = context.openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS withdrawals(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS deposits(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
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
        String bal = _transaction.getAmount().toString();
        String pinn = _transaction.getPin();
        String supNo = _transaction.getsNo();
        Calendar cc = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_pp = sdf.format(cc.getTime());
        SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
        String trans= ff.format(cc.getTime());


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
        if (_transaction.getOperation().equals("deposit")){
            db.execSQL("INSERT INTO deposits VALUES('" + bal + "','"  + pinn+ "','" + supNo + "','" + date_pp + "','0','" + trans + "');");
        }
        else if (_transaction.getOperation().equals("withdraw")){
            db.execSQL("INSERT INTO withdrawals VALUES('" + bal + "','"  + pinn+ "','" + supNo + "','" + date_pp + "','0','" + trans + "');");
        }
        else {

        }


        Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show();
        //progressDoalog.setMessage("Please wait...");
        //progressDoalog.show();
        deposit(_transaction);
    }

    private void deposit(TransactionModel _transaction) {
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


        Print();
    }

    private void Print() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("transaction", _transaction.getOperation());
        intent.putExtra("amount", _transaction.getAmount().toString());
        intent.putExtra("fingurePrint", "");
        intent.putExtra("supplierNo", _transaction.getsNo());
        intent.putExtra("pin", _transaction.getPin());
        context.startActivity(intent);
    }
}
