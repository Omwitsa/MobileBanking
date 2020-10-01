package com.aratek.trustfinger.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

//        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        Bundle extras = getActivity().getIntent().getExtras();
//        String operation = extras.getString("operation");
//        Double amount = Double.parseDouble(extras.getString("amount"));
//        String status = "0";
//        String sNo = extras.getString("supplierNo");
//        String machineId = sharedpreferences.getString("machine_id", "");
//        String auditId = sharedpreferences.getString("loggedInUser", "");






        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.productname);
        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> val = new ArrayList<String>();
        String FingerePrint = "eebdc18";
        //sharedpreferences.getString("account_no", "65690607002292");
        TransactionModel transaction = new TransactionModel("", 0.0, FingerePrint, "", "", "", "", "");
        Call<List<ProductModel>> call = apiService.getAdvanceProcucts(transaction);

        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                List<ProductModel> products = response.body();
                String[] productsname = new String[products.size()];
                for (int i = 0; i < products.size(); i++) {
                    productsname[i] = products.get(i).getDescription();

//                List<String> productDescriptions = new ArrayList<String>();
//                products.forEach(p -> productDescriptions.add(p.getDescription()));
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ProductsActivity.this, android.R.layout.simple_spinner_item, productsname);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter = new ArrayAdapter<String>(ProductsActivity.this, android.R.layout.simple_spinner_item, productsname);
                    spinner.setAdapter(dataAdapter);
                }
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