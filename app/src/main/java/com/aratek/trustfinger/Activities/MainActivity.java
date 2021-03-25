package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aratek.trustfinger.Model.AgencyModel;
import com.aratek.trustfinger.Model.LoadData;
import com.aratek.trustfinger.Model.LoginModel;
import com.aratek.trustfinger.Model.MemberModel;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
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
        progressDoalog = new ProgressDialog(MainActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
}







//        Call<Response> call = apiService.passwordLogin(loadData);
//        call.enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                progressDoalog.dismiss();
//                Response responseData = response.body();
//                String feedback=responseData.getMessage();
//                if (feedback=="True"){
//                    Intent homeIntent = new Intent(getApplicationContext(), IdentificationActivity.class);
//                    startActivity(homeIntent);
//                }
//                else {
//                    Intent homeIntent = new Intent(getApplicationContext(), LoginAdminActivity.class);
//                    startActivity(homeIntent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Response> call, Throwable t) {
//                progressDoalog.dismiss();
//                Toast.makeText(getApplicationContext(), "Sorry, Network error occurred", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//}