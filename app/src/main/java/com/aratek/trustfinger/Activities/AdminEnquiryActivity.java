package com.aratek.trustfinger.Activities;

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

import com.aratek.trustfinger.Model.FirstNameModel;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class AdminEnquiryActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.submit1) Button Enquiry;
    @BindView(R.id.buttonadmin) Button AdminButton;
    @BindView(R.id.buttonBack) Button buttonBack;
    @BindView(R.id.number1) EditText IdNumber;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDoalog = new ProgressDialog(AdminEnquiryActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_enquiry);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        textView1 = findViewById(R.id.text_id2);
        textView2 = findViewById(R.id.text_first2);
        textView3 = findViewById(R.id.text_second2);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), FingerPrintsupdateActivity.class);
                startActivity(homeIntent);

            }
        });
        Enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String IDNo = IdNumber.getText().toString();
                if(IDNo.isEmpty())
                {
                    Toast.makeText(AdminEnquiryActivity.this, "Please enter your ID number", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDoalog.setMessage("Please wait...");
                    progressDoalog.show();
                    FirstName(IDNo);
                }

            }

            private void FirstName(String idNo) {
                FirstNameModel FirstName = new FirstNameModel(idNo);
                Save(FirstName, idNo);

            }

            private void Save(FirstNameModel FirstName, final String idNo) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<Response> call = apiService.existingFirstName(FirstName);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        progressDoalog.dismiss();

                        com.aratek.trustfinger.Model.Response responseData = response.body();
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
                            textView1.setText(idNo);
                            textView2.setText(name1);
                            textView3.setText(name2);

                            AdminClass(idNo, name1, name2);
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

    private void AdminClass(final String idNo, final String name1, final String name2) {
        AdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idNo.isEmpty()&&name1.isEmpty()&&name2.isEmpty())
                {
                    Toast.makeText(AdminEnquiryActivity.this, "Your details are Missing,Search again ", Toast.LENGTH_LONG).show();
                }
                else {
                    final String memberExist = "Exists";
                    Intent homeIntent = new Intent(getApplicationContext(), OperatoerFingerprintActivity.class);
                    editor.putString("MemberFinger", memberExist);
                    editor.putString("NewFirstName", name1);
                    editor.putString("NewSecondName", name2);
                    editor.putString("NewId", idNo);
                    editor.commit();
                    startActivity(homeIntent);
                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        return;
    }
}
