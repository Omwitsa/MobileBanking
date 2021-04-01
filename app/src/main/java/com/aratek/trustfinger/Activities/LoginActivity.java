package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aratek.trustfinger.Model.LoadData;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "POSDETAILS";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDoalog;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(LoginActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        Login();
    }
    private void Login() {
        String machineId = android.os.Build.SERIAL;
        //String MachineID = android.os.Build.MODEL+""+ Build.SERIAL;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.commit();


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        LoadData loadData = new LoadData(machineId);
        Call<Response> call = apiService.passwordLogin(loadData);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response responseData = response.body();
                String feedback = responseData.getMessage();
                String datas="Complete";

                if (!feedback.equals(datas)) {
                    //Toast.makeText(getApplicationContext(), "Sorry, Network error occurred", Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(getApplicationContext(), LoginAdminActivity.class);
                    startActivity(homeIntent);
                } else {
                    //Toast.makeText(getApplicationContext(), feedback, Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(getApplicationContext(), FingeprintActivity.class);
                    startActivity(homeIntent);
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressDoalog.dismiss();
                //Toast.makeText(getApplicationContext(), "Sorry, Network error occurred", Toast.LENGTH_LONG).show();

            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}