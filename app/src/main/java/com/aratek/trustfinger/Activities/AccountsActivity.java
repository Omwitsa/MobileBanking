package com.aratek.trustfinger.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.aratek.trustfinger.Model.AccountModel;
import com.aratek.trustfinger.Model.ProductModel;
import com.aratek.trustfinger.Model.TransactionModel;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(getApplicationContext());
        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.accountno);
        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> val = new ArrayList<String>();
        String FingerePrint = "eebdc18";
        //sharedpreferences.getString("account_no", "65690607002292");
        TransactionModel transaction = new TransactionModel("", 0.0, FingerePrint, "", "", "", "", "");
        Call<List<AccountModel>> call = apiService.getUserAccounts(transaction);

        call.enqueue(new Callback<List<AccountModel>>() {
            @Override
            public void onResponse(Call<List<AccountModel>> call, Response<List<AccountModel>> response) {
                List<AccountModel> accounts = response.body();
                String[] accname = new String[accounts.size()];
                for (int i = 0; i < accounts.size(); i++) {
                    accname[i] = accounts.get(i).getAccountNo();

//                List<String> productDescriptions = new ArrayList<String>();
//                products.forEach(p -> productDescriptions.add(p.getDescription()));
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AccountsActivity.this, android.R.layout.simple_spinner_item, accname);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter = new ArrayAdapter<String>(AccountsActivity.this, android.R.layout.simple_spinner_item, accname);
                    spinner.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<AccountModel>> call, Throwable t) {


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