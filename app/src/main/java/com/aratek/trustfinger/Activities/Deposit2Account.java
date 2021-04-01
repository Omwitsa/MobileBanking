package com.aratek.trustfinger.Activities;

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

import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

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

        submit.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            String bal = amount.getText().toString();
            String sNo = Acc.getText().toString();
            String NamE = Name.getText().toString();
            String ID = IdNumber.getText().toString();
            String Pinn = "";
            String MachineID = android.os.Build.SERIAL;

            if (bal.isEmpty() && Pinn.isEmpty() && sNo.isEmpty()&& NamE.isEmpty()&& ID.isEmpty() ) {
                //Snackbar.make(getView(), "Field(s) are empty !", Snackbar.LENGTH_LONG).show();
                Toast.makeText(Deposit2Account.this, "Fields are empty", Toast.LENGTH_LONG).show();
            }else if(Pinn.length()>4){
                //Snackbar.make(getView(), "Password should have a minimum of 8 characters", Snackbar.LENGTH_LONG).show();
                Toast.makeText(Deposit2Account.this, "Pin should have a maximum of 4 characters", Toast.LENGTH_LONG).show();
            }else{
                //insertDataToSqlite(bal, Pinn, Account);


                Intent intent = new Intent(getApplicationContext(), SubmitTransactionActivity.class);
                editor.putString("operation", "deposit");
                editor.putString("amount", bal);
                editor.putString("fingurePrint", "");
                editor.putString("supplierNo", sNo);
                editor.putString("number", "");
                editor.putString("pin", Pinn);
                editor.putString("name", NamE);
                editor.putString("DidNumber", ID);
                editor.putString("machineId", MachineID);
                editor.putString("auditId", auditId);
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
