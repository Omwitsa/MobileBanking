package com.larry.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

public class WithdrawActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.amount) EditText amount;
    //    @BindView(R.id.pin) EditText pin;
    @BindView(R.id.sNo) EditText sNo;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;
    static SQLiteDatabase db;
    private Context context;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(WithdrawActivity.this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        final String Account = sharedpreferences.getString("supplierNo", "");
        final String idn = sharedpreferences.getString("agtId", "");
        sNo.setText(Account);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bal = amount.getText().toString();
                String Pinn = "";
                if (bal.isEmpty()) {
                    Toast.makeText(WithdrawActivity.this, "Amount is missing", Toast.LENGTH_LONG).show();

                }
                else if(Account.isEmpty())
                {
                    Toast.makeText(WithdrawActivity.this, "Account number is missing", Toast.LENGTH_LONG).show();

                }
                else if(idn.isEmpty())
                  {
                  Toast.makeText(getApplicationContext(),"Agent ID is missing",Toast.LENGTH_LONG).show();
                  }
                else {
                    Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
                    editor.putString("operation", "EasyAgent withdrawal");
                    editor.putString("amount", bal);
                    editor.putString("fingurePrint", "");
                    editor.putString("supplierNo", Account);
                    editor.putString("pin", Pinn);
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