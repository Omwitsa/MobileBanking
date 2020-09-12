package com.example.mobilebanking.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilebanking.Model.ProductModel;
import com.example.mobilebanking.Model.TransactionModel;
import com.example.mobilebanking.R;
import com.example.mobilebanking.Rest.ApiClient;
import com.example.mobilebanking.Rest.ApiInterface;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(getApplicationContext());
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.productname);

        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> val = new ArrayList<String>();

        String accountNo = sharedpreferences.getString("account_no", "");
        TransactionModel transaction = new TransactionModel("", 0.0, "", "", accountNo, "", "", "");
        Call<List<ProductModel>> call = apiService.getAdvanceProcucts(transaction);
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                List<ProductModel> val = response.body();
                
                //val = new ArrayList<ProductModel>();
                ArrayAdapter<ProductModel> dataAdapter = new ArrayAdapter<ProductModel>(ProductsActivity.this, android.R.layout.simple_spinner_item, val);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter = new ArrayAdapter<ProductModel>(ProductsActivity.this, android.R.layout.simple_spinner_item, val);
                spinner.setAdapter(dataAdapter);
                //Toast.makeText(ProductsActivity.this, val.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}