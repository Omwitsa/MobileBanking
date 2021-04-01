package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvanceActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.amount) EditText amount;
    @BindView(R.id.sNo) EditText sNo;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.Product) EditText Productname;
    @BindView(R.id.back) Button back;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(AdvanceActivity.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        //getting device model and serial number
        String Machineid = android.os.Build.MODEL+""+ Build.SERIAL;
        final String Accountno = sharedpreferences.getString("supplierNo", "");
        final String Advanceproduct = sharedpreferences.getString("Account","");
        final String idn = sharedpreferences.getString("agtId", "");
        sNo.setText(Accountno);
        Productname.setText(Advanceproduct);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String advamount = amount.getText().toString();
                if (advamount.isEmpty()) {
                    //Snackbar.make(getView(), "Field(s) are empty !", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(AdvanceActivity.this, "Enter Amount to applly ", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
                    editor.putString("operation", "advance");
                    editor.putString("amount", advamount);
                    editor.putString("fingurePrint", "");
                    editor.putString("supplierNo", Accountno);
                    editor.putString("pin", "");
                    editor.putString("agentId", idn);
                    editor.putString("accountNo", Accountno);
                    editor.putString("productDescription", Advanceproduct);
                    editor.commit();
                    startActivity(intent);

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}