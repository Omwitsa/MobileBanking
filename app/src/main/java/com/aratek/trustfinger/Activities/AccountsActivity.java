package com.aratek.trustfinger.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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

public class AccountsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.back) Button back;
    @BindView(R.id.get_account) Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(getApplicationContext());

        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.accountno);
        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> val = new ArrayList<String>();
        //String id = "0735028";


        final String id = sharedpreferences.getString("loadsPosition", "");
        final String ids = sharedpreferences.getString("agentID", "");
        String machineId = android.os.Build.SERIAL;

        //sharedpreferences.getString("account_no", "65690200100416");
        TransactionModel transaction = new TransactionModel("", 0.0, "", "", id, "", machineId, "", "", "","");
        Call<List<String>> call = apiService.getUserAccounts(transaction);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> accounts = response.body();

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AccountsActivity.this, android.R.layout.simple_spinner_item, accounts);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter = new ArrayAdapter<String>(AccountsActivity.this, android.R.layout.simple_spinner_item, accounts);
                spinner.setAdapter(dataAdapter);

                if (spinner.getCount()<=0)
                {
                    Toast.makeText(AccountsActivity.this, "You details could not be verified please,register your fingerprints", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Acc = spinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                editor.putString("supplierNo", Acc);
                editor.putString("number",id);
                editor.putString("agtId",ids);
                editor.commit();
                //intent.putExtra("supplierNo", Acc);
                startActivity(intent);

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