package com.example.mobilebanking.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mobilebanking.Model.Response;
import com.example.mobilebanking.Model.MemberModel;
import com.example.mobilebanking.R;
import com.example.mobilebanking.Rest.ApiClient;
import com.example.mobilebanking.Rest.ApiInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.btnSignup) Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        seedUser();

        progressDoalog = new ProgressDialog(MainActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDoalog.setMessage("Please wait...");
                progressDoalog.show();
                Login();
            }
        });

        btnSignup.setEnabled(false);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void seedUser() {
        String username = "CoopAdmin";
        String password = "Coop@2020";
        MemberModel memberModel = new MemberModel(username, password);
        Call<Response> call = apiService.createUser(memberModel);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void Login() {
        MemberModel memberModel = new MemberModel(username.getText().toString(), password.getText().toString());
        Call<Response> call = apiService.login(memberModel);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressDoalog.dismiss();
                Response responseData = response.body();
                if (responseData.isSuccess()){
                    Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(homeIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), responseData.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Sorry, An error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}