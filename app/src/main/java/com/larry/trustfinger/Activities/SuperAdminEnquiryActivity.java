package com.larry.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.larry.trustfinger.Model.Response;
import com.larry.trustfinger.Model.SuperAdminModel;
import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SuperAdminEnquiryActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.buttonAdmin) Button AdminButton;
    @BindView(R.id.BtnAdmin) Button ButtonAdmin;
    @BindView(R.id.nexxtBack) Button ButtonBackn;
    @BindView(R.id.edt_admin)
    EditText idnos;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDoalog = new ProgressDialog(SuperAdminEnquiryActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_enquiry);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        textView1 = findViewById(R.id.text_id20);
        textView2 = findViewById(R.id.text_first20);
        textView3 = findViewById(R.id.text_second20);
        final String AdminID = sharedpreferences.getString("LoadAdminID", "");


        idnos.setText(AdminID);
        ButtonBackn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), FingerPrintsupdateActivity.class);
                startActivity(homeIntent);
            }
        });
        ButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDoalog.setMessage("Please wait...");
                progressDoalog.show();

                //final String IDNo = idnos.getText().toString();

                //FirstNameModel FirstName = new FirstNameModel(IDNo);
                //register(FirstName);
                FirstName(AdminID);

            }

            private void FirstName(String AdminID) {
                SuperAdminModel FirstName = new SuperAdminModel(AdminID);
                Save(FirstName, AdminID);

            }

            private void Save(SuperAdminModel FirstName, final String AdminID) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<Response> call = apiService.superAdminDetails(FirstName);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        progressDoalog.dismiss();

                        Response responseData = response.body();
                        assert responseData != null;
                        String role = responseData.getMessage();
                        if(role.equals("You are not registered"))
                        {
                            Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();

                        }
                        else {
                            String[] roleList = role.split(",");
                            String name1 = roleList[0];
                            String name2 = roleList[1];
                            textView1.setText(AdminID);
                            textView2.setText(name1);
                            textView3.setText(name2);

                            AdminClass(AdminID, name1, name2);
                        }





                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        progressDoalog.dismiss();

                    }
                });
            }


        });
    }

    private void AdminClass(final String AdminID, final String name1, final String name2) {
        AdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String memberExist = "Exists";
                Intent homeIntent = new Intent(getApplicationContext(), OperatoerFingerprintActivity.class);
                editor.putString("MemberFinger", memberExist);
                editor.putString("NewFirstName", name1);
                editor.putString("NewSecondName", name2);
                editor.putString("NewId", AdminID);
                editor.commit();
                startActivity(homeIntent);
            }
        });


    }
    @Override
    public void onBackPressed() {
        return;
    }
}