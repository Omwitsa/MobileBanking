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
import android.widget.TextView;
import android.widget.Toast;

import com.aratek.trustfinger.Model.AgentMember;
import com.aratek.trustfinger.Model.FirstNameModel;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.Model.SecondNameModel;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

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
    @BindView(R.id.number) EditText IdNumber;
    public  TextView textView1;
    public TextView textView2;
    public TextView textView3;

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


        Enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDoalog.setMessage("Please wait...");
                progressDoalog.show();
                final String IDNo = IdNumber.getText().toString();
                //FirstNameModel FirstName = new FirstNameModel(IDNo);
                //register(FirstName);
                SecondName(IDNo);

            }

            private void SecondName(String idNo) {
                SecondNameModel SecondName = new SecondNameModel(idNo);
                Save(SecondName,idNo);

            }

            private void Save(SecondNameModel secondName, final String idNo) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<Response> call = apiService.existingSecondName(secondName);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        progressDoalog.dismiss();

                        com.aratek.trustfinger.Model.Response responseData = response.body();
                        assert responseData != null;
                        String role=responseData.getMessage();
                        String [] roleList = role.split(",");
                        String name1 = roleList [0];
                        String name2 = roleList [1];
                        Toast.makeText(getApplicationContext(), "User exists", Toast.LENGTH_LONG).show();
                        textView1.setText(idNo);
                        textView2.setText(name1);
                        textView3.setText(name2);
                       NewClass(idNo,name1,name2);




                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        progressDoalog.dismiss();

                    }
                });
            }



        });

    }

    private void NewClass(final String idNo, final String name1, final String name2) {
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String memberExist="Exists";
                Intent homeIntent = new Intent(getApplicationContext(), VerifyActivity.class);
                editor.putString("MemberFinger", memberExist);
                editor.putString("NewFirstName", name1);
                editor.putString("NewSecondName", name2);
                editor.putString("NewId", idNo);
                editor.commit();
                startActivity(homeIntent);


            }
        });
    }


}