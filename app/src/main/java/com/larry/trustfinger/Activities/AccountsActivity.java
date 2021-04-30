package com.larry.trustfinger.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.larry.trustfinger.Model.TransactionModel;
import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    ApiInterface apiService;

    @BindView(R.id.back) Button back;
    @BindView(R.id.Idnuh)EditText NumbeId;
    @BindView(R.id.accountno) Spinner spinner;
    @BindView(R.id.get_account) Button submit;
    @BindView(R.id.FetchAccount) Button FetchAccount;

    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final String id = sharedpreferences.getString("loadMemberID", "");
        //final String id="22375739";
        NumbeId.setText(id);
        FetchAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineId = android.os.Build.SERIAL;
                //String machineId = "00031000135";
                apiService = ApiClient.getClient().create(ApiInterface.class);
                TransactionModel transaction = new TransactionModel("", 0.0, "", "", "", "", id, "", machineId, "", "", "");
                Call<List<String>> call = apiService.getUserAccounts(transaction);
                call.enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        List<String> accounts = response.body();
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AccountsActivity.this, android.R.layout.simple_spinner_item, accounts);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter = new ArrayAdapter<>(AccountsActivity.this, android.R.layout.simple_spinner_item, accounts);
                        spinner.setAdapter(dataAdapter);
                        if (spinner.getCount() <= 0) {
                            Toast.makeText(AccountsActivity.this, "You details could not be verified please,register your fingerprints", Toast.LENGTH_LONG).show();
                            submit.setEnabled(false);
                            submit.setBackgroundColor(Color.parseColor("#808080"));
                        }

                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {

                    }
                });

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Acc = spinner.getSelectedItem().toString();
                if(spinner.getCount()<=0)
                {
                    Toast.makeText(AccountsActivity.this, "Your details are missing,please contact Agency Administrator", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    editor.putString("supplierNo", Acc);
                    editor.putString("number",id);
                    //editor.putString("agtId",ids);
                    editor.apply();
                    //intent.putExtra("supplierNo", Acc);
                    startActivity(intent);

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IdentificationActivity.class);
                startActivity(intent);
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