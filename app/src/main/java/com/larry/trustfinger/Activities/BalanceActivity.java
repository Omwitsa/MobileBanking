package com.larry.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    @BindView(R.id.sNo) EditText sNo;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(BalanceActivity.this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        final String Account = sharedpreferences.getString("supplierNo", "");
        final String idno = sharedpreferences.getString("number", "");
        final String idn = sharedpreferences.getString("agtId", "");
        sNo.setText(Account);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Account.isEmpty()) {
                    Toast.makeText(BalanceActivity.this, "Your Account Number is Missing", Toast.LENGTH_LONG).show();

                }
                //String SupNo = sNo.getText().toString();
                else {
                    Intent intent = new Intent(getApplicationContext(), ConfirmBalanceActivity.class);
                    editor.putString("operation", "EasyAgent balance");
                    editor.putString("amount", "0");
                    editor.putString("fingurePrint", "");
                    editor.putString("supplierNo", Account);
                    editor.putString("number", idno);
                    editor.putString("pin", "");
                    editor.putString("agentId", idn);
                    editor.putString("accountNo", "");
                    editor.putString("productDescription", "");
                    editor.commit();

                    startActivity(intent);
                }
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
    public void onBackPressed() {
        return;
    }
}