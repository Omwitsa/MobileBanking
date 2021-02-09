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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aratek.trustfinger.Model.ProductModel;
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
        sNo.setText(Account);



        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.productname);
        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> val = new ArrayList<String>();
        TransactionModel transaction = new TransactionModel("", 0.0, "", "", "", "", "", "","","",Account);
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
                if (productsname.length <= 0)
                {
                    Toast.makeText(ProductsActivity.this, "You do not have any products ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {


            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Acc = spinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), AdvanceActivity.class);
                editor.putString("supplierNo",Account );
                editor.putString("Account", Acc);
                editor.commit();

                startActivity(intent);

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
}