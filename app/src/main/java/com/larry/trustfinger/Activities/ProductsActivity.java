package com.larry.trustfinger.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.larry.trustfinger.Model.AdvanceLimitModel;
import com.larry.trustfinger.Model.ProductModel;
import com.larry.trustfinger.Model.TransactionModel;
import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.sNo) EditText sNo;
    @BindView(R.id.apply_advance) Button submit;
    @BindView(R.id.back) Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(getApplicationContext());

        final String Account = sharedpreferences.getString("supplierNo", "");
        if(Account.isEmpty()){
            Toast.makeText(getApplicationContext(),"Account Number is Missing",Toast.LENGTH_LONG).show();
        }
        else {
            sNo.setText(Account);
        }


        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.productname);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        List<String> val = new ArrayList<String>();
        TransactionModel transaction = new TransactionModel("", 0.0, "", "", "", "", "", "","","","",Account);
        Call<List<ProductModel>> call = apiService.getAdvanceProcucts(transaction);

        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                List<ProductModel> products = response.body();
                String[] productsname = new String[products.size()];
                for (int i = 0; i < products.size(); i++) {
                    productsname[i] = products.get(i).getDescription();
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ProductsActivity.this, android.R.layout.simple_spinner_item, productsname);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter = new ArrayAdapter<String>(ProductsActivity.this, android.R.layout.simple_spinner_item, productsname);
                    spinner.setAdapter(dataAdapter);
                }
                if (productsname.length <= 0)
                {
                    Toast.makeText(ProductsActivity.this, "You do not qualify for advance currently ", Toast.LENGTH_LONG).show();
                    submit.setEnabled(false);
                    submit.setBackgroundColor(Color.parseColor("#808080"));
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {


            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Acc = spinner.getSelectedItem().toString();
                AdvanceLimitModel advanceLimitModel = new AdvanceLimitModel(Account, Acc);
                Call<com.larry.trustfinger.Model.Response> call = apiService.advanceLimit(advanceLimitModel);
                call.enqueue(new Callback<com.larry.trustfinger.Model.Response>() {
                    @Override
                    public void onResponse(Call<com.larry.trustfinger.Model.Response> call, retrofit2.Response<com.larry.trustfinger.Model.Response> response)
                    {
                        com.larry.trustfinger.Model.Response responseData = response.body();
                         String AdvanceLimit = responseData.getMessage();
                         send(Account,Acc,AdvanceLimit);

                    }
                    private void send(String account, String acc, String advanceLimit) {
                        Intent intent = new Intent(getApplicationContext(), AdvanceActivity.class);
                        editor.putString("supplierNo",account );
                        editor.putString("Account", acc);
                        editor.putString("MaximumAmount", advanceLimit);
                        editor.apply();
                        startActivity(intent);
                    }


                    @Override
                    public void onFailure(Call<com.larry.trustfinger.Model.Response> call, Throwable t) {

                    }
                });


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onBackPressed() {
        return;
    }
}