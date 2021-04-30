package com.larry.trustfinger.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.larry.trustfinger.Model.AgencyModel;
import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;
import com.larry.trustfinger.Model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class PosUsersActivity extends Activity implements AdapterView.OnItemSelectedListener {

    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.names) EditText fname;
    @BindView(R.id.names2) EditText lname;
    @BindView(R.id.idno) EditText idnos;
    @BindView(R.id.phone) EditText phones;
    @BindView(R.id.admin) Spinner admin;
    @BindView(R.id.submits) Button submit;
    @BindView(R.id.subs) Button Return;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_users);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(PosUsersActivity.this);
        String machineId = android.os.Build.SERIAL;

        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterPhone.class);
                startActivity(intent);
            }
        });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String names = fname.getText().toString();
                        String lastname = lname.getText().toString();
                        String idno = idnos.getText().toString();
                        String phone = phones.getText().toString();
                        String machineId = android.os.Build.SERIAL;
                        String admins = admin.getSelectedItem().toString();
                        String agency = "";
                        String agentid = sharedpreferences.getString("loadsAgentId", "");
                        if (names.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Enter Surname", Toast.LENGTH_LONG).show();
                        } else if (lastname.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Enter Other names", Toast.LENGTH_LONG).show();
                        } else if (idno.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Enter ID number", Toast.LENGTH_LONG).show();
                        }
                        else if (idno.length()<7) {
                            Toast.makeText(getApplicationContext(), "ID number should have 7 or 8 digits", Toast.LENGTH_LONG).show();
                        }
                        else if (phone.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Enter Phone number", Toast.LENGTH_LONG).show();
                        }
                        else if (phone.length()<10) {
                            Toast.makeText(getApplicationContext(), "Phone number should have 10 digits", Toast.LENGTH_LONG).show();
                        }
                        else if (admins.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Select Operator", Toast.LENGTH_LONG).show();
                        } else {
                            AgencyModel members = new AgencyModel(names, lastname, idno, phone, machineId, admins, agency, agentid, "");
                            create(members,names,lastname,idno);


                        }
                    }


                    private void create(AgencyModel members, final String names, final String lastname, final String idno) {
                        progressDoalog.setMessage("Registering ...Please wait...");
                        progressDoalog.show();
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Call<Response> call = apiService.registerAgencyMember(members);

                        call.enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                Response responseData = response.body();
                                assert responseData != null;
                                String role=responseData.getMessage();
                                String datas=" Operator Registered successfully";
                                Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                                if(role.equals(datas)) {
                                    Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), OperatoerFingerprintActivity.class);
                                    editor.putString("NewId",idno);
                                    editor.putString("NewFirstName", names);
                                    editor.putString("NewSecondName", lastname);
                                    editor.commit();
                                    startActivity(intent);

                                }
                                else
                                    {
                                        Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                                    }
                                progressDoalog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {
                                //Toast.makeText(getApplicationContext(), "Sorry, network  error Try again", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

}



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed() {
        return;
    }
}