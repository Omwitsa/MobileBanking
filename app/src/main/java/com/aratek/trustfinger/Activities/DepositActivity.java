package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(DepositActivity.this);
        //getting device model and serial number
        String Machineid = android.os.Build.MODEL+""+ Build.SERIAL;
//        db = openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS deposits(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");

        final String Account = sharedpreferences.getString("supplierNo", "");
        final String idno = sharedpreferences.getString("number", "");
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
                    //insertDataToSqlite(bal, Pinn, Account);

                    Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
                    editor.putString("operation", "deposit");
                    editor.putString("amount", bal);
                    editor.putString("fingurePrint", "");
                    editor.putString("supplierNo", Account);
                    editor.putString("number", idno);
                    editor.putString("pin", Pinn);
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




}