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

public class LoginAdminActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(LoginAdminActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        ButterKnife.bind(this);
               btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDoalog.setMessage("Please wait...");
                progressDoalog.show();
               Login();

            }
        });
    }
        private void Login() {
        String machineId = android.os.Build.SERIAL;
        MemberModel memberModel = new MemberModel(username.getText().toString(), password.getText().toString(), machineId);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("loggedInUser", memberModel.getUsercode());
        editor.commit();

        Call<Response> call = apiService.login(memberModel);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressDoalog.dismiss();
                Response responseData = response.body();
                String status = responseData.getMessage();
                String[] roleList = status.split(",");
                String name1 = roleList[0];
                String name2 = roleList[1];

                String admins="Login Successfull";
                String Permission="SuperAdmin";
                if(!name1.equals(admins))
                {
                    Toast.makeText(getApplicationContext(), name1, Toast.LENGTH_LONG).show();

                }
                else
                    {
                        Toast.makeText(getApplicationContext(),name1, Toast.LENGTH_LONG).show();
                        Intent homeIntent = new Intent(getApplicationContext(), FingeprintActivity.class);
                         editor.putString("LoadPermission", Permission);
//                        editor.putString("LoadAdminID", name2);
//                        editor.commit();
                        startActivity(homeIntent);

                    }
            }


            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Sorry, Network error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}