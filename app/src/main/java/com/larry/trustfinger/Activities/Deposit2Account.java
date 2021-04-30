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

public class Deposit2Account extends AppCompatActivity {
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.cash) EditText amount;
    @BindView(R.id.accno) EditText Acc;
    @BindView(R.id.name) EditText Name;
    @BindView(R.id.ID) EditText IdNumber;
    @BindView(R.id.save) Button submit;
    @BindView(R.id.pack) Button back;
    static SQLiteDatabase db;
    private Context context;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit2_account);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(Deposit2Account.this);
        final String auditId = sharedpreferences.getString("AgentId", "");
        final String permit="Allow";

        submit.setOnClickListener(new View.OnClickListener()
        {@Override
        public void onClick(View view) {
            String bal = amount.getText().toString();
            String sNo = Acc.getText().toString();
            String NamE = Name.getText().toString();
            String ID = IdNumber.getText().toString();
            String Pinn = "";
            String MachineID = android.os.Build.SERIAL;
            if (sNo.isEmpty()) {
                Toast.makeText(Deposit2Account.this, "Account is missing", Toast.LENGTH_LONG).show();
            }
            else if(bal.isEmpty())
            {
                Toast.makeText(Deposit2Account.this, "Amount is missing", Toast.LENGTH_LONG).show();
            }
            else if(NamE.isEmpty())
            {
                Toast.makeText(Deposit2Account.this, "Deposited by  is missing", Toast.LENGTH_LONG).show();
            }
            else if(ID.isEmpty())
            {
                Toast.makeText(Deposit2Account.this, "ID or Admission number  is missing", Toast.LENGTH_LONG).show();
            }

           else{

                Intent intent = new Intent(getApplicationContext(), SubmitTransactionActivity.class);
                editor.putString("operation", "EasyAgent deposit");
                editor.putString("amount", bal);
                editor.putString("machineId", MachineID);
                editor.putString("supplierNo", sNo);
                editor.putString("fingurePrint", "");
                editor.putString("name", NamE);
                editor.putString("DidNumber", ID);
                editor.putString("AuditId", auditId);
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
                Intent intent = new Intent(getApplicationContext(), RegisterPhone.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }

}
