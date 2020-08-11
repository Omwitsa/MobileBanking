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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mobilebanking.Activity.DepositActivity;
import com.example.mobilebanking.Model.DepositModel;
import com.example.mobilebanking.Model.Response;
import com.example.mobilebanking.R;
import com.example.mobilebanking.Rest.ApiClient;
import com.example.mobilebanking.Rest.ApiInterface;
import com.example.mobilebanking.myactivities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    static SQLiteDatabase db;
    private  DepositModel _deposit;
    ApiInterface apiService;
    ProgressDialog progressDoalog;

    public FingerprintHandler(Context mContext, DepositModel deposit) {
        context = mContext;
        _deposit = deposit;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(context);
        db = context.openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS deposits(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR);");
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
        insertDataToSqlite(_deposit);
    }

    public void update(String e, Boolean success){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
        if(success){
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        }
    }

    public void insertDataToSqlite(DepositModel _deposit) {
        String bal = _deposit.getAmount().toString();
        String pinn = _deposit.getPin();
        String supNo = _deposit.getsNo();
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

        Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show();
        //progressDoalog.setMessage("Please wait...");
        //progressDoalog.show();
        deposit(_deposit);
    }

    private void deposit(DepositModel _deposit) {
        if (_deposit.getOperation().equals("deposit")){
            Call<Response> call = apiService.deposit(_deposit);
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
        else if (_deposit.getOperation().equals("withdraw")){
            Call<Response> call = apiService.withdraw(_deposit);
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
        intent.putExtra("amount", _deposit.getAmount());
        intent.putExtra("fingurePrint", "");
        intent.putExtra("supplierNo", _deposit.getsNo());
        intent.putExtra("pin", _deposit.getPin());
        context.startActivity(intent);
    }
}
