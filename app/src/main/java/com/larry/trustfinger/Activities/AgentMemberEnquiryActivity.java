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

import com.larry.trustfinger.Model.AgentNameModel;
import com.larry.trustfinger.Model.Response;
import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class AgentMemberEnquiryActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.submit11) Button Enquiry;
    @BindView(R.id.buttonagent) Button Agent;
    @BindView(R.id.buttonBack1) Button buttonBackk;
    @BindView(R.id.number11) EditText IdNumber;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textViewa4;
    private TextView textViewa5;
    private TextView textViewa6;
    private TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDoalog = new ProgressDialog(AgentMemberEnquiryActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_member_enquiry);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        textView1 = findViewById(R.id.text_id3);
        textView2 = findViewById(R.id.text_first3);
        textView3 = findViewById(R.id.text_second3);
         textViewa4 = findViewById(R.id.tvA);
        textViewa5 = findViewById(R.id.tvCountA);
         textViewa6 = findViewById(R.id.APrintnames);
        Title=findViewById(R.id.text_title);
        final String Agtids = sharedpreferences.getString("loadsCurrentAgentId", "");
        buttonBackk.setOnClickListener(new View.OnClickListener() {
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
                   Toast.makeText(AgentMemberEnquiryActivity.this, "Please enter your ID number", Toast.LENGTH_LONG).show();
               }
                progressDoalog.setMessage("Please wait...");
                progressDoalog.show();
                FirstName(IDNo);

            }

            private void FirstName(String idNo) {
                //AgentNameModel FirstName = new AgentNameModel(idNo);
                Save(idNo);
            }

            private void Save(final String idNo) {
                final  String machineID = android.os.Build.SERIAL;
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                AgentNameModel agentNameModel = new AgentNameModel(idNo,machineID);
                Call<Response> call = apiService.agentMembersSecondName(agentNameModel);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        progressDoalog.dismiss();

                        Response responseData = response.body();
                        assert responseData != null;
                        String role = responseData.getMessage();
                        if (role.equals("You are not registered")) {
                            Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                            textView2.setText("");
                            textView3.setText("");
                            textViewa5.setText("");
                            textViewa6.setText("");

                        } else {
                            String[] roleList = role.split(",");
                            String name1 = roleList[0];
                            String name2 = roleList[1];
                            String name3 = roleList[2];
                            String name4 = roleList[3];


                            textView1.setText(idNo);
                            textView2.setText(name1);
                            textView3.setText(name2);
                            textViewa5.setText(name3);
                            textViewa6.setText(name4);


                            AgentClass(idNo, name1, name2 ,Agtids);


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

    private void AgentClass(final String idNo, final String name1, final String name2,final  String agtids) {
        Agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name1.isEmpty()&&name2.isEmpty())
                {
                    Toast.makeText(AgentMemberEnquiryActivity.this, "Your details are Missing,Search again ", Toast.LENGTH_LONG).show();
                }
                else {
                final String memberExist = "Exists";
                Intent homeIntent = new Intent(getApplicationContext(), AgentMembersFingerPrintActivity.class);

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
