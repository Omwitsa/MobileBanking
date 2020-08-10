package com.example.mobilebanking.Utilities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import retrofit2.Call;
import retrofit2.Callback;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    private  DepositModel _deposit;
    ApiInterface apiService;
    ProgressDialog progressDoalog;

    public FingerprintHandler(Context mContext, DepositModel deposit) {
        context = mContext;
        _deposit = deposit;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(context);
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
        deposit();
    }

    public void update(String e, Boolean success){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
        if(success){
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        }
    }

    private void deposit() {
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
