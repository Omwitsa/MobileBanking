package com.aratek.trustfinger.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aratek.trustfinger.Model.FingurePrintModel;
import com.aratek.trustfinger.Model.RegisterFingerprints;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterPhone extends AppCompatActivity {
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        String machineId = android.os.Build.SERIAL;

        String id = sharedpreferences.getString("loadsPosition", "");
        String fingerprint = sharedpreferences.getString("loadfingerprint", "");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            FingurePrintModel fingurePrint = new FingurePrintModel(fingerprint, id,machineId);
            Call<Response> call = apiService.registerFingerPrints(fingurePrint);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response responseData = response.body();
                    Toast.makeText(RegisterPhone.this, responseData.getMessage(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {

                }
            });
    }
}