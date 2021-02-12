package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.btnSignup) Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(MainActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        //seedUser();
//        btnSignup.setEnabled(false);
//       btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                progressDoalog.setMessage("Please wait...");
//                progressDoalog.show();
//               Login();
                Intent intent = new Intent(getApplicationContext(), IdentificationActivity.class);
                startActivity(intent);

//            }
//        });

//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
    }
//
//    private void seedUser() {
//        String machineId = android.os.Build.SERIAL;
//        editor.putString("machine_id", machineId);
//        editor.commit();
//
//        String username = "PosAgent";
//        String password = "Coop@2020";
//        MemberModel memberModel = new MemberModel(username, password, machineId);
//        Call<Response> call = apiService.createUser(memberModel);
//        call.enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Response> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void Login() {
//        String machineId = android.os.Build.SERIAL;
//        MemberModel memberModel = new MemberModel(username.getText().toString(), password.getText().toString(), machineId);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString("loggedInUser", memberModel.getUsercode());
//        editor.commit();
//
//        Call<Response> call = apiService.login(memberModel);
//        call.enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                progressDoalog.dismiss();
//                Response responseData = response.body();
//                if (responseData.isSuccess()){
//                    Intent homeIntent = new Intent(getApplicationContext(), AuthenticationActivity.class);
//                    startActivity(homeIntent);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), responseData.getMessage(), Toast.LENGTH_LONG).show();
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
}