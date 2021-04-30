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
import com.larry.trustfinger.Model.TransActionEnquiryModel;
import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;
import com.larry.trustfinger.utils.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class TransActionEnquiryActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.submit091) Button Enquiry;
    @BindView(R.id.buttonnnadmin) Button AdminButton;
    @BindView(R.id.buttoonBack) Button buttonBack;
    @BindView(R.id.number10099) EditText IdNumber;
    private TextView textView11;
    private TextView textView22;
    private TextView textView33;
    private TextView textView24;
    private TextView textView35;
    private TextView textView26;
    private TextView textView37;
    private TextView textView38;
    private TextView textView39;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDoalog = new ProgressDialog(TransActionEnquiryActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_action_enquiry);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final String IDNo = sharedpreferences.getString("supplierNo","");
        IdNumber.setText(IDNo);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        textView11 = findViewById(R.id.text_id230);
        textView22 = findViewById(R.id.text_id289);
        textView33 = findViewById(R.id.text_first278);
        textView24 = findViewById(R.id.text_second952);
        textView35 = findViewById(R.id.text_second2278s);
        textView26 = findViewById(R.id.text_second2s3057s);
        textView37 = findViewById(R.id.text_secon65d2sss);
        textView38 = findViewById(R.id.text_sec631ond2ssss);
        textView39 = findViewById(R.id.text_sec631ond2ssss1);



        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);

            }
        });
        Enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //final String IDNo = IdNumber.getText().toString();

                if(IDNo.isEmpty())
                {
                    Toast.makeText(TransActionEnquiryActivity.this, "Please enter your Account number", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDoalog.setMessage("Please wait...");
                    progressDoalog.show();
                    TransactionEnquiry(IDNo);
                }

            }

            private void TransactionEnquiry(String idNo) {
                String machineID = android.os.Build.SERIAL;
                TransActionEnquiryModel TransName = new TransActionEnquiryModel(idNo,machineID);
                Save(TransName, idNo);

            }

            private void Save(TransActionEnquiryModel TransName, final String idNo) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<Response> call = apiService.lastTransaction(TransName);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        progressDoalog.dismiss();

                        Response responseData = response.body();
                        assert responseData != null;
                        String role = responseData.getMessage();
                        if(role.equals("No data found"))
                        {
                            Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();

                        }
                        else {
                            String[] roleList = role.split(",");
                             String name1 = roleList[0];
                             String name2 = roleList[1];
                             String name3 = roleList[2];
                             String name4 = roleList[3];
                             String name5 = roleList[4];
                             String name6 = roleList[5];
                             String name7 = roleList[6];
                             String name8 = roleList[7];
                             String name9 = roleList[8];

                            textView11.setText(idNo);
                            textView22.setText(name1);
                            textView33.setText(name9);
                            textView24.setText(name3);
                            textView35.setText(name4);
                            textView26.setText(name5);
                            textView37.setText(name6);
                            textView38.setText(name7);
                            textView39.setText(name8);
                            AdminClass(idNo, name1, name9,name3,name4,name5,name6,name7,name8);
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
    private void AdminClass(final String idNo, final String name1, final String name9,final String name3,final String name4,final String name5,final String name6,final String name7,final String name8) {
        AdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idNo.isEmpty()&&name1.isEmpty()&&name9.isEmpty()&&name3.isEmpty()&&name4.isEmpty()&&name5.isEmpty()&&name6.isEmpty()&&name7.isEmpty()&&name8.isEmpty())
                {
                    Toast.makeText(TransActionEnquiryActivity.this, "Your Transaction details are Missing,Search again ", Toast.LENGTH_LONG).show();
                }
                else {
                    String RP="Reprint";
                    Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    editor.putString("transaction", RP);
                    editor.putString("MemberACCno", idNo);
                    editor.putString("Acc_name_1", name1);
                    editor.putString("Activity_1", name9);
                    editor.putString("Amount_1", name3);
                    editor.putString("Vno_1", name4);
                    editor.putString("Reprint_Agency_1", name5);
                    editor.putString("RepDate_1", name6);
                    editor.putString("REpName_1", name7);
                    editor.putString("RePID_1", name8);
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