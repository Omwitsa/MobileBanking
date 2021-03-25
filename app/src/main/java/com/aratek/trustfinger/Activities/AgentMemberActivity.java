package com.aratek.trustfinger.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aratek.trustfinger.Model.AgentMember;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

import static java.util.Calendar.DATE;

public class AgentMemberActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.surname) EditText surname;
    @BindView(R.id.othername) EditText othername;
    @BindView(R.id.idnno) EditText iddno;
    @BindView(R.id.mobile) EditText mobile;
    @BindView(R.id.gender) Spinner gender;
    @BindView(R.id.dob) EditText dbb;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;
    public String tomorrow = "";
    public String yesterday = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_member);

        ButterKnife.bind(this);
        progressDoalog = new ProgressDialog(AgentMemberActivity.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final String idn = sharedpreferences.getString("loadsAgentId", "");
        dbb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                final Calendar calendar = Calendar.getInstance();
                calendar.add(  DATE,1);
                tomorrow = df.format(calendar.getTime());
                calendar.add(DATE,-2);
                yesterday = df.format(calendar.getTime());


                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH)+1;
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(AgentMemberActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String monthString = String.valueOf((monthOfYear)+1);
                        if (monthString.length() == 1) {
                            monthString = "0" + monthString;
                        }
                        String dayOfMonthString = String.valueOf(dayOfMonth);
                        if (dayOfMonthString.length() == 1) {
                            dayOfMonthString = "0" + dayOfMonthString;
                        }

                        dbb.setText(new StringBuilder().append(year).append("-")
                                .append(monthString).append("-").append(dayOfMonthString).append(" "));



                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressDoalog.setMessage("Please wait...");
                progressDoalog.show();
                final String Surname = surname.getText().toString();
                final String other_Names = othername.getText().toString();
                final String idno = iddno.getText().toString();
                final String MachineId = android.os.Build.SERIAL;
                final String mobile_number = mobile.getText().toString();
                final String DOB =(dbb.getText().toString().trim());
                final String FingerPrint1="";
                final String FingerPrint2="";
                final String Gender = gender.getSelectedItem().toString();
                final String Agentid = sharedpreferences.getString("loadsAgentId", "");
                AgentMember member = new AgentMember(Surname, other_Names, idno,MachineId, mobile_number, Gender, DOB, FingerPrint1, FingerPrint2, Agentid);
                register(member);
                nectAction(Surname,other_Names,idno);
            }

            private void nectAction(String surname, String other_Names, String idno) {
                String Data="Member Registered successfully";
                Intent intent = new Intent(getApplicationContext(), FingerPrintsupdateActivity.class);
                editor.putString("agentID",idno);
                editor.putString("LoadPermission",Data );
                editor.putString("agentFirstName", surname);
                editor.putString("agentSecondName", other_Names);
                editor.commit();

                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterPhone.class);
                startActivity(intent);
            }
        });


    }

    private void register(AgentMember member) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Response> call = apiService.registerAgentMember(member);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressDoalog.dismiss();

                com.aratek.trustfinger.Model.Response responseData = response.body();
                assert responseData != null;
                String role=responseData.getMessage();
                String Data="Member Registered successfully";
                Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                if(!role.equals(Data)) {
                    Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressDoalog.dismiss();
                //Toast.makeText(getApplicationContext(), "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
            }
        });

}

}