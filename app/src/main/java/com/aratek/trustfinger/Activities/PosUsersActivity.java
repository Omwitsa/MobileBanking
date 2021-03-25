package com.aratek.trustfinger.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aratek.trustfinger.Model.AgencyModel;
import com.aratek.trustfinger.Model.AgentMember;
import com.aratek.trustfinger.Model.TransactionModel;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aratek.trustfinger.Activities.MainActivity.MyPREFERENCES;

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
    //@BindView(R.id.agency) Spinner agencys;
    @BindView(R.id.submits) Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_users);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(getApplicationContext());

        // Spinner element
        //final Spinner spinner = (Spinner) findViewById(R.id.agency);
        String machineId = android.os.Build.SERIAL;
        // Spinner click listener
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
//        List<String> val = new ArrayList<String>();
        //final String id = sharedpreferences.getString("loadsPosition", "");

        //sharedpreferences.getString("account_no", "65690200100416");
//        TransactionModel transaction = new TransactionModel("", 0.0, "", "", "", "", machineId, "", "", "","");
//        Call<List<String>> call = apiService.getAgency(transaction);

//        call.enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                List<String> accounts = response.body();
//
//                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(PosUsersActivity.this, android.R.layout.simple_spinner_item, accounts);
//                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                dataAdapter = new ArrayAdapter<String>(PosUsersActivity.this, android.R.layout.simple_spinner_item, accounts);
//                spinner.setAdapter(dataAdapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//
//            }
//
//        });
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
                        AgencyModel members =  new AgencyModel( names, lastname, idno, phone, machineId,admins, agency, agentid,"");
                        create(members);
                        createOperator(names,idno);

                    }

                    private void createOperator(String names, String idno) {
                        String Data="SuperAdmin";
                        Intent intent = new Intent(getApplicationContext(), FingerPrintsupdateActivity.class);
                        editor.putString("agentID", idno);
                        editor.putString("LoadPermission",Data );
                        editor.putString("agentFirstName", names);
                        editor.putString("agentSecondName", names);
                        startActivity(intent);
                    }

                    private void create(AgencyModel members) {
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Call<com.aratek.trustfinger.Model.Response> call = apiService.registerAgencyMember(members);

                        call.enqueue(new Callback<com.aratek.trustfinger.Model.Response>() {
                            @Override
                            public void onResponse(Call<com.aratek.trustfinger.Model.Response> call, retrofit2.Response<com.aratek.trustfinger.Model.Response> response) {
                                progressDoalog.dismiss();
                               // com.aratek.trustfinger.Model.Response responseData = response.body();
                                com.aratek.trustfinger.Model.Response responseData = response.body();
                                assert responseData != null;
                                String role=responseData.getMessage();
                                String datas=" Operator Registered successfully";

                                if(!role.equals(datas)) {
                                    Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<com.aratek.trustfinger.Model.Response> call, Throwable t) {
                                progressDoalog.dismiss();
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
}