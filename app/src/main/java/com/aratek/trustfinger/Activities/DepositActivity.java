package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.aratek.trustfinger.utils.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepositActivity extends AppCompatActivity {
    ProgressDialog progressDoalog;
    ApiInterface apiService;
    @BindView(R.id.amount) EditText amount;
    @BindView(R.id.sNo) EditText sNo;
    //    @BindView(R.id.pin) EditText pin;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;
    static SQLiteDatabase db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(DepositActivity.this);
        //getting device model and serial number
        String Machineid = android.os.Build.MODEL+""+ Build.SERIAL;
        db = openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS deposits(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
//        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        Bundle extras = getActivity().getIntent().getExtras();
//        String operation = extras.getString("operation");
//        Double amount = Double.parseDouble(extras.getString("amount"));
//        String status = "0";
//        String sNo = extras.getString("supplierNo");
//        String machineId = sharedpreferences.getString("machine_id", "");
//        String auditId = sharedpreferences.getString("loggedInUser", "");





        Bundle extras = getIntent().getExtras();
        assert extras != null;
        final String Account = extras.getString("supplierNo");
        sNo.setText(Account);

        submit.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View view) {
                String bal = amount.getText().toString();
                 //String SupNo = sNo.getText().toString();
                String Pinn = "";

                if (bal.isEmpty() && Pinn.isEmpty() && Account.isEmpty() ) {
                    //Snackbar.make(getView(), "Field(s) are empty !", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(DepositActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
                }else if(Pinn.length()>4){
                    //Snackbar.make(getView(), "Password should have a minimum of 8 characters", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(DepositActivity.this, "Pin shoud have a maximum of 4 characters", Toast.LENGTH_LONG).show();
                }else{
                    insertDataToSqlite(bal, Pinn, Account);

                    Intent intent = new Intent(getApplicationContext(), SubmitTransactionActivity.class);
                    intent.putExtra("operation", "deposit");
                    intent.putExtra("amount", bal);
                    intent.putExtra("fingurePrint", "");
                    intent.putExtra("supplierNo", Account);
                    intent.putExtra("pin", Pinn);
                    intent.putExtra("accountNo", "");
                    intent.putExtra("productDescription", "");
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
    public void insertDataToSqlite(String bal, String pinn, String Account)  {
        Calendar cc = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_pp = sdf.format(cc.getTime());
        SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
        String trans= ff.format(cc.getTime());
        db.execSQL("INSERT INTO deposits VALUES('" + bal + "','"  + pinn+ "','" + Account + "','" + date_pp + "','0','" + trans + "');");
        Toast.makeText(DepositActivity.this, "Deposit saved successfully", Toast.LENGTH_LONG).show();
        }



}