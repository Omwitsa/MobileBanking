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
import com.larry.trustfinger.Model.SecondNameModel;
import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class EnquiryActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.submit) Button Enquiry;
    @BindView(R.id.next) Button Next;
    @BindView(R.id.nextBack) Button nextBackkk;
    @BindView(R.id.number) EditText IdNumber;
    public  TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public TextView textCount;
    public TextView FingerPrint;
    public TextView FingerName;
    public TextView ExistTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDoalog = new ProgressDialog(EnquiryActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        apiService = ApiClient.getClient().create(ApiInterface.class);
        textView1 = findViewById(R.id.text_id1);
        textView2 = findViewById(R.id.text_first1);
        textView3 = findViewById(R.id.text_second1);
        textCount= findViewById(R.id.tvCount);
        FingerPrint= findViewById(R.id.tvIAlwaysHaveAccount);
        FingerName= findViewById(R.id.FPrintnames);
        ExistTitle =findViewById(R.id.text_Ttl);
        final String Agtids = sharedpreferences.getString("loadsCurrentAgentId", "");
        nextBackkk.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(EnquiryActivity.this, "Please enter your ID number", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDoalog.setMessage("Please wait...");
                    progressDoalog.show();
                    SecondName(IDNo);
                }

            }

            private void SecondName(String idNo) {
                Save(idNo);
            }

            private void Save(final String idNo) {
                final  String machineID = android.os.Build.SERIAL;
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                SecondNameModel secondNameModel = new SecondNameModel(idNo,machineID);
                Call<Response> call = apiService.existingSecondName(secondNameModel);
                //Call<Response> call = apiService.login(memberModel);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        progressDoalog.dismiss();

                        Response responseData = response.body();
                        assert responseData != null;
                        String role=responseData.getMessage();
                        if(role.equals("You are not registered"))
                        {
                            Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                            textView2.setText("");
                            textView3.setText("");
                            textCount.setText("");
                            FingerName.setText("");

                        }
                        else {
                            String[] roleList = role.split(",");
                            String name1 = roleList[0];
                            String name2 = roleList[1];
                            String name3 = roleList[2];
                            String name4 = roleList[3];
                            textView1.setText(idNo);
                            textView2.setText(name1);
                            textView3.setText(name2);
                            textCount.setText(name3);
                            FingerName.setText(name4);
                            NewClass(idNo, name1, name2,Agtids);

                        }


                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
            }



        });

    }

    private void NewClass(final String idNo, final String name1, final String name2, final String agtids) {
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name1.isEmpty()&&name2.isEmpty())
                {
                    Toast.makeText(EnquiryActivity.this, "Your details are Missing,Search again ", Toast.LENGTH_LONG).show();
                }
                else {
                    final String memberExist = "Exists";
                    Intent homeIntent = new Intent(getApplicationContext(), VerifyActivity.class);
                    editor.putString("OperatorID", agtids);
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